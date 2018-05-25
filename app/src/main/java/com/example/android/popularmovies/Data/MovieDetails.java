package com.example.android.popularmovies.Data;

import java.util.ArrayList;
import java.util.Date;

public class MovieDetails {

    int voteCount;
    long id;
    boolean isVideoAvailable;
    double voteAverage;
    String title;
    double popularity;
    String posterPath;
    String originalLanguage;
    String originalTitle;
    ArrayList<Integer> genreIds;
    String backdropPath;
    boolean isAdultMovie;
    String overview;
    Date releaseDate;

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
    }

    public MovieDetails(int voteCount, long id, boolean isVideoAvailable, double voteAverage, String title,
                        double popularity, String posterPath, String originalLanguage, String originalTitle,
                        ArrayList<Integer> genreIds, String backdropPath, boolean isAdultMovie, String overview,
                        Date releaseDate) {
        this.voteCount = voteCount;
        this.id = id;
        this.isVideoAvailable = isVideoAvailable;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.isAdultMovie = isAdultMovie;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getMovieId() {
        return id;
    }

    public void setMovieId(long id) {
        this.id = id;
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
        return title;
    }

    public void setMovieTitle(String title) {
        this.title = title;
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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
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
}
