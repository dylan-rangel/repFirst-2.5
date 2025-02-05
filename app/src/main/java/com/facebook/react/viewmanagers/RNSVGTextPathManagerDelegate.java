package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface;
import io.sentry.protocol.ViewHierarchyNode;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class RNSVGTextPathManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGTextPathManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGTextPathManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2012158909:
                if (str.equals("spacing")) {
                    c = 0;
                    break;
                }
                break;
            case -1993948267:
                if (str.equals("startOffset")) {
                    c = 1;
                    break;
                }
                break;
            case -1603134955:
                if (str.equals("lengthAdjust")) {
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
            case -1171891896:
                if (str.equals("alignmentBaseline")) {
                    c = 4;
                    break;
                }
                break;
            case -1139902161:
                if (str.equals("verticalAlign")) {
                    c = 5;
                    break;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 6;
                    break;
                }
                break;
            case -1077554975:
                if (str.equals("method")) {
                    c = 7;
                    break;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    c = '\b';
                    break;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = '\t';
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = '\n';
                    break;
                }
                break;
            case -925180581:
                if (str.equals("rotate")) {
                    c = 11;
                    break;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    c = '\f';
                    break;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    c = 14;
                    break;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    c = 15;
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = 16;
                    break;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    c = 17;
                    break;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    c = 18;
                    break;
                }
                break;
            case 120:
                if (str.equals(ViewHierarchyNode.JsonKeys.X)) {
                    c = 19;
                    break;
                }
                break;
            case 121:
                if (str.equals(ViewHierarchyNode.JsonKeys.Y)) {
                    c = 20;
                    break;
                }
                break;
            case 3220:
                if (str.equals("dx")) {
                    c = 21;
                    break;
                }
                break;
            case 3221:
                if (str.equals("dy")) {
                    c = 22;
                    break;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = 23;
                    break;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    c = 24;
                    break;
                }
                break;
            case 3211051:
                if (str.equals("href")) {
                    c = 25;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 26;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 27;
                    break;
                }
                break;
            case 3530071:
                if (str.equals("side")) {
                    c = 28;
                    break;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    c = 29;
                    break;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    c = 30;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 31;
                    break;
                }
                break;
            case 275888445:
                if (str.equals("baselineShift")) {
                    c = ' ';
                    break;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    c = '!';
                    break;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    c = Typography.quote;
                    break;
                }
                break;
            case 778043962:
                if (str.equals("inlineSize")) {
                    c = '#';
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = '$';
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = '%';
                    break;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case 1054434908:
                if (str.equals("midLine")) {
                    c = '\'';
                    break;
                }
                break;
            case 1637488243:
                if (str.equals("textLength")) {
                    c = '(';
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = ')';
                    break;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    c = '*';
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = '+';
                    break;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    c = ',';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setSpacing(t, obj != null ? (String) obj : null);
                break;
            case 1:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case 2:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setLengthAdjust(t, obj != null ? (String) obj : null);
                break;
            case 3:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 4:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setAlignmentBaseline(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setVerticalAlign(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 7:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMethod(t, obj != null ? (String) obj : null);
                break;
            case '\b':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case '\t':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case '\n':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setRotate(t, (ReadableArray) obj);
                break;
            case '\f':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case '\r':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case 14:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 15:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 16:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 18:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setX(t, (ReadableArray) obj);
                break;
            case 20:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setY(t, (ReadableArray) obj);
                break;
            case 21:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDx(t, (ReadableArray) obj);
                break;
            case 22:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDy(t, (ReadableArray) obj);
                break;
            case 23:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 24:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 25:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setHref(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 27:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setSide(t, obj != null ? (String) obj : null);
                break;
            case 29:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 30:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case ' ':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case '!':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case '\"':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDasharray((RNSVGTextPathManagerInterface) t, (ReadableArray) obj);
                    break;
                }
                break;
            case '#':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case '$':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case '%':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '&':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\'':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMidLine(t, obj != null ? (String) obj : null);
                break;
            case '(':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength((RNSVGTextPathManagerInterface) t, (Double) null);
                    break;
                }
            case ')':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case '*':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '+':
                ((RNSVGTextPathManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case ',':
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTextPathManagerInterface) t, (String) obj);
                    break;
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTextPathManagerInterface) t, (Double) obj);
                    break;
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth((RNSVGTextPathManagerInterface) t, "1");
                    break;
                }
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
