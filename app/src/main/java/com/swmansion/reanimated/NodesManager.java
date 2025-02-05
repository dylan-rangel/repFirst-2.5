package com.swmansion.reanimated;

import android.util.SparseArray;
import android.view.View;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerReanimatedHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.swmansion.reanimated.layoutReanimation.AnimationsManager;
import com.swmansion.reanimated.nodes.AlwaysNode;
import com.swmansion.reanimated.nodes.BezierNode;
import com.swmansion.reanimated.nodes.BlockNode;
import com.swmansion.reanimated.nodes.CallFuncNode;
import com.swmansion.reanimated.nodes.ClockNode;
import com.swmansion.reanimated.nodes.ClockOpNode;
import com.swmansion.reanimated.nodes.ConcatNode;
import com.swmansion.reanimated.nodes.CondNode;
import com.swmansion.reanimated.nodes.DebugNode;
import com.swmansion.reanimated.nodes.EventNode;
import com.swmansion.reanimated.nodes.FunctionNode;
import com.swmansion.reanimated.nodes.JSCallNode;
import com.swmansion.reanimated.nodes.Node;
import com.swmansion.reanimated.nodes.NoopNode;
import com.swmansion.reanimated.nodes.OperatorNode;
import com.swmansion.reanimated.nodes.ParamNode;
import com.swmansion.reanimated.nodes.PropsNode;
import com.swmansion.reanimated.nodes.SetNode;
import com.swmansion.reanimated.nodes.StyleNode;
import com.swmansion.reanimated.nodes.TransformNode;
import com.swmansion.reanimated.nodes.ValueNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class NodesManager implements EventDispatcherListener {
    private static final Double ZERO = Double.valueOf(0.0d);
    public double currentFrameTimeMs;
    private AnimationsManager mAnimationManager;
    private final GuardedFrameCallback mChoreographerCallback;
    private final ReactContext mContext;
    private RCTEventEmitter mCustomEventHandler;
    protected final UIManagerModule.CustomEventNamesResolver mCustomEventNamesResolver;
    private final DeviceEventManagerModule.RCTDeviceEventEmitter mEventEmitter;
    private NativeProxy mNativeProxy;
    private final NoopNode mNoopNode;
    private final ReactChoreographer mReactChoreographer;
    private final UIImplementation mUIImplementation;
    private final UIManagerModule mUIManager;
    private boolean mWantRunUpdates;
    public final UpdateContext updateContext;
    private final SparseArray<Node> mAnimatedNodes = new SparseArray<>();
    private final Map<String, EventNode> mEventMapping = new HashMap();
    private final AtomicBoolean mCallbackPosted = new AtomicBoolean();
    private List<OnAnimationFrame> mFrameCallbacks = new ArrayList();
    private ConcurrentLinkedQueue<CopiedEvent> mEventQueue = new ConcurrentLinkedQueue<>();
    public Set<String> uiProps = Collections.emptySet();
    public Set<String> nativeProps = Collections.emptySet();
    private Queue<NativeUpdateOperation> mOperationsInBatch = new LinkedList();
    private boolean mTryRunBatchUpdatesSynchronously = false;

    public interface OnAnimationFrame {
        void onAnimationFrame(double d);
    }

    public void scrollTo(int i, double d, double d2, boolean z) {
        try {
            NativeMethodsHelper.scrollTo(this.mUIManager.resolveView(i), d, d2, z);
        } catch (IllegalViewOperationException e) {
            e.printStackTrace();
        }
    }

    public float[] measure(int i) {
        try {
            return NativeMethodsHelper.measure(this.mUIManager.resolveView(i));
        } catch (IllegalViewOperationException e) {
            e.printStackTrace();
            return new float[]{Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN};
        }
    }

    public NativeProxy getNativeProxy() {
        return this.mNativeProxy;
    }

    public AnimationsManager getAnimationsManager() {
        return this.mAnimationManager;
    }

    public void onCatalystInstanceDestroy() {
        AnimationsManager animationsManager = this.mAnimationManager;
        if (animationsManager != null) {
            animationsManager.onCatalystInstanceDestroy();
        }
        NativeProxy nativeProxy = this.mNativeProxy;
        if (nativeProxy != null) {
            nativeProxy.onCatalystInstanceDestroy();
            this.mNativeProxy = null;
        }
    }

    public void initWithContext(ReactApplicationContext reactApplicationContext) {
        this.mNativeProxy = new NativeProxy(reactApplicationContext);
        this.mAnimationManager.setScheduler(getNativeProxy().getScheduler());
    }

    private final class NativeUpdateOperation {
        public WritableMap mNativeProps;
        public int mViewTag;

        public NativeUpdateOperation(int i, WritableMap writableMap) {
            this.mViewTag = i;
            this.mNativeProps = writableMap;
        }
    }

    public NodesManager(ReactContext reactContext) {
        this.mAnimationManager = null;
        this.mContext = reactContext;
        UIManagerModule uIManagerModule = (UIManagerModule) reactContext.getNativeModule(UIManagerModule.class);
        this.mUIManager = uIManagerModule;
        this.updateContext = new UpdateContext();
        UIImplementation uIImplementation = uIManagerModule.getUIImplementation();
        this.mUIImplementation = uIImplementation;
        this.mCustomEventNamesResolver = uIManagerModule.getDirectEventNamesResolver();
        this.mEventEmitter = (DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        this.mReactChoreographer = ReactChoreographer.getInstance();
        this.mChoreographerCallback = new GuardedFrameCallback(reactContext) { // from class: com.swmansion.reanimated.NodesManager.1
            @Override // com.facebook.react.uimanager.GuardedFrameCallback
            protected void doFrameGuarded(long j) {
                NodesManager.this.onAnimationFrame(j);
            }
        };
        this.mNoopNode = new NoopNode(this);
        uIManagerModule.getEventDispatcher().addListener(this);
        this.mAnimationManager = new AnimationsManager(reactContext, uIImplementation, uIManagerModule);
    }

    public void onHostPause() {
        if (this.mCallbackPosted.get()) {
            stopUpdatingOnAnimationFrame();
            this.mCallbackPosted.set(true);
        }
    }

    public void onHostResume() {
        if (this.mCallbackPosted.getAndSet(false)) {
            startUpdatingOnAnimationFrame();
        }
    }

    public void startUpdatingOnAnimationFrame() {
        if (this.mCallbackPosted.getAndSet(true)) {
            return;
        }
        this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mChoreographerCallback);
    }

    private void stopUpdatingOnAnimationFrame() {
        if (this.mCallbackPosted.getAndSet(false)) {
            this.mReactChoreographer.removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mChoreographerCallback);
        }
    }

    private void performOperations() {
        if (this.mOperationsInBatch.isEmpty()) {
            return;
        }
        final Queue<NativeUpdateOperation> queue = this.mOperationsInBatch;
        this.mOperationsInBatch = new LinkedList();
        final boolean z = this.mTryRunBatchUpdatesSynchronously;
        this.mTryRunBatchUpdatesSynchronously = false;
        final Semaphore semaphore = new Semaphore(0);
        this.mContext.runOnNativeModulesQueueThread(new GuardedRunnable(this.mContext.getExceptionHandler()) { // from class: com.swmansion.reanimated.NodesManager.2
            @Override // com.facebook.react.bridge.GuardedRunnable
            public void runGuarded() {
                boolean isOperationQueueEmpty = UIManagerReanimatedHelper.isOperationQueueEmpty(NodesManager.this.mUIImplementation);
                boolean z2 = z && isOperationQueueEmpty;
                if (!z2) {
                    semaphore.release();
                }
                while (!queue.isEmpty()) {
                    NativeUpdateOperation nativeUpdateOperation = (NativeUpdateOperation) queue.remove();
                    ReactShadowNode resolveShadowNode = NodesManager.this.mUIImplementation.resolveShadowNode(nativeUpdateOperation.mViewTag);
                    if (resolveShadowNode != null) {
                        NodesManager.this.mUIManager.updateView(nativeUpdateOperation.mViewTag, resolveShadowNode.getViewClass(), nativeUpdateOperation.mNativeProps);
                    }
                }
                if (isOperationQueueEmpty) {
                    NodesManager.this.mUIImplementation.dispatchViewUpdates(-1);
                }
                if (z2) {
                    semaphore.release();
                }
            }
        });
        if (z) {
            try {
                semaphore.tryAcquire(16L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationFrame(long j) {
        this.currentFrameTimeMs = j / 1000000.0d;
        while (!this.mEventQueue.isEmpty()) {
            CopiedEvent poll = this.mEventQueue.poll();
            handleEvent(poll.getTargetTag(), poll.getEventName(), poll.getPayload());
        }
        if (!this.mFrameCallbacks.isEmpty()) {
            List<OnAnimationFrame> list = this.mFrameCallbacks;
            this.mFrameCallbacks = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).onAnimationFrame(this.currentFrameTimeMs);
            }
        }
        if (this.mWantRunUpdates) {
            Node.runUpdates(this.updateContext);
        }
        performOperations();
        this.mCallbackPosted.set(false);
        this.mWantRunUpdates = false;
        if (this.mFrameCallbacks.isEmpty() && this.mEventQueue.isEmpty()) {
            return;
        }
        startUpdatingOnAnimationFrame();
    }

    public Object getNodeValue(int i) {
        Node node = this.mAnimatedNodes.get(i);
        if (node != null) {
            return node.value();
        }
        return ZERO;
    }

    public <T extends Node> T findNodeById(int i, Class<T> cls) {
        T t = (T) this.mAnimatedNodes.get(i);
        if (t == null) {
            if (cls == Node.class || cls == ValueNode.class) {
                return this.mNoopNode;
            }
            throw new IllegalArgumentException("Requested node with id " + i + " of type " + cls + " cannot be found");
        }
        if (cls.isInstance(t)) {
            return t;
        }
        throw new IllegalArgumentException("Node with id " + i + " is of incompatible type " + t.getClass() + ", requested type was " + cls);
    }

    public void createNode(int i, ReadableMap readableMap) {
        Node callFuncNode;
        if (this.mAnimatedNodes.get(i) != null) {
            throw new JSApplicationIllegalArgumentException("Animated node with ID " + i + " already exists");
        }
        String string = readableMap.getString("type");
        if ("props".equals(string)) {
            callFuncNode = new PropsNode(i, readableMap, this, this.mUIImplementation);
        } else if ("style".equals(string)) {
            callFuncNode = new StyleNode(i, readableMap, this);
        } else if (ViewProps.TRANSFORM.equals(string)) {
            callFuncNode = new TransformNode(i, readableMap, this);
        } else if ("value".equals(string)) {
            callFuncNode = new ValueNode(i, readableMap, this);
        } else if ("block".equals(string)) {
            callFuncNode = new BlockNode(i, readableMap, this);
        } else if ("cond".equals(string)) {
            callFuncNode = new CondNode(i, readableMap, this);
        } else if ("op".equals(string)) {
            callFuncNode = new OperatorNode(i, readableMap, this);
        } else if ("set".equals(string)) {
            callFuncNode = new SetNode(i, readableMap, this);
        } else if (BuildConfig.BUILD_TYPE.equals(string)) {
            callFuncNode = new DebugNode(i, readableMap, this);
        } else if ("clock".equals(string)) {
            callFuncNode = new ClockNode(i, readableMap, this);
        } else if ("clockStart".equals(string)) {
            callFuncNode = new ClockOpNode.ClockStartNode(i, readableMap, this);
        } else if ("clockStop".equals(string)) {
            callFuncNode = new ClockOpNode.ClockStopNode(i, readableMap, this);
        } else if ("clockTest".equals(string)) {
            callFuncNode = new ClockOpNode.ClockTestNode(i, readableMap, this);
        } else if (NotificationCompat.CATEGORY_CALL.equals(string)) {
            callFuncNode = new JSCallNode(i, readableMap, this);
        } else if ("bezier".equals(string)) {
            callFuncNode = new BezierNode(i, readableMap, this);
        } else if (NotificationCompat.CATEGORY_EVENT.equals(string)) {
            callFuncNode = new EventNode(i, readableMap, this);
        } else if (ReactScrollViewHelper.OVER_SCROLL_ALWAYS.equals(string)) {
            callFuncNode = new AlwaysNode(i, readableMap, this);
        } else if ("concat".equals(string)) {
            callFuncNode = new ConcatNode(i, readableMap, this);
        } else if ("param".equals(string)) {
            callFuncNode = new ParamNode(i, readableMap, this);
        } else if ("func".equals(string)) {
            callFuncNode = new FunctionNode(i, readableMap, this);
        } else if ("callfunc".equals(string)) {
            callFuncNode = new CallFuncNode(i, readableMap, this);
        } else {
            throw new JSApplicationIllegalArgumentException("Unsupported node type: " + string);
        }
        this.mAnimatedNodes.put(i, callFuncNode);
    }

    public void dropNode(int i) {
        Node node = this.mAnimatedNodes.get(i);
        if (node != null) {
            node.onDrop();
        }
        this.mAnimatedNodes.remove(i);
    }

    public void connectNodes(int i, int i2) {
        Node node = this.mAnimatedNodes.get(i);
        Node node2 = this.mAnimatedNodes.get(i2);
        if (node2 == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with ID " + i2 + " does not exists");
        }
        node.addChild(node2);
    }

    public void disconnectNodes(int i, int i2) {
        Node node = this.mAnimatedNodes.get(i);
        Node node2 = this.mAnimatedNodes.get(i2);
        if (node2 == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with ID " + i2 + " does not exists");
        }
        node.removeChild(node2);
    }

    public void connectNodeToView(int i, int i2) {
        Node node = this.mAnimatedNodes.get(i);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with ID " + i + " does not exists");
        }
        if (!(node instanceof PropsNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node connected to view should beof type " + PropsNode.class.getName());
        }
        ((PropsNode) node).connectToView(i2);
    }

    public void disconnectNodeFromView(int i, int i2) {
        Node node = this.mAnimatedNodes.get(i);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with ID " + i + " does not exists");
        }
        if (!(node instanceof PropsNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node connected to view should beof type " + PropsNode.class.getName());
        }
        ((PropsNode) node).disconnectFromView(i2);
    }

    public void enqueueUpdateViewOnNativeThread(int i, WritableMap writableMap, boolean z) {
        if (z) {
            this.mTryRunBatchUpdatesSynchronously = true;
        }
        this.mOperationsInBatch.add(new NativeUpdateOperation(i, writableMap));
    }

    public void attachEvent(int i, String str, int i2) {
        String str2 = i + str;
        EventNode eventNode = (EventNode) this.mAnimatedNodes.get(i2);
        if (eventNode == null) {
            throw new JSApplicationIllegalArgumentException("Event node " + i2 + " does not exists");
        }
        if (this.mEventMapping.containsKey(str2)) {
            throw new JSApplicationIllegalArgumentException("Event handler already set for the given view and event type");
        }
        this.mEventMapping.put(str2, eventNode);
    }

    public void detachEvent(int i, String str, int i2) {
        this.mEventMapping.remove(i + str);
    }

    public void configureProps(Set<String> set, Set<String> set2) {
        this.uiProps = set;
        this.nativeProps = set2;
    }

    public void getValue(int i, Callback callback) {
        callback.invoke(this.mAnimatedNodes.get(i).value());
    }

    public void postRunUpdatesAfterAnimation() {
        this.mWantRunUpdates = true;
        startUpdatingOnAnimationFrame();
    }

    public void postOnAnimation(OnAnimationFrame onAnimationFrame) {
        this.mFrameCallbacks.add(onAnimationFrame);
        startUpdatingOnAnimationFrame();
    }

    @Override // com.facebook.react.uimanager.events.EventDispatcherListener
    public void onEventDispatch(Event event) {
        NativeProxy nativeProxy;
        if (UiThreadUtil.isOnUiThread()) {
            handleEvent(event);
            performOperations();
            return;
        }
        String resolveCustomEventName = this.mCustomEventNamesResolver.resolveCustomEventName(event.getEventName());
        int viewTag = event.getViewTag();
        StringBuilder sb = new StringBuilder();
        sb.append(viewTag);
        sb.append(resolveCustomEventName);
        if (((this.mCustomEventHandler == null || (nativeProxy = this.mNativeProxy) == null || !nativeProxy.isAnyHandlerWaitingForEvent(sb.toString())) ? false : true) | false) {
            this.mEventQueue.offer(new CopiedEvent(event));
        }
        startUpdatingOnAnimationFrame();
    }

    private void handleEvent(Event event) {
        EventNode eventNode;
        String resolveCustomEventName = this.mCustomEventNamesResolver.resolveCustomEventName(event.getEventName());
        String str = event.getViewTag() + resolveCustomEventName;
        RCTEventEmitter rCTEventEmitter = this.mCustomEventHandler;
        if (rCTEventEmitter != null) {
            event.dispatch(rCTEventEmitter);
        }
        if (this.mEventMapping.isEmpty() || (eventNode = this.mEventMapping.get(str)) == null) {
            return;
        }
        event.dispatch(eventNode);
    }

    private void handleEvent(int i, String str, @Nullable WritableMap writableMap) {
        EventNode eventNode;
        RCTEventEmitter rCTEventEmitter = this.mCustomEventHandler;
        if (rCTEventEmitter != null) {
            rCTEventEmitter.receiveEvent(i, str, writableMap);
        }
        String str2 = i + str;
        if (this.mEventMapping.isEmpty() || (eventNode = this.mEventMapping.get(str2)) == null) {
            return;
        }
        eventNode.receiveEvent(i, str, writableMap);
    }

    public UIManagerModule.CustomEventNamesResolver getEventNameResolver() {
        return this.mCustomEventNamesResolver;
    }

    public void registerEventHandler(RCTEventEmitter rCTEventEmitter) {
        this.mCustomEventHandler = rCTEventEmitter;
    }

    public void sendEvent(String str, WritableMap writableMap) {
        this.mEventEmitter.emit(str, writableMap);
    }

    public void setValue(int i, Double d) {
        Node node = this.mAnimatedNodes.get(i);
        if (node != null) {
            ((ValueNode) node).setValue(d);
        }
    }

    public void updateProps(int i, Map<String, Object> map) {
        JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (this.uiProps.contains(key)) {
                addProp(javaOnlyMap, key, value);
                z = true;
            } else if (this.nativeProps.contains(key)) {
                addProp(createMap2, key, value);
                z2 = true;
            } else {
                addProp(createMap, key, value);
                z3 = true;
            }
        }
        if (i != -1) {
            if (z) {
                this.mUIImplementation.synchronouslyUpdateViewOnUIThread(i, new ReactStylesDiffMap(javaOnlyMap));
            }
            if (z2) {
                enqueueUpdateViewOnNativeThread(i, createMap2, true);
            }
            if (z3) {
                WritableMap createMap3 = Arguments.createMap();
                createMap3.putInt("viewTag", i);
                createMap3.putMap("props", createMap);
                sendEvent("onReanimatedPropsChange", createMap3);
            }
        }
    }

    public String obtainProp(int i, String str) {
        View resolveView = this.mUIManager.resolveView(i);
        String str2 = "error: unknown propName " + str + ", currently supported: opacity, zIndex";
        if (str.equals(ViewProps.OPACITY)) {
            return Float.toString(Float.valueOf(resolveView.getAlpha()).floatValue());
        }
        return str.equals(ViewProps.Z_INDEX) ? Float.toString(Float.valueOf(resolveView.getElevation()).floatValue()) : str2;
    }

    private static WritableMap copyReadableMap(ReadableMap readableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.merge(readableMap);
        return createMap;
    }

    private static WritableArray copyReadableArray(ReadableArray readableArray) {
        WritableArray createArray = Arguments.createArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (AnonymousClass3.$SwitchMap$com$facebook$react$bridge$ReadableType[readableArray.getType(i).ordinal()]) {
                case 1:
                    createArray.pushBoolean(readableArray.getBoolean(i));
                    break;
                case 2:
                    createArray.pushString(readableArray.getString(i));
                    break;
                case 3:
                    createArray.pushNull();
                    break;
                case 4:
                    createArray.pushDouble(readableArray.getDouble(i));
                    break;
                case 5:
                    createArray.pushMap(copyReadableMap(readableArray.getMap(i)));
                    break;
                case 6:
                    createArray.pushArray(copyReadableArray(readableArray.getArray(i)));
                    break;
                default:
                    throw new IllegalStateException("Unknown type of ReadableArray");
            }
        }
        return createArray;
    }

    /* renamed from: com.swmansion.reanimated.NodesManager$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType;

        static {
            int[] iArr = new int[ReadableType.values().length];
            $SwitchMap$com$facebook$react$bridge$ReadableType = iArr;
            try {
                iArr[ReadableType.Boolean.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.String.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.Null.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.Number.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.Map.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.Array.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static void addProp(WritableMap writableMap, String str, Object obj) {
        if (obj == null) {
            writableMap.putNull(str);
            return;
        }
        if (obj instanceof Double) {
            writableMap.putDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (obj instanceof Integer) {
            writableMap.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Number) {
            writableMap.putDouble(str, ((Number) obj).doubleValue());
            return;
        }
        if (obj instanceof Boolean) {
            writableMap.putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof String) {
            writableMap.putString(str, (String) obj);
            return;
        }
        if (obj instanceof ReadableArray) {
            if (!(obj instanceof WritableArray)) {
                writableMap.putArray(str, copyReadableArray((ReadableArray) obj));
                return;
            } else {
                writableMap.putArray(str, (ReadableArray) obj);
                return;
            }
        }
        if (obj instanceof ReadableMap) {
            if (!(obj instanceof WritableMap)) {
                writableMap.putMap(str, copyReadableMap((ReadableMap) obj));
                return;
            } else {
                writableMap.putMap(str, (ReadableMap) obj);
                return;
            }
        }
        throw new IllegalStateException("Unknown type of animated value");
    }
}
