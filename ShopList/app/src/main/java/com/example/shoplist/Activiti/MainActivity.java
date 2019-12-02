package com.example.shoplist.Activiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.Fragments.FilterDialogFragment;
import com.example.shoplist.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private ArrayList<NoteClass> shopList = new ArrayList<>();
    private ArrayList<NoteClass> startList = new ArrayList<>();
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
                createDialog();
                break;
            case R.id.menuFilter:
                FilterDialogFragment dialog = new FilterDialogFragment(this, shopList);
                dialog.show(getSupportFragmentManager(), "tag");
                break;
            case R.id.menuSetting:
                //TODO создать переход к настройкам тут (если они вообще будут).
                break;
            case R.id.checkedButton:
                boolean check = true;
                for (int i = 0; i < shopList.size(); i++) {
                    if (shopList.get(i).getChecked() == false) {
                        check = false;
                    }
                }
                for (int i = 0; i < shopList.size(); i++) {
                    if (check) {
                        shopList.get(i).setChecked(false);
                    } else {
                        shopList.get(i).setChecked(true);
                    }
                }
                updateAdapterData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mSettings.getString("listNote","") ;
        if (json.length() != 0) {
            try {
                for (NoteClass noteClass : gson.fromJson(json, NoteClass[].class)) {
                    shopList.add(noteClass);
                }
            } catch (JsonSyntaxException ex) {}
        }
        startList.addAll(shopList);
        setSubTitle();
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        adapter = new ShopListAdapter(this,shopList);
        recyclerView.setAdapter(adapter);
    }

    private void setSubTitle() {
        int checkedCount = 0;
        for (NoteClass note : shopList ) {
            if (note.getChecked()) {
                checkedCount++;
            }
        }
        getSupportActionBar().setSubtitle(checkedCount+"/"+shopList.size());
    }

    public void saveList() {
        mSettings.edit()
                .putString("listNote", new Gson().toJson(shopList))
                .apply();
    }

    public void updateAdapterData() {
        if (adapter != null) {
            //adapter.notifyDataSetChanged();
            adapter.notifyItemInserted(shopList.size());
            setSubTitle();
        }
    }

    public void createDialog() {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(this, shopList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    public void createDialog(NoteClass note) {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(note,this, shopList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

}
