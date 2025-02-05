package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkp<T> implements zzlb<T> {
    private final zzkj zza;
    private final zzma<?, ?> zzb;
    private final boolean zzc;
    private final zzim<?> zzd;

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final int zza(T t) {
        zzma<?, ?> zzmaVar = this.zzb;
        int zzb = zzmaVar.zzb(zzmaVar.zzd(t)) + 0;
        return this.zzc ? zzb + this.zzd.zza(t).zza() : zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final int zzb(T t) {
        int hashCode = this.zzb.zzd(t).hashCode();
        return this.zzc ? (hashCode * 53) + this.zzd.zza(t).hashCode() : hashCode;
    }

    static <T> zzkp<T> zza(zzma<?, ?> zzmaVar, zzim<?> zzimVar, zzkj zzkjVar) {
        return new zzkp<>(zzmaVar, zzimVar, zzkjVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final T zza() {
        zzkj zzkjVar = this.zza;
        if (zzkjVar instanceof zzix) {
            return (T) ((zzix) zzkjVar).zzbz();
        }
        return (T) zzkjVar.zzcd().zzac();
    }

    private zzkp(zzma<?, ?> zzmaVar, zzim<?> zzimVar, zzkj zzkjVar) {
        this.zzb = zzmaVar;
        this.zzc = zzimVar.zza(zzkjVar);
        this.zzd = zzimVar;
        this.zza = zzkjVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zzc(T t) {
        this.zzb.zzf(t);
        this.zzd.zzc(t);
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zza(T t, T t2) {
        zzld.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzld.zza(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zza(T t, zzlc zzlcVar, zzik zzikVar) throws IOException {
        boolean z;
        zzma<?, ?> zzmaVar = this.zzb;
        zzim<?> zzimVar = this.zzd;
        Object zzc = zzmaVar.zzc(t);
        zziq<?> zzb = zzimVar.zzb(t);
        do {
            try {
                if (zzlcVar.zzc() == Integer.MAX_VALUE) {
                    return;
                }
                int zzd = zzlcVar.zzd();
                if (zzd == 11) {
                    int i = 0;
                    Object obj = null;
                    zzhm zzhmVar = null;
                    while (zzlcVar.zzc() != Integer.MAX_VALUE) {
                        int zzd2 = zzlcVar.zzd();
                        if (zzd2 == 16) {
                            i = zzlcVar.zzj();
                            obj = zzimVar.zza(zzikVar, this.zza, i);
                        } else if (zzd2 == 26) {
                            if (obj != null) {
                                zzimVar.zza(zzlcVar, obj, zzikVar, zzb);
                            } else {
                                zzhmVar = zzlcVar.zzp();
                            }
                        } else if (!zzlcVar.zzt()) {
                            break;
                        }
                    }
                    if (zzlcVar.zzd() != 12) {
                        throw zzji.zzb();
                    }
                    if (zzhmVar != null) {
                        if (obj != null) {
                            zzimVar.zza(zzhmVar, obj, zzikVar, zzb);
                        } else {
                            zzmaVar.zza((zzma<?, ?>) zzc, i, zzhmVar);
                        }
                    }
                } else if ((zzd & 7) == 2) {
                    Object zza = zzimVar.zza(zzikVar, this.zza, zzd >>> 3);
                    if (zza != null) {
                        zzimVar.zza(zzlcVar, zza, zzikVar, zzb);
                    } else {
                        z = zzmaVar.zza((zzma<?, ?>) zzc, zzlcVar);
                    }
                } else {
                    z = zzlcVar.zzt();
                }
                z = true;
            } finally {
                zzmaVar.zzb((Object) t, (T) zzc);
            }
        } while (z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0099 A[EDGE_INSN: B:24:0x0099->B:25:0x0099 BREAK  A[LOOP:1: B:10:0x0053->B:18:0x0053], SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzlb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.measurement.zzhl r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.measurement.zzix r0 = (com.google.android.gms.internal.measurement.zzix) r0
            com.google.android.gms.internal.measurement.zzlz r1 = r0.zzb
            com.google.android.gms.internal.measurement.zzlz r2 = com.google.android.gms.internal.measurement.zzlz.zzc()
            if (r1 != r2) goto L11
            com.google.android.gms.internal.measurement.zzlz r1 = com.google.android.gms.internal.measurement.zzlz.zzd()
            r0.zzb = r1
        L11:
            com.google.android.gms.internal.measurement.zzix$zzd r10 = (com.google.android.gms.internal.measurement.zzix.zzd) r10
            r10.zza()
            r10 = 0
            r0 = r10
        L18:
            if (r12 >= r13) goto La4
            int r4 = com.google.android.gms.internal.measurement.zzhi.zzc(r11, r12, r14)
            int r2 = r14.zza
            r12 = 11
            r3 = 2
            if (r2 == r12) goto L51
            r12 = r2 & 7
            if (r12 != r3) goto L4c
            com.google.android.gms.internal.measurement.zzim<?> r12 = r9.zzd
            com.google.android.gms.internal.measurement.zzik r0 = r14.zzd
            com.google.android.gms.internal.measurement.zzkj r3 = r9.zza
            int r5 = r2 >>> 3
            java.lang.Object r12 = r12.zza(r0, r3, r5)
            r0 = r12
            com.google.android.gms.internal.measurement.zzix$zzf r0 = (com.google.android.gms.internal.measurement.zzix.zzf) r0
            if (r0 != 0) goto L43
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.measurement.zzhi.zza(r2, r3, r4, r5, r6, r7)
            goto L18
        L43:
            com.google.android.gms.internal.measurement.zzkx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L4c:
            int r12 = com.google.android.gms.internal.measurement.zzhi.zza(r2, r11, r4, r13, r14)
            goto L18
        L51:
            r12 = 0
            r2 = r10
        L53:
            if (r4 >= r13) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzhi.zzc(r11, r4, r14)
            int r5 = r14.zza
            int r6 = r5 >>> 3
            r7 = r5 & 7
            if (r6 == r3) goto L7b
            r8 = 3
            if (r6 == r8) goto L65
            goto L90
        L65:
            if (r0 != 0) goto L72
            if (r7 != r3) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzhi.zza(r11, r4, r14)
            java.lang.Object r2 = r14.zzc
            com.google.android.gms.internal.measurement.zzhm r2 = (com.google.android.gms.internal.measurement.zzhm) r2
            goto L53
        L72:
            com.google.android.gms.internal.measurement.zzkx.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L7b:
            if (r7 != 0) goto L90
            int r4 = com.google.android.gms.internal.measurement.zzhi.zzc(r11, r4, r14)
            int r12 = r14.zza
            com.google.android.gms.internal.measurement.zzim<?> r0 = r9.zzd
            com.google.android.gms.internal.measurement.zzik r5 = r14.zzd
            com.google.android.gms.internal.measurement.zzkj r6 = r9.zza
            java.lang.Object r0 = r0.zza(r5, r6, r12)
            com.google.android.gms.internal.measurement.zzix$zzf r0 = (com.google.android.gms.internal.measurement.zzix.zzf) r0
            goto L53
        L90:
            r6 = 12
            if (r5 == r6) goto L99
            int r4 = com.google.android.gms.internal.measurement.zzhi.zza(r5, r11, r4, r13, r14)
            goto L53
        L99:
            if (r2 == 0) goto La1
            int r12 = r12 << 3
            r12 = r12 | r3
            r1.zza(r12, r2)
        La1:
            r12 = r4
            goto L18
        La4:
            if (r12 != r13) goto La7
            return
        La7:
            com.google.android.gms.internal.measurement.zzji r10 = com.google.android.gms.internal.measurement.zzji.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkp.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzhl):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zza(T t, zzmw zzmwVar) throws IOException {
        Iterator<Map.Entry<?, Object>> zzd = this.zzd.zza(t).zzd();
        while (zzd.hasNext()) {
            Map.Entry<?, Object> next = zzd.next();
            zzis zzisVar = (zzis) next.getKey();
            if (zzisVar.zzc() != zzmx.MESSAGE || zzisVar.zze() || zzisVar.zzd()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (next instanceof zzjm) {
                zzmwVar.zza(zzisVar.zza(), (Object) ((zzjm) next).zza().zzc());
            } else {
                zzmwVar.zza(zzisVar.zza(), next.getValue());
            }
        }
        zzma<?, ?> zzmaVar = this.zzb;
        zzmaVar.zza((zzma<?, ?>) zzmaVar.zzd(t), zzmwVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final boolean zzb(T t, T t2) {
        if (!this.zzb.zzd(t).equals(this.zzb.zzd(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza(t).equals(this.zzd.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final boolean zzd(T t) {
        return this.zzd.zza(t).zzg();
    }
}
