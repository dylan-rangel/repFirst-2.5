package com.facebook.react.bridge;

/* loaded from: classes.dex */
public interface NativeModule {

    public interface NativeMethod {
        String getType();

        void invoke(JSInstance jSInstance, ReadableArray readableArray);
    }

    boolean canOverrideExistingModule();

    String getName();

    void initialize();

    void invalidate();

    void onCatalystInstanceDestroy();
}
