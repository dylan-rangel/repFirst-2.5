package com.reactnativecommunity.webview;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.facebook.react.views.textinput.ReactTextInputShadowNode;
import com.google.firebase.messaging.Constants;
import com.reactnativecommunity.webview.events.TopCustomMenuSelectionEvent;
import com.reactnativecommunity.webview.events.TopMessageEvent;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RNCWebView extends WebView implements LifecycleEventListener {
    protected static final String JAVASCRIPT_INTERFACE = "ReactNativeWebView";
    protected RNCWebViewBridge bridge;
    protected boolean hasScrollEvent;
    protected String injectedJS;
    protected String injectedJSBeforeContentLoaded;
    protected boolean injectedJavaScriptBeforeContentLoadedForMainFrameOnly;
    protected boolean injectedJavaScriptForMainFrameOnly;
    protected CatalystInstance mCatalystInstance;
    private OnScrollDispatchHelper mOnScrollDispatchHelper;
    protected RNCWebViewClient mRNCWebViewClient;
    WebChromeClient mWebChromeClient;
    protected List<Map<String, String>> menuCustomItems;
    protected boolean messagingEnabled;
    protected String messagingModuleName;
    protected boolean nestedScrollEnabled;
    protected ProgressChangedFilter progressChangedFilter;
    protected boolean sendContentSizeChangeEvents;

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
    }

    public RNCWebView(ThemedReactContext themedReactContext) {
        super(themedReactContext);
        this.injectedJavaScriptForMainFrameOnly = true;
        this.injectedJavaScriptBeforeContentLoadedForMainFrameOnly = true;
        this.messagingEnabled = false;
        this.sendContentSizeChangeEvents = false;
        this.hasScrollEvent = false;
        this.nestedScrollEnabled = false;
        createCatalystInstance();
        this.progressChangedFilter = new ProgressChangedFilter();
    }

    public void setIgnoreErrFailedForThisURL(String str) {
        this.mRNCWebViewClient.setIgnoreErrFailedForThisURL(str);
    }

    public void setBasicAuthCredential(RNCBasicAuthCredential rNCBasicAuthCredential) {
        this.mRNCWebViewClient.setBasicAuthCredential(rNCBasicAuthCredential);
    }

    public void setSendContentSizeChangeEvents(boolean z) {
        this.sendContentSizeChangeEvents = z;
    }

    public void setHasScrollEvent(boolean z) {
        this.hasScrollEvent = z;
    }

    public void setNestedScrollEnabled(boolean z) {
        this.nestedScrollEnabled = z;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        cleanupCallbacksAndDestroy();
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.nestedScrollEnabled) {
            requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.sendContentSizeChangeEvents) {
            dispatchEvent(this, new ContentSizeChangeEvent(RNCWebViewWrapper.getReactTagFromWebView(this), i, i2));
        }
    }

    public void setMenuCustomItems(List<Map<String, String>> list) {
        this.menuCustomItems = list;
    }

    @Override // android.view.View
    public ActionMode startActionMode(final ActionMode.Callback callback, int i) {
        if (this.menuCustomItems == null) {
            return super.startActionMode(callback, i);
        }
        return super.startActionMode(new ActionMode.Callback2() { // from class: com.reactnativecommunity.webview.RNCWebView.1
            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
            }

            @Override // android.view.ActionMode.Callback
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                for (int i2 = 0; i2 < RNCWebView.this.menuCustomItems.size(); i2++) {
                    menu.add(0, i2, i2, RNCWebView.this.menuCustomItems.get(i2).get(Constants.ScionAnalytics.PARAM_LABEL));
                }
                return true;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(final ActionMode actionMode, final MenuItem menuItem) {
                final WritableMap createMap = Arguments.createMap();
                RNCWebView.this.evaluateJavascript("(function(){return {selection: window.getSelection().toString()} })()", new ValueCallback<String>() { // from class: com.reactnativecommunity.webview.RNCWebView.1.1
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(String str) {
                        String str2;
                        Map<String, String> map = RNCWebView.this.menuCustomItems.get(menuItem.getItemId());
                        createMap.putString(Constants.ScionAnalytics.PARAM_LABEL, map.get(Constants.ScionAnalytics.PARAM_LABEL));
                        createMap.putString("key", map.get("key"));
                        try {
                            str2 = new JSONObject(str).getString(ReactTextInputShadowNode.PROP_SELECTION);
                        } catch (JSONException unused) {
                            str2 = "";
                        }
                        createMap.putString("selectedText", str2);
                        RNCWebView.this.dispatchEvent(RNCWebView.this, new TopCustomMenuSelectionEvent(RNCWebViewWrapper.getReactTagFromWebView(RNCWebView.this), createMap));
                        actionMode.finish();
                    }
                });
                return true;
            }

            @Override // android.view.ActionMode.Callback2
            public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
                ActionMode.Callback callback2 = callback;
                if (callback2 instanceof ActionMode.Callback2) {
                    ((ActionMode.Callback2) callback2).onGetContentRect(actionMode, view, rect);
                } else {
                    super.onGetContentRect(actionMode, view, rect);
                }
            }
        }, i);
    }

    @Override // android.webkit.WebView
    public void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
        if (webViewClient instanceof RNCWebViewClient) {
            RNCWebViewClient rNCWebViewClient = (RNCWebViewClient) webViewClient;
            this.mRNCWebViewClient = rNCWebViewClient;
            rNCWebViewClient.setProgressChangedFilter(this.progressChangedFilter);
        }
    }

    @Override // android.webkit.WebView
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.mWebChromeClient = webChromeClient;
        super.setWebChromeClient(webChromeClient);
        if (webChromeClient instanceof RNCWebChromeClient) {
            ((RNCWebChromeClient) webChromeClient).setProgressChangedFilter(this.progressChangedFilter);
        }
    }

    @Override // android.webkit.WebView
    public WebChromeClient getWebChromeClient() {
        return this.mWebChromeClient;
    }

    public RNCWebViewClient getRNCWebViewClient() {
        return this.mRNCWebViewClient;
    }

    public boolean getMessagingEnabled() {
        return this.messagingEnabled;
    }

    protected RNCWebViewBridge createRNCWebViewBridge(RNCWebView rNCWebView) {
        if (this.bridge == null) {
            RNCWebViewBridge rNCWebViewBridge = new RNCWebViewBridge(rNCWebView);
            this.bridge = rNCWebViewBridge;
            addJavascriptInterface(rNCWebViewBridge, JAVASCRIPT_INTERFACE);
        }
        return this.bridge;
    }

    protected void createCatalystInstance() {
        ThemedReactContext themedReactContext = (ThemedReactContext) getContext();
        if (themedReactContext != null) {
            this.mCatalystInstance = themedReactContext.getCatalystInstance();
        }
    }

    public void setMessagingEnabled(boolean z) {
        if (this.messagingEnabled == z) {
            return;
        }
        this.messagingEnabled = z;
        if (z) {
            createRNCWebViewBridge(this);
        }
    }

    protected void evaluateJavascriptWithFallback(String str) {
        evaluateJavascript(str, null);
    }

    public void callInjectedJavaScript() {
        String str;
        if (!getSettings().getJavaScriptEnabled() || (str = this.injectedJS) == null || TextUtils.isEmpty(str)) {
            return;
        }
        evaluateJavascriptWithFallback("(function() {\n" + this.injectedJS + ";\n})();");
    }

    public void callInjectedJavaScriptBeforeContentLoaded() {
        String str;
        if (!getSettings().getJavaScriptEnabled() || (str = this.injectedJSBeforeContentLoaded) == null || TextUtils.isEmpty(str)) {
            return;
        }
        evaluateJavascriptWithFallback("(function() {\n" + this.injectedJSBeforeContentLoaded + ";\n})();");
    }

    public void setInjectedJavaScriptObject(String str) {
        if (getSettings().getJavaScriptEnabled()) {
            createRNCWebViewBridge(this).setInjectedObjectJson(str);
        }
    }

    public void onMessage(final String str) {
        getThemedReactContext();
        if (this.mRNCWebViewClient != null) {
            post(new Runnable() { // from class: com.reactnativecommunity.webview.RNCWebView.2
                @Override // java.lang.Runnable
                public void run() {
                    if (RNCWebView.this.mRNCWebViewClient == null) {
                        return;
                    }
                    RNCWebViewClient rNCWebViewClient = RNCWebView.this.mRNCWebViewClient;
                    WebView webView = this;
                    WritableMap createWebViewEvent = rNCWebViewClient.createWebViewEvent(webView, webView.getUrl());
                    createWebViewEvent.putString("data", str);
                    if (RNCWebView.this.mCatalystInstance != null) {
                        this.sendDirectMessage("onMessage", createWebViewEvent);
                    } else {
                        RNCWebView.this.dispatchEvent(this, new TopMessageEvent(RNCWebViewWrapper.getReactTagFromWebView(this), createWebViewEvent));
                    }
                }
            });
            return;
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putString("data", str);
        if (this.mCatalystInstance != null) {
            sendDirectMessage("onMessage", createMap);
        } else {
            dispatchEvent(this, new TopMessageEvent(RNCWebViewWrapper.getReactTagFromWebView(this), createMap));
        }
    }

    protected void sendDirectMessage(String str, WritableMap writableMap) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putMap("nativeEvent", writableMap);
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        writableNativeArray.pushMap(writableNativeMap);
        this.mCatalystInstance.callFunction(this.messagingModuleName, str, writableNativeArray);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.hasScrollEvent) {
            if (this.mOnScrollDispatchHelper == null) {
                this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
            }
            if (this.mOnScrollDispatchHelper.onScrollChanged(i, i2)) {
                dispatchEvent(this, ScrollEvent.obtain(RNCWebViewWrapper.getReactTagFromWebView(this), ScrollEventType.SCROLL, i, i2, this.mOnScrollDispatchHelper.getXFlingVelocity(), this.mOnScrollDispatchHelper.getYFlingVelocity(), computeHorizontalScrollRange(), computeVerticalScrollRange(), getWidth(), getHeight()));
            }
        }
    }

    protected void dispatchEvent(WebView webView, Event event) {
        UIManagerHelper.getEventDispatcherForReactTag(getThemedReactContext(), RNCWebViewWrapper.getReactTagFromWebView(webView)).dispatchEvent(event);
    }

    protected void cleanupCallbacksAndDestroy() {
        setWebViewClient(null);
        destroy();
    }

    @Override // android.webkit.WebView
    public void destroy() {
        WebChromeClient webChromeClient = this.mWebChromeClient;
        if (webChromeClient != null) {
            webChromeClient.onHideCustomView();
        }
        super.destroy();
    }

    public ThemedReactContext getThemedReactContext() {
        return (ThemedReactContext) getContext();
    }

    protected class RNCWebViewBridge {
        private String TAG = "RNCWebViewBridge";
        String injectedObjectJson;
        RNCWebView mWebView;

        RNCWebViewBridge(RNCWebView rNCWebView) {
            this.mWebView = rNCWebView;
        }

        public void setInjectedObjectJson(String str) {
            this.injectedObjectJson = str;
        }

        @JavascriptInterface
        public void postMessage(String str) {
            if (this.mWebView.getMessagingEnabled()) {
                this.mWebView.onMessage(str);
            } else {
                FLog.w(this.TAG, "ReactNativeWebView.postMessage method was called but messaging is disabled. Pass an onMessage handler to the WebView.");
            }
        }

        @JavascriptInterface
        public String injectedObjectJson() {
            return this.injectedObjectJson;
        }
    }

    protected static class ProgressChangedFilter {
        private boolean waitingForCommandLoadUrl = false;

        protected ProgressChangedFilter() {
        }

        public void setWaitingForCommandLoadUrl(boolean z) {
            this.waitingForCommandLoadUrl = z;
        }

        public boolean isWaitingForCommandLoadUrl() {
            return this.waitingForCommandLoadUrl;
        }
    }
}
