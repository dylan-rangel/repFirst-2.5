package net.time4j.format.expert;

import java.io.IOException;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSystem;

/* loaded from: classes3.dex */
class NumberProcessor<V> implements FormatProcessor<V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] THRESHOLDS = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    private final ChronoElement<V> element;
    private final boolean fixedInt;
    private final boolean fixedWidth;
    private final Leniency lenientMode;
    private final int maxDigits;
    private final int minDigits;
    private final NumberSystem numberSystem;
    private final int protectedLength;
    private final boolean protectedMode;
    private final int reserved;
    private final int scaleOfNumsys;
    private final SignPolicy signPolicy;
    private final boolean yearOfEra;
    private final char zeroDigit;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return true;
    }

    NumberProcessor(ChronoElement<V> chronoElement, boolean z, int i, int i2, SignPolicy signPolicy, boolean z2) {
        this(chronoElement, z, i, i2, signPolicy, z2, 0, '0', NumberSystem.ARABIC, Leniency.SMART, 0, false);
    }

    private NumberProcessor(ChronoElement<V> chronoElement, boolean z, int i, int i2, SignPolicy signPolicy, boolean z2, int i3, char c, NumberSystem numberSystem, Leniency leniency, int i4, boolean z3) {
        this.element = chronoElement;
        this.fixedWidth = z;
        this.minDigits = i;
        this.maxDigits = i2;
        this.signPolicy = signPolicy;
        this.protectedMode = z2;
        this.fixedInt = z3;
        if (chronoElement == null) {
            throw new NullPointerException("Missing element.");
        }
        if (signPolicy == null) {
            throw new NullPointerException("Missing sign policy.");
        }
        if (i < 1) {
            throw new IllegalArgumentException("Not positive: " + i);
        }
        if (i > i2) {
            throw new IllegalArgumentException("Max smaller than min: " + i2 + " < " + i);
        }
        if (z && i != i2) {
            throw new IllegalArgumentException("Variable width in fixed-width-mode: " + i2 + " != " + i);
        }
        if (z && signPolicy != SignPolicy.SHOW_NEVER) {
            throw new IllegalArgumentException("Sign policy must be SHOW_NEVER in fixed-width-mode.");
        }
        int scale = getScale(numberSystem);
        if (numberSystem.isDecimal()) {
            if (i > scale) {
                throw new IllegalArgumentException("Min digits out of range: " + i);
            }
            if (i2 > scale) {
                throw new IllegalArgumentException("Max digits out of range: " + i2);
            }
        }
        this.yearOfEra = chronoElement.name().equals("YEAR_OF_ERA");
        this.reserved = i3;
        this.zeroDigit = c;
        this.numberSystem = numberSystem;
        this.lenientMode = leniency;
        this.protectedLength = i4;
        this.scaleOfNumsys = scale;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x02e0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0285  */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int print(net.time4j.engine.ChronoDisplay r24, java.lang.Appendable r25, net.time4j.engine.AttributeQuery r26, java.util.Set<net.time4j.format.expert.ElementPosition> r27, boolean r28) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 805
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.NumberProcessor.print(net.time4j.engine.ChronoDisplay, java.lang.Appendable, net.time4j.engine.AttributeQuery, java.util.Set, boolean):int");
    }

    /* renamed from: net.time4j.format.expert.NumberProcessor$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$expert$SignPolicy;

        static {
            int[] iArr = new int[SignPolicy.values().length];
            $SwitchMap$net$time4j$format$expert$SignPolicy = iArr;
            try {
                iArr[SignPolicy.SHOW_ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$expert$SignPolicy[SignPolicy.SHOW_WHEN_BIG_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0374  */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r26, net.time4j.format.expert.ParseLog r27, net.time4j.engine.AttributeQuery r28, net.time4j.format.expert.ParsedEntity<?> r29, boolean r30) {
        /*
            Method dump skipped, instructions count: 1012
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.NumberProcessor.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NumberProcessor)) {
            return false;
        }
        NumberProcessor numberProcessor = (NumberProcessor) obj;
        return this.element.equals(numberProcessor.element) && this.fixedWidth == numberProcessor.fixedWidth && this.minDigits == numberProcessor.minDigits && this.maxDigits == numberProcessor.maxDigits && this.signPolicy == numberProcessor.signPolicy && this.protectedMode == numberProcessor.protectedMode;
    }

    public int hashCode() {
        return (this.element.hashCode() * 7) + ((this.minDigits + (this.maxDigits * 10)) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[element=");
        sb.append(this.element.name());
        sb.append(", fixed-width-mode=");
        sb.append(this.fixedWidth);
        sb.append(", min-digits=");
        sb.append(this.minDigits);
        sb.append(", max-digits=");
        sb.append(this.maxDigits);
        sb.append(", sign-policy=");
        sb.append(this.signPolicy);
        sb.append(", protected-mode=");
        sb.append(this.protectedMode);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<V> getElement() {
        return this.element;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<V> withElement(ChronoElement<V> chronoElement) {
        return (this.protectedMode || this.element == chronoElement) ? this : new NumberProcessor(chronoElement, this.fixedWidth, this.minDigits, this.maxDigits, this.signPolicy, false);
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<V> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        char c;
        char charAt;
        NumberSystem numberSystem = (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ARABIC);
        if (attributeQuery.contains(Attributes.ZERO_DIGIT)) {
            charAt = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT)).charValue();
        } else {
            if (!numberSystem.isDecimal()) {
                c = '0';
                int intValue = ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue();
                return new NumberProcessor(this.element, this.fixedWidth, this.minDigits, this.maxDigits, this.signPolicy, this.protectedMode, i, c, numberSystem, (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART), intValue, numberSystem != NumberSystem.ARABIC && c == '0' && this.fixedWidth && intValue == 0 && this.element.getType() == Integer.class && !this.yearOfEra);
            }
            charAt = numberSystem.getDigits().charAt(0);
        }
        c = charAt;
        int intValue2 = ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue();
        return new NumberProcessor(this.element, this.fixedWidth, this.minDigits, this.maxDigits, this.signPolicy, this.protectedMode, i, c, numberSystem, (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART), intValue2, numberSystem != NumberSystem.ARABIC && c == '0' && this.fixedWidth && intValue2 == 0 && this.element.getType() == Integer.class && !this.yearOfEra);
    }

    private int getScale(NumberSystem numberSystem) {
        if (!numberSystem.isDecimal()) {
            return 100;
        }
        Class<V> type = this.element.getType();
        if (type == Integer.class) {
            return 10;
        }
        return type == Long.class ? 18 : 9;
    }

    private static int length(int i) {
        int i2 = 0;
        while (i > THRESHOLDS[i2]) {
            i2++;
        }
        return i2 + 1;
    }

    private static void appendTwoDigits(int i, Appendable appendable, char c) throws IOException {
        int i2 = (i * 103) >>> 10;
        appendable.append((char) (i2 + c));
        appendable.append((char) ((i - ((i2 << 3) + (i2 << 1))) + c));
    }
}
