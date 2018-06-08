package com.example.android.popularmovies;

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
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<String> {

    private final static int NUM_COL_FOR_GRID = 3;
    private final static int MOVIE_QUERY_LOADER_POPULAR = 2334;
    private final static int MOVIE_QUERY_LOADER_HIGHRATED = 2345;
    private final static String MOVIE_QUERY_URL_EXTRA = "movieUrlExtra";

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
            showLoadingFailed();
            return;
        }

        mMovieAdapter = new MovieDetailsAdapter(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COL_FOR_GRID);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    private void makeMovieQuery() {
        showLoadingProgress();

        String movieQueryUrl = NetworkUtils.BuildQueryURL(this,
                MovieData.getInstance().getCurrentGridArrangement().getString());

        if (movieQueryUrl == null || movieQueryUrl.isEmpty()) {
            showLoadingFailed();
            return;
        }

        Bundle loaderQueryBundle = new Bundle();
        loaderQueryBundle.putString(MOVIE_QUERY_URL_EXTRA, movieQueryUrl);

        String currentArrangement = MovieData.getInstance().getCurrentGridArrangement().getString();
        if (currentArrangement.equals(MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED.getString())) {
            getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER_HIGHRATED, loaderQueryBundle, MainActivity.this);
        } else if (currentArrangement.equals(MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR.getString())) {
            getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER_POPULAR, loaderQueryBundle, MainActivity.this);
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

    private void showLoadingFailed() {
        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_order_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.movieSort);
        Spinner menuSpinner = (Spinner) menuItem.getActionView();
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (MovieData.getInstance().getCurrentGridArrangement().getPosition() == position) {
                    //User selected a sort order that is already on display.
                    return;
                }

                if (position == MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR.getPosition()) {

                    MovieData.getInstance().setCurrentGridArrangement(MovieData.GridArrangement.ARRANGEMENT_MOST_POPULAR);

                } else if (position == MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED.getPosition()) {

                    MovieData.getInstance().setCurrentGridArrangement(MovieData.GridArrangement.ARRANGEMENT_HIGHEST_RATED);
                }

                //Refresh Grid View with new Data
                makeMovieQuery();
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
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
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
                String searchQueryString = args.getString(MOVIE_QUERY_URL_EXTRA);

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
                    Log.d("Main Activity", "getResponseFromHttpUrl failed: " + e.getMessage());
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data == null || data.isEmpty()) {
            showLoadingFailed();
            return;
        }

        try {
            MovieData.getInstance().setMovieDetailsArray(JsonUtils.parseMovieResponseJson(data));
        } catch (JsonParseException e) {
            Log.d("Main Activity", "response JSON could not be de-serialized: " + e.getMessage());
            showLoadingFailed();
        }

        if (MovieData.getInstance().getMovieDetailsArray() == null) {
            Log.d("Main Activity", "movieDetailsArray not initialized");
            showLoadingFailed();
            return;
        }

        setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());
        mMovieAdapter.setMovieData(MovieData.getInstance().getMovieDetailsArray().getResultsArray());
        mMovieAdapter.notifyDataSetChanged();
        showGridContent();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}