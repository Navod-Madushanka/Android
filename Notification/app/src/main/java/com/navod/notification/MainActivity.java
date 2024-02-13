package com.navod.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private NotificationManager notificationManager;
    public String channelId = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "INFO", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            channel.setDescription("This is information channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0, 1000, 1000, 1000});
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "Navod");

                Intent intent = new Intent(MainActivity.this, MassageActivity.class);
                intent.putExtras(bundle);

                PendingIntent pendingIntent = PendingIntent
                        .getActivity(MainActivity.this, 0,
                                intent,
                                PendingIntent.FLAG_ONE_SHOT |
                                PendingIntent.FLAG_IMMUTABLE, bundle);

                Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentTitle("My Notification")
                        .setContentText("This is sample Notification content")
                        .setColor(Color.RED)
                        .setContentIntent(pendingIntent)
                        .build();
                notificationManager.notify(1, notification);
            }
        });
    }
}