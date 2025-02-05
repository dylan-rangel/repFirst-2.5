package net.time4j.format.expert;

import java.io.IOException;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
enum IgnorableWhitespaceProcessor implements FormatProcessor<Void> {
    SINGLETON;

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<Void> getElement() {
        return null;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return this;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "{IGNORABLE_WHITE_SPACE}";
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> withElement(ChronoElement<Void> chronoElement) {
        return this;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        appendable.append(' ');
        return 1;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        int position = parseLog.getPosition();
        while (position < charSequence.length() && Character.isWhitespace(charSequence.charAt(position))) {
            position++;
        }
        parseLog.setPosition(position);
    }
}
