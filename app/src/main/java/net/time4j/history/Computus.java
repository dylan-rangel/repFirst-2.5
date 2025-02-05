package net.time4j.history;

import net.time4j.PlainDate;
import net.time4j.engine.EpochDays;

/* loaded from: classes3.dex */
public enum Computus {
    WESTERN,
    EASTERN;

    public PlainDate easterSunday(int i) {
        int i2;
        int marchDay = marchDay(i);
        if (marchDay > 31) {
            i2 = 4;
            marchDay -= 31;
        } else {
            i2 = 3;
        }
        if (this == WESTERN && i > 1582) {
            return PlainDate.of(i, i2, marchDay);
        }
        return PlainDate.of(JulianMath.toMJD(i, i2, marchDay), EpochDays.MODIFIED_JULIAN_DATE);
    }

    int marchDay(int i) {
        if (i < 532) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        int i2 = i / 100;
        int i3 = 15;
        int i4 = 0;
        if (this == WESTERN && i > 1582) {
            int i5 = ((i2 * 3) + 3) / 4;
            i4 = 2 - i5;
            i3 = (i5 + 15) - (((i2 * 8) + 13) / 25);
        }
        int i6 = i % 19;
        int i7 = ((i6 * 19) + i3) % 30;
        int i8 = i7 / 29;
        int i9 = (i7 + 21) - (i8 + (((i7 / 28) - i8) * (i6 / 11)));
        return i9 + (7 - ((i9 - (7 - (((i + (i / 4)) + i4) % 7))) % 7));
    }
}
