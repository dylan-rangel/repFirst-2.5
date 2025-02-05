package com.th3rdwave.safeareacontext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.facebook.react.views.view.ReactViewGroup;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SafeAreaProvider.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0013\u001a\u00020\u000fH\u0002J\b\u0010\u0014\u001a\u00020\u000fH\u0014J\b\u0010\u0015\u001a\u00020\u000fH\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016J[\u0010\u0018\u001a\u00020\u000f2S\u0010\u0019\u001aO\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u0007j\u0004\u0018\u0001`\u0010R[\u0010\u0006\u001aO\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u0007j\u0004\u0018\u0001`\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/th3rdwave/safeareacontext/SafeAreaProvider;", "Lcom/facebook/react/views/view/ReactViewGroup;", "Landroid/view/ViewTreeObserver$OnPreDrawListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mInsetsChangeHandler", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "view", "Lcom/th3rdwave/safeareacontext/EdgeInsets;", "insets", "Lcom/th3rdwave/safeareacontext/Rect;", "frame", "", "Lcom/th3rdwave/safeareacontext/OnInsetsChangeHandler;", "mLastFrame", "mLastInsets", "maybeUpdateInsets", "onAttachedToWindow", "onDetachedFromWindow", "onPreDraw", "", "setOnInsetsChangeHandler", "handler", "react-native-safe-area-context_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SafeAreaProvider extends ReactViewGroup implements ViewTreeObserver.OnPreDrawListener {
    private Function3<? super SafeAreaProvider, ? super EdgeInsets, ? super Rect, Unit> mInsetsChangeHandler;
    private Rect mLastFrame;
    private EdgeInsets mLastInsets;

    public SafeAreaProvider(Context context) {
        super(context);
    }

    private final void maybeUpdateInsets() {
        SafeAreaProvider safeAreaProvider;
        EdgeInsets safeAreaInsets;
        Function3<? super SafeAreaProvider, ? super EdgeInsets, ? super Rect, Unit> function3 = this.mInsetsChangeHandler;
        if (function3 == null || (safeAreaInsets = SafeAreaUtilsKt.getSafeAreaInsets((safeAreaProvider = this))) == null) {
            return;
        }
        View rootView = getRootView();
        if (rootView == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup");
        }
        Rect frame = SafeAreaUtilsKt.getFrame((ViewGroup) rootView, safeAreaProvider);
        if (frame == null) {
            return;
        }
        if (Intrinsics.areEqual(this.mLastInsets, safeAreaInsets) && Intrinsics.areEqual(this.mLastFrame, frame)) {
            return;
        }
        function3.invoke(this, safeAreaInsets, frame);
        this.mLastInsets = safeAreaInsets;
        this.mLastFrame = frame;
    }

    @Override // com.facebook.react.views.view.ReactViewGroup, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnPreDrawListener(this);
        maybeUpdateInsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this);
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        maybeUpdateInsets();
        return true;
    }

    public final void setOnInsetsChangeHandler(Function3<? super SafeAreaProvider, ? super EdgeInsets, ? super Rect, Unit> handler) {
        this.mInsetsChangeHandler = handler;
        maybeUpdateInsets();
    }
}
