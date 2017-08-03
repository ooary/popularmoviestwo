package com.koalasdev.ooary.popularmovies.Dataset;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ooary on 31/07/2017.
 */

public class Review implements Parcelable{
    String author;
    String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Review(Parcel source) {
        this.author = source.readString();
        this.content = source.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };


}
