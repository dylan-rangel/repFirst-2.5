package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.Message;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;

/* loaded from: classes3.dex */
public interface ISentryClient {
    SentryId captureCheckIn(CheckIn checkIn, Scope scope, Hint hint);

    SentryId captureEnvelope(SentryEnvelope sentryEnvelope);

    SentryId captureEnvelope(SentryEnvelope sentryEnvelope, Hint hint);

    SentryId captureEvent(SentryEvent sentryEvent);

    SentryId captureEvent(SentryEvent sentryEvent, Hint hint);

    SentryId captureEvent(SentryEvent sentryEvent, Scope scope);

    SentryId captureEvent(SentryEvent sentryEvent, Scope scope, Hint hint);

    SentryId captureException(Throwable th);

    SentryId captureException(Throwable th, Hint hint);

    SentryId captureException(Throwable th, Scope scope);

    SentryId captureException(Throwable th, Scope scope, Hint hint);

    SentryId captureMessage(String str, SentryLevel sentryLevel);

    SentryId captureMessage(String str, SentryLevel sentryLevel, Scope scope);

    void captureSession(Session session);

    void captureSession(Session session, Hint hint);

    SentryId captureTransaction(SentryTransaction sentryTransaction);

    SentryId captureTransaction(SentryTransaction sentryTransaction, Scope scope, Hint hint);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Scope scope, Hint hint);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Scope scope, Hint hint, ProfilingTraceData profilingTraceData);

    void captureUserFeedback(UserFeedback userFeedback);

    void close();

    void flush(long j);

    boolean isEnabled();

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.ISentryClient$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static SentryId $default$captureMessage(ISentryClient _this, String str, SentryLevel sentryLevel, Scope scope) {
            SentryEvent sentryEvent = new SentryEvent();
            Message message = new Message();
            message.setFormatted(str);
            sentryEvent.setMessage(message);
            sentryEvent.setLevel(sentryLevel);
            return _this.captureEvent(sentryEvent, scope);
        }
    }
}
