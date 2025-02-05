package com.oblador.vectoricons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.facebook.react.views.text.ReactFontManager;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class VectorIconsModuleImpl {
    public static final String NAME = "RNVectorIcons";
    private static final Map<String, Typeface> sTypefaceCache = new HashMap();

    public static String getImageForFont(String str, String str2, Integer num, Integer num2, Context context) throws IOException, FileNotFoundException {
        FileOutputStream fileOutputStream;
        String str3 = context.getCacheDir().getAbsolutePath() + "/";
        float f = context.getResources().getDisplayMetrics().density;
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        int i = (int) f;
        sb.append(f == ((float) i) ? Integer.toString(i) : Float.toString(f));
        sb.append(ViewHierarchyNode.JsonKeys.X);
        String sb2 = sb.toString();
        int round = Math.round(num.intValue() * f);
        String str4 = str3 + Integer.toString((str + ":" + str2 + ":" + num2).hashCode(), 32) + "_" + Integer.toString(num.intValue()) + sb2 + ".png";
        String str5 = "file://" + str4;
        File file = new File(str4);
        if (file.exists()) {
            return str5;
        }
        FileOutputStream fileOutputStream2 = null;
        Typeface typeface = ReactFontManager.getInstance().getTypeface(str, 0, context.getAssets());
        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setColor(num2.intValue());
        paint.setTextSize(round);
        paint.setAntiAlias(true);
        paint.getTextBounds(str2, 0, str2.length(), new Rect());
        int i2 = round - ((int) paint.getFontMetrics().bottom);
        Bitmap createBitmap = Bitmap.createBitmap(round, round, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str2, 0, i2, paint);
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return str5;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw th;
        }
    }
}
