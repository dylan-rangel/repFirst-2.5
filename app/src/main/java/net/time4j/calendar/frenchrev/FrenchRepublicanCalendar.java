package net.time4j.calendar.frenchrev;

import androidx.exifinterface.media.ExifInterface;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.ParsePosition;
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
import net.time4j.calendar.StdCalendarElement;
import net.time4j.calendar.service.DualYearOfEraElement;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BasicElement;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.Calendrical;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
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
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSystem;
import net.time4j.format.OutputContext;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextElement;
import net.time4j.format.TextWidth;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("frenchrev")
/* loaded from: classes3.dex */
public final class FrenchRepublicanCalendar extends Calendrical<Unit, FrenchRepublicanCalendar> {
    private static final CalendarSystem<FrenchRepublicanCalendar> CALSYS;

    @FormattableElement(alt = "c", dynamic = true, format = "C")
    public static final ChronoElement<DayOfDecade> DAY_OF_DECADE;
    private static final DayOfDecadeAccess DAY_OF_DECADE_ACCESS;

    @FormattableElement(alt = "d", dynamic = true, format = "D")
    public static final StdCalendarElement<Integer, FrenchRepublicanCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(dynamic = true, format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, FrenchRepublicanCalendar> DAY_OF_WEEK;
    public static final StdCalendarElement<Integer, FrenchRepublicanCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final int DECADE_INDEX = 1;
    public static final StdCalendarElement<Integer, FrenchRepublicanCalendar> DECADE_OF_MONTH;
    private static final FrenchRepublicanAlgorithm DEFAULT_ALGORITHM;
    private static final TimeAxis<Unit, FrenchRepublicanCalendar> ENGINE;

    @FormattableElement(dynamic = true, format = "G")
    public static final ChronoElement<FrenchRepublicanEra> ERA;
    static final int MAX_YEAR = 1202;

    @FormattableElement(alt = "m", dynamic = true, format = "M")
    public static final StdCalendarElement<FrenchRepublicanMonth, FrenchRepublicanCalendar> MONTH_OF_YEAR;

    @FormattableElement(alt = "s", dynamic = true, format = ExifInterface.LATITUDE_SOUTH)
    public static final ChronoElement<Sansculottides> SANSCULOTTIDES;
    private static final SansculottidesAccess SANSCULOTTIDES_ACCESS;
    private static final int YEAR_INDEX = 0;

    @FormattableElement(alt = ViewHierarchyNode.JsonKeys.Y, dynamic = true, format = "Y")
    public static final StdCalendarElement<Integer, FrenchRepublicanCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = -6054794927532842783L;
    private final transient int fdoy;
    private final transient int fyear;

    public static boolean isValid(int i, int i2, int i3) {
        return i >= 1 && i <= MAX_YEAR && i2 >= 1 && i2 <= 12 && i3 >= 1 && i3 <= 30;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public FrenchRepublicanCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", FrenchRepublicanCalendar.class, FrenchRepublicanEra.class, 'G');
        ERA = stdEnumDateElement;
        AnonymousClass1 anonymousClass1 = null;
        YearOfEraElement yearOfEraElement = new YearOfEraElement(anonymousClass1);
        YEAR_OF_ERA = yearOfEraElement;
        SansculottidesAccess sansculottidesAccess = new SansculottidesAccess();
        SANSCULOTTIDES_ACCESS = sansculottidesAccess;
        DayOfDecadeAccess dayOfDecadeAccess = new DayOfDecadeAccess();
        DAY_OF_DECADE_ACCESS = dayOfDecadeAccess;
        SANSCULOTTIDES = sansculottidesAccess;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", FrenchRepublicanCalendar.class, FrenchRepublicanMonth.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("DECADE_OF_MONTH", FrenchRepublicanCalendar.class, 1, 3, (char) 0, null, null);
        DECADE_OF_MONTH = stdIntegerDateElement;
        DAY_OF_DECADE = dayOfDecadeAccess;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", FrenchRepublicanCalendar.class, 1, 30, 'D');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", FrenchRepublicanCalendar.class, 1, 365, (char) 0);
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(FrenchRepublicanCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        DEFAULT_ALGORITHM = FrenchRepublicanAlgorithm.EQUINOX;
        Transformer transformer = new Transformer(anonymousClass1);
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, FrenchRepublicanCalendar.class, new Merger(anonymousClass1), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new EraRule(anonymousClass1)).appendElement(yearOfEraElement, new IntegerRule(0), Unit.YEARS).appendElement((ChronoElement) sansculottidesAccess, (ElementRule) sansculottidesAccess).appendElement(stdEnumDateElement2, new MonthRule(anonymousClass1), Unit.MONTHS).appendElement(stdIntegerDateElement, new IntegerRule(1), Unit.DECADES).appendElement(stdIntegerDateElement2, new IntegerRule(2), Unit.DAYS).appendElement(stdIntegerDateElement3, new IntegerRule(3), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(anonymousClass1), Unit.DAYS).appendElement((ChronoElement) dayOfDecadeAccess, (ElementRule) dayOfDecadeAccess).appendUnit(Unit.YEARS, new FUnitRule(Unit.YEARS), Unit.YEARS.getLength()).appendUnit(Unit.MONTHS, new FUnitRule(Unit.MONTHS), Unit.MONTHS.getLength()).appendUnit(Unit.DECADES, new FUnitRule(Unit.DECADES), Unit.DECADES.getLength()).appendUnit(Unit.WEEKS, new FUnitRule(Unit.WEEKS), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, new FUnitRule(Unit.DAYS), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).build();
    }

