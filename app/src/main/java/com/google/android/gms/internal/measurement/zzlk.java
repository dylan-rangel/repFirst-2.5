package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzlk extends zzls {
    private final /* synthetic */ zzlg zza;

    @Override // com.google.android.gms.internal.measurement.zzls, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzli(this.zza);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzlk(zzlg zzlgVar) {
        super(zzlgVar);
        this.zza = zzlgVar;
    }
}
