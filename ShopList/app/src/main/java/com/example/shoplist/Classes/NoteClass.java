package com.example.shoplist.Classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteClass {

    //Список типов покупок.
    public static final String[] TYPE_LIST_ITEM = {"Продукты", "Одежда", "Электроника", "Канцелярия"
                                            , "Прочее"};

    private boolean checked;
    private String text;
    private String date;
    private String type;

    public NoteClass (String text, String type) {

        this.date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        this.text = text;
        this.type = type;
        this.checked = false;

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
}
