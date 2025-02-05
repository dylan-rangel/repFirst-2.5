package com.swmansion.reanimated.layoutReanimation;

import java.util.HashMap;

/* loaded from: classes.dex */
public interface NativeMethodsHolder {
    boolean isLayoutAnimationEnabled();

    void removeConfigForTag(int i);

    void startAnimationForTag(int i, String str, HashMap<String, Float> hashMap);
}
