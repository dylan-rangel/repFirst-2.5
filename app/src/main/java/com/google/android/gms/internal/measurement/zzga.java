package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzga extends ContentObserver {
    private final /* synthetic */ zzfy zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzga(zzfy zzfyVar, Handler handler) {
        super(null);
        this.zza = zzfyVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zza.zzd();
    }
}
