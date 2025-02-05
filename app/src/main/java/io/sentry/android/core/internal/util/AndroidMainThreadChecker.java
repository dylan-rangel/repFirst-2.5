package io.sentry.android.core.internal.util;

import android.os.Looper;
import io.sentry.protocol.SentryThread;
import io.sentry.util.thread.IMainThreadChecker;

/* loaded from: classes3.dex */
public final class AndroidMainThreadChecker implements IMainThreadChecker {
    private static final AndroidMainThreadChecker instance = new AndroidMainThreadChecker();

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

    public static AndroidMainThreadChecker getInstance() {
        return instance;
    }

    private AndroidMainThreadChecker() {
    }

    @Override // io.sentry.util.thread.IMainThreadChecker
    public boolean isMainThread(long j) {
        return Looper.getMainLooper().getThread().getId() == j;
    }
}
