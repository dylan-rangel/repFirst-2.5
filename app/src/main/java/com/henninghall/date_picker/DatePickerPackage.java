package com.henninghall.date_picker;

import com.facebook.react.ReactPackage;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class DatePickerPackage extends TurboReactPackage implements ReactPackage {
    public static ReactApplicationContext context;

    @Override // com.facebook.react.TurboReactPackage
    public NativeModule getModule(String str, ReactApplicationContext reactApplicationContext) {
        if (!str.equals(DatePickerModuleImpl.NAME)) {
            return null;
        }
        context = reactApplicationContext;
        return new DatePickerModule(reactApplicationContext);
    }

    @Override // com.facebook.react.TurboReactPackage
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return new ReactModuleInfoProvider() { // from class: com.henninghall.date_picker.DatePickerPackage.1
            final boolean isTurboModule = false;

            @Override // com.facebook.react.module.model.ReactModuleInfoProvider
            public Map<String, ReactModuleInfo> getReactModuleInfos() {
                HashMap hashMap = new HashMap();
                hashMap.put(DatePickerModuleImpl.NAME, new ReactModuleInfo(DatePickerModuleImpl.NAME, DatePickerModuleImpl.NAME, false, false, true, false, false));
                return hashMap;
            }
        };
    }

    @Override // com.facebook.react.TurboReactPackage, com.facebook.react.ReactPackage
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        context = reactApplicationContext;
        return Arrays.asList(new DatePickerManager());
    }

    @Override // com.facebook.react.TurboReactPackage, com.facebook.react.ReactPackage
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new DatePickerModule(reactApplicationContext));
        return arrayList;
    }
}
