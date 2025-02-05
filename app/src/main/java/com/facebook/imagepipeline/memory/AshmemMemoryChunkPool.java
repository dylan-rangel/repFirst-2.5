package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmableRegistry;

/* loaded from: classes.dex */
public class AshmemMemoryChunkPool extends MemoryChunkPool {
    public AshmemMemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker ashmemMemoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, ashmemMemoryChunkPoolStatsTracker);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.imagepipeline.memory.MemoryChunkPool, com.facebook.imagepipeline.memory.BasePool
    /* renamed from: alloc */
    public MemoryChunk alloc2(int bucketedSize) {
        return new AshmemMemoryChunk(bucketedSize);
    }
}
