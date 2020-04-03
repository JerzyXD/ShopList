package com.example.shoplist.Notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.R;

import androidx.core.app.NotificationCompat;


public class BroadcastNotification extends BroadcastReceiver {

    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "ShopList channel";



    /**
     * Метод для создания уведомления
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context , CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_check_box_24px)
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .setContentTitle("Напоминание")
                        .setContentText("Пора сходить в магазин")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);


        notificationManager.notify(NOTIFY_ID, builder.build());
    }

}