    FrenchRepublicanCalendar(int i, int i2) {
        this.fyear = i;
        this.fdoy = i2;
    }

    public static FrenchRepublicanCalendar of(int i, FrenchRepublicanMonth frenchRepublicanMonth, int i2) {
        return of(i, frenchRepublicanMonth.getValue(), i2);
    }

    public static FrenchRepublicanCalendar of(int i, int i2, int i3) {
        if (i < 1 || i > MAX_YEAR || i2 < 1 || i2 > 12 || i3 < 1 || i3 > 30) {
            throw new IllegalArgumentException("Invalid French republican date: year=" + i + ", month=" + i2 + ", day=" + i3);
        }
        return new FrenchRepublicanCalendar(i, ((i2 - 1) * 30) + i3);
    }

    public static FrenchRepublicanCalendar of(int i, Sansculottides sansculottides) {
        if (i < 1 || i > MAX_YEAR) {
            throw new IllegalArgumentException("Year out of range: " + i);
        }
        if (sansculottides == Sansculottides.LEAP_DAY && !isLeapYear(i)) {
            throw new IllegalArgumentException("Day of Revolution only exists in leap years: " + i);
        }
        return new FrenchRepublicanCalendar(i, sansculottides.getValue() + 360);
    }

    public static FrenchRepublicanCalendar nowInSystemTime() {
        return (FrenchRepublicanCalendar) SystemClock.inLocalView().now(axis());
    }

    public FrenchRepublicanEra getEra() {
        return FrenchRepublicanEra.REPUBLICAN;
    }

    public int getYear() {
        return this.fyear;
    }

    public FrenchRepublicanMonth getMonth() {
        int i = this.fdoy;
        if (i > 360) {
            throw new ChronoException("Complementary days (sansculottides) do not represent any month: " + toString());
        }
        return FrenchRepublicanMonth.valueOf(((i - 1) / 30) + 1);
    }

    public int getDecade() {
        int i = this.fdoy;
        if (i > 360) {
            throw new ChronoException("Complementary days (sansculottides) do not represent any decade: " + toString());
        }
        return (((i - 1) % 30) / 10) + 1;
    }

    public int getDayOfMonth() {
        int i = this.fdoy;
        if (i > 360) {
            throw new ChronoException("Complementary days (sansculottides) are not part of any month: " + toString());
        }
        return ((i - 1) % 30) + 1;
    }

