package com.example.android.popularmovies.ViewModels.ViewModelFactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Database.AppDatabase;
import com.example.android.popularmovies.ViewModels.MovieForIdViewModel;

public class MovieForIdViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase mAppDb;
    private final long mMovieId;

    public MovieForIdViewModelFactory(AppDatabase appDb, long movieId) {
        mAppDb = appDb;
        mMovieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieForIdViewModel(mAppDb, mMovieId);
    }
}
