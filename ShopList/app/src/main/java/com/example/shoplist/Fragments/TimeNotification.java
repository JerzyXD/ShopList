package com.example.shoplist.Fragments;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.R;


public class TimeNotification extends BroadcastReceiver {
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "ShopList channel";

    public TimeNotification(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentTL = new Intent(context, MainActivity.class);
        PendingIntent.getActivity(context, 0, intentTL,
                PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context , CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_check_box_24px)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора сходить в магазин")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(NOTIFY_ID, builder.build());

    }

}
