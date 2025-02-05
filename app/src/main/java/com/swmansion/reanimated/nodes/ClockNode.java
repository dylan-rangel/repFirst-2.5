package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

/* loaded from: classes.dex */
public class ClockNode extends Node implements NodesManager.OnAnimationFrame {
    public boolean isRunning;

    public ClockNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
    }

    public void start() {
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;
        this.mNodesManager.postOnAnimation(this);
    }

    public void stop() {
        this.isRunning = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.swmansion.reanimated.nodes.Node
    public Double evaluate() {
        return Double.valueOf(this.mNodesManager.currentFrameTimeMs);
    }

    @Override // com.swmansion.reanimated.NodesManager.OnAnimationFrame
    public void onAnimationFrame(double d) {
        if (this.isRunning) {
            markUpdated();
            this.mNodesManager.postOnAnimation(this);
        }
    }
}
