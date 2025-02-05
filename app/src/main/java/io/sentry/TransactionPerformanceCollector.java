package io.sentry;

import java.util.List;

/* loaded from: classes3.dex */
public interface TransactionPerformanceCollector {
    void close();

    void start(ITransaction iTransaction);

    List<PerformanceCollectionData> stop(ITransaction iTransaction);
}
