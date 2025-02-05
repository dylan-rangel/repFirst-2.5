package com.facebook.react.fabric.events;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.fabric.FabricSoLoader;

/* loaded from: classes.dex */
public class EventEmitterWrapper {
    private final HybridData mHybridData = initHybrid();

    private static native HybridData initHybrid();

    private native void invokeEvent(String str, NativeMap nativeMap, int i);

    private native void invokeUniqueEvent(String str, NativeMap nativeMap, int i);

    static {
        FabricSoLoader.staticInit();
    }

    private EventEmitterWrapper() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized void invoke(String str, WritableMap writableMap, int i) {
        if (isValid()) {
            invokeEvent(str, writableMap == 0 ? new WritableNativeMap() : (NativeMap) writableMap, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized void invokeUnique(String str, WritableMap writableMap, int i) {
        if (isValid()) {
            invokeUniqueEvent(str, writableMap == 0 ? new WritableNativeMap() : (NativeMap) writableMap, i);
        }
    }

    public synchronized void destroy() {
        HybridData hybridData = this.mHybridData;
        if (hybridData != null) {
            hybridData.resetNative();
        }
    }

    private boolean isValid() {
        HybridData hybridData = this.mHybridData;
        if (hybridData != null) {
            return hybridData.isValid();
        }
        return false;
    }
}
