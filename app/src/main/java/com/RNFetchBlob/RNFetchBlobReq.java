package com.RNFetchBlob;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.os.Build;
import android.util.Base64;
import androidx.exifinterface.media.ExifInterface;
import com.RNFetchBlob.Response.RNFetchBlobFileResp;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.common.net.HttpHeaders;
import io.sentry.ProfilingTraceData;
import io.sentry.protocol.SentryThread;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;

/* loaded from: classes.dex */
public class RNFetchBlobReq extends BroadcastReceiver implements Runnable {
    Callback callback;
    OkHttpClient client;
    long contentLength;
    String destPath;
    long downloadManagerId;
    ReadableMap headers;
    String method;
    RNFetchBlobConfig options;
    String rawRequestBody;
    ReadableArray rawRequestBodyArray;
    RNFetchBlobBody requestBody;
    RequestType requestType;
    WritableMap respInfo;
    ResponseType responseType;
    String taskId;
    String url;
    public static HashMap<String, Call> taskTable = new HashMap<>();
    public static HashMap<String, Long> androidDownloadManagerTaskTable = new HashMap<>();
    static HashMap<String, RNFetchBlobProgressConfig> progressReport = new HashMap<>();
    static HashMap<String, RNFetchBlobProgressConfig> uploadProgressReport = new HashMap<>();
    static ConnectionPool pool = new ConnectionPool();
    ResponseFormat responseFormat = ResponseFormat.Auto;
    boolean timeout = false;
    ArrayList<String> redirects = new ArrayList<>();

    enum RequestType {
        Form,
        SingleFile,
        AsIs,
        WithoutBody,
        Others
    }

    enum ResponseFormat {
        Auto,
        UTF8,
        BASE64
    }

    enum ResponseType {
        KeepInMemory,
        FileStorage
    }

    public RNFetchBlobReq(ReadableMap readableMap, String str, String str2, String str3, ReadableMap readableMap2, String str4, ReadableArray readableArray, OkHttpClient okHttpClient, Callback callback) {
        this.method = str2.toUpperCase();
        RNFetchBlobConfig rNFetchBlobConfig = new RNFetchBlobConfig(readableMap);
        this.options = rNFetchBlobConfig;
        this.taskId = str;
        this.url = str3;
        this.headers = readableMap2;
        this.callback = callback;
        this.rawRequestBody = str4;
        this.rawRequestBodyArray = readableArray;
        this.client = okHttpClient;
        if (rNFetchBlobConfig.fileCache.booleanValue() || this.options.path != null) {
            this.responseType = ResponseType.FileStorage;
        } else {
            this.responseType = ResponseType.KeepInMemory;
        }
        if (str4 != null) {
            this.requestType = RequestType.SingleFile;
        } else if (readableArray != null) {
            this.requestType = RequestType.Form;
        } else {
            this.requestType = RequestType.WithoutBody;
        }
    }

