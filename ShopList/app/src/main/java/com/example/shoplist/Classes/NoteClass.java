package com.example.shoplist.Classes;

public class NoteClass {

    private boolean checked;
    private String text;
    private String date;
    private String type;

    public NoteClass (String text, String date, String type) {

        this.date = date;
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
