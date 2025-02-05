package net.time4j;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.time4j.DayPeriod;
import net.time4j.base.MathUtils;
import net.time4j.base.ResourceLoader;
import net.time4j.base.TimeSource;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BridgeChronology;
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
import net.time4j.engine.FormattableElement;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.Temporal;
import net.time4j.engine.TimeAxis;
import net.time4j.engine.TimePoint;
import net.time4j.engine.UnitRule;
import net.time4j.engine.ValidationElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.DisplayMode;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.TemporalFormatter;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.ClassUtils;

@CalendarType(CalendarText.ISO_CALENDAR_TYPE)
/* loaded from: classes3.dex */
public final class PlainTime extends TimePoint<IsoTimeUnit, PlainTime> implements WallTime, Temporal<PlainTime>, LocalizedPatternSupport {

    @FormattableElement(format = "a")
    public static final ZonalElement<Meridiem> AM_PM_OF_DAY;

    @FormattableElement(format = "h")
    public static final AdjustableElement<Integer, PlainTime> CLOCK_HOUR_OF_AMPM;

    @FormattableElement(format = "k")
    public static final AdjustableElement<Integer, PlainTime> CLOCK_HOUR_OF_DAY;
    public static final WallTimeElement COMPONENT;
    private static final BigDecimal DECIMAL_23_9;
    private static final BigDecimal DECIMAL_24_0;
    private static final BigDecimal DECIMAL_3600;
    private static final BigDecimal DECIMAL_59_9;
    private static final BigDecimal DECIMAL_60;
    public static final ZonalElement<BigDecimal> DECIMAL_HOUR;
    public static final ZonalElement<BigDecimal> DECIMAL_MINUTE;
    private static final BigDecimal DECIMAL_MRD;
    public static final ZonalElement<BigDecimal> DECIMAL_SECOND;

    @FormattableElement(format = "K")
    public static final ProportionalElement<Integer, PlainTime> DIGITAL_HOUR_OF_AMPM;

    @FormattableElement(format = "H")
    public static final ProportionalElement<Integer, PlainTime> DIGITAL_HOUR_OF_DAY;
    private static final Map<String, Object> ELEMENTS;
    private static final TimeAxis<IsoTimeUnit, PlainTime> ENGINE;
    private static final PlainTime[] HOURS;
    public static final ProportionalElement<Integer, PlainTime> HOUR_FROM_0_TO_24;
    private static final ElementRule<PlainTime, BigDecimal> H_DECIMAL_RULE;
    static final char ISO_DECIMAL_SEPARATOR;
    private static final int KILO = 1000;
    static final PlainTime MAX;
    public static final ProportionalElement<Long, PlainTime> MICRO_OF_DAY;
    public static final ProportionalElement<Integer, PlainTime> MICRO_OF_SECOND;

    @FormattableElement(format = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS)
    public static final ProportionalElement<Integer, PlainTime> MILLI_OF_DAY;
    public static final ProportionalElement<Integer, PlainTime> MILLI_OF_SECOND;
    static final PlainTime MIN;
    public static final ProportionalElement<Integer, PlainTime> MINUTE_OF_DAY;

    @FormattableElement(format = "m")
    public static final ProportionalElement<Integer, PlainTime> MINUTE_OF_HOUR;
    private static final int MIO = 1000000;
    private static final int MRD = 1000000000;
    private static final ElementRule<PlainTime, BigDecimal> M_DECIMAL_RULE;
    public static final ProportionalElement<Long, PlainTime> NANO_OF_DAY;

    @FormattableElement(format = ExifInterface.LATITUDE_SOUTH)
    public static final ProportionalElement<Integer, PlainTime> NANO_OF_SECOND;
    public static final ChronoElement<ClockUnit> PRECISION;
    public static final ProportionalElement<Integer, PlainTime> SECOND_OF_DAY;

    @FormattableElement(format = "s")
    public static final ProportionalElement<Integer, PlainTime> SECOND_OF_MINUTE;
    private static final ElementRule<PlainTime, BigDecimal> S_DECIMAL_RULE;
    static final ChronoElement<PlainTime> WALL_TIME;
    private static final long serialVersionUID = 2780881537313863339L;
    private final transient byte hour;
    private final transient byte minute;
    private final transient int nano;
    private final transient byte second;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public PlainTime getContext() {
        return this;
    }

