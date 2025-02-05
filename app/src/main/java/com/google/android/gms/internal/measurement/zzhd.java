package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzhd;
import com.google.android.gms.internal.measurement.zzhf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
public abstract class zzhd<MessageType extends zzhd<MessageType, BuilderType>, BuilderType extends zzhf<MessageType, BuilderType>> implements zzkj {
    protected int zza = 0;

    int zzbt() {
        throw new UnsupportedOperationException();
    }

    int zza(zzlb zzlbVar) {
        int zzbt = zzbt();
        if (zzbt != -1) {
            return zzbt;
        }
        int zza = zzlbVar.zza(this);
        zzc(zza);
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzkj
    public final zzhm zzbu() {
        try {
            zzhv zzc = zzhm.zzc(zzbw());
            zza(zzc.zzb());
            return zzc.zza();
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a ByteString threw an IOException (should never happen).", e);
        }
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zziz.zza(iterable);
        if (iterable instanceof zzjp) {
            List<?> zzb = ((zzjp) iterable).zzb();
            zzjp zzjpVar = (zzjp) list;
            int size = list.size();
            for (Object obj : zzb) {
                if (obj == null) {
                    String str = "Element at index " + (zzjpVar.size() - size) + " is null.";
                    for (int size2 = zzjpVar.size() - 1; size2 >= size; size2--) {
                        zzjpVar.remove(size2);
                    }
                    throw new NullPointerException(str);
                }
                if (obj instanceof zzhm) {
                    zzjpVar.zza((zzhm) obj);
                } else {
                    zzjpVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzkv) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size3 = list.size();
        for (T t : iterable) {
            if (t == null) {
                String str2 = "Element at index " + (list.size() - size3) + " is null.";
                for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                    list.remove(size4);
                }
                throw new NullPointerException(str2);
            }
            list.add(t);
        }
    }

    void zzc(int i) {
        throw new UnsupportedOperationException();
    }

    public final byte[] zzbv() {
        try {
            byte[] bArr = new byte[zzbw()];
            zzig zzb = zzig.zzb(bArr);
            zza(zzb);
            zzb.zzb();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a byte array threw an IOException (should never happen).", e);
        }
    }
}
