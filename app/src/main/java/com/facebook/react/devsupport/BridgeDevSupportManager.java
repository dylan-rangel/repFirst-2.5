package com.facebook.react.devsupport;

import android.content.Context;
import android.widget.Toast;
import com.facebook.common.logging.FLog;
import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.R;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.SurfaceDelegateFactory;
import com.facebook.react.common.futures.SimpleSettableFuture;
import com.facebook.react.devsupport.DevSupportManagerBase;
import com.facebook.react.devsupport.WebsocketJavaScriptExecutor;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.DevSplitBundleCallback;
import com.facebook.react.devsupport.interfaces.RedBoxHandler;
import com.facebook.react.packagerconnection.RequestHandler;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class BridgeDevSupportManager extends DevSupportManagerBase {
    private boolean mIsSamplingProfilerEnabled;

    @Override // com.facebook.react.devsupport.DevSupportManagerBase
    protected String getUniqueTag() {
        return "Bridge";
    }

    public BridgeDevSupportManager(Context context, ReactInstanceDevHelper reactInstanceDevHelper, String str, boolean z, RedBoxHandler redBoxHandler, DevBundleDownloadListener devBundleDownloadListener, int i, Map<String, RequestHandler> map, SurfaceDelegateFactory surfaceDelegateFactory) {
        super(context, reactInstanceDevHelper, str, z, redBoxHandler, devBundleDownloadListener, i, map, surfaceDelegateFactory);
        String string;
        this.mIsSamplingProfilerEnabled = false;
        if (getDevSettings().isStartSamplingProfilerOnInit()) {
            if (!this.mIsSamplingProfilerEnabled) {
                toggleJSSamplingProfiler();
            } else {
                Toast.makeText(context, "JS Sampling Profiler was already running, so did not start the sampling profiler", 1).show();
            }
        }
        addCustomDevOption(this.mIsSamplingProfilerEnabled ? "Disable Sampling Profiler" : "Enable Sampling Profiler", new DevOptionHandler() { // from class: com.facebook.react.devsupport.BridgeDevSupportManager.1
            @Override // com.facebook.react.devsupport.interfaces.DevOptionHandler
            public void onOptionSelected() {
                BridgeDevSupportManager.this.toggleJSSamplingProfiler();
            }
        });
        if (getDevSettings().isDeviceDebugEnabled()) {
            return;
        }
        if (getDevSettings().isRemoteJSDebugEnabled()) {
            string = context.getString(R.string.catalyst_debug_stop);
        } else {
            string = context.getString(R.string.catalyst_debug);
        }
        addCustomDevOption(string, new DevOptionHandler() { // from class: com.facebook.react.devsupport.BridgeDevSupportManager.2
            @Override // com.facebook.react.devsupport.interfaces.DevOptionHandler
            public void onOptionSelected() {
                BridgeDevSupportManager.this.getDevSettings().setRemoteJSDebugEnabled(!BridgeDevSupportManager.this.getDevSettings().isRemoteJSDebugEnabled());
                BridgeDevSupportManager.this.handleReloadJS();
            }
        });
    }

    @Override // com.facebook.react.devsupport.interfaces.DevSupportManager
    public void loadSplitBundleFromServer(final String str, final DevSplitBundleCallback devSplitBundleCallback) {
        fetchSplitBundleAndCreateBundleLoader(str, new DevSupportManagerBase.CallbackWithBundleLoader() { // from class: com.facebook.react.devsupport.BridgeDevSupportManager.3
            @Override // com.facebook.react.devsupport.DevSupportManagerBase.CallbackWithBundleLoader
            public void onSuccess(JSBundleLoader jSBundleLoader) {
                jSBundleLoader.loadScript(BridgeDevSupportManager.this.getCurrentContext().getCatalystInstance());
                ((HMRClient) BridgeDevSupportManager.this.getCurrentContext().getJSModule(HMRClient.class)).registerBundle(BridgeDevSupportManager.this.getDevServerHelper().getDevServerSplitBundleURL(str));
                devSplitBundleCallback.onSuccess();
            }

            @Override // com.facebook.react.devsupport.DevSupportManagerBase.CallbackWithBundleLoader
            public void onError(String str2, Throwable th) {
                devSplitBundleCallback.onError(str2, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WebsocketJavaScriptExecutor.JSExecutorConnectCallback getExecutorConnectCallback(final SimpleSettableFuture<Boolean> simpleSettableFuture) {
        return new WebsocketJavaScriptExecutor.JSExecutorConnectCallback() { // from class: com.facebook.react.devsupport.BridgeDevSupportManager.4
            @Override // com.facebook.react.devsupport.WebsocketJavaScriptExecutor.JSExecutorConnectCallback
            public void onSuccess() {
                simpleSettableFuture.set(true);
                BridgeDevSupportManager.this.hideDevLoadingView();
            }

            @Override // com.facebook.react.devsupport.WebsocketJavaScriptExecutor.JSExecutorConnectCallback
            public void onFailure(Throwable th) {
                BridgeDevSupportManager.this.hideDevLoadingView();
                FLog.e(ReactConstants.TAG, "Failed to connect to debugger!", th);
                simpleSettableFuture.setException(new IOException(BridgeDevSupportManager.this.getApplicationContext().getString(R.string.catalyst_debug_error), th));
            }
        };
    }

    private void reloadJSInProxyMode() {
        getDevServerHelper().launchJSDevtools();
        getReactInstanceDevHelper().onReloadWithJSDebugger(new JavaJSExecutor.Factory() { // from class: com.facebook.react.devsupport.BridgeDevSupportManager.5
            @Override // com.facebook.react.bridge.JavaJSExecutor.Factory
            public JavaJSExecutor create() throws Exception {
                WebsocketJavaScriptExecutor websocketJavaScriptExecutor = new WebsocketJavaScriptExecutor();
                SimpleSettableFuture simpleSettableFuture = new SimpleSettableFuture();
                websocketJavaScriptExecutor.connect(BridgeDevSupportManager.this.getDevServerHelper().getWebsocketProxyURL(), BridgeDevSupportManager.this.getExecutorConnectCallback(simpleSettableFuture));
                try {
                    simpleSettableFuture.get(90L, TimeUnit.SECONDS);
                    return websocketJavaScriptExecutor;
                } catch (InterruptedException e) {
                    e = e;
                    throw new RuntimeException(e);
                } catch (ExecutionException e2) {
                    throw ((Exception) e2.getCause());
                } catch (TimeoutException e3) {
                    e = e3;
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override // com.facebook.react.devsupport.interfaces.DevSupportManager
    public void handleReloadJS() {
        UiThreadUtil.assertOnUiThread();
        ReactMarker.logMarker(ReactMarkerConstants.RELOAD, getDevSettings().getPackagerConnectionSettings().getDebugServerHost());
        hideRedboxDialog();
        if (getDevSettings().isRemoteJSDebugEnabled()) {
            PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: load from Proxy");
            showDevLoadingViewForRemoteJSEnabled();
            reloadJSInProxyMode();
        } else {
            PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: load from Server");
            reloadJSFromServer(getDevServerHelper().getDevServerBundleURL((String) Assertions.assertNotNull(getJSAppBundleName())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleJSSamplingProfiler() {
        JavaScriptExecutorFactory javaScriptExecutorFactory = getReactInstanceDevHelper().getJavaScriptExecutorFactory();
        try {
            if (!this.mIsSamplingProfilerEnabled) {
                try {
                    try {
                        javaScriptExecutorFactory.startSamplingProfiler();
                        Toast.makeText(getApplicationContext(), "Starting Sampling Profiler", 0).show();
                    } catch (UnsupportedOperationException unused) {
                        Toast.makeText(getApplicationContext(), javaScriptExecutorFactory.toString() + " does not support Sampling Profiler", 1).show();
                    }
                    return;
                } finally {
                    this.mIsSamplingProfilerEnabled = true;
                }
            }
            try {
                String path = File.createTempFile("sampling-profiler-trace", ".cpuprofile", getApplicationContext().getCacheDir()).getPath();
                javaScriptExecutorFactory.stopSamplingProfiler(path);
                Toast.makeText(getApplicationContext(), "Saved results from Profiler to " + path, 1).show();
            } catch (IOException unused2) {
                FLog.e(ReactConstants.TAG, "Could not create temporary file for saving results from Sampling Profiler");
            } catch (UnsupportedOperationException unused3) {
                Toast.makeText(getApplicationContext(), javaScriptExecutorFactory.toString() + "does not support Sampling Profiler", 1).show();
            }
        } finally {
            this.mIsSamplingProfilerEnabled = false;
        }
    }
}
