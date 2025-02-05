package net.time4j.engine;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import net.time4j.base.TimeSource;

/* loaded from: classes3.dex */
public class Chronology<T> implements ChronoMerger<T> {
    private static final List<ChronoReference> CHRONOS = new CopyOnWriteArrayList();
    private static final ReferenceQueue<Chronology<?>> QUEUE = new ReferenceQueue<>();
    private final Class<T> chronoType;
    private final List<ChronoExtension> extensions;
    private final Map<ChronoElement<?>, IntElementRule<T>> intRules;
    private final ChronoMerger<T> merger;
    private final Map<ChronoElement<?>, ElementRule<T, ?>> ruleMap;

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T cast(Object obj) {
        return obj;
    }

    public boolean hasCalendarSystem() {
        return false;
    }

    Chronology(Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("Missing chronological type.");
        }
        this.chronoType = cls;
        this.merger = null;
        this.ruleMap = Collections.emptyMap();
        this.extensions = Collections.emptyList();
        this.intRules = Collections.emptyMap();
    }

    Chronology(Class<T> cls, ChronoMerger<T> chronoMerger, Map<ChronoElement<?>, ElementRule<T, ?>> map, List<ChronoExtension> list) {
        if (cls == null) {
            throw new NullPointerException("Missing chronological type.");
        }
        if (chronoMerger == null) {
            throw new NullPointerException("Missing chronological merger.");
        }
        this.chronoType = cls;
        this.merger = chronoMerger;
        Map<ChronoElement<?>, ElementRule<T, ?>> unmodifiableMap = Collections.unmodifiableMap(map);
        this.ruleMap = unmodifiableMap;
        this.extensions = Collections.unmodifiableList(list);
        HashMap hashMap = new HashMap();
        for (ChronoElement<?> chronoElement : unmodifiableMap.keySet()) {
            if (chronoElement.getType() == Integer.class) {
                ElementRule<T, ?> elementRule = this.ruleMap.get(chronoElement);
                if (elementRule instanceof IntElementRule) {
                    hashMap.put(chronoElement, (IntElementRule) elementRule);
                }
            }
        }
        this.intRules = Collections.unmodifiableMap(hashMap);
    }

    public Class<T> getChronoType() {
        return this.chronoType;
    }

    public Set<ChronoElement<?>> getRegisteredElements() {
        return this.ruleMap.keySet();
    }

    public boolean isRegistered(ChronoElement<?> chronoElement) {
        return chronoElement != null && this.ruleMap.containsKey(chronoElement);
    }

    public boolean isSupported(ChronoElement<?> chronoElement) {
        if (chronoElement == null) {
            return false;
        }
        return isRegistered(chronoElement) || getDerivedRule(chronoElement, false) != null;
    }

    @Override // net.time4j.engine.ChronoMerger
    public T createFrom(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
        if (attributeQuery == null) {
            throw new NullPointerException("Missing attributes.");
        }
        return this.merger.createFrom(timeSource, attributeQuery);
    }

    @Override // net.time4j.engine.ChronoMerger
    public T createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
        return this.merger.createFrom(chronoEntity, attributeQuery, z, z2);
    }

    @Override // net.time4j.engine.ChronoMerger
    public ChronoDisplay preformat(T t, AttributeQuery attributeQuery) {
        return this.merger.preformat(t, attributeQuery);
    }

    @Override // net.time4j.engine.ChronoMerger
    public Chronology<?> preparser() {
        return this.merger.preparser();
    }

    @Override // net.time4j.engine.ChronoMerger
    public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
        return this.merger.getFormatPattern(displayStyle, locale);
    }

    @Override // net.time4j.engine.ChronoMerger
    public StartOfDay getDefaultStartOfDay() {
        return this.merger.getDefaultStartOfDay();
    }

    @Override // net.time4j.engine.ChronoMerger
    public int getDefaultPivotYear() {
        return this.merger.getDefaultPivotYear();
    }

    public List<ChronoExtension> getExtensions() {
        return this.extensions;
    }

    public CalendarSystem<T> getCalendarSystem() {
        throw new ChronoException("Calendar system is not available.");
    }

    public CalendarSystem<T> getCalendarSystem(String str) {
        throw new ChronoException("Calendar variant is not available: " + str);
    }

    public final CalendarSystem<T> getCalendarSystem(VariantSource variantSource) {
        return getCalendarSystem(variantSource.getVariant());
    }

    public static <T> Chronology<T> lookup(Class<T> cls) {
        try {
            Class.forName(cls.getName(), true, cls.getClassLoader());
            Chronology chronology = null;
            boolean z = false;
            Iterator<ChronoReference> it = CHRONOS.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Chronology chronology2 = (Chronology) it.next().get();
                if (chronology2 == null) {
                    z = true;
                } else if (chronology2.getChronoType() == cls) {
                    chronology = chronology2;
                    break;
                }
            }
            if (z) {
                purgeQueue();
            }
            return (Chronology) cast(chronology);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    static void register(Chronology<?> chronology) {
        CHRONOS.add(new ChronoReference(chronology, QUEUE));
    }

    <V> ElementRule<T, V> getRule(ChronoElement<V> chronoElement) {
        if (chronoElement == null) {
            throw new NullPointerException("Missing chronological element.");
        }
        ElementRule<T, ?> elementRule = this.ruleMap.get(chronoElement);
        if (elementRule == null && (elementRule = getDerivedRule(chronoElement, true)) == null) {
            throw new RuleNotFoundException((Chronology<?>) this, (ChronoElement<?>) chronoElement);
        }
        return (ElementRule) cast(elementRule);
    }

    IntElementRule<T> getIntegerRule(ChronoElement<Integer> chronoElement) {
        return this.intRules.get(chronoElement);
    }

    private ElementRule<T, ?> getDerivedRule(ChronoElement<?> chronoElement, boolean z) {
        if (!(chronoElement instanceof BasicElement) || !ChronoEntity.class.isAssignableFrom(getChronoType())) {
            return null;
        }
        BasicElement basicElement = (BasicElement) BasicElement.class.cast(chronoElement);
        String veto = z ? basicElement.getVeto(this) : null;
        if (veto == null) {
            return (ElementRule) cast(basicElement.derive((Chronology) cast(this)));
        }
        throw new RuleNotFoundException(veto);
    }

    private static void purgeQueue() {
        while (true) {
            ChronoReference chronoReference = (ChronoReference) QUEUE.poll();
            if (chronoReference == null) {
                return;
            }
            Iterator<ChronoReference> it = CHRONOS.iterator();
            while (true) {
                if (it.hasNext()) {
                    ChronoReference next = it.next();
                    if (next.name.equals(chronoReference.name)) {
                        CHRONOS.remove(next);
                        break;
                    }
                }
            }
        }
    }

    public static class Builder<T extends ChronoEntity<T>> {
        final Class<T> chronoType;
        final List<ChronoExtension> extensions;
        final ChronoMerger<T> merger;
        final Map<ChronoElement<?>, ElementRule<T, ?>> ruleMap;
        final boolean time4j;

        Builder(Class<T> cls, ChronoMerger<T> chronoMerger) {
            if (chronoMerger == null) {
                throw new NullPointerException("Missing chronological merger.");
            }
            this.chronoType = cls;
            this.time4j = cls.getName().startsWith("net.time4j.");
            this.merger = chronoMerger;
            this.ruleMap = new HashMap();
            this.extensions = new ArrayList();
        }

        public static <T extends ChronoEntity<T>> Builder<T> setUp(Class<T> cls, ChronoMerger<T> chronoMerger) {
            if (TimePoint.class.isAssignableFrom(cls)) {
                throw new UnsupportedOperationException("This builder cannot construct a chronology with a time axis, use TimeAxis.Builder instead.");
            }
            return new Builder<>(cls, chronoMerger);
        }

        public <V> Builder<T> appendElement(ChronoElement<V> chronoElement, ElementRule<T, V> elementRule) {
            checkElementDuplicates(chronoElement);
            this.ruleMap.put(chronoElement, elementRule);
            return this;
        }

        public Builder<T> appendExtension(ChronoExtension chronoExtension) {
            if (chronoExtension == null) {
                throw new NullPointerException("Missing chronological extension.");
            }
            if (!this.extensions.contains(chronoExtension)) {
                this.extensions.add(chronoExtension);
            }
            return this;
        }

        public Chronology<T> build() {
            Chronology<T> chronology = new Chronology<>(this.chronoType, this.merger, this.ruleMap, this.extensions);
            Chronology.register(chronology);
            return chronology;
        }

        private void checkElementDuplicates(ChronoElement<?> chronoElement) {
            if (this.time4j) {
                return;
            }
            if (chronoElement == null) {
                throw new NullPointerException("Static initialization problem: Check if given element statically refer to any chronology causing premature class loading.");
            }
            String name = chronoElement.name();
            for (ChronoElement<?> chronoElement2 : this.ruleMap.keySet()) {
                if (chronoElement2.equals(chronoElement) || chronoElement2.name().equals(name)) {
                    throw new IllegalArgumentException("Element duplicate found: " + name);
                }
            }
        }
    }

    private static class ChronoReference extends WeakReference<Chronology<?>> {
        private final String name;

        ChronoReference(Chronology<?> chronology, ReferenceQueue<Chronology<?>> referenceQueue) {
            super(chronology, referenceQueue);
            this.name = ((Chronology) chronology).chronoType.getName();
        }
    }
}
