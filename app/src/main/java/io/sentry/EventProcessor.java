package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.SentryTransaction;

/* loaded from: classes3.dex */
public interface EventProcessor {

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.EventProcessor$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static SentryEvent $default$process(EventProcessor _this, SentryEvent sentryEvent, Hint hint) {
            return sentryEvent;
        }

        public static SentryTransaction $default$process(EventProcessor _this, SentryTransaction sentryTransaction, Hint hint) {
            return sentryTransaction;
        }
    }

    SentryEvent process(SentryEvent sentryEvent, Hint hint);

    SentryTransaction process(SentryTransaction sentryTransaction, Hint hint);
}
