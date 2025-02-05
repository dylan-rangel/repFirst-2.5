package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.DynamicFromObject;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public abstract class BaseViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T>> implements ViewManagerDelegate<T> {
    protected final U mViewManager;

    @Override // com.facebook.react.uimanager.ViewManagerDelegate
    public void receiveCommand(T t, String str, ReadableArray readableArray) {
    }

    public BaseViewManagerDelegate(U u) {
        this.mViewManager = u;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1721943862:
                if (str.equals(ViewProps.TRANSLATE_X)) {
                    c = 0;
                    break;
                }
                break;
            case -1721943861:
                if (str.equals(ViewProps.TRANSLATE_Y)) {
                    c = 1;
                    break;
                }
                break;
            case -1589741021:
                if (str.equals(ViewProps.SHADOW_COLOR)) {
                    c = 2;
                    break;
                }
                break;
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    c = 3;
                    break;
                }
                break;
            case -1228066334:
                if (str.equals(ViewProps.BORDER_TOP_LEFT_RADIUS)) {
                    c = 4;
                    break;
                }
                break;
            case -908189618:
                if (str.equals(ViewProps.SCALE_X)) {
                    c = 5;
                    break;
                }
                break;
            case -908189617:
                if (str.equals(ViewProps.SCALE_Y)) {
                    c = 6;
                    break;
                }
                break;
            case -877170387:
                if (str.equals(ViewProps.TEST_ID)) {
                    c = 7;
                    break;
                }
                break;
            case -731417480:
                if (str.equals(ViewProps.Z_INDEX)) {
                    c = '\b';
                    break;
                }
                break;
            case -101663499:
                if (str.equals(ViewProps.ACCESSIBILITY_HINT)) {
                    c = '\t';
                    break;
                }
                break;
            case -101359900:
                if (str.equals(ViewProps.ACCESSIBILITY_ROLE)) {
                    c = '\n';
                    break;
                }
                break;
            case -80891667:
                if (str.equals(ViewProps.RENDER_TO_HARDWARE_TEXTURE)) {
                    c = 11;
                    break;
                }
                break;
            case -40300674:
                if (str.equals(ViewProps.ROTATION)) {
                    c = '\f';
                    break;
                }
                break;
            case -4379043:
                if (str.equals(ViewProps.ELEVATION)) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 36255470:
                if (str.equals(ViewProps.ACCESSIBILITY_LIVE_REGION)) {
                    c = 14;
                    break;
                }
                break;
            case 333432965:
                if (str.equals(ViewProps.BORDER_TOP_RIGHT_RADIUS)) {
                    c = 15;
                    break;
                }
                break;
            case 581268560:
                if (str.equals(ViewProps.BORDER_BOTTOM_LEFT_RADIUS)) {
                    c = 16;
                    break;
                }
                break;
            case 588239831:
                if (str.equals(ViewProps.BORDER_BOTTOM_RIGHT_RADIUS)) {
                    c = 17;
                    break;
                }
                break;
            case 746986311:
                if (str.equals(ViewProps.IMPORTANT_FOR_ACCESSIBILITY)) {
                    c = 18;
                    break;
                }
                break;
            case 1052666732:
                if (str.equals(ViewProps.TRANSFORM)) {
                    c = 19;
                    break;
                }
                break;
            case 1146842694:
                if (str.equals(ViewProps.ACCESSIBILITY_LABEL)) {
                    c = 20;
                    break;
                }
                break;
            case 1153872867:
                if (str.equals(ViewProps.ACCESSIBILITY_STATE)) {
                    c = 21;
                    break;
                }
                break;
            case 1287124693:
                if (str.equals(ViewProps.BACKGROUND_COLOR)) {
                    c = 22;
                    break;
                }
                break;
            case 1349188574:
                if (str.equals(ViewProps.BORDER_RADIUS)) {
                    c = 23;
                    break;
                }
                break;
            case 1505602511:
                if (str.equals(ViewProps.ACCESSIBILITY_ACTIONS)) {
                    c = 24;
                    break;
                }
                break;
            case 1761903244:
                if (str.equals(ViewProps.ACCESSIBILITY_COLLECTION)) {
                    c = 25;
                    break;
                }
                break;
            case 1865277756:
                if (str.equals(ViewProps.ACCESSIBILITY_LABELLED_BY)) {
                    c = 26;
                    break;
                }
                break;
            case 1993034687:
                if (str.equals(ViewProps.ACCESSIBILITY_COLLECTION_ITEM)) {
                    c = 27;
                    break;
                }
                break;
            case 2045685618:
                if (str.equals(ViewProps.NATIVE_ID)) {
                    c = 28;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mViewManager.setTranslateX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setTranslateY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 2:
                this.mViewManager.setShadowColor(t, obj != null ? ColorPropConverter.getColor(obj, t.getContext()).intValue() : 0);
                break;
            case 3:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 4:
                this.mViewManager.setBorderTopLeftRadius(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 5:
                this.mViewManager.setScaleX(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 6:
                this.mViewManager.setScaleY(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 7:
                this.mViewManager.setTestId(t, (String) obj);
                break;
            case '\b':
                this.mViewManager.setZIndex(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\t':
                this.mViewManager.setAccessibilityHint(t, (String) obj);
                break;
            case '\n':
                this.mViewManager.setAccessibilityRole(t, (String) obj);
                break;
            case 11:
                this.mViewManager.setRenderToHardwareTexture(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '\f':
                this.mViewManager.setRotation(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\r':
                this.mViewManager.setElevation(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                this.mViewManager.setAccessibilityLiveRegion(t, (String) obj);
                break;
            case 15:
                this.mViewManager.setBorderTopRightRadius(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 16:
                this.mViewManager.setBorderBottomLeftRadius(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 17:
                this.mViewManager.setBorderBottomRightRadius(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 18:
                this.mViewManager.setImportantForAccessibility(t, (String) obj);
                break;
            case 19:
                this.mViewManager.setTransform(t, (ReadableArray) obj);
                break;
            case 20:
                this.mViewManager.setAccessibilityLabel(t, (String) obj);
                break;
            case 21:
                this.mViewManager.setViewState(t, (ReadableMap) obj);
                break;
            case 22:
                this.mViewManager.setBackgroundColor(t, obj != null ? ColorPropConverter.getColor(obj, t.getContext()).intValue() : 0);
                break;
            case 23:
                this.mViewManager.setBorderRadius(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 24:
                this.mViewManager.setAccessibilityActions(t, (ReadableArray) obj);
                break;
            case 25:
                this.mViewManager.setAccessibilityCollection(t, (ReadableMap) obj);
                break;
            case 26:
                this.mViewManager.setAccessibilityLabelledBy(t, new DynamicFromObject(obj));
                break;
            case 27:
                this.mViewManager.setAccessibilityCollectionItem(t, (ReadableMap) obj);
                break;
            case 28:
                this.mViewManager.setNativeId(t, (String) obj);
                break;
        }
    }
}
