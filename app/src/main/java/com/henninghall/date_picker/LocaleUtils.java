package com.henninghall.date_picker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.henninghall.date_picker.Formats;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LocaleUtils {
    public static String getDay(String str) {
        return getFormat(str, Formats.Format.MMMEd);
    }

    public static String getYear(String str) {
        return getFormat(str, Formats.Format.y);
    }

    public static String getDate(String str) {
        return getFormat(str, Formats.Format.d);
    }

    private static String getFormat(String str, Formats.Format format) {
        try {
            return Formats.get(str, format);
        } catch (Formats.FormatNotFoundException unused) {
            try {
                return Formats.get(str.substring(0, str.indexOf("_")), format);
            } catch (Formats.FormatNotFoundException | IndexOutOfBoundsException unused2) {
                return Formats.defaultFormat.get(format);
            }
        }
    }

    public static String getDatePattern(Locale locale) {
        return ((SimpleDateFormat) DateFormat.getDateInstance(0, locale)).toLocalizedPattern().replaceAll(",", "").replaceAll("([a-zA-Z]+)", " $1").trim();
    }

    static String getDateTimePattern(Locale locale) {
        return ((SimpleDateFormat) DateFormat.getDateTimeInstance(0, 0, locale)).toLocalizedPattern().replace(",", "");
    }

    public static Locale getLocale(String str) {
        try {
            return org.apache.commons.lang3.LocaleUtils.toLocale(str);
        } catch (Exception unused) {
            return org.apache.commons.lang3.LocaleUtils.toLocale(str.substring(0, str.indexOf("_")));
        }
    }

    public static boolean localeUsesAmPm(Locale locale) {
        DateFormat timeInstance = DateFormat.getTimeInstance(0, locale);
        return (timeInstance instanceof SimpleDateFormat) && ((SimpleDateFormat) timeInstance).toPattern().contains("a");
    }

    public static String getLocaleStringResource(Locale locale, int i, Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                Configuration configuration = new Configuration(context.getResources().getConfiguration());
                configuration.setLocale(locale);
                return context.createConfigurationContext(configuration).getText(i).toString();
            }
            Resources resources = context.getResources();
            Configuration configuration2 = resources.getConfiguration();
            Locale locale2 = configuration2.locale;
            configuration2.locale = locale;
            resources.updateConfiguration(configuration2, null);
            String string = resources.getString(i);
            configuration2.locale = locale2;
            resources.updateConfiguration(configuration2, null);
            return string;
        } catch (Exception unused) {
            return "";
        }
    }
}
