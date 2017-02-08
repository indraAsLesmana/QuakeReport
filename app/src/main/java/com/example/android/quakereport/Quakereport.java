package com.example.android.quakereport;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.android.quakereport.Services.ChatNotifService;
import com.example.android.quakereport.helper.Constant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by indraaguslesmana on 2/8/17.
 */

public class Quakereport extends Application {

    private static Quakereport sInstance;
    private static SharedPreferences sPreferences;

    //firebase
    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChilEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        //initialize firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child(Constant.KEY_MESSAGES);

        startService(new Intent(this, ChatNotifService.class));
    }

    public static Quakereport getsInstance() {
        return sInstance;
    }

    public static DatabaseReference getmMessageDatabaseReference() {
        return mMessageDatabaseReference;
    }

    public static FirebaseDatabase getmFirebaseDatabase() {
        return mFirebaseDatabase;
    }

    public static void setUserSession(String mail) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(Constant.PREFERENCE_USER_MAIL, mail);
        editor.commit();
    }

    public static void destroyUserSession() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.remove(Constant.PREFERENCE_USER_MAIL);
        editor.commit();
    }
}
