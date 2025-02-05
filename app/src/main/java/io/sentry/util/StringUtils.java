package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class StringUtils {
    private static final String CORRUPTED_NIL_UUID = "0000-0000";
    private static final String PROPER_NIL_UUID = "00000000-0000-0000-0000-000000000000";
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private StringUtils() {
    }

    public static String getStringAfterDot(String str) {
        int i;
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(".");
        return (lastIndexOf < 0 || str.length() <= (i = lastIndexOf + 1)) ? str : str.substring(i);
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
    }

    public static String removeSurrounding(String str, String str2) {
        return (str == null || str2 == null || !str.startsWith(str2) || !str.endsWith(str2)) ? str : str.substring(str2.length(), str.length() - str2.length());
    }

    public static String byteCountToString(long j) {
        if (-1000 < j && j < 1000) {
            return j + " B";
        }
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("kMGTPE");
        while (true) {
            if (j > -999950 && j < 999950) {
                return String.format(Locale.ROOT, "%.1f %cB", Double.valueOf(j / 1000.0d), Character.valueOf(stringCharacterIterator.current()));
            }
            j /= 1000;
            stringCharacterIterator.next();
        }
    }

    public static String calculateStringHash(String str, ILogger iLogger) {
        if (str != null && !str.isEmpty()) {
            try {
                return new StringBuilder(new BigInteger(1, MessageDigest.getInstance("SHA-1").digest(str.getBytes(UTF_8))).toString(16)).toString();
            } catch (NoSuchAlgorithmException e) {
                iLogger.log(SentryLevel.INFO, "SHA-1 isn't available to calculate the hash.", e);
            } catch (Throwable th) {
                iLogger.log(SentryLevel.INFO, "string: %s could not calculate its hash", th, str);
            }
        }
        return null;
    }

    public static int countOf(String str, char c) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == c) {
                i++;
            }
        }
        return i;
    }

    public static String normalizeUUID(String str) {
        return str.equals(CORRUPTED_NIL_UUID) ? PROPER_NIL_UUID : str;
    }

    public static String join(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends CharSequence> it = iterable.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(charSequence);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static String removePrefix(String str, String str2) {
        return str == null ? "" : str.indexOf(str2) == 0 ? str.substring(str2.length()) : str;
    }

    public static String substringBefore(String str, String str2) {
        if (str == null) {
            return "";
        }
        int indexOf = str.indexOf(str2);
        return indexOf >= 0 ? str.substring(0, indexOf) : str;
    }
}
