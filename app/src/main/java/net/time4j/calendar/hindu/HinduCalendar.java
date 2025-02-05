package net.time4j.calendar.hindu;

import androidx.exifinterface.media.ExifInterface;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.IndianCalendar;
import net.time4j.calendar.IndianMonth;
import net.time4j.calendar.StdCalendarElement;
import net.time4j.calendar.astro.GeoLocation;
import net.time4j.calendar.astro.SolarTime;
import net.time4j.calendar.astro.StdSolarCalculator;
import net.time4j.calendar.service.Java8Function;
import net.time4j.calendar.service.RelatedGregorianYearRule;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.calendar.service.WeekdayRule;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarFamily;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.IntElementRule;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.ValidationElement;
import net.time4j.engine.VariantSource;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.DisplayElement;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.NumberSystem;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;
import net.time4j.format.internal.DualFormatElement;
import net.time4j.format.internal.DualFormatHelper;
import net.time4j.tz.TZID;
import net.time4j.tz.ZonalOffset;

@CalendarType("hindu")
/* loaded from: classes3.dex */
public final class HinduCalendar extends CalendarVariant<HinduCalendar> implements LocalizedPatternSupport {
    private static final Map<String, HinduCS> CALSYS;
    private static final int DAY_OF_YEAR_INDEX = 1;
    private static final CalendarFamily<HinduCalendar> ENGINE;
    private static final int MAX_YEAR = 5999;
    private static final int MIN_YEAR = 1200;
    private static final int YEAR_INDEX = 0;
    private static final long serialVersionUID = 4078031838043675524L;
    private final transient HinduDay dayOfMonth;
    private final transient int kyYear;
    private final transient HinduMonth month;
    private final transient long utcDays;
    private final transient HinduVariant variant;

    @FormattableElement(format = "G")
    public static final ChronoElement<HinduEra> ERA = new StdEnumDateElement("ERA", HinduCalendar.class, HinduEra.class, 'G');

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final StdCalendarElement<Integer, HinduCalendar> YEAR_OF_ERA = new StdIntegerDateElement("YEAR_OF_ERA", HinduCalendar.class, 0, 6000, 'y');

    @FormattableElement(format = "M")
    public static final AdjustableTextElement<HinduMonth> MONTH_OF_YEAR = MonthElement.SINGLETON;

    @FormattableElement(format = "d")
    public static final AdjustableTextElement<HinduDay> DAY_OF_MONTH = DayOfMonthElement.SINGLETON;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, HinduCalendar> DAY_OF_YEAR = new StdIntegerDateElement("DAY_OF_YEAR", HinduCalendar.class, 1, 365, 'D');

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, HinduCalendar> DAY_OF_WEEK = new StdWeekdayElement(HinduCalendar.class, IndianCalendar.getDefaultWeekmodel());

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public HinduCalendar getContext() {
        return this;
    }

    static {
        VariantMap variantMap = new VariantMap();
        for (HinduRule hinduRule : HinduRule.values()) {
            variantMap.accept(hinduRule.variant());
        }
        variantMap.accept(HinduVariant.VAR_OLD_SOLAR);
        variantMap.accept(HinduVariant.VAR_OLD_LUNAR);
        CALSYS = variantMap;
        CalendarFamily.Builder up = CalendarFamily.Builder.setUp(HinduCalendar.class, new Merger(), variantMap);
        ChronoElement<Integer> chronoElement = CommonElements.RELATED_GREGORIAN_YEAR;
        StdCalendarElement<Integer, HinduCalendar> stdCalendarElement = DAY_OF_YEAR;
        ENGINE = up.appendElement((ChronoElement) chronoElement, (ElementRule) new RelatedGregorianYearRule(variantMap, stdCalendarElement)).appendElement((ChronoElement) ERA, (ElementRule) new EraRule()).appendElement((ChronoElement) YEAR_OF_ERA, (ElementRule) new IntegerRule(0)).appendElement((ChronoElement) MONTH_OF_YEAR, (ElementRule) MonthElement.SINGLETON).appendElement((ChronoElement) DAY_OF_MONTH, (ElementRule) DayOfMonthElement.SINGLETON).appendElement((ChronoElement) stdCalendarElement, (ElementRule) new IntegerRule(1)).appendElement((ChronoElement) DAY_OF_WEEK, (ElementRule) new WeekdayRule(IndianCalendar.getDefaultWeekmodel(), new Java8Function<HinduCalendar, CalendarSystem<HinduCalendar>>() { // from class: net.time4j.calendar.hindu.HinduCalendar.1
            @Override // net.time4j.calendar.service.Java8Function
            public CalendarSystem<HinduCalendar> apply(HinduCalendar hinduCalendar) {
                return hinduCalendar.getCalendarSystem();
            }
        })).build();
    }

    HinduCalendar(HinduVariant hinduVariant, int i, HinduMonth hinduMonth, HinduDay hinduDay, long j) {
        if (hinduVariant == null) {
            throw new NullPointerException("Missing variant.");
        }
        if (hinduMonth == null) {
            throw new NullPointerException("Missing month.");
        }
        if (hinduDay == null) {
            throw new NullPointerException("Missing day of month.");
        }
        this.variant = hinduVariant;
        this.kyYear = i;
        this.month = hinduMonth;
        this.dayOfMonth = hinduDay;
        this.utcDays = j;
    }

    public static HinduCalendar nowInSystemTime(HinduVariant hinduVariant) {
        return nowInSystemTime(hinduVariant, family().getDefaultStartOfDay());
    }

    public static HinduCalendar nowInSystemTime(HinduVariant hinduVariant, StartOfDay startOfDay) {
        return (HinduCalendar) SystemClock.inLocalView().now(family(), hinduVariant, startOfDay).toDate();
    }

