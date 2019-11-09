package com.example.shoplist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.R;

import java.util.ArrayList;

public class ShopListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NoteClass> arrayList;
    private LayoutInflater lInflater;

    public ShopListAdapter(Context context, ArrayList arrayList) {

        this.context = context;
        this.arrayList = arrayList;
        this.lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_element, viewGroup, false);
        }

        ((TextView) view.findViewById(R.id.type)).setText(arrayList.get(i).getType());
        ((TextView) view.findViewById(R.id.date)).setText(arrayList.get(i).getDate());
        ((TextView) view.findViewById(R.id.text)).setText(arrayList.get(i).getText());

        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setChecked(arrayList.get(i).getChecked());
        checkBox.setOnCheckedChangeListener((v, b) -> {
            arrayList.get(i).setChecked(b);
            ((MainActivity) context).saveList();
        });

        Button typeButton = view.findViewById(R.id.type);
        /*
            typeButton.setOnClickListener( v -> {
            ((MainActivity) context).createDialog();
            EditText text = v.findViewById(R.id.text);
            String type = NoteClass.TYPE_LIST_ITEM[1];
            String input = text.getText().toString();
            NoteClass note = new NoteClass(type,input);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList();
        });
        Дописать функционал кнопке
         */


        //TODO определить нажатие на заголовок заметки.

        return view;
    }
}
