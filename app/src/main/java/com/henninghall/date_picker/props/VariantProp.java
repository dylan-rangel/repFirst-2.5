package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Variant;

/* loaded from: classes2.dex */
public class VariantProp extends Prop<Variant> {
    public static final String name = "androidVariant";

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.henninghall.date_picker.props.Prop
    public Variant toValue(Dynamic dynamic) {
        return Variant.valueOf(dynamic.asString());
    }
}
