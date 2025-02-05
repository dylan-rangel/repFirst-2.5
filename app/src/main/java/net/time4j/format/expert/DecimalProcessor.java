package net.time4j.format.expert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;

/* loaded from: classes3.dex */
final class DecimalProcessor implements FormatProcessor<BigDecimal> {
    private final FormatProcessor<Void> decimalSeparator;
    private final ChronoElement<BigDecimal> element;
    private final Leniency lenientMode;
    private final int precision;
    private final int protectedLength;
    private final int scale;
    private final char zeroDigit;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return true;
    }

    DecimalProcessor(ChronoElement<BigDecimal> chronoElement, int i, int i2) {
        this.decimalSeparator = new LiteralProcessor(Attributes.DECIMAL_SEPARATOR);
        this.element = chronoElement;
        this.precision = i;
        this.scale = i2;
        if (chronoElement == null) {
            throw new NullPointerException("Missing element.");
        }
        if (i < 2) {
            throw new IllegalArgumentException("Precision must be >= 2: " + i);
        }
        if (i2 < i) {
            if (i2 < 1) {
                throw new IllegalArgumentException("Scale must be bigger than zero.");
            }
            this.zeroDigit = '0';
            this.lenientMode = Leniency.SMART;
            this.protectedLength = 0;
            return;
        }
        throw new IllegalArgumentException("Precision must be bigger than scale: " + i + "," + i2);
    }

    private DecimalProcessor(FormatProcessor<Void> formatProcessor, ChronoElement<BigDecimal> chronoElement, int i, int i2, char c, Leniency leniency, int i3) {
        this.decimalSeparator = formatProcessor;
        this.element = chronoElement;
        this.precision = i;
        this.scale = i2;
        this.zeroDigit = c;
        this.lenientMode = leniency;
        this.protectedLength = i3;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        char charValue;
        String plainString = ((BigDecimal) chronoDisplay.get(this.element)).setScale(this.scale, RoundingMode.FLOOR).toPlainString();
        int length = plainString.length();
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (plainString.charAt(i4) == '.') {
                i = i4;
            } else if (i >= 0) {
                i3++;
            } else {
                i2++;
            }
        }
        int i5 = this.precision;
        int i6 = (i5 - this.scale) - i2;
        if (i6 < 0) {
            throw new IllegalArgumentException("Integer part of element value exceeds fixed format width: " + plainString);
        }
        StringBuilder sb = new StringBuilder(i5 + 1);
        for (int i7 = 0; i7 < i6; i7++) {
            sb.append('0');
        }
        for (int i8 = 0; i8 < i2; i8++) {
            sb.append(plainString.charAt(i8));
        }
        this.decimalSeparator.print(chronoDisplay, sb, attributeQuery, set, z);
        for (int i9 = 0; i9 < i3; i9++) {
            sb.append(plainString.charAt(i2 + 1 + i9));
        }
        for (int i10 = 0; i10 < this.scale - i3; i10++) {
            sb.append('0');
        }
        String sb2 = sb.toString();
        if (z) {
            charValue = this.zeroDigit;
        } else {
            charValue = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue();
        }
        if (charValue != '0') {
            int i11 = charValue - '0';
            char[] charArray = sb2.toCharArray();
            for (int i12 = 0; i12 < charArray.length; i12++) {
                char c = charArray[i12];
                if (c >= '0' && c <= '9') {
                    charArray[i12] = (char) (c + i11);
                }
            }
            sb2 = new String(charArray);
        }
        int length2 = sb2.length();
        int length3 = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        appendable.append(sb2);
        if (length3 != -1 && length2 > 0 && set != null) {
            set.add(new ElementPosition(this.element, length3, length3 + length2));
        }
        return length2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ef, code lost:
    
        r24.setError(r6, "Fraction part expected.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00f4, code lost:
    
        return;
     */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r23, net.time4j.format.expert.ParseLog r24, net.time4j.engine.AttributeQuery r25, net.time4j.format.expert.ParsedEntity<?> r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.DecimalProcessor.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DecimalProcessor)) {
            return false;
        }
        DecimalProcessor decimalProcessor = (DecimalProcessor) obj;
        return this.element.equals(decimalProcessor.element) && this.precision == decimalProcessor.precision && this.scale == decimalProcessor.scale;
    }

    public int hashCode() {
        return (this.element.hashCode() * 7) + ((this.scale + (this.precision * 10)) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[element=");
        sb.append(this.element.name());
        sb.append(", precision=");
        sb.append(this.precision);
        sb.append(", scale=");
        sb.append(this.scale);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<BigDecimal> getElement() {
        return this.element;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<BigDecimal> withElement(ChronoElement<BigDecimal> chronoElement) {
        return this.element == chronoElement ? this : new DecimalProcessor(chronoElement, this.precision, this.scale);
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<BigDecimal> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return new DecimalProcessor(this.decimalSeparator, this.element, this.precision, this.scale, ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue(), (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART), ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue());
    }
}
