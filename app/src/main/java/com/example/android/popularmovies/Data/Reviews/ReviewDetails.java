package com.example.android.popularmovies.Data.Reviews;

import com.google.gson.annotations.SerializedName;

public class ReviewDetails {

    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_URL = "url";

    @SerializedName(REVIEW_AUTHOR)
    String reviewAuthor;
    @SerializedName(REVIEW_CONTENT)
    String reviewContent;
    @SerializedName(REVIEW_ID)
    String reviewId;
    @SerializedName(REVIEW_URL)
    String reviewUrl;

    public ReviewDetails() {
        reviewAuthor = "No Reviews Found";
        reviewContent = "";
        reviewId = "";
        reviewUrl = "";
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}
