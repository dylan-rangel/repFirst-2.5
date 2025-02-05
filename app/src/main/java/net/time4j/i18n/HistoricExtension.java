package net.time4j.i18n;

import java.util.Locale;
import java.util.Set;
import net.time4j.CalendarDateElement;
import net.time4j.PlainDate;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.Leniency;
import net.time4j.format.TextElement;
import net.time4j.history.ChronoHistory;
import net.time4j.history.HistoricDate;
import net.time4j.history.HistoricEra;
import net.time4j.history.YearDefinition;
import net.time4j.history.internal.HistoricAttribute;
import net.time4j.history.internal.StdHistoricalElement;

/* loaded from: classes3.dex */
public class HistoricExtension implements ChronoExtension {
    @Override // net.time4j.engine.ChronoExtension
    public boolean accept(Class<?> cls) {
        return cls == PlainDate.class;
    }

    @Override // net.time4j.engine.ChronoExtension
    public Set<ChronoElement<?>> getElements(Locale locale, AttributeQuery attributeQuery) {
        return getHistory(locale, attributeQuery).getElements();
    }

    @Override // net.time4j.engine.ChronoExtension
    public ChronoEntity<?> resolve(ChronoEntity<?> chronoEntity, Locale locale, AttributeQuery attributeQuery) {
        return resolve(chronoEntity, getHistory(locale, attributeQuery), attributeQuery);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v1, types: [net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoEntity<?>] */
    /* JADX WARN: Type inference failed for: r9v2, types: [net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoEntity<?>] */
    public ChronoEntity<?> resolve(ChronoEntity<?> chronoEntity, ChronoHistory chronoHistory, AttributeQuery attributeQuery) {
        HistoricEra historicEra;
        HistoricEra historicEra2;
        if (chronoEntity.contains(chronoHistory.era())) {
            historicEra2 = (HistoricEra) chronoEntity.get(chronoHistory.era());
        } else if (((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
            historicEra2 = HistoricEra.AD;
        } else {
            historicEra = null;
            if (historicEra == null && chronoEntity.contains(chronoHistory.yearOfEra())) {
                int i = chronoEntity.getInt(chronoHistory.yearOfEra());
                if (chronoEntity.contains(chronoHistory.month()) && chronoEntity.contains(chronoHistory.dayOfMonth())) {
                    PlainDate convert = chronoHistory.convert(HistoricDate.of(historicEra, i, chronoEntity.getInt(chronoHistory.month()), chronoEntity.getInt(chronoHistory.dayOfMonth()), (YearDefinition) attributeQuery.get(ChronoHistory.YEAR_DEFINITION, YearDefinition.DUAL_DATING), chronoHistory.getNewYearStrategy()));
                    chronoEntity.with((ChronoElement<ChronoElement>) chronoHistory.era(), (ChronoElement) null);
                    chronoEntity.with(chronoHistory.yearOfEra(), (TextElement<Integer>) null);
                    chronoEntity.with(chronoHistory.month(), (TextElement<Integer>) null);
                    chronoEntity.with((ChronoElement<ChronoElement>) chronoHistory.dayOfMonth(), (ChronoElement) null);
                    return chronoEntity.with((ChronoElement<CalendarDateElement>) PlainDate.COMPONENT, (CalendarDateElement) convert);
                }
                if (!chronoEntity.contains(chronoHistory.dayOfYear())) {
                    return chronoEntity;
                }
                int i2 = chronoEntity.getInt(chronoHistory.dayOfYear());
                if (chronoEntity.contains(StdHistoricalElement.YEAR_OF_DISPLAY)) {
                    i = chronoEntity.getInt(StdHistoricalElement.YEAR_OF_DISPLAY);
                }
                return chronoEntity.with((ChronoElement<CalendarDateElement>) PlainDate.COMPONENT, (CalendarDateElement) chronoHistory.convert(chronoHistory.getBeginOfYear(historicEra, i)).with(chronoHistory.dayOfYear(), i2));
            }
        }
        historicEra = historicEra2;
        return historicEra == null ? chronoEntity : chronoEntity;
    }

    @Override // net.time4j.engine.ChronoExtension
    public boolean canResolve(ChronoElement<?> chronoElement) {
        return chronoElement instanceof StdHistoricalElement;
    }

    private static ChronoHistory getHistory(Locale locale, AttributeQuery attributeQuery) {
        if (((String) attributeQuery.get(Attributes.CALENDAR_TYPE, CalendarText.ISO_CALENDAR_TYPE)).equals("julian")) {
            return ChronoHistory.PROLEPTIC_JULIAN;
        }
        if (attributeQuery.contains(HistoricAttribute.CALENDAR_HISTORY)) {
            return (ChronoHistory) attributeQuery.get(HistoricAttribute.CALENDAR_HISTORY);
        }
        if (((String) attributeQuery.get(Attributes.CALENDAR_TYPE, CalendarText.ISO_CALENDAR_TYPE)).equals("historic") && attributeQuery.contains(Attributes.CALENDAR_VARIANT)) {
            return ChronoHistory.from((String) attributeQuery.get(Attributes.CALENDAR_VARIANT));
        }
        return ChronoHistory.of(locale);
    }
}
