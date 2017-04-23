package com.example.artman2111.thenewmdb.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.activity.FilmActivity;
import com.example.artman2111.thenewmdb.activity.activity.MainActivity;
import com.example.artman2111.thenewmdb.activity.models.Film_Wrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by artman2111 on 13.03.17.
 */

public class Adapter_movie extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Film_Wrapper> film_wrappers;
    private MainActivity mainActivity = new MainActivity();






    public Adapter_movie(Context context , List<Film_Wrapper> paths ) {
        inflater = LayoutInflater.from(context);
        this.film_wrappers = paths;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_popular_film, parent, false);
        return new ItemView(itemView);

    }


    public class ItemView extends RecyclerView.ViewHolder{
        View rootView;
        ImageView imageViewImageFilm;
        ImageButton imageButtonFavorite;
        public ItemView(View itemView) {
            super(itemView);
            rootView = itemView;
            imageViewImageFilm = (ImageView) itemView.findViewById(R.id.itemPopularImage);
            imageButtonFavorite = (ImageButton) itemView.findViewById(R.id.favoriteButton);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int position1 = holder.getAdapterPosition();
        item(holder, position1);
        ((ItemView) holder).imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context,"Hello i am new Toast in favorite button in position -> "+ position1,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
            }
        });
        ((ItemView) holder).imageViewImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(position1);
            }
        });
        if (position == film_wrappers.size()-1) {
            upDate();
        }
    }
    public  void favoritButtonClick(int position){
        //TODO this is favorite button for my DATA BASE
    }
    public void onClickListener(int position){
        Intent intent = new Intent(context, FilmActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("poster",film_wrappers.get(position).getPoster());
        intent.putExtra("id",film_wrappers.get(position).getId());
        intent.putExtra("title",film_wrappers.get(position).getTitle());
        intent.putExtra("overview",film_wrappers.get(position).getOverview());
        intent.putExtra("release",film_wrappers.get(position).getRelease());
        context.startActivity(intent);
    }
    public void item(final RecyclerView.ViewHolder holder,int position){
        Drawable d;
            d = (context.getResources().getDrawable(R.drawable.images));
            String result = "https://image.tmdb.org/t/p/w500";
            String poster = film_wrappers.get(position).getPoster();
            Picasso.with(context).load(result+poster).resize(513,769).placeholder(d).into(((ItemView) holder).imageViewImageFilm);
    }

    public void upDate() {
        mainActivity.update(film_wrappers,film_wrappers.size());
    }
    @Override
    public int getItemCount() {
        return film_wrappers.size();

    }


}
