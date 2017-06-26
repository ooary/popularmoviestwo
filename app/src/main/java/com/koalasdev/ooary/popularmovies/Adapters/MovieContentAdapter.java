package com.koalasdev.ooary.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koalasdev.ooary.popularmovies.Config.Api;
import com.koalasdev.ooary.popularmovies.Dataset.Movie;
import com.koalasdev.ooary.popularmovies.MainActivity;
import com.koalasdev.ooary.popularmovies.MovieDetail;
import com.koalasdev.ooary.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ooary on 24/06/2017.
 */

public class MovieContentAdapter  extends  RecyclerView.Adapter<MovieContentAdapter.viewHolderMovie>{

    Api endpoint = new Api();
    ArrayList<Movie> movieList = new ArrayList<>();
    Context context;

    public MovieContentAdapter(ArrayList<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }


    @Override
    public viewHolderMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View v = LayoutInflater.from(context).inflate(R.layout.movie_item_list,parent,false);

        viewHolderMovie vhm = new viewHolderMovie(v);
        return vhm;
    }

    @Override
    public void onBindViewHolder(viewHolderMovie holder, int position) {
        final Movie movie = movieList.get(position);

        Picasso.with(context)
                .load(endpoint.getImagePath()+movie.getPoster_path())
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.mImgPoster);
        holder.mImgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLicked" , "YES");
                Log.i("movie title",movie.getTitle());
                Intent i = new Intent(context, MovieDetail.class);
                i.putExtra("title",movie.getTitle());
                i.putExtra("overview",movie.getSynopsis());
                i.putExtra("poster_img",movie.getPoster_path());
                i.putExtra("release_date",movie.getRelease_date());
                i.putExtra("vote_average",movie.getVote()+"/ 10");
                context.startActivity(i);
            }
        });
    }

    class viewHolderMovie extends RecyclerView.ViewHolder{
        ImageView mImgPoster;

        public viewHolderMovie(View itemView) {
            super(itemView);
            mImgPoster = (ImageView) itemView.findViewById(R.id.img_poster);
        }


    }
}
