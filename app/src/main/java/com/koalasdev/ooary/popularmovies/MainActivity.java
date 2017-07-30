package com.koalasdev.ooary.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Connection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.koalasdev.ooary.popularmovies.Adapters.MovieContentAdapter;
import com.koalasdev.ooary.popularmovies.Config.Api;

import com.koalasdev.ooary.popularmovies.Data.FavoriteContract;
import com.koalasdev.ooary.popularmovies.Dataset.Favorite;
import com.koalasdev.ooary.popularmovies.Dataset.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    Api endpoint = new Api();
    RecyclerView mRvMovieList;
    ProgressDialog mProgressDialog;
    ArrayList<Movie> movieList = new ArrayList<>();
    RecyclerView.Adapter movieContentAdapter;
    Snackbar snackbar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvMovieList = (RecyclerView)findViewById(R.id.rv_movie_list);
        movieContentAdapter = new MovieContentAdapter(movieList,this);
        mRvMovieList.setHasFixedSize(true);
        mRvMovieList.setLayoutManager(new GridLayoutManager(this,calculateNoOfColumns(getApplicationContext())));
        mRvMovieList.setAdapter(movieContentAdapter);
        new movieTask().execute();

    }
    /*
        --references for calculateNoOfColumns
        https://review.udacity.com/?utm_medium=email&utm_campaign=reviewsapp-submission-reviewed&utm_source=blueshift&utm_content=reviewsapp-submission-reviewed&bsft_clkid=f9ddf1de-fad9-44b4-96b3-0598be3b69eb&bsft_uid=0bc34570-d21f-4d0e-8e8e-5e85c4ebd7f1&bsft_mid=032132ff-ba2d-4849-94c1-f8d6bef7a2b2&bsft_eid=6f154690-7543-4582-9be7-e397af208dbd&bsft_txnid=f0bc1e2b-24ea-42cc-a40d-5f780826e2a1#!/reviews/572416
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    private void fetchTopRated(){
        AndroidNetworking.get(endpoint.getTopRatedLink())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response Movie",response.toString());
                        try{
                            JSONArray movieArray = response.getJSONArray("results");
                            for (int i = 1;i<movieArray.length();i++){
                                JSONObject movieJson = movieArray.getJSONObject(i);
                                Movie movie = new Movie(movieJson.getString("original_title"),
                                        movieJson.getString("poster_path"),
                                        movieJson.getString("release_date"),
                                        movieJson.getString("vote_average"),
                                        movieJson.getString("overview"),
                                        movieJson.getString("id"));
                                movieList.add(movie);
                                movieContentAdapter.notifyDataSetChanged();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void fetchFavoriteMovie(){
        ArrayList dataId = new ArrayList();
        Cursor data= getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                FavoriteContract.FavoriteEntry._ID);
        if (data !=null && data.moveToFirst()){

            while(data.moveToNext()){
                int columnData = data.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE);
                dataId.add(data.getString(columnData));


            }
            data.close();
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        Log.d("data Favorite Movie", dataId.toString());

        for (Object id : dataId){
            String fixEndpoint = endpoint.getFavoritedMovieLink(id.toString());
            AndroidNetworking.get(fixEndpoint)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                Log.d("Response Favorite",response.get("original_title").toString());
                                Movie movie = new Movie(response.get("original_title").toString(),
                                        response.get("poster_path").toString(),
                                        response.get("release_date").toString(),
                                        response.get("vote_average").toString(),
                                        response.get("overview").toString(),
                                        response.get("id").toString());
                                movieList.add(movie);
                                movieContentAdapter.notifyDataSetChanged();
                            }catch (Exception e){

                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }



    }

    private void fetchPopularMovie() {
        AndroidNetworking.get(endpoint.getPopularLink())
                         .setPriority(Priority.HIGH)
                         .build()
                         .getAsJSONObject(new JSONObjectRequestListener() {
                             @Override
                             public void onResponse(JSONObject response) {

                                 Log.d("Response Movie",response.toString());
                                 try{
                                     JSONArray movieArray = response.getJSONArray("results");
                                     for (int i = 1;i<movieArray.length();i++){
                                            JSONObject movieJson = movieArray.getJSONObject(i);
                                            Movie movie = new Movie(movieJson.getString("original_title"),
                                                                    movieJson.getString("poster_path"),
                                                                    movieJson.getString("release_date"),
                                                                    movieJson.getString("vote_average"),
                                                                    movieJson.getString("overview"),
                                                                    movieJson.getString("id"));
                                            movieList.add(movie);
                                         movieContentAdapter.notifyDataSetChanged();
                                     }

                                 }catch (JSONException e){
                                        e.printStackTrace();
                                 }
                             }

                             @Override
                             public void onError(ANError anError) {

                             }
                         });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuSelected = item.getItemId();
        if(menuSelected == R.id.set_popular){
            if(movieList.size()>0) {
                movieList.clear();
                new movieTask().execute();
            }
            return true;
        }else if(menuSelected == R.id.set_top_rated){
            if(movieList.size()>0){
                movieList.clear();
                fetchTopRated();
            }

            return true;
        }else if (menuSelected == R.id.set_favorite){
            if(movieList.size()>0){
                movieList.clear();
                fetchFavoriteMovie();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class movieTask extends AsyncTask<URL,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(URL... params) {
            fetchPopularMovie();
            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }
}
