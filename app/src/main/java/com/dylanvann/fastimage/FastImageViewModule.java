package com.dylanvann.fastimage;

import android.app.Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.BaseRequestOptions;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* loaded from: classes.dex */
class FastImageViewModule extends ReactContextBaseJavaModule {
    private static final String REACT_CLASS = "FastImageView";

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    FastImageViewModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void preload(final ReadableArray readableArray) {
        final Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            return;
        }
        currentActivity.runOnUiThread(new Runnable() { // from class: com.dylanvann.fastimage.FastImageViewModule.1
            @Override // java.lang.Runnable
            public void run() {
                Object uri;
                for (int i = 0; i < readableArray.size(); i++) {
                    ReadableMap map = readableArray.getMap(i);
                    FastImageSource imageSource = FastImageViewConverter.getImageSource(currentActivity, map);
                    RequestManager with = Glide.with(currentActivity.getApplicationContext());
                    if (imageSource.isBase64Resource()) {
                        uri = imageSource.getSource();
                    } else {
                        uri = imageSource.isResource() ? imageSource.getUri() : imageSource.getGlideUrl();
                    }
                    with.load(uri).apply((BaseRequestOptions<?>) FastImageViewConverter.getOptions(currentActivity, imageSource, map)).preload();
                }
            }
        });
    }

    @ReactMethod
    public void clearMemoryCache(final Promise promise) {
        final Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.resolve(null);
        } else {
            currentActivity.runOnUiThread(new Runnable() { // from class: com.dylanvann.fastimage.FastImageViewModule.2
                @Override // java.lang.Runnable
                public void run() {
                    Glide.get(currentActivity.getApplicationContext()).clearMemory();
                    promise.resolve(null);
                }
            });
        }
    }

    @ReactMethod
    public void clearDiskCache(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.resolve(null);
        } else {
            Glide.get(currentActivity.getApplicationContext()).clearDiskCache();
            promise.resolve(null);
        }
    }
}
