package com.example.shoplist.Classes;

public class User {
    private int id;
    private String name;
    private int madeCounter;
    private int checkCounter;

    public User(int id, String name, int madeCounter, int checkCounter) {
        this.id = id;
        this.name = name;
        this.madeCounter = madeCounter;
        this.checkCounter = checkCounter;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
