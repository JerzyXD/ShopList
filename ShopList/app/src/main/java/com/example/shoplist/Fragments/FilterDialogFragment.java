package com.example.shoplist.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class FilterDialogFragment extends DialogFragment {
    private Context context;
    private ArrayList<NoteClass> list;

    public FilterDialogFragment(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_fragment_filter, null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Comparator<NoteClass> textComp = new NoteClass.NameComparator().thenComparing(new NoteClass.NameComparator());
        Comparator<NoteClass> typeComp = new NoteClass.TypeComparator().thenComparing(new NoteClass.TypeComparator());
        Comparator<NoteClass> dateComp = new NoteClass.DateComparator().thenComparing(new NoteClass.DateComparator());
        Comparator<NoteClass> checkComp = new NoteClass.CheckComparator().thenComparing(new NoteClass.CheckComparator());

        Button sortTypeBtn = v.findViewById(R.id.sortTypeBtn);
        Button sortAlfBtn = v.findViewById(R.id.sortAlfBtn);
        Button sortDateBtn = v.findViewById(R.id.sortDateBtn);
        Button sortCheck = v.findViewById(R.id.sortCheckBtn);

        sortTypeBtn.setOnClickListener(view -> {
            Collections.sort(list, typeComp);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList(list);
            dismiss();
        });

        sortAlfBtn.setOnClickListener(view -> {
            Collections.sort(list, textComp);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList(list);
            dismiss();
        });

        sortDateBtn.setOnClickListener(view -> {
            Collections.sort(list, dateComp);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList(list);
            dismiss();
        });

        sortCheck.setOnClickListener(view -> {
                Collections.sort(list,checkComp);
                sortCheck.setText("Сначала отмеченные");
                ((MainActivity) context).updateAdapterData();
                ((MainActivity) context).saveList(list);
                dismiss();

        });


        return v;
    }

}
