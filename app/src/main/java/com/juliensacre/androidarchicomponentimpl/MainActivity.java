package com.juliensacre.androidarchicomponentimpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Calendar calendar = Calendar.getInstance();
                adapter.addItem(new Note(editText.getText().toString(), calendar.getTimeInMillis()));
                editText.setText("");
            }else
                Toast.makeText(this,"Empty text",Toast.LENGTH_SHORT).show();
        });
    }

    private List<Note> createNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i=0; i<1000; i++){
            notes.add(new Note("message create number: "+(i+1),calendar.getTimeInMillis()));
        }
        return notes;
    }
}
