package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;

import com.example.android.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3";
    private static final String MOVIEDB_MOVIE_PATH = "movie";
    private static final String MOVIEDB_APIKEY_QUERY = "api_key";

    // return entire result from HTTP response.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
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