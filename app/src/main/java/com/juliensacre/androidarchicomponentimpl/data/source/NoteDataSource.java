package com.juliensacre.androidarchicomponentimpl.data.source;

import android.support.annotation.NonNull;

import com.juliensacre.androidarchicomponentimpl.data.Note;

import java.util.List;

/**
 * Main entry point for accessing tasks data.
 * <p>
 * For simplicity, only getTasks() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface NoteDataSource {
    //For this demo, don't use Rx or LiveData
    interface LoadNotesCallback {

        void onTasksLoaded(List<Note> tasks);

        void onDataNotAvailable();
    }
    void getNotes(@NonNull LoadNotesCallback callback);

    void saveNotes(@NonNull Note note);
}
