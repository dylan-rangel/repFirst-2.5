package com.RNFetchBlob.Response;

import com.RNFetchBlob.RNFetchBlobConst;
import com.RNFetchBlob.RNFetchBlobProgressConfig;
import com.RNFetchBlob.RNFetchBlobReq;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public class RNFetchBlobFileResp extends ResponseBody {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesDownloaded = 0;
    boolean isEndMarkerReceived = false;
    String mPath;
    String mTaskId;
    FileOutputStream ofStream;
    ResponseBody originalBody;
    ReactApplicationContext rctContext;

    public RNFetchBlobFileResp(ReactApplicationContext reactApplicationContext, String str, ResponseBody responseBody, String str2, boolean z) throws IOException {
        this.rctContext = reactApplicationContext;
        this.mTaskId = str;
        this.originalBody = responseBody;
        this.mPath = str2;
        if (str2 != null) {
            boolean z2 = !z;
            String replace = str2.replace("?append=true", "");
            this.mPath = replace;
            File file = new File(replace);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parentFile);
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            this.ofStream = new FileOutputStream(new File(replace), z2);
        }
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentType */
    public MediaType get$contentType() {
        return this.originalBody.get$contentType();
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentLength */
    public long getContentLength() {
        return this.originalBody.getContentLength();
    }

    public boolean isDownloadComplete() {
        return this.bytesDownloaded == getContentLength() || (getContentLength() == -1 && this.isEndMarkerReceived);
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: source */
    public BufferedSource getSource() {
        return Okio.buffer(new ProgressReportingSource());
    }

    private class ProgressReportingSource implements Source {
        @Override // okio.Source
        /* renamed from: timeout */
        public Timeout getTimeout() {
            return null;
        }

        private ProgressReportingSource() {
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            int i = (int) j;
            try {
                byte[] bArr = new byte[i];
                long read = RNFetchBlobFileResp.this.originalBody.byteStream().read(bArr, 0, i);
                RNFetchBlobFileResp.this.bytesDownloaded += read > 0 ? read : 0L;
                if (read > 0) {
                    RNFetchBlobFileResp.this.ofStream.write(bArr, 0, (int) read);
                } else if (RNFetchBlobFileResp.this.getContentLength() == -1 && read == -1) {
                    RNFetchBlobFileResp.this.isEndMarkerReceived = true;
                }
                RNFetchBlobProgressConfig reportProgress = RNFetchBlobReq.getReportProgress(RNFetchBlobFileResp.this.mTaskId);
                if (RNFetchBlobFileResp.this.getContentLength() != 0) {
                    float contentLength = RNFetchBlobFileResp.this.getContentLength() != -1 ? RNFetchBlobFileResp.this.bytesDownloaded / RNFetchBlobFileResp.this.getContentLength() : RNFetchBlobFileResp.this.isEndMarkerReceived ? 1.0f : 0.0f;
                    if (reportProgress != null && reportProgress.shouldReport(contentLength)) {
                        if (RNFetchBlobFileResp.this.getContentLength() != -1) {
                            reportProgress(RNFetchBlobFileResp.this.mTaskId, RNFetchBlobFileResp.this.bytesDownloaded, RNFetchBlobFileResp.this.getContentLength());
                        } else if (!RNFetchBlobFileResp.this.isEndMarkerReceived) {
                            reportProgress(RNFetchBlobFileResp.this.mTaskId, 0L, RNFetchBlobFileResp.this.getContentLength());
                        } else {
                            reportProgress(RNFetchBlobFileResp.this.mTaskId, RNFetchBlobFileResp.this.bytesDownloaded, RNFetchBlobFileResp.this.bytesDownloaded);
                        }
                    }
                }
                return read;
            } catch (Exception unused) {
                return -1L;
            }
        }

        private void reportProgress(String str, long j, long j2) {
            WritableMap createMap = Arguments.createMap();
            createMap.putString("taskId", str);
            createMap.putString("written", String.valueOf(j));
            createMap.putString("total", String.valueOf(j2));
            ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNFetchBlobFileResp.this.rctContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_PROGRESS, createMap);
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            RNFetchBlobFileResp.this.ofStream.close();
        }
    }
}
