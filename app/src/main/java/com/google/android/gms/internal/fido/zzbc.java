package com.google.android.gms.internal.fido;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-fido@@20.0.1 */
/* loaded from: classes2.dex */
final class zzbc extends zzbe {
    final char[] zza;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    zzbc(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.gms.internal.fido.zzbb r4 = new com.google.android.gms.internal.fido.zzbb
            java.lang.String r5 = "0123456789ABCDEF"
            char[] r5 = r5.toCharArray()
            java.lang.String r0 = "base16()"
            r4.<init>(r0, r5)
            r5 = 0
            r3.<init>(r4, r5)
            r5 = 512(0x200, float:7.17E-43)
            char[] r5 = new char[r5]
            r3.zza = r5
            char[] r5 = com.google.android.gms.internal.fido.zzbb.zzc(r4)
            int r5 = r5.length
            r0 = 0
            r1 = 16
            if (r5 != r1) goto L23
            r5 = 1
            goto L24
        L23:
            r5 = 0
        L24:
            com.google.android.gms.internal.fido.zzam.zzc(r5)
        L27:
            r5 = 256(0x100, float:3.59E-43)
            if (r0 >= r5) goto L44
            char[] r5 = r3.zza
            int r1 = r0 >>> 4
            char r1 = r4.zza(r1)
            r5[r0] = r1
            char[] r5 = r3.zza
            r1 = r0 | 256(0x100, float:3.59E-43)
            r2 = r0 & 15
            char r2 = r4.zza(r2)
            r5[r1] = r2
            int r0 = r0 + 1
            goto L27
        L44:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.fido.zzbc.<init>(java.lang.String, java.lang.String):void");
    }

    @Override // com.google.android.gms.internal.fido.zzbe, com.google.android.gms.internal.fido.zzbf
    final void zza(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
        zzam.zze(0, i2, bArr.length);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = bArr[i3] & 255;
            appendable.append(this.zza[i4]);
            appendable.append(this.zza[i4 | 256]);
        }
    }
}
