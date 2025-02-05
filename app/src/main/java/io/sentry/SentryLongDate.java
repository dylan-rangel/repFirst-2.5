package io.sentry;

/* loaded from: classes3.dex */
public final class SentryLongDate extends SentryDate {
    private final long nanos;

    public SentryLongDate(long j) {
        this.nanos = j;
    }

    @Override // io.sentry.SentryDate
    public long nanoTimestamp() {
        return this.nanos;
    }
}
