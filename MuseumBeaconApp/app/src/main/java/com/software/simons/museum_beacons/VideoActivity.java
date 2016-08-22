package com.software.simons.museum_beacons;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends Activity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    public final static String VIDEO_ID_KEY = "videoKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        int videoId = getIntent().getExtras().getInt(VIDEO_ID_KEY, -1);
        Log.d(TAG, "Video ID received = " + videoId);
        VideoView videoPlayerView = (VideoView)findViewById(R.id.videoPlayerView);
        String path = "android.resource://" + getPackageName() + "/" + videoId;
        videoPlayerView.setVideoURI(Uri.parse(path));
        videoPlayerView.start();
        videoPlayerView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG,"Video finished playing....");
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                VideoActivity.this.finish();
            }
        });

    }


}
