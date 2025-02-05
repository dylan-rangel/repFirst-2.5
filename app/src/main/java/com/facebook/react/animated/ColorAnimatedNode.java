package com.facebook.react.animated;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.view.ColorUtil;
import java.util.Iterator;

/* loaded from: classes.dex */
class ColorAnimatedNode extends AnimatedNode implements AnimatedNodeWithUpdateableConfig {
    private int mANodeId;
    private int mBNodeId;
    private int mGNodeId;
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
    private ReadableMap mNativeColor;
    private boolean mNativeColorApplied;
    private int mRNodeId;
    private final ReactApplicationContext mReactApplicationContext;

    public ColorAnimatedNode(ReadableMap readableMap, NativeAnimatedNodesManager nativeAnimatedNodesManager, ReactApplicationContext reactApplicationContext) {
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
        this.mReactApplicationContext = reactApplicationContext;
        onUpdateConfig(readableMap);
    }

    public int getColor() {
        tryApplyNativeColor();
        return ColorUtil.normalize(((ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mRNodeId)).getValue(), ((ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mGNodeId)).getValue(), ((ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mBNodeId)).getValue(), ((ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mANodeId)).getValue());
    }

    @Override // com.facebook.react.animated.AnimatedNodeWithUpdateableConfig
    public void onUpdateConfig(ReadableMap readableMap) {
        this.mRNodeId = readableMap.getInt("r");
        this.mGNodeId = readableMap.getInt("g");
        this.mBNodeId = readableMap.getInt("b");
        this.mANodeId = readableMap.getInt("a");
        this.mNativeColor = readableMap.getMap("nativeColor");
        this.mNativeColorApplied = false;
        tryApplyNativeColor();
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public String prettyPrint() {
        return "ColorAnimatedNode[" + this.mTag + "]: r: " + this.mRNodeId + " g: " + this.mGNodeId + " b: " + this.mBNodeId + " a: " + this.mANodeId;
    }

    private void tryApplyNativeColor() {
        Context context;
        if (this.mNativeColor == null || this.mNativeColorApplied || (context = getContext()) == null) {
            return;
        }
        int intValue = ColorPropConverter.getColor(this.mNativeColor, context).intValue();
        ValueAnimatedNode valueAnimatedNode = (ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mRNodeId);
        ValueAnimatedNode valueAnimatedNode2 = (ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mGNodeId);
        ValueAnimatedNode valueAnimatedNode3 = (ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mBNodeId);
        ValueAnimatedNode valueAnimatedNode4 = (ValueAnimatedNode) this.mNativeAnimatedNodesManager.getNodeById(this.mANodeId);
        valueAnimatedNode.mValue = Color.red(intValue);
        valueAnimatedNode2.mValue = Color.green(intValue);
        valueAnimatedNode3.mValue = Color.blue(intValue);
        valueAnimatedNode4.mValue = Color.alpha(intValue) / 255.0d;
        this.mNativeColorApplied = true;
    }

    private Context getContext() {
        Activity currentActivity = this.mReactApplicationContext.getCurrentActivity();
        return currentActivity != null ? currentActivity : getContextHelper(this);
    }

    private static Context getContextHelper(AnimatedNode animatedNode) {
        if (animatedNode.mChildren != null) {
            Iterator<AnimatedNode> it = animatedNode.mChildren.iterator();
            if (it.hasNext()) {
                AnimatedNode next = it.next();
                if (next instanceof PropsAnimatedNode) {
                    View connectedView = ((PropsAnimatedNode) next).getConnectedView();
                    if (connectedView != null) {
                        return connectedView.getContext();
                    }
                    return null;
                }
                return getContextHelper(next);
            }
        }
        return null;
    }
}
