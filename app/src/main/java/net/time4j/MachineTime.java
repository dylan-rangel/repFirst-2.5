package net.time4j;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.time4j.base.MathUtils;
import net.time4j.base.UnixTime;
import net.time4j.engine.TimeMetric;
import net.time4j.engine.TimePoint;
import net.time4j.engine.TimeSpan;
import net.time4j.format.TimeSpanFormatter;
import net.time4j.scale.TimeScale;
import net.time4j.scale.UniversalTime;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes3.dex */
public final class MachineTime<U> implements TimeSpan<U>, Comparable<MachineTime<U>>, Serializable {
    private static final int MRD = 1000000000;
    public static final TimeMetric<TimeUnit, MachineTime<TimeUnit>> ON_POSIX_SCALE;
    public static final TimeMetric<TimeUnit, MachineTime<SI>> ON_UTC_SCALE;
    private static final MachineTime<TimeUnit> POSIX_ZERO = new MachineTime<>(0, 0, TimeScale.POSIX);
    private static final MachineTime<SI> UTC_ZERO = new MachineTime<>(0, 0, TimeScale.UTC);
    private static final long serialVersionUID = -4150291820807606229L;
    private final transient int nanos;
    private final transient TimeScale scale;
    private final transient long seconds;

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T cast(Object obj) {
        return obj;
    }

    /* synthetic */ MachineTime(long j, int i, TimeScale timeScale, AnonymousClass1 anonymousClass1) {
        this(j, i, timeScale);
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        ON_POSIX_SCALE = new Metric(TimeScale.POSIX, anonymousClass1);
        ON_UTC_SCALE = new Metric(TimeScale.UTC, anonymousClass1);
    }

    private MachineTime(long j, int i, TimeScale timeScale) {
        while (i < 0) {
            i += 1000000000;
            j = MathUtils.safeSubtract(j, 1L);
        }
        while (i >= 1000000000) {
            i -= 1000000000;
            j = MathUtils.safeAdd(j, 1L);
        }
        if (j < 0 && i > 0) {
            j++;
            i -= 1000000000;
        }
        this.seconds = j;
        this.nanos = i;
        this.scale = timeScale;
    }

    public static MachineTime<TimeUnit> of(long j, TimeUnit timeUnit) {
        if (timeUnit.compareTo(TimeUnit.SECONDS) >= 0) {
            return ofPosixUnits(MathUtils.safeMultiply(j, TimeUnit.SECONDS.convert(1L, timeUnit)), 0);
        }
        long safeMultiply = MathUtils.safeMultiply(j, TimeUnit.NANOSECONDS.convert(1L, timeUnit));
        return ofPosixUnits(MathUtils.floorDivide(safeMultiply, 1000000000), MathUtils.floorModulo(safeMultiply, 1000000000));
    }

