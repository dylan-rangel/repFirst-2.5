package net.time4j.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.time4j.engine.Chronology;
import net.time4j.engine.TimePoint;

/* loaded from: classes3.dex */
public final class TimeAxis<U, T extends TimePoint<U, T>> extends Chronology<T> implements TimeLine<T> {
    private final Map<ChronoElement<?>, U> baseUnits;
    private final CalendarSystem<T> calendarSystem;
    private final Map<U, Set<U>> convertibleUnits;
    private final T max;
    private final T min;
    private final ChronoElement<T> self;
    private final TimeLine<T> timeline;
    private final Map<U, Double> unitLengths;
    private final Map<U, UnitRule<T>> unitRules;
    private final Class<U> unitType;

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public /* bridge */ /* synthetic */ Object createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
        return createFrom((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
    }

    private TimeAxis(Class<T> cls, Class<U> cls2, ChronoMerger<T> chronoMerger, Map<ChronoElement<?>, ElementRule<T, ?>> map, Map<U, UnitRule<T>> map2, final Map<U, Double> map3, Map<U, Set<U>> map4, List<ChronoExtension> list, Map<ChronoElement<?>, U> map5, T t, T t2, CalendarSystem<T> calendarSystem, TimeLine<T> timeLine) {
        super(cls, chronoMerger, map, list);
        this.unitType = cls2;
        this.unitRules = Collections.unmodifiableMap(map2);
        this.unitLengths = Collections.unmodifiableMap(map3);
        this.convertibleUnits = Collections.unmodifiableMap(map4);
        this.baseUnits = Collections.unmodifiableMap(map5);
        this.min = t;
        this.max = t2;
        this.calendarSystem = calendarSystem;
        this.self = new SelfElement(cls, t, t2);
        if (timeLine == null) {
            ArrayList arrayList = new ArrayList(map2.keySet());
            Collections.sort(arrayList, new Comparator<U>() { // from class: net.time4j.engine.TimeAxis.1
                @Override // java.util.Comparator
                public int compare(U u, U u2) {
                    return Double.compare(TimeAxis.getLength(map3, u), TimeAxis.getLength(map3, u2));
                }
            });
            this.timeline = new DefaultTimeLine(arrayList.get(0), t, t2);
            return;
        }
        this.timeline = timeLine;
    }

    public Class<U> getUnitType() {
        return this.unitType;
    }

    public Set<U> getRegisteredUnits() {
        return this.unitRules.keySet();
    }

    public boolean isRegistered(U u) {
        return this.unitRules.containsKey(u);
    }

    public boolean isSupported(U u) {
        if (isRegistered((TimeAxis<U, T>) u)) {
            return true;
        }
        return (u instanceof BasicUnit) && ((BasicUnit) BasicUnit.class.cast(u)).derive(this) != null;
    }

    public double getLength(U u) {
        return getLength(this.unitLengths, u);
    }

    public boolean isConvertible(U u, U u2) {
        Set<U> set = this.convertibleUnits.get(u);
        return set != null && set.contains(u2);
    }

    public Comparator<? super U> unitComparator() {
        return new Comparator<U>() { // from class: net.time4j.engine.TimeAxis.2
            @Override // java.util.Comparator
            public int compare(U u, U u2) {
                return Double.compare(TimeAxis.this.getLength(u2), TimeAxis.this.getLength(u));
            }
        };
    }

    @Override // java.util.Comparator
    public int compare(T t, T t2) {
        return t.compareTo(t2);
    }

    public boolean hasBaseUnit(ChronoElement<?> chronoElement) {
        boolean z = false;
        if (chronoElement == null) {
            return false;
        }
        boolean containsKey = this.baseUnits.containsKey(chronoElement);
        if (containsKey || !(chronoElement instanceof BasicElement)) {
            return containsKey;
        }
        ChronoElement<?> parent = ((BasicElement) chronoElement).getParent();
        if (parent != null && this.baseUnits.containsKey(parent)) {
            z = true;
        }
        return z;
    }

    public U getBaseUnit(ChronoElement<?> chronoElement) {
        if (chronoElement == null) {
            throw new NullPointerException("Missing element.");
        }
        U u = this.baseUnits.get(chronoElement);
        if (u == null && (chronoElement instanceof BasicElement)) {
            u = this.baseUnits.get(((BasicElement) chronoElement).getParent());
        }
        if (u != null) {
            return u;
        }
        throw new ChronoException("Base unit not found for: " + chronoElement.name());
    }

    public T getMinimum() {
        return this.min;
    }

    public T getMaximum() {
        return this.max;
    }

    @Override // net.time4j.engine.TimeLine
    public boolean isCalendrical() {
        return this.calendarSystem != null;
    }

    @Override // net.time4j.engine.Chronology
    public boolean hasCalendarSystem() {
        return this.calendarSystem != null;
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<T> getCalendarSystem() {
        CalendarSystem<T> calendarSystem = this.calendarSystem;
        return calendarSystem == null ? super.getCalendarSystem() : calendarSystem;
    }

    @Override // net.time4j.engine.Chronology
    public CalendarSystem<T> getCalendarSystem(String str) {
        if (str.isEmpty()) {
            return getCalendarSystem();
        }
        return super.getCalendarSystem(str);
    }

    @Override // net.time4j.engine.Chronology, net.time4j.engine.ChronoMerger
    public T createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
        if (chronoEntity.contains(this.self)) {
            return (T) chronoEntity.get(this.self);
        }
        return (T) super.createFrom(chronoEntity, attributeQuery, z, z2);
    }

    public ChronoElement<T> element() {
        return this.self;
    }

    @Override // net.time4j.engine.TimeLine
    public T stepForward(T t) {
        return this.timeline.stepForward(t);
    }

    @Override // net.time4j.engine.TimeLine
    public T stepBackwards(T t) {
        return this.timeline.stepBackwards(t);
    }

    UnitRule<T> getRule(U u) {
        UnitRule<T> derive;
        if (u == null) {
            throw new NullPointerException("Missing chronological unit.");
        }
        if (isRegistered((TimeAxis<U, T>) u)) {
            return this.unitRules.get(u);
        }
        if (!(u instanceof BasicUnit) || (derive = ((BasicUnit) BasicUnit.class.cast(u)).derive(this)) == null) {
            throw new RuleNotFoundException(this, u);
        }
        return derive;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <U> double getLength(Map<U, Double> map, U u) {
        Double d = map.get(u);
        if (d == null) {
            if (u instanceof ChronoUnit) {
                return ((ChronoUnit) ChronoUnit.class.cast(u)).getLength();
            }
            return Double.NaN;
        }
        return d.doubleValue();
    }

    public static final class Builder<U, T extends TimePoint<U, T>> extends Chronology.Builder<T> {
        private final Map<ChronoElement<?>, U> baseUnits;
        private final CalendarSystem<T> calendarSystem;
        private final Map<U, Set<U>> convertibleUnits;
        private final T max;
        private final T min;
        private TimeLine<T> timeline;
        private final Map<U, Double> unitLengths;
        private final Map<U, UnitRule<T>> unitRules;
        private final Class<U> unitType;

        private Builder(Class<U> cls, Class<T> cls2, ChronoMerger<T> chronoMerger, T t, T t2, CalendarSystem<T> calendarSystem, TimeLine<T> timeLine) {
            super(cls2, chronoMerger);
            this.timeline = null;
            if (cls == null) {
                throw new NullPointerException("Missing unit type.");
            }
            if (t == null) {
                throw new NullPointerException("Missing minimum of range.");
            }
            if (t2 == null) {
                throw new NullPointerException("Missing maximum of range.");
            }
            if (Calendrical.class.isAssignableFrom(cls2) && calendarSystem == null) {
                throw new NullPointerException("Missing calendar system.");
            }
            this.unitType = cls;
            this.unitRules = new HashMap();
            this.unitLengths = new HashMap();
            this.convertibleUnits = new HashMap();
            this.baseUnits = new HashMap();
            this.min = t;
            this.max = t2;
            this.calendarSystem = calendarSystem;
            this.timeline = timeLine;
        }

        public static <U, T extends TimePoint<U, T>> Builder<U, T> setUp(Class<U> cls, Class<T> cls2, ChronoMerger<T> chronoMerger, T t, T t2) {
            return new Builder<>(cls, cls2, chronoMerger, t, t2, null, null);
        }

        public static <U, D extends Calendrical<U, D>> Builder<U, D> setUp(Class<U> cls, Class<D> cls2, ChronoMerger<D> chronoMerger, CalendarSystem<D> calendarSystem) {
            Builder<U, D> builder = new Builder<>(cls, cls2, chronoMerger, calendarSystem.transform(calendarSystem.getMinimumSinceUTC()), calendarSystem.transform(calendarSystem.getMaximumSinceUTC()), calendarSystem, null);
            for (EpochDays epochDays : EpochDays.values()) {
                builder.appendElement((ChronoElement) epochDays, epochDays.derive(calendarSystem));
            }
            return builder;
        }

        @Override // net.time4j.engine.Chronology.Builder
        public <V> Builder<U, T> appendElement(ChronoElement<V> chronoElement, ElementRule<T, V> elementRule) {
            super.appendElement((ChronoElement) chronoElement, (ElementRule) elementRule);
            return this;
        }

        public <V> Builder<U, T> appendElement(ChronoElement<V> chronoElement, ElementRule<T, V> elementRule, U u) {
            if (u == null) {
                throw new NullPointerException("Missing base unit.");
            }
            super.appendElement((ChronoElement) chronoElement, (ElementRule) elementRule);
            this.baseUnits.put(chronoElement, u);
            return this;
        }

        public Builder<U, T> appendUnit(U u, UnitRule<T> unitRule, double d) {
            return appendUnit(u, unitRule, d, Collections.emptySet());
        }

        public Builder<U, T> appendUnit(U u, UnitRule<T> unitRule, double d, Set<? extends U> set) {
            if (u == null) {
                throw new NullPointerException("Missing time unit.");
            }
            if (unitRule == null) {
                throw new NullPointerException("Missing unit rule.");
            }
            checkUnitDuplicates(u);
            Iterator<? extends U> it = set.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    throw new NullPointerException("Found convertible unit which is null.");
                }
            }
            if (Double.isNaN(d)) {
                throw new IllegalArgumentException("Not a number: " + d);
            }
            if (Double.isInfinite(d)) {
                throw new IllegalArgumentException("Infinite: " + d);
            }
            this.unitRules.put(u, unitRule);
            this.unitLengths.put(u, Double.valueOf(d));
            HashSet hashSet = new HashSet(set);
            hashSet.remove(u);
            this.convertibleUnits.put(u, hashSet);
            return this;
        }

        @Override // net.time4j.engine.Chronology.Builder
        public Builder<U, T> appendExtension(ChronoExtension chronoExtension) {
            super.appendExtension(chronoExtension);
            return this;
        }

        public Builder<U, T> withTimeLine(TimeLine<T> timeLine) {
            if (timeLine == null) {
                throw new NullPointerException("Missing time line.");
            }
            this.timeline = timeLine;
            return this;
        }

        @Override // net.time4j.engine.Chronology.Builder
        public TimeAxis<U, T> build() {
            if (this.unitRules.isEmpty()) {
                throw new IllegalStateException("No time unit was registered.");
            }
            TimeAxis<U, T> timeAxis = new TimeAxis<>(this.chronoType, this.unitType, this.merger, this.ruleMap, this.unitRules, this.unitLengths, this.convertibleUnits, this.extensions, this.baseUnits, this.min, this.max, this.calendarSystem, this.timeline);
            Chronology.register(timeAxis);
            return timeAxis;
        }

        private void checkUnitDuplicates(U u) {
            if (this.time4j) {
                return;
            }
            Iterator<U> it = this.unitRules.keySet().iterator();
            while (it.hasNext()) {
                if (it.next().equals(u)) {
                    throw new IllegalArgumentException("Unit duplicate found: " + u.toString());
                }
            }
            if (u instanceof Enum) {
                String name = ((Enum) Enum.class.cast(u)).name();
                for (U u2 : this.unitRules.keySet()) {
                    if ((u2 instanceof Enum) && ((Enum) Enum.class.cast(u2)).name().equals(name)) {
                        throw new IllegalArgumentException("Unit duplicate found: " + name);
                    }
                }
            }
        }
    }

