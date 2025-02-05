package net.time4j.format;

import java.util.Locale;

/* loaded from: classes3.dex */
public interface UnitPatternProvider {
    String getDayPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getDayPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getHourPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getHourPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getListPattern(Locale locale, TextWidth textWidth, int i);

    String getMicroPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getMilliPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getMinutePattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getMinutePattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getMonthPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getMonthPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getNanoPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getNowWord(Locale locale);

    String getSecondPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getSecondPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getWeekPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getWeekPattern(Locale locale, boolean z, PluralCategory pluralCategory);

    String getYearPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory);

    String getYearPattern(Locale locale, boolean z, PluralCategory pluralCategory);
}
