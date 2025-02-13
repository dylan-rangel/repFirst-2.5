package com.facebook.react.modules.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactContext;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ForwardingCookieHandler extends CookieHandler {
    private static final String COOKIE_HEADER = "Cookie";
    private static final String VERSION_ONE_HEADER = "Set-cookie2";
    private static final String VERSION_ZERO_HEADER = "Set-cookie";
    private final ReactContext mContext;
    private CookieManager mCookieManager;
    private final CookieSaver mCookieSaver = new CookieSaver();

    private static void possiblyWorkaroundSyncManager(Context context) {
    }

    public void destroy() {
    }

    public ForwardingCookieHandler(ReactContext reactContext) {
        this.mContext = reactContext;
    }

    @Override // java.net.CookieHandler
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> map) throws IOException {
        CookieManager cookieManager = getCookieManager();
        if (cookieManager == null) {
            return Collections.emptyMap();
        }
        String cookie = cookieManager.getCookie(uri.toString());
        if (TextUtils.isEmpty(cookie)) {
            return Collections.emptyMap();
        }
        return Collections.singletonMap("Cookie", Collections.singletonList(cookie));
    }

    @Override // java.net.CookieHandler
    public void put(URI uri, Map<String, List<String>> map) throws IOException {
        String uri2 = uri.toString();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key != null && isCookieHeader(key)) {
                addCookies(uri2, entry.getValue());
            }
        }
    }

    public void clearCookies(Callback callback) {
        clearCookiesAsync(callback);
    }

    private void clearCookiesAsync(final Callback callback) {
        CookieManager cookieManager = getCookieManager();
        if (cookieManager != null) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.1
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(Boolean bool) {
                    ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
                    callback.invoke(bool);
                }
            });
        }
    }

    public void addCookies(String str, List<String> list) {
        CookieManager cookieManager = getCookieManager();
        if (cookieManager == null) {
            return;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            addCookieAsync(str, it.next());
        }
        cookieManager.flush();
        this.mCookieSaver.onCookiesModified();
    }

    private void addCookieAsync(String str, String str2) {
        CookieManager cookieManager = getCookieManager();
        if (cookieManager != null) {
            cookieManager.setCookie(str, str2, null);
        }
    }

    private static boolean isCookieHeader(String str) {
        return str.equalsIgnoreCase(VERSION_ZERO_HEADER) || str.equalsIgnoreCase(VERSION_ONE_HEADER);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.network.ForwardingCookieHandler$2] */
    public void runInBackground(final Runnable runnable) {
        new GuardedAsyncTask<Void, Void>(this.mContext) { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... voidArr) {
                runnable.run();
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CookieManager getCookieManager() {
        if (this.mCookieManager == null) {
            possiblyWorkaroundSyncManager(this.mContext);
            try {
                this.mCookieManager = CookieManager.getInstance();
            } catch (IllegalArgumentException | Exception unused) {
                return null;
            }
        }
        return this.mCookieManager;
    }

    private class CookieSaver {
        private static final int MSG_PERSIST_COOKIES = 1;
        private static final int TIMEOUT = 30000;
        private final Handler mHandler;

        public void onCookiesModified() {
        }

        public CookieSaver() {
            this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.CookieSaver.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    if (message.what != 1) {
                        return false;
                    }
                    CookieSaver.this.persistCookies();
                    return true;
                }
            });
        }

        public void persistCookies() {
            this.mHandler.removeMessages(1);
            ForwardingCookieHandler.this.runInBackground(new Runnable() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.CookieSaver.2
                @Override // java.lang.Runnable
                public void run() {
                    CookieSaver.this.flush();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void flush() {
            CookieManager cookieManager = ForwardingCookieHandler.this.getCookieManager();
            if (cookieManager != null) {
                cookieManager.flush();
            }
        }
    }
}
