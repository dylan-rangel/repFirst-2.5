package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.UpdateContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class Node {

    @Nullable
    private List<Node> mChildren;
    private final Map<String, Long> mLastLoopID;
    private final Map<String, Object> mMemoizedValue;
    protected final int mNodeID;
    protected final NodesManager mNodesManager;
    protected final UpdateContext mUpdateContext;
    public static final Double ZERO = Double.valueOf(0.0d);
    public static final Double ONE = Double.valueOf(1.0d);

    @Nullable
    protected abstract Object evaluate();

    public void onDrop() {
    }

    public Node(int i, @Nullable ReadableMap readableMap, NodesManager nodesManager) {
        HashMap hashMap = new HashMap();
        this.mLastLoopID = hashMap;
        this.mMemoizedValue = new HashMap();
        hashMap.put("", -1L);
        this.mNodeID = i;
        this.mNodesManager = nodesManager;
        this.mUpdateContext = nodesManager.updateContext;
    }

    @Nullable
    public final Object value() {
        if (!this.mLastLoopID.containsKey(this.mUpdateContext.callID) || this.mLastLoopID.get(this.mUpdateContext.callID).longValue() < this.mUpdateContext.updateLoopID) {
            this.mLastLoopID.put(this.mUpdateContext.callID, Long.valueOf(this.mUpdateContext.updateLoopID));
            Object evaluate = evaluate();
            this.mMemoizedValue.put(this.mUpdateContext.callID, evaluate);
            return evaluate;
        }
        return this.mMemoizedValue.get(this.mUpdateContext.callID);
    }

    public final Double doubleValue() {
        Object value = value();
        if (value == null) {
            return ZERO;
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue() ? ONE : ZERO;
        }
        throw new IllegalStateException("Value of node " + this + " cannot be cast to a number");
    }

    public void addChild(Node node) {
        if (this.mChildren == null) {
            this.mChildren = new ArrayList();
        }
        this.mChildren.add(node);
        node.dangerouslyRescheduleEvaluate();
    }

    public void removeChild(Node node) {
        List<Node> list = this.mChildren;
        if (list != null) {
            list.remove(node);
        }
    }

    protected void markUpdated() {
        UiThreadUtil.assertOnUiThread();
        this.mUpdateContext.updatedNodes.add(this);
        this.mNodesManager.postRunUpdatesAfterAnimation();
    }

    protected final void dangerouslyRescheduleEvaluate() {
        this.mLastLoopID.put(this.mUpdateContext.callID, -1L);
        markUpdated();
    }

    protected final void forceUpdateMemoizedValue(Object obj) {
        this.mMemoizedValue.put(this.mUpdateContext.callID, obj);
        markUpdated();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void findAndUpdateNodes(Node node, Set<Node> set, Stack<FinalNode> stack) {
        if (set.contains(node)) {
            return;
        }
        set.add(node);
        List<Node> list = node.mChildren;
        if (list != null) {
            Iterator<Node> it = list.iterator();
            while (it.hasNext()) {
                findAndUpdateNodes(it.next(), set, stack);
            }
        }
        if (node instanceof FinalNode) {
            stack.push((FinalNode) node);
        }
    }

    public static void runUpdates(UpdateContext updateContext) {
        UiThreadUtil.assertOnUiThread();
        ArrayList<Node> arrayList = updateContext.updatedNodes;
        HashSet hashSet = new HashSet();
        Stack stack = new Stack();
        for (int i = 0; i < arrayList.size(); i++) {
            findAndUpdateNodes(arrayList.get(i), hashSet, stack);
            if (i == arrayList.size() - 1) {
                while (!stack.isEmpty()) {
                    ((FinalNode) stack.pop()).update();
                }
            }
        }
        arrayList.clear();
        updateContext.updateLoopID++;
    }
}
