package com.henninghall.date_picker.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;

/* loaded from: classes2.dex */
public class FadingOverlay {
    private final GradientDrawable gradientBottom;
    private final GradientDrawable gradientTop;
    private final State state;

    FadingOverlay(State state, View view) {
        this.state = state;
        ImageView imageView = (ImageView) view.findViewById(R.id.overlay_top);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.overlay_bottom);
        this.gradientTop = (GradientDrawable) imageView.getDrawable();
        this.gradientBottom = (GradientDrawable) imageView2.getDrawable();
    }

    void updateColor() {
        String fadeToColor = this.state.getFadeToColor();
        int i = validColor(fadeToColor) ? 255 : 0;
        this.gradientTop.setAlpha(i);
        this.gradientBottom.setAlpha(i);
        if (validColor(fadeToColor)) {
            int parseColor = Color.parseColor("#FF" + fadeToColor.substring(1));
            int parseColor2 = Color.parseColor("#00" + fadeToColor.substring(1));
            this.gradientTop.setColors(new int[]{parseColor, parseColor2});
            this.gradientBottom.setColors(new int[]{parseColor, parseColor2});
        }
    }

    private boolean validColor(String str) {
        return str != null && str.length() == 7;
    }
}
