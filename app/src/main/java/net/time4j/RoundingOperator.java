package net.time4j;

import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class RoundingOperator<T extends ChronoEntity<T>> implements ChronoOperator<T> {
    private final ProportionalElement<?, T> element;
    private final boolean longBased;
    private final double stepwidth;
    private final Boolean up;

    RoundingOperator(ProportionalElement<?, T> proportionalElement, Boolean bool, int i) {
        this.element = proportionalElement;
        this.up = bool;
        this.stepwidth = i;
        this.longBased = proportionalElement.getType().equals(Long.class);
    }

    @Override // net.time4j.engine.ChronoOperator
    public T apply(T t) {
        double floor;
        double d;
        double d2;
        Number valueOf;
        double doubleValue = ((Number) t.get(this.element)).doubleValue();
        Boolean bool = this.up;
        if (bool == null) {
            double ceil = Math.ceil(doubleValue / this.stepwidth);
            double d3 = this.stepwidth;
            d2 = ceil * d3;
            double floor2 = Math.floor(doubleValue / d3) * this.stepwidth;
            if (doubleValue - floor2 < d2 - doubleValue) {
                d2 = floor2;
            }
        } else {
            if (bool.booleanValue()) {
                floor = Math.ceil(doubleValue / this.stepwidth);
                d = this.stepwidth;
            } else {
                floor = Math.floor(doubleValue / this.stepwidth);
                d = this.stepwidth;
            }
            d2 = d * floor;
        }
        if (this.longBased) {
            valueOf = Long.valueOf((long) d2);
        } else {
            valueOf = Integer.valueOf((int) d2);
        }
        return (T) t.with(lenient(this.element, valueOf));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <V extends Number, T extends ChronoEntity<T>> ChronoOperator<T> lenient(ProportionalElement<V, T> proportionalElement, Number number) {
        return proportionalElement.setLenient((Number) proportionalElement.getType().cast(number));
    }
}
