package net.time4j.tz.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;
import net.time4j.tz.TransitionHistory;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class EmptyTransitionModel implements TransitionHistory, Serializable {
    private static final long serialVersionUID = 1374714021808040253L;
    private final ZonalOffset offset;

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime) {
        return null;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getNextTransition(UnixTime unixTime) {
        return null;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getStartTransition(UnixTime unixTime) {
        return null;
    }

    @Override // net.time4j.tz.TransitionHistory
    public boolean hasNegativeDST() {
        return false;
    }

    @Override // net.time4j.tz.TransitionHistory
    public boolean isEmpty() {
        return true;
    }

    EmptyTransitionModel(ZonalOffset zonalOffset) {
        this.offset = zonalOffset;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalOffset getInitialOffset() {
        return this.offset;
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime) {
        return Collections.singletonList(this.offset);
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getStdTransitions() {
        return Collections.emptyList();
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2) {
        return Collections.emptyList();
    }

    @Override // net.time4j.tz.TransitionHistory
    public void dump(Appendable appendable) throws IOException {
        appendable.append("*** Fixed offset:").append(TransitionModel.NEW_LINE).append(">>> ");
        appendable.append(getInitialOffset().canonical()).append(TransitionModel.NEW_LINE);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EmptyTransitionModel) {
            return this.offset.equals(((EmptyTransitionModel) obj).offset);
        }
        return false;
    }

    public int hashCode() {
        return this.offset.hashCode();
    }

    public String toString() {
        return "EmptyTransitionModel=" + this.offset.canonical();
    }
}
