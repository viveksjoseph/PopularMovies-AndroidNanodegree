package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.Data.Movies.MovieDetails;
import com.example.android.popularmovies.MovieDetailActivity;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.MovieDetailsViewHolder> {

    private List<MovieDetails> mMovieDetailList;

    public class MovieDetailsViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mMoviePosterIv;

        private MovieDetailsViewHolder(View view) {
            super(view);
            mMoviePosterIv = view.findViewById(R.id.grid_movie_icon);
        }
    }

    @NonNull
    @Override
    public MovieDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.grid_item_movies, parent, false);
        return new MovieDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieDetailsViewHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext())
                .load(mMovieDetailList.get(position).getAppendedPosterPath())
                .into(holder.mMoviePosterIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_EXTRA_POSITION, position);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovieDetailList == null) {
            return 0;
        }

        return mMovieDetailList.size();
    }

    public void setMovieData(List<MovieDetails> movieList) {
        this.mMovieDetailList = movieList;
        notifyDataSetChanged();
    }
}

