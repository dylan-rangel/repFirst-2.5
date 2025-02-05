package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Is24HourSource;

/* loaded from: classes2.dex */
public class Is24hourSourceProp extends Prop<Is24HourSource> {
    public static final String name = "is24hourSource";

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.henninghall.date_picker.props.Prop
    public Is24HourSource toValue(Dynamic dynamic) {
        return Is24HourSource.valueOf(dynamic.asString());
    }
}
