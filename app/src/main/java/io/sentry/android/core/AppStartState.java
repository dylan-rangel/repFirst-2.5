package io.sentry.android.core;

import android.os.SystemClock;
import io.sentry.SentryDate;
import io.sentry.SentryLongDate;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes3.dex */
public final class AppStartState {
    private static final int MAX_APP_START_MILLIS = 60000;
    private static AppStartState instance = new AppStartState();
    private Long appStartEndMillis;
    private Long appStartMillis;
    private SentryDate appStartTime;
    private Boolean coldStart = null;

    private AppStartState() {
    }

    public static AppStartState getInstance() {
        return instance;
    }

    void resetInstance() {
        instance = new AppStartState();
    }

    synchronized void setAppStartEnd() {
        setAppStartEnd(SystemClock.uptimeMillis());
    }

    void setAppStartEnd(long j) {
        this.appStartEndMillis = Long.valueOf(j);
    }

    public synchronized Long getAppStartInterval() {
        Long l;
        if (this.appStartMillis != null && (l = this.appStartEndMillis) != null && this.coldStart != null) {
            long longValue = l.longValue() - this.appStartMillis.longValue();
            if (longValue >= DateUtils.MILLIS_PER_MINUTE) {
                return null;
            }
            return Long.valueOf(longValue);
        }
        return null;
    }

    public Boolean isColdStart() {
        return this.coldStart;
    }

    synchronized void setColdStart(boolean z) {
        if (this.coldStart != null) {
            return;
        }
        this.coldStart = Boolean.valueOf(z);
    }

    public SentryDate getAppStartTime() {
        return this.appStartTime;
    }

    public SentryDate getAppStartEndTime() {
        Long appStartInterval;
        SentryDate appStartTime = getAppStartTime();
        if (appStartTime == null || (appStartInterval = getAppStartInterval()) == null) {
            return null;
        }
        return new SentryLongDate(appStartTime.nanoTimestamp() + io.sentry.DateUtils.millisToNanos(appStartInterval.longValue()));
    }

    public Long getAppStartMillis() {
        return this.appStartMillis;
    }

    synchronized void setAppStartTime(long j, SentryDate sentryDate) {
        if (this.appStartTime == null || this.appStartMillis == null) {
            this.appStartTime = sentryDate;
            this.appStartMillis = Long.valueOf(j);
        }
    }

    public synchronized void setAppStartMillis(long j) {
        this.appStartMillis = Long.valueOf(j);
    }

    public synchronized void reset() {
        this.appStartTime = null;
        this.appStartMillis = null;
        this.appStartEndMillis = null;
    }
}
