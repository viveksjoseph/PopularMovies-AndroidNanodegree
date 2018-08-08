package com.example.android.popularmovies.Database.TypeConvertors;

import android.arch.persistence.room.TypeConverter;

import com.example.android.popularmovies.Data.Videos.VideoResponse;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.google.gson.Gson;

public class VideosResponseConvertor {

    @TypeConverter
    public static VideoResponse stringToVideosList(String jsonData) {
        if (jsonData == null) {
            return null;
        }

        return JsonUtils.parseVideoResponseJson(jsonData);
    }

    @TypeConverter
    public static String VideosListToString(VideoResponse videoResponse) {
        Gson gson = new Gson();
        return gson.toJson(videoResponse);
    }
}
