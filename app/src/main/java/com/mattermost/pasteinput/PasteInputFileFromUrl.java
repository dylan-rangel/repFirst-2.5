package com.mattermost.pasteinput;

import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.events.TouchesHelper;
import com.facebook.react.views.textinput.ReactEditText;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: PasteInputFileFromUrl.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/mattermost/pasteinput/PasteInputFileFromUrl;", "Ljava/lang/Runnable;", "context", "Lcom/facebook/react/bridge/ReactContext;", TouchesHelper.TARGET_KEY, "Lcom/facebook/react/views/textinput/ReactEditText;", RNFetchBlobConst.DATA_ENCODE_URI, "", "(Lcom/facebook/react/bridge/ReactContext;Lcom/facebook/react/views/textinput/ReactEditText;Ljava/lang/String;)V", "mContext", "mTarget", "mUri", "run", "", "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class PasteInputFileFromUrl implements Runnable {
    private final ReactContext mContext;
    private final ReactEditText mTarget;
    private final String mUri;

    public PasteInputFileFromUrl(ReactContext context, ReactEditText target, String uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.mContext = context;
        this.mTarget = target;
        this.mUri = uri;
    }

    @Override // java.lang.Runnable
    public void run() {
        WritableArray writableArray;
        IOException e;
        WritableMap createMap;
        WritableMap writableMap = null;
        try {
            URLConnection openConnection = new URL(this.mUri).openConnection();
            String headerField = openConnection.getHeaderField(HttpHeaders.CONTENT_TYPE);
            long parseLong = Long.parseLong(openConnection.getHeaderField(HttpHeaders.CONTENT_LENGTH));
            String contentDisposition = openConnection.getHeaderField(HttpHeaders.CONTENT_DISPOSITION);
            Intrinsics.checkNotNullExpressionValue(contentDisposition, "contentDisposition");
            String substring = contentDisposition.substring(StringsKt.indexOf$default((CharSequence) contentDisposition, "filename=\"", 0, false, 6, (Object) null) + 10, contentDisposition.length() - 1);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
            createMap = Arguments.createMap();
            createMap.putString("type", headerField);
            createMap.putDouble("fileSize", parseLong);
            createMap.putString("fileName", substring);
            createMap.putString(RNFetchBlobConst.DATA_ENCODE_URI, this.mUri);
            writableArray = Arguments.createArray();
        } catch (IOException e2) {
            writableArray = null;
            e = e2;
        }
        try {
            writableArray.pushMap(createMap);
        } catch (IOException e3) {
            e = e3;
            WritableMap createMap2 = Arguments.createMap();
            createMap2.putString("message", e.getLocalizedMessage());
            writableMap = createMap2;
            WritableMap createMap3 = Arguments.createMap();
            createMap3.putArray("data", writableArray);
            createMap3.putMap("error", writableMap);
            ((RCTEventEmitter) this.mContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.mTarget.getId(), "onPaste", createMap3);
        }
        WritableMap createMap32 = Arguments.createMap();
        createMap32.putArray("data", writableArray);
        createMap32.putMap("error", writableMap);
        ((RCTEventEmitter) this.mContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.mTarget.getId(), "onPaste", createMap32);
    }
}
