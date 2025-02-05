package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzfc;
import com.google.android.gms.internal.measurement.zzfi;
import com.google.android.gms.internal.measurement.zznp;
import com.google.android.gms.internal.measurement.zznq;
import com.google.android.gms.internal.measurement.zzoi;
import com.google.android.gms.internal.measurement.zzpg;
import com.google.android.gms.internal.measurement.zzps;
import com.google.android.gms.internal.measurement.zzqd;
import com.google.android.gms.measurement.internal.zzih;
import com.google.common.net.HttpHeaders;
import com.google.firebase.messaging.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
public class zzmp implements zzif {
    private static volatile zzmp zza;
    private List<Long> zzaa;
    private long zzab;
    private final Map<String, zzih> zzac;
    private final Map<String, zzay> zzad;
    private final Map<String, zzb> zzae;
    private zzki zzaf;
    private String zzag;
    private final zznf zzah;
    private zzgp zzb;
    private zzfy zzc;
    private zzao zzd;
    private zzgb zze;
    private zzmj zzf;
    private zzt zzg;
    private final zzmz zzh;
    private zzkg zzi;
    private zzls zzj;
    private final zzmn zzk;
    private zzgm zzl;
    private final zzhf zzm;
    private boolean zzn;
    private boolean zzo;
    private long zzp;
    private List<Runnable> zzq;
    private final Set<String> zzr;
    private int zzs;
    private int zzt;
    private boolean zzu;
    private boolean zzv;
    private boolean zzw;
    private FileLock zzx;
    private FileChannel zzy;
    private List<Long> zzz;

