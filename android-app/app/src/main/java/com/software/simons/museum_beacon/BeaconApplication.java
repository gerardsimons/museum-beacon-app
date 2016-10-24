package com.software.simons.museum_beacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.BeaconManager;

/**
 * Created by gerard on 21/06/16.
 */
public class BeaconApplication extends Application {

    private static final String BASIC_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private static final String TAG = "BeaconApplication";
    private BeaconManager beaconManager;

    /**
     * @param title the title to show in the notification
     * @param message the content of the message in the notification
     */
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public void onCreate() {
        super.onCreate();


        Log.d(TAG, "BeaconApplication created succesfully");
    }
}
