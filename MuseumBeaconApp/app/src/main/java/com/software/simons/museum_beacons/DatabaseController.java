package com.software.simons.museum_beacons;

import android.util.Log;

import com.estimote.sdk.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gerard on 03/07/16.
 */
public class DatabaseController {

    private static final String TAG = "DatabaseController";
    //    private List<BeaconConfig> registeredBeacons;
    private Map<String, BeaconConfig> registeredBeacons;

    private static DatabaseController Instance;

    public static DatabaseController getInstance() {
        if(Instance == null) {
            Instance = new DatabaseController();
        }
        return Instance;
    }

    private DatabaseController() {
        registeredBeacons = new HashMap<>();
    }

    public void mockYouTubeData() {
        UUID uuid = UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"); //They all share the same UUID
//        registeredBeacons.add(new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.FAR, "MEmAGJac7Is")); // Top difficult languages to learn
//        registeredBeacons.add(new BeaconConfig("blueberry", uuid, 33777, 44688, Utils.Proximity.NEAR, "cNzPxhiTUMs")); //Music with Diego
//        registeredBeacons.add(new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.IMMEDIATE, "MoY1tkQAUNI")); // Kumail Nanjiani Regrets Some of His ...

        // Configuration with 3 distinct labels, one video per label
        registeredBeacons.put("60128:45892", new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.IMMEDIATE, "7-qGKqveZaM")); // Hi Bye
        registeredBeacons.put("33777:44688", new BeaconConfig("blueberry", uuid, 33777, 44688, Utils.Proximity.NEAR, "9uDgJ9_H0gg")); // SDude
        registeredBeacons.put("42465:50049", new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.FAR, "tPEE9ZwTmy0")); // Cat?

        // 3 Videos per one label, one for each proximity zone
//        registeredBeacons.put("60128:45892:" + Utils.Proximity.IMMEDIATE, new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.IMMEDIATE, "7-qGKqveZaM")); // Hi Bye
//        registeredBeacons.put("60128:45892:" + Utils.Proximity.NEAR, new BeaconConfig("blueberry", uuid, 60128, 45892, Utils.Proximity.NEAR, "9uDgJ9_H0gg")); // Hi Bye
//        registeredBeacons.put("60128:45892:" + Utils.Proximity.FAR, new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.FAR, "tPEE9ZwTmy0")); // Hi Bye
    }

    public void mockNativeVideoData() {
        UUID uuid = UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"); //They all share the same UUID
        Log.d(TAG, "Mocking native video data");
//        registeredBeacons.put("60128:45892", new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.IMMEDIATE, R.raw.selectie_storm + ""));
//        registeredBeacons.put("33777:44688", new BeaconConfig("blueberry", uuid, 33777, 44688, Utils.Proximity.NEAR, R.raw.selectie_juliana + ""));
//        registeredBeacons.put("42465:50049", new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.FAR, R.raw.selectie_achterdek + ""));
//        registeredBeacons.put("42465:50049", new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.NEAR, R.raw.selectie_achterdek + ""));

//        registeredBeacons.put("60128:45892", new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.IMMEDIATE, R.raw.selectie_storm + ""));
//        registeredBeacons.put("33777:44688", new BeaconConfig("blueberry", uuid, 33777, 44688, Utils.Proximity.IMMEDIATE, R.raw.selectie_juliana + ""));
//        registeredBeacons.put("42465:50049", new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.IMMEDIATE, R.raw.selectie_achterdek + ""));

        registeredBeacons.put("60128:45892", new BeaconConfig("ice", uuid, 60128, 45892, Utils.Proximity.IMMEDIATE, R.raw.selectie_storm + ""));
        registeredBeacons.put("33777:44688", new BeaconConfig("blueberry", uuid, 33777, 44688, Utils.Proximity.NEAR, R.raw.selectie_juliana + ""));
        registeredBeacons.put("42465:50049", new BeaconConfig("mint", uuid, 42465, 50049, Utils.Proximity.NEAR, R.raw.selectie_achterdek + ""));
    }

    public Collection<BeaconConfig> getRegisteredBeacons() {
        return registeredBeacons.values();
    }

    public BeaconConfig findRegisteredBeaconWithProximity(UUID uuid, int majorVersion, int minorVersion, Utils.Proximity proximity) {
//        String beaconKey = String.format("%d:%d:%s", majorVersion, minorVersion, proximity.toString()); //Attempt to use proximity as an additional identifier ... Does not work
        String beaconKey = String.format("%d:%d", majorVersion, minorVersion);
        if (registeredBeacons.containsKey(beaconKey)) {
            return registeredBeacons.get(beaconKey);
        }
        else return null;
    }
}
