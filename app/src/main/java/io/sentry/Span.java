package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public final class Span implements ISpan {
    private final SpanContext context;
    private final Map<String, Object> data;
    private final AtomicBoolean finished;
    private final IHub hub;
    private final SpanOptions options;
    private SpanFinishedCallback spanFinishedCallback;
    private SentryDate startTimestamp;
    private Throwable throwable;
    private SentryDate timestamp;
    private final SentryTracer transaction;

    @Override // io.sentry.ISpan
    public boolean isNoOp() {
        return false;
    }

    Span(SentryId sentryId, SpanId spanId, SentryTracer sentryTracer, String str, IHub iHub) {
        this(sentryId, spanId, sentryTracer, str, iHub, null, new SpanOptions(), null);
    }

    Span(SentryId sentryId, SpanId spanId, SentryTracer sentryTracer, String str, IHub iHub, SentryDate sentryDate, SpanOptions spanOptions, SpanFinishedCallback spanFinishedCallback) {
        this.finished = new AtomicBoolean(false);
        this.data = new ConcurrentHashMap();
        this.context = new SpanContext(sentryId, new SpanId(), str, spanId, sentryTracer.getSamplingDecision());
        this.transaction = (SentryTracer) Objects.requireNonNull(sentryTracer, "transaction is required");
        this.hub = (IHub) Objects.requireNonNull(iHub, "hub is required");
        this.options = spanOptions;
        this.spanFinishedCallback = spanFinishedCallback;
        if (sentryDate != null) {
            this.startTimestamp = sentryDate;
        } else {
            this.startTimestamp = iHub.getOptions().getDateProvider().now();
        }
    }

    public Span(TransactionContext transactionContext, SentryTracer sentryTracer, IHub iHub, SentryDate sentryDate, SpanOptions spanOptions) {
        this.finished = new AtomicBoolean(false);
        this.data = new ConcurrentHashMap();
        this.context = (SpanContext) Objects.requireNonNull(transactionContext, "context is required");
        this.transaction = (SentryTracer) Objects.requireNonNull(sentryTracer, "sentryTracer is required");
        this.hub = (IHub) Objects.requireNonNull(iHub, "hub is required");
        this.spanFinishedCallback = null;
        if (sentryDate != null) {
            this.startTimestamp = sentryDate;
        } else {
            this.startTimestamp = iHub.getOptions().getDateProvider().now();
        }
        this.options = spanOptions;
    }

    @Override // io.sentry.ISpan
    public SentryDate getStartDate() {
        return this.startTimestamp;
    }

    @Override // io.sentry.ISpan
    public SentryDate getFinishDate() {
        return this.timestamp;
    }

    @Override // io.sentry.ISpan
    public ISpan startChild(String str) {
        return startChild(str, null);
    }

    @Override // io.sentry.ISpan
    public ISpan startChild(String str, String str2, SentryDate sentryDate, Instrumenter instrumenter, SpanOptions spanOptions) {
        if (this.finished.get()) {
            return NoOpSpan.getInstance();
        }
        return this.transaction.startChild(this.context.getSpanId(), str, str2, sentryDate, instrumenter, spanOptions);
    }

    @Override // io.sentry.ISpan
    public ISpan startChild(String str, String str2) {
        if (this.finished.get()) {
            return NoOpSpan.getInstance();
        }
        return this.transaction.startChild(this.context.getSpanId(), str, str2);
    }

    @Override // io.sentry.ISpan
    public ISpan startChild(String str, String str2, SpanOptions spanOptions) {
        if (this.finished.get()) {
            return NoOpSpan.getInstance();
        }
        return this.transaction.startChild(this.context.getSpanId(), str, str2, spanOptions);
    }

    @Override // io.sentry.ISpan
    public ISpan startChild(String str, String str2, SentryDate sentryDate, Instrumenter instrumenter) {
        return startChild(str, str2, sentryDate, instrumenter, new SpanOptions());
    }

    @Override // io.sentry.ISpan
    public SentryTraceHeader toSentryTrace() {
        return new SentryTraceHeader(this.context.getTraceId(), this.context.getSpanId(), this.context.getSampled());
    }

    @Override // io.sentry.ISpan
    public TraceContext traceContext() {
        return this.transaction.traceContext();
    }

    @Override // io.sentry.ISpan
    public BaggageHeader toBaggageHeader(List<String> list) {
        return this.transaction.toBaggageHeader(list);
    }

    @Override // io.sentry.ISpan
    public void finish() {
        finish(this.context.getStatus());
    }

    @Override // io.sentry.ISpan
    public void finish(SpanStatus spanStatus) {
        finish(spanStatus, this.hub.getOptions().getDateProvider().now());
    }

    @Override // io.sentry.ISpan
    public void finish(SpanStatus spanStatus, SentryDate sentryDate) {
        List<Span> directChildren;
        SentryDate sentryDate2;
        if (this.finished.compareAndSet(false, true)) {
            this.context.setStatus(spanStatus);
            if (sentryDate == null) {
                sentryDate = this.hub.getOptions().getDateProvider().now();
            }
            this.timestamp = sentryDate;
            if (this.options.isTrimStart() || this.options.isTrimEnd()) {
                if (this.transaction.getRoot().getSpanId().equals(getSpanId())) {
                    directChildren = this.transaction.getChildren();
                } else {
                    directChildren = getDirectChildren();
                }
                SentryDate sentryDate3 = null;
                SentryDate sentryDate4 = null;
                for (Span span : directChildren) {
                    if (sentryDate3 == null || span.getStartDate().isBefore(sentryDate3)) {
                        sentryDate3 = span.getStartDate();
                    }
                    if (sentryDate4 == null || (span.getFinishDate() != null && span.getFinishDate().isAfter(sentryDate4))) {
                        sentryDate4 = span.getFinishDate();
                    }
                }
                if (this.options.isTrimStart() && sentryDate3 != null && this.startTimestamp.isBefore(sentryDate3)) {
                    updateStartDate(sentryDate3);
                }
                if (this.options.isTrimEnd() && sentryDate4 != null && ((sentryDate2 = this.timestamp) == null || sentryDate2.isAfter(sentryDate4))) {
                    updateEndDate(sentryDate4);
                }
            }
            Throwable th = this.throwable;
            if (th != null) {
                this.hub.setSpanContext(th, this, this.transaction.getName());
            }
            SpanFinishedCallback spanFinishedCallback = this.spanFinishedCallback;
            if (spanFinishedCallback != null) {
                spanFinishedCallback.execute(this);
            }
        }
    }

    @Override // io.sentry.ISpan
    public void setOperation(String str) {
        if (this.finished.get()) {
            return;
        }
        this.context.setOperation(str);
    }

    @Override // io.sentry.ISpan
    public String getOperation() {
        return this.context.getOperation();
    }

    @Override // io.sentry.ISpan
    public void setDescription(String str) {
        if (this.finished.get()) {
            return;
        }
        this.context.setDescription(str);
    }

    @Override // io.sentry.ISpan
    public String getDescription() {
        return this.context.getDescription();
    }

    @Override // io.sentry.ISpan
    public void setStatus(SpanStatus spanStatus) {
        if (this.finished.get()) {
            return;
        }
        this.context.setStatus(spanStatus);
    }

    @Override // io.sentry.ISpan
    public SpanStatus getStatus() {
        return this.context.getStatus();
    }

    @Override // io.sentry.ISpan
    public SpanContext getSpanContext() {
        return this.context;
    }

    @Override // io.sentry.ISpan
    public void setTag(String str, String str2) {
        if (this.finished.get()) {
            return;
        }
        this.context.setTag(str, str2);
    }

    @Override // io.sentry.ISpan
    public String getTag(String str) {
        return this.context.getTags().get(str);
    }

    @Override // io.sentry.ISpan
    public boolean isFinished() {
        return this.finished.get();
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public Boolean isSampled() {
        return this.context.getSampled();
    }

    public Boolean isProfileSampled() {
        return this.context.getProfileSampled();
    }

    public TracesSamplingDecision getSamplingDecision() {
        return this.context.getSamplingDecision();
    }

    @Override // io.sentry.ISpan
    public void setThrowable(Throwable th) {
        if (this.finished.get()) {
            return;
        }
        this.throwable = th;
    }

    @Override // io.sentry.ISpan
    public Throwable getThrowable() {
        return this.throwable;
    }

    public SentryId getTraceId() {
        return this.context.getTraceId();
    }

    public SpanId getSpanId() {
        return this.context.getSpanId();
    }

    public SpanId getParentSpanId() {
        return this.context.getParentSpanId();
    }

    public Map<String, String> getTags() {
        return this.context.getTags();
    }

    @Override // io.sentry.ISpan
    public void setData(String str, Object obj) {
        if (this.finished.get()) {
            return;
        }
        this.data.put(str, obj);
    }

    @Override // io.sentry.ISpan
    public Object getData(String str) {
        return this.data.get(str);
    }

    @Override // io.sentry.ISpan
    public void setMeasurement(String str, Number number) {
        this.transaction.setMeasurement(str, number);
    }

    @Override // io.sentry.ISpan
    public void setMeasurement(String str, Number number, MeasurementUnit measurementUnit) {
        this.transaction.setMeasurement(str, number, measurementUnit);
    }

    @Override // io.sentry.ISpan
    public boolean updateEndDate(SentryDate sentryDate) {
        if (this.timestamp == null) {
            return false;
        }
        this.timestamp = sentryDate;
        return true;
    }

    void setSpanFinishedCallback(SpanFinishedCallback spanFinishedCallback) {
        this.spanFinishedCallback = spanFinishedCallback;
    }

    private void updateStartDate(SentryDate sentryDate) {
        this.startTimestamp = sentryDate;
    }

    SpanOptions getOptions() {
        return this.options;
    }

    private List<Span> getDirectChildren() {
        ArrayList arrayList = new ArrayList();
        for (Span span : this.transaction.getSpans()) {
            if (span.getParentSpanId() != null && span.getParentSpanId().equals(getSpanId())) {
                arrayList.add(span);
            }
        }
        return arrayList;
    }
}
