package com.example.shoplist.DataBase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoplist.Classes.NoteClass;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<NoteClass>> allNotes;
    public MyViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insert (NoteClass note) {
        noteRepository.insert(note);
    }

    public void delete (NoteClass note) {
        noteRepository.delete(note);
    }

    public void update (NoteClass note) {
        noteRepository.update(note);
    }

    public LiveData<List<NoteClass>> getAllNotes() {
        return allNotes;
    }
}
