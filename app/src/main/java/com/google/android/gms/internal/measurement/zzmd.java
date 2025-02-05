package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzmd implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zzmb zzb;

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zza.next();
    }

    zzmd(zzmb zzmbVar) {
        zzjp zzjpVar;
        this.zzb = zzmbVar;
        zzjpVar = zzmbVar.zza;
        this.zza = zzjpVar.iterator();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }
}
