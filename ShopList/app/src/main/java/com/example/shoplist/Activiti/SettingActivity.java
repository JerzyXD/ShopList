package com.example.shoplist.Activiti;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shoplist.Notification.ServiceNotification;
import com.example.shoplist.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Настройки");
        TextView vkText1 = findViewById(R.id.textVk1);
        TextView vkText2 = findViewById(R.id.textVk2);
        if ((vkText1 != null) && (vkText2 != null) ){
            vkText1.setMovementMethod(LinkMovementMethod.getInstance());
            vkText2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        Switch startNotification = findViewById(R.id.startNotification);
        startNotification.setOnCheckedChangeListener(this::onCheckedChanged);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startService(new Intent(this, ServiceNotification.class));
            //System.out.println("Service start");
        } else {
            stopService(new Intent(this, ServiceNotification.class));
            //System.out.println("Service stop");
        }
    }

}
