package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import io.sentry.protocol.App;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.5.0 */
/* loaded from: classes2.dex */
public class AppMeasurementDynamiteService extends com.google.android.gms.internal.measurement.zzct {
    zzhf zza = null;
    private final Map<Integer, zzil> zzb = new ArrayMap();

    /* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.5.0 */
    class zza implements zzim {
        private com.google.android.gms.internal.measurement.zzda zza;

        zza(com.google.android.gms.internal.measurement.zzda zzdaVar) {
            this.zza = zzdaVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzim
        public final void interceptEvent(String str, String str2, Bundle bundle, long j) {
            try {
                this.zza.zza(str, str2, bundle, j);
            } catch (RemoteException e) {
                if (AppMeasurementDynamiteService.this.zza != null) {
                    AppMeasurementDynamiteService.this.zza.zzj().zzu().zza("Event interceptor threw exception", e);
                }
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.5.0 */
    class zzb implements zzil {
        private com.google.android.gms.internal.measurement.zzda zza;

        zzb(com.google.android.gms.internal.measurement.zzda zzdaVar) {
            this.zza = zzdaVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzil
        public final void onEvent(String str, String str2, Bundle bundle, long j) {
            try {
                this.zza.zza(str, str2, bundle, j);
            } catch (RemoteException e) {
                if (AppMeasurementDynamiteService.this.zza != null) {
                    AppMeasurementDynamiteService.this.zza.zzj().zzu().zza("Event listener threw exception", e);
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void beginAdUnitExposure(String str, long j) throws RemoteException {
        zza();
        this.zza.zze().zza(str, j);
    }

    @EnsuresNonNull({"scion"})
    private final void zza() {
        if (this.zza == null) {
            throw new IllegalStateException("Attempting to perform action before initialize.");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws RemoteException {
        zza();
        this.zza.zzp().zza(str, str2, bundle);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void clearMeasurementEnabled(long j) throws RemoteException {
        zza();
        this.zza.zzp().zza((Boolean) null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void endAdUnitExposure(String str, long j) throws RemoteException {
        zza();
        this.zza.zze().zzb(str, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void generateEventId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        long zzm = this.zza.zzt().zzm();
        zza();
        this.zza.zzt().zza(zzcvVar, zzm);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getAppInstanceId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.zza.zzl().zzb(new zzi(this, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCachedAppInstanceId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.zza.zzp().zzae());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getConditionalUserProperties(String str, String str2, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.zza.zzl().zzb(new zzl(this, zzcvVar, str, str2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCurrentScreenClass(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.zza.zzp().zzaf());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCurrentScreenName(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.zza.zzp().zzag());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getGmpAppId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.zza.zzp().zzah());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getMaxUserProperties(String str, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.zza.zzp();
        Preconditions.checkNotEmpty(str);
        zza();
        this.zza.zzt().zza(zzcvVar, 25);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getSessionId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zziq zzp = this.zza.zzp();
        zzp.zzl().zzb(new zzjq(zzp, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getTestFlag(com.google.android.gms.internal.measurement.zzcv zzcvVar, int i) throws RemoteException {
        zza();
        if (i == 0) {
            this.zza.zzt().zza(zzcvVar, this.zza.zzp().zzai());
            return;
        }
        if (i == 1) {
            this.zza.zzt().zza(zzcvVar, this.zza.zzp().zzad().longValue());
            return;
        }
        if (i != 2) {
            if (i == 3) {
                this.zza.zzt().zza(zzcvVar, this.zza.zzp().zzac().intValue());
                return;
            } else {
                if (i != 4) {
                    return;
                }
                this.zza.zzt().zza(zzcvVar, this.zza.zzp().zzaa().booleanValue());
                return;
            }
        }
        zznd zzt = this.zza.zzt();
        double doubleValue = this.zza.zzp().zzab().doubleValue();
        Bundle bundle = new Bundle();
        bundle.putDouble("r", doubleValue);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e) {
            zzt.zzu.zzj().zzu().zza("Error returning double value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getUserProperties(String str, String str2, boolean z, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.zza.zzl().zzb(new zzj(this, zzcvVar, str, str2, z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void initForTests(Map map) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void initialize(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzdd zzddVar, long j) throws RemoteException {
        zzhf zzhfVar = this.zza;
        if (zzhfVar == null) {
            this.zza = zzhf.zza((Context) Preconditions.checkNotNull((Context) ObjectWrapper.unwrap(iObjectWrapper)), zzddVar, Long.valueOf(j));
        } else {
            zzhfVar.zzj().zzu().zza("Attempting to initialize multiple times");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void isDataCollectionEnabled(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.zza.zzl().zzb(new zzn(this, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws RemoteException {
        zza();
        this.zza.zzp().zza(str, str2, bundle, z, z2, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logEventAndBundle(String str, String str2, Bundle bundle, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j) throws RemoteException {
        zza();
        Preconditions.checkNotEmpty(str2);
        (bundle != null ? new Bundle(bundle) : new Bundle()).putString("_o", App.TYPE);
        this.zza.zzl().zzb(new zzk(this, zzcvVar, new zzbg(str2, new zzbb(bundle), App.TYPE, j), str));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        zza();
        this.zza.zzj().zza(i, true, false, str, iObjectWrapper == null ? null : ObjectWrapper.unwrap(iObjectWrapper), iObjectWrapper2 == null ? null : ObjectWrapper.unwrap(iObjectWrapper2), iObjectWrapper3 != null ? ObjectWrapper.unwrap(iObjectWrapper3) : null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityCreated((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityDestroyed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityPaused((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityResumed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        Bundle bundle = new Bundle();
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivitySaveInstanceState((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e) {
            this.zza.zzj().zzu().zza("Error returning bundle value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityStarted((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zza();
        zzjx zzjxVar = this.zza.zzp().zza;
        if (zzjxVar != null) {
            this.zza.zzp().zzak();
            zzjxVar.onActivityStopped((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void performAction(Bundle bundle, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j) throws RemoteException {
        zza();
        zzcvVar.zza(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void registerOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzda zzdaVar) throws RemoteException {
        zzil zzilVar;
        zza();
        synchronized (this.zzb) {
            zzilVar = this.zzb.get(Integer.valueOf(zzdaVar.zza()));
            if (zzilVar == null) {
                zzilVar = new zzb(zzdaVar);
                this.zzb.put(Integer.valueOf(zzdaVar.zza()), zzilVar);
            }
        }
        this.zza.zzp().zza(zzilVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void resetAnalyticsData(long j) throws RemoteException {
        zza();
        zziq zzp = this.zza.zzp();
        zzp.zza((String) null);
        zzp.zzl().zzb(new zzjk(zzp, j));
    }

    private final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str) {
        zza();
        this.zza.zzt().zza(zzcvVar, str);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConditionalUserProperty(Bundle bundle, long j) throws RemoteException {
        zza();
        if (bundle == null) {
            this.zza.zzj().zzg().zza("Conditional user property must not be null");
        } else {
            this.zza.zzp().zza(bundle, j);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConsent(final Bundle bundle, final long j) throws RemoteException {
        zza();
        final zziq zzp = this.zza.zzp();
        zzp.zzl().zzc(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziw
            @Override // java.lang.Runnable
            public final void run() {
                zziq zziqVar = zziq.this;
                Bundle bundle2 = bundle;
                long j2 = j;
                if (TextUtils.isEmpty(zziqVar.zzg().zzae())) {
                    zziqVar.zza(bundle2, 0, j2);
                } else {
                    zziqVar.zzj().zzv().zza("Using developer consent only; google app id found");
                }
            }
        });
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConsentThirdParty(Bundle bundle, long j) throws RemoteException {
        zza();
        this.zza.zzp().zza(bundle, -20, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws RemoteException {
        zza();
        this.zza.zzq().zza((Activity) ObjectWrapper.unwrap(iObjectWrapper), str, str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setDataCollectionEnabled(boolean z) throws RemoteException {
        zza();
        zziq zzp = this.zza.zzp();
        zzp.zzu();
        zzp.zzl().zzb(new zzjb(zzp, z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setDefaultEventParameters(Bundle bundle) {
        zza();
        final zziq zzp = this.zza.zzp();
        final Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
        zzp.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzit
            @Override // java.lang.Runnable
            public final void run() {
                zziq.this.zza(bundle2);
            }
        });
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setEventInterceptor(com.google.android.gms.internal.measurement.zzda zzdaVar) throws RemoteException {
        zza();
        zza zzaVar = new zza(zzdaVar);
        if (this.zza.zzl().zzg()) {
            this.zza.zzp().zza(zzaVar);
        } else {
            this.zza.zzl().zzb(new zzm(this, zzaVar));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setInstanceIdProvider(com.google.android.gms.internal.measurement.zzdb zzdbVar) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setMeasurementEnabled(boolean z, long j) throws RemoteException {
        zza();
        this.zza.zzp().zza(Boolean.valueOf(z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setMinimumSessionDuration(long j) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setSessionTimeoutDuration(long j) throws RemoteException {
        zza();
        zziq zzp = this.zza.zzp();
        zzp.zzl().zzb(new zzjd(zzp, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setUserId(final String str, long j) throws RemoteException {
        zza();
        final zziq zzp = this.zza.zzp();
        if (str != null && TextUtils.isEmpty(str)) {
            zzp.zzu.zzj().zzu().zza("User ID must be non-empty or null");
        } else {
            zzp.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziy
                @Override // java.lang.Runnable
                public final void run() {
                    zziq zziqVar = zziq.this;
                    if (zziqVar.zzg().zzb(str)) {
                        zziqVar.zzg().zzag();
                    }
                }
            });
            zzp.zza((String) null, "_id", (Object) str, true, j);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException {
        zza();
        this.zza.zzp().zza(str, str2, ObjectWrapper.unwrap(iObjectWrapper), z, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void unregisterOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzda zzdaVar) throws RemoteException {
        zzil remove;
        zza();
        synchronized (this.zzb) {
            remove = this.zzb.remove(Integer.valueOf(zzdaVar.zza()));
        }
        if (remove == null) {
            remove = new zzb(zzdaVar);
        }
        this.zza.zzp().zzb(remove);
    }
}
