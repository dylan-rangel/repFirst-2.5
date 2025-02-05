package com.mattermost.pasteinput;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.textinput.ReactEditText;
import com.facebook.react.views.textinput.ReactTextInputManager;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PasteInputManager.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nH\u0017J\u0014\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\u0018\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0007R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/mattermost/pasteinput/PasteInputManager;", "Lcom/facebook/react/views/textinput/ReactTextInputManager;", "reactContext", "Lcom/facebook/react/bridge/ReactContext;", "(Lcom/facebook/react/bridge/ReactContext;)V", "disableCopyPaste", "", "mCallerContext", "addEventEmitters", "", "Lcom/facebook/react/uimanager/ThemedReactContext;", "editText", "Lcom/facebook/react/views/textinput/ReactEditText;", "createViewInstance", "context", "getExportedCustomBubblingEventTypeConstants", "", "", "", "getName", "setDisableCopyPaste", "Lcom/mattermost/pasteinput/PasteInputEditText;", "disabled", "Companion", "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class PasteInputManager extends ReactTextInputManager {
    public static final String CACHE_DIR_NAME = "mmPasteInput";
    public static final String REACT_CLASS = "PasteInput";
    private boolean disableCopyPaste;
    private final ReactContext mCallerContext;

    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    public PasteInputManager(ReactContext reactContext) {
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        this.mCallerContext = reactContext;
    }

    @ReactProp(defaultBoolean = false, name = "disableCopyPaste")
    public final void setDisableCopyPaste(PasteInputEditText editText, boolean disabled) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this.disableCopyPaste = disabled;
        editText.setCustomInsertionActionModeCallback(new PasteInputActionCallback(editText, disabled));
        editText.setCustomSelectionActionModeCallback(new PasteInputActionCallback(editText, disabled));
        editText.setDisableCopyPaste(disabled);
    }

    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.ViewManager
    public ReactEditText createViewInstance(ThemedReactContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        PasteInputEditText pasteInputEditText = new PasteInputEditText(context);
        pasteInputEditText.setInputType(pasteInputEditText.getInputType() & (-131073));
        pasteInputEditText.setReturnKeyType("done");
        pasteInputEditText.setCustomInsertionActionModeCallback(new PasteInputActionCallback(pasteInputEditText, this.disableCopyPaste));
        pasteInputEditText.setCustomSelectionActionModeCallback(new PasteInputActionCallback(pasteInputEditText, this.disableCopyPaste));
        return pasteInputEditText;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, ReactEditText editText) {
        Intrinsics.checkNotNullParameter(reactContext, "reactContext");
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.addEventEmitters(reactContext, editText);
        PasteInputEditText pasteInputEditText = (PasteInputEditText) editText;
        pasteInputEditText.setOnPasteListener(new PasteInputListener(pasteInputEditText));
    }

    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.ViewManager
    public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
        Map<String, Object> exportedCustomBubblingEventTypeConstants = super.getExportedCustomBubblingEventTypeConstants();
        Intrinsics.checkNotNull(exportedCustomBubblingEventTypeConstants);
        Intrinsics.checkNotNullExpressionValue(exportedCustomBubblingEventTypeConstants, "super.getExportedCustomB…ingEventTypeConstants()!!");
        exportedCustomBubblingEventTypeConstants.put("onPaste", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPaste")));
        return exportedCustomBubblingEventTypeConstants;
    }
}
