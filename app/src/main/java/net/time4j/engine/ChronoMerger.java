package net.time4j.engine;

import java.util.Locale;
import net.time4j.base.TimeSource;

/* loaded from: classes3.dex */
public interface ChronoMerger<T> {
    T createFrom(TimeSource<?> timeSource, AttributeQuery attributeQuery);

    T createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2);

    int getDefaultPivotYear();

    StartOfDay getDefaultStartOfDay();

    String getFormatPattern(DisplayStyle displayStyle, Locale locale);

    ChronoDisplay preformat(T t, AttributeQuery attributeQuery);

    Chronology<?> preparser();
}
