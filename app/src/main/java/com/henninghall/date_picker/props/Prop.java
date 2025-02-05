package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

/* loaded from: classes2.dex */
public abstract class Prop<T> {
    private T value;

    abstract T toValue(Dynamic dynamic);

    public Prop() {
    }

    public Prop(T t) {
        this.value = t;
    }

    public void setValue(Dynamic dynamic) {
        this.value = toValue(dynamic);
    }

    public T getValue() {
        return this.value;
    }
}
