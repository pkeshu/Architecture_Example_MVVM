package com.example.keshar.architecture_example_mvvm.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.getNoteDao();
        allNotes=noteDao.getAllNotes();
    }

    public void insert(NoteEntity noteEntity){
        new InsertNoteAsynkTask(noteDao).execute(noteEntity);

    }
    public void update(NoteEntity noteEntity){

        new UpdateNoteAsynkTask(noteDao).execute(noteEntity);

    }
    public void delete(NoteEntity noteEntity){
        new DeleteNoteAsynkTask(noteDao).execute(noteEntity);

    }
    public void deleteAllNotes(){
        new DeleteAllNotesNoteAsynkTask(noteDao).execute();

    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public static class InsertNoteAsynkTask extends AsyncTask<NoteEntity,Void,Void>{
        private NoteDao noteDao;

        private InsertNoteAsynkTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insert(noteEntities[0]);
            return null;
        }
    }
    public static class UpdateNoteAsynkTask extends AsyncTask<NoteEntity,Void,Void>{
        private NoteDao noteDao;

        private UpdateNoteAsynkTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.update(noteEntities[0]);
            return null;
        }
    }

    public static class DeleteNoteAsynkTask extends AsyncTask<NoteEntity,Void,Void>{
        private NoteDao noteDao;

        private DeleteNoteAsynkTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.delete(noteEntities[0]);
            return null;
        }
    }
    public static class DeleteAllNotesNoteAsynkTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private DeleteAllNotesNoteAsynkTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Void... Void) {
            noteDao.deleteAllNote();
            return null;
        }
    }

}
