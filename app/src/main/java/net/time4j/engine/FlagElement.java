package net.time4j.engine;

import java.util.Locale;

/* loaded from: classes3.dex */
public enum FlagElement implements ChronoElement<Boolean> {
    LEAP_SECOND,
    DAYLIGHT_SAVING;

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
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        boolean contains = chronoDisplay.contains(this);
        if (contains == chronoDisplay2.contains(this)) {
            return 0;
        }
        return contains ? 1 : -1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.engine.ChronoElement
    public Boolean getDefaultMinimum() {
        return Boolean.FALSE;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.engine.ChronoElement
    public Boolean getDefaultMaximum() {
        return Boolean.TRUE;
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return name();
    }
}
