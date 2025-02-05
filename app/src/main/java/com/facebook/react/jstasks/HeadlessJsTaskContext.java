package com.facebook.react.jstasks;

import android.os.Handler;
import android.util.SparseArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactSoftExceptionLogger;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.appregistry.AppRegistry;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class HeadlessJsTaskContext {
    private static final WeakHashMap<ReactContext, HeadlessJsTaskContext> INSTANCES = new WeakHashMap<>();
    private final WeakReference<ReactContext> mReactContext;
    private final Set<HeadlessJsTaskEventListener> mHeadlessJsTaskEventListeners = new CopyOnWriteArraySet();
    private final AtomicInteger mLastTaskId = new AtomicInteger(0);
    private final Handler mHandler = new Handler();
    private final Set<Integer> mActiveTasks = new CopyOnWriteArraySet();
    private final Map<Integer, HeadlessJsTaskConfig> mActiveTaskConfigs = new ConcurrentHashMap();
    private final SparseArray<Runnable> mTaskTimeouts = new SparseArray<>();

    public static HeadlessJsTaskContext getInstance(ReactContext reactContext) {
        WeakHashMap<ReactContext, HeadlessJsTaskContext> weakHashMap = INSTANCES;
        HeadlessJsTaskContext headlessJsTaskContext = weakHashMap.get(reactContext);
        if (headlessJsTaskContext != null) {
            return headlessJsTaskContext;
        }
        HeadlessJsTaskContext headlessJsTaskContext2 = new HeadlessJsTaskContext(reactContext);
        weakHashMap.put(reactContext, headlessJsTaskContext2);
        return headlessJsTaskContext2;
    }

    private HeadlessJsTaskContext(ReactContext reactContext) {
        this.mReactContext = new WeakReference<>(reactContext);
    }

    public synchronized void addTaskEventListener(HeadlessJsTaskEventListener headlessJsTaskEventListener) {
        this.mHeadlessJsTaskEventListeners.add(headlessJsTaskEventListener);
        Iterator<Integer> it = this.mActiveTasks.iterator();
        while (it.hasNext()) {
            headlessJsTaskEventListener.onHeadlessJsTaskStart(it.next().intValue());
        }
    }

    public void removeTaskEventListener(HeadlessJsTaskEventListener headlessJsTaskEventListener) {
        this.mHeadlessJsTaskEventListeners.remove(headlessJsTaskEventListener);
    }

    public boolean hasActiveTasks() {
        return this.mActiveTasks.size() > 0;
    }

    public synchronized int startTask(HeadlessJsTaskConfig headlessJsTaskConfig) {
        int incrementAndGet;
        incrementAndGet = this.mLastTaskId.incrementAndGet();
        startTask(headlessJsTaskConfig, incrementAndGet);
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void startTask(HeadlessJsTaskConfig headlessJsTaskConfig, int i) {
        UiThreadUtil.assertOnUiThread();
        ReactContext reactContext = (ReactContext) Assertions.assertNotNull(this.mReactContext.get(), "Tried to start a task on a react context that has already been destroyed");
        if (reactContext.getLifecycleState() == LifecycleState.RESUMED && !headlessJsTaskConfig.isAllowedInForeground()) {
            throw new IllegalStateException("Tried to start task " + headlessJsTaskConfig.getTaskKey() + " while in foreground, but this is not allowed.");
        }
        this.mActiveTasks.add(Integer.valueOf(i));
        this.mActiveTaskConfigs.put(Integer.valueOf(i), new HeadlessJsTaskConfig(headlessJsTaskConfig));
        if (reactContext.hasActiveReactInstance()) {
            ((AppRegistry) reactContext.getJSModule(AppRegistry.class)).startHeadlessTask(i, headlessJsTaskConfig.getTaskKey(), headlessJsTaskConfig.getData());
        } else {
            ReactSoftExceptionLogger.logSoftException("HeadlessJsTaskContext", new RuntimeException("Cannot start headless task, CatalystInstance not available"));
        }
        if (headlessJsTaskConfig.getTimeout() > 0) {
            scheduleTaskTimeout(i, headlessJsTaskConfig.getTimeout());
        }
        Iterator<HeadlessJsTaskEventListener> it = this.mHeadlessJsTaskEventListeners.iterator();
        while (it.hasNext()) {
            it.next().onHeadlessJsTaskStart(i);
        }
    }

    public synchronized boolean retryTask(final int i) {
        HeadlessJsTaskConfig headlessJsTaskConfig = this.mActiveTaskConfigs.get(Integer.valueOf(i));
        Assertions.assertCondition(headlessJsTaskConfig != null, "Tried to retrieve non-existent task config with id " + i + ".");
        HeadlessJsTaskRetryPolicy retryPolicy = headlessJsTaskConfig.getRetryPolicy();
        if (!retryPolicy.canRetry()) {
            return false;
        }
        removeTimeout(i);
        final HeadlessJsTaskConfig headlessJsTaskConfig2 = new HeadlessJsTaskConfig(headlessJsTaskConfig.getTaskKey(), headlessJsTaskConfig.getData(), headlessJsTaskConfig.getTimeout(), headlessJsTaskConfig.isAllowedInForeground(), retryPolicy.update());
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.jstasks.HeadlessJsTaskContext.1
            @Override // java.lang.Runnable
            public void run() {
                HeadlessJsTaskContext.this.startTask(headlessJsTaskConfig2, i);
            }
        }, retryPolicy.getDelay());
        return true;
    }

    public synchronized void finishTask(final int i) {
        Assertions.assertCondition(this.mActiveTasks.remove(Integer.valueOf(i)), "Tried to finish non-existent task with id " + i + ".");
        Assertions.assertCondition(this.mActiveTaskConfigs.remove(Integer.valueOf(i)) != null, "Tried to remove non-existent task config with id " + i + ".");
        removeTimeout(i);
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.jstasks.HeadlessJsTaskContext.2
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = HeadlessJsTaskContext.this.mHeadlessJsTaskEventListeners.iterator();
                while (it.hasNext()) {
                    ((HeadlessJsTaskEventListener) it.next()).onHeadlessJsTaskFinish(i);
                }
            }
        });
    }

    private void removeTimeout(int i) {
        Runnable runnable = this.mTaskTimeouts.get(i);
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mTaskTimeouts.remove(i);
        }
    }

    public synchronized boolean isTaskRunning(int i) {
        return this.mActiveTasks.contains(Integer.valueOf(i));
    }

    private void scheduleTaskTimeout(final int i, long j) {
        Runnable runnable = new Runnable() { // from class: com.facebook.react.jstasks.HeadlessJsTaskContext.3
            @Override // java.lang.Runnable
            public void run() {
                HeadlessJsTaskContext.this.finishTask(i);
            }
        };
        this.mTaskTimeouts.append(i, runnable);
        this.mHandler.postDelayed(runnable, j);
    }
}
