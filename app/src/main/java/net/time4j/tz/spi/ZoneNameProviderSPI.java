package net.time4j.tz.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.time4j.base.ResourceLoader;
import net.time4j.i18n.PropertyBundle;
import net.time4j.tz.NameStyle;
import net.time4j.tz.ZoneNameProvider;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
public class ZoneNameProviderSPI implements ZoneNameProvider {
    private static final Set<String> GMT_ZONES;
    private static final ConcurrentMap<Locale, Map<String, Map<NameStyle, String>>> NAMES = new ConcurrentHashMap();
    private static final Map<String, String> PRIMARIES;
    private static final Map<String, Set<String>> TERRITORIES;

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("Z");
        hashSet.add(TimeZones.GMT_ID);
        hashSet.add("GMT0");
        hashSet.add("Greenwich");
        hashSet.add("UCT");
        hashSet.add("UTC");
        hashSet.add("UTC0");
        hashSet.add("Universal");
        hashSet.add("Zulu");
        GMT_ZONES = Collections.unmodifiableSet(hashSet);
        HashMap hashMap = new HashMap();
        loadTerritories(hashMap, "data/zone1970.tab");
        TERRITORIES = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        addPrimary(hashMap2, "CL", "America/Santiago");
        addPrimary(hashMap2, "CN", "Asia/Shanghai");
        addPrimary(hashMap2, "DE", "Europe/Berlin");
        addPrimary(hashMap2, "EC", "America/Guayaquil");
        addPrimary(hashMap2, "ES", "Europe/Madrid");
        addPrimary(hashMap2, "MH", "Pacific/Majuro");
        addPrimary(hashMap2, "MY", "Asia/Kuala_Lumpur");
        addPrimary(hashMap2, "NZ", "Pacific/Auckland");
        addPrimary(hashMap2, "PT", "Europe/Lisbon");
        addPrimary(hashMap2, "UA", "Europe/Kiev");
        addPrimary(hashMap2, "UZ", "Asia/Tashkent");
        PRIMARIES = Collections.unmodifiableMap(hashMap2);
    }

    @Override // net.time4j.tz.ZoneNameProvider
    public Set<String> getPreferredIDs(Locale locale, boolean z) {
        String country = locale.getCountry();
        if (z) {
            if (country.equals("US")) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.add("America/New_York");
                linkedHashSet.add("America/Chicago");
                linkedHashSet.add("America/Denver");
                linkedHashSet.add("America/Los_Angeles");
                linkedHashSet.add("America/Anchorage");
                linkedHashSet.add("Pacific/Honolulu");
                return Collections.unmodifiableSet(linkedHashSet);
            }
            String str = PRIMARIES.get(country);
            if (str != null) {
                return Collections.singleton(str);
            }
        }
        Set<String> set = TERRITORIES.get(country);
        return set == null ? Collections.emptySet() : set;
    }

    @Override // net.time4j.tz.ZoneNameProvider
    public String getDisplayName(String str, NameStyle nameStyle, Locale locale) {
        if (GMT_ZONES.contains(str)) {
            return "";
        }
        Map<String, Map<NameStyle, String>> map = NAMES.get(locale);
        if (map == null) {
            String[][] zoneStrings = DateFormatSymbols.getInstance(locale).getZoneStrings();
            HashMap hashMap = new HashMap();
            for (String[] strArr : zoneStrings) {
                EnumMap enumMap = new EnumMap(NameStyle.class);
                enumMap.put((EnumMap) NameStyle.LONG_STANDARD_TIME, (NameStyle) strArr[1]);
                enumMap.put((EnumMap) NameStyle.SHORT_STANDARD_TIME, (NameStyle) strArr[2]);
                enumMap.put((EnumMap) NameStyle.LONG_DAYLIGHT_TIME, (NameStyle) strArr[3]);
                enumMap.put((EnumMap) NameStyle.SHORT_DAYLIGHT_TIME, (NameStyle) strArr[4]);
                hashMap.put(strArr[0], enumMap);
            }
            map = NAMES.putIfAbsent(locale, hashMap);
            if (map == null) {
                map = hashMap;
            }
        }
        Map<NameStyle, String> map2 = map.get(str);
        return map2 != null ? map2.get(nameStyle) : "";
    }

    @Override // net.time4j.tz.ZoneNameProvider
    public String getStdFormatPattern(boolean z, Locale locale) {
        return getBundle(locale).getString(z ? "utc-literal" : "offset-pattern");
    }

    static void loadTerritories(Map<String, Set<String>> map, String str) {
        InputStream load = ResourceLoader.getInstance().load(ResourceLoader.getInstance().locate("olson", ZoneNameProviderSPI.class, str), true);
        if (load == null) {
            load = ZoneNameProviderSPI.class.getClassLoader().getResourceAsStream(str);
        }
        try {
            if (load != null) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(load, "UTF-8"));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (!readLine.startsWith("#") && !readLine.isEmpty()) {
                                String[] split = readLine.split("\t");
                                if (split.length >= 3) {
                                    for (String str2 : split[0].split(",")) {
                                        addTerritory(map, str2, split[2]);
                                    }
                                }
                            }
                        } else {
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
                } catch (IOException e3) {
                    throw new IllegalStateException(e3);
                }
            } else {
                System.err.println("Warning: File \"" + str + "\" not found.");
            }
        } catch (Throwable th) {
            try {
                load.close();
            } catch (IOException e4) {
                e4.printStackTrace(System.err);
            }
            throw th;
        }
    }

    private static void addTerritory(Map<String, Set<String>> map, String str, String str2) {
        Set<String> set = map.get(str);
        if (set == null) {
            set = new LinkedHashSet<>();
            map.put(str, set);
        }
        set.add(str2);
    }

    private static void addPrimary(Map<String, String> map, String str, String str2) {
        map.put(str, str2);
    }

    private static PropertyBundle getBundle(Locale locale) {
        return PropertyBundle.load("olson/zones/tzname", locale);
    }
}
