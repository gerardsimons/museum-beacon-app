package com.software.simons.museum_beacons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public static final String VIDEO_URL = "video_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void videoTestMethod(View v) {
//        String testVideoURL = "http://player.vimeo.com/video/24577973?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800";
        String testVideoURL = "https://player.vimeo.com/video/173035125?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800";

        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(VIDEO_URL,testVideoURL);
        startActivity(intent);
    }
}
