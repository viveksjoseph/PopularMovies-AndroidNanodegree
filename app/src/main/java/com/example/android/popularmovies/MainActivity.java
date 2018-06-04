package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.MovieDetailsAdapter;
import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.loading_bar)
    ProgressBar mLoadingBar;
    @BindView(R.id.movies_gridview)
    GridView mGridView;
    @BindView(R.id.loading_failed_tv)
    TextView mLoadingFailedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());

        makeMovieQuery();
    }

    private void makeMovieQuery() {
        URL movieQueryUrl = null;

        showLoadingProgress();

        try {
            movieQueryUrl = NetworkUtils.BuildQueryURL(this,
                    MovieData.getInstance().getCurrentGridArrangement().getString());
        } catch (MalformedURLException e) {
            Log.d("Main Activity", "creating movieQueryUrl failed: " + e.getMessage());
        } finally {
            if (movieQueryUrl != null) {
                new movieQueryTask().execute(movieQueryUrl);
            }
        }
    }

    private void showLoadingProgress() {
        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        if (mGridView != null) {
            mGridView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setVisibility(View.INVISIBLE);
        }
    }

    private void showGridContent() {
        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mGridView != null) {
            mGridView.setVisibility(View.VISIBLE);
        }

        if (mLoadingFailedTv != null) {
            mLoadingFailedTv.setVisibility(View.INVISIBLE);
        }
    }

    private void showLoadingFailed() {
        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mGridView != null) {
            mGridView.setVisibility(View.INVISIBLE);
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

    public class movieQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String movieQueryResults = null;
            try {
                movieQueryResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                Log.d("Main Activity", "getResponseFromHttpUrl failed: " + e.getMessage());
            }
            return movieQueryResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null && !s.equals("")) {
                try {
                    MovieData.getInstance().setMovieDetailsArray(JsonUtils.parseMovieResponseJson(s));
                } catch (Exception e) {
                    showLoadingFailed();
                }

                if (MovieData.getInstance().getMovieDetailsArray() == null) {
                    Log.d("Main Activity", "movieDetailsArray not initialized");
                    return;
                }

                if (mGridView == null) {
                    Log.d("Main Activity", "GridView not initialized");
                    return;
                }
                MovieDetailsAdapter adapter = new MovieDetailsAdapter(getApplicationContext(),
                        MovieData.getInstance().getMovieDetailsArray().getResultsArray());

                mGridView.setAdapter(adapter);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LaunchMovieDetailActivity(position);
                    }
                });
                setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());
                showGridContent();
            } else {
                showLoadingFailed();
            }
        }

        private void LaunchMovieDetailActivity(int position) {
            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_EXTRA_POSITION, position);
            startActivity(intent);
        }
    }
}