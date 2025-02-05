package net.time4j.tz.model;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.time4j.Moment;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.scale.TimeScale;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class ArrayTransitionModel extends TransitionModel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -5264909488983076587L;
    private transient int hash;
    private final transient boolean negativeDST;
    private final transient List<ZonalTransition> stdTransitions;
    private final transient ZonalTransition[] transitions;

    ArrayTransitionModel(List<ZonalTransition> list) {
        this(list, true, true);
    }

    ArrayTransitionModel(List<ZonalTransition> list, boolean z, boolean z2) {
        this.hash = 0;
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Missing timezone transitions.");
        }
        ZonalTransition[] zonalTransitionArr = (ZonalTransition[]) list.toArray(new ZonalTransition[list.size()]);
        boolean z3 = false;
        for (ZonalTransition zonalTransition : zonalTransitionArr) {
            z3 = z3 || zonalTransition.getDaylightSavingOffset() < 0;
        }
        this.negativeDST = z3;
        if (z) {
            Arrays.sort(zonalTransitionArr);
        }
        if (z2) {
            checkSanity(zonalTransitionArr, list);
        }
        this.transitions = zonalTransitionArr;
        this.stdTransitions = getTransitions(zonalTransitionArr, 0L, TransitionModel.getFutureMoment(1));
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalOffset getInitialOffset() {
        return ZonalOffset.ofTotalSeconds(this.transitions[0].getPreviousOffset());
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getStartTransition(UnixTime unixTime) {
        int search = search(unixTime.getPosixTime(), this.transitions);
        if (search == 0) {
            return null;
        }
        return this.transitions[search - 1];
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime) {
        return getConflictTransition(gregorianDate, wallTime, null);
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getNextTransition(UnixTime unixTime) {
        int search = search(unixTime.getPosixTime(), this.transitions);
        ZonalTransition[] zonalTransitionArr = this.transitions;
        if (search == zonalTransitionArr.length) {
            return null;
        }
        return zonalTransitionArr[search];
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime) {
        return getValidOffsets(gregorianDate, wallTime, null);
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getStdTransitions() {
        return this.stdTransitions;
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2) {
        return getTransitions(this.transitions, unixTime.getPosixTime(), unixTime2.getPosixTime());
    }

    @Override // net.time4j.tz.TransitionHistory
    public void dump(Appendable appendable) throws IOException {
        dump(this.transitions.length, appendable);
    }

    @Override // net.time4j.tz.model.TransitionModel, net.time4j.tz.TransitionHistory
    public boolean hasNegativeDST() {
        return this.negativeDST;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ArrayTransitionModel) {
            return Arrays.equals(this.transitions, ((ArrayTransitionModel) obj).transitions);
        }
        return false;
    }

    public int hashCode() {
        int i = this.hash;
        if (i != 0) {
            return i;
        }
        int hashCode = Arrays.hashCode(this.transitions);
        this.hash = hashCode;
        return hashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(getClass().getName());
        sb.append("[transition-count=");
        sb.append(this.transitions.length);
        sb.append(",hash=");
        sb.append(hashCode());
        sb.append(']');
        return sb.toString();
    }

    ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime, RuleBasedTransitionModel ruleBasedTransitionModel) {
        long localSecs = TransitionModel.toLocalSecs(gregorianDate, wallTime);
        int searchLocal = searchLocal(localSecs, this.transitions);
        ZonalTransition[] zonalTransitionArr = this.transitions;
        if (searchLocal == zonalTransitionArr.length) {
            if (ruleBasedTransitionModel == null) {
                return null;
            }
            return ruleBasedTransitionModel.getConflictTransition(gregorianDate, localSecs);
        }
        ZonalTransition zonalTransition = zonalTransitionArr[searchLocal];
        if (zonalTransition.isGap()) {
            if (zonalTransition.getPosixTime() + zonalTransition.getPreviousOffset() <= localSecs) {
                return zonalTransition;
            }
        } else if (zonalTransition.isOverlap() && zonalTransition.getPosixTime() + zonalTransition.getTotalOffset() <= localSecs) {
            return zonalTransition;
        }
        return null;
    }

    List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime, RuleBasedTransitionModel ruleBasedTransitionModel) {
        long localSecs = TransitionModel.toLocalSecs(gregorianDate, wallTime);
        int searchLocal = searchLocal(localSecs, this.transitions);
        ZonalTransition[] zonalTransitionArr = this.transitions;
        if (searchLocal == zonalTransitionArr.length) {
            if (ruleBasedTransitionModel == null) {
                return TransitionModel.toList(zonalTransitionArr[zonalTransitionArr.length - 1].getTotalOffset());
            }
            return ruleBasedTransitionModel.getValidOffsets(gregorianDate, localSecs);
        }
        ZonalTransition zonalTransition = zonalTransitionArr[searchLocal];
        if (zonalTransition.isGap()) {
            if (zonalTransition.getPosixTime() + zonalTransition.getPreviousOffset() <= localSecs) {
                return Collections.emptyList();
            }
        } else if (zonalTransition.isOverlap() && zonalTransition.getPosixTime() + zonalTransition.getTotalOffset() <= localSecs) {
            return TransitionModel.toList(zonalTransition.getTotalOffset(), zonalTransition.getPreviousOffset());
        }
        return TransitionModel.toList(zonalTransition.getPreviousOffset());
    }

    void dump(int i, Appendable appendable) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            TransitionModel.dump(this.transitions[i2], appendable);
        }
    }

    ZonalTransition getLastTransition() {
        return this.transitions[r0.length - 1];
    }

    boolean equals(ArrayTransitionModel arrayTransitionModel, int i, int i2) {
        int min = Math.min(i, this.transitions.length);
        if (min != Math.min(i2, arrayTransitionModel.transitions.length)) {
            return false;
        }
        for (int i3 = 0; i3 < min; i3++) {
            if (!this.transitions[i3].equals(arrayTransitionModel.transitions[i3])) {
                return false;
            }
        }
        return true;
    }

    int hashCode(int i) {
        int min = Math.min(i, this.transitions.length);
        ZonalTransition[] zonalTransitionArr = new ZonalTransition[min];
        System.arraycopy(this.transitions, 0, zonalTransitionArr, 0, min);
        return Arrays.hashCode(zonalTransitionArr);
    }

    void writeTransitions(ObjectOutput objectOutput) throws IOException {
        writeTransitions(this.transitions.length, objectOutput);
    }

    void writeTransitions(int i, ObjectOutput objectOutput) throws IOException {
        SPX.writeTransitions(this.transitions, i, objectOutput);
    }

    private static void checkSanity(ZonalTransition[] zonalTransitionArr, List<ZonalTransition> list) {
        int totalOffset = zonalTransitionArr[0].getTotalOffset();
        for (int i = 1; i < zonalTransitionArr.length; i++) {
            if (totalOffset != zonalTransitionArr[i].getPreviousOffset()) {
                throw new IllegalArgumentException("Model inconsistency detected at: " + Moment.of(zonalTransitionArr[i].getPosixTime(), TimeScale.POSIX) + " (" + zonalTransitionArr[i].getPosixTime() + ")  in transitions: " + list);
            }
            totalOffset = zonalTransitionArr[i].getTotalOffset();
        }
    }

    private static List<ZonalTransition> getTransitions(ZonalTransition[] zonalTransitionArr, long j, long j2) {
        if (j > j2) {
            throw new IllegalArgumentException("Start after end.");
        }
        int search = search(j, zonalTransitionArr);
        int search2 = search(j2, zonalTransitionArr);
        if (search2 == 0) {
            return Collections.emptyList();
        }
        if (search > 0 && zonalTransitionArr[search - 1].getPosixTime() == j) {
            search--;
        }
        int i = search2 - 1;
        if (zonalTransitionArr[i].getPosixTime() == j2) {
            i--;
        }
        if (search > i) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList((i - search) + 1);
        while (search <= i) {
            arrayList.add(zonalTransitionArr[search]);
            search++;
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static int search(long j, ZonalTransition[] zonalTransitionArr) {
        int length = zonalTransitionArr.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) / 2;
            if (zonalTransitionArr[i2].getPosixTime() <= j) {
                i = i2 + 1;
            } else {
                length = i2 - 1;
            }
        }
        return i;
    }

    private static int searchLocal(long j, ZonalTransition[] zonalTransitionArr) {
        int length = zonalTransitionArr.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) / 2;
            if (zonalTransitionArr[i2].getPosixTime() + Math.max(r3.getTotalOffset(), r3.getPreviousOffset()) <= j) {
                i = i2 + 1;
            } else {
                length = i2 - 1;
            }
        }
        return i;
    }

    private Object writeReplace() {
        return new SPX(this, 126);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}
