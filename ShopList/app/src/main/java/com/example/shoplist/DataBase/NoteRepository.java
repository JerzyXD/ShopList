package com.example.shoplist.DataBase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shoplist.Classes.NoteClass;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<NoteClass>> allNotes;
    private LiveData<List<NoteClass>> sortedByTextNotes;

    public NoteRepository(Application application) {
        AppDataBase dataBase = AppDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAll();
        sortedByTextNotes = noteDao.sortedByText();
    }

    public void insert(NoteClass note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(NoteClass note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(NoteClass note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<NoteClass>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteClass>> getSortedByTextNotes() {return sortedByTextNotes;}

    private static class InsertNoteAsyncTask extends AsyncTask<NoteClass, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteClass... noteClasses) {
            noteDao.insert(noteClasses[0]);
            return null;
        }

    }
    private static class UpdateNoteAsyncTask extends AsyncTask<NoteClass, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteClass... noteClasses) {
            noteDao.update(noteClasses[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteClass, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteClass... noteClasses) {
            noteDao.delete(noteClasses[0]);
            return null;
        }
    }
}
