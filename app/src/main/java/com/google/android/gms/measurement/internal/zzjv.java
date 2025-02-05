package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzps;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzjv implements Runnable {
    private final /* synthetic */ zzih zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ long zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ zzih zze;
    private final /* synthetic */ zziq zzf;

    zzjv(zziq zziqVar, zzih zzihVar, long j, long j2, boolean z, zzih zzihVar2) {
        this.zzf = zziqVar;
        this.zza = zzihVar;
        this.zzb = j;
        this.zzc = j2;
        this.zzd = z;
        this.zze = zzihVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzf.zza(this.zza);
        this.zzf.zza(this.zzb, false);
        zziq.zza(this.zzf, this.zza, this.zzc, true, this.zzd);
        if (zzps.zza() && this.zzf.zze().zza(zzbi.zzbs)) {
            zziq.zza(this.zzf, this.zza, this.zze);
        }
    }
}
