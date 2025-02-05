package net.time4j.engine;

import java.util.Comparator;
import java.util.Locale;

/* loaded from: classes3.dex */
public interface ChronoElement<V> extends Comparator<ChronoDisplay> {
    int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2);

    V getDefaultMaximum();

    V getDefaultMinimum();

    String getDisplayName(Locale locale);

    char getSymbol();

    Class<V> getType();

    boolean isDateElement();

    boolean isLenient();

    boolean isTimeElement();

    String name();
}
