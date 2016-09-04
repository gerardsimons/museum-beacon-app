package com.software.simons.museum_beacons;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MainActivity extends Activity {

    public static final String VIDEO_URL = "video_url";
    public static final String YT_VIDEO_ID = "yt_video_id";
    public static final String PREFEFRENCES_NAME = "prefs";
    private static final String TAG = "MainActivity";

    private static final int PLAY_VIDEO_REQUEST = 1234;
    private static final long BEACON_REVISIT_TIMEOUT = 600; // Time in seconds before a baecon may trigger again

    private BeaconManager beaconManager;
    private DatabaseController databaseController;

    private boolean playingVideo = false;

    private Map<String, Long> visitedBeacons;
    private Queue<String> playQueue;

    private PlayingMode playingMode = PlayingMode.VIDEO_VIEW;
    private boolean debugging = true;

    private enum PlayingMode {
        YOUTUBE,
        VIMEO,
        VIDEO_VIEW,
        VIDEO_WEB_VIEW,
        DISABLED
    }

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First things first, check for BLE
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
//
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }


        databaseController = DatabaseController.getInstance();
        databaseController.mockNativeVideoData(); // For testing purposes, use mocked data

        beaconManager = new BeaconManager(getApplicationContext());
        visitedBeacons = new HashMap<>();
        playQueue = new LinkedList<>();



//        enableRegionMonitoring();
        enableRanging();
    }

    private void checkBeaconConfigSettings() {
        //Update distances according to sharedpreferences

        SharedPreferences sp = getSharedPreferences(PREFEFRENCES_NAME, MODE_PRIVATE)
        List<BeaconConfig> beacons = databaseController.getRegisteredBeacons();
        for(BeaconConfig bc : beacons) {
            //TODO: Get preferences acoording to settings
        }
    }

    public void gotoBeaconConfigActivity() {
        Intent intent = new Intent(this, BeaconConfigActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        checkBeaconConfigSettings();
    }

    private Toast toast;
    public void showAToast (String st){
        try{ toast.getView().isShown();
            toast.setText(st);
        } catch (Exception e) {
            toast = Toast.makeText(this, st, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    private void checkBeaconTrigger(List<Beacon> beaconList) {
        for (Beacon b : beaconList) {
            Utils.Proximity px = Utils.computeProximity(b);
            BeaconConfig bc = databaseController.findRegisteredBeaconWithProximity(b.getProximityUUID(), b.getMajor(), b.getMinor(), px);
            if (bc != null) {
                // Check when last seen
                Log.d(TAG, "Proximity = " + px + "\nBeacon found!" + bc.toString());
                if (visitedBeacons.containsKey(bc.getKey())) {
                    long lastSeen = visitedBeacons.get(bc.getKey());

                    long elapsedSeconds = (long) ((System.nanoTime() - lastSeen) / 10e9);
                    Log.d(TAG, "Last seen " + elapsedSeconds + " seconds ago.");

                    if (elapsedSeconds <= BEACON_REVISIT_TIMEOUT) { //TODO: Not really necessary for region monitoring though ...
                        Log.d(TAG, elapsedSeconds - BEACON_REVISIT_TIMEOUT + " seconds left before timeout");
                        continue;
                    }
                }

                if (bc.getProximityTrigger() == px) {
                    if(debugging)
                        Toast.makeText(this, "Beacon " + bc.getName() + " was triggered !!! ", Toast.LENGTH_SHORT).show();
                    visitedBeacons.put(bc.getKey(), System.nanoTime());
                    playVideo(bc.getVideoID());
                    return;
                } else {
                    String msg = bc.getName() + " has not reached proximity " + bc.getProximityTrigger().toString();
                    if(debugging) {
                        showAToast(msg);
                    }
                    Log.d(TAG, msg);
                }
            } else {
                Log.d(TAG, "No beacon found with given identification.");
            }
        }
    }

    private void enableRanging() {
        Log.d(TAG, "Enabling Estimote Beacon ranging...");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "Beacon Monitoring Service Ready!");
                Collection<BeaconConfig> registeredBeacons = databaseController.getRegisteredBeacons();

                // A separate region for each beacon!
                for (BeaconConfig bc : registeredBeacons) {
                    Log.d(TAG, "Start monitoring beacon = " + bc.toString());
                    Region region = new Region(bc.getName() + "_region", bc.getUUID(), bc.getMajorVersion(), bc.getMinorVersion());
                    beaconManager.startRanging(region);
                }
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty() && !playingVideo) {
                    checkBeaconTrigger(list);
                }
            }
        });
    }

    private void playVideo(String videoId) {
        if(!this.playingVideo) {
            switch (playingMode) {
                case YOUTUBE:
                    startYTVideoActivity(videoId);
                    break;
                case VIMEO:
                    startVimeoActivity(videoId);
                    break;
                case VIDEO_VIEW:
                    startNativeVideoActivity(Integer.parseInt(videoId));
                    break;
                case VIDEO_WEB_VIEW:
                    Log.e(TAG, "VideoViewActivity not yet implemented.");
                    break;
                case DISABLED:
                    Log.d(TAG, "Video playing is currently disabled.");
            }
        }
        else {
            String msg = "A video is already playing!";
            if(debugging) {
                showAToast(msg);
            }
            Log.d(TAG, msg);
        }
    }

    private void enableRegionMonitoring() {
        beaconManager.setBackgroundScanPeriod(20000,0);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "Beacon Monitoring Service Ready!");

                Collection<BeaconConfig> registeredBeacons = databaseController.getRegisteredBeacons();

                // A separate region for each beacon!
                for(BeaconConfig bc : registeredBeacons) {
                    Log.d(TAG, "Start monitoring beacon = " + bc.toString());
                    beaconManager.startMonitoring(new Region(bc.getName() + "_region", bc.getUUID(), bc.getMajorVersion(), bc.getMinorVersion()));
                }
            }
        });

        final TextView debugTextView = (TextView) findViewById(R.id.debugText);

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                checkBeaconTrigger(list);
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
                Log.d(TAG, "Some region was exited");
