package net.time4j.i18n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.time4j.Weekday;
import net.time4j.base.ResourceLoader;
import net.time4j.format.WeekdataProvider;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class WeekdataProviderSPI implements WeekdataProvider {
    private final Set<String> countriesWithMinDays4;
    private final Map<String, Weekday> endOfWeekend;
    private final Map<String, Weekday> firstDayOfWeek;
    private final String source;
    private final Map<String, Weekday> startOfWeekend;

    public WeekdataProviderSPI() {
        String substring;
        Weekday weekday;
        HashMap hashMap;
        URI locate = ResourceLoader.getInstance().locate("i18n", WeekdataProviderSPI.class, "data/week.data");
        InputStream load = ResourceLoader.getInstance().load(locate, true);
        if (load == null) {
            try {
                load = ResourceLoader.getInstance().load(WeekdataProviderSPI.class, "data/week.data", true);
            } catch (IOException unused) {
            }
        }
        if (load != null) {
            this.source = "@" + locate;
            HashSet hashSet = new HashSet();
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            HashMap hashMap4 = new HashMap();
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(load, CharEncoding.US_ASCII));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (!readLine.startsWith("#")) {
                                int indexOf = readLine.indexOf(61);
                                int i = 0;
                                String trim = readLine.substring(0, indexOf).trim();
                                String[] split = readLine.substring(indexOf + 1).split(StringUtils.SPACE);
                                if (trim.equals("minDays-4")) {
                                    int length = split.length;
                                    while (i < length) {
                                        String upperCase = split[i].trim().toUpperCase(Locale.US);
                                        if (!upperCase.isEmpty()) {
                                            hashSet.add(upperCase);
                                        }
                                        i++;
                                    }
                                } else {
                                    if (trim.startsWith("start-")) {
                                        substring = trim.substring(6);
                                        weekday = Weekday.SATURDAY;
                                        hashMap = hashMap3;
                                    } else if (trim.startsWith("end-")) {
                                        substring = trim.substring(4);
                                        weekday = Weekday.SUNDAY;
                                        hashMap = hashMap4;
                                    } else if (trim.startsWith("first-")) {
                                        substring = trim.substring(6);
                                        weekday = Weekday.MONDAY;
                                        hashMap = hashMap2;
                                    } else {
                                        throw new IllegalStateException("Unexpected format: " + this.source);
                                    }
                                    if (substring.equals("sun")) {
                                        weekday = Weekday.SUNDAY;
                                    } else if (substring.equals("sat")) {
                                        weekday = Weekday.SATURDAY;
                                    } else if (substring.equals("fri")) {
                                        weekday = Weekday.FRIDAY;
                                    } else if (substring.equals("thu")) {
                                        weekday = Weekday.THURSDAY;
                                    } else if (substring.equals("wed")) {
                                        weekday = Weekday.WEDNESDAY;
                                    } else if (substring.equals("tue")) {
                                        weekday = Weekday.TUESDAY;
                                    } else if (substring.equals("mon")) {
                                        weekday = Weekday.MONDAY;
                                    }
                                    int length2 = split.length;
                                    while (i < length2) {
                                        String upperCase2 = split[i].trim().toUpperCase(Locale.US);
                                        if (!upperCase2.isEmpty()) {
                                            hashMap.put(upperCase2, weekday);
                                        }
                                        i++;
                                    }
                                }
                            }
                        } else {
                            this.countriesWithMinDays4 = Collections.unmodifiableSet(hashSet);
                            this.firstDayOfWeek = Collections.unmodifiableMap(hashMap2);
                            this.startOfWeekend = Collections.unmodifiableMap(hashMap3);
                            this.endOfWeekend = Collections.unmodifiableMap(hashMap4);
                            try {
                                load.close();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace(System.err);
                                return;
                            }
                        }
                    }
                } catch (UnsupportedEncodingException e2) {
                    throw new AssertionError(e2);
                } catch (Exception e3) {
                    throw new IllegalStateException("Unexpected format: " + this.source, e3);
                }
            } catch (Throwable th) {
                try {
                    load.close();
                } catch (IOException e4) {
                    e4.printStackTrace(System.err);
                }
                throw th;
            }
        } else {
            this.source = "@STATIC";
            this.countriesWithMinDays4 = Collections.emptySet();
            this.firstDayOfWeek = Collections.emptyMap();
            this.startOfWeekend = Collections.emptyMap();
            this.endOfWeekend = Collections.emptyMap();
            System.err.println("Warning: File \"data/week.data\" not found.");
        }
    }

    @Override // net.time4j.format.WeekdataProvider
    public int getFirstDayOfWeek(Locale locale) {
        if (this.firstDayOfWeek.isEmpty()) {
            int firstDayOfWeek = new GregorianCalendar(locale).getFirstDayOfWeek();
            if (firstDayOfWeek == 1) {
                return 7;
            }
            return firstDayOfWeek - 1;
        }
        String country = locale.getCountry();
        Weekday weekday = Weekday.MONDAY;
        if (this.firstDayOfWeek.containsKey(country)) {
            weekday = this.firstDayOfWeek.get(country);
        }
        return weekday.getValue();
    }

    @Override // net.time4j.format.WeekdataProvider
    public int getMinimalDaysInFirstWeek(Locale locale) {
        if (this.countriesWithMinDays4.isEmpty()) {
            return new GregorianCalendar(locale).getMinimalDaysInFirstWeek();
        }
        String country = locale.getCountry();
        return ((country.isEmpty() && locale.getLanguage().isEmpty()) || this.countriesWithMinDays4.contains(country)) ? 4 : 1;
    }

    @Override // net.time4j.format.WeekdataProvider
    public int getStartOfWeekend(Locale locale) {
        String country = locale.getCountry();
        Weekday weekday = Weekday.SATURDAY;
        if (this.startOfWeekend.containsKey(country)) {
            weekday = this.startOfWeekend.get(country);
        }
        return weekday.getValue();
    }

    @Override // net.time4j.format.WeekdataProvider
    public int getEndOfWeekend(Locale locale) {
        String country = locale.getCountry();
        Weekday weekday = Weekday.SUNDAY;
        if (this.endOfWeekend.containsKey(country)) {
            weekday = this.endOfWeekend.get(country);
        }
        return weekday.getValue();
    }

    public String toString() {
        return getClass().getName() + this.source;
    }
}
