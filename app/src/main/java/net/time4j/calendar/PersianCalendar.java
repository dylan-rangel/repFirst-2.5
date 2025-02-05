package net.time4j.calendar;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;
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
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FormattableElement;
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
import net.time4j.tz.ZonalOffset;

@CalendarType("persian")
/* loaded from: classes3.dex */
public final class PersianCalendar extends Calendrical<Unit, PersianCalendar> implements LocalizedPatternSupport {
    public static final StdCalendarElement<Integer, PersianCalendar> BOUNDED_WEEK_OF_MONTH;
    public static final StdCalendarElement<Integer, PersianCalendar> BOUNDED_WEEK_OF_YEAR;
    private static final EraYearMonthDaySystem<PersianCalendar> CALSYS;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, PersianCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, PersianCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, PersianCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final PersianAlgorithm DEFAULT_COMPUTATION;
    private static final TimeAxis<Unit, PersianCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<PersianEra> ERA;
    public static final StdCalendarElement<Weekday, PersianCalendar> LOCAL_DAY_OF_WEEK;

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<PersianMonth, PersianCalendar> MONTH_OF_YEAR;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<PersianCalendar> WEEKDAY_IN_MONTH;
    public static final StdCalendarElement<Integer, PersianCalendar> WEEK_OF_MONTH;
    public static final StdCalendarElement<Integer, PersianCalendar> WEEK_OF_YEAR;
    private static final WeekdayInMonthElement<PersianCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, PersianCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = -411339992208638290L;
    private final transient int pdom;
    private final transient int pmonth;
    private final transient int pyear;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public PersianCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", PersianCalendar.class, PersianEra.class, 'G');
        ERA = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("YEAR_OF_ERA", PersianCalendar.class, 1, 3000, 'y', null, null);
        YEAR_OF_ERA = stdIntegerDateElement;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", PersianCalendar.class, PersianMonth.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", PersianCalendar.class, 1, 31, 'd');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", PersianCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(PersianCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<PersianCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(PersianCalendar.class, stdIntegerDateElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        DEFAULT_COMPUTATION = PersianAlgorithm.BORKOWSKI;
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, PersianCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new EraRule()).appendElement(stdIntegerDateElement, new IntegerRule(0), Unit.YEARS).appendElement(stdEnumDateElement2, new MonthRule(), Unit.MONTHS).appendElement(stdIntegerDateElement2, new IntegerRule(2), Unit.DAYS).appendElement(stdIntegerDateElement3, new IntegerRule(3), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<PersianCalendar, CalendarSystem<PersianCalendar>>() { // from class: net.time4j.calendar.PersianCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<PersianCalendar> apply(PersianCalendar persianCalendar) {
                return PersianCalendar.CALSYS;
            }
        }), Unit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement3)).appendUnit(Unit.YEARS, new PersianUnitRule(Unit.YEARS), Unit.YEARS.getLength(), Collections.singleton(Unit.MONTHS)).appendUnit(Unit.MONTHS, new PersianUnitRule(Unit.MONTHS), Unit.MONTHS.getLength(), Collections.singleton(Unit.YEARS)).appendUnit(Unit.WEEKS, new PersianUnitRule(Unit.WEEKS), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, new PersianUnitRule(Unit.DAYS), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).appendExtension((ChronoExtension) new CommonElements.Weekengine(PersianCalendar.class, stdIntegerDateElement2, stdIntegerDateElement3, getDefaultWeekmodel())).build();
        LOCAL_DAY_OF_WEEK = CommonElements.localDayOfWeek(axis(), getDefaultWeekmodel());
        WEEK_OF_YEAR = CommonElements.weekOfYear(axis(), getDefaultWeekmodel());
        WEEK_OF_MONTH = CommonElements.weekOfMonth(axis(), getDefaultWeekmodel());
        BOUNDED_WEEK_OF_YEAR = CommonElements.boundedWeekOfYear(axis(), getDefaultWeekmodel());
        BOUNDED_WEEK_OF_MONTH = CommonElements.boundedWeekOfMonth(axis(), getDefaultWeekmodel());
    }

    PersianCalendar(int i, int i2, int i3) {
        this.pyear = i;
        this.pmonth = i2;
        this.pdom = i3;
    }

    public static PersianCalendar of(int i, PersianMonth persianMonth, int i2) {
        return of(i, persianMonth.getValue(), i2);
    }

    public static PersianCalendar of(int i, int i2, int i3) {
        if (!CALSYS.isValid(PersianEra.ANNO_PERSICO, i, i2, i3)) {
            throw new IllegalArgumentException("Invalid Persian date: year=" + i + ", month=" + i2 + ", day=" + i3);
        }
        return new PersianCalendar(i, i2, i3);
    }

    public static PersianCalendar nowInSystemTime() {
        return (PersianCalendar) SystemClock.inLocalView().now(axis());
    }

    public PersianEra getEra() {
        return PersianEra.ANNO_PERSICO;
    }

    public int getYear() {
        return this.pyear;
    }

    public PersianMonth getMonth() {
        return PersianMonth.valueOf(this.pmonth);
    }

    public int getDayOfMonth() {
        return this.pdom;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(CALSYS.transform((EraYearMonthDaySystem<PersianCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    public Date getDate(PersianAlgorithm persianAlgorithm) {
        ZonalOffset zonalOffset = PersianAlgorithm.STD_OFFSET;
        PersianAlgorithm persianAlgorithm2 = DEFAULT_COMPUTATION;
        if (persianAlgorithm == persianAlgorithm2) {
            return new Date(persianAlgorithm2, zonalOffset);
        }
        return new Date(persianAlgorithm, zonalOffset);
    }

    public Date getDate(ZonalOffset zonalOffset) {
        if (zonalOffset == null) {
            throw new NullPointerException("Missing timezone offset.");
        }
        PersianAlgorithm persianAlgorithm = PersianAlgorithm.ASTRONOMICAL;
        return new Date(persianAlgorithm, zonalOffset);
    }

    public int lengthOfMonth() {
        return CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, this.pyear, this.pmonth);
    }

    public int lengthOfYear() {
        return CALSYS.getLengthOfYear(PersianEra.ANNO_PERSICO, this.pyear);
    }

    public boolean isLeapYear() {
        return lengthOfYear() > 365;
    }

    public static boolean isValid(int i, int i2, int i3) {
        return CALSYS.isValid(PersianEra.ANNO_PERSICO, i, i2, i3);
    }

    public GeneralTimestamp<PersianCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<PersianCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersianCalendar)) {
            return false;
        }
        PersianCalendar persianCalendar = (PersianCalendar) obj;
        return this.pdom == persianCalendar.pdom && this.pmonth == persianCalendar.pmonth && this.pyear == persianCalendar.pyear;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.pdom * 17) + (this.pmonth * 31) + (this.pyear * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("AP-");
        String valueOf = String.valueOf(this.pyear);
        for (int length = valueOf.length(); length < 4; length++) {
            sb.append('0');
        }
        sb.append(valueOf);
        sb.append('-');
        if (this.pmonth < 10) {
            sb.append('0');
        }
        sb.append(this.pmonth);
        sb.append('-');
        if (this.pdom < 10) {
            sb.append('0');
        }
        sb.append(this.pdom);
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SATURDAY, 1, Weekday.FRIDAY, Weekday.FRIDAY);
    }

    public static TimeAxis<Unit, PersianCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, PersianCalendar> getChronology() {
        return ENGINE;
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.155694336E7d),
        MONTHS(2629745.28d),
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

        public int between(PersianCalendar persianCalendar, PersianCalendar persianCalendar2) {
            return (int) persianCalendar.until(persianCalendar2, (PersianCalendar) this);
        }
    }

    public static final class Date implements ChronoDisplay {
        private final PersianAlgorithm algorithm;
        private final PersianCalendar delegate;
        private final ZonalOffset offset;

        private Date(PersianCalendar persianCalendar, PersianAlgorithm persianAlgorithm, ZonalOffset zonalOffset) {
            this.delegate = persianCalendar;
            this.algorithm = persianAlgorithm;
            this.offset = zonalOffset;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public boolean contains(ChronoElement<?> chronoElement) {
            return PersianCalendar.ENGINE.isRegistered(chronoElement);
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V get(ChronoElement<V> chronoElement) {
            int i = 1;
            if (chronoElement == PersianCalendar.DAY_OF_WEEK) {
                return chronoElement.getType().cast(Weekday.valueOf(MathUtils.floorModulo(this.algorithm.transform(this.delegate, this.offset) + 5, 7) + 1));
            }
            if (chronoElement == PersianCalendar.DAY_OF_YEAR) {
                int i2 = 0;
                while (i < this.delegate.pmonth) {
                    i2 = i <= 6 ? i2 + 31 : i2 + 30;
                    i++;
                }
                return chronoElement.getType().cast(Integer.valueOf(i2 + this.delegate.pdom));
            }
            if (chronoElement == PersianCalendar.WEEKDAY_IN_MONTH) {
                return chronoElement.getType().cast(Integer.valueOf(MathUtils.floorDivide(this.delegate.pdom - 1, 7) + 1));
            }
            if (chronoElement == CommonElements.RELATED_GREGORIAN_YEAR) {
                return chronoElement.getType().cast(Integer.valueOf(this.delegate.getYear() + 621));
            }
            if (!(chronoElement instanceof EpochDays)) {
                if (PersianCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                    return (V) this.delegate.get(chronoElement);
                }
                throw new ChronoException("Persian dates only support registered elements.");
            }
            return chronoElement.getType().cast(Long.valueOf(((EpochDays) EpochDays.class.cast(chronoElement)).transform(this.algorithm.transform(this.delegate, this.offset), EpochDays.UTC)));
        }

        @Override // net.time4j.engine.ChronoDisplay
        public int getInt(ChronoElement<Integer> chronoElement) {
            if (chronoElement == PersianCalendar.DAY_OF_MONTH) {
                return this.delegate.pdom;
            }
            if (chronoElement == PersianCalendar.YEAR_OF_ERA) {
                return this.delegate.pyear;
            }
            int i = 1;
            if (chronoElement == PersianCalendar.DAY_OF_YEAR) {
                int i2 = 0;
                while (i < this.delegate.pmonth) {
                    i2 = i <= 6 ? i2 + 31 : i2 + 30;
                    i++;
                }
                return i2 + this.delegate.pdom;
            }
            if (chronoElement == PersianCalendar.WEEKDAY_IN_MONTH) {
                return MathUtils.floorDivide(this.delegate.pdom - 1, 7) + 1;
            }
            if (chronoElement != CommonElements.RELATED_GREGORIAN_YEAR) {
                if (PersianCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                    return this.delegate.getInt(chronoElement);
                }
                return Integer.MIN_VALUE;
            }
            return this.delegate.getYear() + 621;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMinimum(ChronoElement<V> chronoElement) {
            if (PersianCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                return (V) this.delegate.getMinimum(chronoElement);
            }
            throw new ChronoException("Persian dates only support registered elements.");
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMaximum(ChronoElement<V> chronoElement) {
            if (chronoElement == PersianCalendar.DAY_OF_MONTH) {
                int i = this.delegate.pmonth;
                int i2 = 30;
                if (i <= 6) {
                    i2 = 31;
                } else if (i > 11 && !this.algorithm.isLeapYear(this.delegate.pyear, this.offset)) {
                    i2 = 29;
                }
                return chronoElement.getType().cast(Integer.valueOf(i2));
            }
            if (chronoElement == PersianCalendar.DAY_OF_YEAR) {
                return chronoElement.getType().cast(Integer.valueOf(this.algorithm.isLeapYear(this.delegate.pyear, this.offset) ? 366 : 365));
            }
            if (chronoElement == PersianCalendar.WEEKDAY_IN_MONTH) {
                int i3 = this.delegate.pdom;
                while (true) {
                    int i4 = i3 + 7;
                    if (i4 > ((Integer) getMaximum(PersianCalendar.DAY_OF_MONTH)).intValue()) {
                        return chronoElement.getType().cast(Integer.valueOf(MathUtils.floorDivide(i3 - 1, 7) + 1));
                    }
                    i3 = i4;
                }
            } else {
                if (PersianCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                    return (V) this.delegate.getMaximum(chronoElement);
                }
                throw new ChronoException("Persian dates only support registered elements.");
            }
        }

        @Override // net.time4j.engine.ChronoDisplay
        public boolean hasTimezone() {
            return this.algorithm == PersianAlgorithm.ASTRONOMICAL;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public ZonalOffset getTimezone() {
            if (hasTimezone()) {
                return this.offset;
            }
            throw new ChronoException("Timezone offset not defined.");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Date)) {
                return false;
            }
            Date date = (Date) obj;
            PersianAlgorithm persianAlgorithm = this.algorithm;
            if (persianAlgorithm != date.algorithm) {
                return false;
            }
            if (persianAlgorithm != PersianAlgorithm.ASTRONOMICAL || this.offset.equals(date.offset)) {
                return this.delegate.equals(date.delegate);
            }
            return false;
        }

        public int hashCode() {
            return (this.delegate.hashCode() * 7) + (this.algorithm.hashCode() * 31);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.delegate);
            sb.append('[');
            sb.append(this.algorithm);
            if (this.algorithm == PersianAlgorithm.ASTRONOMICAL) {
                sb.append(this.offset.toString());
            }
            sb.append(']');
            return sb.toString();
        }
    }

    private static class Transformer implements EraYearMonthDaySystem<PersianCalendar> {
        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            return calendarEra == PersianEra.ANNO_PERSICO && i >= 1 && i <= 3000 && i2 >= 1 && i2 <= 12 && i3 >= 1 && i3 <= getLengthOfMonth(calendarEra, i, i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
            if (calendarEra != PersianEra.ANNO_PERSICO) {
                throw new IllegalArgumentException("Invalid era: " + calendarEra);
            }
            if (calendarEra == PersianEra.ANNO_PERSICO && i >= 1 && i <= 3000 && i2 >= 1 && i2 <= 12) {
                if (i2 <= 6) {
                    return 31;
                }
                return (i2 > 11 && getLengthOfYear(calendarEra, i) == 365) ? 29 : 30;
            }
            throw new IllegalArgumentException("Out of bounds: year=" + i + ", month=" + i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            if (calendarEra == PersianEra.ANNO_PERSICO) {
                return PersianCalendar.DEFAULT_COMPUTATION.isLeapYear(i) ? 366 : 365;
            }
            throw new IllegalArgumentException("Invalid era: " + calendarEra);
        }

        @Override // net.time4j.engine.CalendarSystem
        public PersianCalendar transform(long j) {
            return PersianCalendar.DEFAULT_COMPUTATION.transform(j, PersianAlgorithm.STD_OFFSET);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(PersianCalendar persianCalendar) {
            return PersianCalendar.DEFAULT_COMPUTATION.transform(persianCalendar, PersianAlgorithm.STD_OFFSET);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return transform(new PersianCalendar(1, 1, 1));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return transform(new PersianCalendar(AuthApiStatusCodes.AUTH_API_ACCESS_FORBIDDEN, 1, 1)) - 1;
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.singletonList(PersianEra.ANNO_PERSICO);
        }
    }

    private static class IntegerRule implements ElementRule<PersianCalendar, Integer> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(PersianCalendar persianCalendar) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(persianCalendar.pyear);
            }
            if (i == 2) {
                return Integer.valueOf(persianCalendar.pdom);
            }
            if (i == 3) {
                int i2 = 0;
                for (int i3 = 1; i3 < persianCalendar.pmonth; i3++) {
                    i2 += PersianCalendar.CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, persianCalendar.pyear, i3);
                }
                return Integer.valueOf(i2 + persianCalendar.pdom);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(PersianCalendar persianCalendar) {
            int i = this.index;
            if (i == 0 || i == 2 || i == 3) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(PersianCalendar persianCalendar) {
            int i = this.index;
            if (i == 0) {
                return 3000;
            }
            if (i == 2) {
                return Integer.valueOf(PersianCalendar.CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, persianCalendar.pyear, persianCalendar.pmonth));
            }
            if (i == 3) {
                return Integer.valueOf(PersianCalendar.CALSYS.getLengthOfYear(PersianEra.ANNO_PERSICO, persianCalendar.pyear));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(PersianCalendar persianCalendar, Integer num) {
            if (num == null) {
                return false;
            }
            return getMinimum(persianCalendar).compareTo(num) <= 0 && getMaximum(persianCalendar).compareTo(num) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public PersianCalendar withValue2(PersianCalendar persianCalendar, Integer num, boolean z) {
            if (!isValid2(persianCalendar, num)) {
                throw new IllegalArgumentException("Out of range: " + num);
            }
            int i = this.index;
            if (i == 0) {
                int intValue = num.intValue();
                return PersianCalendar.of(intValue, persianCalendar.pmonth, Math.min(persianCalendar.pdom, PersianCalendar.CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, intValue, persianCalendar.pmonth)));
            }
            if (i == 2) {
                return new PersianCalendar(persianCalendar.pyear, persianCalendar.pmonth, num.intValue());
            }
            if (i == 3) {
                return persianCalendar.plus(CalendarDays.of(num.intValue() - getValue(persianCalendar).intValue()));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PersianCalendar persianCalendar) {
            if (this.index == 0) {
                return PersianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PersianCalendar persianCalendar) {
            if (this.index == 0) {
                return PersianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }
    }

    private static class MonthRule implements ElementRule<PersianCalendar, PersianMonth> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(PersianCalendar persianCalendar, PersianMonth persianMonth) {
            return persianMonth != null;
        }

        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public PersianMonth getValue(PersianCalendar persianCalendar) {
            return persianCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public PersianMonth getMinimum(PersianCalendar persianCalendar) {
            return PersianMonth.FARVARDIN;
        }

        @Override // net.time4j.engine.ElementRule
        public PersianMonth getMaximum(PersianCalendar persianCalendar) {
            return PersianMonth.ESFAND;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public PersianCalendar withValue2(PersianCalendar persianCalendar, PersianMonth persianMonth, boolean z) {
            if (persianMonth == null) {
                throw new IllegalArgumentException("Missing month.");
            }
            int value = persianMonth.getValue();
            return new PersianCalendar(persianCalendar.pyear, value, Math.min(persianCalendar.pdom, PersianCalendar.CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, persianCalendar.pyear, value)));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PersianCalendar persianCalendar) {
            return PersianCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PersianCalendar persianCalendar) {
            return PersianCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<PersianCalendar, PersianEra> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(PersianCalendar persianCalendar, PersianEra persianEra) {
            return persianEra != null;
        }

        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public PersianEra getValue(PersianCalendar persianCalendar) {
            return PersianEra.ANNO_PERSICO;
        }

        @Override // net.time4j.engine.ElementRule
        public PersianEra getMinimum(PersianCalendar persianCalendar) {
            return PersianEra.ANNO_PERSICO;
        }

        @Override // net.time4j.engine.ElementRule
        public PersianEra getMaximum(PersianCalendar persianCalendar) {
            return PersianEra.ANNO_PERSICO;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public PersianCalendar withValue2(PersianCalendar persianCalendar, PersianEra persianEra, boolean z) {
            if (persianEra != null) {
                return persianCalendar;
            }
            throw new IllegalArgumentException("Missing era value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PersianCalendar persianCalendar) {
            return PersianCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PersianCalendar persianCalendar) {
            return PersianCalendar.YEAR_OF_ERA;
        }
    }

    private static class Merger implements ChronoMerger<PersianCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ PersianCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ PersianCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("persian", displayStyle, locale);
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 621;
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PersianCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (PersianCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(PersianCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PersianCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i = chronoEntity.getInt(PersianCalendar.YEAR_OF_ERA);
            if (i != Integer.MIN_VALUE) {
                PersianAlgorithm persianAlgorithm = (PersianAlgorithm) attributeQuery.get(PersianAlgorithm.attribute(), PersianCalendar.DEFAULT_COMPUTATION);
                ZonalOffset zonalOffset = PersianAlgorithm.STD_OFFSET;
                if (persianAlgorithm == PersianAlgorithm.ASTRONOMICAL && attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                    TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
                    if (tzid instanceof ZonalOffset) {
                        zonalOffset = (ZonalOffset) tzid;
                    }
                }
                if (chronoEntity.contains(PersianCalendar.MONTH_OF_YEAR)) {
                    int value = ((PersianMonth) chronoEntity.get(PersianCalendar.MONTH_OF_YEAR)).getValue();
                    int i2 = chronoEntity.getInt(PersianCalendar.DAY_OF_MONTH);
                    if (i2 != Integer.MIN_VALUE) {
                        if (persianAlgorithm.isValid(i, value, i2, zonalOffset)) {
                            PersianCalendar persianCalendar = new PersianCalendar(i, value, i2);
                            if (persianAlgorithm == PersianCalendar.DEFAULT_COMPUTATION) {
                                return persianCalendar;
                            }
                            return PersianCalendar.DEFAULT_COMPUTATION.transform(persianAlgorithm.transform(persianCalendar, zonalOffset), PersianAlgorithm.STD_OFFSET);
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Persian date.");
                    }
                } else {
                    int i3 = chronoEntity.getInt(PersianCalendar.DAY_OF_YEAR);
                    if (i3 != Integer.MIN_VALUE) {
                        if (i3 > 0) {
                            int i4 = 0;
                            int i5 = 1;
                            while (true) {
                                if (i5 > 12) {
                                    break;
                                }
                                int i6 = 30;
                                if (i5 <= 6) {
                                    i6 = 31;
                                } else if (i5 > 11 && !persianAlgorithm.isLeapYear(i, zonalOffset)) {
                                    i6 = 29;
                                }
                                int i7 = i4 + i6;
                                if (i3 > i7) {
                                    i5++;
                                    i4 = i7;
                                } else {
                                    int i8 = i3 - i4;
                                    if (persianAlgorithm.isValid(i, i5, i8, zonalOffset)) {
                                        PersianCalendar persianCalendar2 = new PersianCalendar(i, i5, i8);
                                        if (persianAlgorithm == PersianCalendar.DEFAULT_COMPUTATION) {
                                            return persianCalendar2;
                                        }
                                        return PersianCalendar.DEFAULT_COMPUTATION.transform(persianAlgorithm.transform(persianCalendar2, zonalOffset), PersianAlgorithm.STD_OFFSET);
                                    }
                                }
                            }
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Persian date.");
                    }
                }
                return null;
            }
            chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Persian year.");
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(PersianCalendar persianCalendar, AttributeQuery attributeQuery) {
            PersianAlgorithm persianAlgorithm = (PersianAlgorithm) attributeQuery.get(PersianAlgorithm.attribute(), PersianCalendar.DEFAULT_COMPUTATION);
            if (persianAlgorithm == PersianCalendar.DEFAULT_COMPUTATION) {
                return persianCalendar;
            }
            if (persianAlgorithm == PersianAlgorithm.ASTRONOMICAL && attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                ZonalOffset zonalOffset = PersianAlgorithm.STD_OFFSET;
                TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
                if (tzid instanceof ZonalOffset) {
                    zonalOffset = (ZonalOffset) tzid;
                }
                return persianCalendar.getDate(zonalOffset);
            }
            return persianCalendar.getDate(persianAlgorithm);
        }
    }

    private static class PersianUnitRule implements UnitRule<PersianCalendar> {
        private final Unit unit;

        PersianUnitRule(Unit unit) {
            this.unit = unit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public PersianCalendar addTo(PersianCalendar persianCalendar, long j) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$PersianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                j = MathUtils.safeMultiply(j, 12L);
            } else if (i != 2) {
                if (i == 3) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else if (i != 4) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                return (PersianCalendar) PersianCalendar.CALSYS.transform(MathUtils.safeAdd(PersianCalendar.CALSYS.transform((EraYearMonthDaySystem) persianCalendar), j));
            }
            long safeAdd = MathUtils.safeAdd(ymValue(persianCalendar), j);
            int safeCast = MathUtils.safeCast(MathUtils.floorDivide(safeAdd, 12));
            int floorModulo = MathUtils.floorModulo(safeAdd, 12) + 1;
            return PersianCalendar.of(safeCast, floorModulo, Math.min(persianCalendar.pdom, PersianCalendar.CALSYS.getLengthOfMonth(PersianEra.ANNO_PERSICO, safeCast, floorModulo)));
        }

        @Override // net.time4j.engine.UnitRule
        public long between(PersianCalendar persianCalendar, PersianCalendar persianCalendar2) {
            int between;
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$PersianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                between = Unit.MONTHS.between(persianCalendar, persianCalendar2) / 12;
            } else {
                if (i == 2) {
                    long ymValue = ymValue(persianCalendar2) - ymValue(persianCalendar);
                    return (ymValue <= 0 || persianCalendar2.pdom >= persianCalendar.pdom) ? (ymValue >= 0 || persianCalendar2.pdom <= persianCalendar.pdom) ? ymValue : ymValue + 1 : ymValue - 1;
                }
                if (i != 3) {
                    if (i == 4) {
                        return PersianCalendar.CALSYS.transform((EraYearMonthDaySystem) persianCalendar2) - PersianCalendar.CALSYS.transform((EraYearMonthDaySystem) persianCalendar);
                    }
                    throw new UnsupportedOperationException(this.unit.name());
                }
                between = Unit.DAYS.between(persianCalendar, persianCalendar2) / 7;
            }
            return between;
        }

        private static int ymValue(PersianCalendar persianCalendar) {
            return ((persianCalendar.pyear * 12) + persianCalendar.pmonth) - 1;
        }
    }

    /* renamed from: net.time4j.calendar.PersianCalendar$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$PersianCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$PersianCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$PersianCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$PersianCalendar$Unit[Unit.WEEKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$PersianCalendar$Unit[Unit.DAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static class SPX implements Externalizable {
        private static final int PERSIAN = 2;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(2);
            writePersian(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 2) {
                this.obj = readPersian(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writePersian(ObjectOutput objectOutput) throws IOException {
            PersianCalendar persianCalendar = (PersianCalendar) this.obj;
            objectOutput.writeInt(persianCalendar.getYear());
            objectOutput.writeByte(persianCalendar.getMonth().getValue());
            objectOutput.writeByte(persianCalendar.getDayOfMonth());
        }

        private PersianCalendar readPersian(ObjectInput objectInput) throws IOException {
            return PersianCalendar.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
        }
    }
}
