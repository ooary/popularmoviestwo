package com.koalasdev.ooary.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ooary on 28/07/2017.
 */

public class FavoriteContentProvider extends ContentProvider {

    private DbHelper mDbHelper;

    public static final int FAVORITE = 100;
    public static final int FAVORITE_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH, FAVORITE);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH+ "/#", FAVORITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match){
            case FAVORITE:
                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);

                String mSelection = "id_movie=?";

                String[] mSelectionArgs = {id};


                Toast.makeText(getContext(), "selection Args" + mSelectionArgs, Toast.LENGTH_SHORT).show();

                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                throw new UnsupportedOperationException("Uknown Uri :" + uri.toString());
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case FAVORITE:
                long id = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,null,values);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI,id);
                }else{
                    throw new SQLException("Failed to insert" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Uknown Uri:" + uri);

        }

        //Notify URI if the uri changed
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int unFavorite;
        switch (match){
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                unFavorite = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,"id_movie=?",new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Uknown Uri " + uri.toString());
        }

        if (unFavorite != 0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return unFavorite;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
