package io.sentry.util.thread;

import io.sentry.protocol.SentryThread;
import io.sentry.util.thread.IMainThreadChecker;

/* loaded from: classes3.dex */
public final class NoOpMainThreadChecker implements IMainThreadChecker {
    private static final NoOpMainThreadChecker instance = new NoOpMainThreadChecker();

    @Override // io.sentry.util.thread.IMainThreadChecker
    public /* synthetic */ boolean isMainThread() {
        boolean isMainThread;
        isMainThread = isMainThread(Thread.currentThread());
        return isMainThread;
    }

    @Override // io.sentry.util.thread.IMainThreadChecker
    public boolean isMainThread(long j) {
        return false;
    }

    @Override // io.sentry.util.thread.IMainThreadChecker
    public /* synthetic */ boolean isMainThread(SentryThread sentryThread) {
        return IMainThreadChecker.CC.$default$isMainThread(this, sentryThread);
    }

    @Override // io.sentry.util.thread.IMainThreadChecker
    public /* synthetic */ boolean isMainThread(Thread thread) {
        boolean isMainThread;
        isMainThread = isMainThread(thread.getId());
        return isMainThread;
    }

    public static NoOpMainThreadChecker getInstance() {
        return instance;
    }
}
