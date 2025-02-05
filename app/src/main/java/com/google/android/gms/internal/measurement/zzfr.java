package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public class zzfr {
    private static HashMap<String, String> zzh;
    private static Object zzm;
    private static boolean zzn;
    public static final Uri zza = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzd = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzb = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzc = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zze = new AtomicBoolean();
    private static ContentResolver zzf = null;
    private static zzb zzg = null;
    private static final HashMap<String, Boolean> zzi = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Integer> zzj = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Long> zzk = new HashMap<>(16, 1.0f);
    private static final HashMap<String, Float> zzl = new HashMap<>(16, 1.0f);
    private static String[] zzo = new String[0];

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
    public interface zza<T extends Map<String, String>> {
        T zza(int i);
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
    public interface zzb {
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzfr.class) {
            if (zzh == null) {
                zze.set(false);
                zzh = new HashMap<>(16, 1.0f);
                zzm = new Object();
                zzn = false;
                contentResolver.registerContentObserver(zza, true, new zzfu(null));
            } else if (zze.getAndSet(false)) {
                zzh.clear();
                zzi.clear();
                zzj.clear();
                zzk.clear();
                zzl.clear();
                zzm = new Object();
                zzn = false;
            }
            Object obj = zzm;
            if (zzh.containsKey(str)) {
                String str3 = zzh.get(str);
                return str3 != null ? str3 : null;
            }
            for (String str4 : zzo) {
                if (str.startsWith(str4)) {
                    if (!zzn) {
                        HashMap<String, String> hashMap = (HashMap) zza(contentResolver, zzo, new zzft());
                        if (hashMap != null) {
                            if (!hashMap.isEmpty()) {
                                Set<String> keySet = hashMap.keySet();
                                keySet.removeAll(zzi.keySet());
                                keySet.removeAll(zzj.keySet());
                                keySet.removeAll(zzk.keySet());
                                keySet.removeAll(zzl.keySet());
                            }
                            if (!hashMap.isEmpty()) {
                                if (zzh.isEmpty()) {
                                    zzh = hashMap;
                                } else {
                                    zzh.putAll(hashMap);
                                }
                            }
                            zzn = true;
                        }
                        if (zzh.containsKey(str)) {
                            String str5 = zzh.get(str);
                            return str5 != null ? str5 : null;
                        }
                    }
                    return null;
                }
            }
            Cursor query = contentResolver.query(zza, null, null, new String[]{str}, null);
            if (query == null) {
                return null;
            }
            try {
                if (!query.moveToFirst()) {
                    zza(obj, str, (String) null);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                String string = query.getString(1);
                if (query != null) {
                    query.close();
                }
                if (string != null && string.equals(null)) {
                    string = null;
                }
                zza(obj, str, string);
                if (string != null) {
                    return string;
                }
                return null;
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
    }

    private static <T extends Map<String, String>> T zza(ContentResolver contentResolver, String[] strArr, zza<T> zzaVar) {
        Cursor query = contentResolver.query(zzd, null, null, strArr, null);
        if (query == null) {
            return null;
        }
        T zza2 = zzaVar.zza(query.getCount());
        while (query.moveToNext()) {
            try {
                zza2.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return zza2;
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzfr.class) {
            if (obj == zzm) {
                zzh.put(str, str2);
            }
        }
    }
}
