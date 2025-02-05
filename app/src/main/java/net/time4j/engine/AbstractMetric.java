package net.time4j.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.time4j.base.MathUtils;
import net.time4j.engine.AbstractDuration;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.TimeSpan;

/* loaded from: classes3.dex */
public abstract class AbstractMetric<U extends ChronoUnit, P extends AbstractDuration<U>> implements TimeMetric<U, P>, Comparator<U> {
    private static final double LENGTH_OF_FORTNIGHT = 1209600.0d;
    private static final int MIO = 1000000;
    private final boolean normalizing;
    private final List<U> sortedUnits;

    protected abstract P createEmptyTimeSpan();

    protected abstract P createTimeSpan(List<TimeSpan.Item<U>> list, boolean z);

    protected TimeSpan.Item<U> resolve(TimeSpan.Item<U> item) {
        return item;
    }

    protected AbstractMetric(boolean z, U... uArr) {
        this(Arrays.asList(uArr), z);
    }

    protected AbstractMetric(boolean z, Collection<? extends U> collection) {
        this(new ArrayList(collection), z);
    }

    private AbstractMetric(List<U> list, boolean z) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Missing units.");
        }
        Collections.sort(list, this);
        int i = 0;
        int size = list.size();
        while (i < size) {
            U u = list.get(i);
            i++;
            for (int i2 = i; i2 < size; i2++) {
                if (u.equals(list.get(i2))) {
                    throw new IllegalArgumentException("Duplicate unit: " + u);
                }
            }
        }
        this.sortedUnits = Collections.unmodifiableList(list);
        this.normalizing = z;
    }

    @Override // java.util.Comparator
    public int compare(U u, U u2) {
        return Double.compare(u2.getLength(), u.getLength());
    }

    @Override // net.time4j.engine.TimeMetric
    public <T extends TimePoint<? super U, T>> P between(T t, T t2) {
        return between(t, t2, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public <T extends TimePoint<? super U, T>> P between(T t, T t2, int i) {
        T t3;
        boolean z;
        T t4 = t2;
        if (t4.equals(t)) {
            return createEmptyTimeSpan();
        }
        int i2 = 0;
        if (t.compareTo(t2) > 0) {
            t3 = t;
            z = true;
        } else {
            t3 = t4;
            z = false;
            t4 = t;
        }
        List<TimeSpan.Item<U>> arrayList = new ArrayList<>(10);
        TimeAxis<? super U, T> chronology = t.getChronology();
        int size = this.sortedUnits.size();
        while (i2 < size) {
            U u = this.sortedUnits.get(i2);
            if (getLength(chronology, u) >= 1.0d || i2 >= size - 1) {
                int i3 = i2 + 1;
                long j = 1;
                while (i3 < size) {
                    U u2 = this.sortedUnits.get(i3);
                    j *= getFactor(chronology, u, u2);
                    if (j >= 1000000 || !chronology.isConvertible(u, u2)) {
                        break;
                    }
                    i3++;
                    u = u2;
                }
                i2 = i3 - 1;
                long until = t4.until(t3, u);
                if (until < 0) {
                    throw new IllegalStateException("Implementation error: Cannot compute timespan due to illegal negative timespan amounts.");
                }
                for (long j2 = 0; until > j2; j2 = 0) {
                    TimePoint plus = t4.plus(until, u);
                    if (i2 > i || i2 == size - 1 || plus.minus(until, u).equals(t4)) {
                        arrayList.add(resolve(TimeSpan.Item.of(until, u)));
                        t4 = plus;
                        break;
                    }
                    until--;
                }
            }
            i2++;
        }
        if (this.normalizing) {
            normalize(chronology, this.sortedUnits, arrayList);
        }
        return createTimeSpan(arrayList, z);
    }

    @Override // net.time4j.engine.TimeMetric
    public TimeMetric<U, P> reversible() {
        return new ReversalMetric();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T extends TimePoint<? super U, T>> void normalize(TimeAxis<? super U, T> timeAxis, List<U> list, List<TimeSpan.Item<U>> list2) {
        TimeSpan.Item item;
        Comparator<? super Object> unitComparator = timeAxis.unitComparator();
        for (int size = list.size() - 1; size >= 0; size--) {
            if (size > 0) {
                U u = list.get(size);
                U u2 = list.get(size - 1);
                long factor = getFactor(timeAxis, u2, u);
                if (factor < 1000000 && timeAxis.isConvertible(u2, u) && (item = getItem(list2, u)) != null) {
                    long amount = item.getAmount();
                    long j = amount / factor;
                    if (j > 0) {
                        long j2 = amount % factor;
                        if (j2 == 0) {
                            removeItem(list2, u);
                        } else {
                            putItem(list2, unitComparator, j2, u);
                        }
                        TimeSpan.Item item2 = getItem(list2, u2);
                        if (item2 == null) {
                            putItem(list2, unitComparator, j, u2);
                        } else {
                            putItem(list2, unitComparator, MathUtils.safeAdd(item2.getAmount(), j), u2);
                        }
                    }
                }
            }
        }
    }

    private static <U> TimeSpan.Item<U> getItem(List<TimeSpan.Item<U>> list, U u) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            TimeSpan.Item<U> item = list.get(i);
            if (item.getUnit().equals(u)) {
                return item;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <U> void putItem(List<TimeSpan.Item<U>> list, Comparator<? super U> comparator, long j, U u) {
        TimeSpan.Item<U> of = TimeSpan.Item.of(j, u);
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            U unit = list.get(i2).getUnit();
            if (unit.equals(u)) {
                list.set(i2, of);
                return;
            }
            if (i == i2 && comparator.compare(unit, u) < 0) {
                i++;
            }
        }
        list.add(i, of);
    }

    private static <U> void removeItem(List<TimeSpan.Item<U>> list, U u) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).getUnit().equals(u)) {
                list.remove(i);
                return;
            }
        }
    }

    private <T extends TimePoint<? super U, T>> long getFactor(TimeAxis<? super U, T> timeAxis, U u, U u2) {
        return Math.round(getLength(timeAxis, u) / getLength(timeAxis, u2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T extends TimePoint<? super U, T>> double getLength(TimeAxis<? super U, T> timeAxis, U u) {
        return timeAxis.getLength(u);
    }

    private static class ReversalMetric<U extends ChronoUnit, P extends AbstractDuration<U>> implements TimeMetric<U, P> {
        private final AbstractMetric<U, P> delegate;
        private final int monthIndex;

        @Override // net.time4j.engine.TimeMetric
        public TimeMetric<U, P> reversible() {
            return this;
        }

        private ReversalMetric(AbstractMetric<U, P> abstractMetric) {
            this.delegate = abstractMetric;
            int size = ((AbstractMetric) abstractMetric).sortedUnits.size() - 1;
            while (true) {
                if (size < 0) {
                    size = -1;
                    break;
                } else if (Double.compare(((ChronoUnit) ((AbstractMetric) this.delegate).sortedUnits.get(size)).getLength(), AbstractMetric.LENGTH_OF_FORTNIGHT) > 0) {
                    break;
                } else {
                    size--;
                }
            }
            this.monthIndex = size;
        }

        @Override // net.time4j.engine.TimeMetric
        public <T extends TimePoint<? super U, T>> P between(T t, T t2) {
            return (P) this.delegate.between(t, t2, this.monthIndex);
        }
    }
}
