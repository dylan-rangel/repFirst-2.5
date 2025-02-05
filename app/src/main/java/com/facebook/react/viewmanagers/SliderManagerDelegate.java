package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.SliderManagerInterface;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public class SliderManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & SliderManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public SliderManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1900655011:
                if (str.equals("maximumTrackTintColor")) {
                    c = 0;
                    break;
                }
                break;
            case -1736983259:
                if (str.equals("thumbImage")) {
                    c = 1;
                    break;
                }
                break;
            case -1609594047:
                if (str.equals(ViewProps.ENABLED)) {
                    c = 2;
                    break;
                }
                break;
            case -1021497397:
                if (str.equals("minimumTrackTintColor")) {
                    c = 3;
                    break;
                }
                break;
            case -981448432:
                if (str.equals("maximumTrackImage")) {
                    c = 4;
                    break;
                }
                break;
            case -877170387:
                if (str.equals(ViewProps.TEST_ID)) {
                    c = 5;
                    break;
                }
                break;
            case 3540684:
                if (str.equals("step")) {
                    c = 6;
                    break;
                }
                break;
            case 111972721:
                if (str.equals("value")) {
                    c = 7;
                    break;
                }
                break;
            case 270940796:
                if (str.equals("disabled")) {
                    c = '\b';
                    break;
                }
                break;
            case 718061361:
                if (str.equals("maximumValue")) {
                    c = '\t';
                    break;
                }
                break;
            case 1139400400:
                if (str.equals("trackImage")) {
                    c = '\n';
                    break;
                }
                break;
            case 1192487427:
                if (str.equals("minimumValue")) {
                    c = 11;
                    break;
                }
                break;
            case 1333596542:
                if (str.equals("minimumTrackImage")) {
                    c = '\f';
                    break;
                }
                break;
            case 1912319986:
                if (str.equals("thumbTintColor")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((SliderManagerInterface) this.mViewManager).setMaximumTrackTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 1:
                ((SliderManagerInterface) this.mViewManager).setThumbImage(t, (ReadableMap) obj);
                break;
            case 2:
                ((SliderManagerInterface) this.mViewManager).setEnabled(t, obj != null ? ((Boolean) obj).booleanValue() : true);
                break;
            case 3:
                ((SliderManagerInterface) this.mViewManager).setMinimumTrackTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 4:
                ((SliderManagerInterface) this.mViewManager).setMaximumTrackImage(t, (ReadableMap) obj);
                break;
            case 5:
                ((SliderManagerInterface) this.mViewManager).setTestID(t, obj == null ? "" : (String) obj);
                break;
            case 6:
                ((SliderManagerInterface) this.mViewManager).setStep(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 7:
                ((SliderManagerInterface) this.mViewManager).setValue(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case '\b':
                ((SliderManagerInterface) this.mViewManager).setDisabled(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case '\t':
                ((SliderManagerInterface) this.mViewManager).setMaximumValue(t, obj == null ? 1.0d : ((Double) obj).doubleValue());
                break;
            case '\n':
                ((SliderManagerInterface) this.mViewManager).setTrackImage(t, (ReadableMap) obj);
                break;
            case 11:
                ((SliderManagerInterface) this.mViewManager).setMinimumValue(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case '\f':
                ((SliderManagerInterface) this.mViewManager).setMinimumTrackImage(t, (ReadableMap) obj);
                break;
            case '\r':
                ((SliderManagerInterface) this.mViewManager).setThumbTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
