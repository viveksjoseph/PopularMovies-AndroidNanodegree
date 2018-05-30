package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovies.Adapters.MovieDetailsAdapter;
import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(MovieData.getInstance().getCurrentGridArrangement().getCaption());
        makeMovieQuery();
    }

    private void makeMovieQuery() {
        URL movieQueryUrl = null;

        try {
            movieQueryUrl = NetworkUtils.BuildQueryURL(this,
                    MovieData.getInstance().getCurrentGridArrangement().toString());
        } catch (MalformedURLException e) {
            Log.d("Main Activity", "creating movieQueryUrl failed: " + e.getMessage());
        } finally {
            if (movieQueryUrl != null) {
                new movieQueryTask().execute(movieQueryUrl);
            }
        }
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
                MovieData.getInstance().movieDetailsArray = JsonUtils.parseMovieResponseJson(s);

                if (MovieData.getInstance().movieDetailsArray == null) {
                    return;
                }
                MovieDetailsAdapter adapter = new MovieDetailsAdapter(getApplicationContext(),
                        MovieData.getInstance().movieDetailsArray);

                GridView gridView = findViewById(R.id.movies_gridview);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LaunchMovieDetailActivity(position);
                    }
                });
            }
        }

        private void LaunchMovieDetailActivity(int position) {
            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_EXTRA_POSITION, position);
            startActivity(intent);
        }
    }
}