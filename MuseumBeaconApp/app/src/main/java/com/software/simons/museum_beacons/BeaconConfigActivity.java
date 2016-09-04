package com.software.simons.museum_beacons;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class BeaconConfigActivity extends Activity {

    public static final String ICE_SPINNER_PROXIMITY_KEY = "ice_proximity";
    public static final String MINT_SPINNER_PROXIMITY_KEY = "mint_proximity";
    public static final String BLUEBERRY_SPINNER_PROXIMITY_KEY = "blueberry_proximity";

    private spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SharedPreferences sp = getSharedPreferences(MainActivity.PREFEFRENCES_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            String selection = parent.getItemAtPosition(pos).toString();
            if(parent.getId() == R.id.ice_proximity_spinner) {
                editor.putString(ICE_SPINNER_PROXIMITY_KEY, selection);
                Log.d(TAG, "Changed spinner proximity of ICE to " + selecton);
            }
            else if(parent.getId() == R.id.mint_proximity_spinner) {
                editor.putString(MINT_SPINNER_PROXIMITY_KEY, position);
                Log.d(TAG, "Changed spinner proximity of MINT to " + selecton);
            }
            else if(parent.getId() == R.id.blueberry_proximity_spinner) {
                editor.putString(BLUEBERRY_SPINNER_PROXIMITY_KEY, position);
                Log.d(TAG, "Changed spinner proximity of BLUEBERRY to " + selecton);
            }
            editor.commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

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
    }
}
