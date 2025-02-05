package com.swmansion.reanimated;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class Scheduler {
    private final ReactApplicationContext mContext;
    private final AtomicBoolean mActive = new AtomicBoolean(true);
    private final Runnable mUIThreadRunnable = new Runnable() { // from class: com.swmansion.reanimated.Scheduler.1
        @Override // java.lang.Runnable
        public void run() {
            if (Scheduler.this.mActive.get()) {
                Scheduler.this.triggerUI();
            }
        }
    };
    private final HybridData mHybridData = initHybrid();

    private native HybridData initHybrid();

    public native void triggerUI();

    public Scheduler(ReactApplicationContext reactApplicationContext) {
        this.mContext = reactApplicationContext;
    }

    private void scheduleOnUI() {
        UiThreadUtil.runOnUiThread(new GuardedRunnable(this.mContext.getExceptionHandler()) { // from class: com.swmansion.reanimated.Scheduler.2
            @Override // com.facebook.react.bridge.GuardedRunnable
            public void runGuarded() {
                Scheduler.this.mUIThreadRunnable.run();
            }
        });
    }

    public void deactivate() {
        this.mActive.set(false);
    }
}
