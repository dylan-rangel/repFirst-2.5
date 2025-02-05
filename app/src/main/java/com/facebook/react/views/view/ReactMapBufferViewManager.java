package com.facebook.react.views.view;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.mapbuffer.MapBuffer;
import com.facebook.react.common.mapbuffer.ReadableMapBuffer;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReactMapBufferViewManager.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH\u0016J\"\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\"\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00162\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J0\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\fH\u0016J\u001a\u0010$\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\n2\b\u0010%\u001a\u0004\u0018\u00010\u0010H\u0016J\u001a\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J&\u0010(\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u0018\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/facebook/react/views/view/ReactMapBufferViewManager;", "Lcom/facebook/react/views/view/ReactViewManagerWrapper;", "()V", "viewGroupManager", "Lcom/facebook/react/uimanager/ViewGroupManager;", "getViewGroupManager", "()Lcom/facebook/react/uimanager/ViewGroupManager;", "viewManager", "Lcom/facebook/react/views/view/ReactViewManager;", "createView", "Landroid/view/View;", "reactTag", "", "reactContext", "Lcom/facebook/react/uimanager/ThemedReactContext;", "props", "", "stateWrapper", "Lcom/facebook/react/uimanager/StateWrapper;", "jsResponderHandler", "Lcom/facebook/react/touch/JSResponderHandler;", "getName", "", "onDropViewInstance", "", "view", "receiveCommand", "root", "commandId", "args", "Lcom/facebook/react/bridge/ReadableArray;", "setPadding", ViewProps.LEFT, ViewProps.TOP, ViewProps.RIGHT, ViewProps.BOTTOM, "updateExtraData", "extraData", "updateProperties", "viewToUpdate", "updateState", "ReactAndroid_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ReactMapBufferViewManager implements ReactViewManagerWrapper {
    public static final ReactMapBufferViewManager INSTANCE = new ReactMapBufferViewManager();
    private static final ReactViewManager viewManager = new ReactViewManager();

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public Object updateState(View view, Object props, StateWrapper stateWrapper) {
        Intrinsics.checkNotNullParameter(view, "view");
        return null;
    }

    private ReactMapBufferViewManager() {
    }

    /* JADX WARN: Type inference failed for: r8v1, types: [android.view.View, java.lang.Object] */
    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public View createView(int reactTag, ThemedReactContext reactContext, Object props, StateWrapper stateWrapper, JSResponderHandler jsResponderHandler) {
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        Intrinsics.checkNotNullParameter(jsResponderHandler, "jsResponderHandler");
        ?? createView = viewManager.createView(reactTag, reactContext, props instanceof ReactStylesDiffMap ? (ReactStylesDiffMap) props : null, stateWrapper, jsResponderHandler);
        ReactViewGroup view = (ReactViewGroup) createView;
        if (props instanceof ReadableMapBuffer) {
            ReactMapBufferViewManager reactMapBufferViewManager = INSTANCE;
            Intrinsics.checkNotNullExpressionValue(view, "view");
            reactMapBufferViewManager.updateProperties(view, props);
        }
        Intrinsics.checkNotNullExpressionValue(createView, "viewManager\n          .c…            }\n          }");
        return createView;
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void updateProperties(View viewToUpdate, Object props) {
        Intrinsics.checkNotNullParameter(viewToUpdate, "viewToUpdate");
        if (!(props instanceof ReadableMapBuffer)) {
            viewManager.updateProperties((ReactViewGroup) viewToUpdate, props instanceof ReactStylesDiffMap ? (ReactStylesDiffMap) props : null);
        } else {
            ReactMapBufferPropSetter.INSTANCE.setProps((ReactViewGroup) viewToUpdate, viewManager, (MapBuffer) props);
        }
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void receiveCommand(View root, String commandId, ReadableArray args) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(commandId, "commandId");
        viewManager.receiveCommand((ReactViewGroup) root, commandId, args);
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void receiveCommand(View root, int commandId, ReadableArray args) {
        Intrinsics.checkNotNullParameter(root, "root");
        viewManager.receiveCommand((ReactViewGroup) root, commandId, args);
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void setPadding(View view, int left, int top, int right, int bottom) {
        Intrinsics.checkNotNullParameter(view, "view");
        viewManager.setPadding((ReactViewGroup) view, left, top, right, bottom);
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void updateExtraData(View root, Object extraData) {
        Intrinsics.checkNotNullParameter(root, "root");
        viewManager.updateExtraData((ReactViewManager) root, extraData);
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public void onDropViewInstance(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        viewManager.onDropViewInstance((ReactViewGroup) view);
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public String getName() {
        String name = viewManager.getName();
        Intrinsics.checkNotNullExpressionValue(name, "viewManager.name");
        return name;
    }

    @Override // com.facebook.react.views.view.ReactViewManagerWrapper
    public ViewGroupManager<?> getViewGroupManager() {
        return viewManager;
    }
}
