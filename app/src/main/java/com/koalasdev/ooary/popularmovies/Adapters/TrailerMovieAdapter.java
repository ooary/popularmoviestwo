package com.koalasdev.ooary.popularmovies.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.koalasdev.ooary.popularmovies.Config.Api;
import com.koalasdev.ooary.popularmovies.Dataset.Trailer;
import com.koalasdev.ooary.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by ooary on 30/07/2017.
 */

public class TrailerMovieAdapter  extends RecyclerView.Adapter<TrailerMovieAdapter.trailerViewHolder>{
    ArrayList<Trailer> trailerList = new ArrayList<>();
    Context context;

    public TrailerMovieAdapter(ArrayList<Trailer> trailerList, Context context) {
        this.trailerList = trailerList;
        this.context = context;
    }

    @Override
    public trailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.trailer_item_list,parent,false);
        trailerViewHolder tvh = new trailerViewHolder(view);
        return tvh;
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    @Override
    public void onBindViewHolder(final trailerViewHolder holder, int position) {
        final Trailer trailerContent = trailerList.get(position);
        holder.mBtnPlay.setText(trailerContent.getName());
        holder.mBtnPlay.setTag(trailerContent.getKey());

        holder.mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api endpoint = new Api();
                String fix = endpoint.getYOUTUBE_LINK(trailerContent.getKey());
                Toast.makeText(context, fix, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("vnd.youtube://"+trailerContent.getKey());
                Uri webUri = Uri.parse("https://www.youtube.com/watch?v="+trailerContent.getKey());
                Intent app = new Intent(Intent.ACTION_VIEW,uri );
                Intent web = new Intent(Intent.ACTION_VIEW,webUri);
                try{
                    context.startActivity(app);
                }catch (ActivityNotFoundException e){
                    context.startActivity(web);
                }

            }
        });

    }

    class trailerViewHolder extends RecyclerView.ViewHolder{
        Button mBtnPlay;

        public trailerViewHolder(View itemView) {
            super(itemView);
            mBtnPlay = (Button)itemView.findViewById(R.id.btn_play);
        }
    }
}
