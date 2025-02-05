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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.service.GenericDatePatterns;
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
import net.time4j.engine.FormattableElement;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.TimeAxis;
import net.time4j.engine.UnitRule;
import net.time4j.engine.ValidationElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.TextElement;
import net.time4j.history.ChronoHistory;
import net.time4j.history.HistoricDate;
import net.time4j.history.HistoricEra;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("julian")
/* loaded from: classes3.dex */
public final class JulianCalendar extends Calendrical<Unit, JulianCalendar> implements LocalizedPatternSupport {
    private static final EraYearMonthDaySystem<JulianCalendar> CALSYS;
    public static final ChronoElement<HistoricDate> DATE;

    @FormattableElement(format = "d")
    public static final ChronoElement<Integer> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final ChronoElement<Weekday> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final ChronoElement<Integer> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final TimeAxis<Unit, JulianCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<HistoricEra> ERA;

    @FormattableElement(alt = "L", format = "M")
    public static final TextElement<Integer> MONTH_OF_YEAR;
    private static final int OFFSET = 720200;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<JulianCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<JulianCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final ChronoElement<Integer> YEAR_OF_ERA;
    private static final int YMAX = 999999999;
    private static final long serialVersionUID = 3038883058279104976L;
    private final transient int dom;
    private final transient int month;
    private final transient int prolepticYear;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public JulianCalendar getContext() {
        return this;
    }

