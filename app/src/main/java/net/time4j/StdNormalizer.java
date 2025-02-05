package net.time4j;

import java.util.ArrayList;
import java.util.Comparator;
import net.time4j.IsoUnit;
import net.time4j.base.MathUtils;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Normalizer;
import net.time4j.engine.TimeSpan;

/* loaded from: classes3.dex */
class StdNormalizer<U extends IsoUnit> implements Normalizer<U>, Comparator<TimeSpan.Item<? extends ChronoUnit>> {
    private static final int MIO = 1000000;
    private static final int MRD = 1000000000;
    private final boolean mixed;

    private StdNormalizer(boolean z) {
        this.mixed = z;
    }

    static StdNormalizer<IsoUnit> ofMixedUnits() {
        return new StdNormalizer<>(true);
    }

    static StdNormalizer<CalendarUnit> ofCalendarUnits() {
        return new StdNormalizer<>(false);
    }

    static StdNormalizer<ClockUnit> ofClockUnits() {
        return new StdNormalizer<>(false);
    }

    static Comparator<TimeSpan.Item<? extends ChronoUnit>> comparator() {
        return new StdNormalizer(false);
    }

    @Override // java.util.Comparator
    public int compare(TimeSpan.Item<? extends ChronoUnit> item, TimeSpan.Item<? extends ChronoUnit> item2) {
        return compare(item.getUnit(), item2.getUnit());
    }

    @Override // net.time4j.engine.Normalizer
    /* renamed from: normalize */
    public Duration<U> normalize2(TimeSpan<? extends U> timeSpan) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long safeAdd;
        int size = timeSpan.getTotalLength().size();
        ArrayList arrayList = new ArrayList(size);
        int i = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        long j13 = 0;
        long j14 = 0;
        long j15 = 0;
        while (i < size) {
            TimeSpan.Item<? extends U> item = timeSpan.getTotalLength().get(i);
            long j16 = j11;
            j11 = item.getAmount();
            U unit = item.getUnit();
            int i2 = size;
            long j17 = j9;
            if (unit instanceof CalendarUnit) {
                switch (AnonymousClass1.$SwitchMap$net$time4j$CalendarUnit[((CalendarUnit) CalendarUnit.class.cast(unit)).ordinal()]) {
                    case 1:
                        j8 = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 1000L), j8);
                        break;
                    case 2:
                        j8 = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 100L), j8);
                        break;
                    case 3:
                        j8 = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 10L), j8);
                        break;
                    case 4:
                        j8 = MathUtils.safeAdd(j11, j8);
                        break;
                    case 5:
                        safeAdd = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 3L), j13);
                        j13 = safeAdd;
                        break;
                    case 6:
                        safeAdd = MathUtils.safeAdd(j11, j13);
                        j13 = safeAdd;
                        break;
                    case 7:
                        j15 = j11;
                        break;
                    case 8:
                        j14 = j11;
                        break;
                    default:
                        throw new UnsupportedOperationException(unit.toString());
                }
            } else if (unit instanceof ClockUnit) {
                switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[((ClockUnit) ClockUnit.class.cast(unit)).ordinal()]) {
                    case 1:
                        j9 = j11;
                        j11 = j16;
                        continue;
                        i++;
                        size = i2;
                    case 2:
                        j10 = j11;
                        break;
                    case 3:
                        j9 = j17;
                        continue;
                        i++;
                        size = i2;
                    case 4:
                        j12 = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 1000000L), j12);
                        break;
                    case 5:
                        j12 = MathUtils.safeAdd(MathUtils.safeMultiply(j11, 1000L), j12);
                        break;
                    case 6:
                        j12 = MathUtils.safeAdd(j11, j12);
                        break;
                    default:
                        throw new UnsupportedOperationException(unit.toString());
                }
            } else {
                arrayList.add(TimeSpan.Item.of(j11, unit));
            }
            j11 = j16;
            j9 = j17;
            continue;
            i++;
            size = i2;
        }
        long j18 = j9;
        long j19 = j11;
        if ((j18 | j10 | j19 | j12) != 0) {
            long j20 = j12 % 1000000000;
            long safeAdd2 = MathUtils.safeAdd(j19, j12 / 1000000000);
            j4 = safeAdd2 % 60;
            long safeAdd3 = MathUtils.safeAdd(j10, safeAdd2 / 60);
            j3 = safeAdd3 % 60;
            j2 = MathUtils.safeAdd(j18, safeAdd3 / 60);
            if (this.mixed) {
                j = MathUtils.safeAdd(j14, j2 / 24);
                j5 = j20;
                j2 %= 24;
            } else {
                j = j14;
                j5 = j20;
            }
        } else {
            j = j14;
            j2 = 0;
            j3 = 0;
            j4 = 0;
            j5 = 0;
        }
        if ((j8 | j13 | j) != 0) {
            j7 = j4;
            long safeAdd4 = MathUtils.safeAdd(j8, j13 / 12);
            long j21 = j13 % 12;
            j6 = j3;
            long safeAdd5 = MathUtils.safeAdd(MathUtils.safeMultiply(j15, 7L), j);
            if (safeAdd4 != 0) {
                arrayList.add(TimeSpan.Item.of(safeAdd4, CalendarUnit.YEARS));
            }
            if (j21 != 0) {
                arrayList.add(TimeSpan.Item.of(j21, CalendarUnit.MONTHS));
            }
            if (safeAdd5 != 0) {
                arrayList.add(TimeSpan.Item.of(safeAdd5, CalendarUnit.DAYS));
            }
        } else {
            j6 = j3;
            j7 = j4;
            long j22 = j15;
            if (j22 != 0) {
                arrayList.add(TimeSpan.Item.of(j22, CalendarUnit.WEEKS));
            }
        }
        if (j2 != 0) {
            arrayList.add(TimeSpan.Item.of(j2, ClockUnit.HOURS));
        }
        if (j6 != 0) {
            arrayList.add(TimeSpan.Item.of(j6, ClockUnit.MINUTES));
        }
        if (j7 != 0) {
            arrayList.add(TimeSpan.Item.of(j7, ClockUnit.SECONDS));
        }
        long j23 = j5;
        if (j23 != 0) {
            arrayList.add(TimeSpan.Item.of(j23, ClockUnit.NANOS));
        }
        return new Duration<>(arrayList, timeSpan.isNegative());
    }

    /* renamed from: net.time4j.StdNormalizer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$CalendarUnit;
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
            int[] iArr2 = new int[CalendarUnit.values().length];
            $SwitchMap$net$time4j$CalendarUnit = iArr2;
            try {
                iArr2[CalendarUnit.MILLENNIA.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.CENTURIES.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DECADES.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.YEARS.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.QUARTERS.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.MONTHS.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.WEEKS.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DAYS.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    static int compare(ChronoUnit chronoUnit, ChronoUnit chronoUnit2) {
        int compare = Double.compare(chronoUnit2.getLength(), chronoUnit.getLength());
        if (compare != 0 || chronoUnit.equals(chronoUnit2)) {
            return compare;
        }
        throw new IllegalArgumentException("Mixing different units of same length not allowed.");
    }
}
