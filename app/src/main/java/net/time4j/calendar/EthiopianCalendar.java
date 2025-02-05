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
import net.time4j.PlainDate;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.Tabot;
import net.time4j.calendar.service.EthiopianExtension;
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

@CalendarType("ethiopic")
/* loaded from: classes3.dex */
public final class EthiopianCalendar extends Calendrical<Unit, EthiopianCalendar> implements LocalizedPatternSupport {
    private static final EraYearMonthDaySystem<EthiopianCalendar> CALSYS;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, EthiopianCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, EthiopianCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, EthiopianCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final int DELTA_ALEM_MIHRET = 5500;
    private static final TimeAxis<Unit, EthiopianCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<EthiopianEra> ERA;
    public static final ChronoElement<Evangelist> EVANGELIST;
    private static final long MIHRET_EPOCH = ((Long) ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 8, 8, 29)).get(EpochDays.UTC)).longValue();

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<EthiopianMonth, EthiopianCalendar> MONTH_OF_YEAR;
    public static final TextElement<Tabot> TABOT;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<EthiopianCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<EthiopianCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, EthiopianCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = -1632000525062084751L;
    private final transient int edom;
    private final transient int emonth;
    private final transient int mihret;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public EthiopianCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", EthiopianCalendar.class, EthiopianEra.class, 'G');
        ERA = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("YEAR_OF_ERA", EthiopianCalendar.class, 1, 9999, 'y', null, null);
        YEAR_OF_ERA = stdIntegerDateElement;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", EthiopianCalendar.class, EthiopianMonth.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", EthiopianCalendar.class, 1, 30, 'd');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", EthiopianCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(EthiopianCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<EthiopianCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(EthiopianCalendar.class, stdIntegerDateElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        StdEnumDateElement stdEnumDateElement3 = new StdEnumDateElement("EVANGELIST", EthiopianCalendar.class, Evangelist.class, (char) 0, "generic");
        EVANGELIST = stdEnumDateElement3;
        Tabot.Element element = Tabot.Element.TABOT;
        TABOT = element;
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, EthiopianCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new EraRule()).appendElement(stdIntegerDateElement, new IntegerRule(0), Unit.YEARS).appendElement(stdEnumDateElement2, new MonthRule(), Unit.MONTHS).appendElement(stdIntegerDateElement2, new IntegerRule(2), Unit.DAYS).appendElement(stdIntegerDateElement3, new IntegerRule(3), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<EthiopianCalendar, CalendarSystem<EthiopianCalendar>>() { // from class: net.time4j.calendar.EthiopianCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<EthiopianCalendar> apply(EthiopianCalendar ethiopianCalendar) {
                return EthiopianCalendar.CALSYS;
            }
        }), Unit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement3)).appendElement((ChronoElement) stdEnumDateElement3, (ElementRule) new EvangelistRule()).appendElement((ChronoElement) element, (ElementRule) new TabotRule()).appendUnit(Unit.YEARS, new EthiopianUnitRule(Unit.YEARS), Unit.YEARS.getLength(), Collections.singleton(Unit.MONTHS)).appendUnit(Unit.MONTHS, new EthiopianUnitRule(Unit.MONTHS), Unit.MONTHS.getLength(), Collections.singleton(Unit.YEARS)).appendUnit(Unit.WEEKS, new EthiopianUnitRule(Unit.WEEKS), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, new EthiopianUnitRule(Unit.DAYS), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).appendExtension((ChronoExtension) new EthiopianExtension()).appendExtension((ChronoExtension) new CommonElements.Weekengine(EthiopianCalendar.class, stdIntegerDateElement2, stdIntegerDateElement3, getDefaultWeekmodel())).build();
    }

    private EthiopianCalendar(int i, int i2, int i3) {
        this.mihret = i;
        this.emonth = i2;
        this.edom = i3;
    }

    public static EthiopianCalendar of(EthiopianEra ethiopianEra, int i, EthiopianMonth ethiopianMonth, int i2) {
        return of(ethiopianEra, i, ethiopianMonth.getValue(), i2);
    }

    public static EthiopianCalendar of(EthiopianEra ethiopianEra, int i, int i2, int i3) {
        if (!CALSYS.isValid(ethiopianEra, i, i2, i3)) {
            throw new IllegalArgumentException("Invalid Ethiopian date: era=" + ethiopianEra + ", year=" + i + ", month=" + i2 + ", day=" + i3);
        }
        return new EthiopianCalendar(mihret(ethiopianEra, i), i2, i3);
    }

    public static EthiopianCalendar nowInSystemTime() {
        return (EthiopianCalendar) SystemClock.inLocalView().now(axis());
    }

    public EthiopianEra getEra() {
        return this.mihret < 1 ? EthiopianEra.AMETE_ALEM : EthiopianEra.AMETE_MIHRET;
    }

    public int getYear() {
        int i = this.mihret;
        return i < 1 ? i + DELTA_ALEM_MIHRET : i;
    }

    public EthiopianMonth getMonth() {
        return EthiopianMonth.valueOf(this.emonth);
    }

    public int getDayOfMonth() {
        return this.edom;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(CALSYS.transform((EraYearMonthDaySystem<EthiopianCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    public int lengthOfMonth() {
        return CALSYS.getLengthOfMonth(getEra(), getYear(), this.emonth);
    }

    public int lengthOfYear() {
        return isLeapYear() ? 366 : 365;
    }

    public boolean isLeapYear() {
        return getYear() % 4 == 3;
    }

    public static boolean isValid(EthiopianEra ethiopianEra, int i, int i2, int i3) {
        return CALSYS.isValid(ethiopianEra, i, i2, i3);
    }

    public GeneralTimestamp<EthiopianCalendar> at(EthiopianTime ethiopianTime) {
        return GeneralTimestamp.of(this, ethiopianTime.toISO());
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EthiopianCalendar)) {
            return false;
        }
        EthiopianCalendar ethiopianCalendar = (EthiopianCalendar) obj;
        return this.edom == ethiopianCalendar.edom && this.emonth == ethiopianCalendar.emonth && this.mihret == ethiopianCalendar.mihret;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.edom * 17) + (this.emonth * 31) + (this.mihret * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(getEra());
        sb.append('-');
        String valueOf = String.valueOf(getYear());
        for (int length = valueOf.length(); length < 4; length++) {
            sb.append('0');
        }
        sb.append(valueOf);
        sb.append('-');
        if (this.emonth < 10) {
            sb.append('0');
        }
        sb.append(this.emonth);
        sb.append('-');
        if (this.edom < 10) {
            sb.append('0');
        }
        sb.append(this.edom);
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1);
    }

    public static TimeAxis<Unit, EthiopianCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, EthiopianCalendar> getChronology() {
        return ENGINE;
    }

    private static int mihret(CalendarEra calendarEra, int i) {
        return EthiopianEra.AMETE_ALEM.equals(calendarEra) ? i - 5500 : i;
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.15576E7d),
        MONTHS(2592000.0d),
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

        public int between(EthiopianCalendar ethiopianCalendar, EthiopianCalendar ethiopianCalendar2) {
            return (int) ethiopianCalendar.until(ethiopianCalendar2, (EthiopianCalendar) this);
        }
    }

    private static class Transformer implements EraYearMonthDaySystem<EthiopianCalendar> {
        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            if ((calendarEra instanceof EthiopianEra) && i >= 1) {
                if (i <= (EthiopianEra.AMETE_ALEM.equals(calendarEra) ? 15499 : 9999) && i2 >= 1 && i2 <= 13 && i3 >= 1 && i3 <= getLengthOfMonth(calendarEra, i, i2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
            checkEra(calendarEra);
            if (i >= 1) {
                if (i <= (EthiopianEra.AMETE_ALEM.equals(calendarEra) ? 15499 : 9999) && i2 >= 1 && i2 <= 13) {
                    if (i2 <= 12) {
                        return 30;
                    }
                    return i % 4 == 3 ? 6 : 5;
                }
            }
            throw new IllegalArgumentException("Out of bounds: era=" + calendarEra + ", year=" + i + ", month=" + i2);
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            checkEra(calendarEra);
            if (i >= 1) {
                if (i <= (EthiopianEra.AMETE_ALEM.equals(calendarEra) ? 15499 : 9999)) {
                    return i % 4 == 3 ? 366 : 365;
                }
            }
            throw new IllegalArgumentException("Out of bounds: era=" + calendarEra + ", year=" + i);
        }

        @Override // net.time4j.engine.CalendarSystem
        public EthiopianCalendar transform(long j) {
            try {
                int safeCast = MathUtils.safeCast(MathUtils.floorDivide(MathUtils.safeAdd(MathUtils.safeMultiply(4L, MathUtils.safeSubtract(j, EthiopianCalendar.MIHRET_EPOCH)), 1463L), 1461));
                int i = 1;
                int safeCast2 = MathUtils.safeCast(MathUtils.floorDivide(j - MathUtils.safeCast(transform(new EthiopianCalendar(safeCast, i, i))), 30)) + 1;
                int safeCast3 = MathUtils.safeCast(MathUtils.safeSubtract(j, MathUtils.safeCast(transform(new EthiopianCalendar(safeCast, safeCast2, i))))) + 1;
                EthiopianEra ethiopianEra = EthiopianEra.AMETE_MIHRET;
                if (safeCast < 1) {
                    safeCast += EthiopianCalendar.DELTA_ALEM_MIHRET;
                    ethiopianEra = EthiopianEra.AMETE_ALEM;
                }
                return EthiopianCalendar.of(ethiopianEra, safeCast, safeCast2, safeCast3);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(EthiopianCalendar ethiopianCalendar) {
            return (EthiopianCalendar.MIHRET_EPOCH - 1) + ((ethiopianCalendar.mihret - 1) * 365) + MathUtils.floorDivide(ethiopianCalendar.mihret, 4) + ((ethiopianCalendar.emonth - 1) * 30) + ethiopianCalendar.edom;
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            int i = 1;
            return transform(new EthiopianCalendar(-5499, i, i));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return transform(new EthiopianCalendar(9999, 13, 6));
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Arrays.asList(EthiopianEra.AMETE_ALEM, EthiopianEra.AMETE_MIHRET);
        }

        private static void checkEra(CalendarEra calendarEra) {
            if (calendarEra instanceof EthiopianEra) {
                return;
            }
            throw new IllegalArgumentException("Invalid era: " + calendarEra);
        }
    }

    private static class IntegerRule implements ElementRule<EthiopianCalendar, Integer> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(EthiopianCalendar ethiopianCalendar) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(ethiopianCalendar.getYear());
            }
            if (i == 2) {
                return Integer.valueOf(ethiopianCalendar.edom);
            }
            if (i == 3) {
                int i2 = 0;
                for (int i3 = 1; i3 < ethiopianCalendar.emonth; i3++) {
                    i2 += EthiopianCalendar.CALSYS.getLengthOfMonth(ethiopianCalendar.getEra(), ethiopianCalendar.getYear(), i3);
                }
                return Integer.valueOf(i2 + ethiopianCalendar.edom);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(EthiopianCalendar ethiopianCalendar) {
            int i = this.index;
            if (i == 0 || i == 2 || i == 3) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(EthiopianCalendar ethiopianCalendar) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(ethiopianCalendar.getEra() == EthiopianEra.AMETE_ALEM ? 15499 : 9999);
            }
            if (i == 2) {
                return Integer.valueOf(EthiopianCalendar.CALSYS.getLengthOfMonth(ethiopianCalendar.getEra(), ethiopianCalendar.getYear(), ethiopianCalendar.emonth));
            }
            if (i == 3) {
                return Integer.valueOf(EthiopianCalendar.CALSYS.getLengthOfYear(ethiopianCalendar.getEra(), ethiopianCalendar.getYear()));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianCalendar ethiopianCalendar, Integer num) {
            if (num == null) {
                return false;
            }
            return getMinimum(ethiopianCalendar).compareTo(num) <= 0 && getMaximum(ethiopianCalendar).compareTo(num) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianCalendar withValue2(EthiopianCalendar ethiopianCalendar, Integer num, boolean z) {
            if (!isValid2(ethiopianCalendar, num)) {
                throw new IllegalArgumentException("Out of range: " + num);
            }
            int i = this.index;
            if (i == 0) {
                EthiopianEra era = ethiopianCalendar.getEra();
                int intValue = num.intValue();
                return EthiopianCalendar.of(era, intValue, ethiopianCalendar.emonth, Math.min(ethiopianCalendar.edom, EthiopianCalendar.CALSYS.getLengthOfMonth(era, intValue, ethiopianCalendar.emonth)));
            }
            if (i == 2) {
                return new EthiopianCalendar(ethiopianCalendar.mihret, ethiopianCalendar.emonth, num.intValue());
            }
            if (i == 3) {
                return ethiopianCalendar.plus(CalendarDays.of(num.intValue() - getValue(ethiopianCalendar).intValue()));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianCalendar ethiopianCalendar) {
            if (this.index == 0) {
                return EthiopianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianCalendar ethiopianCalendar) {
            if (this.index == 0) {
                return EthiopianCalendar.MONTH_OF_YEAR;
            }
            return null;
        }
    }

    private static class MonthRule implements ElementRule<EthiopianCalendar, EthiopianMonth> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianCalendar ethiopianCalendar, EthiopianMonth ethiopianMonth) {
            return ethiopianMonth != null;
        }

        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianMonth getValue(EthiopianCalendar ethiopianCalendar) {
            return ethiopianCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianMonth getMinimum(EthiopianCalendar ethiopianCalendar) {
            return EthiopianMonth.MESKEREM;
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianMonth getMaximum(EthiopianCalendar ethiopianCalendar) {
            return EthiopianMonth.PAGUMEN;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianCalendar withValue2(EthiopianCalendar ethiopianCalendar, EthiopianMonth ethiopianMonth, boolean z) {
            if (ethiopianMonth == null) {
                throw new IllegalArgumentException("Missing month.");
            }
            int value = ethiopianMonth.getValue();
            return new EthiopianCalendar(ethiopianCalendar.mihret, value, Math.min(ethiopianCalendar.edom, EthiopianCalendar.CALSYS.getLengthOfMonth(ethiopianCalendar.getEra(), ethiopianCalendar.getYear(), value)));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianCalendar ethiopianCalendar) {
            return EthiopianCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianCalendar ethiopianCalendar) {
            return EthiopianCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<EthiopianCalendar, EthiopianEra> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianCalendar ethiopianCalendar, EthiopianEra ethiopianEra) {
            return ethiopianEra != null;
        }

        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianEra getValue(EthiopianCalendar ethiopianCalendar) {
            return ethiopianCalendar.getEra();
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianEra getMinimum(EthiopianCalendar ethiopianCalendar) {
            return EthiopianEra.AMETE_ALEM;
        }

        @Override // net.time4j.engine.ElementRule
        public EthiopianEra getMaximum(EthiopianCalendar ethiopianCalendar) {
            return EthiopianEra.AMETE_MIHRET;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianCalendar withValue2(EthiopianCalendar ethiopianCalendar, EthiopianEra ethiopianEra, boolean z) {
            if (ethiopianEra != null) {
                return ethiopianCalendar;
            }
            throw new IllegalArgumentException("Missing era value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianCalendar ethiopianCalendar) {
            return EthiopianCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianCalendar ethiopianCalendar) {
            return EthiopianCalendar.YEAR_OF_ERA;
        }
    }

    private static class EvangelistRule implements ElementRule<EthiopianCalendar, Evangelist> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianCalendar ethiopianCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianCalendar ethiopianCalendar) {
            return null;
        }

        private EvangelistRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public Evangelist getValue(EthiopianCalendar ethiopianCalendar) {
            return Evangelist.values()[(ethiopianCalendar.getYear() + 3) % 4];
        }

        @Override // net.time4j.engine.ElementRule
        public Evangelist getMinimum(EthiopianCalendar ethiopianCalendar) {
            return Evangelist.MATTHEW;
        }

        @Override // net.time4j.engine.ElementRule
        public Evangelist getMaximum(EthiopianCalendar ethiopianCalendar) {
            return ethiopianCalendar.mihret >= 9997 ? Evangelist.LUKE : Evangelist.JOHN;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianCalendar ethiopianCalendar, Evangelist evangelist) {
            return evangelist != null && evangelist.compareTo(getMaximum(ethiopianCalendar)) <= 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianCalendar withValue2(EthiopianCalendar ethiopianCalendar, Evangelist evangelist, boolean z) {
            if (evangelist == null) {
                throw new IllegalArgumentException("Missing evangelist.");
            }
            return (EthiopianCalendar) ethiopianCalendar.plus(evangelist.ordinal() - getValue(ethiopianCalendar).ordinal(), Unit.YEARS);
        }
    }

    private static class TabotRule implements ElementRule<EthiopianCalendar, Tabot> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianCalendar ethiopianCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianCalendar ethiopianCalendar) {
            return null;
        }

        private TabotRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public Tabot getValue(EthiopianCalendar ethiopianCalendar) {
            return Tabot.of(ethiopianCalendar.getDayOfMonth());
        }

        @Override // net.time4j.engine.ElementRule
        public Tabot getMinimum(EthiopianCalendar ethiopianCalendar) {
            return Tabot.of(1);
        }

        @Override // net.time4j.engine.ElementRule
        public Tabot getMaximum(EthiopianCalendar ethiopianCalendar) {
            return Tabot.of(((Integer) ethiopianCalendar.getMaximum(EthiopianCalendar.DAY_OF_MONTH)).intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianCalendar ethiopianCalendar, Tabot tabot) {
            return tabot != null && tabot.compareTo(getMaximum(ethiopianCalendar)) <= 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianCalendar withValue2(EthiopianCalendar ethiopianCalendar, Tabot tabot, boolean z) {
            if (tabot == null) {
                throw new IllegalArgumentException("Missing tabot.");
            }
            return (EthiopianCalendar) ethiopianCalendar.with((ChronoElement<Integer>) EthiopianCalendar.DAY_OF_MONTH, tabot.getDayOfMonth());
        }
    }

    private static class Merger implements ChronoMerger<EthiopianCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(EthiopianCalendar ethiopianCalendar, AttributeQuery attributeQuery) {
            return ethiopianCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ EthiopianCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ EthiopianCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("ethiopic", displayStyle, locale);
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public EthiopianCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (EthiopianCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(EthiopianCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public EthiopianCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i = chronoEntity.getInt(EthiopianCalendar.YEAR_OF_ERA);
            if (i == Integer.MIN_VALUE) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Ethiopian year.");
                return null;
            }
            if (!chronoEntity.contains(EthiopianCalendar.ERA)) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Ethiopian era.");
            }
            EthiopianEra ethiopianEra = (EthiopianEra) chronoEntity.get(EthiopianCalendar.ERA);
            if (chronoEntity.contains(EthiopianCalendar.MONTH_OF_YEAR)) {
                int value = ((EthiopianMonth) chronoEntity.get(EthiopianCalendar.MONTH_OF_YEAR)).getValue();
                int i2 = chronoEntity.getInt(EthiopianCalendar.DAY_OF_MONTH);
                if (i2 != Integer.MIN_VALUE) {
                    if (EthiopianCalendar.CALSYS.isValid(ethiopianEra, i, value, i2)) {
                        return EthiopianCalendar.of(ethiopianEra, i, value, i2);
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Ethiopian date.");
                }
            } else {
                int i3 = chronoEntity.getInt(EthiopianCalendar.DAY_OF_YEAR);
                if (i3 != Integer.MIN_VALUE) {
                    if (i3 > 0) {
                        int i4 = 0;
                        int i5 = 1;
                        while (i5 <= 13) {
                            int lengthOfMonth = EthiopianCalendar.CALSYS.getLengthOfMonth(ethiopianEra, i, i5) + i4;
                            if (i3 <= lengthOfMonth) {
                                return EthiopianCalendar.of(ethiopianEra, i, i5, i3 - i4);
                            }
                            i5++;
                            i4 = lengthOfMonth;
                        }
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Ethiopian date.");
                }
            }
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MORNING;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 8;
        }
    }

    private static class EthiopianUnitRule implements UnitRule<EthiopianCalendar> {
        private final Unit unit;

        EthiopianUnitRule(Unit unit) {
            this.unit = unit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public EthiopianCalendar addTo(EthiopianCalendar ethiopianCalendar, long j) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                j = MathUtils.safeMultiply(j, 13L);
            } else if (i != 2) {
                if (i == 3) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else if (i != 4) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                return (EthiopianCalendar) EthiopianCalendar.CALSYS.transform(MathUtils.safeAdd(EthiopianCalendar.CALSYS.transform((EraYearMonthDaySystem) ethiopianCalendar), j));
            }
            EthiopianEra ethiopianEra = EthiopianEra.AMETE_MIHRET;
            long safeAdd = MathUtils.safeAdd(ymValue(ethiopianCalendar), j);
            int safeCast = MathUtils.safeCast(MathUtils.floorDivide(safeAdd, 13));
            int floorModulo = MathUtils.floorModulo(safeAdd, 13) + 1;
            if (safeCast < 1) {
                ethiopianEra = EthiopianEra.AMETE_ALEM;
                safeCast += EthiopianCalendar.DELTA_ALEM_MIHRET;
            }
            return EthiopianCalendar.of(ethiopianEra, safeCast, floorModulo, Math.min(ethiopianCalendar.edom, EthiopianCalendar.CALSYS.getLengthOfMonth(ethiopianEra, safeCast, floorModulo)));
        }

        @Override // net.time4j.engine.UnitRule
        public long between(EthiopianCalendar ethiopianCalendar, EthiopianCalendar ethiopianCalendar2) {
            int between;
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                between = Unit.MONTHS.between(ethiopianCalendar, ethiopianCalendar2) / 13;
            } else {
                if (i == 2) {
                    long ymValue = ymValue(ethiopianCalendar2) - ymValue(ethiopianCalendar);
                    return (ymValue <= 0 || ethiopianCalendar2.edom >= ethiopianCalendar.edom) ? (ymValue >= 0 || ethiopianCalendar2.edom <= ethiopianCalendar.edom) ? ymValue : ymValue + 1 : ymValue - 1;
                }
                if (i != 3) {
                    if (i == 4) {
                        return EthiopianCalendar.CALSYS.transform((EraYearMonthDaySystem) ethiopianCalendar2) - EthiopianCalendar.CALSYS.transform((EraYearMonthDaySystem) ethiopianCalendar);
                    }
                    throw new UnsupportedOperationException(this.unit.name());
                }
                between = Unit.DAYS.between(ethiopianCalendar, ethiopianCalendar2) / 7;
            }
            return between;
        }

        private static int ymValue(EthiopianCalendar ethiopianCalendar) {
            return ((ethiopianCalendar.mihret * 13) + ethiopianCalendar.emonth) - 1;
        }
    }

    /* renamed from: net.time4j.calendar.EthiopianCalendar$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit[Unit.WEEKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$EthiopianCalendar$Unit[Unit.DAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static class SPX implements Externalizable {
        private static final int ETHIOPIAN_DATE = 4;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(4);
            writeEthiopianDate(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 4) {
                this.obj = readEthiopianDate(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeEthiopianDate(ObjectOutput objectOutput) throws IOException {
            EthiopianCalendar ethiopianCalendar = (EthiopianCalendar) this.obj;
            objectOutput.writeByte(ethiopianCalendar.getEra().ordinal());
            objectOutput.writeInt(ethiopianCalendar.getYear());
            objectOutput.writeByte(ethiopianCalendar.getMonth().getValue());
            objectOutput.writeByte(ethiopianCalendar.getDayOfMonth());
        }

        private EthiopianCalendar readEthiopianDate(ObjectInput objectInput) throws IOException {
            return EthiopianCalendar.of(EthiopianEra.values()[objectInput.readByte()], objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
        }
    }
}
