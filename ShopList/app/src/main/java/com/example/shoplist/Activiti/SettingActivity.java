package com.example.shoplist.Activiti;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shoplist.Notification.ServiceNotification;
import com.example.shoplist.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch switchNotification;
    SharedPreferences prefs;
    Button setTimeBtn;
    int myHour = 18;
    int myMinute = 0;



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

        switchNotification = findViewById(R.id.switchNotification);
        switchNotification.setOnCheckedChangeListener(this);
        prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean("switchState", true);
        switchNotification.setChecked(switchState);

        setTimeBtn = findViewById(R.id.setTimeBtn);
        setTimeBtn.setOnClickListener(v -> {
            createDialog();
        });

        }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = getSharedPreferences("test", Context.MODE_PRIVATE).edit();
        ed.putBoolean("switchState", switchNotification.isChecked());
        ed.apply();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startService(new Intent(this, ServiceNotification.class));
        } else {
            stopService(new Intent(this, ServiceNotification.class));
        }
    }

    public void createDialog() {
        TimePickerDialog.OnTimeSetListener myCallBack = (view, hourOfDay, minute) -> {
            myHour = hourOfDay;
            myMinute = minute;
            if (myMinute<9) {
                setTimeBtn.setText("Получать уведомление в " + myHour + ":" + "0" + myMinute  + ". Чтобы изменить время, нажмите здесь.");
            } else {
                setTimeBtn.setText("Получать уведомление в " + myHour + ":" + myMinute + ". Чтобы изменить время, нажмите здесь.");
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
        tpd.show();
    }

}
