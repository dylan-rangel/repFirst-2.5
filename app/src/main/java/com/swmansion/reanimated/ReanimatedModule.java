package com.swmansion.reanimated;

import android.util.Log;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModuleListener;
import com.swmansion.reanimated.transitions.TransitionModule;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;

@ReactModule(name = ReanimatedModule.NAME)
/* loaded from: classes.dex */
public class ReanimatedModule extends ReactContextBaseJavaModule implements LifecycleEventListener, UIManagerModuleListener {
    public static final String NAME = "ReanimatedModule";

    @Nullable
    private NodesManager mNodesManager;
    private ArrayList<UIThreadOperation> mOperations;

    @Nullable
    private TransitionModule mTransitionManager;
    private UIManagerModule mUIManager;

    private interface UIThreadOperation {
        void execute(NodesManager nodesManager);
    }

    @ReactMethod
    public void addListener(String str) {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
    }

    @ReactMethod
    public void removeListeners(Integer num) {
    }

    public ReanimatedModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mOperations = new ArrayList<>();
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void initialize() {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        UIManagerModule uIManagerModule = (UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class);
        reactApplicationContext.addLifecycleEventListener(this);
        uIManagerModule.addUIManagerListener(this);
        this.mTransitionManager = new TransitionModule(uIManagerModule);
        this.mUIManager = uIManagerModule;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        NodesManager nodesManager = this.mNodesManager;
        if (nodesManager != null) {
            nodesManager.onHostPause();
        }
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        NodesManager nodesManager = this.mNodesManager;
        if (nodesManager != null) {
            nodesManager.onHostResume();
        }
    }

    @Override // com.facebook.react.uimanager.UIManagerModuleListener
    public void willDispatchViewUpdates(UIManagerModule uIManagerModule) {
        if (this.mOperations.isEmpty()) {
            return;
        }
        final ArrayList<UIThreadOperation> arrayList = this.mOperations;
        this.mOperations = new ArrayList<>();
        uIManagerModule.addUIBlock(new UIBlock() { // from class: com.swmansion.reanimated.ReanimatedModule.1
            @Override // com.facebook.react.uimanager.UIBlock
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                NodesManager nodesManager = ReanimatedModule.this.getNodesManager();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((UIThreadOperation) it.next()).execute(nodesManager);
                }
            }
        });
    }

    public NodesManager getNodesManager() {
        if (this.mNodesManager == null) {
            this.mNodesManager = new NodesManager(getReactApplicationContext());
        }
        return this.mNodesManager;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void installTurboModule() {
        Utils.isChromeDebugger = getReactApplicationContext().getJavaScriptContextHolder().get() == 0;
        if (!Utils.isChromeDebugger) {
            getNodesManager().initWithContext(getReactApplicationContext());
        } else {
            Log.w("[REANIMATED]", "Unable to create Reanimated Native Module. You can ignore this message if you are using Chrome Debugger now.");
        }
    }

    @ReactMethod
    public void animateNextTransition(int i, ReadableMap readableMap) {
        this.mTransitionManager.animateNextTransition(i, readableMap);
    }

    @ReactMethod
    public void createNode(final int i, final ReadableMap readableMap) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.2
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.createNode(i, readableMap);
            }
        });
    }

    @ReactMethod
    public void dropNode(final int i) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.3
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.dropNode(i);
            }
        });
    }

    @ReactMethod
    public void connectNodes(final int i, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.4
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.connectNodes(i, i2);
            }
        });
    }

    @ReactMethod
    public void disconnectNodes(final int i, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.5
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.disconnectNodes(i, i2);
            }
        });
    }

    @ReactMethod
    public void connectNodeToView(final int i, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.6
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.connectNodeToView(i, i2);
            }
        });
    }

    @ReactMethod
    public void disconnectNodeFromView(final int i, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.7
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.disconnectNodeFromView(i, i2);
            }
        });
    }

    @ReactMethod
    public void attachEvent(final int i, final String str, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.8
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.attachEvent(i, str, i2);
            }
        });
    }

    @ReactMethod
    public void detachEvent(final int i, final String str, final int i2) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.9
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.detachEvent(i, str, i2);
            }
        });
    }

    @ReactMethod
    public void getValue(final int i, final Callback callback) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.10
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.getValue(i, callback);
            }
        });
    }

    @ReactMethod
    public void setValue(final int i, final Double d) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.swmansion.reanimated.ReanimatedModule.11
            @Override // com.swmansion.reanimated.ReanimatedModule.UIThreadOperation
            public void execute(NodesManager nodesManager) {
                nodesManager.setValue(i, d);
            }
        });
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        NodesManager nodesManager = this.mNodesManager;
        if (nodesManager != null) {
            nodesManager.onCatalystInstanceDestroy();
        }
    }
}