    static {
        ISO_DECIMAL_SEPARATOR = Boolean.getBoolean("net.time4j.format.iso.decimal.dot") ? ClassUtils.PACKAGE_SEPARATOR_CHAR : ',';
        DECIMAL_60 = new BigDecimal(60);
        DECIMAL_3600 = new BigDecimal(3600);
        DECIMAL_MRD = new BigDecimal(1000000000);
        DECIMAL_24_0 = new BigDecimal("24");
        DECIMAL_23_9 = new BigDecimal("23.999999999999999");
        DECIMAL_59_9 = new BigDecimal("59.999999999999999");
        HOURS = new PlainTime[25];
        for (int i = 0; i <= 24; i++) {
            HOURS[i] = new PlainTime(i, 0, 0, 0, false);
        }
        PlainTime[] plainTimeArr = HOURS;
        PlainTime plainTime = plainTimeArr[0];
        MIN = plainTime;
        PlainTime plainTime2 = plainTimeArr[24];
        MAX = plainTime2;
        TimeElement timeElement = TimeElement.INSTANCE;
        WALL_TIME = timeElement;
        COMPONENT = TimeElement.INSTANCE;
        AmPmElement amPmElement = AmPmElement.AM_PM_OF_DAY;
        AM_PM_OF_DAY = amPmElement;
        IntegerTimeElement createClockElement = IntegerTimeElement.createClockElement("CLOCK_HOUR_OF_AMPM", false);
        CLOCK_HOUR_OF_AMPM = createClockElement;
        IntegerTimeElement createClockElement2 = IntegerTimeElement.createClockElement("CLOCK_HOUR_OF_DAY", true);
        CLOCK_HOUR_OF_DAY = createClockElement2;
        IntegerTimeElement createTimeElement = IntegerTimeElement.createTimeElement("DIGITAL_HOUR_OF_AMPM", 3, 0, 11, 'K');
        DIGITAL_HOUR_OF_AMPM = createTimeElement;
        IntegerTimeElement createTimeElement2 = IntegerTimeElement.createTimeElement("DIGITAL_HOUR_OF_DAY", 4, 0, 23, 'H');
        DIGITAL_HOUR_OF_DAY = createTimeElement2;
        IntegerTimeElement createTimeElement3 = IntegerTimeElement.createTimeElement("HOUR_FROM_0_TO_24", 5, 0, 23, 'H');
        HOUR_FROM_0_TO_24 = createTimeElement3;
        IntegerTimeElement createTimeElement4 = IntegerTimeElement.createTimeElement("MINUTE_OF_HOUR", 6, 0, 59, 'm');
        MINUTE_OF_HOUR = createTimeElement4;
        IntegerTimeElement createTimeElement5 = IntegerTimeElement.createTimeElement("MINUTE_OF_DAY", 7, 0, 1439, (char) 0);
        MINUTE_OF_DAY = createTimeElement5;
        IntegerTimeElement createTimeElement6 = IntegerTimeElement.createTimeElement("SECOND_OF_MINUTE", 8, 0, 59, 's');
        SECOND_OF_MINUTE = createTimeElement6;
        IntegerTimeElement createTimeElement7 = IntegerTimeElement.createTimeElement("SECOND_OF_DAY", 9, 0, 86399, (char) 0);
        SECOND_OF_DAY = createTimeElement7;
        IntegerTimeElement createTimeElement8 = IntegerTimeElement.createTimeElement("MILLI_OF_SECOND", 10, 0, 999, (char) 0);
        MILLI_OF_SECOND = createTimeElement8;
        IntegerTimeElement createTimeElement9 = IntegerTimeElement.createTimeElement("MICRO_OF_SECOND", 11, 0, 999999, (char) 0);
        MICRO_OF_SECOND = createTimeElement9;
        IntegerTimeElement createTimeElement10 = IntegerTimeElement.createTimeElement("NANO_OF_SECOND", 12, 0, 999999999, 'S');
        NANO_OF_SECOND = createTimeElement10;
        IntegerTimeElement createTimeElement11 = IntegerTimeElement.createTimeElement("MILLI_OF_DAY", 13, 0, 86399999, 'A');
        MILLI_OF_DAY = createTimeElement11;
        LongElement create = LongElement.create("MICRO_OF_DAY", 0L, 86399999999L);
        MICRO_OF_DAY = create;
        LongElement create2 = LongElement.create("NANO_OF_DAY", 0L, 86399999999999L);
        NANO_OF_DAY = create2;
        DecimalTimeElement decimalTimeElement = new DecimalTimeElement("DECIMAL_HOUR", DECIMAL_23_9);
        DECIMAL_HOUR = decimalTimeElement;
        BigDecimal bigDecimal = DECIMAL_59_9;
        DecimalTimeElement decimalTimeElement2 = new DecimalTimeElement("DECIMAL_MINUTE", bigDecimal);
        DECIMAL_MINUTE = decimalTimeElement2;
        DecimalTimeElement decimalTimeElement3 = new DecimalTimeElement("DECIMAL_SECOND", bigDecimal);
        DECIMAL_SECOND = decimalTimeElement3;
        ChronoElement<ClockUnit> chronoElement = PrecisionElement.CLOCK_PRECISION;
        PRECISION = chronoElement;
        HashMap hashMap = new HashMap();
        fill(hashMap, timeElement);
        fill(hashMap, amPmElement);
        fill(hashMap, createClockElement);
        fill(hashMap, createClockElement2);
        fill(hashMap, createTimeElement);
        fill(hashMap, createTimeElement2);
        fill(hashMap, createTimeElement3);
        fill(hashMap, createTimeElement4);
        fill(hashMap, createTimeElement5);
        fill(hashMap, createTimeElement6);
        fill(hashMap, createTimeElement7);
        fill(hashMap, createTimeElement8);
        fill(hashMap, createTimeElement9);
        fill(hashMap, createTimeElement10);
        fill(hashMap, createTimeElement11);
        fill(hashMap, create);
        fill(hashMap, create2);
        fill(hashMap, decimalTimeElement);
        fill(hashMap, decimalTimeElement2);
        fill(hashMap, decimalTimeElement3);
        ELEMENTS = Collections.unmodifiableMap(hashMap);
        BigDecimalElementRule bigDecimalElementRule = new BigDecimalElementRule(decimalTimeElement, DECIMAL_24_0);
        H_DECIMAL_RULE = bigDecimalElementRule;
        BigDecimalElementRule bigDecimalElementRule2 = new BigDecimalElementRule(decimalTimeElement2, bigDecimal);
        M_DECIMAL_RULE = bigDecimalElementRule2;
        BigDecimalElementRule bigDecimalElementRule3 = new BigDecimalElementRule(decimalTimeElement3, bigDecimal);
        S_DECIMAL_RULE = bigDecimalElementRule3;
        TimeAxis.Builder up = TimeAxis.Builder.setUp(IsoTimeUnit.class, PlainTime.class, new Merger(null), plainTime, plainTime2);
        AnonymousClass1 anonymousClass1 = null;
        TimeAxis.Builder appendElement = up.appendElement((ChronoElement) timeElement, (ElementRule) new TimeRule(anonymousClass1)).appendElement((ChronoElement) amPmElement, (ElementRule) new MeridiemRule(anonymousClass1)).appendElement(createClockElement, new IntegerElementRule(createClockElement, 1, 12), ClockUnit.HOURS).appendElement(createClockElement2, new IntegerElementRule(createClockElement2, 1, 24), ClockUnit.HOURS).appendElement(createTimeElement, new IntegerElementRule(createTimeElement, 0, 11), ClockUnit.HOURS).appendElement(createTimeElement2, new IntegerElementRule(createTimeElement2, 0, 23), ClockUnit.HOURS).appendElement(createTimeElement3, new IntegerElementRule(createTimeElement3, 0, 24), ClockUnit.HOURS).appendElement(createTimeElement4, new IntegerElementRule(createTimeElement4, 0, 59), ClockUnit.MINUTES).appendElement(createTimeElement5, new IntegerElementRule(createTimeElement5, 0, 1440), ClockUnit.MINUTES).appendElement(createTimeElement6, new IntegerElementRule(createTimeElement6, 0, 59), ClockUnit.SECONDS).appendElement(createTimeElement7, new IntegerElementRule(createTimeElement7, 0, 86400), ClockUnit.SECONDS).appendElement(createTimeElement8, new IntegerElementRule(createTimeElement8, 0, 999), ClockUnit.MILLIS).appendElement(createTimeElement9, new IntegerElementRule(createTimeElement9, 0, 999999), ClockUnit.MICROS).appendElement(createTimeElement10, new IntegerElementRule(createTimeElement10, 0, 999999999), ClockUnit.NANOS).appendElement(createTimeElement11, new IntegerElementRule(createTimeElement11, 0, 86400000), ClockUnit.MILLIS).appendElement(create, new LongElementRule(create, 0L, 86400000000L), ClockUnit.MICROS).appendElement(create2, new LongElementRule(create2, 0L, 86400000000000L), ClockUnit.NANOS).appendElement((ChronoElement) decimalTimeElement, (ElementRule) bigDecimalElementRule).appendElement((ChronoElement) decimalTimeElement2, (ElementRule) bigDecimalElementRule2).appendElement((ChronoElement) decimalTimeElement3, (ElementRule) bigDecimalElementRule3).appendElement((ChronoElement) chronoElement, (ElementRule) new PrecisionRule(null));
        registerExtensions(appendElement);
        registerUnits(appendElement);
        ENGINE = appendElement.build();
    }

    private PlainTime(int i, int i2, int i3, int i4, boolean z) {
        if (z) {
            checkHour(i);
            checkMinute(i2);
            checkSecond(i3);
            checkNano(i4);
            if (i == 24 && (i2 | i3 | i4) != 0) {
                throw new IllegalArgumentException("T24:00:00 exceeded.");
            }
        }
        this.hour = (byte) i;
        this.minute = (byte) i2;
        this.second = (byte) i3;
        this.nano = i4;
    }

    @Override // net.time4j.base.WallTime
    public int getHour() {
        return this.hour;
    }

    @Override // net.time4j.base.WallTime
    public int getMinute() {
        return this.minute;
    }

    @Override // net.time4j.base.WallTime
    public int getSecond() {
        return this.second;
    }

    @Override // net.time4j.base.WallTime
    public int getNanosecond() {
        return this.nano;
    }

    public static PlainTime midnightAtStartOfDay() {
        return MIN;
    }

    public static PlainTime midnightAtEndOfDay() {
        return MAX;
    }

    public static PlainTime of(int i) {
        checkHour(i);
        return HOURS[i];
    }

    public static PlainTime of(int i, int i2) {
        if (i2 == 0) {
            return of(i);
        }
        return new PlainTime(i, i2, 0, 0, true);
    }

    public static PlainTime of(int i, int i2, int i3) {
        if ((i2 | i3) == 0) {
            return of(i);
        }
        return new PlainTime(i, i2, i3, 0, true);
    }

    public static PlainTime of(int i, int i2, int i3, int i4) {
        return of(i, i2, i3, i4, true);
    }

    public static PlainTime of(BigDecimal bigDecimal) {
        return H_DECIMAL_RULE.withValue2(null, bigDecimal, false);
    }

    public static PlainTime nowInSystemTime() {
        return ZonalClock.ofSystem().now().toTime();
    }

