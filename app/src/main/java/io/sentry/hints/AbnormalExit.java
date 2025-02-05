package io.sentry.hints;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* loaded from: classes3.dex */
public interface AbnormalExit {

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.hints.AbnormalExit$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$ignoreCurrentThread(AbnormalExit _this) {
            return false;
        }

        public static Long $default$timestamp(AbnormalExit _this) {
            return null;
        }
    }

    boolean ignoreCurrentThread();

    String mechanism();

    Long timestamp();
}
