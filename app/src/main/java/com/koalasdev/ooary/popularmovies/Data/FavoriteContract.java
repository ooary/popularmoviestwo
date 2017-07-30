package com.koalasdev.ooary.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ooary on 28/07/2017.
 */

public class FavoriteContract  {

    public static final String AUTHORITY = "com.koalasdev.ooary.popularmovies";
    public static final Uri BASE_URI_CONTENT = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "favorite";

    public static final class FavoriteEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_URI_CONTENT.buildUpon().appendPath(PATH).build();


        public static final String TABLE_NAME= "favorite";
        public static final String COLUMN_ID_MOVIE = "id_movie";

    }
}
