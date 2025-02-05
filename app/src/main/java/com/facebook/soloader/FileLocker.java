package com.facebook.soloader;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class FileLocker implements Closeable {

    @Nullable
    private FileLock mLock;
    private FileOutputStream mLockFileOutputStream;

    public static FileLocker lock(File file) throws IOException {
        return new FileLocker(file, false);
    }

    @Nullable
    public static FileLocker tryLock(File file) throws IOException {
        FileLocker fileLocker = new FileLocker(file, true);
        if (fileLocker.mLock != null) {
            return fileLocker;
        }
        fileLocker.close();
        return null;
    }

    private void init(File file, boolean z) throws IOException {
        FileLock fileLock;
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        this.mLockFileOutputStream = fileOutputStream;
        try {
            if (z) {
                try {
                    fileLock = fileOutputStream.getChannel().tryLock();
                } catch (IOException unused) {
                    fileLock = null;
                }
            } else {
                fileLock = fileOutputStream.getChannel().lock();
            }
            if (fileLock == null) {
            }
            this.mLock = fileLock;
        } finally {
            this.mLockFileOutputStream.close();
        }
    }

    private FileLocker(File file, boolean z) throws IOException {
        init(file, z);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            FileLock fileLock = this.mLock;
            if (fileLock != null) {
                fileLock.release();
            }
        } finally {
            this.mLockFileOutputStream.close();
        }
    }
}
