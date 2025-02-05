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

@CalendarType("juche")
/* loaded from: classes3.dex */
public final class JucheCalendar extends Calendrical<CalendarUnit, JucheCalendar> implements LocalizedPatternSupport {
    private static final EraYearMonthDaySystem<JucheCalendar> CALSYS;
    private static final Map<Object, ChronoElement<?>> CHILDREN;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, JucheCalendar> DAY_OF_MONTH;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, JucheCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, JucheCalendar> DAY_OF_YEAR;
    private static final TimeAxis<CalendarUnit, JucheCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<JucheEra> ERA;

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<Month, JucheCalendar> MONTH_OF_YEAR;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<JucheCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<JucheCalendar> WIM_ELEMENT;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, JucheCalendar> YEAR_OF_ERA;
    private static final long serialVersionUID = 757676060690932159L;
    private final PlainDate iso;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public JucheCalendar getContext() {
        return this;
    }

    static {
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("ERA", JucheCalendar.class, JucheEra.class, 'G');
        ERA = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("YEAR_OF_ERA", JucheCalendar.class, 1, 999998088, 'y', null, null);
        YEAR_OF_ERA = stdIntegerDateElement;
        StdEnumDateElement stdEnumDateElement2 = new StdEnumDateElement("MONTH_OF_YEAR", JucheCalendar.class, Month.class, 'M');
        MONTH_OF_YEAR = stdEnumDateElement2;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_MONTH", JucheCalendar.class, 1, 31, 'd');
        DAY_OF_MONTH = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_YEAR", JucheCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement3;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(JucheCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<JucheCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(JucheCalendar.class, stdIntegerDateElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        HashMap hashMap = new HashMap();
        hashMap.put(stdEnumDateElement, stdIntegerDateElement);
        hashMap.put(stdIntegerDateElement, stdEnumDateElement2);
        hashMap.put(stdEnumDateElement2, stdIntegerDateElement2);
        CHILDREN = Collections.unmodifiableMap(hashMap);
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        TimeAxis.Builder appendExtension = TimeAxis.Builder.setUp(CalendarUnit.class, JucheCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) FieldRule.of(stdEnumDateElement)).appendElement(stdIntegerDateElement, FieldRule.of(stdIntegerDateElement), CalendarUnit.YEARS).appendElement(stdEnumDateElement2, FieldRule.of(stdEnumDateElement2), CalendarUnit.MONTHS).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement3)).appendElement(stdIntegerDateElement2, FieldRule.of(stdIntegerDateElement2), CalendarUnit.DAYS).appendElement(stdIntegerDateElement3, FieldRule.of(stdIntegerDateElement3), CalendarUnit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<JucheCalendar, CalendarSystem<JucheCalendar>>() { // from class: net.time4j.calendar.JucheCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<JucheCalendar> apply(JucheCalendar jucheCalendar) {
                return JucheCalendar.CALSYS;
            }
        }), CalendarUnit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendExtension((ChronoExtension) new CommonElements.Weekengine(JucheCalendar.class, stdIntegerDateElement2, stdIntegerDateElement3, getDefaultWeekmodel()));
        registerUnits(appendExtension);
        ENGINE = appendExtension.build();
    }

    JucheCalendar(PlainDate plainDate) {
        if (plainDate.getYear() < 1912) {
            throw new IllegalArgumentException("Juche calendar not valid before gregorian year 1912.");
        }
        this.iso = plainDate;
    }

    public static JucheCalendar of(int i, Month month, int i2) {
        return of(i, month.getValue(), i2);
    }

    public static JucheCalendar of(int i, int i2, int i3) {
        return new JucheCalendar(PlainDate.of(toProlepticYear(i), i2, i3));
    }

    public static JucheCalendar nowInSystemTime() {
        return (JucheCalendar) SystemClock.inLocalView().now(axis());
    }

    public JucheEra getEra() {
        return JucheEra.JUCHE;
    }

    public int getYear() {
        return this.iso.getYear() - 1911;
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
        return this.iso.getInt(PlainDate.DAY_OF_YEAR);
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

    public static boolean isValid(int i, int i2, int i3) {
        return CALSYS.isValid(JucheEra.JUCHE, i, i2, i3);
    }

    public GeneralTimestamp<JucheCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<JucheCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.Calendrical, net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JucheCalendar) {
            return this.iso.equals(((JucheCalendar) obj).iso);
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
        return Weekmodel.of(new Locale("ko", "KP"));
    }

    public static TimeAxis<CalendarUnit, JucheCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<CalendarUnit, JucheCalendar> getChronology() {
        return ENGINE;
    }

    PlainDate toISO() {
        return this.iso;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toProlepticYear(int i) {
        return MathUtils.safeAdd(i, 1911);
    }

    private static void registerUnits(TimeAxis.Builder<CalendarUnit, JucheCalendar> builder) {
        EnumSet range = EnumSet.range(CalendarUnit.MILLENNIA, CalendarUnit.MONTHS);
        EnumSet range2 = EnumSet.range(CalendarUnit.WEEKS, CalendarUnit.DAYS);
        for (CalendarUnit calendarUnit : CalendarUnit.values()) {
            builder.appendUnit(calendarUnit, new JucheUnitRule(calendarUnit), calendarUnit.getLength(), calendarUnit.compareTo(CalendarUnit.WEEKS) < 0 ? range : range2);
        }
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class Transformer implements EraYearMonthDaySystem<JucheCalendar> {
        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return -21915L;
        }

        private Transformer() {
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
            try {
                if (!(calendarEra instanceof JucheEra)) {
                    return false;
                }
                int prolepticYear = JucheCalendar.toProlepticYear(i);
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
                return PlainDate.of(JucheCalendar.toProlepticYear(i), i2, 1).lengthOfMonth();
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.calendar.EraYearMonthDaySystem
        public int getLengthOfYear(CalendarEra calendarEra, int i) {
            try {
                return PlainDate.of(JucheCalendar.toProlepticYear(i), Month.JANUARY, 1).lengthOfYear();
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.engine.CalendarSystem
        public JucheCalendar transform(long j) {
            return new JucheCalendar(PlainDate.of(j, EpochDays.UTC));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(JucheCalendar jucheCalendar) {
            return ((Long) jucheCalendar.iso.get(EpochDays.UTC)).longValue();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return PlainDate.axis().getCalendarSystem().getMaximumSinceUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.singletonList(JucheEra.JUCHE);
        }
    }

    private static class FieldRule<V extends Comparable<V>> implements ElementRule<JucheCalendar, V> {
        private final ChronoElement<V> element;

        private FieldRule(ChronoElement<V> chronoElement) {
            this.element = chronoElement;
        }

        static <V extends Comparable<V>> FieldRule<V> of(ChronoElement<V> chronoElement) {
            return new FieldRule<>(chronoElement);
        }

        @Override // net.time4j.engine.ElementRule
        public V getValue(JucheCalendar jucheCalendar) {
            Object valueOf;
            if (this.element == JucheCalendar.ERA) {
                valueOf = JucheEra.JUCHE;
            } else if (this.element.equals(JucheCalendar.YEAR_OF_ERA)) {
                valueOf = Integer.valueOf(jucheCalendar.getYear());
            } else if (this.element.equals(JucheCalendar.MONTH_OF_YEAR)) {
                valueOf = jucheCalendar.getMonth();
            } else if (this.element.equals(JucheCalendar.DAY_OF_MONTH)) {
                valueOf = Integer.valueOf(jucheCalendar.getDayOfMonth());
            } else if (this.element.equals(JucheCalendar.DAY_OF_YEAR)) {
                valueOf = Integer.valueOf(jucheCalendar.getDayOfYear());
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(valueOf);
        }

        @Override // net.time4j.engine.ElementRule
        public V getMinimum(JucheCalendar jucheCalendar) {
            Object obj;
            if (this.element == JucheCalendar.ERA) {
                obj = JucheEra.JUCHE;
            } else if (Integer.class.isAssignableFrom(this.element.getType())) {
                obj = 1;
            } else if (this.element.equals(JucheCalendar.MONTH_OF_YEAR)) {
                obj = Month.JANUARY;
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(obj);
        }

        @Override // net.time4j.engine.ElementRule
        public V getMaximum(JucheCalendar jucheCalendar) {
            Object maximum;
            if (this.element == JucheCalendar.ERA) {
                maximum = JucheEra.JUCHE;
            } else if (this.element.equals(JucheCalendar.YEAR_OF_ERA)) {
                maximum = 999998088;
            } else if (this.element.equals(JucheCalendar.MONTH_OF_YEAR)) {
                maximum = Month.DECEMBER;
            } else if (this.element.equals(JucheCalendar.DAY_OF_MONTH)) {
                maximum = jucheCalendar.iso.getMaximum(PlainDate.DAY_OF_MONTH);
            } else if (this.element.equals(JucheCalendar.DAY_OF_YEAR)) {
                maximum = jucheCalendar.iso.getMaximum(PlainDate.DAY_OF_YEAR);
            } else {
                throw new ChronoException("Missing rule for: " + this.element.name());
            }
            return this.element.getType().cast(maximum);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(JucheCalendar jucheCalendar, V v) {
            if (v == null) {
                return false;
            }
            if (this.element == JucheCalendar.ERA) {
                return true;
            }
            return getMinimum(jucheCalendar).compareTo(v) <= 0 && v.compareTo(getMaximum(jucheCalendar)) <= 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public JucheCalendar withValue2(JucheCalendar jucheCalendar, V v, boolean z) {
            if (!isValid2(jucheCalendar, (JucheCalendar) v)) {
                throw new IllegalArgumentException("Out of range: " + v);
            }
            if (this.element == JucheCalendar.ERA) {
                return jucheCalendar;
            }
            if (this.element.equals(JucheCalendar.YEAR_OF_ERA)) {
                JucheCalendar of = JucheCalendar.of(toNumber(v), jucheCalendar.getMonth(), 1);
                return (JucheCalendar) of.with((ChronoElement<Integer>) JucheCalendar.DAY_OF_MONTH, Math.min(jucheCalendar.getDayOfMonth(), of.lengthOfMonth()));
            }
            if (this.element.equals(JucheCalendar.MONTH_OF_YEAR)) {
                return new JucheCalendar((PlainDate) jucheCalendar.iso.with(PlainDate.MONTH_OF_YEAR, (NavigableElement<Month>) Month.class.cast(v)));
            }
            if (this.element.equals(JucheCalendar.DAY_OF_MONTH)) {
                return new JucheCalendar((PlainDate) jucheCalendar.iso.with((ChronoElement<Integer>) PlainDate.DAY_OF_MONTH, toNumber(v)));
            }
            if (this.element.equals(JucheCalendar.DAY_OF_YEAR)) {
                return new JucheCalendar((PlainDate) jucheCalendar.iso.with((ChronoElement<Integer>) PlainDate.DAY_OF_YEAR, toNumber(v)));
            }
            throw new ChronoException("Missing rule for: " + this.element.name());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(JucheCalendar jucheCalendar) {
            return (ChronoElement) JucheCalendar.CHILDREN.get(this.element);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(JucheCalendar jucheCalendar) {
            return (ChronoElement) JucheCalendar.CHILDREN.get(this.element);
        }

        private static int toNumber(Object obj) {
            return ((Integer) Integer.class.cast(obj)).intValue();
        }
    }

    private static class JucheUnitRule implements UnitRule<JucheCalendar> {
        private final CalendarUnit unit;

        JucheUnitRule(CalendarUnit calendarUnit) {
            this.unit = calendarUnit;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public JucheCalendar addTo(JucheCalendar jucheCalendar, long j) {
            return new JucheCalendar((PlainDate) jucheCalendar.iso.plus(j, this.unit));
        }

        @Override // net.time4j.engine.UnitRule
        public long between(JucheCalendar jucheCalendar, JucheCalendar jucheCalendar2) {
            return this.unit.between(jucheCalendar.iso, jucheCalendar2.iso);
        }
    }

    private static class Merger implements ChronoMerger<JucheCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(JucheCalendar jucheCalendar, AttributeQuery attributeQuery) {
            return jucheCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ JucheCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ JucheCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
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

        /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public JucheCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (JucheCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(JucheCalendar.ENGINE, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public JucheCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            if (chronoEntity.contains(PlainDate.COMPONENT)) {
                return new JucheCalendar((PlainDate) chronoEntity.get(PlainDate.COMPONENT));
            }
            int i = chronoEntity.getInt(JucheCalendar.YEAR_OF_ERA);
            if (i != Integer.MIN_VALUE) {
                int prolepticYear = JucheCalendar.toProlepticYear(i);
                if (chronoEntity.contains(JucheCalendar.MONTH_OF_YEAR)) {
                    int value = ((Month) chronoEntity.get(JucheCalendar.MONTH_OF_YEAR)).getValue();
                    int i2 = chronoEntity.getInt(JucheCalendar.DAY_OF_MONTH);
                    if (i2 != Integer.MIN_VALUE) {
                        if (JucheCalendar.CALSYS.isValid(JucheEra.JUCHE, i, value, i2)) {
                            return JucheCalendar.of(i, value, i2);
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Juche date.");
                    }
                } else {
                    int i3 = chronoEntity.getInt(JucheCalendar.DAY_OF_YEAR);
                    if (i3 != Integer.MIN_VALUE) {
                        if (i3 > 0) {
                            int i4 = 0;
                            int i5 = 1;
                            while (i5 <= 12) {
                                int lengthOfMonth = GregorianMath.getLengthOfMonth(prolepticYear, i5) + i4;
                                if (i3 <= lengthOfMonth) {
                                    return JucheCalendar.of(i, i5, i3 - i4);
                                }
                                i5++;
                                i4 = lengthOfMonth;
                            }
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Juche date.");
                    }
                }
                return null;
            }
            chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Juche year.");
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear() - 1911;
        }
    }

    private static class SPX implements Externalizable {
        private static final int JUCHE = 17;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(17);
            writeJuche(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            if (objectInput.readByte() == 17) {
                this.obj = readJuche(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeJuche(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeObject(((JucheCalendar) this.obj).toISO());
        }

        private JucheCalendar readJuche(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            return new JucheCalendar((PlainDate) PlainDate.class.cast(objectInput.readObject()));
        }
    }
}
