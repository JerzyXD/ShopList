package com.example.shoplist.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListHolder> {

    private List<NoteClass> notes = new ArrayList<>();

    @NonNull
    @Override
    public ShopListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_element, parent, false);
        return new ShopListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListHolder holder, int position) {
        NoteClass note = notes.get(position);
        holder.text.setText(note.getText());
        holder.type.setText(note.getType());
        //holder.units.setText(note.getUnits());
        //holder.amount.setText(note.getAmount());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<NoteClass> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ShopListHolder extends RecyclerView.ViewHolder {

        private Button type;
        private TextView text;
        private TextView amount;
        private TextView units;

        public ShopListHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.textInfo);
            amount = itemView.findViewById(R.id.textAmount);
            units = itemView.findViewById(R.id.textUnits);
        }
    }
}
