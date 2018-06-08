package com.juliensacre.androidarchicomponentimpl;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Note.class}, version=1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "notelist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(AppDatabase.class.getSimpleName(), "Create new db instance");
                sInstance = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME).build();
            }
        }
        Log.d(AppDatabase.class.getSimpleName(), "Get the db instance");
        return sInstance;
    }
}
