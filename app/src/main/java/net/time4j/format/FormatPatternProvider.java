package net.time4j.format;

import java.util.Locale;

/* loaded from: classes3.dex */
public interface FormatPatternProvider {
    String getDatePattern(DisplayMode displayMode, Locale locale);

    String getDateTimePattern(DisplayMode displayMode, DisplayMode displayMode2, Locale locale);

    String getIntervalPattern(Locale locale);

    String getTimePattern(DisplayMode displayMode, Locale locale);
}
