package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;
import androidx.privacysandbox.ads.adservices.java.measurement.MeasurementManagerFutures;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.measurement.zznp;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.internal.measurement.zzoh;
import com.google.android.gms.internal.measurement.zzoi;
import com.google.android.gms.internal.measurement.zzpg;
import com.google.android.gms.internal.measurement.zzps;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzih;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import io.sentry.protocol.App;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import kotlin.Unit;
import org.checkerframework.dataflow.qual.Pure;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zziq extends zze {
    protected zzjx zza;
    final zzu zzb;
    private zzim zzc;
    private final Set<zzil> zzd;
    private boolean zze;
    private final AtomicReference<String> zzf;
    private final Object zzg;
    private boolean zzh;
    private PriorityQueue<zzmh> zzi;
    private zzih zzj;
    private final AtomicLong zzk;
    private long zzl;
    private boolean zzm;
    private zzaw zzn;
    private final zznf zzo;

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zze
    protected final boolean zzz() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzb zzc() {
        return super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfl zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfo zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zzfq zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ zzfr zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zzgd zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ zzgy zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zziq zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkh zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkp zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzlx zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zznd zzq() {
        return super.zzq();
    }

    public final Boolean zzaa() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzl().zza(atomicReference, 15000L, "boolean test flag value", new zzja(this, atomicReference));
    }

    public final Double zzab() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzl().zza(atomicReference, 15000L, "double test flag value", new zzju(this, atomicReference));
    }

    public final Integer zzac() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzl().zza(atomicReference, 15000L, "int test flag value", new zzjr(this, atomicReference));
    }

    public final Long zzad() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzl().zza(atomicReference, 15000L, "long test flag value", new zzjs(this, atomicReference));
    }

    public final String zzae() {
        return this.zzf.get();
    }

    public final String zzaf() {
        zzki zzaa = this.zzu.zzq().zzaa();
        if (zzaa != null) {
            return zzaa.zzb;
        }
        return null;
    }

    public final String zzag() {
        zzki zzaa = this.zzu.zzq().zzaa();
        if (zzaa != null) {
            return zzaa.zza;
        }
        return null;
    }

    public final String zzah() {
        if (this.zzu.zzu() != null) {
            return this.zzu.zzu();
        }
        try {
            return new zzgz(zza(), this.zzu.zzx()).zza("google_app_id");
        } catch (IllegalStateException e) {
            this.zzu.zzj().zzg().zza("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    public final String zzai() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzl().zza(atomicReference, 15000L, "String test flag value", new zzjj(this, atomicReference));
    }

    public final ArrayList<Bundle> zza(String str, String str2) {
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList<>(0);
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get conditional user properties from main thread");
            return new ArrayList<>(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzl().zza(atomicReference, 5000L, "get conditional user properties", new zzjo(this, atomicReference, null, str, str2));
        List list = (List) atomicReference.get();
        if (list == null) {
            zzj().zzg().zza("Timed out waiting for get conditional user properties", null);
            return new ArrayList<>();
        }
        return zznd.zzb((List<zzad>) list);
    }

    public final List<zznc> zza(boolean z) {
        zzu();
        zzj().zzp().zza("Getting user properties (FE)");
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzl().zza(atomicReference, 5000L, "get user properties", new zzji(this, atomicReference, z));
        List<zznc> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzj().zzg().zza("Timed out waiting for get user properties, includeInternal", Boolean.valueOf(z));
        return Collections.emptyList();
    }

    public final Map<String, Object> zza(String str, String str2, boolean z) {
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzl().zza(atomicReference, 5000L, "get user properties", new zzjn(this, atomicReference, null, str, str2, z));
        List<zznc> list = (List) atomicReference.get();
        if (list == null) {
            zzj().zzg().zza("Timed out waiting for handle get user properties, includeInternal", Boolean.valueOf(z));
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zznc zzncVar : list) {
            Object zza = zzncVar.zza();
            if (zza != null) {
                arrayMap.put(zzncVar.zza, zza);
            }
        }
        return arrayMap;
    }

    private final PriorityQueue<zzmh> zzao() {
        Comparator comparing;
        if (this.zzi == null) {
            comparing = Comparator.comparing(new Function() { // from class: com.google.android.gms.measurement.internal.zzip
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((zzmh) obj).zzb);
                }
            }, new Comparator() { // from class: com.google.android.gms.measurement.internal.zzis
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return (((Long) obj).longValue() > ((Long) obj2).longValue() ? 1 : (((Long) obj).longValue() == ((Long) obj2).longValue() ? 0 : -1));
                }
            });
            this.zzi = new PriorityQueue<>(comparing);
        }
        return this.zzi;
    }

    static /* synthetic */ void zza(zziq zziqVar, zzih zzihVar, zzih zzihVar2) {
        boolean zza = zzihVar.zza(zzihVar2, zzih.zza.ANALYTICS_STORAGE, zzih.zza.AD_STORAGE);
        boolean zzb = zzihVar.zzb(zzihVar2, zzih.zza.ANALYTICS_STORAGE, zzih.zza.AD_STORAGE);
        if (zza || zzb) {
            zziqVar.zzg().zzag();
        }
    }

    static /* synthetic */ void zza(zziq zziqVar, zzih zzihVar, long j, boolean z, boolean z2) {
        zziqVar.zzt();
        zziqVar.zzu();
        zzih zzm = zziqVar.zzk().zzm();
        if (j <= zziqVar.zzl && zzih.zza(zzm.zza(), zzihVar.zza())) {
            zziqVar.zzj().zzn().zza("Dropped out-of-date consent setting, proposed settings", zzihVar);
            return;
        }
        if (zziqVar.zzk().zza(zzihVar)) {
            zziqVar.zzl = j;
            zziqVar.zzo().zza(z);
            if (z2) {
                zziqVar.zzo().zza(new AtomicReference<>());
                return;
            }
            return;
        }
        zziqVar.zzj().zzn().zza("Lower precedence consent source ignored, proposed source", Integer.valueOf(zzihVar.zza()));
    }

    protected zziq(zzhf zzhfVar) {
        super(zzhfVar);
        this.zzd = new CopyOnWriteArraySet();
        this.zzg = new Object();
        this.zzh = false;
        this.zzm = true;
        this.zzo = new zzjp(this);
        this.zzf = new AtomicReference<>();
        this.zzj = zzih.zza;
        this.zzl = -1L;
        this.zzk = new AtomicLong(0L);
        this.zzb = new zzu(zzhfVar);
    }

    public final void zzaj() {
        zzt();
        zzu();
        if (this.zzu.zzaf()) {
            if (zze().zza(zzbi.zzbh)) {
                Boolean zzg = zze().zzg("google_analytics_deferred_deep_link_enabled");
                if (zzg != null && zzg.booleanValue()) {
                    zzj().zzc().zza("Deferred Deep Link feature enabled.");
                    zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziv
                        @Override // java.lang.Runnable
                        public final void run() {
                            zziq.this.zzam();
                        }
                    });
                }
            }
            zzo().zzac();
            this.zzm = false;
            String zzv = zzk().zzv();
            if (TextUtils.isEmpty(zzv)) {
                return;
            }
            zzf().zzab();
            if (zzv.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", zzv);
            zzc("auto", "_ou", bundle);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    public final void zza(String str, String str2, Bundle bundle) {
        long currentTimeMillis = zzb().currentTimeMillis();
        Preconditions.checkNotEmpty(str);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", str);
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, currentTimeMillis);
        if (str2 != null) {
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str2);
            bundle2.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle);
        }
        zzl().zzb(new zzjl(this, bundle2));
    }

    public final void zzak() {
        if (!(zza().getApplicationContext() instanceof Application) || this.zza == null) {
            return;
        }
        ((Application) zza().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zza);
    }

    final void zzal() {
        if (zzpg.zza() && zze().zza(zzbi.zzcg)) {
            if (zzl().zzg()) {
                zzj().zzg().zza("Cannot get trigger URIs from analytics worker thread");
                return;
            }
            if (zzae.zza()) {
                zzj().zzg().zza("Cannot get trigger URIs from main thread");
                return;
            }
            zzu();
            zzj().zzp().zza("Getting trigger URIs (FE)");
            final AtomicReference atomicReference = new AtomicReference();
            zzl().zza(atomicReference, 5000L, "get trigger URIs", new Runnable() { // from class: com.google.android.gms.measurement.internal.zzir
                @Override // java.lang.Runnable
                public final void run() {
                    zziq zziqVar = zziq.this;
                    AtomicReference<List<zzmh>> atomicReference2 = atomicReference;
                    Bundle zza = zziqVar.zzk().zzi.zza();
                    zzkp zzo = zziqVar.zzo();
                    if (zza == null) {
                        zza = new Bundle();
                    }
                    zzo.zza(atomicReference2, zza);
                }
            });
            final List list = (List) atomicReference.get();
            if (list == null) {
                zzj().zzg().zza("Timed out waiting for get trigger URIs");
            } else {
                zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziu
                    @Override // java.lang.Runnable
                    public final void run() {
                        zziq.this.zza(list);
                    }
                });
            }
        }
    }

    public final void zzam() {
        zzt();
        if (zzk().zzo.zza()) {
            zzj().zzc().zza("Deferred Deep Link already retrieved. Not fetching again.");
            return;
        }
        long zza = zzk().zzp.zza();
        zzk().zzp.zza(1 + zza);
        if (zza >= 5) {
            zzj().zzu().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
            zzk().zzo.zza(true);
        } else {
            if (zznp.zza() && zze().zza(zzbi.zzcn)) {
                if (this.zzn == null) {
                    this.zzn = new zzjh(this, this.zzu);
                }
                this.zzn.zza(0L);
                return;
            }
            this.zzu.zzah();
        }
    }

    final /* synthetic */ void zza(List list) {
        zzt();
        if (Build.VERSION.SDK_INT >= 30) {
            SparseArray<Long> zzg = zzk().zzg();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                zzmh zzmhVar = (zzmh) it.next();
                if (!zzg.contains(zzmhVar.zzc) || zzg.get(zzmhVar.zzc).longValue() < zzmhVar.zzb) {
                    zzao().add(zzmhVar);
                }
            }
            zzan();
        }
    }

    final /* synthetic */ void zza(Bundle bundle) {
        if (bundle == null) {
            zzk().zzt.zza(new Bundle());
            return;
        }
        Bundle zza = zzk().zzt.zza();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null && !(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Double)) {
                zzq();
                if (zznd.zza(obj)) {
                    zzq();
                    zznd.zza(this.zzo, 27, (String) null, (String) null, 0);
                }
                zzj().zzv().zza("Invalid default event parameter type. Name, value", str, obj);
            } else if (zznd.zzg(str)) {
                zzj().zzv().zza("Invalid default event parameter name. Name", str);
            } else if (obj == null) {
                zza.remove(str);
            } else if (zzq().zza("param", str, zze().zzb(this.zzu.zzh().zzad()), obj)) {
                zzq().zza(zza, str, obj);
            }
        }
        zzq();
        if (zznd.zza(zza, zze().zzg())) {
            zzq();
            zznd.zza(this.zzo, 26, (String) null, (String) null, 0);
            zzj().zzv().zza("Too many default event parameters set. Discarding beyond event parameter limit");
        }
        zzk().zzt.zza(zza);
        zzo().zza(zza);
    }

    public final void zzb(String str, String str2, Bundle bundle) {
        zza(str, str2, bundle, true, true, zzb().currentTimeMillis());
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        String str3 = str == null ? App.TYPE : str;
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        if (str2 == FirebaseAnalytics.Event.SCREEN_VIEW || (str2 != null && str2.equals(FirebaseAnalytics.Event.SCREEN_VIEW))) {
            zzn().zza(bundle2, j);
        } else {
            zzb(str3, str2, j, bundle2, z2, !z2 || this.zzc == null || zznd.zzg(str2), z, null);
        }
    }

    public final void zza(String str, String str2, Bundle bundle, String str3) {
        zzs();
        zzb(str, str2, zzb().currentTimeMillis(), bundle, false, true, true, str3);
    }

    final void zzc(String str, String str2, Bundle bundle) {
        zzt();
        zza(str, str2, zzb().currentTimeMillis(), bundle);
    }

    final void zza(String str, String str2, long j, Bundle bundle) {
        zzt();
        zza(str, str2, j, bundle, true, this.zzc == null || zznd.zzg(str2), true, null);
    }

    protected final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        boolean zza;
        long j2;
        String str4;
        zziq zziqVar;
        String str5;
        String str6;
        String trim;
        boolean z4;
        int length;
        Class<?> cls;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(bundle);
        zzt();
        zzu();
        if (!this.zzu.zzac()) {
            zzj().zzc().zza("Event not sent since app measurement is disabled");
            return;
        }
        List<String> zzaf = zzg().zzaf();
        if (zzaf != null && !zzaf.contains(str2)) {
            zzj().zzc().zza("Dropping non-safelisted event. event name, origin", str2, str);
            return;
        }
        if (!this.zze) {
            this.zze = true;
            try {
                if (!this.zzu.zzag()) {
                    cls = Class.forName("com.google.android.gms.tagmanager.TagManagerService", true, zza().getClassLoader());
                } else {
                    cls = Class.forName("com.google.android.gms.tagmanager.TagManagerService");
                }
                try {
                    cls.getDeclaredMethod("initialize", Context.class).invoke(null, zza());
                } catch (Exception e) {
                    zzj().zzu().zza("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                zzj().zzn().zza("Tag Manager is not found and thus will not be used");
            }
        }
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(str2)) {
            if (bundle.containsKey("gclid")) {
                zza("auto", "_lgclid", bundle.getString("gclid"), zzb().currentTimeMillis());
            }
            if (zzoi.zza() && zze().zza(zzbi.zzcs) && bundle.containsKey("gbraid")) {
                zza("auto", "_gbraid", bundle.getString("gbraid"), zzb().currentTimeMillis());
            }
        }
        if (z && zznd.zzj(str2)) {
            zzq().zza(bundle, zzk().zzt.zza());
        }
        if (!z3 && !"_iap".equals(str2)) {
            zznd zzt = this.zzu.zzt();
            int i = 2;
            if (zzt.zzc(NotificationCompat.CATEGORY_EVENT, str2)) {
                if (!zzt.zza(NotificationCompat.CATEGORY_EVENT, zzii.zza, zzii.zzb, str2)) {
                    i = 13;
                } else if (zzt.zza(NotificationCompat.CATEGORY_EVENT, 40, str2)) {
                    i = 0;
                }
            }
            if (i != 0) {
                zzj().zzh().zza("Invalid public event name. Event will not be logged (FE)", zzi().zza(str2));
                this.zzu.zzt();
                String zza2 = zznd.zza(str2, 40, true);
                length = str2 != null ? str2.length() : 0;
                this.zzu.zzt();
                zznd.zza(this.zzo, i, "_ev", zza2, length);
                return;
            }
        }
        zzki zza3 = zzn().zza(false);
        if (zza3 != null && !bundle.containsKey("_sc")) {
            zza3.zzd = true;
        }
        zznd.zza(zza3, bundle, z && !z3);
        boolean equals = "am".equals(str);
        boolean zzg = zznd.zzg(str2);
        if (z && this.zzc != null && !zzg && !equals) {
            zzj().zzc().zza("Passing event to registered event handler (FE)", zzi().zza(str2), zzi().zza(bundle));
            Preconditions.checkNotNull(this.zzc);
            this.zzc.interceptEvent(str, str2, bundle, j);
            return;
        }
        if (this.zzu.zzaf()) {
            int zza4 = zzq().zza(str2);
            if (zza4 != 0) {
                zzj().zzh().zza("Invalid event name. Event will not be logged (FE)", zzi().zza(str2));
                zzq();
                String zza5 = zznd.zza(str2, 40, true);
                length = str2 != null ? str2.length() : 0;
                this.zzu.zzt();
                zznd.zza(this.zzo, str3, zza4, "_ev", zza5, length);
                return;
            }
            Bundle zza6 = zzq().zza(str3, str2, bundle, CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"}), z3);
            Preconditions.checkNotNull(zza6);
            if (zzn().zza(false) != null && "_ae".equals(str2)) {
                zzmd zzmdVar = zzp().zzb;
                long elapsedRealtime = zzmdVar.zzb.zzb().elapsedRealtime();
                long j3 = elapsedRealtime - zzmdVar.zza;
                zzmdVar.zza = elapsedRealtime;
                if (j3 > 0) {
                    zzq().zza(zza6, j3);
                }
            }
            if (zznv.zza() && zze().zza(zzbi.zzbm)) {
                if (!"auto".equals(str) && "_ssr".equals(str2)) {
                    zznd zzq = zzq();
                    String string = zza6.getString("_ffr");
                    if (Strings.isEmptyOrWhitespace(string)) {
                        trim = null;
                    } else {
                        trim = string != null ? string.trim() : string;
                    }
                    if (zzng.zza(trim, zzq.zzk().zzq.zza())) {
                        zzq.zzj().zzc().zza("Not logging duplicate session_start_with_rollout event");
                        z4 = false;
                    } else {
                        zzq.zzk().zzq.zza(trim);
                        z4 = true;
                    }
                    if (!z4) {
                        return;
                    }
                } else if ("_ae".equals(str2)) {
                    String zza7 = zzq().zzk().zzq.zza();
                    if (!TextUtils.isEmpty(zza7)) {
                        zza6.putString("_ffr", zza7);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(zza6);
            if (zze().zza(zzbi.zzcj)) {
                zza = zzp().zzaa();
            } else {
                zza = zzk().zzn.zza();
            }
            if (zzk().zzk.zza() > 0 && zzk().zza(j) && zza) {
                zzj().zzp().zza("Current session is expired, remove the session number, ID, and engagement time");
                j2 = 0;
                str4 = "_ae";
                zza("auto", "_sid", (Object) null, zzb().currentTimeMillis());
                zza("auto", "_sno", (Object) null, zzb().currentTimeMillis());
                zza("auto", "_se", (Object) null, zzb().currentTimeMillis());
                zzk().zzl.zza(0L);
            } else {
                j2 = 0;
                str4 = "_ae";
            }
            if (zza6.getLong(FirebaseAnalytics.Param.EXTEND_SESSION, j2) == 1) {
                zzj().zzp().zza("EXTEND_SESSION param attached: initiate a new session or extend the current active session");
                zziqVar = this;
                zziqVar.zzu.zzs().zza.zza(j, true);
            } else {
                zziqVar = this;
            }
            ArrayList arrayList2 = new ArrayList(zza6.keySet());
            Collections.sort(arrayList2);
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                String str7 = (String) obj;
                if (str7 != null) {
                    zzq();
                    Bundle[] zzb = zznd.zzb(zza6.get(str7));
                    if (zzb != null) {
                        zza6.putParcelableArray(str7, zzb);
                    }
                }
            }
            int i3 = 0;
            while (i3 < arrayList.size()) {
                Bundle bundle2 = (Bundle) arrayList.get(i3);
                if (i3 != 0) {
                    str6 = "_ep";
                    str5 = str;
                } else {
                    str5 = str;
                    str6 = str2;
                }
                bundle2.putString("_o", str5);
                if (z2) {
                    bundle2 = zzq().zzb(bundle2);
                }
                Bundle bundle3 = bundle2;
                zzo().zza(new zzbg(str6, new zzbb(bundle3), str, j), str3);
                if (!equals) {
                    Iterator<zzil> it = zziqVar.zzd.iterator();
                    while (it.hasNext()) {
                        it.next().onEvent(str, str2, new Bundle(bundle3), j);
                    }
                }
                i3++;
            }
            if (zzn().zza(false) == null || !str4.equals(str2)) {
                return;
            }
            zzp().zza(true, true, zzb().elapsedRealtime());
        }
    }

    final void zzan() {
        zzmh poll;
        MeasurementManagerFutures zzn;
        zzt();
        if (zzao().isEmpty() || this.zzh || (poll = zzao().poll()) == null || (zzn = zzq().zzn()) == null) {
            return;
        }
        this.zzh = true;
        zzj().zzp().zza("Registering trigger URI", poll.zza);
        ListenableFuture<Unit> registerTriggerAsync = zzn.registerTriggerAsync(Uri.parse(poll.zza));
        if (registerTriggerAsync == null) {
            this.zzh = false;
            zzao().add(poll);
            return;
        }
        SparseArray<Long> zzg = zzk().zzg();
        zzg.put(poll.zzc, Long.valueOf(poll.zzb));
        zzgd zzk = zzk();
        if (zzg == null) {
            zzk.zzi.zza(null);
        } else {
            int[] iArr = new int[zzg.size()];
            long[] jArr = new long[zzg.size()];
            for (int i = 0; i < zzg.size(); i++) {
                iArr[i] = zzg.keyAt(i);
                jArr[i] = zzg.valueAt(i).longValue();
            }
            Bundle bundle = new Bundle();
            bundle.putIntArray("uriSources", iArr);
            bundle.putLongArray("uriTimestamps", jArr);
            zzk.zzi.zza(bundle);
        }
        Futures.addCallback(registerTriggerAsync, new zzjc(this, poll), new zziz(this));
    }

    public final void zza(zzil zzilVar) {
        zzu();
        Preconditions.checkNotNull(zzilVar);
        if (this.zzd.add(zzilVar)) {
            return;
        }
        zzj().zzu().zza("OnEventListener already registered");
    }

    final void zza(long j, boolean z) {
        zzt();
        zzu();
        zzj().zzc().zza("Resetting analytics data (FE)");
        zzlx zzp = zzp();
        zzp.zzt();
        zzp.zzb.zza();
        if (zzps.zza() && zze().zza(zzbi.zzbs)) {
            zzg().zzag();
        }
        boolean zzac = this.zzu.zzac();
        zzgd zzk = zzk();
        zzk.zzc.zza(j);
        if (!TextUtils.isEmpty(zzk.zzk().zzq.zza())) {
            zzk.zzq.zza(null);
        }
        if (zzoh.zza() && zzk.zze().zza(zzbi.zzbn)) {
            zzk.zzk.zza(0L);
        }
        zzk.zzl.zza(0L);
        if (!zzk.zze().zzv()) {
            zzk.zzb(!zzac);
        }
        zzk.zzr.zza(null);
        zzk.zzs.zza(0L);
        zzk.zzt.zza(null);
        if (z) {
            zzo().zzaf();
        }
        if (zzoh.zza() && zze().zza(zzbi.zzbn)) {
            zzp().zza.zza();
        }
        this.zzm = !zzac;
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzl().zzb(new zzjg(this, str, str2, j, zznd.zza(bundle), z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzl().zzb(new zzjf(this, str, str2, obj, j));
    }

    final void zza(String str) {
        this.zzf.set(str);
    }

    public final void zzb(Bundle bundle) {
        zza(bundle, zzb().currentTimeMillis());
    }

    public final void zza(Bundle bundle, long j) {
        Preconditions.checkNotNull(bundle);
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString("app_id"))) {
            zzj().zzu().zza("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove("app_id");
        Preconditions.checkNotNull(bundle2);
        zzie.zza(bundle2, "app_id", String.class, null);
        zzie.zza(bundle2, "origin", String.class, null);
        zzie.zza(bundle2, "name", String.class, null);
        zzie.zza(bundle2, "value", Object.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
        zzie.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        Preconditions.checkNotEmpty(bundle2.getString("name"));
        Preconditions.checkNotEmpty(bundle2.getString("origin"));
        Preconditions.checkNotNull(bundle2.get("value"));
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, j);
        String string = bundle2.getString("name");
        Object obj = bundle2.get("value");
        if (zzq().zzb(string) != 0) {
            zzj().zzg().zza("Invalid conditional user property name", zzi().zzc(string));
            return;
        }
        if (zzq().zza(string, obj) != 0) {
            zzj().zzg().zza("Invalid conditional user property value", zzi().zzc(string), obj);
            return;
        }
        Object zzc = zzq().zzc(string, obj);
        if (zzc == null) {
            zzj().zzg().zza("Unable to normalize conditional user property value", zzi().zzc(string), obj);
            return;
        }
        zzie.zza(bundle2, zzc);
        long j2 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT);
        if (!TextUtils.isEmpty(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME)) && (j2 > 15552000000L || j2 < 1)) {
            zzj().zzg().zza("Invalid conditional user property timeout", zzi().zzc(string), Long.valueOf(j2));
            return;
        }
        long j3 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE);
        if (j3 > 15552000000L || j3 < 1) {
            zzj().zzg().zza("Invalid conditional user property time to live", zzi().zzc(string), Long.valueOf(j3));
        } else {
            zzl().zzb(new zzjm(this, bundle2));
        }
    }

    public final void zza(zzih zzihVar, long j) {
        zzih zzihVar2;
        boolean z;
        boolean z2;
        boolean z3;
        zzih zzihVar3 = zzihVar;
        zzu();
        int zza = zzihVar.zza();
        if (zza != -10 && zzihVar.zzc() == null && zzihVar.zzd() == null) {
            zzj().zzv().zza("Discarding empty consent settings");
            return;
        }
        synchronized (this.zzg) {
            zzihVar2 = this.zzj;
            z = true;
            z2 = false;
            if (zzih.zza(zza, zzihVar2.zza())) {
                boolean zzc = zzihVar3.zzc(this.zzj);
                if (zzihVar.zzh() && !this.zzj.zzh()) {
                    z2 = true;
                }
                zzihVar3 = zzihVar3.zzb(this.zzj);
                this.zzj = zzihVar3;
                z3 = z2;
                z2 = zzc;
            } else {
                z = false;
                z3 = false;
            }
        }
        if (!z) {
            zzj().zzn().zza("Ignoring lower-priority consent settings, proposed settings", zzihVar3);
            return;
        }
        long andIncrement = this.zzk.getAndIncrement();
        if (z2) {
            zza((String) null);
            zzl().zzc(new zzjv(this, zzihVar3, j, andIncrement, z3, zzihVar2));
            return;
        }
        zzjy zzjyVar = new zzjy(this, zzihVar3, andIncrement, z3, zzihVar2);
        if (zza == 30 || zza == -10) {
            zzl().zzc(zzjyVar);
        } else {
            zzl().zzb(zzjyVar);
        }
    }

    final void zza(Bundle bundle, int i, long j) {
        zzu();
        String zza = zzih.zza(bundle);
        if (zza != null) {
            zzj().zzv().zza("Ignoring invalid consent setting", zza);
            zzj().zzv().zza("Valid consent values are 'granted', 'denied'");
        }
        zzih zza2 = zzih.zza(bundle, i);
        if (zznp.zza() && zze().zza(zzbi.zzcl)) {
            if (zza2.zzi()) {
                zza(zza2, j);
            }
            zzay zza3 = zzay.zza(bundle, i);
            if (zza3.zzg()) {
                zza(zza3);
            }
            Boolean zza4 = zzay.zza(bundle);
            if (zza4 != null) {
                zza(App.TYPE, FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS, (Object) zza4.toString(), false);
                return;
            }
            return;
        }
        zza(zza2, j);
    }

    final void zza(zzay zzayVar) {
        zzl().zzb(new zzjw(this, zzayVar));
    }

    public final void zza(zzim zzimVar) {
        zzim zzimVar2;
        zzt();
        zzu();
        if (zzimVar != null && zzimVar != (zzimVar2 = this.zzc)) {
            Preconditions.checkState(zzimVar2 == null, "EventInterceptor already set.");
        }
        this.zzc = zzimVar;
    }

    public final void zza(Boolean bool) {
        zzu();
        zzl().zzb(new zzjt(this, bool));
    }

    final void zza(zzih zzihVar) {
        zzt();
        boolean z = (zzihVar.zzh() && zzihVar.zzg()) || zzo().zzaj();
        if (z != this.zzu.zzad()) {
            this.zzu.zzb(z);
            Boolean zzp = zzk().zzp();
            if (!z || zzp == null || zzp.booleanValue()) {
                zza(Boolean.valueOf(z), false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(Boolean bool, boolean z) {
        zzt();
        zzu();
        zzj().zzc().zza("Setting app measurement enabled (FE)", bool);
        zzk().zza(bool);
        if (z) {
            zzk().zzb(bool);
        }
        if (this.zzu.zzad() || !(bool == null || bool.booleanValue())) {
            zzap();
        }
    }

    public final void zza(String str, String str2, Object obj, boolean z) {
        zza(str, str2, obj, z, zzb().currentTimeMillis());
    }

    public final void zza(String str, String str2, Object obj, boolean z, long j) {
        int length;
        if (str == null) {
            str = App.TYPE;
        }
        String str3 = str;
        int i = 6;
        if (z) {
            i = zzq().zzb(str2);
        } else {
            zznd zzq = zzq();
            if (zzq.zzc("user property", str2)) {
                if (!zzq.zza("user property", zzij.zza, str2)) {
                    i = 15;
                } else if (zzq.zza("user property", 24, str2)) {
                    i = 0;
                }
            }
        }
        if (i != 0) {
            zzq();
            String zza = zznd.zza(str2, 24, true);
            length = str2 != null ? str2.length() : 0;
            this.zzu.zzt();
            zznd.zza(this.zzo, i, "_ev", zza, length);
            return;
        }
        if (obj != null) {
            int zza2 = zzq().zza(str2, obj);
            if (zza2 != 0) {
                zzq();
                String zza3 = zznd.zza(str2, 24, true);
                length = ((obj instanceof String) || (obj instanceof CharSequence)) ? String.valueOf(obj).length() : 0;
                this.zzu.zzt();
                zznd.zza(this.zzo, zza2, "_ev", zza3, length);
                return;
            }
            Object zzc = zzq().zzc(str2, obj);
            if (zzc != null) {
                zza(str3, str2, j, zzc);
                return;
            }
            return;
        }
        zza(str3, str2, j, (Object) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zza(java.lang.String r9, java.lang.String r10, java.lang.Object r11, long r12) {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)
            r8.zzt()
            r8.zzu()
            java.lang.String r0 = "allow_personalized_ads"
            boolean r0 = r0.equals(r10)
            java.lang.String r1 = "_npa"
            if (r0 == 0) goto L63
            boolean r0 = r11 instanceof java.lang.String
            if (r0 == 0) goto L53
            r0 = r11
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L53
            java.util.Locale r10 = java.util.Locale.ENGLISH
            java.lang.String r10 = r0.toLowerCase(r10)
            java.lang.String r11 = "false"
            boolean r10 = r11.equals(r10)
            r2 = 1
            if (r10 == 0) goto L35
            r4 = r2
            goto L37
        L35:
            r4 = 0
        L37:
            java.lang.Long r10 = java.lang.Long.valueOf(r4)
            com.google.android.gms.measurement.internal.zzgd r0 = r8.zzk()
            com.google.android.gms.measurement.internal.zzgj r0 = r0.zzh
            r4 = r10
            java.lang.Long r4 = (java.lang.Long) r4
            long r4 = r10.longValue()
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 != 0) goto L4e
            java.lang.String r11 = "true"
        L4e:
            r0.zza(r11)
            r6 = r10
            goto L61
        L53:
            if (r11 != 0) goto L63
            com.google.android.gms.measurement.internal.zzgd r10 = r8.zzk()
            com.google.android.gms.measurement.internal.zzgj r10 = r10.zzh
            java.lang.String r0 = "unset"
            r10.zza(r0)
            r6 = r11
        L61:
            r3 = r1
            goto L65
        L63:
            r3 = r10
            r6 = r11
        L65:
            com.google.android.gms.measurement.internal.zzhf r10 = r8.zzu
            boolean r10 = r10.zzac()
            if (r10 != 0) goto L7b
            com.google.android.gms.measurement.internal.zzfr r9 = r8.zzj()
            com.google.android.gms.measurement.internal.zzft r9 = r9.zzp()
            java.lang.String r10 = "User property not set since app measurement is disabled"
            r9.zza(r10)
            return
        L7b:
            com.google.android.gms.measurement.internal.zzhf r10 = r8.zzu
            boolean r10 = r10.zzaf()
            if (r10 != 0) goto L84
            return
        L84:
            com.google.android.gms.measurement.internal.zznc r10 = new com.google.android.gms.measurement.internal.zznc
            r2 = r10
            r4 = r12
            r7 = r9
            r2.<init>(r3, r4, r6, r7)
            com.google.android.gms.measurement.internal.zzkp r9 = r8.zzo()
            r9.zza(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zziq.zza(java.lang.String, java.lang.String, java.lang.Object, long):void");
    }

    public final void zzb(zzil zzilVar) {
        zzu();
        Preconditions.checkNotNull(zzilVar);
        if (this.zzd.remove(zzilVar)) {
            return;
        }
        zzj().zzu().zza("OnEventListener had not been registered");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzap() {
        zzt();
        String zza = zzk().zzh.zza();
        if (zza != null) {
            if ("unset".equals(zza)) {
                zza(App.TYPE, "_npa", (Object) null, zzb().currentTimeMillis());
            } else {
                zza(App.TYPE, "_npa", Long.valueOf("true".equals(zza) ? 1L : 0L), zzb().currentTimeMillis());
            }
        }
        if (this.zzu.zzac() && this.zzm) {
            zzj().zzc().zza("Recording app launch after enabling measurement for the first time (FE)");
            zzaj();
            if (zzoh.zza() && zze().zza(zzbi.zzbn)) {
                zzp().zza.zza();
            }
            zzl().zzb(new zzje(this));
            return;
        }
        zzj().zzc().zza("Updating Scion state (FE)");
        zzo().zzag();
    }
}
