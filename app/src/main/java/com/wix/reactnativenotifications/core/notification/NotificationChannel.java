package com.wix.reactnativenotifications.core.notification;

import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationChannel implements INotificationChannel {
    protected final Context mContext;
    protected final NotificationChannelProps mNotificationChannelProps;

    protected NotificationChannel(Context context, Bundle bundle) {
        this.mContext = context;
        this.mNotificationChannelProps = createProps(bundle);
    }

    public static INotificationChannel get(Context context, Bundle bundle) {
        return new NotificationChannel(context, bundle);
    }

    protected NotificationChannelProps createProps(Bundle bundle) {
        return new NotificationChannelProps(bundle);
    }

    @Override // com.wix.reactnativenotifications.core.notification.INotificationChannel
    public void setNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(this.mNotificationChannelProps.getChannelId(), this.mNotificationChannelProps.getName(), this.mNotificationChannelProps.getImportance());
        if (this.mNotificationChannelProps.hasDescription()) {
            notificationChannel.setDescription(this.mNotificationChannelProps.getDescription());
        }
        if (this.mNotificationChannelProps.hasEnableLights()) {
            notificationChannel.enableLights(this.mNotificationChannelProps.getEnableLights());
        }
        if (this.mNotificationChannelProps.hasEnableVibration()) {
            notificationChannel.enableVibration(this.mNotificationChannelProps.getEnableVibration());
        }
        if (Build.VERSION.SDK_INT >= 28 && this.mNotificationChannelProps.hasGroupId()) {
            String groupId = this.mNotificationChannelProps.getGroupId();
            if (notificationManager.getNotificationChannelGroup(groupId) == null) {
                notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, this.mNotificationChannelProps.getGroupName()));
            }
            notificationChannel.setGroup(groupId);
        }
        if (this.mNotificationChannelProps.hasLightColor()) {
            notificationChannel.setLightColor(Color.parseColor(this.mNotificationChannelProps.getLightColor()));
        }
        if (this.mNotificationChannelProps.hasShowBadge()) {
            notificationChannel.setShowBadge(this.mNotificationChannelProps.getShowBadge());
        }
        if (this.mNotificationChannelProps.hasSoundFile()) {
            notificationChannel.setSound(getSound(this.mNotificationChannelProps.getSoundFile()), null);
        }
        if (this.mNotificationChannelProps.hasVibrationPattern()) {
            notificationChannel.setVibrationPattern(createVibrationPatternFromList(this.mNotificationChannelProps.getVibrationPattern()));
        }
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @Override // com.wix.reactnativenotifications.core.notification.INotificationChannel
    public NotificationChannelProps asProps() {
        return this.mNotificationChannelProps.copy();
    }

    private long[] createVibrationPatternFromList(List list) {
        if (list == null) {
            return null;
        }
        long[] jArr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Number) {
                jArr[i] = ((Number) list.get(i)).longValue();
            }
        }
        return jArr;
    }

    private Uri getSound(String str) {
        if (str == null) {
            return null;
        }
        if (str.contains("://")) {
            return Uri.parse(str);
        }
        if (str.equalsIgnoreCase("default")) {
            return RingtoneManager.getDefaultUri(2);
        }
        int resourceId = getResourceId("raw", str);
        if (resourceId == 0) {
            resourceId = getResourceId("raw", str.substring(0, str.lastIndexOf(46)));
        }
        return Uri.parse("android.resource://" + this.mContext.getPackageName() + "/" + resourceId);
    }

    private int getResourceId(String str, String str2) {
        return this.mContext.getResources().getIdentifier(str2, str, this.mContext.getPackageName());
    }
}
