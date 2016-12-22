package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class ContactModel {

    private String mMag, mPlace;
    private long mTime;

    public ContactModel(String mMag, String mPlace, long mTime) {
        this.mMag = mMag;
        this.mPlace = mPlace;
        this.mTime = mTime;
    }

    public String getmMag() {
        return mMag;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmTime() {
        return mTime;
    }
}
