package net.time4j.calendar;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import net.time4j.Meridiem;
import net.time4j.PlainTime;
import net.time4j.ProportionalElement;
import net.time4j.WallTimeElement;
import net.time4j.ZonalElement;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.service.EthiopianExtension;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoMerger;
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
import net.time4j.format.Attributes;
import net.time4j.format.CalendarType;
import net.time4j.format.DisplayElement;
import net.time4j.format.LocalizedPatternSupport;

@CalendarType("ethiopic")
/* loaded from: classes3.dex */
public final class EthiopianTime extends TimePoint<Unit, EthiopianTime> implements Temporal<EthiopianTime>, LocalizedPatternSupport {

    @FormattableElement(format = "a")
    public static final ChronoElement<Meridiem> AM_PM_OF_DAY;
    private static final int DIGITAL_HOUR_INDEX = 1;

    @FormattableElement(format = "H")
    public static final ChronoElement<Integer> DIGITAL_HOUR_OF_DAY;
    private static final TimeAxis<Unit, EthiopianTime> ENGINE;

    @FormattableElement(format = "h")
    public static final ChronoElement<Integer> ETHIOPIAN_HOUR;
    private static final int ETHIOPIAN_HOUR_INDEX = 0;
    public static final ChronoElement<PlainTime> ISO_TIME;
    private static final EthiopianTime MAX;
    private static final EthiopianTime MIN;
    private static final int MINUTE_INDEX = 2;

    @FormattableElement(format = "m")
    public static final ChronoElement<Integer> MINUTE_OF_HOUR;
    private static final int SECOND_INDEX = 3;

    @FormattableElement(format = "s")
    public static final ChronoElement<Integer> SECOND_OF_MINUTE;
    private static final long serialVersionUID = 3576122091324773241L;
    private final transient int hour24;
    private final transient int minute;
    private final transient int second;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public EthiopianTime getContext() {
        return this;
    }

    /* synthetic */ EthiopianTime(int i, int i2, int i3, AnonymousClass1 anonymousClass1) {
        this(i, i2, i3);
    }

    static {
        WallTimeElement wallTimeElement = PlainTime.COMPONENT;
        ISO_TIME = wallTimeElement;
        ZonalElement<Meridiem> zonalElement = PlainTime.AM_PM_OF_DAY;
        AM_PM_OF_DAY = zonalElement;
        EthiopianHour ethiopianHour = EthiopianHour.ELEMENT;
        ETHIOPIAN_HOUR = ethiopianHour;
        ProportionalElement<Integer, PlainTime> proportionalElement = PlainTime.DIGITAL_HOUR_OF_DAY;
        DIGITAL_HOUR_OF_DAY = proportionalElement;
        ProportionalElement<Integer, PlainTime> proportionalElement2 = PlainTime.MINUTE_OF_HOUR;
        MINUTE_OF_HOUR = proportionalElement2;
        ProportionalElement<Integer, PlainTime> proportionalElement3 = PlainTime.SECOND_OF_MINUTE;
        SECOND_OF_MINUTE = proportionalElement3;
        EthiopianTime ethiopianTime = new EthiopianTime(6, 0, 0);
        MIN = ethiopianTime;
        EthiopianTime ethiopianTime2 = new EthiopianTime(5, 59, 59);
        MAX = ethiopianTime2;
        AnonymousClass1 anonymousClass1 = null;
        TimeAxis.Builder appendElement = TimeAxis.Builder.setUp(Unit.class, EthiopianTime.class, new Merger(anonymousClass1), ethiopianTime, ethiopianTime2).appendElement((ChronoElement) zonalElement, (ElementRule) new MeridiemRule(anonymousClass1)).appendElement((ChronoElement) wallTimeElement, (ElementRule) new TimeRule(anonymousClass1)).appendElement(ethiopianHour, new IntegerElementRule(0), Unit.HOURS).appendElement(proportionalElement, new IntegerElementRule(1), Unit.HOURS).appendElement(proportionalElement2, new IntegerElementRule(2), Unit.MINUTES).appendElement(proportionalElement3, new IntegerElementRule(3), Unit.SECONDS);
        registerUnits(appendElement);
        registerExtensions(appendElement);
        ENGINE = appendElement.build();
    }

