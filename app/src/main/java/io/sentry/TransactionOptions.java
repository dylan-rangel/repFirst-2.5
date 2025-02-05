package io.sentry;

/* loaded from: classes3.dex */
public final class TransactionOptions extends SpanOptions {
    private CustomSamplingContext customSamplingContext = null;
    private boolean bindToScope = false;
    private SentryDate startTimestamp = null;
    private boolean waitForChildren = false;
    private Long idleTimeout = null;
    private TransactionFinishedCallback transactionFinishedCallback = null;

    public CustomSamplingContext getCustomSamplingContext() {
        return this.customSamplingContext;
    }

    public void setCustomSamplingContext(CustomSamplingContext customSamplingContext) {
        this.customSamplingContext = customSamplingContext;
    }

    public boolean isBindToScope() {
        return this.bindToScope;
    }

    public void setBindToScope(boolean z) {
        this.bindToScope = z;
    }

    public SentryDate getStartTimestamp() {
        return this.startTimestamp;
    }

    public void setStartTimestamp(SentryDate sentryDate) {
        this.startTimestamp = sentryDate;
    }

    public boolean isWaitForChildren() {
        return this.waitForChildren;
    }

    public void setWaitForChildren(boolean z) {
        this.waitForChildren = z;
    }

    public Long getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(Long l) {
        this.idleTimeout = l;
    }

    public TransactionFinishedCallback getTransactionFinishedCallback() {
        return this.transactionFinishedCallback;
    }

    public void setTransactionFinishedCallback(TransactionFinishedCallback transactionFinishedCallback) {
        this.transactionFinishedCallback = transactionFinishedCallback;
    }
}
