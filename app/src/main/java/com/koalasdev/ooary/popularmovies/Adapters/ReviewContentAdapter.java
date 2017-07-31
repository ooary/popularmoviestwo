package com.koalasdev.ooary.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koalasdev.ooary.popularmovies.Dataset.Review;
import com.koalasdev.ooary.popularmovies.MovieDetail;
import com.koalasdev.ooary.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by ooary on 31/07/2017.
 */

public class ReviewContentAdapter extends RecyclerView.Adapter<ReviewContentAdapter.ReviewViewHolder>{
    Context ctx;
    ArrayList<Review> reviews = new ArrayList<>();


    public ReviewContentAdapter(ArrayList<Review> reviews,Context ctx) {
        this.ctx = ctx;
        this.reviews = reviews;
    }



    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.review_item_list,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final Review review = reviews.get(position);
        holder.mTvUserReview.setText(review.getAuthor());
        holder.mTvReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView mTvReview,mTvUserReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mTvReview = (TextView)itemView.findViewById(R.id.tv_content_review);
            mTvUserReview = (TextView)itemView.findViewById(R.id.tv_author);
        }
    }
}
