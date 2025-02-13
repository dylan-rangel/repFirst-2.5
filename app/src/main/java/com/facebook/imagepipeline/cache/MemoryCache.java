package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.HasDebugData;
import com.facebook.common.internal.Predicate;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public interface MemoryCache<K, V> extends MemoryTrimmable, HasDebugData {

    public interface CacheTrimStrategy {
        double getTrimRatio(MemoryTrimType trimType);
    }

    @Nullable
    CloseableReference<V> cache(K key, CloseableReference<V> value);

    boolean contains(Predicate<K> predicate);

    boolean contains(K key);

    @Nullable
    CloseableReference<V> get(K key);

    int getCount();

    int getSizeInBytes();

    void probe(K key);

    int removeAll(Predicate<K> predicate);
}
