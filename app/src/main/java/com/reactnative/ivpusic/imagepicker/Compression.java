package com.reactnative.ivpusic.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/* loaded from: classes2.dex */
class Compression {
    Compression() {
    }

    File resize(Context context, String str, int i, int i2, int i3, int i4, int i5) throws IOException {
        Bitmap decodeFile;
        Pair<Integer, Integer> calculateTargetDimensions = calculateTargetDimensions(i, i2, i3, i4);
        int intValue = ((Integer) calculateTargetDimensions.first).intValue();
        int intValue2 = ((Integer) calculateTargetDimensions.second).intValue();
        if (i <= i3 && i2 <= i4) {
            decodeFile = BitmapFactory.decodeFile(str);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateInSampleSize(i, i2, intValue, intValue2);
            decodeFile = BitmapFactory.decodeFile(str, options);
        }
        String attribute = new ExifInterface(str).getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeFile, intValue, intValue2, true);
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!externalFilesDir.exists()) {
            Log.d("image-crop-picker", "Pictures Directory is not existing. Will create this directory.");
            externalFilesDir.mkdirs();
        }
        File file = new File(externalFilesDir, UUID.randomUUID() + ".jpg");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        createScaledBitmap.compress(Bitmap.CompressFormat.JPEG, i5, bufferedOutputStream);
        if (shouldSetOrientation(attribute)) {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            exifInterface.setAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, attribute);
            exifInterface.saveAttributes();
        }
        bufferedOutputStream.close();
        createScaledBitmap.recycle();
        return file;
    }

    private int calculateInSampleSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i > i3 || i2 > i4) {
            int i6 = i / 2;
            int i7 = i2 / 2;
            while (i6 / i5 >= i3 && i7 / i5 >= i4) {
                i5 *= 2;
            }
        }
        return i5;
    }

    private boolean shouldSetOrientation(String str) {
        return (str.equals(String.valueOf(1)) || str.equals(String.valueOf(0))) ? false : true;
    }

    File compressImage(Context context, ReadableMap readableMap, String str, BitmapFactory.Options options) throws IOException {
        Integer valueOf = readableMap.hasKey("compressImageMaxWidth") ? Integer.valueOf(readableMap.getInt("compressImageMaxWidth")) : null;
        Integer valueOf2 = readableMap.hasKey("compressImageMaxHeight") ? Integer.valueOf(readableMap.getInt("compressImageMaxHeight")) : null;
        Double valueOf3 = readableMap.hasKey("compressImageQuality") ? Double.valueOf(readableMap.getDouble("compressImageQuality")) : null;
        boolean z = false;
        boolean z2 = valueOf3 == null || valueOf3.doubleValue() == 1.0d;
        boolean z3 = valueOf == null || valueOf.intValue() >= options.outWidth;
        boolean z4 = valueOf2 == null || valueOf2.intValue() >= options.outHeight;
        List asList = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif", "image/tiff");
        if (options.outMimeType != null && asList.contains(options.outMimeType.toLowerCase())) {
            z = true;
        }
        if (z2 && z3 && z4 && z) {
            Log.d("image-crop-picker", "Skipping image compression");
            return new File(str);
        }
        Log.d("image-crop-picker", "Image compression activated");
        int doubleValue = valueOf3 != null ? (int) (valueOf3.doubleValue() * 100.0d) : 100;
        Log.d("image-crop-picker", "Compressing image with quality " + doubleValue);
        if (valueOf == null) {
            valueOf = Integer.valueOf(options.outWidth);
        }
        if (valueOf2 == null) {
            valueOf2 = Integer.valueOf(options.outHeight);
        }
        return resize(context, str, options.outWidth, options.outHeight, valueOf.intValue(), valueOf2.intValue(), doubleValue);
    }

    private Pair<Integer, Integer> calculateTargetDimensions(int i, int i2, int i3, int i4) {
        if (i > i3) {
            i2 = (int) (i2 * (i3 / i));
            i = i3;
        }
        if (i2 > i4) {
            i = (int) (i * (i4 / i2));
        } else {
            i4 = i2;
        }
        return Pair.create(Integer.valueOf(i), Integer.valueOf(i4));
    }

    synchronized void compressVideo(Activity activity, ReadableMap readableMap, String str, String str2, Promise promise) {
        promise.resolve(str);
    }
}
