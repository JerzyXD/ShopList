package com.example.shoplist.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

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
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    /**
     * Метод для того, чтобы задать время показа уведомления
     */

    void someTask() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastNotification.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );


        Calendar timeToStart = Calendar.getInstance();
        timeToStart.set(Calendar.HOUR_OF_DAY, 18);
        timeToStart.set(Calendar.MINUTE, 0);
        Calendar now = Calendar.getInstance();
        if(timeToStart.getTimeInMillis() < now.getTimeInMillis())
            timeToStart.add(Calendar.DAY_OF_MONTH, 1);
        am.setRepeating(AlarmManager.RTC_WAKEUP, timeToStart.getTimeInMillis(), AlarmManager.INTERVAL_DAY, contentIntent);
    }
}
