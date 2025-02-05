package com.wix.reactnativenotifications.core.notification;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationChannelProps {
    protected Bundle mBundle;

    public NotificationChannelProps(Bundle bundle) {
        this.mBundle = bundle;
    }

    public String getChannelId() {
        return this.mBundle.getString("channelId");
    }

    public boolean hasChannelId() {
        return this.mBundle.containsKey("channelId");
    }

    public String getDescription() {
        return this.mBundle.getString("description");
    }

    public boolean hasDescription() {
        return this.mBundle.containsKey("description");
    }

    public boolean getEnableLights() {
        return this.mBundle.getBoolean("enableLights");
    }

    public boolean hasEnableLights() {
        return this.mBundle.containsKey("enableLights");
    }

    public boolean getEnableVibration() {
        return this.mBundle.getBoolean("enableVibration");
    }

    public boolean hasEnableVibration() {
        return this.mBundle.containsKey("enableVibration");
    }

    public String getGroupId() {
        return this.mBundle.getString("groupId");
    }

    public boolean hasGroupId() {
        return this.mBundle.containsKey("groupId");
    }

    public String getGroupName() {
        String string = this.mBundle.getString("groupName");
        return string == null ? getGroupId() : string;
    }

    public boolean hasGroupName() {
        return this.mBundle.containsKey("groupName");
    }

    public int getImportance() {
        return (int) this.mBundle.getDouble("importance");
    }

    public boolean hasImportance() {
        return this.mBundle.containsKey("importance");
    }

    public String getLightColor() {
        return this.mBundle.getString("lightColor");
    }

    public boolean hasLightColor() {
        return this.mBundle.containsKey("lightColor");
    }

    public String getName() {
        return this.mBundle.getString("name");
    }

    public boolean hasName() {
        return this.mBundle.containsKey("name");
    }

    public boolean getShowBadge() {
        return this.mBundle.getBoolean("showBadge");
    }

    public boolean hasShowBadge() {
        return this.mBundle.containsKey("showBadge");
    }

    public String getSoundFile() {
        return this.mBundle.getString("soundFile");
    }

    public boolean hasSoundFile() {
        return this.mBundle.containsKey("soundFile");
    }

    public List getVibrationPattern() {
        return this.mBundle.getParcelableArrayList("vibrationPattern");
    }

    public boolean hasVibrationPattern() {
        return this.mBundle.containsKey("vibrationPattern");
    }

    public Bundle asBundle() {
        return (Bundle) this.mBundle.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(1024);
        for (String str : this.mBundle.keySet()) {
            sb.append(str);
            sb.append("=");
            sb.append(this.mBundle.get(str));
            sb.append(", ");
        }
        return sb.toString();
    }

    protected NotificationChannelProps copy() {
        return new NotificationChannelProps((Bundle) this.mBundle.clone());
    }
}
