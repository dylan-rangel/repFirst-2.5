package net.time4j.format.expert;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.internal.DualFormatElement;

/* loaded from: classes3.dex */
final class FormatStep {
    private final AttributeQuery fullAttrs;
    private final int lastOrBlockIndex;
    private final int level;
    private final boolean orMarker;
    private final int padLeft;
    private final int padRight;
    private final FormatProcessor<?> processor;
    private final int reserved;
    private final int section;
    private final AttributeSet sectionalAttrs;

    FormatStep(FormatProcessor<?> formatProcessor, int i, int i2, AttributeSet attributeSet) {
        this(formatProcessor, i, i2, attributeSet, null, 0, 0, 0, false, -1);
    }

    private FormatStep(FormatProcessor<?> formatProcessor, int i, int i2, AttributeSet attributeSet, AttributeQuery attributeQuery, int i3, int i4, int i5, boolean z, int i6) {
        if (formatProcessor == null) {
            throw new NullPointerException("Missing format processor.");
        }
        if (i < 0) {
            throw new IllegalArgumentException("Invalid level: " + i);
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid section: " + i2);
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("Reserved chars must not be negative: " + i3);
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("Invalid pad-width: " + i4);
        }
        if (i5 < 0) {
            throw new IllegalArgumentException("Invalid pad-width: " + i5);
        }
        this.processor = formatProcessor;
        this.level = i;
        this.section = i2;
        this.sectionalAttrs = attributeSet;
        this.fullAttrs = attributeQuery;
        this.reserved = i3;
        this.padLeft = i4;
        this.padRight = i5;
        this.orMarker = z;
        this.lastOrBlockIndex = i6;
    }

    int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        StringBuilder sb;
        int i;
        int i2;
        int length;
        if (!isPrinting(chronoDisplay)) {
            return 0;
        }
        AttributeQuery query = z ? this.fullAttrs : getQuery(attributeQuery);
        if (this.padLeft == 0 && this.padRight == 0) {
            return this.processor.print(chronoDisplay, appendable, query, set, z);
        }
        LinkedHashSet linkedHashSet = null;
        if (appendable instanceof StringBuilder) {
            sb = (StringBuilder) appendable;
            i = sb.length();
        } else {
            sb = new StringBuilder();
            i = -1;
        }
        if (!(appendable instanceof CharSequence) || set == null) {
            i2 = -1;
        } else {
            if (sb == appendable) {
                FormatProcessor<?> formatProcessor = this.processor;
                if ((formatProcessor instanceof CustomizedProcessor) || (formatProcessor instanceof StyleProcessor)) {
                    length = 0;
                    i2 = length;
                    linkedHashSet = new LinkedHashSet();
                }
            }
            length = ((CharSequence) appendable).length();
            i2 = length;
            linkedHashSet = new LinkedHashSet();
        }
        boolean isStrict = isStrict(query);
        char padChar = getPadChar(query);
        int length2 = sb.length();
        this.processor.print(chronoDisplay, sb, query, linkedHashSet, z);
        int length3 = sb.length() - length2;
        int i3 = this.padLeft;
        if (i3 <= 0) {
            if (isStrict && length3 > this.padRight) {
                throw new IllegalArgumentException(padExceeded());
            }
            if (i == -1) {
                appendable.append(sb);
            }
            while (length3 < this.padRight) {
                appendable.append(padChar);
                length3++;
            }
            if (i2 != -1) {
                for (ElementPosition elementPosition : linkedHashSet) {
                    set.add(new ElementPosition(elementPosition.getElement(), elementPosition.getStartIndex() + i2, elementPosition.getEndIndex() + i2));
                }
            }
            return length3;
        }
        if (isStrict && length3 > i3) {
            throw new IllegalArgumentException(padExceeded());
        }
        int i4 = length3;
        int i5 = 0;
        while (i4 < this.padLeft) {
            if (i == -1) {
                appendable.append(padChar);
            } else {
                sb.insert(i, padChar);
            }
            i4++;
            i5++;
        }
        if (i == -1) {
            appendable.append(sb);
        }
        if (i2 != -1) {
            int i6 = i2 + i5;
            for (ElementPosition elementPosition2 : linkedHashSet) {
                set.add(new ElementPosition(elementPosition2.getElement(), elementPosition2.getStartIndex() + i6, elementPosition2.getEndIndex() + i6));
            }
        }
        int i7 = this.padRight;
        if (i7 <= 0) {
            return i4;
        }
        if (isStrict && length3 > i7) {
            throw new IllegalArgumentException(padExceeded());
        }
        while (length3 < this.padRight) {
            appendable.append(padChar);
            length3++;
            i4++;
        }
        return i4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0089, code lost:
    
