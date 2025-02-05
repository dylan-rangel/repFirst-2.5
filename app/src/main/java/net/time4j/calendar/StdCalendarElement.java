package net.time4j.calendar;

import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
public interface StdCalendarElement<V, T> extends ChronoElement<V> {
    ChronoOperator<T> atCeiling();

    ChronoOperator<T> atFloor();

    ChronoOperator<T> decremented();

    ChronoOperator<T> incremented();

    ChronoOperator<T> maximized();

    ChronoOperator<T> minimized();
}
