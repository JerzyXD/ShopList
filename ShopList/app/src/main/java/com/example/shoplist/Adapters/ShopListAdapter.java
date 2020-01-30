package com.example.shoplist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.DataBase.MyViewModel;
import com.example.shoplist.Fragments.CreateNoteDialogFragment;
import com.example.shoplist.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopListAdapter extends BaseAdapter {

    View view;
    Context context;
    List<NoteClass> list;
    private LayoutInflater lInflater;
    MyViewModel viewModel;

    public ShopListAdapter(Context context, List list, MyViewModel viewModel) {

        this.context = context;
        this.list = list;
        this.lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewModel = viewModel;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_element, viewGroup, false);
        }

        Button typeButton = view.findViewById(R.id.type);
        typeButton.setText(list.get(i).getType());
        typeButton.setBackgroundColor(getTitleColor(
                Arrays.asList(NoteClass.TYPE_LIST_ITEM).indexOf(list.get(i).getType()),
                NoteClass.TYPE_LIST_ITEM.length));

        ((TextView) view.findViewById(R.id.date)).setText(list.get(i).getDate());
        ((TextView) view.findViewById(R.id.text)).setText(list.get(i).getText());

        if (list.get(i).getAmount() != 0) {
            ((TextView) view.findViewById(R.id.textInfo)).setText((list.get(i).getAmount())
                    + " " + list.get(i).getUnits());
            view.findViewById(R.id.textInfo).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.textInfo).setVisibility(View.GONE);
        }


        CheckBox checkBox = view.findViewById(R.id.checkbox);


        checkBox.setOnCheckedChangeListener((v, b) -> {
            list.get(i).setChecked(b);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList(list);
            viewModel.update(list.get(i));
        });
        checkBox.setChecked(list.get(i).getChecked());

        typeButton.setOnClickListener( v -> {
            ((MainActivity) context).createDialog(list.get(i));
        });



        return view;
    }

    /**
     * Определение фона заголовка заметки.
     * (Просто красиво)
     */
    private int getTitleColor(int id, int sum) {
        int color1 = context.getResources().getColor(R.color.colorPrimary);
        int color2 = context.getResources().getColor(R.color.yellow);

        /*
        return color1 > color2 ? color2 : color1 +
                Math.abs(color2 - color1)*id/sum; */
        //TODO написать ссаные градиенты в недалеком пасмурном будущем.
        return color1;
    }
}