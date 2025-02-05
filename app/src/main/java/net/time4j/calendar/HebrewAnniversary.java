package net.time4j.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.time4j.PlainDate;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.ChronoFunction;

/* loaded from: classes3.dex */
public enum HebrewAnniversary {
    BIRTHDAY { // from class: net.time4j.calendar.HebrewAnniversary.1
        @Override // net.time4j.calendar.HebrewAnniversary
        public ChronoFunction<CalendarDate, HebrewCalendar> inHebrewYear(final int i) {
            return new ChronoFunction<CalendarDate, HebrewCalendar>() { // from class: net.time4j.calendar.HebrewAnniversary.1.1
                @Override // net.time4j.engine.ChronoFunction
                public HebrewCalendar apply(CalendarDate calendarDate) {
                    HebrewCalendar convert = HebrewAnniversary.convert(calendarDate);
                    int dayOfMonth = convert.getDayOfMonth();
                    if (convert.getMonth() == HebrewMonth.ADAR_II) {
                        int i2 = i;
                        return HebrewCalendar.ofBiblical(i2, HebrewCalendar.isLeapYear(i2) ? 13 : 12, dayOfMonth);
                    }
                    HebrewMonth month = convert.getMonth();
                    if (month == HebrewMonth.ADAR_I && !HebrewCalendar.isLeapYear(i)) {
                        month = HebrewMonth.ADAR_II;
                    }
                    if (dayOfMonth <= 29) {
                        return HebrewCalendar.of(i, month, dayOfMonth);
                    }
                    return HebrewCalendar.of(i, month, 1).plus(CalendarDays.of(dayOfMonth - 1));
                }
            };
        }
    },
    YAHRZEIT { // from class: net.time4j.calendar.HebrewAnniversary.2
        @Override // net.time4j.calendar.HebrewAnniversary
        public ChronoFunction<CalendarDate, HebrewCalendar> inHebrewYear(final int i) {
            return new ChronoFunction<CalendarDate, HebrewCalendar>() { // from class: net.time4j.calendar.HebrewAnniversary.2.1
                @Override // net.time4j.engine.ChronoFunction
                public HebrewCalendar apply(CalendarDate calendarDate) {
                    HebrewCalendar convert = HebrewAnniversary.convert(calendarDate);
                    int year = convert.getYear();
                    HebrewMonth month = convert.getMonth();
                    int dayOfMonth = convert.getDayOfMonth();
                    if (month == HebrewMonth.HESHVAN && dayOfMonth == 30 && HebrewCalendar.lengthOfMonth(year + 1, HebrewMonth.HESHVAN) == 29) {
                        return HebrewCalendar.of(i, HebrewMonth.KISLEV, 1).minus(CalendarDays.ONE);
                    }
                    if (month == HebrewMonth.KISLEV && dayOfMonth == 30 && HebrewCalendar.lengthOfMonth(year + 1, HebrewMonth.KISLEV) == 29) {
                        return HebrewCalendar.of(i, HebrewMonth.TEVET, 1).minus(CalendarDays.ONE);
                    }
                    if (month == HebrewMonth.ADAR_II && HebrewCalendar.isLeapYear(year)) {
                        return HebrewCalendar.of(i, HebrewMonth.ADAR_II, dayOfMonth);
                    }
                    if (month.getBiblicalValue(false) == 12 && dayOfMonth == 30 && !HebrewCalendar.isLeapYear(i)) {
                        return HebrewCalendar.of(i, HebrewMonth.SHEVAT, 30);
                    }
                    return HebrewCalendar.ofBiblical(i, month.getBiblicalValue(false), 1).plus(CalendarDays.of(dayOfMonth - 1));
                }
            };
        }
    };

    public ChronoFunction<CalendarDate, HebrewCalendar> inHebrewYear(int i) {
        throw new AbstractMethodError();
    }

    public ChronoFunction<CalendarDate, List<PlainDate>> inGregorianYear(final int i) {
        return new ChronoFunction<CalendarDate, List<PlainDate>>() { // from class: net.time4j.calendar.HebrewAnniversary.3
            @Override // net.time4j.engine.ChronoFunction
            public List<PlainDate> apply(CalendarDate calendarDate) {
                HebrewCalendar convert = HebrewAnniversary.convert(calendarDate);
                int year = ((HebrewCalendar) PlainDate.of(i, 1, 1).transform(HebrewCalendar.class)).getYear();
                PlainDate plainDate = (PlainDate) HebrewAnniversary.this.inHebrewYear(year).apply(convert).transform(PlainDate.class);
                PlainDate plainDate2 = (PlainDate) HebrewAnniversary.this.inHebrewYear(year + 1).apply(convert).transform(PlainDate.class);
                ArrayList arrayList = new ArrayList(2);
                if (plainDate.getYear() == i) {
                    arrayList.add(plainDate);
                }
                if (plainDate2.getYear() == i) {
                    arrayList.add(plainDate2);
                }
                return Collections.unmodifiableList(arrayList);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HebrewCalendar convert(CalendarDate calendarDate) {
        if (calendarDate instanceof HebrewCalendar) {
            return (HebrewCalendar) calendarDate;
        }
        return HebrewCalendar.axis().getCalendarSystem().transform(calendarDate.getDaysSinceEpochUTC());
    }
}
