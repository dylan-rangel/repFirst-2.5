package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfi;
import com.google.android.gms.internal.measurement.zznp;
import com.google.android.gms.internal.measurement.zzps;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkg extends zzmo {
    private static String zza(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }

    @Override // com.google.android.gms.measurement.internal.zzmo
    protected final boolean zzc() {
        return false;
    }

    public zzkg(zzmp zzmpVar) {
        super(zzmpVar);
    }

    public final byte[] zza(zzbg zzbgVar, String str) {
        zzne zzneVar;
        zzfi.zzj.zza zzaVar;
        Bundle bundle;
        zzh zzhVar;
        zzfi.zzi.zza zzaVar2;
        byte[] bArr;
        long j;
        zzbc zza;
        zzt();
        this.zzu.zzy();
        Preconditions.checkNotNull(zzbgVar);
        Preconditions.checkNotEmpty(str);
        if (!zze().zze(str, zzbi.zzbc)) {
            zzj().zzc().zza("Generating ScionPayload disabled. packageName", str);
            return new byte[0];
        }
        if (!"_iap".equals(zzbgVar.zza) && !"_iapx".equals(zzbgVar.zza)) {
            zzj().zzc().zza("Generating a payload for this event is not available. package_name, event_name", str, zzbgVar.zza);
            return null;
        }
        zzfi.zzi.zza zzb = zzfi.zzi.zzb();
        zzh().zzp();
        try {
            zzh zzd = zzh().zzd(str);
            if (zzd == null) {
                zzj().zzc().zza("Log and bundle not available. package_name", str);
                return new byte[0];
            }
            if (!zzd.zzak()) {
                zzj().zzc().zza("Log and bundle disabled. package_name", str);
                return new byte[0];
            }
            zzfi.zzj.zza zzp = zzfi.zzj.zzu().zzg(1).zzp("android");
            if (!TextUtils.isEmpty(zzd.zzx())) {
                zzp.zzb(zzd.zzx());
            }
            if (!TextUtils.isEmpty(zzd.zzz())) {
                zzp.zzd((String) Preconditions.checkNotNull(zzd.zzz()));
            }
            if (!TextUtils.isEmpty(zzd.zzaa())) {
                zzp.zze((String) Preconditions.checkNotNull(zzd.zzaa()));
            }
            if (zzd.zzc() != -2147483648L) {
                zzp.zze((int) zzd.zzc());
            }
            zzp.zzf(zzd.zzo()).zzd(zzd.zzm());
            String zzac = zzd.zzac();
            String zzv = zzd.zzv();
            if (!TextUtils.isEmpty(zzac)) {
                zzp.zzm(zzac);
            } else if (!TextUtils.isEmpty(zzv)) {
                zzp.zza(zzv);
            }
            zzp.zzj(zzd.zzt());
            zzih zzb2 = this.zzf.zzb(str);
            zzp.zzc(zzd.zzl());
            if (this.zzu.zzac() && zze().zzk(zzp.zzr()) && zzb2.zzg() && !TextUtils.isEmpty(null)) {
                zzp.zzj((String) null);
            }
            zzp.zzg(zzb2.zze());
            if (zzb2.zzg() && zzd.zzaj()) {
                Pair<String, Boolean> zza2 = zzn().zza(zzd.zzx(), zzb2);
                if (zzd.zzaj() && zza2 != null && !TextUtils.isEmpty((CharSequence) zza2.first)) {
                    zzp.zzq(zza((String) zza2.first, Long.toString(zzbgVar.zzd)));
                    if (zza2.second != null) {
                        zzp.zzc(((Boolean) zza2.second).booleanValue());
                    }
                }
            }
            zzf().zzab();
            zzfi.zzj.zza zzi = zzp.zzi(Build.MODEL);
            zzf().zzab();
            zzi.zzo(Build.VERSION.RELEASE).zzi((int) zzf().zzg()).zzs(zzf().zzh());
            if (zzb2.zzh() && zzd.zzy() != null) {
                zzp.zzc(zza((String) Preconditions.checkNotNull(zzd.zzy()), Long.toString(zzbgVar.zzd)));
            }
            if (!TextUtils.isEmpty(zzd.zzab())) {
                zzp.zzl((String) Preconditions.checkNotNull(zzd.zzab()));
            }
            String zzx = zzd.zzx();
            List<zzne> zzi2 = zzh().zzi(zzx);
            Iterator<zzne> it = zzi2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    zzneVar = null;
                    break;
                }
                zzneVar = it.next();
                if ("_lte".equals(zzneVar.zzc)) {
                    break;
                }
            }
            if (zzneVar == null || zzneVar.zze == null) {
                zzne zzneVar2 = new zzne(zzx, "auto", "_lte", zzb().currentTimeMillis(), 0L);
                zzi2.add(zzneVar2);
                zzh().zza(zzneVar2);
            }
            zzfi.zzn[] zznVarArr = new zzfi.zzn[zzi2.size()];
            for (int i = 0; i < zzi2.size(); i++) {
                zzfi.zzn.zza zzb3 = zzfi.zzn.zze().zza(zzi2.get(i).zzc).zzb(zzi2.get(i).zzd);
                g_().zza(zzb3, zzi2.get(i).zze);
                zznVarArr[i] = (zzfi.zzn) ((com.google.android.gms.internal.measurement.zzix) zzb3.zzab());
            }
            zzp.zze(Arrays.asList(zznVarArr));
            g_().zza(zzp);
            if (zznp.zza() && zze().zza(zzbi.zzcm)) {
                this.zzf.zza(zzd, zzp);
            }
            zzfv zza3 = zzfv.zza(zzbgVar);
            zzq().zza(zza3.zzb, zzh().zzc(str));
            zzq().zza(zza3, zze().zzd(str));
            Bundle bundle2 = zza3.zzb;
            bundle2.putLong("_c", 1L);
            zzj().zzc().zza("Marking in-app purchase as real-time");
            bundle2.putLong("_r", 1L);
            bundle2.putString("_o", zzbgVar.zzc);
            if (zzq().zzf(zzp.zzr())) {
                zzq().zza(bundle2, "_dbg", (Object) 1L);
                zzq().zza(bundle2, "_r", (Object) 1L);
            }
            zzbc zzd2 = zzh().zzd(str, zzbgVar.zza);
            if (zzd2 == null) {
                zzaVar = zzp;
                bundle = bundle2;
                zzhVar = zzd;
                zzaVar2 = zzb;
                bArr = null;
                zza = new zzbc(str, zzbgVar.zza, 0L, 0L, zzbgVar.zzd, 0L, null, null, null, null);
                j = 0;
            } else {
                zzaVar = zzp;
                bundle = bundle2;
                zzhVar = zzd;
                zzaVar2 = zzb;
                bArr = null;
                j = zzd2.zzf;
                zza = zzd2.zza(zzbgVar.zzd);
            }
            zzh().zza(zza);
            zzaz zzazVar = new zzaz(this.zzu, zzbgVar.zzc, str, zzbgVar.zza, zzbgVar.zzd, j, bundle);
            zzfi.zze.zza zza4 = zzfi.zze.zze().zzb(zzazVar.zzc).zza(zzazVar.zzb).zza(zzazVar.zzd);
            Iterator<String> it2 = zzazVar.zze.iterator();
            while (it2.hasNext()) {
                String next = it2.next();
                zzfi.zzg.zza zza5 = zzfi.zzg.zze().zza(next);
                Object zzc = zzazVar.zze.zzc(next);
                if (zzc != null) {
                    g_().zza(zza5, zzc);
                    zza4.zza(zza5);
                }
            }
            zzfi.zzj.zza zzaVar3 = zzaVar;
            zzaVar3.zza(zza4).zza(zzfi.zzk.zza().zza(zzfi.zzf.zza().zza(zza.zzc).zza(zzbgVar.zza)));
            zzaVar3.zza(zzg().zza(zzhVar.zzx(), Collections.emptyList(), zzaVar3.zzx(), Long.valueOf(zza4.zzc()), Long.valueOf(zza4.zzc())));
            if (zza4.zzg()) {
                zzaVar3.zzi(zza4.zzc()).zze(zza4.zzc());
            }
            long zzp2 = zzhVar.zzp();
            if (zzp2 != 0) {
                zzaVar3.zzg(zzp2);
            }
            long zzr = zzhVar.zzr();
            if (zzr != 0) {
                zzaVar3.zzh(zzr);
            } else if (zzp2 != 0) {
                zzaVar3.zzh(zzp2);
            }
            String zzaf = zzhVar.zzaf();
            if (zzps.zza() && zze().zze(str, zzbi.zzbt) && zzaf != null) {
                zzaVar3.zzr(zzaf);
            }
            zzhVar.zzai();
            zzfi.zzj.zza zzk = zzaVar3.zzf((int) zzhVar.zzq()).zzl(82001L).zzk(zzb().currentTimeMillis());
            Boolean bool = Boolean.TRUE;
            zzk.zzd(true);
            if (zze().zza(zzbi.zzbw)) {
                this.zzf.zza(zzaVar3.zzr(), zzaVar3);
            }
            zzfi.zzi.zza zzaVar4 = zzaVar2;
            zzaVar4.zza(zzaVar3);
            zzh zzhVar2 = zzhVar;
            zzhVar2.zzp(zzaVar3.zzd());
            zzhVar2.zzn(zzaVar3.zzc());
            zzh().zza(zzhVar2);
            zzh().zzw();
            try {
                return g_().zzb(((zzfi.zzi) ((com.google.android.gms.internal.measurement.zzix) zzaVar4.zzab())).zzbv());
            } catch (IOException e) {
                zzj().zzg().zza("Data loss. Failed to bundle and serialize. appId", zzfr.zza(str), e);
                return bArr;
            }
        } catch (SecurityException e2) {
            zzj().zzc().zza("Resettable device id encryption failed", e2.getMessage());
            return new byte[0];
        } catch (SecurityException e3) {
            zzj().zzc().zza("app instance id encryption failed", e3.getMessage());
            return new byte[0];
        } finally {
            zzh().zzu();
        }
    }
}