    private EthiopianTime(int i, int i2, int i3) {
        if (i < 0 || i > 23) {
            throw new IllegalArgumentException("HOUR_OF_DAY out of range: " + i);
        }
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("MINUTE_OF_HOUR out of range: " + i2);
        }
        if (i3 < 0 || i3 > 59) {
            throw new IllegalArgumentException("SECOND_OF_MINUTE out of range: " + i3);
        }
        this.hour24 = i;
        this.minute = i2;
        this.second = i3;
    }

    public static EthiopianTime ofDay(int i, int i2) {
        return of(false, i, i2, 0);
    }

    public static EthiopianTime ofDay(int i, int i2, int i3) {
        return of(false, i, i2, i3);
    }

    public static EthiopianTime ofNight(int i, int i2) {
        return of(true, i, i2, 0);
    }

    public static EthiopianTime ofNight(int i, int i2, int i3) {
        return of(true, i, i2, i3);
    }

    public static EthiopianTime nowInSystemTime() {
        return from(PlainTime.nowInSystemTime());
    }

    public boolean isDay() {
        int i = this.hour24;
        return i >= 6 && i < 18;
    }

    public boolean isNight() {
        return !isDay();
    }

    public int getHour() {
        int i = this.hour24 - 6;
        if (i < 0) {
            i += 12;
        } else if (i >= 12) {
            i -= 12;
        }
        if (i == 0) {
            return 12;
        }
        return i;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    @Override // net.time4j.engine.Temporal
    public boolean isAfter(EthiopianTime ethiopianTime) {
        return getTimeOfDay() > ethiopianTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.Temporal
    public boolean isBefore(EthiopianTime ethiopianTime) {
        return getTimeOfDay() < ethiopianTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.Temporal
    public boolean isSimultaneous(EthiopianTime ethiopianTime) {
        return getTimeOfDay() == ethiopianTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public int compareTo(EthiopianTime ethiopianTime) {
        return getTimeOfDay() - ethiopianTime.getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EthiopianTime) && getTimeOfDay() == ((EthiopianTime) obj).getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public int hashCode() {
        return getTimeOfDay();
    }

    @Override // net.time4j.engine.TimePoint
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ethiopic-");
        sb.append(isDay() ? "day-" : "night-");
        sb.append(getHour());
        sb.append(':');
        if (this.minute < 10) {
            sb.append('0');
        }
        sb.append(this.minute);
        sb.append(':');
        if (this.second < 10) {
            sb.append('0');
        }
        sb.append(this.second);
        return sb.toString();
    }

    public PlainTime toISO() {
        return PlainTime.of(this.hour24, this.minute, this.second);
    }

    public static EthiopianTime from(PlainTime plainTime) {
        int hour = plainTime.getHour();
        if (hour == 24) {
            hour = 0;
        }
        return new EthiopianTime(hour, plainTime.getMinute(), plainTime.getSecond());
    }

    public static TimeAxis<Unit, EthiopianTime> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, EthiopianTime> getChronology() {
        return ENGINE;
    }

    private static EthiopianTime of(boolean z, int i, int i2, int i3) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Hour out of range 1-12: " + i);
        }
        if (i == 12) {
            i = 0;
        }
        int i4 = i + 6;
        if (z && (i4 = i4 + 12) >= 24) {
            i4 -= 24;
        }
        return new EthiopianTime(i4, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTimeOfDay() {
        int i = this.second + (this.minute * 60);
        int i2 = this.hour24;
        if (i2 < 6) {
            i2 += 24;
        }
        return i + (i2 * 3600);
    }

    private static void registerUnits(TimeAxis.Builder<Unit, EthiopianTime> builder) {
        EnumSet allOf = EnumSet.allOf(Unit.class);
        for (Unit unit : Unit.values()) {
            builder.appendUnit(unit, new ClockUnitRule(unit, null), unit.getLength(), allOf);
        }
    }

    private static void registerExtensions(TimeAxis.Builder<Unit, EthiopianTime> builder) {
        builder.appendExtension((ChronoExtension) new EthiopianExtension());
        for (ChronoExtension chronoExtension : PlainTime.axis().getExtensions()) {
            Set<ChronoElement<?>> elements = chronoExtension.getElements(Locale.ROOT, Attributes.empty());
            if (elements.size() == 2) {
                Iterator<ChronoElement<?>> it = elements.iterator();
                while (it.hasNext()) {
                    if (it.next().name().endsWith("_DAY_PERIOD")) {
                        builder.appendExtension(chronoExtension);
                        return;
                    }
                }
            }
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
        MINUTES(60.0d),
        SECONDS(1.0d);

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

        public int between(EthiopianTime ethiopianTime, EthiopianTime ethiopianTime2) {
            return (int) ethiopianTime.until(ethiopianTime2, (EthiopianTime) this);
        }
    }

    private static class ClockUnitRule implements UnitRule<EthiopianTime> {
        private final Unit unit;

        /* synthetic */ ClockUnitRule(Unit unit, AnonymousClass1 anonymousClass1) {
            this(unit);
        }

        private ClockUnitRule(Unit unit) {
            this.unit = unit;
        }

        @Override // net.time4j.engine.UnitRule
        public EthiopianTime addTo(EthiopianTime ethiopianTime, long j) {
            long safeAdd;
            if (j == 0) {
                return ethiopianTime;
            }
            int i = ethiopianTime.minute;
            int i2 = ethiopianTime.second;
            int i3 = AnonymousClass1.$SwitchMap$net$time4j$calendar$EthiopianTime$Unit[this.unit.ordinal()];
            if (i3 == 1) {
                safeAdd = MathUtils.safeAdd(ethiopianTime.hour24, j);
            } else if (i3 == 2) {
                long safeAdd2 = MathUtils.safeAdd(ethiopianTime.minute, j);
                safeAdd = MathUtils.safeAdd(ethiopianTime.hour24, MathUtils.floorDivide(safeAdd2, 60));
                i = MathUtils.floorModulo(safeAdd2, 60);
            } else if (i3 == 3) {
                long safeAdd3 = MathUtils.safeAdd(ethiopianTime.second, j);
                long safeAdd4 = MathUtils.safeAdd(ethiopianTime.minute, MathUtils.floorDivide(safeAdd3, 60));
                safeAdd = MathUtils.safeAdd(ethiopianTime.hour24, MathUtils.floorDivide(safeAdd4, 60));
                i = MathUtils.floorModulo(safeAdd4, 60);
                i2 = MathUtils.floorModulo(safeAdd3, 60);
            } else {
                throw new UnsupportedOperationException(this.unit.name());
            }
            return new EthiopianTime(MathUtils.floorModulo(safeAdd, 24), i, i2, null);
        }

        @Override // net.time4j.engine.UnitRule
        public long between(EthiopianTime ethiopianTime, EthiopianTime ethiopianTime2) {
            long j;
            long timeOfDay = ethiopianTime2.getTimeOfDay() - ethiopianTime.getTimeOfDay();
            int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$EthiopianTime$Unit[this.unit.ordinal()];
            if (i == 1) {
                j = 3600;
            } else if (i == 2) {
                j = 60;
            } else {
                if (i != 3) {
                    throw new UnsupportedOperationException(this.unit.name());
                }
                j = 1;
            }
            return timeOfDay / j;
        }
    }

    /* renamed from: net.time4j.calendar.EthiopianTime$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$EthiopianTime$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$net$time4j$calendar$EthiopianTime$Unit = iArr;
            try {
                iArr[Unit.HOURS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$EthiopianTime$Unit[Unit.MINUTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$EthiopianTime$Unit[Unit.SECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static class EthiopianHour extends DisplayElement<Integer> {
        static final EthiopianHour ELEMENT = new EthiopianHour();
        private static final long serialVersionUID = -2095959121446847268L;

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'h';
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return false;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean isSingleton() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return true;
        }

        private EthiopianHour() {
            super("ETHIOPIAN_HOUR");
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<Integer> getType() {
            return Integer.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public Integer getDefaultMinimum() {
            return 1;
        }

        @Override // net.time4j.engine.ChronoElement
        public Integer getDefaultMaximum() {
            return 12;
        }

        @Override // net.time4j.engine.BasicElement
        protected ChronoElement<?> getParent() {
            return PlainTime.CLOCK_HOUR_OF_AMPM;
        }

        @Override // net.time4j.engine.BasicElement
        protected <T extends ChronoEntity<T>> ElementRule<T, Integer> derive(Chronology<T> chronology) {
            AnonymousClass1 anonymousClass1 = null;
            if (PlainTime.axis().equals(chronology)) {
                return new GeneralHourRule(anonymousClass1);
            }
            return null;
        }

        private Object readResolve() {
            return ELEMENT;
        }
    }

    private static class TimeRule implements ElementRule<EthiopianTime, PlainTime> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianTime ethiopianTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianTime ethiopianTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianTime ethiopianTime, PlainTime plainTime) {
            return plainTime != null;
        }

        private TimeRule() {
        }

        /* synthetic */ TimeRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getValue(EthiopianTime ethiopianTime) {
            return ethiopianTime.toISO();
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getMinimum(EthiopianTime ethiopianTime) {
            return PlainTime.midnightAtStartOfDay();
        }

        @Override // net.time4j.engine.ElementRule
        public PlainTime getMaximum(EthiopianTime ethiopianTime) {
            return PlainTime.of(23, 59, 59);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianTime withValue2(EthiopianTime ethiopianTime, PlainTime plainTime, boolean z) {
            if (plainTime == null) {
                throw new IllegalArgumentException("Missing time value.");
            }
            return EthiopianTime.from(plainTime);
        }
    }

    private static class MeridiemRule implements ElementRule<EthiopianTime, Meridiem> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianTime ethiopianTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianTime ethiopianTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianTime ethiopianTime, Meridiem meridiem) {
            return meridiem != null;
        }

        private MeridiemRule() {
        }

        /* synthetic */ MeridiemRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getValue(EthiopianTime ethiopianTime) {
            return ethiopianTime.hour24 < 12 ? Meridiem.AM : Meridiem.PM;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianTime withValue2(EthiopianTime ethiopianTime, Meridiem meridiem, boolean z) {
            int i = ethiopianTime.hour24;
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
            return new EthiopianTime(i, ethiopianTime.minute, ethiopianTime.second, null);
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getMinimum(EthiopianTime ethiopianTime) {
            return Meridiem.AM;
        }

        @Override // net.time4j.engine.ElementRule
        public Meridiem getMaximum(EthiopianTime ethiopianTime) {
            return Meridiem.PM;
        }
    }

    private static class GeneralHourRule<T extends ChronoEntity<T>> implements ElementRule<T, Integer> {
        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(T t) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(T t) {
            return null;
        }

        private GeneralHourRule() {
        }

        /* synthetic */ GeneralHourRule(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(T t) {
            return Integer.valueOf(EthiopianTime.from((PlainTime) t.get(PlainTime.COMPONENT)).getHour());
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(T t) {
            return 1;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(T t) {
            return 12;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(T t, Integer num) {
            return EthiopianTime.from((PlainTime) t.get(PlainTime.COMPONENT)).isValid((ChronoElement<ChronoElement<Integer>>) EthiopianTime.ETHIOPIAN_HOUR, (ChronoElement<Integer>) num);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public T withValue2(T t, Integer num, boolean z) {
            return (T) t.with(PlainTime.COMPONENT, ((EthiopianTime) EthiopianTime.from((PlainTime) t.get(PlainTime.COMPONENT)).with((ChronoElement<ChronoElement<Integer>>) EthiopianTime.ETHIOPIAN_HOUR, (ChronoElement<Integer>) num)).toISO());
        }
    }

    private static class IntegerElementRule implements ElementRule<EthiopianTime, Integer> {
        private final int index;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(EthiopianTime ethiopianTime) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(EthiopianTime ethiopianTime) {
            return null;
        }

        IntegerElementRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(EthiopianTime ethiopianTime) {
            int i = this.index;
            if (i == 0) {
                return Integer.valueOf(ethiopianTime.getHour());
            }
            if (i == 1) {
                return Integer.valueOf(ethiopianTime.hour24);
            }
            if (i == 2) {
                return Integer.valueOf(ethiopianTime.minute);
            }
            if (i == 3) {
                return Integer.valueOf(ethiopianTime.second);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(EthiopianTime ethiopianTime) {
            int i = this.index;
            if (i == 0) {
                return 1;
            }
            if (i == 1 || i == 2 || i == 3) {
                return 0;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(EthiopianTime ethiopianTime) {
            int i = this.index;
            if (i == 0) {
                return 12;
            }
            if (i == 1) {
                return 23;
            }
            if (i == 2 || i == 3) {
                return 59;
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(EthiopianTime ethiopianTime, Integer num) {
            return num != null && getMinimum(ethiopianTime).compareTo(num) <= 0 && getMaximum(ethiopianTime).compareTo(num) >= 0;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public EthiopianTime withValue2(EthiopianTime ethiopianTime, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing element value.");
            }
            int intValue = num.intValue();
            int i = this.index;
            if (i == 0) {
                return ethiopianTime.isDay() ? EthiopianTime.ofDay(intValue, ethiopianTime.minute, ethiopianTime.second) : EthiopianTime.ofNight(intValue, ethiopianTime.minute, ethiopianTime.second);
            }
            AnonymousClass1 anonymousClass1 = null;
            if (i == 1) {
                return new EthiopianTime(intValue, ethiopianTime.minute, ethiopianTime.second, anonymousClass1);
            }
            if (i == 2) {
                return new EthiopianTime(ethiopianTime.hour24, intValue, ethiopianTime.second, anonymousClass1);
            }
            if (i == 3) {
                return new EthiopianTime(ethiopianTime.hour24, ethiopianTime.minute, intValue, anonymousClass1);
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }
    }

    private static class Merger implements ChronoMerger<EthiopianTime> {
        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return 100;
        }

        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(EthiopianTime ethiopianTime, AttributeQuery attributeQuery) {
            return ethiopianTime;
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
        public /* bridge */ /* synthetic */ EthiopianTime createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ EthiopianTime createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return displayStyle.getStyleValue() == 3 ? "h:mm a" : "h:mm:ss a";
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public EthiopianTime createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            return EthiopianTime.from((PlainTime) PlainTime.axis().createFrom(timeSource, attributeQuery));
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public EthiopianTime createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            PlainTime createFrom = PlainTime.axis().createFrom(chronoEntity, attributeQuery, z, false);
            if (createFrom != null) {
                return EthiopianTime.from(createFrom);
            }
            return null;
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MORNING;
        }
    }

    private static class SPX implements Externalizable {
        private static final int ETHIOPIAN_TIME = 5;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(5);
            writeEthiopianTime(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 5) {
                this.obj = readEthiopianTime(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeEthiopianTime(ObjectOutput objectOutput) throws IOException {
            EthiopianTime ethiopianTime = (EthiopianTime) this.obj;
            objectOutput.writeInt((((Integer) ethiopianTime.get(EthiopianTime.DIGITAL_HOUR_OF_DAY)).intValue() * 3600) + (ethiopianTime.getMinute() * 60) + ethiopianTime.getSecond());
        }

        private EthiopianTime readEthiopianTime(ObjectInput objectInput) throws IOException {
            int readInt = objectInput.readInt();
            int i = readInt % 60;
            int i2 = readInt / 60;
            return EthiopianTime.from(PlainTime.of(i2 / 60, i2 % 60, i));
        }
    }
}
