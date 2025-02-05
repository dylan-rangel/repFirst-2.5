package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGMarkerManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGMarkerManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGMarkerManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -1008621499:
                if (str.equals("orient")) {
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
            case 3143043:
                if (str.equals("fill")) {
                    c = 14;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
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
            case 3351622:
                if (str.equals("minX")) {
                    c = 17;
                    break;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
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
            case 3496485:
                if (str.equals("refX")) {
                    c = 20;
                    break;
                }
                break;
            case 3496486:
                if (str.equals("refY")) {
                    c = 21;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 22;
                    break;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    c = 23;
                    break;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 24;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 25;
                    break;
                }
                break;
            case 218785621:
                if (str.equals("markerUnits")) {
                    c = 26;
                    break;
                }
                break;
            case 220478892:
                if (str.equals("markerWidth")) {
                    c = 27;
                    break;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    c = 28;
                    break;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    c = 29;
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 30;
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = 31;
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = ' ';
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = '!';
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = '#';
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = '$';
                    break;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    c = '%';
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case 2106883585:
                if (str.equals("markerHeight")) {
                    c = '\'';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setOrient(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 5:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case '\b':
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            case '\t':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\n':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case '\f':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\r':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 18:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 20:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            case 21:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            case 22:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 23:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 24:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 25:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerUnits(t, obj != null ? (String) obj : null);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            case 28:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 29:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            case 30:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGMarkerManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 31:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case ' ':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '!':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\"':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case '#':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '$':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '%':
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '&':
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth((RNSVGMarkerManagerInterface) t, "1");
                    break;
                }
            case '\'':
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight((RNSVGMarkerManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight((RNSVGMarkerManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight((RNSVGMarkerManagerInterface) t, (Double) null);
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
