package io.sentry.util.thread;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.SentryThread;

/* loaded from: classes3.dex */
public interface IMainThreadChecker {
    boolean isMainThread();

    boolean isMainThread(long j);

    boolean isMainThread(SentryThread sentryThread);

    boolean isMainThread(Thread thread);

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.util.thread.IMainThreadChecker$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$isMainThread(IMainThreadChecker _this, SentryThread sentryThread) {
            Long id = sentryThread.getId();
            return id != null && _this.isMainThread(id.longValue());
        }
    }
}
