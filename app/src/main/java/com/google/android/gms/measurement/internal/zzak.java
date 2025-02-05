package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.internal.zzih;
import java.util.EnumMap;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzak {
    private final EnumMap<zzih.zza, zzaj> zza;

    public final zzaj zza(zzih.zza zzaVar) {
        zzaj zzajVar = this.zza.get(zzaVar);
        return zzajVar == null ? zzaj.UNSET : zzajVar;
    }

    public static zzak zza(String str) {
        EnumMap enumMap = new EnumMap(zzih.zza.class);
        if (str.length() >= zzih.zza.values().length) {
            int i = 0;
            if (str.charAt(0) == '1') {
                zzih.zza[] values = zzih.zza.values();
                int length = values.length;
                int i2 = 1;
                while (i < length) {
                    enumMap.put((EnumMap) values[i], (zzih.zza) zzaj.zza(str.charAt(i2)));
                    i++;
                    i2++;
                }
                return new zzak(enumMap);
            }
        }
        return new zzak();
    }

    public final String toString() {
        char c;
        StringBuilder sb = new StringBuilder("1");
        for (zzih.zza zzaVar : zzih.zza.values()) {
            zzaj zzajVar = this.zza.get(zzaVar);
            if (zzajVar == null) {
                zzajVar = zzaj.UNSET;
            }
            c = zzajVar.zzj;
            sb.append(c);
        }
        return sb.toString();
    }

    zzak() {
        this.zza = new EnumMap<>(zzih.zza.class);
    }

    private zzak(EnumMap<zzih.zza, zzaj> enumMap) {
        EnumMap<zzih.zza, zzaj> enumMap2 = new EnumMap<>((Class<zzih.zza>) zzih.zza.class);
        this.zza = enumMap2;
        enumMap2.putAll(enumMap);
    }

    public final void zza(zzih.zza zzaVar, int i) {
        zzaj zzajVar = zzaj.UNSET;
        if (i != -20) {
            if (i == -10) {
                zzajVar = zzaj.MANIFEST;
            } else if (i != 0) {
                if (i == 30) {
                    zzajVar = zzaj.INITIALIZATION;
                }
            }
            this.zza.put((EnumMap<zzih.zza, zzaj>) zzaVar, (zzih.zza) zzajVar);
        }
        zzajVar = zzaj.API;
        this.zza.put((EnumMap<zzih.zza, zzaj>) zzaVar, (zzih.zza) zzajVar);
    }

    public final void zza(zzih.zza zzaVar, zzaj zzajVar) {
        this.zza.put((EnumMap<zzih.zza, zzaj>) zzaVar, (zzih.zza) zzajVar);
    }
}
