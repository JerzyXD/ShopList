package com.example.shoplist.Activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private ArrayList<NoteClass> shopList = new ArrayList<>();
    private ShopListAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        super.onCreateOptionsMenu(menu);
        setTitle(R.string.title);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.addButton:
                shopList.add(new NoteClass("Хлеб", "Продукты"));
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        shopList.clear();

        shopList.add(new NoteClass("Хлеб", "Продукты"));

        ListView listView = findViewById(R.id.shop_list);
        adapter = new ShopListAdapter(this, shopList);
        listView.setAdapter(adapter);

        Gson gson = new Gson();
        String json = mSettings.getString("list","") ;
        if (json.length() != 0) {
            try {
                for (NoteClass noteClass : gson.fromJson(json, NoteClass[].class)) {
                    shopList.add(noteClass);
                }
            } catch (JsonSyntaxException ex) {}
        }
    }

    @Override
    public void onBackPressed() {

    }
}
