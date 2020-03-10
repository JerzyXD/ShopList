package com.example.shoplist.Classes;

public class User {
    private int id;
    private String username;
    private int madeCounter;
    private int checkCounter;

    public void User(int id, String username, int madeCounter, int checkCounter) {
        this.id = id;
        this.username = username;
        this.madeCounter = madeCounter;
        this.checkCounter = checkCounter;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMadeCounter() {
        return madeCounter;
    }

    public void setMadeCounter(int madeCounter) {
        this.madeCounter = madeCounter;
    }

    public int getCheckCounter() {
        return checkCounter;
    }

    public void setCheckCounter(int checkCounter) {
        this.checkCounter = checkCounter;
    }
}