    /* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
    private class zza implements zzas {
        zzfi.zzj zza;
        List<Long> zzb;
        List<zzfi.zze> zzc;
        private long zzd;

        private static long zza(zzfi.zze zzeVar) {
            return ((zzeVar.zzd() / 1000) / 60) / 60;
        }

        private zza() {
        }

        @Override // com.google.android.gms.measurement.internal.zzas
        public final void zza(zzfi.zzj zzjVar) {
            Preconditions.checkNotNull(zzjVar);
            this.zza = zzjVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzas
        public final boolean zza(long j, zzfi.zze zzeVar) {
            Preconditions.checkNotNull(zzeVar);
            if (this.zzc == null) {
                this.zzc = new ArrayList();
            }
            if (this.zzb == null) {
                this.zzb = new ArrayList();
            }
            if (!this.zzc.isEmpty() && zza(this.zzc.get(0)) != zza(zzeVar)) {
                return false;
            }
            long zzbw = this.zzd + zzeVar.zzbw();
            zzmp.this.zze();
            if (zzbw >= Math.max(0, zzbi.zzi.zza(null).intValue())) {
                return false;
            }
            this.zzd = zzbw;
            this.zzc.add(zzeVar);
            this.zzb.add(Long.valueOf(j));
            int size = this.zzc.size();
            zzmp.this.zze();
            return size < Math.max(1, zzbi.zzj.zza(null).intValue());
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
    private class zzb {
        final String zza;
        long zzb;

        private zzb(zzmp zzmpVar) {
            this(zzmpVar, zzmpVar.zzq().zzp());
        }

        private zzb(zzmp zzmpVar, String str) {
            this.zza = str;
            this.zzb = zzmpVar.zzb().elapsedRealtime();
        }
    }

    private final int zza(FileChannel fileChannel) {
        zzl().zzt();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzj().zzg().zza("Bad channel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0L);
            int read = fileChannel.read(allocate);
            if (read == 4) {
                allocate.flip();
                return allocate.getInt();
            }
            if (read != -1) {
                zzj().zzu().zza("Unexpected data length. Bytes read", Integer.valueOf(read));
            }
            return 0;
        } catch (IOException e) {
            zzj().zzg().zza("Failed to read from channel", e);
            return 0;
        }
    }

    private final long zzx() {
        long currentTimeMillis = zzb().currentTimeMillis();
        zzls zzlsVar = this.zzj;
        zzlsVar.zzak();
        zzlsVar.zzt();
        long zza2 = zzlsVar.zze.zza();
        if (zza2 == 0) {
            zza2 = 1 + zzlsVar.zzq().zzv().nextInt(86400000);
            zzlsVar.zze.zza(zza2);
        }
        return ((((currentTimeMillis + zza2) / 1000) / 60) / 60) / 24;
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    public final Context zza() {
        return this.zzm.zza();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final android.os.Bundle zza(java.lang.String r6) {
        /*
            r5 = this;
            com.google.android.gms.measurement.internal.zzgy r0 = r5.zzl()
            r0.zzt()
            r5.zzs()
            boolean r0 = com.google.android.gms.internal.measurement.zznp.zza()
            r1 = 0
            if (r0 == 0) goto L7e
            com.google.android.gms.measurement.internal.zzgp r0 = r5.zzi()
            com.google.android.gms.internal.measurement.zzfc$zza r0 = r0.zzb(r6)
            if (r0 != 0) goto L1c
            return r1
        L1c:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            com.google.android.gms.measurement.internal.zzih r1 = r5.zzb(r6)
            android.os.Bundle r2 = r1.zzb()
            r0.putAll(r2)
            com.google.android.gms.measurement.internal.zzay r2 = r5.zzd(r6)
            com.google.android.gms.measurement.internal.zzak r3 = new com.google.android.gms.measurement.internal.zzak
            r3.<init>()
            com.google.android.gms.measurement.internal.zzay r1 = r5.zza(r6, r2, r1, r3)
            android.os.Bundle r1 = r1.zzb()
            r0.putAll(r1)
            com.google.android.gms.measurement.internal.zzmz r1 = r5.zzp()
            boolean r1 = r1.zzc(r6)
            r2 = 1
            if (r1 != 0) goto L70
            com.google.android.gms.measurement.internal.zzao r1 = r5.zzf()
            java.lang.String r3 = "_npa"
            com.google.android.gms.measurement.internal.zzne r1 = r1.zze(r6, r3)
            if (r1 == 0) goto L64
            java.lang.Object r6 = r1.zze
            r3 = 1
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            boolean r6 = r6.equals(r1)
            goto L71
        L64:
            com.google.android.gms.measurement.internal.zzgp r1 = r5.zzb
            com.google.android.gms.measurement.internal.zzih$zza r3 = com.google.android.gms.measurement.internal.zzih.zza.AD_PERSONALIZATION
            boolean r6 = r1.zzb(r6, r3)
            if (r6 == 0) goto L70
            r6 = 0
            goto L71
        L70:
            r6 = 1
        L71:
            if (r6 != r2) goto L76
            java.lang.String r6 = "denied"
            goto L78
        L76:
            java.lang.String r6 = "granted"
        L78:
            java.lang.String r1 = "ad_personalization"
            r0.putString(r1, r6)
            return r0
        L7e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zza(java.lang.String):android.os.Bundle");
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    public final Clock zzb() {
        return ((zzhf) Preconditions.checkNotNull(this.zzm)).zzb();
    }

    final zzh zza(zzo zzoVar) {
        zzl().zzt();
        zzs();
        Preconditions.checkNotNull(zzoVar);
        Preconditions.checkNotEmpty(zzoVar.zza);
        if (!zzoVar.zzu.isEmpty()) {
            this.zzae.put(zzoVar.zza, new zzb(zzoVar.zzu));
        }
        zzh zzd = zzf().zzd(zzoVar.zza);
        zzih zza2 = zzb(zzoVar.zza).zza(zzih.zza(zzoVar.zzt));
        String zza3 = zza2.zzg() ? this.zzj.zza(zzoVar.zza, zzoVar.zzn) : "";
        if (zzd == null) {
            zzd = new zzh(this.zzm, zzoVar.zza);
            if (zza2.zzh()) {
                zzd.zzb(zza(zza2));
            }
            if (zza2.zzg()) {
                zzd.zzh(zza3);
            }
        } else if (zza2.zzg() && zza3 != null && !zza3.equals(zzd.zzae())) {
            zzd.zzh(zza3);
            if (zzoVar.zzn && !"00000000-0000-0000-0000-000000000000".equals(this.zzj.zza(zzoVar.zza, zza2).first)) {
                zzd.zzb(zza(zza2));
                if (zzf().zze(zzoVar.zza, "_id") != null && zzf().zze(zzoVar.zza, "_lair") == null) {
                    zzf().zza(new zzne(zzoVar.zza, "auto", "_lair", zzb().currentTimeMillis(), 1L));
                }
            }
        } else if (TextUtils.isEmpty(zzd.zzy()) && zza2.zzh()) {
            zzd.zzb(zza(zza2));
        }
        zzd.zzf(zzoVar.zzb);
        zzd.zza(zzoVar.zzp);
        if (!TextUtils.isEmpty(zzoVar.zzk)) {
            zzd.zze(zzoVar.zzk);
        }
        if (zzoVar.zze != 0) {
            zzd.zzm(zzoVar.zze);
        }
        if (!TextUtils.isEmpty(zzoVar.zzc)) {
            zzd.zzd(zzoVar.zzc);
        }
        zzd.zza(zzoVar.zzj);
        if (zzoVar.zzd != null) {
            zzd.zzc(zzoVar.zzd);
        }
        zzd.zzj(zzoVar.zzf);
        zzd.zzb(zzoVar.zzh);
        if (!TextUtils.isEmpty(zzoVar.zzg)) {
            zzd.zzg(zzoVar.zzg);
        }
        zzd.zza(zzoVar.zzn);
        zzd.zza(zzoVar.zzq);
        zzd.zzk(zzoVar.zzr);
        if (zzps.zza() && (zze().zza(zzbi.zzbr) || zze().zze(zzoVar.zza, zzbi.zzbt))) {
            zzd.zzi(zzoVar.zzv);
        }
        if (zznq.zza() && zze().zza(zzbi.zzbq)) {
            zzd.zza(zzoVar.zzs);
        } else if (zznq.zza() && zze().zza(zzbi.zzbp)) {
            zzd.zza((List<String>) null);
        }
        if (zzqd.zza() && zze().zza(zzbi.zzbu)) {
            zzd.zzc(zzoVar.zzw);
        }
        if (zzpg.zza() && zze().zza(zzbi.zzcf)) {
            zzd.zza(zzoVar.zzaa);
        }
        zzd.zzr(zzoVar.zzx);
        if (zzd.zzal()) {
            zzf().zza(zzd);
        }
        return zzd;
    }

    private final zzo zzc(String str) {
        String str2;
        int i;
        zzh zzd = zzf().zzd(str);
        if (zzd == null || TextUtils.isEmpty(zzd.zzaa())) {
            zzj().zzc().zza("No app data available; dropping", str);
            return null;
        }
        Boolean zza2 = zza(zzd);
        if (zza2 != null && !zza2.booleanValue()) {
            zzj().zzg().zza("App version does not match; dropping. appId", zzfr.zza(str));
            return null;
        }
        zzih zzb2 = zzb(str);
        if (zznp.zza() && zze().zza(zzbi.zzcm)) {
            str2 = zzd(str).zzf();
            i = zzb2.zza();
        } else {
            str2 = "";
            i = 100;
        }
        return new zzo(str, zzd.zzac(), zzd.zzaa(), zzd.zzc(), zzd.zzz(), zzd.zzo(), zzd.zzl(), (String) null, zzd.zzak(), false, zzd.zzab(), zzd.zzb(), 0L, 0, zzd.zzaj(), false, zzd.zzv(), zzd.zzu(), zzd.zzm(), zzd.zzag(), (String) null, zzb2.zze(), "", (String) null, zzd.zzam(), zzd.zzt(), i, str2, zzd.zza(), zzd.zzd());
    }

    public final zzt zzc() {
        return (zzt) zza(this.zzg);
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    public final zzae zzd() {
        return this.zzm.zzd();
    }

    public final zzaf zze() {
        return ((zzhf) Preconditions.checkNotNull(this.zzm)).zzf();
    }

    public final zzao zzf() {
        return (zzao) zza(this.zzd);
    }

    private final zzay zza(String str, zzay zzayVar, zzih zzihVar, zzak zzakVar) {
        if (zznp.zza()) {
            int i = 90;
            if (zzi().zzb(str) == null) {
                if (zzayVar.zzc() == Boolean.FALSE) {
                    i = zzayVar.zza();
                    zzakVar.zza(zzih.zza.AD_USER_DATA, i);
                } else {
                    zzakVar.zza(zzih.zza.AD_USER_DATA, zzaj.FAILSAFE);
                }
                return new zzay((Boolean) false, i, (Boolean) true, "-");
            }
            Boolean zzc = zzayVar.zzc();
            if (zzc != null) {
                i = zzayVar.zza();
                zzakVar.zza(zzih.zza.AD_USER_DATA, i);
            } else {
                if (this.zzb.zza(str, zzih.zza.AD_USER_DATA) == zzih.zza.AD_STORAGE && zzihVar.zzc() != null) {
                    Boolean zzc2 = zzihVar.zzc();
                    zzakVar.zza(zzih.zza.AD_USER_DATA, zzaj.REMOTE_DELEGATION);
                    zzc = zzc2;
                }
                if (zzc == null) {
                    zzc = Boolean.valueOf(this.zzb.zzb(str, zzih.zza.AD_USER_DATA));
                    zzakVar.zza(zzih.zza.AD_USER_DATA, zzaj.REMOTE_DEFAULT);
                }
            }
            Preconditions.checkNotNull(zzc);
            boolean zzn = this.zzb.zzn(str);
            SortedSet<String> zzh = zzi().zzh(str);
            if (!zzc.booleanValue() || zzh.isEmpty()) {
                return new zzay((Boolean) false, i, Boolean.valueOf(zzn), "-");
            }
            return new zzay((Boolean) true, i, Boolean.valueOf(zzn), zzn ? TextUtils.join("", zzh) : "");
        }
        return zzay.zza;
    }

    private final zzay zzd(String str) {
        zzl().zzt();
        zzs();
        if (zznp.zza()) {
            zzay zzayVar = this.zzad.get(str);
            if (zzayVar != null) {
                return zzayVar;
            }
            zzay zzf = zzf().zzf(str);
            this.zzad.put(str, zzf);
            return zzf;
        }
        return zzay.zza;
    }

    public final zzfq zzg() {
        return this.zzm.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    public final zzfr zzj() {
        return ((zzhf) Preconditions.checkNotNull(this.zzm)).zzj();
    }

    public final zzfy zzh() {
        return (zzfy) zza(this.zzc);
    }

    private final zzgb zzy() {
        zzgb zzgbVar = this.zze;
        if (zzgbVar != null) {
            return zzgbVar;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    public final zzgp zzi() {
        return (zzgp) zza(this.zzb);
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    public final zzgy zzl() {
        return ((zzhf) Preconditions.checkNotNull(this.zzm)).zzl();
    }

    final zzhf zzk() {
        return this.zzm;
    }

    final zzih zzb(String str) {
        zzl().zzt();
        zzs();
        zzih zzihVar = this.zzac.get(str);
        if (zzihVar == null) {
            zzihVar = zzf().zzg(str);
            if (zzihVar == null) {
                zzihVar = zzih.zza;
            }
            zza(str, zzihVar);
        }
        return zzihVar;
    }

    public final zzkg zzm() {
        return (zzkg) zza(this.zzi);
    }

    public final zzls zzn() {
        return this.zzj;
    }

    private final zzmj zzz() {
        return (zzmj) zza(this.zzf);
    }

    private static zzmo zza(zzmo zzmoVar) {
        if (zzmoVar == null) {
            throw new IllegalStateException("Upload Component not created");
        }
        if (zzmoVar.zzam()) {
            return zzmoVar;
        }
        throw new IllegalStateException("Component not initialized: " + String.valueOf(zzmoVar.getClass()));
    }

    public final zzmn zzo() {
        return this.zzk;
    }

    public static zzmp zza(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zza == null) {
            synchronized (zzmp.class) {
                if (zza == null) {
                    zza = new zzmp((zzna) Preconditions.checkNotNull(new zzna(context)));
                }
            }
        }
        return zza;
    }

    public final zzmz zzp() {
        return (zzmz) zza(this.zzh);
    }

    public final zznd zzq() {
        return ((zzhf) Preconditions.checkNotNull(this.zzm)).zzt();
    }

    private final Boolean zza(zzh zzhVar) {
        try {
            if (zzhVar.zzc() != -2147483648L) {
                if (zzhVar.zzc() == Wrappers.packageManager(this.zzm.zza()).getPackageInfo(zzhVar.zzx(), 0).versionCode) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzm.zza()).getPackageInfo(zzhVar.zzx(), 0).versionName;
                String zzaa = zzhVar.zzaa();
                if (zzaa != null && zzaa.equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    private final String zza(zzih zzihVar) {
        if (!zzihVar.zzh()) {
            return null;
        }
        byte[] bArr = new byte[16];
        zzq().zzv().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    final String zzb(zzo zzoVar) {
        try {
            return (String) zzl().zza(new zzmt(this, zzoVar)).get(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzj().zzg().zza("Failed to get app instance id. appId", zzfr.zza(zzoVar.zza), e);
            return null;
        }
    }

    static /* synthetic */ void zza(zzmp zzmpVar, zzna zznaVar) {
        zzmpVar.zzl().zzt();
        zzmpVar.zzl = new zzgm(zzmpVar);
        zzao zzaoVar = new zzao(zzmpVar);
        zzaoVar.zzal();
        zzmpVar.zzd = zzaoVar;
        zzmpVar.zze().zza((zzah) Preconditions.checkNotNull(zzmpVar.zzb));
        zzls zzlsVar = new zzls(zzmpVar);
        zzlsVar.zzal();
        zzmpVar.zzj = zzlsVar;
        zzt zztVar = new zzt(zzmpVar);
        zztVar.zzal();
        zzmpVar.zzg = zztVar;
        zzkg zzkgVar = new zzkg(zzmpVar);
        zzkgVar.zzal();
        zzmpVar.zzi = zzkgVar;
        zzmj zzmjVar = new zzmj(zzmpVar);
        zzmjVar.zzal();
        zzmpVar.zzf = zzmjVar;
        zzmpVar.zze = new zzgb(zzmpVar);
        if (zzmpVar.zzs != zzmpVar.zzt) {
            zzmpVar.zzj().zzg().zza("Not all upload components initialized", Integer.valueOf(zzmpVar.zzs), Integer.valueOf(zzmpVar.zzt));
        }
        zzmpVar.zzn = true;
    }

