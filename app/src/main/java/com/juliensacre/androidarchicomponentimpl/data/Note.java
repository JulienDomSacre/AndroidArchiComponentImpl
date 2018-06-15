package com.juliensacre.androidarchicomponentimpl.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.juliensacre.androidarchicomponentimpl.utils.DateConverter;

import java.util.Calendar;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String message;
    @TypeConverters(DateConverter.class)
    private Calendar createdAt;

    public String getMessage() {
        return message;
    }

    public Note(String message, Calendar createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    @Ignore
    public Note(int id, String message, Calendar createdAt) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getCreatedAtInString() {
        return createdAt.getTime().toString();
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
