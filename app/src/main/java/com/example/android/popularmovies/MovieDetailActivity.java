package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Data.MovieDetails;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA_POSITION = "movie_extra_position";
    private static final int MOVIE_NOT_FOUND_POSITION = -1;

    @BindView(R.id.movie_banner_iv)
    ImageView mMovieBannerImage;
    @BindView(R.id.org_title_tv)
    TextView mMovieOrgTitle;
    @BindView(R.id.average_rating_tv)
    TextView mMovieAvgRating;
    @BindView(R.id.average_rating_rb)
    RatingBar mMovieRatingBar;
    @BindView(R.id.release_date_tv)
    TextView mMovieReleaseDate;
    @BindView(R.id.synopsis_tv)
    TextView movieSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d("MovieDetailActivity", "Intent not available");
            return;
        }

        int position = intent.getIntExtra(MOVIE_EXTRA_POSITION, MOVIE_NOT_FOUND_POSITION);

        if (position == MOVIE_NOT_FOUND_POSITION) {
            Log.d("MovieDetailActivity", "Movie data not available");
            finish();
            return;
        }

        MovieDetails movieDetail = MovieData.getInstance().movieDetailsArray.get(position);
        if (movieDetail == null) {
            Log.d("MovieDetailActivity", "Movie data could not be extracted");
            finish();
            return;
        }

        Picasso.with(this)
                .load(movieDetail.getBackdropPath())
                .into(mMovieBannerImage);
        setTitle(movieDetail.getMovieTitle());

        mMovieOrgTitle.setText(movieDetail.getOriginalTitle());

        String voteAvgText = String.valueOf(movieDetail.getVoteAverage()) +
                getString(R.string.avg_vote_out_of);
        mMovieAvgRating.setText(voteAvgText);

        mMovieRatingBar.setRating((float) movieDetail.getVoteAverage() / 2);

        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        mMovieReleaseDate.setText(dateFormat.format(movieDetail.getReleaseDate()));

        movieSynopsis.setText(movieDetail.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}