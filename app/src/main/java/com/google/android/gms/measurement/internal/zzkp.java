package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.time.DateUtils;
import org.checkerframework.dataflow.qual.Pure;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzkp extends zze {
    private final zzlm zza;
    private zzfk zzb;
    private volatile Boolean zzc;
    private final zzaw zzd;
    private final zzmi zze;
    private final List<Runnable> zzf;
    private final zzaw zzg;

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

    private final zzo zzb(boolean z) {
        return zzg().zza(z ? zzj().zzx() : null);
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

    protected final zzam zzaa() {
        zzt();
        zzu();
        zzfk zzfkVar = this.zzb;
        if (zzfkVar == null) {
            zzad();
            zzj().zzc().zza("Failed to get consents; not connected to service yet.");
            return null;
        }
        zzo zzb = zzb(false);
        Preconditions.checkNotNull(zzb);
        try {
            zzam zza = zzfkVar.zza(zzb);
            zzal();
            return zza;
        } catch (RemoteException e) {
            zzj().zzg().zza("Failed to get consents; remote exception", e);
            return null;
        }
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

    final Boolean zzab() {
        return this.zzc;
    }

    static /* synthetic */ void zzd(zzkp zzkpVar) {
        zzkpVar.zzt();
        if (zzkpVar.zzah()) {
            zzkpVar.zzj().zzp().zza("Inactivity, disconnecting from the service");
            zzkpVar.zzae();
        }
    }

    static /* synthetic */ void zza(zzkp zzkpVar, ComponentName componentName) {
        zzkpVar.zzt();
        if (zzkpVar.zzb != null) {
            zzkpVar.zzb = null;
            zzkpVar.zzj().zzp().zza("Disconnected from device MeasurementService", componentName);
            zzkpVar.zzt();
            zzkpVar.zzad();
        }
    }

    protected zzkp(zzhf zzhfVar) {
        super(zzhfVar);
        this.zzf = new ArrayList();
        this.zze = new zzmi(zzhfVar.zzb());
        this.zza = new zzlm(this);
        this.zzd = new zzks(this, zzhfVar);
        this.zzg = new zzlb(this, zzhfVar);
    }

    protected final void zzac() {
        zzt();
        zzu();
        zzo zzb = zzb(true);
        zzh().zzab();
        zza(new zzla(this, zzb));
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

    final void zzad() {
        zzt();
        zzu();
        if (zzah()) {
            return;
        }
        if (zzam()) {
            this.zza.zza();
            return;
        }
        if (zze().zzw()) {
            return;
        }
        List<ResolveInfo> queryIntentServices = zza().getPackageManager().queryIntentServices(new Intent().setClassName(zza(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if ((queryIntentServices == null || queryIntentServices.isEmpty()) ? false : true) {
            Intent intent = new Intent("com.google.android.gms.measurement.START");
            intent.setComponent(new ComponentName(zza(), "com.google.android.gms.measurement.AppMeasurementService"));
            this.zza.zza(intent);
            return;
        }
        zzj().zzg().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
    }

    public final void zzae() {
        zzt();
        zzu();
        this.zza.zzb();
        try {
            ConnectionTracker.getInstance().unbindService(zza(), this.zza);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzb = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzak() {
        zzt();
        zzj().zzp().zza("Processing queued up service tasks", Integer.valueOf(this.zzf.size()));
        Iterator<Runnable> it = this.zzf.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (RuntimeException e) {
                zzj().zzg().zza("Task exception while flushing queue", e);
            }
        }
        this.zzf.clear();
        this.zzg.zza();
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        zzt();
        zzu();
        zza(new zzkx(this, zzb(false), zzcvVar));
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzt();
        zzu();
        zza(new zzky(this, atomicReference, zzb(false)));
    }

    protected final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2) {
        zzt();
        zzu();
        zza(new zzlk(this, str, str2, zzb(false), zzcvVar));
    }

    protected final void zza(AtomicReference<List<zzad>> atomicReference, String str, String str2, String str3) {
        zzt();
        zzu();
        zza(new zzlh(this, atomicReference, str, str2, str3, zzb(false)));
    }

    protected final void zza(AtomicReference<List<zzmh>> atomicReference, Bundle bundle) {
        zzt();
        zzu();
        zza(new zzkt(this, atomicReference, zzb(false), bundle));
    }

    protected final void zza(AtomicReference<List<zznc>> atomicReference, boolean z) {
        zzt();
        zzu();
        zza(new zzku(this, atomicReference, zzb(false), z));
    }

    protected final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2, boolean z) {
        zzt();
        zzu();
        zza(new zzkr(this, str, str2, zzb(false), z, zzcvVar));
    }

    protected final void zza(AtomicReference<List<zznc>> atomicReference, String str, String str2, String str3, boolean z) {
        zzt();
        zzu();
        zza(new zzlj(this, atomicReference, str, str2, str3, zzb(false), z));
    }

    protected final void zza(zzbg zzbgVar, String str) {
        Preconditions.checkNotNull(zzbgVar);
        zzt();
        zzu();
        zza(new zzlf(this, true, zzb(true), zzh().zza(zzbgVar), zzbgVar, str));
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, zzbg zzbgVar, String str) {
        zzt();
        zzu();
        if (zzq().zza(GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE) != 0) {
            zzj().zzu().zza("Not bundling data. Service unavailable or out of date");
            zzq().zza(zzcvVar, new byte[0]);
        } else {
            zza(new zzle(this, zzbgVar, str, zzcvVar));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzal() {
        zzt();
        this.zze.zzb();
        this.zzd.zza(zzbi.zzaj.zza(null).longValue());
    }

    protected final void zzaf() {
        zzt();
        zzu();
        zzo zzb = zzb(false);
        zzh().zzaa();
        zza(new zzkv(this, zzb));
    }

    private final void zza(Runnable runnable) throws IllegalStateException {
        zzt();
        if (zzah()) {
            runnable.run();
        } else {
            if (this.zzf.size() >= 1000) {
                zzj().zzg().zza("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzf.add(runnable);
            this.zzg.zza(DateUtils.MILLIS_PER_MINUTE);
            zzad();
        }
    }

    final void zza(zzfk zzfkVar, AbstractSafeParcelable abstractSafeParcelable, zzo zzoVar) {
        int i;
        zzt();
        zzu();
        int i2 = 0;
        int i3 = 100;
        while (i2 < 1001 && i3 == 100) {
            ArrayList arrayList = new ArrayList();
            List<AbstractSafeParcelable> zza = zzh().zza(100);
            if (zza != null) {
                arrayList.addAll(zza);
                i = zza.size();
            } else {
                i = 0;
            }
            if (abstractSafeParcelable != null && i < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList.get(i4);
                i4++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzbg) {
                    try {
                        zzfkVar.zza((zzbg) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e) {
                        zzj().zzg().zza("Failed to send event to the service", e);
                    }
                } else if (abstractSafeParcelable2 instanceof zznc) {
                    try {
                        zzfkVar.zza((zznc) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e2) {
                        zzj().zzg().zza("Failed to send user property to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzad) {
                    try {
                        zzfkVar.zza((zzad) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e3) {
                        zzj().zzg().zza("Failed to send conditional user property to the service", e3);
                    }
                } else {
                    zzj().zzg().zza("Discarding data. Unrecognized parcel type.");
                }
            }
            i2++;
            i3 = i;
        }
    }

    protected final void zza(zzad zzadVar) {
        Preconditions.checkNotNull(zzadVar);
        zzt();
        zzu();
        zza(new zzli(this, true, zzb(true), zzh().zza(zzadVar), new zzad(zzadVar), zzadVar));
    }

    protected final void zza(boolean z) {
        zzt();
        zzu();
        if (z) {
            zzh().zzaa();
        }
        if (zzaj()) {
            zza(new zzlg(this, zzb(false)));
        }
    }

    protected final void zza(zzki zzkiVar) {
        zzt();
        zzu();
        zza(new zzkz(this, zzkiVar));
    }

    public final void zza(Bundle bundle) {
        zzt();
        zzu();
        zza(new zzlc(this, zzb(false), bundle));
    }

    protected final void zzag() {
        zzt();
        zzu();
        zza(new zzld(this, zzb(true)));
    }

    protected final void zza(zzfk zzfkVar) {
        zzt();
        Preconditions.checkNotNull(zzfkVar);
        this.zzb = zzfkVar;
        zzal();
        zzak();
    }

    protected final void zza(zznc zzncVar) {
        zzt();
        zzu();
        zza(new zzkw(this, zzb(true), zzh().zza(zzncVar), zzncVar));
    }

    public final boolean zzah() {
        zzt();
        zzu();
        return this.zzb != null;
    }

    final boolean zzai() {
        zzt();
        zzu();
        return !zzam() || zzq().zzg() >= 200900;
    }

    final boolean zzaj() {
        zzt();
        zzu();
        return !zzam() || zzq().zzg() >= zzbi.zzbo.zza(null).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzam() {
        /*
            Method dump skipped, instructions count: 264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkp.zzam():boolean");
    }
}
