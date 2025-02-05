package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGSvgViewAndroidManagerInterface;
import com.oblador.keychain.KeychainModule;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGSvgViewAndroidManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGSvgViewAndroidManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGSvgViewAndroidManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2064426617:
                if (str.equals("bbHeight")) {
                    c = 0;
                    break;
                }
                break;
            case -1989576717:
                if (str.equals(ViewProps.BORDER_RIGHT_COLOR)) {
                    c = 1;
                    break;
                }
                break;
            case -1697814026:
                if (str.equals("backfaceVisibility")) {
                    c = 2;
                    break;
                }
                break;
            case -1567958285:
                if (str.equals("vbHeight")) {
                    c = 3;
                    break;
                }
                break;
            case -1470826662:
                if (str.equals(ViewProps.BORDER_TOP_COLOR)) {
                    c = 4;
                    break;
                }
                break;
            case -1308858324:
                if (str.equals(ViewProps.BORDER_BOTTOM_COLOR)) {
                    c = 5;
                    break;
                }
                break;
            case -1228066334:
                if (str.equals(ViewProps.BORDER_TOP_LEFT_RADIUS)) {
                    c = 6;
                    break;
                }
                break;
            case -1141400650:
                if (str.equals(KeychainModule.Maps.ACCESSIBLE)) {
                    c = 7;
                    break;
                }
                break;
            case -1122140597:
                if (str.equals(ViewProps.BORDER_TOP_START_RADIUS)) {
                    c = '\b';
                    break;
                }
                break;
            case -867333731:
                if (str.equals(ViewProps.BORDER_BOTTOM_START_RADIUS)) {
                    c = '\t';
                    break;
                }
                break;
            case -679581037:
                if (str.equals("hasTVPreferredFocus")) {
                    c = '\n';
                    break;
                }
                break;
            case -631506969:
                if (str.equals("nextFocusDown")) {
                    c = 11;
                    break;
                }
                break;
            case -631278772:
                if (str.equals("nextFocusLeft")) {
                    c = '\f';
                    break;
                }
                break;
            case -483490364:
                if (str.equals(ViewProps.BORDER_TOP_END_RADIUS)) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case -329721498:
                if (str.equals("bbWidth")) {
                    c = 14;
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = 15;
                    break;
                }
                break;
            case -252105751:
                if (str.equals(ReactClippingViewGroupHelper.PROP_REMOVE_CLIPPED_SUBVIEWS)) {
                    c = 16;
                    break;
                }
                break;
            case -242276144:
                if (str.equals(ViewProps.BORDER_LEFT_COLOR)) {
                    c = 17;
                    break;
                }
                break;
            case -148030058:
                if (str.equals(ViewProps.BORDER_BOTTOM_END_RADIUS)) {
                    c = 18;
                    break;
                }
                break;
            case -109689771:
                if (str.equals("nativeForegroundAndroid")) {
                    c = 19;
                    break;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    c = 20;
                    break;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    c = 21;
                    break;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    c = 22;
                    break;
                }
                break;
            case 94842723:
                if (str.equals(ViewProps.COLOR)) {
                    c = 23;
                    break;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    c = 24;
                    break;
                }
                break;
            case 333432965:
                if (str.equals(ViewProps.BORDER_TOP_RIGHT_RADIUS)) {
                    c = 25;
                    break;
                }
                break;
            case 503397728:
                if (str.equals("nextFocusForward")) {
                    c = 26;
                    break;
                }
                break;
            case 581268560:
                if (str.equals(ViewProps.BORDER_BOTTOM_LEFT_RADIUS)) {
                    c = 27;
                    break;
                }
                break;
            case 588239831:
                if (str.equals(ViewProps.BORDER_BOTTOM_RIGHT_RADIUS)) {
                    c = 28;
                    break;
                }
                break;
            case 660795168:
                if (str.equals("nextFocusUp")) {
                    c = 29;
                    break;
                }
                break;
            case 722830999:
                if (str.equals(ViewProps.BORDER_COLOR)) {
                    c = 30;
                    break;
                }
                break;
            case 737768677:
                if (str.equals("borderStyle")) {
                    c = 31;
                    break;
                }
                break;
            case 926871597:
                if (str.equals("hitSlop")) {
                    c = ' ';
                    break;
                }
                break;
            case 1220735892:
                if (str.equals(ViewProps.BORDER_END_COLOR)) {
                    c = '!';
                    break;
                }
                break;
            case 1327599912:
                if (str.equals("tintColor")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 1349188574:
                if (str.equals(ViewProps.BORDER_RADIUS)) {
                    c = '#';
                    break;
                }
                break;
            case 1629011506:
                if (str.equals("focusable")) {
                    c = '$';
                    break;
                }
                break;
            case 1667773924:
                if (str.equals(ViewProps.NEEDS_OFFSCREEN_ALPHA_COMPOSITING)) {
                    c = '%';
                    break;
                }
                break;
            case 1747724810:
                if (str.equals("nativeBackgroundAndroid")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    c = '\'';
                    break;
                }
                break;
            case 1910855543:
                if (str.equals("nextFocusRight")) {
                    c = '(';
                    break;
                }
                break;
            case 2119889261:
                if (str.equals(ViewProps.BORDER_START_COLOR)) {
                    c = ')';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (obj instanceof String) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight((RNSVGSvgViewAndroidManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight((RNSVGSvgViewAndroidManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight((RNSVGSvgViewAndroidManagerInterface) t, (Double) null);
                    break;
                }
            case 1:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderRightColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 2:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBackfaceVisibility(t, obj != null ? (String) obj : null);
                break;
            case 3:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 4:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 5:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 6:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopLeftRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 7:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setAccessible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '\b':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopStartRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\t':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomStartRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\n':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setHasTVPreferredFocus(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 11:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusDown(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\f':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusLeft(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\r':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopEndRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth((RNSVGSvgViewAndroidManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth((RNSVGSvgViewAndroidManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth((RNSVGSvgViewAndroidManagerInterface) t, (Double) null);
                    break;
                }
            case 15:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setRemoveClippedSubviews(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 17:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderLeftColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 18:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomEndRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNativeForegroundAndroid(t, (ReadableMap) obj);
                break;
            case 20:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 21:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 22:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 24:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 25:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopRightRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 26:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusForward(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomLeftRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 28:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomRightRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 29:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusUp(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 30:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 31:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderStyle(t, obj != null ? (String) obj : null);
                break;
            case ' ':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setHitSlop(t, (ReadableMap) obj);
                break;
            case '!':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderEndColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case '\"':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case '#':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case '$':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setFocusable(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '%':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNeedsOffscreenAlphaCompositing(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '&':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNativeBackgroundAndroid(t, (ReadableMap) obj);
                break;
            case '\'':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '(':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusRight(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case ')':
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderStartColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
