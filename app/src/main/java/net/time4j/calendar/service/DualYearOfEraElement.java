package net.time4j.calendar.service;

import java.io.IOException;
import java.text.ParsePosition;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSystem;
import net.time4j.format.TextWidth;
import net.time4j.format.internal.DualFormatElement;

/* loaded from: classes3.dex */
public abstract class DualYearOfEraElement<T extends ChronoEntity<T>> extends StdIntegerDateElement<T> implements DualFormatElement {
    protected abstract NumberSystem getNumberSystem(AttributeQuery attributeQuery);

    protected DualYearOfEraElement(Class<T> cls, int i, int i2, char c) {
        super("YEAR_OF_ERA", cls, i, i2, c, null, null);
    }

    @Override // net.time4j.format.TextElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
        char c;
        char charAt;
        NumberSystem numberSystem = getNumberSystem(attributeQuery);
        int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[((TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.NARROW)).ordinal()];
        int i2 = i != 1 ? i != 2 ? i != 3 ? 4 : 3 : 2 : 1;
        if (attributeQuery.contains(Attributes.ZERO_DIGIT)) {
            charAt = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT)).charValue();
        } else {
            if (!numberSystem.isDecimal()) {
                c = '0';
                print(chronoDisplay, appendable, attributeQuery, numberSystem, c, i2, 10);
            }
            charAt = numberSystem.getDigits().charAt(0);
        }
        c = charAt;
        print(chronoDisplay, appendable, attributeQuery, numberSystem, c, i2, 10);
    }

    /* renamed from: net.time4j.calendar.service.DualYearOfEraElement$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$TextWidth;

        static {
            int[] iArr = new int[TextWidth.values().length];
            $SwitchMap$net$time4j$format$TextWidth = iArr;
            try {
                iArr[TextWidth.NARROW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.ABBREVIATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // net.time4j.format.internal.DualFormatElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, NumberSystem numberSystem, char c, int i, int i2) throws IOException, ChronoException {
        String numeral = numberSystem.toNumeral(chronoDisplay.getInt(this));
        if (numberSystem.isDecimal()) {
            int length = i - numeral.length();
            for (int i3 = 0; i3 < length; i3++) {
                appendable.append(c);
            }
        }
        appendable.append(numeral);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.format.TextElement
    public Integer parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
        char charAt;
        int i;
        NumberSystem numberSystem = getNumberSystem(attributeQuery);
        int index = parsePosition.getIndex();
        int i2 = 0;
        if (attributeQuery.contains(Attributes.ZERO_DIGIT)) {
            charAt = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT)).charValue();
        } else {
            charAt = numberSystem.isDecimal() ? numberSystem.getDigits().charAt(0) : '0';
        }
        Leniency leniency = numberSystem.isDecimal() ? Leniency.SMART : (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART);
        long j = 0;
        if (numberSystem.isDecimal()) {
            int min = Math.min(index + 9, charSequence.length());
            int i3 = index;
            i = i3;
            while (i3 < min) {
                int charAt2 = charSequence.charAt(i3) - charAt;
                if (charAt2 < 0 || charAt2 > 9) {
                    break;
                }
                j = (j * 10) + charAt2;
                i++;
                i3++;
            }
        } else {
            int length = charSequence.length();
            for (int i4 = index; i4 < length && numberSystem.contains(charSequence.charAt(i4)); i4++) {
                i2++;
            }
            if (i2 > 0) {
                i = i2 + index;
                j = numberSystem.toInteger(charSequence.subSequence(index, i).toString(), leniency);
            } else {
                i = index;
            }
        }
        if (i == index || j > 2147483647L) {
            parsePosition.setErrorIndex(index);
            return null;
        }
        parsePosition.setIndex(i);
        return Integer.valueOf((int) j);
    }

    @Override // net.time4j.format.internal.DualFormatElement
    public Integer parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery, ChronoEntity<?> chronoEntity) {
        return parse(charSequence, parsePosition, attributeQuery);
    }
}
