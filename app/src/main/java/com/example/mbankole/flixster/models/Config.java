package com.example.mbankole.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbankole on 6/22/17.
 */

public class Config {
    //base value for loading images
    String imageBaseUrl;
    // the postersize
    String posterSize;
    // the backdrop size
    String backdropSize;

    public Config(JSONObject obj) throws JSONException {
        JSONObject images = obj.getJSONObject("images");
        //get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster sizes
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //use option 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
        // get the backdrop sizes
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        //use option 1 or w780 as a fallback
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }

    //helper method for creating urls
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
