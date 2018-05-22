package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayTextView = findViewById(R.id.movie_response);
        makeMovieQuery();
    }

    private void makeMovieQuery() {
        String movieQuery = getString(R.string.moviedb_api);

        URL movieQueryUrl = null;
        try {
            movieQueryUrl = new URL(movieQuery);
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
            if (s != null && !s.equals("")) {
                mDisplayTextView.setText(s);
            }
            super.onPostExecute(s);
        }
    }
}