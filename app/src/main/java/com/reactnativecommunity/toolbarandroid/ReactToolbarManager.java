package com.reactnativecommunity.toolbarandroid;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.oblador.keychain.KeychainModule;
import com.reactnativecommunity.toolbarandroid.events.ToolbarClickEvent;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
public class ReactToolbarManager extends ViewGroupManager<ReactToolbar> {
    private static final int COMMAND_DISMISS_POPUP_MENUS = 1;
    private static final String REACT_CLASS = "ToolbarAndroid";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.IViewManagerWithChildren
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactToolbar createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactToolbar(themedReactContext);
    }

    @ReactProp(name = "logo")
    public void setLogo(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setLogoSource(readableMap);
    }

    @ReactProp(name = "navIcon")
    public void setNavIcon(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setNavIconSource(readableMap);
    }

    @ReactProp(name = "overflowIcon")
    public void setOverflowIcon(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setOverflowIconSource(readableMap);
    }

    @ReactProp(name = "rtl")
    public void setRtl(ReactToolbar reactToolbar, boolean z) {
        ViewCompat.setLayoutDirection(reactToolbar, z ? 1 : 0);
    }

    @ReactProp(name = KeychainModule.AuthPromptOptions.SUBTITLE)
    public void setSubtitle(ReactToolbar reactToolbar, @Nullable String str) {
        reactToolbar.setSubtitle(str);
    }

    @ReactProp(customType = "Color", name = "subtitleColor")
    public void setSubtitleColor(ReactToolbar reactToolbar, @Nullable Integer num) {
        int[] defaultColors = getDefaultColors(reactToolbar.getContext());
        if (num != null) {
            reactToolbar.setSubtitleTextColor(num.intValue());
        } else {
            reactToolbar.setSubtitleTextColor(defaultColors[1]);
        }
    }

    @ReactProp(name = KeychainModule.AuthPromptOptions.TITLE)
    public void setTitle(ReactToolbar reactToolbar, @Nullable String str) {
        reactToolbar.setTitle(str);
    }

    @ReactProp(customType = "Color", name = "titleColor")
    public void setTitleColor(ReactToolbar reactToolbar, @Nullable Integer num) {
        int[] defaultColors = getDefaultColors(reactToolbar.getContext());
        if (num != null) {
            reactToolbar.setTitleTextColor(num.intValue());
        } else {
            reactToolbar.setTitleTextColor(defaultColors[0]);
        }
    }

    @ReactProp(defaultFloat = Float.NaN, name = "contentInsetStart")
    public void setContentInsetStart(ReactToolbar reactToolbar, float f) {
        int round;
        if (Float.isNaN(f)) {
            round = getDefaultContentInsets(reactToolbar.getContext())[0];
        } else {
            round = Math.round(PixelUtil.toPixelFromDIP(f));
        }
        reactToolbar.setContentInsetsRelative(round, reactToolbar.getContentInsetEnd());
    }

    @ReactProp(defaultFloat = Float.NaN, name = "contentInsetEnd")
    public void setContentInsetEnd(ReactToolbar reactToolbar, float f) {
        int round;
        if (Float.isNaN(f)) {
            round = getDefaultContentInsets(reactToolbar.getContext())[1];
        } else {
            round = Math.round(PixelUtil.toPixelFromDIP(f));
        }
        reactToolbar.setContentInsetsRelative(reactToolbar.getContentInsetStart(), round);
    }

    @ReactProp(name = "nativeActions")
    public void setActions(ReactToolbar reactToolbar, @Nullable ReadableArray readableArray) {
        reactToolbar.setActions(readableArray);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext themedReactContext, final ReactToolbar reactToolbar) {
        final EventDispatcher eventDispatcher = ((UIManagerModule) themedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        reactToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbarManager.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                eventDispatcher.dispatchEvent(new ToolbarClickEvent(reactToolbar.getId(), -1));
            }
        });
        reactToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbarManager.2
            @Override // androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                eventDispatcher.dispatchEvent(new ToolbarClickEvent(reactToolbar.getId(), menuItem.getOrder()));
                return true;
            }
        });
    }

    @Override // com.facebook.react.uimanager.ViewManager
    @Nullable
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("ShowAsAction", MapBuilder.of(ReactScrollViewHelper.OVER_SCROLL_NEVER, 0, ReactScrollViewHelper.OVER_SCROLL_ALWAYS, 2, "ifRoom", 1));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    @Nullable
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("dismissPopupMenus", 1);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(ReactToolbar reactToolbar, int i, @Nullable ReadableArray readableArray) {
        if (i == 1) {
            reactToolbar.dismissPopupMenus();
            return;
        }
        throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", Integer.valueOf(i), getClass().getSimpleName()));
    }

    private int[] getDefaultContentInsets(Context context) {
        TypedArray typedArray;
        TypedArray obtainStyledAttributes;
        Resources.Theme theme = context.getTheme();
        TypedArray typedArray2 = null;
        try {
            obtainStyledAttributes = theme.obtainStyledAttributes(new int[]{getIdentifier(context, "toolbarStyle")});
        } catch (Throwable th) {
            th = th;
            typedArray = null;
        }
        try {
            typedArray2 = theme.obtainStyledAttributes(obtainStyledAttributes.getResourceId(0, 0), new int[]{getIdentifier(context, "contentInsetStart"), getIdentifier(context, "contentInsetEnd")});
            int[] iArr = {typedArray2.getDimensionPixelSize(0, 0), typedArray2.getDimensionPixelSize(1, 0)};
            recycleQuietly(obtainStyledAttributes);
            recycleQuietly(typedArray2);
            return iArr;
        } catch (Throwable th2) {
            th = th2;
            typedArray = typedArray2;
            typedArray2 = obtainStyledAttributes;
            recycleQuietly(typedArray2);
            recycleQuietly(typedArray);
            throw th;
        }
    }

    private static int[] getDefaultColors(Context context) {
        TypedArray typedArray;
        TypedArray typedArray2;
        TypedArray typedArray3;
        int resourceId;
        Resources.Theme theme = context.getTheme();
        TypedArray typedArray4 = null;
        try {
            TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(new int[]{getIdentifier(context, "toolbarStyle")});
            try {
                typedArray = theme.obtainStyledAttributes(obtainStyledAttributes.getResourceId(0, 0), new int[]{getIdentifier(context, "titleTextAppearance"), getIdentifier(context, "subtitleTextAppearance")});
            } catch (Throwable th) {
                th = th;
                typedArray = null;
                typedArray2 = null;
            }
            try {
                int resourceId2 = typedArray.getResourceId(0, 0);
                resourceId = typedArray.getResourceId(1, 0);
                typedArray3 = theme.obtainStyledAttributes(resourceId2, new int[]{android.R.attr.textColor});
            } catch (Throwable th2) {
                th = th2;
                typedArray2 = null;
                typedArray3 = typedArray2;
                typedArray4 = obtainStyledAttributes;
                recycleQuietly(typedArray4);
                recycleQuietly(typedArray);
                recycleQuietly(typedArray3);
                recycleQuietly(typedArray2);
                throw th;
            }
            try {
                typedArray4 = theme.obtainStyledAttributes(resourceId, new int[]{android.R.attr.textColor});
                int[] iArr = {typedArray3.getColor(0, ViewCompat.MEASURED_STATE_MASK), typedArray4.getColor(0, ViewCompat.MEASURED_STATE_MASK)};
                recycleQuietly(obtainStyledAttributes);
                recycleQuietly(typedArray);
                recycleQuietly(typedArray3);
                recycleQuietly(typedArray4);
                return iArr;
            } catch (Throwable th3) {
                th = th3;
                typedArray2 = typedArray4;
                typedArray4 = obtainStyledAttributes;
                recycleQuietly(typedArray4);
                recycleQuietly(typedArray);
                recycleQuietly(typedArray3);
                recycleQuietly(typedArray2);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            typedArray = null;
            typedArray2 = null;
            typedArray3 = null;
        }
    }

    private static void recycleQuietly(@Nullable TypedArray typedArray) {
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    private static int getIdentifier(Context context, String str) {
        return context.getResources().getIdentifier(str, "attr", context.getPackageName());
    }
}
