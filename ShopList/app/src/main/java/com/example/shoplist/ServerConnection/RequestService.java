package com.example.shoplist.ServerConnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.Nullable;

import static com.example.shoplist.Activiti.MainActivity.getRequestArray;
import static com.example.shoplist.Activiti.MainActivity.saveRequestArray;
import static com.example.shoplist.ServerConnection.ServerRequest.noteAddServer;
import static com.example.shoplist.ServerConnection.ServerRequest.sendRequestFromMemory;

public class RequestService extends Service {

    final String LOG_TAG = "myLogs";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        sendRequest();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    private void sendRequest() {
        new Thread(() -> {
            String[] requestArray = getRequestArray();
            for (String s : requestArray) {
                if (s != null) {
                    sendRequestFromMemory(s);
                    Logger.getLogger(LOG_TAG).log(Level.INFO, s);

                } else break;
            }
            requestArray = new String[50];
            saveRequestArray(requestArray);
            stopSelf();
        }).start();
    }

}
