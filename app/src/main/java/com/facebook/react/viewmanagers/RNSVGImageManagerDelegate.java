package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGImageManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGImageManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGImageManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGImageManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case 114148:
                if (str.equals("src")) {
                    c = 14;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = 15;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 16;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 17;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 18;
                    break;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
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
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    c = 30;
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = 31;
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
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight((RNSVGImageManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight((RNSVGImageManagerInterface) t, (Double) null);
                    break;
                }
            case 2:
                ((RNSVGImageManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGImageManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGImageManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                ((RNSVGImageManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\b':
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\t':
                ((RNSVGImageManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case '\n':
                ((RNSVGImageManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case '\f':
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX((RNSVGImageManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX((RNSVGImageManagerInterface) t, (Double) null);
                    break;
                }
            case '\r':
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY((RNSVGImageManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY((RNSVGImageManagerInterface) t, (Double) null);
                    break;
                }
            case 14:
                ((RNSVGImageManagerInterface) this.mViewManager).setSrc(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGImageManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGImageManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGImageManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGImageManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 20:
                ((RNSVGImageManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth((RNSVGImageManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth((RNSVGImageManagerInterface) t, (Double) null);
                    break;
                }
            case 22:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGImageManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 24:
                ((RNSVGImageManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGImageManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGImageManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGImageManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 30:
                ((RNSVGImageManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth((RNSVGImageManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth((RNSVGImageManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth((RNSVGImageManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
