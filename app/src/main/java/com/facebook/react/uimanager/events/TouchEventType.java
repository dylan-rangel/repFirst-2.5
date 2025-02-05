package com.facebook.react.uimanager.events;

/* loaded from: classes.dex */
public enum TouchEventType {
    START("topTouchStart"),
    END("topTouchEnd"),
    MOVE("topTouchMove"),
    CANCEL("topTouchCancel");

    private final String mJsName;

    TouchEventType(String str) {
        this.mJsName = str;
    }

    public String getJsName() {
        return this.mJsName;
    }

    public static String getJSEventName(TouchEventType touchEventType) {
        return touchEventType.getJsName();
    }
}
