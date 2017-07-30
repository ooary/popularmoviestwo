package com.koalasdev.ooary.popularmovies.Dataset;

/**
 * Created by ooary on 30/07/2017.
 */

public class Trailer {
    public String name;
    public String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
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
}
