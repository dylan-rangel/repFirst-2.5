package net.time4j.engine;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.time4j.base.TimeSource;
import net.time4j.engine.ChronoEntity;

/* loaded from: classes3.dex */
public final class BridgeChronology<S, T extends ChronoEntity<T>> extends Chronology<S> {
    private final Converter<S, T> converter;
    private final Chronology<T> delegate;

    @Override // net.time4j.engine.Chronology
    public boolean isSupported(ChronoElement<?> chronoElement) {
        return false;
    }

    public BridgeChronology(Converter<S, T> converter, Chronology<T> chronology) {
        super(converter.getSourceType());
        if (!ChronoEntity.class.isAssignableFrom(chronology.getChronoType())) {
            throw new IllegalArgumentException("Target chronology not compatible with ChronoEntity.");
        }
        this.converter = converter;
        this.delegate = chronology;
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public S createFrom(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
        T createFrom = this.delegate.createFrom(timeSource, attributeQuery);
        if (createFrom == null) {
            return null;
        }
        return this.converter.from(createFrom);
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public S createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
        T createFrom;
        if (this.delegate.getChronoType().isInstance(chronoEntity)) {
            createFrom = this.delegate.getChronoType().cast(chronoEntity);
        } else {
            createFrom = this.delegate.createFrom(chronoEntity, attributeQuery, z, z2);
        }
        if (createFrom == null) {
            return null;
        }
        return (S) this.converter.from(createFrom);
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public ChronoDisplay preformat(S s, AttributeQuery attributeQuery) {
        return this.delegate.preformat(this.converter.translate(s), attributeQuery);
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public Chronology<?> preparser() {
        return this.delegate;
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
        throw new UnsupportedOperationException("Localized format patterns are not available for foreign types.");
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public StartOfDay getDefaultStartOfDay() {
        return this.delegate.getDefaultStartOfDay();
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public int getDefaultPivotYear() {
        return this.delegate.getDefaultPivotYear();
    }

    @Override // net.time4j.engine.Chronology
    public boolean hasCalendarSystem() {
        return this.delegate.hasCalendarSystem();
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<S> getCalendarSystem() {
        return new CalendarSystemProxy(this.converter, this.delegate.getCalendarSystem());
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<S> getCalendarSystem(String str) {
        return new CalendarSystemProxy(this.converter, this.delegate.getCalendarSystem(str));
    }

    @Override // net.time4j.engine.Chronology
    public Set<ChronoElement<?>> getRegisteredElements() {
        return Collections.emptySet();
    }

    @Override // net.time4j.engine.Chronology
    public List<ChronoExtension> getExtensions() {
        return Collections.emptyList();
    }

    private static class CalendarSystemProxy<S, T> implements CalendarSystem<S> {
        private final CalendarSystem<T> calsys;
        private final Converter<S, T> converter;

        CalendarSystemProxy(Converter<S, T> converter, CalendarSystem<T> calendarSystem) {
            this.converter = converter;
            this.calsys = calendarSystem;
        }

        @Override // net.time4j.engine.CalendarSystem
        public S transform(long j) {
            return (S) this.converter.from(this.calsys.transform(j));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(S s) {
            return this.calsys.transform((CalendarSystem<T>) this.converter.translate(s));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return this.calsys.getMinimumSinceUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return this.calsys.getMaximumSinceUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return this.calsys.getEras();
        }
    }
}
