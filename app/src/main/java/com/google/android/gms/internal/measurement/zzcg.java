package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzcg extends zzcd {
    @Override // com.google.android.gms.internal.measurement.zzcd
    public final URLConnection zza(URL url, String str) throws IOException {
        return url.openConnection();
    }

    private zzcg() {
    }
}
