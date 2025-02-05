package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkr implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzo zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zze;
    private final /* synthetic */ zzkp zzf;

    zzkr(zzkp zzkpVar, String str, String str2, zzo zzoVar, boolean z, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzf = zzkpVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzoVar;
        this.zzd = z;
        this.zze = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        Bundle bundle = new Bundle();
        try {
            zzfkVar = this.zzf.zzb;
            if (zzfkVar == null) {
                this.zzf.zzj().zzg().zza("Failed to get user properties; not connected to service", this.zza, this.zzb);
                return;
            }
            Preconditions.checkNotNull(this.zzc);
            Bundle zza = zznd.zza(zzfkVar.zza(this.zza, this.zzb, this.zzd, this.zzc));
            this.zzf.zzal();
            this.zzf.zzq().zza(this.zze, zza);
        } catch (RemoteException e) {
            this.zzf.zzj().zzg().zza("Failed to get user properties; remote exception", this.zza, e);
        } finally {
            this.zzf.zzq().zza(this.zze, bundle);
        }
    }
}
