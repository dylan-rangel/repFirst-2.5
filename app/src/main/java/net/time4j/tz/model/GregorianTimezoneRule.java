package net.time4j.tz.model;

import java.io.Serializable;
import net.time4j.CalendarUnit;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.Weekday;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;

@CalendarType(CalendarText.ISO_CALENDAR_TYPE)
/* loaded from: classes3.dex */
public class GregorianTimezoneRule extends DaylightSavingRule implements Serializable {
    private static final long serialVersionUID = 1;
    private final transient byte month;

    @Override // net.time4j.tz.model.DaylightSavingRule
    protected String getCalendarType() {
        return CalendarText.ISO_CALENDAR_TYPE;
    }

    protected GregorianTimezoneRule(Month month, int i, OffsetIndicator offsetIndicator, int i2) {
        super(i, offsetIndicator, i2);
        this.month = (byte) month.getValue();
    }

    public static GregorianTimezoneRule ofFixedDay(Month month, int i, PlainTime plainTime, OffsetIndicator offsetIndicator, int i2) {
        return ofFixedDay(month, i, plainTime.getInt(PlainTime.SECOND_OF_DAY), offsetIndicator, i2);
    }

    public static GregorianTimezoneRule ofFixedDay(Month month, int i, int i2, OffsetIndicator offsetIndicator, int i3) {
        return new FixedDayPattern(month, i, i2, offsetIndicator, i3);
    }

    public static GregorianTimezoneRule ofLastWeekday(Month month, Weekday weekday, PlainTime plainTime, OffsetIndicator offsetIndicator, int i) {
        return ofLastWeekday(month, weekday, plainTime.getInt(PlainTime.SECOND_OF_DAY), offsetIndicator, i);
    }

    public static GregorianTimezoneRule ofLastWeekday(Month month, Weekday weekday, int i, OffsetIndicator offsetIndicator, int i2) {
        return new LastWeekdayPattern(month, weekday, i, offsetIndicator, i2);
    }

    public static GregorianTimezoneRule ofWeekdayAfterDate(Month month, int i, Weekday weekday, PlainTime plainTime, OffsetIndicator offsetIndicator, int i2) {
        return ofWeekdayAfterDate(month, i, weekday, plainTime.getInt(PlainTime.SECOND_OF_DAY), offsetIndicator, i2);
    }

    public static GregorianTimezoneRule ofWeekdayAfterDate(Month month, int i, Weekday weekday, int i2, OffsetIndicator offsetIndicator, int i3) {
        return new DayOfWeekInMonthPattern(month, i, weekday, i2, offsetIndicator, i3, true);
    }

    public static GregorianTimezoneRule ofWeekdayBeforeDate(Month month, int i, Weekday weekday, PlainTime plainTime, OffsetIndicator offsetIndicator, int i2) {
        return ofWeekdayBeforeDate(month, i, weekday, plainTime.getInt(PlainTime.SECOND_OF_DAY), offsetIndicator, i2);
    }

    public static GregorianTimezoneRule ofWeekdayBeforeDate(Month month, int i, Weekday weekday, int i2, OffsetIndicator offsetIndicator, int i3) {
        return new DayOfWeekInMonthPattern(month, i, weekday, i2, offsetIndicator, i3, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.tz.model.DaylightSavingRule
    public final PlainDate getDate(int i) {
        return (PlainDate) getDate0(i).plus(getDayOverflow(), CalendarUnit.DAYS);
    }

    public Month getMonth() {
        return Month.valueOf(this.month);
    }

    protected PlainDate getDate0(int i) {
        throw new AbstractMethodError("Implemented by subclasses.");
    }

    @Override // net.time4j.tz.model.DaylightSavingRule
    protected int toCalendarYear(long j) {
        return GregorianMath.readYear(GregorianMath.toPackedDate(j));
    }

    @Override // net.time4j.tz.model.DaylightSavingRule
    protected int toCalendarYear(GregorianDate gregorianDate) {
        return gregorianDate.getYear();
    }

    protected boolean isEqual(GregorianTimezoneRule gregorianTimezoneRule) {
        return getTimeOfDay().equals(gregorianTimezoneRule.getTimeOfDay()) && getDayOverflow() == gregorianTimezoneRule.getDayOverflow() && getIndicator() == gregorianTimezoneRule.getIndicator() && getSavings() == gregorianTimezoneRule.getSavings() && this.month == gregorianTimezoneRule.month;
    }

    byte getMonthValue() {
        return this.month;
    }
}
