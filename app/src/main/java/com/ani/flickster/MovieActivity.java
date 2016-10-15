package com.ani.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ani.flickster.adapters.MovieArrayAdapter;
import com.ani.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String NOW_PLAYING =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private List<Movie> movies;
    ListView lvMovies;
    private MovieArrayAdapter movieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieArrayAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    movies.addAll(Movie.fromArray(response.getJSONArray("results")));
                    movieArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
