package net.time4j.format.expert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.expert.ZoneLabels;
import net.time4j.tz.NameStyle;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
final class TimezoneGenericProcessor implements FormatProcessor<TZID> {
    private static final Map<NameStyle, ConcurrentMap<Locale, ZoneLabels>> CACHE_ZONENAMES = new EnumMap(NameStyle.class);
    private static final String DEFAULT_PROVIDER = "DEFAULT";
    private static final int MAX = 25;
    private final FormatProcessor<TZID> fallback;
    private final Leniency lenientMode;
    private final Locale locale;
    private final Set<TZID> preferredZones;
    private final int protectedLength;
    private final NameStyle style;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> withElement(ChronoElement<TZID> chronoElement) {
        return this;
    }

    static {
        for (NameStyle nameStyle : NameStyle.values()) {
            CACHE_ZONENAMES.put(nameStyle, new ConcurrentHashMap());
        }
    }

    TimezoneGenericProcessor(NameStyle nameStyle) {
        this.style = nameStyle;
        this.fallback = new LocalizedGMTProcessor(nameStyle.isAbbreviation());
        this.preferredZones = null;
        this.lenientMode = Leniency.SMART;
        this.locale = Locale.ROOT;
        this.protectedLength = 0;
    }

    TimezoneGenericProcessor(NameStyle nameStyle, Set<TZID> set) {
        this.style = nameStyle;
        this.fallback = new LocalizedGMTProcessor(nameStyle.isAbbreviation());
        this.preferredZones = Collections.unmodifiableSet(new LinkedHashSet(set));
        this.lenientMode = Leniency.SMART;
        this.locale = Locale.ROOT;
        this.protectedLength = 0;
    }

