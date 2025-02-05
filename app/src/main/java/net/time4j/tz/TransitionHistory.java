package net.time4j.tz;

import java.io.IOException;
import java.util.List;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;

/* loaded from: classes3.dex */
public interface TransitionHistory {
    void dump(Appendable appendable) throws IOException;

    ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime);

    ZonalOffset getInitialOffset();

    ZonalTransition getNextTransition(UnixTime unixTime);

    ZonalTransition getStartTransition(UnixTime unixTime);

    List<ZonalTransition> getStdTransitions();

    List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2);

    List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime);

    boolean hasNegativeDST();

    boolean isEmpty();
}
