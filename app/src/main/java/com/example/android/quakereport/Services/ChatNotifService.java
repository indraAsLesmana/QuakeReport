package com.example.android.quakereport.Services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.quakereport.Quakereport;
import com.example.android.quakereport.helper.Constant;
import com.example.android.quakereport.helper.Notification;
import com.example.android.quakereport.model.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by indraaguslesmana on 2/8/17.
 */

public class ChatNotifService extends Service {
    private final String TAG = ChatNotifService.class.getSimpleName();
    private SharedPreferences preferences;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Quakereport.getmMessageDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatModel chat = dataSnapshot.getValue(ChatModel.class);
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userMailRegistered = preferences.getString(Constant.PREFERENCE_USER_MAIL, "-");
                String currentUser = chat.getName();
                Log.d(TAG, "onDataChange: " + currentUser );
                Log.d(TAG, "onDataChange: " + "onPreference" + userMailRegistered);

                if (userMailRegistered.equals(currentUser)) {
                    return;
                }

                Notification.createNotifChat(getApplicationContext());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_STICKY; // explicit this service run and stop manualy
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
