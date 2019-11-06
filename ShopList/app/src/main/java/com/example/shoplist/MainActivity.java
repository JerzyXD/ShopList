package com.example.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView shop_list = findViewById(R.id.shop_list);
        Button add = findViewById(R.id.add);
        EditText input = findViewById(R.id.input);


        // Создаём пустой массив для хранения списка покупок
        final ArrayList<String> list = new ArrayList<>();

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        // Привяжем массив через адаптер к ListView
        shop_list.setAdapter(adapter);

        add.setOnClickListener( click -> {
            list.add(0,input.getText().toString());
            adapter.notifyDataSetChanged();
            input.setText("");
        });
    }
}
