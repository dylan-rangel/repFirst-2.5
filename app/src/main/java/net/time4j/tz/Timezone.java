package net.time4j.tz;

import android.util.TimeUtils;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
public abstract class Timezone implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean ALLOW_SYSTEM_TZ_OVERRIDE;
    private static final boolean ANDROID;
    private static final ConcurrentMap<String, NamedReference> CACHE;
    private static final ZoneModelProvider DEFAULT_PROVIDER;
    private static final Map<String, TZID> ETCETERA;
    private static final LinkedList<Timezone> LAST_USED;
    private static final String NAME_DEFAULT = "DEFAULT";
    private static final String NAME_JUT = "java.util.TimeZone";
    static final ZoneNameProvider NAME_PROVIDER;
    private static final String NAME_TZDB = "TZDB";
    private static final ZoneModelProvider PLATFORM_PROVIDER;
    private static final Map<String, TZID> PREDEFINED;
    private static final ConcurrentMap<String, ZoneModelProvider> PROVIDERS;
    private static final ReferenceQueue<Timezone> QUEUE;
    private static final Timezone SYSTEM_TZ_ORIGINAL;
    private static volatile boolean cacheActive;
    private static volatile Timezone currentSystemTZ;
    private static int softLimit;
    private static volatile ZonalKeys zonalKeys;
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String REPOSITORY_VERSION = System.getProperty("net.time4j.tz.repository.version");
    private static final Comparator<TZID> ID_COMPARATOR = new Comparator<TZID>() { // from class: net.time4j.tz.Timezone.1
        @Override // java.util.Comparator
        public int compare(TZID tzid, TZID tzid2) {
            return tzid.canonical().compareTo(tzid2.canonical());
        }
    };
    public static final TransitionStrategy DEFAULT_CONFLICT_STRATEGY = GapResolver.PUSH_FORWARD.and(OverlapResolver.LATER_OFFSET);
    public static final TransitionStrategy STRICT_MODE = GapResolver.ABORT.and(OverlapResolver.LATER_OFFSET);

    public abstract ZonalOffset getDaylightSavingOffset(UnixTime unixTime);

    public abstract TransitionHistory getHistory();

    public abstract TZID getID();

    public abstract ZonalOffset getOffset(GregorianDate gregorianDate, WallTime wallTime);

    public abstract ZonalOffset getOffset(UnixTime unixTime);

    public abstract ZonalOffset getStandardOffset(UnixTime unixTime);

    public abstract TransitionStrategy getStrategy();

    public abstract boolean isDaylightSaving(UnixTime unixTime);

    public abstract boolean isFixed();

    public abstract boolean isInvalid(GregorianDate gregorianDate, WallTime wallTime);

    public abstract Timezone with(TransitionStrategy transitionStrategy);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b3  */
    /* JADX WARN: Type inference failed for: r0v14, types: [net.time4j.tz.Timezone$1] */
    /* JADX WARN: Type inference failed for: r0v15, types: [net.time4j.tz.Timezone] */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27 */
    static {
        /*
            Method dump skipped, instructions count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.tz.Timezone.<clinit>():void");
    }

    Timezone() {
    }

    public static List<TZID> getAvailableIDs() {
        return zonalKeys.availables;
    }

    public static List<TZID> getAvailableIDs(String str) {
        if (!str.equals("INCLUDE_ALIAS")) {
            ZoneModelProvider provider = getProvider(str);
            if (provider == null) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = provider.getAvailableIDs().iterator();
            while (it.hasNext()) {
                arrayList.add(resolve(it.next()));
            }
            Collections.sort(arrayList, ID_COMPARATOR);
            return Collections.unmodifiableList(arrayList);
        }
        return zonalKeys.availablesAndAliases;
    }

    public static Set<TZID> getPreferredIDs(Locale locale, boolean z, String str) {
        ZoneModelProvider provider = getProvider(str);
        if (provider == null) {
            return Collections.emptySet();
        }
        ZoneNameProvider specificZoneNameRepository = provider.getSpecificZoneNameRepository();
        if (specificZoneNameRepository == null) {
            specificZoneNameRepository = NAME_PROVIDER;
        }
        HashSet hashSet = new HashSet();
        Iterator<String> it = specificZoneNameRepository.getPreferredIDs(locale, z).iterator();
        while (it.hasNext()) {
            hashSet.add(resolve(it.next()));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public static Timezone ofSystem() {
        if (ALLOW_SYSTEM_TZ_OVERRIDE && currentSystemTZ != null) {
            return currentSystemTZ;
        }
        return SYSTEM_TZ_ORIGINAL;
    }

    public static Timezone ofPlatform() {
        return new PlatformTimezone();
    }

    public static Timezone of(TZID tzid) {
        return getTZ(tzid, true);
    }

    public static Timezone of(String str) {
        return getTZ(null, str, true);
    }

    public static Timezone of(String str, TZID tzid) {
        Timezone tz = getTZ(null, str, false);
        if (tz != null) {
            return tz;
        }
        Timezone tz2 = getTZ(tzid, false);
        return tz2 == null ? ofSystem() : tz2;
    }

    public static Timezone of(String str, TransitionHistory transitionHistory) {
        return new HistorizedTimezone(resolve(str), transitionHistory);
    }

    public static TZID normalize(TZID tzid) {
        return normalize(tzid.canonical());
    }

    public static TZID normalize(String str) {
        String str2;
        String str3;
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = "";
                str3 = str;
                break;
            }
            if (str.charAt(i) == '~') {
                str2 = str.substring(0, i);
                str3 = str.substring(i + 1);
                break;
            }
            i++;
        }
        if (str3.isEmpty()) {
            throw new IllegalArgumentException("Empty zone identifier: " + str);
        }
        ZoneModelProvider zoneModelProvider = DEFAULT_PROVIDER;
        if (!(str2.isEmpty() || str2.equals(NAME_DEFAULT)) && !str2.equals("WINDOWS") && !str2.equals("MILITARY") && (zoneModelProvider = PROVIDERS.get(str2)) == null) {
            throw new IllegalArgumentException((str2.equals(NAME_TZDB) ? "TZDB provider not available: " : "Timezone model provider not registered: ") + str);
        }
        Map<String, String> aliases = zoneModelProvider.getAliases();
        while (true) {
            String str4 = aliases.get(str3);
            if (str4 == null) {
                break;
            }
            str3 = str4;
        }
        Map<String, TZID> map = ETCETERA;
        if (map.containsKey(str3)) {
            return map.get(str3);
        }
        return resolve(str3);
    }

    public static String getProviderInfo() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(Timezone.class.getName());
        sb.append(":[default-provider=");
        sb.append(DEFAULT_PROVIDER.getName());
        sb.append(", registered={");
        Iterator<String> it = PROVIDERS.keySet().iterator();
        while (it.hasNext()) {
            ZoneModelProvider zoneModelProvider = PROVIDERS.get(it.next());
            if (zoneModelProvider != null) {
                sb.append("(name=");
                sb.append(zoneModelProvider.getName());
                String location = zoneModelProvider.getLocation();
                if (!location.isEmpty()) {
                    sb.append(",location=");
                    sb.append(location);
                }
                String version = zoneModelProvider.getVersion();
                if (!version.isEmpty()) {
                    sb.append(",version=");
                    sb.append(version);
                }
                sb.append(')');
            }
        }
        sb.append("}]");
        return sb.toString();
    }

    public static String getVersion(String str) {
        ZoneModelProvider provider = getProvider(str);
        return provider == null ? "" : provider.getVersion();
    }

    public static Set<String> getRegisteredProviders() {
        return Collections.unmodifiableSet(PROVIDERS.keySet());
    }

    public String getDisplayName(NameStyle nameStyle, Locale locale) {
        return getDisplayName(getID(), nameStyle, locale);
    }

    public static String getDisplayName(TZID tzid, NameStyle nameStyle, Locale locale) {
        String str;
        String canonical = tzid.canonical();
        int indexOf = canonical.indexOf(126);
        ZoneModelProvider zoneModelProvider = DEFAULT_PROVIDER;
        if (indexOf >= 0) {
            String substring = canonical.substring(0, indexOf);
            if (!substring.equals(NAME_DEFAULT) && (zoneModelProvider = PROVIDERS.get(substring)) == null) {
                return canonical;
            }
            str = canonical.substring(indexOf + 1);
        } else {
            str = canonical;
        }
        ZoneNameProvider specificZoneNameRepository = zoneModelProvider.getSpecificZoneNameRepository();
        if (specificZoneNameRepository == null) {
            specificZoneNameRepository = NAME_PROVIDER;
        }
        String displayName = specificZoneNameRepository.getDisplayName(str, nameStyle, locale);
        if (!displayName.isEmpty()) {
            return displayName;
        }
        ZoneNameProvider zoneNameProvider = NAME_PROVIDER;
        if (specificZoneNameRepository != zoneNameProvider) {
            displayName = zoneNameProvider.getDisplayName(str, nameStyle, locale);
        }
        if (!displayName.isEmpty()) {
            canonical = displayName;
        }
        return canonical;
    }

    public static boolean registerProvider(ZoneModelProvider zoneModelProvider) {
        String name = zoneModelProvider.getName();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Missing name of zone model provider.");
        }
        if (name.equals(NAME_TZDB)) {
            throw new IllegalArgumentException("TZDB provider cannot be registered after startup.");
        }
        if (name.equals(NAME_JUT)) {
            throw new IllegalArgumentException("Platform provider cannot be replaced.");
        }
        if (name.equals(NAME_DEFAULT)) {
            throw new IllegalArgumentException("Default zone model provider cannot be overridden.");
        }
        boolean z = PROVIDERS.putIfAbsent(name, zoneModelProvider) == null;
        if (z) {
            zonalKeys = new ZonalKeys();
        }
        return z;
    }

    public void dump(Appendable appendable) throws IOException {
        StringBuilder sb = new StringBuilder(4096);
        sb.append("Start Of Dump =>");
        String str = NEW_LINE;
        sb.append(str);
        sb.append("*** Timezone-ID:");
        sb.append(str);
        sb.append(">>> ");
        sb.append(getID().canonical());
        sb.append(str);
        if (isFixed()) {
            sb.append("*** Fixed offset:");
            sb.append(str);
            sb.append(">>> ");
            sb.append(getHistory().getInitialOffset());
            sb.append(str);
        } else {
            sb.append("*** Strategy:");
            sb.append(str);
            sb.append(">>> ");
            sb.append(getStrategy());
            sb.append(str);
            TransitionHistory history = getHistory();
            sb.append("*** History:");
            sb.append(str);
            if (history == null) {
                sb.append(">>> Not public!");
                sb.append(str);
            } else {
                history.dump(sb);
            }
        }
        sb.append("<= End Of Dump");
        sb.append(str);
        appendable.append(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Timezone getDefaultTZ() {
        String id = TimeZone.getDefault().getID();
        Timezone tz = getTZ(null, id, false);
        return tz == null ? new PlatformTimezone(new NamedID(id)) : tz;
    }

    private static Timezone getTZ(TZID tzid, boolean z) {
        if (tzid instanceof ZonalOffset) {
            return ((ZonalOffset) tzid).getModel();
        }
        return getTZ(tzid, tzid.canonical(), z);
    }

    private static Timezone getTZ(TZID tzid, String str, boolean z) {
        Timezone timezone;
        String str2;
        ConcurrentMap<String, NamedReference> concurrentMap = CACHE;
        NamedReference namedReference = concurrentMap.get(str);
        if (namedReference != null) {
            timezone = namedReference.get();
            if (timezone == null) {
                concurrentMap.remove(namedReference.tzid);
            }
        } else {
            timezone = null;
        }
        if (timezone != null) {
            return timezone;
        }
        String str3 = "";
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = str;
                break;
            }
            if (str.charAt(i) == '~') {
                str3 = str.substring(0, i);
                str2 = str.substring(i + 1);
                break;
            }
            i++;
        }
        if (str2.isEmpty()) {
            if (z) {
                throw new IllegalArgumentException("Timezone key is empty.");
            }
            return null;
        }
        ZoneModelProvider zoneModelProvider = DEFAULT_PROVIDER;
        boolean z2 = str3.isEmpty() || str3.equals(NAME_DEFAULT);
        if (!z2 && (zoneModelProvider = PROVIDERS.get(str3)) == null) {
            if (!z) {
                return null;
            }
            throw new IllegalArgumentException((str3.equals(NAME_TZDB) ? "TZDB provider not available: " : "Timezone model provider not registered: ") + str);
        }
        if (tzid == null) {
            if (z2) {
                tzid = resolve(str2);
                if (tzid instanceof ZonalOffset) {
                    return ((ZonalOffset) tzid).getModel();
                }
            } else {
                tzid = new NamedID(str);
            }
        }
        if (zoneModelProvider == PLATFORM_PROVIDER) {
            PlatformTimezone platformTimezone = new PlatformTimezone(tzid, str2);
            if (!platformTimezone.isGMT() || str2.equals(TimeZones.GMT_ID) || str2.startsWith("UT") || str2.equals("Z")) {
                timezone = platformTimezone;
            }
        } else {
            TransitionHistory load = zoneModelProvider.load(str2);
            if (load == null) {
                timezone = getZoneByAlias(zoneModelProvider, tzid, str2);
            } else {
                timezone = new HistorizedTimezone(tzid, load);
            }
        }
        if (timezone == null) {
            if (!z) {
                return null;
            }
            if (TimeZone.getDefault().getID().equals(str)) {
                return new PlatformTimezone(new NamedID(str));
            }
            throw new IllegalArgumentException("Unknown timezone: " + str);
        }
        if (!cacheActive) {
            return timezone;
        }
        NamedReference putIfAbsent = CACHE.putIfAbsent(str, new NamedReference(timezone, QUEUE));
        if (putIfAbsent == null) {
            synchronized (Timezone.class) {
                LAST_USED.addFirst(timezone);
                while (true) {
                    LinkedList<Timezone> linkedList = LAST_USED;
                    if (linkedList.size() >= softLimit) {
                        linkedList.removeLast();
                    }
                }
            }
            return timezone;
        }
        Timezone timezone2 = putIfAbsent.get();
        return timezone2 != null ? timezone2 : timezone;
    }

    private static Timezone getZoneByAlias(ZoneModelProvider zoneModelProvider, TZID tzid, String str) {
        Map<String, String> aliases = zoneModelProvider.getAliases();
        String str2 = str;
        TransitionHistory transitionHistory = null;
        while (transitionHistory == null) {
            str2 = aliases.get(str2);
            if (str2 == null) {
                break;
            }
            transitionHistory = zoneModelProvider.load(str2);
        }
        if (transitionHistory == null) {
            String fallback = zoneModelProvider.getFallback();
            if (fallback.isEmpty()) {
                return null;
            }
            if (fallback.equals(zoneModelProvider.getName())) {
                throw new IllegalArgumentException("Circular zone model provider fallback: " + zoneModelProvider.getName());
            }
            return new FallbackTimezone(tzid, of(fallback + "~" + str));
        }
        return new HistorizedTimezone(tzid, transitionHistory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TZID resolve(String str) {
        TZID tzid = PREDEFINED.get(str);
        if (tzid != null) {
            return tzid;
        }
        if (str.startsWith(TimeZones.GMT_ID)) {
            str = "UTC" + str.substring(3);
        }
        ZonalOffset parse = ZonalOffset.parse(str, false);
        return parse == null ? new NamedID(str) : parse;
    }

    private static void fillEtcetera(Map<String, TZID> map) {
        map.put("Etc/GMT", ZonalOffset.UTC);
        map.put("Etc/Greenwich", ZonalOffset.UTC);
        map.put("Etc/Universal", ZonalOffset.UTC);
        map.put("Etc/Zulu", ZonalOffset.UTC);
        map.put("Etc/GMT+0", ZonalOffset.UTC);
        map.put("Etc/GMT-0", ZonalOffset.UTC);
        map.put("Etc/GMT0", ZonalOffset.UTC);
        map.put("Etc/UTC", ZonalOffset.UTC);
        map.put("Etc/UCT", ZonalOffset.UTC);
        map.put("Etc/GMT-14", ZonalOffset.ofTotalSeconds(50400));
        map.put("Etc/GMT-13", ZonalOffset.ofTotalSeconds(46800));
        map.put("Etc/GMT-12", ZonalOffset.ofTotalSeconds(43200));
        map.put("Etc/GMT-11", ZonalOffset.ofTotalSeconds(39600));
        map.put("Etc/GMT-10", ZonalOffset.ofTotalSeconds(36000));
        map.put("Etc/GMT-9", ZonalOffset.ofTotalSeconds(32400));
        map.put("Etc/GMT-8", ZonalOffset.ofTotalSeconds(28800));
        map.put("Etc/GMT-7", ZonalOffset.ofTotalSeconds(25200));
        map.put("Etc/GMT-6", ZonalOffset.ofTotalSeconds(21600));
        map.put("Etc/GMT-5", ZonalOffset.ofTotalSeconds(18000));
        map.put("Etc/GMT-4", ZonalOffset.ofTotalSeconds(14400));
        map.put("Etc/GMT-3", ZonalOffset.ofTotalSeconds(10800));
        map.put("Etc/GMT-2", ZonalOffset.ofTotalSeconds(7200));
        map.put("Etc/GMT-1", ZonalOffset.ofTotalSeconds(3600));
        map.put("Etc/GMT+1", ZonalOffset.ofTotalSeconds(-3600));
        map.put("Etc/GMT+2", ZonalOffset.ofTotalSeconds(-7200));
        map.put("Etc/GMT+3", ZonalOffset.ofTotalSeconds(-10800));
        map.put("Etc/GMT+4", ZonalOffset.ofTotalSeconds(-14400));
        map.put("Etc/GMT+5", ZonalOffset.ofTotalSeconds(-18000));
        map.put("Etc/GMT+6", ZonalOffset.ofTotalSeconds(-21600));
        map.put("Etc/GMT+7", ZonalOffset.ofTotalSeconds(-25200));
        map.put("Etc/GMT+8", ZonalOffset.ofTotalSeconds(-28800));
        map.put("Etc/GMT+9", ZonalOffset.ofTotalSeconds(-32400));
        map.put("Etc/GMT+10", ZonalOffset.ofTotalSeconds(-36000));
        map.put("Etc/GMT+11", ZonalOffset.ofTotalSeconds(-39600));
        map.put("Etc/GMT+12", ZonalOffset.ofTotalSeconds(-43200));
    }

    private static List<Class<? extends TZID>> loadPredefined(ClassLoader classLoader, String... strArr) throws ClassNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            Class<?> cls = Class.forName("net.time4j.tz.olson." + str, true, classLoader);
            if (TZID.class.isAssignableFrom(cls)) {
                arrayList.add(cls);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static ZoneModelProvider getProvider(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Missing zone model provider.");
        }
        return str.equals(NAME_DEFAULT) ? DEFAULT_PROVIDER : PROVIDERS.get(str);
    }

    private static ZoneModelProvider compareTZDB(ZoneModelProvider zoneModelProvider, ZoneModelProvider zoneModelProvider2) {
        String version = zoneModelProvider.getVersion();
        if (!version.isEmpty()) {
            String str = REPOSITORY_VERSION;
            if (version.equals(str)) {
                return zoneModelProvider;
            }
            if (str == null) {
                if (zoneModelProvider2 == null || version.compareTo(zoneModelProvider2.getVersion()) > 0) {
                    return zoneModelProvider;
                }
                if (version.compareTo(zoneModelProvider2.getVersion()) == 0 && !zoneModelProvider.getLocation().contains("{java.home}")) {
                    return zoneModelProvider;
                }
            }
        }
        return zoneModelProvider2;
    }

    public static class Cache {
        private Cache() {
        }

        public static void refresh() {
            synchronized (Timezone.class) {
                do {
                } while (Timezone.QUEUE.poll() != null);
                Timezone.LAST_USED.clear();
            }
            ZonalKeys unused = Timezone.zonalKeys = new ZonalKeys();
            Timezone.CACHE.clear();
            if (Timezone.ALLOW_SYSTEM_TZ_OVERRIDE) {
                Timezone unused2 = Timezone.currentSystemTZ = Timezone.getDefaultTZ();
            }
        }

        public static void setCacheActive(boolean z) {
            boolean unused = Timezone.cacheActive = z;
            if (z) {
                return;
            }
            Timezone.CACHE.clear();
        }

        public static void setMinimumCacheSize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Negative timezone cache size: " + i);
            }
            while (true) {
                NamedReference namedReference = (NamedReference) Timezone.QUEUE.poll();
                if (namedReference == null) {
                    break;
                } else {
                    Timezone.CACHE.remove(namedReference.tzid);
                }
            }
            synchronized (Timezone.class) {
                int unused = Timezone.softLimit = i + 1;
                int size = Timezone.LAST_USED.size() - i;
                for (int i2 = 0; i2 < size; i2++) {
                    Timezone.LAST_USED.removeLast();
                }
            }
        }
    }

    private static class NamedReference extends SoftReference<Timezone> {
        private final String tzid;

        NamedReference(Timezone timezone, ReferenceQueue<Timezone> referenceQueue) {
            super(timezone, referenceQueue);
            this.tzid = timezone.getID().canonical();
        }
    }

    private static class ZonalKeys {
        private final List<TZID> availables;
        private final List<TZID> availablesAndAliases;

        ZonalKeys() {
            ArrayList arrayList = new ArrayList(1024);
            ArrayList arrayList2 = new ArrayList(1024);
            arrayList.add(ZonalOffset.UTC);
            Iterator it = Timezone.PROVIDERS.entrySet().iterator();
            while (it.hasNext()) {
                ZoneModelProvider zoneModelProvider = (ZoneModelProvider) ((Map.Entry) it.next()).getValue();
                if (zoneModelProvider != Timezone.PLATFORM_PROVIDER || Timezone.DEFAULT_PROVIDER == Timezone.PLATFORM_PROVIDER) {
                    Iterator<String> it2 = zoneModelProvider.getAvailableIDs().iterator();
                    while (it2.hasNext()) {
                        TZID resolve = Timezone.resolve(it2.next());
                        if (!arrayList.contains(resolve)) {
                            arrayList.add(resolve);
                        }
                    }
                    arrayList2.addAll(arrayList);
                    Iterator<String> it3 = zoneModelProvider.getAliases().keySet().iterator();
                    while (it3.hasNext()) {
                        TZID resolve2 = Timezone.resolve(it3.next());
                        if (!arrayList2.contains(resolve2)) {
                            arrayList2.add(resolve2);
                        }
                    }
                }
            }
            Collections.sort(arrayList, Timezone.ID_COMPARATOR);
            Collections.sort(arrayList2, Timezone.ID_COMPARATOR);
            this.availables = Collections.unmodifiableList(arrayList);
            this.availablesAndAliases = Collections.unmodifiableList(arrayList2);
        }
    }

    private static class PlatformZoneProvider implements ZoneModelProvider, ZoneNameProvider {
        @Override // net.time4j.tz.ZoneModelProvider
        public String getFallback() {
            return "";
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public String getLocation() {
            return "";
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public String getName() {
            return Timezone.NAME_JUT;
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public ZoneNameProvider getSpecificZoneNameRepository() {
            return this;
        }

        @Override // net.time4j.tz.ZoneNameProvider
        public String getStdFormatPattern(boolean z, Locale locale) {
            return z ? TimeZones.GMT_ID : "GMT±hh:mm";
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public TransitionHistory load(String str) {
            return null;
        }

        private PlatformZoneProvider() {
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public Set<String> getAvailableIDs() {
            HashSet hashSet = new HashSet();
            hashSet.addAll(Arrays.asList(TimeZone.getAvailableIDs()));
            return hashSet;
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public Map<String, String> getAliases() {
            return Collections.emptyMap();
        }

        @Override // net.time4j.tz.ZoneModelProvider
        public String getVersion() {
            return TimeUtils.getTimeZoneDatabaseVersion();
        }

        @Override // net.time4j.tz.ZoneNameProvider
        public Set<String> getPreferredIDs(Locale locale, boolean z) {
            return Collections.emptySet();
        }

        @Override // net.time4j.tz.ZoneNameProvider
        public String getDisplayName(String str, NameStyle nameStyle, Locale locale) {
            if (locale == null) {
                throw new NullPointerException("Missing locale.");
            }
            if (str.isEmpty()) {
                return "";
            }
            TimeZone findZone = PlatformTimezone.findZone(str);
            if (findZone.getID().equals(str)) {
                return findZone.getDisplayName(nameStyle.isDaylightSaving(), !nameStyle.isAbbreviation() ? 1 : 0, locale);
            }
            return "";
        }
    }
}
