package com.google.android.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.core.graphics.drawable.DrawableCompat;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class DrawableUtils {
    private DrawableUtils() {
    }

    public static void setTint(Drawable drawable, int i) {
        boolean z = i != 0;
        if (Build.VERSION.SDK_INT == 21) {
            if (z) {
                drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
                return;
            } else {
                drawable.setColorFilter(null);
                return;
            }
        }
        if (z) {
            DrawableCompat.setTint(drawable, i);
        } else {
            DrawableCompat.setTintList(drawable, null);
        }
    }

    public static PorterDuffColorFilter updateTintFilter(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(drawable.getState(), 0), mode);
    }

    public static AttributeSet parseDrawableXml(Context context, int i, CharSequence charSequence) {
        int next;
        try {
            XmlResourceParser xml = context.getResources().getXml(i);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            if (!TextUtils.equals(xml.getName(), charSequence)) {
                throw new XmlPullParserException("Must have a <" + ((Object) charSequence) + "> start tag");
            }
            return Xml.asAttributeSet(xml);
        } catch (IOException | XmlPullParserException e) {
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(i));
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    public static void setRippleDrawableRadius(RippleDrawable rippleDrawable, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            rippleDrawable.setRadius(i);
            return;
        }
        try {
            RippleDrawable.class.getDeclaredMethod("setMaxRadius", Integer.TYPE).invoke(rippleDrawable, Integer.valueOf(i));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Couldn't set RippleDrawable radius", e);
        }
    }

    public static Drawable createTintableDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return createTintableMutatedDrawableIfNeeded(drawable, colorStateList, mode, false);
    }

    public static Drawable createTintableMutatedDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return createTintableMutatedDrawableIfNeeded(drawable, colorStateList, mode, Build.VERSION.SDK_INT < 23);
    }

    private static Drawable createTintableMutatedDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode, boolean z) {
        if (drawable == null) {
            return null;
        }
        if (colorStateList != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (mode != null) {
                DrawableCompat.setTintMode(drawable, mode);
            }
        } else if (z) {
            drawable.mutate();
        }
        return drawable;
    }

    public static Drawable compositeTwoLayeredDrawable(Drawable drawable, Drawable drawable2) {
        int intrinsicWidth;
        int intrinsicHeight;
        if (drawable == null) {
            return drawable2;
        }
        if (drawable2 == null) {
            return drawable;
        }
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable, drawable2});
        if (drawable2.getIntrinsicWidth() == -1 || drawable2.getIntrinsicHeight() == -1) {
            intrinsicWidth = drawable.getIntrinsicWidth();
            intrinsicHeight = drawable.getIntrinsicHeight();
        } else if (drawable2.getIntrinsicWidth() <= drawable.getIntrinsicWidth() && drawable2.getIntrinsicHeight() <= drawable.getIntrinsicHeight()) {
            intrinsicWidth = drawable2.getIntrinsicWidth();
            intrinsicHeight = drawable2.getIntrinsicHeight();
        } else {
            float intrinsicWidth2 = drawable2.getIntrinsicWidth() / drawable2.getIntrinsicHeight();
            if (intrinsicWidth2 >= drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) {
                int intrinsicWidth3 = drawable.getIntrinsicWidth();
                int i = (int) (intrinsicWidth3 / intrinsicWidth2);
                intrinsicWidth = intrinsicWidth3;
                intrinsicHeight = i;
            } else {
                intrinsicHeight = drawable.getIntrinsicHeight();
                intrinsicWidth = (int) (intrinsicWidth2 * intrinsicHeight);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            layerDrawable.setLayerSize(1, intrinsicWidth, intrinsicHeight);
            layerDrawable.setLayerGravity(1, 17);
        } else {
            int intrinsicWidth4 = (drawable.getIntrinsicWidth() - intrinsicWidth) / 2;
            int intrinsicHeight2 = (drawable.getIntrinsicHeight() - intrinsicHeight) / 2;
            layerDrawable.setLayerInset(1, intrinsicWidth4, intrinsicHeight2, intrinsicWidth4, intrinsicHeight2);
        }
        return layerDrawable;
    }

    public static int[] getCheckedState(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 == 16842912) {
                return iArr;
            }
            if (i2 == 0) {
                int[] iArr2 = (int[]) iArr.clone();
                iArr2[i] = 16842912;
                return iArr2;
            }
        }
        int[] copyOf = Arrays.copyOf(iArr, iArr.length + 1);
        copyOf[iArr.length] = 16842912;
        return copyOf;
    }

    public static int[] getUncheckedState(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        int i = 0;
        for (int i2 : iArr) {
            if (i2 != 16842912) {
                iArr2[i] = i2;
                i++;
            }
        }
        return iArr2;
    }

    public static void setOutlineToPath(Outline outline, Path path) {
        if (Build.VERSION.SDK_INT >= 30) {
            outline.setPath(path);
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                outline.setConvexPath(path);
            } catch (IllegalArgumentException unused) {
            }
        } else {
            if (Build.VERSION.SDK_INT < 21 || !path.isConvex()) {
                return;
            }
            outline.setConvexPath(path);
        }
    }
}
