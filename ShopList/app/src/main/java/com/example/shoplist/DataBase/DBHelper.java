package com.example.shoplist.DataBase;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.shoplist.Classes.NoteClass;

@Database(entities = { NoteClass.class }, version = 1, exportSchema = false)
public abstract class DBHelper extends RoomDatabase {

    public abstract NoteDao getDataDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
