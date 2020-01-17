package com.example.shoplist.Activiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shoplist.Notification.ServiceNotification;
import com.example.shoplist.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences mSettings;
    private Switch switchNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences("SettingActivity", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_setting);
        setTitle("Настройки");
        TextView vkText1 = findViewById(R.id.textVk1);
        TextView vkText2 = findViewById(R.id.textVk2);
        if ((vkText1 != null) && (vkText2 != null) ){
            vkText1.setMovementMethod(LinkMovementMethod.getInstance());
            vkText2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        switchNotification = findViewById(R.id.switchNotification);
        switchNotification.setOnCheckedChangeListener(this);
        SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean("switchState", true);

        switchNotification.setChecked(switchState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = getSharedPreferences("test", Context.MODE_PRIVATE).edit();
        ed.putBoolean("switchState", switchNotification.isChecked());
        ed.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startService(new Intent(this, ServiceNotification.class));
        } else {
            stopService(new Intent(this, ServiceNotification.class));
        }
    }

}
