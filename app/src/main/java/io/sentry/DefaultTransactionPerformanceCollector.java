package io.sentry;

import io.sentry.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public final class DefaultTransactionPerformanceCollector implements TransactionPerformanceCollector {
    private static final long TRANSACTION_COLLECTION_INTERVAL_MILLIS = 100;
    private static final long TRANSACTION_COLLECTION_TIMEOUT_MILLIS = 30000;
    private final List<ICollector> collectors;
    private final SentryOptions options;
    private final Object timerLock = new Object();
    private volatile Timer timer = null;
    private final Map<String, List<PerformanceCollectionData>> performanceDataMap = new ConcurrentHashMap();
    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    public DefaultTransactionPerformanceCollector(SentryOptions sentryOptions) {
        this.options = (SentryOptions) Objects.requireNonNull(sentryOptions, "The options object is required.");
        this.collectors = sentryOptions.getCollectors();
    }

    @Override // io.sentry.TransactionPerformanceCollector
    public void start(final ITransaction iTransaction) {
        if (this.collectors.isEmpty()) {
            this.options.getLogger().log(SentryLevel.INFO, "No collector found. Performance stats will not be captured during transactions.", new Object[0]);
            return;
        }
        if (!this.performanceDataMap.containsKey(iTransaction.getEventId().toString())) {
            this.performanceDataMap.put(iTransaction.getEventId().toString(), new ArrayList());
            try {
                this.options.getExecutorService().schedule(new Runnable() { // from class: io.sentry.DefaultTransactionPerformanceCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DefaultTransactionPerformanceCollector.this.m225lambda$start$0$iosentryDefaultTransactionPerformanceCollector(iTransaction);
                    }
                }, TRANSACTION_COLLECTION_TIMEOUT_MILLIS);
            } catch (RejectedExecutionException e) {
                this.options.getLogger().log(SentryLevel.ERROR, "Failed to call the executor. Performance collector will not be automatically finished. Did you call Sentry.close()?", e);
            }
        }
        if (this.isStarted.getAndSet(true)) {
            return;
        }
        synchronized (this.timerLock) {
            if (this.timer == null) {
                this.timer = new Timer(true);
            }
            this.timer.schedule(new TimerTask() { // from class: io.sentry.DefaultTransactionPerformanceCollector.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Iterator it = DefaultTransactionPerformanceCollector.this.collectors.iterator();
                    while (it.hasNext()) {
                        ((ICollector) it.next()).setup();
                    }
                }
            }, 0L);
            this.timer.scheduleAtFixedRate(new TimerTask() { // from class: io.sentry.DefaultTransactionPerformanceCollector.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    PerformanceCollectionData performanceCollectionData = new PerformanceCollectionData();
                    Iterator it = DefaultTransactionPerformanceCollector.this.collectors.iterator();
                    while (it.hasNext()) {
                        ((ICollector) it.next()).collect(performanceCollectionData);
                    }
                    Iterator it2 = DefaultTransactionPerformanceCollector.this.performanceDataMap.values().iterator();
                    while (it2.hasNext()) {
                        ((List) it2.next()).add(performanceCollectionData);
                    }
                }
            }, TRANSACTION_COLLECTION_INTERVAL_MILLIS, TRANSACTION_COLLECTION_INTERVAL_MILLIS);
        }
    }

    @Override // io.sentry.TransactionPerformanceCollector
    /* renamed from: stop, reason: merged with bridge method [inline-methods] */
    public List<PerformanceCollectionData> m225lambda$start$0$iosentryDefaultTransactionPerformanceCollector(ITransaction iTransaction) {
        List<PerformanceCollectionData> remove = this.performanceDataMap.remove(iTransaction.getEventId().toString());
        this.options.getLogger().log(SentryLevel.DEBUG, "stop collecting performance info for transactions %s (%s)", iTransaction.getName(), iTransaction.getSpanContext().getTraceId().toString());
        if (this.performanceDataMap.isEmpty() && this.isStarted.getAndSet(false)) {
            synchronized (this.timerLock) {
                if (this.timer != null) {
                    this.timer.cancel();
                    this.timer = null;
                }
            }
        }
        return remove;
    }

    @Override // io.sentry.TransactionPerformanceCollector
    public void close() {
        this.performanceDataMap.clear();
        this.options.getLogger().log(SentryLevel.DEBUG, "stop collecting all performance info for transactions", new Object[0]);
        if (this.isStarted.getAndSet(false)) {
            synchronized (this.timerLock) {
                if (this.timer != null) {
                    this.timer.cancel();
                    this.timer = null;
                }
            }
        }
    }
}
