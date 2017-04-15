package com.example.artman2111.thenewmdb.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;
import com.squareup.picasso.Picasso;

/**
 * Created by artman2111 on 13.03.17.
 */

public class Adapter_movie extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private int count;
    private String[][] array;
    public String[][] array1;
    private Context context;
    public static String[][] newArray;
    private String imageMovie = "imageMovie";
    private MainActivity mainActivity = new MainActivity();






    public Adapter_movie(Context context , String[][] paths ) {
        inflater = LayoutInflater.from(context);
        array = paths;
        if (array != null) {
            count = array.length;
        }
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int position1 = holder.getAdapterPosition();
        item(holder, position1);
        Handler handler = new Handler();
        ((ItemView) holder).imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position1 = holder.getAdapterPosition();
                Toast toast = Toast.makeText(context,"Hello i am new Toast in favorite button in position -> "+ position1,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
            }
        });
        ((ItemView) holder).imageViewImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position1 = holder.getAdapterPosition();
                onClickListener(position1);
            }
        });
        if (position1 == count-1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    upDate();
                }
            },500);
        }

    }
    public  void favoritButtonClick(int position){
        //TODO this is favorite button for my DATA BASE
    }
    public void onClickListener(int position){
        String id = array[position][0];
        String poster = array[position][1];
        String title = array[position][3];
        String overview = array[position][2];
        String release = array[position][4];
        Intent intent = new Intent(context, FilmActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("position",position);
        intent.putExtra("poster",poster);
        intent.putExtra("title",title);
        intent.putExtra("overview",overview);
        intent.putExtra("release",release);
        context.startActivity(intent);
    }
    public void item(final RecyclerView.ViewHolder holder,int position){
        Drawable d;
        int itemID = position;
        if (array!=null) {
            d = (context.getResources().getDrawable(R.drawable.images));
            String result = "https://image.tmdb.org/t/p/w500";
            String poster = array[position][1];
            Picasso.with(context).load(result+poster).placeholder(d).into(((ItemView) holder).imageViewImageFilm);
        }
    }

    public void upDate(){
        FilmModalAccept.page++;
        array1 = mainActivity.update();
        if (array1!=null) {
            count +=20;
            newArray = new String[count][count];
            for (int i = 0; i < count - 20; i++) {
                newArray[i][0] = array[i][0];
                newArray[i][1] = array[i][1];
                newArray[i][2] = array[i][2];
                newArray[i][3] = array[i][3];
                newArray[i][4] = array[i][4];
            }
            Log.d("artman", "array1 ------ >>>>>>       " + array1[1][1]);
            for (int i = 0; i < 20; i++) {
                newArray[(count - 20) + i][0] = array1[i][0];
                newArray[(count - 20) + i][1] = array1[i][1];
                newArray[(count - 20) + i][2] = array1[i][2];
                newArray[(count - 20) + i][3] = array1[i][3];
                newArray[(count - 20) + i][4] = array1[i][4];
            }
            array = newArray;
        }

    }
    @Override
    public int getItemCount() {
        return count;

    }


}
