package com.koalasdev.ooary.popularmovies.Dataset;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ooary on 30/07/2017.
 */

public class Trailer implements Parcelable{
    public String name;
    public String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public Trailer(Parcel source) {
        this.name =source.readString();
        this.key = source.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.key);

    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
