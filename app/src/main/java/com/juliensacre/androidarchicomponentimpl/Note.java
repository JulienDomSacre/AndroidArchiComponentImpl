package com.juliensacre.androidarchicomponentimpl;

import java.util.Calendar;

public class Note {
    private String message;
    private long createdAt;

    public String getMessage() {
        return message;
    }

    public Note(String message, long createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);

        return calendar.getTime().toString();
    }
}
