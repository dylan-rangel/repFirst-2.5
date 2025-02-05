package net.time4j.engine;

import java.util.Set;
import net.time4j.engine.ChronoEntity;
import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
public abstract class ChronoEntity<T extends ChronoEntity<T>> implements ChronoDisplay {
    protected abstract Chronology<T> getChronology();

    @Override // net.time4j.engine.ChronoDisplay
    public boolean hasTimezone() {
        return false;
    }

    @Override // net.time4j.engine.ChronoDisplay
    public boolean contains(ChronoElement<?> chronoElement) {
        return getChronology().isSupported(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V get(ChronoElement<V> chronoElement) {
        return getRule(chronoElement).getValue(getContext());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoDisplay
    public int getInt(ChronoElement<Integer> chronoElement) {
        IntElementRule<T> integerRule = getChronology().getIntegerRule(chronoElement);
        try {
            if (integerRule == null) {
                return ((Integer) get(chronoElement)).intValue();
            }
            return integerRule.getInt(getContext());
        } catch (ChronoException unused) {
            return Integer.MIN_VALUE;
        }
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMinimum(ChronoElement<V> chronoElement) {
        return getRule(chronoElement).getMinimum(getContext());
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMaximum(ChronoElement<V> chronoElement) {
        return getRule(chronoElement).getMaximum(getContext());
    }

    public final <R> R get(ChronoFunction<? super T, R> chronoFunction) {
        return chronoFunction.apply(getContext());
    }

    public boolean matches(ChronoCondition<? super T> chronoCondition) {
        return chronoCondition.test(getContext());
    }

    public <V> boolean isValid(ChronoElement<V> chronoElement, V v) {
        if (chronoElement != null) {
            return contains(chronoElement) && getRule(chronoElement).isValid2(getContext(), v);
        }
        throw new NullPointerException("Missing chronological element.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isValid(ChronoElement<Integer> chronoElement, int i) {
        IntElementRule<T> integerRule = getChronology().getIntegerRule(chronoElement);
        if (integerRule != null) {
            return integerRule.isValid((IntElementRule<T>) getContext(), i);
        }
        return isValid((ChronoElement<ChronoElement<Integer>>) chronoElement, (ChronoElement<Integer>) Integer.valueOf(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isValid(ChronoElement<Long> chronoElement, long j) {
        return isValid((ChronoElement<ChronoElement<Long>>) chronoElement, (ChronoElement<Long>) Long.valueOf(j));
    }

    public <V> T with(ChronoElement<V> chronoElement, V v) {
        return getRule(chronoElement).withValue2(getContext(), v, chronoElement.isLenient());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T with(ChronoElement<Integer> chronoElement, int i) {
        IntElementRule<T> integerRule = getChronology().getIntegerRule(chronoElement);
        if (integerRule != null) {
            return integerRule.withValue((IntElementRule<T>) getContext(), i, chronoElement.isLenient());
        }
        return with((ChronoElement<ChronoElement<Integer>>) chronoElement, (ChronoElement<Integer>) Integer.valueOf(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T with(ChronoElement<Long> chronoElement, long j) {
        return with((ChronoElement<ChronoElement<Long>>) chronoElement, (ChronoElement<Long>) Long.valueOf(j));
    }

    public T with(ChronoOperator<T> chronoOperator) {
        return chronoOperator.apply(getContext());
    }

    @Override // net.time4j.engine.ChronoDisplay
    public TZID getTimezone() {
        throw new ChronoException("Timezone not available: " + this);
    }

    public Set<ChronoElement<?>> getRegisteredElements() {
        return getChronology().getRegisteredElements();
    }

    protected T getContext() {
        Chronology<T> chronology = getChronology();
        Class<T> chronoType = chronology.getChronoType();
        if (chronoType.isInstance(this)) {
            return chronoType.cast(this);
        }
        for (ChronoElement<?> chronoElement : chronology.getRegisteredElements()) {
            if (chronoType == chronoElement.getType()) {
                return chronoType.cast(get(chronoElement));
            }
        }
        throw new IllegalStateException("Implementation error: Cannot find entity context.");
    }

    <V> ElementRule<T, V> getRule(ChronoElement<V> chronoElement) {
        return getChronology().getRule(chronoElement);
    }
}
