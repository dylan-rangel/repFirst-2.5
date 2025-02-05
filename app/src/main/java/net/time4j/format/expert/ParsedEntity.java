package net.time4j.format.expert;

import java.util.Set;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.Chronology;
import net.time4j.format.expert.ParsedEntity;
import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
abstract class ParsedEntity<T extends ParsedEntity<T>> extends ChronoEntity<T> {
    abstract <E> E getResult();

    abstract void put(ChronoElement<?> chronoElement, int i);

    abstract void put(ChronoElement<?> chronoElement, Object obj);

    abstract void setResult(Object obj);

    ParsedEntity() {
    }

    @Override // net.time4j.engine.ChronoEntity
    public /* bridge */ /* synthetic */ ChronoEntity with(ChronoElement chronoElement, int i) {
        return with((ChronoElement<Integer>) chronoElement, i);
    }

    @Override // net.time4j.engine.ChronoEntity
    public /* bridge */ /* synthetic */ ChronoEntity with(ChronoElement chronoElement, Object obj) {
        return with((ChronoElement<ChronoElement>) chronoElement, (ChronoElement) obj);
    }

    @Override // net.time4j.engine.ChronoEntity
    public <V> boolean isValid(ChronoElement<V> chronoElement, V v) {
        if (chronoElement != null) {
            return true;
        }
        throw new NullPointerException("Missing chronological element.");
    }

    @Override // net.time4j.engine.ChronoEntity
    public <V> T with(ChronoElement<V> chronoElement, V v) {
        put((ChronoElement<?>) chronoElement, (Object) v);
        return this;
    }

    @Override // net.time4j.engine.ChronoEntity
    public T with(ChronoElement<Integer> chronoElement, int i) {
        put(chronoElement, i);
        return this;
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public <V> V getMinimum(ChronoElement<V> chronoElement) {
        return chronoElement.getDefaultMinimum();
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public <V> V getMaximum(ChronoElement<V> chronoElement) {
        return chronoElement.getDefaultMaximum();
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public final boolean hasTimezone() {
        return contains(TimezoneElement.TIMEZONE_ID) || contains(TimezoneElement.TIMEZONE_OFFSET);
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public final TZID getTimezone() {
        Object obj;
        if (contains(TimezoneElement.TIMEZONE_ID)) {
            obj = get(TimezoneElement.TIMEZONE_ID);
        } else {
            obj = contains(TimezoneElement.TIMEZONE_OFFSET) ? get(TimezoneElement.TIMEZONE_OFFSET) : null;
        }
        if (obj instanceof TZID) {
            return (TZID) TZID.class.cast(obj);
        }
        return super.getTimezone();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParsedEntity)) {
            return false;
        }
        ParsedEntity parsedEntity = (ParsedEntity) obj;
        Set<ChronoElement<?>> registeredElements = getRegisteredElements();
        Set<ChronoElement<?>> registeredElements2 = parsedEntity.getRegisteredElements();
        if (registeredElements.size() != registeredElements2.size()) {
            return false;
        }
        for (ChronoElement<?> chronoElement : registeredElements) {
            if (!registeredElements2.contains(chronoElement) || !get(chronoElement).equals(parsedEntity.get(chronoElement))) {
                return false;
            }
        }
        Object result = getResult();
        Object result2 = parsedEntity.getResult();
        if (result == null) {
            return result2 == null;
        }
        return result.equals(result2);
    }

    public final int hashCode() {
        int hashCode = getRegisteredElements().hashCode();
        Object result = getResult();
        return result != null ? hashCode + (result.hashCode() * 31) : hashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append('{');
        boolean z = true;
        for (ChronoElement<?> chronoElement : getRegisteredElements()) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(chronoElement.name());
            sb.append('=');
            sb.append(get(chronoElement));
        }
        sb.append('}');
        Object result = getResult();
        if (result != null) {
            sb.append(">>>result=");
            sb.append(result);
        }
        return sb.toString();
    }

    @Override // net.time4j.engine.ChronoEntity
    protected final Chronology<T> getChronology() {
        throw new UnsupportedOperationException("Parsed values do not have any chronology.");
    }
}
