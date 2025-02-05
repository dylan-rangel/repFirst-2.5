package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzjx implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zziq zza;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00c5 A[Catch: RuntimeException -> 0x01d1, TRY_ENTER, TryCatch #0 {RuntimeException -> 0x01d1, blocks: (B:3:0x000b, B:5:0x0018, B:8:0x0029, B:10:0x002f, B:13:0x0040, B:19:0x00c5, B:21:0x00d1, B:24:0x00e2, B:26:0x00e8, B:29:0x00fd, B:31:0x0103, B:34:0x0110, B:36:0x0116, B:37:0x012e, B:38:0x013d, B:42:0x0144, B:46:0x0167, B:47:0x0183, B:49:0x0174, B:50:0x018a, B:52:0x0190, B:54:0x0196, B:56:0x019c, B:58:0x01a2, B:60:0x01aa, B:64:0x01b7, B:66:0x01c5, B:68:0x01cb, B:76:0x0054, B:79:0x005c, B:81:0x0064, B:83:0x006a, B:85:0x0070, B:87:0x0076, B:89:0x007e, B:91:0x0086, B:94:0x0090, B:96:0x0098, B:97:0x00a4, B:99:0x00bc), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0143 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0144 A[Catch: RuntimeException -> 0x01d1, TRY_LEAVE, TryCatch #0 {RuntimeException -> 0x01d1, blocks: (B:3:0x000b, B:5:0x0018, B:8:0x0029, B:10:0x002f, B:13:0x0040, B:19:0x00c5, B:21:0x00d1, B:24:0x00e2, B:26:0x00e8, B:29:0x00fd, B:31:0x0103, B:34:0x0110, B:36:0x0116, B:37:0x012e, B:38:0x013d, B:42:0x0144, B:46:0x0167, B:47:0x0183, B:49:0x0174, B:50:0x018a, B:52:0x0190, B:54:0x0196, B:56:0x019c, B:58:0x01a2, B:60:0x01aa, B:64:0x01b7, B:66:0x01c5, B:68:0x01cb, B:76:0x0054, B:79:0x005c, B:81:0x0064, B:83:0x006a, B:85:0x0070, B:87:0x0076, B:89:0x007e, B:91:0x0086, B:94:0x0090, B:96:0x0098, B:97:0x00a4, B:99:0x00bc), top: B:2:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void zza(com.google.android.gms.measurement.internal.zzjx r17, boolean r18, android.net.Uri r19, java.lang.String r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjx.zza(com.google.android.gms.measurement.internal.zzjx, boolean, android.net.Uri, java.lang.String, java.lang.String):void");
    }

    zzjx(zziq zziqVar) {
        this.zza = zziqVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zza.zzj().zzp().zza("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent == null) {
                return;
            }
            Uri data = intent.getData();
            if (data == null || !data.isHierarchical()) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String string = extras.getString("com.android.vending.referral_url");
                    if (!TextUtils.isEmpty(string)) {
                        data = Uri.parse(string);
                    }
                }
                data = null;
            }
            Uri uri = data;
            if (uri != null && uri.isHierarchical()) {
                this.zza.zzq();
                this.zza.zzl().zzb(new zzka(this, bundle == null, uri, zznd.zza(intent) ? "gs" : "auto", uri.getQueryParameter("referrer")));
            }
        } catch (RuntimeException e) {
            this.zza.zzj().zzg().zza("Throwable caught in onActivityCreated", e);
        } finally {
            this.zza.zzn().zza(activity, bundle);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzn().zza(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        this.zza.zzn().zzb(activity);
        zzlx zzp = this.zza.zzp();
        zzp.zzl().zzb(new zzlz(zzp, zzp.zzb().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        zzlx zzp = this.zza.zzp();
        zzp.zzl().zzb(new zzma(zzp, zzp.zzb().elapsedRealtime()));
        this.zza.zzn().zzc(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zza.zzn().zzb(activity, bundle);
    }
}
