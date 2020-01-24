package com.example.shoplist.DataBase;

import com.example.shoplist.Classes.NoteClass;

import java.util.ArrayList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteClassDao {
    @Query("SELECT * FROM noteclass")
    ArrayList<NoteClass> getAll();

    @Query("SELECT * FROM noteclass WHERE id = :id")
    NoteClass getById(long id);

    @Insert
    void insert(NoteClass note);

    @Update
    void update(NoteClass note);

    @Delete
    void delete(NoteClass note);
}
