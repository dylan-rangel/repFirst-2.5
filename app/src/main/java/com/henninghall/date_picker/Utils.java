package com.henninghall.date_picker;

import android.text.format.DateFormat;
import android.text.format.DateUtils;
import com.facebook.react.bridge.ReactApplicationContext;
import com.henninghall.date_picker.models.WheelType;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import net.time4j.PrettyTime;

/* loaded from: classes2.dex */
public class Utils {
    public static boolean deviceUsesAmPm() {
        return !DateFormat.is24HourFormat(DatePickerPackage.context);
    }

    public static String printToday(Locale locale) {
        return PrettyTime.of(locale).printToday();
    }

    public static Calendar isoToCalendar(String str, TimeZone timeZone) {
        if (str == null) {
            return null;
        }
        try {
            Calendar calendar = Calendar.getInstance(timeZone);
            calendar.setTime(getIsoUTCFormat().parse(str));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToIso(Calendar calendar) {
        return getIsoUTCFormat().format(calendar.getTime());
    }

    public static boolean isToday(Calendar calendar) {
        return DateUtils.isToday(calendar.getTimeInMillis());
    }

    public static Calendar getTruncatedCalendarOrNull(Calendar calendar) {
        try {
            return org.apache.commons.lang3.time.DateUtils.truncate(calendar, 12);
        } catch (Exception unused) {
            return null;
        }
    }

    private static SimpleDateFormat getIsoUTCFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static ArrayList<String> splitOnSpace(String str) {
        String[] split = str.split("\\s+");
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, split);
        return arrayList;
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static WheelType patternCharToWheelType(char c) throws Exception {
        if (c != 'H') {
            if (c == 'M') {
                return WheelType.MONTH;
            }
            if (c == 'a') {
                return WheelType.AM_PM;
            }
            if (c == 'd') {
                return WheelType.DATE;
            }
            if (c != 'h') {
                if (c == 'm') {
                    return WheelType.MINUTE;
                }
                if (c == 'y') {
                    return WheelType.YEAR;
                }
                throw new Exception("Invalid pattern char: " + c);
            }
        }
        return WheelType.HOUR;
    }

    public static int getShortestScrollOption(int i, int i2, int i3, boolean z) {
        int i4 = i3 + 1;
        int i5 = i2 - i;
        int i6 = i5 > 0 ? i5 - i4 : i4 + i5;
        if (z) {
            return Math.abs(i5) < Math.abs(i6) ? i5 : i6;
        }
        int i7 = i + i5;
        return (i7 <= i3 && i7 >= 0) ? i5 : i6;
    }

    public static String getLocalisedStringFromResources(Locale locale, String str) {
        ReactApplicationContext reactApplicationContext = DatePickerPackage.context;
        return LocaleUtils.getLocaleStringResource(locale, reactApplicationContext.getResources().getIdentifier(str, "string", reactApplicationContext.getPackageName()), reactApplicationContext);
    }

    public static int toDp(int i) {
        return (int) (i * DatePickerPackage.context.getResources().getDisplayMetrics().density);
    }

    public static String toPaddedMinutes(int i) {
        return new DecimalFormat("00").format(i);
    }
}
