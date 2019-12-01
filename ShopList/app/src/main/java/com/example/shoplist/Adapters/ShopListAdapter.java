package com.example.shoplist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<NoteClass> arrayList;
    Context context;

    public ShopListAdapter(Context context, ArrayList<NoteClass> notes) {
        this.arrayList = notes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        NoteClass note = arrayList.get(position);
        holder.text.setText(note.getText());
        holder.textInfo.setText(note.getAmount() + " " + note.getUnits());
        holder.type.setText(note.getType());
        holder.checkBox.setChecked(note.getChecked());
        holder.date.setText(note.getDate());

        holder.type.setBackgroundColor(getTitleColor(
                Arrays.asList(NoteClass.TYPE_LIST_ITEM).indexOf(arrayList.get(position).getType()),
                NoteClass.TYPE_LIST_ITEM.length));

        if (arrayList.get(position).getAmount() != 0) {
            holder.textInfo.setVisibility(View.VISIBLE);
        } else {
            holder.textInfo.findViewById(R.id.textInfo).setVisibility(View.GONE);
        }

        holder.type.setOnClickListener( v -> {
            ((MainActivity) context).createDialog(arrayList.get(position));
        });

        holder.checkBox.setOnCheckedChangeListener((v, b) -> {
            arrayList.get(position).setChecked(b);
            ((MainActivity) context).updateAdapterData();
            ((MainActivity) context).saveList();
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView text, textInfo, date;
        final Button type;
        final CheckBox checkBox;
        public ViewHolder(@NonNull View view) {
            super(view);
             text =  view.findViewById(R.id.text);
             textInfo = view.findViewById(R.id.textInfo);
             type = view.findViewById(R.id.type);
             checkBox = view.findViewById(R.id.checkbox);
             date = view.findViewById(R.id.date);
        }
    }

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