    public static HinduCalendar ofOldSolar(int i, int i2, int i3) {
        if (i3 > 31) {
            throw new IllegalArgumentException("Day-of-month out of range: " + i3);
        }
        return of(HinduVariant.VAR_OLD_SOLAR, HinduEra.KALI_YUGA, i, HinduMonth.ofSolar(i2), HinduDay.valueOf(i3));
    }

    public static HinduCalendar ofOldLunar(int i, HinduMonth hinduMonth, int i2) {
        if (i2 > 30) {
            throw new IllegalArgumentException("Day-of-month out of range: " + i2);
        }
        return of(HinduVariant.VAR_OLD_LUNAR, HinduEra.KALI_YUGA, i, hinduMonth, HinduDay.valueOf(i2));
    }

    public static HinduCalendar of(HinduVariant hinduVariant, HinduEra hinduEra, int i, HinduMonth hinduMonth, HinduDay hinduDay) {
        HinduCS calendarSystem = hinduVariant.with(hinduEra).getCalendarSystem();
        int yearOfEra = HinduEra.KALI_YUGA.yearOfEra(hinduEra, i);
        if (!hinduVariant.isUsingElapsedYears()) {
            yearOfEra--;
        }
        if (yearOfEra < 0) {
            throw new IllegalArgumentException("Kali yuga year must not be smaller than 0: " + yearOfEra);
        }
        if (!hinduVariant.isOld() && yearOfEra < MIN_YEAR) {
            throw new IllegalArgumentException("Year out of range in modern Hindu calendar: " + yearOfEra);
        }
        if (calendarSystem.isValid(yearOfEra, hinduMonth, hinduDay)) {
            return calendarSystem.create(yearOfEra, hinduMonth, hinduDay);
        }
        throw new IllegalArgumentException("Invalid values: " + hinduVariant + "[" + hinduEra + "/" + i + "/" + hinduMonth + "/" + hinduDay + "]");
    }

    public static boolean isValid(HinduVariant hinduVariant, HinduEra hinduEra, int i, HinduMonth hinduMonth, HinduDay hinduDay) {
        HinduCS calendarSystem = hinduVariant.with(hinduEra).getCalendarSystem();
        int yearOfEra = HinduEra.KALI_YUGA.yearOfEra(hinduEra, i);
        if (!hinduVariant.isUsingElapsedYears()) {
            yearOfEra--;
        }
        if (yearOfEra < 0) {
            return false;
        }
        if (hinduVariant.isOld() || yearOfEra >= MIN_YEAR) {
            return calendarSystem.isValid(yearOfEra, hinduMonth, hinduDay);
        }
        return false;
    }

    public int lengthOfMonth() {
        return (int) (withMidOfMonth(1).withFirstDayOfMonth().utcDays - withFirstDayOfMonth().utcDays);
    }

    public int lengthOfYear() {
        return ((Integer) getMaximum(DAY_OF_YEAR)).intValue();
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        return this.variant.getVariant();
    }

    public HinduEra getEra() {
        HinduEra defaultEra = this.variant.getDefaultEra();
        return defaultEra.yearOfEra(HinduEra.KALI_YUGA, this.kyYear) < 0 ? HinduEra.KALI_YUGA : defaultEra;
    }

    public int getYear() {
        int yearOfEra = getEra().yearOfEra(HinduEra.KALI_YUGA, this.kyYear);
        return !this.variant.isUsingElapsedYears() ? yearOfEra + 1 : yearOfEra;
    }

    public HinduMonth getMonth() {
        return this.month;
    }

