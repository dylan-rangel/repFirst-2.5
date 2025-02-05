package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzku implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzkp zzd;

    zzku(zzkp zzkpVar, AtomicReference atomicReference, zzo zzoVar, boolean z) {
        this.zzd = zzkpVar;
        this.zza = atomicReference;
        this.zzb = zzoVar;
        this.zzc = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfk zzfkVar;
        synchronized (this.zza) {
            try {
                try {
                    zzfkVar = this.zzd.zzb;
                } finally {
                    this.zza.notify();
                }
            } catch (RemoteException e) {
                this.zzd.zzj().zzg().zza("Failed to get all user properties; remote exception", e);
            }
            if (zzfkVar == null) {
                this.zzd.zzj().zzg().zza("Failed to get all user properties; not connected to service");
                return;
            }
            Preconditions.checkNotNull(this.zzb);
            this.zza.set(zzfkVar.zza(this.zzb, this.zzc));
            this.zzd.zzal();
            this.zza.notify();
        }
    }
}
