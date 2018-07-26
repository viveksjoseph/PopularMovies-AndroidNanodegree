package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.Movies.MovieResponse;
import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Data.Videos.VideoResponse;
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

    public static VideoResponse parseVideoResponseJson(String json) throws  JsonParseException{
        Gson videoGson = new GsonBuilder().create();
        return videoGson.fromJson(json, VideoResponse.class);
    }

    public static ReviewResponse parseReviewResponseJson(String json) throws  JsonParseException{
        Gson reviewGson = new GsonBuilder().create();
        return reviewGson.fromJson(json, ReviewResponse.class);
    }
}
