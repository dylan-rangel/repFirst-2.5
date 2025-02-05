package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.Utils;

/* loaded from: classes.dex */
public class JSCallNode extends Node {
    private final int[] mInputIDs;

    public JSCallNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mInputIDs = Utils.processIntArray(readableMap.getArray("input"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.swmansion.reanimated.nodes.Node
    public Double evaluate() {
        WritableArray createArray = Arguments.createArray();
        for (int i = 0; i < this.mInputIDs.length; i++) {
            Node findNodeById = this.mNodesManager.findNodeById(this.mInputIDs[i], Node.class);
            if (findNodeById.value() == null) {
                createArray.pushNull();
            } else {
                Object value = findNodeById.value();
                if (value instanceof String) {
                    createArray.pushString((String) value);
                } else {
                    createArray.pushDouble(findNodeById.doubleValue().doubleValue());
                }
            }
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", this.mNodeID);
        createMap.putArray("args", createArray);
        this.mNodesManager.sendEvent("onReanimatedCall", createMap);
        return ZERO;
    }
}
