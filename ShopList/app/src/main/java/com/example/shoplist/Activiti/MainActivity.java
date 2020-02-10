package com.example.shoplist.Activiti;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoplist.Adapters.ShopListAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.DataBase.MyViewModel;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.Fragments.FilterDialogFragment;
import com.example.shoplist.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<NoteClass> shopList;
    private Menu menu;
    private MyViewModel viewModel;
    ShopListAdapter adapter;
    SettingActivity settingActivity = new SettingActivity();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        setTitle(R.string.title);
        MenuItem checkedButton = menu.findItem(R.id.checkedButton);

        
        if(shopList != null) {
            for (int i = 0; i < shopList.size(); i++) {
                if (!shopList.get(i).getChecked()) {
                    checkedButton.setTitle("Выделить всё");
                    checkedButton.setIcon(R.drawable.ic_check_box_24px);
                } else {
                    checkedButton.setTitle("Убрать выделенные");
                    checkedButton.setIcon(R.drawable.ic_check_box_outline_blank_24px);
                }
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.deleteButton:
                viewModel.deleteAllNotes();
                break;
            case R.id.addButton:
                createDialog();
                break;
            case R.id.menuFilter:
                FilterDialogFragment dialog = new FilterDialogFragment(this, shopList, viewModel, adapter);
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
                        viewModel.update(shopList.get(i));
                        item.setTitle("Выделить всё");
                        item.setIcon(R.drawable.ic_check_box_24px);
                    } else {
                        shopList.get(i).setChecked(true);
                        viewModel.update(shopList.get(i));
                        item.setTitle("Убрать выделенные");
                        item.setIcon(R.drawable.ic_check_box_outline_blank_24px);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.shop_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ShopListAdapter adapter = new ShopListAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        viewModel.getAllNotes().observe( this, notes -> {
            adapter.setNotes(notes);
            shopList = new ArrayList<>(notes);
            setSubTitle();
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNotePos(viewHolder.getAdapterPosition()));
                setSubTitle();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(note -> {
            if (note.getChecked()) {
                note.setChecked(false);
                Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_LONG ).show();
            } else {
                note.setChecked(true);
                settingActivity.incCheckedCounter();
                Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_LONG ).show();
            }

            viewModel.update(note);
            setSubTitle();
        });


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
    public void saveList() {

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
    public void updateAdapterData(List<NoteClass> noteList) {
        if (adapter != null) {
            adapter.setNotes(noteList);
            setSubTitle();
        }
    }

    /**
     * Открытие диалога для создания заметки.
     */
    public void createDialog() {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(this, shopList, viewModel);
        dialog.show(getSupportFragmentManager(), "tag");
    }

    /**
     * Открытие диалога для редактирования заметки.
     * @param note
     */
    public void createDialog(NoteClass note) {
        CreateNoteDialogFragment dialog = new CreateNoteDialogFragment(note,this, shopList, viewModel);
        dialog.show(getSupportFragmentManager(), "tag");
    }

}