package net.time4j.format;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import net.time4j.base.MathUtils;

/* loaded from: classes3.dex */
public enum NumberSystem {
    ARABIC("latn") { // from class: net.time4j.format.NumberSystem.1
        @Override // net.time4j.format.NumberSystem
        public boolean contains(char c) {
            return c >= '0' && c <= '9';
        }

        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "0123456789";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }

        @Override // net.time4j.format.NumberSystem
        public String toNumeral(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot convert: " + i);
            }
            return Integer.toString(i);
        }

        @Override // net.time4j.format.NumberSystem
        public int toInteger(String str, Leniency leniency) {
            int parseInt = Integer.parseInt(str);
            if (parseInt >= 0) {
                return parseInt;
            }
            throw new NumberFormatException("Cannot convert negative number: " + str);
        }
    },
    ARABIC_INDIC("arab") { // from class: net.time4j.format.NumberSystem.2
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "٠١٢٣٤٥٦٧٨٩";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    ARABIC_INDIC_EXT("arabext") { // from class: net.time4j.format.NumberSystem.3
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "۰۱۲۳۴۵۶۷۸۹";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    BENGALI("beng") { // from class: net.time4j.format.NumberSystem.4
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "০১২৩৪৫৬৭৮৯";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    DEVANAGARI("deva") { // from class: net.time4j.format.NumberSystem.5
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "०१२३४५६७८९";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    DOZENAL("dozenal") { // from class: net.time4j.format.NumberSystem.6
        @Override // net.time4j.format.NumberSystem
        public boolean contains(char c) {
            return (c >= '0' && c <= '9') || c == 8586 || c == 8587;
        }

        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "0123456789↊↋";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return false;
        }

        @Override // net.time4j.format.NumberSystem
        public String toNumeral(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot convert: " + i);
            }
            return Integer.toString(i, 12).replace('a', (char) 8586).replace('b', (char) 8587);
        }

        @Override // net.time4j.format.NumberSystem
        public int toNumeral(int i, Appendable appendable) throws IOException {
            if (i >= 0) {
                int i2 = 0;
                int i3 = 1;
                while (true) {
                    if (i3 > 4) {
                        break;
                    }
                    if (i < NumberSystem.D_FACTORS[i3]) {
                        i2 = i3;
                        break;
                    }
                    i3++;
                }
                if (i2 > 0) {
                    int i4 = i2 - 1;
                    do {
                        int i5 = i / NumberSystem.D_FACTORS[i4];
                        appendable.append(i5 == 11 ? (char) 8587 : i5 == 10 ? (char) 8586 : (char) (i5 + 48));
                        i -= i5 * NumberSystem.D_FACTORS[i4];
                        i4--;
                    } while (i4 >= 0);
                    return i2;
                }
            }
            return super.toNumeral(i, appendable);
        }

        @Override // net.time4j.format.NumberSystem
        public int toInteger(String str, Leniency leniency) {
            int parseInt = Integer.parseInt(str.replace((char) 8586, 'a').replace((char) 8587, 'b'), 12);
            if (parseInt >= 0) {
                return parseInt;
            }
            throw new NumberFormatException("Cannot convert negative number: " + str);
        }
    },
    ETHIOPIC("ethiopic") { // from class: net.time4j.format.NumberSystem.7
        @Override // net.time4j.format.NumberSystem
        public boolean contains(char c) {
            return c >= 4969 && c <= 4988;
        }

        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "፩፪፫፬፭፮፯፰፱፲፳፴፵፶፷፸፹፺፻፼";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x007e A[SYNTHETIC] */
        @Override // net.time4j.format.NumberSystem
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String toNumeral(int r11) {
            /*
                r10 = this;
                r0 = 1
                if (r11 < r0) goto L86
                java.lang.String r11 = java.lang.String.valueOf(r11)
                int r1 = r11.length()
                int r1 = r1 - r0
                int r2 = r1 % 2
                if (r2 != 0) goto L23
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "0"
                r2.append(r3)
                r2.append(r11)
                java.lang.String r11 = r2.toString()
                int r1 = r1 + 1
            L23:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                r3 = r1
            L29:
                if (r3 < 0) goto L81
                int r4 = r1 - r3
                char r4 = r11.charAt(r4)
                int r3 = r3 + (-1)
                int r5 = r1 - r3
                char r5 = r11.charAt(r5)
                r6 = 48
                r7 = 0
                if (r5 == r6) goto L42
                int r5 = r5 + 4920
                char r5 = (char) r5
                goto L43
            L42:
                r5 = 0
            L43:
                if (r4 == r6) goto L49
                int r4 = r4 + 4929
                char r4 = (char) r4
                goto L4a
            L49:
                r4 = 0
            L4a:
                int r6 = r3 % 4
                int r6 = r6 / 2
                r8 = 4987(0x137b, float:6.988E-42)
                if (r3 == 0) goto L5e
                if (r6 == 0) goto L5b
                if (r5 != 0) goto L58
                if (r4 == 0) goto L5e
            L58:
                r6 = 4987(0x137b, float:6.988E-42)
                goto L5f
            L5b:
                r6 = 4988(0x137c, float:6.99E-42)
                goto L5f
            L5e:
                r6 = 0
            L5f:
                r9 = 4969(0x1369, float:6.963E-42)
                if (r5 != r9) goto L6e
                if (r4 != 0) goto L6e
                if (r1 <= r0) goto L6e
                if (r6 == r8) goto L6f
                int r8 = r3 + 1
                if (r8 != r1) goto L6e
                goto L6f
            L6e:
                r7 = r5
            L6f:
                if (r4 == 0) goto L74
                r2.append(r4)
            L74:
                if (r7 == 0) goto L79
                r2.append(r7)
            L79:
                if (r6 == 0) goto L7e
                r2.append(r6)
            L7e:
                int r3 = r3 + (-1)
                goto L29
            L81:
                java.lang.String r11 = r2.toString()
                return r11
            L86:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Can only convert positive numbers: "
                r1.append(r2)
                r1.append(r11)
                java.lang.String r11 = r1.toString()
                r0.<init>(r11)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.NumberSystem.AnonymousClass7.toNumeral(int):java.lang.String");
        }

        @Override // net.time4j.format.NumberSystem
        public int toInteger(String str, Leniency leniency) {
            int i;
            boolean z = false;
            boolean z2 = false;
            int i2 = 0;
            int i3 = 0;
            int i4 = 1;
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                if (charAt >= 4969 && charAt < 4978) {
                    i = (charAt + 1) - 4969;
                } else if (charAt < 4978 || charAt >= 4987) {
                    if (charAt == 4988) {
                        if (z && i3 == 0) {
                            i3 = 1;
                        }
                        i2 = NumberSystem.addEthiopic(i2, i3, i4);
                        i4 = z ? i4 * 100 : i4 * 10000;
                        z = false;
                        z2 = true;
                    } else if (charAt == 4987) {
                        i2 = NumberSystem.addEthiopic(i2, i3, i4);
                        i4 *= 100;
                        z = true;
                        z2 = false;
                    }
                    i3 = 0;
                } else {
                    i = ((charAt + 1) - 4978) * 10;
                }
                i3 += i;
            }
            return NumberSystem.addEthiopic(i2, ((z || z2) && i3 == 0) ? 1 : i3, i4);
        }
    },
    GUJARATI("gujr") { // from class: net.time4j.format.NumberSystem.8
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "૦૧૨૩૪૫૬૭૮૯";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    JAPANESE("jpan") { // from class: net.time4j.format.NumberSystem.9
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "一二三四五六七八九十百千";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return false;
        }

        @Override // net.time4j.format.NumberSystem
        public String toNumeral(int i) {
            if (i < 1 || i > 9999) {
                throw new IllegalArgumentException("Cannot convert: " + i);
            }
            String digits = getDigits();
            int i2 = i / 1000;
            int i3 = i % 1000;
            int i4 = i3 / 100;
            int i5 = i3 % 100;
            int i6 = i5 / 10;
            int i7 = i5 % 10;
            StringBuilder sb = new StringBuilder();
            if (i2 >= 1) {
                if (i2 > 1) {
                    sb.append(digits.charAt(i2 - 1));
                }
                sb.append((char) 21315);
            }
            if (i4 >= 1) {
                if (i4 > 1) {
                    sb.append(digits.charAt(i4 - 1));
                }
                sb.append((char) 30334);
            }
            if (i6 >= 1) {
                if (i6 > 1) {
                    sb.append(digits.charAt(i6 - 1));
                }
                sb.append((char) 21313);
            }
            if (i7 > 0) {
                sb.append(digits.charAt(i7 - 1));
            }
            return sb.toString();
        }

        @Override // net.time4j.format.NumberSystem
        public int toInteger(String str, Leniency leniency) {
            boolean z;
            String digits = getDigits();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                if (charAt == 21313) {
                    if (i != 0 || i3 != 0 || i4 != 0) {
                        throw new IllegalArgumentException("Invalid Japanese numeral: " + str);
                    }
                    i++;
                } else if (charAt == 21315) {
                    if (i4 != 0) {
                        throw new IllegalArgumentException("Invalid Japanese numeral: " + str);
                    }
                    i4++;
                } else if (charAt != 30334) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= 9) {
                            z = false;
                            break;
                        }
                        if (digits.charAt(i5) == charAt) {
                            int i6 = i5 + 1;
                            if (i4 == 1) {
                                i2 += i6 * 1000;
                                i4 = -1;
                            } else if (i3 == 1) {
                                i2 += i6 * 100;
                                i3 = -1;
                            } else if (i == 1) {
                                i2 += i6 * 10;
                                i = -1;
                            } else {
                                i2 += i6;
                            }
                            z = true;
                        } else {
                            i5++;
                        }
                    }
                    if (!z) {
                        throw new IllegalArgumentException("Invalid Japanese numeral: " + str);
                    }
                } else {
                    if (i3 != 0 || i4 != 0) {
                        throw new IllegalArgumentException("Invalid Japanese numeral: " + str);
                    }
                    i3++;
                }
            }
            if (i == 1) {
                i2 += 10;
            }
            if (i3 == 1) {
                i2 += 100;
            }
            return i4 == 1 ? i2 + 1000 : i2;
        }
    },
    KHMER("khmr") { // from class: net.time4j.format.NumberSystem.10
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "០១២៣៤៥៦៧៨៩";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    MYANMAR("mymr") { // from class: net.time4j.format.NumberSystem.11
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "၀၁၂၃၄၅၆၇၈၉";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    ORYA("orya") { // from class: net.time4j.format.NumberSystem.12
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "୦୧୨୩୪୫୬୭୮୯";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    ROMAN("roman") { // from class: net.time4j.format.NumberSystem.13
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "IVXLCDM";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return false;
        }

        @Override // net.time4j.format.NumberSystem
        public String toNumeral(int i) {
            if (i < 1 || i > 3999) {
                throw new IllegalArgumentException("Out of range (1-3999): " + i);
            }
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < NumberSystem.NUMBERS.length; i2++) {
                while (i >= NumberSystem.NUMBERS[i2]) {
                    sb.append(NumberSystem.LETTERS[i2]);
                    i -= NumberSystem.NUMBERS[i2];
                }
            }
            return sb.toString();
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x0016, code lost:
        
            continue;
         */
        @Override // net.time4j.format.NumberSystem
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int toInteger(java.lang.String r11, net.time4j.format.Leniency r12) {
            /*
                Method dump skipped, instructions count: 224
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.NumberSystem.AnonymousClass13.toInteger(java.lang.String, net.time4j.format.Leniency):int");
        }

        @Override // net.time4j.format.NumberSystem
        public boolean contains(char c) {
            char upperCase = Character.toUpperCase(c);
            return upperCase == 'I' || upperCase == 'V' || upperCase == 'X' || upperCase == 'L' || upperCase == 'C' || upperCase == 'D' || upperCase == 'M';
        }
    },
    TELUGU("telu") { // from class: net.time4j.format.NumberSystem.14
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "౦౧౨౩౪౫౬౭౮౯";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    },
    THAI("thai") { // from class: net.time4j.format.NumberSystem.15
        @Override // net.time4j.format.NumberSystem
        public String getDigits() {
            return "๐๑๒๓๔๕๖๗๘๙";
        }

        @Override // net.time4j.format.NumberSystem
        public boolean isDecimal() {
            return true;
        }
    };

    private static final char ETHIOPIC_HUNDRED = 4987;
    private static final char ETHIOPIC_ONE = 4969;
    private static final char ETHIOPIC_TEN = 4978;
    private static final char ETHIOPIC_TEN_THOUSAND = 4988;
    private final String code;
    private static final int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] LETTERS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "IV", "I"};
    private static final int[] D_FACTORS = {1, 12, 144, 1728, 20736};

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRomanCombination(char c, char c2) {
        if (c == 'C') {
            return c2 == 'M' || c2 == 'D';
        }
        if (c == 'I') {
            return c2 == 'X' || c2 == 'V';
        }
        if (c != 'X') {
            return false;
        }
        return c2 == 'C' || c2 == 'L';
    }

    NumberSystem(String str) {
        this.code = str;
    }

    public String toNumeral(int i) {
        if (isDecimal() && i >= 0) {
            int charAt = getDigits().charAt(0) - '0';
            String num = Integer.toString(i);
            StringBuilder sb = new StringBuilder();
            int length = num.length();
            for (int i2 = 0; i2 < length; i2++) {
                sb.append((char) (num.charAt(i2) + charAt));
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("Cannot convert: " + i);
    }

    public int toNumeral(int i, Appendable appendable) throws IOException {
        String numeral = toNumeral(i);
        appendable.append(numeral);
        return numeral.length();
    }

    public final int toInteger(String str) {
        return toInteger(str, Leniency.SMART);
    }

    public int toInteger(String str, Leniency leniency) {
        if (isDecimal()) {
            int charAt = getDigits().charAt(0) - '0';
            StringBuilder sb = new StringBuilder();
            int length = str.length();
            for (int i = 0; i < length; i++) {
                sb.append((char) (str.charAt(i) - charAt));
            }
            int parseInt = Integer.parseInt(sb.toString());
            if (parseInt >= 0) {
                return parseInt;
            }
            throw new NumberFormatException("Cannot convert negative number: " + str);
        }
        throw new NumberFormatException("Cannot convert: " + str);
    }

    public boolean contains(char c) {
        String digits = getDigits();
        int length = digits.length();
        for (int i = 0; i < length; i++) {
            if (digits.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    public String getDigits() {
        throw new AbstractMethodError();
    }

    public boolean isDecimal() {
        throw new AbstractMethodError();
    }

    public String getCode() {
        return this.code;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int addEthiopic(int i, int i2, int i3) {
        return MathUtils.safeAdd(i, MathUtils.safeMultiply(i2, i3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getValue(char c) {
        if (c == 'C') {
            return 100;
        }
        if (c == 'D') {
            return 500;
        }
        if (c == 'I') {
            return 1;
        }
        if (c == 'V') {
            return 5;
        }
        if (c == 'X') {
            return 10;
        }
        if (c == 'L') {
            return 50;
        }
        if (c == 'M') {
            return 1000;
        }
        throw new NumberFormatException("Invalid Roman digit: " + c);
    }
}
