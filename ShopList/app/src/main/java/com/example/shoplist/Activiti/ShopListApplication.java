package com.example.shoplist.Activiti;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKSdk;

public class ShopListApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }
}
