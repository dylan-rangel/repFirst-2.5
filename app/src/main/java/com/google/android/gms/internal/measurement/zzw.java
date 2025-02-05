package com.google.android.gms.internal.measurement;

import io.sentry.protocol.SentryThread;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzw extends zzal {
    private zzaa zzk;

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zza(this.zza, 3, list);
        String zzf = zzhVar.zza(list.get(0)).zzf();
        zzaq zza = zzhVar.zza(list.get(1));
        if (!(zza instanceof zzar)) {
            throw new IllegalArgumentException("Invalid callback type");
        }
        zzaq zza2 = zzhVar.zza(list.get(2));
        if (!(zza2 instanceof zzap)) {
            throw new IllegalArgumentException("Invalid callback params");
        }
        zzap zzapVar = (zzap) zza2;
        if (!zzapVar.zzc("type")) {
            throw new IllegalArgumentException("Undefined rule type");
        }
        this.zzk.zza(zzf, zzapVar.zzc(SentryThread.JsonKeys.PRIORITY) ? zzg.zzb(zzapVar.zza(SentryThread.JsonKeys.PRIORITY).zze().doubleValue()) : 1000, (zzar) zza, zzapVar.zza("type").zzf());
        return zzaq.zzc;
    }

    public zzw(zzaa zzaaVar) {
        super("internal.registerCallback");
        this.zzk = zzaaVar;
    }
}