    private static class SelfElement<T extends TimePoint<?, T>> extends BasicElement<T> implements ElementRule<T, T> {
        private static final long serialVersionUID = 4777240530511579802L;
        private final T max;
        private final T min;
        private final Class<T> type;

        @Override // net.time4j.engine.ElementRule
        public T getValue(T t) {
            return t;
        }

        @Override // net.time4j.engine.BasicElement
        protected String getVeto(Chronology<?> chronology) {
            return null;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return false;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean isSingleton() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return false;
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(T t, T t2) {
            return t2 != null;
        }

        private SelfElement(Class<T> cls, T t, T t2) {
            super(cls.getName() + "-AXIS");
            this.type = cls;
            this.min = t;
            this.max = t2;
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<T> getType() {
            return this.type;
        }

        @Override // net.time4j.engine.ChronoElement
        public T getDefaultMinimum() {
            return this.min;
        }

        @Override // net.time4j.engine.ChronoElement
        public T getDefaultMaximum() {
            return this.max;
        }

        @Override // net.time4j.engine.ElementRule
        public T getMinimum(T t) {
            return getDefaultMinimum();
        }

        @Override // net.time4j.engine.ElementRule
        public T getMaximum(T t) {
            return getDefaultMaximum();
        }

        @Override // net.time4j.engine.ElementRule
        public T withValue2(T t, T t2, boolean z) {
            if (t2 != null) {
                return t2;
            }
            throw new IllegalArgumentException("Missing value.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(T t) {
            throw new UnsupportedOperationException();
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(T t) {
            throw new UnsupportedOperationException();
        }

        @Override // net.time4j.engine.BasicElement
        protected <X extends ChronoEntity<X>> ElementRule<X, T> derive(Chronology<X> chronology) {
            if (chronology.getChronoType().equals(this.type)) {
                return this;
            }
            return null;
        }
    }

    private static class DefaultTimeLine<U, T extends TimePoint<U, T>> implements TimeLine<T> {
        private final T max;
        private final T min;
        private final U step;

        DefaultTimeLine(U u, T t, T t2) {
            this.step = u;
            this.min = t;
            this.max = t2;
        }

        @Override // net.time4j.engine.TimeLine
        public T stepForward(T t) {
            if (t.compareTo(this.max) >= 0) {
                return null;
            }
            return (T) t.plus(1L, this.step);
        }

        @Override // net.time4j.engine.TimeLine
        public T stepBackwards(T t) {
            if (t.compareTo(this.min) <= 0) {
                return null;
            }
            return (T) t.minus(1L, this.step);
        }

        @Override // net.time4j.engine.TimeLine
        public boolean isCalendrical() {
            return this.max instanceof CalendarDate;
        }

        @Override // java.util.Comparator
        public int compare(T t, T t2) {
            return t.compareTo(t2);
        }
    }
}
