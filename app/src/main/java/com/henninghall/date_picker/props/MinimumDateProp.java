package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

/* loaded from: classes2.dex */
public class MinimumDateProp extends Prop<String> {
    public static final String name = "minimumDate";

    @Override // com.henninghall.date_picker.props.Prop
    public String toValue(Dynamic dynamic) {
        return dynamic.asString();
    }
}
