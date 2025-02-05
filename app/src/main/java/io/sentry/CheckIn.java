package io.sentry;

import io.sentry.MonitorConfig;
import io.sentry.MonitorContexts;
import io.sentry.protocol.SentryId;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class CheckIn implements JsonUnknown, JsonSerializable {
    private final SentryId checkInId;
    private final MonitorContexts contexts;
    private Double duration;
    private String environment;
    private MonitorConfig monitorConfig;
    private String monitorSlug;
    private String release;
    private String status;
    private Map<String, Object> unknown;

    public static final class JsonKeys {
        public static final String CHECK_IN_ID = "check_in_id";
        public static final String CONTEXTS = "contexts";
        public static final String DURATION = "duration";
        public static final String ENVIRONMENT = "environment";
        public static final String MONITOR_CONFIG = "monitor_config";
        public static final String MONITOR_SLUG = "monitor_slug";
        public static final String RELEASE = "release";
        public static final String STATUS = "status";
    }

    public CheckIn(String str, CheckInStatus checkInStatus) {
        this((SentryId) null, str, checkInStatus.apiName());
    }

    public CheckIn(SentryId sentryId, String str, CheckInStatus checkInStatus) {
        this(sentryId, str, checkInStatus.apiName());
    }

    public CheckIn(SentryId sentryId, String str, String str2) {
        this.contexts = new MonitorContexts();
        this.checkInId = sentryId == null ? new SentryId() : sentryId;
        this.monitorSlug = str;
        this.status = str2;
    }

    public SentryId getCheckInId() {
        return this.checkInId;
    }

    public String getMonitorSlug() {
        return this.monitorSlug;
    }

    public void setMonitorSlug(String str) {
        this.monitorSlug = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setStatus(CheckInStatus checkInStatus) {
        this.status = checkInStatus.apiName();
    }

    public Double getDuration() {
        return this.duration;
    }

    public void setDuration(Double d) {
        this.duration = d;
    }

    public String getRelease() {
        return this.release;
    }

    public void setRelease(String str) {
        this.release = str;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String str) {
        this.environment = str;
    }

    public MonitorConfig getMonitorConfig() {
        return this.monitorConfig;
    }

    public void setMonitorConfig(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
    }

    public MonitorContexts getContexts() {
        return this.contexts;
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
        objectWriter.name(JsonKeys.CHECK_IN_ID);
        this.checkInId.serialize(objectWriter, iLogger);
        objectWriter.name(JsonKeys.MONITOR_SLUG).value(this.monitorSlug);
        objectWriter.name("status").value(this.status);
        if (this.duration != null) {
            objectWriter.name("duration").value(this.duration);
        }
        if (this.release != null) {
            objectWriter.name("release").value(this.release);
        }
        if (this.environment != null) {
            objectWriter.name("environment").value(this.environment);
        }
        if (this.monitorConfig != null) {
            objectWriter.name(JsonKeys.MONITOR_CONFIG);
            this.monitorConfig.serialize(objectWriter, iLogger);
        }
        if (this.contexts != null) {
            objectWriter.name("contexts");
            this.contexts.serialize(objectWriter, iLogger);
        }
        Map<String, Object> map = this.unknown;
        if (map != null) {
            for (String str : map.keySet()) {
                objectWriter.name(str).value(iLogger, this.unknown.get(str));
            }
        }
        objectWriter.endObject();
    }

    public static final class Deserializer implements JsonDeserializer<CheckIn> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public CheckIn deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            jsonObjectReader.beginObject();
            SentryId sentryId = null;
            String str = null;
            String str2 = null;
            HashMap hashMap = null;
            Double d = null;
            String str3 = null;
            String str4 = null;
            MonitorConfig monitorConfig = null;
            MonitorContexts monitorContexts = null;
            while (jsonObjectReader.peek() == JsonToken.NAME) {
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "monitor_config":
                        monitorConfig = new MonitorConfig.Deserializer().deserialize(jsonObjectReader, iLogger);
                        break;
                    case "duration":
                        d = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "status":
                        str2 = jsonObjectReader.nextStringOrNull();
                        break;
                    case "contexts":
                        monitorContexts = new MonitorContexts.Deserializer().deserialize(jsonObjectReader, iLogger);
                        break;
                    case "environment":
                        str4 = jsonObjectReader.nextStringOrNull();
                        break;
                    case "release":
                        str3 = jsonObjectReader.nextStringOrNull();
                        break;
                    case "check_in_id":
                        sentryId = new SentryId.Deserializer().deserialize(jsonObjectReader, iLogger);
                        break;
                    case "monitor_slug":
                        str = jsonObjectReader.nextStringOrNull();
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
            if (sentryId == null) {
                IllegalStateException illegalStateException = new IllegalStateException("Missing required field \"check_in_id\"");
                iLogger.log(SentryLevel.ERROR, "Missing required field \"check_in_id\"", illegalStateException);
                throw illegalStateException;
            }
            if (str == null) {
                IllegalStateException illegalStateException2 = new IllegalStateException("Missing required field \"monitor_slug\"");
                iLogger.log(SentryLevel.ERROR, "Missing required field \"monitor_slug\"", illegalStateException2);
                throw illegalStateException2;
            }
            if (str2 == null) {
                IllegalStateException illegalStateException3 = new IllegalStateException("Missing required field \"status\"");
                iLogger.log(SentryLevel.ERROR, "Missing required field \"status\"", illegalStateException3);
                throw illegalStateException3;
            }
            CheckIn checkIn = new CheckIn(sentryId, str, str2);
            checkIn.setDuration(d);
            checkIn.setRelease(str3);
            checkIn.setEnvironment(str4);
            checkIn.setMonitorConfig(monitorConfig);
            checkIn.getContexts().putAll(monitorContexts);
            checkIn.setUnknown(hashMap);
            return checkIn;
        }
    }
}
