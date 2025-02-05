package com.henninghall.date_picker.pickers;

import android.graphics.Paint;
import android.view.View;

/* loaded from: classes2.dex */
public interface Picker {

    public interface OnValueChangeListener {
        void onValueChange();
    }

    public interface OnValueChangeListenerInScrolling {
        void onValueChangeInScrolling(Picker picker, int i, int i2);
    }

    String[] getDisplayedValues();

    int getMaxValue();

    int getValue();

    View getView();

    boolean getWrapSelectorWheel();

    boolean isSpinning();

    void setDisplayedValues(String[] strArr);

    void setDividerHeight(int i);

    void setItemPaddingHorizontal(int i);

    void setMaxValue(int i);

    void setMinValue(int i);

    void setOnValueChangeListenerInScrolling(OnValueChangeListenerInScrolling onValueChangeListenerInScrolling);

    void setOnValueChangedListener(OnValueChangeListener onValueChangeListener);

    void setShownCount(int i);

    void setTextAlign(Paint.Align align);

    void setTextColor(String str);

    void setValue(int i);

    void setVisibility(int i);

    void setWrapSelectorWheel(boolean z);

    void smoothScrollToValue(int i);

    void smoothScrollToValue(int i, boolean z);
}
