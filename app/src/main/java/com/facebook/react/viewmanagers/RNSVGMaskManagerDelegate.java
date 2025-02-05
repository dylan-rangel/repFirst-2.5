package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGMaskManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGMaskManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGMaskManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGMaskManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    c = 0;
                    break;
                }
                break;
            case -1221029593:
                if (str.equals("height")) {
                    c = 1;
                    break;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 2;
                    break;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    c = 3;
                    break;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = 4;
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = 5;
                    break;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    c = 6;
                    break;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    c = 7;
                    break;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    c = '\b';
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = '\t';
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = '\n';
                    break;
                }
                break;
            case -61221917:
                if (str.equals("maskUnits")) {
                    c = 11;
                    break;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = '\f';
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 120:
                if (str.equals(ViewHierarchyNode.JsonKeys.X)) {
                    c = 14;
                    break;
                }
                break;
            case 121:
                if (str.equals(ViewHierarchyNode.JsonKeys.Y)) {
                    c = 15;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = 16;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    c = 17;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 18;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 19;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 20;
                    break;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 21;
                    break;
                }
                break;
            case 113126854:
                if (str.equals("width")) {
                    c = 22;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 23;
                    break;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    c = 24;
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 25;
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = 26;
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = 27;
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = 28;
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = 29;
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = 30;
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = 31;
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = ' ';
                    break;
                }
                break;
            case 2037673858:
                if (str.equals("maskContentUnits")) {
                    c = '!';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setHeight((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setHeight((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setHeight((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case 2:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGMaskManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGMaskManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontWeight((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontWeight((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontWeight((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case '\b':
                ((RNSVGMaskManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\t':
                ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\n':
                ((RNSVGMaskManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMaskUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\f':
                ((RNSVGMaskManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\r':
                ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setX((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setX((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setX((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case 15:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setY((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setY((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setY((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case 16:
                ((RNSVGMaskManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 17:
                ((RNSVGMaskManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 18:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGMaskManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 20:
                ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 21:
                ((RNSVGMaskManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 22:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setWidth((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setWidth((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setWidth((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case 23:
                ((RNSVGMaskManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 24:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontSize((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontSize((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setFontSize((RNSVGMaskManagerInterface) t, (Double) null);
                    break;
                }
            case 25:
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGMaskManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 26:
                ((RNSVGMaskManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 27:
                ((RNSVGMaskManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 28:
                ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGMaskManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 30:
                ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                ((RNSVGMaskManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case ' ':
                if (obj instanceof String) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMaskManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMaskManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMaskManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMaskManagerInterface) t, "1");
                    break;
                }
            case '!':
                ((RNSVGMaskManagerInterface) this.mViewManager).setMaskContentUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
