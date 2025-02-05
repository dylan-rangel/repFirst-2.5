package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.Utils;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes.dex */
public class ConcatNode extends Node {
    private static final NumberFormat sFormatter;
    private final int[] mInputIDs;

    static {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        sFormatter = numberFormat;
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setGroupingUsed(false);
    }

    public ConcatNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mInputIDs = Utils.processIntArray(readableMap.getArray("input"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.swmansion.reanimated.nodes.Node
    public String evaluate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mInputIDs.length; i++) {
            Object value = this.mNodesManager.findNodeById(this.mInputIDs[i], Node.class).value();
            if (value instanceof Double) {
                value = sFormatter.format((Double) value);
            }
            sb.append(value);
        }
        return sb.toString();
    }
}
