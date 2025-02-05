package com.vinzscam.reactnativefileviewer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.File;

/* loaded from: classes.dex */
public class RNFileViewerModule extends ReactContextBaseJavaModule {
    private static final String DISMISS_EVENT = "RNFileViewerDidDismiss";
    private static final String OPEN_EVENT = "RNFileViewerDidOpen";
    private static final Integer RN_FILE_VIEWER_REQUEST = 33341;
    private static final String SHOW_OPEN_WITH_DIALOG = "showOpenWithDialog";
    private static final String SHOW_STORE_SUGGESTIONS = "showAppsSuggestions";
    private final ActivityEventListener mActivityEventListener;
    private final ReactApplicationContext reactContext;

    @ReactMethod
    public void addListener(String str) {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNFileViewer";
    }

    @ReactMethod
    public void removeListeners(Integer num) {
    }

    public RNFileViewerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        BaseActivityEventListener baseActivityEventListener = new BaseActivityEventListener() { // from class: com.vinzscam.reactnativefileviewer.RNFileViewerModule.1
            @Override // com.facebook.react.bridge.BaseActivityEventListener, com.facebook.react.bridge.ActivityEventListener
            public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
                RNFileViewerModule.this.sendEvent(RNFileViewerModule.DISMISS_EVENT, Integer.valueOf(i - RNFileViewerModule.RN_FILE_VIEWER_REQUEST.intValue()), null);
            }
        };
        this.mActivityEventListener = baseActivityEventListener;
        this.reactContext = reactApplicationContext;
        reactApplicationContext.addActivityEventListener(baseActivityEventListener);
    }

    @ReactMethod
    public void open(String str, Integer num, ReadableMap readableMap) {
        Uri uriForFile;
        Boolean valueOf = Boolean.valueOf(readableMap.hasKey(SHOW_OPEN_WITH_DIALOG) ? readableMap.getBoolean(SHOW_OPEN_WITH_DIALOG) : false);
        Boolean valueOf2 = Boolean.valueOf(readableMap.hasKey(SHOW_STORE_SUGGESTIONS) ? readableMap.getBoolean(SHOW_STORE_SUGGESTIONS) : false);
        if (str.startsWith(RNFetchBlobConst.FILE_PREFIX_CONTENT)) {
            uriForFile = Uri.parse(str);
        } else {
            File file = new File(str);
            Activity currentActivity = getCurrentActivity();
            if (currentActivity == null) {
                sendEvent(OPEN_EVENT, num, "Activity doesn't exist");
                return;
            }
            try {
                uriForFile = androidx.core.content.FileProvider.getUriForFile(currentActivity, currentActivity.getPackageName() + ".provider", file);
            } catch (IllegalArgumentException e) {
                sendEvent(OPEN_EVENT, num, e.getMessage());
                return;
            }
        }
        if (uriForFile == null) {
            sendEvent(OPEN_EVENT, num, "Invalid file");
            return;
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str).toLowerCase());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(1);
        intent.setDataAndType(uriForFile, mimeTypeFromExtension);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        Intent createChooser = valueOf.booleanValue() ? Intent.createChooser(intent, "Open with") : intent;
        if (intent.resolveActivity(getCurrentActivity().getPackageManager()) != null) {
            try {
                getCurrentActivity().startActivityForResult(createChooser, num.intValue() + RN_FILE_VIEWER_REQUEST.intValue());
                sendEvent(OPEN_EVENT, num, null);
                return;
            } catch (Exception e2) {
                sendEvent(OPEN_EVENT, num, e2.getMessage());
                return;
            }
        }
        try {
            if (valueOf2.booleanValue()) {
                if (mimeTypeFromExtension == null) {
                    throw new Exception("It wasn't possible to detect the type of the file");
                }
                getCurrentActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=" + mimeTypeFromExtension + "&c=apps")));
            }
            throw new Exception("No app associated with this mime type");
        } catch (Exception e3) {
            sendEvent(OPEN_EVENT, num, e3.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(String str, Integer num, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", num.intValue());
        if (str2 != null) {
            createMap.putString("error", str2);
        }
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, createMap);
    }
}
