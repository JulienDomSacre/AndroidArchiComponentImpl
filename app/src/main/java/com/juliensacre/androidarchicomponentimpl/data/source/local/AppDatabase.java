package com.juliensacre.androidarchicomponentimpl.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.juliensacre.androidarchicomponentimpl.data.Note;

@Database(entities = {Note.class}, version=1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "notelist";
    private static AppDatabase sInstance;

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context){
        synchronized (LOCK){
            if(sInstance == null){
                Log.d(AppDatabase.class.getSimpleName(), "Create new db instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).build();
            }
            Log.d(AppDatabase.class.getSimpleName(), "Get the db instance");
            return sInstance;
        }
    }
}
