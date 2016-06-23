package com.software.simons.museum_beacons;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView beaconVideoView;

    private void playVideo(int resourceId) {

        String videoURIPath = "android.resource://com.software.simons.museum_beacons/" + resourceId;
        Uri videoURI = Uri.parse(videoURIPath);
        beaconVideoView.setVideoURI(videoURI);
        beaconVideoView.requestFocus();
        beaconVideoView.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconVideoView = (VideoView)findViewById(R.id.beaconVideoView);

        //Play video
        playVideo(R.raw.selectie_storm_org);
    }
}
