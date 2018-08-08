package com.example.android.popularmovies.Database.TypeConvertors;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConvertor {

    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return (timeStamp == null) ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return (date == null) ? null : date.getTime();
    }
}
