package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.pickers.Picker;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class AndroidNative extends NumberPicker implements Picker {
    private final Handler handler;
    private boolean isAnimating;
    private Picker.OnValueChangeListenerInScrolling listenerInScrolling;
    private Picker.OnValueChangeListener onValueChangedListener;
    private int state;

    @Override // com.henninghall.date_picker.pickers.Picker
    public View getView() {
        return this;
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setDividerHeight(int i) {
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setItemPaddingHorizontal(int i) {
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setShownCount(int i) {
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setTextAlign(Paint.Align align) {
    }

    public AndroidNative(Context context) {
        super(context);
        this.state = 0;
        this.handler = new Handler();
    }

    public AndroidNative(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.state = 0;
        this.handler = new Handler();
    }

    public AndroidNative(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.state = 0;
        this.handler = new Handler();
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void smoothScrollToValue(int i, boolean z) {
        smoothScrollToValue(i);
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setTextColor(String str) {
        int parseColor = Color.parseColor(str);
        if (Build.VERSION.SDK_INT >= 29) {
            super.setTextColor(parseColor);
            return;
        }
        try {
            Field declaredField = getClass().getSuperclass().getDeclaredField("mSelectorWheelPaint");
            declaredField.setAccessible(true);
            ((Paint) declaredField.get(this)).setColor(parseColor);
        } catch (IllegalAccessException e) {
            Log.w("setSelectedTextColor", e);
        } catch (IllegalArgumentException e2) {
            Log.w("setSelectedTextColor", e2);
        } catch (NoSuchFieldException e3) {
            Log.w("setSelectedTextColor", e3);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof EditText) {
                ((EditText) childAt).setTextColor(parseColor);
            }
        }
        invalidate();
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public boolean isSpinning() {
        return this.state == 2 || this.isAnimating;
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void smoothScrollToValue(int i) {
        int value = getValue();
        if (i == value) {
            return;
        }
        int shortestScrollOption = Utils.getShortestScrollOption(value, i, getMaxValue(), getWrapSelectorWheel());
        int abs = Math.abs(shortestScrollOption);
        this.isAnimating = true;
        this.handler.postDelayed(new Runnable() { // from class: com.henninghall.date_picker.pickers.AndroidNative.1
            @Override // java.lang.Runnable
            public void run() {
                AndroidNative.this.isAnimating = false;
            }
        }, abs * 100);
        int i2 = 0;
        while (i2 < abs) {
            changeValueByOne(shortestScrollOption > 0, i2 * 100, i2 == abs + (-1));
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
    
        if (r9 == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0054, code lost:
    
        setValue((r8 + r1) % getMaxValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0053, code lost:
    
        r1 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:
    
        if (r9 == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0046, code lost:
    
        if (r9 == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0030, code lost:
    
        if (r9 == false) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void changeValueByOne(android.widget.NumberPicker r8, boolean r9) {
        /*
            r7 = this;
            java.lang.String r0 = "changeValueByOne"
            r1 = -1
            r2 = 1
            java.lang.Class r3 = r7.getClass()     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.Class r3 = r3.getSuperclass()     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            r6 = 0
            r4[r6] = r5     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.reflect.Method r3 = r3.getDeclaredMethod(r0, r4)     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            r3.setAccessible(r2)     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r9)     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            r4[r6] = r5     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            r3.invoke(r8, r4)     // Catch: java.lang.Throwable -> L26 java.lang.reflect.InvocationTargetException -> L28 java.lang.IllegalAccessException -> L33 java.lang.IllegalArgumentException -> L3e java.lang.NoSuchMethodException -> L49
            goto L5d
        L26:
            r8 = move-exception
            goto L5e
        L28:
            r8 = move-exception
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L26
            int r8 = r7.getValue()
            if (r9 == 0) goto L54
            goto L53
        L33:
            r8 = move-exception
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L26
            int r8 = r7.getValue()
            if (r9 == 0) goto L54
            goto L53
        L3e:
            r8 = move-exception
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L26
            int r8 = r7.getValue()
            if (r9 == 0) goto L54
            goto L53
        L49:
            r8 = move-exception
            android.util.Log.w(r0, r8)     // Catch: java.lang.Throwable -> L26
            int r8 = r7.getValue()
            if (r9 == 0) goto L54
        L53:
            r1 = 1
        L54:
            int r8 = r8 + r1
            int r9 = r7.getMaxValue()
            int r8 = r8 % r9
            r7.setValue(r8)
        L5d:
            return
        L5e:
            int r0 = r7.getValue()
            if (r9 == 0) goto L65
            r1 = 1
        L65:
            int r0 = r0 + r1
            int r9 = r7.getMaxValue()
            int r0 = r0 % r9
            r7.setValue(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.henninghall.date_picker.pickers.AndroidNative.changeValueByOne(android.widget.NumberPicker, boolean):void");
    }

    private void changeValueByOne(final boolean z, int i, final boolean z2) {
        this.handler.postDelayed(new Runnable() { // from class: com.henninghall.date_picker.pickers.AndroidNative.2
            @Override // java.lang.Runnable
            public void run() {
                AndroidNative.this.changeValueByOne(this, z);
                if (z2) {
                    AndroidNative.this.sendEventIn500ms();
                }
            }
        }, i);
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setOnValueChangeListenerInScrolling(Picker.OnValueChangeListenerInScrolling onValueChangeListenerInScrolling) {
        this.listenerInScrolling = onValueChangeListenerInScrolling;
    }

    @Override // com.henninghall.date_picker.pickers.Picker
    public void setOnValueChangedListener(Picker.OnValueChangeListener onValueChangeListener) {
        this.onValueChangedListener = onValueChangeListener;
        super.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.henninghall.date_picker.pickers.AndroidNative.3
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                if (AndroidNative.this.listenerInScrolling != null) {
                    AndroidNative.this.listenerInScrolling.onValueChangeInScrolling(this, i, i2);
                }
                if (AndroidNative.this.state == 0) {
                    AndroidNative.this.sendEventIn500ms();
                }
            }
        });
        super.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: com.henninghall.date_picker.pickers.AndroidNative.4
            @Override // android.widget.NumberPicker.OnScrollListener
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                AndroidNative.this.sendEventIfStopped(i);
                AndroidNative.this.state = i;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEventIfStopped(int i) {
        if (this.state != 0 && i == 0) {
            sendEventIn500ms();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEventIn500ms() {
        this.handler.postDelayed(new Runnable() { // from class: com.henninghall.date_picker.pickers.AndroidNative.5
            @Override // java.lang.Runnable
            public void run() {
                AndroidNative.this.onValueChangedListener.onValueChange();
            }
        }, 500L);
    }

    @Override // android.widget.NumberPicker, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
    }
}
