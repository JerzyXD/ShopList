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
import com.example.shoplist.Adapters.SpinnerTypeAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.DialogFragment;

public class CreateNoteDialogFragment extends DialogFragment {

    private Context context;
    private ArrayList<NoteClass> list;
    private int id;
    private NoteClass note;

    public CreateNoteDialogFragment(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
        this.id = 0;
    }

    public CreateNoteDialogFragment(NoteClass note, Context context, ArrayList list) {
        this.note = note;
        this.context = context;
        this.list = list;
        this.id = 0;
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

        if (note !=  null) {
            deleteButton.setVisibility(View.VISIBLE);
            TextView textAmount = v.findViewById(R.id.textAmount);
            EditText textUnits = v.findViewById(R.id.textUnits);
            EditText text = v.findViewById(R.id.text);
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
                note.setType(type);
                note.setText(input);
                note.setAmount(amount);
                note.setUnits(units);
                //((MainActivity) context).updateAdapterData();
                //((MainActivity) context).saveList();
                ((MainActivity) context).updateAdapterData();
                ((MainActivity) context).saveList();
                dismiss();
            });
            deleteButton.setOnClickListener(view -> {
                list.remove(note);
                //((MainActivity) context).updateAdapterData();
                //((MainActivity) context).saveList();
                ((MainActivity) context).updateAdapterData();
                ((MainActivity) context).saveList();
                dismiss();
            });
        }else {
            saveButton.setOnClickListener(view -> {
                TextView textAmount = v.findViewById(R.id.textAmount);
                EditText textUnits = v.findViewById(R.id.textUnits);
                EditText text = v.findViewById(R.id.text);
                String type = NoteClass.TYPE_LIST_ITEM[id];
                String units = textUnits.getText().toString();
                int amount;
                try {
                     amount = Integer.parseInt(textAmount.getText().toString());
                } catch (NumberFormatException e) {
                    amount = 0;
                }

                String input = text.getText().toString();
                NoteClass note = new NoteClass(input,type,units,amount);
                list.add(0, note);
                //((MainActivity) context).updateAdapterData();
                //((MainActivity) context).saveList();
                ((MainActivity) context).updateAdapterData();
                ((MainActivity) context).saveList();
                dismiss();
            });
        }


        return v;
    }


}
