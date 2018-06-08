package com.juliensacre.androidarchicomponentimpl;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String message;
    private long createdAt;

    public String getMessage() {
        return message;
    }

    public Note(String message, long createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    @Ignore
    public Note(int id, String message, long createdAt) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);

        return calendar.getTime().toString();
    }
}
