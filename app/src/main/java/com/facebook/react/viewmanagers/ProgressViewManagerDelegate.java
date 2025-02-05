package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.ProgressViewManagerInterface;

/* loaded from: classes.dex */
public class ProgressViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & ProgressViewManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public ProgressViewManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        switch (str) {
            case "progressViewStyle":
                ((ProgressViewManagerInterface) this.mViewManager).setProgressViewStyle(t, (String) obj);
                break;
            case "progress":
                ((ProgressViewManagerInterface) this.mViewManager).setProgress(t, obj == null ? 0.0f : ((Double) obj).floatValue());
                break;
            case "trackTintColor":
                ((ProgressViewManagerInterface) this.mViewManager).setTrackTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case "progressImage":
                ((ProgressViewManagerInterface) this.mViewManager).setProgressImage(t, (ReadableMap) obj);
                break;
            case "progressTintColor":
                ((ProgressViewManagerInterface) this.mViewManager).setProgressTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case "trackImage":
                ((ProgressViewManagerInterface) this.mViewManager).setTrackImage(t, (ReadableMap) obj);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
