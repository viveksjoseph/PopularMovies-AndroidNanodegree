package com.example.android.popularmovies.Data;

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
    private GridArrangement currentGridArrangement;

    private MovieResponse movieDetailsArray = null;

    private MovieData() {
        currentGridArrangement = GridArrangement.ARRANGEMENT_MOST_POPULAR;
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

    public MovieResponse getMovieDetailsArray() {
        return this.movieDetailsArray;
    }

    public void setMovieDetailsArray(MovieResponse movieDetailsArray) {
        this.movieDetailsArray = movieDetailsArray;
    }
}
