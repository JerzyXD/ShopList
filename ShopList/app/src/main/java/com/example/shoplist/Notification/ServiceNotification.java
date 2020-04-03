package com.example.shoplist.Notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class ServiceNotification extends Service {

    final String LOG_TAG = "myLogs";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Создание сервиса
     */

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    /**
     * Запуск сервиса, содержащего метод someTask
     * @param intent
     * @param flags
     * @param startId
     * @return
     */

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        sendNotif(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    /**
     * Метод для того, чтобы задать время показа уведомления
     */

    void sendNotif(Intent intent2) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastNotification.class);
        int hour = intent2.getIntExtra("hour", 18);
        int minute = intent2.getIntExtra("minute", 0);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );

        Calendar timeToStart = Calendar.getInstance();
        timeToStart.set(Calendar.HOUR_OF_DAY, hour);
        timeToStart.set(Calendar.MINUTE, minute);
        System.out.println(hour + " " + minute);
        Calendar now = Calendar.getInstance();
        if(timeToStart.getTimeInMillis() < now.getTimeInMillis())
            timeToStart.add(Calendar.DAY_OF_MONTH, 1);
        am.setRepeating(AlarmManager.RTC_WAKEUP, timeToStart.getTimeInMillis(), AlarmManager.INTERVAL_DAY, contentIntent);
    }
}