        r11 = r10.padRight;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x008b, code lost:
    
        if (r11 <= 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008e, code lost:
    
        if ((r15 + r0) == r11) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0090, code lost:
    
        r12.setError(r14 - r0, padMismatched());
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0098, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void parse(java.lang.CharSequence r11, net.time4j.format.expert.ParseLog r12, net.time4j.engine.AttributeQuery r13, net.time4j.format.expert.ParsedEntity<?> r14, boolean r15) {
        /*
            r10 = this;
            if (r15 == 0) goto L5
            net.time4j.engine.AttributeQuery r13 = r10.fullAttrs
            goto L9
        L5:
            net.time4j.engine.AttributeQuery r13 = r10.getQuery(r13)
        L9:
            r3 = r13
            int r13 = r10.padLeft
            if (r13 != 0) goto L1b
            int r13 = r10.padRight
            if (r13 != 0) goto L1b
            r0 = r10
            r1 = r11
            r2 = r12
            r4 = r14
            r5 = r15
            r0.doParse(r1, r2, r3, r4, r5)
            return
        L1b:
            boolean r13 = r10.isStrict(r3)
            char r6 = r10.getPadChar(r3)
            int r7 = r12.getPosition()
            int r8 = r11.length()
            r0 = r7
        L2c:
            if (r0 >= r8) goto L37
            char r1 = r11.charAt(r0)
            if (r1 != r6) goto L37
            int r0 = r0 + 1
            goto L2c
        L37:
            int r9 = r0 - r7
            if (r13 == 0) goto L47
            int r1 = r10.padLeft
            if (r9 <= r1) goto L47
            java.lang.String r11 = r10.padExceeded()
            r12.setError(r7, r11)
            return
        L47:
            r12.setPosition(r0)
            r0 = r10
            r1 = r11
            r2 = r12
            r4 = r14
            r5 = r15
            r0.doParse(r1, r2, r3, r4, r5)
            boolean r14 = r12.isError()
            if (r14 == 0) goto L59
            return
        L59:
            int r14 = r12.getPosition()
            int r15 = r14 - r7
            int r15 = r15 - r9
            if (r13 == 0) goto L71
            int r0 = r10.padLeft
            if (r0 <= 0) goto L71
            int r9 = r9 + r15
            if (r9 == r0) goto L71
            java.lang.String r11 = r10.padMismatched()
            r12.setError(r7, r11)
            return
        L71:
            r0 = 0
        L72:
            if (r14 >= r8) goto L87
            if (r13 == 0) goto L7c
            int r1 = r15 + r0
            int r2 = r10.padRight
            if (r1 >= r2) goto L87
        L7c:
            char r1 = r11.charAt(r14)
            if (r1 != r6) goto L87
            int r14 = r14 + 1
            int r0 = r0 + 1
            goto L72
        L87:
            if (r13 == 0) goto L99
            int r11 = r10.padRight
            if (r11 <= 0) goto L99
            int r15 = r15 + r0
            if (r15 == r11) goto L99
            int r14 = r14 - r0
            java.lang.String r11 = r10.padMismatched()
            r12.setError(r14, r11)
            return
        L99:
            r12.setPosition(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.FormatStep.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    int getLevel() {
        return this.level;
    }

    int getSection() {
        return this.section;
    }

    boolean isDecimal() {
        FormatProcessor<?> formatProcessor = this.processor;
        return (formatProcessor instanceof FractionProcessor) || (formatProcessor instanceof DecimalProcessor);
    }

    boolean isNumerical() {
        return this.processor.isNumerical();
    }

    FormatProcessor<?> getProcessor() {
        return this.processor;
    }

    FormatStep quickPath(ChronoFormatter<?> chronoFormatter) {
        AttributeSet attributes0 = chronoFormatter.getAttributes0();
        if (this.sectionalAttrs != null) {
            attributes0 = attributes0.withAttributes(new Attributes.Builder().setAll(attributes0.getAttributes()).setAll(this.sectionalAttrs.getAttributes()).build());
        }
        AttributeSet attributeSet = attributes0;
        return new FormatStep(this.processor.quickPath(chronoFormatter, attributeSet, this.reserved), this.level, this.section, this.sectionalAttrs, attributeSet, this.reserved, this.padLeft, this.padRight, this.orMarker, this.lastOrBlockIndex);
    }

    FormatStep updateElement(ChronoElement<?> chronoElement) {
        FormatProcessor<?> update = update(this.processor, chronoElement);
        return this.processor == update ? this : new FormatStep(update, this.level, this.section, this.sectionalAttrs, this.fullAttrs, this.reserved, this.padLeft, this.padRight, this.orMarker, this.lastOrBlockIndex);
    }

    FormatStep reserve(int i) {
        return new FormatStep(this.processor, this.level, this.section, this.sectionalAttrs, null, this.reserved + i, this.padLeft, this.padRight, this.orMarker, this.lastOrBlockIndex);
    }

    FormatStep pad(int i, int i2) {
        return new FormatStep(this.processor, this.level, this.section, this.sectionalAttrs, null, this.reserved, this.padLeft + i, this.padRight + i2, this.orMarker, this.lastOrBlockIndex);
    }

    FormatStep startNewOrBlock() {
        if (this.orMarker) {
            throw new IllegalStateException("Cannot start or-block twice.");
        }
        return new FormatStep(this.processor, this.level, this.section, this.sectionalAttrs, null, this.reserved, this.padLeft, this.padRight, true, -1);
    }

    FormatStep markLastOrBlock(int i) {
        if (!this.orMarker) {
            throw new IllegalStateException("This step is not starting an or-block.");
        }
        return new FormatStep(this.processor, this.level, this.section, this.sectionalAttrs, this.fullAttrs, this.reserved, this.padLeft, this.padRight, true, i);
    }

    boolean isNewOrBlockStarted() {
        return this.orMarker;
    }

    int skipTrailingOrBlocks() {
        return this.lastOrBlockIndex;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FormatStep)) {
            return false;
        }
        FormatStep formatStep = (FormatStep) obj;
        return this.processor.equals(formatStep.processor) && this.level == formatStep.level && this.section == formatStep.section && isEqual(this.sectionalAttrs, formatStep.sectionalAttrs) && isEqual(this.fullAttrs, formatStep.fullAttrs) && this.reserved == formatStep.reserved && this.padLeft == formatStep.padLeft && this.padRight == formatStep.padRight && this.orMarker == formatStep.orMarker && this.lastOrBlockIndex == formatStep.lastOrBlockIndex;
    }

    public int hashCode() {
        int hashCode = this.processor.hashCode() * 7;
        AttributeSet attributeSet = this.sectionalAttrs;
        return hashCode + ((attributeSet == null ? 0 : attributeSet.hashCode()) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[processor=");
        sb.append(this.processor);
        sb.append(", level=");
        sb.append(this.level);
        sb.append(", section=");
        sb.append(this.section);
        if (this.sectionalAttrs != null) {
            sb.append(", attributes=");
            sb.append(this.sectionalAttrs);
        }
        sb.append(", reserved=");
        sb.append(this.reserved);
        sb.append(", pad-left=");
        sb.append(this.padLeft);
        sb.append(", pad-right=");
        sb.append(this.padRight);
        if (this.orMarker) {
            sb.append(", or-block-started");
        }
        sb.append(']');
        return sb.toString();
    }

    private AttributeQuery getQuery(AttributeQuery attributeQuery) {
        AttributeSet attributeSet = this.sectionalAttrs;
        return attributeSet == null ? attributeQuery : new MergedAttributes(attributeSet, attributeQuery);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <V> FormatProcessor<V> update(FormatProcessor<V> formatProcessor, ChronoElement<?> chronoElement) {
        if (formatProcessor.getElement() == null) {
            return formatProcessor;
        }
        if (formatProcessor.getElement().getType() != chronoElement.getType() && !(chronoElement instanceof DualFormatElement)) {
            throw new IllegalArgumentException("Cannot change element value type: " + chronoElement.name());
        }
        return formatProcessor.withElement(chronoElement);
    }

    private static boolean isEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    private void doParse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        int position = parseLog.getPosition();
        try {
            this.processor.parse(charSequence, parseLog, attributeQuery, parsedEntity, z);
        } catch (RuntimeException e) {
            parseLog.setError(position, e.getMessage());
        }
    }

    private boolean isStrict(AttributeQuery attributeQuery) {
        return ((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isStrict();
    }

    private char getPadChar(AttributeQuery attributeQuery) {
        return ((Character) attributeQuery.get(Attributes.PAD_CHAR, ' ')).charValue();
    }

    private String padExceeded() {
        return "Pad width exceeded: " + this.processor.getElement().name();
    }

    private String padMismatched() {
        return "Pad width mismatched: " + this.processor.getElement().name();
    }

    private boolean isPrinting(ChronoDisplay chronoDisplay) {
        ChronoCondition<ChronoDisplay> condition;
        AttributeSet attributeSet = this.sectionalAttrs;
        return attributeSet == null || (condition = attributeSet.getCondition()) == null || condition.test(chronoDisplay);
    }
}
