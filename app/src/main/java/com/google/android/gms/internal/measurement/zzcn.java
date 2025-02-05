package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzcn implements zzcm {
    @Override // com.google.android.gms.internal.measurement.zzcm
    public final Runnable zza(Runnable runnable) {
        return runnable;
    }

    @Override // com.google.android.gms.internal.measurement.zzcm
    public final <V> Callable<V> zza(Callable<V> callable) {
        return callable;
    }

    zzcn() {
    }
}
