package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGDefsManagerInterface;

/* loaded from: classes.dex */
public class RNSVGDefsManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGDefsManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGDefsManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -933864895:
                if (str.equals("markerEnd")) {
                    c = 2;
                    break;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    c = 3;
                    break;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    c = 4;
                    break;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    c = 5;
                    break;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = 6;
                    break;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    c = 7;
                    break;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    c = '\b';
                    break;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    c = '\t';
                    break;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    c = '\n';
                    break;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mViewManager.setOpacity(t, obj == null ? 1.0f : ((Double) obj).floatValue());
                break;
            case 1:
                ((RNSVGDefsManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 2:
                ((RNSVGDefsManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 3:
                ((RNSVGDefsManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGDefsManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGDefsManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGDefsManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGDefsManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case '\b':
                ((RNSVGDefsManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case '\t':
                ((RNSVGDefsManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case '\n':
                ((RNSVGDefsManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGDefsManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
