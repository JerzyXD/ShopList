package com.example.shoplist.Classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteClass {

    //Список типов покупок.
    public static final String[] TYPE_LIST_ITEM = {"Продукты", "Одежда", "Электроника", "Канцелярия","Медицина"
                                            , "Прочее"};

    private boolean checked;
    private String text;
    private String data;
    private String type;
    private int amount;
    private String units;
    @NonNull
    @PrimaryKey
    private String id;

    public NoteClass (String text, String type, String units, int amount, String id) {

        this.data = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        this.text = text;
        this.type = type;
        this.checked = false;
        this.amount = amount;
        this.units = units;
        this.id = id;

    }


    public String getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public Boolean getChecked() {
        return checked;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnits() {
        return units;
    }

    public String getId() {
        return id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Компараторы для каждого типа сортировки: по типу, наименованию товара, дате, отмечен/неотмечен товар
     */

    public static class TypeComparator implements Comparator<NoteClass> {

        @Override
        public int compare(NoteClass o1, NoteClass o2) {
            int result  = o1.getType().compareTo(o2.getType());
            if (result == 0) {
                result = o1.getText().compareTo(o2.getText());
            }
            return result;
        }
    }

    public static class NameComparator implements Comparator<NoteClass> {

        @Override
        public int compare(NoteClass o1, NoteClass o2) {
            int result = o1.getText().compareTo(o2.getText());
            return result;
        }
    }

    public static class DateComparator implements Comparator<NoteClass> {

        @Override
        public int compare(NoteClass o1, NoteClass o2) {
            int result = o1.getData().compareTo(o2.getData());
            return result;
        }
    }

    public static class CheckComparator implements Comparator<NoteClass> {

        @Override
        public int compare(NoteClass o1, NoteClass o2) {
            int result = o1.getChecked().compareTo(o2.getChecked());
            if (result == 0) {
                result = o1.getText().compareTo(o2.getText());
            }
            return result;
        }
    }
}