    public static void cancelTask(String str) {
        if (taskTable.containsKey(str)) {
            taskTable.get(str).cancel();
            taskTable.remove(str);
        }
        if (androidDownloadManagerTaskTable.containsKey(str)) {
            ((DownloadManager) RNFetchBlob.RCTContext.getApplicationContext().getSystemService("download")).remove(androidDownloadManagerTaskTable.get(str).longValue());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x044f A[Catch: Exception -> 0x049f, TryCatch #1 {Exception -> 0x049f, blocks: (B:54:0x01b3, B:56:0x01bd, B:57:0x01ca, B:59:0x01d5, B:61:0x01db, B:63:0x01ef, B:69:0x01fe, B:73:0x0205, B:76:0x020b, B:78:0x0220, B:68:0x0219, B:84:0x0234, B:85:0x0239, B:87:0x023e, B:88:0x024d, B:90:0x0256, B:91:0x025a, B:93:0x0260, B:100:0x0272, B:110:0x027a, B:103:0x027f, B:106:0x0288, B:96:0x028d, B:113:0x029c, B:116:0x02aa, B:118:0x02b2, B:121:0x02bb, B:122:0x033f, B:130:0x0431, B:132:0x044f, B:133:0x0461, B:135:0x0361, B:137:0x0369, B:139:0x0371, B:142:0x037a, B:143:0x0382, B:144:0x0391, B:145:0x03dc, B:146:0x0407, B:147:0x02c1, B:149:0x02cd, B:150:0x02e7, B:152:0x02eb, B:154:0x02f3, B:157:0x02fe, B:159:0x0308, B:162:0x0315, B:163:0x031a, B:165:0x032a, B:166:0x032d, B:168:0x0333, B:169:0x0336, B:170:0x033b, B:171:0x02d2, B:173:0x02d8, B:175:0x02de, B:176:0x02e3, B:179:0x024a, B:180:0x01c4), top: B:53:0x01b3, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0407 A[Catch: Exception -> 0x049f, TryCatch #1 {Exception -> 0x049f, blocks: (B:54:0x01b3, B:56:0x01bd, B:57:0x01ca, B:59:0x01d5, B:61:0x01db, B:63:0x01ef, B:69:0x01fe, B:73:0x0205, B:76:0x020b, B:78:0x0220, B:68:0x0219, B:84:0x0234, B:85:0x0239, B:87:0x023e, B:88:0x024d, B:90:0x0256, B:91:0x025a, B:93:0x0260, B:100:0x0272, B:110:0x027a, B:103:0x027f, B:106:0x0288, B:96:0x028d, B:113:0x029c, B:116:0x02aa, B:118:0x02b2, B:121:0x02bb, B:122:0x033f, B:130:0x0431, B:132:0x044f, B:133:0x0461, B:135:0x0361, B:137:0x0369, B:139:0x0371, B:142:0x037a, B:143:0x0382, B:144:0x0391, B:145:0x03dc, B:146:0x0407, B:147:0x02c1, B:149:0x02cd, B:150:0x02e7, B:152:0x02eb, B:154:0x02f3, B:157:0x02fe, B:159:0x0308, B:162:0x0315, B:163:0x031a, B:165:0x032a, B:166:0x032d, B:168:0x0333, B:169:0x0336, B:170:0x033b, B:171:0x02d2, B:173:0x02d8, B:175:0x02de, B:176:0x02e3, B:179:0x024a, B:180:0x01c4), top: B:53:0x01b3, inners: #0 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instructions count: 1229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobReq.run():void");
    }

    /* renamed from: com.RNFetchBlob.RNFetchBlobReq$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType;

        static {
            int[] iArr = new int[ResponseType.values().length];
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType = iArr;
            try {
                iArr[ResponseType.KeepInMemory.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType[ResponseType.FileStorage.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[RequestType.values().length];
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = iArr2;
            try {
                iArr2[RequestType.SingleFile.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[RequestType.AsIs.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[RequestType.Form.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[RequestType.WithoutBody.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseTaskResource() {
        if (taskTable.containsKey(this.taskId)) {
            taskTable.remove(this.taskId);
        }
        if (androidDownloadManagerTaskTable.containsKey(this.taskId)) {
            androidDownloadManagerTaskTable.remove(this.taskId);
        }
        if (uploadProgressReport.containsKey(this.taskId)) {
            uploadProgressReport.remove(this.taskId);
        }
        if (progressReport.containsKey(this.taskId)) {
            progressReport.remove(this.taskId);
        }
        RNFetchBlobBody rNFetchBlobBody = this.requestBody;
        if (rNFetchBlobBody != null) {
            rNFetchBlobBody.clearRequestBody();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void done(Response response) {
        boolean isBlobResponse = isBlobResponse(response);
        emitStateEvent(getResponseInfo(response, isBlobResponse));
        int i = AnonymousClass4.$SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType[this.responseType.ordinal()];
        if (i == 1) {
            if (isBlobResponse) {
                try {
                    if (this.options.auto.booleanValue()) {
                        String tmpPath = RNFetchBlobFS.getTmpPath(this.taskId);
                        InputStream byteStream = response.body().byteStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(tmpPath));
                        byte[] bArr = new byte[10240];
                        while (true) {
                            int read = byteStream.read(bArr);
                            if (read == -1) {
                                break;
                            } else {
                                fileOutputStream.write(bArr, 0, read);
                            }
                        }
                        byteStream.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_PATH, tmpPath);
                    }
                } catch (IOException unused) {
                    this.callback.invoke("RNFetchBlob failed to encode response data to BASE64 string.", null);
                }
            }
            byte[] bytes = response.body().bytes();
            CharsetEncoder newEncoder = Charset.forName("UTF-8").newEncoder();
            if (this.responseFormat == ResponseFormat.BASE64) {
                this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_BASE64, Base64.encodeToString(bytes, 2));
                return;
            }
            try {
                newEncoder.encode(ByteBuffer.wrap(bytes).asCharBuffer());
                this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_UTF8, new String(bytes));
            } catch (CharacterCodingException unused2) {
                if (this.responseFormat == ResponseFormat.UTF8) {
                    this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_UTF8, new String(bytes));
                } else {
                    this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_BASE64, Base64.encodeToString(bytes, 2));
                }
            }
        } else if (i == 2) {
            ResponseBody body = response.body();
            try {
                body.bytes();
            } catch (Exception unused3) {
            }
            RNFetchBlobFileResp rNFetchBlobFileResp = (RNFetchBlobFileResp) body;
            if (rNFetchBlobFileResp != null && !rNFetchBlobFileResp.isDownloadComplete()) {
                this.callback.invoke("Download interrupted.", null);
            } else {
                String replace = this.destPath.replace("?append=true", "");
                this.destPath = replace;
                this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_PATH, replace);
            }
        } else {
            try {
                this.callback.invoke(null, RNFetchBlobConst.RNFB_RESPONSE_UTF8, new String(response.body().bytes(), "UTF-8"));
            } catch (IOException unused4) {
                this.callback.invoke("RNFetchBlob failed to encode response data to UTF8 string.", null);
            }
        }
        response.body().close();
        releaseTaskResource();
    }

    public static RNFetchBlobProgressConfig getReportProgress(String str) {
        if (progressReport.containsKey(str)) {
            return progressReport.get(str);
        }
        return null;
    }

    public static RNFetchBlobProgressConfig getReportUploadProgress(String str) {
        if (uploadProgressReport.containsKey(str)) {
            return uploadProgressReport.get(str);
        }
        return null;
    }

    private WritableMap getResponseInfo(Response response, boolean z) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("status", response.code());
        createMap.putString(SentryThread.JsonKeys.STATE, ExifInterface.GPS_MEASUREMENT_2D);
        createMap.putString("taskId", this.taskId);
        createMap.putBoolean(ProfilingTraceData.TRUNCATION_REASON_TIMEOUT, this.timeout);
        WritableMap createMap2 = Arguments.createMap();
        for (int i = 0; i < response.headers().size(); i++) {
            createMap2.putString(response.headers().name(i), response.headers().value(i));
        }
        WritableArray createArray = Arguments.createArray();
        Iterator<String> it = this.redirects.iterator();
        while (it.hasNext()) {
            createArray.pushString(it.next());
        }
        createMap.putArray("redirects", createArray);
        createMap.putMap("headers", createMap2);
        Headers headers = response.headers();
        if (z) {
            createMap.putString("respType", "blob");
        } else if (getHeaderIgnoreCases(headers, "content-type").equalsIgnoreCase("text/")) {
            createMap.putString("respType", "text");
        } else if (getHeaderIgnoreCases(headers, "content-type").contains("application/json")) {
            createMap.putString("respType", "json");
        } else {
            createMap.putString("respType", "");
        }
        return createMap;
    }

    private boolean isBlobResponse(Response response) {
        boolean z;
        String headerIgnoreCases = getHeaderIgnoreCases(response.headers(), HttpHeaders.CONTENT_TYPE);
        boolean z2 = !headerIgnoreCases.equalsIgnoreCase("text/");
        boolean z3 = !headerIgnoreCases.equalsIgnoreCase("application/json");
        if (this.options.binaryContentTypes != null) {
            for (int i = 0; i < this.options.binaryContentTypes.size(); i++) {
                if (headerIgnoreCases.toLowerCase().contains(this.options.binaryContentTypes.getString(i).toLowerCase())) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return !(z3 || z2) || z;
    }

    private String getHeaderIgnoreCases(Headers headers, String str) {
        String str2 = headers.get(str);
        return str2 != null ? str2 : headers.get(str.toLowerCase()) == null ? "" : headers.get(str.toLowerCase());
    }

    private String getHeaderIgnoreCases(HashMap<String, String> hashMap, String str) {
        String str2 = hashMap.get(str);
        if (str2 != null) {
            return str2;
        }
        String str3 = hashMap.get(str.toLowerCase());
        return str3 == null ? "" : str3;
    }

    private void emitStateEvent(WritableMap writableMap) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNFetchBlob.RCTContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_HTTP_STATE, writableMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onReceive(android.content.Context r14, android.content.Intent r15) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobReq.onReceive(android.content.Context, android.content.Intent):void");
    }

    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder builder) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length == 1) {
                    TrustManager trustManager = trustManagers[0];
                    if (trustManager instanceof X509TrustManager) {
                        X509TrustManager x509TrustManager = (X509TrustManager) trustManager;
                        SSLContext sSLContext = SSLContext.getInstance("SSL");
                        sSLContext.init(null, new TrustManager[]{x509TrustManager}, null);
                        builder.sslSocketFactory(sSLContext.getSocketFactory(), x509TrustManager);
                        ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build();
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(build);
                        arrayList.add(ConnectionSpec.COMPATIBLE_TLS);
                        arrayList.add(ConnectionSpec.CLEARTEXT);
                        builder.connectionSpecs(arrayList);
                    }
                }
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            } catch (Exception e) {
                FLog.e("OkHttpClientProvider", "Error while enabling TLS 1.2", e);
            }
        }
        return builder;
    }
}
