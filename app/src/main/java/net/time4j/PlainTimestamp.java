package net.time4j;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.time4j.base.GregorianDate;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BridgeChronology;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.Chronology;
import net.time4j.engine.Converter;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FlagElement;
import net.time4j.engine.Normalizer;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.Temporal;
import net.time4j.engine.TimeAxis;
import net.time4j.engine.TimeMetric;
import net.time4j.engine.TimePoint;
import net.time4j.engine.TimeSpan;
import net.time4j.engine.UnitRule;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.DisplayMode;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.TemporalFormatter;
import net.time4j.scale.TimeScale;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.TransitionStrategy;
import net.time4j.tz.ZonalOffset;

@CalendarType(CalendarText.ISO_CALENDAR_TYPE)
/* loaded from: classes3.dex */
public final class PlainTimestamp extends TimePoint<IsoUnit, PlainTimestamp> implements GregorianDate, WallTime, Temporal<PlainTimestamp>, Normalizer<IsoUnit>, LocalizedPatternSupport {
    private static final Map<Object, ChronoElement<?>> CHILDREN;
    private static final TimeAxis<IsoUnit, PlainTimestamp> ENGINE;
    private static final PlainTimestamp MAX;
    private static final PlainTimestamp MIN;
    private static final int MRD = 1000000000;
    private static final TimeMetric<IsoUnit, Duration<IsoUnit>> STD_METRIC;
    private static final long serialVersionUID = 7458380065762437714L;
    private final transient PlainDate date;
    private final transient PlainTime time;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public PlainTimestamp getContext() {
        return this;
    }

