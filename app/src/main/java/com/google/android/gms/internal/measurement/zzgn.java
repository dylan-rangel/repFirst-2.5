package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public abstract class zzgn<T> {

    @Nullable
    private static volatile zzgu zzb = null;
    private static volatile boolean zzc = false;
    private final zzgv zzg;
    private final String zzh;
    private final T zzi;
    private volatile int zzj;
    private volatile T zzk;
    private final boolean zzl;
    private static final Object zza = new Object();
    private static final AtomicReference<Collection<zzgn<?>>> zzd = new AtomicReference<>();
    private static zzgy zze = new zzgy(new zzhb() { // from class: com.google.android.gms.internal.measurement.zzgo
        @Override // com.google.android.gms.internal.measurement.zzhb
        public final boolean zza() {
            return zzgn.zzd();
        }
    });
    private static final AtomicInteger zzf = new AtomicInteger();

    static /* synthetic */ boolean zzd() {
        return true;
    }

    abstract T zza(Object obj);

    static /* synthetic */ zzgn zza(zzgv zzgvVar, String str, Boolean bool, boolean z) {
        return new zzgq(zzgvVar, str, bool, true);
    }

    static /* synthetic */ zzgn zza(zzgv zzgvVar, String str, Double d, boolean z) {
        return new zzgt(zzgvVar, str, d, true);
    }

    static /* synthetic */ zzgn zza(zzgv zzgvVar, String str, Long l, boolean z) {
        return new zzgr(zzgvVar, str, l, true);
    }

    static /* synthetic */ zzgn zza(zzgv zzgvVar, String str, String str2, boolean z) {
        return new zzgs(zzgvVar, str, str2, true);
    }

    public final T zza() {
        T zzb2;
        if (!this.zzl) {
            Preconditions.checkState(zze.zza(this.zzh), "Attempt to access PhenotypeFlag not via codegen. All new PhenotypeFlags must be accessed through codegen APIs. If you believe you are seeing this error by mistake, you can add your flag to the exemption list located at //java/com/google/android/libraries/phenotype/client/lockdown/flags.textproto. Send the addition CL to ph-reviews@. See go/phenotype-android-codegen for information about generated code. See go/ph-lockdown for more information about this error.");
        }
        int i = zzf.get();
        if (this.zzj < i) {
            synchronized (this) {
                if (this.zzj < i) {
                    zzgu zzguVar = zzb;
                    Optional<zzgh> absent = Optional.absent();
                    String str = null;
                    if (zzguVar != null) {
                        absent = zzguVar.zzb().get();
                        if (absent.isPresent()) {
                            str = absent.get().zza(this.zzg.zzb, this.zzg.zza, this.zzg.zzd, this.zzh);
                        }
                    }
                    Preconditions.checkState(zzguVar != null, "Must call PhenotypeFlagInitializer.maybeInit() first");
                    if (!this.zzg.zzf ? (zzb2 = zzb(zzguVar)) == null && (zzb2 = zza(zzguVar)) == null : (zzb2 = zza(zzguVar)) == null && (zzb2 = zzb(zzguVar)) == null) {
                        zzb2 = this.zzi;
                    }
                    if (absent.isPresent()) {
                        zzb2 = str == null ? this.zzi : zza((Object) str);
                    }
                    this.zzk = zzb2;
                    this.zzj = i;
                }
            }
        }
        return this.zzk;
    }

    @Nullable
    private final T zza(zzgu zzguVar) {
        if (!this.zzg.zze && (this.zzg.zzh == null || this.zzg.zzh.apply(zzguVar.zza()).booleanValue())) {
            Object zza2 = zzgg.zza(zzguVar.zza()).zza(this.zzg.zze ? null : zza(this.zzg.zzc));
            if (zza2 != null) {
                return zza(zza2);
            }
        }
        return null;
    }

    @Nullable
    private final T zzb(zzgu zzguVar) {
        zzgb zza2;
        Object zza3;
        if (this.zzg.zzb != null) {
            if (!zzgl.zza(zzguVar.zza(), this.zzg.zzb)) {
                zza2 = null;
            } else if (this.zzg.zzg) {
                zza2 = zzfy.zza(zzguVar.zza().getContentResolver(), zzgk.zza(zzgk.zza(zzguVar.zza(), this.zzg.zzb.getLastPathSegment())), new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgm
                    @Override // java.lang.Runnable
                    public final void run() {
                        zzgn.zzc();
                    }
                });
            } else {
                zza2 = zzfy.zza(zzguVar.zza().getContentResolver(), this.zzg.zzb, new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgm
                    @Override // java.lang.Runnable
                    public final void run() {
                        zzgn.zzc();
                    }
                });
            }
        } else {
            zza2 = zzgw.zza(zzguVar.zza(), this.zzg.zza, new Runnable() { // from class: com.google.android.gms.internal.measurement.zzgm
                @Override // java.lang.Runnable
                public final void run() {
                    zzgn.zzc();
                }
            });
        }
        if (zza2 == null || (zza3 = zza2.zza(zzb())) == null) {
            return null;
        }
        return zza(zza3);
    }

    public final String zzb() {
        return zza(this.zzg.zzd);
    }

    private final String zza(String str) {
        if (str != null && str.isEmpty()) {
            return this.zzh;
        }
        return str + this.zzh;
    }

    private zzgn(zzgv zzgvVar, String str, T t, boolean z) {
        this.zzj = -1;
        if (zzgvVar.zza == null && zzgvVar.zzb == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        if (zzgvVar.zza != null && zzgvVar.zzb != null) {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
        this.zzg = zzgvVar;
        this.zzh = str;
        this.zzi = t;
        this.zzl = z;
    }

    public static void zzc() {
        zzf.incrementAndGet();
    }

    public static void zzb(final Context context) {
        if (zzb != null || context == null) {
            return;
        }
        Object obj = zza;
        synchronized (obj) {
            if (zzb == null && context != null) {
                synchronized (obj) {
                    zzgu zzguVar = zzb;
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                    if (zzguVar == null || zzguVar.zza() != context) {
                        zzfy.zzc();
                        zzgw.zza();
                        zzgg.zza();
                        zzb = new zzfv(context, Suppliers.memoize(new Supplier() { // from class: com.google.android.gms.internal.measurement.zzgp
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                Optional zza2;
                                zza2 = zzgj.zza.zza(context);
                                return zza2;
                            }
                        }));
                        zzf.incrementAndGet();
                    }
                }
            }
        }
    }
}
