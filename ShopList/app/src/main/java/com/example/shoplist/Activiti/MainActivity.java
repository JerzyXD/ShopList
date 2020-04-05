package com.example.shoplist.Activiti;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.vk.sdk.VKSdk;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.shoplist.Classes.ServerRequest.deleteNoteServer;
import static com.example.shoplist.Classes.ServerRequest.updateServerUserInfo;

public class MainActivity extends AppCompatActivity {

    private List<NoteClass> shopList;
    private Menu menu;
    private MyViewModel viewModel;
    ShopListAdapter adapter;
    private static int checkedCounter;
    public static int madeCounter;
    public static int userId;
    SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        setTitle(R.string.title);

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
                    } else {
                        if (!shopList.get(i).getChecked()) {
                            incCheckCounter();
                        }
                        shopList.get(i).setChecked(true);
                        viewModel.update(shopList.get(i));
                    }
                }
                break;

            case R.id.copyButton:
                StringBuilder builder = new StringBuilder();
                for (NoteClass note: shopList) {
                    builder.append(shopList.indexOf(note) + 1 + ") " + note.toString() + "\n");
                }
                builder.setLength(builder.length()-1);
                ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", builder);
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getApplicationContext(), "Список скопирован", Toast.LENGTH_SHORT);
                toast.show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        checkedCounter = prefs.getInt("checkedCounter", 0);
        madeCounter = prefs.getInt("madeCounter", 0);
        userId = prefs.getInt("id", 0);
        System.out.println(userId);

        RecyclerView recyclerView = findViewById(R.id.shop_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ShopListAdapter adapter = new ShopListAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        viewModel.getAllNotes().observe( this, notes -> {
            adapter.setNotes(notes);
            shopList = new ArrayList<>(notes);

            try {
                MenuItem checkedButton = menu.findItem(R.id.checkedButton);
                boolean allCheck = true;
                for (int i = 0; i < notes.size(); i++) {
                    if (!notes.get(i).getChecked()) {
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

            } catch (NullPointerException ex){}


            setSubTitle();
        });

        /**
         * Обработка свайпов заметки (только влево)
         */

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNotePos(viewHolder.getAdapterPosition()));
                deleteNoteServer(adapter.getNotePos(viewHolder.getAdapterPosition()));
                setSubTitle();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(note -> {
            if (note.getChecked()) {
                note.setChecked(false);
                Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_LONG ).show();
            } else {
                note.setChecked(true);
                incCheckCounter();
                Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_LONG ).show();
            }

            updateServerUserInfo();

            viewModel.update(note);
            setSubTitle();
        });


    }

    /**
     * Переопределение кнопки назад, чтобы
     * не попасть обратно на сплэш.
     */
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("checkedCounter", checkedCounter);
        ed.putInt("madeCounter", madeCounter);
        ed.putInt("id", userId);
        ed.apply();
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

    private static void incCheckCounter() {
        checkedCounter++;
    }

    public static int getCheckedCounter() {
        return checkedCounter;
    }

    public static void incMadeCounter() {
        madeCounter++;
    }

    public static int getMadeCounter() {
        return madeCounter;
    }

    public static void clearCheckCounter() {
        checkedCounter = 0;
    }

    public static void clearMadeCounter() {
        madeCounter = 0;
    }

    public static void setUserId(int id) {
        MainActivity.userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}