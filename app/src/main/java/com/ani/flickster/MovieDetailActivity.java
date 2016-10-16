package com.ani.flickster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ani.flickster.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA);

        TextView tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        ImageView ivDetailMovieImage = (ImageView) findViewById(R.id.ivDetailMovieImage);
        RatingBar rbDetailRating = (RatingBar) findViewById(R.id.rbDetailRating);
        TextView tvDetailOverview = (TextView) findViewById(R.id.tvDetailOverview);

        tvDetailTitle.setText(movie.getOriginalTitle());
        Picasso.with(this).load(movie.getBackdropPath("w1280")).into(ivDetailMovieImage);
        rbDetailRating.setIsIndicator(true);
        rbDetailRating.setNumStars(10);
        rbDetailRating.setStepSize(0.5f);
        rbDetailRating.setRating((float) movie.getRating());
        tvDetailOverview.setText(movie.getOverview());
    }
}
