package net.time4j.calendar;

import java.util.Locale;
import net.time4j.ClockUnit;
import net.time4j.Moment;
import net.time4j.base.TimeSource;
import net.time4j.calendar.EastAsianCalendar;
import net.time4j.calendar.service.GenericDatePatterns;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.StartOfDay;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

/* loaded from: classes3.dex */
abstract class AbstractMergerEA<C extends EastAsianCalendar<?, C>> implements ChronoMerger<C> {
    private final Class<C> chronoType;

    @Override // net.time4j.engine.ChronoMerger
    public abstract C createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2);

    @Override // net.time4j.engine.ChronoMerger
    public int getDefaultPivotYear() {
        return 100;
    }

    @Override // net.time4j.engine.ChronoMerger
    public ChronoDisplay preformat(C c, AttributeQuery attributeQuery) {
        return c;
    }

    @Override // net.time4j.engine.ChronoMerger
    public Chronology<?> preparser() {
        return null;
    }

    @Override // net.time4j.engine.ChronoMerger
    public /* bridge */ /* synthetic */ Object createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
        return createFrom((TimeSource<?>) timeSource, attributeQuery);
    }

    @Override // net.time4j.engine.ChronoMerger
    public /* bridge */ /* synthetic */ Object createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
        return createFrom((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
    }

    AbstractMergerEA(Class<C> cls) {
        this.chronoType = cls;
    }

    @Override // net.time4j.engine.ChronoMerger
    public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
        return GenericDatePatterns.get("chinese", displayStyle, locale);
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [net.time4j.base.UnixTime] */
    @Override // net.time4j.engine.ChronoMerger
    public C createFrom(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
        TZID id;
        if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
            id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
        } else {
            if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                return null;
            }
            id = Timezone.ofSystem().getID();
        }
        StartOfDay startOfDay = (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay());
        return (C) Moment.from(timeSource.currentTime()).toZonalTimestamp(id).minus(startOfDay.getDeviation(r4.getCalendarDate(), id), ClockUnit.SECONDS).getCalendarDate().transform(this.chronoType);
    }

    @Override // net.time4j.engine.ChronoMerger
    public StartOfDay getDefaultStartOfDay() {
        return StartOfDay.MIDNIGHT;
    }
}
