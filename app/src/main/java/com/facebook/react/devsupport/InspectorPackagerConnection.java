package com.facebook.react.devsupport;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Inspector;
import com.oblador.keychain.KeychainModule;
import io.sentry.protocol.App;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class InspectorPackagerConnection {
    private static final String TAG = "InspectorPackagerConnection";
    private BundleStatusProvider mBundleStatusProvider;
    private final Connection mConnection;
    private final Map<String, Inspector.LocalConnection> mInspectorConnections = new HashMap();
    private final String mPackageName;

    public interface BundleStatusProvider {
        BundleStatus getBundleStatus();
    }

    public InspectorPackagerConnection(String str, String str2, BundleStatusProvider bundleStatusProvider) {
        this.mConnection = new Connection(str);
        this.mPackageName = str2;
        this.mBundleStatusProvider = bundleStatusProvider;
    }

    public void connect() {
        this.mConnection.connect();
    }

    public void closeQuietly() {
        this.mConnection.close();
    }

    public void sendEventToAllConnections(String str) {
        Iterator<Map.Entry<String, Inspector.LocalConnection>> it = this.mInspectorConnections.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().sendMessage(str);
        }
    }

    void handleProxyMessage(JSONObject jSONObject) throws JSONException, IOException {
        String string;
        string = jSONObject.getString(NotificationCompat.CATEGORY_EVENT);
        string.hashCode();
        switch (string) {
            case "disconnect":
                handleDisconnect(jSONObject.getJSONObject("payload"));
                return;
            case "connect":
                handleConnect(jSONObject.getJSONObject("payload"));
                return;
            case "wrappedEvent":
                handleWrappedEvent(jSONObject.getJSONObject("payload"));
                return;
            case "getPages":
                sendEvent("getPages", getPages());
                return;
            default:
                throw new IllegalArgumentException("Unknown event: " + string);
        }
    }

    void closeAllConnections() {
        Iterator<Map.Entry<String, Inspector.LocalConnection>> it = this.mInspectorConnections.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().disconnect();
        }
        this.mInspectorConnections.clear();
    }

    private void handleConnect(JSONObject jSONObject) throws JSONException {
        final String string = jSONObject.getString("pageId");
        if (this.mInspectorConnections.remove(string) != null) {
            throw new IllegalStateException("Already connected: " + string);
        }
        try {
            this.mInspectorConnections.put(string, Inspector.connect(Integer.parseInt(string), new Inspector.RemoteConnection() { // from class: com.facebook.react.devsupport.InspectorPackagerConnection.1
                @Override // com.facebook.react.bridge.Inspector.RemoteConnection
                public void onMessage(String str) {
                    try {
                        InspectorPackagerConnection.this.sendWrappedEvent(string, str);
                    } catch (JSONException e) {
                        FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                    }
                }

                @Override // com.facebook.react.bridge.Inspector.RemoteConnection
                public void onDisconnect() {
                    try {
                        InspectorPackagerConnection.this.mInspectorConnections.remove(string);
                        InspectorPackagerConnection inspectorPackagerConnection = InspectorPackagerConnection.this;
                        inspectorPackagerConnection.sendEvent("disconnect", inspectorPackagerConnection.makePageIdPayload(string));
                    } catch (JSONException e) {
                        FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                    }
                }
            }));
        } catch (Exception e) {
            FLog.w(TAG, "Failed to open page: " + string, e);
            sendEvent("disconnect", makePageIdPayload(string));
        }
    }

    private void handleDisconnect(JSONObject jSONObject) throws JSONException {
        Inspector.LocalConnection remove = this.mInspectorConnections.remove(jSONObject.getString("pageId"));
        if (remove == null) {
            return;
        }
        remove.disconnect();
    }

    private void handleWrappedEvent(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString("pageId");
        String string2 = jSONObject.getString("wrappedEvent");
        Inspector.LocalConnection localConnection = this.mInspectorConnections.get(string);
        if (localConnection == null) {
            FLog.w(TAG, "PageID " + string + " is disconnected. Dropping event: " + string2);
            return;
        }
        localConnection.sendMessage(string2);
    }

    private JSONArray getPages() throws JSONException {
        List<Inspector.Page> pages = Inspector.getPages();
        JSONArray jSONArray = new JSONArray();
        BundleStatus bundleStatus = this.mBundleStatusProvider.getBundleStatus();
        for (Inspector.Page page : pages) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", String.valueOf(page.getId()));
            jSONObject.put(KeychainModule.AuthPromptOptions.TITLE, page.getTitle());
            jSONObject.put(App.TYPE, this.mPackageName);
            jSONObject.put("vm", page.getVM());
            jSONObject.put("isLastBundleDownloadSuccess", bundleStatus.isLastDownloadSucess);
            jSONObject.put("bundleUpdateTimestamp", bundleStatus.updateTimestamp);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWrappedEvent(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pageId", str);
        jSONObject.put("wrappedEvent", str2);
        sendEvent("wrappedEvent", jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(String str, Object obj) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NotificationCompat.CATEGORY_EVENT, str);
        jSONObject.put("payload", obj);
        this.mConnection.send(jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject makePageIdPayload(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pageId", str);
        return jSONObject;
    }

    private class Connection extends WebSocketListener {
        private static final int RECONNECT_DELAY_MS = 2000;
        private boolean mClosed;
        private final Handler mHandler = new Handler(Looper.getMainLooper());
        private OkHttpClient mHttpClient;
        private boolean mSuppressConnectionErrors;
        private final String mUrl;
        private WebSocket mWebSocket;

        public Connection(String str) {
            this.mUrl = str;
        }

        @Override // okhttp3.WebSocketListener
        public void onOpen(WebSocket webSocket, Response response) {
            this.mWebSocket = webSocket;
        }

        @Override // okhttp3.WebSocketListener
        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            if (this.mWebSocket != null) {
                abort("Websocket exception", th);
            }
            if (this.mClosed) {
                return;
            }
            reconnect();
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, String str) {
            try {
                InspectorPackagerConnection.this.handleProxyMessage(new JSONObject(str));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onClosed(WebSocket webSocket, int i, String str) {
            this.mWebSocket = null;
            InspectorPackagerConnection.this.closeAllConnections();
            if (this.mClosed) {
                return;
            }
            reconnect();
        }

        public void connect() {
            if (this.mClosed) {
                throw new IllegalStateException("Can't connect closed client");
            }
            if (this.mHttpClient == null) {
                this.mHttpClient = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MINUTES).build();
            }
            this.mHttpClient.newWebSocket(new Request.Builder().url(this.mUrl).build(), this);
        }

        private void reconnect() {
            if (this.mClosed) {
                throw new IllegalStateException("Can't reconnect closed client");
            }
            if (!this.mSuppressConnectionErrors) {
                FLog.w(InspectorPackagerConnection.TAG, "Couldn't connect to packager, will silently retry");
                this.mSuppressConnectionErrors = true;
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.facebook.react.devsupport.InspectorPackagerConnection.Connection.1
                @Override // java.lang.Runnable
                public void run() {
                    if (Connection.this.mClosed) {
                        return;
                    }
                    Connection.this.connect();
                }
            }, 2000L);
        }

        public void close() {
            this.mClosed = true;
            WebSocket webSocket = this.mWebSocket;
            if (webSocket != null) {
                try {
                    webSocket.close(1000, "End of session");
                } catch (Exception unused) {
                }
                this.mWebSocket = null;
            }
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.devsupport.InspectorPackagerConnection$Connection$2] */
        public void send(final JSONObject jSONObject) {
            new AsyncTask<WebSocket, Void, Void>() { // from class: com.facebook.react.devsupport.InspectorPackagerConnection.Connection.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public Void doInBackground(WebSocket... webSocketArr) {
                    if (webSocketArr != null && webSocketArr.length != 0) {
                        try {
                            webSocketArr[0].send(jSONObject.toString());
                        } catch (Exception e) {
                            FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                        }
                    }
                    return null;
                }
            }.execute(this.mWebSocket);
        }

        private void abort(String str, Throwable th) {
            FLog.e(InspectorPackagerConnection.TAG, "Error occurred, shutting down websocket connection: " + str, th);
            InspectorPackagerConnection.this.closeAllConnections();
            closeWebSocketQuietly();
        }

        private void closeWebSocketQuietly() {
            WebSocket webSocket = this.mWebSocket;
            if (webSocket != null) {
                try {
                    webSocket.close(1000, "End of session");
                } catch (Exception unused) {
                }
                this.mWebSocket = null;
            }
        }
    }

    public static class BundleStatus {
        public Boolean isLastDownloadSucess;
        public long updateTimestamp;

        public BundleStatus(Boolean bool, long j) {
            this.isLastDownloadSucess = bool;
            this.updateTimestamp = j;
        }

        public BundleStatus() {
            this(false, -1L);
        }
    }
}
