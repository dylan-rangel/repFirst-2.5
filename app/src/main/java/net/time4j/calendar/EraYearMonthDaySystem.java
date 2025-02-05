package net.time4j.calendar;

import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarSystem;

/* loaded from: classes3.dex */
interface EraYearMonthDaySystem<D> extends CalendarSystem<D> {
    int getLengthOfMonth(CalendarEra calendarEra, int i, int i2);

    int getLengthOfYear(CalendarEra calendarEra, int i);

    boolean isValid(CalendarEra calendarEra, int i, int i2, int i3);
}
