package com.swmansion.reanimated.layoutReanimation;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.ReactApplicationContext;
import com.swmansion.reanimated.ReanimatedModule;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public class LayoutAnimations {
    private WeakReference<ReactApplicationContext> mContext;
    private final HybridData mHybridData = initHybrid();

    private native HybridData initHybrid();

    public native boolean isLayoutAnimationEnabled();

    public native void removeConfigForTag(int i);

    public native void startAnimationForTag(int i, String str, Map<String, String> map);

    static {
        System.loadLibrary("reanimated");
    }

    public LayoutAnimations(ReactApplicationContext reactApplicationContext) {
        this.mContext = new WeakReference<>(reactApplicationContext);
    }

    private void notifyAboutEnd(int i, int i2) {
        ReactApplicationContext reactApplicationContext = this.mContext.get();
        if (reactApplicationContext != null) {
            ((ReanimatedModule) reactApplicationContext.getNativeModule(ReanimatedModule.class)).getNodesManager().getAnimationsManager().notifyAboutEnd(i, i2 != 0);
        }
    }

    private void notifyAboutProgress(Map<String, Object> map, int i) {
        ReactApplicationContext reactApplicationContext = this.mContext.get();
        if (reactApplicationContext != null) {
            ((ReanimatedModule) reactApplicationContext.getNativeModule(ReanimatedModule.class)).getNodesManager().getAnimationsManager().notifyAboutProgress(map, Integer.valueOf(i));
        }
    }
}
