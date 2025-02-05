package com.reactnativecommunity.toolbarandroid;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
public class ReactToolbar extends Toolbar {
    private static final String PROP_ACTION_ICON = "icon";
    private static final String PROP_ACTION_SHOW = "show";
    private static final String PROP_ACTION_SHOW_WITH_TEXT = "showWithText";
    private static final String PROP_ACTION_TITLE = "title";
    private static final String PROP_ICON_HEIGHT = "height";
    private static final String PROP_ICON_URI = "uri";
    private static final String PROP_ICON_WIDTH = "width";
    private final MultiDraweeHolder<GenericDraweeHierarchy> mActionsHolder;
    private final Runnable mLayoutRunnable;
    private IconControllerListener mLogoControllerListener;
    private final DraweeHolder mLogoHolder;
    private IconControllerListener mNavIconControllerListener;
    private final DraweeHolder mNavIconHolder;
    private IconControllerListener mOverflowIconControllerListener;
    private final DraweeHolder mOverflowIconHolder;

    private abstract class IconControllerListener extends BaseControllerListener<ImageInfo> {
        private final DraweeHolder mHolder;
        private IconImageInfo mIconImageInfo;

        protected abstract void setDrawable(Drawable drawable);

        public IconControllerListener(DraweeHolder draweeHolder) {
            this.mHolder = draweeHolder;
        }

        public void setIconImageInfo(IconImageInfo iconImageInfo) {
            this.mIconImageInfo = iconImageInfo;
        }

        @Override // com.facebook.drawee.controller.BaseControllerListener, com.facebook.drawee.controller.ControllerListener
        public void onFinalImageSet(String str, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
            super.onFinalImageSet(str, (String) imageInfo, animatable);
            IconImageInfo iconImageInfo = this.mIconImageInfo;
            if (iconImageInfo != null) {
                imageInfo = iconImageInfo;
            }
            setDrawable(new DrawableWithIntrinsicSize(this.mHolder.getTopLevelDrawable(), imageInfo));
        }
    }

    private class ActionIconControllerListener extends IconControllerListener {
        private final MenuItem mItem;

        ActionIconControllerListener(MenuItem menuItem, DraweeHolder draweeHolder) {
            super(draweeHolder);
            this.mItem = menuItem;
        }

        @Override // com.reactnativecommunity.toolbarandroid.ReactToolbar.IconControllerListener
        protected void setDrawable(Drawable drawable) {
            this.mItem.setIcon(drawable);
            ReactToolbar.this.requestLayout();
        }
    }

    private static class IconImageInfo implements ImageInfo {
        private int mHeight;
        private int mWidth;

        @Override // com.facebook.imagepipeline.image.HasImageMetadata
        public Map<String, Object> getExtras() {
            return null;
        }

        @Override // com.facebook.imagepipeline.image.ImageInfo
        public QualityInfo getQualityInfo() {
            return null;
        }

        public IconImageInfo(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        @Override // com.facebook.imagepipeline.image.ImageInfo
        public int getWidth() {
            return this.mWidth;
        }

        @Override // com.facebook.imagepipeline.image.ImageInfo
        public int getHeight() {
            return this.mHeight;
        }
    }

