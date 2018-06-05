package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.MovieResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class JsonUtils {

    public static MovieResponse parseMovieResponseJson(String json) throws JsonParseException {
        Gson movieGson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd")
                .create();
        return movieGson.fromJson(json, MovieResponse.class);
    }
}
