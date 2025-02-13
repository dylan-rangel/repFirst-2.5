package com.facebook.react.modules.bundleloader;

import com.facebook.fbreact.specs.NativeDevSplitBundleLoaderSpec;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.devsupport.interfaces.DevSplitBundleCallback;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = NativeDevSplitBundleLoaderModule.NAME)
/* loaded from: classes.dex */
public class NativeDevSplitBundleLoaderModule extends NativeDevSplitBundleLoaderSpec {
    public static final String NAME = "DevSplitBundleLoader";
    private static final String REJECTION_CODE = "E_BUNDLE_LOAD_ERROR";
    private final DevSupportManager mDevSupportManager;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    public NativeDevSplitBundleLoaderModule(ReactApplicationContext reactApplicationContext, DevSupportManager devSupportManager) {
        super(reactApplicationContext);
        this.mDevSupportManager = devSupportManager;
    }

    @Override // com.facebook.fbreact.specs.NativeDevSplitBundleLoaderSpec
    public void loadBundle(String str, final Promise promise) {
        this.mDevSupportManager.loadSplitBundleFromServer(str, new DevSplitBundleCallback() { // from class: com.facebook.react.modules.bundleloader.NativeDevSplitBundleLoaderModule.1
            @Override // com.facebook.react.devsupport.interfaces.DevSplitBundleCallback
            public void onSuccess() {
                promise.resolve(true);
            }

            @Override // com.facebook.react.devsupport.interfaces.DevSplitBundleCallback
            public void onError(String str2, Throwable th) {
                String str3;
                if (th instanceof DebugServerException) {
                    str3 = ((DebugServerException) th).getOriginalMessage();
                } else {
                    str3 = "Unknown error fetching '" + str2 + "'.";
                }
                promise.reject(NativeDevSplitBundleLoaderModule.REJECTION_CODE, str3, th);
            }
        });
    }
}
