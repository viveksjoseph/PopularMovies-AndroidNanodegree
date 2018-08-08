package com.example.android.popularmovies.Database.TypeConvertors;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

// Fix for Storing a list of Objects in Room
// https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9
// Thank you Todd Cooke

public class IntegerListConvertor {

    @TypeConverter
    public static List<Integer> stringToIntegerList(String jsonData) {
        if (jsonData == null) {
            return Collections.emptyList();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(jsonData, type);
    }

    @TypeConverter
    public static String IntegerListToString(List<Integer> integerList) {
        Gson gson = new Gson();
        return gson.toJson(integerList);
    }
}
