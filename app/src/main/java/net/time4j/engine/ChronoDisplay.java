package net.time4j.engine;

import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
public interface ChronoDisplay {
    boolean contains(ChronoElement<?> chronoElement);

    <V> V get(ChronoElement<V> chronoElement);

    int getInt(ChronoElement<Integer> chronoElement);

    <V> V getMaximum(ChronoElement<V> chronoElement);

    <V> V getMinimum(ChronoElement<V> chronoElement);

    TZID getTimezone();

    boolean hasTimezone();
}
