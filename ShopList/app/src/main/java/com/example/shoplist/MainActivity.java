package com.example.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private ArrayList<NoteClass> shoplist = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        setTitle(R.string.title);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        shoplist.clear();

        shoplist.add(new NoteClass("Хлеб", "Продукты"));

        ListView listView = findViewById(R.id.shop_list);
        ShopListAdapter adapter = new ShopListAdapter(this, shoplist);
        listView.setAdapter(adapter);

        Gson gson = new Gson();
        String json = mSettings.getString("list","") ;
        if (json.length() != 0) {

            for (NoteClass noteClass : gson.fromJson(json, NoteClass[].class)) {

                shoplist.add(noteClass);

            }

        }


    }
}
