package net.time4j.format.expert;

import java.util.Locale;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.format.CalendarText;
import net.time4j.tz.OffsetSign;
import net.time4j.tz.TZID;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
enum TimezoneElement implements ChronoElement<TZID> {
    TIMEZONE_ID,
    TIMEZONE_OFFSET;

    @Override // net.time4j.engine.ChronoElement
    public char getSymbol() {
        return (char) 0;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isLenient() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<TZID> getType() {
        return TZID.class;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        return chronoDisplay.getTimezone().canonical().compareTo(chronoDisplay2.getTimezone().canonical());
    }

    @Override // net.time4j.engine.ChronoElement
    public TZID getDefaultMinimum() {
        return ZonalOffset.ofHours(OffsetSign.BEHIND_UTC, 14);
    }

    @Override // net.time4j.engine.ChronoElement
    public TZID getDefaultMaximum() {
        return ZonalOffset.ofHours(OffsetSign.AHEAD_OF_UTC, 14);
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        String str = CalendarText.getIsoInstance(locale).getTextForms().get("L_zone");
        return str == null ? name() : str;
    }
}
