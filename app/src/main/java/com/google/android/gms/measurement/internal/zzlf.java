package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzlf implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzbg zzd;
    private final /* synthetic */ String zze;
    private final /* synthetic */ zzkp zzf;

    zzlf(zzkp zzkpVar, boolean z, zzo zzoVar, boolean z2, zzbg zzbgVar, String str) {
        this.zzf = zzkpVar;
        this.zza = z;
        this.zzb = zzoVar;
        this.zzc = z2;
        this.zzd = zzbgVar;
        this.zze = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        zzfkVar = this.zzf.zzb;
        if (zzfkVar == null) {
            this.zzf.zzj().zzg().zza("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zza) {
            Preconditions.checkNotNull(this.zzb);
            this.zzf.zza(zzfkVar, this.zzc ? null : this.zzd, this.zzb);
        } else {
            try {
                if (TextUtils.isEmpty(this.zze)) {
                    Preconditions.checkNotNull(this.zzb);
                    zzfkVar.zza(this.zzd, this.zzb);
                } else {
                    zzfkVar.zza(this.zzd, this.zze, this.zzf.zzj().zzx());
                }
            } catch (RemoteException e) {
                this.zzf.zzj().zzg().zza("Failed to send event to the service", e);
            }
        }
        this.zzf.zzal();
    }
}
