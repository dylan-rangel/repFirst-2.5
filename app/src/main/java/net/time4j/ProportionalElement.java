package net.time4j;

import java.lang.Number;
import java.math.BigDecimal;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
public interface ProportionalElement<V extends Number, T> extends AdjustableElement<V, T> {
    ChronoFunction<ChronoEntity<?>, BigDecimal> ratio();

    ChronoOperator<T> roundedDown(int i);

    ChronoOperator<T> roundedHalf(int i);

    ChronoOperator<T> roundedUp(int i);

    ElementOperator<T> setLenient(V v);
}
