package com.henninghall.date_picker;

import android.view.View;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.props.DateProp;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class Emitter {
    private static RCTEventEmitter eventEmitter() {
        return (RCTEventEmitter) DatePickerPackage.context.getJSModule(RCTEventEmitter.class);
    }

    private static DeviceEventManagerModule.RCTDeviceEventEmitter deviceEventEmitter() {
        return (DeviceEventManagerModule.RCTDeviceEventEmitter) DatePickerPackage.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    public static void onDateChange(Calendar calendar, String str, String str2, View view) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(DateProp.name, Utils.dateToIso(calendar));
        createMap.putString("dateString", str);
        createMap.putString("id", str2);
        eventEmitter().receiveEvent(view.getId(), "dateChange", createMap);
    }

    public static void onConfirm(String str, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(DateProp.name, str);
        createMap.putString("id", str2);
        deviceEventEmitter().emit("onConfirm", createMap);
    }

    public static void onCancel(String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("id", str);
        deviceEventEmitter().emit("onCancel", createMap);
    }
}
