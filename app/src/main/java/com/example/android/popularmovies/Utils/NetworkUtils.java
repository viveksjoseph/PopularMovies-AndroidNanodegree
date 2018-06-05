package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;

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
    private static final String MOVIEDB_APIKEY_QUERY = "api_key";

    // return entire result from HTTP response.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
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

    public static URL BuildQueryURL(Context context, String sortPreference) throws MalformedURLException {
        Uri queryUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(MOVIEDB_MOVIE_PATH)
                .appendPath(sortPreference)
                .appendQueryParameter(MOVIEDB_APIKEY_QUERY, context.getString(R.string.moviedb_api))
                .build();

        URL movieQueryUrl = BuildUrlFromString(queryUri.toString());
        return movieQueryUrl;
    }
}