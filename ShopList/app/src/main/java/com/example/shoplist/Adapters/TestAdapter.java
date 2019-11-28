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
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private List<NoteClass> noteList;
    Context context;

    public class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private Button type;
        private TextView textInfo;
        private CheckBox checkBox;
        private TextView date;

        public void setNote (NoteClass note) {
            text.setText(note.getText());
            type.setText(note.getType());
            textInfo.setText(note.getAmount() + " " + note.getUnits());
            checkBox.setChecked(note.getChecked());
            date.setText(note.getDate());
        }

        public void setItem(Collection<NoteClass> notes) {
            noteList.addAll(notes);
            notifyDataSetChanged();
        }

        public TestViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            type = itemView.findViewById(R.id.type);
            textInfo = itemView.findViewById(R.id.textInfo);
            checkBox = itemView.findViewById(R.id.checkbox);
            date = itemView.findViewById(R.id.date);


        }


    }


    @Override
    public TestAdapter.TestViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_element, parent, false);

        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TestAdapter.TestViewHolder holder, int position) {

        holder.setNote(noteList.get(position));

    }

    @Override
    public int getItemCount() {
        return noteList.size();
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
