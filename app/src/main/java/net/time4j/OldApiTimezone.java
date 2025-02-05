package net.time4j;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import net.time4j.tz.Timezone;
import net.time4j.tz.TransitionHistory;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class OldApiTimezone extends TimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -6919910650419401271L;
    private final Timezone tz;

    @Override // java.util.TimeZone
    public void setRawOffset(int i) {
    }

    OldApiTimezone(Timezone timezone) {
        this.tz = timezone;
        setID(timezone.getID().canonical());
    }

    @Override // java.util.TimeZone
    public int getOffset(long j) {
        return this.tz.getOffset(TemporalType.MILLIS_SINCE_UNIX.translate(Long.valueOf(j))).getIntegralAmount() * 1000;
    }

    @Override // java.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i6 < 0 || i6 >= 86400000) {
            throw new IllegalArgumentException("Milliseconds out of range: " + i6);
        }
        if (i == 0) {
            i2 = 1 - i2;
        } else {
            if (i != 1) {
                throw new IllegalArgumentException("Unknown era: " + i);
            }
            if (i5 < 1 || i5 > 7) {
                throw new IllegalArgumentException("Day-of-week out of range: " + i5);
            }
        }
        return this.tz.getOffset(PlainDate.of(i2, i3 + 1, i4), PlainTime.midnightAtStartOfDay().plus(i6, ClockUnit.MILLIS)).getIntegralAmount() * 1000;
    }

    @Override // java.util.TimeZone
    public int getRawOffset() {
        return this.tz.getStandardOffset(SystemClock.currentMoment()).getIntegralAmount() * 1000;
    }

    @Override // java.util.TimeZone
    public int getDSTSavings() {
        int i;
        TransitionHistory history = this.tz.getHistory();
        if (history != null) {
            List<ZonalTransition> stdTransitions = history.getStdTransitions();
            i = Integer.MIN_VALUE;
            for (int size = stdTransitions.size() - 1; size >= 0; size--) {
                int daylightSavingOffset = stdTransitions.get(size).getDaylightSavingOffset();
                if (i == Integer.MIN_VALUE) {
                    i = daylightSavingOffset;
                } else if (i != daylightSavingOffset) {
                    return Math.max(i, daylightSavingOffset) * 1000;
                }
            }
        } else {
            i = Integer.MIN_VALUE;
        }
        if (i == Integer.MIN_VALUE) {
            return 0;
        }
        return i * 1000;
    }

    @Override // java.util.TimeZone
    public boolean useDaylightTime() {
        TransitionHistory history;
        if (this.tz.isFixed() || (history = this.tz.getHistory()) == null) {
            return false;
        }
        List<ZonalTransition> stdTransitions = history.getStdTransitions();
        int i = Integer.MIN_VALUE;
        for (int size = stdTransitions.size() - 1; size >= 0; size--) {
            int daylightSavingOffset = stdTransitions.get(size).getDaylightSavingOffset();
            if (i == Integer.MIN_VALUE) {
                i = daylightSavingOffset;
            } else if (i != daylightSavingOffset) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.TimeZone
    public boolean inDaylightTime(Date date) {
        return this.tz.isDaylightSaving(TemporalType.JAVA_UTIL_DATE.translate(date));
    }

    @Override // java.util.TimeZone
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (timeZone instanceof OldApiTimezone) {
            return this.tz.getHistory().equals(((OldApiTimezone) timeZone).tz.getHistory());
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof OldApiTimezone) {
            return this.tz.equals(((OldApiTimezone) obj).tz);
        }
        return false;
    }

    public int hashCode() {
        return this.tz.hashCode();
    }

    public String toString() {
        return getClass().getName() + "@" + this.tz.toString();
    }

    Timezone getDelegate() {
        return this.tz;
    }
}
