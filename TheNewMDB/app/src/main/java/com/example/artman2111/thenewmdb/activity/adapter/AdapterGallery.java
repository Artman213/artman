package com.example.artman2111.thenewmdb.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.activity.ImageActivity;
import com.example.artman2111.thenewmdb.activity.models.GalleryAccept;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by artman2111 on 14.04.17.
 */

public class AdapterGallery extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private LayoutInflater inflater;
    private Context context;
    private List<GalleryAccept> galleries;


    public AdapterGallery(Context context, List<GalleryAccept> galleries){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.galleries = galleries;
    }


    private class ItemView extends RecyclerView.ViewHolder{
        View rootView;
        ImageView imageViewImageFilm;
        private ItemView(View itemView) {
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String result = "https://image.tmdb.org/t/p/w500";
        Drawable d;
        d = (context.getResources().getDrawable(R.drawable.images));
        Picasso.with(context).load(result+galleries.get(position).getPoster())
                .placeholder(d).into(((ItemView) holder).imageViewImageFilm);
        ((ItemView) holder).imageViewImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClick(holder.getAdapterPosition());
            }
        });



    }
    private void OnClick(int id){
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image",galleries.get(id).getPoster());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }
}
