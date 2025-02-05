package net.time4j.format.expert;

import java.io.IOException;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
interface FormatProcessor<V> {
    ChronoElement<V> getElement();

    boolean isNumerical();

    void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z);

    int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException;

    FormatProcessor<V> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i);

    FormatProcessor<V> withElement(ChronoElement<V> chronoElement);
}
