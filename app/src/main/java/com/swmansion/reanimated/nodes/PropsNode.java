package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.Utils;
import java.util.Map;

/* loaded from: classes.dex */
public class PropsNode extends Node implements FinalNode {
    private int mConnectedViewTag;
    private final ReactStylesDiffMap mDiffMap;
    private final Map<String, Integer> mMapping;
    private final JavaOnlyMap mPropMap;
    private final UIImplementation mUIImplementation;

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
        } else if (obj instanceof WritableArray) {
            writableMap.putArray(str, (WritableArray) obj);
        } else {
            if (obj instanceof WritableMap) {
                writableMap.putMap(str, (WritableMap) obj);
                return;
            }
            throw new IllegalStateException("Unknown type of animated value");
        }
    }

    public PropsNode(int i, ReadableMap readableMap, NodesManager nodesManager, UIImplementation uIImplementation) {
        super(i, readableMap, nodesManager);
        this.mConnectedViewTag = -1;
        this.mMapping = Utils.processMapping(readableMap.getMap("props"));
        this.mUIImplementation = uIImplementation;
        JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
        this.mPropMap = javaOnlyMap;
        this.mDiffMap = new ReactStylesDiffMap(javaOnlyMap);
    }

    public void connectToView(int i) {
        this.mConnectedViewTag = i;
        dangerouslyRescheduleEvaluate();
    }

    public void disconnectFromView(int i) {
        this.mConnectedViewTag = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.swmansion.reanimated.nodes.Node
    public Double evaluate() {
        boolean z;
        boolean z2;
        boolean z3;
        WritableMap writableMap;
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        for (Map.Entry<String, Integer> entry : this.mMapping.entrySet()) {
            Node findNodeById = this.mNodesManager.findNodeById(entry.getValue().intValue(), Node.class);
            if (findNodeById instanceof StyleNode) {
                WritableMap writableMap2 = (WritableMap) findNodeById.value();
                ReadableMapKeySetIterator keySetIterator = writableMap2.keySetIterator();
                while (keySetIterator.hasNextKey()) {
                    String nextKey = keySetIterator.nextKey();
                    if (this.mNodesManager.uiProps.contains(nextKey)) {
                        writableMap = this.mPropMap;
                        z2 = z6;
                        z = z5;
                        z3 = true;
                    } else if (this.mNodesManager.nativeProps.contains(nextKey)) {
                        z3 = z4;
                        z2 = z6;
                        z = true;
                        writableMap = createMap2;
                    } else {
                        z = z5;
                        z2 = true;
                        z3 = z4;
                        writableMap = createMap;
                    }
                    ReadableType type = writableMap2.getType(nextKey);
                    int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[type.ordinal()];
                    if (i == 1) {
                        writableMap.putDouble(nextKey, writableMap2.getDouble(nextKey));
                    } else if (i == 2) {
                        writableMap.putString(nextKey, writableMap2.getString(nextKey));
                    } else if (i == 3) {
                        writableMap.putArray(nextKey, (WritableArray) writableMap2.getArray(nextKey));
                    } else {
                        throw new IllegalArgumentException("Unexpected type " + type);
                    }
                    z4 = z3;
                    z5 = z;
                    z6 = z2;
                }
            } else {
                String key = entry.getKey();
                Object value = findNodeById.value();
                if (this.mNodesManager.uiProps.contains(key)) {
                    addProp(this.mPropMap, key, value);
                    z4 = true;
                } else {
                    addProp(createMap2, key, value);
                    z5 = true;
                }
            }
        }
        int i2 = this.mConnectedViewTag;
        if (i2 != -1) {
            if (z4) {
                this.mUIImplementation.synchronouslyUpdateViewOnUIThread(i2, this.mDiffMap);
            }
            if (z5) {
                this.mNodesManager.enqueueUpdateViewOnNativeThread(this.mConnectedViewTag, createMap2, false);
            }
            if (z6) {
                WritableMap createMap3 = Arguments.createMap();
                createMap3.putInt("viewTag", this.mConnectedViewTag);
                createMap3.putMap("props", createMap);
                this.mNodesManager.sendEvent("onReanimatedPropsChange", createMap3);
            }
        }
        return ZERO;
    }

    /* renamed from: com.swmansion.reanimated.nodes.PropsNode$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType;

        static {
            int[] iArr = new int[ReadableType.values().length];
            $SwitchMap$com$facebook$react$bridge$ReadableType = iArr;
            try {
                iArr[ReadableType.Number.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.String.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.Array.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.swmansion.reanimated.nodes.FinalNode
    public void update() {
        if (this.mConnectedViewTag == -1) {
            return;
        }
        value();
    }
}
