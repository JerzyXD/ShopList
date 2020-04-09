package com.example.shoplist.ServerConnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.example.shoplist.Activiti.MainActivity.getRequestArray;
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
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void sendRequest() {
        String[] requestArray = getRequestArray();
        for (int i = 0; i < requestArray.length; i++) {
            if (requestArray[i] != null) {
                sendRequestFromMemory(requestArray[i]);
            }
        }
    }


}
