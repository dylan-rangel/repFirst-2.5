package com.facebook.common.webp;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class WebpSupportStatus {
    private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
    private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
    private static final String VP8X_WEBP_BASE64 = "UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==";
    private static final byte[] WEBP_NAME_BYTES;
    private static final byte[] WEBP_RIFF_BYTES;
    private static final byte[] WEBP_VP8L_BYTES;
    private static final byte[] WEBP_VP8X_BYTES;
    private static final byte[] WEBP_VP8_BYTES;
    public static final boolean sIsExtendedWebpSupported;
    public static final boolean sIsSimpleWebpSupported;
    public static final boolean sIsWebpSupportRequired;

    @Nullable
    public static WebpBitmapFactory sWebpBitmapFactory;
    private static boolean sWebpLibraryChecked;

    static {
        sIsWebpSupportRequired = Build.VERSION.SDK_INT <= 17;
        sIsSimpleWebpSupported = Build.VERSION.SDK_INT >= 14;
        sIsExtendedWebpSupported = isExtendedWebpSupported();
        sWebpBitmapFactory = null;
        sWebpLibraryChecked = false;
        WEBP_RIFF_BYTES = asciiBytes("RIFF");
        WEBP_NAME_BYTES = asciiBytes("WEBP");
        WEBP_VP8_BYTES = asciiBytes("VP8 ");
        WEBP_VP8L_BYTES = asciiBytes("VP8L");
        WEBP_VP8X_BYTES = asciiBytes("VP8X");
    }

    @Nullable
    public static WebpBitmapFactory loadWebpBitmapFactoryIfExists() {
        if (sWebpLibraryChecked) {
            return sWebpBitmapFactory;
        }
        WebpBitmapFactory webpBitmapFactory = null;
        try {
            webpBitmapFactory = (WebpBitmapFactory) Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
        } catch (Throwable unused) {
        }
        sWebpLibraryChecked = true;
        return webpBitmapFactory;
    }

    private static byte[] asciiBytes(String value) {
        try {
            return value.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("ASCII not found!", e);
        }
    }

    private static boolean isExtendedWebpSupported() {
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        if (Build.VERSION.SDK_INT == 17) {
            byte[] decode = Base64.decode(VP8X_WEBP_BASE64, 0);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
            if (options.outHeight != 1 || options.outWidth != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWebpSupportedByPlatform(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        if (isSimpleWebpHeader(imageHeaderBytes, offset)) {
            return sIsSimpleWebpSupported;
        }
        if (isLosslessWebpHeader(imageHeaderBytes, offset)) {
            return sIsExtendedWebpSupported;
        }
        if (!isExtendedWebpHeader(imageHeaderBytes, offset, headerSize) || isAnimatedWebpHeader(imageHeaderBytes, offset)) {
            return false;
        }
        return sIsExtendedWebpSupported;
    }

    public static boolean isAnimatedWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES) && ((imageHeaderBytes[offset + 20] & 2) == 2);
    }

    public static boolean isSimpleWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8_BYTES);
    }

    public static boolean isLosslessWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8L_BYTES);
    }

    public static boolean isExtendedWebpHeader(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        return headerSize >= 21 && matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES);
    }

    public static boolean isExtendedWebpHeaderWithAlpha(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES) && ((imageHeaderBytes[offset + 20] & Ascii.DLE) == 16);
    }

    public static boolean isWebpHeader(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        return headerSize >= 20 && matchBytePattern(imageHeaderBytes, offset, WEBP_RIFF_BYTES) && matchBytePattern(imageHeaderBytes, offset + 8, WEBP_NAME_BYTES);
    }

    private static boolean matchBytePattern(final byte[] byteArray, final int offset, final byte[] pattern) {
        if (pattern == null || byteArray == null || pattern.length + offset > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i + offset] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