    public HinduDay getDayOfMonth() {
        return this.dayOfMonth;
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(this.utcDays + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return (int) ((this.utcDays - withNewYear().utcDays) + 1);
    }

    public HinduCalendar previousDay() {
        return getCalendarSystem().transform(this.utcDays - 1);
    }

    public HinduCalendar previousMonth() {
        HinduCalendar withAdjustedDayInMonth = withMidOfMonth(-1).withAdjustedDayInMonth(this.dayOfMonth);
        if (withAdjustedDayInMonth.utcDays >= this.variant.getCalendarSystem().getMinimumSinceUTC()) {
            return withAdjustedDayInMonth;
        }
        throw new IllegalArgumentException("Hindu date out of range");
    }

    public HinduCalendar previousYear() {
        return withYearChangedBy(-1);
    }

    public HinduCalendar nextDay() {
        return getCalendarSystem().transform(this.utcDays + 1);
    }

    public HinduCalendar nextMonth() {
        HinduCalendar withAdjustedDayInMonth = withMidOfMonth(1).withAdjustedDayInMonth(this.dayOfMonth);
        if (withAdjustedDayInMonth.utcDays <= this.variant.getCalendarSystem().getMaximumSinceUTC()) {
            return withAdjustedDayInMonth;
        }
        throw new IllegalArgumentException("Hindu date out of range");
    }

    public HinduCalendar nextYear() {
        return withYearChangedBy(1);
    }

    public GeneralTimestamp<HinduCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<HinduCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.CalendarVariant
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HinduCalendar)) {
            return false;
        }
        HinduCalendar hinduCalendar = (HinduCalendar) obj;
        return this.variant.equals(hinduCalendar.variant) && this.kyYear == hinduCalendar.kyYear && this.month.equals(hinduCalendar.month) && this.dayOfMonth.equals(hinduCalendar.dayOfMonth) && this.utcDays == hinduCalendar.utcDays;
    }

    @Override // net.time4j.engine.CalendarVariant
    public int hashCode() {
        return (this.variant.hashCode() * 7) + (this.kyYear * 17) + (this.month.hashCode() * 31) + (this.dayOfMonth.hashCode() * 37);
    }

    @Override // net.time4j.engine.CalendarVariant
    public String toString() {
        return '[' + this.variant + ",era=" + getEra() + ",year-of-era=" + getYear() + ",month=" + this.month + ",day-of-month=" + this.dayOfMonth + ']';
    }

    @Override // net.time4j.engine.CalendarVariant, net.time4j.engine.CalendarDate
    public long getDaysSinceEpochUTC() {
        return this.utcDays;
    }

    public static CalendarFamily<HinduCalendar> family() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.CalendarVariant, net.time4j.engine.ChronoEntity
    public CalendarFamily<HinduCalendar> getChronology() {
        return ENGINE;
    }

    @Override // net.time4j.engine.CalendarVariant
    protected CalendarSystem<HinduCalendar> getCalendarSystem() {
        return this.variant.getCalendarSystem();
    }

    int getExpiredYearOfKaliYuga() {
        return this.kyYear;
    }

    HinduCalendar withNewYear() {
        HinduMonth ofLunisolar;
        if (this.variant.isPurnimanta()) {
            return this.variant.getCalendarSystem().create(this.variant.toAmanta().create(this.kyYear, HinduMonth.ofLunisolar(1), HinduDay.valueOf(15)).withNewYear().getDaysSinceEpochUTC());
        }
        if (this.variant.isSolar()) {
            ofLunisolar = HinduMonth.ofSolar(1);
        } else {
            ofLunisolar = HinduMonth.ofLunisolar(this.variant.getFirstMonthOfYear());
        }
        HinduCS calendarSystem = this.variant.getCalendarSystem();
        HinduCalendar create = calendarSystem.create(this.kyYear, ofLunisolar, HinduDay.valueOf(15));
        if (this.variant.isLunisolar()) {
            HinduCalendar create2 = calendarSystem.create(create.utcDays - 30);
            if (create2.getMonth().isLeap() && create2.kyYear == this.kyYear) {
                create = create2;
            }
        }
        return create.withFirstDayOfMonth();
    }

    HinduCalendar withFirstDayOfMonth() {
        HinduDay valueOf = HinduDay.valueOf(1);
        HinduCS calendarSystem = this.variant.getCalendarSystem();
        int i = this.kyYear;
        if (this.variant.isLunisolar()) {
            int i2 = 3;
            if (this.variant.isPurnimanta()) {
                HinduDay valueOf2 = HinduDay.valueOf(16);
                if (isChaitra() && this.dayOfMonth.getValue() < 16) {
                    if (this.month.equals(withNewYear().month)) {
                        i--;
                    }
                }
                valueOf = valueOf2;
            }
            while (calendarSystem.isExpunged(i, this.month, valueOf)) {
                if (i2 == 0) {
                    throw new IllegalArgumentException("Cannot determine first day of month: " + this);
                }
                if (valueOf.isLeap()) {
                    valueOf = HinduDay.valueOf(valueOf.getValue() + 1);
                } else {
                    valueOf = valueOf.withLeap();
                }
                i2--;
            }
        }
        return calendarSystem.create(i, this.month, valueOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HinduCalendar withMidOfMonth(int i) {
        int value = this.dayOfMonth.getValue();
        if (this.variant.isPurnimanta()) {
            value = value >= 16 ? value - 15 : value + 15;
        }
        return this.variant.getCalendarSystem().create(((this.utcDays + Math.round(i * (this.variant.isSolar() ? 30.4d : 29.5d))) + 15) - value);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private HinduCalendar withYearChangedBy(int i) {
        return (HinduCalendar) with((ChronoElement<Integer>) YEAR_OF_ERA, getYear() + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HinduCalendar withAdjustedDayInMonth(HinduDay hinduDay) {
        HinduCS calendarSystem = this.variant.getCalendarSystem();
        boolean isPurnimanta = this.variant.isPurnimanta();
        boolean z = isPurnimanta && isChaitra() && withNewYear().month.equals(this.month);
        int i = 5;
        HinduDay hinduDay2 = hinduDay;
        while (true) {
            int criticalYear = criticalYear(z, hinduDay2);
            if (calendarSystem.isExpunged(criticalYear, this.month, hinduDay2)) {
                if (hinduDay2.getValue() == (isPurnimanta ? 16 : 1) && !hinduDay2.isLeap()) {
                    return withFirstDayOfMonth();
                }
                if (i == 0) {
                    if (calendarSystem.isExpunged(criticalYear, this.month)) {
                        throw new IllegalArgumentException("Kshaia (lost) month is never valid: kali-yuga-year=" + criticalYear + ", month=" + this.month);
                    }
                    throw new IllegalArgumentException("No valid day found for: " + this + " => (desired day=" + hinduDay + ")");
                }
                if (hinduDay2.isLeap()) {
                    hinduDay2 = HinduDay.valueOf(hinduDay2.getValue());
                } else {
                    int value = hinduDay2.getValue() - 1;
                    if (isPurnimanta && value == 0) {
                        value = 30;
                    }
                    hinduDay2 = HinduDay.valueOf(value);
                    if (this.variant.isLunisolar()) {
                        hinduDay2 = hinduDay2.withLeap();
                    }
                }
                i--;
            } else {
                return calendarSystem.create(criticalYear, this.month, hinduDay2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int criticalYear(boolean z, HinduDay hinduDay) {
        if (z) {
            if (this.dayOfMonth.getValue() >= 16 && hinduDay.getValue() < 16) {
                return this.kyYear + 1;
            }
            if (this.dayOfMonth.getValue() < 16 && hinduDay.getValue() >= 16) {
                return this.kyYear - 1;
            }
        }
        return this.kyYear;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isChaitra() {
        return this.month.getValue().equals(IndianMonth.CHAITRA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0036 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int parseLeadingLeapInfo(java.lang.CharSequence r3, int r4, int r5, boolean r6, java.lang.String r7, char r8, java.util.Locale r9) {
        /*
            int r0 = r7.length()
            int r0 = r0 + r4
            r1 = 1
            if (r0 >= r5) goto L2d
            java.lang.CharSequence r2 = r3.subSequence(r4, r0)
            java.lang.String r2 = r2.toString()
            if (r6 == 0) goto L1a
            java.lang.String r7 = r7.toUpperCase(r9)
            java.lang.String r2 = r2.toUpperCase(r9)
        L1a:
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L2d
            if (r0 >= r5) goto L2f
            char r5 = r3.charAt(r0)
            r7 = 32
            if (r5 != r7) goto L2f
            int r0 = r0 + 1
            goto L2f
        L2d:
            r1 = 0
            r0 = r4
        L2f:
            if (r1 != 0) goto L36
            int r3 = parseLeapIndicator(r3, r4, r6, r8)
            return r3
        L36:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.hindu.HinduCalendar.parseLeadingLeapInfo(java.lang.CharSequence, int, int, boolean, java.lang.String, char, java.util.Locale):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int parseTrailingLeapInfo(java.lang.CharSequence r3, int r4, int r5, boolean r6, java.lang.String r7, char r8, java.util.Locale r9) {
        /*
            int r0 = r7.length()
            int r0 = r0 + r4
            if (r0 >= r5) goto L14
            char r1 = r3.charAt(r4)
            r2 = 32
            if (r1 != r2) goto L14
            int r1 = r4 + 1
            int r0 = r0 + 1
            goto L15
        L14:
            r1 = r4
        L15:
            if (r0 >= r5) goto L31
            java.lang.CharSequence r5 = r3.subSequence(r1, r0)
            java.lang.String r5 = r5.toString()
            if (r6 == 0) goto L29
            java.lang.String r7 = r7.toUpperCase(r9)
            java.lang.String r5 = r5.toUpperCase(r9)
        L29:
            boolean r5 = r7.equals(r5)
            if (r5 == 0) goto L31
            r5 = 1
            goto L33
        L31:
            r5 = 0
            r0 = r1
        L33:
            if (r5 != 0) goto L3a
            int r3 = parseLeapIndicator(r3, r4, r6, r8)
            return r3
        L3a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.hindu.HinduCalendar.parseTrailingLeapInfo(java.lang.CharSequence, int, int, boolean, java.lang.String, char, java.util.Locale):int");
    }

    private static int parseLeapIndicator(CharSequence charSequence, int i, boolean z, char c) {
        char charAt = charSequence.charAt(i);
        if (z) {
            charAt = Character.toUpperCase(charAt);
            c = Character.toUpperCase(c);
        }
        if (charAt == c) {
            return i + 1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HinduVariant getVariant(ChronoDisplay chronoDisplay, AttributeQuery attributeQuery) {
        if (chronoDisplay instanceof VariantSource) {
            return HinduVariant.from(((VariantSource) chronoDisplay).getVariant());
        }
        if (attributeQuery.contains(Attributes.CALENDAR_VARIANT)) {
            return HinduVariant.from((String) attributeQuery.get(Attributes.CALENDAR_VARIANT));
        }
        throw new IllegalArgumentException("Cannot infer Hindu calendar variant: " + (chronoDisplay == null ? "<attributes>" : chronoDisplay.toString()));
    }

    private Object writeReplace() {
        return new SPX(this, 20);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class VariantMap extends ConcurrentHashMap<String, HinduCS> {
        private VariantMap() {
        }

        @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
        public HinduCS get(Object obj) {
            HinduCS hinduCS = (HinduCS) super.get(obj);
            if (hinduCS != null) {
                return hinduCS;
            }
            String obj2 = obj.toString();
            HinduCS calendarSystem = HinduVariant.from(obj2).getCalendarSystem();
            HinduCS putIfAbsent = putIfAbsent(obj2, calendarSystem);
            return putIfAbsent != null ? putIfAbsent : calendarSystem;
        }

        void accept(HinduVariant hinduVariant) {
            put(hinduVariant.getVariant(), hinduVariant.getCalendarSystem());
        }
    }

    private static class EraRule implements ElementRule<HinduCalendar, HinduEra> {
        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HinduEra getValue(HinduCalendar hinduCalendar) {
            return hinduCalendar.getEra();
        }

        @Override // net.time4j.engine.ElementRule
        public HinduEra getMinimum(HinduCalendar hinduCalendar) {
            return HinduEra.KALI_YUGA;
        }

        @Override // net.time4j.engine.ElementRule
        public HinduEra getMaximum(HinduCalendar hinduCalendar) {
            if (!hinduCalendar.variant.isOld()) {
                HinduEra[] values = HinduEra.values();
                for (int length = values.length - 1; length >= 1; length--) {
                    HinduEra hinduEra = values[length];
                    if (hinduEra.yearOfEra(HinduEra.KALI_YUGA, hinduCalendar.kyYear) >= 0) {
                        return hinduEra;
                    }
                }
            }
            return HinduEra.KALI_YUGA;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HinduCalendar hinduCalendar, HinduEra hinduEra) {
            if (hinduCalendar.variant.isOld()) {
                if (hinduEra == HinduEra.KALI_YUGA) {
                    return true;
                }
            } else if (hinduEra != null) {
                return true;
            }
            return false;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HinduCalendar withValue2(HinduCalendar hinduCalendar, HinduEra hinduEra, boolean z) {
            if (isValid2(hinduCalendar, hinduEra)) {
                HinduVariant with = hinduCalendar.variant.with(hinduEra);
                return with == hinduCalendar.variant ? hinduCalendar : new HinduCalendar(with, hinduCalendar.kyYear, hinduCalendar.month, hinduCalendar.dayOfMonth, hinduCalendar.utcDays);
            }
            throw new IllegalArgumentException("Invalid Hindu era: " + hinduEra);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HinduCalendar hinduCalendar) {
            return HinduCalendar.YEAR_OF_ERA;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HinduCalendar hinduCalendar) {
            return HinduCalendar.YEAR_OF_ERA;
        }
    }

    private static class IntegerRule implements IntElementRule<HinduCalendar> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.IntElementRule
        public int getInt(HinduCalendar hinduCalendar) {
            int i = this.index;
            if (i == 0) {
                return hinduCalendar.getYear();
            }
            if (i == 1) {
                return hinduCalendar.getDayOfYear();
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(HinduCalendar hinduCalendar, int i) {
            return getMin(hinduCalendar) <= i && getMax(hinduCalendar) >= i;
        }

        @Override // net.time4j.engine.IntElementRule
        public HinduCalendar withValue(HinduCalendar hinduCalendar, int i, boolean z) {
            if (!isValid(hinduCalendar, i)) {
                throw new IllegalArgumentException("Out of range: " + i);
            }
            int i2 = this.index;
            if (i2 != 0) {
                if (i2 == 1) {
                    return hinduCalendar.plus(CalendarDays.of(i - getInt(hinduCalendar)));
                }
                throw new UnsupportedOperationException("Unknown element index: " + this.index);
            }
            int yearOfEra = HinduEra.KALI_YUGA.yearOfEra(hinduCalendar.getEra(), i);
            if (!hinduCalendar.variant.isUsingElapsedYears()) {
                yearOfEra--;
            }
            if (yearOfEra == hinduCalendar.kyYear) {
                return hinduCalendar;
            }
            int i3 = 15;
            if (hinduCalendar.variant.isPurnimanta()) {
                i3 = hinduCalendar.dayOfMonth.getValue() >= 16 ? 29 : 2;
            }
            HinduCS calendarSystem = hinduCalendar.variant.getCalendarSystem();
            HinduMonth hinduMonth = hinduCalendar.month;
            boolean isExpunged = calendarSystem.isExpunged(yearOfEra, hinduMonth);
            if (isExpunged) {
                hinduMonth = HinduMonth.of(hinduCalendar.month.getValue().roll(yearOfEra > hinduCalendar.kyYear ? -1 : 1));
                if (yearOfEra < hinduCalendar.kyYear) {
                    HinduMonth hinduMonth2 = calendarSystem.create(calendarSystem.create(yearOfEra, hinduMonth, HinduDay.valueOf(i3)).getDaysSinceEpochUTC() - 30).month;
                    if (hinduMonth2.equals(hinduMonth.withLeap())) {
                        hinduMonth = hinduMonth2;
                    }
                }
            }
            HinduCalendar create = calendarSystem.create(yearOfEra, hinduMonth, HinduDay.valueOf(i3));
            if (!isExpunged && hinduMonth.isLeap()) {
                create = calendarSystem.transform(create.utcDays);
                if (create.month.getValue().getValue() > hinduMonth.getValue().getValue()) {
                    create = calendarSystem.create(create.utcDays - 30);
                }
            }
            return create.withAdjustedDayInMonth(hinduCalendar.dayOfMonth);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(HinduCalendar hinduCalendar) {
            return Integer.valueOf(getInt(hinduCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(HinduCalendar hinduCalendar) {
            return Integer.valueOf(getMin(hinduCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(HinduCalendar hinduCalendar) {
            return Integer.valueOf(getMax(hinduCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(HinduCalendar hinduCalendar, Integer num) {
            return num != null && isValid(hinduCalendar, num.intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public HinduCalendar withValue2(HinduCalendar hinduCalendar, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing new value.");
            }
            return withValue(hinduCalendar, num.intValue(), z);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HinduCalendar hinduCalendar) {
            if (this.index == 0) {
                return HinduCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HinduCalendar hinduCalendar) {
            if (this.index == 0) {
                return HinduCalendar.MONTH_OF_YEAR;
            }
            return null;
        }

        private int getMin(HinduCalendar hinduCalendar) {
            int i = this.index;
            if (i == 0) {
                int i2 = hinduCalendar.variant.isOld() ? 0 : HinduCalendar.MIN_YEAR;
                return hinduCalendar.variant.isUsingElapsedYears() ? i2 : i2 + 1;
            }
            if (i == 1) {
                return 1;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        private int getMax(HinduCalendar hinduCalendar) {
            int i = this.index;
            if (i == 0) {
                return hinduCalendar.getEra().yearOfEra(HinduEra.KALI_YUGA, hinduCalendar.variant.isUsingElapsedYears() ? HinduCalendar.MAX_YEAR : 6000);
            }
            if (i == 1) {
                HinduCalendar withNewYear = hinduCalendar.withNewYear();
                return (int) (hinduCalendar.variant.getCalendarSystem().create(withNewYear.utcDays + 400).withNewYear().utcDays - withNewYear.utcDays);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }
    }

    private static class MonthElement extends DisplayElement<HinduMonth> implements AdjustableTextElement<HinduMonth>, ElementRule<HinduCalendar, HinduMonth> {
        static final MonthElement SINGLETON = new MonthElement();
        private static final long serialVersionUID = 7462717336727909653L;

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'M';
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

        private MonthElement() {
            super("MONTH_OF_YEAR");
        }

        @Override // net.time4j.calendar.hindu.AdjustableTextElement
        public ChronoOperator<HinduCalendar> minimized() {
            return new ChronoOperator<HinduCalendar>() { // from class: net.time4j.calendar.hindu.HinduCalendar.MonthElement.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // net.time4j.engine.ChronoOperator
                public HinduCalendar apply(HinduCalendar hinduCalendar) {
                    MonthElement monthElement = MonthElement.this;
                    return (HinduCalendar) hinduCalendar.with((ChronoElement<MonthElement>) monthElement, (MonthElement) hinduCalendar.getMinimum(monthElement));
                }
            };
        }

        @Override // net.time4j.calendar.hindu.AdjustableTextElement
        public ChronoOperator<HinduCalendar> maximized() {
            return new ChronoOperator<HinduCalendar>() { // from class: net.time4j.calendar.hindu.HinduCalendar.MonthElement.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // net.time4j.engine.ChronoOperator
                public HinduCalendar apply(HinduCalendar hinduCalendar) {
                    MonthElement monthElement = MonthElement.this;
                    return (HinduCalendar) hinduCalendar.with((ChronoElement<MonthElement>) monthElement, (MonthElement) hinduCalendar.getMaximum(monthElement));
                }
            };
        }

        @Override // net.time4j.engine.ElementRule
        public HinduMonth getValue(HinduCalendar hinduCalendar) {
            return hinduCalendar.month;
        }

        @Override // net.time4j.engine.ElementRule
        public HinduMonth getMinimum(HinduCalendar hinduCalendar) {
            return hinduCalendar.withNewYear().month;
        }

        @Override // net.time4j.engine.ElementRule
        public HinduMonth getMaximum(HinduCalendar hinduCalendar) {
            if (!hinduCalendar.variant.isSolar()) {
                return hinduCalendar.variant.getCalendarSystem().create(hinduCalendar.withNewYear().utcDays - 20).month;
            }
            return HinduMonth.ofSolar(12);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HinduCalendar hinduCalendar, HinduMonth hinduMonth) {
            if (hinduMonth == null || (hinduMonth.isLeap() && hinduCalendar.variant.isSolar())) {
                return false;
            }
            if (hinduMonth.isLeap()) {
                int i = 0;
                for (HinduCalendar withNewYear = hinduCalendar.withNewYear(); !withNewYear.month.equals(hinduMonth); withNewYear = withNewYear.nextMonth()) {
                    if (!withNewYear.month.isLeap() && (i = i + 1) >= 12) {
                        return false;
                    }
                }
            }
            if (!hinduCalendar.variant.isLunisolar() || hinduCalendar.variant.isOld()) {
                return true;
            }
            return !hinduCalendar.variant.getCalendarSystem().isExpunged(hinduCalendar.kyYear, hinduMonth);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HinduCalendar withValue2(HinduCalendar hinduCalendar, HinduMonth hinduMonth, boolean z) {
            if (hinduMonth == null || (hinduMonth.isLeap() && hinduCalendar.variant.isSolar())) {
                throw new IllegalArgumentException("Invalid month: " + hinduMonth);
            }
            HinduCalendar withNewYear = hinduCalendar.withNewYear();
            int i = 0;
            while (!withNewYear.month.equals(hinduMonth)) {
                if (!withNewYear.month.isLeap() && (i = i + 1) >= 12) {
                    throw new IllegalArgumentException("Invalid month: " + hinduMonth);
                }
                withNewYear = withNewYear.nextMonth();
            }
            return withNewYear.withAdjustedDayInMonth(hinduCalendar.dayOfMonth);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HinduCalendar hinduCalendar) {
            return HinduCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HinduCalendar hinduCalendar) {
            return HinduCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<HinduMonth> getType() {
            return HinduMonth.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public HinduMonth getDefaultMinimum() {
            return HinduMonth.ofLunisolar(1);
        }

        @Override // net.time4j.engine.ChronoElement
        public HinduMonth getDefaultMaximum() {
            return HinduMonth.ofLunisolar(12);
        }

        protected Object readResolve() throws ObjectStreamException {
            return SINGLETON;
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            char c;
            CharSequence charSequence;
            boolean z;
            HinduVariant variant = HinduCalendar.getVariant(chronoDisplay, attributeQuery);
            Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
            int intValue = ((Integer) attributeQuery.get(DualFormatElement.COUNT_OF_PATTERN_SYMBOLS, 0)).intValue();
            HinduMonth hinduMonth = (HinduMonth) chronoDisplay.get(HinduCalendar.MONTH_OF_YEAR);
            if (hinduMonth.isLeap()) {
                Map<String, String> textForms = CalendarText.getInstance("generic", locale).getTextForms();
                z = ((Boolean) attributeQuery.get(HinduPrimitive.ADHIKA_IS_TRAILING, Boolean.valueOf("R".equals(textForms.get("leap-alignment"))))).booleanValue();
                c = ((Character) attributeQuery.get(HinduPrimitive.ADHIKA_INDICATOR, Character.valueOf(textForms.get("leap-indicator").charAt(0)))).charValue();
                charSequence = (String) CalendarText.getInstance("hindu", locale).getTextForms().get("adhika");
            } else {
                c = '*';
                charSequence = "";
                z = false;
            }
            if (intValue == 0) {
                if (variant.isSolar() && ((Boolean) attributeQuery.get(HinduMonth.RASI_NAMES, Boolean.valueOf(variant.prefersRasiNames()))).booleanValue()) {
                    appendable.append(hinduMonth.getRasi(locale));
                    return;
                }
                TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
                OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
                if (hinduMonth.isLeap() && !z) {
                    if (textWidth == TextWidth.WIDE) {
                        appendable.append(charSequence);
                        appendable.append(' ');
                    } else {
                        appendable.append(c);
                    }
                    hinduMonth = HinduMonth.of(hinduMonth.getValue());
                }
                appendable.append(hinduMonth.getDisplayName(locale, textWidth, outputContext));
                if (z) {
                    if (textWidth == TextWidth.WIDE) {
                        appendable.append(' ');
                        appendable.append(charSequence);
                        return;
                    } else {
                        appendable.append(c);
                        return;
                    }
                }
                return;
            }
            if (hinduMonth.isLeap() && !z) {
                appendable.append(c);
            }
            int rasi = variant.isSolar() ? hinduMonth.getRasi() : hinduMonth.getValue().getValue();
            NumberSystem numberSystem = (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ARABIC);
            char charValue = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, Character.valueOf(numberSystem.getDigits().charAt(0)))).charValue();
            String numeral = DualFormatHelper.toNumeral(numberSystem, charValue, rasi);
            if (variant.isSolar() && numberSystem.isDecimal()) {
                for (int length = intValue - numeral.length(); length > 0; length--) {
                    appendable.append(charValue);
                }
            }
            appendable.append(numeral);
            if (z) {
                appendable.append(c);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x00e1  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x00e5  */
        @Override // net.time4j.format.TextElement
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public net.time4j.calendar.hindu.HinduMonth parse(java.lang.CharSequence r24, java.text.ParsePosition r25, net.time4j.engine.AttributeQuery r26) {
            /*
                Method dump skipped, instructions count: 500
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.hindu.HinduCalendar.MonthElement.parse(java.lang.CharSequence, java.text.ParsePosition, net.time4j.engine.AttributeQuery):net.time4j.calendar.hindu.HinduMonth");
        }
    }

    private static class DayOfMonthElement extends DisplayElement<HinduDay> implements AdjustableTextElement<HinduDay>, ElementRule<HinduCalendar, HinduDay> {
        static final DayOfMonthElement SINGLETON = new DayOfMonthElement();
        private static final long serialVersionUID = 992340906349614332L;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HinduCalendar hinduCalendar) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HinduCalendar hinduCalendar) {
            return null;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'd';
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

        private DayOfMonthElement() {
            super("DAY_OF_MONTH");
        }

        @Override // net.time4j.calendar.hindu.AdjustableTextElement
        public ChronoOperator<HinduCalendar> minimized() {
            return new ChronoOperator<HinduCalendar>() { // from class: net.time4j.calendar.hindu.HinduCalendar.DayOfMonthElement.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // net.time4j.engine.ChronoOperator
                public HinduCalendar apply(HinduCalendar hinduCalendar) {
                    DayOfMonthElement dayOfMonthElement = DayOfMonthElement.this;
                    return (HinduCalendar) hinduCalendar.with((ChronoElement<DayOfMonthElement>) dayOfMonthElement, (DayOfMonthElement) hinduCalendar.getMinimum(dayOfMonthElement));
                }
            };
        }

        @Override // net.time4j.calendar.hindu.AdjustableTextElement
        public ChronoOperator<HinduCalendar> maximized() {
            return new ChronoOperator<HinduCalendar>() { // from class: net.time4j.calendar.hindu.HinduCalendar.DayOfMonthElement.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // net.time4j.engine.ChronoOperator
                public HinduCalendar apply(HinduCalendar hinduCalendar) {
                    DayOfMonthElement dayOfMonthElement = DayOfMonthElement.this;
                    return (HinduCalendar) hinduCalendar.with((ChronoElement<DayOfMonthElement>) dayOfMonthElement, (DayOfMonthElement) hinduCalendar.getMaximum(dayOfMonthElement));
                }
            };
        }

        @Override // net.time4j.engine.ElementRule
        public HinduDay getValue(HinduCalendar hinduCalendar) {
            return hinduCalendar.dayOfMonth;
        }

        @Override // net.time4j.engine.ElementRule
        public HinduDay getMinimum(HinduCalendar hinduCalendar) {
            return hinduCalendar.withFirstDayOfMonth().dayOfMonth;
        }

        @Override // net.time4j.engine.ElementRule
        public HinduDay getMaximum(HinduCalendar hinduCalendar) {
            return hinduCalendar.variant.getCalendarSystem().create(hinduCalendar.withMidOfMonth(1).withFirstDayOfMonth().utcDays - 1).dayOfMonth;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HinduCalendar hinduCalendar, HinduDay hinduDay) {
            boolean z = false;
            if (hinduDay == null || (hinduDay.isLeap() && hinduCalendar.variant.isSolar())) {
                return false;
            }
            if (hinduCalendar.variant.isPurnimanta() && hinduCalendar.isChaitra() && hinduCalendar.withNewYear().month.equals(hinduCalendar.month)) {
                z = true;
            }
            return hinduCalendar.variant.getCalendarSystem().isValid(hinduCalendar.criticalYear(z, hinduDay), hinduCalendar.month, hinduDay);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HinduCalendar withValue2(HinduCalendar hinduCalendar, HinduDay hinduDay, boolean z) {
            if (hinduDay != null && (!hinduDay.isLeap() || !hinduCalendar.variant.isSolar())) {
                int criticalYear = hinduCalendar.criticalYear(hinduCalendar.variant.isPurnimanta() && hinduCalendar.isChaitra() && hinduCalendar.withNewYear().month.equals(hinduCalendar.month), hinduDay);
                HinduCS calendarSystem = hinduCalendar.variant.getCalendarSystem();
                if (calendarSystem.isValid(criticalYear, hinduCalendar.month, hinduDay)) {
                    return calendarSystem.create(criticalYear, hinduCalendar.month, hinduDay);
                }
            }
            throw new IllegalArgumentException("Invalid day of month: " + hinduDay);
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<HinduDay> getType() {
            return HinduDay.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public HinduDay getDefaultMinimum() {
            return HinduDay.valueOf(1);
        }

        @Override // net.time4j.engine.ChronoElement
        public HinduDay getDefaultMaximum() {
            return HinduDay.valueOf(32);
        }

        protected Object readResolve() throws ObjectStreamException {
            return SINGLETON;
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            char c;
            CharSequence charSequence;
            boolean z;
            HinduVariant variant = HinduCalendar.getVariant(chronoDisplay, attributeQuery);
            Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
            int intValue = ((Integer) attributeQuery.get(DualFormatElement.COUNT_OF_PATTERN_SYMBOLS, 0)).intValue();
            HinduDay hinduDay = (HinduDay) chronoDisplay.get(HinduCalendar.DAY_OF_MONTH);
            if (hinduDay.isLeap()) {
                Map<String, String> textForms = CalendarText.getInstance("generic", locale).getTextForms();
                z = ((Boolean) attributeQuery.get(HinduPrimitive.ADHIKA_IS_TRAILING, Boolean.valueOf("R".equals(textForms.get("leap-alignment"))))).booleanValue();
                c = ((Character) attributeQuery.get(HinduPrimitive.ADHIKA_INDICATOR, Character.valueOf(textForms.get("leap-indicator").charAt(0)))).charValue();
                charSequence = (String) CalendarText.getInstance("hindu", locale).getTextForms().get("adhika");
            } else {
                c = '*';
                charSequence = "";
                z = false;
            }
            if (hinduDay.isLeap() && !z) {
                if (intValue >= 2) {
                    appendable.append(charSequence);
                    appendable.append(' ');
                } else {
                    appendable.append(c);
                }
            }
            NumberSystem numberSystem = (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ARABIC);
            char charValue = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, Character.valueOf(numberSystem.getDigits().charAt(0)))).charValue();
            String numeral = DualFormatHelper.toNumeral(numberSystem, charValue, hinduDay.getValue());
            if (variant.isSolar() && numberSystem.isDecimal()) {
                for (int length = intValue - numeral.length(); length > 0; length--) {
                    appendable.append(charValue);
                }
            }
            appendable.append(numeral);
            if (z) {
                if (intValue >= 2) {
                    appendable.append(' ');
                    appendable.append(charSequence);
                } else {
                    appendable.append(c);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x00c1  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x00c6  */
        @Override // net.time4j.format.TextElement
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public net.time4j.calendar.hindu.HinduDay parse(java.lang.CharSequence r21, java.text.ParsePosition r22, net.time4j.engine.AttributeQuery r23) {
            /*
                Method dump skipped, instructions count: 356
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.hindu.HinduCalendar.DayOfMonthElement.parse(java.lang.CharSequence, java.text.ParsePosition, net.time4j.engine.AttributeQuery):net.time4j.calendar.hindu.HinduDay");
        }
    }

    private static class Merger implements ChronoMerger<HinduCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return 100;
        }

        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(HinduCalendar hinduCalendar, AttributeQuery attributeQuery) {
            return hinduCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HinduCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HinduCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return IndianCalendar.axis().getFormatPattern(displayStyle, locale);
        }

        /* JADX WARN: Type inference failed for: r10v1, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HinduCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID atLongitude;
            String str = (String) attributeQuery.get(Attributes.CALENDAR_VARIANT, "");
            if (str.isEmpty()) {
                return null;
            }
            HinduVariant from = HinduVariant.from(str);
            GeoLocation location = from.getLocation();
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                atLongitude = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                atLongitude = ZonalOffset.atLongitude(BigDecimal.valueOf(from.getLocation().getLongitude()));
            }
            return (HinduCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(HinduCalendar.ENGINE, str, atLongitude, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, StartOfDay.definedBy(SolarTime.ofLocation(location.getLatitude(), location.getLongitude(), location.getAltitude(), StdSolarCalculator.CC).sunrise()))).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HinduCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            String str = (String) attributeQuery.get(Attributes.CALENDAR_VARIANT, "");
            if (str.isEmpty()) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Hindu calendar variant.");
                return null;
            }
            try {
                HinduVariant from = HinduVariant.from(str);
                HinduCS calendarSystem = from.getCalendarSystem();
                HinduEra defaultEra = from.getDefaultEra();
                if (chronoEntity.contains(HinduCalendar.ERA)) {
                    defaultEra = (HinduEra) chronoEntity.get(HinduCalendar.ERA);
                }
                int i = chronoEntity.getInt(HinduCalendar.YEAR_OF_ERA);
                if (i == Integer.MIN_VALUE) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing Hindu year.");
                    return null;
                }
                int yearOfEra = HinduEra.KALI_YUGA.yearOfEra(defaultEra, i);
                if (!from.isUsingElapsedYears()) {
                    yearOfEra--;
                }
                if (chronoEntity.contains(HinduCalendar.MONTH_OF_YEAR) && chronoEntity.contains(HinduCalendar.DAY_OF_MONTH)) {
                    HinduMonth hinduMonth = (HinduMonth) chronoEntity.get(HinduCalendar.MONTH_OF_YEAR);
                    HinduDay hinduDay = (HinduDay) chronoEntity.get(HinduCalendar.DAY_OF_MONTH);
                    if (calendarSystem.isValid(yearOfEra, hinduMonth, hinduDay)) {
                        return calendarSystem.create(yearOfEra, hinduMonth, hinduDay);
                    }
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Hindu date.");
                } else {
                    int i2 = chronoEntity.getInt(HinduCalendar.DAY_OF_YEAR);
                    if (i2 != Integer.MIN_VALUE) {
                        if (i2 >= 1) {
                            long j = (calendarSystem.create(calendarSystem.create(yearOfEra, HinduMonth.of(IndianMonth.AGRAHAYANA), HinduDay.valueOf(1)).utcDays).withNewYear().utcDays + i2) - 1;
                            HinduCalendar create = calendarSystem.create(j);
                            if (calendarSystem.getMinimumSinceUTC() <= j && calendarSystem.getMaximumSinceUTC() >= j && (z || create.kyYear == yearOfEra)) {
                                return create;
                            }
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Hindu date.");
                    }
                }
                return null;
            } catch (IllegalArgumentException unused) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Invalid Hindu calendar variant.");
                return null;
            }
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.definedBy(SolarTime.ofLocation(HinduVariant.UJJAIN.getLatitude(), HinduVariant.UJJAIN.getLongitude()).sunrise());
        }
    }
}
