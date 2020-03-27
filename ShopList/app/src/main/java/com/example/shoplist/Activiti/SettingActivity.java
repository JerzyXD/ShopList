package com.example.shoplist.Activiti;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoplist.Classes.URLSendRequest;
import com.example.shoplist.Classes.User;
import com.example.shoplist.Notification.ServiceNotification;
import com.example.shoplist.R;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch switchNotification;
    SharedPreferences prefs;
    Button setTimeBtn;
    int myHour;
    int myMinute;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Настройки");
        prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        Logger.getLogger("mylog").log(Level.INFO, "on create");

        TextView vkText1 = findViewById(R.id.textVk1);
        TextView vkText2 = findViewById(R.id.textVk2);
        if ((vkText1 != null) && (vkText2 != null) ){
            vkText1.setMovementMethod(LinkMovementMethod.getInstance());
            vkText2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        TextView madeCounterTV = findViewById(R.id.madeCounterTV);
        TextView checkCounterTV = findViewById(R.id.checkCounterTV);
        checkCounterTV.setText("Куплено: " + MainActivity.getCheckedCounter());
        madeCounterTV.setText("Создано: " + MainActivity.getMadeCounter());

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

        Button clearStat = findViewById(R.id.clearStat);
        clearStat.setOnClickListener(v -> {
            checkCounterTV.setText("Куплено: 0");
            madeCounterTV.setText("Создано: 0");
            MainActivity.clearCheckCounter();
            MainActivity.clearMadeCounter();
        });

        Button login = findViewById(R.id.login_btn);
        login.setText(prefs.getString("login", "Чтобы авторизоваться для синхронизации заметок, нажмите здесь"));
        login.setOnClickListener(view -> {
            if (checkInternetConnection()) {
                VKSdk.login(this, "wall");
                onActivityResult(1,RESULT_OK, getIntent());

            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Нет сети", Toast.LENGTH_SHORT);
                toast.show();
            }

        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKLogin();
            }
            @Override
            public void onError(VKError error) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Вход отменён", Toast.LENGTH_SHORT);
                    toast.show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = prefs.edit();
        Button login = findViewById(R.id.login_btn);
        ed.putString("welcome", (String) login.getText() );
        ed.putBoolean("switchState", switchNotification.isChecked());


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

    private void VKLogin() {
        VKRequest request = VKApi.users().get();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList list =  (VKList) response.parsedModel;
                JSONObject object = list.get(0).fields;
                try {
                    int id = object.getInt("id");
                    MainActivity.setUserId(id);
                    name = object.getString("first_name");
                    User user = createUser(id, name);
                    String SERVER_IP = "http://192.168.56.1:8080/ShopListServer/";
                    URLSendRequest url = new URLSendRequest(SERVER_IP, 20000);
                    Logger.getLogger("mylog").log(Level.INFO, "send");
                    int r = Integer.parseInt(url.get("login?act=reg&iduser="+ user.getId() +"&username="+ user.getName() +"&madecounter="+ user.getMadeCounter()+ "&checkcounter=" + user.getCheckCounter()).replaceAll("\n",""));

                    Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Button login = findViewById(R.id.login_btn);
                login.setText("Здравствуйте, " + name);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("login", (String) login.getText() );
                ed.apply();

            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    private User createUser(int id, String name) {
        User newUser = new User(id, name, MainActivity.getMadeCounter(), MainActivity.getCheckedCounter());
        System.out.println("/////////////////////////////////////////////////////");
        System.out.println("Name: " + newUser.getName());
        System.out.println("Id: " +newUser.getId());
        System.out.println("MadeCounter: " + newUser.getMadeCounter());
        System.out.println("CheckCounter: " + newUser.getCheckCounter());
        return newUser;
    }

    private boolean checkInternetConnection() {
        boolean connect;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        connect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connect;
    }


}
