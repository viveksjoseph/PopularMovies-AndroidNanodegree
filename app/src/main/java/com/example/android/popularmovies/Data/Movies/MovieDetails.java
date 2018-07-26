package com.example.android.popularmovies.Data.Movies;

import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Data.Videos.VideoResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MovieDetails {

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

    @SerializedName(JSON_VOTE_COUNT)
    int voteCount;
    @SerializedName(JSON_ID)
    long id;
    @SerializedName(JSON_VIDEO)
    boolean isVideoAvailable;
    @SerializedName(JSON_VOTE_AVERAGE)
    double voteAverage;
    @SerializedName(JSON_TITLE)
    String title;
    @SerializedName(JSON_POPULARITY)
    double popularity;
    @SerializedName(JSON_POSTER_PATH)
    String posterPath;
    @SerializedName(JSON_ORG_LANGUAGE)
    String originalLanguage;
    @SerializedName(JSON_ORG_TITLE)
    String originalTitle;
    @SerializedName(JSON_GENRE_IDS)
    List<Integer> genreIds;
    @SerializedName(JSON_BACKDROP_PATH)
    String backdropPath;
    @SerializedName(JSON_ADULT)
    boolean isAdultMovie;
    @SerializedName(JSON_OVERVIEW)
    String overview;
    @SerializedName(JSON_RELEASE_DATE)
    Date releaseDate;

    VideoResponse videoResponse;
    ReviewResponse reviewResponse;

    public MovieDetails() {
        voteCount = 0;
        id = 0;
        isVideoAvailable = false;
        voteAverage = 0;
        title = "";
        popularity = 0;
        posterPath = "";
        originalLanguage = "";
        originalTitle = "";
        genreIds = null;
        backdropPath = "";
        isAdultMovie = false;
        overview = "";
        releaseDate = null;

        videoResponse = null;
        reviewResponse = null;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public long getMovieId() {
        return id;
    }

    public boolean isVideoAvailable() {
        return isVideoAvailable;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getMovieTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return IMAGE_INITIAL_PATH + posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return IMAGE_INITIAL_PATH + backdropPath;
    }

    public boolean isAdultMovie() {
        return isAdultMovie;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public VideoResponse getVideoResponse() {
        return videoResponse;
    }

    public void setVideoResponse(VideoResponse videoResponse) {
        this.videoResponse = videoResponse;
    }

    public ReviewResponse getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(ReviewResponse reviewResponse) {
        this.reviewResponse = reviewResponse;
    }
}
