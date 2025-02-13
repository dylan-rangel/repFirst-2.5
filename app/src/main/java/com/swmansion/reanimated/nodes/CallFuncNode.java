package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.Utils;
import io.sentry.protocol.Message;

/* loaded from: classes.dex */
public class CallFuncNode extends Node {
    private final int[] mArgs;
    private final int[] mParams;
    private String mPreviousCallID;
    private final int mWhatNodeID;

    public CallFuncNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mWhatNodeID = readableMap.getInt("what");
        this.mParams = Utils.processIntArray(readableMap.getArray(Message.JsonKeys.PARAMS));
        this.mArgs = Utils.processIntArray(readableMap.getArray("args"));
    }

    private void beginContext() {
        this.mPreviousCallID = this.mNodesManager.updateContext.callID;
        this.mNodesManager.updateContext.callID = this.mNodesManager.updateContext.callID + '/' + String.valueOf(this.mNodeID);
        int i = 0;
        while (true) {
            int[] iArr = this.mParams;
            if (i >= iArr.length) {
                return;
            }
            ((ParamNode) this.mNodesManager.findNodeById(iArr[i], ParamNode.class)).beginContext(Integer.valueOf(this.mArgs[i]), this.mPreviousCallID);
            i++;
        }
    }

    private void endContext() {
        int i = 0;
        while (true) {
            int[] iArr = this.mParams;
            if (i < iArr.length) {
                ((ParamNode) this.mNodesManager.findNodeById(iArr[i], ParamNode.class)).endContext();
                i++;
            } else {
                this.mNodesManager.updateContext.callID = this.mPreviousCallID;
                return;
            }
        }
    }

    @Override // com.swmansion.reanimated.nodes.Node
    protected Object evaluate() {
        beginContext();
        Object value = this.mNodesManager.findNodeById(this.mWhatNodeID, Node.class).value();
        endContext();
        return value;
    }
}
