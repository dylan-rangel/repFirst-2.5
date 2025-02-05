package io.sentry.util.thread;

import io.sentry.protocol.SentryThread;
import io.sentry.util.thread.IMainThreadChecker;

/* loaded from: classes3.dex */
public final class MainThreadChecker implements IMainThreadChecker {
    private static final long mainThreadId = Thread.currentThread().getId();
    private static final MainThreadChecker instance = new MainThreadChecker();

    @Override // io.sentry.util.thread.IMainThreadChecker
    public /* synthetic */ boolean isMainThread() {
        boolean isMainThread;
        isMainThread = isMainThread(Thread.currentThread());
        return isMainThread;
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

    public static MainThreadChecker getInstance() {
        return instance;
    }

    private MainThreadChecker() {
    }

    @Override // io.sentry.util.thread.IMainThreadChecker
    public boolean isMainThread(long j) {
        return mainThreadId == j;
    }
}