//                showNotification("Region exited","Yep.");
            }
        });
    }

    private void startYTVideoActivity(String ytVideoId) {
        Intent intent = new Intent(this, YouTubeActivity.class);
        intent.putExtra(YT_VIDEO_ID, ytVideoId);
        playingVideo = true;
        startActivityForResult(intent, PLAY_VIDEO_REQUEST);
    }

    private void checkPlayQueue() {
        if(!playQueue.isEmpty()) {
            String newVideoId = playQueue.remove();
            playVideo(newVideoId);
        }
        else {
            Log.d(TAG, "Play queue is empty, idling ... ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLAY_VIDEO_REQUEST) {
            if(resultCode == Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "Playing video activity finished succesfully...");
                playingVideo = false;
                checkPlayQueue();
            }
        }
    }//onActivityResult


    private void startNativeVideoActivity(int videoId) {
        Intent intent = new Intent(this, VideoActivity.class);
        playingVideo = true;
        intent.putExtra(VideoActivity.VIDEO_ID_KEY, videoId);
        startActivityForResult(intent, PLAY_VIDEO_REQUEST);
    }

    private void startVimeoActivity(String videoURL) {
        String testVideoURL = "https://player.vimeo.com/video/173035125?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800";

        Intent intent = new Intent(this, VimeoActivity.class);
        intent.putExtra(VIDEO_URL,testVideoURL);
        startActivity(intent);
    }

    public void videoTestMethod(View v) {
//        String testVideoURL = "http://player.vimeo.com/video/24577973?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800";
//        startYTVideoActivity("B2lOBiN_RD8"); // Test video
        startNativeVideoActivity(R.raw.selectie_achterdek);
    }
}
