package com.example.shoplist.Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

public class NoteClass {

    //Список типов покупок.
    public static final String[] TYPE_LIST_ITEM = {"Продукты", "Одежда", "Электроника", "Канцелярия","Медицина"
                                            , "Прочее"};

    private boolean checked;
    private String text;
    private String date;
    private String type;
    private int amount;
    private String units;

    public NoteClass (String text, String type, String units, int amount) {

        this.date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        this.text = text;
        this.type = type;
        this.checked = false;
        this.amount = amount;
        this.units = units;

    }


    public String getDate() {
        return date;
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


    public void setDate(String date) {
        this.date = date;
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
            int result = o1.getDate().compareTo(o2.getDate());
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