    private zzmp(zzna zznaVar) {
        this(zznaVar, null);
    }

    private zzmp(zzna zznaVar, zzhf zzhfVar) {
        this.zzn = false;
        this.zzr = new HashSet();
        this.zzah = new zzmw(this);
        Preconditions.checkNotNull(zznaVar);
        this.zzm = zzhf.zza(zznaVar.zza, null, null);
        this.zzab = -1L;
        this.zzk = new zzmn(this);
        zzmz zzmzVar = new zzmz(this);
        zzmzVar.zzal();
        this.zzh = zzmzVar;
        zzfy zzfyVar = new zzfy(this);
        zzfyVar.zzal();
        this.zzc = zzfyVar;
        zzgp zzgpVar = new zzgp(this);
        zzgpVar.zzal();
        this.zzb = zzgpVar;
        this.zzac = new HashMap();
        this.zzad = new HashMap();
        this.zzae = new HashMap();
        zzl().zzb(new zzms(this, zznaVar));
    }

    final void zza(Runnable runnable) {
        zzl().zzt();
        if (this.zzq == null) {
            this.zzq = new ArrayList();
        }
        this.zzq.add(runnable);
    }

    final void zzr() {
        zzl().zzt();
        zzs();
        if (this.zzo) {
            return;
        }
        this.zzo = true;
        if (zzad()) {
            int zza2 = zza(this.zzy);
            int zzab = this.zzm.zzh().zzab();
            zzl().zzt();
            if (zza2 > zzab) {
                zzj().zzg().zza("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzab));
            } else if (zza2 < zzab) {
                if (zza(zzab, this.zzy)) {
                    zzj().zzp().zza("Storage version upgraded. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzab));
                } else {
                    zzj().zzg().zza("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzab));
                }
            }
        }
    }

    final void zzs() {
        if (!this.zzn) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private final void zzaa() {
        zzl().zzt();
        if (this.zzu || this.zzv || this.zzw) {
            zzj().zzp().zza("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzu), Boolean.valueOf(this.zzv), Boolean.valueOf(this.zzw));
            return;
        }
        zzj().zzp().zza("Stopping uploading service(s)");
        List<Runnable> list = this.zzq;
        if (list == null) {
            return;
        }
        Iterator<Runnable> it = list.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        ((List) Preconditions.checkNotNull(this.zzq)).clear();
    }

    final void zza(String str, zzfi.zzj.zza zzaVar) {
        int zza2;
        int indexOf;
        Set<String> zzg = zzi().zzg(str);
        if (zzg != null) {
            zzaVar.zzd(zzg);
        }
        if (zzi().zzq(str)) {
            zzaVar.zzg();
        }
        if (zzi().zzt(str)) {
            if (zze().zze(str, zzbi.zzbv)) {
                String zzu = zzaVar.zzu();
                if (!TextUtils.isEmpty(zzu) && (indexOf = zzu.indexOf(".")) != -1) {
                    zzaVar.zzo(zzu.substring(0, indexOf));
                }
            } else {
                zzaVar.zzl();
            }
        }
        if (zzi().zzu(str) && (zza2 = zzmz.zza(zzaVar, "_id")) != -1) {
            zzaVar.zzc(zza2);
        }
        if (zzi().zzs(str)) {
            zzaVar.zzh();
        }
        if (zzi().zzp(str)) {
            zzaVar.zze();
            zzb zzbVar = this.zzae.get(str);
            if (zzbVar == null || zzbVar.zzb + zze().zzc(str, zzbi.zzat) < zzb().elapsedRealtime()) {
                zzbVar = new zzb();
                this.zzae.put(str, zzbVar);
            }
            zzaVar.zzk(zzbVar.zza);
        }
        if (zzi().zzr(str)) {
            zzaVar.zzp();
        }
    }

    private final void zzb(zzh zzhVar) {
        zzl().zzt();
        if (TextUtils.isEmpty(zzhVar.zzac()) && TextUtils.isEmpty(zzhVar.zzv())) {
            zza((String) Preconditions.checkNotNull(zzhVar.zzx()), 204, (Throwable) null, (byte[]) null, (Map<String, List<String>>) null);
            return;
        }
        Uri.Builder builder = new Uri.Builder();
        String zzac = zzhVar.zzac();
        if (TextUtils.isEmpty(zzac)) {
            zzac = zzhVar.zzv();
        }
        ArrayMap arrayMap = null;
        builder.scheme(zzbi.zze.zza(null)).encodedAuthority(zzbi.zzf.zza(null)).path("config/app/" + zzac).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", "82001").appendQueryParameter("runtime_version", "0");
        String uri = builder.build().toString();
        try {
            String str = (String) Preconditions.checkNotNull(zzhVar.zzx());
            URL url = new URL(uri);
            zzj().zzp().zza("Fetching remote configuration", str);
            zzfc.zzd zzc = zzi().zzc(str);
            String zze = zzi().zze(str);
            if (zzc != null) {
                if (!TextUtils.isEmpty(zze)) {
                    arrayMap = new ArrayMap();
                    arrayMap.put(HttpHeaders.IF_MODIFIED_SINCE, zze);
                }
                String zzd = zzi().zzd(str);
                if (!TextUtils.isEmpty(zzd)) {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                    }
                    arrayMap.put(HttpHeaders.IF_NONE_MATCH, zzd);
                }
            }
            this.zzu = true;
            zzfy zzh = zzh();
            zzmu zzmuVar = new zzmu(this);
            zzh.zzt();
            zzh.zzak();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(zzmuVar);
            zzh.zzl().zza(new zzgc(zzh, str, url, null, arrayMap, zzmuVar));
        } catch (MalformedURLException unused) {
            zzj().zzg().zza("Failed to parse config URL. Not fetching. appId", zzfr.zza(zzhVar.zzx()), uri);
        }
    }

    final void zza(zzh zzhVar, zzfi.zzj.zza zzaVar) {
        zzl().zzt();
        zzs();
        if (zznp.zza()) {
            zzak zza2 = zzak.zza(zzaVar.zzs());
            String zzx = zzhVar.zzx();
            zzl().zzt();
            zzs();
            if (zznp.zza()) {
                zzih zzb2 = zzb(zzx);
                if (zznp.zza() && zze().zza(zzbi.zzco)) {
                    zzaVar.zzg(zzb2.zzf());
                }
                if (zzb2.zzc() != null) {
                    zza2.zza(zzih.zza.AD_STORAGE, zzb2.zza());
                } else {
                    zza2.zza(zzih.zza.AD_STORAGE, zzaj.FAILSAFE);
                }
                if (zzb2.zzd() != null) {
                    zza2.zza(zzih.zza.ANALYTICS_STORAGE, zzb2.zza());
                } else {
                    zza2.zza(zzih.zza.ANALYTICS_STORAGE, zzaj.FAILSAFE);
                }
            }
            String zzx2 = zzhVar.zzx();
            zzl().zzt();
            zzs();
            if (zznp.zza()) {
                zzay zza3 = zza(zzx2, zzd(zzx2), zzb(zzx2), zza2);
                zzaVar.zzb(((Boolean) Preconditions.checkNotNull(zza3.zzd())).booleanValue());
                if (!TextUtils.isEmpty(zza3.zze())) {
                    zzaVar.zzh(zza3.zze());
                }
            }
            zzl().zzt();
            zzs();
            if (zznp.zza()) {
                zzfi.zzn zznVar = null;
                Iterator<zzfi.zzn> it = zzaVar.zzx().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    zzfi.zzn next = it.next();
                    if ("_npa".equals(next.zzg())) {
                        zznVar = next;
                        break;
                    }
                }
                if (zznVar != null) {
                    if (zza2.zza(zzih.zza.AD_PERSONALIZATION) == zzaj.UNSET) {
                        Boolean zzu = zzhVar.zzu();
                        if (zzu == null || ((zzu == Boolean.TRUE && zznVar.zzc() != 1) || (zzu == Boolean.FALSE && zznVar.zzc() != 0))) {
                            zza2.zza(zzih.zza.AD_PERSONALIZATION, zzaj.API);
                        } else {
                            zza2.zza(zzih.zza.AD_PERSONALIZATION, zzaj.MANIFEST);
                        }
                    }
                } else if (zznp.zza() && zze().zza(zzbi.zzcp)) {
                    int i = 1;
                    if (this.zzb.zzb(zzhVar.zzx()) == null) {
                        zza2.zza(zzih.zza.AD_PERSONALIZATION, zzaj.FAILSAFE);
                    } else {
                        i = 1 ^ (this.zzb.zzb(zzhVar.zzx(), zzih.zza.AD_PERSONALIZATION) ? 1 : 0);
                        zza2.zza(zzih.zza.AD_PERSONALIZATION, zzaj.REMOTE_DEFAULT);
                    }
                    zzaVar.zza((zzfi.zzn) ((com.google.android.gms.internal.measurement.zzix) zzfi.zzn.zze().zza("_npa").zzb(zzb().currentTimeMillis()).zza(i).zzab()));
                }
            }
            zzaVar.zzf(zza2.toString());
        }
    }

    private static void zza(zzfi.zze.zza zzaVar, int i, String str) {
        List<zzfi.zzg> zzf = zzaVar.zzf();
        for (int i2 = 0; i2 < zzf.size(); i2++) {
            if ("_err".equals(zzf.get(i2).zzg())) {
                return;
            }
        }
        zzaVar.zza((zzfi.zzg) ((com.google.android.gms.internal.measurement.zzix) zzfi.zzg.zze().zza("_err").zza(Long.valueOf(i).longValue()).zzab())).zza((zzfi.zzg) ((com.google.android.gms.internal.measurement.zzix) zzfi.zzg.zze().zza("_ev").zzb(str).zzab()));
    }

    final void zza(zzbg zzbgVar, zzo zzoVar) {
        zzbg zzbgVar2;
        List<zzad> zza2;
        List<zzad> zza3;
        List<zzad> zza4;
        String str;
        Preconditions.checkNotNull(zzoVar);
        Preconditions.checkNotEmpty(zzoVar.zza);
        zzl().zzt();
        zzs();
        String str2 = zzoVar.zza;
        long j = zzbgVar.zzd;
        zzfv zza5 = zzfv.zza(zzbgVar);
        zzl().zzt();
        zznd.zza((this.zzaf == null || (str = this.zzag) == null || !str.equals(str2)) ? null : this.zzaf, zza5.zzb, false);
        zzbg zza6 = zza5.zza();
        zzp();
        if (zzmz.zza(zza6, zzoVar)) {
            if (!zzoVar.zzh) {
                zza(zzoVar);
                return;
            }
            if (zzoVar.zzs == null) {
                zzbgVar2 = zza6;
            } else if (zzoVar.zzs.contains(zza6.zza)) {
                Bundle zzb2 = zza6.zzb.zzb();
                zzb2.putLong("ga_safelisted", 1L);
                zzbgVar2 = new zzbg(zza6.zza, new zzbb(zzb2), zza6.zzc, zza6.zzd);
            } else {
                zzj().zzc().zza("Dropping non-safelisted event. appId, event name, origin", str2, zza6.zza, zza6.zzc);
                return;
            }
            zzf().zzp();
            try {
                zzao zzf = zzf();
                Preconditions.checkNotEmpty(str2);
                zzf.zzt();
                zzf.zzak();
                if (j < 0) {
                    zzf.zzj().zzu().zza("Invalid time querying timed out conditional properties", zzfr.zza(str2), Long.valueOf(j));
                    zza2 = Collections.emptyList();
                } else {
                    zza2 = zzf.zza("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzad zzadVar : zza2) {
                    if (zzadVar != null) {
                        zzj().zzp().zza("User property timed out", zzadVar.zza, this.zzm.zzk().zzc(zzadVar.zzc.zza), zzadVar.zzc.zza());
                        if (zzadVar.zzg != null) {
                            zzc(new zzbg(zzadVar.zzg, j), zzoVar);
                        }
                        zzf().zza(str2, zzadVar.zzc.zza);
                    }
                }
                zzao zzf2 = zzf();
                Preconditions.checkNotEmpty(str2);
                zzf2.zzt();
                zzf2.zzak();
                if (j < 0) {
                    zzf2.zzj().zzu().zza("Invalid time querying expired conditional properties", zzfr.zza(str2), Long.valueOf(j));
                    zza3 = Collections.emptyList();
                } else {
                    zza3 = zzf2.zza("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(zza3.size());
                for (zzad zzadVar2 : zza3) {
                    if (zzadVar2 != null) {
                        zzj().zzp().zza("User property expired", zzadVar2.zza, this.zzm.zzk().zzc(zzadVar2.zzc.zza), zzadVar2.zzc.zza());
                        zzf().zzh(str2, zzadVar2.zzc.zza);
                        if (zzadVar2.zzk != null) {
                            arrayList.add(zzadVar2.zzk);
                        }
                        zzf().zza(str2, zzadVar2.zzc.zza);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzc(new zzbg((zzbg) obj, j), zzoVar);
                }
                zzao zzf3 = zzf();
                String str3 = zzbgVar2.zza;
                Preconditions.checkNotEmpty(str2);
                Preconditions.checkNotEmpty(str3);
                zzf3.zzt();
                zzf3.zzak();
                if (j < 0) {
                    zzf3.zzj().zzu().zza("Invalid time querying triggered conditional properties", zzfr.zza(str2), zzf3.zzi().zza(str3), Long.valueOf(j));
                    zza4 = Collections.emptyList();
                } else {
                    zza4 = zzf3.zza("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str3, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(zza4.size());
                for (zzad zzadVar3 : zza4) {
                    if (zzadVar3 != null) {
                        zznc zzncVar = zzadVar3.zzc;
                        zzne zzneVar = new zzne((String) Preconditions.checkNotNull(zzadVar3.zza), zzadVar3.zzb, zzncVar.zza, j, Preconditions.checkNotNull(zzncVar.zza()));
                        if (zzf().zza(zzneVar)) {
                            zzj().zzp().zza("User property triggered", zzadVar3.zza, this.zzm.zzk().zzc(zzneVar.zzc), zzneVar.zze);
                        } else {
                            zzj().zzg().zza("Too many active user properties, ignoring", zzfr.zza(zzadVar3.zza), this.zzm.zzk().zzc(zzneVar.zzc), zzneVar.zze);
                        }
                        if (zzadVar3.zzi != null) {
                            arrayList3.add(zzadVar3.zzi);
                        }
                        zzadVar3.zzc = new zznc(zzneVar);
                        zzadVar3.zze = true;
                        zzf().zza(zzadVar3);
                    }
                }
                zzc(zzbgVar2, zzoVar);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList3.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList3.get(i2);
                    i2++;
                    zzc(new zzbg((zzbg) obj2, j), zzoVar);
                }
                zzf().zzw();
            } finally {
                zzf().zzu();
            }
        }
    }

    final void zza(zzbg zzbgVar, String str) {
        String str2;
        int i;
        zzh zzd = zzf().zzd(str);
        if (zzd == null || TextUtils.isEmpty(zzd.zzaa())) {
            zzj().zzc().zza("No app data available; dropping event", str);
            return;
        }
        Boolean zza2 = zza(zzd);
        if (zza2 == null) {
            if (!"_ui".equals(zzbgVar.zza)) {
                zzj().zzu().zza("Could not find package. appId", zzfr.zza(str));
            }
        } else if (!zza2.booleanValue()) {
            zzj().zzg().zza("App version does not match; dropping event. appId", zzfr.zza(str));
            return;
        }
        zzih zzb2 = zzb(str);
        if (zznp.zza() && zze().zza(zzbi.zzcm)) {
            str2 = zzd(str).zzf();
            i = zzb2.zza();
        } else {
            str2 = "";
            i = 100;
        }
        zzb(zzbgVar, new zzo(str, zzd.zzac(), zzd.zzaa(), zzd.zzc(), zzd.zzz(), zzd.zzo(), zzd.zzl(), (String) null, zzd.zzak(), false, zzd.zzab(), zzd.zzb(), 0L, 0, zzd.zzaj(), false, zzd.zzv(), zzd.zzu(), zzd.zzm(), zzd.zzag(), (String) null, zzb2.zze(), "", (String) null, zzd.zzam(), zzd.zzt(), i, str2, zzd.zza(), zzd.zzd()));
    }

    private final void zzb(zzbg zzbgVar, zzo zzoVar) {
        Preconditions.checkNotEmpty(zzoVar.zza);
        zzfv zza2 = zzfv.zza(zzbgVar);
        zzq().zza(zza2.zzb, zzf().zzc(zzoVar.zza));
        zzq().zza(zza2, zze().zzd(zzoVar.zza));
        zzbg zza3 = zza2.zza();
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zza3.zza) && "referrer API v2".equals(zza3.zzb.zzd("_cis"))) {
            String zzd = zza3.zzb.zzd("gclid");
            if (!TextUtils.isEmpty(zzd)) {
                zza(new zznc("_lgclid", zza3.zzd, zzd, "auto"), zzoVar);
            }
        }
        if (zzoi.zza() && zzoi.zzc() && Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zza3.zza) && "referrer API v2".equals(zza3.zzb.zzd("_cis"))) {
            String zzd2 = zza3.zzb.zzd("gbraid");
            if (!TextUtils.isEmpty(zzd2)) {
                zza(new zznc("_gbraid", zza3.zzd, zzd2, "auto"), zzoVar);
            }
        }
        zza(zza3, zzoVar);
    }

    private final void zza(zzfi.zzj.zza zzaVar, long j, boolean z) {
        zzne zzneVar;
        String str = z ? "_se" : "_lte";
        zzne zze = zzf().zze(zzaVar.zzr(), str);
        if (zze == null || zze.zze == null) {
            zzneVar = new zzne(zzaVar.zzr(), "auto", str, zzb().currentTimeMillis(), Long.valueOf(j));
        } else {
            zzneVar = new zzne(zzaVar.zzr(), "auto", str, zzb().currentTimeMillis(), Long.valueOf(((Long) zze.zze).longValue() + j));
        }
        zzfi.zzn zznVar = (zzfi.zzn) ((com.google.android.gms.internal.measurement.zzix) zzfi.zzn.zze().zza(str).zzb(zzb().currentTimeMillis()).zza(((Long) zzneVar.zze).longValue()).zzab());
        boolean z2 = false;
        int zza2 = zzmz.zza(zzaVar, str);
        if (zza2 >= 0) {
            zzaVar.zza(zza2, zznVar);
            z2 = true;
        }
        if (!z2) {
            zzaVar.zza(zznVar);
        }
        if (j > 0) {
            zzf().zza(zzneVar);
            zzj().zzp().zza("Updated engagement user property. scope, value", z ? "session-scoped" : "lifetime", zzneVar.zze);
        }
    }

    final void zzt() {
        this.zzt++;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a6, code lost:
    
        r6.zzj.zzb.zza(zzb().currentTimeMillis());
     */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0144 A[Catch: all -> 0x0193, TryCatch #0 {all -> 0x0193, blocks: (B:5:0x002b, B:12:0x0047, B:13:0x017f, B:23:0x0061, B:30:0x00a6, B:31:0x00b5, B:34:0x00bd, B:36:0x00c9, B:38:0x00cf, B:40:0x00d9, B:42:0x00e5, B:44:0x00eb, B:48:0x00f8, B:53:0x0130, B:55:0x0144, B:56:0x0168, B:58:0x0172, B:60:0x0178, B:61:0x017c, B:62:0x0152, B:63:0x010f, B:65:0x0119), top: B:4:0x002b, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0152 A[Catch: all -> 0x0193, TryCatch #0 {all -> 0x0193, blocks: (B:5:0x002b, B:12:0x0047, B:13:0x017f, B:23:0x0061, B:30:0x00a6, B:31:0x00b5, B:34:0x00bd, B:36:0x00c9, B:38:0x00cf, B:40:0x00d9, B:42:0x00e5, B:44:0x00eb, B:48:0x00f8, B:53:0x0130, B:55:0x0144, B:56:0x0168, B:58:0x0172, B:60:0x0178, B:61:0x017c, B:62:0x0152, B:63:0x010f, B:65:0x0119), top: B:4:0x002b, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zza(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r11) {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zza(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    final void zza(boolean z) {
        zzab();
    }

    /* JADX WARN: Code restructure failed: missing block: B:93:0x01a3, code lost:
    
        r8.zzj.zzb.zza(zzb().currentTimeMillis());
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c4 A[Catch: all -> 0x0142, TRY_LEAVE, TryCatch #1 {all -> 0x0142, blocks: (B:27:0x00ba, B:28:0x00be, B:30:0x00c4, B:32:0x00ca, B:34:0x00e4, B:37:0x00ef, B:38:0x00f6, B:47:0x00f8, B:48:0x0105, B:52:0x0107, B:54:0x010b, B:59:0x0112, B:62:0x0113), top: B:26:0x00ba, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zza(boolean r9, int r10, java.lang.Throwable r11, byte[] r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zza(boolean, int, java.lang.Throwable, byte[], java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:161:0x0536 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0119 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01d9 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0210 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0237 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0257 A[Catch: all -> 0x0564, TRY_LEAVE, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03c7 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03f2 A[Catch: all -> 0x0564, TRY_LEAVE, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x04c9 A[Catch: all -> 0x0564, TryCatch #3 {all -> 0x0564, blocks: (B:25:0x00a3, B:27:0x00af, B:31:0x0107, B:33:0x0119, B:35:0x012e, B:37:0x0154, B:39:0x01b2, B:43:0x01c5, B:45:0x01d9, B:47:0x01e4, B:50:0x01f3, B:53:0x0201, B:56:0x020c, B:58:0x0210, B:59:0x0232, B:61:0x0237, B:63:0x0257, B:66:0x026b, B:68:0x0295, B:71:0x029d, B:73:0x02ac, B:74:0x0395, B:76:0x03c7, B:77:0x03ca, B:79:0x03f2, B:84:0x04c9, B:85:0x04ce, B:86:0x0555, B:91:0x0409, B:93:0x042e, B:95:0x0437, B:97:0x0442, B:101:0x0454, B:103:0x0462, B:106:0x046d, B:108:0x0488, B:110:0x04ad, B:112:0x04b3, B:113:0x04b8, B:115:0x04be, B:118:0x0499, B:120:0x045a, B:126:0x041a, B:127:0x02bd, B:129:0x02e8, B:130:0x02f9, B:132:0x0300, B:134:0x0306, B:136:0x0310, B:138:0x031a, B:140:0x0320, B:142:0x0326, B:144:0x032b, B:147:0x034d, B:151:0x0352, B:152:0x0366, B:153:0x0376, B:154:0x0386, B:157:0x04eb, B:159:0x051c, B:160:0x051f, B:161:0x0536, B:163:0x053a, B:166:0x0247, B:172:0x00c1, B:174:0x00c5, B:177:0x00d6, B:179:0x00ee, B:181:0x00f8, B:185:0x0104), top: B:24:0x00a3, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0409 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzc(com.google.android.gms.measurement.internal.zzo r24) {
        /*
            Method dump skipped, instructions count: 1389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zzc(com.google.android.gms.measurement.internal.zzo):void");
    }

    final void zzu() {
        this.zzs++;
    }

    final void zza(zzad zzadVar) {
        zzo zzc = zzc((String) Preconditions.checkNotNull(zzadVar.zza));
        if (zzc != null) {
            zza(zzadVar, zzc);
        }
    }

    final void zza(zzad zzadVar, zzo zzoVar) {
        Preconditions.checkNotNull(zzadVar);
        Preconditions.checkNotEmpty(zzadVar.zza);
        Preconditions.checkNotNull(zzadVar.zzc);
        Preconditions.checkNotEmpty(zzadVar.zzc.zza);
        zzl().zzt();
        zzs();
        if (zze(zzoVar)) {
            if (!zzoVar.zzh) {
                zza(zzoVar);
                return;
            }
            zzf().zzp();
            try {
                zza(zzoVar);
                String str = (String) Preconditions.checkNotNull(zzadVar.zza);
                zzad zzc = zzf().zzc(str, zzadVar.zzc.zza);
                if (zzc != null) {
                    zzj().zzc().zza("Removing conditional user property", zzadVar.zza, this.zzm.zzk().zzc(zzadVar.zzc.zza));
                    zzf().zza(str, zzadVar.zzc.zza);
                    if (zzc.zze) {
                        zzf().zzh(str, zzadVar.zzc.zza);
                    }
                    if (zzadVar.zzk != null) {
                        zzc((zzbg) Preconditions.checkNotNull(zzq().zza(str, ((zzbg) Preconditions.checkNotNull(zzadVar.zzk)).zza, zzadVar.zzk.zzb != null ? zzadVar.zzk.zzb.zzb() : null, zzc.zzb, zzadVar.zzk.zzd, true, true)), zzoVar);
                    }
                } else {
                    zzj().zzu().zza("Conditional user property doesn't exist", zzfr.zza(zzadVar.zza), this.zzm.zzk().zzc(zzadVar.zzc.zza));
                }
                zzf().zzw();
            } finally {
                zzf().zzu();
            }
        }
    }

    private static void zza(zzfi.zze.zza zzaVar, String str) {
        List<zzfi.zzg> zzf = zzaVar.zzf();
        for (int i = 0; i < zzf.size(); i++) {
            if (str.equals(zzf.get(i).zzg())) {
                zzaVar.zza(i);
                return;
            }
        }
    }

    final void zza(String str, zzo zzoVar) {
        zzl().zzt();
        zzs();
        if (zze(zzoVar)) {
            if (!zzoVar.zzh) {
                zza(zzoVar);
                return;
            }
            if ("_npa".equals(str) && zzoVar.zzq != null) {
                zzj().zzc().zza("Falling back to manifest metadata value for ad personalization");
                zza(new zznc("_npa", zzb().currentTimeMillis(), Long.valueOf(zzoVar.zzq.booleanValue() ? 1L : 0L), "auto"), zzoVar);
                return;
            }
            zzj().zzc().zza("Removing user property", this.zzm.zzk().zzc(str));
            zzf().zzp();
            try {
                zza(zzoVar);
                if ("_id".equals(str)) {
                    zzf().zzh((String) Preconditions.checkNotNull(zzoVar.zza), "_lair");
                }
                zzf().zzh((String) Preconditions.checkNotNull(zzoVar.zza), str);
                zzf().zzw();
                zzj().zzc().zza("User property removed", this.zzm.zzk().zzc(str));
            } finally {
                zzf().zzu();
            }
        }
    }

    final void zzd(zzo zzoVar) {
        if (this.zzz != null) {
            ArrayList arrayList = new ArrayList();
            this.zzaa = arrayList;
            arrayList.addAll(this.zzz);
        }
        zzao zzf = zzf();
        String str = (String) Preconditions.checkNotNull(zzoVar.zza);
        Preconditions.checkNotEmpty(str);
        zzf.zzt();
        zzf.zzak();
        try {
            SQLiteDatabase e_ = zzf.e_();
            String[] strArr = {str};
            int delete = e_.delete("apps", "app_id=?", strArr) + 0 + e_.delete("events", "app_id=?", strArr) + e_.delete("user_attributes", "app_id=?", strArr) + e_.delete("conditional_properties", "app_id=?", strArr) + e_.delete("raw_events", "app_id=?", strArr) + e_.delete("raw_events_metadata", "app_id=?", strArr) + e_.delete("queue", "app_id=?", strArr) + e_.delete("audience_filter_values", "app_id=?", strArr) + e_.delete("main_event_params", "app_id=?", strArr) + e_.delete("default_event_params", "app_id=?", strArr) + e_.delete("trigger_uris", "app_id=?", strArr);
            if (delete > 0) {
                zzf.zzj().zzp().zza("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzf.zzj().zzg().zza("Error resetting analytics data. appId, error", zzfr.zza(str), e);
        }
        if (zzoVar.zzh) {
            zzc(zzoVar);
        }
    }

    public final void zza(String str, zzki zzkiVar) {
        zzl().zzt();
        String str2 = this.zzag;
        if (str2 == null || str2.equals(str) || zzkiVar != null) {
            this.zzag = str;
            this.zzaf = zzkiVar;
        }
    }

    private final void zza(List<Long> list) {
        Preconditions.checkArgument(!list.isEmpty());
        if (this.zzz != null) {
            zzj().zzg().zza("Set uploading progress before finishing the previous upload");
        } else {
            this.zzz = new ArrayList(list);
        }
    }

    protected final void zzv() {
        zzl().zzt();
        zzf().zzv();
        if (this.zzj.zzc.zza() == 0) {
            this.zzj.zzc.zza(zzb().currentTimeMillis());
        }
        zzab();
    }

    final void zzb(zzad zzadVar) {
        zzo zzc = zzc((String) Preconditions.checkNotNull(zzadVar.zza));
        if (zzc != null) {
            zzb(zzadVar, zzc);
        }
    }

    final void zzb(zzad zzadVar, zzo zzoVar) {
        Preconditions.checkNotNull(zzadVar);
        Preconditions.checkNotEmpty(zzadVar.zza);
        Preconditions.checkNotNull(zzadVar.zzb);
        Preconditions.checkNotNull(zzadVar.zzc);
        Preconditions.checkNotEmpty(zzadVar.zzc.zza);
        zzl().zzt();
        zzs();
        if (zze(zzoVar)) {
            if (!zzoVar.zzh) {
                zza(zzoVar);
                return;
            }
            zzad zzadVar2 = new zzad(zzadVar);
            boolean z = false;
            zzadVar2.zze = false;
            zzf().zzp();
            try {
                zzad zzc = zzf().zzc((String) Preconditions.checkNotNull(zzadVar2.zza), zzadVar2.zzc.zza);
                if (zzc != null && !zzc.zzb.equals(zzadVar2.zzb)) {
                    zzj().zzu().zza("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzm.zzk().zzc(zzadVar2.zzc.zza), zzadVar2.zzb, zzc.zzb);
                }
                if (zzc != null && zzc.zze) {
                    zzadVar2.zzb = zzc.zzb;
                    zzadVar2.zzd = zzc.zzd;
                    zzadVar2.zzh = zzc.zzh;
                    zzadVar2.zzf = zzc.zzf;
                    zzadVar2.zzi = zzc.zzi;
                    zzadVar2.zze = zzc.zze;
                    zzadVar2.zzc = new zznc(zzadVar2.zzc.zza, zzc.zzc.zzb, zzadVar2.zzc.zza(), zzc.zzc.zze);
                } else if (TextUtils.isEmpty(zzadVar2.zzf)) {
                    zzadVar2.zzc = new zznc(zzadVar2.zzc.zza, zzadVar2.zzd, zzadVar2.zzc.zza(), zzadVar2.zzc.zze);
                    zzadVar2.zze = true;
                    z = true;
                }
                if (zzadVar2.zze) {
                    zznc zzncVar = zzadVar2.zzc;
                    zzne zzneVar = new zzne((String) Preconditions.checkNotNull(zzadVar2.zza), zzadVar2.zzb, zzncVar.zza, zzncVar.zzb, Preconditions.checkNotNull(zzncVar.zza()));
                    if (zzf().zza(zzneVar)) {
                        zzj().zzc().zza("User property updated immediately", zzadVar2.zza, this.zzm.zzk().zzc(zzneVar.zzc), zzneVar.zze);
                    } else {
                        zzj().zzg().zza("(2)Too many active user properties, ignoring", zzfr.zza(zzadVar2.zza), this.zzm.zzk().zzc(zzneVar.zzc), zzneVar.zze);
                    }
                    if (z && zzadVar2.zzi != null) {
                        zzc(new zzbg(zzadVar2.zzi, zzadVar2.zzd), zzoVar);
                    }
                }
                if (zzf().zza(zzadVar2)) {
                    zzj().zzc().zza("Conditional property added", zzadVar2.zza, this.zzm.zzk().zzc(zzadVar2.zzc.zza), zzadVar2.zzc.zza());
                } else {
                    zzj().zzg().zza("Too many conditional properties, ignoring", zzfr.zza(zzadVar2.zza), this.zzm.zzk().zzc(zzadVar2.zzc.zza), zzadVar2.zzc.zza());
                }
                zzf().zzw();
            } finally {
                zzf().zzu();
            }
        }
    }

    final void zza(String str, zzih zzihVar) {
        zzl().zzt();
        zzs();
        this.zzac.put(str, zzihVar);
        zzf().zza(str, zzihVar);
    }

    final void zza(String str, zzay zzayVar) {
        zzl().zzt();
        zzs();
        if (zznp.zza()) {
            this.zzad.put(str, zzayVar);
            zzf().zza(str, zzayVar);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzab() {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zzab():void");
    }

    private final void zza(String str, boolean z) {
        zzh zzd = zzf().zzd(str);
        if (zzd != null) {
            zzd.zzd(z);
            if (zzd.zzal()) {
                zzf().zza(zzd);
            }
        }
    }

    final void zza(zznc zzncVar, zzo zzoVar) {
        zzne zze;
        zzl().zzt();
        zzs();
        if (zze(zzoVar)) {
            if (!zzoVar.zzh) {
                zza(zzoVar);
                return;
            }
            int zzb2 = zzq().zzb(zzncVar.zza);
            if (zzb2 != 0) {
                zzq();
                String str = zzncVar.zza;
                zze();
                String zza2 = zznd.zza(str, 24, true);
                int length = zzncVar.zza != null ? zzncVar.zza.length() : 0;
                zzq();
                zznd.zza(this.zzah, zzoVar.zza, zzb2, "_ev", zza2, length);
                return;
            }
            int zza3 = zzq().zza(zzncVar.zza, zzncVar.zza());
            if (zza3 != 0) {
                zzq();
                String str2 = zzncVar.zza;
                zze();
                String zza4 = zznd.zza(str2, 24, true);
                Object zza5 = zzncVar.zza();
                int length2 = (zza5 == null || !((zza5 instanceof String) || (zza5 instanceof CharSequence))) ? 0 : String.valueOf(zza5).length();
                zzq();
                zznd.zza(this.zzah, zzoVar.zza, zza3, "_ev", zza4, length2);
                return;
            }
            Object zzc = zzq().zzc(zzncVar.zza, zzncVar.zza());
            if (zzc == null) {
                return;
            }
            if ("_sid".equals(zzncVar.zza)) {
                long j = zzncVar.zzb;
                String str3 = zzncVar.zze;
                String str4 = (String) Preconditions.checkNotNull(zzoVar.zza);
                long j2 = 0;
                zzne zze2 = zzf().zze(str4, "_sno");
                if (zze2 != null && (zze2.zze instanceof Long)) {
                    j2 = ((Long) zze2.zze).longValue();
                } else {
                    if (zze2 != null) {
                        zzj().zzu().zza("Retrieved last session number from database does not contain a valid (long) value", zze2.zze);
                    }
                    zzbc zzd = zzf().zzd(str4, "_s");
                    if (zzd != null) {
                        j2 = zzd.zzc;
                        zzj().zzp().zza("Backfill the session number. Last used session number", Long.valueOf(j2));
                    }
                }
                zza(new zznc("_sno", j, Long.valueOf(j2 + 1), str3), zzoVar);
            }
            zzne zzneVar = new zzne((String) Preconditions.checkNotNull(zzoVar.zza), (String) Preconditions.checkNotNull(zzncVar.zze), zzncVar.zza, zzncVar.zzb, zzc);
            zzj().zzp().zza("Setting user property", this.zzm.zzk().zzc(zzneVar.zzc), zzc);
            zzf().zzp();
            try {
                if ("_id".equals(zzneVar.zzc) && (zze = zzf().zze(zzoVar.zza, "_id")) != null && !zzneVar.zze.equals(zze.zze)) {
                    zzf().zzh(zzoVar.zza, "_lair");
                }
                zza(zzoVar);
                boolean zza6 = zzf().zza(zzneVar);
                if ("_sid".equals(zzncVar.zza)) {
                    long zza7 = zzp().zza(zzoVar.zzv);
                    zzh zzd2 = zzf().zzd(zzoVar.zza);
                    if (zzd2 != null) {
                        zzd2.zzq(zza7);
                        if (zzd2.zzal()) {
                            zzf().zza(zzd2);
                        }
                    }
                }
                zzf().zzw();
                if (!zza6) {
                    zzj().zzg().zza("Too many unique user properties are set. Ignoring user property", this.zzm.zzk().zzc(zzneVar.zzc), zzneVar.zze);
                    zzq();
                    zznd.zza(this.zzah, zzoVar.zza, 9, (String) null, (String) null, 0);
                }
            } finally {
                zzf().zzu();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0312  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzw() {
        /*
            Method dump skipped, instructions count: 1108
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zzw():void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:93|(6:98|99|100|(1:102)|103|(0))|329|330|331|332|99|100|(0)|103|(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(55:(2:117|(5:119|(1:121)|122|123|124))|(2:126|(5:128|(1:130)|131|132|133))|134|135|(1:137)|138|(1:144)|145|(1:147)|148|(2:150|(1:156)(3:153|154|155))(1:328)|157|(1:159)|160|(1:162)|163|(1:165)|166|(1:174)|175|(1:177)|178|(1:180)|181|(1:185)|186|(2:190|(33:192|(1:196)|197|(1:199)(1:326)|200|(15:202|(1:204)(1:230)|205|(1:207)(1:229)|208|(1:210)(1:228)|211|(1:213)(1:227)|214|(1:216)(1:226)|217|(1:219)(1:225)|220|(1:222)(1:224)|223)|231|(1:233)|234|(1:236)|237|(4:247|(1:249)|250|(21:262|263|(2:265|(1:267))|268|(3:270|(1:272)|273)|274|(1:278)|279|(1:281)|282|(4:285|(2:291|292)|293|283)|297|298|299|(2:301|(2:302|(2:304|(2:306|307)(1:314))(3:315|316|(1:320))))|321|308|(1:310)|311|312|313))|325|263|(0)|268|(0)|274|(2:276|278)|279|(0)|282|(1:283)|297|298|299|(0)|321|308|(0)|311|312|313))|327|231|(0)|234|(0)|237|(8:239|241|243|245|247|(0)|250|(26:252|254|256|258|260|262|263|(0)|268|(0)|274|(0)|279|(0)|282|(1:283)|297|298|299|(0)|321|308|(0)|311|312|313))|325|263|(0)|268|(0)|274|(0)|279|(0)|282|(1:283)|297|298|299|(0)|321|308|(0)|311|312|313) */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x09d1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x09d2, code lost:
    
        zzj().zzg().zza("Data loss. Failed to insert raw event metadata. appId", com.google.android.gms.measurement.internal.zzfr.zza(r2.zzr()), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x02d8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x02da, code lost:
    
        r9.zzj().zzg().zza("Error pruning currencies. appId", com.google.android.gms.measurement.internal.zzfr.zza(r8), r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x030e A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0344 A[Catch: all -> 0x0a19, TRY_LEAVE, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x03ae A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x073f A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0753 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0799 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0840 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0859 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x08bf A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:281:0x08e0 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x08fe A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0974 A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:310:0x09ce A[Catch: all -> 0x0a19, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0353  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ce A[Catch: all -> 0x0a19, TRY_LEAVE, TryCatch #2 {all -> 0x0a19, blocks: (B:61:0x0197, B:64:0x01a6, B:66:0x01b0, B:70:0x01bc, B:76:0x01ce, B:79:0x01da, B:81:0x01f1, B:86:0x020a, B:89:0x023f, B:91:0x0245, B:93:0x0253, B:95:0x026b, B:98:0x0272, B:100:0x0304, B:102:0x030e, B:105:0x0344, B:108:0x0358, B:110:0x03ae, B:112:0x03b3, B:113:0x03ca, B:117:0x03db, B:119:0x03f3, B:121:0x03fa, B:122:0x0411, B:126:0x0433, B:130:0x0459, B:131:0x0470, B:134:0x047f, B:137:0x049e, B:138:0x04b8, B:140:0x04c2, B:142:0x04ce, B:144:0x04d4, B:145:0x04dd, B:147:0x04eb, B:148:0x0500, B:150:0x0526, B:153:0x053d, B:156:0x057c, B:157:0x05a6, B:159:0x05e4, B:160:0x05e9, B:162:0x05f1, B:163:0x05f6, B:165:0x05fe, B:166:0x0603, B:168:0x0609, B:170:0x0611, B:172:0x061d, B:174:0x062b, B:175:0x0630, B:177:0x0639, B:178:0x063f, B:180:0x064c, B:181:0x0651, B:183:0x0678, B:185:0x0680, B:186:0x0685, B:188:0x068b, B:190:0x0699, B:192:0x06a4, B:196:0x06b9, B:200:0x06c8, B:202:0x06cf, B:205:0x06dc, B:208:0x06e9, B:211:0x06f6, B:214:0x0703, B:217:0x0710, B:220:0x071b, B:223:0x0728, B:231:0x0739, B:233:0x073f, B:234:0x0744, B:236:0x0753, B:237:0x0756, B:239:0x0772, B:241:0x0776, B:243:0x0780, B:245:0x078a, B:247:0x078e, B:249:0x0799, B:250:0x07a4, B:252:0x07aa, B:254:0x07b6, B:256:0x07be, B:258:0x07ca, B:260:0x07d6, B:262:0x07dc, B:263:0x07f9, B:265:0x0840, B:267:0x084a, B:268:0x084d, B:270:0x0859, B:272:0x0879, B:273:0x0886, B:274:0x08b9, B:276:0x08bf, B:278:0x08c9, B:279:0x08d6, B:281:0x08e0, B:282:0x08ed, B:283:0x08f8, B:285:0x08fe, B:287:0x093c, B:289:0x0944, B:291:0x0956, B:298:0x095c, B:299:0x096c, B:301:0x0974, B:302:0x097a, B:304:0x0980, B:308:0x09c8, B:310:0x09ce, B:311:0x09e8, B:316:0x098d, B:318:0x09b5, B:324:0x09d2, B:328:0x0598, B:329:0x029f, B:331:0x02bd, B:332:0x02eb, B:336:0x02da, B:338:0x0218, B:339:0x0235), top: B:60:0x0197, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzc(com.google.android.gms.measurement.internal.zzbg r29, com.google.android.gms.measurement.internal.zzo r30) {
        /*
            Method dump skipped, instructions count: 2595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zzc(com.google.android.gms.measurement.internal.zzbg, com.google.android.gms.measurement.internal.zzo):void");
    }

    private static boolean zze(zzo zzoVar) {
        return (TextUtils.isEmpty(zzoVar.zzb) && TextUtils.isEmpty(zzoVar.zzp)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:129:0x05b8 A[Catch: all -> 0x0ef7, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0683 A[Catch: all -> 0x0ef7, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x06d5 A[Catch: all -> 0x0ef7, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0243 A[Catch: all -> 0x0ef7, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0251 A[Catch: all -> 0x0ef7, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0edf A[Catch: all -> 0x0ef7, TRY_ENTER, TRY_LEAVE, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:544:0x023c A[Catch: all -> 0x0ef7, TRY_ENTER, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0ef3 A[Catch: all -> 0x0ef7, TRY_ENTER, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:572:? A[Catch: all -> 0x0ef7, SYNTHETIC, TRY_LEAVE, TryCatch #4 {all -> 0x0ef7, blocks: (B:3:0x000b, B:20:0x0076, B:21:0x023f, B:23:0x0243, B:28:0x0251, B:29:0x0267, B:32:0x027d, B:35:0x02a7, B:37:0x02dc, B:42:0x02f2, B:44:0x02fc, B:47:0x0745, B:49:0x0326, B:51:0x0334, B:54:0x0350, B:56:0x0356, B:58:0x0368, B:60:0x0376, B:62:0x0386, B:64:0x0393, B:69:0x0398, B:71:0x03ae, B:80:0x03e7, B:83:0x03f1, B:85:0x03ff, B:87:0x0450, B:88:0x0421, B:90:0x0431, B:97:0x045d, B:99:0x048b, B:100:0x04b7, B:102:0x04e9, B:103:0x04ef, B:106:0x04fb, B:108:0x052e, B:109:0x0549, B:111:0x054f, B:113:0x055d, B:115:0x0574, B:116:0x0569, B:124:0x057b, B:126:0x0581, B:127:0x059f, B:129:0x05b8, B:130:0x05c4, B:133:0x05ce, B:137:0x05f1, B:138:0x05e0, B:146:0x05f7, B:148:0x0603, B:150:0x060f, B:155:0x065c, B:156:0x0677, B:158:0x0683, B:161:0x0696, B:163:0x06a8, B:165:0x06b6, B:167:0x072d, B:172:0x06d5, B:174:0x06e5, B:177:0x06fa, B:179:0x070c, B:181:0x071a, B:183:0x062e, B:187:0x0642, B:189:0x0648, B:191:0x0653, B:201:0x03c4, B:208:0x075b, B:210:0x0769, B:212:0x0772, B:214:0x07aa, B:215:0x077a, B:217:0x0783, B:219:0x0789, B:221:0x0795, B:223:0x079d, B:229:0x07af, B:230:0x07bd, B:232:0x07c3, B:238:0x07dc, B:239:0x07e7, B:243:0x07f4, B:244:0x0819, B:246:0x0826, B:248:0x0832, B:250:0x0848, B:252:0x0852, B:253:0x0864, B:254:0x0867, B:255:0x0876, B:257:0x087c, B:259:0x088c, B:260:0x0893, B:262:0x089f, B:264:0x08a6, B:267:0x08a9, B:269:0x08b2, B:271:0x08c4, B:273:0x08d3, B:275:0x08e3, B:278:0x08ec, B:280:0x08f4, B:281:0x090d, B:283:0x0913, B:288:0x0928, B:290:0x0940, B:292:0x0952, B:293:0x0975, B:295:0x09a2, B:297:0x09cf, B:299:0x09da, B:305:0x09de, B:307:0x0a19, B:308:0x0a2c, B:310:0x0a32, B:313:0x0a4d, B:315:0x0a68, B:317:0x0a7e, B:319:0x0a83, B:321:0x0a87, B:323:0x0a8b, B:325:0x0a97, B:326:0x0a9f, B:328:0x0aa3, B:330:0x0aab, B:331:0x0ab9, B:332:0x0ac4, B:335:0x0d16, B:336:0x0ad1, B:340:0x0b03, B:341:0x0b0b, B:343:0x0b11, B:347:0x0b23, B:349:0x0b27, B:353:0x0b5d, B:355:0x0b73, B:356:0x0b98, B:358:0x0ba4, B:360:0x0bba, B:361:0x0bf9, B:364:0x0c11, B:366:0x0c18, B:368:0x0c29, B:370:0x0c2d, B:372:0x0c31, B:374:0x0c35, B:375:0x0c41, B:376:0x0c46, B:378:0x0c4c, B:380:0x0c6b, B:381:0x0c74, B:382:0x0d13, B:384:0x0c8b, B:386:0x0c92, B:389:0x0cb2, B:391:0x0cdc, B:392:0x0cea, B:394:0x0cfc, B:396:0x0d06, B:397:0x0c9d, B:401:0x0b35, B:403:0x0b39, B:405:0x0b43, B:407:0x0b47, B:412:0x0d22, B:414:0x0d2f, B:415:0x0d36, B:416:0x0d3e, B:418:0x0d44, B:421:0x0d5c, B:423:0x0d6c, B:424:0x0ddf, B:426:0x0de5, B:428:0x0df5, B:431:0x0dfc, B:432:0x0e2d, B:433:0x0e04, B:435:0x0e10, B:436:0x0e16, B:437:0x0e3e, B:438:0x0e55, B:441:0x0e5d, B:443:0x0e62, B:446:0x0e72, B:448:0x0e8c, B:449:0x0ea5, B:451:0x0ead, B:452:0x0ecf, B:459:0x0ebe, B:460:0x0d84, B:462:0x0d8a, B:464:0x0d94, B:465:0x0d9b, B:470:0x0dab, B:471:0x0db2, B:473:0x0dd1, B:474:0x0dd8, B:475:0x0dd5, B:476:0x0daf, B:478:0x0d98, B:480:0x07f9, B:482:0x07ff, B:487:0x0edf, B:498:0x010f, B:512:0x01a5, B:527:0x01de, B:523:0x01fd, B:538:0x0216, B:544:0x023c, B:570:0x0ef3, B:571:0x0ef6, B:560:0x00c2, B:501:0x0118), top: B:2:0x000b, inners: #5, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x03d6  */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.gms.measurement.internal.zzmx] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zza(java.lang.String r41, long r42) {
        /*
            Method dump skipped, instructions count: 3841
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzmp.zza(java.lang.String, long):boolean");
    }

    private final boolean zzac() {
        zzl().zzt();
        zzs();
        return zzf().zzx() || !TextUtils.isEmpty(zzf().f_());
    }

    private final boolean zzad() {
        zzl().zzt();
        FileLock fileLock = this.zzx;
        if (fileLock != null && fileLock.isValid()) {
            zzj().zzp().zza("Storage concurrent access okay");
            return true;
        }
        try {
            FileChannel channel = new RandomAccessFile(new File(this.zzm.zza().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzy = channel;
            FileLock tryLock = channel.tryLock();
            this.zzx = tryLock;
            if (tryLock != null) {
                zzj().zzp().zza("Storage concurrent access okay");
                return true;
            }
            zzj().zzg().zza("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzj().zzg().zza("Failed to acquire storage lock", e);
            return false;
        } catch (IOException e2) {
            zzj().zzg().zza("Failed to access storage lock file", e2);
            return false;
        } catch (OverlappingFileLockException e3) {
            zzj().zzu().zza("Storage lock already acquired", e3);
            return false;
        }
    }

    private final boolean zza(zzfi.zze.zza zzaVar, zzfi.zze.zza zzaVar2) {
        Preconditions.checkArgument("_e".equals(zzaVar.zze()));
        zzp();
        zzfi.zzg zza2 = zzmz.zza((zzfi.zze) ((com.google.android.gms.internal.measurement.zzix) zzaVar.zzab()), "_sc");
        String zzh = zza2 == null ? null : zza2.zzh();
        zzp();
        zzfi.zzg zza3 = zzmz.zza((zzfi.zze) ((com.google.android.gms.internal.measurement.zzix) zzaVar2.zzab()), "_pc");
        String zzh2 = zza3 != null ? zza3.zzh() : null;
        if (zzh2 == null || !zzh2.equals(zzh)) {
            return false;
        }
        Preconditions.checkArgument("_e".equals(zzaVar.zze()));
        zzp();
        zzfi.zzg zza4 = zzmz.zza((zzfi.zze) ((com.google.android.gms.internal.measurement.zzix) zzaVar.zzab()), "_et");
        if (zza4 == null || !zza4.zzl() || zza4.zzd() <= 0) {
            return true;
        }
        long zzd = zza4.zzd();
        zzp();
        zzfi.zzg zza5 = zzmz.zza((zzfi.zze) ((com.google.android.gms.internal.measurement.zzix) zzaVar2.zzab()), "_et");
        if (zza5 != null && zza5.zzd() > 0) {
            zzd += zza5.zzd();
        }
        zzp();
        zzmz.zza(zzaVar2, "_et", Long.valueOf(zzd));
        zzp();
        zzmz.zza(zzaVar, "_fr", (Object) 1L);
        return true;
    }

    private final boolean zza(int i, FileChannel fileChannel) {
        zzl().zzt();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzj().zzg().zza("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0L);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                zzj().zzg().zza("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            zzj().zzg().zza("Failed to write to channel", e);
            return false;
        }
    }
}
