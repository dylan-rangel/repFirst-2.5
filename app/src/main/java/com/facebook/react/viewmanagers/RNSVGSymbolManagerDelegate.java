package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGSymbolManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGSymbolManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGSymbolManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = 11;
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = '\f';
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    c = 14;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 15;
                    break;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    c = 16;
                    break;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    c = 17;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 18;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 19;
                    break;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
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
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 22;
                    break;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
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
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    c = ' ';
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = '!';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight((RNSVGSymbolManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight((RNSVGSymbolManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight((RNSVGSymbolManagerInterface) t, (Double) null);
                    break;
                }
            case '\b':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\t':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\n':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\f':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\r':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 14:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 17:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 18:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 20:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 21:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 22:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 24:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize((RNSVGSymbolManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize((RNSVGSymbolManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize((RNSVGSymbolManagerInterface) t, (Double) null);
                    break;
                }
            case 25:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGSymbolManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGSymbolManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 26:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 27:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 28:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 30:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case ' ':
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '!':
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth((RNSVGSymbolManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth((RNSVGSymbolManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth((RNSVGSymbolManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
