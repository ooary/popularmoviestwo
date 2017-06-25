package com.koalasdev.ooary.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.koalasdev.ooary.popularmovies.Config.Api;
import com.koalasdev.ooary.popularmovies.Config.Helpers;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {
    Helpers help = new Helpers();
    Api endpoint = new Api();
    ImageView mImagePoster;
    TextView mTitle,mOverview,mVote,mReleaseDate;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImagePoster = (ImageView)findViewById(R.id.img_poster_detail);
        mTitle = (TextView)findViewById(R.id.tv_title);
        mOverview = (TextView)findViewById(R.id.tv_overview);
        mVote = (TextView)findViewById(R.id.tv_vote);
        mReleaseDate = (TextView)findViewById(R.id.tv_release_date);

        mTitle.setText(getIntent().getStringExtra("title"));
        mOverview.setText(getIntent().getStringExtra("overview"));
        mVote.setText(getIntent().getStringExtra("vote_average"));
        mReleaseDate.setText(help.dateFormat(getIntent().getStringExtra("release_date")));
        Picasso.with(context).load(endpoint.getImagePath()+getIntent().getStringExtra("poster_img")).into(mImagePoster);
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
}
