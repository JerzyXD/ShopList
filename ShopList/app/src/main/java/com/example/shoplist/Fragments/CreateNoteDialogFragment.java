package com.example.shoplist.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Adapters.SpinnerTypeAdapter;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;

public class CreateNoteDialogFragment extends DialogFragment {

    private Context context;
    private ArrayList<NoteClass> list;

    public CreateNoteDialogFragment(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_fragment_create_note, null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerTypeAdapter(context, NoteClass.TYPE_LIST_ITEM));

        Button saveButton = v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(view -> {
            EditText text = v.findViewById(R.id.text);
            String type = NoteClass.TYPE_LIST_ITEM[(int) spinner.getSelectedItemId()];
            String input = text.getText().toString();
            NoteClass note = new NoteClass(input,type);
            list.add(note);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList();
        });

        return v;
    }


}
