package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.RNGestureHandlerButtonManagerInterface;

/* loaded from: classes.dex */
public class RNGestureHandlerButtonManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNGestureHandlerButtonManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNGestureHandlerButtonManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        switch (str) {
            case "rippleRadius":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setRippleRadius(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case "enabled":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setEnabled(t, obj != null ? ((Boolean) obj).booleanValue() : true);
                break;
            case "rippleColor":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setRippleColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case "touchSoundDisabled":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setTouchSoundDisabled(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case "exclusive":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setExclusive(t, obj != null ? ((Boolean) obj).booleanValue() : true);
                break;
            case "borderless":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setBorderless(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case "foreground":
                ((RNGestureHandlerButtonManagerInterface) this.mViewManager).setForeground(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
