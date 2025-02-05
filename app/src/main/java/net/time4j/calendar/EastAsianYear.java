package net.time4j.calendar;

import net.time4j.base.MathUtils;

/* loaded from: classes3.dex */
public abstract class EastAsianYear {
    public abstract int getElapsedCyclicYears();

    public static EastAsianYear forGregorian(final int i) {
        return new EastAsianYear() { // from class: net.time4j.calendar.EastAsianYear.1
            @Override // net.time4j.calendar.EastAsianYear
            public int getElapsedCyclicYears() {
                return MathUtils.safeAdd(i, 2636);
            }
        };
    }

    public static EastAsianYear forMinguo(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Minguo year must not be smaller than 1: " + i);
        }
        return forGregorian(MathUtils.safeAdd(i, 1911));
    }

    public static EastAsianYear forDangi(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Dangi year must not be smaller than 1: " + i);
        }
        return forGregorian(MathUtils.safeAdd(i, -2333));
    }

    public static EastAsianYear forJuche(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Juche year must not be smaller than 1: " + i);
        }
        return forGregorian(MathUtils.safeAdd(i, 1911));
    }

    public final int getCycle() {
        return MathUtils.floorDivide((getElapsedCyclicYears() + 1) - 1, 60) + 1;
    }

    public final CyclicYear getYearOfCycle() {
        int floorModulo = MathUtils.floorModulo(getElapsedCyclicYears() + 1, 60);
        return CyclicYear.of(floorModulo != 0 ? floorModulo : 60);
    }
}
