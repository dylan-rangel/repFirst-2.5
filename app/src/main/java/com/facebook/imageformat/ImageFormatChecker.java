package com.facebook.imageformat;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.imageformat.ImageFormat;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class ImageFormatChecker {
    private static ImageFormatChecker sInstance;

    @Nullable
    private List<ImageFormat.FormatChecker> mCustomImageFormatCheckers;
    private final DefaultImageFormatChecker mDefaultFormatChecker = new DefaultImageFormatChecker();
    private int mMaxHeaderLength;

    private ImageFormatChecker() {
        updateMaxHeaderLength();
    }

    public void setUseNewOrder(boolean useNewOrder) {
        this.mDefaultFormatChecker.setUseNewOrder(useNewOrder);
    }

    public void setCustomImageFormatCheckers(@Nullable List<ImageFormat.FormatChecker> customImageFormatCheckers) {
        this.mCustomImageFormatCheckers = customImageFormatCheckers;
        updateMaxHeaderLength();
    }

    public ImageFormat determineImageFormat(final InputStream is) throws IOException {
        Preconditions.checkNotNull(is);
        int i = this.mMaxHeaderLength;
        byte[] bArr = new byte[i];
        int readHeaderFromStream = readHeaderFromStream(i, is, bArr);
        ImageFormat determineFormat = this.mDefaultFormatChecker.determineFormat(bArr, readHeaderFromStream);
        if (determineFormat != null && determineFormat != ImageFormat.UNKNOWN) {
            return determineFormat;
        }
        List<ImageFormat.FormatChecker> list = this.mCustomImageFormatCheckers;
        if (list != null) {
            Iterator<ImageFormat.FormatChecker> it = list.iterator();
            while (it.hasNext()) {
                ImageFormat determineFormat2 = it.next().determineFormat(bArr, readHeaderFromStream);
                if (determineFormat2 != null && determineFormat2 != ImageFormat.UNKNOWN) {
                    return determineFormat2;
                }
            }
        }
        return ImageFormat.UNKNOWN;
    }

    private void updateMaxHeaderLength() {
        this.mMaxHeaderLength = this.mDefaultFormatChecker.getHeaderSize();
        List<ImageFormat.FormatChecker> list = this.mCustomImageFormatCheckers;
        if (list != null) {
            Iterator<ImageFormat.FormatChecker> it = list.iterator();
            while (it.hasNext()) {
                this.mMaxHeaderLength = Math.max(this.mMaxHeaderLength, it.next().getHeaderSize());
            }
        }
    }

    private static int readHeaderFromStream(int maxHeaderLength, final InputStream is, final byte[] imageHeaderBytes) throws IOException {
        Preconditions.checkNotNull(is);
        Preconditions.checkNotNull(imageHeaderBytes);
        Preconditions.checkArgument(Boolean.valueOf(imageHeaderBytes.length >= maxHeaderLength));
        if (is.markSupported()) {
            try {
                is.mark(maxHeaderLength);
                return ByteStreams.read(is, imageHeaderBytes, 0, maxHeaderLength);
            } finally {
                is.reset();
            }
        }
        return ByteStreams.read(is, imageHeaderBytes, 0, maxHeaderLength);
    }

    public static synchronized ImageFormatChecker getInstance() {
        ImageFormatChecker imageFormatChecker;
        synchronized (ImageFormatChecker.class) {
            if (sInstance == null) {
                sInstance = new ImageFormatChecker();
            }
            imageFormatChecker = sInstance;
        }
        return imageFormatChecker;
    }

    public static ImageFormat getImageFormat(final InputStream is) throws IOException {
        return getInstance().determineImageFormat(is);
    }

    public static ImageFormat getImageFormat_WrapIOException(final InputStream is) {
        try {
            return getImageFormat(is);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public static ImageFormat getImageFormat(String filename) {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(filename);
                try {
                    ImageFormat imageFormat = getImageFormat(fileInputStream2);
                    Closeables.closeQuietly(fileInputStream2);
                    return imageFormat;
                } catch (IOException unused) {
                    fileInputStream = fileInputStream2;
                    ImageFormat imageFormat2 = ImageFormat.UNKNOWN;
                    Closeables.closeQuietly(fileInputStream);
                    return imageFormat2;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    Closeables.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
