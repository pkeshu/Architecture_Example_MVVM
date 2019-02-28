package com.example.keshar.architecture_example_mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.keshar.architecture_example_mvvm.Activities.AddNoteActivity;
import com.example.keshar.architecture_example_mvvm.Adapters.NoteAdapter;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;
import com.example.keshar.architecture_example_mvvm.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int ADD_NOTE_REQUEST=1;

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("ViewModelApp");
        btnAddNote=findViewById(R.id.btn_add);
        btnAddNote.setOnClickListener(this);
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
    private void addNote(){
        Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
        startActivityForResult(intent,ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);
            
            NoteEntity noteEntity=new NoteEntity(title,description,priority);
            noteViewModel.insert(noteEntity);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnAddNote))
            addNote();

    }
}
