package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonObjectReader;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class SdkInfo implements JsonUnknown, JsonSerializable {
    private String sdkName;
    private Map<String, Object> unknown;
    private Integer versionMajor;
    private Integer versionMinor;
    private Integer versionPatchlevel;

    public static final class JsonKeys {
        public static final String SDK_NAME = "sdk_name";
        public static final String VERSION_MAJOR = "version_major";
        public static final String VERSION_MINOR = "version_minor";
        public static final String VERSION_PATCHLEVEL = "version_patchlevel";
    }

    public String getSdkName() {
        return this.sdkName;
    }

    public void setSdkName(String str) {
        this.sdkName = str;
    }

    public Integer getVersionMajor() {
        return this.versionMajor;
    }

    public void setVersionMajor(Integer num) {
        this.versionMajor = num;
    }

    public Integer getVersionMinor() {
        return this.versionMinor;
    }

    public void setVersionMinor(Integer num) {
        this.versionMinor = num;
    }

    public Integer getVersionPatchlevel() {
        return this.versionPatchlevel;
    }

    public void setVersionPatchlevel(Integer num) {
        this.versionPatchlevel = num;
    }

    @Override // io.sentry.JsonUnknown
    public Map<String, Object> getUnknown() {
        return this.unknown;
    }

    @Override // io.sentry.JsonUnknown
    public void setUnknown(Map<String, Object> map) {
        this.unknown = map;
    }

    @Override // io.sentry.JsonSerializable
    public void serialize(ObjectWriter objectWriter, ILogger iLogger) throws IOException {
        objectWriter.beginObject();
        if (this.sdkName != null) {
            objectWriter.name(JsonKeys.SDK_NAME).value(this.sdkName);
        }
        if (this.versionMajor != null) {
            objectWriter.name(JsonKeys.VERSION_MAJOR).value(this.versionMajor);
        }
        if (this.versionMinor != null) {
            objectWriter.name(JsonKeys.VERSION_MINOR).value(this.versionMinor);
        }
        if (this.versionPatchlevel != null) {
            objectWriter.name(JsonKeys.VERSION_PATCHLEVEL).value(this.versionPatchlevel);
        }
        Map<String, Object> map = this.unknown;
        if (map != null) {
            for (String str : map.keySet()) {
                objectWriter.name(str).value(iLogger, this.unknown.get(str));
            }
        }
        objectWriter.endObject();
    }

    public static final class Deserializer implements JsonDeserializer<SdkInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public SdkInfo deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            SdkInfo sdkInfo = new SdkInfo();
            jsonObjectReader.beginObject();
            HashMap hashMap = null;
            while (jsonObjectReader.peek() == JsonToken.NAME) {
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "sdk_name":
                        sdkInfo.sdkName = jsonObjectReader.nextStringOrNull();
                        break;
                    case "version_patchlevel":
                        sdkInfo.versionPatchlevel = jsonObjectReader.nextIntegerOrNull();
                        break;
                    case "version_major":
                        sdkInfo.versionMajor = jsonObjectReader.nextIntegerOrNull();
                        break;
                    case "version_minor":
                        sdkInfo.versionMinor = jsonObjectReader.nextIntegerOrNull();
                        break;
                    default:
                        if (hashMap == null) {
                            hashMap = new HashMap();
                        }
                        jsonObjectReader.nextUnknown(iLogger, hashMap, nextName);
                        break;
                }
            }
            jsonObjectReader.endObject();
            sdkInfo.setUnknown(hashMap);
            return sdkInfo;
        }
    }
}
