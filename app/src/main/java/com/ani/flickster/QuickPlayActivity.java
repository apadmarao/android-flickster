package com.ani.flickster;

import android.os.Bundle;

import com.ani.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class QuickPlayActivity extends YouTubeBaseActivity {

    public static final String MOVIE_EXTRA = "movie";
    private static final String VIDEOS =
            "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // FIXME : Setup with real key
    private static final String API_KEY = "";
    private final AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        final Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.ytPlayer);
        youTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        playVideo(movie.getId(), youTubePlayer);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private void playVideo(long videoId, final YouTubePlayer youTubePlayer) {
        client.get(String.format(VIDEOS, String.valueOf(videoId)), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    // default to playing the first video
                    if (results.length() == 0) {
                        return;
                    }

                    String key = results.getJSONObject(0).getString("key");
                    youTubePlayer.cueVideo(key);
                    youTubePlayer.play();
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
