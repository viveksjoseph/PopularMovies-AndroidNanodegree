package com.example.android.popularmovies.Data;

import java.util.ArrayList;

public class MovieData {

    public enum GridArrangement {
        ARRANGEMENT_MOST_POPULAR{
            @Override
            public String toString() {
                return "popular";
            }
        },
        ARRANGEMENT_HIGHEST_RATED{
            @Override
            public String toString() {
                return "top_rated";
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

    public void setCurrentGridArrangement(GridArrangement currentArrangement){
        this.currentGridArrangement = currentArrangement;
    }

    public GridArrangement getCurrentGridArrangement(){
        return this.currentGridArrangement;
    }

    public static MovieData getInstance() {
        if (instance == null) {
            instance = new MovieData();
        }
        return instance;
    }
}
