package com.example.android.popularmovies.Data;

import com.example.android.popularmovies.Data.Movies.MovieResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieData {

    public interface ArrangementInterface {
        String getCaption();

        int getPosition();

        String getString();
    }

    public enum GridArrangement implements ArrangementInterface {
        ARRANGEMENT_MOST_POPULAR {
            @Override
            public String getCaption() {
                return "Popular Movies";
            }

            @Override
            public int getPosition() {
                return 0;
            }

            @Override
            public String getString() {
                return "popular";
            }
        },
        ARRANGEMENT_HIGHEST_RATED {
            @Override
            public String getCaption() {
                return "Top Rated Movies";
            }

            @Override
            public int getPosition() {
                return 1;
            }

            @Override
            public String getString() {
                return "top_rated";
            }
        }
    }

    private static MovieData instance = null;

    private GridArrangement mCurrentGridArrangement;
    private List<MovieResponse> mMovieDetailsArray;

    public static MovieData getInstance() {
        if (instance == null) {
            instance = new MovieData();
        }
        return instance;
    }

    private MovieData() {
        this.mCurrentGridArrangement = GridArrangement.ARRANGEMENT_MOST_POPULAR;

        ReloadCachedResponses();
    }

    public void setCurrentGridArrangement(GridArrangement currentArrangement) {
        this.mCurrentGridArrangement = currentArrangement;
    }

    public GridArrangement getCurrentGridArrangement() {
        return this.mCurrentGridArrangement;
    }

    public MovieResponse getMovieDetailsArray(GridArrangement arrangement) {
        return this.mMovieDetailsArray.get(arrangement.getPosition());
    }

    public void setMovieDetailsArray(GridArrangement arrangement, MovieResponse movieDetailsArray) {
        this.mMovieDetailsArray.add(arrangement.getPosition(), movieDetailsArray);
    }

    public void ReloadCachedResponses() {
        this.mMovieDetailsArray = new ArrayList<>();
        for (int i = 0; i < GridArrangement.values().length; i++) {
            this.mMovieDetailsArray.add(null);
        }
    }

}
