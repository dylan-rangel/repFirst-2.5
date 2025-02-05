package com.swmansion.gesturehandler.react;

import android.util.SparseArray;
import android.view.View;
import com.facebook.react.bridge.UiThreadUtil;
import com.swmansion.gesturehandler.core.GestureHandler;
import com.swmansion.gesturehandler.core.GestureHandlerRegistry;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RNGestureHandlerRegistry.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0002J\u0006\u0010\u0012\u001a\u00020\u0010J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0005J\u0014\u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u0005J\u001c\u0010\u0015\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0007\u0018\u00010\t2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001a\u0010\u0018\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0007\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u0005J\u0012\u0010\u0019\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0007J\u001c\u0010\u001a\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/swmansion/gesturehandler/react/RNGestureHandlerRegistry;", "Lcom/swmansion/gesturehandler/core/GestureHandlerRegistry;", "()V", "attachedTo", "Landroid/util/SparseArray;", "", "handlers", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "handlersForView", "Ljava/util/ArrayList;", "attachHandlerToView", "", "handlerTag", "viewTag", "actionType", "detachHandler", "", "handler", "dropAllHandlers", "dropHandler", "getHandler", "getHandlersForView", "view", "Landroid/view/View;", "getHandlersForViewWithTag", "registerHandler", "registerHandlerForViewWithTag", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RNGestureHandlerRegistry implements GestureHandlerRegistry {
    private final SparseArray<GestureHandler<?>> handlers = new SparseArray<>();
    private final SparseArray<Integer> attachedTo = new SparseArray<>();
    private final SparseArray<ArrayList<GestureHandler<?>>> handlersForView = new SparseArray<>();

    public final synchronized void registerHandler(GestureHandler<?> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.handlers.put(handler.getTag(), handler);
    }

    public final synchronized GestureHandler<?> getHandler(int handlerTag) {
        return this.handlers.get(handlerTag);
    }

    public final synchronized boolean attachHandlerToView(int handlerTag, int viewTag, int actionType) {
        boolean z;
        GestureHandler<?> handler = this.handlers.get(handlerTag);
        if (handler == null) {
            z = false;
        } else {
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            detachHandler(handler);
            handler.setActionType(actionType);
            registerHandlerForViewWithTag(viewTag, handler);
            z = true;
        }
        return z;
    }

    private final synchronized void registerHandlerForViewWithTag(int viewTag, GestureHandler<?> handler) {
        if (!(this.attachedTo.get(handler.getTag()) == null)) {
            throw new IllegalStateException(("Handler " + handler + " already attached").toString());
        }
        this.attachedTo.put(handler.getTag(), Integer.valueOf(viewTag));
        ArrayList<GestureHandler<?>> arrayList = this.handlersForView.get(viewTag);
        if (arrayList == null) {
            ArrayList<GestureHandler<?>> arrayList2 = new ArrayList<>(1);
            arrayList2.add(handler);
            this.handlersForView.put(viewTag, arrayList2);
        } else {
            synchronized (arrayList) {
                arrayList.add(handler);
            }
        }
    }

    private final synchronized void detachHandler(final GestureHandler<?> handler) {
        Integer num = this.attachedTo.get(handler.getTag());
        if (num != null) {
            this.attachedTo.remove(handler.getTag());
            ArrayList<GestureHandler<?>> arrayList = this.handlersForView.get(num.intValue());
            if (arrayList != null) {
                synchronized (arrayList) {
                    arrayList.remove(handler);
                }
                if (arrayList.size() == 0) {
                    this.handlersForView.remove(num.intValue());
                }
            }
        }
        if (handler.getView() != null) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.gesturehandler.react.RNGestureHandlerRegistry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RNGestureHandlerRegistry.m191detachHandler$lambda4(GestureHandler.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: detachHandler$lambda-4, reason: not valid java name */
    public static final void m191detachHandler$lambda4(GestureHandler handler) {
        Intrinsics.checkNotNullParameter(handler, "$handler");
        handler.cancel();
    }

    public final synchronized void dropHandler(int handlerTag) {
        GestureHandler<?> gestureHandler = this.handlers.get(handlerTag);
        if (gestureHandler != null) {
            detachHandler(gestureHandler);
            this.handlers.remove(handlerTag);
        }
    }

    public final synchronized void dropAllHandlers() {
        this.handlers.clear();
        this.attachedTo.clear();
        this.handlersForView.clear();
    }

    public final synchronized ArrayList<GestureHandler<?>> getHandlersForViewWithTag(int viewTag) {
        return this.handlersForView.get(viewTag);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandlerRegistry
    public synchronized ArrayList<GestureHandler<?>> getHandlersForView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return getHandlersForViewWithTag(view.getId());
    }
}
