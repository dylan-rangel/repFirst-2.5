package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

/* loaded from: classes2.dex */
public class TimezoneOffsetInMinutesProp extends Prop<String> {
    public static final String name = "timezoneOffsetInMinutes";

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.henninghall.date_picker.props.Prop
    public String toValue(Dynamic dynamic) {
        if (dynamic.isNull()) {
            return null;
        }
        return dynamic.asString();
    }
}
