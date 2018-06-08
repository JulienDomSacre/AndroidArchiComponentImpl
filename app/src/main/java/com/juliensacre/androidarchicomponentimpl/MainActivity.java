package com.juliensacre.androidarchicomponentimpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juliensacre.androidarchicomponentimpl.data.Note;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(createNotes());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(v -> {
            if(!editText.getText().toString().equals("")) {
                adapter.addItem(new Note(editText.getText().toString(), Calendar.getInstance()));
                editText.setText("");
            }else
                Toast.makeText(this,"Empty text",Toast.LENGTH_SHORT).show();
        });
    }

    private List<Note> createNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        for (int i=1; i<=1000; i++){
            notes.add(new Note("message create number: "+(i),Calendar.getInstance()));
        }
        return notes;
    }
}
