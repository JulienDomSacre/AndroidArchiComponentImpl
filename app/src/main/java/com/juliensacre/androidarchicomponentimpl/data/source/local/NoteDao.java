package com.juliensacre.androidarchicomponentimpl.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.juliensacre.androidarchicomponentimpl.data.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insertNote (Note note);
}
