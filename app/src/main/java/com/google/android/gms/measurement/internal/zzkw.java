package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkw implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ boolean zzb;
    private final /* synthetic */ zznc zzc;
    private final /* synthetic */ zzkp zzd;

    zzkw(zzkp zzkpVar, zzo zzoVar, boolean z, zznc zzncVar) {
        this.zzd = zzkpVar;
        this.zza = zzoVar;
        this.zzb = z;
        this.zzc = zzncVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        zzfkVar = this.zzd.zzb;
        if (zzfkVar == null) {
            this.zzd.zzj().zzg().zza("Discarding data. Failed to set user property");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        this.zzd.zza(zzfkVar, this.zzb ? null : this.zzc, this.zza);
        this.zzd.zzal();
    }
}
