package io.sentry;

import io.sentry.IHub;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import java.util.List;

/* loaded from: classes3.dex */
public final class NoOpHub implements IHub {
    private static final NoOpHub instance = new NoOpHub();
    private final SentryOptions emptyOptions = SentryOptions.empty();

    @Override // io.sentry.IHub
    public /* synthetic */ void addBreadcrumb(Breadcrumb breadcrumb) {
        addBreadcrumb(breadcrumb, new Hint());
    }

    @Override // io.sentry.IHub
    public void addBreadcrumb(Breadcrumb breadcrumb, Hint hint) {
    }

    @Override // io.sentry.IHub
    public /* synthetic */ void addBreadcrumb(String str) {
        addBreadcrumb(new Breadcrumb(str));
    }

    @Override // io.sentry.IHub
    public /* synthetic */ void addBreadcrumb(String str, String str2) {
        IHub.CC.$default$addBreadcrumb(this, str, str2);
    }

    @Override // io.sentry.IHub
    public void bindClient(ISentryClient iSentryClient) {
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureEnvelope(SentryEnvelope sentryEnvelope) {
        SentryId captureEnvelope;
        captureEnvelope = captureEnvelope(sentryEnvelope, new Hint());
        return captureEnvelope;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureEvent(SentryEvent sentryEvent) {
        SentryId captureEvent;
        captureEvent = captureEvent(sentryEvent, new Hint());
        return captureEvent;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureEvent(SentryEvent sentryEvent, ScopeCallback scopeCallback) {
        SentryId captureEvent;
        captureEvent = captureEvent(sentryEvent, new Hint(), scopeCallback);
        return captureEvent;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureException(Throwable th) {
        SentryId captureException;
        captureException = captureException(th, new Hint());
        return captureException;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureException(Throwable th, ScopeCallback scopeCallback) {
        SentryId captureException;
        captureException = captureException(th, new Hint(), scopeCallback);
        return captureException;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureMessage(String str) {
        SentryId captureMessage;
        captureMessage = captureMessage(str, SentryLevel.INFO);
        return captureMessage;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureMessage(String str, ScopeCallback scopeCallback) {
        SentryId captureMessage;
        captureMessage = captureMessage(str, SentryLevel.INFO, scopeCallback);
        return captureMessage;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureTransaction(SentryTransaction sentryTransaction, Hint hint) {
        SentryId captureTransaction;
        captureTransaction = captureTransaction(sentryTransaction, null, hint);
        return captureTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext) {
        SentryId captureTransaction;
        captureTransaction = captureTransaction(sentryTransaction, traceContext, null);
        return captureTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Hint hint) {
        SentryId captureTransaction;
        captureTransaction = captureTransaction(sentryTransaction, traceContext, hint, null);
        return captureTransaction;
    }

    @Override // io.sentry.IHub
    public void captureUserFeedback(UserFeedback userFeedback) {
    }

    @Override // io.sentry.IHub
    public void clearBreadcrumbs() {
    }

    @Override // io.sentry.IHub
    public void close() {
    }

    @Override // io.sentry.IHub
    public void configureScope(ScopeCallback scopeCallback) {
    }

    @Override // io.sentry.IHub
    public TransactionContext continueTrace(String str, List<String> list) {
        return null;
    }

    @Override // io.sentry.IHub
    public void endSession() {
    }

    @Override // io.sentry.IHub
    public void flush(long j) {
    }

    @Override // io.sentry.IHub
    public BaggageHeader getBaggage() {
        return null;
    }

    @Override // io.sentry.IHub
    public ISpan getSpan() {
        return null;
    }

    @Override // io.sentry.IHub
    public SentryTraceHeader getTraceparent() {
        return null;
    }

    @Override // io.sentry.IHub
    public Boolean isCrashedLastRun() {
        return null;
    }

    @Override // io.sentry.IHub
    public boolean isEnabled() {
        return false;
    }

    @Override // io.sentry.IHub
    public void popScope() {
    }

    @Override // io.sentry.IHub
    public void pushScope() {
    }

    @Override // io.sentry.IHub
    public void removeExtra(String str) {
    }

    @Override // io.sentry.IHub
    public void removeTag(String str) {
    }

    @Override // io.sentry.IHub
    public /* synthetic */ void reportFullDisplayed() {
        reportFullyDisplayed();
    }

    @Override // io.sentry.IHub
    public void reportFullyDisplayed() {
    }

    @Override // io.sentry.IHub
    public void setExtra(String str, String str2) {
    }

    @Override // io.sentry.IHub
    public void setFingerprint(List<String> list) {
    }

    @Override // io.sentry.IHub
    public void setLevel(SentryLevel sentryLevel) {
    }

    @Override // io.sentry.IHub
    public void setSpanContext(Throwable th, ISpan iSpan, String str) {
    }

    @Override // io.sentry.IHub
    public void setTag(String str, String str2) {
    }

    @Override // io.sentry.IHub
    public void setTransaction(String str) {
    }

    @Override // io.sentry.IHub
    public void setUser(User user) {
    }

    @Override // io.sentry.IHub
    public void startSession() {
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(TransactionContext transactionContext, CustomSamplingContext customSamplingContext) {
        ITransaction startTransaction;
        startTransaction = startTransaction(transactionContext, customSamplingContext, false);
        return startTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(TransactionContext transactionContext, boolean z) {
        ITransaction startTransaction;
        startTransaction = startTransaction(transactionContext, (CustomSamplingContext) null, z);
        return startTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(String str, String str2) {
        ITransaction startTransaction;
        startTransaction = startTransaction(str, str2, (CustomSamplingContext) null);
        return startTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(String str, String str2, CustomSamplingContext customSamplingContext) {
        ITransaction startTransaction;
        startTransaction = startTransaction(str, str2, customSamplingContext, false);
        return startTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(String str, String str2, CustomSamplingContext customSamplingContext, boolean z) {
        ITransaction startTransaction;
        startTransaction = startTransaction(new TransactionContext(str, str2), customSamplingContext, z);
        return startTransaction;
    }

    @Override // io.sentry.IHub
    public /* synthetic */ ITransaction startTransaction(String str, String str2, boolean z) {
        return IHub.CC.$default$startTransaction(this, str, str2, z);
    }

    @Override // io.sentry.IHub
    public void withScope(ScopeCallback scopeCallback) {
    }

    private NoOpHub() {
    }

    public static NoOpHub getInstance() {
        return instance;
    }

    @Override // io.sentry.IHub
    public SentryId captureEvent(SentryEvent sentryEvent, Hint hint) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureEvent(SentryEvent sentryEvent, Hint hint, ScopeCallback scopeCallback) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureMessage(String str, SentryLevel sentryLevel) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureMessage(String str, SentryLevel sentryLevel, ScopeCallback scopeCallback) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureEnvelope(SentryEnvelope sentryEnvelope, Hint hint) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureException(Throwable th, Hint hint) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId captureException(Throwable th, Hint hint, ScopeCallback scopeCallback) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public SentryId getLastEventId() {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IHub m234clone() {
        return instance;
    }

    @Override // io.sentry.IHub
    public SentryId captureTransaction(SentryTransaction sentryTransaction, TraceContext traceContext, Hint hint, ProfilingTraceData profilingTraceData) {
        return SentryId.EMPTY_ID;
    }

    @Override // io.sentry.IHub
    public ITransaction startTransaction(TransactionContext transactionContext) {
        return NoOpTransaction.getInstance();
    }

    @Override // io.sentry.IHub
    public ITransaction startTransaction(TransactionContext transactionContext, CustomSamplingContext customSamplingContext, boolean z) {
        return NoOpTransaction.getInstance();
    }

    @Override // io.sentry.IHub
    public ITransaction startTransaction(TransactionContext transactionContext, TransactionOptions transactionOptions) {
        return NoOpTransaction.getInstance();
    }

    @Override // io.sentry.IHub
    @Deprecated
    public SentryTraceHeader traceHeaders() {
        return new SentryTraceHeader(SentryId.EMPTY_ID, SpanId.EMPTY_ID, true);
    }

    @Override // io.sentry.IHub
    public SentryOptions getOptions() {
        return this.emptyOptions;
    }

    @Override // io.sentry.IHub
    public SentryId captureCheckIn(CheckIn checkIn) {
        return SentryId.EMPTY_ID;
    }
}
