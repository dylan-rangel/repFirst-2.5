package net.time4j.format.expert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;

/* loaded from: classes3.dex */
final class FractionProcessor implements FormatProcessor<Integer> {
    private static final int MRD_MINUS_1 = 999999999;
    private final FormatProcessor<Void> decimalSeparator;
    private final ChronoElement<Integer> element;
    private final boolean fixedWidth;
    private final Leniency lenientMode;
    private final int maxDigits;
    private final int minDigits;
    private final char zeroDigit;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return true;
    }

    FractionProcessor(ChronoElement<Integer> chronoElement, int i, int i2, boolean z) {
        this.element = chronoElement;
        this.minDigits = i;
        this.maxDigits = i2;
        this.fixedWidth = !z && i == i2;
        this.decimalSeparator = z ? new LiteralProcessor(Attributes.DECIMAL_SEPARATOR) : null;
        if (chronoElement == null) {
            throw new NullPointerException("Missing element.");
        }
        if (i < 0) {
            throw new IllegalArgumentException("Negative min digits: " + i);
        }
        if (i > i2) {
            throw new IllegalArgumentException("Max smaller than min: " + i2 + " < " + i);
        }
        if (i > 9) {
            throw new IllegalArgumentException("Min digits out of range: " + i);
        }
        if (i2 > 9) {
            throw new IllegalArgumentException("Max digits out of range: " + i2);
        }
        this.zeroDigit = '0';
        this.lenientMode = Leniency.SMART;
    }

    private FractionProcessor(FormatProcessor<Void> formatProcessor, ChronoElement<Integer> chronoElement, int i, int i2, boolean z, char c, Leniency leniency) {
        this.decimalSeparator = formatProcessor;
        this.element = chronoElement;
        this.minDigits = i;
        this.maxDigits = i2;
        this.fixedWidth = z;
        this.zeroDigit = c;
        this.lenientMode = leniency;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        char charValue;
        int i;
        int i2;
        BigDecimal decimal = toDecimal((Number) chronoDisplay.get(this.element));
        BigDecimal decimal2 = toDecimal((Number) chronoDisplay.getMinimum(this.element));
        BigDecimal decimal3 = toDecimal((Number) chronoDisplay.getMaximum(this.element));
        if (decimal.compareTo(decimal3) > 0) {
            decimal = decimal3;
        }
        BigDecimal divide = decimal.subtract(decimal2).divide(decimal3.subtract(decimal2).add(BigDecimal.ONE), 9, RoundingMode.FLOOR);
        BigDecimal stripTrailingZeros = divide.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : divide.stripTrailingZeros();
        if (z) {
            charValue = this.zeroDigit;
        } else {
            charValue = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue();
        }
        char c = charValue;
        int length = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        int i3 = 0;
        if (stripTrailingZeros.scale() == 0) {
            if (this.minDigits > 0) {
                if (hasDecimalSeparator()) {
                    this.decimalSeparator.print(chronoDisplay, appendable, attributeQuery, set, z);
                    i = 1;
                } else {
                    i = 0;
                }
                while (true) {
                    i2 = this.minDigits;
                    if (i3 >= i2) {
                        break;
                    }
                    appendable.append(c);
                    i3++;
                }
                i3 = i + i2;
            }
        } else {
            if (hasDecimalSeparator()) {
                this.decimalSeparator.print(chronoDisplay, appendable, attributeQuery, set, z);
                i3 = 1;
            }
            String plainString = stripTrailingZeros.setScale(Math.min(Math.max(stripTrailingZeros.scale(), this.minDigits), this.maxDigits), RoundingMode.FLOOR).toPlainString();
            int i4 = c - '0';
            int length2 = plainString.length();
            for (int i5 = 2; i5 < length2; i5++) {
                appendable.append((char) (plainString.charAt(i5) + i4));
                i3++;
            }
        }
        if (length != -1 && i3 > 1 && set != null) {
            set.add(new ElementPosition(this.element, length + 1, length + i3));
        }
        return i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012a  */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r20, net.time4j.format.expert.ParseLog r21, net.time4j.engine.AttributeQuery r22, net.time4j.format.expert.ParsedEntity<?> r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.FractionProcessor.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FractionProcessor)) {
            return false;
        }
        FractionProcessor fractionProcessor = (FractionProcessor) obj;
        return this.element.equals(fractionProcessor.element) && this.minDigits == fractionProcessor.minDigits && this.maxDigits == fractionProcessor.maxDigits && hasDecimalSeparator() == fractionProcessor.hasDecimalSeparator();
    }

    public int hashCode() {
        return (this.element.hashCode() * 7) + ((this.minDigits + (this.maxDigits * 10)) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[element=");
        sb.append(this.element.name());
        sb.append(", min-digits=");
        sb.append(this.minDigits);
        sb.append(", max-digits=");
        sb.append(this.maxDigits);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<Integer> getElement() {
        return this.element;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Integer> withElement(ChronoElement<Integer> chronoElement) {
        return this.element == chronoElement ? this : new FractionProcessor(chronoElement, this.minDigits, this.maxDigits, hasDecimalSeparator());
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Integer> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return new FractionProcessor(this.decimalSeparator, this.element, this.minDigits, this.maxDigits, this.fixedWidth, ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue(), (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART));
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoEntity<?>] */
    ChronoEntity<?> update(ChronoEntity<?> chronoEntity, ChronoEntity<?> chronoEntity2) {
        if (!chronoEntity2.contains(FractionalElement.FRACTION)) {
            return chronoEntity;
        }
        int realValue = getRealValue((BigDecimal) chronoEntity2.get(FractionalElement.FRACTION), ((Integer) chronoEntity.getMinimum(this.element)).intValue(), ((Integer) chronoEntity.getMaximum(this.element)).intValue());
        chronoEntity2.with(FractionalElement.FRACTION, (FractionalElement) null);
        chronoEntity2.with(this.element, realValue);
        return chronoEntity.with(this.element, realValue);
    }

    private int getRealValue(BigDecimal bigDecimal, int i, int i2) {
        BigDecimal valueOf = BigDecimal.valueOf(i);
        return bigDecimal.multiply(BigDecimal.valueOf(i2).subtract(valueOf).add(BigDecimal.ONE)).setScale(0, RoundingMode.FLOOR).add(valueOf).intValueExact();
    }

    private static BigDecimal toDecimal(Number number) {
        return BigDecimal.valueOf(number.longValue());
    }

    private boolean hasDecimalSeparator() {
        return this.decimalSeparator != null;
    }
}
