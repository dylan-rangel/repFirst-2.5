package net.time4j.history;

/* loaded from: classes3.dex */
public final class HistoricDate implements Comparable<HistoricDate> {
    private final int dom;
    private final HistoricEra era;
    private final int month;
    private final int yearOfEra;

    HistoricDate(HistoricEra historicEra, int i, int i2, int i3) {
        this.era = historicEra;
        this.yearOfEra = i;
        this.month = i2;
        this.dom = i3;
    }

    public static HistoricDate of(HistoricEra historicEra, int i, int i2, int i3) {
        return of(historicEra, i, i2, i3, YearDefinition.DUAL_DATING, NewYearStrategy.DEFAULT);
    }

    public static HistoricDate of(HistoricEra historicEra, int i, int i2, int i3, YearDefinition yearDefinition, NewYearStrategy newYearStrategy) {
        if (historicEra == null) {
            throw new NullPointerException("Missing historic era.");
        }
        if (i3 < 1 || i3 > 31) {
            throw new IllegalArgumentException("Day of month out of range: " + toString(historicEra, i, i2, i3));
        }
        if (i2 < 1 || i2 > 12) {
            throw new IllegalArgumentException("Month out of range: " + toString(historicEra, i, i2, i3));
        }
        if (historicEra == HistoricEra.BYZANTINE) {
            if (i < 0 || (i == 0 && i2 < 9)) {
                throw new IllegalArgumentException("Before creation of the world: " + toString(historicEra, i, i2, i3));
            }
        } else if (i < 1) {
            throw new IllegalArgumentException("Year of era must be positive: " + toString(historicEra, i, i2, i3));
        }
        if (!yearDefinition.equals(YearDefinition.DUAL_DATING)) {
            i = newYearStrategy.rule(historicEra, i).standardYear(yearDefinition == YearDefinition.AFTER_NEW_YEAR, newYearStrategy, historicEra, i, i2, i3);
        }
        return new HistoricDate(historicEra, i, i2, i3);
    }

    public HistoricEra getEra() {
        return this.era;
    }

    public int getYearOfEra() {
        return this.yearOfEra;
    }

    public int getYearOfEra(NewYearStrategy newYearStrategy) {
        return newYearStrategy.displayedYear(this);
    }

    public int getMonth() {
        return this.month;
    }

    public int getDayOfMonth() {
        return this.dom;
    }

    @Override // java.lang.Comparable
    public int compareTo(HistoricDate historicDate) {
        int annoDomini = this.era.annoDomini(this.yearOfEra);
        int annoDomini2 = historicDate.era.annoDomini(historicDate.yearOfEra);
        if (annoDomini < annoDomini2) {
            return -1;
        }
        if (annoDomini > annoDomini2) {
            return 1;
        }
        int month = getMonth() - historicDate.getMonth();
        if (month == 0) {
            month = getDayOfMonth() - historicDate.getDayOfMonth();
        }
        if (month < 0) {
            return -1;
        }
        return month > 0 ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistoricDate)) {
            return false;
        }
        HistoricDate historicDate = (HistoricDate) obj;
        return this.era == historicDate.era && this.yearOfEra == historicDate.yearOfEra && this.month == historicDate.month && this.dom == historicDate.dom;
    }

    public int hashCode() {
        int i = (this.yearOfEra * 1000) + (this.month * 32) + this.dom;
        return this.era == HistoricEra.AD ? i : -i;
    }

    public String toString() {
        return toString(this.era, this.yearOfEra, this.month, this.dom);
    }

    private static String toString(HistoricEra historicEra, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(historicEra);
        sb.append('-');
        String valueOf = String.valueOf(i);
        for (int length = 4 - valueOf.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(valueOf);
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
}
