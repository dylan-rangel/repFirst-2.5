package net.time4j.engine;

/* loaded from: classes3.dex */
public interface AttributeQuery {
    boolean contains(AttributeKey<?> attributeKey);

    <A> A get(AttributeKey<A> attributeKey);

    <A> A get(AttributeKey<A> attributeKey, A a);
}
