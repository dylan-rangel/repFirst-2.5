package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.5.0 */
/* loaded from: classes2.dex */
final class zzdp extends zzdf.zza {
    private final /* synthetic */ zzdf zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdp(zzdf zzdfVar) {
        super(zzdfVar);
        this.zzc = zzdfVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        zzcu zzcuVar;
        zzcuVar = this.zzc.zzj;
        ((zzcu) Preconditions.checkNotNull(zzcuVar)).resetAnalyticsData(this.zza);
    }
}
