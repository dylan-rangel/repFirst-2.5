package net.time4j;

import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class ValueOperator<T> implements ChronoOperator<T> {
    private final ChronoOperator<T> delegate;
    private final Object value;

    private ValueOperator(ChronoOperator<T> chronoOperator, Object obj) {
        this.delegate = chronoOperator;
        this.value = obj;
    }

    static <T> ValueOperator of(ChronoOperator<T> chronoOperator, Object obj) {
        return new ValueOperator(chronoOperator, obj);
    }

    @Override // net.time4j.engine.ChronoOperator
    public T apply(T t) {
        return this.delegate.apply(t);
    }

    Object getValue() {
        return this.value;
    }
}
