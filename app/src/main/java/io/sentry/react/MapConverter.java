package io.sentry.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.AndroidLogger;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class MapConverter {
    public static final String NAME = "RNSentry.MapConverter";
    private static final ILogger logger = new AndroidLogger(NAME);

    public static Object convertToWritable(Object obj) {
        if (obj instanceof List) {
            WritableArray createArray = Arguments.createArray();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                addValueToWritableArray(createArray, convertToWritable(it.next()));
            }
            return createArray;
        }
        if (obj instanceof Map) {
            WritableMap createMap = Arguments.createMap();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key instanceof String) {
                    addValueToWritableMap(createMap, (String) key, convertToWritable(value));
                } else {
                    logger.log(SentryLevel.ERROR, "Only String keys are supported in Map.", key);
                }
            }
            return createMap;
        }
        if (obj instanceof Byte) {
            return Integer.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Integer.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).floatValue());
        }
        if (obj instanceof Long) {
            return Double.valueOf(((Long) obj).longValue());
        }
        if (obj instanceof BigInteger) {
            return Double.valueOf(((BigInteger) obj).doubleValue());
        }
        if (obj instanceof BigDecimal) {
            return Double.valueOf(((BigDecimal) obj).doubleValue());
        }
        if ((obj instanceof Integer) || (obj instanceof Double) || (obj instanceof Boolean) || obj == null || (obj instanceof String)) {
            return obj;
        }
        logger.log(SentryLevel.ERROR, "Supplied serialized value could not be converted." + obj, new Object[0]);
        return null;
    }

    private static void addValueToWritableArray(WritableArray writableArray, Object obj) {
        if (obj == null) {
            writableArray.pushNull();
            return;
        }
        if (obj instanceof Boolean) {
            writableArray.pushBoolean(((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof Double) {
            writableArray.pushDouble(((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof Float) {
            writableArray.pushDouble(((Float) obj).doubleValue());
            return;
        }
        if (obj instanceof Integer) {
            writableArray.pushInt(((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Short) {
            writableArray.pushInt(((Short) obj).intValue());
            return;
        }
        if (obj instanceof Byte) {
            writableArray.pushInt(((Byte) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            writableArray.pushDouble(((Long) obj).doubleValue());
            return;
        }
        if (obj instanceof BigInteger) {
            writableArray.pushDouble(((BigInteger) obj).doubleValue());
            return;
        }
        if (obj instanceof BigDecimal) {
            writableArray.pushDouble(((BigDecimal) obj).doubleValue());
            return;
        }
        if (obj instanceof String) {
            writableArray.pushString((String) obj);
            return;
        }
        if (obj instanceof ReadableMap) {
            writableArray.pushMap((ReadableMap) obj);
            return;
        }
        if (obj instanceof ReadableArray) {
            writableArray.pushArray((ReadableArray) obj);
            return;
        }
        logger.log(SentryLevel.ERROR, "Could not convert object: " + obj, new Object[0]);
    }

    private static void addValueToWritableMap(WritableMap writableMap, String str, Object obj) {
        if (obj == null) {
            writableMap.putNull(str);
            return;
        }
        if (obj instanceof Boolean) {
            writableMap.putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof Double) {
            writableMap.putDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof Float) {
            writableMap.putDouble(str, ((Float) obj).doubleValue());
            return;
        }
        if (obj instanceof Integer) {
            writableMap.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Short) {
            writableMap.putInt(str, ((Short) obj).intValue());
            return;
        }
        if (obj instanceof Byte) {
            writableMap.putInt(str, ((Byte) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            writableMap.putDouble(str, ((Long) obj).doubleValue());
            return;
        }
        if (obj instanceof BigInteger) {
            writableMap.putDouble(str, ((BigInteger) obj).doubleValue());
            return;
        }
        if (obj instanceof BigDecimal) {
            writableMap.putDouble(str, ((BigDecimal) obj).doubleValue());
            return;
        }
        if (obj instanceof String) {
            writableMap.putString(str, (String) obj);
            return;
        }
        if (obj instanceof ReadableArray) {
            writableMap.putArray(str, (ReadableArray) obj);
            return;
        }
        if (obj instanceof ReadableMap) {
            writableMap.putMap(str, (ReadableMap) obj);
            return;
        }
        logger.log(SentryLevel.ERROR, "Could not convert object" + obj, new Object[0]);
    }
}
