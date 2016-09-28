package com.software.simons.museum_beacon;

import com.estimote.sdk.Utils;

/**
 * Created by gerard on 04/09/16.
 */
public class Util {

    public static Utils.Proximity ProximityForInt(int proximityInt) {
        switch(proximityInt) {
            case 0:
                return Utils.Proximity.IMMEDIATE;
            case 1:
                return Utils.Proximity.NEAR;
            case 2:
                return Utils.Proximity.FAR;
            default:
                return null;
        }
    }
}
