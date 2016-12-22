package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeModel {

    private String mPlace;
    private double mMag;
    private long mTime;

    public EarthquakeModel(double mMag, String mPlace, long mTime) {
        this.mMag = mMag;
        this.mPlace = mPlace;
        this.mTime = mTime;
    }

    public double getmMag() {
        return mMag;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmTime() {
        return mTime;
    }
}
