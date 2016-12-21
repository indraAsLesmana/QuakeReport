package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeModel {
    private String mMagnitude, mLocation, mDate;

    public EarthquakeModel(String mMagnitude, String mLocation, String mDate) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mDate = mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }
}
