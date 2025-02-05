package net.time4j.format.expert;

import java.io.IOException;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoFunction;

/* loaded from: classes3.dex */
public interface ChronoPrinter<T> {
    <R> R print(T t, Appendable appendable, AttributeQuery attributeQuery, ChronoFunction<ChronoDisplay, R> chronoFunction) throws IOException;
}
