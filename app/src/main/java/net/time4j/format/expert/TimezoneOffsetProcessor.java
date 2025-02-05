package net.time4j.format.expert;

import androidx.core.app.NotificationManagerCompat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.time4j.base.UnixTime;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.Attributes;
import net.time4j.format.DisplayMode;
import net.time4j.format.Leniency;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes3.dex */
final class TimezoneOffsetProcessor implements FormatProcessor<TZID> {
    static final TimezoneOffsetProcessor EXTENDED_LONG_PARSER = new TimezoneOffsetProcessor();
    private final boolean caseInsensitive;
    private final boolean extended;
    private final Leniency lenientMode;
    private final DisplayMode precision;
    private final List<String> zeroOffsets;

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> withElement(ChronoElement<TZID> chronoElement) {
        return this;
    }

    TimezoneOffsetProcessor(DisplayMode displayMode, boolean z, List<String> list) {
        if (displayMode == null) {
            throw new NullPointerException("Missing display mode.");
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Missing zero offsets.");
        }
        ArrayList arrayList = new ArrayList(list);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).trim().isEmpty()) {
                throw new IllegalArgumentException("Zero offset must not be white-space-only.");
            }
        }
        this.precision = displayMode;
        this.extended = z;
        this.zeroOffsets = Collections.unmodifiableList(arrayList);
        this.caseInsensitive = true;
        this.lenientMode = Leniency.SMART;
    }

    private TimezoneOffsetProcessor() {
        this.precision = DisplayMode.LONG;
        this.extended = true;
        this.zeroOffsets = Collections.emptyList();
        this.caseInsensitive = true;
        this.lenientMode = Leniency.SMART;
    }

    private TimezoneOffsetProcessor(DisplayMode displayMode, boolean z, List<String> list, boolean z2, Leniency leniency) {
        this.precision = displayMode;
        this.extended = z;
        this.zeroOffsets = list;
        this.caseInsensitive = z2;
        this.lenientMode = leniency;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        ZonalOffset offset;
        int i;
        int length = appendable instanceof CharSequence ? ((CharSequence) appendable).length() : -1;
        TZID timezone = chronoDisplay.hasTimezone() ? chronoDisplay.getTimezone() : null;
        if (timezone == null) {
            offset = getOffset(chronoDisplay, attributeQuery);
        } else if (timezone instanceof ZonalOffset) {
            offset = (ZonalOffset) timezone;
        } else if (chronoDisplay instanceof UnixTime) {
            offset = Timezone.of(timezone).getOffset((UnixTime) chronoDisplay);
        } else {
            throw new IllegalArgumentException("Cannot extract timezone offset from: " + chronoDisplay);
        }
        int integralAmount = offset.getIntegralAmount();
        int fractionalAmount = offset.getFractionalAmount();
        if ((integralAmount | fractionalAmount) == 0) {
            String str = this.zeroOffsets.get(0);
            appendable.append(str);
            i = str.length();
        } else {
            int i2 = 1;
            appendable.append(integralAmount < 0 || fractionalAmount < 0 ? '-' : '+');
            int abs = Math.abs(integralAmount);
            int i3 = abs / 3600;
            int i4 = (abs / 60) % 60;
            int i5 = abs % 60;
            if (i3 < 10) {
                appendable.append('0');
                i2 = 2;
            }
            String valueOf = String.valueOf(i3);
            appendable.append(valueOf);
            int length2 = i2 + valueOf.length();
            if (this.precision != DisplayMode.SHORT || i4 != 0) {
                if (this.extended) {
                    appendable.append(':');
                    length2++;
                }
                if (i4 < 10) {
                    appendable.append('0');
                    length2++;
                }
                String valueOf2 = String.valueOf(i4);
                appendable.append(valueOf2);
                length2 += valueOf2.length();
                if (this.precision != DisplayMode.SHORT && this.precision != DisplayMode.MEDIUM && (this.precision == DisplayMode.FULL || (i5 | fractionalAmount) != 0)) {
                    if (this.extended) {
                        appendable.append(':');
                        length2++;
                    }
                    if (i5 < 10) {
                        appendable.append('0');
                        length2++;
                    }
                    String valueOf3 = String.valueOf(i5);
                    appendable.append(valueOf3);
                    int length3 = valueOf3.length() + length2;
                    if (fractionalAmount != 0) {
                        appendable.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                        int i6 = length3 + 1;
                        String valueOf4 = String.valueOf(Math.abs(fractionalAmount));
                        int length4 = 9 - valueOf4.length();
                        for (int i7 = 0; i7 < length4; i7++) {
                            appendable.append('0');
                            i6++;
                        }
                        appendable.append(valueOf4);
                        i = valueOf4.length() + i6;
                    } else {
                        i = length3;
                    }
                }
            }
            i = length2;
        }
        if (length != -1 && i > 0 && set != null) {
            set.add(new ElementPosition(TimezoneElement.TIMEZONE_ID, length, length + i));
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0153  */
    @Override // net.time4j.format.expert.FormatProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r11, net.time4j.format.expert.ParseLog r12, net.time4j.engine.AttributeQuery r13, net.time4j.format.expert.ParsedEntity<?> r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.format.expert.TimezoneOffsetProcessor.parse(java.lang.CharSequence, net.time4j.format.expert.ParseLog, net.time4j.engine.AttributeQuery, net.time4j.format.expert.ParsedEntity, boolean):void");
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<TZID> getElement() {
        return TimezoneElement.TIMEZONE_OFFSET;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<TZID> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        return new TimezoneOffsetProcessor(this.precision, this.extended, this.zeroOffsets, ((Boolean) attributeQuery.get(Attributes.PARSE_CASE_INSENSITIVE, Boolean.TRUE)).booleanValue(), (Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimezoneOffsetProcessor)) {
            return false;
        }
        TimezoneOffsetProcessor timezoneOffsetProcessor = (TimezoneOffsetProcessor) obj;
        return this.precision == timezoneOffsetProcessor.precision && this.extended == timezoneOffsetProcessor.extended && this.zeroOffsets.equals(timezoneOffsetProcessor.zeroOffsets);
    }

    public int hashCode() {
        return (this.precision.hashCode() * 7) + (this.zeroOffsets.hashCode() * 31) + (this.extended ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[precision=");
        sb.append(this.precision);
        sb.append(", extended=");
        sb.append(this.extended);
        sb.append(", zero-offsets=");
        sb.append(this.zeroOffsets);
        sb.append(']');
        return sb.toString();
    }

    private static ZonalOffset getOffset(ChronoDisplay chronoDisplay, AttributeQuery attributeQuery) {
        if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
            TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            if (tzid instanceof ZonalOffset) {
                return (ZonalOffset) tzid;
            }
            if (tzid != null) {
                throw new IllegalArgumentException("Use a timezone offset instead of [" + tzid.canonical() + "] when formatting [" + chronoDisplay + "].");
            }
        }
        throw new IllegalArgumentException("Cannot extract timezone offset from format attributes for: " + chronoDisplay);
    }

    private static int parseNum(CharSequence charSequence, int i, Leniency leniency) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 2) {
            int i4 = i + i2;
            char charAt = i4 >= charSequence.length() ? (char) 0 : charSequence.charAt(i4);
            if (charAt < '0' || charAt > '9') {
                return (i2 == 0 || leniency.isStrict()) ? NotificationManagerCompat.IMPORTANCE_UNSPECIFIED : ~i3;
            }
            i3 = (i3 * 10) + (charAt - '0');
            i2++;
        }
        return i3;
    }
}
