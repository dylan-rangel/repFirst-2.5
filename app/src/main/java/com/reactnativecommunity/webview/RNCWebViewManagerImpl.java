package com.reactnativecommunity.webview;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.core.view.ViewCompat;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.google.common.net.HttpHeaders;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RNCWebViewManagerImpl.kt */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b1\u0018\u0000 ~2\u00020\u0001:\u0001~B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u0002022\u0006\u0010/\u001a\u000200J\u0016\u00101\u001a\u0002022\u0006\u0010/\u001a\u0002002\u0006\u00103\u001a\u00020.J\u0014\u00104\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0006\u0018\u000105J\n\u00106\u001a\u0004\u0018\u00010\u0004H\u0002J\n\u00107\u001a\u0004\u0018\u00010\u0004H\u0002J\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u000202J\u001e\u0010;\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010<\u001a\u00020\u00042\u0006\u0010=\u001a\u00020>J\u0016\u0010?\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010@\u001a\u00020$J\u0016\u0010A\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010C\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010D\u001a\u00020$J\u0016\u0010E\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010F\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0018\u0010H\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010I\u001a\u0004\u0018\u00010\u0004J\u0018\u0010J\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010K\u001a\u0004\u0018\u00010\u0004J\u0018\u0010L\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010M\u001a\u0004\u0018\u00010NJ\u0016\u0010O\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0018\u0010P\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010Q\u001a\u0004\u0018\u00010\u0004J\u0016\u0010R\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0010\u0010S\u001a\u0002092\b\u0010B\u001a\u0004\u0018\u00010\u0004J\u0016\u0010T\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0016\u0010U\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010V\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010W\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010X\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0018\u0010Y\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010Z\u001a\u0004\u0018\u00010\u0004J\u0018\u0010[\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010B\u001a\u0004\u0018\u00010\u0004J\u0016\u0010\\\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010]\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0018\u0010^\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010B\u001a\u0004\u0018\u00010\u0004J\u0016\u0010_\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010`\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0010\u0010a\u001a\u0002092\b\u0010B\u001a\u0004\u0018\u00010\u0004J\u0016\u0010b\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010c\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020>J\u0016\u0010d\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0018\u0010e\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010B\u001a\u0004\u0018\u00010\u0004J\u0016\u0010f\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020\u0006J\u0018\u0010g\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010h\u001a\u0004\u0018\u00010\u0004J\u0016\u0010i\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0018\u0010j\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010k\u001a\u0004\u0018\u00010\u0004J\u0016\u0010l\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010m\u001a\u00020$J\u0016\u0010n\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010o\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010p\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010q\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010r\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\u0016\u0010s\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020$J\"\u0010t\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010u\u001a\u0004\u0018\u00010N2\b\b\u0002\u0010v\u001a\u00020$J\u0016\u0010w\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010B\u001a\u00020\u0006J\u0016\u0010x\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0018\u0010y\u001a\u0002092\u0006\u0010:\u001a\u0002022\b\u0010z\u001a\u0004\u0018\u00010\u0004J\u0010\u0010{\u001a\u0002092\u0006\u0010:\u001a\u000202H\u0002J\u0016\u0010|\u001a\u0002092\u0006\u0010:\u001a\u0002022\u0006\u0010G\u001a\u00020$J\u0010\u0010}\u001a\u0002092\u0006\u00103\u001a\u00020.H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0013\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0014\u0010\u0015\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0014\u0010\u0017\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\bR\u0014\u0010\u001b\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\bR\u000e\u0010\u001d\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u007f"}, d2 = {"Lcom/reactnativecommunity/webview/RNCWebViewManagerImpl;", "", "()V", "BLANK_URL", "", "COMMAND_CLEAR_CACHE", "", "getCOMMAND_CLEAR_CACHE", "()I", "COMMAND_CLEAR_FORM_DATA", "getCOMMAND_CLEAR_FORM_DATA", "COMMAND_CLEAR_HISTORY", "getCOMMAND_CLEAR_HISTORY", "COMMAND_FOCUS", "getCOMMAND_FOCUS", "COMMAND_GO_BACK", "getCOMMAND_GO_BACK", "COMMAND_GO_FORWARD", "getCOMMAND_GO_FORWARD", "COMMAND_INJECT_JAVASCRIPT", "getCOMMAND_INJECT_JAVASCRIPT", "COMMAND_LOAD_URL", "getCOMMAND_LOAD_URL", "COMMAND_POST_MESSAGE", "getCOMMAND_POST_MESSAGE", "COMMAND_RELOAD", "getCOMMAND_RELOAD", "COMMAND_STOP_LOADING", "getCOMMAND_STOP_LOADING", "DEFAULT_DOWNLOADING_MESSAGE", "DEFAULT_LACK_PERMISSION_TO_DOWNLOAD_MESSAGE", "HTML_ENCODING", "HTML_MIME_TYPE", "HTTP_METHOD_POST", "TAG", "mAllowsFullscreenVideo", "", "mAllowsProtectedMedia", "mDownloadingMessage", "mHasOnOpenWindowEvent", "mLackPermissionToDownloadMessage", "mUserAgent", "mUserAgentWithApplicationName", "mWebViewConfig", "Lcom/reactnativecommunity/webview/RNCWebViewConfig;", "createRNCWebViewInstance", "Lcom/reactnativecommunity/webview/RNCWebView;", "context", "Lcom/facebook/react/uimanager/ThemedReactContext;", "createViewInstance", "Lcom/reactnativecommunity/webview/RNCWebViewWrapper;", "webView", "getCommandsMap", "", "getDownloadingMessageOrDefault", "getLackPermissionToDownloadMessageOrDefault", "onDropViewInstance", "", "viewWrapper", "receiveCommand", "commandId", "args", "Lcom/facebook/react/bridge/ReadableArray;", "setAllowFileAccess", "allowFileAccess", "setAllowFileAccessFromFileURLs", "value", "setAllowUniversalAccessFromFileURLs", "allow", "setAllowsFullscreenVideo", "setAllowsProtectedMedia", ViewProps.ENABLED, "setAndroidLayerType", "layerTypeString", "setApplicationNameForUserAgent", "applicationName", "setBasicAuthCredential", "credential", "Lcom/facebook/react/bridge/ReadableMap;", "setCacheEnabled", "setCacheMode", "cacheModeString", "setDomStorageEnabled", "setDownloadingMessage", "setForceDarkOn", "setGeolocationEnabled", "setHasOnOpenWindowEvent", "setHasOnScroll", "setIncognito", "setInjectedJavaScript", "injectedJavaScript", "setInjectedJavaScriptBeforeContentLoaded", "setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly", "setInjectedJavaScriptForMainFrameOnly", "setInjectedJavaScriptObject", "setJavaScriptCanOpenWindowsAutomatically", "setJavaScriptEnabled", "setLackPermissionToDownloadMessage", "setMediaPlaybackRequiresUserAction", "setMenuCustomItems", "setMessagingEnabled", "setMessagingModuleName", "setMinimumFontSize", "setMixedContentMode", "mixedContentMode", "setNestedScrollEnabled", "setOverScrollMode", "overScrollModeString", "setSaveFormDataDisabled", "disabled", "setScalesPageToFit", "setSetBuiltInZoomControls", "setSetDisplayZoomControls", "setSetSupportMultipleWindows", "setShowsHorizontalScrollIndicator", "setShowsVerticalScrollIndicator", "setSource", "source", "newArch", "setTextZoom", "setThirdPartyCookiesEnabled", "setUserAgent", "userAgent", "setUserAgentString", "setWebviewDebuggingEnabled", "setupWebChromeClient", "Companion", "react-native-webview_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class RNCWebViewManagerImpl {
    public static final String NAME = "RNCWebView";
    private boolean mAllowsFullscreenVideo;
    private boolean mAllowsProtectedMedia;
    private String mDownloadingMessage;
    private boolean mHasOnOpenWindowEvent;
    private String mLackPermissionToDownloadMessage;
    private String mUserAgent;
    private String mUserAgentWithApplicationName;
    private final String TAG = "RNCWebViewManagerImpl";
    private RNCWebViewConfig mWebViewConfig = new RNCWebViewConfig() { // from class: com.reactnativecommunity.webview.RNCWebViewManagerImpl$$ExternalSyntheticLambda1
        @Override // com.reactnativecommunity.webview.RNCWebViewConfig
        public final void configWebView(WebView webView) {
            RNCWebViewManagerImpl.m178mWebViewConfig$lambda0(webView);
        }
    };
    private final String HTML_ENCODING = "UTF-8";
    private final String HTML_MIME_TYPE = "text/html";
    private final String HTTP_METHOD_POST = ShareTarget.METHOD_POST;
    private final String BLANK_URL = "about:blank";
    private final String DEFAULT_DOWNLOADING_MESSAGE = "Downloading";
    private final String DEFAULT_LACK_PERMISSION_TO_DOWNLOAD_MESSAGE = "Cannot download files as permission was denied. Please provide permission to write to storage, in order to download files.";
    private final int COMMAND_GO_BACK = 1;
    private final int COMMAND_GO_FORWARD = 2;
    private final int COMMAND_RELOAD = 3;
    private final int COMMAND_STOP_LOADING = 4;
    private final int COMMAND_POST_MESSAGE = 5;
    private final int COMMAND_INJECT_JAVASCRIPT = 6;
    private final int COMMAND_LOAD_URL = 7;
    private final int COMMAND_FOCUS = 8;
    private final int COMMAND_CLEAR_FORM_DATA = 1000;
    private final int COMMAND_CLEAR_CACHE = 1001;
    private final int COMMAND_CLEAR_HISTORY = 1002;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: mWebViewConfig$lambda-0, reason: not valid java name */
    public static final void m178mWebViewConfig$lambda0(WebView webView) {
    }

    public final RNCWebView createRNCWebViewInstance(ThemedReactContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new RNCWebView(context);
    }

    public final RNCWebViewWrapper createViewInstance(ThemedReactContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return createViewInstance(context, createRNCWebViewInstance(context));
    }

    public final RNCWebViewWrapper createViewInstance(ThemedReactContext context, final RNCWebView webView) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(webView, "webView");
        setupWebChromeClient(webView);
        context.addLifecycleEventListener(webView);
        this.mWebViewConfig.configWebView(webView);
        WebSettings settings = webView.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "webView.settings");
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setMixedContentMode(1);
        webView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        webView.setDownloadListener(new DownloadListener() { // from class: com.reactnativecommunity.webview.RNCWebViewManagerImpl$$ExternalSyntheticLambda0
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                RNCWebViewManagerImpl.m177createViewInstance$lambda1(RNCWebView.this, this, str, str2, str3, str4, j);
            }
        });
        return new RNCWebViewWrapper(context, webView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: createViewInstance$lambda-1, reason: not valid java name */
    public static final void m177createViewInstance$lambda1(RNCWebView webView, RNCWebViewManagerImpl this$0, String str, String str2, String str3, String str4, long j) {
        Intrinsics.checkNotNullParameter(webView, "$webView");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        webView.setIgnoreErrFailedForThisURL(str);
        RNCWebViewModule rNCWebViewModule = (RNCWebViewModule) webView.getThemedReactContext().getNativeModule(RNCWebViewModule.class);
        if (rNCWebViewModule == null) {
            return;
        }
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
            String fileName = URLUtil.guessFileName(str, str3, str4);
            Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
            String replace = RNCWebViewManagerImplKt.getInvalidCharRegex().replace(fileName, "_");
            String stringPlus = Intrinsics.stringPlus("Downloading ", replace);
            try {
                URL url = new URL(str);
                request.addRequestHeader("Cookie", CookieManager.getInstance().getCookie(url.getProtocol() + "://" + ((Object) url.getHost())));
            } catch (MalformedURLException e) {
                Log.w(this$0.TAG, "Error getting cookie for DownloadManager", e);
            }
            request.addRequestHeader(HttpHeaders.USER_AGENT, str2);
            request.setTitle(replace);
            request.setDescription(stringPlus);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, replace);
            rNCWebViewModule.setDownloadRequest(request);
            if (rNCWebViewModule.grantFileDownloaderPermissions(this$0.getDownloadingMessageOrDefault(), this$0.getLackPermissionToDownloadMessageOrDefault())) {
                rNCWebViewModule.downloadFile(this$0.getDownloadingMessageOrDefault());
            }
        } catch (IllegalArgumentException e2) {
            Log.w(this$0.TAG, "Unsupported URI, aborting download", e2);
        }
    }

    private final void setupWebChromeClient(final RNCWebView webView) {
        final Activity currentActivity = webView.getThemedReactContext().getCurrentActivity();
        if (this.mAllowsFullscreenVideo && currentActivity != null) {
            final int requestedOrientation = currentActivity.getRequestedOrientation();
            RNCWebChromeClient rNCWebChromeClient = new RNCWebChromeClient(currentActivity, requestedOrientation) { // from class: com.reactnativecommunity.webview.RNCWebViewManagerImpl$setupWebChromeClient$webChromeClient$1
                final /* synthetic */ Activity $activity;
                final /* synthetic */ int $initialRequestedOrientation;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(RNCWebView.this);
                    this.$activity = currentActivity;
                    this.$initialRequestedOrientation = requestedOrientation;
                }

                @Override // android.webkit.WebChromeClient
                public Bitmap getDefaultVideoPoster() {
                    return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
                }

                @Override // android.webkit.WebChromeClient
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                    Intrinsics.checkNotNullParameter(view, "view");
                    Intrinsics.checkNotNullParameter(callback, "callback");
                    if (this.mVideoView != null) {
                        callback.onCustomViewHidden();
                        return;
                    }
                    this.mVideoView = view;
                    this.mCustomViewCallback = callback;
                    this.$activity.setRequestedOrientation(-1);
                    this.mVideoView.setSystemUiVisibility(7942);
                    this.$activity.getWindow().setFlags(512, 512);
                    this.mVideoView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                    ViewGroup rootView = getRootView();
                    rootView.addView(this.mVideoView, RNCWebChromeClient.FULLSCREEN_LAYOUT_PARAMS);
                    if (rootView.getRootView() != this.mWebView.getRootView()) {
                        this.mWebView.getRootView().setVisibility(8);
                    } else {
                        this.mWebView.setVisibility(8);
                    }
                    this.mWebView.getThemedReactContext().addLifecycleEventListener(this);
                }

                @Override // android.webkit.WebChromeClient
                public void onHideCustomView() {
                    if (this.mVideoView == null) {
                        return;
                    }
                    ViewGroup rootView = getRootView();
                    if (rootView.getRootView() != this.mWebView.getRootView()) {
                        this.mWebView.getRootView().setVisibility(0);
                    } else {
                        this.mWebView.setVisibility(0);
                    }
                    this.$activity.getWindow().clearFlags(512);
                    rootView.removeView(this.mVideoView);
                    this.mCustomViewCallback.onCustomViewHidden();
                    this.mVideoView = null;
                    this.mCustomViewCallback = null;
                    this.$activity.setRequestedOrientation(this.$initialRequestedOrientation);
                    this.mWebView.getThemedReactContext().removeLifecycleEventListener(this);
                }
            };
            rNCWebChromeClient.setAllowsProtectedMedia(this.mAllowsProtectedMedia);
            rNCWebChromeClient.setHasOnOpenWindowEvent(this.mHasOnOpenWindowEvent);
            webView.setWebChromeClient(rNCWebChromeClient);
            return;
        }
        RNCWebChromeClient rNCWebChromeClient2 = (RNCWebChromeClient) webView.getWebChromeClient();
        if (rNCWebChromeClient2 != null) {
            rNCWebChromeClient2.onHideCustomView();
        }
        RNCWebChromeClient rNCWebChromeClient3 = new RNCWebChromeClient() { // from class: com.reactnativecommunity.webview.RNCWebViewManagerImpl$setupWebChromeClient$1
            {
                super(RNCWebView.this);
            }

            @Override // android.webkit.WebChromeClient
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
            }
        };
        RNCWebViewManagerImpl$setupWebChromeClient$1 rNCWebViewManagerImpl$setupWebChromeClient$1 = (RNCWebViewManagerImpl$setupWebChromeClient$1) rNCWebChromeClient3;
        rNCWebViewManagerImpl$setupWebChromeClient$1.setAllowsProtectedMedia(this.mAllowsProtectedMedia);
        rNCWebViewManagerImpl$setupWebChromeClient$1.setHasOnOpenWindowEvent(this.mHasOnOpenWindowEvent);
        webView.setWebChromeClient(rNCWebChromeClient3);
    }

    public final void setUserAgent(RNCWebViewWrapper viewWrapper, String userAgent) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        this.mUserAgent = userAgent;
        setUserAgentString(viewWrapper);
    }

    public final void setApplicationNameForUserAgent(RNCWebViewWrapper viewWrapper, String applicationName) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        if (applicationName != null) {
            String defaultUserAgent = WebSettings.getDefaultUserAgent(viewWrapper.getWebView().getContext());
            StringBuilder sb = new StringBuilder();
            sb.append((Object) defaultUserAgent);
            sb.append(' ');
            sb.append((Object) applicationName);
            this.mUserAgentWithApplicationName = sb.toString();
        } else {
            this.mUserAgentWithApplicationName = null;
        }
        setUserAgentString(viewWrapper);
    }

    private final void setUserAgentString(RNCWebViewWrapper viewWrapper) {
        RNCWebView webView = viewWrapper.getWebView();
        if (this.mUserAgent != null) {
            webView.getSettings().setUserAgentString(this.mUserAgent);
        } else if (this.mUserAgentWithApplicationName != null) {
            webView.getSettings().setUserAgentString(this.mUserAgentWithApplicationName);
        } else {
            webView.getSettings().setUserAgentString(WebSettings.getDefaultUserAgent(webView.getContext()));
        }
    }

    public final void setBasicAuthCredential(RNCWebViewWrapper viewWrapper, ReadableMap credential) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setBasicAuthCredential((credential != null && credential.hasKey("username") && credential.hasKey("password")) ? new RNCBasicAuthCredential(credential.getString("username"), credential.getString("password")) : null);
    }

    public final void onDropViewInstance(RNCWebViewWrapper viewWrapper) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        webView.getThemedReactContext().removeLifecycleEventListener(webView);
        webView.cleanupCallbacksAndDestroy();
        webView.mWebChromeClient = null;
    }

    public final int getCOMMAND_GO_BACK() {
        return this.COMMAND_GO_BACK;
    }

    public final int getCOMMAND_GO_FORWARD() {
        return this.COMMAND_GO_FORWARD;
    }

    public final int getCOMMAND_RELOAD() {
        return this.COMMAND_RELOAD;
    }

    public final int getCOMMAND_STOP_LOADING() {
        return this.COMMAND_STOP_LOADING;
    }

    public final int getCOMMAND_POST_MESSAGE() {
        return this.COMMAND_POST_MESSAGE;
    }

    public final int getCOMMAND_INJECT_JAVASCRIPT() {
        return this.COMMAND_INJECT_JAVASCRIPT;
    }

    public final int getCOMMAND_LOAD_URL() {
        return this.COMMAND_LOAD_URL;
    }

    public final int getCOMMAND_FOCUS() {
        return this.COMMAND_FOCUS;
    }

    public final int getCOMMAND_CLEAR_FORM_DATA() {
        return this.COMMAND_CLEAR_FORM_DATA;
    }

    public final int getCOMMAND_CLEAR_CACHE() {
        return this.COMMAND_CLEAR_CACHE;
    }

    public final int getCOMMAND_CLEAR_HISTORY() {
        return this.COMMAND_CLEAR_HISTORY;
    }

    public final Map<String, Integer> getCommandsMap() {
        return MapBuilder.builder().put("goBack", Integer.valueOf(this.COMMAND_GO_BACK)).put("goForward", Integer.valueOf(this.COMMAND_GO_FORWARD)).put("reload", Integer.valueOf(this.COMMAND_RELOAD)).put("stopLoading", Integer.valueOf(this.COMMAND_STOP_LOADING)).put("postMessage", Integer.valueOf(this.COMMAND_POST_MESSAGE)).put("injectJavaScript", Integer.valueOf(this.COMMAND_INJECT_JAVASCRIPT)).put("loadUrl", Integer.valueOf(this.COMMAND_LOAD_URL)).put("requestFocus", Integer.valueOf(this.COMMAND_FOCUS)).put("clearFormData", Integer.valueOf(this.COMMAND_CLEAR_FORM_DATA)).put("clearCache", Integer.valueOf(this.COMMAND_CLEAR_CACHE)).put("clearHistory", Integer.valueOf(this.COMMAND_CLEAR_HISTORY)).build();
    }

    public final void receiveCommand(RNCWebViewWrapper viewWrapper, String commandId, ReadableArray args) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        Intrinsics.checkNotNullParameter(commandId, "commandId");
        Intrinsics.checkNotNullParameter(args, "args");
        RNCWebView webView = viewWrapper.getWebView();
        switch (commandId.hashCode()) {
            case -1241591313:
                if (commandId.equals("goBack")) {
                    webView.goBack();
                    return;
                }
                return;
            case -948122918:
                if (commandId.equals("stopLoading")) {
                    webView.stopLoading();
                    return;
                }
                return;
            case -934641255:
                if (commandId.equals("reload")) {
                    webView.reload();
                    return;
                }
                return;
            case -759238347:
                if (commandId.equals("clearCache")) {
                    webView.clearCache(args.getBoolean(0));
                    return;
                }
                return;
            case -318289731:
                if (commandId.equals("goForward")) {
                    webView.goForward();
                    return;
                }
                return;
            case -265032709:
                if (commandId.equals("clearFormData")) {
                    webView.clearFormData();
                    return;
                }
                return;
            case 336631465:
                if (commandId.equals("loadUrl")) {
                    webView.progressChangedFilter.setWaitingForCommandLoadUrl(false);
                    webView.loadUrl(args.getString(0));
                    return;
                }
                return;
            case 903120263:
                if (commandId.equals("clearHistory")) {
                    webView.clearHistory();
                    return;
                }
                return;
            case 1280029577:
                if (commandId.equals("requestFocus")) {
                    webView.requestFocus();
                    return;
                }
                return;
            case 1490029383:
                if (commandId.equals("postMessage")) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("data", args.getString(0));
                        webView.evaluateJavascriptWithFallback("(function () {var event;var data = " + jSONObject + ";try {event = new MessageEvent('message', data);} catch (e) {event = document.createEvent('MessageEvent');event.initMessageEvent('message', true, true, data.data, data.origin, data.lastEventId, data.source);}document.dispatchEvent(event);})();");
                        return;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                return;
            case 2104576510:
                if (commandId.equals("injectJavaScript")) {
                    webView.evaluateJavascriptWithFallback(args.getString(0));
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void setMixedContentMode(RNCWebViewWrapper viewWrapper, String mixedContentMode) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        if (mixedContentMode == null || Intrinsics.areEqual(ReactScrollViewHelper.OVER_SCROLL_NEVER, mixedContentMode)) {
            webView.getSettings().setMixedContentMode(1);
        } else if (Intrinsics.areEqual(ReactScrollViewHelper.OVER_SCROLL_ALWAYS, mixedContentMode)) {
            webView.getSettings().setMixedContentMode(0);
        } else if (Intrinsics.areEqual("compatibility", mixedContentMode)) {
            webView.getSettings().setMixedContentMode(2);
        }
    }

    public final void setAllowUniversalAccessFromFileURLs(RNCWebViewWrapper viewWrapper, boolean allow) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setAllowUniversalAccessFromFileURLs(allow);
    }

    private final String getDownloadingMessageOrDefault() {
        String str = this.mDownloadingMessage;
        return str == null ? this.DEFAULT_DOWNLOADING_MESSAGE : str;
    }

    private final String getLackPermissionToDownloadMessageOrDefault() {
        String str = this.mLackPermissionToDownloadMessage;
        return str == null ? this.DEFAULT_LACK_PERMISSION_TO_DOWNLOAD_MESSAGE : str;
    }

    public static /* synthetic */ void setSource$default(RNCWebViewManagerImpl rNCWebViewManagerImpl, RNCWebViewWrapper rNCWebViewWrapper, ReadableMap readableMap, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        rNCWebViewManagerImpl.setSource(rNCWebViewWrapper, readableMap, z);
    }

    public final void setSource(RNCWebViewWrapper viewWrapper, ReadableMap source, boolean newArch) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        if (source != null) {
            if (source.hasKey("html")) {
                String string = source.getString("html");
                String string2 = source.hasKey("baseUrl") ? source.getString("baseUrl") : "";
                Intrinsics.checkNotNull(string);
                webView.loadDataWithBaseURL(string2, string, this.HTML_MIME_TYPE, this.HTML_ENCODING, null);
                return;
            }
            if (source.hasKey(RNFetchBlobConst.DATA_ENCODE_URI)) {
                String string3 = source.getString(RNFetchBlobConst.DATA_ENCODE_URI);
                String url = webView.getUrl();
                if (url == null || !Intrinsics.areEqual(url, string3)) {
                    if (source.hasKey("method") && StringsKt.equals(source.getString("method"), this.HTTP_METHOD_POST, true)) {
                        byte[] bArr = null;
                        if (source.hasKey("body")) {
                            String string4 = source.getString("body");
                            try {
                                Intrinsics.checkNotNull(string4);
                                Charset forName = Charset.forName("UTF-8");
                                Intrinsics.checkNotNullExpressionValue(forName, "forName(charsetName)");
                                bArr = string4.getBytes(forName);
                                Intrinsics.checkNotNullExpressionValue(bArr, "this as java.lang.String).getBytes(charset)");
                            } catch (UnsupportedEncodingException unused) {
                                Intrinsics.checkNotNull(string4);
                                byte[] bytes = string4.getBytes(Charsets.UTF_8);
                                Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                                bArr = bytes;
                            }
                        }
                        if (bArr == null) {
                            bArr = new byte[0];
                        }
                        Intrinsics.checkNotNull(string3);
                        webView.postUrl(string3, bArr);
                        return;
                    }
                    HashMap hashMap = new HashMap();
                    if (source.hasKey("headers")) {
                        if (newArch) {
                            ReadableArray array = source.getArray("headers");
                            Intrinsics.checkNotNull(array);
                            Iterator<Object> it = array.toArrayList().iterator();
                            while (it.hasNext()) {
                                Object next = it.next();
                                if (next != null) {
                                    HashMap hashMap2 = (HashMap) next;
                                    String str = (String) hashMap2.get("name");
                                    if (str == null) {
                                        str = "";
                                    }
                                    String str2 = (String) hashMap2.get("value");
                                    if (str2 == null) {
                                        str2 = "";
                                    }
                                    Locale ENGLISH = Locale.ENGLISH;
                                    Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
                                    String lowerCase = str.toLowerCase(ENGLISH);
                                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
                                    if (Intrinsics.areEqual("user-agent", lowerCase)) {
                                        webView.getSettings().setUserAgentString(str2);
                                    } else {
                                        hashMap.put(str, str2);
                                    }
                                } else {
                                    throw new NullPointerException("null cannot be cast to non-null type java.util.HashMap<kotlin.String, kotlin.String>{ kotlin.collections.TypeAliasesKt.HashMap<kotlin.String, kotlin.String> }");
                                }
                            }
                        } else {
                            ReadableMap map = source.getMap("headers");
                            Intrinsics.checkNotNull(map);
                            ReadableMapKeySetIterator keySetIterator = map.keySetIterator();
                            Intrinsics.checkNotNullExpressionValue(keySetIterator, "headers!!.keySetIterator()");
                            while (keySetIterator.hasNextKey()) {
                                String key = keySetIterator.nextKey();
                                Intrinsics.checkNotNullExpressionValue(key, "key");
                                Locale ENGLISH2 = Locale.ENGLISH;
                                Intrinsics.checkNotNullExpressionValue(ENGLISH2, "ENGLISH");
                                String lowerCase2 = key.toLowerCase(ENGLISH2);
                                Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(locale)");
                                if (Intrinsics.areEqual("user-agent", lowerCase2)) {
                                    webView.getSettings().setUserAgentString(map.getString(key));
                                } else {
                                    hashMap.put(key, map.getString(key));
                                }
                            }
                        }
                    }
                    Intrinsics.checkNotNull(string3);
                    webView.loadUrl(string3, hashMap);
                    return;
                }
                return;
            }
        }
        webView.loadUrl(this.BLANK_URL);
    }

    public final void setMessagingModuleName(RNCWebViewWrapper viewWrapper, String value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().messagingModuleName = value;
    }

    public final void setCacheEnabled(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setCacheMode(enabled ? -1 : 2);
    }

    public final void setIncognito(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        if (enabled) {
            CookieManager.getInstance().removeAllCookies(null);
            webView.getSettings().setCacheMode(2);
            webView.clearHistory();
            webView.clearCache(true);
            webView.clearFormData();
            webView.getSettings().setSavePassword(false);
            webView.getSettings().setSaveFormData(false);
        }
    }

    public final void setInjectedJavaScript(RNCWebViewWrapper viewWrapper, String injectedJavaScript) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().injectedJS = injectedJavaScript;
    }

    public final void setInjectedJavaScriptBeforeContentLoaded(RNCWebViewWrapper viewWrapper, String value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().injectedJSBeforeContentLoaded = value;
    }

    public final void setInjectedJavaScriptForMainFrameOnly(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().injectedJavaScriptForMainFrameOnly = value;
    }

    public final void setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().injectedJavaScriptBeforeContentLoadedForMainFrameOnly = value;
    }

    public final void setInjectedJavaScriptObject(RNCWebViewWrapper viewWrapper, String value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setInjectedJavaScriptObject(value);
    }

    public final void setJavaScriptCanOpenWindowsAutomatically(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setJavaScriptCanOpenWindowsAutomatically(value);
    }

    public final void setShowsVerticalScrollIndicator(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setVerticalScrollBarEnabled(value);
    }

    public final void setShowsHorizontalScrollIndicator(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setHorizontalScrollBarEnabled(value);
    }

    public final void setMessagingEnabled(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setMessagingEnabled(value);
    }

    public final void setMediaPlaybackRequiresUserAction(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setMediaPlaybackRequiresUserGesture(value);
    }

    public final void setHasOnScroll(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().setHasScrollEvent(value);
    }

    public final void setJavaScriptEnabled(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setJavaScriptEnabled(enabled);
    }

    public final void setAllowFileAccess(RNCWebViewWrapper viewWrapper, boolean allowFileAccess) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setAllowFileAccess(allowFileAccess);
    }

    public final void setAllowFileAccessFromFileURLs(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setAllowFileAccessFromFileURLs(value);
    }

    public final void setAllowsFullscreenVideo(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        this.mAllowsFullscreenVideo = value;
        setupWebChromeClient(webView);
    }

    public final void setAndroidLayerType(RNCWebViewWrapper viewWrapper, String layerTypeString) {
        int i;
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        if (Intrinsics.areEqual(layerTypeString, "hardware")) {
            i = 2;
        } else {
            i = Intrinsics.areEqual(layerTypeString, "software") ? 1 : 0;
        }
        webView.setLayerType(i, null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void setCacheMode(RNCWebViewWrapper viewWrapper, String cacheModeString) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        WebSettings settings = viewWrapper.getWebView().getSettings();
        int i = -1;
        if (cacheModeString != null) {
            switch (cacheModeString.hashCode()) {
                case -2059164003:
                    if (cacheModeString.equals("LOAD_NO_CACHE")) {
                        i = 2;
                        break;
                    }
                    break;
                case -1215135800:
                    cacheModeString.equals("LOAD_DEFAULT");
                    break;
                case -873877826:
                    if (cacheModeString.equals("LOAD_CACHE_ELSE_NETWORK")) {
                        i = 1;
                        break;
                    }
                    break;
                case 1548620642:
                    if (cacheModeString.equals("LOAD_CACHE_ONLY")) {
                        i = 3;
                        break;
                    }
                    break;
            }
        }
        settings.setCacheMode(i);
    }

    public final void setDomStorageEnabled(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setDomStorageEnabled(value);
    }

    public final void setDownloadingMessage(String value) {
        this.mDownloadingMessage = value;
    }

    public final void setForceDarkOn(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        if (Build.VERSION.SDK_INT > 28) {
            if (WebViewFeature.isFeatureSupported("FORCE_DARK")) {
                WebSettingsCompat.setForceDark(webView.getSettings(), enabled ? 2 : 0);
            }
            if (enabled && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
                WebSettingsCompat.setForceDarkStrategy(webView.getSettings(), 2);
            }
        }
    }

    public final void setGeolocationEnabled(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setGeolocationEnabled(value);
    }

    public final void setLackPermissionToDownloadMessage(String value) {
        this.mLackPermissionToDownloadMessage = value;
    }

    public final void setHasOnOpenWindowEvent(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        this.mHasOnOpenWindowEvent = value;
        setupWebChromeClient(webView);
    }

    public final void setMinimumFontSize(RNCWebViewWrapper viewWrapper, int value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setMinimumFontSize(value);
    }

    public final void setAllowsProtectedMedia(RNCWebViewWrapper viewWrapper, boolean enabled) {
        WebChromeClient webChromeClient;
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        this.mAllowsProtectedMedia = enabled;
        if (Build.VERSION.SDK_INT < 26 || (webChromeClient = webView.getWebChromeClient()) == null || !(webChromeClient instanceof RNCWebChromeClient)) {
            return;
        }
        ((RNCWebChromeClient) webChromeClient).setAllowsProtectedMedia(enabled);
    }

    public final void setMenuCustomItems(RNCWebViewWrapper viewWrapper, ReadableArray value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        Intrinsics.checkNotNullParameter(value, "value");
        viewWrapper.getWebView().setMenuCustomItems(value.toArrayList());
    }

    public final void setNestedScrollEnabled(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().nestedScrollEnabled = value;
    }

    public final void setOverScrollMode(RNCWebViewWrapper viewWrapper, String overScrollModeString) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        int i = 0;
        if (overScrollModeString != null) {
            int hashCode = overScrollModeString.hashCode();
            if (hashCode == -1414557169) {
                overScrollModeString.equals(ReactScrollViewHelper.OVER_SCROLL_ALWAYS);
            } else if (hashCode != 104712844) {
                if (hashCode == 951530617 && overScrollModeString.equals("content")) {
                    i = 1;
                }
            } else if (overScrollModeString.equals(ReactScrollViewHelper.OVER_SCROLL_NEVER)) {
                i = 2;
            }
        }
        webView.setOverScrollMode(i);
    }

    public final void setSaveFormDataDisabled(RNCWebViewWrapper viewWrapper, boolean disabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setSaveFormData(!disabled);
    }

    public final void setScalesPageToFit(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView webView = viewWrapper.getWebView();
        webView.getSettings().setLoadWithOverviewMode(value);
        webView.getSettings().setUseWideViewPort(value);
    }

    public final void setSetBuiltInZoomControls(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setBuiltInZoomControls(value);
    }

    public final void setSetDisplayZoomControls(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setDisplayZoomControls(value);
    }

    public final void setSetSupportMultipleWindows(RNCWebViewWrapper viewWrapper, boolean value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setSupportMultipleWindows(value);
    }

    public final void setTextZoom(RNCWebViewWrapper viewWrapper, int value) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        viewWrapper.getWebView().getSettings().setTextZoom(value);
    }

    public final void setThirdPartyCookiesEnabled(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        CookieManager.getInstance().setAcceptThirdPartyCookies(viewWrapper.getWebView(), enabled);
    }

    public final void setWebviewDebuggingEnabled(RNCWebViewWrapper viewWrapper, boolean enabled) {
        Intrinsics.checkNotNullParameter(viewWrapper, "viewWrapper");
        RNCWebView.setWebContentsDebuggingEnabled(enabled);
    }
}
