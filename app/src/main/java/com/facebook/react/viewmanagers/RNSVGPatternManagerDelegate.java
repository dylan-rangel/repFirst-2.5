package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGPatternManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGPatternManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGPatternManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGPatternManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1567958285:
                if (str.equals("vbHeight")) {
                    c = 0;
                    break;
                }
                break;
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    c = 1;
                    break;
                }
                break;
            case -1221029593:
                if (str.equals("height")) {
                    c = 2;
                    break;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 3;
                    break;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    c = 4;
                    break;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = 5;
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = 6;
                    break;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    c = 7;
                    break;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    c = '\b';
                    break;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    c = '\t';
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = '\n';
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = 11;
                    break;
                }
                break;
            case -207800897:
                if (str.equals("patternUnits")) {
                    c = '\f';
                    break;
                }
                break;
            case -128680410:
                if (str.equals("patternContentUnits")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = 14;
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = 15;
                    break;
                }
                break;
            case 120:
                if (str.equals(ViewHierarchyNode.JsonKeys.X)) {
                    c = 16;
                    break;
                }
                break;
            case 121:
                if (str.equals(ViewHierarchyNode.JsonKeys.Y)) {
                    c = 17;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = 18;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    c = 19;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 20;
                    break;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    c = 21;
                    break;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    c = 22;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 23;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 24;
                    break;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    c = 25;
                    break;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 26;
                    break;
                }
                break;
            case 113126854:
                if (str.equals("width")) {
                    c = 27;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 28;
                    break;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    c = 29;
                    break;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    c = 30;
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 31;
                    break;
                }
                break;
            case 746561980:
                if (str.equals("patternTransform")) {
                    c = ' ';
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = '!';
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = '#';
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = '$';
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = '%';
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
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
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = '(';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case 3:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 5:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case '\b':
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case '\t':
                ((RNSVGPatternManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\n':
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case '\f':
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\r':
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternContentUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 14:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 15:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 16:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case 17:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case 18:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 19:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 20:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 21:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 22:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 23:
                ((RNSVGPatternManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 24:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 25:
                ((RNSVGPatternManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case 28:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 29:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize((RNSVGPatternManagerInterface) t, (Double) null);
                    break;
                }
            case 31:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGPatternManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case ' ':
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternTransform(t, (ReadableArray) obj);
                break;
            case '!':
                ((RNSVGPatternManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case '\"':
                ((RNSVGPatternManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '#':
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '$':
                ((RNSVGPatternManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case '%':
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '&':
                ((RNSVGPatternManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '\'':
                ((RNSVGPatternManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '(':
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth((RNSVGPatternManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth((RNSVGPatternManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth((RNSVGPatternManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
