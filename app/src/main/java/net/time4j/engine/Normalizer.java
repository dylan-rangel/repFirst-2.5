package net.time4j.engine;

/* loaded from: classes3.dex */
public interface Normalizer<U> {
    TimeSpan<U> normalize(TimeSpan<? extends U> timeSpan);
}
