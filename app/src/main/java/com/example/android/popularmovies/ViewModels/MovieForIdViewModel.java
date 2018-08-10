package com.example.android.popularmovies.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.Database.AppDatabase;

public class MovieForIdViewModel extends ViewModel {

    private LiveData<MovieDetails> mMovie;

    public MovieForIdViewModel(AppDatabase appDb, long movieId){

        mMovie = appDb.movieDetailsDAO().getMovieForId(movieId);
    }

    public LiveData<MovieDetails> getMovie() {
        return mMovie;
    }
}
