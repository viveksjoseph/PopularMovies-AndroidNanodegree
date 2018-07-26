package com.example.android.popularmovies.Data.Videos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    private static final String JSON_RESULTS = "results";

    @SerializedName(JSON_RESULTS)
    List<VideoDetails> resultsArray;

    public List<VideoDetails> getResultsArray() {
        return resultsArray;
    }
}
