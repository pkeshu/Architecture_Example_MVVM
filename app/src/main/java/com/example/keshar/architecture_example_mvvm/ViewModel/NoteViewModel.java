package com.example.keshar.architecture_example_mvvm.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<NoteEntity>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository=new NoteRepository(application);
        allNotes=repository.getAllNotes();
    }


    public void insert(NoteEntity noteEntity){
        repository.insert(noteEntity);
    }
    public void update(NoteEntity noteEntity){
        repository.update(noteEntity);
    }
    public void delete(NoteEntity noteEntity){
        repository.delete(noteEntity);
    }
    public  void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }
}
