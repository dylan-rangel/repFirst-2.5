package net.time4j.format.expert;

import java.math.BigDecimal;
import java.util.Locale;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
enum FractionalElement implements ChronoElement<BigDecimal> {
    FRACTION;

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
    public Class<BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        return ((BigDecimal) chronoDisplay.get(this)).compareTo((BigDecimal) chronoDisplay2.get(this));
    }

    @Override // net.time4j.engine.ChronoElement
    public BigDecimal getDefaultMinimum() {
        return BigDecimal.ZERO;
    }

    @Override // net.time4j.engine.ChronoElement
    public BigDecimal getDefaultMaximum() {
        return BigDecimal.ONE;
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return name();
    }
}
