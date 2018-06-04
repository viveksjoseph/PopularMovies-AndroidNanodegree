package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.MovieResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    public static MovieResponse parseMovieResponseJson(String json) throws Exception {
        Gson movieGson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd")
                .create();
        MovieResponse moviesArray = movieGson.fromJson(json, MovieResponse.class);
        return moviesArray;

    }
}
