package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.5.0 */
/* loaded from: classes2.dex */
final class zzdo extends zzdf.zza {
    private final /* synthetic */ Boolean zzc;
    private final /* synthetic */ zzdf zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdo(zzdf zzdfVar, Boolean bool) {
        super(zzdfVar);
        this.zzd = zzdfVar;
        this.zzc = bool;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        zzcu zzcuVar;
        zzcu zzcuVar2;
        if (this.zzc != null) {
            zzcuVar2 = this.zzd.zzj;
            ((zzcu) Preconditions.checkNotNull(zzcuVar2)).setMeasurementEnabled(this.zzc.booleanValue(), this.zza);
        } else {
            zzcuVar = this.zzd.zzj;
            ((zzcu) Preconditions.checkNotNull(zzcuVar)).clearMeasurementEnabled(this.zza);
        }
    }
}