    static {
        ChronoElement<HistoricDate> date = ChronoHistory.PROLEPTIC_JULIAN.date();
        DATE = date;
        ChronoElement<HistoricEra> era = ChronoHistory.PROLEPTIC_JULIAN.era();
        ERA = era;
        TextElement<Integer> yearOfEra = ChronoHistory.PROLEPTIC_JULIAN.yearOfEra();
        YEAR_OF_ERA = yearOfEra;
        TextElement<Integer> month = ChronoHistory.PROLEPTIC_JULIAN.month();
        MONTH_OF_YEAR = month;
        ChronoElement<Integer> dayOfMonth = ChronoHistory.PROLEPTIC_JULIAN.dayOfMonth();
        DAY_OF_MONTH = dayOfMonth;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("DAY_OF_YEAR", JulianCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(JulianCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<JulianCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(JulianCalendar.class, dayOfMonth, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, JulianCalendar.class, new Merger(), transformer).appendElement((ChronoElement) date, (ElementRule) new DateRule()).appendElement((ChronoElement) era, (ElementRule) new EraRule()).appendElement(yearOfEra, new IntegerRule(0), Unit.YEARS).appendElement(month, new MonthRule(), Unit.MONTHS).appendElement(dayOfMonth, new IntegerRule(2), Unit.DAYS).appendElement(stdIntegerDateElement, new IntegerRule(3), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<JulianCalendar, CalendarSystem<JulianCalendar>>() { // from class: net.time4j.calendar.JulianCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<JulianCalendar> apply(JulianCalendar julianCalendar) {
                return JulianCalendar.CALSYS;
            }
        }), Unit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement)).appendUnit(Unit.YEARS, new JulianUnitRule(Unit.YEARS), Unit.YEARS.getLength(), Collections.singleton(Unit.MONTHS)).appendUnit(Unit.MONTHS, new JulianUnitRule(Unit.MONTHS), Unit.MONTHS.getLength(), Collections.singleton(Unit.YEARS)).appendUnit(Unit.WEEKS, new JulianUnitRule(Unit.WEEKS), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, new JulianUnitRule(Unit.DAYS), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).appendExtension((ChronoExtension) new CommonElements.Weekengine(JulianCalendar.class, dayOfMonth, stdIntegerDateElement, getDefaultWeekmodel())).build();
    }

    private JulianCalendar(int i, int i2, int i3) {
        this.prolepticYear = i;
        this.month = i2;
        this.dom = i3;
    }

    public static JulianCalendar of(HistoricEra historicEra, int i, int i2, int i3) {
        if (historicEra == null) {
            throw new NullPointerException("Missing Julian era.");
        }
        if (!CALSYS.isValid(historicEra, i, i2, i3)) {
            throw new IllegalArgumentException("Out of bounds: " + toString(historicEra, i, i2, i3));
        }
        if (historicEra == HistoricEra.AD) {
            return new JulianCalendar(i, i2, i3);
        }
        return new JulianCalendar(MathUtils.safeSubtract(1, i), i2, i3);
    }

    public static JulianCalendar of(HistoricEra historicEra, int i, Month month, int i2) {
        return of(historicEra, i, month.getValue(), i2);
    }

    public static JulianCalendar nowInSystemTime() {
        return (JulianCalendar) SystemClock.inLocalView().now(axis());
    }

    public HistoricEra getEra() {
        return this.prolepticYear >= 1 ? HistoricEra.AD : HistoricEra.BC;
    }

    public int getYear() {
        int i = this.prolepticYear;
        return i >= 1 ? i : MathUtils.safeSubtract(1, i);
    }

    public Month getMonth() {
        return Month.valueOf(this.month);
    }

    public int getDayOfMonth() {
        return this.dom;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(CALSYS.transform((EraYearMonthDaySystem<JulianCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    public int lengthOfMonth() {
        return lengthOfMonth(this.prolepticYear, this.month);
    }

    public int lengthOfYear() {
        return isLeapYear() ? 366 : 365;
    }

    public boolean isLeapYear() {
        return this.prolepticYear % 4 == 0;
    }

    public static boolean isValid(HistoricEra historicEra, int i, int i2, int i3) {
        return CALSYS.isValid(historicEra, i, i2, i3);
    }

    public GeneralTimestamp<JulianCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<JulianCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JulianCalendar)) {
            return false;
        }
        JulianCalendar julianCalendar = (JulianCalendar) obj;
        return this.dom == julianCalendar.dom && this.month == julianCalendar.month && this.prolepticYear == julianCalendar.prolepticYear;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.dom * 17) + (this.month * 31) + (this.prolepticYear * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        return toString(getEra(), getYear(), this.month, this.dom);
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1);
    }

    public static TimeAxis<Unit, JulianCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, JulianCalendar> getChronology() {
        return ENGINE;
    }

    int getProlepticYear() {
        return this.prolepticYear;
    }

    private static String toString(CalendarEra calendarEra, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder(32);
        sb.append("JULIAN-");
        sb.append(calendarEra.name());
        sb.append('-');
        String valueOf = String.valueOf(i);
        for (int length = valueOf.length(); length < 4; length++) {
            sb.append('0');
        }
        sb.append(valueOf);
        sb.append('-');
        if (i2 < 10) {
            sb.append('0');
        }
        sb.append(i2);
        sb.append('-');
        if (i3 < 10) {
            sb.append('0');
        }
        sb.append(i3);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lengthOfMonth(int i, int i2) {
        return i2 != 2 ? (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31 : i % 4 == 0 ? 29 : 28;
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.15576E7d),
        MONTHS(2629800.0d),
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

        public int between(JulianCalendar julianCalendar, JulianCalendar julianCalendar2) {
            return MathUtils.safeCast(julianCalendar.until(julianCalendar2, (JulianCalendar) this));
        }
    }

    private static class Transformer implements EraYearMonthDaySystem<JulianCalendar> {
        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            int safeSubtract;
            if (calendarEra == HistoricEra.AD) {
                safeSubtract = i;
            } else {
                if (calendarEra != HistoricEra.BC) {
                    return false;
                }
                safeSubtract = MathUtils.safeSubtract(1, i);
            }
            return i >= 1 && i <= 999999999 && i2 >= 1 && i2 <= 12 && i3 >= 1 && i3 <= JulianCalendar.lengthOfMonth(safeSubtract, i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
            int safeSubtract;
            if (calendarEra == HistoricEra.AD) {
                safeSubtract = i;
            } else if (calendarEra == HistoricEra.BC) {
                safeSubtract = MathUtils.safeSubtract(1, i);
            } else {
                throw new IllegalArgumentException("Invalid era: " + calendarEra);
            }
            if (i >= 1 && i <= 999999999 && i2 >= 1 && i2 <= 12) {
                return JulianCalendar.lengthOfMonth(safeSubtract, i2);
            }
            throw new IllegalArgumentException("Out of bounds: year=" + i + ", month=" + i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            int safeSubtract;
            if (calendarEra == HistoricEra.AD) {
                safeSubtract = i;
            } else if (calendarEra == HistoricEra.BC) {
                safeSubtract = MathUtils.safeSubtract(1, i);
            } else {
                throw new IllegalArgumentException("Invalid era: " + calendarEra);
            }
            if (i >= 1 && i <= 999999999) {
                return safeSubtract % 4 == 0 ? 366 : 365;
            }
            throw new IllegalArgumentException("Out of bounds: year=" + i);
        }

        @Override // net.time4j.engine.CalendarSystem
        public JulianCalendar transform(long j) {
            long j2;
            int i;
            try {
                long safeAdd = MathUtils.safeAdd(j, 720200L);
                long floorDivide = MathUtils.floorDivide(safeAdd, 1461);
                int floorModulo = MathUtils.floorModulo(safeAdd, 1461);
                int i2 = 2;
                if (floorModulo == 1460) {
                    j2 = (floorDivide + 1) * 4;
                    i = 29;
                } else {
                    int i3 = floorModulo / 365;
                    int i4 = floorModulo % 365;
                    j2 = (floorDivide * 4) + i3;
                    i2 = 2 + (((i4 + 31) * 5) / 153);
                    i = (i4 - (((i2 + 1) * 153) / 5)) + 123;
                    if (i2 > 12) {
                        j2++;
                        i2 -= 12;
                    }
                }
                HistoricEra historicEra = j2 >= 1 ? HistoricEra.AD : HistoricEra.BC;
                if (j2 < 1) {
                    j2 = MathUtils.safeSubtract(1L, j2);
                }
                return JulianCalendar.of(historicEra, MathUtils.safeCast(j2), i2, i);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(JulianCalendar julianCalendar) {
            long j = julianCalendar.prolepticYear;
            int i = julianCalendar.month;
            if (i < 3) {
                j--;
                i += 12;
            }
            return (((((365 * j) + MathUtils.floorDivide(j, 4)) + (((i + 1) * 153) / 5)) - 123) + julianCalendar.dom) - 720200;
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            int i = 1;
            return transform(new JulianCalendar(-999999998, i, i));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return transform(new JulianCalendar(999999999, 12, 31));
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.unmodifiableList(Arrays.asList(HistoricEra.BC, HistoricEra.AD));
        }
    }

    private static class IntegerRule implements ElementRule<JulianCalendar, Integer> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(JulianCalendar julianCalendar) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(julianCalendar.getYear());
            }
            if (i == 2) {
                return Integer.valueOf(julianCalendar.dom);
            }
            if (i == 3) {
                int i2 = 0;
                for (int i3 = 1; i3 < julianCalendar.month; i3++) {
                    i2 += JulianCalendar.lengthOfMonth(julianCalendar.prolepticYear, i3);
                }
                return Integer.valueOf(i2 + julianCalendar.dom);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(JulianCalendar julianCalendar) {
            int i = this.index;
            if (i == 0 || i == 2 || i == 3) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(JulianCalendar julianCalendar) {
            int i = this.index;
            if (i == 0) {
                return 999999999;
            }
            if (i == 2) {
                return Integer.valueOf(JulianCalendar.lengthOfMonth(julianCalendar.prolepticYear, julianCalendar.month));
            }
            if (i == 3) {
                return Integer.valueOf(julianCalendar.prolepticYear % 4 == 0 ? 366 : 365);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(JulianCalendar julianCalendar, Integer num) {
            if (num == null) {
                return false;
            }
            return getMinimum(julianCalendar).compareTo(num) <= 0 && getMaximum(julianCalendar).compareTo(num) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public JulianCalendar withValue2(JulianCalendar julianCalendar, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            int i = this.index;
            if (i == 0) {
                int intValue = num.intValue();
                return JulianCalendar.of(julianCalendar.getEra(), intValue, julianCalendar.month, Math.min(julianCalendar.dom, JulianCalendar.lengthOfMonth(julianCalendar.getEra() == HistoricEra.AD ? intValue : MathUtils.safeSubtract(1, intValue), julianCalendar.month)));
            }
            if (i == 2) {
                return JulianCalendar.of(julianCalendar.getEra(), julianCalendar.getYear(), julianCalendar.month, num.intValue());
            }
            if (i == 3) {
                int intValue2 = num.intValue();
                if (intValue2 >= 1 && intValue2 <= julianCalendar.lengthOfYear()) {
                    return julianCalendar.plus(CalendarDays.of(num.intValue() - getValue(julianCalendar).intValue()));
                }
                throw new IllegalArgumentException("Invalid day of year: " + num);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(JulianCalendar julianCalendar) {
            if (this.index == 0) {
                return JulianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(JulianCalendar julianCalendar) {
            if (this.index == 0) {
                return JulianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }
    }

    private static class MonthRule implements ElementRule<JulianCalendar, Integer> {
        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(JulianCalendar julianCalendar) {
            return Integer.valueOf(julianCalendar.month);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(JulianCalendar julianCalendar) {
            return 1;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(JulianCalendar julianCalendar) {
            return 12;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(JulianCalendar julianCalendar, Integer num) {
            int intValue;
            return num != null && (intValue = num.intValue()) >= 1 && intValue <= 12;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public JulianCalendar withValue2(JulianCalendar julianCalendar, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing month.");
            }
            int intValue = num.intValue();
            return new JulianCalendar(julianCalendar.prolepticYear, intValue, Math.min(julianCalendar.dom, JulianCalendar.lengthOfMonth(julianCalendar.prolepticYear, intValue)));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(JulianCalendar julianCalendar) {
            return JulianCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(JulianCalendar julianCalendar) {
            return JulianCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<JulianCalendar, HistoricEra> {
        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getValue(JulianCalendar julianCalendar) {
            return julianCalendar.getEra();
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getMinimum(JulianCalendar julianCalendar) {
            return HistoricEra.BC;
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getMaximum(JulianCalendar julianCalendar) {
            return HistoricEra.AD;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(JulianCalendar julianCalendar, HistoricEra historicEra) {
            return julianCalendar.getEra().equals(historicEra);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public JulianCalendar withValue2(JulianCalendar julianCalendar, HistoricEra historicEra, boolean z) {
            if (isValid2(julianCalendar, historicEra)) {
                return julianCalendar;
            }
            throw new IllegalArgumentException("Julian era cannot be changed.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(JulianCalendar julianCalendar) {
            return JulianCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(JulianCalendar julianCalendar) {
            return JulianCalendar.YEAR_OF_ERA;
        }
    }

    private static class DateRule implements ElementRule<JulianCalendar, HistoricDate> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(JulianCalendar julianCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(JulianCalendar julianCalendar) {
            return null;
        }

        private DateRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricDate getValue(JulianCalendar julianCalendar) {
            return HistoricDate.of(julianCalendar.getEra(), julianCalendar.getYear(), julianCalendar.month, julianCalendar.dom);
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricDate getMinimum(JulianCalendar julianCalendar) {
            return HistoricDate.of(HistoricEra.BC, 999999999, 1, 1);
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricDate getMaximum(JulianCalendar julianCalendar) {
            return HistoricDate.of(HistoricEra.AD, 999999999, 12, 31);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(JulianCalendar julianCalendar, HistoricDate historicDate) {
            if (historicDate == null) {
                return false;
            }
            return JulianCalendar.CALSYS.isValid(historicDate.getEra(), historicDate.getYearOfEra(), historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public JulianCalendar withValue2(JulianCalendar julianCalendar, HistoricDate historicDate, boolean z) {
            if (historicDate == null) {
                throw new IllegalArgumentException("Missing historic date value.");
            }
            return JulianCalendar.of(historicDate.getEra(), historicDate.getYearOfEra(), historicDate.getMonth(), historicDate.getDayOfMonth());
        }
    }

    private static class Merger implements ChronoMerger<JulianCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(JulianCalendar julianCalendar, AttributeQuery attributeQuery) {
            return julianCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ JulianCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ JulianCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("generic", displayStyle, locale);
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public JulianCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (JulianCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(JulianCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public JulianCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i;
            if (!chronoEntity.contains(JulianCalendar.ERA)) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Julian era.");
                return null;
            }
            HistoricEra historicEra = (HistoricEra) chronoEntity.get(JulianCalendar.ERA);
            int i2 = chronoEntity.getInt(JulianCalendar.YEAR_OF_ERA);
            if (i2 == Integer.MIN_VALUE) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Julian year.");
                return null;
            }
            int i3 = chronoEntity.getInt(JulianCalendar.MONTH_OF_YEAR);
            if (i3 != Integer.MIN_VALUE && (i = chronoEntity.getInt(JulianCalendar.DAY_OF_MONTH)) != Integer.MIN_VALUE) {
                if (JulianCalendar.CALSYS.isValid(historicEra, i2, i3, i)) {
                    return JulianCalendar.of(historicEra, i2, i3, i);
                }
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Julian date.");
            }
            int i4 = chronoEntity.getInt(JulianCalendar.DAY_OF_YEAR);
            if (i4 != Integer.MIN_VALUE) {
                if (i4 > 0) {
                    int i5 = 1;
                    int safeSubtract = historicEra == HistoricEra.AD ? i2 : MathUtils.safeSubtract(1, i2);
                    int i6 = 0;
                    while (i5 <= 12) {
                        int lengthOfMonth = JulianCalendar.lengthOfMonth(safeSubtract, i5) + i6;
                        if (i4 <= lengthOfMonth) {
                            return JulianCalendar.of(historicEra, i2, i5, i4 - i6);
                        }
                        i5++;
                        i6 = lengthOfMonth;
                    }
                }
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Julian date.");
            }
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear();
        }
    }

    private static class JulianUnitRule implements UnitRule<JulianCalendar> {
        private final Unit unit;

        JulianUnitRule(Unit unit) {
            this.unit = unit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public JulianCalendar addTo(JulianCalendar julianCalendar, long j) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$JulianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                j = MathUtils.safeMultiply(j, 12L);
            } else if (i != 2) {
                if (i == 3) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else if (i != 4) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                return (JulianCalendar) JulianCalendar.CALSYS.transform(MathUtils.safeAdd(JulianCalendar.CALSYS.transform((EraYearMonthDaySystem) julianCalendar), j));
            }
            long safeAdd = MathUtils.safeAdd(ymValue(julianCalendar), j);
            int safeCast = MathUtils.safeCast(MathUtils.floorDivide(safeAdd, 12));
            int floorModulo = MathUtils.floorModulo(safeAdd, 12) + 1;
            int min = Math.min(julianCalendar.dom, JulianCalendar.lengthOfMonth(safeCast, floorModulo));
            HistoricEra historicEra = safeCast >= 1 ? HistoricEra.AD : HistoricEra.BC;
            if (safeCast < 1) {
                safeCast = MathUtils.safeSubtract(1, safeCast);
            }
            return JulianCalendar.of(historicEra, safeCast, floorModulo, min);
        }

        @Override // net.time4j.engine.UnitRule
        public long between(JulianCalendar julianCalendar, JulianCalendar julianCalendar2) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$JulianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                return julianCalendar.until(julianCalendar2, (JulianCalendar) Unit.MONTHS) / 12;
            }
            if (i == 2) {
                long ymValue = ymValue(julianCalendar2) - ymValue(julianCalendar);
                return (ymValue <= 0 || julianCalendar2.dom >= julianCalendar.dom) ? (ymValue >= 0 || julianCalendar2.dom <= julianCalendar.dom) ? ymValue : ymValue + 1 : ymValue - 1;
            }
            if (i == 3) {
                return julianCalendar.until(julianCalendar2, (JulianCalendar) Unit.DAYS) / 7;
            }
            if (i == 4) {
                return JulianCalendar.CALSYS.transform((EraYearMonthDaySystem) julianCalendar2) - JulianCalendar.CALSYS.transform((EraYearMonthDaySystem) julianCalendar);
            }
            throw new UnsupportedOperationException(this.unit.name());
        }

        private static long ymValue(JulianCalendar julianCalendar) {
            return ((julianCalendar.prolepticYear * 12) + julianCalendar.month) - 1;
        }
    }

    /* renamed from: net.time4j.calendar.JulianCalendar$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$JulianCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$JulianCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$JulianCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$JulianCalendar$Unit[Unit.WEEKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$JulianCalendar$Unit[Unit.DAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static class SPX implements Externalizable {
        private static final int JULIAN = 7;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(7);
            writeJulian(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 7) {
                this.obj = readJulian(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeJulian(ObjectOutput objectOutput) throws IOException {
            JulianCalendar julianCalendar = (JulianCalendar) this.obj;
            objectOutput.writeInt(julianCalendar.getProlepticYear());
            objectOutput.writeInt(julianCalendar.getMonth().getValue());
            objectOutput.writeInt(julianCalendar.getDayOfMonth());
        }

        private JulianCalendar readJulian(ObjectInput objectInput) throws IOException {
            int readInt = objectInput.readInt();
            int readInt2 = objectInput.readInt();
            int readInt3 = objectInput.readInt();
            HistoricEra historicEra = readInt >= 1 ? HistoricEra.AD : HistoricEra.BC;
            if (readInt < 1) {
                readInt = MathUtils.safeSubtract(1, readInt);
            }
            return JulianCalendar.of(historicEra, readInt, readInt2, readInt3);
        }
    }
}
