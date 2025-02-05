package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkz implements Runnable {
    private final /* synthetic */ zzki zza;
    private final /* synthetic */ zzkp zzb;

    zzkz(zzkp zzkpVar, zzki zzkiVar) {
        this.zzb = zzkpVar;
        this.zza = zzkiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        zzfkVar = this.zzb.zzb;
        if (zzfkVar == null) {
            this.zzb.zzj().zzg().zza("Failed to send current screen to service");
            return;
        }
        try {
            zzki zzkiVar = this.zza;
            if (zzkiVar == null) {
                zzfkVar.zza(0L, (String) null, (String) null, this.zzb.zza().getPackageName());
            } else {
                zzfkVar.zza(zzkiVar.zzc, this.zza.zza, this.zza.zzb, this.zzb.zza().getPackageName());
            }
            this.zzb.zzal();
        } catch (RemoteException e) {
            this.zzb.zzj().zzg().zza("Failed to send current screen to the service", e);
        }
    }
}
