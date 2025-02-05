package net.time4j.engine;

/* loaded from: classes3.dex */
public interface Converter<S, T> {
    S from(T t);

    Class<S> getSourceType();

    T translate(S s);
}
