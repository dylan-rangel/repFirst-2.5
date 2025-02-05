package net.time4j.format.expert;

import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;

/* loaded from: classes3.dex */
final class MergedAttributes implements AttributeQuery {
    private final AttributeQuery inner;
    private final AttributeQuery outer;

    MergedAttributes(AttributeQuery attributeQuery, AttributeQuery attributeQuery2) {
        this.outer = attributeQuery;
        this.inner = attributeQuery2;
    }

    @Override // net.time4j.engine.AttributeQuery
    public boolean contains(AttributeKey<?> attributeKey) {
        return this.outer.contains(attributeKey) || this.inner.contains(attributeKey);
    }

    @Override // net.time4j.engine.AttributeQuery
    public <A> A get(AttributeKey<A> attributeKey) {
        if (this.outer.contains(attributeKey)) {
            return (A) this.outer.get(attributeKey);
        }
        return (A) this.inner.get(attributeKey);
    }

    @Override // net.time4j.engine.AttributeQuery
    public <A> A get(AttributeKey<A> attributeKey, A a) {
        if (this.outer.contains(attributeKey)) {
            return (A) this.outer.get(attributeKey);
        }
        return (A) this.inner.get(attributeKey, a);
    }
}
