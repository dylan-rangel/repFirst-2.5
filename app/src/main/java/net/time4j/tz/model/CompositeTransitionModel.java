package net.time4j.tz.model;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class CompositeTransitionModel extends TransitionModel {
    private static final long serialVersionUID = 1749643877954103721L;
    private final transient ArrayTransitionModel arrayModel;
    private transient int hash = 0;
    private final transient ZonalTransition last;
    private final transient RuleBasedTransitionModel ruleModel;
    private final transient int size;

    CompositeTransitionModel(int i, List<ZonalTransition> list, List<DaylightSavingRule> list2, boolean z, boolean z2) {
        this.size = i;
        ArrayTransitionModel arrayTransitionModel = new ArrayTransitionModel(list, z, z2);
        this.arrayModel = arrayTransitionModel;
        ZonalTransition lastTransition = arrayTransitionModel.getLastTransition();
        this.last = lastTransition;
        this.ruleModel = new RuleBasedTransitionModel(lastTransition, list2, z);
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalOffset getInitialOffset() {
        return this.arrayModel.getInitialOffset();
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getStartTransition(UnixTime unixTime) {
        if (unixTime.getPosixTime() < this.last.getPosixTime()) {
            return this.arrayModel.getStartTransition(unixTime);
        }
        ZonalTransition startTransition = this.ruleModel.getStartTransition(unixTime);
        return startTransition == null ? this.last : startTransition;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime) {
        return this.arrayModel.getConflictTransition(gregorianDate, wallTime, this.ruleModel);
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getNextTransition(UnixTime unixTime) {
        ZonalTransition nextTransition = this.arrayModel.getNextTransition(unixTime);
        return nextTransition == null ? this.ruleModel.getNextTransition(unixTime) : nextTransition;
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime) {
        return this.arrayModel.getValidOffsets(gregorianDate, wallTime, this.ruleModel);
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getStdTransitions() {
        return this.arrayModel.getStdTransitions();
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.arrayModel.getTransitions(unixTime, unixTime2));
        arrayList.addAll(this.ruleModel.getTransitions(unixTime, unixTime2));
        return Collections.unmodifiableList(arrayList);
    }

    @Override // net.time4j.tz.TransitionHistory
    public void dump(Appendable appendable) throws IOException {
        this.arrayModel.dump(this.size, appendable);
        this.ruleModel.dump(appendable);
    }

    @Override // net.time4j.tz.model.TransitionModel, net.time4j.tz.TransitionHistory
    public boolean hasNegativeDST() {
        return this.ruleModel.hasNegativeDST() || this.arrayModel.hasNegativeDST();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompositeTransitionModel)) {
            return false;
        }
        CompositeTransitionModel compositeTransitionModel = (CompositeTransitionModel) obj;
        return this.arrayModel.equals(compositeTransitionModel.arrayModel, this.size, compositeTransitionModel.size) && this.ruleModel.getRules().equals(compositeTransitionModel.ruleModel.getRules());
    }

    public int hashCode() {
        int i = this.hash;
        if (i != 0) {
            return i;
        }
        int hashCode = this.arrayModel.hashCode(this.size) + (this.ruleModel.getRules().hashCode() * 37);
        this.hash = hashCode;
        return hashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(getClass().getName());
        sb.append("[transition-count=");
        sb.append(this.size);
        sb.append(",hash=");
        sb.append(hashCode());
        sb.append(",last-rules=");
        sb.append(this.ruleModel.getRules());
        sb.append(']');
        return sb.toString();
    }

    void writeTransitions(ObjectOutput objectOutput) throws IOException {
        this.arrayModel.writeTransitions(this.size, objectOutput);
    }

    List<DaylightSavingRule> getRules() {
        return this.ruleModel.getRules();
    }

    private Object writeReplace() {
        return new SPX(this, 127);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}
