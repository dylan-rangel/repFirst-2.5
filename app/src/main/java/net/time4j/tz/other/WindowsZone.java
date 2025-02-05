package net.time4j.tz.other;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import net.time4j.tz.NameStyle;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.spi.WinZoneProviderSPI;

/* loaded from: classes3.dex */
public final class WindowsZone implements Comparable<WindowsZone>, Serializable {
    private static final long serialVersionUID = -6071278077083785308L;
    private final String name;
    private static final Locale WORLDWIDE = new Locale("", "001");
    private static final WinZoneProviderSPI PROVIDER = new WinZoneProviderSPI();

    private WindowsZone(String str) {
        this.name = str;
    }

    public static Set<String> getAvailableNames() {
        return WinZoneProviderSPI.NAME_BASED_MAP.keySet();
    }

    public static WindowsZone of(String str) {
        check(str);
        return new WindowsZone(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WindowsZone) {
            return this.name.equals(((WindowsZone) obj).name);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }

    public static String toString(TZID tzid, Locale locale) {
        return toString(tzid.canonical(), locale);
    }

    public static String toString(String str, Locale locale) {
        String canonical = Timezone.normalize(str).canonical();
        WinZoneProviderSPI winZoneProviderSPI = PROVIDER;
        String displayName = winZoneProviderSPI.getDisplayName(canonical, NameStyle.LONG_STANDARD_TIME, locale);
        return displayName.isEmpty() ? winZoneProviderSPI.getDisplayName(canonical, NameStyle.LONG_STANDARD_TIME, WORLDWIDE) : displayName;
    }

    @Override // java.lang.Comparable
    public int compareTo(WindowsZone windowsZone) {
        return this.name.compareTo(windowsZone.name);
    }

    public Set<TZID> resolve(Locale locale) {
        Set<TZID> set = WinZoneProviderSPI.NAME_BASED_MAP.get(this.name).get(locale.getCountry());
        if (set == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(set);
    }

    public TZID resolveSmart(Locale locale) {
        Set<TZID> resolve = resolve(locale);
        if (resolve.size() != 1) {
            String country = locale.getCountry();
            if (!country.isEmpty() && !country.equals("001")) {
                resolve = WinZoneProviderSPI.NAME_BASED_MAP.get(this.name).get("001");
            }
        }
        if (resolve.isEmpty()) {
            return null;
        }
        return resolve.iterator().next();
    }

    public static void registerAsTimezone() {
        Timezone.registerProvider(PROVIDER);
    }

    static String getVersion() {
        return WinZoneProviderSPI.WIN_NAME_VERSION;
    }

    private static void check(String str) {
        if (str.isEmpty() || !WinZoneProviderSPI.NAME_BASED_MAP.keySet().contains(str)) {
            throw new IllegalArgumentException("Unknown windows zone: " + str);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        check(this.name);
    }
}