    private TimezoneGenericProcessor(NameStyle nameStyle, FormatProcessor<TZID> formatProcessor, Set<TZID> set, Leniency leniency, Locale locale, int i) {
        this.style = nameStyle;
        this.fallback = formatProcessor;
        this.preferredZones = set;
        this.lenientMode = leniency;
        this.locale = locale;
        this.protectedLength = i;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        TZID tzid;
        if (chronoDisplay.hasTimezone()) {
            tzid = chronoDisplay.getTimezone();
        } else if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
            tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
        } else {
            throw new IllegalArgumentException("Cannot extract timezone name in style " + this.style + " from: " + chronoDisplay);
        }
        if (tzid instanceof ZonalOffset) {
            return this.fallback.print(chronoDisplay, appendable, attributeQuery, set, z);
        }
        String displayName = Timezone.of(tzid).getDisplayName(this.style, z ? this.locale : (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT));
        if (displayName.equals(tzid.canonical())) {
            return this.fallback.print(chronoDisplay, appendable, attributeQuery, set, z);
        }
        int length = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        appendable.append(displayName);
        int length2 = displayName.length();
        if (length != -1 && length2 > 0 && set != null) {
            set.add(new ElementPosition(TimezoneElement.TIMEZONE_ID, length, length + length2));
        }
        return length2;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        List<TZID> list;
        boolean z2;
        ZoneLabels putIfAbsent;
        int position = parseLog.getPosition();
        int length = charSequence.length();
        int intValue = z ? this.protectedLength : ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue();
        if (intValue > 0) {
            length -= intValue;
        }
        if (position >= length) {
            parseLog.setError(position, "Missing timezone name in style " + this.style + ".");
            return;
        }
        Locale locale = z ? this.locale : (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
        Leniency leniency = z ? this.lenientMode : (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART);
        String charSequence2 = charSequence.subSequence(position, Math.min(position + 3, length)).toString();
        if (charSequence2.startsWith(TimeZones.GMT_ID) || charSequence2.startsWith("UT")) {
            this.fallback.parse(charSequence, parseLog, attributeQuery, parsedEntity, z);
            return;
        }
        ConcurrentMap<Locale, ZoneLabels> concurrentMap = CACHE_ZONENAMES.get(this.style);
        ZoneLabels zoneLabels = concurrentMap.get(locale);
        if (zoneLabels == null) {
            zoneLabels = createZoneNames(locale);
            if (concurrentMap.size() < 25 && (putIfAbsent = concurrentMap.putIfAbsent(locale, zoneLabels)) != null) {
                zoneLabels = putIfAbsent;
            }
        }
        int[] iArr = {position};
        List<TZID> readZoneNames = readZoneNames(zoneLabels, charSequence.subSequence(0, length), iArr);
        int size = readZoneNames.size();
        if (size == 0) {
            parseLog.setError(position, "Unknown timezone name: " + charSequence2);
            return;
        }
        if (size > 1 && !leniency.isStrict()) {
            readZoneNames = excludeWinZones(readZoneNames);
            size = readZoneNames.size();
        }
        if (size <= 1 || leniency.isLax()) {
            list = readZoneNames;
        } else {
            TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID, ZonalOffset.UTC);
            if (tzid instanceof ZonalOffset) {
                list = resolveUsingPreferred(readZoneNames, locale, leniency);
            } else {
                Iterator<TZID> it = readZoneNames.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        list = readZoneNames;
                        z2 = false;
                        break;
                    } else {
                        TZID next = it.next();
                        if (next.canonical().equals(tzid.canonical())) {
                            list = Collections.singletonList(next);
                            z2 = true;
                            break;
                        }
                    }
                }
                if (!z2) {
                    list = resolveUsingPreferred(list, locale, leniency);
                }
            }
        }
        int size2 = list.size();
        if (size2 == 0) {
            ArrayList arrayList = new ArrayList();
            Iterator<TZID> it2 = readZoneNames.iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next().canonical());
            }
            parseLog.setError(position, "Time zone name \"" + charSequence2 + "\" not found among preferred timezones in locale " + locale + ", style=" + this.style + ", candidates=" + arrayList);
            return;
        }
        if (size2 == 1 || leniency.isLax()) {
            parsedEntity.put(TimezoneElement.TIMEZONE_ID, list.get(0));
            parseLog.setPosition(iArr[0]);
            return;
        }
        parseLog.setError(position, "Time zone name of style " + this.style + " is not unique: \"" + charSequence2 + "\" in " + toString(list));
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<TZID> getElement() {
        return TimezoneElement.TIMEZONE_ID;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return new TimezoneGenericProcessor(this.style, this.fallback, this.preferredZones, (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART), (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), ((Integer) attributeQuery.get(Attributes.PROTECTED_CHARACTERS, 0)).intValue());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimezoneGenericProcessor)) {
            return false;
        }
        TimezoneGenericProcessor timezoneGenericProcessor = (TimezoneGenericProcessor) obj;
        if (this.style == timezoneGenericProcessor.style) {
            Set<TZID> set = this.preferredZones;
            Set<TZID> set2 = timezoneGenericProcessor.preferredZones;
            if (set == null) {
                if (set2 == null) {
                    return true;
                }
            } else if (set.equals(set2)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.style.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[style=");
        sb.append(this.style);
        sb.append(", preferredZones=");
        sb.append(this.preferredZones);
        sb.append(']');
        return sb.toString();
    }

    private ZoneLabels createZoneNames(Locale locale) {
        ZoneLabels.Node node = null;
        for (TZID tzid : Timezone.getAvailableIDs()) {
            String displayName = Timezone.getDisplayName(tzid, this.style, locale);
            if (!displayName.equals(tzid.canonical())) {
                node = ZoneLabels.insert(node, displayName, tzid);
            }
        }
        return new ZoneLabels(node);
    }

    private static List<TZID> readZoneNames(ZoneLabels zoneLabels, CharSequence charSequence, int[] iArr) {
        String longestPrefixOf = zoneLabels.longestPrefixOf(charSequence, iArr[0]);
        List<TZID> find = zoneLabels.find(longestPrefixOf);
        if (!find.isEmpty()) {
            iArr[0] = iArr[0] + longestPrefixOf.length();
        }
        return find;
    }

    private static List<TZID> excludeWinZones(List<TZID> list) {
        if (list.size() > 1) {
            ArrayList arrayList = new ArrayList(list);
            int size = list.size();
            for (int i = 1; i < size; i++) {
                TZID tzid = list.get(i);
                if (tzid.canonical().startsWith("WINDOWS~")) {
                    arrayList.remove(tzid);
                }
            }
            if (!arrayList.isEmpty()) {
                return arrayList;
            }
        }
        return list;
    }

    private List<TZID> resolveUsingPreferred(List<TZID> list, Locale locale, Leniency leniency) {
        boolean z;
        HashMap hashMap = new HashMap();
        hashMap.put(DEFAULT_PROVIDER, new ArrayList());
        Iterator<TZID> it = list.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            String canonical = it.next().canonical();
            Set<TZID> set = this.preferredZones;
            int indexOf = canonical.indexOf(126);
            String substring = indexOf >= 0 ? canonical.substring(0, indexOf) : DEFAULT_PROVIDER;
            if (set == null) {
                set = Timezone.getPreferredIDs(locale, leniency.isSmart(), substring);
            }
            Iterator<TZID> it2 = set.iterator();
            while (true) {
                if (it2.hasNext()) {
                    TZID next = it2.next();
                    if (next.canonical().equals(canonical)) {
                        List list2 = (List) hashMap.get(substring);
                        if (list2 == null) {
                            list2 = new ArrayList();
                            hashMap.put(substring, list2);
                        }
                        list2.add(next);
                    }
                }
            }
        }
        List<TZID> list3 = (List) hashMap.get(DEFAULT_PROVIDER);
        if (!list3.isEmpty()) {
            return list3;
        }
        hashMap.remove(DEFAULT_PROVIDER);
        Iterator it3 = hashMap.keySet().iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            List<TZID> list4 = (List) hashMap.get((String) it3.next());
            if (!list4.isEmpty()) {
                z = true;
                list = list4;
                break;
            }
        }
        if (!z) {
            list = Collections.emptyList();
        }
        return list;
    }

    private static String toString(List<TZID> list) {
        StringBuilder sb = new StringBuilder(list.size() * 16);
        sb.append('{');
        boolean z = true;
        for (TZID tzid : list) {
            if (z) {
                z = false;
            } else {
                sb.append(',');
            }
            sb.append(tzid.canonical());
        }
        sb.append('}');
        return sb.toString();
    }
}
