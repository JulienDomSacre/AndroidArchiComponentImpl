package com.juliensacre.androidarchicomponentimpl.data.source;

import android.support.annotation.NonNull;

import com.juliensacre.androidarchicomponentimpl.data.Note;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoteRepository implements NoteDataSource{
    private static NoteRepository INSTANCE = null;

    private final NoteDataSource mTasksRemoteDataSource;
    private final NoteDataSource mTasksLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, Note> mCachedNotes;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     *
     * In this demo the cache is never dirty, because it's dirty when the user want to refresh data manualy (pull to refresh by example)
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private NoteRepository(@NonNull NoteDataSource noteRemoteDataSource,
                            @NonNull NoteDataSource noteLocalDataSource) {
        mTasksRemoteDataSource = checkNotNull(noteRemoteDataSource);
        mTasksLocalDataSource = checkNotNull(noteLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param noteRemoteDataSource the backend data source
     * @param noteLocalDataSource  the device storage data source
     * @return the {@link NoteRepository} instance
     */
    public static NoteRepository getInstance(NoteDataSource noteRemoteDataSource,
                                             NoteDataSource noteLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(noteRemoteDataSource, noteLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(NoteDataSource, NoteDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedNotes != null && !mCacheIsDirty) {
            callback.onTasksLoaded(new ArrayList<>(mCachedNotes.values()));
            return;
        }

        /*if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getNotesFromRemoteDataSource(callback);
        } else {*/
        // Query the local storage if available. If not, query the network.
        mTasksLocalDataSource.getNotes(new LoadNotesCallback() {
            @Override
            public void onTasksLoaded(List<Note> tasks) {
                refreshCache(tasks);
                callback.onTasksLoaded(new ArrayList<>(mCachedNotes.values()));
            }

            @Override
            public void onDataNotAvailable() {
                //getNotesFromRemoteDataSource(callback);
            }
        });
    }

    @Override
    public void saveNotes(@NonNull Note note) {
        checkNotNull(note);
        mTasksRemoteDataSource.saveNotes(note); //save in the cloud
        mTasksLocalDataSource.saveNotes(note); //save on the db

        // Do in memory cache update to keep the app UI up to date
        if (mCachedNotes == null) {
            mCachedNotes = new LinkedHashMap<>();
        }
        mCachedNotes.put(note.getId(), note);
    }

    private void refreshCache(List<Note> notes) {
        if (mCachedNotes == null) {
            mCachedNotes = new LinkedHashMap<>();
        }
        mCachedNotes.clear();
        for (Note note : notes) {
            mCachedNotes.put(note.getId(), note);
        }
        mCacheIsDirty = false;
    }
}
