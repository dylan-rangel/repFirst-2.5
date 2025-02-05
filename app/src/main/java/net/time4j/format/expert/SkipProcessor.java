package net.time4j.format.expert;

import java.io.IOException;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
final class SkipProcessor implements FormatProcessor<Void> {
    private final ChronoCondition<Character> condition;
    private final int count;

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<Void> getElement() {
        return null;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        return 0;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return this;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> withElement(ChronoElement<Void> chronoElement) {
        return this;
    }

    SkipProcessor(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Must not be negative: " + i);
        }
        this.condition = null;
        this.count = i;
    }

    SkipProcessor(ChronoCondition<Character> chronoCondition, int i) {
        if (chronoCondition == null) {
            throw new NullPointerException("Missing condition for unparseable chars.");
        }
        if (i < 1) {
            throw new IllegalArgumentException("Must be positive: " + i);
        }
        this.condition = chronoCondition;
        this.count = i;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        int i;
        int i2;
        int position = parseLog.getPosition();
        int length = charSequence.length();
        if (this.condition == null) {
            i = length - this.count;
        } else {
            int i3 = position;
            for (int i4 = 0; i4 < this.count && (i2 = i4 + position) < length && this.condition.test(Character.valueOf(charSequence.charAt(i2))); i4++) {
                i3++;
            }
            i = i3;
        }
        int min = Math.min(Math.max(i, 0), length);
        if (min > position) {
            parseLog.setPosition(min);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SkipProcessor)) {
            return false;
        }
        SkipProcessor skipProcessor = (SkipProcessor) obj;
        if (this.count == skipProcessor.count) {
            ChronoCondition<Character> chronoCondition = this.condition;
            ChronoCondition<Character> chronoCondition2 = skipProcessor.condition;
            if (chronoCondition == null) {
                if (chronoCondition2 == null) {
                    return true;
                }
            } else if (chronoCondition.equals(chronoCondition2)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        ChronoCondition<Character> chronoCondition = this.condition;
        if (chronoCondition == null) {
            return this.count;
        }
        return chronoCondition.hashCode() ^ (~this.count);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        if (this.condition == null) {
            sb.append("[keepRemainingChars=");
            sb.append(this.count);
        } else {
            sb.append("[condition=");
            sb.append(this.condition);
            sb.append(", maxIterations=");
            sb.append(this.count);
        }
        sb.append(']');
        return sb.toString();
    }
}
