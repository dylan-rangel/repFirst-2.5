package com.facebook.react.views.switchview;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.TouchesHelper;

/* loaded from: classes.dex */
class ReactSwitchEvent extends Event<ReactSwitchEvent> {
    public static final String EVENT_NAME = "topChange";
    private final boolean mIsChecked;

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topChange";
    }

    @Deprecated
    public ReactSwitchEvent(int i, boolean z) {
        this(-1, i, z);
    }

    public ReactSwitchEvent(int i, int i2, boolean z) {
        super(i, i2);
        this.mIsChecked = z;
    }

    public boolean getIsChecked() {
        return this.mIsChecked;
    }

    @Override // com.facebook.react.uimanager.events.Event
    protected WritableMap getEventData() {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt(TouchesHelper.TARGET_KEY, getViewTag());
        createMap.putBoolean("value", getIsChecked());
        return createMap;
    }
}
