package com.example.shoplist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
        ((CheckBox) view.findViewById(R.id.checkbox)).setChecked(arrayList.get(i).getChecked());

        return view;
    }
}
