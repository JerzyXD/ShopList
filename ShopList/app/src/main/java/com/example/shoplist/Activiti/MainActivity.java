package com.example.shoplist.Activiti;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Classes.Sorter;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.Fragments.FilterDialogFragment;
import com.example.shoplist.Notification.BroadcastNotification;
import com.example.shoplist.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private ArrayList<NoteClass> shopList = new ArrayList<>();
    private ArrayList<NoteClass> startList = new ArrayList<>();
    private ShopListAdapter adapter;
    private Menu menu;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        setTitle(R.string.title);

        MenuItem checkedButton = menu.findItem(R.id.checkedButton);
        for (int i = 0; i < shopList.size(); i++) {
            if (!shopList.get(i).getChecked()) {
                checkedButton.setTitle("Выделить всё");
                checkedButton.setIcon(R.drawable.ic_check_box_24px);
            } else {
                checkedButton.setTitle("Убрать выделенные");
                checkedButton.setIcon(R.drawable.ic_check_box_outline_blank_24px);
            }
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.deleteButton:
                shopList.removeIf(NoteClass::getChecked);
                updateAdapterData();
                saveList(shopList);
                break;
            case R.id.addButton:
                createDialog();
                break;
            case R.id.menuFilter:
                FilterDialogFragment dialog = new FilterDialogFragment(this, shopList);
                dialog.show(getSupportFragmentManager(), "tag");
                break;
            case R.id.menuSetting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.checkedButton:
                boolean check = true;
                for (int i = 0; i < shopList.size(); i++) {
                    if (!shopList.get(i).getChecked()) {
                        check = false;
                    }
                }

                for (int i = 0; i < shopList.size(); i++) {
                    if (check) {
                        shopList.get(i).setChecked(false);
                        item.setTitle("Выделить всё");
                        item.setIcon(R.drawable.ic_check_box_24px);
                    } else {
                        shopList.get(i).setChecked(true);
                        item.setTitle("Убрать выделенные");
                        item.setIcon(R.drawable.ic_check_box_outline_blank_24px);
                    }
                }
                updateAdapterData();
                saveList(shopList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mSettings.getString("listNote","") ;
        if (json.length() != 0) {
            try {
                Collections.addAll(shopList, gson.fromJson(json, NoteClass[].class));
            } catch (JsonSyntaxException ex) {}
        }
        startList.addAll(shopList);
        setSubTitle();

        ListView listView = findViewById(R.id.shop_list);
        adapter = new ShopListAdapter(this, shopList);
        listView.setAdapter(adapter);



    }

    /**
     * Переопределение кнопки назад чтоб
     * не попасть обратно на сплэш.
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * Запись количества отмеченных товаров в subtitle
     */

    private void setSubTitle() {
        int checkedCount = 0;
        for (NoteClass note : shopList ) {
            if (note.getChecked()) {
                checkedCount++;
            }
        }
        getSupportActionBar().setSubtitle(checkedCount+"/"+shopList.size());
    }

    /**
     * Сохранение списка покупок.
     */
    public void saveList(ArrayList<NoteClass> saveList) {
        mSettings.edit()
                .putString("listNote", new Gson().toJson(saveList))
                .apply();

        try {
            MenuItem checkedButton = menu.findItem(R.id.checkedButton);
            boolean allCheck = true;
            for (int i = 0; i < shopList.size(); i++) {
                if (!shopList.get(i).getChecked()) {
                    allCheck = false;
                }
            }

            if (allCheck) {
                checkedButton.setTitle("Убрать выделенные");
                checkedButton.setIcon(R.drawable.ic_check_box_outline_blank_24px);
            } else {
                checkedButton.setTitle("Выделить всё");
                checkedButton.setIcon(R.drawable.ic_check_box_24px);
            }

        } catch (NullPointerException ex) {}

    }

    /**
     * Обновление данных адатера.
     */
    public void updateAdapterData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            setSubTitle();
        }
    }

    /**
     * Открытие диалога для создания заметки.
     */
    public void createDialog() {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(this, shopList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    /**
     * Открытие диалога для редактирования заметки.
     * @param note
     */
    public void createDialog(NoteClass note) {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(note,this, shopList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    /**
     * Перезапись списка заметок при отмене фильтра.
     */
    public void reloadNoteList() {
        shopList.clear();
        shopList.addAll(startList);
    }

}