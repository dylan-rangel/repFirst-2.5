package net.time4j.engine;

import java.util.Locale;
import java.util.Set;

/* loaded from: classes3.dex */
public interface ChronoExtension {
    boolean accept(Class<?> cls);

    boolean canResolve(ChronoElement<?> chronoElement);

    Set<ChronoElement<?>> getElements(Locale locale, AttributeQuery attributeQuery);

    ChronoEntity<?> resolve(ChronoEntity<?> chronoEntity, Locale locale, AttributeQuery attributeQuery);
}
