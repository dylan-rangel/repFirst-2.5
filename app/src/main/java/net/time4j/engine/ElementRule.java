package net.time4j.engine;

/* loaded from: classes3.dex */
public interface ElementRule<T, V> {
    ChronoElement<?> getChildAtCeiling(T t);

    ChronoElement<?> getChildAtFloor(T t);

    V getMaximum(T t);

    V getMinimum(T t);

    V getValue(T t);

    boolean isValid(T t, V v);

    T withValue(T t, V v, boolean z);
}
