package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 1/19/17.
 */

public class UserProfileModel {
    public static String mUsername;
    public static String mEmail;

    public static final String ANYNOMUS = "anynomus";

    public UserProfileModel() {
    }

    public static String getmUsername() {
        return mUsername;
    }

    public static void setmUsername(String mUsername) {
        UserProfileModel.mUsername = mUsername;
    }

    public static String getmEmail() {
        return mEmail;
    }

    public static void setmEmail(String mEmail) {
        UserProfileModel.mEmail = mEmail;
    }
}
