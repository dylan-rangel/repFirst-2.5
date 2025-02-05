package net.time4j.history;

import net.time4j.base.GregorianMath;

/* loaded from: classes3.dex */
enum CalendarAlgorithm implements Calculus {
    GREGORIAN { // from class: net.time4j.history.CalendarAlgorithm.1
        @Override // net.time4j.history.Calculus
        public long toMJD(HistoricDate historicDate) {
            return GregorianMath.toMJD(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.history.Calculus
        public HistoricDate fromMJD(long j) {
            long packedDate = GregorianMath.toPackedDate(j);
            int readYear = GregorianMath.readYear(packedDate);
            int readMonth = GregorianMath.readMonth(packedDate);
            int readDayOfMonth = GregorianMath.readDayOfMonth(packedDate);
            HistoricEra historicEra = readYear <= 0 ? HistoricEra.BC : HistoricEra.AD;
            if (readYear <= 0) {
                readYear = 1 - readYear;
            }
            return new HistoricDate(historicEra, readYear, readMonth, readDayOfMonth);
        }

        @Override // net.time4j.history.Calculus
        public boolean isValid(HistoricDate historicDate) {
            return GregorianMath.isValid(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.history.Calculus
        public int getMaximumDayOfMonth(HistoricDate historicDate) {
            return GregorianMath.getLengthOfMonth(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth());
        }
    },
    JULIAN { // from class: net.time4j.history.CalendarAlgorithm.2
        @Override // net.time4j.history.Calculus
        public long toMJD(HistoricDate historicDate) {
            return JulianMath.toMJD(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.history.Calculus
        public HistoricDate fromMJD(long j) {
            long packedDate = JulianMath.toPackedDate(j);
            int readYear = JulianMath.readYear(packedDate);
            int readMonth = JulianMath.readMonth(packedDate);
            int readDayOfMonth = JulianMath.readDayOfMonth(packedDate);
            HistoricEra historicEra = readYear <= 0 ? HistoricEra.BC : HistoricEra.AD;
            if (readYear <= 0) {
                readYear = 1 - readYear;
            }
            return new HistoricDate(historicEra, readYear, readMonth, readDayOfMonth);
        }

        @Override // net.time4j.history.Calculus
        public boolean isValid(HistoricDate historicDate) {
            return JulianMath.isValid(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.history.Calculus
        public int getMaximumDayOfMonth(HistoricDate historicDate) {
            return JulianMath.getLengthOfMonth(CalendarAlgorithm.getProlepticYear(historicDate), historicDate.getMonth());
        }
    },
    SWEDISH { // from class: net.time4j.history.CalendarAlgorithm.3
        @Override // net.time4j.history.Calculus
        public long toMJD(HistoricDate historicDate) {
            int prolepticYear = CalendarAlgorithm.getProlepticYear(historicDate);
            if (historicDate.getDayOfMonth() == 30 && historicDate.getMonth() == 2 && prolepticYear == 1712) {
                return -53576L;
            }
            return JulianMath.toMJD(prolepticYear, historicDate.getMonth(), historicDate.getDayOfMonth()) - 1;
        }

        @Override // net.time4j.history.Calculus
        public HistoricDate fromMJD(long j) {
            if (j == -53576) {
                return new HistoricDate(HistoricEra.AD, 1712, 2, 30);
            }
            return JULIAN.fromMJD(j + 1);
        }

        @Override // net.time4j.history.Calculus
        public boolean isValid(HistoricDate historicDate) {
            int prolepticYear = CalendarAlgorithm.getProlepticYear(historicDate);
            if (historicDate.getDayOfMonth() == 30 && historicDate.getMonth() == 2 && prolepticYear == 1712) {
                return true;
            }
            return JulianMath.isValid(prolepticYear, historicDate.getMonth(), historicDate.getDayOfMonth());
        }

        @Override // net.time4j.history.Calculus
        public int getMaximumDayOfMonth(HistoricDate historicDate) {
            int prolepticYear = CalendarAlgorithm.getProlepticYear(historicDate);
            if (historicDate.getMonth() == 2 && prolepticYear == 1712) {
                return 30;
            }
            return JulianMath.getLengthOfMonth(prolepticYear, historicDate.getMonth());
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static int getProlepticYear(HistoricDate historicDate) {
        return historicDate.getEra().annoDomini(historicDate.getYearOfEra());
    }
}
