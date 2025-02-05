package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.ui.Accessibility;

/* loaded from: classes2.dex */
public class IosClone extends NumberPickerView implements Picker {
    private Picker.OnValueChangeListenerInScrolling mOnValueChangeListenerInScrolling;

    @Override // com.henninghall.date_picker.pickers.Picker
    public View getView() {
        return this;
    }

    public IosClone(Context context) {
        super(context);
        init();
    }

    public IosClone(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public IosClone(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        initAccessibility();
        initSetOnValueChangeListenerInScrolling();
    }

    private void initAccessibility() {
        Accessibility.startAccessibilityDelegate(this);
    }

    private void initSetOnValueChangeListenerInScrolling() {
        super.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() { // from class: com.henninghall.date_picker.pickers.IosClone.1
            @Override // cn.carbswang.android.numberpickerview.library.NumberPickerView.OnValueChangeListenerInScrolling
            public void onValueChangeInScrolling(NumberPickerView numberPickerView, int i, int i2) {
                Accessibility.sendValueChangedEvent(this, i2);
                if (IosClone.this.mOnValueChangeListenerInScrolling != null) {
                    IosClone.this.mOnValueChangeListenerInScrolling.onValueChangeInScrolling(this, i, i2);
                }
            }
        });
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setTextColor(String str) {
        int parseColor = Color.parseColor(str);
        setNormalTextColor(Color.parseColor("#70" + str.substring(1)));
        setSelectedTextColor(parseColor);
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setOnValueChangeListenerInScrolling(Picker.OnValueChangeListenerInScrolling onValueChangeListenerInScrolling) {
        this.mOnValueChangeListenerInScrolling = onValueChangeListenerInScrolling;
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setOnValueChangedListener(final Picker.OnValueChangeListener onValueChangeListener) {
        super.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() { // from class: com.henninghall.date_picker.pickers.IosClone.2
            @Override // cn.carbswang.android.numberpickerview.library.NumberPickerView.OnValueChangeListener
            public void onValueChange(NumberPickerView numberPickerView, int i, int i2) {
                onValueChangeListener.onValueChange();
            }
        });
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public boolean isSpinning() {
        return super.isScrolling();
    }

    @Override // cn.carbswang.android.numberpickerview.library.NumberPickerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (Accessibility.shouldAllowScroll(this)) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        Accessibility.setRoleToSlider(this, accessibilityNodeInfo);
    }
}
