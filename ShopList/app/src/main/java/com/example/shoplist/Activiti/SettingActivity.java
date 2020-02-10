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
    int myHour;
    int myMinute;
    int madeCounter;
    int checkedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Настройки");
        prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        checkedCounter = prefs.getInt("checkCounter", 0);
        madeCounter = prefs.getInt("madeCounter", 0);

        TextView vkText1 = findViewById(R.id.textVk1);
        TextView vkText2 = findViewById(R.id.textVk2);
        if ((vkText1 != null) && (vkText2 != null) ){
            vkText1.setMovementMethod(LinkMovementMethod.getInstance());
            vkText2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        TextView madeCounterTV = findViewById(R.id.madeCounterTV);
        TextView checkCounterTV = findViewById(R.id.checkCounterTV);
        checkCounterTV.setText("Куплено: " + checkedCounter);
        madeCounterTV.setText("Создано: " + madeCounter);

        myHour = prefs.getInt("hour", 18);
        myMinute = prefs.getInt("minute", 0);

        switchNotification = findViewById(R.id.switchNotification);
        switchNotification.setOnCheckedChangeListener(this);
        boolean switchState = prefs.getBoolean("switchState", true);
        switchNotification.setChecked(switchState);

        setTimeBtn = findViewById(R.id.setTimeBtn);
        if (myMinute<9) {
            setTimeBtn.setText("Получать уведомление в " + myHour + ":" + "0" + myMinute  + ". Чтобы изменить время, нажмите здесь.");
        } else {
            setTimeBtn.setText("Получать уведомление в " + myHour + ":" + myMinute + ". Чтобы изменить время, нажмите здесь.");
        }
        setTimeBtn.setOnClickListener(v -> {
            createDialog();
        });

        }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("madeCounter", madeCounter);
        ed.putInt("checkedCounter", checkedCounter);
        ed.putBoolean("switchState", switchNotification.isChecked());
        ed.apply();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startService(setTime());
        } else {
            stopService(new Intent(this, ServiceNotification.class));
        }
    }

    public void createDialog() {
        TimePickerDialog.OnTimeSetListener myCallBack = (view, hourOfDay, minute) -> {
            myHour = hourOfDay;
            myMinute = minute;
            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt("hour", myHour);
            ed.putInt("minute", myMinute);
            ed.apply();

            if (myMinute<9) {
                setTimeBtn.setText("Получать уведомление в " + myHour + ":" + "0" + myMinute  + ". Чтобы изменить время, нажмите здесь.");
            } else {
                setTimeBtn.setText("Получать уведомление в " + myHour + ":" + myMinute + ". Чтобы изменить время, нажмите здесь.");
            }

            if (switchNotification.isChecked()) {
                stopService(new Intent(this, ServiceNotification.class));
                startService(setTime());
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
        tpd.show();
    }

    public Intent setTime() {
        Intent intent = new Intent(this, ServiceNotification.class);
        intent.putExtra("hour", myHour);
        intent.putExtra("minute", myMinute);
        return intent;
    }

    public void setMadeCounter() {
        madeCounter++;
    }

    public void incCheckedCounter() {
        checkedCounter++;
        System.out.println(checkedCounter);
    }
}
