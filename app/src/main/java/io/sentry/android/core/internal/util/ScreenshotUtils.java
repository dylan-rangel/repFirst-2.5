package io.sentry.android.core.internal.util;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.View;
import io.sentry.ILogger;
import io.sentry.android.core.BuildInfoProvider;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ScreenshotUtils {
    private static final long CAPTURE_TIMEOUT_MS = 1000;

    public static byte[] takeScreenshot(Activity activity, ILogger iLogger, BuildInfoProvider buildInfoProvider) {
        return takeScreenshot(activity, AndroidMainThreadChecker.getInstance(), iLogger, buildInfoProvider);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x009f, code lost:
    
        if (r14.get() != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] takeScreenshot(android.app.Activity r11, io.sentry.util.thread.IMainThreadChecker r12, final io.sentry.ILogger r13, io.sentry.android.core.BuildInfoProvider r14) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.sentry.android.core.internal.util.ScreenshotUtils.takeScreenshot(android.app.Activity, io.sentry.util.thread.IMainThreadChecker, io.sentry.ILogger, io.sentry.android.core.BuildInfoProvider):byte[]");
    }

    static /* synthetic */ void lambda$takeScreenshot$0(AtomicBoolean atomicBoolean, CountDownLatch countDownLatch, int i) {
        atomicBoolean.set(i == 0);
        countDownLatch.countDown();
    }

    static /* synthetic */ void lambda$takeScreenshot$1(View view, Canvas canvas, ILogger iLogger, CountDownLatch countDownLatch) {
        try {
            view.draw(canvas);
        } finally {
            try {
            } finally {
            }
        }
    }

    private static boolean isActivityValid(Activity activity, BuildInfoProvider buildInfoProvider) {
        if (buildInfoProvider.getSdkInfoVersion() >= 17) {
            return (activity.isFinishing() || activity.isDestroyed()) ? false : true;
        }
        return !activity.isFinishing();
    }
}
