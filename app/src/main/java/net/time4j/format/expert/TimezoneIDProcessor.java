package net.time4j.format.expert;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
enum TimezoneIDProcessor implements FormatProcessor<TZID> {
    INSTANCE;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        if (!chronoDisplay.hasTimezone()) {
            throw new IllegalArgumentException("Cannot extract timezone id from: " + chronoDisplay);
        }
        int length = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        String canonical = chronoDisplay.getTimezone().canonical();
        appendable.append(canonical);
        int length2 = canonical.length();
        if (length != -1 && length2 > 0 && set != null) {
            set.add(new ElementPosition(TimezoneElement.TIMEZONE_ID, length, length + length2));
        }
        return length2;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        char charAt;
        char charAt2;
        int length = charSequence.length();
        int position = parseLog.getPosition();
        if (position >= length) {
            parseLog.setError(position, "Missing timezone name.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int i = position;
        while (i < length && (((charAt2 = charSequence.charAt(i)) >= 'a' && charAt2 <= 'z') || ((charAt2 >= 'A' && charAt2 <= 'Z') || charAt2 == '-' || charAt2 == '_' || charAt2 == '/'))) {
            sb.append(charAt2);
            i++;
        }
        if (!Character.isLetter(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
            i--;
        }
        String sb2 = sb.toString();
        if (sb2.isEmpty()) {
            parseLog.setError(position, "Missing valid timezone id.");
            return;
        }
        if (sb2.startsWith("Etc/GMT")) {
            parseLog.setError(position, "Inverse Etc/GMT-Offsets are not supported, use UTC-Offsets instead.");
            return;
        }
        if (sb2.equals("Z")) {
            parsedEntity.put(TimezoneElement.TIMEZONE_OFFSET, ZonalOffset.UTC);
            parseLog.setPosition(i);
            return;
        }
        if (sb2.equals("UTC") || sb2.equals(TimeZones.GMT_ID) || sb2.equals("UT")) {
            if (length > i && ((charAt = charSequence.charAt(i)) == '+' || charAt == '-')) {
                parseLog.setPosition(i);
                TimezoneOffsetProcessor.EXTENDED_LONG_PARSER.parse(charSequence, parseLog, attributeQuery, parsedEntity, z);
                return;
            } else {
                parsedEntity.put(TimezoneElement.TIMEZONE_OFFSET, ZonalOffset.UTC);
                parseLog.setPosition(i);
                return;
            }
        }
        List<TZID> availableIDs = Timezone.getAvailableIDs("INCLUDE_ALIAS");
        int i2 = 0;
        int size = availableIDs.size() - 1;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            TZID tzid = availableIDs.get(i3);
            int compareTo = tzid.canonical().compareTo(sb2);
            if (compareTo < 0) {
                i2 = i3 + 1;
            } else {
                if (compareTo <= 0) {
                    parsedEntity.put(TimezoneElement.TIMEZONE_ID, tzid);
                    parseLog.setPosition(i);
                    return;
                }
                size = i3 - 1;
            }
        }
        parseLog.setError(position, "Cannot parse to timezone id: " + sb2);
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<TZID> getElement() {
        return TimezoneElement.TIMEZONE_ID;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> withElement(ChronoElement<TZID> chronoElement) {
        return INSTANCE;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return INSTANCE;
    }
}
