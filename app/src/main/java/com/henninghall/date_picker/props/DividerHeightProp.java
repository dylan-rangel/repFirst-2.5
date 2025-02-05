package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

/* loaded from: classes2.dex */
public class DividerHeightProp extends Prop<Integer> {
    public static final String name = "dividerHeight";

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.henninghall.date_picker.props.Prop
    public Integer toValue(Dynamic dynamic) {
        return Integer.valueOf(dynamic.asInt());
    }
}
