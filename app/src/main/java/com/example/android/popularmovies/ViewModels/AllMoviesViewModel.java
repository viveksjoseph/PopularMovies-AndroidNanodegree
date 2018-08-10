package com.example.android.popularmovies.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.Database.AppDatabase;

import java.util.List;

public class AllMoviesViewModel extends AndroidViewModel {

    private LiveData<List<MovieDetails>> mMovieDetailsLiveData;

    public AllMoviesViewModel(@NonNull Application app) {
        super(app);
        AppDatabase appDb = AppDatabase.getInstance(this.getApplication());
        mMovieDetailsLiveData = appDb.movieDetailsDAO().loadAllMovies();
    }

    public LiveData<List<MovieDetails>> getMovieDetailsLiveData() {
        return mMovieDetailsLiveData;
    }
}
