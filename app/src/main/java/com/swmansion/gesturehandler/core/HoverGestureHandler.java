package com.swmansion.gesturehandler.core;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.NotificationCompat;
import com.swmansion.gesturehandler.react.RNViewConfigurationHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HoverGestureHandler.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0015\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0082\u0004J)\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0014J\u0018\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0014J\u0014\u0010\u0016\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\u0014\u0010\u0017\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\u0014\u0010\u0018\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/swmansion/gesturehandler/core/HoverGestureHandler;", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "()V", "finishRunnable", "Ljava/lang/Runnable;", "handler", "Landroid/os/Handler;", "finish", "", "isAncestorOf", "", "other", "isViewDisplayedOverAnother", "view", "Landroid/view/View;", "rootView", "(Landroid/view/View;Landroid/view/View;Landroid/view/View;)Ljava/lang/Boolean;", "onHandle", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "sourceEvent", "onHandleHover", "shouldBeCancelledBy", "shouldRecognizeSimultaneously", "shouldRequireToWaitForFailure", "Companion", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class HoverGestureHandler extends GestureHandler<HoverGestureHandler> {
    private static final RNViewConfigurationHelper viewConfigHelper = new RNViewConfigurationHelper();
    private Runnable finishRunnable = new Runnable() { // from class: com.swmansion.gesturehandler.core.HoverGestureHandler$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            HoverGestureHandler.m183finishRunnable$lambda0(HoverGestureHandler.this);
        }
    };
    private Handler handler;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: finishRunnable$lambda-0, reason: not valid java name */
    public static final void m183finishRunnable$lambda0(HoverGestureHandler this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final boolean isAncestorOf(GestureHandler<?> other) {
        View view = other.getView();
        while (view != null) {
            if (Intrinsics.areEqual(view, getView())) {
                return true;
            }
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return false;
    }

    static /* synthetic */ Boolean isViewDisplayedOverAnother$default(HoverGestureHandler hoverGestureHandler, View view, View view2, View view3, int i, Object obj) {
        if ((i & 4) != 0) {
            view3 = view.getRootView();
            Intrinsics.checkNotNullExpressionValue(view3, "fun isViewDisplayedOverA…   }\n\n    return null\n  }");
        }
        return hoverGestureHandler.isViewDisplayedOverAnother(view, view2, view3);
    }

    private final Boolean isViewDisplayedOverAnother(View view, View other, View rootView) {
        if (Intrinsics.areEqual(rootView, other)) {
            return true;
        }
        int i = 0;
        if (Intrinsics.areEqual(rootView, view)) {
            return false;
        }
        if (!(rootView instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) rootView;
        int childCount = viewGroup.getChildCount();
        while (i < childCount) {
            int i2 = i + 1;
            Boolean isViewDisplayedOverAnother = isViewDisplayedOverAnother(view, other, viewConfigHelper.getChildInDrawingOrderAtIndex(viewGroup, i));
            if (isViewDisplayedOverAnother != null) {
                return isViewDisplayedOverAnother;
            }
            i = i2;
        }
        return null;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public boolean shouldBeCancelledBy(GestureHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        if ((handler instanceof HoverGestureHandler) && !((HoverGestureHandler) handler).isAncestorOf(this)) {
            View view = handler.getView();
            Intrinsics.checkNotNull(view);
            View view2 = getView();
            Intrinsics.checkNotNull(view2);
            Boolean isViewDisplayedOverAnother$default = isViewDisplayedOverAnother$default(this, view, view2, null, 4, null);
            Intrinsics.checkNotNull(isViewDisplayedOverAnother$default);
            return isViewDisplayedOverAnother$default.booleanValue();
        }
        return super.shouldBeCancelledBy(handler);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public boolean shouldRequireToWaitForFailure(GestureHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        if ((handler instanceof HoverGestureHandler) && !isAncestorOf(handler) && !((HoverGestureHandler) handler).isAncestorOf(this)) {
            View view = getView();
            Intrinsics.checkNotNull(view);
            View view2 = handler.getView();
            Intrinsics.checkNotNull(view2);
            Boolean isViewDisplayedOverAnother$default = isViewDisplayedOverAnother$default(this, view, view2, null, 4, null);
            if (isViewDisplayedOverAnother$default != null) {
                return isViewDisplayedOverAnother$default.booleanValue();
            }
        }
        return super.shouldRequireToWaitForFailure(handler);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public boolean shouldRecognizeSimultaneously(GestureHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        if ((handler instanceof HoverGestureHandler) && (isAncestorOf(handler) || ((HoverGestureHandler) handler).isAncestorOf(this))) {
            return true;
        }
        return super.shouldRecognizeSimultaneously(handler);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onHandle(MotionEvent event, MotionEvent sourceEvent) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sourceEvent, "sourceEvent");
        if (event.getAction() == 0) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            this.handler = null;
            return;
        }
        if (event.getAction() != 1 || getIsWithinBounds()) {
            return;
        }
        finish();
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onHandleHover(MotionEvent event, MotionEvent sourceEvent) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sourceEvent, "sourceEvent");
        if (event.getAction() == 10) {
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            Handler handler = this.handler;
            Intrinsics.checkNotNull(handler);
            handler.postDelayed(this.finishRunnable, 4L);
            return;
        }
        if (!getIsWithinBounds()) {
            finish();
            return;
        }
        if (getState() == 0) {
            if (event.getAction() == 7 || event.getAction() == 9) {
                begin();
                activate();
            }
        }
    }

    private final void finish() {
        int state = getState();
        if (state == 0) {
            cancel();
        } else if (state == 2) {
            fail();
        } else {
            if (state != 4) {
                return;
            }
            end();
        }
    }
}
