package androidx.appcompat.widget;

import android.R;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class DrawableUtils {
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] EMPTY_STATE_SET = new int[0];
    public static final Rect INSETS_NONE = new Rect();

    private DrawableUtils() {
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 29) {
            Insets opticalInsets = Api29Impl.getOpticalInsets(drawable);
            return new Rect(opticalInsets.left, opticalInsets.top, opticalInsets.right, opticalInsets.bottom);
        }
        if (Build.VERSION.SDK_INT >= 18) {
            return Api18Impl.getOpticalInsets(DrawableCompat.unwrap(drawable));
        }
        return INSETS_NONE;
    }

    static void fixDrawable(Drawable drawable) {
        String name = drawable.getClass().getName();
        if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(name)) {
            forceDrawableStateChange(drawable);
        } else {
            if (Build.VERSION.SDK_INT < 29 || Build.VERSION.SDK_INT >= 31 || !"android.graphics.drawable.ColorStateListDrawable".equals(name)) {
                return;
            }
            forceDrawableStateChange(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean canSafelyMutateDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 17) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState instanceof DrawableContainer.DrawableContainerState) {
                for (Drawable drawable2 : ((DrawableContainer.DrawableContainerState) constantState).getChildren()) {
                    if (!canSafelyMutateDrawable(drawable2)) {
                        return false;
                    }
                }
            }
        } else {
            if (drawable instanceof WrappedDrawable) {
                return canSafelyMutateDrawable(((WrappedDrawable) drawable).getWrappedDrawable());
            }
            if (drawable instanceof DrawableWrapper) {
                return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
            }
            if (drawable instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
            }
        }
        return true;
    }

    private static void forceDrawableStateChange(Drawable drawable) {
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(CHECKED_STATE_SET);
        } else {
            drawable.setState(EMPTY_STATE_SET);
        }
        drawable.setState(state);
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    static class Api18Impl {
        private static final Field sBottom;
        private static final Method sGetOpticalInsets;
        private static final Field sLeft;
        private static final boolean sReflectionSuccessful;
        private static final Field sRight;
        private static final Field sTop;

        /* JADX WARN: Removed duplicated region for block: B:14:0x0057  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
        static {
            /*
                r0 = 1
                r1 = 0
                r2 = 0
                java.lang.String r3 = "android.graphics.Insets"
                java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch: java.lang.NoSuchFieldException -> L44 java.lang.ClassNotFoundException -> L49 java.lang.NoSuchMethodException -> L4e
                java.lang.Class<android.graphics.drawable.Drawable> r4 = android.graphics.drawable.Drawable.class
                java.lang.String r5 = "getOpticalInsets"
                java.lang.Class[] r6 = new java.lang.Class[r1]     // Catch: java.lang.NoSuchFieldException -> L44 java.lang.ClassNotFoundException -> L49 java.lang.NoSuchMethodException -> L4e
                java.lang.reflect.Method r4 = r4.getMethod(r5, r6)     // Catch: java.lang.NoSuchFieldException -> L44 java.lang.ClassNotFoundException -> L49 java.lang.NoSuchMethodException -> L4e
                java.lang.String r5 = "left"
                java.lang.reflect.Field r5 = r3.getField(r5)     // Catch: java.lang.NoSuchFieldException -> L3b java.lang.ClassNotFoundException -> L3e java.lang.NoSuchMethodException -> L41
                java.lang.String r6 = "top"
                java.lang.reflect.Field r6 = r3.getField(r6)     // Catch: java.lang.NoSuchFieldException -> L32 java.lang.ClassNotFoundException -> L35 java.lang.NoSuchMethodException -> L38
                java.lang.String r7 = "right"
                java.lang.reflect.Field r7 = r3.getField(r7)     // Catch: java.lang.Throwable -> L2f
                java.lang.String r8 = "bottom"
                java.lang.reflect.Field r3 = r3.getField(r8)     // Catch: java.lang.Throwable -> L2d
                r8 = 1
                goto L55
            L2d:
                goto L53
            L2f:
                r7 = r2
                goto L53
            L32:
                r6 = r2
                goto L52
            L35:
                r6 = r2
                goto L52
            L38:
                r6 = r2
                goto L52
            L3b:
                r5 = r2
                goto L47
            L3e:
                r5 = r2
                goto L4c
            L41:
                r5 = r2
                goto L51
            L44:
                r4 = r2
                r5 = r4
            L47:
                r6 = r5
                goto L52
            L49:
                r4 = r2
                r5 = r4
            L4c:
                r6 = r5
                goto L52
            L4e:
                r4 = r2
                r5 = r4
            L51:
                r6 = r5
            L52:
                r7 = r6
            L53:
                r3 = r2
                r8 = 0
            L55:
                if (r8 == 0) goto L64
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sGetOpticalInsets = r4
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sLeft = r5
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sTop = r6
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sRight = r7
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sBottom = r3
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sReflectionSuccessful = r0
                goto L70
            L64:
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sGetOpticalInsets = r2
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sLeft = r2
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sTop = r2
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sRight = r2
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sBottom = r2
                androidx.appcompat.widget.DrawableUtils.Api18Impl.sReflectionSuccessful = r1
            L70:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.DrawableUtils.Api18Impl.<clinit>():void");
        }

        private Api18Impl() {
        }

        static Rect getOpticalInsets(Drawable drawable) {
            if (Build.VERSION.SDK_INT < 29 && sReflectionSuccessful) {
                try {
                    Object invoke = sGetOpticalInsets.invoke(drawable, new Object[0]);
                    if (invoke != null) {
                        return new Rect(sLeft.getInt(invoke), sTop.getInt(invoke), sRight.getInt(invoke), sBottom.getInt(invoke));
                    }
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
            }
            return DrawableUtils.INSETS_NONE;
        }
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static Insets getOpticalInsets(Drawable drawable) {
            return drawable.getOpticalInsets();
        }
    }
}
