package com.facebook.react.uimanager;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import com.facebook.infer.annotation.Assertions;

/* loaded from: classes.dex */
public class RootViewUtil {
    /* JADX WARN: Multi-variable type inference failed */
    public static RootView getRootView(View view) {
        View view2 = view;
        while (!(view2 instanceof RootView)) {
            Object parent = view2.getParent();
            if (parent == null) {
                return null;
            }
            Assertions.assertCondition(parent instanceof View);
            view2 = (View) parent;
        }
        return (RootView) view2;
    }

    public static Point getViewportOffset(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        iArr[0] = iArr[0] - rect.left;
        iArr[1] = iArr[1] - rect.top;
        return new Point(iArr[0], iArr[1]);
    }
}
