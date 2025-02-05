package com.mattermost.pasteinput;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.util.Patterns;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: PasteInputListener.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0017R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lcom/mattermost/pasteinput/PasteInputListener;", "Lcom/mattermost/pasteinput/IPasteInputListener;", "editText", "Lcom/mattermost/pasteinput/PasteInputEditText;", "(Lcom/mattermost/pasteinput/PasteInputEditText;)V", "mEditText", "getMEditText", "()Lcom/mattermost/pasteinput/PasteInputEditText;", "onPaste", "", "itemUri", "Landroid/net/Uri;", "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class PasteInputListener implements IPasteInputListener {
    private final PasteInputEditText mEditText;

    public PasteInputListener(PasteInputEditText editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this.mEditText = editText;
    }

    public final PasteInputEditText getMEditText() {
        return this.mEditText;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.mattermost.pasteinput.IPasteInputListener
    public void onPaste(Uri itemUri) {
        String fileExtensionFromUrl;
        String mimeTypeFromExtension;
        WritableMap writableMap;
        AssetFileDescriptor openAssetFileDescriptor;
        ClipData.Item itemAt;
        Intrinsics.checkNotNullParameter(itemUri, "itemUri");
        Context context = this.mEditText.getContext();
        if (context == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
        }
        ReactContext reactContext = (ReactContext) context;
        if (reactContext.getContentResolver().getType(itemUri) == null) {
            return;
        }
        String uri = itemUri.toString();
        Intrinsics.checkNotNullExpressionValue(uri, "itemUri.toString()");
        WritableMap writableMap2 = null;
        if (uri.equals("content://com.google.android.apps.docs.editors.kix.editors.clipboard")) {
            Object systemService = reactContext.getSystemService("clipboard");
            if (systemService == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.content.ClipboardManager");
            }
            ClipData primaryClip = ((ClipboardManager) systemService).getPrimaryClip();
            if (primaryClip == null || (itemAt = primaryClip.getItemAt(0)) == null) {
                return;
            }
            String htmlText = itemAt.getHtmlText();
            Matcher matcher = Patterns.WEB_URL.matcher(htmlText);
            if (matcher.find()) {
                Intrinsics.checkNotNullExpressionValue(htmlText, "htmlText");
                Intrinsics.checkNotNullExpressionValue(htmlText.substring(matcher.start(1), matcher.end()), "this as java.lang.String…ing(startIndex, endIndex)");
            }
        } else if (StringsKt.startsWith$default(uri, "http", false, 2, (Object) null)) {
            new Thread(new PasteInputFileFromUrl(reactContext, this.mEditText, uri)).start();
            return;
        }
        String realPathFromURI = RealPathUtil.INSTANCE.getRealPathFromURI(reactContext, itemUri);
        if (realPathFromURI == null || (fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(realPathFromURI)) == null || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl)) == null) {
            return;
        }
        String guessFileName = URLUtil.guessFileName(realPathFromURI, null, mimeTypeFromExtension);
        Intrinsics.checkNotNullExpressionValue(guessFileName, "guessFileName(uriString, null, mimeType)");
        try {
            openAssetFileDescriptor = reactContext.getContentResolver().openAssetFileDescriptor(itemUri, "r");
        } catch (FileNotFoundException e) {
            e = e;
        }
        if (openAssetFileDescriptor == null) {
            return;
        }
        WritableMap createMap = Arguments.createMap();
        WritableArray createArray = Arguments.createArray();
        try {
            long length = openAssetFileDescriptor.getLength();
            createMap.putString("type", mimeTypeFromExtension);
            createMap.putDouble("fileSize", length);
            createMap.putString("fileName", guessFileName);
            createMap.putString(RNFetchBlobConst.DATA_ENCODE_URI, Intrinsics.stringPlus("file://", realPathFromURI));
            createArray.pushMap(createMap);
            writableMap = createArray;
        } catch (FileNotFoundException e2) {
            e = e2;
            writableMap2 = createArray;
            WritableMap createMap2 = Arguments.createMap();
            createMap2.putString("message", e.getLocalizedMessage());
            writableMap = writableMap2;
            writableMap2 = createMap2;
            WritableMap createMap3 = Arguments.createMap();
            createMap3.putArray("data", (ReadableArray) writableMap);
            createMap3.putMap("error", writableMap2);
            ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.mEditText.getId(), "onPaste", createMap3);
        }
        WritableMap createMap32 = Arguments.createMap();
        createMap32.putArray("data", (ReadableArray) writableMap);
        createMap32.putMap("error", writableMap2);
        ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.mEditText.getId(), "onPaste", createMap32);
    }
}
