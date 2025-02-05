package net.time4j.format.internal;

/* loaded from: classes3.dex */
public class FormatUtils {
    private FormatUtils() {
    }

    public static String removeZones(String str) {
        int i;
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i2 = 0;
        boolean z = false;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            if (charAt == '\'') {
                int i3 = i2 + 1;
                if (i3 >= length || str.charAt(i3) != '\'') {
                    z = !z;
                } else {
                    sb.append(charAt);
                    i2 = i3;
                }
                sb.append(charAt);
            } else if (z) {
                sb.append(charAt);
            } else if (charAt != 'z' && charAt != 'Z' && charAt != 'v' && charAt != 'V' && charAt != 'x' && charAt != 'X') {
                sb.append(charAt);
            }
            i2++;
        }
        int i4 = 0;
        while (i4 < sb.length()) {
            char charAt2 = sb.charAt(i4);
            if (charAt2 == ' ' && (i = i4 + 1) < sb.length() && sb.charAt(i) == ' ') {
                sb.deleteCharAt(i4);
            } else if (charAt2 == '[' || charAt2 == ']' || charAt2 == '(' || charAt2 == ')') {
                sb.deleteCharAt(i4);
            } else {
                i4++;
            }
            i4--;
            i4++;
        }
        String trim = sb.toString().trim();
        if (!trim.endsWith(" '")) {
            return trim.endsWith(",") ? trim.substring(0, trim.length() - 1) : trim;
        }
        return trim.substring(0, trim.length() - 2) + "'";
    }
}
