package com.koalasdev.ooary.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ooary on 28/07/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "movie.db";
    public static final int VERSION = 1;

    public DbHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DATABASE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME
                + " ("+ FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE + " TEXT NOT NULL"
                + ");";
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