    public ReactToolbar(Context context) {
        super(context);
        this.mActionsHolder = new MultiDraweeHolder<>();
        this.mLayoutRunnable = new Runnable() { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbar.4
            @Override // java.lang.Runnable
            public void run() {
                ReactToolbar reactToolbar = ReactToolbar.this;
                reactToolbar.measure(View.MeasureSpec.makeMeasureSpec(reactToolbar.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactToolbar.this.getHeight(), 1073741824));
                ReactToolbar reactToolbar2 = ReactToolbar.this;
                reactToolbar2.layout(reactToolbar2.getLeft(), ReactToolbar.this.getTop(), ReactToolbar.this.getRight(), ReactToolbar.this.getBottom());
            }
        };
        DraweeHolder create = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mLogoHolder = create;
        DraweeHolder create2 = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mNavIconHolder = create2;
        DraweeHolder create3 = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mOverflowIconHolder = create3;
        this.mLogoControllerListener = new IconControllerListener(create) { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbar.1
            @Override // com.reactnativecommunity.toolbarandroid.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable drawable) {
                ReactToolbar.this.setLogo(drawable);
            }
        };
        this.mNavIconControllerListener = new IconControllerListener(create2) { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbar.2
            @Override // com.reactnativecommunity.toolbarandroid.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable drawable) {
                ReactToolbar.this.setNavigationIcon(drawable);
            }
        };
        this.mOverflowIconControllerListener = new IconControllerListener(create3) { // from class: com.reactnativecommunity.toolbarandroid.ReactToolbar.3
            @Override // com.reactnativecommunity.toolbarandroid.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable drawable) {
                ReactToolbar.this.setOverflowIcon(drawable);
            }
        };
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        post(this.mLayoutRunnable);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detachDraweeHolders();
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        detachDraweeHolders();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachDraweeHolders();
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        attachDraweeHolders();
    }

    private void detachDraweeHolders() {
        this.mLogoHolder.onDetach();
        this.mNavIconHolder.onDetach();
        this.mOverflowIconHolder.onDetach();
        this.mActionsHolder.onDetach();
    }

    private void attachDraweeHolders() {
        this.mLogoHolder.onAttach();
        this.mNavIconHolder.onAttach();
        this.mOverflowIconHolder.onAttach();
        this.mActionsHolder.onAttach();
    }

    void setLogoSource(@Nullable ReadableMap readableMap) {
        setIconSource(readableMap, this.mLogoControllerListener, this.mLogoHolder);
    }

    void setNavIconSource(@Nullable ReadableMap readableMap) {
        setIconSource(readableMap, this.mNavIconControllerListener, this.mNavIconHolder);
    }

    void setOverflowIconSource(@Nullable ReadableMap readableMap) {
        setIconSource(readableMap, this.mOverflowIconControllerListener, this.mOverflowIconHolder);
    }

    void setActions(@Nullable ReadableArray readableArray) {
        Menu menu = getMenu();
        menu.clear();
        this.mActionsHolder.clear();
        if (readableArray != null) {
            for (int i = 0; i < readableArray.size(); i++) {
                ReadableMap map = readableArray.getMap(i);
                MenuItem add = menu.add(0, 0, i, map.getString("title"));
                if (map.hasKey(PROP_ACTION_ICON)) {
                    setMenuItemIcon(add, map.getMap(PROP_ACTION_ICON));
                }
                int i2 = map.hasKey(PROP_ACTION_SHOW) ? map.getInt(PROP_ACTION_SHOW) : 0;
                if (map.hasKey(PROP_ACTION_SHOW_WITH_TEXT) && map.getBoolean(PROP_ACTION_SHOW_WITH_TEXT)) {
                    i2 |= 4;
                }
                add.setShowAsAction(i2);
            }
        }
    }

    private void setMenuItemIcon(MenuItem menuItem, ReadableMap readableMap) {
        DraweeHolder<GenericDraweeHierarchy> create = DraweeHolder.create(createDraweeHierarchy(), getContext());
        ActionIconControllerListener actionIconControllerListener = new ActionIconControllerListener(menuItem, create);
        actionIconControllerListener.setIconImageInfo(getIconImageInfo(readableMap));
        setIconSource(readableMap, actionIconControllerListener, create);
        this.mActionsHolder.add(create);
    }

    private void setIconSource(ReadableMap readableMap, IconControllerListener iconControllerListener, DraweeHolder draweeHolder) {
        String string = readableMap != null ? readableMap.getString("uri") : null;
        if (string == null) {
            iconControllerListener.setIconImageInfo(null);
            iconControllerListener.setDrawable(null);
        } else {
            if (string.startsWith("http://") || string.startsWith("https://") || string.startsWith("file://")) {
                iconControllerListener.setIconImageInfo(getIconImageInfo(readableMap));
                draweeHolder.setController(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(string)).setControllerListener(iconControllerListener).setOldController(draweeHolder.getController()).build());
                draweeHolder.getTopLevelDrawable().setVisible(true, true);
                return;
            }
            iconControllerListener.setDrawable(getDrawableByName(string));
        }
    }

    private GenericDraweeHierarchy createDraweeHierarchy() {
        return new GenericDraweeHierarchyBuilder(getResources()).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).setFadeDuration(0).build();
    }

    private int getDrawableResourceByName(String str) {
        return getResources().getIdentifier(str, "drawable", getContext().getPackageName());
    }

    private Drawable getDrawableByName(String str) {
        if (getDrawableResourceByName(str) != 0) {
            return getResources().getDrawable(getDrawableResourceByName(str));
        }
        return null;
    }

    private IconImageInfo getIconImageInfo(ReadableMap readableMap) {
        if (readableMap.hasKey("width") && readableMap.hasKey("height")) {
            return new IconImageInfo(Math.round(PixelUtil.toPixelFromDIP(readableMap.getInt("width"))), Math.round(PixelUtil.toPixelFromDIP(readableMap.getInt("height"))));
        }
        return null;
    }
}
