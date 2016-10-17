package com.ani.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {

    private static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";

    private final long id;
    private final String backdropPath;
    private final String posterPath;
    private final String originalTitle;
    private final String overview;
    private final double rating; // rating between 0.5 and 10.0

    public Movie(JSONObject json) throws JSONException {
        id = json.getLong("id");
        backdropPath = json.getString("backdrop_path");
        posterPath = json.getString("poster_path");
        originalTitle = json.getString("original_title");
        overview = json.getString("overview");
        rating = json.getDouble("vote_average");
    }

    public static List<Movie> fromArray(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            movies.add(new Movie(array.getJSONObject(i)));
        }
        return movies;
    }

    public long getId() {
        return id;
    }

    public String getPosterPath(String size) {
        return getUrl(size, posterPath);
    }

    public String getBackdropPath(String size) {
        return getUrl(size, backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    private String getUrl(String size, String path) {
        return String.format("%s%s/%s", SECURE_BASE_URL, size, path);
    }
}
