package com.example.mbankole.flixster;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mbankole.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetail extends AppCompatActivity {

    public final static String TAG = "MovieDetailActivity";

    Movie movie;

    ImageView ivPosterImage;
    ImageView ivBackdropImage;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    String posterUrl;
    String backdropUrl;
    float voteAverage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        context = this.getApplicationContext();
        ivPosterImage = (ImageView)findViewById(R.id.ivPosterImage);
        ivBackdropImage = (ImageView)findViewById(R.id.ivBackdropImage);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar)findViewById(R.id.rbVoteAverage);
        posterUrl = getIntent().getStringExtra("posterUrl");
        backdropUrl = getIntent().getStringExtra("backdropUrl");

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        voteAverage = movie.getVoteAverage();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        Glide.with(context)
                .load(backdropUrl)
                //.bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(R.drawable.flicks_backdrop_placeholder)
                .into(ivBackdropImage);
    }
}
