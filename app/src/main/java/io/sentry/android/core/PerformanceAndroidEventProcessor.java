package io.sentry.android.core;

import io.sentry.EventProcessor;
import io.sentry.Hint;
import io.sentry.MeasurementUnit;
import io.sentry.SentryEvent;
import io.sentry.SpanContext;
import io.sentry.protocol.MeasurementValue;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentrySpan;
import io.sentry.protocol.SentryTransaction;
import io.sentry.util.Objects;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class PerformanceAndroidEventProcessor implements EventProcessor {
    private final ActivityFramesTracker activityFramesTracker;
    private final SentryAndroidOptions options;
    private boolean sentStartMeasurement = false;

    @Override // io.sentry.EventProcessor
    public SentryEvent process(SentryEvent sentryEvent, Hint hint) {
        return sentryEvent;
    }

    PerformanceAndroidEventProcessor(SentryAndroidOptions sentryAndroidOptions, ActivityFramesTracker activityFramesTracker) {
        this.options = (SentryAndroidOptions) Objects.requireNonNull(sentryAndroidOptions, "SentryAndroidOptions is required");
        this.activityFramesTracker = (ActivityFramesTracker) Objects.requireNonNull(activityFramesTracker, "ActivityFramesTracker is required");
    }

    @Override // io.sentry.EventProcessor
    public synchronized SentryTransaction process(SentryTransaction sentryTransaction, Hint hint) {
        Map<String, MeasurementValue> takeMetrics;
        Long appStartInterval;
        if (!this.options.isTracingEnabled()) {
            return sentryTransaction;
        }
        if (!this.sentStartMeasurement && hasAppStartSpan(sentryTransaction.getSpans()) && (appStartInterval = AppStartState.getInstance().getAppStartInterval()) != null) {
            sentryTransaction.getMeasurements().put(AppStartState.getInstance().isColdStart().booleanValue() ? MeasurementValue.KEY_APP_START_COLD : MeasurementValue.KEY_APP_START_WARM, new MeasurementValue(Float.valueOf(appStartInterval.longValue()), MeasurementUnit.Duration.MILLISECOND.apiName()));
            this.sentStartMeasurement = true;
        }
        SentryId eventId = sentryTransaction.getEventId();
        SpanContext trace = sentryTransaction.getContexts().getTrace();
        if (eventId != null && trace != null && trace.getOperation().contentEquals("ui.load") && (takeMetrics = this.activityFramesTracker.takeMetrics(eventId)) != null) {
            sentryTransaction.getMeasurements().putAll(takeMetrics);
        }
        return sentryTransaction;
    }

    private boolean hasAppStartSpan(List<SentrySpan> list) {
        for (SentrySpan sentrySpan : list) {
            if (sentrySpan.getOp().contentEquals("app.start.cold") || sentrySpan.getOp().contentEquals("app.start.warm")) {
                return true;
            }
        }
        return false;
    }
}
