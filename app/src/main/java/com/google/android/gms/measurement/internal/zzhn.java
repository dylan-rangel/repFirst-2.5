package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhn implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzhj zze;

    zzhn(zzhj zzhjVar, String str, String str2, String str3, long j) {
        this.zze = zzhjVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzmp zzmpVar;
        zzmp zzmpVar2;
        if (this.zza == null) {
            zzmpVar2 = this.zze.zza;
            zzmpVar2.zza(this.zzb, (zzki) null);
        } else {
            zzki zzkiVar = new zzki(this.zzc, this.zza, this.zzd);
            zzmpVar = this.zze.zza;
            zzmpVar.zza(this.zzb, zzkiVar);
        }
    }
}
