package com.google.android.gms.iid;

import android.util.Log;

/* loaded from: classes2.dex */
final class zzj implements Runnable {
    private final /* synthetic */ zzg zzbn;
    private final /* synthetic */ zzi zzbo;

    zzj(zzi zziVar, zzg zzgVar) {
        this.zzbo = zziVar;
        this.zzbn = zzgVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zze zzeVar;
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        zzeVar = this.zzbo.zzbm;
        zzeVar.handleIntent(this.zzbn.intent);
        this.zzbn.finish();
    }
}
