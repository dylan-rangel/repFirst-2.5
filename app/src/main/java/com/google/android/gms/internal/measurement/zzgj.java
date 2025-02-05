package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import com.google.common.base.Optional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzgj {
    private static zzgh zza(Context context, File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
                HashMap hashMap = new HashMap();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        Log.w("HermeticFileOverrides", "Parsed " + String.valueOf(file) + " for Android package " + context.getPackageName());
                        zzgc zzgcVar = new zzgc(simpleArrayMap);
                        bufferedReader.close();
                        return zzgcVar;
                    }
                    String[] split = readLine.split(StringUtils.SPACE, 3);
                    if (split.length != 3) {
                        Log.e("HermeticFileOverrides", "Invalid: " + readLine);
                    } else {
                        String zza2 = zza(split[0]);
                        String decode = Uri.decode(zza(split[1]));
                        String str = (String) hashMap.get(split[2]);
                        if (str == null) {
                            String zza3 = zza(split[2]);
                            str = Uri.decode(zza3);
                            if (str.length() < 1024 || str == zza3) {
                                hashMap.put(zza3, str);
                            }
                        }
                        if (!simpleArrayMap.containsKey(zza2)) {
                            simpleArrayMap.put(zza2, new SimpleArrayMap());
                        }
                        ((SimpleArrayMap) simpleArrayMap.get(zza2)).put(decode, str);
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    try {
                        Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class).invoke(th, th2);
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
    public static class zza {
        private static volatile Optional<zzgh> zza;

        /* JADX WARN: Removed duplicated region for block: B:12:0x0036 A[Catch: all -> 0x0056, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0018, B:12:0x0036, B:14:0x0052, B:15:0x003c, B:17:0x0042, B:20:0x0049, B:21:0x004d, B:22:0x0020, B:24:0x0028, B:28:0x0054), top: B:3:0x0003 }] */
        /* JADX WARN: Removed duplicated region for block: B:15:0x003c A[Catch: all -> 0x0056, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0018, B:12:0x0036, B:14:0x0052, B:15:0x003c, B:17:0x0042, B:20:0x0049, B:21:0x004d, B:22:0x0020, B:24:0x0028, B:28:0x0054), top: B:3:0x0003 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.google.common.base.Optional<com.google.android.gms.internal.measurement.zzgh> zza(android.content.Context r4) {
            /*
                java.lang.Class<com.google.android.gms.internal.measurement.zzgj$zza> r0 = com.google.android.gms.internal.measurement.zzgj.zza.class
                monitor-enter(r0)
                com.google.common.base.Optional<com.google.android.gms.internal.measurement.zzgh> r1 = com.google.android.gms.internal.measurement.zzgj.zza.zza     // Catch: java.lang.Throwable -> L56
                if (r1 != 0) goto L54
                com.google.android.gms.internal.measurement.zzgj r1 = new com.google.android.gms.internal.measurement.zzgj     // Catch: java.lang.Throwable -> L56
                r1.<init>()     // Catch: java.lang.Throwable -> L56
                java.lang.String r1 = android.os.Build.TYPE     // Catch: java.lang.Throwable -> L56
                java.lang.String r2 = android.os.Build.TAGS     // Catch: java.lang.Throwable -> L56
                java.lang.String r3 = "eng"
                boolean r3 = r1.equals(r3)     // Catch: java.lang.Throwable -> L56
                if (r3 != 0) goto L20
                java.lang.String r3 = "userdebug"
                boolean r1 = r1.equals(r3)     // Catch: java.lang.Throwable -> L56
                if (r1 == 0) goto L31
            L20:
                java.lang.String r1 = "dev-keys"
                boolean r1 = r2.contains(r1)     // Catch: java.lang.Throwable -> L56
                if (r1 != 0) goto L33
                java.lang.String r1 = "test-keys"
                boolean r1 = r2.contains(r1)     // Catch: java.lang.Throwable -> L56
                if (r1 == 0) goto L31
                goto L33
            L31:
                r1 = 0
                goto L34
            L33:
                r1 = 1
            L34:
                if (r1 != 0) goto L3c
                com.google.common.base.Optional r4 = com.google.common.base.Optional.absent()     // Catch: java.lang.Throwable -> L56
            L3a:
                r1 = r4
                goto L52
            L3c:
                boolean r1 = com.google.android.gms.internal.measurement.zzfw.zza()     // Catch: java.lang.Throwable -> L56
                if (r1 == 0) goto L4d
                boolean r1 = r4.isDeviceProtectedStorage()     // Catch: java.lang.Throwable -> L56
                if (r1 == 0) goto L49
                goto L4d
            L49:
                android.content.Context r4 = r4.createDeviceProtectedStorageContext()     // Catch: java.lang.Throwable -> L56
            L4d:
                com.google.common.base.Optional r4 = com.google.android.gms.internal.measurement.zzgj.zza(r4)     // Catch: java.lang.Throwable -> L56
                goto L3a
            L52:
                com.google.android.gms.internal.measurement.zzgj.zza.zza = r1     // Catch: java.lang.Throwable -> L56
            L54:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L56
                return r1
            L56:
                r4 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L56
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgj.zza.zza(android.content.Context):com.google.common.base.Optional");
        }

        private zza() {
        }
    }

    private static Optional<File> zzb(Context context) {
        try {
            File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
            return file.exists() ? Optional.of(file) : Optional.absent();
        } catch (RuntimeException e) {
            Log.e("HermeticFileOverrides", "no data dir", e);
            return Optional.absent();
        }
    }

    static Optional<zzgh> zza(Context context) {
        Optional<zzgh> absent;
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            StrictMode.allowThreadDiskWrites();
            Optional<File> zzb = zzb(context);
            if (zzb.isPresent()) {
                absent = Optional.of(zza(context, zzb.get()));
            } else {
                absent = Optional.absent();
            }
            return absent;
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    private static final String zza(String str) {
        return new String(str);
    }
}
