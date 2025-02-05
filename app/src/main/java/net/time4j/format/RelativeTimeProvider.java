package net.time4j.format;

import java.util.Locale;
import net.time4j.Weekday;

/* loaded from: classes3.dex */
public interface RelativeTimeProvider extends UnitPatternProvider {
    String getShortDayPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortHourPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortMinutePattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortMonthPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortSecondPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortWeekPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getShortYearPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getTodayWord(Locale locale);

    String getTomorrowWord(Locale locale);

    String getYesterdayWord(Locale locale);

    String labelForLast(Weekday weekday, Locale locale);

    String labelForNext(Weekday weekday, Locale locale);
}
