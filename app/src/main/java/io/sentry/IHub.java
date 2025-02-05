package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import java.util.List;

/* loaded from: classes3.dex */
public interface IHub {
    void addBreadcrumb(Breadcrumb breadcrumb);

    void addBreadcrumb(Breadcrumb breadcrumb, Hint hint);

    void addBreadcrumb(String str);

    void addBreadcrumb(String str, String str2);

    void bindClient(ISentryClient iSentryClient);

    SentryId captureCheckIn(CheckIn checkIn);

    SentryId captureEnvelope(SentryEnvelope sentryEnvelope);

    SentryId captureEnvelope(SentryEnvelope sentryEnvelope, Hint hint);

    SentryId captureEvent(SentryEvent sentryEvent);

    SentryId captureEvent(SentryEvent sentryEvent, Hint hint);

    SentryId captureEvent(SentryEvent sentryEvent, Hint hint, ScopeCallback scopeCallback);

    SentryId captureEvent(SentryEvent sentryEvent, ScopeCallback scopeCallback);

    SentryId captureException(Throwable th);

    SentryId captureException(Throwable th, Hint hint);

    SentryId captureException(Throwable th, Hint hint, ScopeCallback scopeCallback);

    SentryId captureException(Throwable th, ScopeCallback scopeCallback);

    SentryId captureMessage(String str);

    SentryId captureMessage(String str, ScopeCallback scopeCallback);

    SentryId captureMessage(String str, SentryLevel sentryLevel);

    SentryId captureMessage(String str, SentryLevel sentryLevel, ScopeCallback scopeCallback);

    SentryId captureTransaction(SentryTransaction sentryTransaction, Hint hint);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Hint hint);

    SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Hint hint, ProfilingTraceData profilingTraceData);

    void captureUserFeedback(UserFeedback userFeedback);

    void clearBreadcrumbs();

    /* renamed from: clone */
    IHub m232clone();

    void close();

    void configureScope(ScopeCallback scopeCallback);

    TransactionContext continueTrace(String str, List<String> list);

    void endSession();

    void flush(long j);

    BaggageHeader getBaggage();

    SentryId getLastEventId();

    SentryOptions getOptions();

    ISpan getSpan();

    SentryTraceHeader getTraceparent();

    Boolean isCrashedLastRun();

    boolean isEnabled();

    void popScope();

    void pushScope();

    void removeExtra(String str);

    void removeTag(String str);

    @Deprecated
    void reportFullDisplayed();

    void reportFullyDisplayed();

    void setExtra(String str, String str2);

    void setFingerprint(List<String> list);

    void setLevel(SentryLevel sentryLevel);

    void setSpanContext(Throwable th, ISpan iSpan, String str);

    void setTag(String str, String str2);

    void setTransaction(String str);

    void setUser(User user);

    void startSession();

    ITransaction startTransaction(TransactionContext transactionContext);

    ITransaction startTransaction(TransactionContext transactionContext, CustomSamplingContext customSamplingContext);

    ITransaction startTransaction(TransactionContext transactionContext, CustomSamplingContext customSamplingContext, boolean z);

    ITransaction startTransaction(TransactionContext transactionContext, TransactionOptions transactionOptions);

    ITransaction startTransaction(TransactionContext transactionContext, boolean z);

    ITransaction startTransaction(String str, String str2);

    ITransaction startTransaction(String str, String str2, CustomSamplingContext customSamplingContext);

    ITransaction startTransaction(String str, String str2, CustomSamplingContext customSamplingContext, boolean z);

    ITransaction startTransaction(String str, String str2, boolean z);

    @Deprecated
    SentryTraceHeader traceHeaders();

    void withScope(ScopeCallback scopeCallback);

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.IHub$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$addBreadcrumb(IHub _this, String str, String str2) {
            Breadcrumb breadcrumb = new Breadcrumb(str);
            breadcrumb.setCategory(str2);
            _this.addBreadcrumb(breadcrumb);
        }

        public static ITransaction $default$startTransaction(IHub _this, String str, String str2, boolean z) {
            return _this.startTransaction(str, str2, null, z);
        }
    }
}