    public DayOfDecade getDayOfDecade() {
        if (hasSansculottides()) {
            throw new ChronoException("Day of decade does not exist on sansculottides: " + toString());
        }
        return DayOfDecade.valueOf(((this.fdoy - 1) % 10) + 1);
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(CALSYS.transform((CalendarSystem<FrenchRepublicanCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return this.fdoy;
    }

    public Sansculottides getSansculottides() {
        int i = this.fdoy;
        if (i <= 360) {
            throw new ChronoException("Not a sansculottides day: " + toString());
        }
        return Sansculottides.valueOf(i - 360);
    }

    public boolean hasSansculottides() {
        return this.fdoy > 360;
    }

    public boolean hasMonth() {
        return this.fdoy <= 360;
    }

    public boolean isLeapYear() {
        return isLeapYear(this.fyear);
    }

    public static boolean isLeapYear(int i) {
        return DEFAULT_ALGORITHM.isLeapYear(i);
    }

    public Date getDate(FrenchRepublicanAlgorithm frenchRepublicanAlgorithm) {
        FrenchRepublicanAlgorithm frenchRepublicanAlgorithm2 = DEFAULT_ALGORITHM;
        AnonymousClass1 anonymousClass1 = null;
        if (frenchRepublicanAlgorithm == frenchRepublicanAlgorithm2) {
            return new Date(this, frenchRepublicanAlgorithm2, anonymousClass1);
        }
        return new Date(frenchRepublicanAlgorithm.transform(frenchRepublicanAlgorithm2.transform(this)), frenchRepublicanAlgorithm, anonymousClass1);
    }

    public GeneralTimestamp<FrenchRepublicanCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<FrenchRepublicanCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    public FrenchRepublicanCalendar withEndOfFranciade() {
        int i = this.fyear;
        while (!isLeapYear(i)) {
            i++;
        }
        return new FrenchRepublicanCalendar(i, 366);
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FrenchRepublicanCalendar)) {
            return false;
        }
        FrenchRepublicanCalendar frenchRepublicanCalendar = (FrenchRepublicanCalendar) obj;
        return this.fyear == frenchRepublicanCalendar.fyear && this.fdoy == frenchRepublicanCalendar.fdoy;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.fdoy * 17) + (this.fyear * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("French-Republic-");
        sb.append(NumberSystem.ROMAN.toNumeral(this.fyear));
        sb.append('-');
        if (this.fdoy > 360) {
            sb.append("Sansculottides-");
            sb.append(String.valueOf(this.fdoy - 360));
        } else {
            int value = getMonth().getValue();
            if (value < 10) {
                sb.append('0');
            }
            sb.append(value);
            sb.append('-');
            int dayOfMonth = getDayOfMonth();
            if (dayOfMonth < 10) {
                sb.append('0');
            }
            sb.append(dayOfMonth);
        }
        return sb.toString();
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public boolean contains(ChronoElement<?> chronoElement) {
        if (chronoElement == MONTH_OF_YEAR || chronoElement == DECADE_OF_MONTH || chronoElement == DAY_OF_DECADE || chronoElement == DAY_OF_MONTH) {
            return hasMonth();
        }
        if (chronoElement == SANSCULOTTIDES) {
            return hasSansculottides();
        }
        if (getRegisteredElements().contains(chronoElement)) {
            return true;
        }
        return isAccessible(this, chronoElement);
    }

    @Override // net.time4j.engine.ChronoEntity
    public <V> boolean isValid(ChronoElement<V> chronoElement, V v) {
        if (chronoElement == MONTH_OF_YEAR) {
            return v != null;
        }
        if (chronoElement == SANSCULOTTIDES) {
            return SANSCULOTTIDES_ACCESS.isValid2(this, (Sansculottides) Sansculottides.class.cast(v));
        }
        return super.isValid((ChronoElement<ChronoElement<V>>) chronoElement, (ChronoElement<V>) v);
    }

    public static TimeAxis<Unit, FrenchRepublicanCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, FrenchRepublicanCalendar> getChronology() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1, Weekday.SUNDAY, Weekday.SUNDAY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <V> boolean isAccessible(FrenchRepublicanCalendar frenchRepublicanCalendar, ChronoElement<V> chronoElement) {
        try {
            return frenchRepublicanCalendar.isValid((ChronoElement<ChronoElement<V>>) chronoElement, (ChronoElement<V>) frenchRepublicanCalendar.get(chronoElement));
        } catch (ChronoException unused) {
            return false;
        }
    }

    private Object writeReplace() {
        return new SPX(this, 18);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.1556941113599997E7d),
        MONTHS(2592000.0d),
        DECADES(864000.0d),
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

        public long between(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanCalendar frenchRepublicanCalendar2) {
            return frenchRepublicanCalendar.until(frenchRepublicanCalendar2, (FrenchRepublicanCalendar) this);
        }
    }

    public static final class Date implements ChronoDisplay {
        private final FrenchRepublicanAlgorithm algorithm;
        private final FrenchRepublicanCalendar delegate;

        @Override // net.time4j.engine.ChronoDisplay
        public boolean hasTimezone() {
            return false;
        }

        /* synthetic */ Date(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanAlgorithm frenchRepublicanAlgorithm, AnonymousClass1 anonymousClass1) {
            this(frenchRepublicanCalendar, frenchRepublicanAlgorithm);
        }

        private Date(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanAlgorithm frenchRepublicanAlgorithm) {
            this.delegate = frenchRepublicanCalendar;
            this.algorithm = frenchRepublicanAlgorithm;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public boolean contains(ChronoElement<?> chronoElement) {
            return FrenchRepublicanCalendar.ENGINE.isRegistered(chronoElement);
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V get(ChronoElement<V> chronoElement) {
            if (chronoElement == FrenchRepublicanCalendar.DAY_OF_WEEK) {
                return chronoElement.getType().cast(Weekday.valueOf(MathUtils.floorModulo(this.algorithm.transform(this.delegate) + 5, 7) + 1));
            }
            if (!(chronoElement instanceof EpochDays)) {
                if (FrenchRepublicanCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                    return (V) this.delegate.get(chronoElement);
                }
                throw new ChronoException("French republican dates only support registered elements.");
            }
            return chronoElement.getType().cast(Long.valueOf(((EpochDays) EpochDays.class.cast(chronoElement)).transform(this.algorithm.transform(this.delegate), EpochDays.UTC)));
        }

        @Override // net.time4j.engine.ChronoDisplay
        public int getInt(ChronoElement<Integer> chronoElement) {
            if (FrenchRepublicanCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                return this.delegate.getInt(chronoElement);
            }
            return Integer.MIN_VALUE;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMinimum(ChronoElement<V> chronoElement) {
            if (FrenchRepublicanCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                return (V) this.delegate.getMinimum(chronoElement);
            }
            throw new ChronoException("French republican dates only support registered elements.");
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMaximum(ChronoElement<V> chronoElement) {
            if (FrenchRepublicanCalendar.ENGINE.isRegistered((ChronoElement<?>) chronoElement)) {
                return (V) this.delegate.getMaximum(chronoElement);
            }
            throw new ChronoException("French republican dates only support registered elements.");
        }

        @Override // net.time4j.engine.ChronoDisplay
        public TZID getTimezone() {
            throw new ChronoException("Timezone not available.");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Date)) {
                return false;
            }
            Date date = (Date) obj;
            if (this.algorithm != date.algorithm) {
                return false;
            }
            return this.delegate.equals(date.delegate);
        }

        public int hashCode() {
            return (this.delegate.hashCode() * 7) + (this.algorithm.hashCode() * 31);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.delegate);
            sb.append('[');
            sb.append(this.algorithm);
            sb.append(']');
            return sb.toString();
        }
    }

    private static class Transformer implements CalendarSystem<FrenchRepublicanCalendar> {
        private Transformer() {
        }

        /* synthetic */ Transformer(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.CalendarSystem
        public FrenchRepublicanCalendar transform(long j) {
            return FrenchRepublicanCalendar.DEFAULT_ALGORITHM.transform(j);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanCalendar.DEFAULT_ALGORITHM.transform(frenchRepublicanCalendar);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return transform(new FrenchRepublicanCalendar(1, 1));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return transform(new FrenchRepublicanCalendar(FrenchRepublicanCalendar.MAX_YEAR, 366));
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.singletonList(FrenchRepublicanEra.REPUBLICAN);
        }
    }

    private static class YearOfEraElement extends DualYearOfEraElement<FrenchRepublicanCalendar> {
        private static final long serialVersionUID = 7337125729623271040L;

        /* synthetic */ YearOfEraElement(AnonymousClass1 anonymousClass1) {
            this();
        }

        private YearOfEraElement() {
            super(FrenchRepublicanCalendar.class, 1, FrenchRepublicanCalendar.MAX_YEAR, 'Y');
        }

        @Override // net.time4j.calendar.service.DualYearOfEraElement
        protected NumberSystem getNumberSystem(AttributeQuery attributeQuery) {
            if (((String) attributeQuery.get(Attributes.FORMAT_PATTERN, "")).contains("Y") && ((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT)).getLanguage().equals("fr")) {
                return NumberSystem.ROMAN;
            }
            return (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ROMAN);
        }
    }

    private static class IntegerRule implements IntElementRule<FrenchRepublicanCalendar> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.IntElementRule
        public int getInt(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            int i = this.index;
            if (i == 0) {
                return frenchRepublicanCalendar.fyear;
            }
            if (i == 1) {
                return frenchRepublicanCalendar.getDecade();
            }
            if (i == 2) {
                return frenchRepublicanCalendar.getDayOfMonth();
            }
            if (i == 3) {
                return frenchRepublicanCalendar.fdoy;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(FrenchRepublicanCalendar frenchRepublicanCalendar, int i) {
            int i2 = this.index;
            if ((i2 == 2 || i2 == 1) && frenchRepublicanCalendar.hasSansculottides()) {
                return false;
            }
            return getMin(frenchRepublicanCalendar) <= i && getMax(frenchRepublicanCalendar) >= i;
        }

        @Override // net.time4j.engine.IntElementRule
        public FrenchRepublicanCalendar withValue(FrenchRepublicanCalendar frenchRepublicanCalendar, int i, boolean z) {
            if (this.index == 2 && frenchRepublicanCalendar.hasSansculottides()) {
                throw new IllegalArgumentException("Day of month not defined on sansculottides: " + i);
            }
            if (this.index == 1 && frenchRepublicanCalendar.hasSansculottides()) {
                throw new IllegalArgumentException("Decade of month not defined on sansculottides: " + i);
            }
            if (!isValid(frenchRepublicanCalendar, i)) {
                throw new IllegalArgumentException("Out of range: " + i);
            }
            int i2 = this.index;
            if (i2 == 0) {
                return new FrenchRepublicanCalendar(i, Math.min(frenchRepublicanCalendar.fdoy, FrenchRepublicanCalendar.DEFAULT_ALGORITHM.isLeapYear(i) ? 366 : 365));
            }
            if (i2 == 1) {
                return FrenchRepublicanCalendar.of(frenchRepublicanCalendar.fyear, frenchRepublicanCalendar.getMonth(), ((i - 1) * 10) + ((frenchRepublicanCalendar.getDayOfMonth() - 1) % 10) + 1);
            }
            if (i2 == 2) {
                return FrenchRepublicanCalendar.of(frenchRepublicanCalendar.fyear, frenchRepublicanCalendar.getMonth(), i);
            }
            if (i2 == 3) {
                return new FrenchRepublicanCalendar(frenchRepublicanCalendar.fyear, i);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return Integer.valueOf(getInt(frenchRepublicanCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return Integer.valueOf(getMin(frenchRepublicanCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return Integer.valueOf(getMax(frenchRepublicanCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, Integer num) {
            return num != null && isValid(frenchRepublicanCalendar, num.intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing new value.");
            }
            return withValue(frenchRepublicanCalendar, num.intValue(), z);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            int i = this.index;
            if (i == 0) {
                return FrenchRepublicanCalendar.MONTH_OF_YEAR;
            }
            if (i == 1) {
                return FrenchRepublicanCalendar.DAY_OF_DECADE;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            int i = this.index;
            if (i == 0) {
                return FrenchRepublicanCalendar.SANSCULOTTIDES;
            }
            if (i == 1) {
                return FrenchRepublicanCalendar.DAY_OF_DECADE;
            }
            return null;
        }

        private int getMin(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            int i = this.index;
            if (i != 0) {
                if (i == 1 || i == 2) {
                    if (!frenchRepublicanCalendar.hasSansculottides()) {
                        return 1;
                    }
                    throw new ChronoException("Complementary days (sansculottides) are not part of any month or decade: " + frenchRepublicanCalendar);
                }
                if (i != 3) {
                    throw new UnsupportedOperationException("Unknown element index: " + this.index);
                }
            }
            return 1;
        }

        private int getMax(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            int i = this.index;
            if (i == 0) {
                return FrenchRepublicanCalendar.MAX_YEAR;
            }
            if (i == 1 || i == 2) {
                if (!frenchRepublicanCalendar.hasSansculottides()) {
                    return this.index == 2 ? 30 : 3;
                }
                throw new ChronoException("Complementary days (sansculottides) are not part of any month: " + frenchRepublicanCalendar);
            }
            if (i == 3) {
                return FrenchRepublicanCalendar.DEFAULT_ALGORITHM.isLeapYear(frenchRepublicanCalendar.fyear) ? 366 : 365;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }
    }

    private static class MonthRule implements ElementRule<FrenchRepublicanCalendar, FrenchRepublicanMonth> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanMonth frenchRepublicanMonth) {
            return frenchRepublicanMonth != null;
        }

        private MonthRule() {
        }

        /* synthetic */ MonthRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanMonth getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return frenchRepublicanCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanMonth getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanMonth.VENDEMIAIRE;
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanMonth getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanMonth.FRUCTIDOR;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanMonth frenchRepublicanMonth, boolean z) {
            if (frenchRepublicanMonth != null) {
                return frenchRepublicanCalendar.hasSansculottides() ? FrenchRepublicanCalendar.of(frenchRepublicanCalendar.fyear, frenchRepublicanMonth, 30) : FrenchRepublicanCalendar.of(frenchRepublicanCalendar.fyear, frenchRepublicanMonth, frenchRepublicanCalendar.getDayOfMonth());
            }
            throw new IllegalArgumentException("Missing republican month.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<FrenchRepublicanCalendar, FrenchRepublicanEra> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanEra frenchRepublicanEra) {
            return frenchRepublicanEra != null;
        }

        private EraRule() {
        }

        /* synthetic */ EraRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanEra getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanEra.REPUBLICAN;
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanEra getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanEra.REPUBLICAN;
        }

        @Override // net.time4j.engine.ElementRule
        public FrenchRepublicanEra getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanEra.REPUBLICAN;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanEra frenchRepublicanEra, boolean z) {
            if (frenchRepublicanEra != null) {
                return frenchRepublicanCalendar;
            }
            throw new IllegalArgumentException("Missing era value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return FrenchRepublicanCalendar.YEAR_OF_ERA;
        }
    }

    private static class DayOfDecadeAccess extends BasicElement<DayOfDecade> implements TextElement<DayOfDecade>, ElementRule<FrenchRepublicanCalendar, DayOfDecade> {
        private static final long serialVersionUID = -8211850819064695450L;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'C';
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return true;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean isSingleton() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return false;
        }

        DayOfDecadeAccess() {
            super("DAY_OF_DECADE");
        }

        @Override // net.time4j.engine.ElementRule
        public DayOfDecade getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return frenchRepublicanCalendar.getDayOfDecade();
        }

        @Override // net.time4j.engine.ElementRule
        public DayOfDecade getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            if (frenchRepublicanCalendar.hasSansculottides()) {
                throw new ChronoException("Cannot get minimum for day of decade on sansculottides: " + frenchRepublicanCalendar);
            }
            return DayOfDecade.PRIMIDI;
        }

        @Override // net.time4j.engine.ElementRule
        public DayOfDecade getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            if (frenchRepublicanCalendar.hasSansculottides()) {
                throw new ChronoException("Cannot get maximum for day of decade on sansculottides: " + frenchRepublicanCalendar);
            }
            return DayOfDecade.DECADI;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, DayOfDecade dayOfDecade) {
            return (dayOfDecade == null || frenchRepublicanCalendar.hasSansculottides()) ? false : true;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, DayOfDecade dayOfDecade, boolean z) {
            if (dayOfDecade == null) {
                throw new IllegalArgumentException("Missing day of decade.");
            }
            if (frenchRepublicanCalendar.hasSansculottides()) {
                throw new IllegalArgumentException("Cannot set day of decade on sansculottides.");
            }
            int value = dayOfDecade.getValue() - (((frenchRepublicanCalendar.fdoy - 1) % 10) + 1);
            return value == 0 ? frenchRepublicanCalendar : new FrenchRepublicanCalendar(frenchRepublicanCalendar.fyear, frenchRepublicanCalendar.fdoy + value);
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            appendable.append(accessor((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), attributeQuery).print((DayOfDecade) chronoDisplay.get(this)));
        }

        @Override // net.time4j.format.TextElement
        public DayOfDecade parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
            return (DayOfDecade) accessor((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), attributeQuery).parse(charSequence, parsePosition, getType(), attributeQuery);
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<DayOfDecade> getType() {
            return DayOfDecade.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public DayOfDecade getDefaultMinimum() {
            return DayOfDecade.PRIMIDI;
        }

        @Override // net.time4j.engine.ChronoElement
        public DayOfDecade getDefaultMaximum() {
            return DayOfDecade.DECADI;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public String getDisplayName(Locale locale) {
            String str = CalendarText.getInstance("frenchrev", locale).getTextForms().get("L_dayofdecade");
            return str == null ? name() : str;
        }

        private TextAccessor accessor(Locale locale, AttributeQuery attributeQuery) {
            return CalendarText.getInstance("frenchrev", locale).getTextForms(name(), getType(), ((TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE)) == TextWidth.NARROW ? "N" : ((OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT)) == OutputContext.FORMAT ? "w" : ExifInterface.LONGITUDE_WEST);
        }
    }

    private static class SansculottidesAccess extends BasicElement<Sansculottides> implements TextElement<Sansculottides>, ElementRule<FrenchRepublicanCalendar, Sansculottides> {
        private static final long serialVersionUID = -6615947737325572130L;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'S';
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return true;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean isSingleton() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return false;
        }

        SansculottidesAccess() {
            super("SANSCULOTTIDES");
        }

        @Override // net.time4j.engine.ElementRule
        public Sansculottides getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return frenchRepublicanCalendar.getSansculottides();
        }

        @Override // net.time4j.engine.ElementRule
        public Sansculottides getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return Sansculottides.COMPLEMENTARY_DAY_1;
        }

        @Override // net.time4j.engine.ElementRule
        public Sansculottides getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return frenchRepublicanCalendar.isLeapYear() ? Sansculottides.LEAP_DAY : Sansculottides.COMPLEMENTARY_DAY_5;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, Sansculottides sansculottides) {
            if (sansculottides != null) {
                return frenchRepublicanCalendar.isLeapYear() || sansculottides != Sansculottides.LEAP_DAY;
            }
            return false;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, Sansculottides sansculottides, boolean z) {
            if (sansculottides != null) {
                return FrenchRepublicanCalendar.of(frenchRepublicanCalendar.fyear, sansculottides);
            }
            throw new IllegalArgumentException("Missing sansculottides value.");
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            appendable.append(accessor((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT)).print((Sansculottides) chronoDisplay.get(this)));
        }

        @Override // net.time4j.format.TextElement
        public Sansculottides parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
            int index = parsePosition.getIndex();
            Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
            OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
            Sansculottides sansculottides = (Sansculottides) accessor(locale, outputContext).parse(charSequence, parsePosition, getType(), attributeQuery);
            if (sansculottides != null || !((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue()) {
                return sansculottides;
            }
            parsePosition.setErrorIndex(-1);
            parsePosition.setIndex(index);
            return (Sansculottides) accessor(locale, outputContext == OutputContext.FORMAT ? OutputContext.STANDALONE : OutputContext.FORMAT).parse(charSequence, parsePosition, getType(), attributeQuery);
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<Sansculottides> getType() {
            return Sansculottides.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public Sansculottides getDefaultMinimum() {
            return Sansculottides.COMPLEMENTARY_DAY_1;
        }

        @Override // net.time4j.engine.ChronoElement
        public Sansculottides getDefaultMaximum() {
            return Sansculottides.COMPLEMENTARY_DAY_5;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public String getDisplayName(Locale locale) {
            String str = CalendarText.getInstance("frenchrev", locale).getTextForms().get("L_sansculottides");
            return str == null ? name() : str;
        }

        private TextAccessor accessor(Locale locale, OutputContext outputContext) {
            return CalendarText.getInstance("frenchrev", locale).getTextForms(name(), getType(), outputContext == OutputContext.FORMAT ? "w" : ExifInterface.LONGITUDE_WEST);
        }
    }

    private static class WeekdayRule implements ElementRule<FrenchRepublicanCalendar, Weekday> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return null;
        }

        private WeekdayRule() {
        }

        /* synthetic */ WeekdayRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return frenchRepublicanCalendar.getDayOfWeek();
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getMinimum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return (frenchRepublicanCalendar.fyear == 1 && frenchRepublicanCalendar.fdoy == 1) ? Weekday.SATURDAY : Weekday.SUNDAY;
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getMaximum(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return (frenchRepublicanCalendar.fyear == FrenchRepublicanCalendar.MAX_YEAR && frenchRepublicanCalendar.fdoy == 366) ? Weekday.SUNDAY : Weekday.SATURDAY;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(FrenchRepublicanCalendar frenchRepublicanCalendar, Weekday weekday) {
            if (weekday == null) {
                return false;
            }
            int value = weekday.getValue(FrenchRepublicanCalendar.getDefaultWeekmodel());
            return getMinimum(frenchRepublicanCalendar).getValue(FrenchRepublicanCalendar.getDefaultWeekmodel()) <= value && value <= getMaximum(frenchRepublicanCalendar).getValue(FrenchRepublicanCalendar.getDefaultWeekmodel());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar withValue2(FrenchRepublicanCalendar frenchRepublicanCalendar, Weekday weekday, boolean z) {
            if (weekday != null) {
                Weekmodel defaultWeekmodel = FrenchRepublicanCalendar.getDefaultWeekmodel();
                return frenchRepublicanCalendar.plus(CalendarDays.of(weekday.getValue(defaultWeekmodel) - frenchRepublicanCalendar.getDayOfWeek().getValue(defaultWeekmodel)));
            }
            throw new IllegalArgumentException("Missing weekday.");
        }
    }

    private static class Merger implements ChronoMerger<FrenchRepublicanCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        /* synthetic */ Merger(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ FrenchRepublicanCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ FrenchRepublicanCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (FrenchRepublicanCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(FrenchRepublicanCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public FrenchRepublicanCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i;
            FrenchRepublicanAlgorithm frenchRepublicanAlgorithm = (FrenchRepublicanAlgorithm) attributeQuery.get(FrenchRepublicanAlgorithm.attribute(), FrenchRepublicanCalendar.DEFAULT_ALGORITHM);
            int i2 = chronoEntity.getInt(FrenchRepublicanCalendar.YEAR_OF_ERA);
            FrenchRepublicanCalendar frenchRepublicanCalendar = null;
            if (i2 == Integer.MIN_VALUE) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing republican year.");
                return null;
            }
            if (i2 < 1 || i2 > FrenchRepublicanCalendar.MAX_YEAR) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("Republican year out of range: " + i2));
                return null;
            }
            if (chronoEntity.contains(FrenchRepublicanCalendar.MONTH_OF_YEAR)) {
                int value = ((FrenchRepublicanMonth) chronoEntity.get(FrenchRepublicanCalendar.MONTH_OF_YEAR)).getValue();
                int i3 = chronoEntity.getInt(FrenchRepublicanCalendar.DAY_OF_MONTH);
                if (i3 == Integer.MIN_VALUE && chronoEntity.contains(FrenchRepublicanCalendar.DAY_OF_DECADE) && (i = chronoEntity.getInt(FrenchRepublicanCalendar.DECADE_OF_MONTH)) != Integer.MIN_VALUE) {
                    i3 = ((DayOfDecade) chronoEntity.get(FrenchRepublicanCalendar.DAY_OF_DECADE)).getValue() + ((i - 1) * 10);
                }
                if (i3 != Integer.MIN_VALUE) {
                    if (i3 >= 1 && i3 <= 30) {
                        frenchRepublicanCalendar = FrenchRepublicanCalendar.of(i2, value, i3);
                    } else {
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid republican date.");
                    }
                }
            } else if (chronoEntity.contains(FrenchRepublicanCalendar.SANSCULOTTIDES)) {
                int value2 = ((Sansculottides) chronoEntity.get(FrenchRepublicanCalendar.SANSCULOTTIDES)).getValue() + 360;
                if (value2 == 6 && !frenchRepublicanAlgorithm.isLeapYear(i2)) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Republican date is no leap year.");
                } else {
                    frenchRepublicanCalendar = new FrenchRepublicanCalendar(i2, value2);
                }
            } else {
                int i4 = chronoEntity.getInt(FrenchRepublicanCalendar.DAY_OF_YEAR);
                if (i4 != Integer.MIN_VALUE) {
                    if (i4 >= 1) {
                        if (i4 <= (frenchRepublicanAlgorithm.isLeapYear(i2) ? 366 : 365)) {
                            frenchRepublicanCalendar = new FrenchRepublicanCalendar(i2, i4);
                        }
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid republican date.");
                }
            }
            return (frenchRepublicanCalendar == null || frenchRepublicanAlgorithm == FrenchRepublicanCalendar.DEFAULT_ALGORITHM) ? frenchRepublicanCalendar : FrenchRepublicanCalendar.DEFAULT_ALGORITHM.transform(frenchRepublicanAlgorithm.transform(frenchRepublicanCalendar));
        }

        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(FrenchRepublicanCalendar frenchRepublicanCalendar, AttributeQuery attributeQuery) {
            FrenchRepublicanAlgorithm frenchRepublicanAlgorithm = (FrenchRepublicanAlgorithm) attributeQuery.get(FrenchRepublicanAlgorithm.attribute(), FrenchRepublicanCalendar.DEFAULT_ALGORITHM);
            return frenchRepublicanAlgorithm == FrenchRepublicanCalendar.DEFAULT_ALGORITHM ? frenchRepublicanCalendar : frenchRepublicanCalendar.getDate(frenchRepublicanAlgorithm);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            throw new UnsupportedOperationException("Localized format patterns are not available.");
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 1792;
        }
    }

    private static class FUnitRule implements UnitRule<FrenchRepublicanCalendar> {
        private final Unit unit;

        FUnitRule(Unit unit) {
            this.unit = unit;
        }

        @Override // net.time4j.engine.UnitRule
        public FrenchRepublicanCalendar addTo(FrenchRepublicanCalendar frenchRepublicanCalendar, long j) {
            int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                int safeCast = MathUtils.safeCast(MathUtils.safeAdd(frenchRepublicanCalendar.fyear, j));
                if (safeCast < 1 || safeCast > FrenchRepublicanCalendar.MAX_YEAR) {
                    throw new IllegalArgumentException("Resulting year out of bounds: " + safeCast);
                }
                return new FrenchRepublicanCalendar(safeCast, Math.min(frenchRepublicanCalendar.fdoy, FrenchRepublicanCalendar.isLeapYear(safeCast) ? 366 : 365));
            }
            if (i == 2) {
                long safeAdd = MathUtils.safeAdd(ymValue(frenchRepublicanCalendar), j);
                return FrenchRepublicanCalendar.of(MathUtils.safeCast(MathUtils.floorDivide(safeAdd, 12)), MathUtils.floorModulo(safeAdd, 12) + 1, frenchRepublicanCalendar.hasSansculottides() ? 30 : frenchRepublicanCalendar.getDayOfMonth());
            }
            if (i != 3) {
                if (i == 4) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else if (i != 5) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                return (FrenchRepublicanCalendar) FrenchRepublicanCalendar.CALSYS.transform(MathUtils.safeAdd(FrenchRepublicanCalendar.CALSYS.transform((CalendarSystem) frenchRepublicanCalendar), j));
            }
            long safeAdd2 = MathUtils.safeAdd(decValue(frenchRepublicanCalendar), j);
            int safeCast2 = MathUtils.safeCast(MathUtils.floorDivide(safeAdd2, 36));
            int floorModulo = MathUtils.floorModulo(safeAdd2, 36);
            return FrenchRepublicanCalendar.of(safeCast2, MathUtils.floorDivide(floorModulo, 3) + 1, (MathUtils.floorModulo(floorModulo, 3) * 10) + (((frenchRepublicanCalendar.hasSansculottides() ? 30 : frenchRepublicanCalendar.getDayOfMonth()) - 1) % 10) + 1);
        }

        @Override // net.time4j.engine.UnitRule
        public long between(FrenchRepublicanCalendar frenchRepublicanCalendar, FrenchRepublicanCalendar frenchRepublicanCalendar2) {
            int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[this.unit.ordinal()];
            if (i == 1) {
                int i2 = frenchRepublicanCalendar2.fyear - frenchRepublicanCalendar.fyear;
                if (i2 > 0 && frenchRepublicanCalendar2.fdoy < frenchRepublicanCalendar.fdoy) {
                    i2--;
                } else if (i2 < 0 && frenchRepublicanCalendar2.fdoy > frenchRepublicanCalendar.fdoy) {
                    i2++;
                }
                return i2;
            }
            if (i == 2) {
                long ymValue = ymValue(frenchRepublicanCalendar2) - ymValue(frenchRepublicanCalendar);
                int dayOfMonth = frenchRepublicanCalendar.hasSansculottides() ? frenchRepublicanCalendar.fdoy - 330 : frenchRepublicanCalendar.getDayOfMonth();
                int dayOfMonth2 = frenchRepublicanCalendar2.hasSansculottides() ? frenchRepublicanCalendar2.fdoy - 330 : frenchRepublicanCalendar2.getDayOfMonth();
                return (ymValue <= 0 || dayOfMonth2 >= dayOfMonth) ? (ymValue >= 0 || dayOfMonth2 <= dayOfMonth) ? ymValue : ymValue + 1 : ymValue - 1;
            }
            if (i == 3) {
                long decValue = decValue(frenchRepublicanCalendar2) - decValue(frenchRepublicanCalendar);
                int value = frenchRepublicanCalendar.hasSansculottides() ? frenchRepublicanCalendar.fdoy - 350 : frenchRepublicanCalendar.getDayOfDecade().getValue();
                int value2 = frenchRepublicanCalendar2.hasSansculottides() ? frenchRepublicanCalendar2.fdoy - 350 : frenchRepublicanCalendar2.getDayOfDecade().getValue();
                return (decValue <= 0 || value2 >= value) ? (decValue >= 0 || value2 <= value) ? decValue : decValue + 1 : decValue - 1;
            }
            if (i == 4) {
                return Unit.DAYS.between(frenchRepublicanCalendar, frenchRepublicanCalendar2) / 7;
            }
            if (i == 5) {
                return FrenchRepublicanCalendar.CALSYS.transform((CalendarSystem) frenchRepublicanCalendar2) - FrenchRepublicanCalendar.CALSYS.transform((CalendarSystem) frenchRepublicanCalendar);
            }
            throw new UnsupportedOperationException(this.unit.name());
        }

        private static int ymValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return ((frenchRepublicanCalendar.fyear * 12) + (frenchRepublicanCalendar.hasSansculottides() ? 12 : frenchRepublicanCalendar.getMonth().getValue())) - 1;
        }

        private static int decValue(FrenchRepublicanCalendar frenchRepublicanCalendar) {
            return ((ymValue(frenchRepublicanCalendar) * 3) + (frenchRepublicanCalendar.hasSansculottides() ? 3 : frenchRepublicanCalendar.getDecade())) - 1;
        }
    }

    /* renamed from: net.time4j.calendar.frenchrev.FrenchRepublicanCalendar$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[Unit.DECADES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[Unit.WEEKS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$calendar$frenchrev$FrenchRepublicanCalendar$Unit[Unit.DAYS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}
