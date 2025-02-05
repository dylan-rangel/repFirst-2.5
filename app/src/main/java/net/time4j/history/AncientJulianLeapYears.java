package net.time4j.history;

import java.util.Arrays;

/* loaded from: classes3.dex */
public final class AncientJulianLeapYears {
    private static final HistoricDate AD8 = HistoricDate.of(HistoricEra.AD, 8, 1, 1);
    private static final HistoricDate BC45 = HistoricDate.of(HistoricEra.BC, 45, 1, 1);
    private static final long MJD_OF_AD8 = -676021;
    public static final AncientJulianLeapYears SCALIGER;
    private static final int[] SEQUENCE_SCALIGER;
    private final Calculus calculus;
    private final int[] leaps;

    static {
        int[] iArr = {42, 39, 36, 33, 30, 27, 24, 21, 18, 15, 12, 9};
        SEQUENCE_SCALIGER = iArr;
        SCALIGER = new AncientJulianLeapYears(iArr);
    }

    private AncientJulianLeapYears(int... iArr) {
        int i;
        int[] iArr2 = new int[iArr.length];
        int i2 = 0;
        while (true) {
            i = 1;
            if (i2 >= iArr.length) {
                break;
            }
            iArr2[i2] = 1 - iArr[i2];
            i2++;
        }
        Arrays.sort(iArr2);
        this.leaps = iArr2;
        if (iArr2.length == 0) {
            throw new IllegalArgumentException("Missing leap years.");
        }
        int i3 = iArr2[0];
        if (i3 < -44 || iArr2[iArr2.length - 1] >= 8) {
            throw new IllegalArgumentException("Out of range: " + Arrays.toString(iArr));
        }
        while (i < iArr.length) {
            int i4 = iArr2[i];
            if (i4 == i3) {
                throw new IllegalArgumentException("Contains duplicates: " + Arrays.toString(iArr));
            }
            i++;
            i3 = i4;
        }
        this.calculus = new Calculus() { // from class: net.time4j.history.AncientJulianLeapYears.1
            @Override // net.time4j.history.Calculus
            public long toMJD(HistoricDate historicDate) {
                if (historicDate.compareTo(AncientJulianLeapYears.AD8) < 0) {
                    if (historicDate.compareTo(AncientJulianLeapYears.BC45) < 0) {
                        throw new IllegalArgumentException("Not valid before 45 BC: " + historicDate);
                    }
                    long j = AncientJulianLeapYears.MJD_OF_AD8;
                    int prolepticYear = getProlepticYear(historicDate);
                    for (int i5 = 7; i5 >= prolepticYear; i5--) {
                        j -= isLeapYear(i5) ? 366L : 365L;
                    }
                    for (int i6 = 1; i6 < historicDate.getMonth(); i6++) {
                        j += getMaximumDayOfMonth(prolepticYear, i6);
                    }
                    return (j + historicDate.getDayOfMonth()) - 1;
                }
                return CalendarAlgorithm.JULIAN.toMJD(historicDate);
            }

            @Override // net.time4j.history.Calculus
            public HistoricDate fromMJD(long j) {
                long j2 = AncientJulianLeapYears.MJD_OF_AD8;
                if (j >= AncientJulianLeapYears.MJD_OF_AD8) {
                    return CalendarAlgorithm.JULIAN.fromMJD(j);
                }
                int i5 = 7;
                while (i5 >= -44) {
                    j2 -= isLeapYear(i5) ? 366L : 365L;
                    if (j2 <= j) {
                        int i6 = 1;
                        while (i6 <= 12) {
                            long maximumDayOfMonth = getMaximumDayOfMonth(i5, i6) + j2;
                            if (maximumDayOfMonth > j) {
                                HistoricEra historicEra = i5 <= 0 ? HistoricEra.BC : HistoricEra.AD;
                                if (i5 <= 0) {
                                    i5 = 1 - i5;
                                }
                                return HistoricDate.of(historicEra, i5, i6, (int) ((j - j2) + 1));
                            }
                            i6++;
                            j2 = maximumDayOfMonth;
                        }
                    }
                    i5--;
                }
                throw new IllegalArgumentException("Not valid before 45 BC: " + j);
            }

            @Override // net.time4j.history.Calculus
            public boolean isValid(HistoricDate historicDate) {
                int prolepticYear;
                if (historicDate == null || (prolepticYear = getProlepticYear(historicDate)) < -44) {
                    return false;
                }
                if (prolepticYear >= 8) {
                    return CalendarAlgorithm.JULIAN.isValid(historicDate);
                }
                return historicDate.getDayOfMonth() <= getMaximumDayOfMonth(prolepticYear, historicDate.getMonth());
            }

            @Override // net.time4j.history.Calculus
            public int getMaximumDayOfMonth(HistoricDate historicDate) {
                if (historicDate.compareTo(AncientJulianLeapYears.AD8) < 0) {
                    if (historicDate.compareTo(AncientJulianLeapYears.BC45) < 0) {
                        throw new IllegalArgumentException("Not valid before 45 BC: " + historicDate);
                    }
                    return getMaximumDayOfMonth(getProlepticYear(historicDate), historicDate.getMonth());
                }
                return CalendarAlgorithm.JULIAN.getMaximumDayOfMonth(historicDate);
            }

            private int getMaximumDayOfMonth(int i5, int i6) {
                switch (i6) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        return 31;
                    case 2:
                        return isLeapYear(i5) ? 29 : 28;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        return 30;
                    default:
                        throw new IllegalArgumentException("Invalid month: " + i6);
                }
            }

            private int getProlepticYear(HistoricDate historicDate) {
                return historicDate.getEra().annoDomini(historicDate.getYearOfEra());
            }

            private boolean isLeapYear(int i5) {
                return Arrays.binarySearch(AncientJulianLeapYears.this.leaps, i5) >= 0;
            }
        };
    }

    public static AncientJulianLeapYears of(int... iArr) {
        if (Arrays.equals(iArr, SEQUENCE_SCALIGER)) {
            return SCALIGER;
        }
        return new AncientJulianLeapYears(iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AncientJulianLeapYears) && this.leaps == ((AncientJulianLeapYears) obj).leaps;
    }

    public int hashCode() {
        return Arrays.hashCode(this.leaps);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.leaps.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            int i2 = 1 - this.leaps[i];
            if (i2 > 0) {
                sb.append("BC ");
                sb.append(i2);
            } else {
                sb.append("AD ");
                sb.append(this.leaps[i]);
            }
        }
        return sb.toString();
    }

    int[] getPattern() {
        return this.leaps;
    }

    Calculus getCalculus() {
        return this.calculus;
    }
}
