package com.th3rdwave.safeareacontext;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SafeAreaUtils.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u00020\u0005H\u0002\u001a\u0012\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u00020\u0005H\u0002\u001a\u0012\u0010\t\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u00020\u0005H\u0003\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u00020\u0005H\u0003\u001a\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\f"}, d2 = {"getFrame", "Lcom/th3rdwave/safeareacontext/Rect;", "rootView", "Landroid/view/ViewGroup;", "view", "Landroid/view/View;", "getRootWindowInsetsCompat", "Lcom/th3rdwave/safeareacontext/EdgeInsets;", "getRootWindowInsetsCompatBase", "getRootWindowInsetsCompatM", "getRootWindowInsetsCompatR", "getSafeAreaInsets", "react-native-safe-area-context_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SafeAreaUtilsKt {
    private static final EdgeInsets getRootWindowInsetsCompatR(View view) {
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if ((rootWindowInsets == null ? null : rootWindowInsets.getInsets(WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars())) == null) {
            return null;
        }
        return new EdgeInsets(r4.top, r4.right, r4.bottom, r4.left);
    }

    private static final EdgeInsets getRootWindowInsetsCompatM(View view) {
        if (view.getRootWindowInsets() == null) {
            return null;
        }
        return new EdgeInsets(r5.getSystemWindowInsetTop(), r5.getSystemWindowInsetRight(), Math.min(r5.getSystemWindowInsetBottom(), r5.getStableInsetBottom()), r5.getSystemWindowInsetLeft());
    }

    private static final EdgeInsets getRootWindowInsetsCompatBase(View view) {
        view.getWindowVisibleDisplayFrame(new android.graphics.Rect());
        return new EdgeInsets(r0.top, view.getWidth() - r0.right, view.getHeight() - r0.bottom, r0.left);
    }

    private static final EdgeInsets getRootWindowInsetsCompat(View view) {
        return Build.VERSION.SDK_INT >= 30 ? getRootWindowInsetsCompatR(view) : Build.VERSION.SDK_INT >= 23 ? getRootWindowInsetsCompatM(view) : getRootWindowInsetsCompatBase(view);
    }

    public static final EdgeInsets getSafeAreaInsets(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (view.getHeight() == 0) {
            return null;
        }
        View rootView = view.getRootView();
        Intrinsics.checkNotNullExpressionValue(rootView, "rootView");
        EdgeInsets rootWindowInsetsCompat = getRootWindowInsetsCompat(rootView);
        if (rootWindowInsetsCompat == null) {
            return null;
        }
        float width = rootView.getWidth();
        float height = rootView.getHeight();
        view.getGlobalVisibleRect(new android.graphics.Rect());
        return new EdgeInsets(Math.max(rootWindowInsetsCompat.getTop() - r3.top, 0.0f), Math.max(Math.min((r3.left + view.getWidth()) - width, 0.0f) + rootWindowInsetsCompat.getRight(), 0.0f), Math.max(Math.min((r3.top + view.getHeight()) - height, 0.0f) + rootWindowInsetsCompat.getBottom(), 0.0f), Math.max(rootWindowInsetsCompat.getLeft() - r3.left, 0.0f));
    }

    public static final Rect getFrame(ViewGroup rootView, View view) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        Intrinsics.checkNotNullParameter(view, "view");
        if (view.getParent() == null) {
            return null;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        view.getDrawingRect(rect);
        try {
            rootView.offsetDescendantRectToMyCoords(view, rect);
            return new Rect(rect.left, rect.top, view.getWidth(), view.getHeight());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
