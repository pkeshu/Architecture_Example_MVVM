package com.example.keshar.architecture_example_mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.keshar.architecture_example_mvvm.Activities.AddEditNoteActivity;
import com.example.keshar.architecture_example_mvvm.Adapters.NoteAdapter;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;
import com.example.keshar.architecture_example_mvvm.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NoteAdapter.OnItemClickListerner {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Simple Note");
        btnAddNote = findViewById(R.id.btn_add);
        btnAddNote.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                //update your data
//                Toast.makeText(MainActivity.this, "onChanged.", Toast.LENGTH_SHORT).show();
                noteAdapter.submitList(noteEntities);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListerner(this);

    }

    private void addNote() {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            NoteEntity noteEntity = new NoteEntity(title, description, priority);
            noteViewModel.insert(noteEntity);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } else if ((requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK)) {
            int id =data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(this, "Note cant be updated.", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            NoteEntity noteEntity = new NoteEntity(title, description, priority);
            noteEntity.setId(id);
            noteViewModel.update(noteEntity);
            Toast.makeText(this, "Note Updated.", Toast.LENGTH_SHORT).show();



        } else
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes are deleted.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAddNote))
            addNote();

    }

    @Override
    public void OnItemClick(NoteEntity noteEntity) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, noteEntity.getId());
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, noteEntity.getTitle());
        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, noteEntity.getDescription());
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, noteEntity.getPriority());
        startActivityForResult(intent, EDIT_NOTE_REQUEST);

    }
}
