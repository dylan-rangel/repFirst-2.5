package com.swmansion.reanimated.layoutReanimation;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationController;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationListener;
import com.swmansion.reanimated.ReanimatedModule;
import com.swmansion.rnscreens.ScreenStackViewManager;
import com.swmansion.rnscreens.ScreenViewManager;
import java.lang.ref.WeakReference;

/* compiled from: ReanimatedNativeHierarchyManager.java */
/* loaded from: classes.dex */
class ReaLayoutAnimator extends LayoutAnimationController {
    private ReactApplicationContext mContext;
    private WeakReference<NativeViewHierarchyManager> mWeakNativeViewHierarchyManage;
    private AnimationsManager mAnimationsManager = null;
    private volatile boolean mInitialized = false;

    ReaLayoutAnimator(ReactApplicationContext reactApplicationContext, NativeViewHierarchyManager nativeViewHierarchyManager) {
        this.mWeakNativeViewHierarchyManage = new WeakReference<>(null);
        this.mContext = reactApplicationContext;
        this.mWeakNativeViewHierarchyManage = new WeakReference<>(nativeViewHierarchyManager);
    }

    public void maybeInit() {
        if (this.mInitialized) {
            return;
        }
        this.mInitialized = true;
        AnimationsManager animationsManager = ((ReanimatedModule) this.mContext.getNativeModule(ReanimatedModule.class)).getNodesManager().getAnimationsManager();
        this.mAnimationsManager = animationsManager;
        animationsManager.setReanimatedNativeHierarchyManager((ReanimatedNativeHierarchyManager) this.mWeakNativeViewHierarchyManage.get());
    }

    @Override // com.facebook.react.uimanager.layoutanimation.LayoutAnimationController
    public boolean shouldAnimateLayout(View view) {
        if (isLayoutAnimationEnabled()) {
            return (view == null || view.getParent() == null) ? false : true;
        }
        return super.shouldAnimateLayout(view);
    }

    @Override // com.facebook.react.uimanager.layoutanimation.LayoutAnimationController
    public void applyLayoutUpdate(View view, int i, int i2, int i3, int i4) {
        if (!isLayoutAnimationEnabled()) {
            super.applyLayoutUpdate(view, i, i2, i3, i4);
            return;
        }
        UiThreadUtil.assertOnUiThread();
        maybeInit();
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            view.layout(i, i2, i3 + i, i4 + i2);
            if (view.getId() != -1) {
                this.mAnimationsManager.onViewCreate(view, (ViewGroup) view.getParent(), new Snapshot(view, this.mWeakNativeViewHierarchyManage.get()));
                return;
            }
            return;
        }
        Snapshot snapshot = new Snapshot(view, this.mWeakNativeViewHierarchyManage.get());
        view.layout(i, i2, i3 + i, i4 + i2);
        this.mAnimationsManager.onViewUpdate(view, snapshot, new Snapshot(view, this.mWeakNativeViewHierarchyManage.get()));
    }

    @Override // com.facebook.react.uimanager.layoutanimation.LayoutAnimationController
    public void deleteView(View view, final LayoutAnimationListener layoutAnimationListener) {
        if (!isLayoutAnimationEnabled()) {
            super.deleteView(view, layoutAnimationListener);
            return;
        }
        UiThreadUtil.assertOnUiThread();
        NativeViewHierarchyManager nativeViewHierarchyManager = this.mWeakNativeViewHierarchyManage.get();
        try {
            ViewManager resolveViewManager = nativeViewHierarchyManager.resolveViewManager(view.getId());
            if (resolveViewManager.getName().equals(ScreenViewManager.REACT_CLASS) && view.getParent() != null && (view.getParent().getParent() instanceof View)) {
                try {
                    if (nativeViewHierarchyManager.resolveViewManager(((View) view.getParent().getParent()).getId()).getName().equals(ScreenStackViewManager.REACT_CLASS)) {
                        super.deleteView(view, layoutAnimationListener);
                        return;
                    }
                } catch (IllegalViewOperationException e) {
                    e.printStackTrace();
                    super.deleteView(view, layoutAnimationListener);
                    return;
                }
            }
            maybeInit();
            this.mAnimationsManager.onViewRemoval(view, (ViewGroup) view.getParent(), new Snapshot(view, this.mWeakNativeViewHierarchyManage.get()), new Runnable() { // from class: com.swmansion.reanimated.layoutReanimation.ReaLayoutAnimator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LayoutAnimationListener.this.onAnimationEnd();
                }
            });
            if (!(resolveViewManager instanceof ViewGroupManager)) {
                return;
            }
            ViewGroupManager viewGroupManager = (ViewGroupManager) resolveViewManager;
            int i = 0;
            while (true) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (i >= viewGroupManager.getChildCount(viewGroup)) {
                    return;
                }
                dfs(viewGroupManager.getChildAt(viewGroup, i), nativeViewHierarchyManager);
                i++;
            }
        } catch (IllegalViewOperationException e2) {
            e2.printStackTrace();
            super.deleteView(view, layoutAnimationListener);
        }
    }

    private void dfs(final View view, final NativeViewHierarchyManager nativeViewHierarchyManager) {
        int id = view.getId();
        if (id == -1) {
            return;
        }
        ViewManager viewManager = null;
        try {
            viewManager = nativeViewHierarchyManager.resolveViewManager(id);
            this.mAnimationsManager.onViewRemoval(view, (ViewGroup) view.getParent(), new Snapshot(view, this.mWeakNativeViewHierarchyManage.get()), new Runnable() { // from class: com.swmansion.reanimated.layoutReanimation.ReaLayoutAnimator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ((ReanimatedNativeHierarchyManager) NativeViewHierarchyManager.this).publicDropView(view);
                }
            });
        } catch (IllegalViewOperationException e) {
            e.printStackTrace();
        }
        if (!(viewManager instanceof ViewGroupManager)) {
            return;
        }
        ViewGroupManager viewGroupManager = (ViewGroupManager) viewManager;
        int i = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (i >= viewGroupManager.getChildCount(viewGroup)) {
                return;
            }
            dfs(viewGroupManager.getChildAt(viewGroup, i), nativeViewHierarchyManager);
            i++;
        }
    }

    public boolean isLayoutAnimationEnabled() {
        maybeInit();
        return this.mAnimationsManager.isLayoutAnimationEnabled();
    }
}
