package com.example.keshar.architecture_example_mvvm.RoomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {NoteEntity.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDao getNoteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            instance=Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;
        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao=db.getNoteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new NoteEntity("Title 1","Description 1",1));
            noteDao.insert(new NoteEntity("Title 2","Description 2",2));
            noteDao.insert(new NoteEntity("Title 3","Description 3",3));
            return null;
        }
    }

}
