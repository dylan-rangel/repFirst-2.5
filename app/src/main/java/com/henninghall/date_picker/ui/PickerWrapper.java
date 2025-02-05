package com.henninghall.date_picker.ui;

import android.view.View;
import android.widget.LinearLayout;
import com.henninghall.date_picker.R;

/* loaded from: classes2.dex */
class PickerWrapper {
    private final LinearLayout view;

    PickerWrapper(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.pickerWrapper);
        this.view = linearLayout;
        linearLayout.setWillNotDraw(false);
    }

    void addPicker(View view) {
        this.view.addView(view);
    }

    void addPicker(View view, int i) {
        this.view.addView(view, i);
    }

    void removeAll() {
        this.view.removeAllViews();
    }
}
