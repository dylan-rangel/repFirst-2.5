package net.time4j.format;

import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;

/* loaded from: classes3.dex */
public interface NumericalElement<V> extends ChronoElement<V> {
    int numerical(V v);

    boolean parseFromInt(ChronoEntity<?> chronoEntity, int i);

    int printToInt(V v, ChronoDisplay chronoDisplay, AttributeQuery attributeQuery);
}
