package com.koalasdev.ooary.popularmovies.Config;

/**
 * Created by ooary on 24/06/2017.
 */

public class Api {
    public String API_KEY = "21e152f83127dc7c8b53b7ff95a14b94";//use your own api key get from themoviedb
    public String POPULAR_LINK = "http://api.themoviedb.org/3/movie/popular?api_key=";
    public String TOP_RATED_LINK = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    public String PATH_IMAGE_POSTER_LINK = "http://image.tmdb.org/t/p/w500/";
    public String FAVORITED_MOVIE_LINK = "https://api.themoviedb.org/3/movie/";
    public String YOUTUBE_LINK = "www.youtube.com/watch?v=";


    public String getPopularLink(){
        return POPULAR_LINK+API_KEY;
    }

    public String getTopRatedLink(){
        return  TOP_RATED_LINK+API_KEY;
    }

    public String getImagePath(){
        return PATH_IMAGE_POSTER_LINK;
    }

    public String getFavoritedMovieLink(String id){
        return FAVORITED_MOVIE_LINK+id+"?api_key="+API_KEY;
    }
    public String getReviewLink(String id){
        return FAVORITED_MOVIE_LINK+id+"/reviews?api_key="+API_KEY;
    }
    public String getYOUTUBE_LINK(String key){
        return YOUTUBE_LINK + key;
    }

    public String getTRAILER_LINK(String id){
        return FAVORITED_MOVIE_LINK+id+"/videos?api_key="+API_KEY;
    }
}
