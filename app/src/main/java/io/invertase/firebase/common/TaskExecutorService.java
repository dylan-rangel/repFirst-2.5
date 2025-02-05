package io.invertase.firebase.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class TaskExecutorService {
    private static final String KEEP_ALIVE_SECONDS_KEY = "android_task_executor_keep_alive_seconds";
    private static final String MAXIMUM_POOL_SIZE_KEY = "android_task_executor_maximum_pool_size";
    private static final Map<String, ExecutorService> executors = new HashMap();
    private final RejectedExecutionHandler executeInFallback = new RejectedExecutionHandler() { // from class: io.invertase.firebase.common.TaskExecutorService$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.RejectedExecutionHandler
        public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            TaskExecutorService.this.m223lambda$new$0$ioinvertasefirebasecommonTaskExecutorService(runnable, threadPoolExecutor);
        }
    };
    private final int keepAliveSeconds;
    private final int maximumPoolSize;
    private final String name;

    TaskExecutorService(String str) {
        this.name = str;
        ReactNativeFirebaseJSON sharedInstance = ReactNativeFirebaseJSON.getSharedInstance();
        this.maximumPoolSize = sharedInstance.getIntValue(MAXIMUM_POOL_SIZE_KEY, 1);
        this.keepAliveSeconds = sharedInstance.getIntValue(KEEP_ALIVE_SECONDS_KEY, 3);
    }

    public ExecutorService getExecutor() {
        return getExecutor(this.maximumPoolSize <= 1, "");
    }

    public ExecutorService getTransactionalExecutor() {
        return getExecutor(true, "");
    }

    public ExecutorService getTransactionalExecutor(String str) {
        if (this.maximumPoolSize == 0) {
            str = "";
        }
        return getExecutor(true, str);
    }

    public ExecutorService getExecutor(boolean z, String str) {
        String executorName = getExecutorName(z, str);
        Map<String, ExecutorService> map = executors;
        synchronized (map) {
            ExecutorService executorService = map.get(executorName);
            if (executorService != null) {
                return executorService;
            }
            ExecutorService newExecutor = getNewExecutor(z);
            map.put(executorName, newExecutor);
            return newExecutor;
        }
    }

    private ExecutorService getNewExecutor(boolean z) {
        if (z) {
            return Executors.newSingleThreadExecutor();
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, this.maximumPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS, new SynchronousQueue());
        threadPoolExecutor.setRejectedExecutionHandler(this.executeInFallback);
        return threadPoolExecutor;
    }

    /* renamed from: lambda$new$0$io-invertase-firebase-common-TaskExecutorService, reason: not valid java name */
    /* synthetic */ void m223lambda$new$0$ioinvertasefirebasecommonTaskExecutorService(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminated() || threadPoolExecutor.isTerminating()) {
            return;
        }
        getTransactionalExecutor().execute(runnable);
    }

    public String getExecutorName(boolean z, String str) {
        if (z) {
            return this.name + "TransactionalExecutor" + str;
        }
        return this.name + "Executor" + str;
    }

    public void shutdown() {
        Map<String, ExecutorService> map = executors;
        synchronized (map) {
            for (String str : new ArrayList(map.keySet())) {
                if (!str.startsWith(this.name)) {
                    executors.remove(str);
                } else {
                    removeExecutor(str);
                }
            }
        }
    }

    public void removeExecutor(String str) {
        Map<String, ExecutorService> map = executors;
        synchronized (map) {
            ExecutorService executorService = map.get(str);
            if (executorService != null) {
                executorService.shutdownNow();
                map.remove(str);
            }
        }
    }
}
