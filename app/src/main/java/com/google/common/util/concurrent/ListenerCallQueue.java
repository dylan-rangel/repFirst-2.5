package com.google.common.util.concurrent;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
final class ListenerCallQueue<L> {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    interface Event<L> {
        void call(L l);
    }

    ListenerCallQueue() {
    }

    public void addListener(L l, Executor executor) {
        Preconditions.checkNotNull(l, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue<>(l, executor));
    }

    public void enqueue(Event<L> event) {
        enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String str) {
        enqueueHelper(event, str);
    }

    private void enqueueHelper(Event<L> event, Object obj) {
        Preconditions.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        Preconditions.checkNotNull(obj, Constants.ScionAnalytics.PARAM_LABEL);
        synchronized (this.listeners) {
            Iterator<PerListenerQueue<L>> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().add(event, obj);
            }
        }
    }

    public void dispatch() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).dispatch();
        }
    }

    private static final class PerListenerQueue<L> implements Runnable {
        final Executor executor;
        boolean isThreadScheduled;
        final L listener;
        final Queue<Event<L>> waitQueue = Queues.newArrayDeque();
        final Queue<Object> labelQueue = Queues.newArrayDeque();

        PerListenerQueue(L l, Executor executor) {
            this.listener = (L) Preconditions.checkNotNull(l);
            this.executor = (Executor) Preconditions.checkNotNull(executor);
        }

        synchronized void add(Event<L> event, Object obj) {
            this.waitQueue.add(event);
            this.labelQueue.add(obj);
        }

        void dispatch() {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.isThreadScheduled) {
                    z = false;
                } else {
                    this.isThreadScheduled = true;
                }
            }
            if (z) {
                try {
                    this.executor.execute(this);
                } catch (RuntimeException e) {
                    synchronized (this) {
                        this.isThreadScheduled = false;
                        Logger logger = ListenerCallQueue.logger;
                        Level level = Level.SEVERE;
                        String valueOf = String.valueOf(this.listener);
                        String valueOf2 = String.valueOf(this.executor);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42 + String.valueOf(valueOf2).length());
                        sb.append("Exception while running callbacks for ");
                        sb.append(valueOf);
                        sb.append(" on ");
                        sb.append(valueOf2);
                        logger.log(level, sb.toString(), (Throwable) e);
                        throw e;
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0020, code lost:
        
            r2.call(r10.listener);
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
        
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
        
            r4 = com.google.common.util.concurrent.ListenerCallQueue.logger;
            r5 = java.util.logging.Level.SEVERE;
            r6 = java.lang.String.valueOf(r10.listener);
            r3 = java.lang.String.valueOf(r3);
            r8 = new java.lang.StringBuilder((java.lang.String.valueOf(r6).length() + 37) + java.lang.String.valueOf(r3).length());
            r8.append("Exception while executing callback: ");
            r8.append(r6);
            r8.append(org.apache.commons.lang3.StringUtils.SPACE);
            r8.append(r3);
            r4.log(r5, r8.toString(), (java.lang.Throwable) r2);
         */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0076  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r10 = this;
            L0:
                r0 = 0
                r1 = 1
                monitor-enter(r10)     // Catch: java.lang.Throwable -> L73
                boolean r2 = r10.isThreadScheduled     // Catch: java.lang.Throwable -> L67
                com.google.common.base.Preconditions.checkState(r2)     // Catch: java.lang.Throwable -> L67
                java.util.Queue<com.google.common.util.concurrent.ListenerCallQueue$Event<L>> r2 = r10.waitQueue     // Catch: java.lang.Throwable -> L67
                java.lang.Object r2 = r2.poll()     // Catch: java.lang.Throwable -> L67
                com.google.common.util.concurrent.ListenerCallQueue$Event r2 = (com.google.common.util.concurrent.ListenerCallQueue.Event) r2     // Catch: java.lang.Throwable -> L67
                java.util.Queue<java.lang.Object> r3 = r10.labelQueue     // Catch: java.lang.Throwable -> L67
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L67
                if (r2 != 0) goto L1f
                r10.isThreadScheduled = r0     // Catch: java.lang.Throwable -> L67
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L1c
                return
            L1c:
                r1 = move-exception
                r2 = 0
                goto L6a
            L1f:
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L67
                L r4 = r10.listener     // Catch: java.lang.RuntimeException -> L26 java.lang.Throwable -> L73
                r2.call(r4)     // Catch: java.lang.RuntimeException -> L26 java.lang.Throwable -> L73
                goto L0
            L26:
                r2 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.ListenerCallQueue.access$000()     // Catch: java.lang.Throwable -> L73
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L73
                L r6 = r10.listener     // Catch: java.lang.Throwable -> L73
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L73
                java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch: java.lang.Throwable -> L73
                java.lang.String r7 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L73
                int r7 = r7.length()     // Catch: java.lang.Throwable -> L73
                int r7 = r7 + 37
                java.lang.String r8 = java.lang.String.valueOf(r3)     // Catch: java.lang.Throwable -> L73
                int r8 = r8.length()     // Catch: java.lang.Throwable -> L73
                int r7 = r7 + r8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73
                r8.<init>(r7)     // Catch: java.lang.Throwable -> L73
                java.lang.String r7 = "Exception while executing callback: "
                r8.append(r7)     // Catch: java.lang.Throwable -> L73
                r8.append(r6)     // Catch: java.lang.Throwable -> L73
                java.lang.String r6 = " "
                r8.append(r6)     // Catch: java.lang.Throwable -> L73
                r8.append(r3)     // Catch: java.lang.Throwable -> L73
                java.lang.String r3 = r8.toString()     // Catch: java.lang.Throwable -> L73
                r4.log(r5, r3, r2)     // Catch: java.lang.Throwable -> L73
                goto L0
            L67:
                r2 = move-exception
                r1 = r2
                r2 = 1
            L6a:
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L71
                throw r1     // Catch: java.lang.Throwable -> L6c
            L6c:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L74
            L71:
                r1 = move-exception
                goto L6a
            L73:
                r2 = move-exception
            L74:
                if (r1 == 0) goto L7e
                monitor-enter(r10)
                r10.isThreadScheduled = r0     // Catch: java.lang.Throwable -> L7b
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L7b
                goto L7e
            L7b:
                r0 = move-exception
                monitor-exit(r10)     // Catch: java.lang.Throwable -> L7b
                throw r0
            L7e:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.PerListenerQueue.run():void");
        }
    }
}
