package com.software.simons.museum_beacons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    private static final int TIME_OUT_PERIOD = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Automatically progress to next activity after some time
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
            }
        }, TIME_OUT_PERIOD);

    }
}
