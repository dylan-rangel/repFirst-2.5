package io.sentry;

/* loaded from: classes3.dex */
public final class JavaMemoryCollector implements ICollector {
    private final Runtime runtime = Runtime.getRuntime();

    @Override // io.sentry.ICollector
    public void setup() {
    }

    @Override // io.sentry.ICollector
    public void collect(PerformanceCollectionData performanceCollectionData) {
        performanceCollectionData.addMemoryData(new MemoryCollectionData(System.currentTimeMillis(), this.runtime.totalMemory() - this.runtime.freeMemory()));
    }
}
