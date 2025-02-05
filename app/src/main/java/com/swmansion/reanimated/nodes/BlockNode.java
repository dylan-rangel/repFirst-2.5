package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.Utils;

/* loaded from: classes.dex */
public class BlockNode extends Node {
    private final int[] mBlock;

    public BlockNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mBlock = Utils.processIntArray(readableMap.getArray("block"));
    }

    @Override // com.swmansion.reanimated.nodes.Node
    protected Object evaluate() {
        Object obj = null;
        for (int i = 0; i < this.mBlock.length; i++) {
            obj = this.mNodesManager.findNodeById(this.mBlock[i], Node.class).value();
        }
        return obj;
    }
}
