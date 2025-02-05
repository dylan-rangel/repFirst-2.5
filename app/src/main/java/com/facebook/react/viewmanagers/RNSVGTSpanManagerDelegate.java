package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGTSpanManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGTSpanManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGTSpanManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1603134955:
                if (str.equals("lengthAdjust")) {
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
            case -1171891896:
                if (str.equals("alignmentBaseline")) {
                    c = 2;
                    break;
                }
                break;
            case -1139902161:
                if (str.equals("verticalAlign")) {
                    c = 3;
                    break;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 4;
                    break;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    c = 5;
                    break;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = 6;
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = 7;
                    break;
                }
                break;
            case -925180581:
                if (str.equals("rotate")) {
                    c = '\b';
                    break;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    c = '\t';
                    break;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    c = '\n';
                    break;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    c = 11;
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = '\f';
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
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
            case 3220:
                if (str.equals("dx")) {
                    c = 18;
                    break;
                }
                break;
            case 3221:
                if (str.equals("dy")) {
                    c = 19;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = 20;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    c = 21;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
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
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 25;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 26;
                    break;
                }
                break;
            case 275888445:
                if (str.equals("baselineShift")) {
                    c = 27;
                    break;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    c = 28;
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = 29;
                    break;
                }
                break;
            case 778043962:
                if (str.equals("inlineSize")) {
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
            case 951530617:
                if (str.equals("content")) {
                    c = '!';
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 1637488243:
                if (str.equals("textLength")) {
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
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = '\'';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setLengthAdjust(t, obj != null ? (String) obj : null);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setAlignmentBaseline(t, obj != null ? (String) obj : null);
                break;
            case 3:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setVerticalAlign(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 5:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 6:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case '\b':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setRotate(t, (ReadableArray) obj);
                break;
            case '\t':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case '\n':
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontWeight((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontWeight((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontWeight((RNSVGTSpanManagerInterface) t, (Double) null);
                    break;
                }
            case 11:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case '\f':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case '\r':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 14:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 15:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 16:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setX(t, (ReadableArray) obj);
                break;
            case 17:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setY(t, (ReadableArray) obj);
                break;
            case 18:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setDx(t, (ReadableArray) obj);
                break;
            case 19:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setDy(t, (ReadableArray) obj);
                break;
            case 20:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 21:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 22:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 24:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 25:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setBaselineShift((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setBaselineShift((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setBaselineShift((RNSVGTSpanManagerInterface) t, (Double) null);
                    break;
                }
            case 28:
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontSize((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontSize((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setFontSize((RNSVGTSpanManagerInterface) t, (Double) null);
                    break;
                }
            case 29:
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGTSpanManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setInlineSize((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setInlineSize((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setInlineSize((RNSVGTSpanManagerInterface) t, (Double) null);
                    break;
                }
            case 31:
                ((RNSVGTSpanManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case ' ':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '!':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setContent(t, obj != null ? (String) obj : null);
                break;
            case '\"':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '#':
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setTextLength((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setTextLength((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setTextLength((RNSVGTSpanManagerInterface) t, (Double) null);
                    break;
                }
            case '$':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case '%':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '&':
                ((RNSVGTSpanManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '\'':
                if (obj instanceof String) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTSpanManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTSpanManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTSpanManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTSpanManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
