package com.juliensacre.androidarchicomponentimpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.juliensacre.androidarchicomponentimpl.data.Note;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteDataSource;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteRepository;
import com.juliensacre.androidarchicomponentimpl.data.source.local.AppDatabase;
import com.juliensacre.androidarchicomponentimpl.data.source.local.NoteLocalDataSource;
import com.juliensacre.androidarchicomponentimpl.data.source.web.NoteRemoteDataSource;
import com.juliensacre.androidarchicomponentimpl.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private NoteRepository repository;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(getClass().getSimpleName(), "onCreate main");

        ButterKnife.bind(this);

        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AppDatabase database = AppDatabase.getInstance(getApplicationContext());
        repository = NoteRepository.getInstance(NoteRemoteDataSource.getInstance(),
                NoteLocalDataSource.getInstance(new AppExecutors(), database.noteDao()));

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setItem(getNotes());
    }
}
