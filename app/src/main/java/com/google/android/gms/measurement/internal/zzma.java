package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzma implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzlx zzb;

    zzma(zzlx zzlxVar, long j) {
        this.zzb = zzlxVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzlx.zzb(this.zzb, this.zza);
    }
}
