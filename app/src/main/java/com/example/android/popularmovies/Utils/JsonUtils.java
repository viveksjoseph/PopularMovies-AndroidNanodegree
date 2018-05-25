package com.example.android.popularmovies.Utils;

import android.util.Log;

import com.example.android.popularmovies.Data.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JsonUtils {

    private static final String JSON_RESULTS = "results";
    private static final String JSON_VOTE_COUNT = "vote_count";
    private static final String JSON_ID = "id";
    private static final String JSON_VIDEO = "video";
    private static final String JSON_VOTE_AVERAGE = "vote_average";
    private static final String JSON_TITLE = "title";
    private static final String JSON_POPULARITY = "popularity";
    private static final String JSON_POSTER_PATH = "poster_path";
    private static final String JSON_ORG_LANGUAGE = "original_language";
    private static final String JSON_ORG_TITLE = "original_title";
    private static final String JSON_GENRE_IDS = "genre_ids";
    private static final String JSON_BACKDROP_PATH = "backdrop_path";
    private static final String JSON_ADULT = "adult";
    private static final String JSON_OVERVIEW = "overview";
    private static final String JSON_RELEASE_DATE = "release_date";

    private static final String IMAGE_INITIAL_PATH = "http://image.tmdb.org/t/p/original/";

    public static MovieDetails parseEachMovieDetails(JSONObject movieObject) {

        MovieDetails movieDetails = new MovieDetails();
        try {
            if (movieObject.has(JSON_VOTE_COUNT)) {
                movieDetails.setVoteCount(movieObject.getInt(JSON_VOTE_COUNT));
            }
            if (movieObject.has(JSON_ID)) {
                movieDetails.setMovieId(movieObject.getLong(JSON_ID));
            }
            if (movieObject.has(JSON_VIDEO)) {
                movieDetails.setAdultMovie(movieObject.getBoolean(JSON_VIDEO));
            }
            if (movieObject.has(JSON_VOTE_AVERAGE)) {
                movieDetails.setVoteAverage(movieObject.getDouble(JSON_VOTE_AVERAGE));
            }
            if (movieObject.has(JSON_TITLE)) {
                movieDetails.setMovieTitle(movieObject.getString(JSON_TITLE));
            }
            if (movieObject.has(JSON_POPULARITY)) {
                movieDetails.setPopularity(movieObject.getDouble(JSON_POPULARITY));
            }
            if (movieObject.has(JSON_POSTER_PATH)) {
                movieDetails.setPosterPath(IMAGE_INITIAL_PATH + movieObject.getString(JSON_POSTER_PATH));
            }
            if (movieObject.has(JSON_ORG_LANGUAGE)) {
                movieDetails.setOriginalLanguage(movieObject.getString(JSON_ORG_LANGUAGE));
            }
            if (movieObject.has(JSON_ORG_TITLE)) {
                movieDetails.setOriginalTitle(JSON_ORG_TITLE);
            }
            if (movieObject.has(JSON_GENRE_IDS)) {
                JSONArray genreIdArray = movieObject.getJSONArray(JSON_GENRE_IDS);
                ArrayList<Integer> genreList = new ArrayList<>();
                for (int index = 0; index < genreIdArray.length(); index++) {
                    genreList.add(Integer.valueOf(genreIdArray.getInt(index)));
                }
            }
            if (movieObject.has(JSON_BACKDROP_PATH)) {
                movieDetails.setBackdropPath(IMAGE_INITIAL_PATH + movieObject.getString(JSON_BACKDROP_PATH));
            }
            if (movieObject.has(JSON_ADULT)) {
                movieDetails.setAdultMovie(movieObject.getBoolean(JSON_ADULT));
            }
            if (movieObject.has(JSON_OVERVIEW)) {
                movieDetails.setOverview(movieObject.getString(JSON_OVERVIEW));
            }
            if (movieObject.has(JSON_RELEASE_DATE)) {
                Date movieDate = null;
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    movieDate = dateFormat.parse(movieObject.getString(JSON_RELEASE_DATE));
                } catch (ParseException e) {
                    Log.d("JsonUtils", "Unable to parse date: " + e.getMessage());
                } finally {
                    movieDetails.setReleaseDate(movieDate);
                }
            }


        } catch (JSONException e) {
            Log.d("JsonUtils", "Unable to parse results object: " + e.getMessage());
        }

        return movieDetails;
    }

    public static ArrayList<MovieDetails> parseMovieResponseJson(String json) {

        ArrayList<MovieDetails> moviesArray = null;
        try {
            JSONObject parsedData = new JSONObject(json);

            if (parsedData.has(JSON_RESULTS)) {
                JSONArray resultJsonArray = parsedData.getJSONArray(JSON_RESULTS);
                moviesArray = new ArrayList<>();

                for (int index = 0; index < resultJsonArray.length(); index++) {
                    MovieDetails tempDetails = parseEachMovieDetails((JSONObject) resultJsonArray.get(index));

                    if (tempDetails != null) {
                        moviesArray.add(tempDetails);
                    }
                }
            } else {
                Log.d("JsonUtils", "The returned response contains errors, or do not contain results");
            }

        } catch (JSONException e) {
            Log.d("JSONUtils", "Unable to parse Movie JSON: " + e.getMessage());
        }
        return moviesArray;
    }
}
