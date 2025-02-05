package com.facebook.soloader;

import android.content.Context;
import android.os.Parcel;
import com.facebook.soloader.ExtractFromZipSoSource;
import com.facebook.soloader.UnpackingSoSource;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class ApkSoSource extends ExtractFromZipSoSource {
    private static final byte APK_SO_SOURCE_SIGNATURE_VERSION = 2;
    private static final byte LIBS_DIR_DOESNT_EXIST = 1;
    private static final byte LIBS_DIR_DONT_CARE = 0;
    private static final byte LIBS_DIR_SNAPSHOT = 2;
    public static final int PREFER_ANDROID_LIBS_DIRECTORY = 1;
    private static final String TAG = "ApkSoSource";
    private final int mFlags;

    public ApkSoSource(Context context, String str, int i) {
        this(context, new File(context.getApplicationInfo().sourceDir), str, i);
    }

    public ApkSoSource(Context context, File file, String str, int i) {
        super(context, str, file, "^lib/([^/]+)/([^/]+\\.so)$");
        this.mFlags = i;
    }

    @Override // com.facebook.soloader.ExtractFromZipSoSource, com.facebook.soloader.UnpackingSoSource
    protected UnpackingSoSource.Unpacker makeUnpacker(byte b) throws IOException {
        return new ApkUnpacker(this);
    }

    protected class ApkUnpacker extends ExtractFromZipSoSource.ZipUnpacker {
        private final int mFlags;
        private final File mLibDir;

        ApkUnpacker(ExtractFromZipSoSource extractFromZipSoSource) throws IOException {
            super(extractFromZipSoSource);
            this.mLibDir = new File(ApkSoSource.this.mContext.getApplicationInfo().nativeLibraryDir);
            this.mFlags = ApkSoSource.this.mFlags;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00cc  */
        @Override // com.facebook.soloader.ExtractFromZipSoSource.ZipUnpacker
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected boolean shouldExtract(java.util.zip.ZipEntry r10, java.lang.String r11) {
            /*
                r9 = this;
                java.lang.String r0 = r10.getName()
                com.facebook.soloader.ApkSoSource r1 = com.facebook.soloader.ApkSoSource.this
                java.lang.String r1 = r1.mCorruptedLib
                boolean r1 = r11.equals(r1)
                r2 = 0
                r3 = 1
                if (r1 == 0) goto L22
                com.facebook.soloader.ApkSoSource r10 = com.facebook.soloader.ApkSoSource.this
                r0 = 0
                r10.mCorruptedLib = r0
                java.lang.Object[] r10 = new java.lang.Object[r3]
                r10[r2] = r11
                java.lang.String r11 = "allowing consideration of corrupted lib %s"
                java.lang.String r10 = java.lang.String.format(r11, r10)
            L1f:
                r2 = 1
                goto Lcd
            L22:
                int r1 = r9.mFlags
                r1 = r1 & r3
                if (r1 != 0) goto L3e
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r11 = "allowing consideration of "
                r10.append(r11)
                r10.append(r0)
                java.lang.String r11 = ": self-extraction preferred"
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                goto L1f
            L3e:
                java.io.File r1 = new java.io.File
                java.io.File r4 = r9.mLibDir
                r1.<init>(r4, r11)
                r4 = 3
                r5 = 2
                java.lang.String r6 = r1.getCanonicalPath()     // Catch: java.io.IOException -> L68
                java.io.File r7 = r9.mLibDir     // Catch: java.io.IOException -> L68
                java.lang.String r7 = r7.getCanonicalPath()     // Catch: java.io.IOException -> L68
                boolean r6 = r6.startsWith(r7)     // Catch: java.io.IOException -> L68
                if (r6 != 0) goto L64
                java.lang.String r6 = "not allowing consideration of %s: %s not in lib dir"
                java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch: java.io.IOException -> L68
                r7[r2] = r0     // Catch: java.io.IOException -> L68
                r7[r3] = r11     // Catch: java.io.IOException -> L68
                java.lang.String r6 = java.lang.String.format(r6, r7)     // Catch: java.io.IOException -> L68
                goto L7b
            L64:
                java.lang.String r6 = ""
                r7 = 1
                goto L7c
            L68:
                r6 = move-exception
                java.lang.Object[] r7 = new java.lang.Object[r4]
                r7[r2] = r0
                r7[r3] = r11
                java.lang.String r6 = r6.toString()
                r7[r5] = r6
                java.lang.String r6 = "not allowing consideration of %s: %s, IOException when constructing path: %s"
                java.lang.String r6 = java.lang.String.format(r6, r7)
            L7b:
                r7 = 0
            L7c:
                if (r7 == 0) goto Lcc
                boolean r6 = r1.isFile()
                if (r6 != 0) goto L91
                java.lang.Object[] r10 = new java.lang.Object[r5]
                r10[r2] = r0
                r10[r3] = r11
                java.lang.String r11 = "allowing consideration of %s: %s not in system lib dir"
                java.lang.String r10 = java.lang.String.format(r11, r10)
                goto L1f
            L91:
                long r6 = r1.length()
                long r10 = r10.getSize()
                int r8 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
                if (r8 == 0) goto Lb5
                java.lang.Object[] r0 = new java.lang.Object[r4]
                r0[r2] = r1
                java.lang.Long r1 = java.lang.Long.valueOf(r6)
                r0[r3] = r1
                java.lang.Long r10 = java.lang.Long.valueOf(r10)
                r0[r5] = r10
                java.lang.String r10 = "allowing consideration of %s: sysdir file length is %s, but the file is %s bytes long in the APK"
                java.lang.String r10 = java.lang.String.format(r10, r0)
                goto L1f
            Lb5:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r11 = "not allowing consideration of "
                r10.append(r11)
                r10.append(r0)
                java.lang.String r11 = ": deferring to libdir"
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                goto Lcd
            Lcc:
                r10 = r6
            Lcd:
                java.lang.String r11 = "ApkSoSource"
                android.util.Log.d(r11, r10)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.ApkSoSource.ApkUnpacker.shouldExtract(java.util.zip.ZipEntry, java.lang.String):boolean");
        }
    }

    @Override // com.facebook.soloader.UnpackingSoSource
    protected byte[] getDepsBlock() throws IOException {
        File canonicalFile = this.mZipFileName.getCanonicalFile();
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeByte((byte) 2);
            obtain.writeString(canonicalFile.getPath());
            obtain.writeLong(canonicalFile.lastModified());
            obtain.writeInt(SysUtil.getAppVersionCode(this.mContext));
            if ((this.mFlags & 1) == 0) {
                obtain.writeByte((byte) 0);
                return obtain.marshall();
            }
            String str = this.mContext.getApplicationInfo().nativeLibraryDir;
            if (str == null) {
                obtain.writeByte((byte) 1);
                return obtain.marshall();
            }
            File canonicalFile2 = new File(str).getCanonicalFile();
            if (!canonicalFile2.exists()) {
                obtain.writeByte((byte) 1);
                return obtain.marshall();
            }
            obtain.writeByte((byte) 2);
            obtain.writeString(canonicalFile2.getPath());
            obtain.writeLong(canonicalFile2.lastModified());
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }
}
