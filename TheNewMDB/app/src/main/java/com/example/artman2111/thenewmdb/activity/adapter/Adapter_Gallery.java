package com.example.artman2111.thenewmdb.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artman2111.thenewmdb.R;

/**
 * Created by artman2111 on 14.04.17.
 */

public class Adapter_Gallery extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private LayoutInflater inflater;
    private Context context;


    public Adapter_Gallery(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
