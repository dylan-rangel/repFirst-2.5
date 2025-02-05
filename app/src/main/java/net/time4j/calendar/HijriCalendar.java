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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.HijriMonth;
import net.time4j.calendar.service.GenericDatePatterns;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarFamily;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.CalendarVariant;
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
import net.time4j.engine.FormattableElement;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.ValidationElement;
import net.time4j.engine.VariantSource;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("islamic")
/* loaded from: classes3.dex */
public final class HijriCalendar extends CalendarVariant<HijriCalendar> implements LocalizedPatternSupport {
    public static final StdCalendarElement<Integer, HijriCalendar> BOUNDED_WEEK_OF_MONTH;
    public static final StdCalendarElement<Integer, HijriCalendar> BOUNDED_WEEK_OF_YEAR;
    private static final Map<String, EraYearMonthDaySystem<HijriCalendar>> CALSYS;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, HijriCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, HijriCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, HijriCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final CalendarFamily<HijriCalendar> ENGINE;
    public static final StdCalendarElement<Weekday, HijriCalendar> LOCAL_DAY_OF_WEEK;
    public static final String VARIANT_DIYANET = "islamic-diyanet";

    @Deprecated
    public static final String VARIANT_ICU4J = "islamic-icu4j";
    public static final String VARIANT_UMALQURA = "islamic-umalqura";

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<HijriCalendar> WEEKDAY_IN_MONTH;
    public static final StdCalendarElement<Integer, HijriCalendar> WEEK_OF_MONTH;
    public static final StdCalendarElement<Integer, HijriCalendar> WEEK_OF_YEAR;
    private static final WeekdayInMonthElement<HijriCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;
    private static final long serialVersionUID = 4666707700222367373L;
    private final transient int hdom;
    private final transient int hmonth;
    private final transient int hyear;
    private final transient String variant;

    @FormattableElement(format = "G")
    public static final ChronoElement<HijriEra> ERA = new StdEnumDateElement("ERA", HijriCalendar.class, HijriEra.class, 'G');

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, HijriCalendar> YEAR_OF_ERA = new StdIntegerDateElement("YEAR_OF_ERA", HijriCalendar.class, Integer.MIN_VALUE, Integer.MAX_VALUE, 'y', new HijriMonth.Operator(-12), new HijriMonth.Operator(12));

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<HijriMonth, HijriCalendar> MONTH_OF_YEAR = new StdEnumDateElement("MONTH_OF_YEAR", HijriCalendar.class, HijriMonth.class, 'M', new HijriMonth.Operator(-1), new HijriMonth.Operator(1));

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public HijriCalendar getContext() {
        return this;
    }

