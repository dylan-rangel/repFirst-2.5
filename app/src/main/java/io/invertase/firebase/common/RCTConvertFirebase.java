package io.invertase.firebase.common;

import android.content.Context;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public class RCTConvertFirebase {
    private static String TAG = "RCTConvertFirebase";

    public static Map<String, Object> firebaseAppToMap(FirebaseApp firebaseApp) {
        String name = firebaseApp.getName();
        FirebaseOptions options = firebaseApp.getOptions();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        hashMap3.put("name", name);
        hashMap3.put("automaticDataCollectionEnabled", Boolean.valueOf(firebaseApp.isDataCollectionDefaultEnabled()));
        hashMap2.put("apiKey", options.getApiKey());
        hashMap2.put("appId", options.getApplicationId());
        hashMap2.put("projectId", options.getProjectId());
        hashMap2.put("databaseURL", options.getDatabaseUrl());
        hashMap2.put("gaTrackingId", options.getGaTrackingId());
        hashMap2.put("messagingSenderId", options.getGcmSenderId());
        hashMap2.put("storageBucket", options.getStorageBucket());
        hashMap.put("options", hashMap2);
        hashMap.put("appConfig", hashMap3);
        return hashMap;
    }

    public static WritableMap firebaseAppToWritableMap(FirebaseApp firebaseApp) {
        return Arguments.makeNativeMap(firebaseAppToMap(firebaseApp));
    }

    public static FirebaseApp readableMapToFirebaseApp(ReadableMap readableMap, ReadableMap readableMap2, Context context) {
        FirebaseApp initializeApp;
        FirebaseOptions.Builder builder = new FirebaseOptions.Builder();
        String string = readableMap2.getString("name");
        builder.setApiKey(readableMap.getString("apiKey"));
        builder.setApplicationId(readableMap.getString("appId"));
        builder.setProjectId(readableMap.getString("projectId"));
        builder.setDatabaseUrl(readableMap.getString("databaseURL"));
        if (readableMap.hasKey("gaTrackingId")) {
            builder.setGaTrackingId(readableMap.getString("gaTrackingId"));
        }
        builder.setStorageBucket(readableMap.getString("storageBucket"));
        builder.setGcmSenderId(readableMap.getString("messagingSenderId"));
        if (string.equals(FirebaseApp.DEFAULT_APP_NAME)) {
            initializeApp = FirebaseApp.initializeApp(context, builder.build());
        } else {
            initializeApp = FirebaseApp.initializeApp(context, builder.build(), string);
        }
        if (readableMap2.hasKey("automaticDataCollectionEnabled")) {
            initializeApp.setDataCollectionDefaultEnabled(readableMap2.getBoolean("automaticDataCollectionEnabled"));
        }
        if (readableMap2.hasKey("automaticResourceManagement")) {
            initializeApp.setAutomaticResourceManagementEnabled(readableMap2.getBoolean("automaticResourceManagement"));
        }
        return initializeApp;
    }

    public static WritableMap mapPutValue(String str, @Nullable Object obj, WritableMap writableMap) {
        String name;
        if (obj == null) {
            writableMap.putNull(str);
            return writableMap;
        }
        name = obj.getClass().getName();
        name.hashCode();
        switch (name) {
            case "java.lang.Integer":
                writableMap.putInt(str, ((Integer) obj).intValue());
                return writableMap;
            case "java.lang.Float":
                writableMap.putDouble(str, ((Float) obj).floatValue());
                return writableMap;
            case "java.lang.Boolean":
                writableMap.putBoolean(str, ((Boolean) obj).booleanValue());
                return writableMap;
            case "java.lang.Long":
                writableMap.putDouble(str, ((Long) obj).longValue());
                return writableMap;
            case "java.lang.Double":
                writableMap.putDouble(str, ((Double) obj).doubleValue());
                return writableMap;
            case "java.lang.String":
                writableMap.putString(str, (String) obj);
                return writableMap;
            case "org.json.JSONObject$1":
                writableMap.putString(str, obj.toString());
                return writableMap;
            default:
                if (List.class.isAssignableFrom(obj.getClass())) {
                    writableMap.putArray(str, Arguments.makeNativeArray((List) obj));
                } else if (Map.class.isAssignableFrom(obj.getClass())) {
                    WritableMap createMap = Arguments.createMap();
                    for (Map.Entry entry : ((Map) obj).entrySet()) {
                        mapPutValue((String) entry.getKey(), entry.getValue(), createMap);
                    }
                    writableMap.putMap(str, createMap);
                } else {
                    Log.d(TAG, "utils:mapPutValue:unknownType:" + name);
                    writableMap.putNull(str);
                }
                return writableMap;
        }
    }

    public static WritableMap readableMapToWritableMap(ReadableMap readableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.merge(readableMap);
        return createMap;
    }

    public static Map<String, Object> toHashMap(ReadableMap readableMap) {
        return readableMap.toHashMap();
    }

    public static List<Object> toArrayList(ReadableArray readableArray) {
        return readableArray.toArrayList();
    }
}
