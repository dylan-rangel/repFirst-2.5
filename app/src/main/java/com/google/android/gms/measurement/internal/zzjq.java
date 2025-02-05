package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzjq implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zza;
    private final /* synthetic */ zziq zzb;

    zzjq(zziq zziqVar, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzb = zziqVar;
        this.zza = zzcvVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            com.google.android.gms.measurement.internal.zziq r0 = r7.zzb
            com.google.android.gms.measurement.internal.zzlx r0 = r0.zzp()
            boolean r1 = com.google.android.gms.internal.measurement.zzpr.zza()
            r2 = 0
            if (r1 == 0) goto L67
            com.google.android.gms.measurement.internal.zzaf r1 = r0.zze()
            com.google.android.gms.measurement.internal.zzfi<java.lang.Boolean> r3 = com.google.android.gms.measurement.internal.zzbi.zzbx
            boolean r1 = r1.zza(r3)
            if (r1 == 0) goto L67
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzk()
            com.google.android.gms.measurement.internal.zzih r1 = r1.zzm()
            boolean r1 = r1.zzh()
            if (r1 != 0) goto L35
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzj()
            com.google.android.gms.measurement.internal.zzft r0 = r0.zzv()
            java.lang.String r1 = "Analytics storage consent denied; will not get session id"
            r0.zza(r1)
            goto L74
        L35:
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzk()
            com.google.android.gms.common.util.Clock r3 = r0.zzb()
            long r3 = r3.currentTimeMillis()
            boolean r1 = r1.zza(r3)
            if (r1 != 0) goto L74
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzk()
            com.google.android.gms.measurement.internal.zzgi r1 = r1.zzl
            long r3 = r1.zza()
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L58
            goto L74
        L58:
            com.google.android.gms.measurement.internal.zzgd r0 = r0.zzk()
            com.google.android.gms.measurement.internal.zzgi r0 = r0.zzl
            long r0 = r0.zza()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            goto L75
        L67:
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzj()
            com.google.android.gms.measurement.internal.zzft r0 = r0.zzv()
            java.lang.String r1 = "getSessionId has been disabled."
            r0.zza(r1)
        L74:
            r0 = r2
        L75:
            if (r0 == 0) goto L89
            com.google.android.gms.measurement.internal.zziq r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzhf r1 = r1.zzu
            com.google.android.gms.measurement.internal.zznd r1 = r1.zzt()
            com.google.android.gms.internal.measurement.zzcv r2 = r7.zza
            long r3 = r0.longValue()
            r1.zza(r2, r3)
            return
        L89:
            com.google.android.gms.internal.measurement.zzcv r0 = r7.zza     // Catch: android.os.RemoteException -> L8f
            r0.zza(r2)     // Catch: android.os.RemoteException -> L8f
            return
        L8f:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zziq r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzhf r1 = r1.zzu
            com.google.android.gms.measurement.internal.zzfr r1 = r1.zzj()
            com.google.android.gms.measurement.internal.zzft r1 = r1.zzg()
            java.lang.String r2 = "getSessionId failed with exception"
            r1.zza(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjq.run():void");
    }
}
