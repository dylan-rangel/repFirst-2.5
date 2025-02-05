package com.facebook.common.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class Files {
    private Files() {
    }

    static byte[] readFile(InputStream in, long expectedSize) throws IOException {
        if (expectedSize <= 2147483647L) {
            if (expectedSize == 0) {
                return ByteStreams.toByteArray(in);
            }
            return ByteStreams.toByteArray(in, (int) expectedSize);
        }
        throw new OutOfMemoryError("file is too large to fit in a byte array: " + expectedSize + " bytes");
    }

    public static byte[] toByteArray(File file) throws IOException {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] readFile = readFile(fileInputStream, fileInputStream.getChannel().size());
            fileInputStream.close();
            return readFile;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
    }
}
