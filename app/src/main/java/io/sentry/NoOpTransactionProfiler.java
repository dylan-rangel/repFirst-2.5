package io.sentry;

import java.util.List;

/* loaded from: classes3.dex */
public final class NoOpTransactionProfiler implements ITransactionProfiler {
    private static final NoOpTransactionProfiler instance = new NoOpTransactionProfiler();

    @Override // io.sentry.ITransactionProfiler
    public void close() {
    }

    @Override // io.sentry.ITransactionProfiler
    public ProfilingTraceData onTransactionFinish(ITransaction iTransaction, List<PerformanceCollectionData> list) {
        return null;
    }

    @Override // io.sentry.ITransactionProfiler
    public void onTransactionStart(ITransaction iTransaction) {
    }

    private NoOpTransactionProfiler() {
    }

    public static NoOpTransactionProfiler getInstance() {
        return instance;
    }
}
