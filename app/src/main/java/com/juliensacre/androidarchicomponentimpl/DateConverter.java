package com.juliensacre.androidarchicomponentimpl;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class DateConverter {
    @TypeConverter
    public static Calendar tocalendar (Long timestamp){
        Calendar calendar = Calendar.getInstance();
        if(timestamp != null) {
            calendar.setTimeInMillis(timestamp);
            return calendar;
        }else
            return calendar;
    }

    @TypeConverter
    public  static Long toLong (Calendar calendar){
        if(calendar == null)
            calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
