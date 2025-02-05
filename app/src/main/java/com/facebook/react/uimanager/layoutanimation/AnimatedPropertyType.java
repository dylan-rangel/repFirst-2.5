package com.facebook.react.uimanager.layoutanimation;

/* loaded from: classes.dex */
enum AnimatedPropertyType {
    OPACITY,
    SCALE_X,
    SCALE_Y,
    SCALE_XY;

    public static AnimatedPropertyType fromString(String str) {
        str.hashCode();
        switch (str) {
            case "opacity":
                return OPACITY;
            case "scaleX":
                return SCALE_X;
            case "scaleY":
                return SCALE_Y;
            case "scaleXY":
                return SCALE_XY;
            default:
                throw new IllegalArgumentException("Unsupported animated property: " + str);
        }
    }
}
