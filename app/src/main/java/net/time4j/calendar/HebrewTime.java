package net.time4j.calendar;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.ZonalElement;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.astro.SolarTime;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Chronology;
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
import net.time4j.format.CalendarType;
import net.time4j.format.Leniency;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("hebrew")
/* loaded from: classes3.dex */
public final class HebrewTime extends TimePoint<Unit, HebrewTime> implements Temporal<HebrewTime> {
    public static final ChronoElement<ClockCycle> CLOCK_CYCLE;

    @FormattableElement(format = "h")
    public static final StdCalendarElement<Integer, HebrewTime> CLOCK_HOUR;

    @FormattableElement(format = "H")
    public static final StdCalendarElement<Integer, HebrewTime> DIGITAL_HOUR;
    private static final TimeAxis<Unit, HebrewTime> ENGINE;
    private static final int HOUR12_INDEX = 0;
    private static final int HOUR23_INDEX = 1;
    private static final HebrewTime MAX;
    private static final HebrewTime MIN;
    private static final int PARTS_IN_HOUR = 1080;
    private static final int PART_INDEX = 2;

    @FormattableElement(dynamic = true, format = "P")
    public static final StdCalendarElement<Integer, HebrewTime> PART_OF_HOUR;
    private static final long serialVersionUID = -6206874394178665128L;
    private final transient int hour23;
    private final transient int part;

    public enum ClockCycle {
        NIGHT,
        DAY
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public HebrewTime getContext() {
        return this;
    }

    static {
        boolean z = false;
        StdEnumDateElement stdEnumDateElement = new StdEnumDateElement("CLOCK_CYCLE", HebrewTime.class, ClockCycle.class, (char) 0);
        CLOCK_CYCLE = stdEnumDateElement;
        boolean z2 = true;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("CLOCK_HOUR", HebrewTime.class, 1, 12, 'h', new UnitOperator(Unit.HOURS, z2), new UnitOperator(Unit.HOURS, z));
        CLOCK_HOUR = stdIntegerDateElement;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DIGITAL_HOUR", HebrewTime.class, 0, 23, 'H', new UnitOperator(Unit.HOURS, z2), new UnitOperator(Unit.HOURS, z));
        DIGITAL_HOUR = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("PART_OF_HOUR", HebrewTime.class, 0, 1079, 'P', new UnitOperator(Unit.HALAKIM, z2), new UnitOperator(Unit.HALAKIM, z));
        PART_OF_HOUR = stdIntegerDateElement3;
        HebrewTime hebrewTime = new HebrewTime(0, 0);
        MIN = hebrewTime;
        HebrewTime hebrewTime2 = new HebrewTime(23, 1079);
        MAX = hebrewTime2;
        TimeAxis.Builder appendElement = TimeAxis.Builder.setUp(Unit.class, HebrewTime.class, new Merger(), hebrewTime, hebrewTime2).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new CycleRule()).appendElement(stdIntegerDateElement, new IntegerElementRule(0), Unit.HOURS).appendElement(stdIntegerDateElement2, new IntegerElementRule(1), Unit.HOURS).appendElement(stdIntegerDateElement3, new IntegerElementRule(2), Unit.HALAKIM);
        registerUnits(appendElement);
        ENGINE = appendElement.build();
    }

