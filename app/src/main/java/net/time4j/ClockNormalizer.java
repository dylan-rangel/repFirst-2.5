package net.time4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Normalizer;
import net.time4j.engine.TimeSpan;

/* loaded from: classes3.dex */
final class ClockNormalizer implements Normalizer<ClockUnit> {
    private static final int ONLY_MODE = 0;
    private static final int ROUNDING_MODE = 2;
    private static final int TRUNCATE_MODE = 1;
    private final int mode;
    private final ClockUnit unit;
    private static final Map<ClockUnit, ClockNormalizer> MAP_ONLY = fill(0);
    private static final Map<ClockUnit, ClockNormalizer> MAP_TRUNC = fill(1);
    private static final Map<ClockUnit, ClockNormalizer> MAP_ROUND = fill(2);

    private ClockNormalizer(ClockUnit clockUnit, int i) {
        this.unit = clockUnit;
        this.mode = i;
    }

    static ClockNormalizer ofOnlyMode(ClockUnit clockUnit) {
        ClockNormalizer clockNormalizer = MAP_ONLY.get(clockUnit);
        if (clockNormalizer != null) {
            return clockNormalizer;
        }
        throw new IllegalArgumentException(clockUnit.name());
    }

    static ClockNormalizer ofTruncateMode(ClockUnit clockUnit) {
        ClockNormalizer clockNormalizer = MAP_TRUNC.get(clockUnit);
        if (clockNormalizer != null) {
            return clockNormalizer;
        }
        throw new IllegalArgumentException(clockUnit.name());
    }

    static ClockNormalizer ofRoundingMode(ClockUnit clockUnit) {
        ClockNormalizer clockNormalizer = MAP_ROUND.get(clockUnit);
        if (clockNormalizer != null) {
            return clockNormalizer;
        }
        throw new IllegalArgumentException(clockUnit.name());
    }

    @Override // net.time4j.engine.Normalizer
    /* renamed from: normalize, reason: merged with bridge method [inline-methods] */
    public TimeSpan<ClockUnit> normalize2(TimeSpan<? extends ClockUnit> timeSpan) {
        long j;
        int i = this.mode;
        if (i == 0) {
            return Duration.of(this.unit.convert(timeSpan), this.unit);
        }
        if (i == 1) {
            ArrayList arrayList = new ArrayList();
            for (TimeSpan.Item<? extends ClockUnit> item : timeSpan.getTotalLength()) {
                ClockUnit unit = item.getUnit();
                if (unit.compareTo(this.unit) <= 0) {
                    arrayList.add(TimeSpan.Item.of(item.getAmount(), unit));
                }
            }
            if (arrayList.isEmpty()) {
                return Duration.ofZero();
            }
            return new Duration(arrayList, timeSpan.isNegative());
        }
        if (i == 2) {
            boolean isNegative = timeSpan.isNegative();
            Duration plus = Duration.ofZero().plus(timeSpan);
            if (isNegative) {
                plus = plus.abs();
            }
            Duration with = plus.with(Duration.STD_CLOCK_PERIOD);
            int i2 = AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[this.unit.ordinal()];
            if (i2 == 1 || i2 == 2) {
                j = 30;
            } else {
                if (i2 != 3 && i2 != 4 && i2 != 5) {
                    return with;
                }
                j = 500;
            }
            if (with.getPartialAmount((ChronoUnit) ClockUnit.values()[this.unit.ordinal() + 1]) >= j) {
                with = with.plus(1L, this.unit).with(Duration.STD_CLOCK_PERIOD);
            }
            Duration with2 = with.with(this.unit.truncated());
            return isNegative ? with2.inverse() : with2;
        }
        throw new UnsupportedOperationException("Unknown mode: " + this.mode);
    }

    /* renamed from: net.time4j.ClockNormalizer$1, reason: invalid class name */
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
        }
    }

    private static Map<ClockUnit, ClockNormalizer> fill(int i) {
        EnumMap enumMap = new EnumMap(ClockUnit.class);
        for (ClockUnit clockUnit : ClockUnit.values()) {
            enumMap.put((EnumMap) clockUnit, (ClockUnit) new ClockNormalizer(clockUnit, i));
        }
        return Collections.unmodifiableMap(enumMap);
    }
}
