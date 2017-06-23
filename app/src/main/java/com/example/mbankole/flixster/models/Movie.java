package com.example.mbankole.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mbankole on 6/22/17.
 */

@Parcel
public class Movie {
    //values from api
    String title;
    String overview;
    String posterPath; // only the partial url
    String backdropPath; // only the partial url
    double popularity;
    double voteAverage;
    String releaseDate;
    int id;
    boolean hasVideo;

    //default constructor
    public Movie() {}
    //constructor
    public Movie(JSONObject obj) throws JSONException {
        title = obj.getString("title");
        overview = obj.getString("overview");
        posterPath = obj.getString("poster_path");
        backdropPath = obj.getString("backdrop_path");
        popularity = obj.getDouble("popularity");
        voteAverage = obj.getDouble("vote_average");
        releaseDate = obj.getString("release_date");
        id = obj.getInt("id");
        hasVideo = obj.getBoolean("video");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() { return backdropPath; }

    public double getPopularity() { return popularity; }

    public float getVoteAverage() { return (float)voteAverage; }

    public String getReleaseDate() { return releaseDate; }

    public int getId() { return id; }

    public boolean hasVideo() { return hasVideo; }
}