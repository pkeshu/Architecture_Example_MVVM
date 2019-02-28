package com.example.keshar.architecture_example_mvvm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.keshar.architecture_example_mvvm.R;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE=
            "com.example.keshar.architecture_example_mvvm.Activities.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION=
            "com.example.keshar.architecture_example_mvvm.Activities.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.example.keshar.architecture_example_mvvm.Activities.EXTRA_PRIORITY";
    private EditText edtTextTitle;
    private EditText edtTexrDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        edtTextTitle = findViewById(R.id.edt_txt_title);
        edtTexrDescription = findViewById(R.id.edt_txt_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(9);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");


    }
    private void saveNote(){
        String title=edtTextTitle.getText().toString();
        String description=edtTexrDescription.getText().toString();
        int priority=numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data=new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
