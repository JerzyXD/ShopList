package com.example.shoplist.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Activiti.SettingActivity;
import com.example.shoplist.Adapters.SpinnerTypeAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Classes.URLSendRequest;
import com.example.shoplist.DataBase.MyViewModel;
import com.example.shoplist.R;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.fragment.app.DialogFragment;

public class CreateNoteDialogFragment extends DialogFragment {

    private NoteClass note;
    private Context context;
    private List<NoteClass> list;
    private int id;
    private MyViewModel viewModel;
    private static String SERVER_IP = "http://192.168.56.1:8080/ShopListServer/";
    private static URLSendRequest url = new URLSendRequest(SERVER_IP, 20000);

    public CreateNoteDialogFragment(Context context, List list, MyViewModel viewModel ) {
        this.context = context;
        this.list = list;
        this.id = 0;
        this.viewModel = viewModel;
    }

    public CreateNoteDialogFragment(NoteClass note, Context context, List list, MyViewModel viewModel) {
        this.note = note;
        this.context = context;
        this.list = list;
        this.id = 0;
        this.viewModel = viewModel;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_fragment_create_note, null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerTypeAdapter(context, NoteClass.TYPE_LIST_ITEM));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button saveButton = v.findViewById(R.id.save_button);
        Button deleteButton = v.findViewById(R.id.delete_button);
        TextView textAmount = v.findViewById(R.id.textAmount);
        EditText textUnits = v.findViewById(R.id.textUnits);
        EditText text = v.findViewById(R.id.text);

        if (note !=  null) {
            deleteButton.setVisibility(View.VISIBLE);
            text.setText(note.getText());
            textAmount.setText(Integer.toString( note.getAmount()));
            textUnits.setText(note.getUnits());
            ((TextView) v.findViewById(R.id.titleText)).setText("Редактирование замеки");
            spinner.setSelection(Arrays.asList(NoteClass.TYPE_LIST_ITEM).indexOf(note.getType()));

            saveButton.setOnClickListener(view -> {
                String type = NoteClass.TYPE_LIST_ITEM[id];
                String input = text.getText().toString();
                String units = textUnits.getText().toString();
                int amount;
                try {
                    amount = Integer.parseInt(textAmount.getText().toString());
                } catch (NumberFormatException e) {
                    amount = 0;
                }
                if (input.equals("")) {
                    dismiss();
                } else {
                    note.setType(type);
                    note.setText(input);
                    note.setAmount(amount);
                    note.setUnits(units);
                    viewModel.update(note);
                    String SERVER_IP = "http://192.168.56.1:8080/ShopListServer/";
                    URLSendRequest url = new URLSendRequest(SERVER_IP, 20000);
                    Logger.getLogger("mylog").log(Level.INFO, "send");
                    int r = Integer.parseInt(url.get("note?act=edit&name="+ note.getText()
                            + "&type=" + note.getType()
                            + "&amount="+ note.getAmount()
                            + "&units=" + note.getUnits()
                            + "&idnote=" + note.getId()).replaceAll("\n",""));
                    Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
                    dismiss();
                }
            });
            deleteButton.setOnClickListener(view -> {
                viewModel.delete(note);
                deleteNoteServer(note);
                dismiss();
            });

        }else {
            saveButton.setOnClickListener(view -> {
                String type = NoteClass.TYPE_LIST_ITEM[id];
                String units = textUnits.getText().toString();
                int amount;
                try {
                     amount = Integer.parseInt(textAmount.getText().toString());
                } catch (NumberFormatException e) {
                    amount = 0;
                }
                String input = text.getText().toString();
                if (input.equals("")) {
                    dismiss();
                } else {
                    UUID uuid = UUID.randomUUID();
                    String id = uuid.toString();
                    NoteClass note = new NoteClass(input, type, units, amount, id);
                    list.add(0, note);
                    viewModel.insert(note);
                    noteAddServer(note);
                    dismiss();
                }
            });
        }

        return v;
    }

    public static void noteAddServer(NoteClass note) {
        Logger.getLogger("mylog").log(Level.INFO, "send");
        int r = Integer.parseInt(url.get("note?act=add&idnote="+ note.getId()
                + "&name="+ note.getText()
                + "&type=" + note.getType()
                + "&amount="+ note.getAmount()
                + "&units=" + note.getUnits()
                + "&date=" + note.getData()
                + "&checked=" + note.getChecked().toString()
                + "&iduser=" + MainActivity.getUserId()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
        MainActivity.incMadeCounter();
        updateServerUserInfo();

    }

    public static void updateServerUserInfo() {
        int r = Integer.parseInt(url.get("login?act=update&iduser="+MainActivity.getUserId()
                + "&madecounter=" + MainActivity.getMadeCounter()
                + "&checkcounter=" + MainActivity.getCheckedCounter()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }

    public static void deleteNoteServer(NoteClass note) {
        Logger.getLogger("mylog").log(Level.INFO, "send");
        int r = Integer.parseInt(url.get("note?act=delete&idnote=" + note.getId()).replaceAll("\n",""));
        Logger.getLogger("mylog").log(Level.INFO, "result: " + r);
    }

}
