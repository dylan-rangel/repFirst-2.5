package com.swmansion.rnscreens;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.ViewProps;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.swmansion.rnscreens.Screen;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScreenContainer.kt */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0019H\u0014J\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0015J\u0018\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020#H\u0004J\u0018\u0010'\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020*H\u0002J\u0012\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020\u0010H\u0002J\u000e\u0010.\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0015J\u000e\u0010/\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0015J\u0012\u00100\u001a\u00020\u00062\b\u0010-\u001a\u0004\u0018\u00010\u0010H\u0016J\u0006\u00101\u001a\u00020\u001fJ\b\u00102\u001a\u00020\u001fH\u0014J\b\u00103\u001a\u00020\u001fH\u0014J\b\u00104\u001a\u00020\u001fH\u0014J0\u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u00020\u00062\u0006\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0015H\u0014J\u0018\u0010;\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020\u00152\u0006\u0010=\u001a\u00020\u0015H\u0014J\b\u0010>\u001a\u00020\u001fH\u0002J\b\u0010?\u001a\u00020\u001fH\u0016J\u0006\u0010@\u001a\u00020\u001fJ\b\u0010A\u001a\u00020\u001fH\u0004J\b\u0010B\u001a\u00020\u001fH\u0016J\u0010\u0010C\u001a\u00020\u001f2\u0006\u0010D\u001a\u00020\tH\u0002J\u0010\u0010E\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0015H\u0016J\u0010\u0010F\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020\u001fH\u0016J\u0010\u0010J\u001a\u00020\u001f2\u0006\u0010K\u001a\u00020\tH\u0002J\b\u0010L\u001a\u00020\u001fH\u0002R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00100\u0012j\b\u0012\u0004\u0012\u00020\u0010`\u00138\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006M"}, d2 = {"Lcom/swmansion/rnscreens/ScreenContainer;", "Landroid/view/ViewGroup;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "isNested", "", "()Z", "mFragmentManager", "Landroidx/fragment/app/FragmentManager;", "mIsAttached", "mLayoutCallback", "Lcom/facebook/react/modules/core/ChoreographerCompat$FrameCallback;", "mLayoutEnqueued", "mNeedUpdate", "mParentScreenFragment", "Lcom/swmansion/rnscreens/ScreenFragmentWrapper;", "mScreenFragments", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "screenCount", "", "getScreenCount", "()I", "topScreen", "Lcom/swmansion/rnscreens/Screen;", "getTopScreen", "()Lcom/swmansion/rnscreens/Screen;", "adapt", "screen", "addScreen", "", FirebaseAnalytics.Param.INDEX, "attachScreen", "transaction", "Landroidx/fragment/app/FragmentTransaction;", "fragment", "Landroidx/fragment/app/Fragment;", "createTransaction", "detachScreen", "findFragmentManagerForReactRootView", "rootView", "Lcom/facebook/react/ReactRootView;", "getActivityState", "Lcom/swmansion/rnscreens/Screen$ActivityState;", "screenFragmentWrapper", "getScreenAt", "getScreenFragmentWrapperAt", "hasScreen", "notifyChildUpdate", "notifyContainerUpdate", "onAttachedToWindow", "onDetachedFromWindow", ViewProps.ON_LAYOUT, "changed", "l", "t", "r", "b", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScreenChanged", "onUpdate", "performUpdates", "performUpdatesNow", "removeAllScreens", "removeMyFragments", "fragmentManager", "removeScreenAt", "removeView", "view", "Landroid/view/View;", "requestLayout", "setFragmentManager", "fm", "setupFragmentManager", "react-native-screens_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public class ScreenContainer extends ViewGroup {
    protected FragmentManager mFragmentManager;
    private boolean mIsAttached;
    private final ChoreographerCompat.FrameCallback mLayoutCallback;
    private boolean mLayoutEnqueued;
    private boolean mNeedUpdate;
    private ScreenFragmentWrapper mParentScreenFragment;
    protected final ArrayList<ScreenFragmentWrapper> mScreenFragments;

    public ScreenContainer(Context context) {
        super(context);
        this.mScreenFragments = new ArrayList<>();
        this.mLayoutCallback = new ChoreographerCompat.FrameCallback() { // from class: com.swmansion.rnscreens.ScreenContainer$mLayoutCallback$1
            @Override // com.facebook.react.modules.core.ChoreographerCompat.FrameCallback
            public void doFrame(long frameTimeNanos) {
                ScreenContainer.this.mLayoutEnqueued = false;
                ScreenContainer screenContainer = ScreenContainer.this;
                screenContainer.measure(View.MeasureSpec.makeMeasureSpec(screenContainer.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ScreenContainer.this.getHeight(), 1073741824));
                ScreenContainer screenContainer2 = ScreenContainer.this;
                screenContainer2.layout(screenContainer2.getLeft(), ScreenContainer.this.getTop(), ScreenContainer.this.getRight(), ScreenContainer.this.getBottom());
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(0, 0, getWidth(), getHeight());
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (view == getFocusedChild()) {
            Object systemService = getContext().getSystemService("input_method");
            if (systemService == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            }
            ((InputMethodManager) systemService).hideSoftInputFromWindow(getWindowToken(), 2);
        }
        super.removeView(view);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        if (this.mLayoutEnqueued || this.mLayoutCallback == null) {
            return;
        }
        this.mLayoutEnqueued = true;
        ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mLayoutCallback);
    }

    public final boolean isNested() {
        return this.mParentScreenFragment != null;
    }

    public final void notifyChildUpdate() {
        performUpdatesNow();
    }

    protected ScreenFragmentWrapper adapt(Screen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        return new ScreenFragment(screen);
    }

    public final void addScreen(Screen screen, int index) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        ScreenFragmentWrapper adapt = adapt(screen);
        screen.setFragmentWrapper(adapt);
        this.mScreenFragments.add(index, adapt);
        screen.setContainer(this);
        onScreenChanged();
    }

    public void removeScreenAt(int index) {
        this.mScreenFragments.get(index).getScreen().setContainer(null);
        this.mScreenFragments.remove(index);
        onScreenChanged();
    }

    public void removeAllScreens() {
        Iterator<ScreenFragmentWrapper> it = this.mScreenFragments.iterator();
        while (it.hasNext()) {
            it.next().getScreen().setContainer(null);
        }
        this.mScreenFragments.clear();
        onScreenChanged();
    }

    public final int getScreenCount() {
        return this.mScreenFragments.size();
    }

    public final Screen getScreenAt(int index) {
        return this.mScreenFragments.get(index).getScreen();
    }

    public final ScreenFragmentWrapper getScreenFragmentWrapperAt(int index) {
        ScreenFragmentWrapper screenFragmentWrapper = this.mScreenFragments.get(index);
        Intrinsics.checkNotNullExpressionValue(screenFragmentWrapper, "mScreenFragments[index]");
        return screenFragmentWrapper;
    }

    public Screen getTopScreen() {
        Object obj;
        Iterator<T> it = this.mScreenFragments.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (getActivityState((ScreenFragmentWrapper) obj) == Screen.ActivityState.ON_TOP) {
                break;
            }
        }
        ScreenFragmentWrapper screenFragmentWrapper = (ScreenFragmentWrapper) obj;
        if (screenFragmentWrapper == null) {
            return null;
        }
        return screenFragmentWrapper.getScreen();
    }

    private final void setFragmentManager(FragmentManager fm) {
        this.mFragmentManager = fm;
        performUpdatesNow();
    }

    private final FragmentManager findFragmentManagerForReactRootView(ReactRootView rootView) {
        boolean z;
        FragmentManager supportFragmentManager;
        Context context = rootView.getContext();
        while (true) {
            z = context instanceof FragmentActivity;
            if (z || !(context instanceof ContextWrapper)) {
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (!z) {
            throw new IllegalStateException("In order to use RNScreens components your app's activity need to extend ReactActivity".toString());
        }
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        if (fragmentActivity.getSupportFragmentManager().getFragments().isEmpty()) {
            FragmentManager supportFragmentManager2 = fragmentActivity.getSupportFragmentManager();
            Intrinsics.checkNotNullExpressionValue(supportFragmentManager2, "{\n            // We are …FragmentManager\n        }");
            return supportFragmentManager2;
        }
        try {
            supportFragmentManager = FragmentManager.findFragment(rootView).getChildFragmentManager();
        } catch (IllegalStateException unused) {
            supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        }
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "{\n            // We are …r\n            }\n        }");
        return supportFragmentManager;
    }

    private final void setupFragmentManager() {
        boolean z;
        Unit unit;
        ScreenContainer screenContainer = this;
        while (true) {
            z = screenContainer instanceof ReactRootView;
            if (z || (screenContainer instanceof Screen) || screenContainer.getParent() == null) {
                break;
            }
            screenContainer = screenContainer.getParent();
            Intrinsics.checkNotNullExpressionValue(screenContainer, "parent.parent");
        }
        if (!(screenContainer instanceof Screen)) {
            if (!z) {
                throw new IllegalStateException("ScreenContainer is not attached under ReactRootView".toString());
            }
            setFragmentManager(findFragmentManagerForReactRootView((ReactRootView) screenContainer));
            return;
        }
        ScreenFragmentWrapper fragmentWrapper = ((Screen) screenContainer).getFragmentWrapper();
        if (fragmentWrapper == null) {
            unit = null;
        } else {
            this.mParentScreenFragment = fragmentWrapper;
            fragmentWrapper.addChildScreenContainer(this);
            FragmentManager childFragmentManager = fragmentWrapper.getFragment().getChildFragmentManager();
            Intrinsics.checkNotNullExpressionValue(childFragmentManager, "fragmentWrapper.fragment.childFragmentManager");
            setFragmentManager(childFragmentManager);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            throw new IllegalStateException("Parent Screen does not have its Fragment attached".toString());
        }
    }

    protected final FragmentTransaction createTransaction() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager == null) {
            throw new IllegalArgumentException("mFragmentManager is null when creating transaction".toString());
        }
        FragmentTransaction reorderingAllowed = fragmentManager.beginTransaction().setReorderingAllowed(true);
        Intrinsics.checkNotNullExpressionValue(reorderingAllowed, "requireNotNull(mFragment…etReorderingAllowed(true)");
        return reorderingAllowed;
    }

    private final void attachScreen(FragmentTransaction transaction, Fragment fragment) {
        transaction.add(getId(), fragment);
    }

    private final void detachScreen(FragmentTransaction transaction, Fragment fragment) {
        transaction.remove(fragment);
    }

    private final Screen.ActivityState getActivityState(ScreenFragmentWrapper screenFragmentWrapper) {
        return screenFragmentWrapper.getScreen().getActivityState();
    }

    public boolean hasScreen(ScreenFragmentWrapper screenFragmentWrapper) {
        return CollectionsKt.contains(this.mScreenFragments, screenFragmentWrapper);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttached = true;
        setupFragmentManager();
    }

    private final void removeMyFragments(FragmentManager fragmentManager) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(beginTransaction, "fragmentManager.beginTransaction()");
        boolean z = false;
        for (Fragment fragment : fragmentManager.getFragments()) {
            if ((fragment instanceof ScreenFragment) && ((ScreenFragment) fragment).getScreen().getContainer() == this) {
                beginTransaction.remove(fragment);
                z = true;
            }
        }
        if (z) {
            beginTransaction.commitNowAllowingStateLoss();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null && !fragmentManager.isDestroyed()) {
            removeMyFragments(fragmentManager);
            fragmentManager.executePendingTransactions();
        }
        ScreenFragmentWrapper screenFragmentWrapper = this.mParentScreenFragment;
        if (screenFragmentWrapper != null) {
            screenFragmentWrapper.removeChildScreenContainer(this);
        }
        this.mParentScreenFragment = null;
        super.onDetachedFromWindow();
        this.mIsAttached = false;
        int childCount = getChildCount() - 1;
        if (childCount < 0) {
            return;
        }
        while (true) {
            int i = childCount - 1;
            removeViewAt(childCount);
            if (i < 0) {
                return;
            } else {
                childCount = i;
            }
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private final void onScreenChanged() {
        this.mNeedUpdate = true;
        Context context = getContext();
        ReactContext reactContext = context instanceof ReactContext ? (ReactContext) context : null;
        if (reactContext == null) {
            return;
        }
        reactContext.runOnUiQueueThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenContainer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenContainer.m198onScreenChanged$lambda7(ScreenContainer.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onScreenChanged$lambda-7, reason: not valid java name */
    public static final void m198onScreenChanged$lambda7(ScreenContainer this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.performUpdates();
    }

    protected final void performUpdatesNow() {
        this.mNeedUpdate = true;
        performUpdates();
    }

    public final void performUpdates() {
        FragmentManager fragmentManager;
        if (this.mNeedUpdate && this.mIsAttached && (fragmentManager = this.mFragmentManager) != null) {
            if (fragmentManager != null && fragmentManager.isDestroyed()) {
                return;
            }
            this.mNeedUpdate = false;
            onUpdate();
            notifyContainerUpdate();
        }
    }

    public void onUpdate() {
        FragmentTransaction createTransaction = createTransaction();
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null) {
            HashSet hashSet = new HashSet(fragmentManager.getFragments());
            Iterator<ScreenFragmentWrapper> it = this.mScreenFragments.iterator();
            while (it.hasNext()) {
                ScreenFragmentWrapper fragmentWrapper = it.next();
                Intrinsics.checkNotNullExpressionValue(fragmentWrapper, "fragmentWrapper");
                if (getActivityState(fragmentWrapper) == Screen.ActivityState.INACTIVE && fragmentWrapper.getFragment().isAdded()) {
                    detachScreen(createTransaction, fragmentWrapper.getFragment());
                }
                hashSet.remove(fragmentWrapper.getFragment());
            }
            HashSet hashSet2 = hashSet;
            boolean z = false;
            if (!hashSet2.isEmpty()) {
                Object[] array = hashSet2.toArray(new Fragment[0]);
                Intrinsics.checkNotNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                Fragment[] fragmentArr = (Fragment[]) array;
                int length = fragmentArr.length;
                int i = 0;
                while (i < length) {
                    Fragment fragment = fragmentArr[i];
                    i++;
                    if ((fragment instanceof ScreenFragment) && ((ScreenFragment) fragment).getScreen().getContainer() == null) {
                        detachScreen(createTransaction, fragment);
                    }
                }
            }
            boolean z2 = getTopScreen() == null;
            ArrayList arrayList = new ArrayList();
            Iterator<ScreenFragmentWrapper> it2 = this.mScreenFragments.iterator();
            while (it2.hasNext()) {
                ScreenFragmentWrapper fragmentWrapper2 = it2.next();
                Intrinsics.checkNotNullExpressionValue(fragmentWrapper2, "fragmentWrapper");
                Screen.ActivityState activityState = getActivityState(fragmentWrapper2);
                if (activityState != Screen.ActivityState.INACTIVE && !fragmentWrapper2.getFragment().isAdded()) {
                    attachScreen(createTransaction, fragmentWrapper2.getFragment());
                    z = true;
                } else if (activityState != Screen.ActivityState.INACTIVE && z) {
                    detachScreen(createTransaction, fragmentWrapper2.getFragment());
                    arrayList.add(fragmentWrapper2);
                }
                fragmentWrapper2.getScreen().setTransitioning(z2);
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                attachScreen(createTransaction, ((ScreenFragmentWrapper) it3.next()).getFragment());
            }
            createTransaction.commitNowAllowingStateLoss();
            return;
        }
        throw new IllegalArgumentException("mFragmentManager is null when performing update in ScreenContainer".toString());
    }

    protected void notifyContainerUpdate() {
        ScreenFragmentWrapper fragmentWrapper;
        Screen topScreen = getTopScreen();
        if (topScreen == null || (fragmentWrapper = topScreen.getFragmentWrapper()) == null) {
            return;
        }
        fragmentWrapper.onContainerUpdate();
    }
}
