package com.example.shoplist.Activiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Adapters.TestAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.Fragments.FilterDialogFragment;
import com.example.shoplist.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private ArrayList<NoteClass> testList = new ArrayList<>();
    private ArrayList<NoteClass> startTestList = new ArrayList<>();
    private TestAdapter testAdapter;
    private SharedPreferences mSettings;




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
                FilterDialogFragment dialog = new FilterDialogFragment(this, testList);
                dialog.show(getSupportFragmentManager(), "tag");
                break;
            case R.id.menuSetting:
                //TODO создать переход к настройкам тут (если они вообще будут).
                break;
            case R.id.checkedButton:
                boolean check = true;
                for (int i = 0; i < testList.size(); i++) {
                    if (testList.get(i).getChecked() == false) {
                        check = false;
                    }
                }
                for (int i = 0; i < testList.size(); i++) {
                    if (check) {
                        testList.get(i).setChecked(false);
                    } else {
                        testList.get(i).setChecked(true);
                    }
                }
                updateAdapterData();
                saveList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSettings.getString("test","") ;
        if (json.length() == 0) try {
            for (NoteClass noteClass : gson.fromJson(json, NoteClass[].class)) {
                testList.add(noteClass);
            }
        } catch (JsonSyntaxException ex) {}

        startTestList.addAll(testList);
        setSubTitle();
        RecyclerView recyclerView = findViewById(R.id.test_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter = new TestAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(testAdapter);
    }

    public void createDialog() {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(this, testList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    public void createDialog(NoteClass note) {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(note,this, testList);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    public void updateAdapterData() {
        if (testAdapter != null) {
            testAdapter.notifyDataSetChanged();
            setSubTitle();
        }
    }

    public void saveList() {
        mSettings = getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        mSettings.edit()
                .putString("test", new Gson().toJson(testList))
                .apply();
    }

    private void setSubTitle() {
        int checkedCount = 0;
        for (NoteClass note : testList ) {
            if (note.getChecked()) {
                checkedCount++;
            }
        }
        getSupportActionBar().setSubtitle(checkedCount+"/"+testList.size());
    }
}
