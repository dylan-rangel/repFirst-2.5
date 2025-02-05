package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhy implements Runnable {
    private final /* synthetic */ zznc zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ zzhj zzc;

    zzhy(zzhj zzhjVar, zznc zzncVar, zzo zzoVar) {
        this.zzc = zzhjVar;
        this.zza = zzncVar;
        this.zzb = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzmp zzmpVar;
        zzmp zzmpVar2;
        zzmp zzmpVar3;
        zzmpVar = this.zzc.zza;
        zzmpVar.zzr();
        if (this.zza.zza() == null) {
            zzmpVar3 = this.zzc.zza;
            zzmpVar3.zza(this.zza.zza, this.zzb);
        } else {
            zzmpVar2 = this.zzc.zza;
            zzmpVar2.zza(this.zza, this.zzb);
        }
    }
}
