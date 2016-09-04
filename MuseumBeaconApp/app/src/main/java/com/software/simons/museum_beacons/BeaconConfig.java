package com.software.simons.museum_beacons;

import com.estimote.sdk.Utils;

import java.util.UUID;

/**
 * Created by gerard on 03/07/16.
 */
public class BeaconConfig {

    private final String name;
    private final String videoID;
    private final UUID uuid;
    private final int majorVersion;
    private final int minorVersion;

    private Utils.Proximity proximityTrigger;

    public BeaconConfig(String name, UUID uuid, int majorVersion, int minorVersion, Utils.Proximity proximityTrigger, String videoID) {
        this.name = name;
        this.uuid = uuid;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.proximityTrigger = proximityTrigger;
        this.videoID = videoID;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BeaconConfig) {
            BeaconConfig bc = (BeaconConfig) o;
            return bc.uuid == uuid && majorVersion == bc.majorVersion && bc.minorVersion == minorVersion;
        } else return false;
    }

    @Override
    public String toString() {
        return "Beacon '" + name + "' : \n" +
                "UUID : " + uuid + "\n" +
                "Major : " + majorVersion + "\n" +
                "Minor : " + minorVersion;

    }

    public String getKey() {
        return String.format("%d:%d", majorVersion, minorVersion);
    }

    public String getVideoID() {
        return videoID;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public Utils.Proximity getProximityTrigger() {
        return proximityTrigger;
    }

    public void setProximityTrigger(Utils.Proximity proximityTrigger) {
        this.proximityTrigger = proximityTrigger;
    }
}
