package com.example.mbankole.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mbankole.flixster.models.Config;
import com.example.mbankole.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by mbankole on 6/22/17
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder> {

    // list of movies
    ArrayList<Movie> movies;
    //config
    Config config;
    //context for rendering
    Context context;

    public final static String TAG = "MovieAdapter";


    // constructor
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //creates and inflate a new view
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the new view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return that view wrapped by a viewgholder
        return new viewHolder(movieView);
    }

    //bind an inflated view to a new item
    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        //get the movie data at the specified position
        Movie movie = movies.get(position);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        String imageUrl = null;

        //build url for the image
        if (isPortrait) {
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        } else {
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        }

        // get the correct placeholder and imageview for the layout
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;


        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);

    }

    //returns the number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the viewholder as a static inner class
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetail.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                intent.putExtra("backdropUrl", config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath()));
                intent.putExtra("posterUrl", config.getImageUrl(config.getPosterSize(), movie.getPosterPath()));
                // show the activity
                context.startActivity(intent);
            }
        }

        public viewHolder(View itemView) {
            super(itemView);
            // lookup object by id
            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView)itemView.findViewById(R.id.ivBackdropImage);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView)itemView.findViewById(R.id.tvOverview);
            itemView.setOnClickListener(this);
        }

    }
}
