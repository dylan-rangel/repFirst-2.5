package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.Clock;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.checkerframework.dataflow.qual.Pure;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzls extends zzmo {
    public final zzgi zza;
    public final zzgi zzb;
    public final zzgi zzc;
    public final zzgi zzd;
    public final zzgi zze;
    private final Map<String, zzlr> zzg;

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzmo
    protected final boolean zzc() {
        return false;
    }

    @Deprecated
    private final Pair<String, Boolean> zza(String str) {
        zzlr zzlrVar;
        zzt();
        long elapsedRealtime = zzb().elapsedRealtime();
        zzlr zzlrVar2 = this.zzg.get(str);
        if (zzlrVar2 != null && elapsedRealtime < zzlrVar2.zzc) {
            return new Pair<>(zzlrVar2.zza, Boolean.valueOf(zzlrVar2.zzb));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long zzf = zze().zzf(str) + elapsedRealtime;
        AdvertisingIdClient.Info info = null;
        try {
            long zzc = zze().zzc(str, zzbi.zzb);
            if (zzc > 0) {
                try {
                    info = AdvertisingIdClient.getAdvertisingIdInfo(zza());
                } catch (PackageManager.NameNotFoundException unused) {
                    if (zzlrVar2 != null && elapsedRealtime < zzlrVar2.zzc + zzc) {
                        return new Pair<>(zzlrVar2.zza, Boolean.valueOf(zzlrVar2.zzb));
                    }
                }
            } else {
                info = AdvertisingIdClient.getAdvertisingIdInfo(zza());
            }
        } catch (Exception e) {
            zzj().zzc().zza("Unable to get advertising id", e);
            zzlrVar = new zzlr("", false, zzf);
        }
        if (info == null) {
            return new Pair<>("00000000-0000-0000-0000-000000000000", false);
        }
        String id = info.getId();
        zzlrVar = id != null ? new zzlr(id, info.isLimitAdTrackingEnabled(), zzf) : new zzlr("", info.isLimitAdTrackingEnabled(), zzf);
        this.zzg.put(str, zzlrVar);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(zzlrVar.zza, Boolean.valueOf(zzlrVar.zzb));
    }

    final Pair<String, Boolean> zza(String str, zzih zzihVar) {
        if (zzihVar.zzg()) {
            return zza(str);
        }
        return new Pair<>("", false);
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzt zzg() {
        return super.zzg();
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

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzao zzh() {
        return super.zzh();
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

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzgp zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzid, com.google.android.gms.measurement.internal.zzif
    @Pure
    public final /* bridge */ /* synthetic */ zzgy zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzls zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzmn zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    public final /* bridge */ /* synthetic */ zzmz g_() {
        return super.g_();
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    @Pure
    public final /* bridge */ /* synthetic */ zznd zzq() {
        return super.zzq();
    }

    @Deprecated
    final String zza(String str, boolean z) {
        zzt();
        String str2 = z ? (String) zza(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest zzu = zznd.zzu();
        if (zzu == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, zzu.digest(str2.getBytes())));
    }

    zzls(zzmp zzmpVar) {
        super(zzmpVar);
        this.zzg = new HashMap();
        zzgd zzk = zzk();
        zzk.getClass();
        this.zza = new zzgi(zzk, "last_delete_stale", 0L);
        zzgd zzk2 = zzk();
        zzk2.getClass();
        this.zzb = new zzgi(zzk2, "backoff", 0L);
        zzgd zzk3 = zzk();
        zzk3.getClass();
        this.zzc = new zzgi(zzk3, "last_upload", 0L);
        zzgd zzk4 = zzk();
        zzk4.getClass();
        this.zzd = new zzgi(zzk4, "last_upload_attempt", 0L);
        zzgd zzk5 = zzk();
        zzk5.getClass();
        this.zze = new zzgi(zzk5, "midnight_offset", 0L);
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
}
