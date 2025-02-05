package com.facebook.react;

import com.facebook.jni.HybridData;
import com.facebook.react.ReactPackageTurboModuleManagerDelegate;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.turbomodule.core.TurboModuleManagerDelegate;
import com.facebook.soloader.SoLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class CompositeReactPackageTurboModuleManagerDelegate extends ReactPackageTurboModuleManagerDelegate {
    private static volatile boolean sIsSoLibraryLoaded;
    private final List<TurboModuleManagerDelegate> mDelegates;

    private native void addTurboModuleManagerDelegate(TurboModuleManagerDelegate turboModuleManagerDelegate);

    @Override // com.facebook.react.turbomodule.core.TurboModuleManagerDelegate
    protected native HybridData initHybrid();

    private CompositeReactPackageTurboModuleManagerDelegate(ReactApplicationContext reactApplicationContext, List<ReactPackage> list, List<TurboModuleManagerDelegate> list2) {
        super(reactApplicationContext, list);
        this.mDelegates = list2;
        Iterator<TurboModuleManagerDelegate> it = list2.iterator();
        while (it.hasNext()) {
            addTurboModuleManagerDelegate(it.next());
        }
    }

    public static class Builder extends ReactPackageTurboModuleManagerDelegate.Builder {
        private final List<ReactPackageTurboModuleManagerDelegate.Builder> mDelegatesBuilder;

        public Builder(List<ReactPackageTurboModuleManagerDelegate.Builder> list) {
            this.mDelegatesBuilder = list;
        }

        @Override // com.facebook.react.ReactPackageTurboModuleManagerDelegate.Builder
        protected ReactPackageTurboModuleManagerDelegate build(ReactApplicationContext reactApplicationContext, List<ReactPackage> list) {
            ArrayList arrayList = new ArrayList();
            Iterator<ReactPackageTurboModuleManagerDelegate.Builder> it = this.mDelegatesBuilder.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().build(reactApplicationContext, Collections.emptyList()));
            }
            return new CompositeReactPackageTurboModuleManagerDelegate(reactApplicationContext, list, arrayList);
        }
    }

    @Override // com.facebook.react.turbomodule.core.TurboModuleManagerDelegate
    protected synchronized void maybeLoadOtherSoLibraries() {
        if (!sIsSoLibraryLoaded) {
            SoLoader.loadLibrary("turbomodulejsijni");
            sIsSoLibraryLoaded = true;
        }
    }
}
