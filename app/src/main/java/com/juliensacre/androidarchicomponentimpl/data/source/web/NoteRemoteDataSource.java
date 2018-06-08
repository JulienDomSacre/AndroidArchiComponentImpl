package com.juliensacre.androidarchicomponentimpl.data.source.web;

import android.support.annotation.NonNull;

import com.juliensacre.androidarchicomponentimpl.data.Note;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteDataSource;

/**
 * Not use for the moment, but implemented for use the repository pattern
 */
public class NoteRemoteDataSource implements NoteDataSource {
    private static NoteRemoteDataSource INSTANCE;

    public static NoteRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private NoteRemoteDataSource() {}

    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {

    }

    @Override
    public void saveNotes(@NonNull Note note) {

    }
}
