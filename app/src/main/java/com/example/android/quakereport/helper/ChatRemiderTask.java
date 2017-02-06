package com.example.android.quakereport.helper;

import android.content.Context;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class ChatRemiderTask {
    public static final String ACTION_TO_CHAT_THREAD = "to-chat-thread";

    public static void executeTask(Context context, String action){
        if (action.equals(ACTION_TO_CHAT_THREAD)){
            notifCreate(context);
        }
    }

    private static void notifCreate(Context context){
        Notification.createNotifChat(context);
    }
}
