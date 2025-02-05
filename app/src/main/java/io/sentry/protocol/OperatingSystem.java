package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonObjectReader;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public final class OperatingSystem implements JsonUnknown, JsonSerializable {
    public static final String TYPE = "os";
    private String build;
    private String kernelVersion;
    private String name;
    private String rawDescription;
    private Boolean rooted;
    private Map<String, Object> unknown;
    private String version;

    public static final class JsonKeys {
        public static final String BUILD = "build";
        public static final String KERNEL_VERSION = "kernel_version";
        public static final String NAME = "name";
        public static final String RAW_DESCRIPTION = "raw_description";
        public static final String ROOTED = "rooted";
        public static final String VERSION = "version";
    }

    public OperatingSystem() {
    }

    OperatingSystem(OperatingSystem operatingSystem) {
        this.name = operatingSystem.name;
        this.version = operatingSystem.version;
        this.rawDescription = operatingSystem.rawDescription;
        this.build = operatingSystem.build;
        this.kernelVersion = operatingSystem.kernelVersion;
        this.rooted = operatingSystem.rooted;
        this.unknown = CollectionUtils.newConcurrentHashMap(operatingSystem.unknown);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getRawDescription() {
        return this.rawDescription;
    }

    public void setRawDescription(String str) {
        this.rawDescription = str;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String str) {
        this.build = str;
    }

    public String getKernelVersion() {
        return this.kernelVersion;
    }

    public void setKernelVersion(String str) {
        this.kernelVersion = str;
    }

    public Boolean isRooted() {
        return this.rooted;
    }

    public void setRooted(Boolean bool) {
        this.rooted = bool;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OperatingSystem operatingSystem = (OperatingSystem) obj;
        return Objects.equals(this.name, operatingSystem.name) && Objects.equals(this.version, operatingSystem.version) && Objects.equals(this.rawDescription, operatingSystem.rawDescription) && Objects.equals(this.build, operatingSystem.build) && Objects.equals(this.kernelVersion, operatingSystem.kernelVersion) && Objects.equals(this.rooted, operatingSystem.rooted);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.version, this.rawDescription, this.build, this.kernelVersion, this.rooted);
    }

    @Override // io.sentry.JsonSerializable
    public void serialize(ObjectWriter objectWriter, ILogger iLogger) throws IOException {
        objectWriter.beginObject();
        if (this.name != null) {
            objectWriter.name("name").value(this.name);
        }
        if (this.version != null) {
            objectWriter.name("version").value(this.version);
        }
        if (this.rawDescription != null) {
            objectWriter.name("raw_description").value(this.rawDescription);
        }
        if (this.build != null) {
            objectWriter.name(JsonKeys.BUILD).value(this.build);
        }
        if (this.kernelVersion != null) {
            objectWriter.name(JsonKeys.KERNEL_VERSION).value(this.kernelVersion);
        }
        if (this.rooted != null) {
            objectWriter.name(JsonKeys.ROOTED).value(this.rooted);
        }
        Map<String, Object> map = this.unknown;
        if (map != null) {
            for (String str : map.keySet()) {
                Object obj = this.unknown.get(str);
                objectWriter.name(str);
                objectWriter.value(iLogger, obj);
            }
        }
        objectWriter.endObject();
    }

    @Override // io.sentry.JsonUnknown
    public Map<String, Object> getUnknown() {
        return this.unknown;
    }

    @Override // io.sentry.JsonUnknown
    public void setUnknown(Map<String, Object> map) {
        this.unknown = map;
    }

    public static final class Deserializer implements JsonDeserializer<OperatingSystem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public OperatingSystem deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            jsonObjectReader.beginObject();
            OperatingSystem operatingSystem = new OperatingSystem();
            ConcurrentHashMap concurrentHashMap = null;
            while (jsonObjectReader.peek() == JsonToken.NAME) {
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "rooted":
                        operatingSystem.rooted = jsonObjectReader.nextBooleanOrNull();
                        break;
                    case "raw_description":
                        operatingSystem.rawDescription = jsonObjectReader.nextStringOrNull();
                        break;
                    case "name":
                        operatingSystem.name = jsonObjectReader.nextStringOrNull();
                        break;
                    case "build":
                        operatingSystem.build = jsonObjectReader.nextStringOrNull();
                        break;
                    case "version":
                        operatingSystem.version = jsonObjectReader.nextStringOrNull();
                        break;
                    case "kernel_version":
                        operatingSystem.kernelVersion = jsonObjectReader.nextStringOrNull();
                        break;
                    default:
                        if (concurrentHashMap == null) {
                            concurrentHashMap = new ConcurrentHashMap();
                        }
                        jsonObjectReader.nextUnknown(iLogger, concurrentHashMap, nextName);
                        break;
                }
            }
            operatingSystem.setUnknown(concurrentHashMap);
            jsonObjectReader.endObject();
            return operatingSystem;
        }
    }
}
