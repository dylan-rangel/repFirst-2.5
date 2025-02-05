package net.time4j.engine;

import net.time4j.engine.ChronoEntity;

/* loaded from: classes3.dex */
public final class StdOperator<T extends ChronoEntity<T>> implements ChronoOperator<T> {
    private static final int CEILING_MODE = 4;
    private static final int DECREMENTING_MODE = 6;
    private static final int FLOOR_MODE = 3;
    private static final int INCREMENTING_MODE = 7;
    private static final int LENIENT_MODE = 5;
    private static final int MAX_MODE = 2;
    private static final int MIN_MODE = 1;
    private static final int NEW_VALUE_MODE = 0;
    private final ChronoElement<?> element;
    private final int mode;
    private final Object value;

    private StdOperator(int i, ChronoElement<?> chronoElement) {
        this(i, chronoElement, null);
    }

    private StdOperator(int i, ChronoElement<?> chronoElement, Object obj) {
        if (chronoElement == null) {
            throw new NullPointerException("Missing chronological element.");
        }
        this.mode = i;
        this.element = chronoElement;
        this.value = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoOperator
    public T apply(T t) {
        switch (this.mode) {
            case 0:
                return value(t, this.element, this.value, false);
            case 1:
                return min(t, this.element);
            case 2:
                return max(t, this.element);
            case 3:
                return floor(t, this.element);
            case 4:
                return ceiling(t, this.element);
            case 5:
                return value(t, this.element, this.value, true);
            case 6:
                return move(t, false);
            case 7:
                return move(t, true);
            default:
                throw new UnsupportedOperationException("Unknown mode: " + this.mode);
        }
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> minimized(ChronoElement<?> chronoElement) {
        return new StdOperator(1, chronoElement);
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> maximized(ChronoElement<?> chronoElement) {
        return new StdOperator(2, chronoElement);
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> decremented(ChronoElement<?> chronoElement) {
        return new StdOperator(6, chronoElement);
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> incremented(ChronoElement<?> chronoElement) {
        return new StdOperator(7, chronoElement);
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> atFloor(ChronoElement<?> chronoElement) {
        return new StdOperator(3, chronoElement);
    }

    public static <T extends ChronoEntity<T>> ChronoOperator<T> atCeiling(ChronoElement<?> chronoElement) {
        return new StdOperator(4, chronoElement);
    }

    public static <T extends ChronoEntity<T>, V> ChronoOperator<T> setLenient(V v, ChronoElement<V> chronoElement) {
        return new StdOperator(5, chronoElement, v);
    }

    public static <T extends ChronoEntity<T>, V> ChronoOperator<T> newValue(V v, ChronoElement<V> chronoElement) {
        return new StdOperator(0, chronoElement, v);
    }

    private <V> T min(ChronoEntity<T> chronoEntity, ChronoElement<V> chronoElement) {
        return chronoEntity.with((ChronoElement<ChronoElement<V>>) chronoElement, (ChronoElement<V>) chronoEntity.getMinimum(chronoElement));
    }

    private <V> T max(ChronoEntity<T> chronoEntity, ChronoElement<V> chronoElement) {
        return chronoEntity.with((ChronoElement<ChronoElement<V>>) chronoElement, (ChronoElement<V>) chronoEntity.getMaximum(chronoElement));
    }

    private <V> T floor(ChronoEntity<T> chronoEntity, ChronoElement<V> chronoElement) {
        T context = chronoEntity.getContext();
        while (true) {
            chronoElement = (ChronoElement<V>) context.getChronology().getRule(chronoElement).getChildAtFloor(context);
            if (chronoElement == null) {
                return context;
            }
            context = withFloor(context, chronoElement);
        }
    }

    private <V> T ceiling(ChronoEntity<T> chronoEntity, ChronoElement<V> chronoElement) {
        T context = chronoEntity.getContext();
        while (true) {
            chronoElement = (ChronoElement<V>) context.getChronology().getRule(chronoElement).getChildAtCeiling(context);
            if (chronoElement == null) {
                return context;
            }
            context = withCeiling(context, chronoElement);
        }
    }

    private <V> T value(ChronoEntity<T> chronoEntity, ChronoElement<V> chronoElement, Object obj, boolean z) {
        T context = chronoEntity.getContext();
        return context.getChronology().getRule(chronoElement).withValue2(context, chronoElement.getType().cast(obj), z);
    }

    private <V> T withFloor(T t, ChronoElement<V> chronoElement) {
        ElementRule<T, V> rule = t.getChronology().getRule(chronoElement);
        return rule.withValue2(t, rule.getMinimum(t), chronoElement.isLenient());
    }

    private <V> T withCeiling(T t, ChronoElement<V> chronoElement) {
        ElementRule<T, V> rule = t.getChronology().getRule(chronoElement);
        return rule.withValue2(t, rule.getMaximum(t), chronoElement.isLenient());
    }

    private T move(T t, boolean z) {
        if (t instanceof TimePoint) {
            return t.getChronology().getChronoType().cast(add((TimePoint) TimePoint.class.cast(t), this.element, z));
        }
        throw new ChronoException("Base units not supported by: " + t.getChronology().getChronoType());
    }

    private static <U, T extends TimePoint<U, T>> T add(TimePoint<U, T> timePoint, ChronoElement<?> chronoElement, boolean z) {
        U baseUnit = timePoint.getChronology().getBaseUnit(chronoElement);
        if (z) {
            return timePoint.plus(1L, baseUnit);
        }
        return timePoint.minus(1L, baseUnit);
    }
}
