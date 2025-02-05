package net.time4j.format.expert;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;

/* loaded from: classes3.dex */
final class LiteralProcessor implements FormatProcessor<Void> {
    private final char alt;
    private final AttributeKey<Character> attribute;
    private final boolean caseInsensitive;
    private final boolean interpunctuationMode;
    private final String multi;
    private final boolean rtl;
    private final char single;

    private static boolean isBidi(char c) {
        return c == 8206 || c == 8207 || c == 1564;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<Void> getElement() {
        return null;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> withElement(ChronoElement<Void> chronoElement) {
        return this;
    }

    LiteralProcessor(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Missing literal.");
        }
        char charAt = str.charAt(0);
        this.single = charAt;
        this.alt = charAt;
        this.attribute = null;
        this.multi = str;
        if (charAt < ' ') {
            throw new IllegalArgumentException("Literal must not start with non-printable char.");
        }
        this.caseInsensitive = true;
        this.interpunctuationMode = str.length() == 1 && isInterpunctuation(charAt);
        this.rtl = false;
    }

    LiteralProcessor(char c, char c2) {
        this.single = c;
        this.alt = c2;
        this.attribute = null;
        this.multi = null;
        if (c < ' ' || c2 < ' ') {
            throw new IllegalArgumentException("Literal must not start with non-printable char.");
        }
        if (Character.isDigit(c) || Character.isDigit(c2)) {
            throw new IllegalArgumentException("Literal must not be a decimal digit.");
        }
        this.caseInsensitive = true;
        this.interpunctuationMode = false;
        this.rtl = false;
    }

    LiteralProcessor(AttributeKey<Character> attributeKey) {
        if (attributeKey == null) {
            throw new NullPointerException("Missing format attribute.");
        }
        this.single = (char) 0;
        this.alt = (char) 0;
        this.attribute = attributeKey;
        this.multi = null;
        this.caseInsensitive = true;
        this.interpunctuationMode = false;
        this.rtl = false;
    }

    private LiteralProcessor(char c, char c2, String str, AttributeKey<Character> attributeKey, boolean z, boolean z2, boolean z3) {
        this.single = c;
        this.alt = c2;
        this.multi = str;
        this.attribute = attributeKey;
        this.caseInsensitive = z;
        this.interpunctuationMode = z2;
        this.rtl = z3;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        AttributeKey<Character> attributeKey = this.attribute;
        if (attributeKey != null) {
            appendable.append(((Character) attributeQuery.get(attributeKey, null)).charValue());
            return 1;
        }
        String str = this.multi;
        if (str == null) {
            appendable.append(this.single);
            return 1;
        }
        appendable.append(str);
        return this.multi.length();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        if (z && this.interpunctuationMode) {
            int position = parseLog.getPosition();
            if (position < charSequence.length() && charSequence.charAt(position) == this.single) {
                parseLog.setPosition(position + 1);
                return;
            } else {
                if (this.single == '.' && ((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue()) {
                    return;
                }
                logError(charSequence, parseLog);
                return;
            }
        }
        if (this.multi == null) {
            parseChar(charSequence, parseLog, attributeQuery, z);
        } else {
            parseMulti(charSequence, parseLog, attributeQuery, z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x008b, code lost:
    
        if (charEqualsIgnoreCase(r2, r5) != false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseChar(java.lang.CharSequence r10, net.time4j.format.expert.ParseLog r11, net.time4j.engine.AttributeQuery r12, boolean r13) {
        /*
            r9 = this;
            int r0 = r11.getPosition()
            char r1 = r9.single
            net.time4j.engine.AttributeKey<java.lang.Character> r2 = r9.attribute
            r3 = 0
            if (r2 == 0) goto L19
            java.lang.Character r1 = java.lang.Character.valueOf(r3)
            java.lang.Object r1 = r12.get(r2, r1)
            java.lang.Character r1 = (java.lang.Character) r1
            char r1 = r1.charValue()
        L19:
            int r2 = r10.length()
            r4 = 1
            if (r0 >= r2) goto L90
            if (r1 == 0) goto L90
            boolean r2 = java.lang.Character.isDigit(r1)
            if (r2 == 0) goto L2a
            goto L90
        L2a:
            char r2 = r10.charAt(r0)
            char r5 = r9.alt
            net.time4j.engine.AttributeKey<java.lang.Character> r6 = r9.attribute
            if (r6 == 0) goto L63
            net.time4j.engine.AttributeKey<java.lang.Character> r6 = net.time4j.format.Attributes.DECIMAL_SEPARATOR
            java.lang.String r6 = r6.name()
            net.time4j.engine.AttributeKey<java.lang.Character> r7 = r9.attribute
            java.lang.String r7 = r7.name()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L63
            java.util.Locale r6 = java.util.Locale.ROOT
            net.time4j.engine.AttributeKey<java.util.Locale> r7 = net.time4j.format.Attributes.LANGUAGE
            java.util.Locale r8 = java.util.Locale.ROOT
            java.lang.Object r7 = r12.get(r7, r8)
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L63
            r5 = 46
            r6 = 44
            if (r1 != r6) goto L5d
            goto L63
        L5d:
            if (r1 != r5) goto L62
            r5 = 44
            goto L63
        L62:
            r5 = r1
        L63:
            if (r2 == r1) goto L69
            if (r2 == r5) goto L69
            r6 = 1
            goto L6a
        L69:
            r6 = 0
        L6a:
            if (r6 == 0) goto L8e
            if (r13 == 0) goto L71
            boolean r12 = r9.caseInsensitive
            goto L7f
        L71:
            net.time4j.engine.AttributeKey<java.lang.Boolean> r13 = net.time4j.format.Attributes.PARSE_CASE_INSENSITIVE
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            java.lang.Object r12 = r12.get(r13, r7)
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
        L7f:
            if (r12 == 0) goto L8e
            boolean r12 = charEqualsIgnoreCase(r2, r1)
            if (r12 != 0) goto L92
            boolean r12 = charEqualsIgnoreCase(r2, r5)
            if (r12 == 0) goto L8e
            goto L92
        L8e:
            r3 = r6
            goto L92
        L90:
            r2 = 0
            r3 = 1
        L92:
            if (r3 == 0) goto Lbd
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "Cannot parse: \""
            r12.<init>(r13)
            r12.append(r10)
            java.lang.String r10 = "\" (expected: ["
            r12.append(r10)
            r12.append(r1)
            java.lang.String r10 = "], found: ["
            r12.append(r10)
            if (r2 == 0) goto Lb0
            r12.append(r2)
        Lb0:
            java.lang.String r10 = "])"
            r12.append(r10)
            java.lang.String r10 = r12.toString()
            r11.setError(r0, r10)
            goto Lc1
        Lbd:
            int r0 = r0 + r4
            r11.setPosition(r0)
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.LiteralProcessor.parseChar(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, boolean):void");
    }

    private void parseMulti(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, boolean z) {
        boolean booleanValue;
        boolean isRTL;
        int position = parseLog.getPosition();
        if (z) {
            booleanValue = this.caseInsensitive;
        } else {
            booleanValue = ((Boolean) attributeQuery.get(Attributes.PARSE_CASE_INSENSITIVE, Boolean.TRUE)).booleanValue();
        }
        if (z) {
            isRTL = this.rtl;
        } else {
            isRTL = CalendarText.isRTL((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT));
        }
        int subSequenceEquals = subSequenceEquals(charSequence, position, this.multi, booleanValue, isRTL);
        if (subSequenceEquals == -1) {
            logError(charSequence, parseLog);
        } else {
            parseLog.setPosition(position + subSequenceEquals);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LiteralProcessor)) {
            return false;
        }
        LiteralProcessor literalProcessor = (LiteralProcessor) obj;
        AttributeKey<Character> attributeKey = this.attribute;
        if (attributeKey != null) {
            return attributeKey.equals(literalProcessor.attribute);
        }
        String str = this.multi;
        return str == null ? literalProcessor.multi == null && this.single == literalProcessor.single && this.alt == literalProcessor.alt : str.equals(literalProcessor.multi) && this.interpunctuationMode == literalProcessor.interpunctuationMode;
    }

    public int hashCode() {
        String name;
        AttributeKey<Character> attributeKey = this.attribute;
        if (attributeKey == null) {
            name = this.multi;
            if (name == null) {
                name = "";
            }
        } else {
            name = attributeKey.name();
        }
        return name.hashCode() ^ this.single;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append("[literal=");
        if (this.attribute != null) {
            sb.append('{');
            sb.append(this.attribute);
            sb.append('}');
        } else {
            String str = this.multi;
            if (str == null) {
                sb.append(this.single);
                if (this.alt != this.single) {
                    sb.append(", alternative=");
                    sb.append(this.alt);
                }
            } else {
                sb.append(str);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return this.multi != null && getPrefixedDigitArea() == this.multi.length();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<Void> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        boolean isRTL = CalendarText.isRTL((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT));
        return new LiteralProcessor(this.single, this.alt, this.multi, this.attribute, ((Boolean) attributeQuery.get(Attributes.PARSE_CASE_INSENSITIVE, Boolean.TRUE)).booleanValue(), this.interpunctuationMode && !isRTL, isRTL);
    }

    int getPrefixedDigitArea() {
        String str = this.multi;
        if (str == null) {
            return 0;
        }
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length && Character.isDigit(this.multi.charAt(i2)); i2++) {
            i++;
        }
        return i;
    }

    static int subSequenceEquals(CharSequence charSequence, int i, CharSequence charSequence2, boolean z, boolean z2) {
        char charAt;
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length2; i3++) {
            char charAt2 = charSequence2.charAt(i3);
            if (!isBidi(charAt2)) {
                if (z2) {
                    charAt = 0;
                    while (true) {
                        int i4 = i2 + i;
                        if (i4 >= length) {
                            break;
                        }
                        charAt = charSequence.charAt(i4);
                        if (!isBidi(charAt)) {
                            break;
                        }
                        i2++;
                    }
                } else {
                    int i5 = i2 + i;
                    charAt = i5 < length ? charSequence.charAt(i5) : (char) 0;
                }
                if (i2 + i >= length) {
                    return -1;
                }
                i2++;
                if (z) {
                    if (!charEqualsIgnoreCase(charAt, charAt2)) {
                        return -1;
                    }
                } else if (charAt != charAt2) {
                    return -1;
                }
            }
        }
        if (z2) {
            while (true) {
                int i6 = i2 + i;
                if (i6 >= length || !isBidi(charSequence.charAt(i6))) {
                    break;
                }
                i2++;
            }
        }
        return i2;
    }

    private static boolean charEqualsIgnoreCase(char c, char c2) {
        return c == c2 || Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
    }

    private static boolean isInterpunctuation(char c) {
        return (Character.isLetter(c) || Character.isDigit(c) || isBidi(c)) ? false : true;
    }

    private void logError(CharSequence charSequence, ParseLog parseLog) {
        int position = parseLog.getPosition();
        parseLog.setError(position, "Cannot parse: \"" + charSequence + "\" (expected: [" + this.multi + "], found: [" + charSequence.subSequence(position, Math.min(this.multi.length() + position, charSequence.length())) + "])");
    }
}
