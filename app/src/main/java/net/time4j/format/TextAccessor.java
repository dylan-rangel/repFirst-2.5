package net.time4j.format;

import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.time4j.engine.AttributeQuery;

/* loaded from: classes3.dex */
public final class TextAccessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char PROTECTED_SPACE = 160;
    private final List<String> textForms;

    TextAccessor(String[] strArr) {
        this.textForms = Collections.unmodifiableList(Arrays.asList(strArr));
    }

    public String print(Enum<?> r3) {
        int ordinal = r3.ordinal();
        if (this.textForms.size() <= ordinal) {
            return r3.name();
        }
        return this.textForms.get(ordinal);
    }

    public <V extends Enum<V>> V parse(CharSequence charSequence, ParsePosition parsePosition, Class<V> cls) {
        return (V) parse(charSequence, parsePosition, cls, true, false, true);
    }

    public <V extends Enum<V>> V parse(CharSequence charSequence, ParsePosition parsePosition, Class<V> cls, Leniency leniency) {
        boolean z;
        boolean z2;
        boolean z3;
        if (leniency == Leniency.STRICT) {
            z = false;
            z2 = false;
            z3 = false;
        } else {
            z = true;
            z2 = leniency == Leniency.LAX;
            z3 = true;
        }
        return (V) parse(charSequence, parsePosition, cls, z, z2, z3);
    }

    public <V extends Enum<V>> V parse(CharSequence charSequence, ParsePosition parsePosition, Class<V> cls, AttributeQuery attributeQuery) {
        return (V) parse(charSequence, parsePosition, cls, ((Boolean) attributeQuery.get(Attributes.PARSE_CASE_INSENSITIVE, Boolean.TRUE)).booleanValue(), ((Boolean) attributeQuery.get(Attributes.PARSE_PARTIAL_COMPARE, Boolean.FALSE)).booleanValue(), ((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue());
    }

    public List<String> getTextForms() {
        return this.textForms;
    }

    public String toString() {
        int size = this.textForms.size();
        StringBuilder sb = new StringBuilder((size * 16) + 2);
        sb.append('{');
        boolean z = true;
        for (int i = 0; i < size; i++) {
            if (z) {
                z = false;
            } else {
                sb.append(',');
            }
            sb.append(this.textForms.get(i));
        }
        sb.append('}');
        return sb.toString();
    }

    private <V extends Enum<V>> V parse(CharSequence charSequence, ParsePosition parsePosition, Class<V> cls, boolean z, boolean z2, boolean z3) {
        int i;
        int i2;
        String str;
        V v;
        V[] enumConstants = cls.getEnumConstants();
        int size = this.textForms.size();
        int index = parsePosition.getIndex();
        int length = charSequence.length();
        String str2 = "";
        String str3 = "";
        int i3 = 0;
        V v2 = null;
        int i4 = 0;
        while (i3 < enumConstants.length) {
            boolean isEmpty = str3.isEmpty();
            if (isEmpty) {
                str3 = i3 >= size ? enumConstants[i3].name() : this.textForms.get(i3);
            }
            int length2 = str3.length();
            int i5 = index;
            int i6 = 0;
            boolean z4 = true;
            while (z4 && i6 < length2) {
                int i7 = size;
                int i8 = index + i6;
                if (i8 >= length) {
                    str = str2;
                    v = v2;
                    z4 = false;
                } else {
                    char charAt = charSequence.charAt(i8);
                    str = str2;
                    char charAt2 = str3.charAt(i6);
                    v = v2;
                    if (z3) {
                        if (charAt == 160) {
                            charAt = ' ';
                        }
                        if (charAt2 == 160) {
                            charAt2 = ' ';
                        }
                    }
                    boolean z5 = !z ? charAt != charAt2 : !(charAt == charAt2 || compareIgnoreCase(charAt, charAt2));
                    if (z5) {
                        i5++;
                    }
                    z4 = z5;
                }
                i6++;
                size = i7;
                str2 = str;
                v2 = v;
            }
            int i9 = size;
            String str4 = str2;
            V v3 = v2;
            if (z3 && isEmpty && length2 == 5 && str3.charAt(4) == '.' && i5 == (i2 = index + 3) && i2 < length && charSequence.charAt(i2) == '.') {
                i3--;
                str3 = ((Object) str3.subSequence(index, i2)) + ".";
            } else {
                if (z2 || length2 == 1) {
                    int i10 = i5 - index;
                    if (i4 < i10) {
                        v2 = enumConstants[i3];
                        i4 = i10;
                        str3 = str4;
                        i = 1;
                        i3 += i;
                        size = i9;
                        str2 = str4;
                    } else if (i4 == i10) {
                        str3 = str4;
                        i = 1;
                        v2 = null;
                        i3 += i;
                        size = i9;
                        str2 = str4;
                    }
                } else if (z4) {
                    parsePosition.setIndex(i5);
                    return enumConstants[i3];
                }
                str3 = str4;
            }
            v2 = v3;
            i = 1;
            i3 += i;
            size = i9;
            str2 = str4;
        }
        V v4 = v2;
        if (v4 == null) {
            parsePosition.setErrorIndex(index);
        } else {
            parsePosition.setIndex(index + i4);
        }
        return v4;
    }

    private boolean compareIgnoreCase(char c, char c2) {
        if (c >= 'a' && c <= 'z') {
            if (c2 >= 'A' && c2 <= 'Z') {
                c2 = (char) ((c2 + 'a') - 65);
            }
            return c == c2;
        }
        if (c < 'A' || c > 'Z') {
            return Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
        }
        char c3 = (char) ((c + 'a') - 65);
        if (c2 >= 'A' && c2 <= 'Z') {
            c2 = (char) ((c2 + 'a') - 65);
        }
        return c3 == c2;
    }
}