    static {
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("DAY_OF_MONTH", HijriCalendar.class, 1, 30, 'd');
        DAY_OF_MONTH = stdIntegerDateElement;
        DAY_OF_YEAR = new StdIntegerDateElement("DAY_OF_YEAR", HijriCalendar.class, 1, 355, 'D');
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(HijriCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<HijriCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(HijriCalendar.class, stdIntegerDateElement, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        VariantMap variantMap = new VariantMap();
        variantMap.put(VARIANT_UMALQURA, AstronomicalHijriData.UMALQURA);
        for (HijriAlgorithm hijriAlgorithm : HijriAlgorithm.values()) {
            variantMap.put(hijriAlgorithm.getVariant(), hijriAlgorithm.getCalendarSystem(0));
        }
        CALSYS = variantMap;
        CalendarFamily.Builder appendElement = CalendarFamily.Builder.setUp(HijriCalendar.class, new Merger(), variantMap).appendElement((ChronoElement) ERA, (ElementRule) new EraRule()).appendElement((ChronoElement) YEAR_OF_ERA, (ElementRule) new IntegerRule(0)).appendElement((ChronoElement) MONTH_OF_YEAR, (ElementRule) new MonthRule());
        ChronoElement<Integer> chronoElement = CommonElements.RELATED_GREGORIAN_YEAR;
        StdCalendarElement<Integer, HijriCalendar> stdCalendarElement = DAY_OF_YEAR;
        CalendarFamily.Builder appendElement2 = appendElement.appendElement((ChronoElement) chronoElement, (ElementRule) new RelatedGregorianYearRule(variantMap, stdCalendarElement));
        StdCalendarElement<Integer, HijriCalendar> stdCalendarElement2 = DAY_OF_MONTH;
        CalendarFamily.Builder appendElement3 = appendElement2.appendElement((ChronoElement) stdCalendarElement2, (ElementRule) new IntegerRule(2)).appendElement((ChronoElement) stdCalendarElement, (ElementRule) new IntegerRule(3)).appendElement((ChronoElement) DAY_OF_WEEK, (ElementRule) new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<HijriCalendar, CalendarSystem<HijriCalendar>>() { // from class: net.time4j.calendar.HijriCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<HijriCalendar> apply(HijriCalendar hijriCalendar) {
                return hijriCalendar.getChronology().getCalendarSystem(hijriCalendar.getVariant());
            }
        }));
        WeekdayInMonthElement<HijriCalendar> weekdayInMonthElement2 = WIM_ELEMENT;
        ENGINE = appendElement3.appendElement((ChronoElement) weekdayInMonthElement2, WeekdayInMonthElement.getRule(weekdayInMonthElement2)).appendExtension((ChronoExtension) new CommonElements.Weekengine(HijriCalendar.class, stdCalendarElement2, stdCalendarElement, getDefaultWeekmodel())).build();
        LOCAL_DAY_OF_WEEK = CommonElements.localDayOfWeek(family(), getDefaultWeekmodel());
        WEEK_OF_YEAR = CommonElements.weekOfYear(family(), getDefaultWeekmodel());
        WEEK_OF_MONTH = CommonElements.weekOfMonth(family(), getDefaultWeekmodel());
        BOUNDED_WEEK_OF_YEAR = CommonElements.boundedWeekOfYear(family(), getDefaultWeekmodel());
        BOUNDED_WEEK_OF_MONTH = CommonElements.boundedWeekOfMonth(family(), getDefaultWeekmodel());
    }

    private HijriCalendar(int i, int i2, int i3, String str) {
        this.hyear = i;
        this.hmonth = i2;
        this.hdom = i3;
        this.variant = str;
    }

    public static HijriCalendar of(String str, int i, HijriMonth hijriMonth, int i2) {
        return of(str, i, hijriMonth.getValue(), i2);
    }

    public static HijriCalendar of(String str, int i, int i2, int i3) {
        if (!getCalendarSystem(str).isValid(HijriEra.ANNO_HEGIRAE, i, i2, i3)) {
            throw new IllegalArgumentException("Invalid hijri date: year=" + i + ", month=" + i2 + ", day=" + i3);
        }
        return new HijriCalendar(i, i2, i3, str);
    }

    public static HijriCalendar of(VariantSource variantSource, int i, HijriMonth hijriMonth, int i2) {
        return of(variantSource.getVariant(), i, hijriMonth.getValue(), i2);
    }

    public static HijriCalendar of(VariantSource variantSource, int i, int i2, int i3) {
        return of(variantSource.getVariant(), i, i2, i3);
    }

    public static HijriCalendar ofUmalqura(int i, HijriMonth hijriMonth, int i2) {
        return of(VARIANT_UMALQURA, i, hijriMonth.getValue(), i2);
    }

    public static HijriCalendar ofUmalqura(int i, int i2, int i3) {
        return of(VARIANT_UMALQURA, i, i2, i3);
    }

    public static HijriCalendar nowInSystemTime(String str, StartOfDay startOfDay) {
        return (HijriCalendar) SystemClock.inLocalView().now(family(), str, startOfDay).toDate();
    }

    public static HijriCalendar nowInSystemTime(VariantSource variantSource, StartOfDay startOfDay) {
        return (HijriCalendar) SystemClock.inLocalView().now(family(), variantSource, startOfDay).toDate();
    }

