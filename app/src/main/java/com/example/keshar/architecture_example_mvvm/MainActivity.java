package com.example.keshar.architecture_example_mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.keshar.architecture_example_mvvm.Adapters.NoteAdapter;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;
import com.example.keshar.architecture_example_mvvm.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("ViewModelApp");
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        noteAdapter=new NoteAdapter(this);
        noteViewModel=ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                //update your data
//                Toast.makeText(MainActivity.this, "onChanged.", Toast.LENGTH_SHORT).show();
                noteAdapter.setNotes(noteEntities);
            }
        });
        recyclerView.setAdapter(noteAdapter);
    }
}
