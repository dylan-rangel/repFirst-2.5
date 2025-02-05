package net.time4j.history.internal;

import net.time4j.engine.AttributeKey;
import net.time4j.format.Attributes;
import net.time4j.history.ChronoHistory;

/* loaded from: classes3.dex */
public final class HistoricAttribute {
    public static final AttributeKey<ChronoHistory> CALENDAR_HISTORY = Attributes.createKey("CALENDAR_HISTORY", ChronoHistory.class);
    public static final AttributeKey<Boolean> COMMON_ERA = Attributes.createKey("COMMON_ERA", Boolean.class);
    public static final AttributeKey<Boolean> LATIN_ERA = Attributes.createKey("LATIN_ERA", Boolean.class);

    private HistoricAttribute() {
    }
}
