package com.swmansion.reanimated.nodes;

import com.swmansion.reanimated.NodesManager;

/* loaded from: classes.dex */
public class NoopNode extends ValueNode {
    @Override // com.swmansion.reanimated.nodes.Node
    public void addChild(Node node) {
    }

    @Override // com.swmansion.reanimated.nodes.Node
    protected void markUpdated() {
    }

    @Override // com.swmansion.reanimated.nodes.Node
    public void removeChild(Node node) {
    }

    @Override // com.swmansion.reanimated.nodes.ValueNode
    public void setValue(Object obj) {
    }

    public NoopNode(NodesManager nodesManager) {
        super(-2, null, nodesManager);
    }
}
