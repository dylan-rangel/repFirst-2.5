package com.swmansion.rnscreens;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.ViewProps;
import com.swmansion.rnscreens.Screen;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScreenWindowTraits.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\n\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\fJ\r\u0010\r\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u000eJ\r\u0010\u000f\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J)\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0001¢\u0006\u0002\b J\u001f\u0010!\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b\"J\u001f\u0010#\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b$J\u001f\u0010%\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b&J\u001f\u0010'\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b(J)\u0010)\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b*J)\u0010+\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b,J)\u0010-\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b.R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/swmansion/rnscreens/ScreenWindowTraits;", "", "()V", "mDefaultStatusBarColor", "", "Ljava/lang/Integer;", "mDidSetNavigationBarAppearance", "", "mDidSetOrientation", "mDidSetStatusBarAppearance", "applyDidSetNavigationBarAppearance", "", "applyDidSetNavigationBarAppearance$react_native_screens_release", "applyDidSetOrientation", "applyDidSetOrientation$react_native_screens_release", "applyDidSetStatusBarAppearance", "applyDidSetStatusBarAppearance$react_native_screens_release", "checkTraitForScreen", "screen", "Lcom/swmansion/rnscreens/Screen;", "trait", "Lcom/swmansion/rnscreens/Screen$WindowTraits;", "childScreenWithTraitSet", "findParentWithTraitSet", "findScreenForTrait", "isColorLight", ViewProps.COLOR, "setColor", "activity", "Landroid/app/Activity;", "context", "Lcom/facebook/react/bridge/ReactContext;", "setColor$react_native_screens_release", "setHidden", "setHidden$react_native_screens_release", "setNavigationBarColor", "setNavigationBarColor$react_native_screens_release", "setNavigationBarHidden", "setNavigationBarHidden$react_native_screens_release", "setOrientation", "setOrientation$react_native_screens_release", "setStyle", "setStyle$react_native_screens_release", "setTranslucent", "setTranslucent$react_native_screens_release", "trySetWindowTraits", "trySetWindowTraits$react_native_screens_release", "react-native-screens_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ScreenWindowTraits {
    public static final ScreenWindowTraits INSTANCE = new ScreenWindowTraits();
    private static Integer mDefaultStatusBarColor;
    private static boolean mDidSetNavigationBarAppearance;
    private static boolean mDidSetOrientation;
    private static boolean mDidSetStatusBarAppearance;

    /* compiled from: ScreenWindowTraits.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Screen.WindowTraits.values().length];
            iArr[Screen.WindowTraits.ORIENTATION.ordinal()] = 1;
            iArr[Screen.WindowTraits.COLOR.ordinal()] = 2;
            iArr[Screen.WindowTraits.STYLE.ordinal()] = 3;
            iArr[Screen.WindowTraits.TRANSLUCENT.ordinal()] = 4;
            iArr[Screen.WindowTraits.HIDDEN.ordinal()] = 5;
            iArr[Screen.WindowTraits.ANIMATED.ordinal()] = 6;
            iArr[Screen.WindowTraits.NAVIGATION_BAR_COLOR.ordinal()] = 7;
            iArr[Screen.WindowTraits.NAVIGATION_BAR_HIDDEN.ordinal()] = 8;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ScreenWindowTraits() {
    }

    public final void applyDidSetOrientation$react_native_screens_release() {
        mDidSetOrientation = true;
    }

    public final void applyDidSetStatusBarAppearance$react_native_screens_release() {
        mDidSetStatusBarAppearance = true;
    }

    public final void applyDidSetNavigationBarAppearance$react_native_screens_release() {
        mDidSetNavigationBarAppearance = true;
    }

    public final void setOrientation$react_native_screens_release(Screen screen, Activity activity) {
        Integer screenOrientation;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.ORIENTATION);
        int i = -1;
        if (findScreenForTrait != null && (screenOrientation = findScreenForTrait.getScreenOrientation()) != null) {
            i = screenOrientation.intValue();
        }
        activity.setRequestedOrientation(i);
    }

    public final void setColor$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Boolean isStatusBarAnimated;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null) {
            return;
        }
        if (mDefaultStatusBarColor == null) {
            mDefaultStatusBarColor = Integer.valueOf(activity.getWindow().getStatusBarColor());
        }
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.COLOR);
        Screen findScreenForTrait2 = findScreenForTrait(screen, Screen.WindowTraits.ANIMATED);
        Integer mStatusBarColor = findScreenForTrait == null ? null : findScreenForTrait.getMStatusBarColor();
        if (mStatusBarColor == null) {
            mStatusBarColor = mDefaultStatusBarColor;
        }
        boolean z = false;
        if (findScreenForTrait2 != null && (isStatusBarAnimated = findScreenForTrait2.getIsStatusBarAnimated()) != null) {
            z = isStatusBarAnimated.booleanValue();
        }
        UiThreadUtil.runOnUiThread(new ScreenWindowTraits$setColor$1(context, activity, mStatusBarColor, z));
    }

    public final void setStyle$react_native_screens_release(Screen screen, final Activity activity, ReactContext context) {
        String mStatusBarStyle;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null || Build.VERSION.SDK_INT < 23) {
            return;
        }
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.STYLE);
        final String str = "light";
        if (findScreenForTrait != null && (mStatusBarStyle = findScreenForTrait.getMStatusBarStyle()) != null) {
            str = mStatusBarStyle;
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.m204setStyle$lambda0(activity, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setStyle$lambda-0, reason: not valid java name */
    public static final void m204setStyle$lambda0(Activity activity, String style) {
        Intrinsics.checkNotNullParameter(style, "$style");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "activity.window.decorView");
        new WindowInsetsControllerCompat(activity.getWindow(), decorView).setAppearanceLightStatusBars(Intrinsics.areEqual(style, "dark"));
    }

    public final void setTranslucent$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Boolean mStatusBarTranslucent;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null) {
            return;
        }
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.TRANSLUCENT);
        boolean z = false;
        if (findScreenForTrait != null && (mStatusBarTranslucent = findScreenForTrait.getMStatusBarTranslucent()) != null) {
            z = mStatusBarTranslucent.booleanValue();
        }
        UiThreadUtil.runOnUiThread(new ScreenWindowTraits$setTranslucent$1(context, activity, z));
    }

    public final void setHidden$react_native_screens_release(Screen screen, Activity activity) {
        Boolean mStatusBarHidden;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.HIDDEN);
        final boolean z = false;
        if (findScreenForTrait != null && (mStatusBarHidden = findScreenForTrait.getMStatusBarHidden()) != null) {
            z = mStatusBarHidden.booleanValue();
        }
        Window window = activity.getWindow();
        final WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, window.getDecorView());
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.m202setHidden$lambda1(z, windowInsetsControllerCompat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setHidden$lambda-1, reason: not valid java name */
    public static final void m202setHidden$lambda1(boolean z, WindowInsetsControllerCompat controller) {
        Intrinsics.checkNotNullParameter(controller, "$controller");
        if (z) {
            controller.hide(WindowInsetsCompat.Type.statusBars());
        } else {
            controller.show(WindowInsetsCompat.Type.statusBars());
        }
    }

    public final void setNavigationBarColor$react_native_screens_release(Screen screen, Activity activity) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        final Window window = activity.getWindow();
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.NAVIGATION_BAR_COLOR);
        Integer mNavigationBarColor = findScreenForTrait == null ? null : findScreenForTrait.getMNavigationBarColor();
        final int navigationBarColor = mNavigationBarColor == null ? window.getNavigationBarColor() : mNavigationBarColor.intValue();
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.m203setNavigationBarColor$lambda2(window, navigationBarColor);
            }
        });
        window.setNavigationBarColor(navigationBarColor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setNavigationBarColor$lambda-2, reason: not valid java name */
    public static final void m203setNavigationBarColor$lambda2(Window window, int i) {
        new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightNavigationBars(INSTANCE.isColorLight(i));
    }

    public final void setNavigationBarHidden$react_native_screens_release(Screen screen, Activity activity) {
        Boolean mNavigationBarHidden;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        Screen findScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.NAVIGATION_BAR_HIDDEN);
        boolean z = false;
        if (findScreenForTrait != null && (mNavigationBarHidden = findScreenForTrait.getMNavigationBarHidden()) != null) {
            z = mNavigationBarHidden.booleanValue();
        }
        WindowCompat.setDecorFitsSystemWindows(window, z);
        if (z) {
            WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, window.getDecorView());
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.navigationBars());
            windowInsetsControllerCompat.setSystemBarsBehavior(2);
            return;
        }
        new WindowInsetsControllerCompat(window, window.getDecorView()).show(WindowInsetsCompat.Type.navigationBars());
    }

    public final void trySetWindowTraits$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (mDidSetOrientation) {
            setOrientation$react_native_screens_release(screen, activity);
        }
        if (mDidSetStatusBarAppearance) {
            setColor$react_native_screens_release(screen, activity, context);
            setStyle$react_native_screens_release(screen, activity, context);
            setTranslucent$react_native_screens_release(screen, activity, context);
            setHidden$react_native_screens_release(screen, activity);
        }
        if (mDidSetNavigationBarAppearance) {
            setNavigationBarColor$react_native_screens_release(screen, activity);
            setNavigationBarHidden$react_native_screens_release(screen, activity);
        }
    }

    private final Screen findScreenForTrait(Screen screen, Screen.WindowTraits trait) {
        Screen childScreenWithTraitSet = childScreenWithTraitSet(screen, trait);
        return childScreenWithTraitSet != null ? childScreenWithTraitSet : checkTraitForScreen(screen, trait) ? screen : findParentWithTraitSet(screen, trait);
    }

    private final Screen findParentWithTraitSet(Screen screen, Screen.WindowTraits trait) {
        for (ViewParent container = screen.getContainer(); container != null; container = container.getParent()) {
            if (container instanceof Screen) {
                Screen screen2 = (Screen) container;
                if (checkTraitForScreen(screen2, trait)) {
                    return screen2;
                }
            }
        }
        return null;
    }

    private final Screen childScreenWithTraitSet(Screen screen, Screen.WindowTraits trait) {
        ScreenFragmentWrapper fragmentWrapper;
        if (screen == null || (fragmentWrapper = screen.getFragmentWrapper()) == null) {
            return null;
        }
        Iterator<ScreenContainer> it = fragmentWrapper.getChildScreenContainers().iterator();
        while (it.hasNext()) {
            Screen topScreen = it.next().getTopScreen();
            ScreenWindowTraits screenWindowTraits = INSTANCE;
            Screen childScreenWithTraitSet = screenWindowTraits.childScreenWithTraitSet(topScreen, trait);
            if (childScreenWithTraitSet != null) {
                return childScreenWithTraitSet;
            }
            if (topScreen != null && screenWindowTraits.checkTraitForScreen(topScreen, trait)) {
                return topScreen;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x004b A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean checkTraitForScreen(com.swmansion.rnscreens.Screen r3, com.swmansion.rnscreens.Screen.WindowTraits r4) {
        /*
            r2 = this;
            int[] r0 = com.swmansion.rnscreens.ScreenWindowTraits.WhenMappings.$EnumSwitchMapping$0
            int r4 = r4.ordinal()
            r4 = r0[r4]
            r0 = 1
            r1 = 0
            switch(r4) {
                case 1: goto L44;
                case 2: goto L3d;
                case 3: goto L36;
                case 4: goto L2f;
                case 5: goto L28;
                case 6: goto L21;
                case 7: goto L1a;
                case 8: goto L13;
                default: goto Ld;
            }
        Ld:
            kotlin.NoWhenBranchMatchedException r3 = new kotlin.NoWhenBranchMatchedException
            r3.<init>()
            throw r3
        L13:
            java.lang.Boolean r3 = r3.getMNavigationBarHidden()
            if (r3 == 0) goto L4b
            goto L4c
        L1a:
            java.lang.Integer r3 = r3.getMNavigationBarColor()
            if (r3 == 0) goto L4b
            goto L4c
        L21:
            java.lang.Boolean r3 = r3.getIsStatusBarAnimated()
            if (r3 == 0) goto L4b
            goto L4c
        L28:
            java.lang.Boolean r3 = r3.getMStatusBarHidden()
            if (r3 == 0) goto L4b
            goto L4c
        L2f:
            java.lang.Boolean r3 = r3.getMStatusBarTranslucent()
            if (r3 == 0) goto L4b
            goto L4c
        L36:
            java.lang.String r3 = r3.getMStatusBarStyle()
            if (r3 == 0) goto L4b
            goto L4c
        L3d:
            java.lang.Integer r3 = r3.getMStatusBarColor()
            if (r3 == 0) goto L4b
            goto L4c
        L44:
            java.lang.Integer r3 = r3.getScreenOrientation()
            if (r3 == 0) goto L4b
            goto L4c
        L4b:
            r0 = 0
        L4c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenWindowTraits.checkTraitForScreen(com.swmansion.rnscreens.Screen, com.swmansion.rnscreens.Screen$WindowTraits):boolean");
    }

    private final boolean isColorLight(int color) {
        return ((double) 1) - ((((((double) Color.red(color)) * 0.299d) + (((double) Color.green(color)) * 0.587d)) + (((double) Color.blue(color)) * 0.114d)) / ((double) 255)) < 0.5d;
    }
}
