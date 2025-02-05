package net.time4j.format.expert;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.NumberType;
import net.time4j.format.PluralCategory;
import net.time4j.format.PluralRules;

/* loaded from: classes3.dex */
final class OrdinalProcessor implements FormatProcessor<Integer> {
    private static final Map<PluralCategory, String> ENGLISH_ORDINALS;
    private final ChronoElement<Integer> element;
    private final Map<PluralCategory, String> indicators;
    private final Leniency lenientMode;
    private final Locale locale;
    private final int protectedLength;
    private final int reserved;
    private final char zeroDigit;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    static {
        EnumMap enumMap = new EnumMap(PluralCategory.class);
        enumMap.put((EnumMap) PluralCategory.ONE, (PluralCategory) "st");
        enumMap.put((EnumMap) PluralCategory.TWO, (PluralCategory) "nd");
        enumMap.put((EnumMap) PluralCategory.FEW, (PluralCategory) "rd");
        enumMap.put((EnumMap) PluralCategory.OTHER, (PluralCategory) "th");
        ENGLISH_ORDINALS = Collections.unmodifiableMap(enumMap);
    }

    OrdinalProcessor(ChronoElement<Integer> chronoElement, Map<PluralCategory, String> map) {
        if (chronoElement == null) {
            throw new NullPointerException("Missing element.");
        }
        this.element = chronoElement;
        if (map == null) {
            this.indicators = null;
        } else {
            Map<PluralCategory, String> unmodifiableMap = Collections.unmodifiableMap(new EnumMap(map));
            this.indicators = unmodifiableMap;
            if (!unmodifiableMap.containsKey(PluralCategory.OTHER)) {
                throw new IllegalArgumentException("Missing plural category OTHER: " + map);
            }
        }
        this.reserved = 0;
        this.protectedLength = 0;
        this.zeroDigit = '0';
        this.lenientMode = Leniency.SMART;
        this.locale = Locale.ROOT;
    }

    private OrdinalProcessor(ChronoElement<Integer> chronoElement, Map<PluralCategory, String> map, int i, int i2, char c, Leniency leniency, Locale locale) {
        this.element = chronoElement;
        this.indicators = map;
        this.reserved = i;
        this.protectedLength = i2;
        this.zeroDigit = c;
        this.lenientMode = leniency;
        this.locale = locale;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        char charValue;
        int i = chronoDisplay.getInt(this.element);
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return -1;
            }
            throw new IllegalArgumentException("Cannot format negative ordinal numbers: " + chronoDisplay);
        }
        String num = Integer.toString(i);
        if (z) {
            charValue = this.zeroDigit;
        } else {
            charValue = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue();
        }
        if (charValue != '0') {
            int i2 = charValue - '0';
            char[] charArray = num.toCharArray();
            for (int i3 = 0; i3 < charArray.length; i3++) {
                charArray[i3] = (char) (charArray[i3] + i2);
            }
            num = new String(charArray);
        }
        int length = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        appendable.append(num);
        int length2 = 0 + num.length();
        String indicator = getIndicator(attributeQuery, z, i);
        appendable.append(indicator);
        int length3 = length2 + indicator.length();
        if (length != -1 && length3 > 0 && set != null) {
            set.add(new ElementPosition(this.element, length, length + length3));
        }
        return length3;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e3  */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r21, net.time4j.format.expert.ParseLog r22, net.time4j.engine.AttributeQuery r23, net.time4j.format.expert.ParsedEntity<?> r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.OrdinalProcessor.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrdinalProcessor)) {
            return false;
        }
        OrdinalProcessor ordinalProcessor = (OrdinalProcessor) obj;
        return this.element.equals(ordinalProcessor.element) && getIndicators().equals(ordinalProcessor.getIndicators());
    }

    public int hashCode() {
        return (this.element.hashCode() * 7) + (getIndicators().hashCode() * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[element=");
        sb.append(this.element.name());
        sb.append(", indicators=");
        sb.append(getIndicators());
        sb.append(']');
        return sb.toString();
    }

    private String getIndicator(AttributeQuery attributeQuery, boolean z, int i) {
        Locale locale;
        if (isEnglish()) {
            locale = Locale.ENGLISH;
        } else {
            locale = z ? this.locale : (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
        }
        PluralCategory category = PluralRules.of(locale, NumberType.ORDINALS).getCategory(i);
        if (!getIndicators().containsKey(category)) {
            category = PluralCategory.OTHER;
        }
        return getIndicators().get(category);
    }

    private boolean isEnglish() {
        return this.indicators == null;
    }

    private Map<PluralCategory, String> getIndicators() {
        if (isEnglish()) {
            return ENGLISH_ORDINALS;
        }
        return this.indicators;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<Integer> getElement() {
        return this.element;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Integer> withElement(ChronoElement<Integer> chronoElement) {
        return this.element == chronoElement ? this : new OrdinalProcessor(chronoElement, this.indicators);
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Integer> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return new OrdinalProcessor(this.element, this.indicators, i, ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue(), ((Character) attributeQuery.get(Attributes.ZERO_DIGIT, '0')).charValue(), (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART), (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT));
    }
}
