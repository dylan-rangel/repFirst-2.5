package net.time4j.calendar;

import java.io.ObjectStreamException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.calendar.SexagesimalName;

/* loaded from: classes3.dex */
public final class CyclicYear extends SexagesimalName {
    private static final CyclicYear[] INSTANCES;
    private static final long serialVersionUID = 4908662352833192131L;

    static {
        CyclicYear[] cyclicYearArr = new CyclicYear[60];
        int i = 0;
        while (i < 60) {
            int i2 = i + 1;
            cyclicYearArr[i] = new CyclicYear(i2);
            i = i2;
        }
        INSTANCES = cyclicYearArr;
    }

    private CyclicYear(int i) {
        super(i);
    }

    public static CyclicYear of(int i) {
        if (i < 1 || i > 60) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return INSTANCES[i - 1];
    }

    public static CyclicYear of(SexagesimalName.Stem stem, SexagesimalName.Branch branch) {
        return of(SexagesimalName.of(stem, branch).getNumber());
    }

    public static CyclicYear parse(String str, Locale locale) throws ParseException {
        return of(SexagesimalName.parse(str, locale).getNumber());
    }

    @Override // net.time4j.calendar.SexagesimalName
    public CyclicYear roll(int i) {
        return i == 0 ? this : of(super.roll(i).getNumber());
    }

    public EastAsianYear inQingDynasty(ChineseEra chineseEra) {
        if (chineseEra.isQingDynasty()) {
            if (chineseEra == ChineseEra.QING_KANGXI_1662_1723 && getNumber() == 39) {
                throw new IllegalArgumentException("Ambivalent cyclic year in Kangxi-era (1662 or 1722): " + getDisplayName(Locale.ROOT));
            }
            final int startAsGregorianYear = chineseEra.getStartAsGregorianYear();
            final int number = getNumber() - EastAsianYear.forGregorian(startAsGregorianYear).getYearOfCycle().getNumber();
            return new EastAsianYear() { // from class: net.time4j.calendar.CyclicYear.1
                @Override // net.time4j.calendar.EastAsianYear
                public int getElapsedCyclicYears() {
                    int i = startAsGregorianYear;
                    int i2 = number;
                    return i + i2 + (i2 < 0 ? 2696 : 2636);
                }
            };
        }
        throw new IllegalArgumentException("Chinese era must be related to a Qing dynasty.");
    }

    public EastAsianYear inCycle(final int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Cycle number must not be smaller than 1: " + i);
        }
        return new EastAsianYear() { // from class: net.time4j.calendar.CyclicYear.2
            @Override // net.time4j.calendar.EastAsianYear
            public int getElapsedCyclicYears() {
                return (((i - 1) * 60) + CyclicYear.this.getNumber()) - 1;
            }
        };
    }

    static CyclicYear parse(CharSequence charSequence, ParsePosition parsePosition, Locale locale, boolean z) {
        SexagesimalName parse = SexagesimalName.parse(charSequence, parsePosition, locale, z);
        if (parse == null) {
            return null;
        }
        return of(parse.getNumber());
    }

    @Override // net.time4j.calendar.SexagesimalName
    Object readResolve() throws ObjectStreamException {
        return of(super.getNumber());
    }
}