    public HijriEra getEra() {
        return HijriEra.ANNO_HEGIRAE;
    }

    public int getYear() {
        return this.hyear;
    }

    public HijriMonth getMonth() {
        return HijriMonth.valueOf(this.hmonth);
    }

    public int getDayOfMonth() {
        return this.hdom;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(getCalendarSystem(this.variant).transform((EraYearMonthDaySystem<HijriCalendar>) this) + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        return this.variant;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [net.time4j.calendar.EraYearMonthDaySystem] */
    public int lengthOfMonth() {
        return getCalendarSystem2().getLengthOfMonth(HijriEra.ANNO_HEGIRAE, this.hyear, this.hmonth);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.calendar.EraYearMonthDaySystem] */
    public int lengthOfYear() {
        try {
            return getCalendarSystem2().getLengthOfYear(HijriEra.ANNO_HEGIRAE, this.hyear);
        } catch (IllegalArgumentException e) {
            throw new ChronoException(e.getMessage(), e);
        }
    }

    public static boolean isValid(String str, int i, int i2, int i3) {
        EraYearMonthDaySystem<HijriCalendar> eraYearMonthDaySystem = CALSYS.get(str);
        return eraYearMonthDaySystem != null && eraYearMonthDaySystem.isValid(HijriEra.ANNO_HEGIRAE, i, i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HijriCalendar nextYear() {
        return (HijriCalendar) with(YEAR_OF_ERA.incremented());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HijriCalendar nextMonth() {
        return (HijriCalendar) with(MONTH_OF_YEAR.incremented());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HijriCalendar nextDay() {
        return (HijriCalendar) with(DAY_OF_MONTH.incremented());
    }

    public GeneralTimestamp<HijriCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<HijriCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    public HijriCalendar plus(int i, Unit unit) {
        try {
            return unit.addTo(this, i);
        } catch (IllegalArgumentException e) {
            ArithmeticException arithmeticException = new ArithmeticException(e.getMessage());
            arithmeticException.initCause(e);
            throw arithmeticException;
        }
    }

    public HijriCalendar minus(int i, Unit unit) {
        return plus(MathUtils.safeNegate(i), unit);
    }

    @Override // net.time4j.engine.CalendarVariant
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HijriCalendar)) {
            return false;
        }
        HijriCalendar hijriCalendar = (HijriCalendar) obj;
        return this.hdom == hijriCalendar.hdom && this.hmonth == hijriCalendar.hmonth && this.hyear == hijriCalendar.hyear && this.variant.equals(hijriCalendar.variant);
    }

    @Override // net.time4j.engine.CalendarVariant
    public int hashCode() {
        return (((this.hdom * 17) + (this.hmonth * 31)) + (this.hyear * 37)) ^ this.variant.hashCode();
    }

    @Override // net.time4j.engine.CalendarVariant
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("AH-");
        String valueOf = String.valueOf(this.hyear);
        for (int length = valueOf.length(); length < 4; length++) {
            sb.append('0');
        }
        sb.append(valueOf);
        sb.append('-');
        if (this.hmonth < 10) {
            sb.append('0');
        }
        sb.append(this.hmonth);
        sb.append('-');
        if (this.hdom < 10) {
            sb.append('0');
        }
        sb.append(this.hdom);
        sb.append('[');
        sb.append(this.variant);
        sb.append(']');
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1, Weekday.FRIDAY, Weekday.SATURDAY);
    }

    public static CalendarFamily<HijriCalendar> family() {
        return ENGINE;
    }

    public static void register(HijriData hijriData) {
        String str = "islamic-" + hijriData.name();
        hijriData.prepare();
        try {
            CALSYS.put(str, new AstronomicalHijriData(hijriData));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid Hijri data.", e);
        }
    }

    public static String getVersion(String str) {
        EraYearMonthDaySystem<HijriCalendar> calendarSystem = getCalendarSystem(str);
        return calendarSystem instanceof AstronomicalHijriData ? ((AstronomicalHijriData) AstronomicalHijriData.class.cast(calendarSystem)).getVersion() : "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.CalendarVariant, net.time4j.engine.ChronoEntity
    public CalendarFamily<HijriCalendar> getChronology() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.CalendarVariant
    /* renamed from: getCalendarSystem, reason: merged with bridge method [inline-methods] */
    public CalendarSystem<HijriCalendar> getCalendarSystem2() {
        return getCalendarSystem(this.variant);
    }

    private static EraYearMonthDaySystem<HijriCalendar> getCalendarSystem(String str) {
        EraYearMonthDaySystem<HijriCalendar> eraYearMonthDaySystem = CALSYS.get(str);
        if (eraYearMonthDaySystem != null) {
            return eraYearMonthDaySystem;
        }
        throw new ChronoException("Unsupported calendar variant: " + str);
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        YEARS(3.061728E7d),
        MONTHS(2551440.0d),
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

        public int between(HijriCalendar hijriCalendar, HijriCalendar hijriCalendar2, String str) {
            int i = AnonymousClass2.$SwitchMap$net$time4j$calendar$HijriCalendar$Unit[ordinal()];
            if (i == 1) {
                return MONTHS.between(hijriCalendar, hijriCalendar2, str) / 12;
            }
            if (i == 2) {
                HijriCalendar withVariant = hijriCalendar.withVariant(str);
                HijriCalendar withVariant2 = hijriCalendar2.withVariant(str);
                int i2 = (((withVariant2.hyear * 12) + (withVariant2.hmonth - 1)) - (withVariant.hyear * 12)) - (withVariant.hmonth - 1);
                return (i2 <= 0 || withVariant2.hdom >= withVariant.hdom) ? (i2 >= 0 || withVariant2.hdom <= withVariant.hdom) ? i2 : i2 + 1 : i2 - 1;
            }
            if (i == 3) {
                return DAYS.between(hijriCalendar, hijriCalendar2, str) / 7;
            }
            if (i == 4) {
                return (int) CalendarDays.between(hijriCalendar, hijriCalendar2).getAmount();
            }
            throw new UnsupportedOperationException(name());
        }

        public int between(HijriCalendar hijriCalendar, HijriCalendar hijriCalendar2, VariantSource variantSource) {
            return between(hijriCalendar, hijriCalendar2, variantSource.getVariant());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v3, types: [net.time4j.calendar.EraYearMonthDaySystem] */
        HijriCalendar addTo(HijriCalendar hijriCalendar, int i) {
            int i2 = AnonymousClass2.$SwitchMap$net$time4j$calendar$HijriCalendar$Unit[ordinal()];
            if (i2 == 1) {
                return (HijriCalendar) hijriCalendar.with((ChronoElement<Integer>) HijriCalendar.YEAR_OF_ERA, MathUtils.safeAdd(hijriCalendar.getYear(), i));
            }
            if (i2 != 2) {
                if (i2 == 3) {
                    return DAYS.addTo(hijriCalendar, MathUtils.safeMultiply(i, 7));
                }
                if (i2 == 4) {
                    return hijriCalendar.plus(CalendarDays.of(i));
                }
                throw new UnsupportedOperationException(name());
            }
            int safeAdd = MathUtils.safeAdd((hijriCalendar.hyear * 12) + (hijriCalendar.hmonth - 1), i);
            int floorDivide = MathUtils.floorDivide(safeAdd, 12);
            int floorModulo = MathUtils.floorModulo(safeAdd, 12) + 1;
            return HijriCalendar.of(hijriCalendar.getVariant(), floorDivide, floorModulo, Math.min(hijriCalendar.hdom, hijriCalendar.getCalendarSystem2().getLengthOfMonth(HijriEra.ANNO_HEGIRAE, floorDivide, floorModulo)));
        }
    }

    /* renamed from: net.time4j.calendar.HijriCalendar$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$HijriCalendar$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$HijriCalendar$Unit = iArr;
            try {
                iArr[Unit.YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$HijriCalendar$Unit[Unit.MONTHS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$HijriCalendar$Unit[Unit.WEEKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$HijriCalendar$Unit[Unit.DAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static class VariantMap extends ConcurrentHashMap<String, EraYearMonthDaySystem<HijriCalendar>> {
        private VariantMap() {
        }

        @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
        public EraYearMonthDaySystem<HijriCalendar> get(Object obj) {
            EraYearMonthDaySystem<HijriCalendar> eraYearMonthDaySystem = (EraYearMonthDaySystem) super.get(obj);
            if (eraYearMonthDaySystem != null) {
                return eraYearMonthDaySystem;
            }
            String obj2 = obj.toString();
            if (obj.equals(HijriCalendar.VARIANT_UMALQURA)) {
                eraYearMonthDaySystem = AstronomicalHijriData.UMALQURA;
            } else {
                HijriAdjustment from = HijriAdjustment.from(obj2);
                String baseVariant = from.getBaseVariant();
                HijriAlgorithm[] values = HijriAlgorithm.values();
                int length = values.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    HijriAlgorithm hijriAlgorithm = values[i];
                    if (hijriAlgorithm.getVariant().equals(baseVariant)) {
                        eraYearMonthDaySystem = hijriAlgorithm.getCalendarSystem(from.getValue());
                        break;
                    }
                    i++;
                }
                if (eraYearMonthDaySystem == null) {
                    try {
                        eraYearMonthDaySystem = new AstronomicalHijriData(obj2);
                    } catch (IOException | ChronoException unused) {
                        return null;
                    }
                }
            }
            EraYearMonthDaySystem<HijriCalendar> putIfAbsent = putIfAbsent(obj2, eraYearMonthDaySystem);
            return putIfAbsent != null ? putIfAbsent : eraYearMonthDaySystem;
        }
    }

    private static class IntegerRule implements ElementRule<HijriCalendar, Integer> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        /* JADX WARN: Type inference failed for: r1v4, types: [net.time4j.calendar.EraYearMonthDaySystem] */
        @Override // net.time4j.engine.ElementRule
        public Integer getValue(HijriCalendar hijriCalendar) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(hijriCalendar.hyear);
            }
            if (i == 2) {
                return Integer.valueOf(hijriCalendar.hdom);
            }
            if (i == 3) {
                int i2 = 0;
                ?? calendarSystem2 = hijriCalendar.getCalendarSystem2();
                for (int i3 = 1; i3 < hijriCalendar.hmonth; i3++) {
                    i2 += calendarSystem2.getLengthOfMonth(HijriEra.ANNO_HEGIRAE, hijriCalendar.hyear, i3);
                }
                return Integer.valueOf(i2 + hijriCalendar.hdom);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(HijriCalendar hijriCalendar) {
            int i = this.index;
            if (i == 0) {
                CalendarSystem<HijriCalendar> calendarSystem2 = hijriCalendar.getCalendarSystem2();
                return Integer.valueOf(calendarSystem2.transform(calendarSystem2.getMinimumSinceUTC()).hyear);
            }
            if (i == 2 || i == 3) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [net.time4j.calendar.EraYearMonthDaySystem] */
        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(HijriCalendar hijriCalendar) {
            ?? calendarSystem2 = hijriCalendar.getCalendarSystem2();
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(((HijriCalendar) calendarSystem2.transform(calendarSystem2.getMaximumSinceUTC())).hyear);
            }
            if (i == 2) {
                return Integer.valueOf(calendarSystem2.getLengthOfMonth(HijriEra.ANNO_HEGIRAE, hijriCalendar.hyear, hijriCalendar.hmonth));
            }
            if (i == 3) {
                return Integer.valueOf(calendarSystem2.getLengthOfYear(HijriEra.ANNO_HEGIRAE, hijriCalendar.hyear));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HijriCalendar hijriCalendar, Integer num) {
            if (num == null) {
                return false;
            }
            return getMinimum(hijriCalendar).compareTo(num) <= 0 && getMaximum(hijriCalendar).compareTo(num) >= 0;
        }

        /* JADX WARN: Type inference failed for: r9v4, types: [net.time4j.calendar.EraYearMonthDaySystem] */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HijriCalendar withValue2(HijriCalendar hijriCalendar, Integer num, boolean z) {
            if (!isValid2(hijriCalendar, num)) {
                throw new IllegalArgumentException("Out of range: " + num);
            }
            int i = this.index;
            if (i == 0) {
                ?? calendarSystem2 = hijriCalendar.getCalendarSystem2();
                int intValue = num.intValue();
                return HijriCalendar.of(hijriCalendar.getVariant(), intValue, hijriCalendar.hmonth, Math.min(hijriCalendar.hdom, calendarSystem2.getLengthOfMonth(HijriEra.ANNO_HEGIRAE, intValue, hijriCalendar.hmonth)));
            }
            if (i == 2) {
                return new HijriCalendar(hijriCalendar.hyear, hijriCalendar.hmonth, num.intValue(), hijriCalendar.getVariant());
            }
            if (i == 3) {
                return hijriCalendar.plus(CalendarDays.of(num.intValue() - getValue(hijriCalendar).intValue()));
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HijriCalendar hijriCalendar) {
            if (this.index == 0) {
                return HijriCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HijriCalendar hijriCalendar) {
            if (this.index == 0) {
                return HijriCalendar.MONTH_OF_YEAR;
            }
            return null;
        }
    }

    private static class MonthRule implements ElementRule<HijriCalendar, HijriMonth> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HijriCalendar hijriCalendar, HijriMonth hijriMonth) {
            return hijriMonth != null;
        }

        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HijriMonth getValue(HijriCalendar hijriCalendar) {
            return hijriCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public HijriMonth getMinimum(HijriCalendar hijriCalendar) {
            return HijriMonth.MUHARRAM;
        }

        @Override // net.time4j.engine.ElementRule
        public HijriMonth getMaximum(HijriCalendar hijriCalendar) {
            return HijriMonth.DHU_AL_HIJJAH;
        }

        /* JADX WARN: Type inference failed for: r8v2, types: [net.time4j.calendar.EraYearMonthDaySystem] */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HijriCalendar withValue2(HijriCalendar hijriCalendar, HijriMonth hijriMonth, boolean z) {
            if (hijriMonth == null) {
                throw new IllegalArgumentException("Missing month.");
            }
            int value = hijriMonth.getValue();
            return new HijriCalendar(hijriCalendar.hyear, value, Math.min(hijriCalendar.hdom, hijriCalendar.getCalendarSystem2().getLengthOfMonth(HijriEra.ANNO_HEGIRAE, hijriCalendar.hyear, value)), hijriCalendar.getVariant());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HijriCalendar hijriCalendar) {
            return HijriCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HijriCalendar hijriCalendar) {
            return HijriCalendar.DAY_OF_MONTH;
        }
    }

    private static class EraRule implements ElementRule<HijriCalendar, HijriEra> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HijriCalendar hijriCalendar, HijriEra hijriEra) {
            return hijriEra != null;
        }

        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HijriEra getValue(HijriCalendar hijriCalendar) {
            return HijriEra.ANNO_HEGIRAE;
        }

        @Override // net.time4j.engine.ElementRule
        public HijriEra getMinimum(HijriCalendar hijriCalendar) {
            return HijriEra.ANNO_HEGIRAE;
        }

        @Override // net.time4j.engine.ElementRule
        public HijriEra getMaximum(HijriCalendar hijriCalendar) {
            return HijriEra.ANNO_HEGIRAE;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HijriCalendar withValue2(HijriCalendar hijriCalendar, HijriEra hijriEra, boolean z) {
            if (hijriEra != null) {
                return hijriCalendar;
            }
            throw new IllegalArgumentException("Missing era value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HijriCalendar hijriCalendar) {
            return HijriCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HijriCalendar hijriCalendar) {
            return HijriCalendar.YEAR_OF_ERA;
        }
    }

    private static class Merger implements ChronoMerger<HijriCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(HijriCalendar hijriCalendar, AttributeQuery attributeQuery) {
            return hijriCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HijriCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HijriCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("islamic", displayStyle, locale);
        }

        /* JADX WARN: Type inference failed for: r5v1, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HijriCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            String str = (String) attributeQuery.get(Attributes.CALENDAR_VARIANT, "");
            if (str.isEmpty()) {
                return null;
            }
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (HijriCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(HijriCalendar.ENGINE, str, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HijriCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            String str = (String) attributeQuery.get(Attributes.CALENDAR_VARIANT, "");
            if (!str.isEmpty()) {
                EraYearMonthDaySystem eraYearMonthDaySystem = (EraYearMonthDaySystem) HijriCalendar.CALSYS.get(str);
                if (eraYearMonthDaySystem == null) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("Unknown Hijri calendar variant: " + str));
                    return null;
                }
                int i = chronoEntity.getInt(HijriCalendar.YEAR_OF_ERA);
                if (i == Integer.MIN_VALUE) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing islamic year.");
                    return null;
                }
                if (chronoEntity.contains(HijriCalendar.MONTH_OF_YEAR)) {
                    int value = ((HijriMonth) chronoEntity.get(HijriCalendar.MONTH_OF_YEAR)).getValue();
                    int i2 = chronoEntity.getInt(HijriCalendar.DAY_OF_MONTH);
                    if (i2 != Integer.MIN_VALUE) {
                        if (eraYearMonthDaySystem.isValid(HijriEra.ANNO_HEGIRAE, i, value, i2)) {
                            return HijriCalendar.of(str, i, value, i2);
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Hijri date.");
                    }
                } else {
                    int i3 = chronoEntity.getInt(HijriCalendar.DAY_OF_YEAR);
                    if (i3 != Integer.MIN_VALUE) {
                        if (i3 > 0) {
                            int i4 = 0;
                            int i5 = 1;
                            while (i5 <= 12) {
                                int lengthOfMonth = eraYearMonthDaySystem.getLengthOfMonth(HijriEra.ANNO_HEGIRAE, i, i5) + i4;
                                if (i3 <= lengthOfMonth) {
                                    return HijriCalendar.of(str, i, i5, i3 - i4);
                                }
                                i5++;
                                i4 = lengthOfMonth;
                            }
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Hijri date.");
                    }
                }
                return null;
            }
            chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Hijri calendar variant.");
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.EVENING;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return HijriCalendar.nowInSystemTime(HijriAlgorithm.WEST_ISLAMIC_CIVIL, StartOfDay.MIDNIGHT).getYear() + 20;
        }
    }

    private static class SPX implements Externalizable {
        private static final int HIJRI = 1;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(1);
            writeHijri(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 1) {
                this.obj = readHijri(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeHijri(ObjectOutput objectOutput) throws IOException {
            HijriCalendar hijriCalendar = (HijriCalendar) this.obj;
            objectOutput.writeUTF(hijriCalendar.getVariant());
            objectOutput.writeUTF(HijriCalendar.getVersion(hijriCalendar.getVariant()));
            objectOutput.writeInt(hijriCalendar.getYear());
            objectOutput.writeByte(hijriCalendar.getMonth().getValue());
            objectOutput.writeByte(hijriCalendar.getDayOfMonth());
        }

        private HijriCalendar readHijri(ObjectInput objectInput) throws IOException {
            String readUTF = objectInput.readUTF();
            String readUTF2 = objectInput.readUTF();
            if (!HijriCalendar.getVersion(readUTF).equals(readUTF2)) {
                throw new InvalidObjectException("Hijri calendar object with different data version not supported: " + readUTF + "/" + readUTF2);
            }
            return HijriCalendar.of(readUTF, objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
        }
    }
}
