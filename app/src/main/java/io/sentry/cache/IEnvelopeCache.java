package io.sentry.cache;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.Hint;
import io.sentry.SentryEnvelope;

/* loaded from: classes3.dex */
public interface IEnvelopeCache extends Iterable<SentryEnvelope> {
    void discard(SentryEnvelope sentryEnvelope);

    void store(SentryEnvelope sentryEnvelope);

    void store(SentryEnvelope sentryEnvelope, Hint hint);

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.cache.IEnvelopeCache$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }
}
