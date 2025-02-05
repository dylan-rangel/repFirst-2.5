package net.time4j.format;

import java.io.IOException;
import java.text.ParsePosition;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoException;

/* loaded from: classes3.dex */
public interface TextElement<V> extends ChronoElement<V> {
    V parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery);

    void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException;
}
