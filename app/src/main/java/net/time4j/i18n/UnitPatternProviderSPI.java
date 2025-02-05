package net.time4j.i18n;

import com.facebook.react.uimanager.ViewProps;
import java.util.Locale;
import java.util.MissingResourceException;
import net.time4j.Weekday;
import net.time4j.format.PluralCategory;
import net.time4j.format.RelativeTimeProvider;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public final class UnitPatternProviderSPI implements RelativeTimeProvider {
    @Override // net.time4j.format.UnitPatternProvider
    public String getYearPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'Y', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMonthPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'M', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getWeekPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'W', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getDayPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'D', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getHourPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'H', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMinutePattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'N', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getSecondPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, 'S', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMilliPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, '3', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMicroPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, '6', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getNanoPattern(Locale locale, TextWidth textWidth, PluralCategory pluralCategory) {
        return getUnitPattern(locale, '9', textWidth, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getYearPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'Y', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMonthPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'M', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getWeekPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'W', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getDayPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'D', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getHourPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'H', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getMinutePattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'N', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getSecondPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'S', z, pluralCategory);
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getNowWord(Locale locale) {
        return getPattern(locale, "reltime/relpattern", "now", null, PluralCategory.OTHER);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortYearPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'y', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortMonthPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'm', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortWeekPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'w', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortDayPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'd', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortHourPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'h', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortMinutePattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 'n', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getShortSecondPattern(Locale locale, boolean z, PluralCategory pluralCategory) {
        return getRelativePattern(locale, 's', z, pluralCategory);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getYesterdayWord(Locale locale) {
        return getPattern(locale, "reltime/relpattern", "yesterday", null, PluralCategory.OTHER);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getTodayWord(Locale locale) {
        return getPattern(locale, "reltime/relpattern", "today", null, PluralCategory.OTHER);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String getTomorrowWord(Locale locale) {
        return getPattern(locale, "reltime/relpattern", "tomorrow", null, PluralCategory.OTHER);
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String labelForLast(Weekday weekday, Locale locale) {
        return getLabel(locale, weekday.name().substring(0, 3).toLowerCase() + "-");
    }

    @Override // net.time4j.format.RelativeTimeProvider
    public String labelForNext(Weekday weekday, Locale locale) {
        return getLabel(locale, weekday.name().substring(0, 3).toLowerCase() + "+");
    }

    @Override // net.time4j.format.UnitPatternProvider
    public String getListPattern(Locale locale, TextWidth textWidth, int i) {
        int i2;
        if (i < 2) {
            throw new IllegalArgumentException("Size must be greater than 1.");
        }
        PropertyBundle load = PropertyBundle.load("i18n/units/upattern", locale);
        String buildListKey = buildListKey(textWidth, String.valueOf(i));
        if (load.containsKey(buildListKey)) {
            return load.getString(buildListKey);
        }
        String string = load.getString(buildListKey(textWidth, ViewProps.END));
        if (i == 2) {
            return string;
        }
        String string2 = load.getString(buildListKey(textWidth, ViewProps.START));
        String string3 = load.getString(buildListKey(textWidth, "middle"));
        String replace = replace(replace(string, '1', i - 1), '0', i - 2);
        int i3 = i - 3;
        String str = replace;
        while (i3 >= 0) {
            String str2 = i3 == 0 ? string2 : string3;
            int length = str2.length();
            int i4 = length - 1;
            while (true) {
                if (i4 < 0) {
                    i2 = -1;
                    break;
                }
                if (i4 >= 2 && str2.charAt(i4) == '}' && str2.charAt(i4 - 1) == '1') {
                    i2 = i4 - 2;
                    if (str2.charAt(i2) == '{') {
                        break;
                    }
                }
                i4--;
            }
            if (i2 > -1) {
                replace = str2.substring(0, i2) + str;
                if (i2 < length - 3) {
                    replace = replace + str2.substring(i2 + 3);
                }
            }
            if (i3 > 0) {
                str = replace(replace, '0', i3);
            }
            i3--;
        }
        return replace;
    }

    private String getUnitPattern(Locale locale, char c, TextWidth textWidth, PluralCategory pluralCategory) {
        return getPattern(locale, "units/upattern", buildKey(c, textWidth, pluralCategory), buildKey(c, textWidth, PluralCategory.OTHER), pluralCategory);
    }

    private String getRelativePattern(Locale locale, char c, boolean z, PluralCategory pluralCategory) {
        return getPattern(locale, "reltime/relpattern", buildKey(c, z, pluralCategory), buildKey(c, z, PluralCategory.OTHER), pluralCategory);
    }

    private String getPattern(Locale locale, String str, String str2, String str3, PluralCategory pluralCategory) {
        boolean z = true;
        PropertyBundle propertyBundle = null;
        for (Locale locale2 : PropertyBundle.getCandidateLocales(locale)) {
            PropertyBundle load = (!z || propertyBundle == null) ? PropertyBundle.load("i18n/" + str, locale2) : propertyBundle;
            if (z) {
                if (locale2.equals(load.getLocale())) {
                    z = false;
                } else {
                    propertyBundle = load;
                }
            }
            if (load.getInternalKeys().contains(str2)) {
                return load.getString(str2);
            }
            if (pluralCategory != PluralCategory.OTHER && load.getInternalKeys().contains(str3)) {
                return load.getString(str3);
            }
        }
        throw new MissingResourceException("Can't find resource for bundle " + str + ".properties, key " + str2, str + ".properties", str2);
    }

    private String getLabel(Locale locale, String str) {
        boolean z = true;
        PropertyBundle propertyBundle = null;
        for (Locale locale2 : PropertyBundle.getCandidateLocales(locale)) {
            PropertyBundle load = (!z || propertyBundle == null) ? PropertyBundle.load("i18n/reltime/relpattern", locale2) : propertyBundle;
            if (z) {
                if (locale2.equals(load.getLocale())) {
                    z = false;
                } else {
                    propertyBundle = load;
                }
            }
            if (load.getInternalKeys().contains(str)) {
                return load.getString(str);
            }
        }
        return "";
    }

    private static String buildKey(char c, TextWidth textWidth, PluralCategory pluralCategory) {
        StringBuilder sb = new StringBuilder(3);
        sb.append(c);
        int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
        if (i == 1) {
            sb.append('w');
        } else if (i == 2 || i == 3) {
            sb.append('s');
        } else if (i == 4) {
            sb.append('n');
        } else {
            throw new UnsupportedOperationException(textWidth.name());
        }
        sb.append(pluralCategory.ordinal());
        return sb.toString();
    }

    /* renamed from: net.time4j.i18n.UnitPatternProviderSPI$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$TextWidth;

        static {
            int[] iArr = new int[TextWidth.values().length];
            $SwitchMap$net$time4j$format$TextWidth = iArr;
            try {
                iArr[TextWidth.WIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.ABBREVIATED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.NARROW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static String buildKey(char c, boolean z, PluralCategory pluralCategory) {
        StringBuilder sb = new StringBuilder(3);
        sb.append(c);
        sb.append(z ? '+' : '-');
        sb.append(pluralCategory.ordinal());
        return sb.toString();
    }

    private static String buildListKey(TextWidth textWidth, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('L');
        int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
        if (i == 1) {
            sb.append('w');
        } else if (i == 2 || i == 3) {
            sb.append('s');
        } else if (i == 4) {
            sb.append('n');
        } else {
            throw new UnsupportedOperationException(textWidth.name());
        }
        sb.append('-');
        sb.append(str);
        return sb.toString();
    }

    private static String replace(String str, char c, int i) {
        int length = str.length() - 2;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) == '{') {
                int i3 = i2 + 1;
                if (str.charAt(i3) == c) {
                    int i4 = i2 + 2;
                    if (str.charAt(i4) == '}') {
                        StringBuilder sb = new StringBuilder(length + 10);
                        sb.append(str);
                        sb.replace(i3, i4, String.valueOf(i));
                        return sb.toString();
                    }
                } else {
                    continue;
                }
            }
        }
        return str;
    }
}
