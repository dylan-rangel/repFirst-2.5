package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import java.util.Stack;

/* loaded from: classes.dex */
public class ParamNode extends ValueNode {
    private final Stack<Integer> mArgsStack;
    private String mPrevCallID;

    public ParamNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mArgsStack = new Stack<>();
    }

    @Override // com.swmansion.reanimated.nodes.ValueNode
    public void setValue(Object obj) {
        Node findNodeById = this.mNodesManager.findNodeById(this.mArgsStack.peek().intValue(), Node.class);
        String str = this.mUpdateContext.callID;
        this.mUpdateContext.callID = this.mPrevCallID;
        ((ValueNode) findNodeById).setValue(obj);
        this.mUpdateContext.callID = str;
        forceUpdateMemoizedValue(obj);
    }

    public void beginContext(Integer num, String str) {
        this.mPrevCallID = str;
        this.mArgsStack.push(num);
    }

    public void endContext() {
        this.mArgsStack.pop();
    }

    @Override // com.swmansion.reanimated.nodes.ValueNode, com.swmansion.reanimated.nodes.Node
    protected Object evaluate() {
        String str = this.mUpdateContext.callID;
        this.mUpdateContext.callID = this.mPrevCallID;
        Object value = this.mNodesManager.findNodeById(this.mArgsStack.peek().intValue(), Node.class).value();
        this.mUpdateContext.callID = str;
        return value;
    }

    public void start() {
        Node findNodeById = this.mNodesManager.findNodeById(this.mArgsStack.peek().intValue(), Node.class);
        if (findNodeById instanceof ParamNode) {
            ((ParamNode) findNodeById).start();
        } else {
            ((ClockNode) findNodeById).start();
        }
    }

    public void stop() {
        Node findNodeById = this.mNodesManager.findNodeById(this.mArgsStack.peek().intValue(), Node.class);
        if (findNodeById instanceof ParamNode) {
            ((ParamNode) findNodeById).stop();
        } else {
            ((ClockNode) findNodeById).stop();
        }
    }

    public boolean isRunning() {
        Node findNodeById = this.mNodesManager.findNodeById(this.mArgsStack.peek().intValue(), Node.class);
        if (findNodeById instanceof ParamNode) {
            return ((ParamNode) findNodeById).isRunning();
        }
        return ((ClockNode) findNodeById).isRunning;
    }
}
