package com.example.android.quakereport.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.example.android.quakereport.R;
import com.example.android.quakereport.Services.ChatNotifService;
import com.example.android.quakereport.activity.EarthquakeActivity;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class Notification {
    private static final int CHAT_NOTIF_ID = 1;


    /**
     * pending intent
     * */
    private static final int CHAT_PENDING_INTENT = 101;
    private static final int ACTION_CHAT_READ = 201;


    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void createNotifChat (Context context){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.chat_notification_title))
                .setContentText(context.getString(R.string.chat_notification_contextText))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.chat_notifcation_body)))
                .setDefaults(android.app.Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(android.app.Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        /* WATER_REMINDER_NOTIFICATION_ID allows you to update or cancel the notification later on */
        notificationManager.notify(CHAT_NOTIF_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, EarthquakeActivity.class);
        return PendingIntent.getActivity(
                context,
                CHAT_NOTIF_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Action chatReadAction(Context context) {
        Intent chatRad = new Intent(context, ChatNotifService.class);
        chatRad.setAction(ChatRemiderTask.ACTION_TO_CHAT_THREAD);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                CHAT_PENDING_INTENT,
                chatRad,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action drinkWaterAction = new NotificationCompat.Action(R.mipmap.ic_launcher,
                "I did it!",
                incrementWaterPendingIntent);
        return drinkWaterAction;
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.messenger_bubble_large_blue);
        return largeIcon;
    }
}
