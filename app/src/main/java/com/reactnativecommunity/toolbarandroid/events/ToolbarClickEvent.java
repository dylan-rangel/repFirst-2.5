package com.reactnativecommunity.toolbarandroid.events;

import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* loaded from: classes2.dex */
public class ToolbarClickEvent extends Event<ToolbarClickEvent> {
    private static final String EVENT_NAME = "topSelect";
    private final int position;

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return false;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topSelect";
    }

    public ToolbarClickEvent(int i, int i2) {
        super(i);
        this.position = i2;
    }

    public int getPosition() {
        return this.position;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putInt(ViewProps.POSITION, getPosition());
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), writableNativeMap);
    }
}
