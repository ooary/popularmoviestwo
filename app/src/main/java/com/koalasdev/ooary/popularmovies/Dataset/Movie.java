package com.koalasdev.ooary.popularmovies.Dataset;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ooary on 24/06/2017.
 */

public class Movie implements Parcelable{

    private String title;
    private String poster_path;
    private String release_date;
    private String vote;
    private String synopsis;
    private String id_movie;


    public Movie(String title, String poster_path, String release_date, String vote, String synopsis, String id_movie) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote = vote;
        this.synopsis = synopsis;
        this.id_movie = id_movie;
    }

    public Movie(Parcel source) {
        this.title = source.readString();
        this.poster_path = source.readString();
        this.release_date = source.readString();
        this.vote = source.readString();
        this.synopsis = source.readString();
        this.id_movie = source.readString();
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

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.title);
        dest.writeString( this.poster_path);
        dest.writeString( this.release_date);
        dest.writeString( this.vote);
        dest.writeString( this.synopsis);
        dest.writeString( this.id_movie);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
