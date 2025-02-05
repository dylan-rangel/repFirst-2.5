package net.time4j.engine;

import java.util.Locale;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: classes3.dex */
public enum ValidationElement implements ChronoElement<String> {
    ERROR_MESSAGE;

    @Override // net.time4j.engine.ChronoElement
    public String getDefaultMinimum() {
        return "";
    }

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
    public Class<String> getType() {
        return String.class;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        boolean contains = chronoDisplay.contains(this);
        if (contains == chronoDisplay2.contains(this)) {
            return 0;
        }
        return contains ? 1 : -1;
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDefaultMaximum() {
        return String.valueOf(CharCompanionObject.MAX_VALUE);
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return name();
    }
}
