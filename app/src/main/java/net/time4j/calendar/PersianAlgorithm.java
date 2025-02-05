package net.time4j.calendar;

import net.time4j.CalendarUnit;
import net.time4j.PlainDate;
import net.time4j.PlainTimestamp;
import net.time4j.base.MathUtils;
import net.time4j.calendar.astro.AstronomicalSeason;
import net.time4j.calendar.astro.SolarTime;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.EpochDays;
import net.time4j.format.Attributes;
import net.time4j.tz.OffsetSign;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public enum PersianAlgorithm {
    BORKOWSKI { // from class: net.time4j.calendar.PersianAlgorithm.1
        @Override // net.time4j.calendar.PersianAlgorithm
        boolean isLeapYear(int i, ZonalOffset zonalOffset) {
            PersianAlgorithm.checkYear(i);
            return transform(new PersianCalendar(i + 1, 1, 1), zonalOffset) - transform(new PersianCalendar(i, 1, 1), zonalOffset) == 366;
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        PersianCalendar transform(long j, ZonalOffset zonalOffset) {
            PlainDate of = PlainDate.of(j, EpochDays.UTC);
            int year = of.getYear() - 621;
            if (of.getMonth() < 3) {
                year--;
            }
            long between = CalendarUnit.DAYS.between(vernalEquinox(year), of);
            while (between < 0) {
                year--;
                between = CalendarUnit.DAYS.between(vernalEquinox(year), of);
            }
            int i = 1;
            while (i < 12) {
                long j2 = i <= 6 ? 31 : 30;
                if (between < j2) {
                    break;
                }
                between -= j2;
                i++;
            }
            return PersianCalendar.of(year, i, (int) (between + 1));
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        long transform(PersianCalendar persianCalendar, ZonalOffset zonalOffset) {
            int year = persianCalendar.getYear();
            int value = persianCalendar.getMonth().getValue();
            return vernalEquinox(year).getDaysSinceEpochUTC() + (((((value - 1) * 31) - ((value / 7) * (value - 7))) + persianCalendar.getDayOfMonth()) - 1);
        }

        private PlainDate vernalEquinox(int i) {
            int[] iArr = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
            int i2 = iArr[19];
            if (i < 1 || i >= i2) {
                throw new IllegalArgumentException("Persian year out of range 1-" + i2 + ": " + i);
            }
            int i3 = i + 621;
            int i4 = -14;
            int i5 = 0;
            int i6 = iArr[0];
            int i7 = 1;
            while (true) {
                if (i7 >= 20) {
                    break;
                }
                int i8 = iArr[i7];
                int i9 = i8 - i6;
                if (i < i8) {
                    i5 = i9;
                    break;
                }
                i4 += ((i9 / 33) * 8) + ((i9 % 33) / 4);
                i7++;
                i6 = i8;
                i5 = i9;
            }
            int i10 = i - i6;
            int i11 = i4 + ((i10 / 33) * 8) + (((i10 % 33) + 3) / 4);
            if (i5 % 33 == 4 && i5 - i10 == 4) {
                i11++;
            }
            return PlainDate.of(i3, 3, (i11 + 20) - (((i3 / 4) - ((((i3 / 100) + 1) * 3) / 4)) - 150));
        }
    },
    KHAYYAM { // from class: net.time4j.calendar.PersianAlgorithm.2
        @Override // net.time4j.calendar.PersianAlgorithm
        boolean isLeapYear(int i, ZonalOffset zonalOffset) {
            PersianAlgorithm.checkYear(i);
            int i2 = i % 33;
            return i2 == 1 || i2 == 5 || i2 == 9 || i2 == 13 || i2 == 17 || i2 == 22 || i2 == 26 || i2 == 30;
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        PersianCalendar transform(long j, ZonalOffset zonalOffset) {
            PersianAlgorithm.checkRange(j);
            long j2 = j + PersianAlgorithm.REFERENCE_ZERO_KHAYYAM;
            int i = (int) (j2 % 12053);
            int i2 = ((int) (j2 / 12053)) * 33;
            int i3 = 0;
            while (i3 < 33) {
                int i4 = (i3 == 1 || i3 == 5 || i3 == 9 || i3 == 13 || i3 == 17 || i3 == 22 || i3 == 26 || i3 == 30) ? 366 : 365;
                if (i < i4) {
                    break;
                }
                i -= i4;
                i2++;
                i3++;
            }
            int i5 = 1;
            int i6 = 1;
            while (i5 < 12) {
                int i7 = i5 <= 6 ? 31 : 30;
                if (i < i7) {
                    break;
                }
                i -= i7;
                i6++;
                i5++;
            }
            return new PersianCalendar(i2, i6, 1 + i);
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        long transform(PersianCalendar persianCalendar, ZonalOffset zonalOffset) {
            int year = persianCalendar.getYear();
            long j = ((year / 33) * PersianAlgorithm.LENGTH_OF_KHAYYAM_CYCLE) - PersianAlgorithm.REFERENCE_ZERO_KHAYYAM;
            int i = 0;
            while (i < year % 33) {
                j += (i == 1 || i == 5 || i == 9 || i == 13 || i == 17 || i == 22 || i == 26 || i == 30) ? 366 : 365;
                i++;
            }
            return ((j + (persianCalendar.getMonth().getValue() <= 7 ? (r7 - 1) * 31 : ((r7 - 1) * 30) + 6)) + persianCalendar.getDayOfMonth()) - 1;
        }
    },
    BIRASHK { // from class: net.time4j.calendar.PersianAlgorithm.3
        @Override // net.time4j.calendar.PersianAlgorithm
        boolean isLeapYear(int i, ZonalOffset zonalOffset) {
            PersianAlgorithm.checkYear(i);
            return MathUtils.floorModulo((MathUtils.floorModulo(i + (-474), 2820) + 512) * 31, 128) < 31;
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        PersianCalendar transform(long j, ZonalOffset zonalOffset) {
            PersianAlgorithm.checkRange(j);
            int i = (int) (j - PersianAlgorithm.START_OF_BIRASHK_CYCLE);
            int floorDivide = MathUtils.floorDivide(i, 1029983);
            int floorModulo = MathUtils.floorModulo(i, 1029983);
            int floorDivide2 = (floorDivide * 2820) + 474 + (floorModulo == 1029982 ? 2820 : MathUtils.floorDivide((floorModulo * 128) + 46878, 46751));
            int transform = (int) (j - transform(new PersianCalendar(floorDivide2, 1, 1), zonalOffset));
            int i2 = 1;
            int i3 = 1;
            while (i2 < 12) {
                int i4 = i2 <= 6 ? 31 : 30;
                if (transform < i4) {
                    break;
                }
                transform -= i4;
                i3++;
                i2++;
            }
            return new PersianCalendar(floorDivide2, i3, 1 + transform);
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        long transform(PersianCalendar persianCalendar, ZonalOffset zonalOffset) {
            int floorModulo = MathUtils.floorModulo(persianCalendar.getYear() - 474, 2820) + 474;
            return ((MathUtils.floorDivide(r8, 2820) * 1029983) - 492998) + ((floorModulo - 1) * 365) + MathUtils.floorDivide((floorModulo * 31) - 5, 128) + (persianCalendar.getMonth().getValue() <= 7 ? (r8 - 1) * 31 : ((r8 - 1) * 30) + 6) + persianCalendar.getDayOfMonth();
        }
    },
    ASTRONOMICAL { // from class: net.time4j.calendar.PersianAlgorithm.4
        @Override // net.time4j.calendar.PersianAlgorithm
        int getMaxPersianYear() {
            return 2378;
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        boolean isLeapYear(int i, ZonalOffset zonalOffset) {
            if (i >= 1 && i <= getMaxPersianYear()) {
                return transform(new PersianCalendar(i + 1, 1, 1), zonalOffset) - transform(new PersianCalendar(i, 1, 1), zonalOffset) == 366;
            }
            throw new IllegalArgumentException("Out of range: " + i);
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        PersianCalendar transform(long j, ZonalOffset zonalOffset) {
            if (j < -492997 || j > 375548) {
                throw new IllegalArgumentException("Out of range: " + j);
            }
            PlainDate of = PlainDate.of(j, EpochDays.UTC);
            int year = of.getYear() - 621;
            if (of.getMonth() < 3) {
                year--;
            }
            long between = CalendarUnit.DAYS.between(vernalEquinox(year, zonalOffset), of);
            while (between < 0) {
                year--;
                between = CalendarUnit.DAYS.between(vernalEquinox(year, zonalOffset), of);
            }
            int i = 1;
            while (i < 12) {
                long j2 = i <= 6 ? 31 : 30;
                if (between < j2) {
                    break;
                }
                between -= j2;
                i++;
            }
            return new PersianCalendar(year, i, (int) (between + 1));
        }

        @Override // net.time4j.calendar.PersianAlgorithm
        long transform(PersianCalendar persianCalendar, ZonalOffset zonalOffset) {
            int year = persianCalendar.getYear();
            int value = persianCalendar.getMonth().getValue();
            return vernalEquinox(year, zonalOffset).getDaysSinceEpochUTC() + (((((value - 1) * 31) - ((value / 7) * (value - 7))) + persianCalendar.getDayOfMonth()) - 1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private PlainDate vernalEquinox(int i, ZonalOffset zonalOffset) {
            PlainTimestamp plainTimestamp = (PlainTimestamp) AstronomicalSeason.VERNAL_EQUINOX.inYear(i + 621).get(SolarTime.apparentAt(zonalOffset));
            if (plainTimestamp.getHour() >= 12) {
                return (PlainDate) plainTimestamp.getCalendarDate().plus(1L, CalendarUnit.DAYS);
            }
            return plainTimestamp.getCalendarDate();
        }
    };

    private static final int LENGTH_OF_KHAYYAM_CYCLE = 12053;
    private static final long REFERENCE_ZERO_KHAYYAM = 493363;
    private static final long START_OF_BIRASHK_CYCLE = -319872;
    static final ZonalOffset STD_OFFSET = ZonalOffset.ofHoursMinutes(OffsetSign.AHEAD_OF_UTC, 3, 30);
    private static final AttributeKey<PersianAlgorithm> ATTRIBUTE = Attributes.createKey("PERSIAN_ALGORITHM", PersianAlgorithm.class);

    int getMaxPersianYear() {
        return 3000;
    }

    abstract boolean isLeapYear(int i, ZonalOffset zonalOffset);

    abstract long transform(PersianCalendar persianCalendar, ZonalOffset zonalOffset);

    abstract PersianCalendar transform(long j, ZonalOffset zonalOffset);

    public static AttributeKey<PersianAlgorithm> attribute() {
        return ATTRIBUTE;
    }

    public boolean isLeapYear(int i) {
        return isLeapYear(i, STD_OFFSET);
    }

    boolean isValid(int i, int i2, int i3, ZonalOffset zonalOffset) {
        if (i < 1 || i > getMaxPersianYear() || i2 < 1 || i2 > 12 || i3 < 1) {
            return false;
        }
        if (i2 <= 6) {
            return i3 <= 31;
        }
        if (i2 <= 11) {
            return i3 <= 30;
        }
        return i3 <= (isLeapYear(i, zonalOffset) ? 30 : 29);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkRange(long j) {
        CalendarSystem<PersianCalendar> calendarSystem = PersianCalendar.axis().getCalendarSystem();
        if (j < calendarSystem.getMinimumSinceUTC() || j > calendarSystem.getMaximumSinceUTC()) {
            throw new IllegalArgumentException("Out of range: " + j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkYear(int i) {
        if (i < 1 || i > 3000) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
    }
}
