package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzix;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
public class zzik {
    static final zzik zza = new zzik(true);
    private static volatile boolean zzb = false;
    private static boolean zzc = true;
    private static volatile zzik zzd;
    private final Map<zza, zzix.zzf<?, ?>> zze;

    /* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
    private static final class zza {
        private final Object zza;
        private final int zzb;

        public final int hashCode() {
            return (System.identityHashCode(this.zza) * 65535) + this.zzb;
        }

        zza(Object obj, int i) {
            this.zza = obj;
            this.zzb = i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.zza == zzaVar.zza && this.zzb == zzaVar.zzb;
        }
    }

    public static zzik zza() {
        zzik zzikVar = zzd;
        if (zzikVar != null) {
            return zzikVar;
        }
        synchronized (zzik.class) {
            zzik zzikVar2 = zzd;
            if (zzikVar2 != null) {
                return zzikVar2;
            }
            zzik zza2 = zziv.zza(zzik.class);
            zzd = zza2;
            return zza2;
        }
    }

    public final <ContainingType extends zzkj> zzix.zzf<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzix.zzf) this.zze.get(new zza(containingtype, i));
    }

    zzik() {
        this.zze = new HashMap();
    }

    private zzik(boolean z) {
        this.zze = Collections.emptyMap();
    }
}
