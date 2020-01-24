package com.example.shoplist.DataBase;

import com.example.shoplist.Classes.NoteClass;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteClass.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteClassDao noteDao();
}
