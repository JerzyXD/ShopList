package com.example.shoplist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoplist.Activiti.MainActivity;
import com.example.shoplist.Classes.NoteClass;
import com.example.shoplist.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListHolder> {

    private List<NoteClass> notes = new ArrayList<>();
    private Context context;

    public ShopListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ShopListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_element, parent, false);
        return new ShopListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShopListHolder holder, int position) {
        NoteClass note = notes.get(position);
        holder.text.setText(note.getText());
        holder.type.setText(note.getType());
        String units = note.getUnits();
        String amount = Integer.toString(note.getAmount());
        if (units.equals("") | amount.equals("0")) {
            holder.info.setVisibility(View.GONE);
        } else {
            holder.info.setText(amount + " " + units);
            holder.info.setVisibility(View.VISIBLE);
        }

        holder.type.setOnClickListener(view -> {
            ((MainActivity) context).createDialog(note);
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<NoteClass> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<NoteClass> getNotes() {
        return notes;
    }

    public NoteClass getNotePos(int position) {
        return notes.get(position);
    }

    public class ShopListHolder extends RecyclerView.ViewHolder {

        private Button type;
        private TextView text;
        private TextView info;

        public ShopListHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.text);
            info = itemView.findViewById(R.id.textInfo);
        }
    }
}
