package com.juliensacre.androidarchicomponentimpl.data.source.local;

import android.support.annotation.NonNull;

import com.juliensacre.androidarchicomponentimpl.data.Note;
import com.juliensacre.androidarchicomponentimpl.data.source.NoteDataSource;
import com.juliensacre.androidarchicomponentimpl.utils.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoteLocalDataSource implements NoteDataSource {
    private static volatile NoteLocalDataSource INSTANCE;

    private NoteDao mNoteDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private NoteLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull NoteDao noteDao) {
        mAppExecutors = appExecutors;
        mNoteDao = noteDao;
    }

    public static NoteLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull NoteDao noteDao) {
        if (INSTANCE == null) {
            synchronized (NoteLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteLocalDataSource(appExecutors, noteDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadNotesCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {
        Runnable runnable = ()->{
            final List<Note> tasks = mNoteDao.getAll();

            mAppExecutors.mainThread().execute(() -> {
                if (tasks.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable();
                } else {
                    callback.onTasksLoaded(tasks);
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveNotes(@NonNull Note note) {
        checkNotNull(note);
        Runnable saveRunnable = ()-> mNoteDao.insertNote(note);
        mAppExecutors.diskIO().execute(saveRunnable);
    }
}
