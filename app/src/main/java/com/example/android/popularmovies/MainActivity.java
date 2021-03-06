package com.example.android.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.MovieDetailsAdapter;
import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.Data.Movies.MovieResponse;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.ViewModels.AllMoviesViewModel;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<String> {

    private final static int NUM_COL_FOR_GRID_PORTRAIT = 3;
    private final static int NUM_COL_FOR_GRID_LANDSCAPE = 4;

    private final static int MOVIE_QUERY_LOADER_POPULAR = 2334;
    private final static int MOVIE_QUERY_LOADER_HIGHRATED = 3445;
    private final static String MOVIE_POPULAR_QUERY_URL_EXTRA = "moviePopularUrlExtra";
    private final static String MOVIE_HIGH_RATED_QUERY_URL_EXTRA = "movieHighRatedUrlExtra";

    @BindView(R.id.loading_bar)
    ProgressBar mLoadingBar;
    @BindView(R.id.movies_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_failed_tv)
    TextView mLoadingFailedTv;

    MovieDetailsAdapter mMovieAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());

        LoadRecyclerView();
        makeMovieQuery();
    }

    private void LoadRecyclerView() {
        if (mRecyclerView == null) {
            Log.d("Main Activity", "Recycler View not initialized");
            showLoadingFailed(R.string.app_error_text);
            return;
        }

        mMovieAdapter = new MovieDetailsAdapter();
        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? NUM_COL_FOR_GRID_LANDSCAPE : NUM_COL_FOR_GRID_PORTRAIT;
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    private void makeMovieQuery() {
        showLoadingProgress();

        String movieQueryUrlPopular = NetworkUtils.BuildQueryURL(this,
                MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR.getString());
        String movieQueryUrlHighRated = NetworkUtils.BuildQueryURL(this,
                MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED.getString());

        if (movieQueryUrlPopular == null || movieQueryUrlPopular.isEmpty() ||
                movieQueryUrlHighRated == null || movieQueryUrlHighRated.isEmpty()) {
            showLoadingFromServerFailed();
            return;
        }

        if (MovieData.getInstance().getMovieDetailsArray(MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR) == null ||
                MovieData.getInstance().getMovieDetailsArray(MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED) == null) {

            Bundle loaderQueryBundle = new Bundle();
            loaderQueryBundle.putString(MOVIE_POPULAR_QUERY_URL_EXTRA, movieQueryUrlPopular);
            loaderQueryBundle.putString(MOVIE_HIGH_RATED_QUERY_URL_EXTRA, movieQueryUrlHighRated);

            getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER_POPULAR,
                    loaderQueryBundle, MainActivity.this);

            getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER_HIGHRATED,
                    loaderQueryBundle, MainActivity.this);

        } else {
            SetMovieResults();
        }
    }

    private void showLoadingProgress() {
        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setVisibility(View.INVISIBLE);
        }
    }

    private void showGridContent() {

        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setVisibility(View.INVISIBLE);
        }
    }

    private void showLoadingFromServerFailed() {
        if (MovieData.getInstance().getCurrentGridArrangement()
                == MovieData.GridArrangement.ARRANGEMENT_FAVORITES) {
            // favorites is loaded from cache. No server involved
            return;
        }

        showLoadingFailed(R.string.loading_failed_text);
    }

    private void showLoadingFailed(int failTextId) {

        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setText(getText(failTextId));
            mLoadingFailedTv.setVisibility(View.VISIBLE);
        }
    }

    private void LoadFavoriteMovies() {

        AllMoviesViewModel viewModel = ViewModelProviders.of(this).get(AllMoviesViewModel.class);
        viewModel.getMovieDetailsLiveData().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable List<MovieDetails> movieDetailsList) {
                MovieResponse movieResponse = new MovieResponse(movieDetailsList);
                MovieData.getInstance().setMovieDetailsArray(
                        MovieData.GridArrangement.ARRANGEMENT_FAVORITES,
                        movieResponse);
                SetMovieResults();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_order_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.movieSort);
        Spinner menuSpinner = (Spinner) menuItem.getActionView();
        menuSpinner.setSelection(MovieData.getInstance().getCurrentGridArrangement().getPosition());
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (MovieData.getInstance().getCurrentGridArrangement().getPosition() == position) {
                    //User selected a sort order that is already on display.
                    return;
                }

                if (position == MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR.getPosition()) {

                    MovieData.getInstance().setCurrentGridArrangement(MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR);

                    //Refresh Grid View with new Data
                    makeMovieQuery();

                } else if (position == MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED.getPosition()) {

                    MovieData.getInstance().setCurrentGridArrangement(MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED);

                    //Refresh Grid View with new Data
                    makeMovieQuery();

                } else if (position == MovieData.GridArrangement.ARRANGEMENT_FAVORITES.getPosition()) {

                    MovieData.getInstance().setCurrentGridArrangement(MovieData.GridArrangement.ARRANGEMENT_FAVORITES);

                    //Refresh Grid View with new Data
                    LoadFavoriteMovies();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing to do here
            }
        });

        return true;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(final int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String searchQueryString = null;
                if (id == MOVIE_QUERY_LOADER_POPULAR) {
                    searchQueryString = args.getString(MOVIE_POPULAR_QUERY_URL_EXTRA);
                } else if (id == MOVIE_QUERY_LOADER_HIGHRATED) {
                    searchQueryString = args.getString(MOVIE_HIGH_RATED_QUERY_URL_EXTRA);
                }

                if (searchQueryString == null || searchQueryString.isEmpty()) {
                    return null;
                }

                URL searchUrl;
                try {
                    searchUrl = NetworkUtils.BuildUrlFromString(searchQueryString);
                } catch (MalformedURLException e) {
                    Log.d("Main Activity", "Building URL from String failed: " + e.getMessage());
                    return null;
                }

                try {
                    return NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    Log.d("Main Activity", "getResponseFromHttpUrl failed: " + e.getLocalizedMessage());
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data == null || data.isEmpty()) {
            showLoadingFromServerFailed();
            return;
        }

        MovieResponse movieResponse = null;
        try {
            movieResponse = JsonUtils.parseMovieResponseJson(data);
        } catch (JsonParseException e) {
            Log.d("Main Activity", "response JSON could not be de-serialized: " + e.getMessage());
            showLoadingFromServerFailed();
            return;
        }

        if (loader.getId() == MOVIE_QUERY_LOADER_POPULAR) {

            MovieData.getInstance().setMovieDetailsArray(MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR,
                    movieResponse);

        } else if (loader.getId() == MOVIE_QUERY_LOADER_HIGHRATED) {

            MovieData.getInstance().setMovieDetailsArray(MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED,
                    movieResponse);

        }

        getSupportLoaderManager().destroyLoader(loader.getId());
        SetMovieResults();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private void SetMovieResults() {

        MovieData.GridArrangement currentArrangement = MovieData.getInstance().getCurrentGridArrangement();
        MovieResponse response = MovieData.getInstance().getMovieDetailsArray(currentArrangement);

        if (response == null) {
            // data not loaded yet. Return
            return;
        }

        setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());

        if (response.getResultsArray().isEmpty() || response.getResultsArray() == null) {
            showLoadingFailed(R.string.no_result_found_text);
            return;
        }

        mMovieAdapter.setMovieData(response.getResultsArray());
        showGridContent();
    }
}