package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies.Data.MovieDetails;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsAdapter extends ArrayAdapter<MovieDetails> {

    public MovieDetailsAdapter(Context context, List<MovieDetails> movieDetailsArray) {
        super(context, 0, movieDetailsArray);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieDetails currentMovie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movies, parent, false);
        }

        ImageView gridMovieIv = (ImageView) convertView.findViewById(R.id.grid_movie_icon);
        Picasso.with(getContext())
                .load(currentMovie.getPosterPath())
                .into(gridMovieIv);

        return convertView;
    }
}
