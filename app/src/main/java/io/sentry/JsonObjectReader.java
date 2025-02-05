package io.sentry;

import io.sentry.vendor.gson.stream.JsonReader;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public final class JsonObjectReader extends JsonReader {
    public JsonObjectReader(Reader reader) {
        super(reader);
    }

    public String nextStringOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return nextString();
    }

    public Double nextDoubleOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return Double.valueOf(nextDouble());
    }

    public Float nextFloatOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return nextFloat();
    }

    public Float nextFloat() throws IOException {
        return Float.valueOf((float) nextDouble());
    }

    public Long nextLongOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return Long.valueOf(nextLong());
    }

    public Integer nextIntegerOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return Integer.valueOf(nextInt());
    }

    public Boolean nextBooleanOrNull() throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return Boolean.valueOf(nextBoolean());
    }

    public void nextUnknown(ILogger iLogger, Map<String, Object> map, String str) {
        try {
            map.put(str, nextObjectOrNull());
        } catch (Exception e) {
            iLogger.log(SentryLevel.ERROR, e, "Error deserializing unknown key: %s", str);
        }
    }

    public <T> List<T> nextListOrNull(ILogger iLogger, JsonDeserializer<T> jsonDeserializer) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        beginArray();
        ArrayList arrayList = new ArrayList();
        if (hasNext()) {
            do {
                try {
                    arrayList.add(jsonDeserializer.deserialize(this, iLogger));
                } catch (Exception e) {
                    iLogger.log(SentryLevel.WARNING, "Failed to deserialize object in list.", e);
                }
            } while (peek() == JsonToken.BEGIN_OBJECT);
        }
        endArray();
        return arrayList;
    }

    public <T> Map<String, T> nextMapOrNull(ILogger iLogger, JsonDeserializer<T> jsonDeserializer) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        beginObject();
        HashMap hashMap = new HashMap();
        if (hasNext()) {
            while (true) {
                try {
                    hashMap.put(nextName(), jsonDeserializer.deserialize(this, iLogger));
                } catch (Exception e) {
                    iLogger.log(SentryLevel.WARNING, "Failed to deserialize object in map.", e);
                }
                if (peek() != JsonToken.BEGIN_OBJECT && peek() != JsonToken.NAME) {
                    break;
                }
            }
        }
        endObject();
        return hashMap;
    }

    public <T> T nextOrNull(ILogger iLogger, JsonDeserializer<T> jsonDeserializer) throws Exception {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return jsonDeserializer.deserialize(this, iLogger);
    }

    public Date nextDateOrNull(ILogger iLogger) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        return dateOrNull(nextString(), iLogger);
    }

    public static Date dateOrNull(String str, ILogger iLogger) {
        if (str == null) {
            return null;
        }
        try {
            try {
                return DateUtils.getDateTime(str);
            } catch (Exception unused) {
                return DateUtils.getDateTimeWithMillisPrecision(str);
            }
        } catch (Exception e) {
            iLogger.log(SentryLevel.ERROR, "Error when deserializing millis timestamp format.", e);
            return null;
        }
    }

    public TimeZone nextTimeZoneOrNull(ILogger iLogger) throws IOException {
        if (peek() == JsonToken.NULL) {
            nextNull();
            return null;
        }
        try {
            return TimeZone.getTimeZone(nextString());
        } catch (Exception e) {
            iLogger.log(SentryLevel.ERROR, "Error when deserializing TimeZone", e);
            return null;
        }
    }

    public Object nextObjectOrNull() throws IOException {
        return new JsonObjectDeserializer().deserialize(this);
    }
}
