package com.facebook.common.references;

import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class NoOpCloseableReference<T> extends CloseableReference<T> {
    @Override // com.facebook.common.references.CloseableReference
    /* renamed from: clone */
    public CloseableReference<T> mo42clone() {
        return this;
    }

    @Override // com.facebook.common.references.CloseableReference, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    NoOpCloseableReference(T t, ResourceReleaser<T> resourceReleaser, CloseableReference.LeakHandler leakHandler, @Nullable Throwable stacktrace) {
        super(t, resourceReleaser, leakHandler, stacktrace);
    }
}
