package com.mattermost.pasteinput;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.textinput.ReactEditText;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PasteInputEditText.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\bJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006J\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/mattermost/pasteinput/PasteInputEditText;", "Lcom/facebook/react/views/textinput/ReactEditText;", "context", "Lcom/facebook/react/uimanager/ThemedReactContext;", "(Lcom/facebook/react/uimanager/ThemedReactContext;)V", "mDisabledCopyPaste", "", "mOnPasteListener", "Lcom/mattermost/pasteinput/IPasteInputListener;", "getOnPasteListener", "onCreateInputConnection", "Landroid/view/inputmethod/InputConnection;", "outAttrs", "Landroid/view/inputmethod/EditorInfo;", "setDisableCopyPaste", "", "disabled", "setOnPasteListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class PasteInputEditText extends ReactEditText {
    private boolean mDisabledCopyPaste;
    private IPasteInputListener mOnPasteListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PasteInputEditText(ThemedReactContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final void setDisableCopyPaste(boolean disabled) {
        this.mDisabledCopyPaste = disabled;
    }

    public final void setOnPasteListener(IPasteInputListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnPasteListener = listener;
    }

    public final IPasteInputListener getOnPasteListener() {
        IPasteInputListener iPasteInputListener = this.mOnPasteListener;
        if (iPasteInputListener != null) {
            return iPasteInputListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mOnPasteListener");
        return null;
    }

    @Override // com.facebook.react.views.textinput.ReactEditText, androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Intrinsics.checkNotNullParameter(outAttrs, "outAttrs");
        InputConnection onCreateInputConnection = super.onCreateInputConnection(outAttrs);
        EditorInfoCompat.setContentMimeTypes(outAttrs, new String[]{"*/*"});
        InputConnectionCompat.OnCommitContentListener onCommitContentListener = new InputConnectionCompat.OnCommitContentListener() { // from class: com.mattermost.pasteinput.PasteInputEditText$$ExternalSyntheticLambda0
            @Override // androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
            public final boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
                boolean m168onCreateInputConnection$lambda0;
                m168onCreateInputConnection$lambda0 = PasteInputEditText.m168onCreateInputConnection$lambda0(PasteInputEditText.this, inputContentInfoCompat, i, bundle);
                return m168onCreateInputConnection$lambda0;
            }
        };
        Intrinsics.checkNotNull(onCreateInputConnection);
        InputConnection createWrapper = InputConnectionCompat.createWrapper(onCreateInputConnection, outAttrs, onCommitContentListener);
        Intrinsics.checkNotNullExpressionValue(createWrapper, "createWrapper(ic!!, outAttrs, callback)");
        return createWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onCreateInputConnection$lambda-0, reason: not valid java name */
    public static final boolean m168onCreateInputConnection$lambda0(PasteInputEditText this$0, InputContentInfoCompat inputContentInfo, int i, Bundle bundle) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(inputContentInfo, "inputContentInfo");
        boolean z = (i & 1) != 0;
        if (Build.VERSION.SDK_INT >= 25 && z) {
            try {
                inputContentInfo.requestPermission();
            } catch (Exception unused) {
                return false;
            }
        }
        if (!this$0.mDisabledCopyPaste) {
            IPasteInputListener onPasteListener = this$0.getOnPasteListener();
            Uri contentUri = inputContentInfo.getContentUri();
            Intrinsics.checkNotNullExpressionValue(contentUri, "inputContentInfo.contentUri");
            onPasteListener.onPaste(contentUri);
        }
        return true;
    }
}
