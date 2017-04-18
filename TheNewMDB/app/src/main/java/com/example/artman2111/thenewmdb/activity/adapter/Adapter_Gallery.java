package com.example.artman2111.thenewmdb.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.models.Gallery_Acept;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by artman2111 on 14.04.17.
 */

public class Adapter_Gallery extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private LayoutInflater inflater;
    private Context context;
    private List<Gallery_Acept> galleries;


    public Adapter_Gallery(Context context,List<Gallery_Acept> galleries){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.galleries = galleries;
        Log.d("artman","galleries size -> " +galleries.size());

    }


    public class ItemView extends RecyclerView.ViewHolder{
        View rootView;
        ImageView imageViewImageFilm;
        public ItemView(View itemView) {
            super(itemView);
            rootView = itemView;
            imageViewImageFilm = (ImageView) itemView.findViewById(R.id.itemPopularImage);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_poster_film,parent,false);
        return new ItemView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("artman","galleries size -> " +galleries.size());
        String result = "https://image.tmdb.org/t/p/w500";
        Drawable d;
        d = (context.getResources().getDrawable(R.drawable.images));
        Picasso.with(context).load(result+galleries.get(position).getImage()).resize(513,769).placeholder(d).into(((ItemView) holder).imageViewImageFilm);


    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }
}
