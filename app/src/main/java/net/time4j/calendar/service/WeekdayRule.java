package net.time4j.calendar.service;

import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ElementRule;

/* loaded from: classes3.dex */
public class WeekdayRule<D extends CalendarDate> implements ElementRule<D, Weekday> {
    private final Java8Function<D, CalendarSystem<D>> calsysFunc;
    private final Weekmodel stdWeekmodel;

    @Override // net.time4j.engine.ElementRule
    public ChronoElement<?> getChildAtCeiling(D d) {
        return null;
    }

    @Override // net.time4j.engine.ElementRule
    public ChronoElement<?> getChildAtFloor(D d) {
        return null;
    }

    public WeekdayRule(Weekmodel weekmodel, Java8Function<D, CalendarSystem<D>> java8Function) {
        this.stdWeekmodel = weekmodel;
        this.calsysFunc = java8Function;
    }

    @Override // net.time4j.engine.ElementRule
    public Weekday getValue(D d) {
        return getWeekday(d.getDaysSinceEpochUTC());
    }

    @Override // net.time4j.engine.ElementRule
    public Weekday getMinimum(D d) {
        CalendarSystem<D> apply = this.calsysFunc.apply(d);
        if ((d.getDaysSinceEpochUTC() + 1) - getValue((WeekdayRule<D>) d).getValue(this.stdWeekmodel) < apply.getMinimumSinceUTC()) {
            return getWeekday(apply.getMinimumSinceUTC());
        }
        return this.stdWeekmodel.getFirstDayOfWeek();
    }

    @Override // net.time4j.engine.ElementRule
    public Weekday getMaximum(D d) {
        CalendarSystem<D> apply = this.calsysFunc.apply(d);
        if ((d.getDaysSinceEpochUTC() + 7) - getValue((WeekdayRule<D>) d).getValue(this.stdWeekmodel) > apply.getMaximumSinceUTC()) {
            return getWeekday(apply.getMaximumSinceUTC());
        }
        return this.stdWeekmodel.getFirstDayOfWeek().roll(6);
    }

    @Override // net.time4j.engine.ElementRule
    /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
    public boolean isValid2(D d, Weekday weekday) {
        if (weekday == null) {
            return false;
        }
        long daysSinceEpochUTC = (d.getDaysSinceEpochUTC() + weekday.getValue(this.stdWeekmodel)) - getValue((WeekdayRule<D>) d).getValue(this.stdWeekmodel);
        CalendarSystem<D> apply = this.calsysFunc.apply(d);
        return daysSinceEpochUTC >= apply.getMinimumSinceUTC() && daysSinceEpochUTC <= apply.getMaximumSinceUTC();
    }

    @Override // net.time4j.engine.ElementRule
    /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
    public D withValue2(D d, Weekday weekday, boolean z) {
        if (weekday == null) {
            throw new IllegalArgumentException("Missing weekday.");
        }
        long daysSinceEpochUTC = (d.getDaysSinceEpochUTC() + weekday.getValue(this.stdWeekmodel)) - getValue((WeekdayRule<D>) d).getValue(this.stdWeekmodel);
        CalendarSystem<D> apply = this.calsysFunc.apply(d);
        if (daysSinceEpochUTC >= apply.getMinimumSinceUTC() && daysSinceEpochUTC <= apply.getMaximumSinceUTC()) {
            return apply.transform(daysSinceEpochUTC);
        }
        throw new IllegalArgumentException("New day out of supported range.");
    }

    private static Weekday getWeekday(long j) {
        return Weekday.valueOf(MathUtils.floorModulo(j + 5, 7) + 1);
    }
}
