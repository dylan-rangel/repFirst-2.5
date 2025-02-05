package com.swmansion.rnscreens;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.text.ReactTypefaceUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.oblador.keychain.KeychainModule;
import com.swmansion.rnscreens.ScreenStackHeaderSubview;
import com.swmansion.rnscreens.events.HeaderAttachedEvent;
import com.swmansion.rnscreens.events.HeaderDetachedEvent;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScreenStackHeaderConfig.kt */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b-\u0018\u00002\u00020\u0001:\u0001kB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0006J\u0006\u0010B\u001a\u00020?J\u000e\u0010C\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0006J\b\u0010D\u001a\u00020?H\u0002J\b\u0010E\u001a\u00020?H\u0014J\b\u0010F\u001a\u00020?H\u0014J0\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020\f2\u0006\u0010I\u001a\u00020\u00062\u0006\u0010J\u001a\u00020\u00062\u0006\u0010K\u001a\u00020\u00062\u0006\u0010L\u001a\u00020\u0006H\u0014J\u0006\u0010M\u001a\u00020?J\u0006\u0010N\u001a\u00020?J\u000e\u0010O\u001a\u00020?2\u0006\u0010A\u001a\u00020\u0006J\u000e\u0010P\u001a\u00020?2\u0006\u0010Q\u001a\u00020\fJ\u0015\u0010R\u001a\u00020?2\b\u0010S\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010TJ\u0010\u0010U\u001a\u00020?2\b\u0010V\u001a\u0004\u0018\u00010\u0018J\u000e\u0010W\u001a\u00020?2\u0006\u0010X\u001a\u00020\fJ\u000e\u0010Y\u001a\u00020?2\u0006\u0010Z\u001a\u00020\fJ\u000e\u0010[\u001a\u00020?2\u0006\u0010\\\u001a\u00020\fJ\u000e\u0010]\u001a\u00020?2\u0006\u0010S\u001a\u00020\u0006J\u0010\u0010^\u001a\u00020?2\b\u0010_\u001a\u0004\u0018\u00010\u0018J\u000e\u0010`\u001a\u00020?2\u0006\u0010S\u001a\u00020\u0006J\u0010\u0010a\u001a\u00020?2\b\u0010b\u001a\u0004\u0018\u00010\u0018J\u000e\u0010c\u001a\u00020?2\u0006\u0010d\u001a\u00020(J\u0010\u0010e\u001a\u00020?2\b\u0010f\u001a\u0004\u0018\u00010\u0018J\u000e\u0010g\u001a\u00020?2\u0006\u0010h\u001a\u00020\fJ\u000e\u0010i\u001a\u00020?2\u0006\u0010j\u001a\u00020\fR\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010*\u001a\u0004\u0018\u00010+8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0013\u0010.\u001a\u0004\u0018\u00010/8F¢\u0006\u0006\u001a\u0004\b0\u00101R\u0016\u00102\u001a\u0004\u0018\u0001038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0016\u00106\u001a\u0004\u0018\u0001078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0011\u0010:\u001a\u00020;¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=¨\u0006l"}, d2 = {"Lcom/swmansion/rnscreens/ScreenStackHeaderConfig;", "Landroid/view/ViewGroup;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "configSubviewsCount", "", "getConfigSubviewsCount", "()I", "headerTopInset", "Ljava/lang/Integer;", "mBackButtonInCustomView", "", "mBackClickListener", "Landroid/view/View$OnClickListener;", "mBackgroundColor", "mConfigSubviews", "Ljava/util/ArrayList;", "Lcom/swmansion/rnscreens/ScreenStackHeaderSubview;", "Lkotlin/collections/ArrayList;", "mDefaultStartInset", "mDefaultStartInsetWithNavigation", "mDestroyed", "mDirection", "", "mIsAttachedToWindow", "mIsBackButtonHidden", "mIsHidden", "getMIsHidden", "()Z", "setMIsHidden", "(Z)V", "mIsShadowHidden", "mIsTopInsetEnabled", "mIsTranslucent", "mTintColor", "mTitle", "mTitleColor", "mTitleFontFamily", "mTitleFontSize", "", "mTitleFontWeight", "screen", "Lcom/swmansion/rnscreens/Screen;", "getScreen", "()Lcom/swmansion/rnscreens/Screen;", "screenFragment", "Lcom/swmansion/rnscreens/ScreenStackFragment;", "getScreenFragment", "()Lcom/swmansion/rnscreens/ScreenStackFragment;", "screenStack", "Lcom/swmansion/rnscreens/ScreenStack;", "getScreenStack", "()Lcom/swmansion/rnscreens/ScreenStack;", "titleTextView", "Landroid/widget/TextView;", "getTitleTextView", "()Landroid/widget/TextView;", "toolbar", "Lcom/swmansion/rnscreens/CustomToolbar;", "getToolbar", "()Lcom/swmansion/rnscreens/CustomToolbar;", "addConfigSubview", "", "child", FirebaseAnalytics.Param.INDEX, "destroy", "getConfigSubview", "maybeUpdate", "onAttachedToWindow", "onDetachedFromWindow", ViewProps.ON_LAYOUT, "changed", "l", "t", "r", "b", "onUpdate", "removeAllConfigSubviews", "removeConfigSubview", "setBackButtonInCustomView", "backButtonInCustomView", "setBackgroundColor", ViewProps.COLOR, "(Ljava/lang/Integer;)V", "setDirection", "direction", "setHidden", ViewProps.HIDDEN, "setHideBackButton", "hideBackButton", "setHideShadow", "hideShadow", "setTintColor", "setTitle", KeychainModule.AuthPromptOptions.TITLE, "setTitleColor", "setTitleFontFamily", "titleFontFamily", "setTitleFontSize", "titleFontSize", "setTitleFontWeight", "fontWeightString", "setTopInsetEnabled", "topInsetEnabled", "setTranslucent", "translucent", "DebugMenuToolbar", "react-native-screens_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ScreenStackHeaderConfig extends ViewGroup {
    private Integer headerTopInset;
    private boolean mBackButtonInCustomView;
    private final View.OnClickListener mBackClickListener;
    private Integer mBackgroundColor;
    private final ArrayList<ScreenStackHeaderSubview> mConfigSubviews;
    private final int mDefaultStartInset;
    private final int mDefaultStartInsetWithNavigation;
    private boolean mDestroyed;
    private String mDirection;
    private boolean mIsAttachedToWindow;
    private boolean mIsBackButtonHidden;
    private boolean mIsHidden;
    private boolean mIsShadowHidden;
    private boolean mIsTopInsetEnabled;
    private boolean mIsTranslucent;
    private int mTintColor;
    private String mTitle;
    private int mTitleColor;
    private String mTitleFontFamily;
    private float mTitleFontSize;
    private int mTitleFontWeight;
    private final CustomToolbar toolbar;

    /* compiled from: ScreenStackHeaderConfig.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScreenStackHeaderSubview.Type.values().length];
            iArr[ScreenStackHeaderSubview.Type.LEFT.ordinal()] = 1;
            iArr[ScreenStackHeaderSubview.Type.RIGHT.ordinal()] = 2;
            iArr[ScreenStackHeaderSubview.Type.CENTER.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenStackHeaderConfig(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mConfigSubviews = new ArrayList<>(3);
        this.mIsTopInsetEnabled = true;
        this.mBackClickListener = new View.OnClickListener() { // from class: com.swmansion.rnscreens.ScreenStackHeaderConfig$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenStackHeaderConfig.m201mBackClickListener$lambda1(ScreenStackHeaderConfig.this, view);
            }
        };
        setVisibility(8);
        CustomToolbar customToolbar = new CustomToolbar(context, this);
        this.toolbar = customToolbar;
        this.mDefaultStartInset = customToolbar.getContentInsetStart();
        this.mDefaultStartInsetWithNavigation = customToolbar.getContentInsetStartWithNavigation();
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true)) {
            customToolbar.setBackgroundColor(typedValue.data);
        }
        customToolbar.setClipChildren(false);
    }

    public final CustomToolbar getToolbar() {
        return this.toolbar;
    }

    public final boolean getMIsHidden() {
        return this.mIsHidden;
    }

    public final void setMIsHidden(boolean z) {
        this.mIsHidden = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: mBackClickListener$lambda-1, reason: not valid java name */
    public static final void m201mBackClickListener$lambda1(ScreenStackHeaderConfig this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ScreenStackFragment screenFragment = this$0.getScreenFragment();
        if (screenFragment == null) {
            return;
        }
        ScreenStack screenStack = this$0.getScreenStack();
        if (screenStack != null && Intrinsics.areEqual(screenStack.getRootScreen(), screenFragment.getScreen())) {
            Fragment parentFragment = screenFragment.getParentFragment();
            if (parentFragment instanceof ScreenStackFragment) {
                ScreenStackFragment screenStackFragment = (ScreenStackFragment) parentFragment;
                if (screenStackFragment.getScreen().getMNativeBackButtonDismissalEnabled()) {
                    screenStackFragment.dismiss();
                    return;
                } else {
                    screenStackFragment.dispatchHeaderBackButtonClickedEvent();
                    return;
                }
            }
            return;
        }
        if (screenFragment.getScreen().getMNativeBackButtonDismissalEnabled()) {
            screenFragment.dismiss();
        } else {
            screenFragment.dispatchHeaderBackButtonClickedEvent();
        }
    }

    public final void destroy() {
        this.mDestroyed = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        Integer valueOf;
        super.onAttachedToWindow();
        this.mIsAttachedToWindow = true;
        int surfaceId = UIManagerHelper.getSurfaceId(this);
        Context context = getContext();
        if (context == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
        }
        EventDispatcher eventDispatcherForReactTag = UIManagerHelper.getEventDispatcherForReactTag((ReactContext) context, getId());
        if (eventDispatcherForReactTag != null) {
            eventDispatcherForReactTag.dispatchEvent(new HeaderAttachedEvent(surfaceId, getId()));
        }
        if (this.headerTopInset == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                valueOf = Integer.valueOf(getRootWindowInsets().getSystemWindowInsetTop());
            } else {
                valueOf = Integer.valueOf((int) (25 * getResources().getDisplayMetrics().density));
            }
            this.headerTopInset = valueOf;
        }
        onUpdate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttachedToWindow = false;
        int surfaceId = UIManagerHelper.getSurfaceId(this);
        Context context = getContext();
        if (context == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
        }
        EventDispatcher eventDispatcherForReactTag = UIManagerHelper.getEventDispatcherForReactTag((ReactContext) context, getId());
        if (eventDispatcherForReactTag == null) {
            return;
        }
        eventDispatcherForReactTag.dispatchEvent(new HeaderDetachedEvent(surfaceId, getId()));
    }

    private final Screen getScreen() {
        ViewParent parent = getParent();
        if (parent instanceof Screen) {
            return (Screen) parent;
        }
        return null;
    }

    private final ScreenStack getScreenStack() {
        Screen screen = getScreen();
        ScreenContainer container = screen == null ? null : screen.getContainer();
        if (container instanceof ScreenStack) {
            return (ScreenStack) container;
        }
        return null;
    }

    public final ScreenStackFragment getScreenFragment() {
        ViewParent parent = getParent();
        if (!(parent instanceof Screen)) {
            return null;
        }
        Fragment fragment = ((Screen) parent).getFragment();
        if (fragment instanceof ScreenStackFragment) {
            return (ScreenStackFragment) fragment;
        }
        return null;
    }

    public final void onUpdate() {
        Drawable navigationIcon;
        ScreenStackFragment screenFragment;
        ScreenStackFragment screenFragment2;
        ReactContext tryGetContext;
        ScreenStack screenStack = getScreenStack();
        boolean z = screenStack == null || Intrinsics.areEqual(screenStack.getTopScreen(), getParent());
        if (this.mIsAttachedToWindow && z && !this.mDestroyed) {
            ScreenStackFragment screenFragment3 = getScreenFragment();
            AppCompatActivity appCompatActivity = (AppCompatActivity) (screenFragment3 == null ? null : screenFragment3.getActivity());
            if (appCompatActivity == null) {
                return;
            }
            String str = this.mDirection;
            if (str != null) {
                if (Intrinsics.areEqual(str, "rtl")) {
                    this.toolbar.setLayoutDirection(1);
                } else if (Intrinsics.areEqual(this.mDirection, "ltr")) {
                    this.toolbar.setLayoutDirection(0);
                }
            }
            Screen screen = getScreen();
            if (screen != null) {
                if (getContext() instanceof ReactContext) {
                    Context context = getContext();
                    if (context == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
                    }
                    tryGetContext = (ReactContext) context;
                } else {
                    ScreenFragmentWrapper fragmentWrapper = screen.getFragmentWrapper();
                    tryGetContext = fragmentWrapper == null ? null : fragmentWrapper.tryGetContext();
                }
                ScreenWindowTraits.INSTANCE.trySetWindowTraits$react_native_screens_release(screen, appCompatActivity, tryGetContext);
            }
            if (this.mIsHidden) {
                if (this.toolbar.getParent() == null || (screenFragment2 = getScreenFragment()) == null) {
                    return;
                }
                screenFragment2.removeToolbar();
                return;
            }
            if (this.toolbar.getParent() == null && (screenFragment = getScreenFragment()) != null) {
                screenFragment.setToolbar(this.toolbar);
            }
            if (this.mIsTopInsetEnabled) {
                Integer num = this.headerTopInset;
                getToolbar().setPadding(0, num == null ? 0 : num.intValue(), 0, 0);
            } else if (this.toolbar.getPaddingTop() > 0) {
                this.toolbar.setPadding(0, 0, 0, 0);
            }
            appCompatActivity.setSupportActionBar(this.toolbar);
            ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
            if (supportActionBar == null) {
                throw new IllegalArgumentException("Required value was null.".toString());
            }
            Intrinsics.checkNotNullExpressionValue(supportActionBar, "requireNotNull(activity.supportActionBar)");
            this.toolbar.setContentInsetStartWithNavigation(this.mDefaultStartInsetWithNavigation);
            CustomToolbar customToolbar = this.toolbar;
            int i = this.mDefaultStartInset;
            customToolbar.setContentInsetsRelative(i, i);
            ScreenStackFragment screenFragment4 = getScreenFragment();
            supportActionBar.setDisplayHomeAsUpEnabled((screenFragment4 != null && screenFragment4.canNavigateBack()) && !this.mIsBackButtonHidden);
            this.toolbar.setNavigationOnClickListener(this.mBackClickListener);
            ScreenStackFragment screenFragment5 = getScreenFragment();
            if (screenFragment5 != null) {
                screenFragment5.setToolbarShadowHidden(this.mIsShadowHidden);
            }
            ScreenStackFragment screenFragment6 = getScreenFragment();
            if (screenFragment6 != null) {
                screenFragment6.setToolbarTranslucent(this.mIsTranslucent);
            }
            supportActionBar.setTitle(this.mTitle);
            if (TextUtils.isEmpty(this.mTitle)) {
                this.toolbar.setContentInsetStartWithNavigation(0);
            }
            TextView titleTextView = getTitleTextView();
            int i2 = this.mTitleColor;
            if (i2 != 0) {
                this.toolbar.setTitleTextColor(i2);
            }
            if (titleTextView != null) {
                String str2 = this.mTitleFontFamily;
                if (str2 != null || this.mTitleFontWeight > 0) {
                    Typeface applyStyles = ReactTypefaceUtils.applyStyles(null, 0, this.mTitleFontWeight, str2, getContext().getAssets());
                    Intrinsics.checkNotNullExpressionValue(applyStyles, "applyStyles(\n           ….assets\n                )");
                    titleTextView.setTypeface(applyStyles);
                }
                float f = this.mTitleFontSize;
                if (f > 0.0f) {
                    titleTextView.setTextSize(f);
                }
            }
            Integer num2 = this.mBackgroundColor;
            if (num2 != null) {
                getToolbar().setBackgroundColor(num2.intValue());
            }
            if (this.mTintColor != 0 && (navigationIcon = this.toolbar.getNavigationIcon()) != null) {
                navigationIcon.setColorFilter(this.mTintColor, PorterDuff.Mode.SRC_ATOP);
            }
            int childCount = this.toolbar.getChildCount() - 1;
            if (childCount >= 0) {
                while (true) {
                    int i3 = childCount - 1;
                    if (this.toolbar.getChildAt(childCount) instanceof ScreenStackHeaderSubview) {
                        this.toolbar.removeViewAt(childCount);
                    }
                    if (i3 < 0) {
                        break;
                    } else {
                        childCount = i3;
                    }
                }
            }
            int size = this.mConfigSubviews.size();
            for (int i4 = 0; i4 < size; i4++) {
                ScreenStackHeaderSubview screenStackHeaderSubview = this.mConfigSubviews.get(i4);
                Intrinsics.checkNotNullExpressionValue(screenStackHeaderSubview, "mConfigSubviews[i]");
                ScreenStackHeaderSubview screenStackHeaderSubview2 = screenStackHeaderSubview;
                ScreenStackHeaderSubview.Type type = screenStackHeaderSubview2.getType();
                if (type == ScreenStackHeaderSubview.Type.BACK) {
                    View childAt = screenStackHeaderSubview2.getChildAt(0);
                    ImageView imageView = childAt instanceof ImageView ? (ImageView) childAt : null;
                    if (imageView == null) {
                        throw new JSApplicationIllegalArgumentException("Back button header config view should have Image as first child");
                    }
                    supportActionBar.setHomeAsUpIndicator(imageView.getDrawable());
                } else {
                    Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -1);
                    int i5 = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
                    if (i5 == 1) {
                        if (!this.mBackButtonInCustomView) {
                            this.toolbar.setNavigationIcon((Drawable) null);
                        }
                        this.toolbar.setTitle((CharSequence) null);
                        layoutParams.gravity = GravityCompat.START;
                    } else if (i5 == 2) {
                        layoutParams.gravity = GravityCompat.END;
                    } else if (i5 == 3) {
                        layoutParams.width = -1;
                        layoutParams.gravity = 1;
                        this.toolbar.setTitle((CharSequence) null);
                    }
                    screenStackHeaderSubview2.setLayoutParams(layoutParams);
                    this.toolbar.addView(screenStackHeaderSubview2);
                }
            }
        }
    }

    private final void maybeUpdate() {
        if (getParent() == null || this.mDestroyed) {
            return;
        }
        onUpdate();
    }

    public final ScreenStackHeaderSubview getConfigSubview(int index) {
        ScreenStackHeaderSubview screenStackHeaderSubview = this.mConfigSubviews.get(index);
        Intrinsics.checkNotNullExpressionValue(screenStackHeaderSubview, "mConfigSubviews[index]");
        return screenStackHeaderSubview;
    }

    public final int getConfigSubviewsCount() {
        return this.mConfigSubviews.size();
    }

    public final void removeConfigSubview(int index) {
        this.mConfigSubviews.remove(index);
        maybeUpdate();
    }

    public final void removeAllConfigSubviews() {
        this.mConfigSubviews.clear();
        maybeUpdate();
    }

    public final void addConfigSubview(ScreenStackHeaderSubview child, int index) {
        Intrinsics.checkNotNullParameter(child, "child");
        this.mConfigSubviews.add(index, child);
        maybeUpdate();
    }

    private final TextView getTitleTextView() {
        int childCount = this.toolbar.getChildCount();
        int i = 0;
        while (i < childCount) {
            int i2 = i + 1;
            View childAt = this.toolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (Intrinsics.areEqual(textView.getText(), this.toolbar.getTitle())) {
                    return textView;
                }
            }
            i = i2;
        }
        return null;
    }

    public final void setTitle(String title) {
        this.mTitle = title;
    }

    public final void setTitleFontFamily(String titleFontFamily) {
        this.mTitleFontFamily = titleFontFamily;
    }

    public final void setTitleFontWeight(String fontWeightString) {
        this.mTitleFontWeight = ReactTypefaceUtils.parseFontWeight(fontWeightString);
    }

    public final void setTitleFontSize(float titleFontSize) {
        this.mTitleFontSize = titleFontSize;
    }

    public final void setTitleColor(int color) {
        this.mTitleColor = color;
    }

    public final void setTintColor(int color) {
        this.mTintColor = color;
    }

    public final void setTopInsetEnabled(boolean topInsetEnabled) {
        this.mIsTopInsetEnabled = topInsetEnabled;
    }

    public final void setBackgroundColor(Integer color) {
        this.mBackgroundColor = color;
    }

    public final void setHideShadow(boolean hideShadow) {
        this.mIsShadowHidden = hideShadow;
    }

    public final void setHideBackButton(boolean hideBackButton) {
        this.mIsBackButtonHidden = hideBackButton;
    }

    public final void setHidden(boolean hidden) {
        this.mIsHidden = hidden;
    }

    public final void setTranslucent(boolean translucent) {
        this.mIsTranslucent = translucent;
    }

    public final void setBackButtonInCustomView(boolean backButtonInCustomView) {
        this.mBackButtonInCustomView = backButtonInCustomView;
    }

    public final void setDirection(String direction) {
        this.mDirection = direction;
    }

    /* compiled from: ScreenStackHeaderConfig.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/swmansion/rnscreens/ScreenStackHeaderConfig$DebugMenuToolbar;", "Lcom/swmansion/rnscreens/CustomToolbar;", "context", "Landroid/content/Context;", "config", "Lcom/swmansion/rnscreens/ScreenStackHeaderConfig;", "(Landroid/content/Context;Lcom/swmansion/rnscreens/ScreenStackHeaderConfig;)V", "showOverflowMenu", "", "react-native-screens_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    private static final class DebugMenuToolbar extends CustomToolbar {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DebugMenuToolbar(Context context, ScreenStackHeaderConfig config) {
            super(context, config);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(config, "config");
        }

        @Override // androidx.appcompat.widget.Toolbar
        public boolean showOverflowMenu() {
            Object applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.facebook.react.ReactApplication");
            }
            ((ReactApplication) applicationContext).getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
            return true;
        }
    }
}
