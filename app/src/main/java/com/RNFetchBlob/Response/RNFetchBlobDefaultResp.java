package com.RNFetchBlob.Response;

import com.RNFetchBlob.RNFetchBlobConst;
import com.RNFetchBlob.RNFetchBlobProgressConfig;
import com.RNFetchBlob.RNFetchBlobReq;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;

/* loaded from: classes.dex */
public class RNFetchBlobDefaultResp extends ResponseBody {
    boolean isIncrement;
    String mTaskId;
    ResponseBody originalBody;
    ReactApplicationContext rctContext;

    public RNFetchBlobDefaultResp(ReactApplicationContext reactApplicationContext, String str, ResponseBody responseBody, boolean z) {
        this.rctContext = reactApplicationContext;
        this.mTaskId = str;
        this.originalBody = responseBody;
        this.isIncrement = z;
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

    @Override // okhttp3.ResponseBody
    /* renamed from: source */
    public BufferedSource getSource() {
        return Okio.buffer(new ProgressReportingSource(this.originalBody.getSource()));
    }

    private class ProgressReportingSource implements Source {
        long bytesRead = 0;
        BufferedSource mOriginalSource;

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        @Override // okio.Source
        /* renamed from: timeout */
        public Timeout getTimeout() {
            return null;
        }

        ProgressReportingSource(BufferedSource bufferedSource) {
            this.mOriginalSource = bufferedSource;
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            long read = this.mOriginalSource.read(buffer, j);
            this.bytesRead += read > 0 ? read : 0L;
            RNFetchBlobProgressConfig reportProgress = RNFetchBlobReq.getReportProgress(RNFetchBlobDefaultResp.this.mTaskId);
            long contentLength = RNFetchBlobDefaultResp.this.getContentLength();
            if (reportProgress != null && contentLength != 0 && reportProgress.shouldReport(this.bytesRead / RNFetchBlobDefaultResp.this.getContentLength())) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString("taskId", RNFetchBlobDefaultResp.this.mTaskId);
                createMap.putString("written", String.valueOf(this.bytesRead));
                createMap.putString("total", String.valueOf(RNFetchBlobDefaultResp.this.getContentLength()));
                if (RNFetchBlobDefaultResp.this.isIncrement) {
                    createMap.putString("chunk", buffer.readString(Charset.defaultCharset()));
                } else {
                    createMap.putString("chunk", "");
                }
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNFetchBlobDefaultResp.this.rctContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_PROGRESS, createMap);
            }
            return read;
        }
    }
}
