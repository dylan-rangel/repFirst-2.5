package com.RNFetchBlob;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import io.sentry.ProfilingTraceData;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes.dex */
class RNFetchBlobConfig {
    public ReadableMap addAndroidDownloads;
    public String appendExt;
    public Boolean auto;
    public ReadableArray binaryContentTypes;
    public Boolean fileCache;
    public Boolean followRedirect;
    public Boolean increment;
    public String key;
    public String mime;
    public Boolean overwrite;
    public String path;
    public long timeout;
    public Boolean trusty;
    public Boolean wifiOnly;

    RNFetchBlobConfig(ReadableMap readableMap) {
        this.wifiOnly = false;
        this.overwrite = true;
        this.timeout = DateUtils.MILLIS_PER_MINUTE;
        this.increment = false;
        this.followRedirect = true;
        this.binaryContentTypes = null;
        if (readableMap == null) {
            return;
        }
        this.fileCache = Boolean.valueOf(readableMap.hasKey("fileCache") ? readableMap.getBoolean("fileCache") : false);
        this.path = readableMap.hasKey(RNFetchBlobConst.RNFB_RESPONSE_PATH) ? readableMap.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH) : null;
        this.appendExt = readableMap.hasKey("appendExt") ? readableMap.getString("appendExt") : "";
        this.trusty = Boolean.valueOf(readableMap.hasKey("trusty") ? readableMap.getBoolean("trusty") : false);
        this.wifiOnly = Boolean.valueOf(readableMap.hasKey("wifiOnly") ? readableMap.getBoolean("wifiOnly") : false);
        if (readableMap.hasKey("addAndroidDownloads")) {
            this.addAndroidDownloads = readableMap.getMap("addAndroidDownloads");
        }
        if (readableMap.hasKey("binaryContentTypes")) {
            this.binaryContentTypes = readableMap.getArray("binaryContentTypes");
        }
        String str = this.path;
        if (str != null && str.toLowerCase().contains("?append=true")) {
            this.overwrite = false;
        }
        if (readableMap.hasKey("overwrite")) {
            this.overwrite = Boolean.valueOf(readableMap.getBoolean("overwrite"));
        }
        if (readableMap.hasKey("followRedirect")) {
            this.followRedirect = Boolean.valueOf(readableMap.getBoolean("followRedirect"));
        }
        this.key = readableMap.hasKey("key") ? readableMap.getString("key") : null;
        this.mime = readableMap.hasKey("contentType") ? readableMap.getString("contentType") : null;
        this.increment = Boolean.valueOf(readableMap.hasKey("increment") ? readableMap.getBoolean("increment") : false);
        this.auto = Boolean.valueOf(readableMap.hasKey("auto") ? readableMap.getBoolean("auto") : false);
        if (readableMap.hasKey(ProfilingTraceData.TRUNCATION_REASON_TIMEOUT)) {
            this.timeout = readableMap.getInt(ProfilingTraceData.TRUNCATION_REASON_TIMEOUT);
        }
    }
}
