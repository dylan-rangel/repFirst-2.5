package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkl implements Runnable {
    private final /* synthetic */ zzkh zza;

    zzkl(zzkh zzkhVar) {
        this.zza = zzkhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzki zzkiVar;
        zzkh zzkhVar = this.zza;
        zzkiVar = zzkhVar.zzh;
        zzkhVar.zza = zzkiVar;
    }
}
