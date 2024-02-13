package com.navod.etrade.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.navod.etrade.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "INFO";
    private static final String CHANNEL_NAME = "eTrade Notification";
    private static final int NOTIFICATION_ID = 1;
    public static void createNotificationChannel(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    public static void sendNotification(Context context, String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentText(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
