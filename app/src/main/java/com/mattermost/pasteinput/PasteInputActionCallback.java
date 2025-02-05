package com.mattermost.pasteinput;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import com.henninghall.date_picker.props.ModeProp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PasteInputActionCallback.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\u001c\u0010\u0012\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u001c\u0010\u0017\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0018\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001c\u0010\u0019\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lcom/mattermost/pasteinput/PasteInputActionCallback;", "Landroid/view/ActionMode$Callback;", "editText", "Lcom/mattermost/pasteinput/PasteInputEditText;", "disabled", "", "(Lcom/mattermost/pasteinput/PasteInputEditText;Z)V", "isDisabled", "()Z", "mEditText", "getMEditText", "()Lcom/mattermost/pasteinput/PasteInputEditText;", "disableMenus", "", "menu", "Landroid/view/Menu;", "getUriInClipboard", "Landroid/net/Uri;", "onActionItemClicked", ModeProp.name, "Landroid/view/ActionMode;", "item", "Landroid/view/MenuItem;", "onCreateActionMode", "onDestroyActionMode", "onPrepareActionMode", "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class PasteInputActionCallback implements ActionMode.Callback {
    private final boolean isDisabled;
    private final PasteInputEditText mEditText;

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode mode) {
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public PasteInputActionCallback(PasteInputEditText editText, boolean z) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this.isDisabled = z;
        this.mEditText = editText;
    }

    /* renamed from: isDisabled, reason: from getter */
    public final boolean getIsDisabled() {
        return this.isDisabled;
    }

    public final PasteInputEditText getMEditText() {
        return this.mEditText;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (!this.isDisabled) {
            return true;
        }
        disableMenus(menu);
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Uri uriInClipboard = getUriInClipboard();
        boolean z = false;
        if (item != null && item.getItemId() == 16908322) {
            z = true;
        }
        if (z && uriInClipboard != null) {
            this.mEditText.getOnPasteListener().onPaste(uriInClipboard);
            if (mode != null) {
                mode.finish();
            }
        } else {
            PasteInputEditText pasteInputEditText = this.mEditText;
            Intrinsics.checkNotNull(item);
            pasteInputEditText.onTextContextMenuItem(item.getItemId());
        }
        return true;
    }

    private final void disableMenus(Menu menu) {
        if (menu != null) {
            int size = menu.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                MenuItem item = menu.getItem(i);
                int itemId = item.getItemId();
                item.setEnabled(!(itemId == 16908322 || itemId == 16908321 || itemId == 16908320));
                i = i2;
            }
        }
    }

    private final Uri getUriInClipboard() {
        ClipData.Item itemAt;
        CharSequence text;
        Object systemService = this.mEditText.getContext().getSystemService("clipboard");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.content.ClipboardManager");
        }
        ClipData primaryClip = ((ClipboardManager) systemService).getPrimaryClip();
        if (primaryClip == null || (itemAt = primaryClip.getItemAt(0)) == null || (text = itemAt.getText()) == null || text.toString().length() > 0) {
            return null;
        }
        return itemAt.getUri();
    }
}
