package com.koalasdev.ooary.popularmovies.Config;

/**
 * Created by ooary on 24/06/2017.
 */

public class Api {
    public String API_KEY = "xxxxxxxxxxxxxxx";//use your own api key get from themoviedb
    public String POPULAR_LINK = "http://api.themoviedb.org/3/movie/popular?api_key=";
    public String TOP_RATED_LINK = "http://api.themoviedb.org/3/movie/top_rated?api_key=";

    public String PATH_IMAGE_POSTER_LINK = "http://image.tmdb.org/t/p/w500/";

    public String getPopularLink(){
        return POPULAR_LINK+API_KEY;
    }

    public String getTopRatedLink(){
        return  TOP_RATED_LINK+API_KEY;
    }

    public String getImagePath(){
        return PATH_IMAGE_POSTER_LINK;
    }
}
