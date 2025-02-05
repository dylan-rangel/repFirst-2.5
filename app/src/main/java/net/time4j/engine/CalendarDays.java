package net.time4j.engine;

import java.io.Serializable;
import net.time4j.base.MathUtils;

/* loaded from: classes3.dex */
public final class CalendarDays implements Comparable<CalendarDays>, Serializable {
    private final long days;
    public static final CalendarDays ZERO = new CalendarDays(0);
    public static final CalendarDays ONE = new CalendarDays(1);

    private CalendarDays(long j) {
        this.days = j;
    }

    public static CalendarDays of(long j) {
        if (j == 0) {
            return ZERO;
        }
        return j == 1 ? ONE : new CalendarDays(j);
    }

    public long getAmount() {
        return this.days;
    }

    public boolean isZero() {
        return this.days == 0;
    }

    public boolean isNegative() {
        return this.days < 0;
    }

    public static CalendarDays between(CalendarDate calendarDate, CalendarDate calendarDate2) {
        return of(MathUtils.safeSubtract(calendarDate2.getDaysSinceEpochUTC(), calendarDate.getDaysSinceEpochUTC()));
    }

    public CalendarDays abs() {
        return this.days < 0 ? inverse() : this;
    }

    public CalendarDays inverse() {
        return of(MathUtils.safeNegate(this.days));
    }

    public CalendarDays plus(CalendarDays calendarDays) {
        return of(MathUtils.safeAdd(this.days, calendarDays.days));
    }

    public CalendarDays minus(CalendarDays calendarDays) {
        return of(MathUtils.safeSubtract(this.days, calendarDays.days));
    }

    @Override // java.lang.Comparable
    public int compareTo(CalendarDays calendarDays) {
        long j = this.days;
        long j2 = calendarDays.days;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CalendarDays) && this.days == ((CalendarDays) obj).days;
    }

    public int hashCode() {
        long j = this.days;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.days < 0) {
            sb.append('-');
        }
        sb.append('P');
        sb.append(Math.abs(this.days));
        sb.append('D');
        return sb.toString();
    }
}
