package net.time4j.engine;

/* loaded from: classes3.dex */
public interface Temporal<C> {
    boolean isAfter(C c);

    boolean isBefore(C c);

    boolean isSimultaneous(C c);
}