    private HebrewTime(ClockCycle clockCycle, int i, int i2) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("CLOCK_HOUR out of range: " + i);
        }
        if (i2 < 0 || i2 >= PARTS_IN_HOUR) {
            throw new IllegalArgumentException("PART_OF_HOUR out of range: " + i2);
        }
        i = i == 12 ? 0 : i;
        this.hour23 = clockCycle.equals(ClockCycle.NIGHT) ? i : i + 12;
        this.part = i2;
    }

    private HebrewTime(int i, int i2) {
        this.hour23 = i;
        this.part = i2;
    }

    public static HebrewTime ofDigital(int i, int i2) {
        if (i < 0 || i > 23) {
            throw new IllegalArgumentException("DIGITAL_HOUR out of range: " + i);
        }
        if (i2 < 0 || i2 >= PARTS_IN_HOUR) {
            throw new IllegalArgumentException("PART_OF_HOUR out of range: " + i2);
        }
        return new HebrewTime(i, i2);
    }

    public static HebrewTime ofNight(int i, int i2) {
        return new HebrewTime(ClockCycle.NIGHT, i, i2);
    }

    public static HebrewTime ofDay(int i, int i2) {
        return new HebrewTime(ClockCycle.DAY, i, i2);
    }

    public static HebrewTime now(SolarTime solarTime) {
        return at(solarTime).apply(SystemClock.currentMoment());
    }

    public static HebrewTime nowInSystemTime() {
        return (HebrewTime) SystemClock.inLocalView().now(axis());
    }

    public static ChronoFunction<Moment, HebrewTime> at(final SolarTime solarTime) {
        return new ChronoFunction<Moment, HebrewTime>() { // from class: net.time4j.calendar.HebrewTime.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:13:0x008f A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:17:0x00e1  */
            @Override // net.time4j.engine.ChronoFunction
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public net.time4j.calendar.HebrewTime apply(net.time4j.Moment r12) {
                /*
                    r11 = this;
                    java.math.BigDecimal r0 = new java.math.BigDecimal
                    net.time4j.calendar.astro.SolarTime r1 = net.time4j.calendar.astro.SolarTime.this
                    double r1 = r1.getLongitude()
                    r0.<init>(r1)
                    net.time4j.tz.ZonalOffset r0 = net.time4j.tz.ZonalOffset.atLongitude(r0)
                    net.time4j.PlainTimestamp r0 = r12.toZonalTimestamp(r0)
                    net.time4j.PlainDate r1 = r0.toDate()
                    net.time4j.calendar.astro.SolarTime r2 = net.time4j.calendar.astro.SolarTime.this
                    net.time4j.engine.ChronoFunction r2 = r2.sunset()
                    java.lang.Object r1 = r1.get(r2)
                    net.time4j.Moment r1 = (net.time4j.Moment) r1
                    r2 = 0
                    if (r1 == 0) goto Le7
                    boolean r3 = r12.isBefore(r1)
                    r4 = 1
                    if (r3 == 0) goto L6a
                    net.time4j.PlainDate r3 = r0.toDate()
                    net.time4j.calendar.astro.SolarTime r6 = net.time4j.calendar.astro.SolarTime.this
                    net.time4j.engine.ChronoFunction r6 = r6.sunrise()
                    java.lang.Object r3 = r3.get(r6)
                    net.time4j.Moment r3 = (net.time4j.Moment) r3
                    if (r3 == 0) goto L8a
                    boolean r6 = r12.isBefore(r3)
                    if (r6 == 0) goto L64
                    net.time4j.PlainDate r0 = r0.toDate()
                    net.time4j.CalendarUnit r1 = net.time4j.CalendarUnit.DAYS
                    net.time4j.engine.TimePoint r0 = r0.minus(r4, r1)
                    net.time4j.PlainDate r0 = (net.time4j.PlainDate) r0
                    net.time4j.calendar.astro.SolarTime r1 = net.time4j.calendar.astro.SolarTime.this
                    net.time4j.engine.ChronoFunction r1 = r1.sunset()
                    java.lang.Object r0 = r0.get(r1)
                    r1 = r0
                    net.time4j.Moment r1 = (net.time4j.Moment) r1
                    if (r1 == 0) goto L8a
                    net.time4j.calendar.HebrewTime$ClockCycle r0 = net.time4j.calendar.HebrewTime.ClockCycle.NIGHT
                    goto L8d
                L64:
                    net.time4j.calendar.HebrewTime$ClockCycle r0 = net.time4j.calendar.HebrewTime.ClockCycle.DAY
                    r10 = r3
                    r3 = r1
                    r1 = r10
                    goto L8d
                L6a:
                    net.time4j.PlainDate r0 = r0.toDate()
                    net.time4j.CalendarUnit r3 = net.time4j.CalendarUnit.DAYS
                    net.time4j.engine.TimePoint r0 = r0.plus(r4, r3)
                    net.time4j.PlainDate r0 = (net.time4j.PlainDate) r0
                    net.time4j.calendar.astro.SolarTime r3 = net.time4j.calendar.astro.SolarTime.this
                    net.time4j.engine.ChronoFunction r3 = r3.sunrise()
                    java.lang.Object r0 = r0.get(r3)
                    net.time4j.Moment r0 = (net.time4j.Moment) r0
                    if (r0 == 0) goto L8a
                    net.time4j.calendar.HebrewTime$ClockCycle r3 = net.time4j.calendar.HebrewTime.ClockCycle.NIGHT
                    r10 = r3
                    r3 = r0
                    r0 = r10
                    goto L8d
                L8a:
                    r0 = r2
                    r1 = r0
                    r3 = r1
                L8d:
                    if (r0 == 0) goto Le7
                    if (r1 == 0) goto Le7
                    if (r3 == 0) goto Le7
                    java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.SECONDS
                    long r4 = r1.until(r3, r4)
                    r6 = 1000000000(0x3b9aca00, double:4.94065646E-315)
                    long r4 = r4 * r6
                    int r3 = r3.getNanosecond()
                    long r8 = (long) r3
                    long r4 = r4 + r8
                    int r3 = r1.getNanosecond()
                    long r8 = (long) r3
                    long r4 = r4 - r8
                    java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS
                    long r8 = r1.until(r12, r3)
                    long r8 = r8 * r6
                    int r12 = r12.getNanosecond()
                    long r6 = (long) r12
                    long r8 = r8 + r6
                    int r12 = r1.getNanosecond()
                    long r6 = (long) r12
                    long r8 = r8 - r6
                    r6 = 4668350449676451840(0x40c9500000000000, double:12960.0)
                    double r8 = (double) r8
                    double r8 = r8 * r6
                    double r3 = (double) r4
                    double r8 = r8 / r3
                    r3 = 4652464705678344192(0x4090e00000000000, double:1080.0)
                    double r3 = r8 / r3
                    double r3 = java.lang.Math.floor(r3)
                    int r12 = (int) r3
                    int r1 = r12 * 1080
                    double r3 = (double) r1
                    double r8 = r8 - r3
                    double r3 = java.lang.Math.floor(r8)
                    int r1 = (int) r3
                    net.time4j.calendar.HebrewTime r3 = new net.time4j.calendar.HebrewTime
                    if (r12 != 0) goto Le3
                    r12 = 12
                Le3:
                    r3.<init>(r0, r12, r1)
                    return r3
                Le7:
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.HebrewTime.AnonymousClass1.apply(net.time4j.Moment):net.time4j.calendar.HebrewTime");
            }
        };
    }

    public static ChronoFunction<Moment, HebrewTime> at(final TZID tzid) {
        return new ChronoFunction<Moment, HebrewTime>() { // from class: net.time4j.calendar.HebrewTime.2
            @Override // net.time4j.engine.ChronoFunction
            public HebrewTime apply(Moment moment) {
                PlainTime wallTime = moment.toZonalTimestamp(TZID.this).getWallTime();
                return new HebrewTime((wallTime.getHour() + 6) % 24, ((BigDecimal) wallTime.get(PlainTime.DECIMAL_HOUR)).subtract(new BigDecimal(wallTime.getHour())).multiply(new BigDecimal(HebrewTime.PARTS_IN_HOUR)).intValue());
            }
        };
    }

    public boolean isNight() {
        return this.hour23 < 12;
    }

    public boolean isDay() {
        return this.hour23 >= 12;
    }

    public int getClockHour() {
        int i = this.hour23;
        if (isDay()) {
            i -= 12;
        }
        if (i == 0) {
            return 12;
        }
        return i;
    }

    public int getDigitalHour() {
        return this.hour23;
    }

    public int getPart() {
        return this.part;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isAfter(HebrewTime hebrewTime) {
        return getTimeOfDay() > hebrewTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.Temporal
    public boolean isBefore(HebrewTime hebrewTime) {
        return getTimeOfDay() < hebrewTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.Temporal
    public boolean isSimultaneous(HebrewTime hebrewTime) {
        return getTimeOfDay() == hebrewTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public int compareTo(HebrewTime hebrewTime) {
        return getTimeOfDay() - hebrewTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof HebrewTime) && getTimeOfDay() == ((HebrewTime) obj).getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public int hashCode() {
        return getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.hour23);
        sb.append('H');
        sb.append(this.part);
        sb.append('P');
        return sb.toString();
    }

    public Moment on(HebrewCalendar hebrewCalendar, SolarTime solarTime) {
        Moment apply;
        HebrewTime hebrewTime;
        Moment moment;
        PlainDate plainDate = (PlainDate) hebrewCalendar.transform(PlainDate.class);
        if (isNight()) {
            moment = solarTime.sunset().apply(plainDate.minus(CalendarDays.ONE));
            apply = solarTime.sunrise().apply(plainDate);
            hebrewTime = this;
        } else {
            HebrewTime minus = minus(12L, Unit.HOURS);
            Moment apply2 = solarTime.sunrise().apply(plainDate);
            apply = solarTime.sunset().apply(plainDate);
            hebrewTime = minus;
            moment = apply2;
        }
        if (moment == null || apply == null) {
            return null;
        }
        int until = (int) moment.until(apply, (Moment) TimeUnit.SECONDS);
        if (moment.getNanosecond() > apply.getNanosecond()) {
            until--;
        }
        return moment.plus((long) Math.floor((hebrewTime.getTimeOfDay() * until) / 12960.0d), (long) TimeUnit.SECONDS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Moment on(HebrewCalendar hebrewCalendar, Timezone timezone) {
        return hebrewCalendar.at((PlainTime) PlainTime.of(18).with(PlainTime.DECIMAL_HOUR, (ZonalElement<BigDecimal>) new BigDecimal(this.part).setScale(15, RoundingMode.UNNECESSARY).divide(new BigDecimal(PARTS_IN_HOUR), RoundingMode.FLOOR).add(new BigDecimal((this.hour23 + 18) % 24)))).in(timezone, StartOfDay.EVENING);
    }

    public static TimeAxis<Unit, HebrewTime> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, HebrewTime> getChronology() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTimeOfDay() {
        return this.part + (this.hour23 * PARTS_IN_HOUR);
    }

    private static void registerUnits(TimeAxis.Builder<Unit, HebrewTime> builder) {
        EnumSet allOf = EnumSet.allOf(Unit.class);
        for (Unit unit : Unit.values()) {
            builder.appendUnit(unit, new ClockUnitRule(unit), unit.getLength(), allOf);
        }
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        HOURS(3600.0d),
        HALAKIM(3.3333333333333335d);

        private final transient double length;

        @Override // net.time4j.engine.ChronoUnit
        public boolean isCalendrical() {
            return false;
        }

        Unit(double d) {
            this.length = d;
        }

        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return this.length;
        }

        public int between(HebrewTime hebrewTime, HebrewTime hebrewTime2) {
            return (int) hebrewTime.until(hebrewTime2, (HebrewTime) this);
        }
    }

    private static class ClockUnitRule implements UnitRule<HebrewTime> {
        private final Unit unit;

        private ClockUnitRule(Unit unit) {
            this.unit = unit;
        }

        @Override // net.time4j.engine.UnitRule
        public HebrewTime addTo(HebrewTime hebrewTime, long j) {
            int i;
            int i2;
            if (j == 0) {
                return hebrewTime;
            }
            int i3 = AnonymousClass3.$SwitchMap$net$time4j$calendar$HebrewTime$Unit[this.unit.ordinal()];
            if (i3 == 1) {
                int floorModulo = MathUtils.floorModulo(MathUtils.safeAdd(hebrewTime.hour23, j), 24);
                i = hebrewTime.part;
                i2 = floorModulo;
            } else if (i3 == 2) {
                long safeAdd = MathUtils.safeAdd(hebrewTime.part, j);
                i = MathUtils.floorModulo(safeAdd, HebrewTime.PARTS_IN_HOUR);
                i2 = MathUtils.floorModulo(MathUtils.safeAdd(hebrewTime.hour23, MathUtils.floorDivide(safeAdd, HebrewTime.PARTS_IN_HOUR)), 24);
            } else {
                throw new UnsupportedOperationException(this.unit.name());
            }
            return new HebrewTime(i2, i);
        }

        @Override // net.time4j.engine.UnitRule
        public long between(HebrewTime hebrewTime, HebrewTime hebrewTime2) {
            long timeOfDay = hebrewTime2.getTimeOfDay() - hebrewTime.getTimeOfDay();
            int i = AnonymousClass3.$SwitchMap$net$time4j$calendar$HebrewTime$Unit[this.unit.ordinal()];
            if (i == 1) {
                return timeOfDay / 1080;
            }
            if (i == 2) {
                return timeOfDay;
            }
            throw new UnsupportedOperationException(this.unit.name());
        }
    }

    /* renamed from: net.time4j.calendar.HebrewTime$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$HebrewTime$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$HebrewTime$Unit = iArr;
            try {
                iArr[Unit.HOURS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$HebrewTime$Unit[Unit.HALAKIM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static class UnitOperator implements ChronoOperator<HebrewTime> {
        private final boolean decrementing;
        private final Unit unit;

        private UnitOperator(Unit unit, boolean z) {
            this.unit = unit;
            this.decrementing = z;
        }

        @Override // net.time4j.engine.ChronoOperator
        public HebrewTime apply(HebrewTime hebrewTime) {
            return hebrewTime.plus(this.decrementing ? -1L : 1L, this.unit);
        }
    }

    private static class CycleRule implements ElementRule<HebrewTime, ClockCycle> {
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HebrewTime hebrewTime, ClockCycle clockCycle) {
            return clockCycle != null;
        }

        private CycleRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public ClockCycle getValue(HebrewTime hebrewTime) {
            return hebrewTime.hour23 < 12 ? ClockCycle.NIGHT : ClockCycle.DAY;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HebrewTime withValue2(HebrewTime hebrewTime, ClockCycle clockCycle, boolean z) {
            if (clockCycle == null) {
                throw new IllegalArgumentException("Missing Hebrew cycle.");
            }
            return new HebrewTime(clockCycle, hebrewTime.getClockHour(), hebrewTime.getPart());
        }

        @Override // net.time4j.engine.ElementRule
        public ClockCycle getMinimum(HebrewTime hebrewTime) {
            return ClockCycle.NIGHT;
        }

        @Override // net.time4j.engine.ElementRule
        public ClockCycle getMaximum(HebrewTime hebrewTime) {
            return ClockCycle.DAY;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HebrewTime hebrewTime) {
            return HebrewTime.CLOCK_HOUR;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HebrewTime hebrewTime) {
            return HebrewTime.CLOCK_HOUR;
        }
    }

    private static class IntegerElementRule implements ElementRule<HebrewTime, Integer> {
        private final int index;

        IntegerElementRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(HebrewTime hebrewTime) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(hebrewTime.getClockHour());
            }
            if (i == 1) {
                return Integer.valueOf(hebrewTime.hour23);
            }
            if (i == 2) {
                return Integer.valueOf(hebrewTime.part);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(HebrewTime hebrewTime) {
            int i = this.index;
            if (i == 0) {
                return 1;
            }
            if (i == 1 || i == 2) {
                return 0;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(HebrewTime hebrewTime) {
            int i = this.index;
            if (i == 0) {
                return 12;
            }
            if (i == 1) {
                return 23;
            }
            if (i == 2) {
                return 1079;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HebrewTime hebrewTime, Integer num) {
            return num != null && getMinimum(hebrewTime).compareTo(num) <= 0 && getMaximum(hebrewTime).compareTo(num) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HebrewTime withValue2(HebrewTime hebrewTime, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            int intValue = num.intValue();
            int i = this.index;
            if (i == 0) {
                if (z) {
                    return hebrewTime.plus(MathUtils.safeSubtract(intValue, hebrewTime.getClockHour()), Unit.HOURS);
                }
                return hebrewTime.isDay() ? HebrewTime.ofDay(intValue, hebrewTime.part) : HebrewTime.ofNight(intValue, hebrewTime.part);
            }
            if (i == 1) {
                return z ? hebrewTime.plus(MathUtils.safeSubtract(intValue, hebrewTime.hour23), Unit.HOURS) : HebrewTime.ofDigital(intValue, hebrewTime.part);
            }
            if (i == 2) {
                return z ? hebrewTime.plus(MathUtils.safeSubtract(intValue, hebrewTime.part), Unit.HALAKIM) : HebrewTime.ofDigital(hebrewTime.hour23, intValue);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HebrewTime hebrewTime) {
            int i = this.index;
            if (i == 0 || i == 1) {
                return HebrewTime.PART_OF_HOUR;
            }
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HebrewTime hebrewTime) {
            return getChildAtFloor(hebrewTime);
        }
    }

    private static class Merger implements ChronoMerger<HebrewTime> {
        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return 100;
        }

        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(HebrewTime hebrewTime, AttributeQuery attributeQuery) {
            return hebrewTime;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HebrewTime createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HebrewTime createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        /* JADX WARN: Type inference failed for: r3v2, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HebrewTime createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return HebrewTime.at(id).apply(Moment.from(timeSource.currentTime()));
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HebrewTime createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            int i;
            if (chronoEntity.contains(HebrewTime.PART_OF_HOUR)) {
                i = chronoEntity.getInt(HebrewTime.PART_OF_HOUR);
                if (i < 0 || i >= HebrewTime.PARTS_IN_HOUR) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("PART_OF_HOUR out of range: " + i));
                    return null;
                }
            } else {
                i = 0;
            }
            if (chronoEntity.contains(HebrewTime.CLOCK_CYCLE) && chronoEntity.contains(HebrewTime.CLOCK_HOUR)) {
                ClockCycle clockCycle = (ClockCycle) chronoEntity.get(HebrewTime.CLOCK_CYCLE);
                int i2 = chronoEntity.getInt(HebrewTime.CLOCK_HOUR);
                if (i2 < 1 || i2 > 12) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("CLOCK_HOUR out of range: " + i2));
                    return null;
                }
                return new HebrewTime(clockCycle, i2, i);
            }
            if (chronoEntity.contains(HebrewTime.DIGITAL_HOUR)) {
                int i3 = chronoEntity.getInt(HebrewTime.DIGITAL_HOUR);
                if (i3 < 0 || i3 > 23) {
                    chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) ("DIGITAL_HOUR out of range: " + i3));
                    return null;
                }
                return new HebrewTime(i3, i);
            }
            chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Missing cycle or hour of cycle.");
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            throw new UnsupportedOperationException("Localized format patterns are not available.");
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.EVENING;
        }
    }

    private static class SPX implements Externalizable {
        private static final int HEBREW_TIME = 13;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(13);
            writeHebrewTime(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 13) {
                this.obj = readHebrewTime(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeHebrewTime(ObjectOutput objectOutput) throws IOException {
            HebrewTime hebrewTime = (HebrewTime) this.obj;
            objectOutput.writeByte(hebrewTime.getDigitalHour());
            objectOutput.writeShort(hebrewTime.getPart());
        }

        private HebrewTime readHebrewTime(ObjectInput objectInput) throws IOException {
            return HebrewTime.ofDigital(objectInput.readByte(), objectInput.readShort());
        }
    }
}
