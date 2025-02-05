package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkt implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zzkp zzd;

    zzkt(zzkp zzkpVar, AtomicReference atomicReference, zzo zzoVar, Bundle bundle) {
        this.zzd = zzkpVar;
        this.zza = atomicReference;
        this.zzb = zzoVar;
        this.zzc = bundle;
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
                this.zzd.zzj().zzg().zza("Failed to get trigger URIs; remote exception", e);
            }
            if (zzfkVar == null) {
                this.zzd.zzj().zzg().zza("Failed to get trigger URIs; not connected to service");
                return;
            }
            Preconditions.checkNotNull(this.zzb);
            this.zza.set(zzfkVar.zza(this.zzb, this.zzc));
            this.zzd.zzal();
            this.zza.notify();
        }
    }
}
