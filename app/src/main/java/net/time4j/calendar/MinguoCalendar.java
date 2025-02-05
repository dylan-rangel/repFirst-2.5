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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.time4j.CalendarUnit;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.Month;
import net.time4j.NavigableElement;
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

@CalendarType("roc")
/* loaded from: classes3.dex */
public final class MinguoCalendar extends Calendrical<CalendarUnit, MinguoCalendar> implements LocalizedPatternSupport {
    private static final EraYearMonthDaySystem<MinguoCalendar> CALSYS;
    private static final Map<Object, ChronoElement<?>> CHILDREN;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, MinguoCalendar> DAY_OF_MONTH;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, MinguoCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, MinguoCalendar> DAY_OF_YEAR;
    private static final TimeAxis<CalendarUnit, MinguoCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<MinguoEra> ERA;

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<Month, MinguoCalendar> MONTH_OF_YEAR;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<MinguoCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<MinguoCalendar> WIM_ELEMENT;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, MinguoCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = -6628190121085147706L;
    private final PlainDate iso;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public MinguoCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", MinguoCalendar.class, MinguoEra.class, 'G');
        ERA = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("YEAR_OF_ERA", MinguoCalendar.class, 1, 999998088, 'y', null, null);
        YEAR_OF_ERA = stdIntegerDateElement;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", MinguoCalendar.class, Month.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", MinguoCalendar.class, 1, 31, 'd');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", MinguoCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(MinguoCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<MinguoCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(MinguoCalendar.class, stdIntegerDateElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        HashMap hashMap = new HashMap();
        hashMap.put(stdEnumDateElement, stdIntegerDateElement);
        hashMap.put(stdIntegerDateElement, stdEnumDateElement2);
        hashMap.put(stdEnumDateElement2, stdIntegerDateElement2);
        CHILDREN = Collections.unmodifiableMap(hashMap);
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        TimeAxis.Builder appendExtension = TimeAxis.Builder.setUp(CalendarUnit.class, MinguoCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) FieldRule.of(stdEnumDateElement)).appendElement(stdIntegerDateElement, FieldRule.of(stdIntegerDateElement), CalendarUnit.YEARS).appendElement(stdEnumDateElement2, FieldRule.of(stdEnumDateElement2), CalendarUnit.MONTHS).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement3)).appendElement(stdIntegerDateElement2, FieldRule.of(stdIntegerDateElement2), CalendarUnit.DAYS).appendElement(stdIntegerDateElement3, FieldRule.of(stdIntegerDateElement3), CalendarUnit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<MinguoCalendar, CalendarSystem<MinguoCalendar>>() { // from class: net.time4j.calendar.MinguoCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<MinguoCalendar> apply(MinguoCalendar minguoCalendar) {
                return MinguoCalendar.CALSYS;
            }
        }), CalendarUnit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendExtension((ChronoExtension) new CommonElements.Weekengine(MinguoCalendar.class, stdIntegerDateElement2, stdIntegerDateElement3, getDefaultWeekmodel()));
        registerUnits(appendExtension);
        ENGINE = appendExtension.build();
    }

    private MinguoCalendar(PlainDate plainDate) {
        this.iso = plainDate;
    }

    public static MinguoCalendar of(MinguoEra minguoEra, int i, Month month, int i2) {
        return of(minguoEra, i, month.getValue(), i2);
    }

    public static MinguoCalendar of(MinguoEra minguoEra, int i, int i2, int i3) {
        return new MinguoCalendar(PlainDate.of(toProlepticYear(minguoEra, i), i2, i3));
    }

    public static MinguoCalendar nowInSystemTime() {
        return (MinguoCalendar) SystemClock.inLocalView().now(axis());
    }

    public MinguoEra getEra() {
        return this.iso.getYear() < 1912 ? MinguoEra.BEFORE_ROC : MinguoEra.ROC;
    }

    public int getYear() {
        return getEra() == MinguoEra.ROC ? this.iso.getYear() - 1911 : 1912 - this.iso.getYear();
    }

    public Month getMonth() {
        return Month.valueOf(this.iso.getMonth());
    }

    public int getDayOfMonth() {
        return this.iso.getDayOfMonth();
    }

    public Weekday getDayOfWeek() {
        return (Weekday) this.iso.get(PlainDate.DAY_OF_WEEK);
    }

    public int getDayOfYear() {
        return ((Integer) this.iso.get(PlainDate.DAY_OF_YEAR)).intValue();
    }

    public int lengthOfMonth() {
        return this.iso.lengthOfMonth();
    }

    public int lengthOfYear() {
        return this.iso.lengthOfYear();
    }

    public boolean isLeapYear() {
        return this.iso.isLeapYear();
    }

    public static boolean isValid(MinguoEra minguoEra, int i, int i2, int i3) {
        return CALSYS.isValid(minguoEra, i, i2, i3);
    }

    public GeneralTimestamp<MinguoCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<MinguoCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MinguoCalendar) {
            return this.iso.equals(((MinguoCalendar) obj).iso);
        }
        return false;
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public int hashCode() {
        return this.iso.hashCode();
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(getEra());
        sb.append('-');
        sb.append(getYear());
        sb.append('-');
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
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1);
    }

    public static TimeAxis<CalendarUnit, MinguoCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<CalendarUnit, MinguoCalendar> getChronology() {
        return ENGINE;
    }

    PlainDate toISO() {
        return this.iso;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toProlepticYear(MinguoEra minguoEra, int i) {
        if (minguoEra == MinguoEra.ROC) {
            return MathUtils.safeAdd(i, 1911);
        }
        return MathUtils.safeSubtract(1912, i);
    }

    private static void registerUnits(TimeAxis.Builder<CalendarUnit, MinguoCalendar> builder) {
        EnumSet range = EnumSet.range(CalendarUnit.MILLENNIA, CalendarUnit.MONTHS);
        EnumSet range2 = EnumSet.range(CalendarUnit.WEEKS, CalendarUnit.DAYS);
        for (CalendarUnit calendarUnit : CalendarUnit.values()) {
            builder.appendUnit(calendarUnit, new MinguoUnitRule(calendarUnit), calendarUnit.getLength(), calendarUnit.compareTo(CalendarUnit.WEEKS) < 0 ? range : range2);
        }
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class Transformer implements EraYearMonthDaySystem<MinguoCalendar> {
        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            try {
                if (!(calendarEra instanceof MinguoEra)) {
                    return false;
                }
                int prolepticYear = MinguoCalendar.toProlepticYear((MinguoEra) MinguoEra.class.cast(calendarEra), i);
                if (i < 1 || prolepticYear > 999999999 || i2 < 1 || i2 > 12 || i3 < 1) {
                    return false;
                }
                return i3 <= GregorianMath.getLengthOfMonth(prolepticYear, i2);
            } catch (ArithmeticException unused) {
                return false;
            }
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
            try {
                return PlainDate.of(MinguoCalendar.toProlepticYear((MinguoEra) MinguoEra.class.cast(calendarEra), i), i2, 1).lengthOfMonth();
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            try {
                return PlainDate.of(MinguoCalendar.toProlepticYear((MinguoEra) MinguoEra.class.cast(calendarEra), i), Month.JANUARY, 1).lengthOfYear();
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.engine.CalendarSystem
        public MinguoCalendar transform(long j) {
            return new MinguoCalendar(PlainDate.of(j, EpochDays.UTC));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(MinguoCalendar minguoCalendar) {
            return ((Long) minguoCalendar.iso.get(EpochDays.UTC)).longValue();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return PlainDate.axis().getCalendarSystem().getMinimumSinceUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return PlainDate.axis().getCalendarSystem().getMaximumSinceUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Arrays.asList(MinguoEra.BEFORE_ROC, MinguoEra.ROC);
        }
    }

    private static class FieldRule<V extends Comparable<V>> implements ElementRule<MinguoCalendar, V> {
        private final ChronoElement<V> element;

        private FieldRule(ChronoElement<V> chronoElement) {
            this.element = chronoElement;
        }

        static <V extends Comparable<V>> FieldRule<V> of(ChronoElement<V> chronoElement) {
            return new FieldRule<>(chronoElement);
        }

        @Override // net.time4j.engine.ElementRule
        public V getValue(MinguoCalendar minguoCalendar) {
            Object valueOf;
            if (this.element == MinguoCalendar.ERA) {
                valueOf = minguoCalendar.getEra();
            } else if (this.element.equals(MinguoCalendar.YEAR_OF_ERA)) {
                valueOf = Integer.valueOf(minguoCalendar.getYear());
            } else if (this.element.equals(MinguoCalendar.MONTH_OF_YEAR)) {
                valueOf = minguoCalendar.getMonth();
            } else if (this.element.equals(MinguoCalendar.DAY_OF_MONTH)) {
                valueOf = Integer.valueOf(minguoCalendar.getDayOfMonth());
            } else if (this.element.equals(MinguoCalendar.DAY_OF_YEAR)) {
                valueOf = Integer.valueOf(minguoCalendar.getDayOfYear());
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(valueOf);
        }

        @Override // net.time4j.engine.ElementRule
        public V getMinimum(MinguoCalendar minguoCalendar) {
            Object obj;
            if (this.element == MinguoCalendar.ERA) {
                obj = MinguoEra.BEFORE_ROC;
            } else if (Integer.class.isAssignableFrom(this.element.getType())) {
                obj = 1;
            } else if (this.element.equals(MinguoCalendar.MONTH_OF_YEAR)) {
                obj = Month.JANUARY;
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(obj);
        }

        @Override // net.time4j.engine.ElementRule
        public V getMaximum(MinguoCalendar minguoCalendar) {
            Object maximum;
            if (this.element == MinguoCalendar.ERA) {
                maximum = MinguoEra.ROC;
            } else if (this.element.equals(MinguoCalendar.YEAR_OF_ERA)) {
                maximum = minguoCalendar.getEra() == MinguoEra.ROC ? 999998088 : 1000001911;
            } else if (this.element.equals(MinguoCalendar.MONTH_OF_YEAR)) {
                maximum = Month.DECEMBER;
            } else if (this.element.equals(MinguoCalendar.DAY_OF_MONTH)) {
                maximum = minguoCalendar.iso.getMaximum(PlainDate.DAY_OF_MONTH);
            } else if (this.element.equals(MinguoCalendar.DAY_OF_YEAR)) {
                maximum = minguoCalendar.iso.getMaximum(PlainDate.DAY_OF_YEAR);
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(maximum);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(MinguoCalendar minguoCalendar, V v) {
            if (v == null) {
                return false;
            }
            if (this.element == MinguoCalendar.ERA) {
                return v.equals(minguoCalendar.getEra());
            }
            return getMinimum(minguoCalendar).compareTo(v) <= 0 && v.compareTo(getMaximum(minguoCalendar)) <= 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public MinguoCalendar withValue2(MinguoCalendar minguoCalendar, V v, boolean z) {
            if (!isValid2(minguoCalendar, (MinguoCalendar) v)) {
                throw new IllegalArgumentException("Out of range: " + v);
            }
            if (this.element == MinguoCalendar.ERA) {
                return minguoCalendar;
            }
            if (this.element.equals(MinguoCalendar.YEAR_OF_ERA)) {
                MinguoCalendar of = MinguoCalendar.of(minguoCalendar.getEra(), toNumber(v), minguoCalendar.getMonth(), 1);
                return (MinguoCalendar) of.with((ChronoElement<Integer>) MinguoCalendar.DAY_OF_MONTH, Math.min(minguoCalendar.getDayOfMonth(), of.lengthOfMonth()));
            }
            if (this.element.equals(MinguoCalendar.MONTH_OF_YEAR)) {
                return new MinguoCalendar((PlainDate) minguoCalendar.iso.with(PlainDate.MONTH_OF_YEAR, (NavigableElement<Month>) Month.class.cast(v)));
            }
            if (this.element.equals(MinguoCalendar.DAY_OF_MONTH)) {
                return new MinguoCalendar((PlainDate) minguoCalendar.iso.with((ChronoElement<Integer>) PlainDate.DAY_OF_MONTH, toNumber(v)));
            }
            if (this.element.equals(MinguoCalendar.DAY_OF_YEAR)) {
                return new MinguoCalendar((PlainDate) minguoCalendar.iso.with((ChronoElement<Integer>) PlainDate.DAY_OF_YEAR, toNumber(v)));
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(MinguoCalendar minguoCalendar) {
            return (ChronoElement) MinguoCalendar.CHILDREN.get(this.element);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(MinguoCalendar minguoCalendar) {
            return (ChronoElement) MinguoCalendar.CHILDREN.get(this.element);
        }

        private static int toNumber(Object obj) {
            return ((Integer) Integer.class.cast(obj)).intValue();
        }
    }

    private static class MinguoUnitRule implements UnitRule<MinguoCalendar> {
        private final CalendarUnit unit;

        MinguoUnitRule(CalendarUnit calendarUnit) {
            this.unit = calendarUnit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public MinguoCalendar addTo(MinguoCalendar minguoCalendar, long j) {
            return new MinguoCalendar((PlainDate) minguoCalendar.iso.plus(j, this.unit));
        }

        @Override // net.time4j.engine.UnitRule
        public long between(MinguoCalendar minguoCalendar, MinguoCalendar minguoCalendar2) {
            return this.unit.between(minguoCalendar.iso, minguoCalendar2.iso);
        }
    }

    private static class Merger implements ChronoMerger<MinguoCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(MinguoCalendar minguoCalendar, AttributeQuery attributeQuery) {
            return minguoCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ MinguoCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ MinguoCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("roc", displayStyle, locale);
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 1911;
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public MinguoCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (MinguoCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(MinguoCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public MinguoCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            MinguoEra minguoEra;
            if (chronoEntity.contains(PlainDate.COMPONENT)) {
                return new MinguoCalendar((PlainDate) chronoEntity.get(PlainDate.COMPONENT));
            }
            if (chronoEntity.contains(MinguoCalendar.ERA)) {
                minguoEra = (MinguoEra) chronoEntity.get(MinguoCalendar.ERA);
            } else if (z) {
                minguoEra = MinguoEra.ROC;
            } else {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Minguo era.");
                return null;
            }
            int i = chronoEntity.getInt(MinguoCalendar.YEAR_OF_ERA);
            if (i != Integer.MIN_VALUE) {
                int prolepticYear = MinguoCalendar.toProlepticYear(minguoEra, i);
                if (chronoEntity.contains(MinguoCalendar.MONTH_OF_YEAR)) {
                    int value = ((Month) chronoEntity.get(MinguoCalendar.MONTH_OF_YEAR)).getValue();
                    int i2 = chronoEntity.getInt(MinguoCalendar.DAY_OF_MONTH);
                    if (i2 != Integer.MIN_VALUE) {
                        if (MinguoCalendar.CALSYS.isValid(minguoEra, i, value, i2)) {
                            return MinguoCalendar.of(minguoEra, i, value, i2);
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Minguo date.");
                    }
                } else {
                    int i3 = chronoEntity.getInt(MinguoCalendar.DAY_OF_YEAR);
                    if (i3 != Integer.MIN_VALUE) {
                        if (i3 > 0) {
                            int i4 = 0;
                            int i5 = 1;
                            while (i5 <= 12) {
                                int lengthOfMonth = GregorianMath.getLengthOfMonth(prolepticYear, i5) + i4;
                                if (i3 <= lengthOfMonth) {
                                    return MinguoCalendar.of(minguoEra, i, i5, i3 - i4);
                                }
                                i5++;
                                i4 = lengthOfMonth;
                            }
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Minguo date.");
                    }
                }
                return null;
            }
            chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Minguo year.");
            return null;
        }
    }

    private static class SPX implements Externalizable {
        private static final int MINGUO = 6;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(6);
            writeMinguo(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            if (objectInput.readByte() == 6) {
                this.obj = readMinguo(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeMinguo(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeObject(((MinguoCalendar) this.obj).toISO());
        }

        private MinguoCalendar readMinguo(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            return (MinguoCalendar) ((PlainDate) PlainDate.class.cast(objectInput.readObject())).transform(MinguoCalendar.class);
        }
    }
}
