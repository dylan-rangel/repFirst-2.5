package net.time4j.engine;

import java.io.Serializable;
import java.lang.Comparable;
import java.util.Locale;
import net.time4j.base.UnixTime;

/* loaded from: classes3.dex */
public abstract class BasicElement<V extends Comparable<V>> implements ChronoElement<V>, Serializable {
    private final int hash;
    private final int identity;
    private final String name;

    protected <T extends ChronoEntity<T>> ElementRule<T, V> derive(Chronology<T> chronology) {
        return null;
    }

    protected boolean doEquals(BasicElement<?> basicElement) {
        return true;
    }

    protected ChronoElement<?> getParent() {
        return null;
    }

    @Override // net.time4j.engine.ChronoElement
    public char getSymbol() {
        return (char) 0;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isLenient() {
        return false;
    }

    public boolean isLocal() {
        return true;
    }

    protected boolean isSingleton() {
        return false;
    }

    protected BasicElement(String str) {
        if (str.trim().isEmpty()) {
            throw new IllegalArgumentException("Element name is empty or contains only white space.");
        }
        this.name = str;
        int hashCode = str.hashCode();
        this.hash = hashCode;
        if (!isSingleton()) {
            hashCode = -1;
        } else if (hashCode == -1) {
            hashCode = ~hashCode;
        }
        this.identity = hashCode;
    }

    @Override // net.time4j.engine.ChronoElement
    public final String name() {
        return this.name;
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return this.name;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        return ((Comparable) chronoDisplay.get(this)).compareTo(chronoDisplay2.get(this));
    }

    @Override // java.util.Comparator
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicElement<?> basicElement = (BasicElement) obj;
        int i = this.identity;
        if (i == basicElement.identity) {
            if (i != -1) {
                return true;
            }
            if (name().equals(basicElement.name()) && doEquals(basicElement)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.hash;
    }

    public String toString() {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder(name.length() + 32);
        sb.append(name);
        sb.append('@');
        sb.append(this.name);
        return sb.toString();
    }

    protected String getVeto(Chronology<?> chronology) {
        if (!isLocal() || !UnixTime.class.isAssignableFrom(chronology.getChronoType())) {
            return null;
        }
        return "Accessing the local element [" + this.name + "] from a global type requires a timezone.\n- Try to apply a zonal query like \"" + this.name + ".atUTC()\".\n- Or try to first convert the global type to a zonal timestamp: \"moment.toZonalTimestamp(...)\".\n- If used in formatting then consider \"ChronoFormatter.withTimezone(TZID)\".";
    }
}
