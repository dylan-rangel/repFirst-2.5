package net.time4j.tz.model;

import net.time4j.ClockUnit;
import net.time4j.DayCycles;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.base.GregorianDate;
import net.time4j.format.CalendarType;

/* loaded from: classes3.dex */
public abstract class DaylightSavingRule {
    private final transient long dayOverflow;
    private final transient OffsetIndicator indicator;
    private final transient int savings;
    private final transient PlainTime timeOfDay;

    public abstract PlainDate getDate(int i);

    int getType() {
        return 0;
    }

    protected abstract int toCalendarYear(long j);

    protected abstract int toCalendarYear(GregorianDate gregorianDate);

    protected DaylightSavingRule(int i, OffsetIndicator offsetIndicator, int i2) {
        if (offsetIndicator == null) {
            throw new NullPointerException("Missing offset indicator.");
        }
        if (i2 != Integer.MAX_VALUE && (i2 < -64800 || i2 > 64800)) {
            throw new IllegalArgumentException("DST out of range: " + i2);
        }
        if (i == 86400) {
            this.dayOverflow = 0L;
            this.timeOfDay = PlainTime.midnightAtEndOfDay();
        } else {
            DayCycles roll = PlainTime.midnightAtStartOfDay().roll(i, ClockUnit.SECONDS);
            this.dayOverflow = roll.getDayOverflow();
            this.timeOfDay = roll.getWallTime();
        }
        this.indicator = offsetIndicator;
        this.savings = i2 == Integer.MAX_VALUE ? 0 : i2;
    }

    public final PlainTime getTimeOfDay() {
        return this.timeOfDay;
    }

    public final OffsetIndicator getIndicator() {
        return this.indicator;
    }

    public final int getSavings() {
        return this.savings;
    }

    protected String getCalendarType() {
        CalendarType calendarType = (CalendarType) getClass().getAnnotation(CalendarType.class);
        if (calendarType == null) {
            throw new IllegalStateException("Cannot find calendar type annotation: " + getClass());
        }
        return calendarType.value();
    }

    protected final long getDayOverflow() {
        return this.dayOverflow;
    }
}
