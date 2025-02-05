package io.sentry.protocol;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonObjectReader;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.Span;
import io.sentry.SpanId;
import io.sentry.SpanStatus;
import io.sentry.protocol.SentryId;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public final class SentrySpan implements JsonUnknown, JsonSerializable {
    private final Map<String, Object> data;
    private final String description;
    private final String op;
    private final String origin;
    private final SpanId parentSpanId;
    private final SpanId spanId;
    private final Double startTimestamp;
    private final SpanStatus status;
    private final Map<String, String> tags;
    private final Double timestamp;
    private final SentryId traceId;
    private Map<String, Object> unknown;

    public static final class JsonKeys {
        public static final String DATA = "data";
        public static final String DESCRIPTION = "description";
        public static final String OP = "op";
        public static final String ORIGIN = "origin";
        public static final String PARENT_SPAN_ID = "parent_span_id";
        public static final String SPAN_ID = "span_id";
        public static final String START_TIMESTAMP = "start_timestamp";
        public static final String STATUS = "status";
        public static final String TAGS = "tags";
        public static final String TIMESTAMP = "timestamp";
        public static final String TRACE_ID = "trace_id";
    }

    public SentrySpan(Span span) {
        this(span, span.getData());
    }

    public SentrySpan(Span span, Map<String, Object> map) {
        Objects.requireNonNull(span, "span is required");
        this.description = span.getDescription();
        this.op = span.getOperation();
        this.spanId = span.getSpanId();
        this.parentSpanId = span.getParentSpanId();
        this.traceId = span.getTraceId();
        this.status = span.getStatus();
        this.origin = span.getSpanContext().getOrigin();
        Map<String, String> newConcurrentHashMap = CollectionUtils.newConcurrentHashMap(span.getTags());
        this.tags = newConcurrentHashMap == null ? new ConcurrentHashMap<>() : newConcurrentHashMap;
        this.timestamp = Double.valueOf(DateUtils.nanosToSeconds(span.getStartDate().laterDateNanosTimestampByDiff(span.getFinishDate())));
        this.startTimestamp = Double.valueOf(DateUtils.nanosToSeconds(span.getStartDate().nanoTimestamp()));
        this.data = map;
    }

    public SentrySpan(Double d, Double d2, SentryId sentryId, SpanId spanId, SpanId spanId2, String str, String str2, SpanStatus spanStatus, String str3, Map<String, String> map, Map<String, Object> map2) {
        this.startTimestamp = d;
        this.timestamp = d2;
        this.traceId = sentryId;
        this.spanId = spanId;
        this.parentSpanId = spanId2;
        this.op = str;
        this.description = str2;
        this.status = spanStatus;
        this.tags = map;
        this.data = map2;
        this.origin = str3;
    }

    public boolean isFinished() {
        return this.timestamp != null;
    }

    public Double getStartTimestamp() {
        return this.startTimestamp;
    }

    public Double getTimestamp() {
        return this.timestamp;
    }

    public SentryId getTraceId() {
        return this.traceId;
    }

    public SpanId getSpanId() {
        return this.spanId;
    }

    public SpanId getParentSpanId() {
        return this.parentSpanId;
    }

    public String getOp() {
        return this.op;
    }

    public String getDescription() {
        return this.description;
    }

    public SpanStatus getStatus() {
        return this.status;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public String getOrigin() {
        return this.origin;
    }

    @Override // io.sentry.JsonSerializable
    public void serialize(ObjectWriter objectWriter, ILogger iLogger) throws IOException {
        objectWriter.beginObject();
        objectWriter.name("start_timestamp").value(iLogger, doubleToBigDecimal(this.startTimestamp));
        if (this.timestamp != null) {
            objectWriter.name("timestamp").value(iLogger, doubleToBigDecimal(this.timestamp));
        }
        objectWriter.name("trace_id").value(iLogger, this.traceId);
        objectWriter.name("span_id").value(iLogger, this.spanId);
        if (this.parentSpanId != null) {
            objectWriter.name("parent_span_id").value(iLogger, this.parentSpanId);
        }
        objectWriter.name("op").value(this.op);
        if (this.description != null) {
            objectWriter.name("description").value(this.description);
        }
        if (this.status != null) {
            objectWriter.name("status").value(iLogger, this.status);
        }
        if (this.origin != null) {
            objectWriter.name("origin").value(iLogger, this.origin);
        }
        if (!this.tags.isEmpty()) {
            objectWriter.name("tags").value(iLogger, this.tags);
        }
        if (this.data != null) {
            objectWriter.name("data").value(iLogger, this.data);
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

    private BigDecimal doubleToBigDecimal(Double d) {
        return BigDecimal.valueOf(d.doubleValue()).setScale(6, RoundingMode.DOWN);
    }

    @Override // io.sentry.JsonUnknown
    public Map<String, Object> getUnknown() {
        return this.unknown;
    }

    @Override // io.sentry.JsonUnknown
    public void setUnknown(Map<String, Object> map) {
        this.unknown = map;
    }

    public static final class Deserializer implements JsonDeserializer<SentrySpan> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public SentrySpan deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            jsonObjectReader.beginObject();
            Map map = null;
            Double d = null;
            Double d2 = null;
            SentryId sentryId = null;
            SpanId spanId = null;
            SpanId spanId2 = null;
            String str = null;
            String str2 = null;
            SpanStatus spanStatus = null;
            String str3 = null;
            ConcurrentHashMap concurrentHashMap = null;
            Map map2 = null;
            while (true) {
                Map map3 = map2;
                String str4 = str3;
                SpanStatus spanStatus2 = spanStatus;
                String str5 = str2;
                if (jsonObjectReader.peek() != JsonToken.NAME) {
                    if (d == null) {
                        throw missingRequiredFieldException("start_timestamp", iLogger);
                    }
                    if (sentryId == null) {
                        throw missingRequiredFieldException("trace_id", iLogger);
                    }
                    if (spanId == null) {
                        throw missingRequiredFieldException("span_id", iLogger);
                    }
                    if (str == null) {
                        throw missingRequiredFieldException("op", iLogger);
                    }
                    SentrySpan sentrySpan = new SentrySpan(d, d2, sentryId, spanId, spanId2, str, str5, spanStatus2, str4, map == null ? new HashMap() : map, map3);
                    sentrySpan.setUnknown(concurrentHashMap);
                    jsonObjectReader.endObject();
                    return sentrySpan;
                }
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "span_id":
                        spanId = new SpanId.Deserializer().deserialize(jsonObjectReader, iLogger);
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "parent_span_id":
                        spanId2 = (SpanId) jsonObjectReader.nextOrNull(iLogger, new SpanId.Deserializer());
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "description":
                        str2 = jsonObjectReader.nextStringOrNull();
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        break;
                    case "start_timestamp":
                        try {
                            d = jsonObjectReader.nextDoubleOrNull();
                        } catch (NumberFormatException unused) {
                            Date nextDateOrNull = jsonObjectReader.nextDateOrNull(iLogger);
                            d = nextDateOrNull != null ? Double.valueOf(DateUtils.dateToSeconds(nextDateOrNull)) : null;
                        }
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "origin":
                        str3 = jsonObjectReader.nextStringOrNull();
                        map2 = map3;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "status":
                        spanStatus = (SpanStatus) jsonObjectReader.nextOrNull(iLogger, new SpanStatus.Deserializer());
                        map2 = map3;
                        str3 = str4;
                        str2 = str5;
                        break;
                    case "op":
                        str = jsonObjectReader.nextStringOrNull();
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "data":
                        map2 = (Map) jsonObjectReader.nextObjectOrNull();
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "tags":
                        map = (Map) jsonObjectReader.nextObjectOrNull();
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "timestamp":
                        try {
                            d2 = jsonObjectReader.nextDoubleOrNull();
                        } catch (NumberFormatException unused2) {
                            Date nextDateOrNull2 = jsonObjectReader.nextDateOrNull(iLogger);
                            d2 = nextDateOrNull2 != null ? Double.valueOf(DateUtils.dateToSeconds(nextDateOrNull2)) : null;
                        }
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    case "trace_id":
                        sentryId = new SentryId.Deserializer().deserialize(jsonObjectReader, iLogger);
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                    default:
                        if (concurrentHashMap == null) {
                            concurrentHashMap = new ConcurrentHashMap();
                        }
                        jsonObjectReader.nextUnknown(iLogger, concurrentHashMap, nextName);
                        map2 = map3;
                        str3 = str4;
                        spanStatus = spanStatus2;
                        str2 = str5;
                        break;
                }
            }
        }

        private Exception missingRequiredFieldException(String str, ILogger iLogger) {
            String str2 = "Missing required field \"" + str + "\"";
            IllegalStateException illegalStateException = new IllegalStateException(str2);
            iLogger.log(SentryLevel.ERROR, str2, illegalStateException);
            return illegalStateException;
        }
    }
}
