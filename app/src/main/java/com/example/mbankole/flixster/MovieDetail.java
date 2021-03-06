package com.example.mbankole.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mbankole.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieDetail extends AppCompatActivity {


    //Base url for api
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //API key parameter name
    public final static String API_KEY_PARAM = "api_key";
    //tag for logging from this activity
    public final static String TAG = "MovieDetailActivity";

    AsyncHttpClient client;

    Movie movie;

    ImageView ivBackdropImage;
    ImageView ivBackdropImagePlay;
    ImageView ivPosterImage;
    ImageView ivPosterImagePlay;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvPopularity;
    RatingBar rbVoteAverage;
    String posterUrl;
    String backdropUrl;
    float voteAverage;
    Context context;
    String youtubeKey = null;
    boolean isPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        client = new AsyncHttpClient();

        context = this.getApplicationContext();
        ivBackdropImage = (ImageView)findViewById(R.id.ivBackdropImage);
        ivBackdropImagePlay = (ImageView)findViewById(R.id.ivBackdropImagePlay);
        ivPosterImage = (ImageView)findViewById(R.id.ivPosterImage);
        ivPosterImagePlay = (ImageView)findViewById(R.id.ivPosterImagePlay);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvOverview);
        tvPopularity = (TextView)findViewById(R.id.tvPopularity);
        rbVoteAverage = (RatingBar)findViewById(R.id.rbVoteAverage);
        posterUrl = getIntent().getStringExtra("posterUrl");
        backdropUrl = getIntent().getStringExtra("backdropUrl");

        isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;


        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvPopularity.setText("Popularity : " + movie.getPopularity().toString());

        voteAverage = movie.getVoteAverage();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        getTrailer();

        if (isPortrait){
            Glide.with(context)
                    .load(backdropUrl)
                    //.bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .error(R.drawable.flicks_backdrop_placeholder)
                    .into(ivBackdropImage);
        }
        else {
            Glide.with(context)
                    .load(posterUrl)
                    //.bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .error(R.drawable.flicks_movie_placeholder)
                    .into(ivPosterImage);
        }

    }

    private void getTrailer() {
        String url = API_BASE_URL + "/movie/" + movie.getVideoId().toString() + "/videos";
        // set request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a get request, expects a json
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load up the results
                try {
                    JSONArray results = response.getJSONArray("results");

                    JSONObject obj =  results.getJSONObject(0);

                    if (obj.getString("site").equals("YouTube")) {
                        youtubeKey = obj.getString("key");
                    }

                    Log.i(TAG, String.format("Loaded youtube key %s for %s", youtubeKey, movie.getTitle()));

                    addTrailerListener();

                } catch (JSONException e) {
                    logError("Failed to parse movie videos json response", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get now playing movie data", throwable, true);
            }
        });
    }

    private void addTrailerListener() {
        if (youtubeKey != null) {
            if (isPortrait){
                ivBackdropImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MovieTrailerActivity.class);
                        // put in the shit
                        intent.putExtra("youtubeKey", youtubeKey);
                        // show the activity
                        context.startActivity(intent);
                    }
                });

                ivBackdropImagePlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MovieTrailerActivity.class);
                        // put in the shit
                        intent.putExtra("youtubeKey", youtubeKey);
                        // show the activity
                        context.startActivity(intent);
                    }
                });
            }
            else {
                ivPosterImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MovieTrailerActivity.class);
                        // put in the shit
                        intent.putExtra("youtubeKey", youtubeKey);
                        // show the activity
                        context.startActivity(intent);
                    }
                });

                ivPosterImagePlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MovieTrailerActivity.class);
                        // put in the shit
                        intent.putExtra("youtubeKey", youtubeKey);
                        // show the activity
                        context.startActivity(intent);
                    }
                });
            }

        }
        else {
            ivBackdropImage.setVisibility(View.INVISIBLE);
        }
    }

    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        // Always log errors
        Log.e(TAG, message, error);
        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
