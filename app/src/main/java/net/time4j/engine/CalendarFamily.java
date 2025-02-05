package net.time4j.engine;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.Chronology;

/* loaded from: classes3.dex */
public final class CalendarFamily<T extends CalendarVariant<T>> extends Chronology<T> {
    private final Map<String, ? extends CalendarSystem<T>> calendars;

    @Override // net.time4j.engine.Chronology
    public boolean hasCalendarSystem() {
        return true;
    }

    private CalendarFamily(Class<T> cls, ChronoMerger<T> chronoMerger, Map<ChronoElement<?>, ElementRule<T, ?>> map, List<ChronoExtension> list, Map<String, ? extends CalendarSystem<T>> map2) {
        super(cls, chronoMerger, map, list);
        this.calendars = map2;
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<T> getCalendarSystem() {
        throw new ChronoException("Cannot determine calendar system without variant.");
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<T> getCalendarSystem(String str) {
        if (str.isEmpty()) {
            return getCalendarSystem();
        }
        CalendarSystem<T> calendarSystem = this.calendars.get(str);
        return calendarSystem == null ? super.getCalendarSystem(str) : calendarSystem;
    }

    public TimeLine<T> getTimeLine(String str) {
        return new CalendarTimeLine(this, str);
    }

    public TimeLine<T> getTimeLine(VariantSource variantSource) {
        return getTimeLine(variantSource.getVariant());
    }

    @Override // net.time4j.engine.Chronology
    public boolean isSupported(ChronoElement<?> chronoElement) {
        return super.isSupported(chronoElement) || (chronoElement instanceof EpochDays);
    }

    public static final class Builder<T extends CalendarVariant<T>> extends Chronology.Builder<T> {
        private final Map<String, ? extends CalendarSystem<T>> calendars;

        private Builder(Class<T> cls, ChronoMerger<T> chronoMerger, Map<String, ? extends CalendarSystem<T>> map) {
            super(cls, chronoMerger);
            if (map.isEmpty()) {
                throw new IllegalArgumentException("Missing calendar variants.");
            }
            this.calendars = map;
        }

        public static <T extends CalendarVariant<T>> Builder<T> setUp(Class<T> cls, ChronoMerger<T> chronoMerger, Map<String, ? extends CalendarSystem<T>> map) {
            return new Builder<>(cls, chronoMerger, map);
        }

        @Override // net.time4j.engine.Chronology.Builder
        public <V> Builder<T> appendElement(ChronoElement<V> chronoElement, ElementRule<T, V> elementRule) {
            super.appendElement((ChronoElement) chronoElement, (ElementRule) elementRule);
            return this;
        }

        @Override // net.time4j.engine.Chronology.Builder
        public Builder<T> appendExtension(ChronoExtension chronoExtension) {
            super.appendExtension(chronoExtension);
            return this;
        }

        @Override // net.time4j.engine.Chronology.Builder
        public CalendarFamily<T> build() {
            CalendarFamily<T> calendarFamily = new CalendarFamily<>(this.chronoType, this.merger, this.ruleMap, this.extensions, this.calendars);
            Chronology.register(calendarFamily);
            return calendarFamily;
        }
    }

    private static class CalendarTimeLine<D extends CalendarVariant<D>> implements TimeLine<D>, Serializable {
        private final transient CalendarSystem<D> calsys;
        private final Class<D> chronoType;
        private final String variant;

        @Override // net.time4j.engine.TimeLine
        public boolean isCalendrical() {
            return true;
        }

        private CalendarTimeLine(Chronology<D> chronology, String str) {
            this.calsys = chronology.getCalendarSystem(str);
            this.chronoType = chronology.getChronoType();
            this.variant = str;
        }

        @Override // net.time4j.engine.TimeLine
        public D stepForward(D d) {
            if (d.getDaysSinceEpochUTC() == this.calsys.getMaximumSinceUTC()) {
                return null;
            }
            return (D) d.plus(CalendarDays.ONE);
        }

        @Override // net.time4j.engine.TimeLine
        public D stepBackwards(D d) {
            if (d.getDaysSinceEpochUTC() == this.calsys.getMinimumSinceUTC()) {
                return null;
            }
            return (D) d.minus(CalendarDays.ONE);
        }

        @Override // java.util.Comparator
        public int compare(D d, D d2) {
            long daysSinceEpochUTC = d.getDaysSinceEpochUTC();
            long daysSinceEpochUTC2 = d2.getDaysSinceEpochUTC();
            if (daysSinceEpochUTC < daysSinceEpochUTC2) {
                return -1;
            }
            return daysSinceEpochUTC > daysSinceEpochUTC2 ? 1 : 0;
        }

        @Override // java.util.Comparator
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CalendarTimeLine)) {
                return false;
            }
            CalendarTimeLine calendarTimeLine = (CalendarTimeLine) obj;
            return this.chronoType == calendarTimeLine.chronoType && this.variant.equals(calendarTimeLine.variant);
        }

        public int hashCode() {
            return this.chronoType.hashCode() + (this.variant.hashCode() * 31);
        }

        private Object readResolve() throws ObjectStreamException {
            return new CalendarTimeLine(Chronology.lookup(this.chronoType), this.variant);
        }
    }
}
