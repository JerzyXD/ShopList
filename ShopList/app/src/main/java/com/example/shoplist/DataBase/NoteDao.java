package com.example.shoplist.DataBase;

import com.example.shoplist.Classes.NoteClass;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note_table ")
    LiveData<List<NoteClass>> getAll();

    @Insert
    void insert(NoteClass note);

    @Update
    void update(NoteClass note);

    @Delete
    void delete(NoteClass note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
