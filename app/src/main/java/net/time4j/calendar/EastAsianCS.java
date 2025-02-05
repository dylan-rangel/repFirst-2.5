package net.time4j.calendar;

import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.base.MathUtils;
import net.time4j.calendar.EastAsianCalendar;
import net.time4j.calendar.astro.AstronomicalSeason;
import net.time4j.calendar.astro.JulianDay;
import net.time4j.calendar.astro.MoonPhase;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.EpochDays;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
abstract class EastAsianCS<D extends EastAsianCalendar<?, D>> implements CalendarSystem<D> {
    static final double MEAN_SYNODIC_MONTH = 29.530588861d;
    static final double MEAN_TROPICAL_YEAR = 365.242189d;
    private static final long CALENDAR_REFORM_1645 = PlainDate.of(1645, 1, 28).getDaysSinceEpochUTC();
    private static final long MAX_LIMIT = PlainDate.of(3000, 1, 27).getDaysSinceEpochUTC();
    private static final long EPOCH_CHINESE = PlainDate.of(-2636, 2, 15).getDaysSinceEpochUTC();

    abstract D create(int i, int i2, EastAsianMonth eastAsianMonth, int i3, long j);

    abstract int[] getLeapMonths();

    abstract ZonalOffset getOffset(long j);

    EastAsianCS() {
    }

    @Override // net.time4j.engine.CalendarSystem
    public final D transform(long j) {
        long winterOnOrBefore = winterOnOrBefore(j);
        long winterOnOrBefore2 = winterOnOrBefore(370 + winterOnOrBefore);
        long newMoonOnOrAfter = newMoonOnOrAfter(winterOnOrBefore + 1);
        long newMoonBefore = newMoonBefore(winterOnOrBefore2 + 1);
        long newMoonBefore2 = newMoonBefore(j + 1);
        boolean z = lunations(newMoonOnOrAfter, newMoonBefore) == 12;
        long lunations = lunations(newMoonOnOrAfter, newMoonBefore2);
        if (z && hasLeapMonth(newMoonOnOrAfter, newMoonBefore2)) {
            lunations--;
        }
        int floorModulo = MathUtils.floorModulo(lunations, 12);
        int i = floorModulo != 0 ? floorModulo : 12;
        long floor = (long) Math.floor((1.5d - (i / 12.0d)) + ((j - EPOCH_CHINESE) / MEAN_TROPICAL_YEAR));
        int floorDivide = 1 + ((int) MathUtils.floorDivide(floor - 1, 60));
        int floorModulo2 = MathUtils.floorModulo(floor, 60);
        int i2 = floorModulo2 != 0 ? floorModulo2 : 60;
        int i3 = (int) ((j - newMoonBefore2) + 1);
        EastAsianMonth valueOf = EastAsianMonth.valueOf(i);
        if (z && hasNoMajorSolarTerm(newMoonBefore2) && !hasLeapMonth(newMoonOnOrAfter, newMoonBefore(newMoonBefore2))) {
            valueOf = valueOf.withLeap();
        }
        return create(floorDivide, i2, valueOf, i3, j);
    }

    @Override // net.time4j.engine.CalendarSystem
    public final long transform(D d) {
        return transform(d.getCycle(), d.getYear().getNumber(), d.getMonth(), d.getDayOfMonth());
    }

    @Override // net.time4j.engine.CalendarSystem
    public long getMinimumSinceUTC() {
        return CALENDAR_REFORM_1645;
    }

    @Override // net.time4j.engine.CalendarSystem
    public final long getMaximumSinceUTC() {
        return MAX_LIMIT;
    }

    final int getLeapMonth(int i, int i2) {
        int[] leapMonths = getLeapMonths();
        int i3 = (((i - 1) * 60) + i2) - 1;
        int i4 = ((i3 - leapMonths[0]) / 3) * 2;
        while (i4 < leapMonths.length) {
            int i5 = leapMonths[i4];
            if (i5 >= i3) {
                if (i5 > i3) {
                    return 0;
                }
                return leapMonths[i4 + 1];
            }
            i4 += Math.max(((i3 - i5) / 3) * 2, 2);
        }
        return 0;
    }

    final long transform(int i, int i2, EastAsianMonth eastAsianMonth, int i3) {
        if (!isValid(i, i2, eastAsianMonth, i3)) {
            throw new IllegalArgumentException("Invalid date.");
        }
        return (firstDayOfMonth(i, i2, eastAsianMonth) + i3) - 1;
    }

