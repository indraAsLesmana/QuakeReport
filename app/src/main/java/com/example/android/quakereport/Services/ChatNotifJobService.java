package com.example.android.quakereport.Services;

import android.app.IntentService;
import android.content.Intent;

import com.example.android.quakereport.helper.ChatRemiderTask;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class ChatNotifJobService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ChatNotifJobService() {
        super("ChatNotifJobService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ChatRemiderTask.executeTask(this, action);
    }
}
