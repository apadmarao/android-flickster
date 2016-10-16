package com.ani.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ani.flickster.MovieDetailActivity;
import com.ani.flickster.R;
import com.ani.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static final int POSTER_VIEW_ORDINAL = 0;
    private static final int BACKDROP_VIEW_ORDINAL = 1;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.item_movie, movies);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        return movie.getRating() <= 5 ? POSTER_VIEW_ORDINAL : BACKDROP_VIEW_ORDINAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = getItem(position);

        ViewHolder viewHolder;
        // check the existing view being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            if (getItemViewType(position) == POSTER_VIEW_ORDINAL) {
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new PosterViewHolder(convertView);
            } else {
                convertView = inflater.inflate(R.layout.item_movie_backdrop, parent, false);
                viewHolder = new BackdropViewHolder(convertView);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.populate(movie);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    interface ViewHolder {
        void populate(Movie movie);
    }

    private class PosterViewHolder implements ViewHolder {
        private TextView tvTitle;
        private TextView tvOverview;
        private ImageView ivMovieImage;

        private PosterViewHolder(View view) {
            ivMovieImage = (ImageView) view.findViewById(R.id.ivMovieImage);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvOverview = (TextView) view.findViewById(R.id.tvOverview);
        }

        @Override
        public void populate(Movie movie) {
            // clear the image view
            ivMovieImage.setImageResource(0);
            // populate data
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
            Picasso.with(getContext())
                    .load(image(movie))
                    .centerInside()
                    .fit()
                    .placeholder(R.drawable.ic_movie_black_24dp)
                    .into(ivMovieImage);
        }

        private String image(Movie movie) {
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                return movie.getPosterPath("w780");
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return movie.getBackdropPath("w1280");
            } else {
                throw new IllegalStateException(String.format("Unexpected orientation %s", orientation));
            }
        }
    }

    private class BackdropViewHolder implements ViewHolder {
        private ImageView ivMovieBackdropImage;

        private BackdropViewHolder(View view) {
            ivMovieBackdropImage = (ImageView) view.findViewById(R.id.ivMovieBackdropImage);
        }

        @Override
        public void populate(Movie movie) {
            // clear the image view
            ivMovieBackdropImage.setImageResource(0);
            Picasso.with(getContext())
                    .load(movie.getBackdropPath("w1280"))
                    .centerInside()
                    .fit()
                    .placeholder(R.drawable.ic_movie_black_24dp)
                    .into(ivMovieBackdropImage);
        }
    }
}
