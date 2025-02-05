package net.time4j.calendar.service;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import net.time4j.PlainDate;
import net.time4j.calendar.KoreanCalendar;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;

/* loaded from: classes3.dex */
public class KoreanExtension implements ChronoExtension {
    @Override // net.time4j.engine.ChronoExtension
    public boolean accept(Class<?> cls) {
        return cls == PlainDate.class;
    }

    @Override // net.time4j.engine.ChronoExtension
    public Set<ChronoElement<?>> getElements(Locale locale, AttributeQuery attributeQuery) {
        return Collections.emptySet();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoEntity<?>] */
    @Override // net.time4j.engine.ChronoExtension
    public ChronoEntity<?> resolve(ChronoEntity<?> chronoEntity, Locale locale, AttributeQuery attributeQuery) {
        if (!chronoEntity.contains(KoreanCalendar.YEAR_OF_ERA)) {
            return chronoEntity;
        }
        return chronoEntity.with((ChronoElement<Integer>) PlainDate.YEAR, chronoEntity.getInt(KoreanCalendar.YEAR_OF_ERA) - 2333);
    }

    @Override // net.time4j.engine.ChronoExtension
    public boolean canResolve(ChronoElement<?> chronoElement) {
        return chronoElement == KoreanCalendar.YEAR_OF_ERA;
    }
}
