package net.time4j.format;

import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoException;
import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
public class RawValues {
    private ChronoDisplay rawValues = new EmptyRawValues();

    public void accept(ChronoDisplay chronoDisplay) {
        if (chronoDisplay == null) {
            throw new NullPointerException("Missing raw values.");
        }
        this.rawValues = chronoDisplay;
    }

    public ChronoDisplay get() {
        return this.rawValues;
    }

    private static class EmptyRawValues implements ChronoDisplay {
        @Override // net.time4j.engine.ChronoDisplay
        public boolean contains(ChronoElement<?> chronoElement) {
            return false;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public int getInt(ChronoElement<Integer> chronoElement) {
            return Integer.MIN_VALUE;
        }

        @Override // net.time4j.engine.ChronoDisplay
        public boolean hasTimezone() {
            return false;
        }

        public String toString() {
            return "raw-values={}";
        }

        private EmptyRawValues() {
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V get(ChronoElement<V> chronoElement) {
            throw new ChronoException("Not supported:" + chronoElement.name());
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMinimum(ChronoElement<V> chronoElement) {
            throw new ChronoException("Not supported:" + chronoElement.name());
        }

        @Override // net.time4j.engine.ChronoDisplay
        public <V> V getMaximum(ChronoElement<V> chronoElement) {
            throw new ChronoException("Not supported:" + chronoElement.name());
        }

        @Override // net.time4j.engine.ChronoDisplay
        public TZID getTimezone() {
            throw new ChronoException("Timezone does not exist.");
        }
    }
}
