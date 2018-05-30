package com.example.android.popularmovies.Data;

import java.util.ArrayList;

public class MovieData {

    public interface ArrangementInterface {
        String getCaption();
    }

    public enum GridArrangement implements ArrangementInterface {
        ARRANGEMENT_MOST_POPULAR {
            @Override
            public String toString() {
                return "popular";
            }

            public String getCaption(){
                return "Popular Movies";
            }
        },
        ARRANGEMENT_HIGHEST_RATED {
            @Override
            public String toString() {
                return "top_rated";
            }

            @Override
            public String getCaption() {
                return "TOP RATED MOVIES";
            }
        }
    }

    private static MovieData instance = null;
    private GridArrangement currentGridArrangement;

    public ArrayList<MovieDetails> movieDetailsArray;

    private MovieData() {
        currentGridArrangement = GridArrangement.ARRANGEMENT_MOST_POPULAR;

        movieDetailsArray = new ArrayList<>();
    }

    public void setCurrentGridArrangement(GridArrangement currentArrangement) {
        this.currentGridArrangement = currentArrangement;
    }

    public GridArrangement getCurrentGridArrangement() {
        return this.currentGridArrangement;
    }

    public static MovieData getInstance() {
        if (instance == null) {
            instance = new MovieData();
        }
        return instance;
    }
}
