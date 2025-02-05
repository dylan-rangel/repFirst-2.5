package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGCircleManagerInterface;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGCircleManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGCircleManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGCircleManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 1;
                    break;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    c = 2;
                    break;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = 3;
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = 4;
                    break;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    c = 5;
                    break;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    c = 6;
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = 7;
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = '\b';
                    break;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = '\t';
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = '\n';
                    break;
                }
                break;
            case 114:
                if (str.equals("r")) {
                    c = 11;
                    break;
                }
                break;
            case 3189:
                if (str.equals("cx")) {
                    c = '\f';
                    break;
                }
                break;
            case 3190:
                if (str.equals("cy")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
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
            case 3373707:
                if (str.equals("name")) {
                    c = 16;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 17;
                    break;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 18;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 19;
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 20;
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = 21;
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = 22;
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = 23;
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = 24;
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = 25;
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = 26;
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = 27;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                ((RNSVGCircleManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 2:
                ((RNSVGCircleManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGCircleManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGCircleManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGCircleManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 6:
                ((RNSVGCircleManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 7:
                ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\b':
                ((RNSVGCircleManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case '\t':
                ((RNSVGCircleManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\n':
                ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 11:
                if (obj instanceof String) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setR((RNSVGCircleManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setR((RNSVGCircleManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setR((RNSVGCircleManagerInterface) t, (Double) null);
                    break;
                }
            case '\f':
                if (obj instanceof String) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCx((RNSVGCircleManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCx((RNSVGCircleManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCx((RNSVGCircleManagerInterface) t, (Double) null);
                    break;
                }
            case '\r':
                if (obj instanceof String) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCy((RNSVGCircleManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCy((RNSVGCircleManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setCy((RNSVGCircleManagerInterface) t, (Double) null);
                    break;
                }
            case 14:
                ((RNSVGCircleManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGCircleManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGCircleManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 18:
                ((RNSVGCircleManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 19:
                ((RNSVGCircleManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 20:
                if (obj instanceof String) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGCircleManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGCircleManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 21:
                ((RNSVGCircleManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 22:
                ((RNSVGCircleManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 23:
                ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 24:
                ((RNSVGCircleManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGCircleManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeWidth((RNSVGCircleManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeWidth((RNSVGCircleManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGCircleManagerInterface) this.mViewManager).setStrokeWidth((RNSVGCircleManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
