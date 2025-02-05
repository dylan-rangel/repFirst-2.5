package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.RNCSafeAreaViewManagerInterface;
import com.henninghall.date_picker.props.ModeProp;

/* loaded from: classes.dex */
public class RNCSafeAreaViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNCSafeAreaViewManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNCSafeAreaViewManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        if (str.equals(ModeProp.name)) {
            ((RNCSafeAreaViewManagerInterface) this.mViewManager).setMode(t, (String) obj);
        } else if (str.equals("edges")) {
            ((RNCSafeAreaViewManagerInterface) this.mViewManager).setEdges(t, (ReadableMap) obj);
        } else {
            super.setProperty(t, str, obj);
        }
    }
}
