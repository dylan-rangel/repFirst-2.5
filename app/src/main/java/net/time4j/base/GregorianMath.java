package net.time4j.base;

/* loaded from: classes3.dex */
public final class GregorianMath {
    public static final int MAX_YEAR = 999999999;
    public static final int MIN_YEAR = -999999999;
    private static final int OFFSET = 678881;

    public static int readDayOfMonth(long j) {
        return (int) (j & 255);
    }

    public static int readMonth(long j) {
        return (int) ((j >> 16) & 255);
    }

    public static int readYear(long j) {
        return (int) (j >> 32);
    }

    private GregorianMath() {
    }

    public static boolean isLeapYear(int i) {
        return (i <= 1900 || i >= 2100) ? ((i & 3) == 0 && i % 100 != 0) || i % 400 == 0 : (i & 3) == 0;
    }

    public static int getLengthOfMonth(int i, int i2) {
        switch (i2) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return isLeapYear(i) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid month: " + i2);
        }
    }

    public static boolean isValid(int i, int i2, int i3) {
        return i >= -999999999 && i <= 999999999 && i2 >= 1 && i2 <= 12 && i3 >= 1 && i3 <= getLengthOfMonth(i, i2);
    }

    public static void checkDate(int i, int i2, int i3) {
        if (i < -999999999 || i > 999999999) {
            throw new IllegalArgumentException("YEAR out of range: " + i);
        }
        if (i2 < 1 || i2 > 12) {
            throw new IllegalArgumentException("MONTH out of range: " + i2);
        }
        if (i3 < 1 || i3 > 31) {
            throw new IllegalArgumentException("DAY_OF_MONTH out of range: " + i3);
        }
        if (i3 <= getLengthOfMonth(i, i2)) {
            return;
        }
        throw new IllegalArgumentException("DAY_OF_MONTH exceeds month length in given year: " + toString(i, i2, i3));
    }

    public static int getDayOfWeek(int i, int i2, int i3) {
        if (i3 < 1 || i3 > 31) {
            throw new IllegalArgumentException("Day out of range: " + i3);
        }
        if (i3 > getLengthOfMonth(i, i2)) {
            throw new IllegalArgumentException("Day exceeds month length: " + toString(i, i2, i3));
        }
        int gaussianWeekTerm = gaussianWeekTerm(i2);
        int i4 = i % 100;
        int floorDivide = MathUtils.floorDivide(i, 100);
        if (i4 < 0) {
            i4 += 100;
        }
        if (i2 <= 2 && i4 - 1 < 0) {
            i4 = 99;
            floorDivide--;
        }
        int floorDivide2 = (((((i3 + gaussianWeekTerm) + i4) + (i4 / 4)) + MathUtils.floorDivide(floorDivide, 4)) - (floorDivide * 2)) % 7;
        return floorDivide2 <= 0 ? floorDivide2 + 7 : floorDivide2;
    }

    public static long toPackedDate(long j) {
        long j2;
        long safeAdd = MathUtils.safeAdd(j, 678881L);
        long floorDivide = MathUtils.floorDivide(safeAdd, 146097);
        int floorModulo = MathUtils.floorModulo(safeAdd, 146097);
        int i = 29;
        int i2 = 2;
        if (floorModulo == 146096) {
            j2 = (floorDivide + 1) * 400;
        } else {
            int i3 = floorModulo / 36524;
            int i4 = floorModulo % 36524;
            int i5 = i4 / 1461;
            int i6 = i4 % 1461;
            if (i6 == 1460) {
                j2 = (floorDivide * 400) + (i3 * 100) + ((i5 + 1) * 4);
            } else {
                int i7 = i6 / 365;
                int i8 = i6 % 365;
                j2 = (floorDivide * 400) + (i3 * 100) + (i5 * 4) + i7;
                i2 = 2 + (((i8 + 31) * 5) / 153);
                i = (i8 - (((i2 + 1) * 153) / 5)) + 123;
                if (i2 > 12) {
                    j2++;
                    i2 -= 12;
                }
            }
        }
        if (j2 >= -999999999 && j2 <= 999999999) {
            return i | (j2 << 32) | (i2 << 16);
        }
        throw new IllegalArgumentException("Year out of range: " + j2);
    }

    public static long toMJD(GregorianDate gregorianDate) {
        return toMJD(gregorianDate.getYear(), gregorianDate.getMonth(), gregorianDate.getDayOfMonth());
    }

    public static long toMJD(int i, int i2, int i3) {
        checkDate(i, i2, i3);
        long j = i;
        if (i2 < 3) {
            j--;
            i2 += 12;
        }
        return (((((((365 * j) + MathUtils.floorDivide(j, 4)) - MathUtils.floorDivide(j, 100)) + MathUtils.floorDivide(j, 400)) + (((i2 + 1) * 153) / 5)) - 123) + i3) - 678881;
    }

    static String toString(int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append('-');
        if (i2 < 10) {
            sb.append('0');
        }
        sb.append(i2);
        sb.append('-');
        if (i3 < 10) {
            sb.append('0');
        }
        sb.append(i3);
        return sb.toString();
    }

    private static int gaussianWeekTerm(int i) {
        switch (i) {
            case 1:
                return 28;
            case 2:
                return 31;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 7;
            case 6:
                return 10;
            case 7:
                return 12;
            case 8:
                return 15;
            case 9:
                return 18;
            case 10:
                return 20;
            case 11:
                return 23;
            case 12:
                return 25;
            default:
                throw new IllegalArgumentException("Month out of range: " + i);
        }
    }
}
