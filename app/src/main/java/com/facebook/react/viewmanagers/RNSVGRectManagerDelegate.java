package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGRectManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGRectManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGRectManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGRectManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -729118945:
                if (str.equals("fillRule")) {
                    c = 7;
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = '\b';
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = '\t';
                    break;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = '\n';
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = 11;
                    break;
                }
                break;
            case 120:
                if (str.equals(ViewHierarchyNode.JsonKeys.X)) {
                    c = '\f';
                    break;
                }
                break;
            case 121:
                if (str.equals(ViewHierarchyNode.JsonKeys.Y)) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 3654:
                if (str.equals("rx")) {
                    c = 14;
                    break;
                }
                break;
            case 3655:
                if (str.equals("ry")) {
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
            case 3344108:
                if (str.equals("mask")) {
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
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 20;
                    break;
                }
                break;
            case 113126854:
                if (str.equals("width")) {
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
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 23;
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = 24;
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = 25;
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = 26;
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = 27;
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = 28;
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = 29;
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = 30;
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
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case 2:
                ((RNSVGRectManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGRectManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGRectManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                ((RNSVGRectManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\b':
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\t':
                ((RNSVGRectManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case '\n':
                ((RNSVGRectManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\f':
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case '\r':
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case 14:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case 15:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case 16:
                ((RNSVGRectManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 17:
                ((RNSVGRectManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGRectManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 20:
                ((RNSVGRectManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth((RNSVGRectManagerInterface) t, (Double) null);
                    break;
                }
            case 22:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGRectManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 24:
                ((RNSVGRectManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGRectManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGRectManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGRectManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth((RNSVGRectManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth((RNSVGRectManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth((RNSVGRectManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
