package com.swmansion.gesturehandler.react;

import android.util.SparseArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.swmansion.gesturehandler.core.GestureHandler;
import com.swmansion.gesturehandler.core.GestureHandlerInteractionController;
import com.swmansion.gesturehandler.core.NativeViewGestureHandler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RNGestureHandlerInteractionManager.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\tJ \u0010\u0015\u001a\u00020\u00162\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J \u0010\u0018\u001a\u00020\u00162\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J \u0010\u0019\u001a\u00020\u00162\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J \u0010\u001a\u001a\u00020\u00162\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/swmansion/gesturehandler/react/RNGestureHandlerInteractionManager;", "Lcom/swmansion/gesturehandler/core/GestureHandlerInteractionController;", "()V", "blockingRelations", "Landroid/util/SparseArray;", "", "simultaneousRelations", "waitForRelations", "configureInteractions", "", "handler", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "config", "Lcom/facebook/react/bridge/ReadableMap;", "convertHandlerTagsArray", "key", "", "dropRelationsForHandlerWithTag", "handlerTag", "", "reset", "shouldHandlerBeCancelledBy", "", "otherHandler", "shouldRecognizeSimultaneously", "shouldRequireHandlerToWaitForFailure", "shouldWaitForHandlerFailure", "Companion", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RNGestureHandlerInteractionManager implements GestureHandlerInteractionController {
    private static final String KEY_BLOCKS_HANDLERS = "blocksHandlers";
    private static final String KEY_SIMULTANEOUS_HANDLERS = "simultaneousHandlers";
    private static final String KEY_WAIT_FOR = "waitFor";
    private final SparseArray<int[]> waitForRelations = new SparseArray<>();
    private final SparseArray<int[]> simultaneousRelations = new SparseArray<>();
    private final SparseArray<int[]> blockingRelations = new SparseArray<>();

    public final void dropRelationsForHandlerWithTag(int handlerTag) {
        this.waitForRelations.remove(handlerTag);
        this.simultaneousRelations.remove(handlerTag);
    }

    private final int[] convertHandlerTagsArray(ReadableMap config, String key) {
        ReadableArray array = config.getArray(key);
        Intrinsics.checkNotNull(array);
        Intrinsics.checkNotNullExpressionValue(array, "config.getArray(key)!!");
        int size = array.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = array.getInt(i);
        }
        return iArr;
    }

    public final void configureInteractions(GestureHandler<?> handler, ReadableMap config) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(config, "config");
        handler.setInteractionController(this);
        if (config.hasKey(KEY_WAIT_FOR)) {
            this.waitForRelations.put(handler.getTag(), convertHandlerTagsArray(config, KEY_WAIT_FOR));
        }
        if (config.hasKey(KEY_SIMULTANEOUS_HANDLERS)) {
            this.simultaneousRelations.put(handler.getTag(), convertHandlerTagsArray(config, KEY_SIMULTANEOUS_HANDLERS));
        }
        if (config.hasKey(KEY_BLOCKS_HANDLERS)) {
            this.blockingRelations.put(handler.getTag(), convertHandlerTagsArray(config, KEY_BLOCKS_HANDLERS));
        }
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandlerInteractionController
    public boolean shouldWaitForHandlerFailure(GestureHandler<?> handler, GestureHandler<?> otherHandler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(otherHandler, "otherHandler");
        int[] iArr = this.waitForRelations.get(handler.getTag());
        if (iArr == null) {
            return false;
        }
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            i++;
            if (i2 == otherHandler.getTag()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandlerInteractionController
    public boolean shouldRequireHandlerToWaitForFailure(GestureHandler<?> handler, GestureHandler<?> otherHandler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(otherHandler, "otherHandler");
        int[] iArr = this.blockingRelations.get(handler.getTag());
        if (iArr == null) {
            return false;
        }
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            i++;
            if (i2 == otherHandler.getTag()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandlerInteractionController
    public boolean shouldHandlerBeCancelledBy(GestureHandler<?> handler, GestureHandler<?> otherHandler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(otherHandler, "otherHandler");
        if (otherHandler instanceof NativeViewGestureHandler) {
            return ((NativeViewGestureHandler) otherHandler).getDisallowInterruption();
        }
        return false;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandlerInteractionController
    public boolean shouldRecognizeSimultaneously(GestureHandler<?> handler, GestureHandler<?> otherHandler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(otherHandler, "otherHandler");
        int[] iArr = this.simultaneousRelations.get(handler.getTag());
        if (iArr == null) {
            return false;
        }
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            i++;
            if (i2 == otherHandler.getTag()) {
                return true;
            }
        }
        return false;
    }

    public final void reset() {
        this.waitForRelations.clear();
        this.simultaneousRelations.clear();
    }
}
