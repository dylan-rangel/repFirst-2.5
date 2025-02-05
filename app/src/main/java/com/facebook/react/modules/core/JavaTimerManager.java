package com.facebook.react.modules.core;

import android.util.SparseArray;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.common.SystemClock;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.ReactChoreographer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes.dex */
public class JavaTimerManager {
    private static final float FRAME_DURATION_MS = 16.666666f;
    private static final float IDLE_CALLBACK_FRAME_DEADLINE_MS = 1.0f;
    private IdleCallbackRunnable mCurrentIdleCallbackRunnable;
    private final DevSupportManager mDevSupportManager;
    private final IdleFrameCallback mIdleFrameCallback;
    private final JavaScriptTimerExecutor mJavaScriptTimerExecutor;
    private final ReactApplicationContext mReactApplicationContext;
    private final ReactChoreographer mReactChoreographer;
    private final TimerFrameCallback mTimerFrameCallback;
    private final Object mTimerGuard = new Object();
    private final Object mIdleCallbackGuard = new Object();
    private final AtomicBoolean isPaused = new AtomicBoolean(true);
    private final AtomicBoolean isRunningTasks = new AtomicBoolean(false);
    private boolean mFrameCallbackPosted = false;
    private boolean mFrameIdleCallbackPosted = false;
    private boolean mSendIdleEvents = false;
    private final PriorityQueue<Timer> mTimers = new PriorityQueue<>(11, new Comparator<Timer>() { // from class: com.facebook.react.modules.core.JavaTimerManager.1
        @Override // java.util.Comparator
        public int compare(Timer timer, Timer timer2) {
            long j = timer.mTargetTime - timer2.mTargetTime;
            if (j == 0) {
                return 0;
            }
            return j < 0 ? -1 : 1;
        }
    });
    private final SparseArray<Timer> mTimerIdsToTimers = new SparseArray<>();

    private static class Timer {
        private final int mCallbackID;
        private final int mInterval;
        private final boolean mRepeat;
        private long mTargetTime;

        private Timer(int i, long j, int i2, boolean z) {
            this.mCallbackID = i;
            this.mTargetTime = j;
            this.mInterval = i2;
            this.mRepeat = z;
        }
    }

    private class TimerFrameCallback extends ChoreographerCompat.FrameCallback {
        private WritableArray mTimersToCall;

        private TimerFrameCallback() {
            this.mTimersToCall = null;
        }

