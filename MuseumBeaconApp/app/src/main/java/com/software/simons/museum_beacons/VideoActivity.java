package com.software.simons.museum_beacons;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends Activity {


    private static final String TAG = "VideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        WebView videoWebView = (WebView) findViewById(R.id.videoWebView);
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.getSettings().setAppCacheEnabled(true);
        videoWebView.getSettings().setDomStorageEnabled(true);
        videoWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        videoWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        String videoURL = getIntent().getExtras().getString(MainActivity.VIDEO_URL);

        // Additional GET parameters to affect control
//        videoURL += "?autoplay=true";

        Log.d(TAG, "Video URL received = " + videoURL);
        videoWebView.loadUrl(videoURL);
    }
}
