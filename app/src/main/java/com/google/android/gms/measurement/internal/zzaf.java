package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzoo;
import com.google.android.gms.internal.measurement.zzot;
import java.lang.reflect.InvocationTargetException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.dataflow.qual.Pure;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzaf extends zzid {
    private Boolean zza;
    private zzah zzb;
    private Boolean zzc;

    public final double zza(String str, zzfi<Double> zzfiVar) {
        if (str == null) {
            return zzfiVar.zza(null).doubleValue();
        }
        String zza = this.zzb.zza(str, zzfiVar.zza());
        if (TextUtils.isEmpty(zza)) {
            return zzfiVar.zza(null).doubleValue();
        }
        try {
            return zzfiVar.zza(Double.valueOf(Double.parseDouble(zza))).doubleValue();
        } catch (NumberFormatException unused) {
            return zzfiVar.zza(null).doubleValue();
        }
    }

    final int zzc() {
        return (zzot.zza() && zze().zzf(null, zzbi.zzcc) && zzq().zza(231100000, true)) ? 35 : 0;
    }

    final int zza(String str) {
        return zza(str, zzbi.zzah, 500, CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE);
    }

    final int zzb(String str) {
        return (zzoo.zza() && zze().zzf(null, zzbi.zzcu)) ? 500 : 100;
    }

    final int zzc(String str) {
        return Math.max(zzb(str), 256);
    }

    public final int zzg() {
        return zzq().zza(201500000, true) ? 100 : 25;
    }

    public final int zzd(String str) {
        return zza(str, zzbi.zzai, 25, 100);
    }

    public final int zze(String str) {
        return zzb(str, zzbi.zzo);
    }

    public final int zzb(String str, zzfi<Integer> zzfiVar) {
        if (str == null) {
            return zzfiVar.zza(null).intValue();
        }
        String zza = this.zzb.zza(str, zzfiVar.zza());
        if (TextUtils.isEmpty(zza)) {
            return zzfiVar.zza(null).intValue();
        }
        try {
            return zzfiVar.zza(Integer.valueOf(Integer.parseInt(zza))).intValue();
        } catch (NumberFormatException unused) {
            return zzfiVar.zza(null).intValue();
        }
    }

    public final int zza(String str, zzfi<Integer> zzfiVar, int i, int i2) {
        return Math.max(Math.min(zzb(str, zzfiVar), i2), i);
    }

    final long zzf(String str) {
        return zzc(str, zzbi.zza);
    }

    public static long zzh() {
        return zzbi.zzd.zza(null).longValue();
    }

    public static long zzm() {
        return zzbi.zzad.zza(null).longValue();
    }

    public final long zzc(String str, zzfi<Long> zzfiVar) {
        if (str == null) {
            return zzfiVar.zza(null).longValue();
        }
        String zza = this.zzb.zza(str, zzfiVar.zza());
        if (TextUtils.isEmpty(zza)) {
            return zzfiVar.zza(null).longValue();
        }
        try {
            return zzfiVar.zza(Long.valueOf(Long.parseLong(zza))).longValue();
        } catch (NumberFormatException unused) {
            return zzfiVar.zza(null).longValue();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    private final Bundle zzy() {
        try {
            if (zza().getPackageManager() == null) {
                zzj().zzg().zza("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(zza()).getApplicationInfo(zza().getPackageName(), 128);
            if (applicationInfo == null) {
                zzj().zzg().zza("Failed to load metadata: ApplicationInfo is null");
                return null;
            }
            return applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            zzj().zzg().zza("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
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

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zznd zzq() {
        return super.zzq();
    }

    final Boolean zzg(String str) {
        Preconditions.checkNotEmpty(str);
        Bundle zzy = zzy();
        if (zzy == null) {
            zzj().zzg().zza("Failed to load metadata: Metadata bundle is null");
            return null;
        }
        if (zzy.containsKey(str)) {
            return Boolean.valueOf(zzy.getBoolean(str));
        }
        return null;
    }

    public final String zzn() {
        return zza("debug.firebase.analytics.app", "");
    }

    public final String zzo() {
        return zza("debug.deferred.deeplink", "");
    }

    public final String zzd(String str, zzfi<String> zzfiVar) {
        if (str == null) {
            return zzfiVar.zza(null);
        }
        return zzfiVar.zza(this.zzb.zza(str, zzfiVar.zza()));
    }

    final String zzh(String str) {
        return zzd(str, zzbi.zzal);
    }

    private final String zza(String str, String str2) {
        try {
            String str3 = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, str, str2);
            Preconditions.checkNotNull(str3);
            return str3;
        } catch (ClassNotFoundException e) {
            zzj().zzg().zza("Could not find SystemProperties class", e);
            return str2;
        } catch (IllegalAccessException e2) {
            zzj().zzg().zza("Could not access SystemProperties.get()", e2);
            return str2;
        } catch (NoSuchMethodException e3) {
            zzj().zzg().zza("Could not find SystemProperties.get() method", e3);
            return str2;
        } catch (InvocationTargetException e4) {
            zzj().zzg().zza("SystemProperties.get() threw an exception", e4);
            return str2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x002a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.util.List<java.lang.String> zzi(java.lang.String r4) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)
            android.os.Bundle r0 = r3.zzy()
            r1 = 0
            if (r0 != 0) goto L19
            com.google.android.gms.measurement.internal.zzfr r4 = r3.zzj()
            com.google.android.gms.measurement.internal.zzft r4 = r4.zzg()
            java.lang.String r0 = "Failed to load metadata: Metadata bundle is null"
            r4.zza(r0)
        L17:
            r4 = r1
            goto L28
        L19:
            boolean r2 = r0.containsKey(r4)
            if (r2 != 0) goto L20
            goto L17
        L20:
            int r4 = r0.getInt(r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
        L28:
            if (r4 != 0) goto L2b
            return r1
        L2b:
            android.content.Context r0 = r3.zza()     // Catch: android.content.res.Resources.NotFoundException -> L43
            android.content.res.Resources r0 = r0.getResources()     // Catch: android.content.res.Resources.NotFoundException -> L43
            int r4 = r4.intValue()     // Catch: android.content.res.Resources.NotFoundException -> L43
            java.lang.String[] r4 = r0.getStringArray(r4)     // Catch: android.content.res.Resources.NotFoundException -> L43
            if (r4 != 0) goto L3e
            return r1
        L3e:
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch: android.content.res.Resources.NotFoundException -> L43
            return r4
        L43:
            r4 = move-exception
            com.google.android.gms.measurement.internal.zzfr r0 = r3.zzj()
            com.google.android.gms.measurement.internal.zzft r0 = r0.zzg()
            java.lang.String r2 = "Failed to load string array from metadata: resource not found"
            r0.zza(r2, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaf.zzi(java.lang.String):java.util.List");
    }

    zzaf(zzhf zzhfVar) {
        super(zzhfVar);
        this.zzb = new zzah() { // from class: com.google.android.gms.measurement.internal.zzai
            @Override // com.google.android.gms.measurement.internal.zzah
            public final String zza(String str, String str2) {
                return null;
            }
        };
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    final void zza(zzah zzahVar) {
        this.zzb = zzahVar;
    }

    public final boolean zzp() {
        Boolean zzg = zzg("google_analytics_adid_collection_enabled");
        return zzg == null || zzg.booleanValue();
    }

    final boolean zzj(String str) {
        return zzf(str, zzbi.zzak);
    }

    public final boolean zza(zzfi<Boolean> zzfiVar) {
        return zzf(null, zzfiVar);
    }

    public final boolean zze(String str, zzfi<Boolean> zzfiVar) {
        return zzf(str, zzfiVar);
    }

    public final boolean zzf(String str, zzfi<Boolean> zzfiVar) {
        if (str == null) {
            return zzfiVar.zza(null).booleanValue();
        }
        String zza = this.zzb.zza(str, zzfiVar.zza());
        if (TextUtils.isEmpty(zza)) {
            return zzfiVar.zza(null).booleanValue();
        }
        return zzfiVar.zza(Boolean.valueOf("1".equals(zza))).booleanValue();
    }

    public final boolean zzk(String str) {
        return "1".equals(this.zzb.zza(str, "gaia_collection_enabled"));
    }

    public final boolean zzu() {
        Boolean zzg = zzg("google_analytics_automatic_screen_reporting_enabled");
        return zzg == null || zzg.booleanValue();
    }

    public final boolean zzv() {
        Boolean zzg = zzg("firebase_analytics_collection_deactivated");
        return zzg != null && zzg.booleanValue();
    }

    public final boolean zzl(String str) {
        return "1".equals(this.zzb.zza(str, "measurement.event_sampling_enabled"));
    }

    final boolean zzw() {
        if (this.zza == null) {
            Boolean zzg = zzg("app_measurement_lite");
            this.zza = zzg;
            if (zzg == null) {
                this.zza = false;
            }
        }
        return this.zza.booleanValue() || !this.zzu.zzag();
    }

    @EnsuresNonNull({"this.isMainProcess"})
    public final boolean zzx() {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    ApplicationInfo applicationInfo = zza().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzc = Boolean.valueOf(str != null && str.equals(myProcessName));
                    }
                    if (this.zzc == null) {
                        this.zzc = Boolean.TRUE;
                        zzj().zzg().zza("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzc.booleanValue();
    }
}
