package com.example.shoplist.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;

public class FilterDialogFragment extends DialogFragment {
    private Context context;
    private ArrayList<NoteClass> list;

    public FilterDialogFragment(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_fragment_filter, null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        

        return v;
    }

}
