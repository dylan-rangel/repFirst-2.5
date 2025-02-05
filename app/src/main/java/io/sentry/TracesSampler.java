package io.sentry;

import io.sentry.util.Objects;
import java.security.SecureRandom;

/* loaded from: classes3.dex */
final class TracesSampler {
    private static final Double DEFAULT_TRACES_SAMPLE_RATE = Double.valueOf(1.0d);
    private final SentryOptions options;
    private final SecureRandom random;

    public TracesSampler(SentryOptions sentryOptions) {
        this((SentryOptions) Objects.requireNonNull(sentryOptions, "options are required"), new SecureRandom());
    }

    TracesSampler(SentryOptions sentryOptions, SecureRandom secureRandom) {
        this.options = sentryOptions;
        this.random = secureRandom;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0082 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    io.sentry.TracesSamplingDecision sample(io.sentry.SamplingContext r9) {
        /*
            r8 = this;
            io.sentry.TransactionContext r0 = r9.getTransactionContext()
            io.sentry.TracesSamplingDecision r0 = r0.getSamplingDecision()
            if (r0 == 0) goto Lb
            return r0
        Lb:
            io.sentry.SentryOptions r0 = r8.options
            io.sentry.SentryOptions$ProfilesSamplerCallback r0 = r0.getProfilesSampler()
            r1 = 0
            if (r0 == 0) goto L2d
            io.sentry.SentryOptions r0 = r8.options     // Catch: java.lang.Throwable -> L1f
            io.sentry.SentryOptions$ProfilesSamplerCallback r0 = r0.getProfilesSampler()     // Catch: java.lang.Throwable -> L1f
            java.lang.Double r0 = r0.sample(r9)     // Catch: java.lang.Throwable -> L1f
            goto L2e
        L1f:
            r0 = move-exception
            io.sentry.SentryOptions r2 = r8.options
            io.sentry.ILogger r2 = r2.getLogger()
            io.sentry.SentryLevel r3 = io.sentry.SentryLevel.ERROR
            java.lang.String r4 = "Error in the 'ProfilesSamplerCallback' callback."
            r2.log(r3, r4, r0)
        L2d:
            r0 = r1
        L2e:
            if (r0 != 0) goto L36
            io.sentry.SentryOptions r0 = r8.options
            java.lang.Double r0 = r0.getProfilesSampleRate()
        L36:
            r2 = 0
            if (r0 == 0) goto L41
            boolean r3 = r8.sample(r0)
            if (r3 == 0) goto L41
            r3 = 1
            goto L42
        L41:
            r3 = 0
        L42:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            io.sentry.SentryOptions r4 = r8.options
            io.sentry.SentryOptions$TracesSamplerCallback r4 = r4.getTracesSampler()
            if (r4 == 0) goto L78
            io.sentry.SentryOptions r4 = r8.options     // Catch: java.lang.Throwable -> L59
            io.sentry.SentryOptions$TracesSamplerCallback r4 = r4.getTracesSampler()     // Catch: java.lang.Throwable -> L59
            java.lang.Double r4 = r4.sample(r9)     // Catch: java.lang.Throwable -> L59
            goto L68
        L59:
            r4 = move-exception
            io.sentry.SentryOptions r5 = r8.options
            io.sentry.ILogger r5 = r5.getLogger()
            io.sentry.SentryLevel r6 = io.sentry.SentryLevel.ERROR
            java.lang.String r7 = "Error in the 'TracesSamplerCallback' callback."
            r5.log(r6, r7, r4)
            r4 = r1
        L68:
            if (r4 == 0) goto L78
            io.sentry.TracesSamplingDecision r9 = new io.sentry.TracesSamplingDecision
            boolean r1 = r8.sample(r4)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r9.<init>(r1, r4, r3, r0)
            return r9
        L78:
            io.sentry.TransactionContext r9 = r9.getTransactionContext()
            io.sentry.TracesSamplingDecision r9 = r9.getParentSamplingDecision()
            if (r9 == 0) goto L83
            return r9
        L83:
            io.sentry.SentryOptions r9 = r8.options
            java.lang.Double r9 = r9.getTracesSampleRate()
            io.sentry.SentryOptions r4 = r8.options
            java.lang.Boolean r4 = r4.getEnableTracing()
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L9a
            java.lang.Double r4 = io.sentry.TracesSampler.DEFAULT_TRACES_SAMPLE_RATE
            goto L9b
        L9a:
            r4 = r1
        L9b:
            if (r9 != 0) goto L9e
            r9 = r4
        L9e:
            if (r9 == 0) goto Lae
            io.sentry.TracesSamplingDecision r1 = new io.sentry.TracesSamplingDecision
            boolean r2 = r8.sample(r9)
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r1.<init>(r2, r9, r3, r0)
            return r1
        Lae:
            io.sentry.TracesSamplingDecision r9 = new io.sentry.TracesSamplingDecision
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r2)
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r9.<init>(r0, r1, r2, r1)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.sentry.TracesSampler.sample(io.sentry.SamplingContext):io.sentry.TracesSamplingDecision");
    }

    private boolean sample(Double d) {
        return d.doubleValue() >= this.random.nextDouble();
    }
}
