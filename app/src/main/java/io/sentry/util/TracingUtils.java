package io.sentry.util;

import io.sentry.Baggage;
import io.sentry.BaggageHeader;
import io.sentry.IHub;
import io.sentry.ISpan;
import io.sentry.PropagationContext;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.SentryOptions;
import io.sentry.SentryTraceHeader;
import io.sentry.util.TracingUtils;
import java.util.List;

/* loaded from: classes3.dex */
public final class TracingUtils {
    public static void startNewTrace(IHub iHub) {
        iHub.configureScope(new ScopeCallback() { // from class: io.sentry.util.TracingUtils$$ExternalSyntheticLambda1
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                scope.withPropagationContext(new Scope.IWithPropagationContext() { // from class: io.sentry.util.TracingUtils$$ExternalSyntheticLambda0
                    @Override // io.sentry.Scope.IWithPropagationContext
                    public final void accept(PropagationContext propagationContext) {
                        Scope.this.setPropagationContext(new PropagationContext());
                    }
                });
            }
        });
    }

    public static TracingHeaders traceIfAllowed(IHub iHub, String str, List<String> list, ISpan iSpan) {
        SentryOptions options = iHub.getOptions();
        if (options.isTraceSampling() && shouldAttachTracingHeaders(str, options)) {
            return trace(iHub, list, iSpan);
        }
        return null;
    }

    public static TracingHeaders trace(IHub iHub, List<String> list, ISpan iSpan) {
        final SentryOptions options = iHub.getOptions();
        if (iSpan != null && !iSpan.isNoOp()) {
            return new TracingHeaders(iSpan.toSentryTrace(), iSpan.toBaggageHeader(list));
        }
        final PropagationContextHolder propagationContextHolder = new PropagationContextHolder();
        iHub.configureScope(new ScopeCallback() { // from class: io.sentry.util.TracingUtils$$ExternalSyntheticLambda3
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                TracingUtils.PropagationContextHolder.this.propagationContext = TracingUtils.maybeUpdateBaggage(scope, options);
            }
        });
        if (propagationContextHolder.propagationContext == null) {
            return null;
        }
        PropagationContext propagationContext = propagationContextHolder.propagationContext;
        Baggage baggage = propagationContext.getBaggage();
        return new TracingHeaders(new SentryTraceHeader(propagationContext.getTraceId(), propagationContext.getSpanId(), null), baggage != null ? BaggageHeader.fromBaggageAndOutgoingHeader(baggage, list) : null);
    }

    public static PropagationContext maybeUpdateBaggage(final Scope scope, final SentryOptions sentryOptions) {
        return scope.withPropagationContext(new Scope.IWithPropagationContext() { // from class: io.sentry.util.TracingUtils$$ExternalSyntheticLambda2
            @Override // io.sentry.Scope.IWithPropagationContext
            public final void accept(PropagationContext propagationContext) {
                TracingUtils.lambda$maybeUpdateBaggage$3(SentryOptions.this, scope, propagationContext);
            }
        });
    }

    static /* synthetic */ void lambda$maybeUpdateBaggage$3(SentryOptions sentryOptions, Scope scope, PropagationContext propagationContext) {
        Baggage baggage = propagationContext.getBaggage();
        if (baggage == null) {
            baggage = new Baggage(sentryOptions.getLogger());
            propagationContext.setBaggage(baggage);
        }
        if (baggage.isMutable()) {
            baggage.setValuesFromScope(scope, sentryOptions);
            baggage.freeze();
        }
    }

    private static boolean shouldAttachTracingHeaders(String str, SentryOptions sentryOptions) {
        return PropagationTargetsUtils.contain(sentryOptions.getTracePropagationTargets(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PropagationContextHolder {
        private PropagationContext propagationContext;

        private PropagationContextHolder() {
            this.propagationContext = null;
        }
    }

    public static final class TracingHeaders {
        private final BaggageHeader baggageHeader;
        private final SentryTraceHeader sentryTraceHeader;

        public TracingHeaders(SentryTraceHeader sentryTraceHeader, BaggageHeader baggageHeader) {
            this.sentryTraceHeader = sentryTraceHeader;
            this.baggageHeader = baggageHeader;
        }

        public SentryTraceHeader getSentryTraceHeader() {
            return this.sentryTraceHeader;
        }

        public BaggageHeader getBaggageHeader() {
            return this.baggageHeader;
        }
    }
}
