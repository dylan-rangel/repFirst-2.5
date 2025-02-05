package net.time4j.format.internal;

import net.time4j.format.NumberSystem;

/* loaded from: classes3.dex */
public class DualFormatHelper {
    public static String toNumeral(NumberSystem numberSystem, char c, int i) {
        if (numberSystem.isDecimal()) {
            int i2 = c - '0';
            String num = Integer.toString(i);
            if (i2 == 0) {
                return num;
            }
            StringBuilder sb = new StringBuilder();
            int length = num.length();
            for (int i3 = 0; i3 < length; i3++) {
                sb.append((char) (num.charAt(i3) + i2));
            }
            return sb.toString();
        }
        return numberSystem.toNumeral(i);
    }
}
