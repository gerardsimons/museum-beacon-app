package com.software.simons.museum_beacon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.estimote.sdk.Utils;

public class BeaconConfigActivity extends Activity {

    public static final String PROXIMITY_SUFFIX = "proximity";
    public static final String ICE_SPINNER_PROXIMITY_KEY = "ice_proximity";
    public static final String MINT_SPINNER_PROXIMITY_KEY = "mint_proximity";
    public static final String BLUEBERRY_SPINNER_PROXIMITY_KEY = "blueberry_proximity";
    private static final String TAG = "BeaconConfigActivity";

    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SharedPreferences sp = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            String selection = parent.getItemAtPosition(position).toString();
            if(parent.getId() == R.id.ice_proximity_spinner) {
                editor.putInt(ICE_SPINNER_PROXIMITY_KEY, position);
                Log.d(TAG, "Changed spinner proximity of ICE to " + selection);
            }
            else if(parent.getId() == R.id.mint_proximity_spinner) {
                editor.putInt(MINT_SPINNER_PROXIMITY_KEY, position);
                Log.d(TAG, "Changed spinner proximity of MINT to " + selection);
            }
            else if(parent.getId() == R.id.blueberry_proximity_spinner) {
//                editor.putString(BLUEBERRY_SPINNER_PROXIMITY_KEY, position);
                editor.putInt(BLUEBERRY_SPINNER_PROXIMITY_KEY, position);
                Log.d(TAG, "Changed spinner proximity of BLUEBERRY to " + selection);
            }
            editor.commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setSpinnerFromPref(Spinner spinner, String prefKey, int defaultProximity) {
        SharedPreferences sp = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
        int proximityInt = sp.getInt(prefKey, defaultProximity);
        Utils.Proximity proximity = Util.ProximityForInt(proximityInt);
        if(proximity == null) {
            Log.e(TAG,"ERROR: Invalid proximity");
        }
        else {
            Log.d(TAG, spinner.getId() + " set to " + proximity);
            spinner.setSelection(proximityInt);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_config);

        Spinner iceSpinner = (Spinner) findViewById(R.id.ice_proximity_spinner);
        Spinner blueberrySpinner = (Spinner) findViewById(R.id.blueberry_proximity_spinner);
        Spinner mintSpinner = (Spinner) findViewById(R.id.mint_proximity_spinner);

        iceSpinner.setOnItemSelectedListener(spinnerListener);
        blueberrySpinner.setOnItemSelectedListener(spinnerListener);
        mintSpinner.setOnItemSelectedListener(spinnerListener);

        setSpinnerFromPref(iceSpinner, ICE_SPINNER_PROXIMITY_KEY, 0);
        setSpinnerFromPref(mintSpinner, MINT_SPINNER_PROXIMITY_KEY, 1);
        setSpinnerFromPref(blueberrySpinner, BLUEBERRY_SPINNER_PROXIMITY_KEY, 2);
    }
}
