package com.juliensacre.androidarchicomponentimpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juliensacre.androidarchicomponentimpl.data.Note;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteRepository;
import com.juliensacre.androidarchicomponentimpl.data.source.local.AppDatabase;
import com.juliensacre.androidarchicomponentimpl.data.source.local.NoteLocalDataSource;
import com.juliensacre.androidarchicomponentimpl.data.source.web.NoteRemoteDataSource;
import com.juliensacre.androidarchicomponentimpl.utils.AppExecutors;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.editText)
    EditText editText;

    private NoteRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);

        button.setOnClickListener(v -> {
            if (!editText.getText().toString().equals("")) {
                AppDatabase database = AppDatabase.getInstance(getApplicationContext());
                repository = NoteRepository.getInstance(NoteRemoteDataSource.getInstance(),
                        NoteLocalDataSource.getInstance(new AppExecutors(), database.noteDao()));

                Note note = new Note(editText.getText().toString(), Calendar.getInstance());
                repository.saveNotes(note);
                editText.setText("");
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Empty text", Toast.LENGTH_SHORT).show();
        });
    }
}
