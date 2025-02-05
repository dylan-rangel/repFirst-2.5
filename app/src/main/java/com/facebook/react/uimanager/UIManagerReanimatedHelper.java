package com.facebook.react.uimanager;

/* loaded from: classes.dex */
public class UIManagerReanimatedHelper {
    public static boolean isOperationQueueEmpty(UIImplementation uIImplementation) {
        return uIImplementation.getUIViewOperationQueue().isEmpty();
    }
}
