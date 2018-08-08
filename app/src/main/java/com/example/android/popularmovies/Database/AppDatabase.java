package com.example.android.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.Database.TypeConvertors.DateConvertor;
import com.example.android.popularmovies.Database.TypeConvertors.IntegerListConvertor;
import com.example.android.popularmovies.Database.TypeConvertors.ReviewsResponseConvertor;
import com.example.android.popularmovies.Database.TypeConvertors.VideosResponseConvertor;

@Database(entities = MovieDetails.class, version = 1, exportSchema = false)
@TypeConverters({DateConvertor.class, IntegerListConvertor.class,
        VideosResponseConvertor.class, ReviewsResponseConvertor.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MoviesDatabase";
    private static AppDatabase pInstance;

    public static AppDatabase getInstance(Context context) {
        if (pInstance == null) {
            synchronized (LOCK) {
                pInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return pInstance;
    }

    public abstract MovieDetailsDAO movieDetailsDAO();
}
