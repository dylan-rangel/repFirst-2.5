package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhp implements Runnable {
    private final /* synthetic */ zzad zza;
    private final /* synthetic */ zzhj zzb;

    zzhp(zzhj zzhjVar, zzad zzadVar) {
        this.zzb = zzhjVar;
        this.zza = zzadVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzmp zzmpVar;
        zzmp zzmpVar2;
        zzmp zzmpVar3;
        zzmpVar = this.zzb.zza;
        zzmpVar.zzr();
        if (this.zza.zzc.zza() == null) {
            zzmpVar3 = this.zzb.zza;
            zzmpVar3.zza(this.zza);
        } else {
            zzmpVar2 = this.zzb.zza;
            zzmpVar2.zzb(this.zza);
        }
    }
}
