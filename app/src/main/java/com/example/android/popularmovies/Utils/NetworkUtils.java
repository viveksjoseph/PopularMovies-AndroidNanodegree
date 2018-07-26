package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3";
    private static final String MOVIEDB_MOVIE_PATH = "movie";
    private static final String MOVIEDB_VIDEO_PATH = "videos";
    private static final String MOVIEDB_REVIEW_PATH = "reviews";
    private static final String MOVIEDB_APIKEY_QUERY = "api_key";

    // return entire result from HTTP response.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d("NetworkUtils", "Attempting to connect to url: " + url.toString());
        OkHttpClient urlConnectionClient = new OkHttpClient();
        Request connectionRequest = new Request.Builder()
                .url(url)
                .build();

        try (Response urlResponse = urlConnectionClient.newCall(connectionRequest).execute()) {
            if (!urlResponse.isSuccessful()) {
                throw new IOException("Unexpected Response: " + urlResponse);
            }
            return urlResponse.body().string();
        }
    }

    public static URL BuildUrlFromString(String s) throws MalformedURLException {
        URL url = new URL(s);
        return url;
    }

    public static String BuildQueryURL(Context context, String sortPreference) {
        Uri queryUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(MOVIEDB_MOVIE_PATH)
                .appendPath(sortPreference)
                .appendQueryParameter(MOVIEDB_APIKEY_QUERY, context.getString(R.string.moviedb_api))
                .build();

        return queryUri.toString();
    }

    public static String BuildVideoURL(Context context, long movieId){
        Uri queryUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(MOVIEDB_MOVIE_PATH)
                .appendPath(String.valueOf(movieId))
                .appendPath(MOVIEDB_VIDEO_PATH)
                .appendQueryParameter(MOVIEDB_APIKEY_QUERY, context.getString(R.string.moviedb_api))
                .build();

        return queryUri.toString();
    }

    public static String BuildReviewURL(Context context, long movieId){
        Uri queryUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(MOVIEDB_MOVIE_PATH)
                .appendPath(String.valueOf(movieId))
                .appendPath(MOVIEDB_REVIEW_PATH)
                .appendQueryParameter(MOVIEDB_APIKEY_QUERY, context.getString(R.string.moviedb_api))
                .build();

        return queryUri.toString();
    }
}