    static {
        PlainTimestamp plainTimestamp = new PlainTimestamp(PlainDate.MIN, PlainTime.MIN);
        MIN = plainTimestamp;
        PlainTimestamp plainTimestamp2 = new PlainTimestamp(PlainDate.MAX, PlainTime.WALL_TIME.getDefaultMaximum());
        MAX = plainTimestamp2;
        HashMap hashMap = new HashMap();
        hashMap.put(PlainDate.CALENDAR_DATE, PlainTime.WALL_TIME);
        hashMap.put(PlainDate.YEAR, PlainDate.MONTH_AS_NUMBER);
        hashMap.put(PlainDate.YEAR_OF_WEEKDATE, Weekmodel.ISO.weekOfYear());
        hashMap.put(PlainDate.QUARTER_OF_YEAR, PlainDate.DAY_OF_QUARTER);
        hashMap.put(PlainDate.MONTH_OF_YEAR, PlainDate.DAY_OF_MONTH);
        hashMap.put(PlainDate.MONTH_AS_NUMBER, PlainDate.DAY_OF_MONTH);
        hashMap.put(PlainDate.DAY_OF_MONTH, PlainTime.WALL_TIME);
        hashMap.put(PlainDate.DAY_OF_WEEK, PlainTime.WALL_TIME);
        hashMap.put(PlainDate.DAY_OF_YEAR, PlainTime.WALL_TIME);
        hashMap.put(PlainDate.DAY_OF_QUARTER, PlainTime.WALL_TIME);
        hashMap.put(PlainDate.WEEKDAY_IN_MONTH, PlainTime.WALL_TIME);
        hashMap.put(PlainTime.AM_PM_OF_DAY, PlainTime.DIGITAL_HOUR_OF_AMPM);
        hashMap.put(PlainTime.CLOCK_HOUR_OF_AMPM, PlainTime.MINUTE_OF_HOUR);
        hashMap.put(PlainTime.CLOCK_HOUR_OF_DAY, PlainTime.MINUTE_OF_HOUR);
        hashMap.put(PlainTime.DIGITAL_HOUR_OF_AMPM, PlainTime.MINUTE_OF_HOUR);
        hashMap.put(PlainTime.DIGITAL_HOUR_OF_DAY, PlainTime.MINUTE_OF_HOUR);
        hashMap.put(PlainTime.HOUR_FROM_0_TO_24, PlainTime.MINUTE_OF_HOUR);
        hashMap.put(PlainTime.MINUTE_OF_HOUR, PlainTime.SECOND_OF_MINUTE);
        hashMap.put(PlainTime.MINUTE_OF_DAY, PlainTime.SECOND_OF_MINUTE);
        hashMap.put(PlainTime.SECOND_OF_MINUTE, PlainTime.NANO_OF_SECOND);
        hashMap.put(PlainTime.SECOND_OF_DAY, PlainTime.NANO_OF_SECOND);
        CHILDREN = Collections.unmodifiableMap(hashMap);
        TimeAxis.Builder appendElement = TimeAxis.Builder.setUp(IsoUnit.class, PlainTimestamp.class, new Merger(null), plainTimestamp, plainTimestamp2).appendElement(PlainDate.CALENDAR_DATE, FieldRule.of(PlainDate.CALENDAR_DATE), CalendarUnit.DAYS).appendElement(PlainDate.YEAR, FieldRule.of(PlainDate.YEAR), CalendarUnit.YEARS).appendElement(PlainDate.YEAR_OF_WEEKDATE, FieldRule.of(PlainDate.YEAR_OF_WEEKDATE), Weekcycle.YEARS).appendElement(PlainDate.QUARTER_OF_YEAR, FieldRule.of(PlainDate.QUARTER_OF_YEAR), CalendarUnit.QUARTERS).appendElement(PlainDate.MONTH_OF_YEAR, FieldRule.of(PlainDate.MONTH_OF_YEAR), CalendarUnit.MONTHS).appendElement(PlainDate.MONTH_AS_NUMBER, FieldRule.of(PlainDate.MONTH_AS_NUMBER), CalendarUnit.MONTHS).appendElement(PlainDate.DAY_OF_MONTH, FieldRule.of(PlainDate.DAY_OF_MONTH), CalendarUnit.DAYS).appendElement(PlainDate.DAY_OF_WEEK, FieldRule.of(PlainDate.DAY_OF_WEEK), CalendarUnit.DAYS).appendElement(PlainDate.DAY_OF_YEAR, FieldRule.of(PlainDate.DAY_OF_YEAR), CalendarUnit.DAYS).appendElement(PlainDate.DAY_OF_QUARTER, FieldRule.of(PlainDate.DAY_OF_QUARTER), CalendarUnit.DAYS).appendElement(PlainDate.WEEKDAY_IN_MONTH, FieldRule.of(PlainDate.WEEKDAY_IN_MONTH), CalendarUnit.WEEKS).appendElement((ChronoElement) PlainTime.WALL_TIME, (ElementRule) FieldRule.of(PlainTime.WALL_TIME)).appendElement((ChronoElement) PlainTime.AM_PM_OF_DAY, (ElementRule) FieldRule.of(PlainTime.AM_PM_OF_DAY)).appendElement(PlainTime.CLOCK_HOUR_OF_AMPM, FieldRule.of(PlainTime.CLOCK_HOUR_OF_AMPM), ClockUnit.HOURS).appendElement(PlainTime.CLOCK_HOUR_OF_DAY, FieldRule.of(PlainTime.CLOCK_HOUR_OF_DAY), ClockUnit.HOURS).appendElement(PlainTime.DIGITAL_HOUR_OF_AMPM, FieldRule.of(PlainTime.DIGITAL_HOUR_OF_AMPM), ClockUnit.HOURS).appendElement(PlainTime.DIGITAL_HOUR_OF_DAY, FieldRule.of(PlainTime.DIGITAL_HOUR_OF_DAY), ClockUnit.HOURS).appendElement(PlainTime.HOUR_FROM_0_TO_24, FieldRule.of(PlainTime.HOUR_FROM_0_TO_24), ClockUnit.HOURS).appendElement(PlainTime.MINUTE_OF_HOUR, FieldRule.of(PlainTime.MINUTE_OF_HOUR), ClockUnit.MINUTES).appendElement(PlainTime.MINUTE_OF_DAY, FieldRule.of(PlainTime.MINUTE_OF_DAY), ClockUnit.MINUTES).appendElement(PlainTime.SECOND_OF_MINUTE, FieldRule.of(PlainTime.SECOND_OF_MINUTE), ClockUnit.SECONDS).appendElement(PlainTime.SECOND_OF_DAY, FieldRule.of(PlainTime.SECOND_OF_DAY), ClockUnit.SECONDS).appendElement(PlainTime.MILLI_OF_SECOND, FieldRule.of(PlainTime.MILLI_OF_SECOND), ClockUnit.MILLIS).appendElement(PlainTime.MICRO_OF_SECOND, FieldRule.of(PlainTime.MICRO_OF_SECOND), ClockUnit.MICROS).appendElement(PlainTime.NANO_OF_SECOND, FieldRule.of(PlainTime.NANO_OF_SECOND), ClockUnit.NANOS).appendElement(PlainTime.MILLI_OF_DAY, FieldRule.of(PlainTime.MILLI_OF_DAY), ClockUnit.MILLIS).appendElement(PlainTime.MICRO_OF_DAY, FieldRule.of(PlainTime.MICRO_OF_DAY), ClockUnit.MICROS).appendElement(PlainTime.NANO_OF_DAY, FieldRule.of(PlainTime.NANO_OF_DAY), ClockUnit.NANOS).appendElement((ChronoElement) PlainTime.DECIMAL_HOUR, (ElementRule) new DecimalRule(PlainTime.DECIMAL_HOUR)).appendElement((ChronoElement) PlainTime.DECIMAL_MINUTE, (ElementRule) new DecimalRule(PlainTime.DECIMAL_MINUTE)).appendElement((ChronoElement) PlainTime.DECIMAL_SECOND, (ElementRule) new DecimalRule(PlainTime.DECIMAL_SECOND)).appendElement((ChronoElement) PlainTime.PRECISION, (ElementRule) FieldRule.of(PlainTime.PRECISION));
        registerCalendarUnits(appendElement);
        registerClockUnits(appendElement);
        registerExtensions(appendElement);
        ENGINE = appendElement.build();
        STD_METRIC = Duration.in(CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.DAYS, ClockUnit.HOURS, ClockUnit.MINUTES, ClockUnit.SECONDS, ClockUnit.NANOS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private PlainTimestamp(PlainDate plainDate, PlainTime plainTime) {
        if (plainTime.getHour() == 24) {
            this.date = (PlainDate) plainDate.plus(1L, CalendarUnit.DAYS);
            this.time = PlainTime.MIN;
        } else {
            if (plainDate == null) {
                throw new NullPointerException("Missing date.");
            }
            this.date = plainDate;
            this.time = plainTime;
        }
    }

    public static PlainTimestamp of(PlainDate plainDate, PlainTime plainTime) {
        return new PlainTimestamp(plainDate, plainTime);
    }

    public static PlainTimestamp of(int i, int i2, int i3, int i4, int i5) {
        return of(i, i2, i3, i4, i5, 0);
    }

    public static PlainTimestamp of(int i, int i2, int i3, int i4, int i5, int i6) {
        return of(PlainDate.of(i, i2, i3), PlainTime.of(i4, i5, i6));
    }

    public static PlainTimestamp nowInSystemTime() {
        return ZonalClock.ofSystem().now();
    }

    public PlainDate getCalendarDate() {
        return this.date;
    }

    public PlainTime getWallTime() {
        return this.time;
    }

    @Override // net.time4j.base.GregorianDate
    public int getYear() {
        return this.date.getYear();
    }

    @Override // net.time4j.base.GregorianDate
    public int getMonth() {
        return this.date.getMonth();
    }

    @Override // net.time4j.base.GregorianDate
    public int getDayOfMonth() {
        return this.date.getDayOfMonth();
    }

    @Override // net.time4j.base.WallTime
    public int getHour() {
        return this.time.getHour();
    }

    @Override // net.time4j.base.WallTime
    public int getMinute() {
        return this.time.getMinute();
    }

    @Override // net.time4j.base.WallTime
    public int getSecond() {
        return this.time.getSecond();
    }

    @Override // net.time4j.base.WallTime
    public int getNanosecond() {
        return this.time.getNanosecond();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlainTimestamp with(ElementOperator<?> elementOperator) {
        return (PlainTimestamp) with(elementOperator.onTimestamp());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlainTimestamp with(PlainDate plainDate) {
        return (PlainTimestamp) with((ChronoElement<ChronoElement<PlainDate>>) PlainDate.CALENDAR_DATE, (ChronoElement<PlainDate>) plainDate);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlainTimestamp with(PlainTime plainTime) {
        return (PlainTimestamp) with((ChronoElement<ChronoElement<PlainTime>>) PlainTime.WALL_TIME, (ChronoElement<PlainTime>) plainTime);
    }

    @Override // net.time4j.engine.Temporal
    public boolean isBefore(PlainTimestamp plainTimestamp) {
        return compareTo(plainTimestamp) < 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isAfter(PlainTimestamp plainTimestamp) {
        return compareTo(plainTimestamp) > 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isSimultaneous(PlainTimestamp plainTimestamp) {
        return compareTo(plainTimestamp) == 0;
    }

    @Override // net.time4j.engine.TimePoint
    public int compareTo(PlainTimestamp plainTimestamp) {
        if (this.date.isAfter((CalendarDate) plainTimestamp.date)) {
            return 1;
        }
        if (this.date.isBefore((CalendarDate) plainTimestamp.date)) {
            return -1;
        }
        return this.time.compareTo(plainTimestamp.time);
    }

    @Override // net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlainTimestamp)) {
            return false;
        }
        PlainTimestamp plainTimestamp = (PlainTimestamp) obj;
        return this.date.equals(plainTimestamp.date) && this.time.equals(plainTimestamp.time);
    }

    @Override // net.time4j.engine.TimePoint
    public int hashCode() {
        return (this.date.hashCode() * 13) + (this.time.hashCode() * 37);
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        return this.date.toString() + this.time.toString();
    }

    public PlainDate toDate() {
        return this.date;
    }

    public PlainTime toTime() {
        return this.time;
    }

    public String print(TemporalFormatter<PlainTimestamp> temporalFormatter) {
        return temporalFormatter.print(this);
    }

    public static PlainTimestamp parse(String str, TemporalFormatter<PlainTimestamp> temporalFormatter) {
        try {
            return temporalFormatter.parse(str);
        } catch (ParseException e) {
            throw new ChronoException(e.getMessage(), e);
        }
    }

    public static TimeAxis<IsoUnit, PlainTimestamp> axis() {
        return ENGINE;
    }

    public static <S> Chronology<S> axis(Converter<S, PlainTimestamp> converter) {
        return new BridgeChronology(converter, ENGINE);
    }

    public Moment atUTC() {
        return at(ZonalOffset.UTC);
    }

    public Moment at(ZonalOffset zonalOffset) {
        long safeMultiply = MathUtils.safeMultiply(this.date.getDaysSinceUTC() + 730, 86400L) + (this.time.getHour() * 3600) + (this.time.getMinute() * 60) + this.time.getSecond();
        long integralAmount = safeMultiply - zonalOffset.getIntegralAmount();
        int nanosecond = this.time.getNanosecond() - zonalOffset.getFractionalAmount();
        if (nanosecond < 0) {
            nanosecond += 1000000000;
            integralAmount--;
        } else if (nanosecond >= 1000000000) {
            nanosecond -= 1000000000;
            integralAmount++;
        }
        return Moment.of(integralAmount, nanosecond, TimeScale.POSIX);
    }

    public Moment inStdTimezone() {
        return in(Timezone.ofSystem());
    }

    public Moment inTimezone(TZID tzid) {
        return in(Timezone.of(tzid));
    }

    public Moment in(Timezone timezone) {
        if (timezone.isFixed()) {
            return at(timezone.getOffset(this.date, this.time));
        }
        TransitionStrategy strategy = timezone.getStrategy();
        long resolve = strategy.resolve(this.date, this.time, timezone);
        Moment of = Moment.of(resolve, this.time.getNanosecond(), TimeScale.POSIX);
        if (strategy == Timezone.STRICT_MODE) {
            Moment.checkNegativeLS(resolve, this);
        }
        return of;
    }

    public ZonalDateTime inLocalView() {
        return inZonalView(Timezone.ofSystem());
    }

    public ZonalDateTime inZonalView(Timezone timezone) {
        return ZonalDateTime.of(in(timezone), timezone);
    }

    public boolean isValid(TZID tzid) {
        if (tzid == null) {
            return false;
        }
        return !Timezone.of(tzid).isInvalid(this.date, this.time);
    }

    @Override // net.time4j.engine.Normalizer
    /* renamed from: normalize */
    public TimeSpan<IsoUnit> normalize2(TimeSpan<? extends IsoUnit> timeSpan) {
        return (Duration) until(plus(timeSpan), (TimeMetric) STD_METRIC);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<IsoUnit, PlainTimestamp> getChronology() {
        return ENGINE;
    }

    static PlainTimestamp from(UnixTime unixTime, ZonalOffset zonalOffset) {
        long posixTime = unixTime.getPosixTime() + zonalOffset.getIntegralAmount();
        int nanosecond = unixTime.getNanosecond() + zonalOffset.getFractionalAmount();
        if (nanosecond < 0) {
            nanosecond += 1000000000;
            posixTime--;
        } else if (nanosecond >= 1000000000) {
            nanosecond -= 1000000000;
            posixTime++;
        }
        PlainDate of = PlainDate.of(MathUtils.floorDivide(posixTime, 86400), EpochDays.UNIX);
        int floorModulo = MathUtils.floorModulo(posixTime, 86400);
        int i = floorModulo % 60;
        int i2 = floorModulo / 60;
        return of(of, PlainTime.of(i2 / 60, i2 % 60, i, nanosecond));
    }

    private static void registerCalendarUnits(TimeAxis.Builder<IsoUnit, PlainTimestamp> builder) {
        Set<? extends IsoUnit> range = EnumSet.range(CalendarUnit.MILLENNIA, CalendarUnit.MONTHS);
        Set<? extends IsoUnit> range2 = EnumSet.range(CalendarUnit.WEEKS, CalendarUnit.DAYS);
        for (CalendarUnit calendarUnit : CalendarUnit.values()) {
            builder.appendUnit(calendarUnit, new CompositeUnitRule(calendarUnit), calendarUnit.getLength(), calendarUnit.compareTo(CalendarUnit.WEEKS) < 0 ? range : range2);
        }
    }

    private static void registerClockUnits(TimeAxis.Builder<IsoUnit, PlainTimestamp> builder) {
        for (ClockUnit clockUnit : ClockUnit.values()) {
            builder.appendUnit(clockUnit, new CompositeUnitRule(clockUnit), clockUnit.getLength(), EnumSet.allOf(ClockUnit.class));
        }
    }

    private static void registerExtensions(TimeAxis.Builder<IsoUnit, PlainTimestamp> builder) {
        Iterator<ChronoExtension> it = PlainDate.axis().getExtensions().iterator();
        while (it.hasNext()) {
            builder.appendExtension(it.next());
        }
        Iterator<ChronoExtension> it2 = PlainTime.axis().getExtensions().iterator();
        while (it2.hasNext()) {
            builder.appendExtension(it2.next());
        }
    }

    private Object writeReplace() {
        return new SPX(this, 8);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class Merger implements ChronoMerger<PlainTimestamp> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(PlainTimestamp plainTimestamp, AttributeQuery attributeQuery) {
            return plainTimestamp;
        }

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
        public /* bridge */ /* synthetic */ PlainTimestamp createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ PlainTimestamp createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            DisplayMode ofStyle = DisplayMode.ofStyle(displayStyle.getStyleValue());
            return CalendarText.patternForTimestamp(ofStyle, ofStyle, locale);
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PlainTimestamp createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            Timezone timezone;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                timezone = Timezone.of((TZID) attributeQuery.get(Attributes.TIMEZONE_ID));
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                timezone = Timezone.ofSystem();
            }
            ?? currentTime = timeSource.currentTime();
            return PlainTimestamp.from(currentTime, timezone.getOffset(currentTime));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PlainTimestamp createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            PlainDate createFrom;
            PlainTime createFrom2;
            TZID tzid;
            if (chronoEntity instanceof UnixTime) {
                if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                    tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
                } else if (z) {
                    tzid = ZonalOffset.UTC;
                } else {
                    throw new IllegalArgumentException("Missing timezone attribute for type conversion.");
                }
                return Moment.from((UnixTime) UnixTime.class.cast(chronoEntity)).toZonalTimestamp(tzid);
            }
            boolean z3 = z2 && chronoEntity.getInt(PlainTime.SECOND_OF_MINUTE) == 60;
            if (z3) {
                chronoEntity.with((ChronoElement<Integer>) PlainTime.SECOND_OF_MINUTE, 59);
            }
            if (chronoEntity.contains(PlainDate.CALENDAR_DATE)) {
                createFrom = (PlainDate) chronoEntity.get(PlainDate.CALENDAR_DATE);
            } else {
                createFrom = PlainDate.axis().createFrom(chronoEntity, attributeQuery, z, false);
            }
            if (createFrom == null) {
                return null;
            }
            if (chronoEntity.contains(PlainTime.WALL_TIME)) {
                createFrom2 = (PlainTime) chronoEntity.get(PlainTime.WALL_TIME);
            } else {
                createFrom2 = PlainTime.axis().createFrom(chronoEntity, attributeQuery, z, false);
                if (createFrom2 == null && z) {
                    createFrom2 = PlainTime.MIN;
                }
            }
            if (createFrom2 == null) {
                return null;
            }
            if (chronoEntity.contains(LongElement.DAY_OVERFLOW)) {
                createFrom = (PlainDate) createFrom.plus(((Long) chronoEntity.get(LongElement.DAY_OVERFLOW)).longValue(), CalendarUnit.DAYS);
            }
            if (z3 && chronoEntity.isValid((ChronoElement<FlagElement>) FlagElement.LEAP_SECOND, (FlagElement) Boolean.TRUE)) {
                chronoEntity.with((ChronoElement<FlagElement>) FlagElement.LEAP_SECOND, (FlagElement) Boolean.TRUE);
            }
            return PlainTimestamp.of(createFrom, createFrom2);
        }
    }

    private static class FieldRule<V> implements ElementRule<PlainTimestamp, V> {
        private final ChronoElement<V> element;

        /* synthetic */ FieldRule(ChronoElement chronoElement, AnonymousClass1 anonymousClass1) {
            this(chronoElement);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid */
        public /* bridge */ /* synthetic */ boolean isValid2(PlainTimestamp plainTimestamp, Object obj) {
            return isValid(plainTimestamp, (PlainTimestamp) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue */
        public /* bridge */ /* synthetic */ PlainTimestamp withValue2(PlainTimestamp plainTimestamp, Object obj, boolean z) {
            return withValue(plainTimestamp, (PlainTimestamp) obj, z);
        }

        private FieldRule(ChronoElement<V> chronoElement) {
            this.element = chronoElement;
        }

        static <V> FieldRule<V> of(ChronoElement<V> chronoElement) {
            return new FieldRule<>(chronoElement);
        }

        @Override // net.time4j.engine.ElementRule
        public V getValue(PlainTimestamp plainTimestamp) {
            if (this.element.isDateElement()) {
                return (V) plainTimestamp.date.get(this.element);
            }
            if (this.element.isTimeElement()) {
                return (V) plainTimestamp.time.get(this.element);
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        @Override // net.time4j.engine.ElementRule
        public V getMinimum(PlainTimestamp plainTimestamp) {
            if (this.element.isDateElement()) {
                return (V) plainTimestamp.date.getMinimum(this.element);
            }
            if (this.element.isTimeElement()) {
                return this.element.getDefaultMinimum();
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        @Override // net.time4j.engine.ElementRule
        public V getMaximum(PlainTimestamp plainTimestamp) {
            if (this.element.isDateElement()) {
                return (V) plainTimestamp.date.getMaximum(this.element);
            }
            if (this.element.isTimeElement()) {
                return this.element.getDefaultMaximum();
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        public boolean isValid(PlainTimestamp plainTimestamp, V v) {
            if (v == null) {
                return false;
            }
            if (this.element.isDateElement()) {
                return plainTimestamp.date.isValid((ChronoElement<ChronoElement<V>>) this.element, (ChronoElement<V>) v);
            }
            if (this.element.isTimeElement()) {
                if (Number.class.isAssignableFrom(this.element.getType())) {
                    long number = toNumber(this.element.getDefaultMinimum());
                    long number2 = toNumber(this.element.getDefaultMaximum());
                    long number3 = toNumber(v);
                    return number <= number3 && number2 >= number3;
                }
                if (this.element.equals(PlainTime.WALL_TIME) && PlainTime.MAX.equals(v)) {
                    return false;
                }
                return plainTimestamp.time.isValid((ChronoElement<ChronoElement<V>>) this.element, (ChronoElement<V>) v);
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Multi-variable type inference failed */
        public PlainTimestamp withValue(PlainTimestamp plainTimestamp, V v, boolean z) {
            if (v == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            if (v.equals(getValue(plainTimestamp))) {
                return plainTimestamp;
            }
            if (z) {
                return plainTimestamp.plus(MathUtils.safeSubtract(toNumber(v), toNumber(getValue(plainTimestamp))), (IsoUnit) PlainTimestamp.ENGINE.getBaseUnit(this.element));
            }
            if (this.element.isDateElement()) {
                return PlainTimestamp.of((PlainDate) plainTimestamp.date.with((ChronoElement<ChronoElement<V>>) this.element, (ChronoElement<V>) v), plainTimestamp.time);
            }
            if (this.element.isTimeElement()) {
                if (Number.class.isAssignableFrom(this.element.getType())) {
                    long number = toNumber(this.element.getDefaultMinimum());
                    long number2 = toNumber(this.element.getDefaultMaximum());
                    long number3 = toNumber(v);
                    if (number > number3 || number2 < number3) {
                        throw new IllegalArgumentException("Out of range: " + v);
                    }
                } else if (this.element.equals(PlainTime.WALL_TIME) && v.equals(PlainTime.MAX)) {
                    throw new IllegalArgumentException("Out of range: " + v);
                }
                return PlainTimestamp.of(plainTimestamp.date, (PlainTime) plainTimestamp.time.with((ChronoElement<ChronoElement<V>>) this.element, (ChronoElement<V>) v));
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTimestamp plainTimestamp) {
            return (ChronoElement) PlainTimestamp.CHILDREN.get(this.element);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTimestamp plainTimestamp) {
            return (ChronoElement) PlainTimestamp.CHILDREN.get(this.element);
        }

        private long toNumber(V v) {
            return ((Number) Number.class.cast(v)).longValue();
        }
    }

    private static class DecimalRule extends FieldRule<BigDecimal> {
        DecimalRule(ChronoElement<BigDecimal> chronoElement) {
            super(chronoElement, null);
        }

        @Override // net.time4j.PlainTimestamp.FieldRule
        public boolean isValid2(PlainTimestamp plainTimestamp, BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                return false;
            }
            return ((BigDecimal) ((FieldRule) this).element.getDefaultMinimum()).compareTo(bigDecimal) <= 0 && bigDecimal.compareTo((BigDecimal) ((FieldRule) this).element.getDefaultMaximum()) <= 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.PlainTimestamp.FieldRule
        public PlainTimestamp withValue2(PlainTimestamp plainTimestamp, BigDecimal bigDecimal, boolean z) {
            if (isValid(plainTimestamp, bigDecimal)) {
                return PlainTimestamp.of(plainTimestamp.date, (PlainTime) plainTimestamp.time.with((ChronoElement<ChronoElement>) ((FieldRule) this).element, (ChronoElement) bigDecimal));
            }
            throw new IllegalArgumentException("Out of range: " + bigDecimal);
        }
    }

    private static class CompositeUnitRule implements UnitRule<PlainTimestamp> {
        private final CalendarUnit calendarUnit;
        private final ClockUnit clockUnit;

        CompositeUnitRule(CalendarUnit calendarUnit) {
            this.calendarUnit = calendarUnit;
            this.clockUnit = null;
        }

        CompositeUnitRule(ClockUnit clockUnit) {
            this.calendarUnit = null;
            this.clockUnit = clockUnit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public PlainTimestamp addTo(PlainTimestamp plainTimestamp, long j) {
            PlainDate plainDate;
            PlainTime plainTime;
            if (this.calendarUnit != null) {
                plainDate = (PlainDate) plainTimestamp.date.plus(j, this.calendarUnit);
                plainTime = plainTimestamp.time;
            } else {
                DayCycles roll = plainTimestamp.time.roll(j, this.clockUnit);
                PlainDate plainDate2 = (PlainDate) plainTimestamp.date.plus(roll.getDayOverflow(), CalendarUnit.DAYS);
                PlainTime wallTime = roll.getWallTime();
                plainDate = plainDate2;
                plainTime = wallTime;
            }
            return PlainTimestamp.of(plainDate, plainTime);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public long between(PlainTimestamp plainTimestamp, PlainTimestamp plainTimestamp2) {
            long safeAdd;
            CalendarUnit calendarUnit = this.calendarUnit;
            if (calendarUnit != null) {
                long between = calendarUnit.between(plainTimestamp.date, plainTimestamp2.date);
                if (between == 0) {
                    return between;
                }
                boolean z = true;
                if (this.calendarUnit != CalendarUnit.DAYS && ((PlainDate) plainTimestamp.date.plus(between, this.calendarUnit)).compareByTime(plainTimestamp2.date) != 0) {
                    z = false;
                }
                if (!z) {
                    return between;
                }
                PlainTime plainTime = plainTimestamp.time;
                PlainTime plainTime2 = plainTimestamp2.time;
                return (between <= 0 || !plainTime.isAfter(plainTime2)) ? (between >= 0 || !plainTime.isBefore(plainTime2)) ? between : between + 1 : between - 1;
            }
            if (!plainTimestamp.date.isAfter((CalendarDate) plainTimestamp2.date)) {
                long until = plainTimestamp.date.until(plainTimestamp2.date, (PlainDate) CalendarUnit.DAYS);
                if (until == 0) {
                    return this.clockUnit.between(plainTimestamp.time, plainTimestamp2.time);
                }
                if (this.clockUnit.compareTo(ClockUnit.SECONDS) <= 0) {
                    long safeAdd2 = MathUtils.safeAdd(MathUtils.safeMultiply(until, 86400L), MathUtils.safeSubtract(((Integer) plainTimestamp2.time.get(PlainTime.SECOND_OF_DAY)).longValue(), ((Integer) plainTimestamp.time.get(PlainTime.SECOND_OF_DAY)).longValue()));
                    if (plainTimestamp.time.getNanosecond() > plainTimestamp2.time.getNanosecond()) {
                        safeAdd2--;
                    }
                    safeAdd = safeAdd2;
                } else {
                    safeAdd = MathUtils.safeAdd(MathUtils.safeMultiply(until, 86400000000000L), MathUtils.safeSubtract(((Long) plainTimestamp2.time.get(PlainTime.NANO_OF_DAY)).longValue(), ((Long) plainTimestamp.time.get(PlainTime.NANO_OF_DAY)).longValue()));
                }
                switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[this.clockUnit.ordinal()]) {
                    case 1:
                        return safeAdd / 3600;
                    case 2:
                        return safeAdd / 60;
                    case 3:
                    case 6:
                        return safeAdd;
                    case 4:
                        return safeAdd / 1000000;
                    case 5:
                        return safeAdd / 1000;
                    default:
                        throw new UnsupportedOperationException(this.clockUnit.name());
                }
            }
            return -between(plainTimestamp2, plainTimestamp);
        }
    }

    /* renamed from: net.time4j.PlainTimestamp$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$ClockUnit;

        static {
            int[] iArr = new int[ClockUnit.values().length];
            $SwitchMap$net$time4j$ClockUnit = iArr;
            try {
                iArr[ClockUnit.HOURS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MINUTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.SECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MILLIS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MICROS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.NANOS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
