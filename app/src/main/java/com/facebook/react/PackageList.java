package com.facebook.react;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.RNFetchBlob.RNFetchBlobPackage;
import com.dylanvann.fastimage.FastImageViewPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.henninghall.date_picker.DatePickerPackage;
import com.horcrux.svg.SvgPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.mattermost.pasteinput.PasteInputPackage;
import com.oblador.keychain.KeychainPackage;
import com.oblador.vectoricons.VectorIconsPackage;
import com.reactnative.ivpusic.imagepicker.PickerPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;
import com.reactnativecommunity.cameraroll.CameraRollPackage;
import com.reactnativecommunity.clipboard.ClipboardPackage;
import com.reactnativecommunity.cookies.CookieManagerPackage;
import com.reactnativecommunity.geolocation.GeolocationPackage;
import com.reactnativecommunity.picker.RNCPickerPackage;
import com.reactnativecommunity.toolbarandroid.ReactToolbarPackage;
import com.reactnativecommunity.webview.RNCWebViewPackage;
import com.reactnativedocumentpicker.RNDocumentPickerPackage;
import com.rnappauth.RNAppAuthPackage;
import com.swmansion.gesturehandler.RNGestureHandlerPackage;
import com.swmansion.reanimated.ReanimatedPackage;
import com.swmansion.rnscreens.RNScreensPackage;
import com.th3rdwave.safeareacontext.SafeAreaContextPackage;
import com.vinzscam.reactnativefileviewer.RNFileViewerPackage;
import com.wix.reactnativenotifications.RNNotificationsPackage;
import com.zoontek.rnpermissions.RNPermissionsPackage;
import io.invertase.firebase.app.ReactNativeFirebaseAppPackage;
import io.invertase.firebase.messaging.ReactNativeFirebaseMessagingPackage;
import io.sentry.react.RNSentryPackage;
import java.util.ArrayList;
import java.util.Arrays;
import org.linusu.RNGetRandomValuesPackage;
import org.reactnative.maskedview.RNCMaskedViewPackage;

/* loaded from: classes.dex */
public class PackageList {
    private Application application;
    private MainPackageConfig mConfig;
    private ReactNativeHost reactNativeHost;

    public PackageList(ReactNativeHost reactNativeHost) {
        this(reactNativeHost, (MainPackageConfig) null);
    }

    public PackageList(Application application) {
        this(application, (MainPackageConfig) null);
    }

    public PackageList(ReactNativeHost reactNativeHost, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = reactNativeHost;
        this.mConfig = mainPackageConfig;
    }

    public PackageList(Application application, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = null;
        this.application = application;
        this.mConfig = mainPackageConfig;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.reactNativeHost;
    }

    private Resources getResources() {
        return getApplication().getResources();
    }

    private Application getApplication() {
        ReactNativeHost reactNativeHost = this.reactNativeHost;
        return reactNativeHost == null ? this.application : reactNativeHost.getApplication();
    }

    private Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }

    public ArrayList<ReactPackage> getPackages() {
        return new ArrayList<>(Arrays.asList(new MainReactPackage(this.mConfig), new PasteInputPackage(), new CameraRollPackage(), new AsyncStoragePackage(), new ClipboardPackage(), new GeolocationPackage(), new RNCMaskedViewPackage(), new ReactToolbarPackage(), new CookieManagerPackage(), new ReactNativeFirebaseAppPackage(), new ReactNativeFirebaseMessagingPackage(), new RNCPickerPackage(), new RNSentryPackage(), new RNAppAuthPackage(), new DatePickerPackage(), new RNDeviceInfo(), new RNDocumentPickerPackage(), new FastImageViewPackage(), new RNFileViewerPackage(), new RNGestureHandlerPackage(), new RNGetRandomValuesPackage(), new PickerPackage(), new KeychainPackage(), new RNNotificationsPackage(this.reactNativeHost.getApplication()), new RNPermissionsPackage(), new ReanimatedPackage(), new SafeAreaContextPackage(), new RNScreensPackage(), new SvgPackage(), new VectorIconsPackage(), new RNCWebViewPackage(), new RNFetchBlobPackage()));
    }
}
