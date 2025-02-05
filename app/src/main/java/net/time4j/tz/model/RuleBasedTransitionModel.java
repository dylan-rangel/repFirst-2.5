package net.time4j.tz.model;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.time4j.Moment;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.base.MathUtils;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.engine.EpochDays;
import net.time4j.format.CalendarText;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class RuleBasedTransitionModel extends TransitionModel {
    private static final int LAST_CACHED_YEAR = GregorianMath.readYear(GregorianMath.toPackedDate(EpochDays.MODIFIED_JULIAN_DATE.transform(TransitionModel.getFutureMoment(100), EpochDays.UNIX)));
    private static final long serialVersionUID = 2456700806862862287L;
    private final transient boolean gregorian;
    private final transient ZonalTransition initial;
    private final transient List<DaylightSavingRule> rules;
    private final transient List<ZonalTransition> stdTransitions;
    private final transient ConcurrentMap<Integer, List<ZonalTransition>> tCache;

    RuleBasedTransitionModel(ZonalOffset zonalOffset, List<DaylightSavingRule> list) {
        this(zonalOffset, list, true);
    }

    RuleBasedTransitionModel(ZonalOffset zonalOffset, List<DaylightSavingRule> list, boolean z) {
        this(new ZonalTransition(Long.MIN_VALUE, zonalOffset.getIntegralAmount(), zonalOffset.getIntegralAmount(), 0), list, z);
    }

    RuleBasedTransitionModel(ZonalTransition zonalTransition, List<DaylightSavingRule> list, boolean z) {
        ZonalTransition zonalTransition2;
        this.tCache = new ConcurrentHashMap();
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Missing daylight saving rules.");
        }
        if (list.size() >= 128) {
            throw new IllegalArgumentException("Too many daylight saving rules: " + list);
        }
        list = z ? new ArrayList(list) : list;
        Collections.sort(list, RuleComparator.INSTANCE);
        String str = null;
        if (list.size() > 1) {
            for (DaylightSavingRule daylightSavingRule : list) {
                if (str == null) {
                    str = daylightSavingRule.getCalendarType();
                } else if (!str.equals(daylightSavingRule.getCalendarType())) {
                    throw new IllegalArgumentException("Rules with different calendar systems not permitted.");
                }
            }
        }
        this.gregorian = CalendarText.ISO_CALENDAR_TYPE.equals(str);
        if (zonalTransition.getPosixTime() == Long.MIN_VALUE) {
            if (zonalTransition.getDaylightSavingOffset() != 0) {
                throw new IllegalArgumentException("Initial transition must not have any dst-offset: " + zonalTransition);
            }
            zonalTransition2 = new ZonalTransition(Moment.axis().getMinimum().getPosixTime(), zonalTransition.getStandardOffset(), zonalTransition.getStandardOffset(), 0);
        } else {
            if (zonalTransition.getTotalOffset() != getNextTransition(zonalTransition.getPosixTime(), zonalTransition, list).getPreviousOffset()) {
                throw new IllegalArgumentException("Inconsistent model: " + zonalTransition + " / " + list);
            }
            zonalTransition2 = zonalTransition;
        }
        this.initial = zonalTransition2;
        List<DaylightSavingRule> unmodifiableList = Collections.unmodifiableList(list);
        this.rules = unmodifiableList;
        this.stdTransitions = getTransitions(zonalTransition2, unmodifiableList, 0L, TransitionModel.getFutureMoment(1));
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalOffset getInitialOffset() {
        return ZonalOffset.ofTotalSeconds(this.initial.getTotalOffset());
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getStartTransition(UnixTime unixTime) {
        ZonalTransition zonalTransition;
        long posixTime = this.initial.getPosixTime();
        ZonalTransition zonalTransition2 = null;
        if (unixTime.getPosixTime() <= posixTime) {
            return null;
        }
        int standardOffset = this.initial.getStandardOffset();
        int size = this.rules.size();
        int i = size - 1;
        int year = getYear(this.rules.get(0), unixTime.getPosixTime() + getShift(r5, standardOffset, this.rules.get(i).getSavings()));
        List<ZonalTransition> transitions = getTransitions(year);
        for (int i2 = 0; i2 < size; i2++) {
            ZonalTransition zonalTransition3 = transitions.get(i2);
            long posixTime2 = zonalTransition3.getPosixTime();
            if (unixTime.getPosixTime() < posixTime2) {
                if (zonalTransition2 != null) {
                    return zonalTransition2;
                }
                if (i2 == 0) {
                    zonalTransition = getTransitions(year - 1).get(i);
                } else {
                    zonalTransition = transitions.get(i2 - 1);
                }
                return zonalTransition.getPosixTime() > posixTime ? zonalTransition : zonalTransition2;
            }
            if (posixTime2 > posixTime) {
                zonalTransition2 = zonalTransition3;
            }
        }
        return zonalTransition2;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime) {
        return getConflictTransition(gregorianDate, TransitionModel.toLocalSecs(gregorianDate, wallTime));
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getNextTransition(UnixTime unixTime) {
        return getNextTransition(unixTime.getPosixTime(), this.initial, this.rules);
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime) {
        return getValidOffsets(gregorianDate, TransitionModel.toLocalSecs(gregorianDate, wallTime));
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getStdTransitions() {
        return this.stdTransitions;
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2) {
        return getTransitions(this.initial, this.rules, unixTime.getPosixTime(), unixTime2.getPosixTime());
    }

    @Override // net.time4j.tz.model.TransitionModel, net.time4j.tz.TransitionHistory
    public boolean hasNegativeDST() {
        Iterator<DaylightSavingRule> it = this.rules.iterator();
        while (it.hasNext()) {
            if (it.next().getSavings() < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RuleBasedTransitionModel)) {
            return false;
        }
        RuleBasedTransitionModel ruleBasedTransitionModel = (RuleBasedTransitionModel) obj;
        return this.initial.equals(ruleBasedTransitionModel.initial) && this.rules.equals(ruleBasedTransitionModel.rules);
    }

    public int hashCode() {
        return (this.initial.hashCode() * 17) + (this.rules.hashCode() * 37);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(getClass().getName());
        sb.append("[initial=");
        sb.append(this.initial);
        sb.append(",rules=");
        sb.append(this.rules);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.tz.TransitionHistory
    public void dump(Appendable appendable) throws IOException {
        appendable.append("*** Last rules:").append(NEW_LINE);
        Iterator<DaylightSavingRule> it = this.rules.iterator();
        while (it.hasNext()) {
            appendable.append(">>> ").append(it.next().toString()).append(NEW_LINE);
        }
    }

    ZonalTransition getInitialTransition() {
        return this.initial;
    }

    List<DaylightSavingRule> getRules() {
        return this.rules;
    }

    ZonalTransition getConflictTransition(GregorianDate gregorianDate, long j) {
        if (j <= this.initial.getPosixTime() + Math.max(this.initial.getPreviousOffset(), this.initial.getTotalOffset())) {
            return null;
        }
        for (ZonalTransition zonalTransition : getTransitions(gregorianDate)) {
            long posixTime = zonalTransition.getPosixTime();
            if (zonalTransition.isGap()) {
                if (j < zonalTransition.getPreviousOffset() + posixTime) {
                    return null;
                }
                if (j < posixTime + zonalTransition.getTotalOffset()) {
                    return zonalTransition;
                }
            } else if (!zonalTransition.isOverlap()) {
                continue;
            } else {
                if (j < zonalTransition.getTotalOffset() + posixTime) {
                    return null;
                }
                if (j < posixTime + zonalTransition.getPreviousOffset()) {
                    return zonalTransition;
                }
            }
        }
        return null;
    }

    List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, long j) {
        long posixTime = this.initial.getPosixTime();
        int totalOffset = this.initial.getTotalOffset();
        if (j <= posixTime + Math.max(this.initial.getPreviousOffset(), totalOffset)) {
            return TransitionModel.toList(totalOffset);
        }
        for (ZonalTransition zonalTransition : getTransitions(gregorianDate)) {
            long posixTime2 = zonalTransition.getPosixTime();
            int totalOffset2 = zonalTransition.getTotalOffset();
            if (zonalTransition.isGap()) {
                if (j < zonalTransition.getPreviousOffset() + posixTime2) {
                    return TransitionModel.toList(zonalTransition.getPreviousOffset());
                }
                if (j < posixTime2 + totalOffset2) {
                    return Collections.emptyList();
                }
            } else if (!zonalTransition.isOverlap()) {
                continue;
            } else {
                if (j < totalOffset2 + posixTime2) {
                    return TransitionModel.toList(zonalTransition.getPreviousOffset());
                }
                if (j < posixTime2 + zonalTransition.getPreviousOffset()) {
                    return TransitionModel.toList(totalOffset2, zonalTransition.getPreviousOffset());
                }
            }
            totalOffset = totalOffset2;
        }
        return TransitionModel.toList(totalOffset);
    }

    static List<ZonalTransition> getTransitions(ZonalTransition zonalTransition, List<DaylightSavingRule> list, long j, long j2) {
        int i;
        long posixTime = zonalTransition.getPosixTime();
        if (j > j2) {
            throw new IllegalArgumentException("Start after end.");
        }
        if (j2 <= posixTime || j == j2) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int i2 = Integer.MIN_VALUE;
        int size = list.size();
        int i3 = 0;
        int standardOffset = zonalTransition.getStandardOffset();
        while (true) {
            int i4 = i3 % size;
            DaylightSavingRule daylightSavingRule = list.get(i4);
            DaylightSavingRule daylightSavingRule2 = list.get(((i3 - 1) + size) % size);
            int shift = getShift(daylightSavingRule, standardOffset, daylightSavingRule2.getSavings());
            if (i3 == 0) {
                i = size;
                i2 = getYear(daylightSavingRule, Math.max(j, posixTime) + shift);
            } else {
                i = size;
                if (i4 == 0) {
                    i2++;
                }
            }
            long transitionTime = getTransitionTime(daylightSavingRule, i2, shift);
            i3++;
            if (transitionTime >= j2) {
                return Collections.unmodifiableList(arrayList);
            }
            if (transitionTime >= j && transitionTime > posixTime) {
                arrayList.add(new ZonalTransition(transitionTime, standardOffset + daylightSavingRule2.getSavings(), standardOffset + daylightSavingRule.getSavings(), daylightSavingRule.getSavings()));
            }
            size = i;
        }
    }

    private static ZonalTransition getNextTransition(long j, ZonalTransition zonalTransition, List<DaylightSavingRule> list) {
        long max = Math.max(j, zonalTransition.getPosixTime());
        int standardOffset = zonalTransition.getStandardOffset();
        int size = list.size();
        int i = Integer.MIN_VALUE;
        ZonalTransition zonalTransition2 = null;
        int i2 = 0;
        while (zonalTransition2 == null) {
            int i3 = i2 % size;
            DaylightSavingRule daylightSavingRule = list.get(i3);
            DaylightSavingRule daylightSavingRule2 = list.get(((i2 - 1) + size) % size);
            int shift = getShift(daylightSavingRule, standardOffset, daylightSavingRule2.getSavings());
            if (i2 == 0) {
                i = getYear(daylightSavingRule, shift + max);
            } else if (i3 == 0) {
                i++;
            }
            long transitionTime = getTransitionTime(daylightSavingRule, i, shift);
            if (transitionTime > max) {
                zonalTransition2 = new ZonalTransition(transitionTime, standardOffset + daylightSavingRule2.getSavings(), standardOffset + daylightSavingRule.getSavings(), daylightSavingRule.getSavings());
            }
            i2++;
        }
        return zonalTransition2;
    }

    private static int getShift(DaylightSavingRule daylightSavingRule, int i, int i2) {
        OffsetIndicator indicator = daylightSavingRule.getIndicator();
        int i3 = AnonymousClass1.$SwitchMap$net$time4j$tz$model$OffsetIndicator[indicator.ordinal()];
        if (i3 == 1) {
            return 0;
        }
        if (i3 == 2) {
            return i;
        }
        if (i3 == 3) {
            return i + i2;
        }
        throw new UnsupportedOperationException(indicator.name());
    }

    /* renamed from: net.time4j.tz.model.RuleBasedTransitionModel$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$tz$model$OffsetIndicator;

        static {
            int[] iArr = new int[OffsetIndicator.values().length];
            $SwitchMap$net$time4j$tz$model$OffsetIndicator = iArr;
            try {
                iArr[OffsetIndicator.UTC_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$tz$model$OffsetIndicator[OffsetIndicator.STANDARD_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$tz$model$OffsetIndicator[OffsetIndicator.WALL_TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static long getTransitionTime(DaylightSavingRule daylightSavingRule, int i, int i2) {
        return daylightSavingRule.getDate(i).at(daylightSavingRule.getTimeOfDay()).at(ZonalOffset.ofTotalSeconds(i2)).getPosixTime();
    }

    private List<ZonalTransition> getTransitions(GregorianDate gregorianDate) {
        return getTransitions(this.rules.get(0).toCalendarYear(gregorianDate));
    }

    private List<ZonalTransition> getTransitions(int i) {
        List<ZonalTransition> putIfAbsent;
        Integer valueOf = Integer.valueOf(i);
        List<ZonalTransition> list = this.tCache.get(valueOf);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        int standardOffset = this.initial.getStandardOffset();
        int size = this.rules.size();
        for (int i2 = 0; i2 < size; i2++) {
            DaylightSavingRule daylightSavingRule = this.rules.get(i2);
            DaylightSavingRule daylightSavingRule2 = this.rules.get(((i2 - 1) + size) % size);
            arrayList.add(new ZonalTransition(getTransitionTime(daylightSavingRule, i, getShift(daylightSavingRule, standardOffset, daylightSavingRule2.getSavings())), standardOffset + daylightSavingRule2.getSavings(), standardOffset + daylightSavingRule.getSavings(), daylightSavingRule.getSavings()));
        }
        List<ZonalTransition> unmodifiableList = Collections.unmodifiableList(arrayList);
        return (i > LAST_CACHED_YEAR || !this.gregorian || (putIfAbsent = this.tCache.putIfAbsent(valueOf, unmodifiableList)) == null) ? unmodifiableList : putIfAbsent;
    }

    private static int getYear(DaylightSavingRule daylightSavingRule, long j) {
        return daylightSavingRule.toCalendarYear(EpochDays.MODIFIED_JULIAN_DATE.transform(MathUtils.floorDivide(j, 86400), EpochDays.UNIX));
    }

    private Object writeReplace() {
        return new SPX(this, 125);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}