    public static PlainTime from(WallTime wallTime) {
        if (wallTime instanceof PlainTime) {
            return (PlainTime) wallTime;
        }
        if (wallTime instanceof PlainTimestamp) {
            return ((PlainTimestamp) wallTime).getWallTime();
        }
        return of(wallTime.getHour(), wallTime.getMinute(), wallTime.getSecond(), wallTime.getNanosecond());
    }

    public DayCycles roll(long j, ClockUnit clockUnit) {
        return ClockUnitRule.addToWithOverflow(this, j, clockUnit);
    }

    public String print(TemporalFormatter<PlainTime> temporalFormatter) {
        return temporalFormatter.print(this);
    }

    public static PlainTime parse(String str, TemporalFormatter<PlainTime> temporalFormatter) {
        try {
            return temporalFormatter.parse(str);
        } catch (ParseException e) {
            throw new ChronoException(e.getMessage(), e);
        }
    }

    @Override // net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlainTime)) {
            return false;
        }
        PlainTime plainTime = (PlainTime) obj;
        return this.hour == plainTime.hour && this.minute == plainTime.minute && this.second == plainTime.second && this.nano == plainTime.nano;
    }

    @Override // net.time4j.engine.TimePoint
    public int hashCode() {
        return this.hour + (this.minute * 60) + (this.second * Ascii.DLE) + (this.nano * 37);
    }

    @Override // net.time4j.engine.Temporal
    public boolean isBefore(PlainTime plainTime) {
        return compareTo(plainTime) < 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isAfter(PlainTime plainTime) {
        return compareTo(plainTime) > 0;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isSimultaneous(PlainTime plainTime) {
        return compareTo(plainTime) == 0;
    }

    public boolean isMidnight() {
        return isFullHour() && this.hour % Ascii.CAN == 0;
    }

    @Override // net.time4j.engine.TimePoint
    public int compareTo(PlainTime plainTime) {
        int i = this.hour - plainTime.hour;
        if (i == 0 && (i = this.minute - plainTime.minute) == 0 && (i = this.second - plainTime.second) == 0) {
            i = this.nano - plainTime.nano;
        }
        if (i < 0) {
            return -1;
        }
        return i == 0 ? 0 : 1;
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder(19);
        sb.append('T');
        append2Digits(this.hour, sb);
        if ((this.minute | this.second | this.nano) != 0) {
            sb.append(':');
            append2Digits(this.minute, sb);
            if ((this.second | this.nano) != 0) {
                sb.append(':');
                append2Digits(this.second, sb);
                int i = this.nano;
                if (i != 0) {
                    printNanos(sb, i);
                }
            }
        }
        return sb.toString();
    }

    public static TimeAxis<IsoTimeUnit, PlainTime> axis() {
        return ENGINE;
    }

    public static <S> Chronology<S> axis(Converter<S, PlainTime> converter) {
        return new BridgeChronology(converter, ENGINE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<IsoTimeUnit, PlainTime> getChronology() {
        return ENGINE;
    }

    static void printNanos(StringBuilder sb, int i) {
        int i2;
        sb.append(ISO_DECIMAL_SEPARATOR);
        String num = Integer.toString(i);
        if (i % 1000000 == 0) {
            i2 = 3;
        } else {
            i2 = i % 1000 == 0 ? 6 : 9;
        }
        for (int length = num.length(); length < 9; length++) {
            sb.append('0');
        }
        int length2 = (i2 + num.length()) - 9;
        for (int i3 = 0; i3 < length2; i3++) {
            sb.append(num.charAt(i3));
        }
    }

    static PlainTime from(UnixTime unixTime, ZonalOffset zonalOffset) {
        long posixTime = unixTime.getPosixTime() + zonalOffset.getIntegralAmount();
        int nanosecond = unixTime.getNanosecond() + zonalOffset.getFractionalAmount();
        if (nanosecond < 0) {
            nanosecond += 1000000000;
            posixTime--;
        } else if (nanosecond >= 1000000000) {
            nanosecond -= 1000000000;
            posixTime++;
        }
        int floorModulo = MathUtils.floorModulo(posixTime, 86400);
        int i = floorModulo % 60;
        int i2 = floorModulo / 60;
        return of(i2 / 60, i2 % 60, i, nanosecond);
    }

    static Object lookupElement(String str) {
        return ELEMENTS.get(str);
    }

    boolean hasReducedRange(ChronoElement<?> chronoElement) {
        return (chronoElement == MILLI_OF_DAY && this.nano % 1000000 != 0) || (chronoElement == HOUR_FROM_0_TO_24 && !isFullHour()) || ((chronoElement == MINUTE_OF_DAY && !isFullMinute()) || ((chronoElement == SECOND_OF_DAY && this.nano != 0) || (chronoElement == MICRO_OF_DAY && this.nano % 1000 != 0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PlainTime of(int i, int i2, int i3, int i4, boolean z) {
        if ((i2 | i3 | i4) != 0) {
            return new PlainTime(i, i2, i3, i4, z);
        }
        if (z) {
            return of(i);
        }
        return HOURS[i];
    }

    private static void fill(Map<String, Object> map, ChronoElement<?> chronoElement) {
        map.put(chronoElement.name(), chronoElement);
    }

    private static void append2Digits(int i, StringBuilder sb) {
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
    }

    private static void checkHour(long j) {
        if (j < 0 || j > 24) {
            throw new IllegalArgumentException("HOUR_OF_DAY out of range: " + j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkMinute(long j) {
        if (j < 0 || j > 59) {
            throw new IllegalArgumentException("MINUTE_OF_HOUR out of range: " + j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkSecond(long j) {
        if (j < 0 || j > 59) {
            throw new IllegalArgumentException("SECOND_OF_MINUTE out of range: " + j);
        }
    }

    private static void checkNano(int i) {
        if (i < 0 || i >= 1000000000) {
            throw new IllegalArgumentException("NANO_OF_SECOND out of range: " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PlainTime createFromMillis(int i, int i2) {
        int i3 = ((i % 1000) * 1000000) + i2;
        int i4 = i / 1000;
        int i5 = i4 % 60;
        int i6 = i4 / 60;
        return of(i6 / 60, i6 % 60, i5, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PlainTime createFromMicros(long j, int i) {
        int i2 = (((int) (j % 1000000)) * 1000) + i;
        int i3 = (int) (j / 1000000);
        int i4 = i3 % 60;
        int i5 = i3 / 60;
        return of(i5 / 60, i5 % 60, i4, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PlainTime createFromNanos(long j) {
        int i = (int) (j % 1000000000);
        int i2 = (int) (j / 1000000000);
        int i3 = i2 % 60;
        int i4 = i2 / 60;
        return of(i4 / 60, i4 % 60, i3, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getNanoOfDay() {
        return this.nano + (this.second * 1000000000) + (this.minute * 60 * 1000000000) + (this.hour * 3600 * 1000000000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFullHour() {
        return ((this.minute | this.second) | this.nano) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFullMinute() {
        return (this.second | this.nano) == 0;
    }

    private static void registerExtensions(TimeAxis.Builder<IsoTimeUnit, PlainTime> builder) {
        for (ChronoExtension chronoExtension : ResourceLoader.getInstance().services(ChronoExtension.class)) {
            if (chronoExtension.accept(PlainTime.class)) {
                builder.appendExtension(chronoExtension);
            }
        }
        builder.appendExtension((ChronoExtension) new DayPeriod.Extension());
    }

    private static void registerUnits(TimeAxis.Builder<IsoTimeUnit, PlainTime> builder) {
        Set<? extends IsoTimeUnit> allOf = EnumSet.allOf(ClockUnit.class);
        for (ClockUnit clockUnit : ClockUnit.values()) {
            builder.appendUnit(clockUnit, new ClockUnitRule(clockUnit, null), clockUnit.getLength(), allOf);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long floorMod(long j, long j2) {
        long j3 = j >= 0 ? j / j2 : ((j + 1) / j2) - 1;
        Long.signum(j2);
        return j - (j2 * j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long floorDiv(long j, long j2) {
        if (j >= 0) {
            return j / j2;
        }
        return ((j + 1) / j2) - 1;
    }

    private Object writeReplace() {
        return new SPX(this, 2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class ClockUnitRule implements UnitRule<PlainTime> {
        private final ClockUnit unit;

        /* synthetic */ ClockUnitRule(ClockUnit clockUnit, AnonymousClass1 anonymousClass1) {
            this(clockUnit);
        }

        private ClockUnitRule(ClockUnit clockUnit) {
            this.unit = clockUnit;
        }

        @Override // net.time4j.engine.UnitRule
        public PlainTime addTo(PlainTime plainTime, long j) {
            return j == 0 ? plainTime : (PlainTime) doAdd(PlainTime.class, this.unit, plainTime, j);
        }

        @Override // net.time4j.engine.UnitRule
        public long between(PlainTime plainTime, PlainTime plainTime2) {
            long j;
            long nanoOfDay = plainTime2.getNanoOfDay() - plainTime.getNanoOfDay();
            switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[this.unit.ordinal()]) {
                case 1:
                    j = 3600000000000L;
                    break;
                case 2:
                    j = 60000000000L;
                    break;
                case 3:
                    j = 1000000000;
                    break;
                case 4:
                    j = 1000000;
                    break;
                case 5:
                    j = 1000;
                    break;
                case 6:
                    j = 1;
                    break;
                default:
                    throw new UnsupportedOperationException(this.unit.name());
            }
            return nanoOfDay / j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static DayCycles addToWithOverflow(PlainTime plainTime, long j, ClockUnit clockUnit) {
            if (j == 0 && plainTime.hour < 24) {
                return new DayCycles(0L, plainTime);
            }
            return (DayCycles) doAdd(DayCycles.class, clockUnit, plainTime, j);
        }

        private static <R> R doAdd(Class<R> cls, ClockUnit clockUnit, PlainTime plainTime, long j) {
            long safeAdd;
            PlainTime of;
            int i = plainTime.minute;
            int i2 = plainTime.second;
            int i3 = plainTime.nano;
            switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[clockUnit.ordinal()]) {
                case 1:
                    safeAdd = MathUtils.safeAdd(plainTime.hour, j);
                    break;
                case 2:
                    long safeAdd2 = MathUtils.safeAdd(plainTime.minute, j);
                    safeAdd = MathUtils.safeAdd(plainTime.hour, MathUtils.floorDivide(safeAdd2, 60));
                    i = MathUtils.floorModulo(safeAdd2, 60);
                    break;
                case 3:
                    long safeAdd3 = MathUtils.safeAdd(plainTime.second, j);
                    long safeAdd4 = MathUtils.safeAdd(plainTime.minute, MathUtils.floorDivide(safeAdd3, 60));
                    safeAdd = MathUtils.safeAdd(plainTime.hour, MathUtils.floorDivide(safeAdd4, 60));
                    int floorModulo = MathUtils.floorModulo(safeAdd4, 60);
                    i2 = MathUtils.floorModulo(safeAdd3, 60);
                    i = floorModulo;
                    break;
                case 4:
                    return (R) doAdd(cls, ClockUnit.NANOS, plainTime, MathUtils.safeMultiply(j, 1000000L));
                case 5:
                    return (R) doAdd(cls, ClockUnit.NANOS, plainTime, MathUtils.safeMultiply(j, 1000L));
                case 6:
                    long safeAdd5 = MathUtils.safeAdd(plainTime.nano, j);
                    long safeAdd6 = MathUtils.safeAdd(plainTime.second, MathUtils.floorDivide(safeAdd5, 1000000000));
                    long safeAdd7 = MathUtils.safeAdd(plainTime.minute, MathUtils.floorDivide(safeAdd6, 60));
                    safeAdd = MathUtils.safeAdd(plainTime.hour, MathUtils.floorDivide(safeAdd7, 60));
                    int floorModulo2 = MathUtils.floorModulo(safeAdd7, 60);
                    int floorModulo3 = MathUtils.floorModulo(safeAdd6, 60);
                    int floorModulo4 = MathUtils.floorModulo(safeAdd5, 1000000000);
                    i = floorModulo2;
                    i2 = floorModulo3;
                    i3 = floorModulo4;
                    break;
                default:
                    throw new UnsupportedOperationException(clockUnit.name());
            }
            int floorModulo5 = MathUtils.floorModulo(safeAdd, 24);
            if ((floorModulo5 | i | i2 | i3) == 0) {
                of = (j <= 0 || cls != PlainTime.class) ? PlainTime.MIN : PlainTime.MAX;
            } else {
                of = PlainTime.of(floorModulo5, i, i2, i3);
            }
            if (cls == PlainTime.class) {
                return cls.cast(of);
            }
            return cls.cast(new DayCycles(MathUtils.floorDivide(safeAdd, 24), of));
        }
    }

    /* renamed from: net.time4j.PlainTime$1, reason: invalid class name */
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

    private static class TimeRule implements ElementRule<PlainTime, PlainTime> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getValue(PlainTime plainTime) {
            return plainTime;
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, PlainTime plainTime2) {
            return plainTime2 != null;
        }

        private TimeRule() {
        }

        /* synthetic */ TimeRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime withValue2(PlainTime plainTime, PlainTime plainTime2, boolean z) {
            if (plainTime2 != null) {
                return plainTime2;
            }
            throw new IllegalArgumentException("Missing time value.");
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getMinimum(PlainTime plainTime) {
            return PlainTime.MIN;
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getMaximum(PlainTime plainTime) {
            return PlainTime.MAX;
        }
    }

    private static class PrecisionRule implements ElementRule<PlainTime, ClockUnit> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, ClockUnit clockUnit) {
            return clockUnit != null;
        }

        private PrecisionRule() {
        }

        /* synthetic */ PrecisionRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public ClockUnit getValue(PlainTime plainTime) {
            if (plainTime.nano != 0) {
                if (plainTime.nano % 1000000 != 0) {
                    if (plainTime.nano % 1000 == 0) {
                        return ClockUnit.MICROS;
                    }
                    return ClockUnit.NANOS;
                }
                return ClockUnit.MILLIS;
            }
            if (plainTime.second == 0) {
                if (plainTime.minute != 0) {
                    return ClockUnit.MINUTES;
                }
                return ClockUnit.HOURS;
            }
            return ClockUnit.SECONDS;
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime withValue2(PlainTime plainTime, ClockUnit clockUnit, boolean z) {
            if (clockUnit == null) {
                throw new IllegalArgumentException("Missing precision value.");
            }
            if (clockUnit.ordinal() >= getValue(plainTime).ordinal()) {
                return plainTime;
            }
            switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[clockUnit.ordinal()]) {
                case 1:
                    return PlainTime.of(plainTime.hour);
                case 2:
                    return PlainTime.of(plainTime.hour, plainTime.minute);
                case 3:
                    return PlainTime.of(plainTime.hour, plainTime.minute, plainTime.second);
                case 4:
                    return PlainTime.of(plainTime.hour, plainTime.minute, plainTime.second, (plainTime.nano / 1000000) * 1000000);
                case 5:
                    return PlainTime.of(plainTime.hour, plainTime.minute, plainTime.second, (plainTime.nano / 1000) * 1000);
                case 6:
                    return plainTime;
                default:
                    throw new UnsupportedOperationException(clockUnit.name());
            }
        }

        @Override // net.time4j.engine.ElementRule
        public ClockUnit getMinimum(PlainTime plainTime) {
            return ClockUnit.HOURS;
        }

        @Override // net.time4j.engine.ElementRule
        public ClockUnit getMaximum(PlainTime plainTime) {
            return ClockUnit.NANOS;
        }
    }

    private static class MeridiemRule implements ElementRule<PlainTime, Meridiem> {
        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, Meridiem meridiem) {
            return meridiem != null;
        }

        private MeridiemRule() {
        }

        /* synthetic */ MeridiemRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getValue(PlainTime plainTime) {
            return Meridiem.ofHour(plainTime.hour);
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime withValue2(PlainTime plainTime, Meridiem meridiem, boolean z) {
            int i = plainTime.hour == 24 ? 0 : plainTime.hour;
            if (meridiem == null) {
                throw new IllegalArgumentException("Missing am/pm-value.");
            }
            if (meridiem == Meridiem.AM) {
                if (i >= 12) {
                    i -= 12;
                }
            } else if (meridiem == Meridiem.PM && i < 12) {
                i += 12;
            }
            return PlainTime.of(i, plainTime.minute, plainTime.second, plainTime.nano);
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getMinimum(PlainTime plainTime) {
            return Meridiem.AM;
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getMaximum(PlainTime plainTime) {
            return Meridiem.PM;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return PlainTime.DIGITAL_HOUR_OF_AMPM;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return PlainTime.DIGITAL_HOUR_OF_AMPM;
        }
    }

    private static class IntegerElementRule implements ElementRule<PlainTime, Integer> {
        private final ChronoElement<Integer> element;
        private final int index;
        private final int max;
        private final int min;

        IntegerElementRule(ChronoElement<Integer> chronoElement, int i, int i2) {
            this.element = chronoElement;
            if (chronoElement instanceof IntegerTimeElement) {
                this.index = ((IntegerTimeElement) chronoElement).getIndex();
            } else {
                this.index = -1;
            }
            this.min = i;
            this.max = i2;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(PlainTime plainTime) {
            int i;
            byte b;
            int i2 = 24;
            switch (this.index) {
                case 1:
                    i2 = plainTime.hour % Ascii.FF;
                    if (i2 == 0) {
                        i2 = 12;
                    }
                    return Integer.valueOf(i2);
                case 2:
                    int i3 = plainTime.hour % Ascii.CAN;
                    if (i3 != 0) {
                        i2 = i3;
                    }
                    return Integer.valueOf(i2);
                case 3:
                    i2 = plainTime.hour % Ascii.FF;
                    return Integer.valueOf(i2);
                case 4:
                    i2 = plainTime.hour % Ascii.CAN;
                    return Integer.valueOf(i2);
                case 5:
                    i2 = plainTime.hour;
                    return Integer.valueOf(i2);
                case 6:
                    i2 = plainTime.minute;
                    return Integer.valueOf(i2);
                case 7:
                    i = plainTime.hour * 60;
                    b = plainTime.minute;
                    i2 = i + b;
                    return Integer.valueOf(i2);
                case 8:
                    i2 = plainTime.second;
                    return Integer.valueOf(i2);
                case 9:
                    i = (plainTime.hour * Ascii.DLE) + (plainTime.minute * 60);
                    b = plainTime.second;
                    i2 = i + b;
                    return Integer.valueOf(i2);
                case 10:
                    i2 = plainTime.nano / 1000000;
                    return Integer.valueOf(i2);
                case 11:
                    i2 = plainTime.nano / 1000;
                    return Integer.valueOf(i2);
                case 12:
                    i2 = plainTime.nano;
                    return Integer.valueOf(i2);
                case 13:
                    i2 = (int) (plainTime.getNanoOfDay() / 1000000);
                    return Integer.valueOf(i2);
                default:
                    throw new UnsupportedOperationException(this.element.name());
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0073, code lost:
        
            if (isAM(r7) != false) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:
        
            r8 = r8 + 12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0088, code lost:
        
            if (isAM(r7) != false) goto L22;
         */
        @Override // net.time4j.engine.ElementRule
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public net.time4j.PlainTime withValue2(net.time4j.PlainTime r7, java.lang.Integer r8, boolean r9) {
            /*
                r6 = this;
                if (r8 == 0) goto La7
                if (r9 == 0) goto Ld
                int r8 = r8.intValue()
                net.time4j.PlainTime r7 = r6.withValueInLenientMode(r7, r8)
                return r7
            Ld:
                boolean r9 = r6.isValid(r7, r8)
                if (r9 == 0) goto L90
                byte r9 = net.time4j.PlainTime.access$700(r7)
                byte r0 = net.time4j.PlainTime.access$800(r7)
                byte r1 = net.time4j.PlainTime.access$900(r7)
                int r2 = net.time4j.PlainTime.access$1000(r7)
                int r8 = r8.intValue()
                int r3 = r6.index
                r4 = 0
                r5 = 1000000(0xf4240, float:1.401298E-39)
                switch(r3) {
                    case 1: goto L7f;
                    case 2: goto L79;
                    case 3: goto L6f;
                    case 4: goto L6d;
                    case 5: goto L6d;
                    case 6: goto L6b;
                    case 7: goto L66;
                    case 8: goto L64;
                    case 9: goto L5b;
                    case 10: goto L51;
                    case 11: goto L48;
                    case 12: goto L46;
                    case 13: goto L3c;
                    default: goto L30;
                }
            L30:
                java.lang.UnsupportedOperationException r7 = new java.lang.UnsupportedOperationException
                net.time4j.engine.ChronoElement<java.lang.Integer> r8 = r6.element
                java.lang.String r8 = r8.name()
                r7.<init>(r8)
                throw r7
            L3c:
                int r7 = net.time4j.PlainTime.access$1000(r7)
                int r7 = r7 % r5
                net.time4j.PlainTime r7 = net.time4j.PlainTime.access$1100(r8, r7)
                return r7
            L46:
                r2 = r8
                goto L8b
            L48:
                int r8 = r8 * 1000
                int r7 = net.time4j.PlainTime.access$1000(r7)
                int r7 = r7 % 1000
                goto L58
            L51:
                int r8 = r8 * r5
                int r7 = net.time4j.PlainTime.access$1000(r7)
                int r7 = r7 % r5
            L58:
                int r2 = r8 + r7
                goto L8b
            L5b:
                int r9 = r8 / 3600
                int r8 = r8 % 3600
                int r0 = r8 / 60
                int r1 = r8 % 60
                goto L8b
            L64:
                r1 = r8
                goto L8b
            L66:
                int r9 = r8 / 60
                int r0 = r8 % 60
                goto L8b
            L6b:
                r0 = r8
                goto L8b
            L6d:
                r9 = r8
                goto L8b
            L6f:
                boolean r7 = isAM(r7)
                if (r7 == 0) goto L76
                goto L6d
            L76:
                int r8 = r8 + 12
                goto L6d
            L79:
                r7 = 24
                if (r8 != r7) goto L6d
                r9 = 0
                goto L8b
            L7f:
                r9 = 12
                if (r8 != r9) goto L84
                r8 = 0
            L84:
                boolean r7 = isAM(r7)
                if (r7 == 0) goto L76
                goto L6d
            L8b:
                net.time4j.PlainTime r7 = net.time4j.PlainTime.of(r9, r0, r1, r2)
                return r7
            L90:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r0 = "Value out of range: "
                r9.append(r0)
                r9.append(r8)
                java.lang.String r8 = r9.toString()
                r7.<init>(r8)
                throw r7
            La7:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                java.lang.String r8 = "Missing element value."
                r7.<init>(r8)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.PlainTime.IntegerElementRule.withValue(net.time4j.PlainTime, java.lang.Integer, boolean):net.time4j.PlainTime");
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, Integer num) {
            int intValue;
            int i;
            if (num == null || (intValue = num.intValue()) < this.min || intValue > (i = this.max)) {
                return false;
            }
            if (intValue == i) {
                int i2 = this.index;
                if (i2 == 5) {
                    return plainTime.isFullHour();
                }
                if (i2 == 7) {
                    return plainTime.isFullMinute();
                }
                if (i2 == 9) {
                    return plainTime.nano == 0;
                }
                if (i2 == 13) {
                    return plainTime.nano % 1000000 == 0;
                }
            }
            if (plainTime.hour == 24) {
                switch (this.index) {
                    case 6:
                    case 8:
                    case 10:
                    case 11:
                    case 12:
                        return intValue == 0;
                }
            }
            return true;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(PlainTime plainTime) {
            return Integer.valueOf(this.min);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(PlainTime plainTime) {
            if (plainTime.hour == 24) {
                switch (this.index) {
                    case 6:
                    case 8:
                    case 10:
                    case 11:
                    case 12:
                        return 0;
                }
            }
            if (plainTime.hasReducedRange(this.element)) {
                return Integer.valueOf(this.max - 1);
            }
            return Integer.valueOf(this.max);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return getChild(plainTime);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return getChild(plainTime);
        }

        private ChronoElement<?> getChild(PlainTime plainTime) {
            switch (this.index) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return PlainTime.MINUTE_OF_HOUR;
                case 6:
                case 7:
                    return PlainTime.SECOND_OF_MINUTE;
                case 8:
                case 9:
                    return PlainTime.NANO_OF_SECOND;
                default:
                    return null;
            }
        }

        private PlainTime withValueInLenientMode(PlainTime plainTime, int i) {
            if (this.element == PlainTime.HOUR_FROM_0_TO_24 || this.element == PlainTime.DIGITAL_HOUR_OF_DAY || this.element == PlainTime.DIGITAL_HOUR_OF_AMPM) {
                return plainTime.plus(MathUtils.safeSubtract(i, ((Integer) plainTime.get(this.element)).intValue()), ClockUnit.HOURS);
            }
            if (this.element == PlainTime.MINUTE_OF_HOUR) {
                return plainTime.plus(MathUtils.safeSubtract(i, (int) plainTime.minute), ClockUnit.MINUTES);
            }
            if (this.element == PlainTime.SECOND_OF_MINUTE) {
                return plainTime.plus(MathUtils.safeSubtract(i, (int) plainTime.second), ClockUnit.SECONDS);
            }
            if (this.element == PlainTime.MILLI_OF_SECOND) {
                return plainTime.plus(MathUtils.safeSubtract(i, ((Integer) plainTime.get(PlainTime.MILLI_OF_SECOND)).intValue()), ClockUnit.MILLIS);
            }
            if (this.element == PlainTime.MICRO_OF_SECOND) {
                return plainTime.plus(MathUtils.safeSubtract(i, ((Integer) plainTime.get(PlainTime.MICRO_OF_SECOND)).intValue()), ClockUnit.MICROS);
            }
            if (this.element == PlainTime.NANO_OF_SECOND) {
                return plainTime.plus(MathUtils.safeSubtract(i, plainTime.nano), ClockUnit.NANOS);
            }
            if (this.element == PlainTime.MILLI_OF_DAY) {
                int floorModulo = MathUtils.floorModulo(i, 86400000);
                int i2 = plainTime.nano % 1000000;
                if (floorModulo == 0 && i2 == 0) {
                    return i > 0 ? PlainTime.MAX : PlainTime.MIN;
                }
                return PlainTime.createFromMillis(floorModulo, i2);
            }
            if (this.element == PlainTime.MINUTE_OF_DAY) {
                int floorModulo2 = MathUtils.floorModulo(i, 1440);
                if (floorModulo2 == 0 && plainTime.isFullMinute()) {
                    return i > 0 ? PlainTime.MAX : PlainTime.MIN;
                }
                return withValue(plainTime, Integer.valueOf(floorModulo2), false);
            }
            if (this.element == PlainTime.SECOND_OF_DAY) {
                int floorModulo3 = MathUtils.floorModulo(i, 86400);
                if (floorModulo3 == 0 && plainTime.nano == 0) {
                    return i > 0 ? PlainTime.MAX : PlainTime.MIN;
                }
                return withValue(plainTime, Integer.valueOf(floorModulo3), false);
            }
            throw new UnsupportedOperationException(this.element.name());
        }

        private static boolean isAM(PlainTime plainTime) {
            return plainTime.hour < 12 || plainTime.hour == 24;
        }
    }

    private static class LongElementRule implements ElementRule<PlainTime, Long> {
        private final ChronoElement<Long> element;
        private final long max;
        private final long min;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return null;
        }

        LongElementRule(ChronoElement<Long> chronoElement, long j, long j2) {
            this.element = chronoElement;
            this.min = j;
            this.max = j2;
        }

        @Override // net.time4j.engine.ElementRule
        public Long getValue(PlainTime plainTime) {
            return Long.valueOf(this.element == PlainTime.MICRO_OF_DAY ? plainTime.getNanoOfDay() / 1000 : plainTime.getNanoOfDay());
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime withValue2(PlainTime plainTime, Long l, boolean z) {
            if (l == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            if (z) {
                return withValueInLenientMode(plainTime, l.longValue());
            }
            if (!isValid(plainTime, l)) {
                throw new IllegalArgumentException("Value out of range: " + l);
            }
            long longValue = l.longValue();
            return this.element == PlainTime.MICRO_OF_DAY ? PlainTime.createFromMicros(longValue, plainTime.nano % 1000) : PlainTime.createFromNanos(longValue);
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, Long l) {
            if (l == null) {
                return false;
            }
            return (this.element == PlainTime.MICRO_OF_DAY && l.longValue() == this.max) ? plainTime.nano % 1000 == 0 : this.min <= l.longValue() && l.longValue() <= this.max;
        }

        @Override // net.time4j.engine.ElementRule
        public Long getMinimum(PlainTime plainTime) {
            return Long.valueOf(this.min);
        }

        @Override // net.time4j.engine.ElementRule
        public Long getMaximum(PlainTime plainTime) {
            if (this.element == PlainTime.MICRO_OF_DAY && plainTime.nano % 1000 != 0) {
                return Long.valueOf(this.max - 1);
            }
            return Long.valueOf(this.max);
        }

        private PlainTime withValueInLenientMode(PlainTime plainTime, long j) {
            if (this.element == PlainTime.MICRO_OF_DAY) {
                long floorMod = PlainTime.floorMod(j, 86400000000L);
                int i = plainTime.nano % 1000;
                if (floorMod != 0 || i != 0 || j <= 0) {
                    return PlainTime.createFromMicros(floorMod, i);
                }
                return PlainTime.MAX;
            }
            long floorMod2 = PlainTime.floorMod(j, 86400000000000L);
            if (floorMod2 != 0 || j <= 0) {
                return PlainTime.createFromNanos(floorMod2);
            }
            return PlainTime.MAX;
        }
    }

    private static class BigDecimalElementRule implements ElementRule<PlainTime, BigDecimal> {
        private final ChronoElement<BigDecimal> element;
        private final BigDecimal max;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(PlainTime plainTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(PlainTime plainTime) {
            return null;
        }

        BigDecimalElementRule(ChronoElement<BigDecimal> chronoElement, BigDecimal bigDecimal) {
            this.element = chronoElement;
            this.max = bigDecimal;
        }

        @Override // net.time4j.engine.ElementRule
        public BigDecimal getValue(PlainTime plainTime) {
            BigDecimal add;
            if (this.element == PlainTime.DECIMAL_HOUR) {
                if (!plainTime.equals(PlainTime.MIN)) {
                    if (plainTime.hour == 24) {
                        return PlainTime.DECIMAL_24_0;
                    }
                    add = BigDecimal.valueOf(plainTime.hour).add(div(BigDecimal.valueOf(plainTime.minute), PlainTime.DECIMAL_60)).add(div(BigDecimal.valueOf(plainTime.second), PlainTime.DECIMAL_3600)).add(div(BigDecimal.valueOf(plainTime.nano), PlainTime.DECIMAL_3600.multiply(PlainTime.DECIMAL_MRD)));
                } else {
                    return BigDecimal.ZERO;
                }
            } else if (this.element == PlainTime.DECIMAL_MINUTE) {
                if (plainTime.isFullHour()) {
                    return BigDecimal.ZERO;
                }
                add = BigDecimal.valueOf(plainTime.minute).add(div(BigDecimal.valueOf(plainTime.second), PlainTime.DECIMAL_60)).add(div(BigDecimal.valueOf(plainTime.nano), PlainTime.DECIMAL_60.multiply(PlainTime.DECIMAL_MRD)));
            } else if (this.element == PlainTime.DECIMAL_SECOND) {
                if (!plainTime.isFullMinute()) {
                    add = BigDecimal.valueOf(plainTime.second).add(div(BigDecimal.valueOf(plainTime.nano), PlainTime.DECIMAL_MRD));
                } else {
                    return BigDecimal.ZERO;
                }
            } else {
                throw new UnsupportedOperationException(this.element.name());
            }
            return add.setScale(15, RoundingMode.FLOOR).stripTrailingZeros();
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime withValue2(PlainTime plainTime, BigDecimal bigDecimal, boolean z) {
            int i;
            int i2;
            long j;
            int i3;
            int i4;
            int i5;
            if (bigDecimal == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            if (this.element == PlainTime.DECIMAL_HOUR) {
                BigDecimal scale = bigDecimal.setScale(0, RoundingMode.FLOOR);
                BigDecimal multiply = bigDecimal.subtract(scale).multiply(PlainTime.DECIMAL_60);
                BigDecimal scale2 = multiply.setScale(0, RoundingMode.FLOOR);
                BigDecimal multiply2 = multiply.subtract(scale2).multiply(PlainTime.DECIMAL_60);
                BigDecimal scale3 = multiply2.setScale(0, RoundingMode.FLOOR);
                j = scale.longValueExact();
                i = scale2.intValue();
                i3 = scale3.intValue();
                i4 = toNano(multiply2.subtract(scale3));
            } else if (this.element == PlainTime.DECIMAL_MINUTE) {
                BigDecimal scale4 = bigDecimal.setScale(0, RoundingMode.FLOOR);
                BigDecimal multiply3 = bigDecimal.subtract(scale4).multiply(PlainTime.DECIMAL_60);
                BigDecimal scale5 = multiply3.setScale(0, RoundingMode.FLOOR);
                int intValue = scale5.intValue();
                int nano = toNano(multiply3.subtract(scale5));
                long longValueExact = scale4.longValueExact();
                long j2 = plainTime.hour;
                if (!z) {
                    PlainTime.checkMinute(longValueExact);
                    i = (int) longValueExact;
                } else {
                    j2 += MathUtils.floorDivide(longValueExact, 60);
                    i = MathUtils.floorModulo(longValueExact, 60);
                }
                i4 = nano;
                i3 = intValue;
                j = j2;
            } else if (this.element == PlainTime.DECIMAL_SECOND) {
                BigDecimal scale6 = bigDecimal.setScale(0, RoundingMode.FLOOR);
                int nano2 = toNano(bigDecimal.subtract(scale6));
                long longValueExact2 = scale6.longValueExact();
                long j3 = plainTime.hour;
                i = plainTime.minute;
                if (!z) {
                    PlainTime.checkSecond(longValueExact2);
                    i2 = (int) longValueExact2;
                } else {
                    i2 = MathUtils.floorModulo(longValueExact2, 60);
                    long floorDivide = i + MathUtils.floorDivide(longValueExact2, 60);
                    j3 += MathUtils.floorDivide(floorDivide, 60);
                    i = MathUtils.floorModulo(floorDivide, 60);
                }
                j = j3;
                i3 = i2;
                i4 = nano2;
            } else {
                throw new UnsupportedOperationException(this.element.name());
            }
            if (z) {
                i5 = MathUtils.floorModulo(j, 24);
                if (j > 0 && (i5 | i | i3 | i4) == 0) {
                    return PlainTime.MAX;
                }
            } else {
                if (j < 0 || j > 24) {
                    throw new IllegalArgumentException("Value out of range: " + bigDecimal);
                }
                i5 = (int) j;
            }
            return PlainTime.of(i5, i, i3, i4);
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(PlainTime plainTime, BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                return false;
            }
            return (plainTime.hour == 24 && (this.element == PlainTime.DECIMAL_MINUTE || this.element == PlainTime.DECIMAL_SECOND)) ? BigDecimal.ZERO.compareTo(bigDecimal) == 0 : BigDecimal.ZERO.compareTo(bigDecimal) <= 0 && this.max.compareTo(bigDecimal) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        public BigDecimal getMinimum(PlainTime plainTime) {
            return BigDecimal.ZERO;
        }

        @Override // net.time4j.engine.ElementRule
        public BigDecimal getMaximum(PlainTime plainTime) {
            if (plainTime.hour == 24 && (this.element == PlainTime.DECIMAL_MINUTE || this.element == PlainTime.DECIMAL_SECOND)) {
                return BigDecimal.ZERO;
            }
            return this.max;
        }

        private static BigDecimal div(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
            return bigDecimal.divide(bigDecimal2, 16, RoundingMode.FLOOR);
        }

        private static int toNano(BigDecimal bigDecimal) {
            return Math.min(999999999, bigDecimal.movePointRight(9).setScale(0, RoundingMode.HALF_UP).intValue());
        }
    }

    private static class Merger implements ChronoMerger<PlainTime> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(PlainTime plainTime, AttributeQuery attributeQuery) {
            return plainTime;
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
        public /* bridge */ /* synthetic */ PlainTime createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ PlainTime createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return CalendarText.patternForTime(DisplayMode.ofStyle(displayStyle.getStyleValue()), locale);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PlainTime createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
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
            return PlainTime.from(currentTime, timezone.getOffset(currentTime));
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public PlainTime createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            if (chronoEntity instanceof UnixTime) {
                return PlainTimestamp.axis().createFrom(chronoEntity, attributeQuery, z, z2).getWallTime();
            }
            if (chronoEntity.contains(PlainTime.WALL_TIME)) {
                return (PlainTime) chronoEntity.get(PlainTime.WALL_TIME);
            }
            if (chronoEntity.contains(PlainTime.DECIMAL_HOUR)) {
                return PlainTime.of((BigDecimal) chronoEntity.get(PlainTime.DECIMAL_HOUR));
            }
            int i = chronoEntity.getInt(PlainTime.HOUR_FROM_0_TO_24);
            if (i == Integer.MIN_VALUE) {
                i = readHour(chronoEntity);
                if (i == Integer.MIN_VALUE) {
                    return readSpecialCases(chronoEntity);
                }
                if (i == -1 || i == -2) {
                    if (!z) {
                        flagValidationError(chronoEntity, "Clock hour cannot be zero.");
                        return null;
                    }
                    i = i == -1 ? 0 : 12;
                } else if (i == 24 && !z) {
                    flagValidationError(chronoEntity, "Time 24:00 not allowed, use lax mode or element HOUR_FROM_0_TO_24 instead.");
                    return null;
                }
            }
            if (chronoEntity.contains(PlainTime.DECIMAL_MINUTE)) {
                return (PlainTime) PlainTime.M_DECIMAL_RULE.withValue2(PlainTime.of(i), chronoEntity.get(PlainTime.DECIMAL_MINUTE), false);
            }
            int i2 = chronoEntity.getInt(PlainTime.MINUTE_OF_HOUR);
            if (i2 == Integer.MIN_VALUE) {
                i2 = 0;
            }
            if (chronoEntity.contains(PlainTime.DECIMAL_SECOND)) {
                return (PlainTime) PlainTime.S_DECIMAL_RULE.withValue2(PlainTime.of(i, i2), chronoEntity.get(PlainTime.DECIMAL_SECOND), false);
            }
            int i3 = chronoEntity.getInt(PlainTime.SECOND_OF_MINUTE);
            if (i3 == Integer.MIN_VALUE) {
                i3 = 0;
            }
            int i4 = chronoEntity.getInt(PlainTime.NANO_OF_SECOND);
            if (i4 == Integer.MIN_VALUE) {
                int i5 = chronoEntity.getInt(PlainTime.MICRO_OF_SECOND);
                if (i5 == Integer.MIN_VALUE) {
                    int i6 = chronoEntity.getInt(PlainTime.MILLI_OF_SECOND);
                    i4 = i6 == Integer.MIN_VALUE ? 0 : MathUtils.safeMultiply(i6, 1000000);
                } else {
                    i4 = MathUtils.safeMultiply(i5, 1000);
                }
            }
            if (z) {
                long safeAdd = MathUtils.safeAdd(MathUtils.safeMultiply(MathUtils.safeAdd(MathUtils.safeAdd(MathUtils.safeMultiply(i, 3600L), MathUtils.safeMultiply(i2, 60L)), i3), 1000000000L), i4);
                long floorMod = PlainTime.floorMod(safeAdd, 86400000000000L);
                long floorDiv = PlainTime.floorDiv(safeAdd, 86400000000000L);
                if (floorDiv != 0 && chronoEntity.isValid(LongElement.DAY_OVERFLOW, floorDiv)) {
                    chronoEntity.with(LongElement.DAY_OVERFLOW, floorDiv);
                }
                if (floorMod != 0 || floorDiv <= 0) {
                    return PlainTime.createFromNanos(floorMod);
                }
                return PlainTime.MAX;
            }
            if ((i >= 0 && i2 >= 0 && i3 >= 0 && i4 >= 0 && i == 24 && (i2 | i3 | i4) == 0) || (i < 24 && i2 <= 59 && i3 <= 59 && i4 <= 1000000000)) {
                return PlainTime.of(i, i2, i3, i4, false);
            }
            flagValidationError(chronoEntity, "Time component out of range.");
            return null;
        }

        private static int readHour(ChronoEntity<?> chronoEntity) {
            int i = chronoEntity.getInt(PlainTime.DIGITAL_HOUR_OF_DAY);
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            int i2 = chronoEntity.getInt(PlainTime.CLOCK_HOUR_OF_DAY);
            if (i2 == 0) {
                return -1;
            }
            if (i2 == 24) {
                return 0;
            }
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (chronoEntity.contains(PlainTime.AM_PM_OF_DAY)) {
                Meridiem meridiem = (Meridiem) chronoEntity.get(PlainTime.AM_PM_OF_DAY);
                int i3 = chronoEntity.getInt(PlainTime.CLOCK_HOUR_OF_AMPM);
                if (i3 != Integer.MIN_VALUE) {
                    if (i3 == 0) {
                        return meridiem == Meridiem.AM ? -1 : -2;
                    }
                    int i4 = i3 != 12 ? i3 : 0;
                    return meridiem == Meridiem.AM ? i4 : i4 + 12;
                }
                int i5 = chronoEntity.getInt(PlainTime.DIGITAL_HOUR_OF_AMPM);
                if (i5 != Integer.MIN_VALUE) {
                    return meridiem == Meridiem.AM ? i5 : i5 + 12;
                }
            }
            return Integer.MIN_VALUE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static PlainTime readSpecialCases(ChronoEntity<?> chronoEntity) {
            int intValue;
            int intValue2;
            if (chronoEntity.contains(PlainTime.NANO_OF_DAY)) {
                long longValue = ((Long) chronoEntity.get(PlainTime.NANO_OF_DAY)).longValue();
                if (longValue >= 0 && longValue <= 86400000000000L) {
                    return PlainTime.createFromNanos(longValue);
                }
                flagValidationError(chronoEntity, "NANO_OF_DAY out of range: " + longValue);
                return null;
            }
            if (chronoEntity.contains(PlainTime.MICRO_OF_DAY)) {
                return PlainTime.createFromMicros(((Long) chronoEntity.get(PlainTime.MICRO_OF_DAY)).longValue(), chronoEntity.contains(PlainTime.NANO_OF_SECOND) ? ((Integer) chronoEntity.get(PlainTime.NANO_OF_SECOND)).intValue() % 1000 : 0);
            }
            if (chronoEntity.contains(PlainTime.MILLI_OF_DAY)) {
                if (chronoEntity.contains(PlainTime.NANO_OF_SECOND)) {
                    int intValue3 = ((Integer) chronoEntity.get(PlainTime.NANO_OF_SECOND)).intValue();
                    if (intValue3 < 0 || intValue3 >= 1000000000) {
                        flagValidationError(chronoEntity, "NANO_OF_SECOND out of range: " + intValue3);
                        return null;
                    }
                    r2 = intValue3 % 1000000;
                } else if (chronoEntity.contains(PlainTime.MICRO_OF_SECOND)) {
                    int intValue4 = ((Integer) chronoEntity.get(PlainTime.MICRO_OF_SECOND)).intValue();
                    if (intValue4 < 0 || intValue4 >= 1000000) {
                        flagValidationError(chronoEntity, "MICRO_OF_SECOND out of range: " + intValue4);
                        return null;
                    }
                    r2 = intValue4 % 1000;
                }
                int intValue5 = ((Integer) chronoEntity.get(PlainTime.MILLI_OF_DAY)).intValue();
                if (intValue5 >= 0 && intValue5 <= 86400000) {
                    return PlainTime.createFromMillis(intValue5, r2);
                }
                flagValidationError(chronoEntity, "MILLI_OF_DAY out of range: " + intValue5);
                return null;
            }
            if (chronoEntity.contains(PlainTime.SECOND_OF_DAY)) {
                if (chronoEntity.contains(PlainTime.NANO_OF_SECOND)) {
                    intValue2 = ((Integer) chronoEntity.get(PlainTime.NANO_OF_SECOND)).intValue();
                } else if (chronoEntity.contains(PlainTime.MICRO_OF_SECOND)) {
                    intValue2 = ((Integer) chronoEntity.get(PlainTime.MICRO_OF_SECOND)).intValue() * 1000;
                } else {
                    intValue2 = chronoEntity.contains(PlainTime.MILLI_OF_SECOND) ? ((Integer) chronoEntity.get(PlainTime.MILLI_OF_SECOND)).intValue() * 1000000 : 0;
                }
                return (PlainTime) PlainTime.of(0, 0, 0, intValue2).with(PlainTime.SECOND_OF_DAY, (ProportionalElement<Integer, PlainTime>) chronoEntity.get(PlainTime.SECOND_OF_DAY));
            }
            if (!chronoEntity.contains(PlainTime.MINUTE_OF_DAY)) {
                return null;
            }
            if (chronoEntity.contains(PlainTime.NANO_OF_SECOND)) {
                intValue = ((Integer) chronoEntity.get(PlainTime.NANO_OF_SECOND)).intValue();
            } else if (chronoEntity.contains(PlainTime.MICRO_OF_SECOND)) {
                intValue = ((Integer) chronoEntity.get(PlainTime.MICRO_OF_SECOND)).intValue() * 1000;
            } else {
                intValue = chronoEntity.contains(PlainTime.MILLI_OF_SECOND) ? ((Integer) chronoEntity.get(PlainTime.MILLI_OF_SECOND)).intValue() * 1000000 : 0;
            }
            return (PlainTime) PlainTime.of(0, 0, chronoEntity.contains(PlainTime.SECOND_OF_MINUTE) ? ((Integer) chronoEntity.get(PlainTime.SECOND_OF_MINUTE)).intValue() : 0, intValue).with(PlainTime.MINUTE_OF_DAY, (ProportionalElement<Integer, PlainTime>) chronoEntity.get(PlainTime.MINUTE_OF_DAY));
        }

        private static void flagValidationError(ChronoEntity<?> chronoEntity, String str) {
            if (chronoEntity.isValid((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) str)) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) str);
            }
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
}
