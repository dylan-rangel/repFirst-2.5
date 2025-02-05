package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzge implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzgb zzb;

    zzge(zzgb zzgbVar, boolean z) {
        this.zzb = zzgbVar;
        this.zza = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzmp zzmpVar;
        zzmpVar = this.zzb.zzb;
        zzmpVar.zza(this.zza);
    }
}