    boolean isValid(int i, int i2, EastAsianMonth eastAsianMonth, int i3) {
        if (i < 72 || i > 94 || i2 < 1 || i2 > 60 || ((i == 72 && i2 < 22) || ((i == 94 && i2 > 56) || i3 < 1 || i3 > 30 || eastAsianMonth == null || (eastAsianMonth.isLeap() && eastAsianMonth.getNumber() != getLeapMonth(i, i2))))) {
            return false;
        }
        if (i3 != 30) {
            return true;
        }
        long firstDayOfMonth = firstDayOfMonth(i, i2, eastAsianMonth);
        return newMoonOnOrAfter(1 + firstDayOfMonth) - firstDayOfMonth == 30;
    }

    final long newYear(int i, int i2) {
        return newYearOnOrBefore((long) Math.floor(EPOCH_CHINESE + (((((i - 1) * 60) + i2) - 0.5d) * MEAN_TROPICAL_YEAR)));
    }

    final int getMajorSolarTerm(long j) {
        int floor = (((int) Math.floor(SolarTerm.solarLongitude(JulianDay.ofEphemerisTime(midnight(j)).getValue()) / 30.0d)) + 2) % 12;
        if (floor == 0) {
            return 12;
        }
        return floor;
    }

    final boolean hasNoMajorSolarTerm(long j) {
        return (((int) Math.floor(SolarTerm.solarLongitude(JulianDay.ofEphemerisTime(midnight(j)).getValue()) / 30.0d)) + 2) % 12 == (((int) Math.floor(SolarTerm.solarLongitude(JulianDay.ofEphemerisTime(midnight(newMoonOnOrAfter(j + 1))).getValue()) / 30.0d)) + 2) % 12;
    }

    final long newMoonOnOrAfter(long j) {
        return MoonPhase.NEW_MOON.atOrAfter(midnight(j)).toZonalTimestamp(getOffset(j)).toDate().getDaysSinceEpochUTC();
    }

    Moment midnight(long j) {
        return PlainDate.of(j, EpochDays.UTC).atStartOfDay().at(getOffset(j));
    }

    private long newMoonBefore(long j) {
        return MoonPhase.NEW_MOON.before(midnight(j)).toZonalTimestamp(getOffset(j)).toDate().getDaysSinceEpochUTC();
    }

    private static long lunations(long j, long j2) {
        return Math.round((j2 - j) / MEAN_SYNODIC_MONTH);
    }

    private long newYearInSui(long j) {
        long winterOnOrBefore = winterOnOrBefore(j);
        long winterOnOrBefore2 = winterOnOrBefore(370 + winterOnOrBefore);
        long newMoonOnOrAfter = newMoonOnOrAfter(winterOnOrBefore + 1);
        long newMoonOnOrAfter2 = newMoonOnOrAfter(newMoonOnOrAfter + 1);
        return (lunations(newMoonOnOrAfter, newMoonBefore(winterOnOrBefore2 + 1)) == 12 && (hasNoMajorSolarTerm(newMoonOnOrAfter) || hasNoMajorSolarTerm(newMoonOnOrAfter2))) ? newMoonOnOrAfter(newMoonOnOrAfter2 + 1) : newMoonOnOrAfter2;
    }

    private long newYearOnOrBefore(long j) {
        long newYearInSui = newYearInSui(j);
        return j >= newYearInSui ? newYearInSui : newYearInSui(j - 180);
    }

    private boolean hasLeapMonth(long j, long j2) {
        return j2 >= j && (hasNoMajorSolarTerm(j2) || hasLeapMonth(j, newMoonBefore(j2)));
    }

    private long firstDayOfMonth(int i, int i2, EastAsianMonth eastAsianMonth) {
        long newMoonOnOrAfter = newMoonOnOrAfter(newYear(i, i2) + ((eastAsianMonth.getNumber() - 1) * 29));
        return eastAsianMonth.equals(transform(newMoonOnOrAfter).getMonth()) ? newMoonOnOrAfter : newMoonOnOrAfter(newMoonOnOrAfter + 1);
    }

    private long winterOnOrBefore(long j) {
        ZonalOffset offset = getOffset(j);
        PlainDate of = PlainDate.of(j, EpochDays.UTC);
        int year = (of.getMonth() <= 11 || of.getDayOfMonth() <= 15) ? of.getYear() - 1 : of.getYear();
        PlainDate calendarDate = AstronomicalSeason.WINTER_SOLSTICE.inYear(year).toZonalTimestamp(offset).getCalendarDate();
        if (calendarDate.isAfter((CalendarDate) of)) {
            calendarDate = AstronomicalSeason.WINTER_SOLSTICE.inYear(year - 1).toZonalTimestamp(offset).getCalendarDate();
        }
        return calendarDate.getDaysSinceEpochUTC();
    }
}
