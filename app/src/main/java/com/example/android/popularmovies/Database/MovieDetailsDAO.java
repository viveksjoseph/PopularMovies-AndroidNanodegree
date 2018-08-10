package com.example.android.popularmovies.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popularmovies.Data.Movies.MovieDetails;

import java.util.List;

@Dao
public interface MovieDetailsDAO {

    @Query("SELECT * FROM Movies")
    LiveData<List<MovieDetails>> loadAllMovies();

    @Query("SELECT * FROM Movies WHERE movieId = :movieId")
    LiveData<MovieDetails> getMovieForId(long movieId);

    @Insert
    void insertMovie(MovieDetails movieDetails);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieDetails movieDetails);

    @Delete
    void deleteMovie(MovieDetails movieDetails);
}
