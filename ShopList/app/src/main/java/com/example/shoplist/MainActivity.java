package com.example.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        ListView shop_list = findViewById(R.id.shop_list);
        Button add = findViewById(R.id.add);
        EditText input = findViewById(R.id.input);

        // Создаём пустой массив для хранения списка покупок
        // или добавляем список из памяти
        final ArrayList<String> list;
        String json =mSettings.getString("list","") ;
        if (json.length() == 0) {

            list = new ArrayList<>();

        } else {

            list = new ArrayList<>();
            for (String s : gson.fromJson(json, String[].class)) {

                list.add(s);

            }

        }

        //list.add("Хлеб");

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
        final ShopListAdapter adapter;
        adapter = new ShopListAdapter(this, list);
        // Привяжем массив через адаптер к ListView
        shop_list.setAdapter(adapter);


        add.setOnClickListener( click -> {
            list.add(input.getText().toString());
            adapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("list", gson.toJson(list) );
            editor.apply();
            input.setText("");
        });
    }
}
