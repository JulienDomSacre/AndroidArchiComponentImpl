package com.juliensacre.androidarchicomponentimpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juliensacre.androidarchicomponentimpl.data.Note;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteDataSource;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteRepository;
import com.juliensacre.androidarchicomponentimpl.data.source.local.AppDatabase;
import com.juliensacre.androidarchicomponentimpl.data.source.local.NoteLocalDataSource;
import com.juliensacre.androidarchicomponentimpl.data.source.web.NoteRemoteDataSource;
import com.juliensacre.androidarchicomponentimpl.utils.AppExecutors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.editText)
    EditText editText;

    private NoteRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        AppDatabase database = AppDatabase.getInstance(getApplicationContext());
        repository = NoteRepository.getInstance(NoteRemoteDataSource.getInstance(),
                NoteLocalDataSource.getInstance(new AppExecutors(), database.noteDao()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getNotes());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(v -> {
            if(!editText.getText().toString().equals("")) {
                Note note = new Note(editText.getText().toString(), Calendar.getInstance());
                repository.saveNotes(note);
                adapter.addItem(note);
                editText.setText("");
            }else
                Toast.makeText(this,"Empty text",Toast.LENGTH_SHORT).show();
        });
    }

    private List<Note> getNotes(){
        final List<Note> notes = new ArrayList<>();
        repository.getNotes(new NoteDataSource.LoadNotesCallback() {
            @Override
            public void onTasksLoaded(List<Note> tasks) {
                notes.addAll(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getApplicationContext(),"No note saved",Toast.LENGTH_SHORT).show();
            }
        });
        return notes;
    }
}