        @Override // com.facebook.react.modules.core.ChoreographerCompat.FrameCallback
        public void doFrame(long j) {
            if (!JavaTimerManager.this.isPaused.get() || JavaTimerManager.this.isRunningTasks.get()) {
                long j2 = j / 1000000;
                synchronized (JavaTimerManager.this.mTimerGuard) {
                    while (!JavaTimerManager.this.mTimers.isEmpty() && ((Timer) JavaTimerManager.this.mTimers.peek()).mTargetTime < j2) {
                        Timer timer = (Timer) JavaTimerManager.this.mTimers.poll();
                        if (this.mTimersToCall == null) {
                            this.mTimersToCall = Arguments.createArray();
                        }
                        this.mTimersToCall.pushInt(timer.mCallbackID);
                        if (timer.mRepeat) {
                            timer.mTargetTime = timer.mInterval + j2;
                            JavaTimerManager.this.mTimers.add(timer);
                        } else {
                            JavaTimerManager.this.mTimerIdsToTimers.remove(timer.mCallbackID);
                        }
                    }
                }
                if (this.mTimersToCall != null) {
                    JavaTimerManager.this.mJavaScriptTimerExecutor.callTimers(this.mTimersToCall);
                    this.mTimersToCall = null;
                }
                JavaTimerManager.this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this);
            }
        }
    }

    private class IdleFrameCallback extends ChoreographerCompat.FrameCallback {
        private IdleFrameCallback() {
        }

        @Override // com.facebook.react.modules.core.ChoreographerCompat.FrameCallback
        public void doFrame(long j) {
            if (!JavaTimerManager.this.isPaused.get() || JavaTimerManager.this.isRunningTasks.get()) {
                if (JavaTimerManager.this.mCurrentIdleCallbackRunnable != null) {
                    JavaTimerManager.this.mCurrentIdleCallbackRunnable.cancel();
                }
                JavaTimerManager javaTimerManager = JavaTimerManager.this;
                javaTimerManager.mCurrentIdleCallbackRunnable = javaTimerManager.new IdleCallbackRunnable(j);
                JavaTimerManager.this.mReactApplicationContext.runOnJSQueueThread(JavaTimerManager.this.mCurrentIdleCallbackRunnable);
                JavaTimerManager.this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this);
            }
        }
    }

    private class IdleCallbackRunnable implements Runnable {
        private volatile boolean mCancelled = false;
        private final long mFrameStartTime;

        public IdleCallbackRunnable(long j) {
            this.mFrameStartTime = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            if (this.mCancelled) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis() - (this.mFrameStartTime / 1000000);
            long currentTimeMillis = SystemClock.currentTimeMillis() - uptimeMillis;
            if (JavaTimerManager.FRAME_DURATION_MS - uptimeMillis < 1.0f) {
                return;
            }
            synchronized (JavaTimerManager.this.mIdleCallbackGuard) {
                z = JavaTimerManager.this.mSendIdleEvents;
            }
            if (z) {
                JavaTimerManager.this.mJavaScriptTimerExecutor.callIdleCallbacks(currentTimeMillis);
            }
            JavaTimerManager.this.mCurrentIdleCallbackRunnable = null;
        }

        public void cancel() {
            this.mCancelled = true;
        }
    }

    public JavaTimerManager(ReactApplicationContext reactApplicationContext, JavaScriptTimerExecutor javaScriptTimerExecutor, ReactChoreographer reactChoreographer, DevSupportManager devSupportManager) {
        this.mTimerFrameCallback = new TimerFrameCallback();
        this.mIdleFrameCallback = new IdleFrameCallback();
        this.mReactApplicationContext = reactApplicationContext;
        this.mJavaScriptTimerExecutor = javaScriptTimerExecutor;
        this.mReactChoreographer = reactChoreographer;
        this.mDevSupportManager = devSupportManager;
    }

    public void onHostPause() {
        this.isPaused.set(true);
        clearFrameCallback();
        maybeIdleCallback();
    }

    public void onHostDestroy() {
        clearFrameCallback();
        maybeIdleCallback();
    }

    public void onHostResume() {
        this.isPaused.set(false);
        setChoreographerCallback();
        maybeSetChoreographerIdleCallback();
    }

    public void onHeadlessJsTaskStart(int i) {
        if (this.isRunningTasks.getAndSet(true)) {
            return;
        }
        setChoreographerCallback();
        maybeSetChoreographerIdleCallback();
    }

    public void onHeadlessJsTaskFinish(int i) {
        if (HeadlessJsTaskContext.getInstance(this.mReactApplicationContext).hasActiveTasks()) {
            return;
        }
        this.isRunningTasks.set(false);
        clearFrameCallback();
        maybeIdleCallback();
    }

    public void onInstanceDestroy() {
        clearFrameCallback();
        clearChoreographerIdleCallback();
    }

    private void maybeSetChoreographerIdleCallback() {
        synchronized (this.mIdleCallbackGuard) {
            if (this.mSendIdleEvents) {
                setChoreographerIdleCallback();
            }
        }
    }

    private void maybeIdleCallback() {
        if (!this.isPaused.get() || this.isRunningTasks.get()) {
            return;
        }
        clearFrameCallback();
    }

    private void setChoreographerCallback() {
        if (this.mFrameCallbackPosted) {
            return;
        }
        this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
        this.mFrameCallbackPosted = true;
    }

    private void clearFrameCallback() {
        HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(this.mReactApplicationContext);
        if (this.mFrameCallbackPosted && this.isPaused.get() && !headlessJsTaskContext.hasActiveTasks()) {
            this.mReactChoreographer.removeFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
            this.mFrameCallbackPosted = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setChoreographerIdleCallback() {
        if (this.mFrameIdleCallbackPosted) {
            return;
        }
        this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
        this.mFrameIdleCallbackPosted = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearChoreographerIdleCallback() {
        if (this.mFrameIdleCallbackPosted) {
            this.mReactChoreographer.removeFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
            this.mFrameIdleCallbackPosted = false;
        }
    }

    public void createTimer(int i, long j, boolean z) {
        Timer timer = new Timer(i, (SystemClock.nanoTime() / 1000000) + j, (int) j, z);
        synchronized (this.mTimerGuard) {
            this.mTimers.add(timer);
            this.mTimerIdsToTimers.put(i, timer);
        }
    }

    public void createAndMaybeCallTimer(int i, int i2, double d, boolean z) {
        long currentTimeMillis = SystemClock.currentTimeMillis();
        long j = (long) d;
        if (this.mDevSupportManager.getDevSupportEnabled() && Math.abs(j - currentTimeMillis) > DateUtils.MILLIS_PER_MINUTE) {
            this.mJavaScriptTimerExecutor.emitTimeDriftWarning("Debugger and device times have drifted by more than 60s. Please correct this by running adb shell \"date `date +%m%d%H%M%Y.%S`\" on your debugger machine.");
        }
        long max = Math.max(0L, (j - currentTimeMillis) + i2);
        if (i2 == 0 && !z) {
            WritableArray createArray = Arguments.createArray();
            createArray.pushInt(i);
            this.mJavaScriptTimerExecutor.callTimers(createArray);
            return;
        }
        createTimer(i, max, z);
    }

    public void deleteTimer(int i) {
        synchronized (this.mTimerGuard) {
            Timer timer = this.mTimerIdsToTimers.get(i);
            if (timer == null) {
                return;
            }
            this.mTimerIdsToTimers.remove(i);
            this.mTimers.remove(timer);
        }
    }

    public void setSendIdleEvents(final boolean z) {
        synchronized (this.mIdleCallbackGuard) {
            this.mSendIdleEvents = z;
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.core.JavaTimerManager.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (JavaTimerManager.this.mIdleCallbackGuard) {
                    if (z) {
                        JavaTimerManager.this.setChoreographerIdleCallback();
                    } else {
                        JavaTimerManager.this.clearChoreographerIdleCallback();
                    }
                }
            }
        });
    }

    boolean hasActiveTimersInRange(long j) {
        synchronized (this.mTimerGuard) {
            Timer peek = this.mTimers.peek();
            if (peek == null) {
                return false;
            }
            if (isTimerInRange(peek, j)) {
                return true;
            }
            Iterator<Timer> it = this.mTimers.iterator();
            while (it.hasNext()) {
                if (isTimerInRange(it.next(), j)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static boolean isTimerInRange(Timer timer, long j) {
        return !timer.mRepeat && ((long) timer.mInterval) < j;
    }
}
