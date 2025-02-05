package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzih {
    public static final zzih zza = new zzih(null, null, 100);
    private final EnumMap<zza, Boolean> zzb;
    private final int zzc;

    static String zza(int i) {
        return i != -20 ? i != -10 ? i != 0 ? i != 30 ? i != 90 ? i != 100 ? "OTHER" : "UNKNOWN" : "REMOTE_CONFIG" : "1P_INIT" : "1P_API" : "MANIFEST" : "API";
    }

    static String zza(boolean z) {
        return z ? "granted" : "denied";
    }

    public static boolean zza(int i, int i2) {
        return i <= i2;
    }

    static char zza(Boolean bool) {
        if (bool == null) {
            return '-';
        }
        return bool.booleanValue() ? '1' : '0';
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
    public enum zza {
        AD_STORAGE("ad_storage"),
        ANALYTICS_STORAGE("analytics_storage"),
        AD_USER_DATA("ad_user_data"),
        AD_PERSONALIZATION("ad_personalization");

        public final String zze;

        zza(String str) {
            this.zze = str;
        }
    }

    private static int zzb(Boolean bool) {
        if (bool == null) {
            return 0;
        }
        return bool.booleanValue() ? 1 : 2;
    }

    public final int zza() {
        return this.zzc;
    }

    public final int hashCode() {
        int i = this.zzc * 17;
        Iterator<Boolean> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            i = (i * 31) + zzb(it.next());
        }
        return i;
    }

    public final Bundle zzb() {
        Bundle bundle = new Bundle();
        for (Map.Entry<zza, Boolean> entry : this.zzb.entrySet()) {
            Boolean value = entry.getValue();
            if (value != null) {
                bundle.putString(entry.getKey().zze, zza(value.booleanValue()));
            }
        }
        return bundle;
    }

    public static zzih zza(Bundle bundle, int i) {
        zza[] zzaVarArr;
        if (bundle == null) {
            return new zzih(null, null, i);
        }
        EnumMap enumMap = new EnumMap(zza.class);
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            enumMap.put((EnumMap) zzaVar, (zza) zzb(bundle.getString(zzaVar.zze)));
        }
        return new zzih(enumMap, i);
    }

    public static zzih zza(String str) {
        return zza(str, 100);
    }

    public static zzih zza(String str, int i) {
        EnumMap enumMap = new EnumMap(zza.class);
        if (str != null) {
            zza[] zza2 = zzig.STORAGE.zza();
            for (int i2 = 0; i2 < zza2.length; i2++) {
                zza zzaVar = zza2[i2];
                int i3 = i2 + 2;
                if (i3 < str.length()) {
                    enumMap.put((EnumMap) zzaVar, (zza) zza(str.charAt(i3)));
                }
            }
        }
        return new zzih(enumMap, i);
    }

    public final zzih zza(zzih zzihVar) {
        zza[] zzaVarArr;
        EnumMap enumMap = new EnumMap(zza.class);
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            Boolean bool = this.zzb.get(zzaVar);
            Boolean bool2 = zzihVar.zzb.get(zzaVar);
            if (bool == null) {
                bool = bool2;
            } else if (bool2 != null) {
                bool = Boolean.valueOf(bool.booleanValue() && bool2.booleanValue());
            }
            enumMap.put((EnumMap) zzaVar, (zza) bool);
        }
        return new zzih(enumMap, 100);
    }

    public final zzih zzb(zzih zzihVar) {
        zza[] zzaVarArr;
        EnumMap enumMap = new EnumMap(zza.class);
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            Boolean bool = this.zzb.get(zzaVar);
            if (bool == null) {
                bool = zzihVar.zzb.get(zzaVar);
            }
            enumMap.put((EnumMap) zzaVar, (zza) bool);
        }
        return new zzih(enumMap, this.zzc);
    }

    static Boolean zzb(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("granted")) {
            return Boolean.TRUE;
        }
        if (str.equals("denied")) {
            return Boolean.FALSE;
        }
        return null;
    }

    static Boolean zza(char c) {
        if (c == '0') {
            return Boolean.FALSE;
        }
        if (c != '1') {
            return null;
        }
        return Boolean.TRUE;
    }

    public final Boolean zzc() {
        return this.zzb.get(zza.AD_STORAGE);
    }

    public final Boolean zzd() {
        return this.zzb.get(zza.ANALYTICS_STORAGE);
    }

    public static String zza(Bundle bundle) {
        zza[] zzaVarArr;
        String string;
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            if (bundle.containsKey(zzaVar.zze) && (string = bundle.getString(zzaVar.zze)) != null && zzb(string) == null) {
                return string;
            }
        }
        return null;
    }

    public final String zze() {
        StringBuilder sb = new StringBuilder("G1");
        for (zza zzaVar : zzig.STORAGE.zza()) {
            sb.append(zza(this.zzb.get(zzaVar)));
        }
        return sb.toString();
    }

    public final String zzf() {
        char c;
        StringBuilder sb = new StringBuilder("G2");
        for (zza zzaVar : zzig.STORAGE.zza()) {
            Boolean bool = this.zzb.get(zzaVar);
            if (bool == null) {
                c = 'g';
            } else {
                c = bool.booleanValue() ? 'G' : 'D';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public final String toString() {
        zza[] zzaVarArr;
        StringBuilder sb = new StringBuilder("source=");
        sb.append(zza(this.zzc));
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            sb.append(",");
            sb.append(zzaVar.zze);
            sb.append("=");
            Boolean bool = this.zzb.get(zzaVar);
            if (bool == null) {
                sb.append("uninitialized");
            } else {
                sb.append(bool.booleanValue() ? "granted" : "denied");
            }
        }
        return sb.toString();
    }

    private zzih(EnumMap<zza, Boolean> enumMap, int i) {
        EnumMap<zza, Boolean> enumMap2 = new EnumMap<>((Class<zza>) zza.class);
        this.zzb = enumMap2;
        enumMap2.putAll(enumMap);
        this.zzc = i;
    }

    public zzih(Boolean bool, Boolean bool2, int i) {
        EnumMap<zza, Boolean> enumMap = new EnumMap<>((Class<zza>) zza.class);
        this.zzb = enumMap;
        enumMap.put((EnumMap<zza, Boolean>) zza.AD_STORAGE, (zza) bool);
        enumMap.put((EnumMap<zza, Boolean>) zza.ANALYTICS_STORAGE, (zza) bool2);
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        zza[] zzaVarArr;
        if (!(obj instanceof zzih)) {
            return false;
        }
        zzih zzihVar = (zzih) obj;
        zzaVarArr = zzig.STORAGE.zzd;
        for (zza zzaVar : zzaVarArr) {
            if (zzb(this.zzb.get(zzaVar)) != zzb(zzihVar.zzb.get(zzaVar))) {
                return false;
            }
        }
        return this.zzc == zzihVar.zzc;
    }

    public final boolean zza(zzih zzihVar, zza... zzaVarArr) {
        for (zza zzaVar : zzaVarArr) {
            if (!zzihVar.zza(zzaVar) && zza(zzaVar)) {
                return true;
            }
        }
        return false;
    }

    public final boolean zzg() {
        return zza(zza.AD_STORAGE);
    }

    public final boolean zza(zza zzaVar) {
        Boolean bool = this.zzb.get(zzaVar);
        return bool == null || bool.booleanValue();
    }

    public final boolean zzh() {
        return zza(zza.ANALYTICS_STORAGE);
    }

    public final boolean zzi() {
        Iterator<Boolean> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean zzc(zzih zzihVar) {
        return zzb(zzihVar, (zza[]) this.zzb.keySet().toArray(new zza[0]));
    }

    public final boolean zzb(zzih zzihVar, zza... zzaVarArr) {
        for (zza zzaVar : zzaVarArr) {
            Boolean bool = this.zzb.get(zzaVar);
            Boolean bool2 = zzihVar.zzb.get(zzaVar);
            if (bool == Boolean.FALSE && bool2 != Boolean.FALSE) {
                return true;
            }
        }
        return false;
    }
}
