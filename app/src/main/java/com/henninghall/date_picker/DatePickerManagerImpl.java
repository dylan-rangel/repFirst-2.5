package com.henninghall.date_picker;

import android.widget.LinearLayout;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public class DatePickerManagerImpl {
    static final String NAME = "RNDatePicker";
    static final int SCROLL = 1;

    public String getName() {
        return "RNDatePicker";
    }

    public static PickerView createViewInstance(ThemedReactContext themedReactContext) {
        return new PickerView(new LinearLayout.LayoutParams(-1, -1));
    }

    public static void setProps(PickerView pickerView, int i, Dynamic dynamic, Class<? extends DatePickerManager> cls) {
        updateProp("setProps", pickerView, i, dynamic, cls);
    }

    public static void setStyle(PickerView pickerView, int i, Dynamic dynamic, Class<? extends DatePickerManager> cls) {
        updateProp("setStyle", pickerView, i, dynamic, cls);
    }

    public static Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(ViewProps.SCROLL, 1);
    }

    protected static void onAfterUpdateTransaction(PickerView pickerView) {
        try {
            pickerView.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receiveCommand(PickerView pickerView, int i, ReadableArray readableArray) {
        if (i == 1) {
            pickerView.scroll(readableArray.getInt(0), readableArray.getInt(1));
        }
    }

    public static void updateProp(String str, PickerView pickerView, int i, Dynamic dynamic, Class<? extends DatePickerManager> cls) {
        pickerView.updateProp(getMethodAnnotation(str, cls).names()[i], dynamic);
    }

    private static ReactPropGroup getMethodAnnotation(String str, Class<? extends DatePickerManager> cls) {
        Method method = null;
        for (Method method2 : cls.getMethods()) {
            if (method2.getName().equals(str)) {
                method = method2;
            }
        }
        return (ReactPropGroup) method.getAnnotation(ReactPropGroup.class);
    }
}
