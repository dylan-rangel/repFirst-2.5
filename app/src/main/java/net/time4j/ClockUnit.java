package net.time4j;

import net.time4j.base.MathUtils;
import net.time4j.engine.Normalizer;
import net.time4j.engine.TimePoint;
import net.time4j.engine.TimeSpan;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes3.dex */
public enum ClockUnit implements IsoTimeUnit {
    HOURS { // from class: net.time4j.ClockUnit.1
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 3600.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'H';
        }
    },
    MINUTES { // from class: net.time4j.ClockUnit.2
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 60.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'M';
        }
    },
    SECONDS { // from class: net.time4j.ClockUnit.3
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 1.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'S';
        }
    },
    MILLIS { // from class: net.time4j.ClockUnit.4
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 0.001d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return '3';
        }
    },
    MICROS { // from class: net.time4j.ClockUnit.5
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 1.0E-6d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return '6';
        }
    },
    NANOS { // from class: net.time4j.ClockUnit.6
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 1.0E-9d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return '9';
        }
    };

    private static final long[] FACTORS = {1, 60, 3600, DateUtils.MILLIS_PER_HOUR, 3600000000L, 3600000000000L};

    @Override // net.time4j.engine.ChronoUnit
    public boolean isCalendrical() {
        return false;
    }

    public <T extends TimePoint<? super ClockUnit, T>> long between(T t, T t2) {
        return t.until(t2, this);
    }

    public long convert(long j, ClockUnit clockUnit) {
        if (j == 0) {
            return 0L;
        }
        int ordinal = ordinal();
        int ordinal2 = clockUnit.ordinal();
        if (ordinal == ordinal2) {
            return j;
        }
        if (ordinal > ordinal2) {
            long[] jArr = FACTORS;
            return MathUtils.safeMultiply(j, jArr[ordinal] / jArr[ordinal2]);
        }
        long[] jArr2 = FACTORS;
        return j / (jArr2[ordinal2] / jArr2[ordinal]);
    }

    public long convert(TimeSpan<? extends ClockUnit> timeSpan) {
        long j = 0;
        if (timeSpan.isEmpty()) {
            return 0L;
        }
        ClockUnit clockUnit = null;
        for (int size = timeSpan.getTotalLength().size() - 1; size >= 0; size--) {
            TimeSpan.Item<? extends ClockUnit> item = timeSpan.getTotalLength().get(size);
            ClockUnit unit = item.getUnit();
            if (clockUnit == null) {
                j = item.getAmount();
                clockUnit = unit;
            } else {
                j = MathUtils.safeAdd(j, clockUnit.convert(item.getAmount(), unit));
            }
        }
        if (timeSpan.isNegative()) {
            j = MathUtils.safeNegate(j);
        }
        return convert(j, clockUnit);
    }

    public Normalizer<ClockUnit> only() {
        return ClockNormalizer.ofOnlyMode(this);
    }

    public Normalizer<ClockUnit> truncated() {
        return ClockNormalizer.ofTruncateMode(this);
    }

    public Normalizer<ClockUnit> rounded() {
        return ClockNormalizer.ofRoundingMode(this);
    }
}
