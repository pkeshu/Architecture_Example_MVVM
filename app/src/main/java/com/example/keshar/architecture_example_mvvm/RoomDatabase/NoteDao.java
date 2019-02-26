package com.example.keshar.architecture_example_mvvm.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(NoteEntity noteEntity);
    @Update
    void update(NoteEntity noteEntity);
    @Delete
    void delete(NoteEntity noteEntity);
    @Query("delete from note_table")
    void deleteAllNote();
    @Query("select * from note_table order by priority desc")
    LiveData<List<NoteEntity>> getAllNotes();

}
