package com.example.android.popularmovies.Data.Movies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Data.Videos.VideoResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity(tableName = "Movies")
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
    private int voteCount;

    @PrimaryKey(autoGenerate = false)
    @SerializedName(JSON_ID)
    private long movieId;

    @SerializedName(JSON_VIDEO)
    private boolean isVideoAvailable;

    @SerializedName(JSON_VOTE_AVERAGE)
    private double voteAverage;

    @SerializedName(JSON_TITLE)
    private String movieTitle;

    @SerializedName(JSON_POPULARITY)
    private double popularity;

    @SerializedName(JSON_POSTER_PATH)
    private String posterPath;

    @SerializedName(JSON_ORG_LANGUAGE)
    private String originalLanguage;

    @SerializedName(JSON_ORG_TITLE)
    private String originalTitle;

    @SerializedName(JSON_GENRE_IDS)
    private List<Integer> genreIds;

    @SerializedName(JSON_BACKDROP_PATH)
    private String backdropPath;

    @SerializedName(JSON_ADULT)
    private boolean isAdultMovie;

    @SerializedName(JSON_OVERVIEW)
    private String overview;

    @SerializedName(JSON_RELEASE_DATE)
    private Date releaseDate;

    private VideoResponse videoResponse;
    private ReviewResponse reviewResponse;

    @Ignore
    public MovieDetails() {
        voteCount = 0;
        movieId = 0;
        isVideoAvailable = false;
        voteAverage = 0;
        movieTitle = "";
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

    public MovieDetails(int voteCount, long movieId,
                        boolean isVideoAvailable, boolean isAdultMovie,
                        double voteAverage, String movieTitle,
                        double popularity, String posterPath,
                        String originalLanguage, String originalTitle,
                        List<Integer> genreIds, String backdropPath,
                        String overview, Date releaseDate,
                        VideoResponse videoResponse, ReviewResponse reviewResponse) {
        this.voteCount = voteCount;
        this.movieId = movieId;
        this.isVideoAvailable = isVideoAvailable;
        this.voteAverage = voteAverage;
        this.movieTitle = movieTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.isAdultMovie = isAdultMovie;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.videoResponse = videoResponse;
        this.reviewResponse = reviewResponse;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }


    public boolean isVideoAvailable() {
        return isVideoAvailable;
    }

    public void setVideoAvailable(boolean videoAvailable) {
        isVideoAvailable = videoAvailable;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getAppendedPosterPath(){
        return IMAGE_INITIAL_PATH + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }


    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }


    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getAppendedBackdropPath() {
        return IMAGE_INITIAL_PATH + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdultMovie() {
        return isAdultMovie;
    }


    public void setAdultMovie(boolean adultMovie) {
        isAdultMovie = adultMovie;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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
