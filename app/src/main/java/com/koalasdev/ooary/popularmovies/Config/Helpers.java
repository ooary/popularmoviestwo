package com.koalasdev.ooary.popularmovies.Config;

/**
 * Created by ooary on 25/06/2017.
 */

public class Helpers {

    public static String dateFormat (String tanggal){
        return tanggal.substring(8,10)+'/'+tanggal.substring(5,7)+'/'+tanggal.substring(0,4);
    }
}
