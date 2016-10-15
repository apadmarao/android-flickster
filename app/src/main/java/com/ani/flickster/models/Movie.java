package com.ani.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";

    private final String backdropPath;
    private final String posterPath;
    private final String originalTitle;
    private final String overview;

    public Movie(JSONObject json) throws JSONException {
        backdropPath = json.getString("backdrop_path");
        posterPath = json.getString("poster_path");
        originalTitle = json.getString("original_title");
        overview = json.getString("overview");
    }

    public static List<Movie> fromArray(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            movies.add(new Movie(array.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("%sw342/%s", SECURE_BASE_URL, posterPath);
    }

    public String getBackdropPath() {
        return String.format("%sw780/%s", SECURE_BASE_URL, backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }
}
