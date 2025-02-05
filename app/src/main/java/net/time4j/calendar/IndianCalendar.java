package net.time4j.calendar;

import androidx.exifinterface.media.ExifInterface;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.GregorianMath;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.service.GenericDatePatterns;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.Calendrical;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.IntElementRule;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.TimeAxis;
import net.time4j.engine.UnitRule;
import net.time4j.engine.ValidationElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("indian")
/* loaded from: classes3.dex */
public final class IndianCalendar extends Calendrical<Unit, IndianCalendar> implements LocalizedPatternSupport {
    private static final EraYearMonthDaySystem<IndianCalendar> CALSYS;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, IndianCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, IndianCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, IndianCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final TimeAxis<Unit, IndianCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<IndianEra> ERA;
    private static final int MAX_YEAR = 999999921;

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<IndianMonth, IndianCalendar> MONTH_OF_YEAR;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<IndianCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<IndianCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, IndianCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = 7482205842000661998L;
    private final transient int idom;
    private final transient int imonth;
    private final transient int iyear;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public IndianCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", IndianCalendar.class, IndianEra.class, 'G');
        ERA = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("YEAR_OF_ERA", IndianCalendar.class, 1, MAX_YEAR, 'y', null, null);
        YEAR_OF_ERA = stdIntegerDateElement;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", IndianCalendar.class, IndianMonth.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", IndianCalendar.class, 1, 31, 'd');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", IndianCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(IndianCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<IndianCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(IndianCalendar.class, stdIntegerDateElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, IndianCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new EraRule()).appendElement(stdIntegerDateElement, new IntegerRule(0), Unit.YEARS).appendElement(stdEnumDateElement2, new MonthRule(), Unit.MONTHS).appendElement(stdIntegerDateElement2, new IntegerRule(2), Unit.DAYS).appendElement(stdIntegerDateElement3, new IntegerRule(3), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<IndianCalendar, CalendarSystem<IndianCalendar>>() { // from class: net.time4j.calendar.IndianCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<IndianCalendar> apply(IndianCalendar indianCalendar) {
                return IndianCalendar.CALSYS;
            }
        }), Unit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement3)).appendUnit(Unit.YEARS, new IndianUnitRule(Unit.YEARS), Unit.YEARS.getLength(), Collections.singleton(Unit.MONTHS)).appendUnit(Unit.MONTHS, new IndianUnitRule(Unit.MONTHS), Unit.MONTHS.getLength(), Collections.singleton(Unit.YEARS)).appendUnit(Unit.WEEKS, new IndianUnitRule(Unit.WEEKS), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, new IndianUnitRule(Unit.DAYS), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).appendExtension((ChronoExtension) new CommonElements.Weekengine(IndianCalendar.class, stdIntegerDateElement2, stdIntegerDateElement3, getDefaultWeekmodel())).build();
    }

    private IndianCalendar(int i, int i2, int i3) {
        this.iyear = i;
        this.imonth = i2;
        this.idom = i3;
    }

    public static IndianCalendar of(int i, IndianMonth indianMonth, int i2) {
        return of(i, indianMonth.getValue(), i2);
    }

    public static IndianCalendar of(int i, int i2, int i3) {
        if (!CALSYS.isValid(IndianEra.SAKA, i, i2, i3)) {
            throw new IllegalArgumentException("Invalid Indian date: year=" + i + ", month=" + i2 + ", day=" + i3);
        }
        return new IndianCalendar(i, i2, i3);
    }

    public static IndianCalendar nowInSystemTime() {
        return (IndianCalendar) SystemClock.inLocalView().now(axis());
    }

    public IndianEra getEra() {
        return IndianEra.SAKA;
    }

    public int getYear() {
        return this.iyear;
    }

    public IndianMonth getMonth() {
        return IndianMonth.valueOf(this.imonth);
    }

    public int getDayOfMonth() {
        return this.idom;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(CALSYS.transform((EraYearMonthDaySystem<IndianCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    public int lengthOfMonth() {
        return CALSYS.getLengthOfMonth(IndianEra.SAKA, this.iyear, this.imonth);
    }

    public int lengthOfYear() {
        return isLeapYear() ? 366 : 365;
    }

    public boolean isLeapYear() {
        return GregorianMath.isLeapYear(this.iyear + 78);
    }

    public static boolean isValid(int i, int i2, int i3) {
        return CALSYS.isValid(IndianEra.SAKA, i, i2, i3);
    }

    public GeneralTimestamp<IndianCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<IndianCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndianCalendar)) {
            return false;
        }
        IndianCalendar indianCalendar = (IndianCalendar) obj;
        return this.idom == indianCalendar.idom && this.imonth == indianCalendar.imonth && this.iyear == indianCalendar.iyear;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.idom * 17) + (this.imonth * 31) + (this.iyear * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("Saka-");
        String valueOf = String.valueOf(this.iyear);
        for (int length = valueOf.length(); length < 4; length++) {
            sb.append('0');
        }
        sb.append(valueOf);
        sb.append('-');
        if (this.imonth < 10) {
            sb.append('0');
        }
        sb.append(this.imonth);
        sb.append('-');
        if (this.idom < 10) {
            sb.append('0');
        }
        sb.append(this.idom);
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1, Weekday.SUNDAY, Weekday.SUNDAY);
    }

    public static TimeAxis<Unit, IndianCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, IndianCalendar> getChronology() {
        return ENGINE;
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.1556952E7d),
        MONTHS(2629746.0d),
        WEEKS(604800.0d),
        DAYS(86400.0d);

        private final transient double length;

        @Override // net.time4j.engine.ChronoUnit
        public boolean isCalendrical() {
            return true;
        }

        Unit(double d) {
            this.length = d;
        }

        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return this.length;
        }

        public long between(IndianCalendar indianCalendar, IndianCalendar indianCalendar2) {
            return indianCalendar.until(indianCalendar2, (IndianCalendar) this);
        }
    }

    private static class Transformer implements EraYearMonthDaySystem<IndianCalendar> {
        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            if (calendarEra == IndianEra.SAKA && i >= 1 && i <= IndianCalendar.MAX_YEAR && i2 >= 1) {
                if (i2 <= (i == IndianCalendar.MAX_YEAR ? 10 : 12) && i3 >= 1 && i3 <= getLengthOfMonth(calendarEra, i, i2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
            if (calendarEra != IndianEra.SAKA) {
                throw new IllegalArgumentException("Invalid era: " + calendarEra);
            }
            if (i >= 1 && i <= IndianCalendar.MAX_YEAR && i2 >= 1) {
                if (i == IndianCalendar.MAX_YEAR && i2 == 10) {
                    return 10;
                }
                if (i2 == 1) {
                    return GregorianMath.isLeapYear(i + 78) ? 31 : 30;
                }
                if (i2 <= 6) {
                    return 31;
                }
                if (i2 <= 12) {
                    return 30;
                }
            }
            throw new IllegalArgumentException("Out of bounds: year=" + i + ", month=" + i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            if (calendarEra != IndianEra.SAKA) {
                throw new IllegalArgumentException("Invalid era: " + calendarEra);
            }
            if (i >= 1 && i < IndianCalendar.MAX_YEAR) {
                return GregorianMath.isLeapYear(i + 78) ? 366 : 365;
            }
            if (i == IndianCalendar.MAX_YEAR) {
                return 285;
            }
            throw new IllegalArgumentException("Out of bounds: year=" + i);
        }

        @Override // net.time4j.engine.CalendarSystem
        public IndianCalendar transform(long j) {
            int i;
            PlainDate of = PlainDate.of(j, EpochDays.UTC);
            int year = of.getYear();
            int month = of.getMonth();
            int dayOfMonth = of.getDayOfMonth();
            boolean isLeapYear = GregorianMath.isLeapYear(year);
            int i2 = isLeapYear ? 21 : 22;
            int year2 = of.getYear() - 78;
            if (month != 12 || dayOfMonth < 22) {
                if (month == 12) {
                    i = dayOfMonth + 9;
                } else if (month != 11 || dayOfMonth < 22) {
                    if (month == 11) {
                        i = dayOfMonth + 9;
                    } else if (month != 10 || dayOfMonth < 23) {
                        if (month == 10) {
                            i = dayOfMonth + 8;
                        } else if (month != 9 || dayOfMonth < 23) {
                            if (month == 9) {
                                i = dayOfMonth + 9;
                            } else if (month != 8 || dayOfMonth < 23) {
                                if (month == 8) {
                                    i = dayOfMonth + 9;
                                } else if (month != 7 || dayOfMonth < 23) {
                                    if (month == 7) {
                                        i = dayOfMonth + 9;
                                    } else if (month != 6 || dayOfMonth < 22) {
                                        if (month == 6) {
                                            i = dayOfMonth + 10;
                                        } else if (month != 5 || dayOfMonth < 22) {
                                            if (month == 5) {
                                                i = dayOfMonth + 10;
                                            } else if (month != 4 || dayOfMonth < 21) {
                                                if (month == 4) {
                                                    i = dayOfMonth + (isLeapYear ? 11 : 10);
                                                } else if (month != 3 || dayOfMonth < i2) {
                                                    if (month == 3) {
                                                        year2--;
                                                        i = dayOfMonth + (isLeapYear ? 10 : 9);
                                                    } else if (month != 2 || dayOfMonth < 20) {
                                                        if (month == 2) {
                                                            year2--;
                                                            i = dayOfMonth + 11;
                                                        } else if (month != 1 || dayOfMonth < 21) {
                                                            year2--;
                                                            i = dayOfMonth + 10;
                                                        } else {
                                                            year2--;
                                                            i = dayOfMonth - 20;
                                                        }
                                                        r7 = 11;
                                                    } else {
                                                        year2--;
                                                        i = dayOfMonth - 19;
                                                    }
                                                    r7 = 12;
                                                } else {
                                                    i = (dayOfMonth - i2) + 1;
                                                }
                                                r7 = 1;
                                            } else {
                                                i = dayOfMonth - 20;
                                            }
                                            r7 = 2;
                                        } else {
                                            i = dayOfMonth - 21;
                                        }
                                        r7 = 3;
                                    } else {
                                        i = dayOfMonth - 21;
                                    }
                                    r7 = 4;
                                } else {
                                    i = dayOfMonth - 22;
                                }
                                r7 = 5;
                            } else {
                                i = dayOfMonth - 22;
                            }
                            r7 = 6;
                        } else {
                            i = dayOfMonth - 22;
                        }
                        r7 = 7;
                    } else {
                        i = dayOfMonth - 22;
                    }
                    r7 = 8;
                } else {
                    i = dayOfMonth - 21;
                }
                r7 = 9;
            } else {
                i = dayOfMonth - 21;
            }
            return IndianCalendar.of(year2, r7, i);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(IndianCalendar indianCalendar) {
            int i = indianCalendar.iyear + 78;
            boolean isLeapYear = GregorianMath.isLeapYear(i);
            long longValue = ((Long) PlainDate.of(i, 3, isLeapYear ? 21 : 22).get(EpochDays.UTC)).longValue();
            int i2 = 0;
            for (int i3 = 1; i3 < indianCalendar.imonth; i3++) {
                switch (i3) {
                    case 1:
                        i2 += isLeapYear ? 31 : 30;
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        i2 += 31;
                        break;
                    default:
                        i2 += 30;
                        break;
                }
            }
            return longValue + i2 + (indianCalendar.idom - 1);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            int i = 1;
            return transform(new IndianCalendar(i, i, i));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            int i = 10;
            return transform(new IndianCalendar(IndianCalendar.MAX_YEAR, i, i));
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.singletonList(IndianEra.SAKA);
        }
    }

    private static class IntegerRule implements IntElementRule<IndianCalendar> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.IntElementRule
        public int getInt(IndianCalendar indianCalendar) {
            int i = this.index;
            if (i == 0) {
                return indianCalendar.iyear;
            }
            if (i == 2) {
                return indianCalendar.idom;
            }
            if (i == 3) {
                int i2 = 0;
                for (int i3 = 1; i3 < indianCalendar.imonth; i3++) {
                    i2 += IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, indianCalendar.iyear, i3);
                }
                return i2 + indianCalendar.idom;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(IndianCalendar indianCalendar, int i) {
            return getMin() <= i && getMax(indianCalendar) >= i;
        }

        @Override // net.time4j.engine.IntElementRule
        public IndianCalendar withValue(IndianCalendar indianCalendar, int i, boolean z) {
            if (!isValid(indianCalendar, i)) {
                throw new IllegalArgumentException("Out of range: " + i);
            }
            int i2 = this.index;
            if (i2 == 0) {
                return new IndianCalendar(i, indianCalendar.imonth, Math.min(indianCalendar.idom, IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, i, indianCalendar.imonth)));
            }
            if (i2 == 2) {
                return new IndianCalendar(indianCalendar.iyear, indianCalendar.imonth, i);
            }
            if (i2 == 3) {
                return indianCalendar.plus(CalendarDays.of(i - getValue(indianCalendar).intValue()));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(IndianCalendar indianCalendar) {
            return Integer.valueOf(getInt(indianCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(IndianCalendar indianCalendar) {
            return Integer.valueOf(getMin());
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(IndianCalendar indianCalendar) {
            return Integer.valueOf(getMax(indianCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(IndianCalendar indianCalendar, Integer num) {
            return num != null && isValid(indianCalendar, num.intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public IndianCalendar withValue2(IndianCalendar indianCalendar, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing new value.");
            }
            return withValue(indianCalendar, num.intValue(), z);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(IndianCalendar indianCalendar) {
            if (this.index == 0) {
                return IndianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(IndianCalendar indianCalendar) {
            if (this.index == 0) {
                return IndianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        private int getMin() {
            int i = this.index;
            if (i == 0 || i == 2 || i == 3) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        private int getMax(IndianCalendar indianCalendar) {
            int i = this.index;
            if (i == 0) {
                return IndianCalendar.MAX_YEAR;
            }
            if (i == 2) {
                return IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, indianCalendar.iyear, indianCalendar.imonth);
            }
            if (i == 3) {
                return IndianCalendar.CALSYS.getLengthOfYear(IndianEra.SAKA, indianCalendar.iyear);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }
    }

    private static class MonthRule implements ElementRule<IndianCalendar, IndianMonth> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(IndianCalendar indianCalendar, IndianMonth indianMonth) {
            return indianMonth != null;
        }

        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public IndianMonth getValue(IndianCalendar indianCalendar) {
            return indianCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public IndianMonth getMinimum(IndianCalendar indianCalendar) {
            return IndianMonth.CHAITRA;
        }

        @Override // net.time4j.engine.ElementRule
        public IndianMonth getMaximum(IndianCalendar indianCalendar) {
            return IndianMonth.PHALGUNA;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public IndianCalendar withValue2(IndianCalendar indianCalendar, IndianMonth indianMonth, boolean z) {
            if (indianMonth == null) {
                throw new IllegalArgumentException("Missing month.");
            }
            int value = indianMonth.getValue();
            return new IndianCalendar(indianCalendar.iyear, value, Math.min(indianCalendar.idom, IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, indianCalendar.iyear, value)));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(IndianCalendar indianCalendar) {
            return IndianCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(IndianCalendar indianCalendar) {
            return IndianCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<IndianCalendar, IndianEra> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(IndianCalendar indianCalendar, IndianEra indianEra) {
            return indianEra != null;
        }

        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public IndianEra getValue(IndianCalendar indianCalendar) {
            return IndianEra.SAKA;
        }

        @Override // net.time4j.engine.ElementRule
        public IndianEra getMinimum(IndianCalendar indianCalendar) {
            return IndianEra.SAKA;
        }

        @Override // net.time4j.engine.ElementRule
        public IndianEra getMaximum(IndianCalendar indianCalendar) {
            return IndianEra.SAKA;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public IndianCalendar withValue2(IndianCalendar indianCalendar, IndianEra indianEra, boolean z) {
            if (indianEra != null) {
                return indianCalendar;
            }
            throw new IllegalArgumentException("Missing era value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(IndianCalendar indianCalendar) {
            return IndianCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(IndianCalendar indianCalendar) {
            return IndianCalendar.YEAR_OF_ERA;
        }
    }

    private static class Merger implements ChronoMerger<IndianCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(IndianCalendar indianCalendar, AttributeQuery attributeQuery) {
            return indianCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ IndianCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ IndianCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("indian", displayStyle, locale);
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public IndianCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (IndianCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(IndianCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public IndianCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i = chronoEntity.getInt(IndianCalendar.YEAR_OF_ERA);
            if (i == Integer.MIN_VALUE) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Indian year.");
                return null;
            }
            if (chronoEntity.contains(IndianCalendar.MONTH_OF_YEAR)) {
                int value = ((IndianMonth) chronoEntity.get(IndianCalendar.MONTH_OF_YEAR)).getValue();
                int i2 = chronoEntity.getInt(IndianCalendar.DAY_OF_MONTH);
                if (i2 != Integer.MIN_VALUE) {
                    if (IndianCalendar.CALSYS.isValid(IndianEra.SAKA, i, value, i2)) {
                        return IndianCalendar.of(i, value, i2);
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Indian date.");
                }
            } else {
                int i3 = chronoEntity.getInt(IndianCalendar.DAY_OF_YEAR);
                if (i3 != Integer.MIN_VALUE) {
                    if (i3 > 0) {
                        int i4 = 0;
                        int i5 = 1;
                        while (i5 <= 12) {
                            int lengthOfMonth = IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, i, i5) + i4;
                            if (i3 <= lengthOfMonth) {
                                return IndianCalendar.of(i, i5, i3 - i4);
                            }
                            i5++;
                            i4 = lengthOfMonth;
                        }
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Indian date.");
                }
            }
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 78;
        }
    }

    private static class IndianUnitRule implements UnitRule<IndianCalendar> {
        private final Unit unit;

        IndianUnitRule(Unit unit) {
            this.unit = unit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public IndianCalendar addTo(IndianCalendar indianCalendar, long j) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$IndianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                j = MathUtils.safeMultiply(j, 12L);
            } else if (i != 2) {
                if (i == 3) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else if (i != 4) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                return (IndianCalendar) IndianCalendar.CALSYS.transform(MathUtils.safeAdd(IndianCalendar.CALSYS.transform((EraYearMonthDaySystem) indianCalendar), j));
            }
            long safeAdd = MathUtils.safeAdd(ymValue(indianCalendar), j);
            int safeCast = MathUtils.safeCast(MathUtils.floorDivide(safeAdd, 12));
            int floorModulo = MathUtils.floorModulo(safeAdd, 12) + 1;
            return IndianCalendar.of(safeCast, floorModulo, Math.min(indianCalendar.idom, IndianCalendar.CALSYS.getLengthOfMonth(IndianEra.SAKA, safeCast, floorModulo)));
        }

        @Override // net.time4j.engine.UnitRule
        public long between(IndianCalendar indianCalendar, IndianCalendar indianCalendar2) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$IndianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                return Unit.MONTHS.between(indianCalendar, indianCalendar2) / 12;
            }
            if (i == 2) {
                long ymValue = ymValue(indianCalendar2) - ymValue(indianCalendar);
                return (ymValue <= 0 || indianCalendar2.idom >= indianCalendar.idom) ? (ymValue >= 0 || indianCalendar2.idom <= indianCalendar.idom) ? ymValue : ymValue + 1 : ymValue - 1;
            }
            if (i == 3) {
                return Unit.DAYS.between(indianCalendar, indianCalendar2) / 7;
            }
            if (i == 4) {
                return IndianCalendar.CALSYS.transform((EraYearMonthDaySystem) indianCalendar2) - IndianCalendar.CALSYS.transform((EraYearMonthDaySystem) indianCalendar);
            }
            throw new UnsupportedOperationException(this.unit.name());
        }

        private static int ymValue(IndianCalendar indianCalendar) {
            return ((indianCalendar.iyear * 12) + indianCalendar.imonth) - 1;
        }
    }

    /* renamed from: net.time4j.calendar.IndianCalendar$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$IndianCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$IndianCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$IndianCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$IndianCalendar$Unit[Unit.WEEKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$IndianCalendar$Unit[Unit.DAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static class SPX implements Externalizable {
        private static final int INDIAN = 10;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(10);
            writeIndian(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 10) {
                this.obj = readIndian(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeIndian(ObjectOutput objectOutput) throws IOException {
            IndianCalendar indianCalendar = (IndianCalendar) this.obj;
            objectOutput.writeInt(indianCalendar.getYear());
            objectOutput.writeByte(indianCalendar.getMonth().getValue());
            objectOutput.writeByte(indianCalendar.getDayOfMonth());
        }

        private IndianCalendar readIndian(ObjectInput objectInput) throws IOException {
            return IndianCalendar.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
        }
    }
}
