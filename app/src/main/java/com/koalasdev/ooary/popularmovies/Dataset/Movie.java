package com.koalasdev.ooary.popularmovies.Dataset;

/**
 * Created by ooary on 24/06/2017.
 */

public class Movie {

    private String title;
    private String poster_path;
    private String release_date;
    private String vote;
    private String synopsis;


    public Movie(String title, String poster_path, String release_date, String vote, String synopsis) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote = vote;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }


}
