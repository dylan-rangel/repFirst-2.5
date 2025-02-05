package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkx implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zzb;
    private final /* synthetic */ zzkp zzc;

    zzkx(zzkp zzkpVar, zzo zzoVar, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzc = zzkpVar;
        this.zza = zzoVar;
        this.zzb = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        try {
            if (!this.zzc.zzk().zzm().zzh()) {
                this.zzc.zzj().zzv().zza("Analytics storage consent denied; will not get app instance id");
                this.zzc.zzm().zza((String) null);
                this.zzc.zzk().zze.zza(null);
                return;
            }
            zzfkVar = this.zzc.zzb;
            if (zzfkVar == null) {
                this.zzc.zzj().zzg().zza("Failed to get app instance id");
                return;
            }
            Preconditions.checkNotNull(this.zza);
            String zzb = zzfkVar.zzb(this.zza);
            if (zzb != null) {
                this.zzc.zzm().zza(zzb);
                this.zzc.zzk().zze.zza(zzb);
            }
            this.zzc.zzal();
            this.zzc.zzq().zza(this.zzb, zzb);
        } catch (RemoteException e) {
            this.zzc.zzj().zzg().zza("Failed to get app instance id", e);
        } finally {
            this.zzc.zzq().zza(this.zzb, (String) null);
        }
    }
}
