package net.time4j.engine;

import java.util.Comparator;

/* loaded from: classes3.dex */
public interface TimeLine<T> extends Comparator<T> {
    boolean isCalendrical();

    T stepBackwards(T t);

    T stepForward(T t);
}
