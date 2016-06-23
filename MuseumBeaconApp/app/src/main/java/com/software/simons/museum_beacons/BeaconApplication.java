package com.software.simons.museum_beacons;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

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
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "Beacon Monitoring Service Ready!");
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString(BASIC_UUID), 60128, 45892)); // The icy one
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                String title = "Entered the field of " + list.size() + " trackers";
                String content = "";
                for(Beacon b : list) {
                    content += b.getMajor() + ":" + b.getMinor() + "\n";
                }
                Log.d(TAG, "Some region was entered");
//                showNotification(title, content);
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
                Log.d(TAG, "Some region was entered");
//                showNotification("Region exited","Yep.");
            }
        });

        Log.d(TAG, "BeaconApplication created succesfully");
    }
}
