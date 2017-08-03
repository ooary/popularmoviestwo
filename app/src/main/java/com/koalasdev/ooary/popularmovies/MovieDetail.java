package com.koalasdev.ooary.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.koalasdev.ooary.popularmovies.Adapters.ReviewContentAdapter;
import com.koalasdev.ooary.popularmovies.Adapters.TrailerMovieAdapter;
import com.koalasdev.ooary.popularmovies.Config.Api;
import com.koalasdev.ooary.popularmovies.Config.Helpers;
import com.koalasdev.ooary.popularmovies.Data.FavoriteContract;
import com.koalasdev.ooary.popularmovies.Dataset.Review;
import com.koalasdev.ooary.popularmovies.Dataset.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    Helpers help = new Helpers();
    Api endpoint = new Api();
    ImageView mImagePoster,mImageFav;
    TextView mTitle,mOverview,mVote,mReleaseDate;
    Context context;
    ScrollView mSvDetail;
    Cursor data;
    public static final String LIST_STATE_USER = "recycler_view_user";
    public static final String LIST_STATE_TRAILER = "recycler_view_trailer";
    public static final int MOVIE_LOADER_ID = 0;
    public boolean isAvailable = false;
    RecyclerView mRvTrailer,mRvReview;
    ArrayList<Trailer> trailerList = new ArrayList<>();
    ArrayList<Review> reviewList = new ArrayList<>();
    ArrayList<Trailer> stateTrailer = new ArrayList<>();
    ArrayList<Review> stateReview = new ArrayList<>();
    RecyclerView.Adapter trailerContentAdapter,reviewContentAdapter;
    Parcelable mListStateUser,mListStateTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initRvReview();
        initRvTrailer();
        setDataFromIntent();
        mImageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAvailable==false){
                    Log.d("Clicked Favorite","true");
                    doFavorite();
                }else{
                    Log.d("Clicked Unfavorite","true");
                    unFavorite();
                }
            }
        });

        if(savedInstanceState !=null){
            stateReview = savedInstanceState.getParcelableArrayList(LIST_STATE_USER);
            stateTrailer  = savedInstanceState.getParcelableArrayList(LIST_STATE_TRAILER);
            reviewList.addAll(stateReview);
            trailerList.addAll(stateTrailer);

        }else{
            final ProgressBar pb = (ProgressBar) findViewById(R.id.pb_trailer);
            final ProgressBar pbReview = (ProgressBar)findViewById(R.id.pb_review);
            pb.setVisibility(View.VISIBLE);
            pbReview.setVisibility(View.VISIBLE);
            getTrailer(pb);
            fetchMovieDetail(pbReview);

        }
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,this);




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_TRAILER,trailerList);
        outState.putParcelableArrayList(LIST_STATE_USER,reviewList);
    }




    private void setDataFromIntent() {
        mTitle.setText(getIntent().getStringExtra("title"));
        mOverview.setText(getIntent().getStringExtra("overview"));
        mVote.setText(getIntent().getStringExtra("vote_average"));
        mReleaseDate.setText(help.dateFormat(getIntent().getStringExtra("release_date")));
        Picasso.with(context).load(endpoint.getImagePath()+getIntent().getStringExtra("poster_img")).into(mImagePoster);
//        Toast.makeText(this, getIntent().getStringExtra("id_movie"), Toast.LENGTH_SHORT).show();
    }

    private void initRvTrailer() {
        mRvTrailer = (RecyclerView)findViewById(R.id.rv_trailer);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        trailerContentAdapter = new TrailerMovieAdapter(trailerList,this);
        mRvTrailer.setHasFixedSize(true);
        mRvTrailer.setLayoutManager(llm);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTrailer.setAdapter(trailerContentAdapter);
    }

    private void initRvReview() {
        mRvReview = (RecyclerView)findViewById(R.id.rv_review);
        LinearLayoutManager llmReview = new LinearLayoutManager(getApplicationContext());
        reviewContentAdapter = new ReviewContentAdapter(reviewList,this);
        mRvReview.setHasFixedSize(true);
        mRvReview.setLayoutManager(llmReview);
        llmReview.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvReview.setAdapter(reviewContentAdapter);
    }

    private void initView() {
        mImagePoster = (ImageView)findViewById(R.id.img_poster_detail);
        mTitle = (TextView)findViewById(R.id.tv_title);
        mOverview = (TextView)findViewById(R.id.tv_overview);
        mVote = (TextView)findViewById(R.id.tv_vote);
        mReleaseDate = (TextView)findViewById(R.id.tv_release_date);
        mImageFav = (ImageView) findViewById(R.id.image_fav);
        mSvDetail = (ScrollView) findViewById(R.id.sv_detail);
    }

    public void getTrailer(final ProgressBar pb){
                String fix = endpoint.getTRAILER_LINK(getIntent().getStringExtra("id_movie"));
                AndroidNetworking.get(fix)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response trailer",response.toString());
                                try{
                                    JSONArray jsonArray = response.getJSONArray("results");
                                    Log.d("Json Array", jsonArray.toString());
                                    for (int i = 0; i<jsonArray.length();i++){
                                        JSONObject trailerObject = jsonArray.getJSONObject(i);
                                        Trailer trailer = new Trailer(
                                                trailerObject.getString("name"),
                                                trailerObject.getString("key")
                                                );
                                        trailerList.add(trailer);
                                        trailerContentAdapter.notifyDataSetChanged();
                                    }
                                pb.setVisibility(View.GONE);
                                }catch(Exception e){

                                }


                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
    }
    public void unFavorite(){
        Log.d("Work Unfavorite","YES");
        String id = getIntent().getStringExtra("id_movie");
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();
        getContentResolver().delete(uri,null,null);
        mImageFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_unfav_black_24dp));
        isAvailable = false;
        Toast.makeText(this, "Unfavorite Clicked", Toast.LENGTH_SHORT).show();
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
    }

    public void doFavorite(){
        Log.d("Work doFavorite","YES");
        String id = getIntent().getStringExtra("id_movie");
        String original_title = getIntent().getStringExtra("title");
        ContentValues cv = new ContentValues();
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE,id);
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE_MOVIE,original_title);
        Uri uri = getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI,cv);
        if(uri != null){
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
        }
        isAvailable = true;
        mImageFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        Toast.makeText(this, "Favorite Clicked", Toast.LENGTH_SHORT).show();
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);


    }

    public void fetchMovieDetail(final ProgressBar pb){

        String idMovie = getIntent().getStringExtra("id_movie");
        String movieDetailEndpoint = endpoint.getReviewLink(idMovie);
        AndroidNetworking.get(movieDetailEndpoint)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Log.d("Response Movie Detail",response.toString());
                        try{
                            JSONArray resultArray = response.getJSONArray("results");
                            for (int i = 0; i<resultArray.length();i++){
                                JSONObject resultObject = resultArray.getJSONObject(i);
                                Review review = new Review(resultObject.getString("author"),resultObject.getString("content"));
                                reviewList.add(review);
                                reviewContentAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                onPause();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mData = null ;
            @Override
            protected void onStartLoading() {
                if(mData != null){
                    deliverResult(mData);
                }else{
                    forceLoad();
                }
                super.onStartLoading();
            }

            @Override
            public Cursor loadInBackground() {
                String idMovie = getIntent().getStringExtra("id_movie");
                Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
                String[] mSelectionArgs = {idMovie};
                Log.d("ID MOVIE FROM INTENT ",idMovie);
                return getContentResolver().query(uri,
                        null,
                        FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE+"=?",
                        mSelectionArgs,
                        null);
            }
            @Override
            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //https://gxnotes.com/article/123465.html
        if (data != null && data.moveToFirst()){
            int columnData = data.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE);
            String getIdMovie = data.getString(columnData);
            Log.d("Data Id Movie", getIdMovie);
            if (getIdMovie == null){
                Log.d("Data Kosong","Data kosong");
                isAvailable = false;
            }else{
                Log.d("Data ada","Data ada");
                isAvailable = true;
            }
            if(isAvailable == false){
                mImageFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_unfav_black_24dp));
            }else{
                mImageFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
