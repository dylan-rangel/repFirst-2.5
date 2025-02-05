package io.sentry.transport;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes3.dex */
public interface ITransport extends Closeable {
    void flush(long j);

    void send(SentryEnvelope sentryEnvelope) throws IOException;

    void send(SentryEnvelope sentryEnvelope, Hint hint) throws IOException;

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.transport.ITransport$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }
}
