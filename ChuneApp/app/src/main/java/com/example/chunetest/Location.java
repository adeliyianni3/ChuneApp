package com.example.chunetest;

/**
 * Created by themiya on 12/4/2016.
 */

public class Location {
    public double latitude;
    public double longitude;

    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
