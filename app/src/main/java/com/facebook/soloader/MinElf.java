package com.facebook.soloader;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedByInterruptException;
import kotlin.UShort;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes.dex */
public final class MinElf {
    public static final int DT_NEEDED = 1;
    public static final int DT_NULL = 0;
    public static final int DT_STRTAB = 5;
    public static final int ELF_MAGIC = 1179403647;
    public static final int PN_XNUM = 65535;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_LOAD = 1;
    private static final String TAG = "MinElf";

    public enum ISA {
        NOT_SO("not_so"),
        X86("x86"),
        ARM("armeabi-v7a"),
        X86_64("x86_64"),
        AARCH64("arm64-v8a"),
        OTHERS("others");

        private final String value;

        ISA(String str) {
            this.value = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.value;
        }
    }

    public static String[] extract_DT_NEEDED(File file) throws IOException {
        ElfFileChannel elfFileChannel = new ElfFileChannel(file);
        try {
            String[] extract_DT_NEEDED = extract_DT_NEEDED(elfFileChannel);
            elfFileChannel.close();
            return extract_DT_NEEDED;
        } catch (Throwable th) {
            try {
                elfFileChannel.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String[] extract_DT_NEEDED_with_retries(ElfFileChannel elfFileChannel) throws IOException {
        int i = 0;
        while (true) {
            try {
                return extract_DT_NEEDED_no_retries(elfFileChannel);
            } catch (ClosedByInterruptException e) {
                i++;
                if (i > 4) {
                    throw e;
                }
                Thread.interrupted();
                Log.e(TAG, "retrying extract_DT_NEEDED due to ClosedByInterruptException", e);
                elfFileChannel.openChannel();
            }
        }
    }

    public static String[] extract_DT_NEEDED(ElfByteChannel elfByteChannel) throws IOException {
        if (elfByteChannel instanceof ElfFileChannel) {
            return extract_DT_NEEDED_with_retries((ElfFileChannel) elfByteChannel);
        }
        return extract_DT_NEEDED_no_retries(elfByteChannel);
    }

    private static String[] extract_DT_NEEDED_no_retries(ElfByteChannel elfByteChannel) throws IOException {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        long j11 = getu32(elfByteChannel, allocate, 0L);
        if (j11 != 1179403647) {
            throw new ElfError("file is not ELF: 0x" + Long.toHexString(j11));
        }
        boolean z = getu8(elfByteChannel, allocate, 4L) == 1;
        if (getu8(elfByteChannel, allocate, 5L) == 2) {
            allocate.order(ByteOrder.BIG_ENDIAN);
        }
        long j12 = z ? getu32(elfByteChannel, allocate, 28L) : get64(elfByteChannel, allocate, 32L);
        long j13 = z ? getu16(elfByteChannel, allocate, 44L) : getu16(elfByteChannel, allocate, 56L);
        int i = getu16(elfByteChannel, allocate, z ? 42L : 54L);
        if (j13 == WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            long j14 = z ? getu32(elfByteChannel, allocate, 32L) : get64(elfByteChannel, allocate, 40L);
            if (z) {
                j10 = getu32(elfByteChannel, allocate, j14 + 28);
            } else {
                j10 = getu32(elfByteChannel, allocate, j14 + 44);
            }
            j13 = j10;
        }
        long j15 = j12;
        long j16 = 0;
        while (true) {
            if (j16 >= j13) {
                j = 0;
                break;
            }
            if (z) {
                j9 = getu32(elfByteChannel, allocate, j15 + 0);
            } else {
                j9 = getu32(elfByteChannel, allocate, j15 + 0);
            }
            if (j9 != 2) {
                j15 += i;
                j16++;
            } else if (z) {
                j = getu32(elfByteChannel, allocate, j15 + 4);
            } else {
                j = get64(elfByteChannel, allocate, j15 + 8);
            }
        }
        long j17 = 0;
        if (j == 0) {
            throw new ElfError("ELF file does not contain dynamic linking information");
        }
        long j18 = j;
        long j19 = 0;
        int i2 = 0;
        while (true) {
            boolean z2 = z;
            long j20 = z ? getu32(elfByteChannel, allocate, j18 + j17) : get64(elfByteChannel, allocate, j18 + j17);
            if (j20 == 1) {
                j2 = j;
                if (i2 == Integer.MAX_VALUE) {
                    throw new ElfError("malformed DT_NEEDED section");
                }
                i2++;
            } else {
                j2 = j;
                if (j20 == 5) {
                    j19 = z2 ? getu32(elfByteChannel, allocate, j18 + 4) : get64(elfByteChannel, allocate, j18 + 8);
                }
            }
            long j21 = 16;
            j18 += z2 ? 8L : 16L;
            j17 = 0;
            if (j20 != 0) {
                z = z2;
                j = j2;
            } else {
                if (j19 == 0) {
                    throw new ElfError("Dynamic section string-table not found");
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= j13) {
                        j3 = 0;
                        break;
                    }
                    if (z2) {
                        j4 = getu32(elfByteChannel, allocate, j12 + j17);
                    } else {
                        j4 = getu32(elfByteChannel, allocate, j12 + j17);
                    }
                    if (j4 == 1) {
                        if (z2) {
                            j6 = getu32(elfByteChannel, allocate, j12 + 8);
                        } else {
                            j6 = get64(elfByteChannel, allocate, j12 + j21);
                        }
                        if (z2) {
                            j5 = j13;
                            j7 = getu32(elfByteChannel, allocate, j12 + 20);
                        } else {
                            j5 = j13;
                            j7 = get64(elfByteChannel, allocate, j12 + 40);
                        }
                        if (j6 <= j19 && j19 < j7 + j6) {
                            if (z2) {
                                j8 = getu32(elfByteChannel, allocate, j12 + 4);
                            } else {
                                j8 = get64(elfByteChannel, allocate, j12 + 8);
                            }
                            j3 = j8 + (j19 - j6);
                        }
                    } else {
                        j5 = j13;
                    }
                    j12 += i;
                    i3++;
                    j13 = j5;
                    j21 = 16;
                    j17 = 0;
                }
                long j22 = 0;
                if (j3 == 0) {
                    throw new ElfError("did not find file offset of DT_STRTAB table");
                }
                String[] strArr = new String[i2];
                int i4 = 0;
                while (true) {
                    long j23 = j2 + j22;
                    long j24 = z2 ? getu32(elfByteChannel, allocate, j23) : get64(elfByteChannel, allocate, j23);
                    if (j24 == 1) {
                        strArr[i4] = getSz(elfByteChannel, allocate, (z2 ? getu32(elfByteChannel, allocate, j2 + 4) : get64(elfByteChannel, allocate, j2 + 8)) + j3);
                        if (i4 == Integer.MAX_VALUE) {
                            throw new ElfError("malformed DT_NEEDED section");
                        }
                        i4++;
                    }
                    j2 += z2 ? 8L : 16L;
                    if (j24 == 0) {
                        if (i4 == i2) {
                            return strArr;
                        }
                        throw new ElfError("malformed DT_NEEDED section");
                    }
                    j22 = 0;
                }
            }
        }
    }

    private static String getSz(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short u8Var = getu8(elfByteChannel, byteBuffer, j);
            if (u8Var != 0) {
                sb.append((char) u8Var);
                j = j2;
            } else {
                return sb.toString();
            }
        }
    }

    private static void read(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, int i, long j) throws IOException {
        int read;
        byteBuffer.position(0);
        byteBuffer.limit(i);
        while (byteBuffer.remaining() > 0 && (read = elfByteChannel.read(byteBuffer, j)) != -1) {
            j += read;
        }
        if (byteBuffer.remaining() > 0) {
            throw new ElfError("ELF file truncated");
        }
        byteBuffer.position(0);
    }

    private static long get64(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 8, j);
        return byteBuffer.getLong();
    }

    private static long getu32(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 4, j);
        return byteBuffer.getInt() & 4294967295L;
    }

    private static int getu16(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 2, j);
        return byteBuffer.getShort() & UShort.MAX_VALUE;
    }

    private static short getu8(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 1, j);
        return (short) (byteBuffer.get() & 255);
    }

    private static class ElfError extends RuntimeException {
        ElfError(String str) {
            super(str);
        }
    }
}
