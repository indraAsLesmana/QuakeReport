package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeModel {

    private String mPlace, mUrl;
    private double mMag;
    private long mTime;

    public EarthquakeModel(double mMag, String mPlace, long mTime, String mUrl) {
        this.mMag = mMag;
        this.mPlace = mPlace;
        this.mTime = mTime;
        this.mUrl = mUrl;
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

    public String getmUrl() {
        return mUrl;
    }
}
