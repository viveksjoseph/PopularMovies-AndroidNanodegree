package com.example.android.popularmovies.Data.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    private static final String PAGE_NUMBER = "page";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";
    private static final String JSON_RESULTS = "results";

    @SerializedName(PAGE_NUMBER)
    int pageNumber;
    @SerializedName(TOTAL_RESULTS)
    int totalResults;
    @SerializedName(TOTAL_PAGES)
    int totalPages;
    @SerializedName(JSON_RESULTS)
    List<MovieDetails> resultsArray;

    public List<MovieDetails> getResultsArray() {
        return resultsArray;
    }

}
