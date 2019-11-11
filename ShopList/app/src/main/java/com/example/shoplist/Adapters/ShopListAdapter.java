package com.example.shoplist.Adapters;

import android.content.Context;
import android.graphics.Color;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

        Button button = view.findViewById(R.id.type);
        button.setText(arrayList.get(i).getType());
        button.setBackgroundColor(getTitleColor(
                Arrays.asList(NoteClass.TYPE_LIST_ITEM).indexOf(arrayList.get(i).getType()),
                NoteClass.TYPE_LIST_ITEM.length));

        ((TextView) view.findViewById(R.id.date)).setText(arrayList.get(i).getDate());
        ((TextView) view.findViewById(R.id.text)).setText(arrayList.get(i).getText());

        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setChecked(arrayList.get(i).getChecked());
        checkBox.setOnCheckedChangeListener((v, b) -> {
            arrayList.get(i).setChecked(b);
            ((MainActivity) context).saveList();
        });

        Button typeButton = view.findViewById(R.id.type);
        typeButton.setOnClickListener( l -> {
            ((MainActivity) context).createDialog(arrayList.get(i));
        });

        return view;
    }

    /**
     * Определение фона заголовка заметки.
     * (Просто красиво)
     */
    private int getTitleColor(int id, int summ) {
        int color1 = context.getResources().getColor(R.color.colorPrimary);
        int color2 = context.getResources().getColor(R.color.yellow);

        /*
        return color1 > color2 ? color2 : color1 +
                Math.abs(color2 - color1)*id/summ; */
        return color1;
    }
}
