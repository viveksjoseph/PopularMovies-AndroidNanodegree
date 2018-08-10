package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.Movies.MovieResponse;
import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Data.Videos.VideoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JsonUtils {

    public static MovieResponse parseMovieResponseJson(String json) throws JsonParseException {

        // Fix for Parse failure when ReleaseDate is an empty string in JSON.
        // https://stackoverflow.com/a/9185368/9426223
        // Thank you Hauke Ingmar Schmidt

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.US);

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });

        Gson movieGson = gsonBuilder
                .setDateFormat("yyyy-mm-dd")
                .create();
        return movieGson.fromJson(json, MovieResponse.class);
    }

    public static VideoResponse parseVideoResponseJson(String json) throws JsonParseException {
        Gson videoGson = new GsonBuilder().create();
        return videoGson.fromJson(json, VideoResponse.class);
    }

    public static ReviewResponse parseReviewResponseJson(String json) throws JsonParseException {
        Gson reviewGson = new GsonBuilder().create();
        return reviewGson.fromJson(json, ReviewResponse.class);
    }
}
