package net.time4j;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.base.TimeSource;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.IntElementRule;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.Temporal;
import net.time4j.engine.ValidationElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType(CalendarText.ISO_CALENDAR_TYPE)
/* loaded from: classes3.dex */
public final class AnnualDate extends ChronoEntity<AnnualDate> implements Comparable<AnnualDate>, Temporal<AnnualDate>, LocalizedPatternSupport, Serializable {

    @FormattableElement(format = "d")
    public static final ChronoElement<Integer> DAY_OF_MONTH;
    private static final Chronology<AnnualDate> ENGINE;
    public static final ChronoElement<Integer> MONTH_AS_NUMBER;

    @FormattableElement(alt = "L", format = "M")
    public static final ChronoElement<Month> MONTH_OF_YEAR;
    private static final long serialVersionUID = 7510648008819092983L;
    private final int dayOfMonth;
    private final int month;

    /* JADX INFO: Access modifiers changed from: private */
    public static int getMaxDay(int i) {
        if (i != 2) {
            return (i == 4 || i == 6 || i == 9 || i == 11) ? 30 : 31;
        }
        return 29;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.engine.ChronoEntity
    public AnnualDate getContext() {
        return this;
    }

    static {
        NavigableElement<Month> navigableElement = PlainDate.MONTH_OF_YEAR;
        MONTH_OF_YEAR = navigableElement;
        ProportionalElement<Integer, PlainDate> proportionalElement = PlainDate.MONTH_AS_NUMBER;
        MONTH_AS_NUMBER = proportionalElement;
        ProportionalElement<Integer, PlainDate> proportionalElement2 = PlainDate.DAY_OF_MONTH;
        DAY_OF_MONTH = proportionalElement2;
        ENGINE = Chronology.Builder.setUp(AnnualDate.class, new Merger()).appendElement(proportionalElement2, new IntegerElementRule(true)).appendElement(navigableElement, new MonthElementRule()).appendElement(proportionalElement, new IntegerElementRule(false)).build();
    }

    private AnnualDate(int i, int i2) {
        this.month = i;
        this.dayOfMonth = i2;
    }

    public static AnnualDate of(Month month, int i) {
        return of(month.getValue(), i);
    }

    public static AnnualDate of(int i, int i2) {
        check(i, i2);
        return new AnnualDate(i, i2);
    }

    public static AnnualDate from(GregorianDate gregorianDate) {
        PlainDate from = PlainDate.from(gregorianDate);
        return new AnnualDate(from.getMonth(), from.getDayOfMonth());
    }

    public static AnnualDate nowInSystemTime() {
        return (AnnualDate) SystemClock.inLocalView().now(ENGINE);
    }

    public Month getMonth() {
        return Month.valueOf(this.month);
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isAfter(AnnualDate annualDate) {
        return compareTo(annualDate) > 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isBefore(AnnualDate annualDate) {
        return compareTo(annualDate) < 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isSimultaneous(AnnualDate annualDate) {
        return compareTo(annualDate) == 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(AnnualDate annualDate) {
        int i = this.month;
        int i2 = annualDate.month;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int i3 = this.dayOfMonth;
        int i4 = annualDate.dayOfMonth;
        if (i3 < i4) {
            return -1;
        }
        return i3 > i4 ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnnualDate)) {
            return false;
        }
        AnnualDate annualDate = (AnnualDate) obj;
        return this.month == annualDate.month && this.dayOfMonth == annualDate.dayOfMonth;
    }

    public int hashCode() {
        return (this.month << 16) + this.dayOfMonth;
    }

    public String toString() {
        return toString(this.month, this.dayOfMonth);
    }

    public static AnnualDate parseXML(String str) throws ParseException {
        if (str.length() == 7 && str.charAt(0) == '-' && str.charAt(1) == '-' && str.charAt(4) == '-') {
            return new AnnualDate((toDigit(str, 2) * 10) + toDigit(str, 3), (toDigit(str, 5) * 10) + toDigit(str, 6));
        }
        throw new ParseException("Not compatible to standard XML-format: " + str, str.length());
    }

    public PlainDate atYear(int i) {
        return PlainDate.of(i, this.month, this.dayOfMonth);
    }

    public boolean isValidDate(int i) {
        return i >= -999999999 && i <= 999999999 && !(this.month == 2 && this.dayOfMonth == 29 && !GregorianMath.isLeapYear(i));
    }

    public ChronoOperator<PlainDate> asNextExactEvent() {
        return new ChronoOperator<PlainDate>() { // from class: net.time4j.AnnualDate.1
            @Override // net.time4j.engine.ChronoOperator
            public PlainDate apply(PlainDate plainDate) {
                int year = plainDate.getYear();
                int value = AnnualDate.this.getMonth().getValue();
                int dayOfMonth = AnnualDate.this.getDayOfMonth();
                if (value < plainDate.getMonth() || (value == plainDate.getMonth() && dayOfMonth <= plainDate.getDayOfMonth())) {
                    year++;
                }
                if (value == 2 && dayOfMonth == 29) {
                    while (!GregorianMath.isLeapYear(year)) {
                        year++;
                    }
                }
                return PlainDate.of(year, value, dayOfMonth);
            }
        };
    }

    public ChronoOperator<PlainDate> asNextRoundedEvent() {
        return new ChronoOperator<PlainDate>() { // from class: net.time4j.AnnualDate.2
            @Override // net.time4j.engine.ChronoOperator
            public PlainDate apply(PlainDate plainDate) {
                int year = plainDate.getYear();
                int value = AnnualDate.this.getMonth().getValue();
                int dayOfMonth = AnnualDate.this.getDayOfMonth();
                if (value < plainDate.getMonth() || (value == plainDate.getMonth() && dayOfMonth <= plainDate.getDayOfMonth())) {
                    year++;
                }
                if (value == 2 && dayOfMonth == 29 && !GregorianMath.isLeapYear(year)) {
                    value = 3;
                    dayOfMonth = 1;
                }
                return PlainDate.of(year, value, dayOfMonth);
            }
        };
    }

    public static Chronology<AnnualDate> chronology() {
        return ENGINE;
    }

    @Override // net.time4j.engine.ChronoEntity
    protected Chronology<AnnualDate> getChronology() {
        return ENGINE;
    }

    private static void check(int i, int i2) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Month not in range 1-12: " + i);
        }
        if (i2 < 1 || i2 > getMaxDay(i)) {
            throw new IllegalArgumentException("Out of bounds: " + toString(i, i2));
        }
    }

    private static int toDigit(String str, int i) throws ParseException {
        char charAt = str.charAt(i);
        if (charAt >= '0' && charAt <= '9') {
            return charAt - '0';
        }
        throw new ParseException("Digit expected: " + str, i);
    }

    private static String toString(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        sb.append('-');
        if (i2 < 10) {
            sb.append('0');
        }
        sb.append(i2);
        return sb.toString();
    }

    private Object readResolve() throws InvalidObjectException {
        try {
            check(this.month, this.dayOfMonth);
            return this;
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    private static class Merger implements ChronoMerger<AnnualDate> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(AnnualDate annualDate, AttributeQuery attributeQuery) {
            return annualDate;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ AnnualDate createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ AnnualDate createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        /* JADX WARN: Type inference failed for: r3v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public AnnualDate createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            Timezone ofSystem;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                ofSystem = Timezone.of((TZID) attributeQuery.get(Attributes.TIMEZONE_ID));
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                ofSystem = Timezone.ofSystem();
            }
            PlainDate date = Moment.from(timeSource.currentTime()).toZonalTimestamp(ofSystem.getID()).toDate();
            return AnnualDate.of(date.getMonth(), date.getDayOfMonth());
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public AnnualDate createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i = chronoEntity.getInt(AnnualDate.DAY_OF_MONTH);
            if (i != Integer.MIN_VALUE) {
                int i2 = chronoEntity.getInt(PlainDate.MONTH_AS_NUMBER);
                if (i2 == Integer.MIN_VALUE && chronoEntity.contains(AnnualDate.MONTH_OF_YEAR)) {
                    i2 = ((Month) chronoEntity.get(AnnualDate.MONTH_OF_YEAR)).getValue();
                }
                if (i2 != Integer.MIN_VALUE) {
                    if (i < 1 || i > AnnualDate.getMaxDay(i2)) {
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("Day-of-month out of bounds: " + i));
                    } else {
                        if (i2 >= 1 && i2 <= 12) {
                            return new AnnualDate(i2, i);
                        }
                        chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("Month out of bounds: " + i2));
                    }
                }
            }
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            Map<String, String> textForms = CalendarText.getIsoInstance(locale).getTextForms();
            int styleValue = displayStyle.getStyleValue();
            String formatPattern = getFormatPattern(textForms, styleValue != 0 ? styleValue != 1 ? styleValue != 2 ? styleValue != 3 ? null : "F_Md" : "F_MMd" : "F_MMMd" : "F_MMMMd");
            return formatPattern == null ? "MM-dd" : formatPattern;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear();
        }

        private static String getFormatPattern(Map<String, String> map, String str) {
            if (map.containsKey(str)) {
                return map.get(str);
            }
            if ("F_MMMMd".equals(str)) {
                return getFormatPattern(map, "F_MMMd");
            }
            if ("F_MMMd".equals(str)) {
                return getFormatPattern(map, "F_MMd");
            }
            if ("F_MMd".equals(str)) {
                return getFormatPattern(map, "F_Md");
            }
            return null;
        }
    }

    private static class MonthElementRule implements ElementRule<AnnualDate, Month> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(AnnualDate annualDate, Month month) {
            return month != null;
        }

        private MonthElementRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public Month getValue(AnnualDate annualDate) {
            return annualDate.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public Month getMinimum(AnnualDate annualDate) {
            return Month.JANUARY;
        }

        @Override // net.time4j.engine.ElementRule
        public Month getMaximum(AnnualDate annualDate) {
            return Month.DECEMBER;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public AnnualDate withValue2(AnnualDate annualDate, Month month, boolean z) {
            if (month == null) {
                throw new IllegalArgumentException("Missing new value.");
            }
            int value = month.getValue();
            return new AnnualDate(value, Math.min(annualDate.dayOfMonth, AnnualDate.getMaxDay(value)));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(AnnualDate annualDate) {
            return AnnualDate.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(AnnualDate annualDate) {
            return AnnualDate.DAY_OF_MONTH;
        }
    }

    private static class IntegerElementRule implements IntElementRule<AnnualDate> {
        private final boolean daywise;

        IntegerElementRule(boolean z) {
            this.daywise = z;
        }

        @Override // net.time4j.engine.IntElementRule
        public int getInt(AnnualDate annualDate) {
            return this.daywise ? annualDate.dayOfMonth : annualDate.month;
        }

        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(AnnualDate annualDate, int i) {
            if (i < 1) {
                return false;
            }
            return this.daywise ? i <= AnnualDate.getMaxDay(annualDate.month) : i <= 12;
        }

        @Override // net.time4j.engine.IntElementRule
        public AnnualDate withValue(AnnualDate annualDate, int i, boolean z) {
            return this.daywise ? AnnualDate.of(annualDate.month, i) : AnnualDate.of(i, Math.min(annualDate.dayOfMonth, AnnualDate.getMaxDay(i)));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(AnnualDate annualDate) {
            return Integer.valueOf(getInt(annualDate));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(AnnualDate annualDate) {
            return 1;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(AnnualDate annualDate) {
            if (this.daywise) {
                return Integer.valueOf(AnnualDate.getMaxDay(annualDate.month));
            }
            return 12;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(AnnualDate annualDate, Integer num) {
            if (num == null) {
                return false;
            }
            return isValid(annualDate, num.intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public AnnualDate withValue2(AnnualDate annualDate, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing new value.");
            }
            return withValue(annualDate, num.intValue(), z);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(AnnualDate annualDate) {
            if (this.daywise) {
                return null;
            }
            return AnnualDate.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(AnnualDate annualDate) {
            if (this.daywise) {
                return null;
            }
            return AnnualDate.DAY_OF_MONTH;
        }
    }
}