    public static MachineTime<SI> of(long j, SI si) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$SI[si.ordinal()];
        if (i == 1) {
            return ofSIUnits(j, 0);
        }
        if (i == 2) {
            return ofSIUnits(MathUtils.floorDivide(j, 1000000000), MathUtils.floorModulo(j, 1000000000));
        }
        throw new UnsupportedOperationException(si.name());
    }

    public static MachineTime<TimeUnit> ofPosixUnits(long j, int i) {
        if (j == 0 && i == 0) {
            return POSIX_ZERO;
        }
        return new MachineTime<>(j, i, TimeScale.POSIX);
    }

    public static MachineTime<SI> ofSIUnits(long j, int i) {
        if (j == 0 && i == 0) {
            return UTC_ZERO;
        }
        return new MachineTime<>(j, i, TimeScale.UTC);
    }

    public static MachineTime<TimeUnit> ofPosixSeconds(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Invalid value: " + d);
        }
        long floor = (long) Math.floor(d);
        return ofPosixUnits(floor, (int) ((d - floor) * 1.0E9d));
    }

    public static MachineTime<TimeUnit> ofPosixSeconds(BigDecimal bigDecimal) {
        BigDecimal scale = bigDecimal.setScale(0, RoundingMode.FLOOR);
        return ofPosixUnits(scale.longValueExact(), bigDecimal.subtract(scale).multiply(BigDecimal.valueOf(1000000000L)).setScale(0, RoundingMode.DOWN).intValueExact());
    }

    public static MachineTime<SI> ofSISeconds(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Invalid value: " + d);
        }
        long floor = (long) Math.floor(d);
        return ofSIUnits(floor, (int) ((d - floor) * 1.0E9d));
    }

    public static MachineTime<SI> ofSISeconds(BigDecimal bigDecimal) {
        BigDecimal scale = bigDecimal.setScale(0, RoundingMode.FLOOR);
        return ofSIUnits(scale.longValueExact(), bigDecimal.subtract(scale).multiply(BigDecimal.valueOf(1000000000L)).setScale(0, RoundingMode.DOWN).intValueExact());
    }

    public long getSeconds() {
        long j = this.seconds;
        return this.nanos < 0 ? j - 1 : j;
    }

    public int getFraction() {
        int i = this.nanos;
        return i < 0 ? i + 1000000000 : i;
    }

    public TimeScale getScale() {
        return this.scale;
    }

    @Override // net.time4j.engine.TimeSpan
    public List<TimeSpan.Item<U>> getTotalLength() {
        ArrayList arrayList = new ArrayList(2);
        if (this.seconds != 0) {
            arrayList.add(TimeSpan.Item.of(Math.abs(this.seconds), cast(this.scale == TimeScale.UTC ? SI.SECONDS : TimeUnit.SECONDS)));
        }
        if (this.nanos != 0) {
            arrayList.add(TimeSpan.Item.of(Math.abs(this.nanos), cast(this.scale == TimeScale.UTC ? SI.NANOSECONDS : TimeUnit.NANOSECONDS)));
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // net.time4j.engine.TimeSpan
    public boolean contains(Object obj) {
        return ((this.scale == TimeScale.POSIX && TimeUnit.SECONDS.equals(obj)) || (this.scale == TimeScale.UTC && SI.SECONDS.equals(obj))) ? this.seconds != 0 : ((this.scale == TimeScale.POSIX && TimeUnit.NANOSECONDS.equals(obj)) || (this.scale == TimeScale.UTC && SI.NANOSECONDS.equals(obj))) && this.nanos != 0;
    }

    @Override // net.time4j.engine.TimeSpan
    public long getPartialAmount(Object obj) {
        if ((this.scale == TimeScale.POSIX && TimeUnit.SECONDS.equals(obj)) || (this.scale == TimeScale.UTC && SI.SECONDS.equals(obj))) {
            return Math.abs(this.seconds);
        }
        if ((this.scale == TimeScale.POSIX && TimeUnit.NANOSECONDS.equals(obj)) || (this.scale == TimeScale.UTC && SI.NANOSECONDS.equals(obj))) {
            return Math.abs(this.nanos);
        }
        return 0L;
    }

    @Override // net.time4j.engine.TimeSpan
    public boolean isNegative() {
        return this.seconds < 0 || this.nanos < 0;
    }

    @Override // net.time4j.engine.TimeSpan
    public boolean isPositive() {
        return this.seconds > 0 || this.nanos > 0;
    }

    @Override // net.time4j.engine.TimeSpan
    public boolean isEmpty() {
        return this.seconds == 0 && this.nanos == 0;
    }

    public MachineTime<U> plus(long j, U u) {
        long safeAdd;
        long safeAdd2;
        long j2 = this.seconds;
        int i = this.nanos;
        if (this.scale == TimeScale.POSIX) {
            TimeUnit timeUnit = (TimeUnit) TimeUnit.class.cast(u);
            if (timeUnit.compareTo(TimeUnit.SECONDS) >= 0) {
                safeAdd = MathUtils.safeAdd(j2, MathUtils.safeMultiply(j, TimeUnit.SECONDS.convert(1L, timeUnit)));
            } else {
                long safeAdd3 = MathUtils.safeAdd(i, MathUtils.safeMultiply(j, TimeUnit.NANOSECONDS.convert(1L, timeUnit)));
                safeAdd2 = MathUtils.safeAdd(j2, MathUtils.floorDivide(safeAdd3, 1000000000));
                i = MathUtils.floorModulo(safeAdd3, 1000000000);
                safeAdd = safeAdd2;
            }
        } else {
            int i2 = AnonymousClass1.$SwitchMap$net$time4j$SI[((SI) SI.class.cast(u)).ordinal()];
            if (i2 == 1) {
                safeAdd = MathUtils.safeAdd(j2, j);
            } else if (i2 == 2) {
                long safeAdd4 = MathUtils.safeAdd(i, j);
                safeAdd2 = MathUtils.safeAdd(j2, MathUtils.floorDivide(safeAdd4, 1000000000));
                i = MathUtils.floorModulo(safeAdd4, 1000000000);
                safeAdd = safeAdd2;
            } else {
                throw new UnsupportedOperationException(u.toString());
            }
        }
        return new MachineTime<>(safeAdd, i, this.scale);
    }

    public MachineTime<U> plus(MachineTime<U> machineTime) {
        return machineTime.isEmpty() ? this : isEmpty() ? machineTime : new MachineTime<>(MathUtils.safeAdd(this.seconds, machineTime.seconds), this.nanos + machineTime.nanos, this.scale);
    }

    public MachineTime<U> minus(long j, U u) {
        return plus(negateExact(j), u);
    }

    public MachineTime<U> minus(MachineTime<U> machineTime) {
        if (machineTime.isEmpty()) {
            return this;
        }
        if (isEmpty()) {
            return machineTime.inverse();
        }
        return new MachineTime<>(MathUtils.safeSubtract(this.seconds, machineTime.seconds), this.nanos - machineTime.nanos, this.scale);
    }

    public MachineTime<U> abs() {
        return isNegative() ? new MachineTime<>(negateExact(this.seconds), -this.nanos, this.scale) : this;
    }

    public MachineTime<U> inverse() {
        return isEmpty() ? this : new MachineTime<>(negateExact(this.seconds), -this.nanos, this.scale);
    }

    public MachineTime<U> multipliedBy(long j) {
        Object ofSISeconds;
        if (j == 1) {
            return this;
        }
        if (j == 0) {
            if (this.scale == TimeScale.POSIX) {
                return (MachineTime) cast(POSIX_ZERO);
            }
            return (MachineTime) cast(UTC_ZERO);
        }
        BigDecimal multiply = toBigDecimal().multiply(BigDecimal.valueOf(j));
        if (this.scale == TimeScale.POSIX) {
            ofSISeconds = ofPosixSeconds(multiply);
        } else {
            ofSISeconds = ofSISeconds(multiply);
        }
        return (MachineTime) cast(ofSISeconds);
    }

    public MachineTime<U> multipliedBy(double d) {
        Object ofSISeconds;
        if (d == 1.0d) {
            return this;
        }
        if (d == 0.0d) {
            if (this.scale == TimeScale.POSIX) {
                return (MachineTime) cast(POSIX_ZERO);
            }
            return (MachineTime) cast(UTC_ZERO);
        }
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            double doubleValue = toBigDecimal().doubleValue() * d;
            if (this.scale == TimeScale.POSIX) {
                ofSISeconds = ofPosixSeconds(doubleValue);
            } else {
                ofSISeconds = ofSISeconds(doubleValue);
            }
            return (MachineTime) cast(ofSISeconds);
        }
        throw new IllegalArgumentException("Not finite: " + d);
    }

    public MachineTime<U> dividedBy(long j, RoundingMode roundingMode) {
        Object ofSISeconds;
        if (j == 1) {
            return this;
        }
        BigDecimal divide = toBigDecimal().setScale(9, RoundingMode.FLOOR).divide(new BigDecimal(j), roundingMode);
        if (this.scale == TimeScale.POSIX) {
            ofSISeconds = ofPosixSeconds(divide);
        } else {
            ofSISeconds = ofSISeconds(divide);
        }
        return (MachineTime) cast(ofSISeconds);
    }

    @Override // net.time4j.engine.TimeSpan
    public <T extends TimePoint<? super U, T>> T addTo(T t) {
        Enum r0;
        Enum r1;
        if (this.scale == TimeScale.POSIX) {
            r0 = TimeUnit.SECONDS;
            r1 = TimeUnit.NANOSECONDS;
        } else {
            r0 = SI.SECONDS;
            r1 = SI.NANOSECONDS;
        }
        return (T) t.plus(this.seconds, r0).plus(this.nanos, r1);
    }

    @Override // net.time4j.engine.TimeSpan
    public <T extends TimePoint<? super U, T>> T subtractFrom(T t) {
        Enum r0;
        Enum r1;
        if (this.scale == TimeScale.POSIX) {
            r0 = TimeUnit.SECONDS;
            r1 = TimeUnit.NANOSECONDS;
        } else {
            r0 = SI.SECONDS;
            r1 = SI.NANOSECONDS;
        }
        return (T) t.minus(this.seconds, r0).minus(this.nanos, r1);
    }

    public boolean isShorterThan(MachineTime<U> machineTime) {
        return abs().compareTo((MachineTime) machineTime.abs()) < 0;
    }

    public boolean isLongerThan(MachineTime<U> machineTime) {
        return abs().compareTo((MachineTime) machineTime.abs()) > 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(MachineTime<U> machineTime) {
        if (this.scale == machineTime.scale) {
            long j = this.seconds;
            long j2 = machineTime.seconds;
            if (j < j2) {
                return -1;
            }
            if (j > j2) {
                return 1;
            }
            return this.nanos - machineTime.nanos;
        }
        throw new ClassCastException("Different time scales.");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MachineTime)) {
            return false;
        }
        MachineTime machineTime = (MachineTime) obj;
        return this.seconds == machineTime.seconds && this.nanos == machineTime.nanos && this.scale == machineTime.scale;
    }

    public int hashCode() {
        long j = this.seconds;
        return ((((161 + ((int) (j ^ (j >>> 32)))) * 23) + this.nanos) * 23) + this.scale.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        createNumber(sb);
        sb.append("s [");
        sb.append(this.scale.name());
        sb.append(']');
        return sb.toString();
    }

    public BigDecimal toBigDecimal() {
        StringBuilder sb = new StringBuilder();
        createNumber(sb);
        return new BigDecimal(sb.toString());
    }

    private void createNumber(StringBuilder sb) {
        if (isNegative()) {
            sb.append('-');
            sb.append(Math.abs(this.seconds));
        } else {
            sb.append(this.seconds);
        }
        if (this.nanos != 0) {
            sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            String valueOf = String.valueOf(Math.abs(this.nanos));
            for (int length = 9 - valueOf.length(); length > 0; length--) {
                sb.append('0');
            }
            sb.append(valueOf);
        }
    }

    private static long negateExact(long j) {
        if (j != Long.MIN_VALUE) {
            return -j;
        }
        throw new ArithmeticException("Long overflow.");
    }

    private Object writeReplace() {
        return new SPX(this, 5);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public static final class Formatter extends TimeSpanFormatter<TimeUnit, MachineTime<TimeUnit>> {
        private Formatter(String str) {
            super(TimeUnit.class, str);
        }

        public static Formatter ofPattern(String str) {
            return new Formatter(str);
        }

        @Override // net.time4j.format.TimeSpanFormatter
        public void print(TimeSpan<? super TimeUnit> timeSpan, Appendable appendable) throws IOException {
            String pattern = getPattern();
            int length = pattern.length();
            StringBuilder sb = new StringBuilder(length);
            int i = 0;
            while (i < length) {
                char charAt = pattern.charAt(i);
                if (charAt == '\'') {
                    while (true) {
                        i++;
                        if (i >= length) {
                            break;
                        }
                        if (pattern.charAt(i) == '\'') {
                            int i2 = i + 1;
                            if (i2 < length && pattern.charAt(i2) == '\'') {
                                i = i2;
                            }
                        }
                    }
                } else {
                    sb.append(charAt);
                }
                i++;
            }
            String sb2 = sb.toString();
            EnumSet noneOf = EnumSet.noneOf(TimeUnit.class);
            if (sb2.contains("D")) {
                noneOf.add(TimeUnit.DAYS);
            }
            if (sb2.contains("h")) {
                noneOf.add(TimeUnit.HOURS);
            }
            if (sb2.contains("m")) {
                noneOf.add(TimeUnit.MINUTES);
            }
            if (sb2.contains("s")) {
                noneOf.add(TimeUnit.SECONDS);
            }
            if (sb2.contains("f")) {
                noneOf.add(TimeUnit.NANOSECONDS);
            }
            super.print(new Normalized(timeSpan, noneOf), appendable);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.time4j.format.TimeSpanFormatter
        public MachineTime<TimeUnit> convert(Map<TimeUnit, Long> map, boolean z) {
            int safeCast = map.containsKey(TimeUnit.NANOSECONDS) ? MathUtils.safeCast(map.get(TimeUnit.NANOSECONDS).longValue()) : 0;
            long j = 0;
            for (Map.Entry<TimeUnit, Long> entry : map.entrySet()) {
                int i = AnonymousClass1.$SwitchMap$java$util$concurrent$TimeUnit[entry.getKey().ordinal()];
                int i2 = 1;
                if (i == 1) {
                    i2 = 86400;
                } else if (i == 2) {
                    i2 = 3600;
                } else if (i == 3) {
                    i2 = 60;
                } else if (i != 4) {
                }
                j += MathUtils.safeMultiply(entry.getValue().longValue(), i2);
            }
            if (z) {
                j = MathUtils.safeNegate(j);
                safeCast = -safeCast;
            }
            return MachineTime.ofPosixUnits(j, safeCast);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.time4j.format.TimeSpanFormatter
        public TimeUnit getUnit(char c) {
            if (c == 'D') {
                return TimeUnit.DAYS;
            }
            if (c == 'f') {
                return TimeUnit.NANOSECONDS;
            }
            if (c == 'h') {
                return TimeUnit.HOURS;
            }
            if (c == 'm') {
                return TimeUnit.MINUTES;
            }
            if (c == 's') {
                return TimeUnit.SECONDS;
            }
            throw new IllegalArgumentException("Unsupported pattern symbol: " + c);
        }
    }

    /* renamed from: net.time4j.MachineTime$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$SI;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr;
            try {
                iArr[TimeUnit.DAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.HOURS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MINUTES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[SI.values().length];
            $SwitchMap$net$time4j$SI = iArr2;
            try {
                iArr2[SI.SECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$SI[SI.NANOSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static class Normalized implements TimeSpan<TimeUnit> {
        private final TimeSpan<? super TimeUnit> duration;
        private final Set<TimeUnit> patternUnits;

        @Override // net.time4j.engine.TimeSpan
        public boolean contains(TimeUnit timeUnit) {
            return true;
        }

        Normalized(TimeSpan<? super TimeUnit> timeSpan, Set<TimeUnit> set) {
            timeSpan.getClass();
            this.duration = timeSpan;
            this.patternUnits = set;
        }

        @Override // net.time4j.engine.TimeSpan
        public List<TimeSpan.Item<TimeUnit>> getTotalLength() {
            throw new AssertionError("Never called.");
        }

        @Override // net.time4j.engine.TimeSpan
        public long getPartialAmount(TimeUnit timeUnit) {
            long j;
            long j2;
            if (timeUnit == TimeUnit.NANOSECONDS) {
                return this.duration.getPartialAmount(TimeUnit.NANOSECONDS);
            }
            long partialAmount = this.duration.getPartialAmount(TimeUnit.SECONDS);
            long j3 = 0;
            if (this.patternUnits.contains(TimeUnit.DAYS)) {
                j = partialAmount / 86400;
                partialAmount -= 86400 * j;
            } else {
                j = 0;
            }
            if (this.patternUnits.contains(TimeUnit.HOURS)) {
                j2 = partialAmount / 3600;
                partialAmount -= 3600 * j2;
            } else {
                j2 = 0;
            }
            if (this.patternUnits.contains(TimeUnit.MINUTES)) {
                long j4 = partialAmount / 60;
                partialAmount -= 60 * j4;
                j3 = j4;
            }
            int i = AnonymousClass1.$SwitchMap$java$util$concurrent$TimeUnit[timeUnit.ordinal()];
            if (i == 1) {
                return j;
            }
            if (i == 2) {
                return j2;
            }
            if (i == 3) {
                return j3;
            }
            if (i == 4) {
                return partialAmount;
            }
            throw new AssertionError("Never called.");
        }

        @Override // net.time4j.engine.TimeSpan
        public boolean isNegative() {
            return this.duration.isNegative();
        }

        @Override // net.time4j.engine.TimeSpan
        public boolean isPositive() {
            return this.duration.isPositive();
        }

        @Override // net.time4j.engine.TimeSpan
        public boolean isEmpty() {
            return this.duration.isEmpty();
        }

        @Override // net.time4j.engine.TimeSpan
        public <T extends TimePoint<? super TimeUnit, T>> T addTo(T t) {
            throw new AssertionError("Never called.");
        }

        @Override // net.time4j.engine.TimeSpan
        public <T extends TimePoint<? super TimeUnit, T>> T subtractFrom(T t) {
            throw new AssertionError("Never called.");
        }
    }

    private static class Metric<U> implements TimeMetric<TimeUnit, MachineTime<U>> {
        private final TimeScale scale;

        @Override // net.time4j.engine.TimeMetric
        public TimeMetric<TimeUnit, MachineTime<U>> reversible() {
            return this;
        }

        /* synthetic */ Metric(TimeScale timeScale, AnonymousClass1 anonymousClass1) {
            this(timeScale);
        }

        private Metric(TimeScale timeScale) {
            this.scale = timeScale;
        }

        @Override // net.time4j.engine.TimeMetric
        public <T extends TimePoint<? super TimeUnit, T>> MachineTime<U> between(T t, T t2) {
            long posixTime;
            int nanosecond;
            int nanosecond2;
            if (this.scale == TimeScale.UTC && (t instanceof UniversalTime)) {
                UniversalTime universalTime = (UniversalTime) t;
                UniversalTime universalTime2 = (UniversalTime) t2;
                long elapsedTime = universalTime2.getElapsedTime(TimeScale.UTC);
                long elapsedTime2 = universalTime.getElapsedTime(TimeScale.UTC);
                if (elapsedTime < 0 || elapsedTime2 < 0) {
                    throw new UnsupportedOperationException("Cannot calculate SI-duration before 1972-01-01.");
                }
                posixTime = elapsedTime - elapsedTime2;
                nanosecond = universalTime2.getNanosecond(TimeScale.UTC);
                nanosecond2 = universalTime.getNanosecond(TimeScale.UTC);
            } else if (t instanceof UnixTime) {
                UnixTime unixTime = (UnixTime) t;
                UnixTime unixTime2 = (UnixTime) t2;
                posixTime = unixTime2.getPosixTime() - unixTime.getPosixTime();
                nanosecond = unixTime2.getNanosecond();
                nanosecond2 = unixTime.getNanosecond();
            } else {
                throw new UnsupportedOperationException("Machine time requires objects of type 'UnixTime'.");
            }
            return new MachineTime<>(posixTime, nanosecond - nanosecond2, this.scale, null);
        }
    }
}
