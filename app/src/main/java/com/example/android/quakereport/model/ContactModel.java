package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class ContactModel {

    private String mId, mName, mEmail, mAddress, mGender;
    private boolean gender;

    public ContactModel(String mId, String mName, String mEmail, String mAddress, String mGender) {
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mGender = mGender;
    }

    public String getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmGender() {
        return mGender;
    }

    public boolean isGender() {
        return gender;
    }
}
