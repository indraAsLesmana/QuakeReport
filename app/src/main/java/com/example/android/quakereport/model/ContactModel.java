package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class ContactModel {

    private String mMag, mTitle, mTime;

    public ContactModel(String mMag, String mTitle, String mTime) {
        this.mMag = mMag;
        this.mTitle = mTitle;
        this.mTime = mTime;
    }

    public String getmMag() {
        return mMag;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmTime() {
        return mTime;
    }
}
