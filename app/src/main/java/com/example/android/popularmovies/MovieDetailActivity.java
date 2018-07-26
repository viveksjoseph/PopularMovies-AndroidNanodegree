package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.MovieExpandableListAdapter;
import com.example.android.popularmovies.Adapters.MovieExpandableListAdapter.ExpandableListType;
import com.example.android.popularmovies.CustomViews.NonScrollableExpandableListView;
import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.Data.Reviews.ReviewDetails;
import com.example.android.popularmovies.Data.Reviews.ReviewResponse;
import com.example.android.popularmovies.Data.Videos.VideoDetails;
import com.example.android.popularmovies.Data.Videos.VideoResponse;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.google.gson.JsonParseException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements LoaderCallbacks<String> {

    public static final String MOVIE_EXTRA_POSITION = "movie_extra_position";
    private final static int VIDEO_QUERY_LOADER = 4556;
    private final static int REVIEW_QUERY_LOADER = 5667;
    private final static String VIDEO_QUERY_URL_EXTRA = "videoUrlExtra";
    private final static String REVIEW_QUERY_URL_EXTRA = "reviewUrlExtra";
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
    TextView mMovieSynopsis;
    @BindView(R.id.exp_list_video_view)
    NonScrollableExpandableListView mExpListVideoView;
    @BindView(R.id.exp_list_review_view)
    NonScrollableExpandableListView mExpListReviewView;
    @BindView(R.id.detail_progress_bar)
    ProgressBar mLoadingBar;

    private int mCurrentPosition = MOVIE_NOT_FOUND_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d("MovieDetailActivity", "Intent not available");
            return;
        }

        mCurrentPosition = intent.getIntExtra(MOVIE_EXTRA_POSITION, MOVIE_NOT_FOUND_POSITION);

        if (mCurrentPosition == MOVIE_NOT_FOUND_POSITION) {
            Log.d("MovieDetailActivity", "Movie data not available");
            finish();
            return;
        }

        MovieData.GridArrangement currentArrangement = MovieData.getInstance().getCurrentGridArrangement();
        MovieDetails movieDetail = MovieData.getInstance().getMovieDetailsArray(currentArrangement)
                .getResultsArray()
                .get(mCurrentPosition);
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

        mMovieSynopsis.setText(movieDetail.getOverview());

        Bundle loaderQueryBundle = new Bundle();

        if ((movieDetail.getVideoResponse() == null) || (movieDetail.getReviewResponse() == null)) {
            String videoQueryUrl = NetworkUtils.BuildVideoURL(this,
                    movieDetail.getMovieId());

            String reviewQueryUrl = NetworkUtils.BuildReviewURL(this,
                    movieDetail.getMovieId());

            if (videoQueryUrl != null && !videoQueryUrl.isEmpty() &&
                    reviewQueryUrl != null && !reviewQueryUrl.isEmpty()) {

                loaderQueryBundle.putString(VIDEO_QUERY_URL_EXTRA, videoQueryUrl);
                getSupportLoaderManager().initLoader(VIDEO_QUERY_LOADER,
                        loaderQueryBundle, MovieDetailActivity.this);

                loaderQueryBundle.putString(REVIEW_QUERY_URL_EXTRA, reviewQueryUrl);
                getSupportLoaderManager().initLoader(REVIEW_QUERY_LOADER,
                        loaderQueryBundle, MovieDetailActivity.this);
            } else {
                showLoadingFailed();
            }
        } else {
            setVideoReviewResults();
        }
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

    private void showLoadingFailed() {

        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mExpListVideoView != null) {
            mExpListVideoView.setVisibility(View.INVISIBLE);
        }

        if (mExpListReviewView != null) {
            mExpListReviewView.setVisibility(View.INVISIBLE);
        }
    }

    private void displayUiElements() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mLoadingBar != null) {
            mLoadingBar.setVisibility(View.INVISIBLE);
        }

        if (mExpListVideoView != null) {
            mExpListVideoView.setVisibility(View.VISIBLE);
        }

        if (mExpListReviewView != null) {
            mExpListReviewView.setVisibility(View.VISIBLE);
        }
    }

    private void setVideoReviewResults() {
        //TODO: Complete set values

        MovieData.GridArrangement currentArrangement = MovieData.getInstance().getCurrentGridArrangement();
        ReviewResponse reviewResponse = MovieData.getInstance().getMovieDetailsArray(currentArrangement)
                .getResultsArray()
                .get(mCurrentPosition)
                .getReviewResponse();

        VideoResponse videoResponse = MovieData.getInstance().getMovieDetailsArray(currentArrangement)
                .getResultsArray()
                .get(mCurrentPosition)
                .getVideoResponse();

        if (videoResponse == null || reviewResponse == null) {
            return;
        }

        if (mExpListVideoView != null) {

            List<String> headingsList = Arrays.asList(getString(R.string.exp_heading_trailers));
            HashMap<String, List<?>> childMap = new HashMap<>();
            List<VideoDetails> videosList = videoResponse.getResultsArray();

            if (videosList.size() == 0) {
                VideoDetails videoDetails = new VideoDetails();
                videosList = Arrays.asList(videoDetails);
            }

            childMap.put(getResources().getString(R.string.exp_heading_trailers), videosList);

            final MovieExpandableListAdapter videoListAdapter = new MovieExpandableListAdapter(this,
                    ExpandableListType.EXPANDABLE_VIDEO_LIST,
                    headingsList,
                    childMap);

            mExpListVideoView.setAdapter(videoListAdapter);
            mExpListVideoView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    VideoDetails clickedVideo = (VideoDetails) videoListAdapter.getChild(i, i1);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedVideo.getYoutubeUrl()));
                    startActivity(intent);
                    return true;
                }
            });

        }

        if (mExpListReviewView != null) {

            List<String> headingsList = Arrays.asList(getString(R.string.exp_heading_reviews));
            HashMap<String, List<?>> childMap = new HashMap<>();
            List<ReviewDetails> reviewsList = reviewResponse.getResultsArray();

            if (reviewsList.size() == 0) {
                ReviewDetails reviewDetails = new ReviewDetails();
                reviewsList = Arrays.asList(reviewDetails);
            }

            childMap.put(getResources().getString(R.string.exp_heading_reviews), reviewsList);

            final MovieExpandableListAdapter reviewListAdapter = new MovieExpandableListAdapter(this,
                    ExpandableListType.EXPANDABLE_REVIEW_LIST,
                    headingsList,
                    childMap);

            mExpListReviewView.setAdapter(reviewListAdapter);
            mExpListReviewView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    ReviewDetails clickedReview = (ReviewDetails) reviewListAdapter.getChild(i, i1);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedReview.getReviewUrl()));
                    startActivity(intent);
                    return true;
                }
            });

        }

        displayUiElements();
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
                if (id == VIDEO_QUERY_LOADER) {
                    searchQueryString = args.getString(VIDEO_QUERY_URL_EXTRA);
                } else if (id == REVIEW_QUERY_LOADER) {
                    searchQueryString = args.getString(REVIEW_QUERY_URL_EXTRA);
                }

                if (searchQueryString == null || searchQueryString.isEmpty()) {
                    return null;
                }

                URL searchUrl;
                try {
                    searchUrl = NetworkUtils.BuildUrlFromString(searchQueryString);
                } catch (MalformedURLException e) {
                    Log.d("Detail Activity", "Building URL from String failed: " + e.getMessage());
                    return null;
                }

                try {
                    return NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    Log.d("Detail Activity", "getResponseFromHttpUrl failed: " + e.getLocalizedMessage());
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

        MovieData.GridArrangement currentArrangement = MovieData.getInstance().getCurrentGridArrangement();

        if (loader.getId() == VIDEO_QUERY_LOADER) {

            VideoResponse videoResponse = null;
            try {
                videoResponse = JsonUtils.parseVideoResponseJson(data);
            } catch (JsonParseException e) {
                Log.d("Detail Activity", "Video Response JSON could not be de-serialized: " + e.getMessage());
                showLoadingFailed();
                return;
            }

            MovieData.getInstance().getMovieDetailsArray(currentArrangement)
                    .getResultsArray()
                    .get(mCurrentPosition)
                    .setVideoResponse(videoResponse);

        } else if (loader.getId() == REVIEW_QUERY_LOADER) {

            ReviewResponse reviewResponse = null;
            try {
                reviewResponse = JsonUtils.parseReviewResponseJson(data);
            } catch (JsonParseException e) {
                Log.d("Detail Activity", "Review Response JSON could not be de-serialized: " + e.getMessage());
                showLoadingFailed();
                return;
            }

            MovieData.getInstance().getMovieDetailsArray(currentArrangement)
                    .getResultsArray()
                    .get(mCurrentPosition)
                    .setReviewResponse(reviewResponse);
        }

        getSupportLoaderManager().destroyLoader(loader.getId());
        setVideoReviewResults();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}