package com.example.android.popularmovies.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    private static final String JSON_RESULTS = "results";

    @SerializedName(JSON_RESULTS)
    List<MovieDetails> resultsArray;

    public List<MovieDetails> getResultsArray() {
        return resultsArray;
    }

}
