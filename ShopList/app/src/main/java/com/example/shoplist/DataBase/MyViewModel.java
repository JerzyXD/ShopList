package com.example.shoplist.DataBase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoplist.Classes.NoteClass;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<NoteClass>> allNotes;
    private LiveData<List<NoteClass>> sortedByText;
    public MyViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
        sortedByText = noteRepository.getSortedByTextNotes();
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

    public void deleteAllNotes() {noteRepository.deleteAllNotes();}

    public LiveData<List<NoteClass>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteClass>> getSortedByText() {
        return sortedByText;
    }
}
