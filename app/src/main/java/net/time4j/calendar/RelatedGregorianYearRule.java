package net.time4j.calendar;

import java.util.Collections;
import java.util.Map;
import net.time4j.base.MathUtils;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;

/* loaded from: classes3.dex */
final class RelatedGregorianYearRule<T extends ChronoEntity<T>> implements ElementRule<T, Integer> {
    private static final String KEY_CALENDRICAL = "calendrical";
    private final ChronoElement<Integer> dayOfYear;
    private final Map<String, ? extends CalendarSystem<T>> map;

    @Override // net.time4j.engine.ElementRule
    public ChronoElement<?> getChildAtCeiling(T t) {
        return null;
    }

    @Override // net.time4j.engine.ElementRule
    public ChronoElement<?> getChildAtFloor(T t) {
        return null;
    }

    RelatedGregorianYearRule(CalendarSystem<T> calendarSystem, ChronoElement<Integer> chronoElement) {
        this.map = Collections.singletonMap(KEY_CALENDRICAL, calendarSystem);
        this.dayOfYear = chronoElement;
    }

    RelatedGregorianYearRule(Map<String, ? extends CalendarSystem<T>> map, ChronoElement<Integer> chronoElement) {
        this.map = map;
        this.dayOfYear = chronoElement;
    }

    @Override // net.time4j.engine.ElementRule
    public Integer getValue(T t) {
        return toGregorianYear(getCalendarSystem(t).transform((CalendarSystem<T>) t.with(this.dayOfYear, 1)));
    }

    @Override // net.time4j.engine.ElementRule
    public Integer getMinimum(T t) {
        CalendarSystem<T> calendarSystem = getCalendarSystem(t);
        return toGregorianYear(calendarSystem.transform((CalendarSystem<T>) ((ChronoEntity) calendarSystem.transform(calendarSystem.getMinimumSinceUTC())).with(this.dayOfYear, 1)));
    }

    @Override // net.time4j.engine.ElementRule
    public Integer getMaximum(T t) {
        CalendarSystem<T> calendarSystem = getCalendarSystem(t);
        return toGregorianYear(calendarSystem.transform((CalendarSystem<T>) ((ChronoEntity) calendarSystem.transform(calendarSystem.getMaximumSinceUTC())).with(this.dayOfYear, 1)));
    }

    @Override // net.time4j.engine.ElementRule
    /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
    public boolean isValid2(T t, Integer num) {
        return getValue((RelatedGregorianYearRule<T>) t).equals(num);
    }

    @Override // net.time4j.engine.ElementRule
    /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
    public T withValue2(T t, Integer num, boolean z) {
        if (isValid2((RelatedGregorianYearRule<T>) t, num)) {
            return t;
        }
        throw new IllegalArgumentException("The related gregorian year is read-only.");
    }

    private CalendarSystem<T> getCalendarSystem(T t) {
        if (t instanceof CalendarVariant) {
            return this.map.get(((CalendarVariant) CalendarVariant.class.cast(t)).getVariant());
        }
        return this.map.get(KEY_CALENDRICAL);
    }

    private static Integer toGregorianYear(long j) {
        long j2;
        long safeAdd = MathUtils.safeAdd(EpochDays.MODIFIED_JULIAN_DATE.transform(j, EpochDays.UTC), 678881L);
        long floorDivide = MathUtils.floorDivide(safeAdd, 146097);
        int floorModulo = MathUtils.floorModulo(safeAdd, 146097);
        if (floorModulo == 146096) {
            j2 = (floorDivide + 1) * 400;
        } else {
            int i = floorModulo / 36524;
            int i2 = floorModulo % 36524;
            int i3 = i2 / 1461;
            int i4 = i2 % 1461;
            if (i4 == 1460) {
                j2 = (floorDivide * 400) + (i * 100) + ((i3 + 1) * 4);
            } else {
                j2 = (floorDivide * 400) + (i * 100) + (i3 * 4) + (i4 / 365);
                if (((((i4 % 365) + 31) * 5) / 153) + 2 > 12) {
                    j2++;
                }
            }
        }
        return Integer.valueOf(MathUtils.safeCast(j2));
    }
}
