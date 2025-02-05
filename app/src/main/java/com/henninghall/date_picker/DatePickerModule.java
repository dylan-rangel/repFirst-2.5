package com.henninghall.date_picker;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/* loaded from: classes2.dex */
public class DatePickerModule extends ReactContextBaseJavaModule {
    private final DatePickerModuleImpl module;

    @ReactMethod
    public void addListener(String str) {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return DatePickerModuleImpl.NAME;
    }

    @ReactMethod
    public void removeListeners(Integer num) {
    }

    DatePickerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.module = new DatePickerModuleImpl(reactApplicationContext);
    }

    @ReactMethod
    public void openPicker(ReadableMap readableMap) {
        this.module.openPicker(readableMap);
    }
}
