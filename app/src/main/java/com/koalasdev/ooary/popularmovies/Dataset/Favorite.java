package com.koalasdev.ooary.popularmovies.Dataset;

/**
 * Created by ooary on 28/07/2017.
 */

public class Favorite {
    String id_movie;

    public Favorite(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }
}
