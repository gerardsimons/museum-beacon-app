package com.software.simons.museum_beacon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

public class ScreenSlidePageFragment extends Fragment {

    public static final String SSPF_IMAGE_KEY = "sspfImageKey";
    private static final String TAG = "SSPF";
    private ViewGroup rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        int imgResourceId = getArguments().getInt(SSPF_IMAGE_KEY);

        ImageView imgView = (ImageView) rootView.findViewById(R.id.slidePageImageView);
        imgView.setImageResource(imgResourceId);
        Log.d(TAG, "SSPF Created!");
        return rootView;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "SSPF Destroyed!");
        unbindDrawables(rootView.findViewById(R.id.fsspLayout)); // <---This should be the ID of this fragments (ScreenSlidePageFragment) layout
    }

    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
