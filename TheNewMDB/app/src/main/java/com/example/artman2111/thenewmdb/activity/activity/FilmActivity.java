package com.example.artman2111.thenewmdb.activity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.tmdb.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by artman2111 on 21.03.17.
 */


public class FilmActivity extends AppCompatActivity implements View.OnClickListener {
    private int position;
    private ScrollView scrollView;
    private TextView titel , about, data;
    private ImageButton imageButton;
    private Intent intent;
    private String title;
    private String id;
    private String overview;
    private String release;
    private String poster;

    public FilmActivity(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        scrollView = (ScrollView) findViewById(R.id.posterINfilmActivity) ;
        titel = (TextView) findViewById(R.id.titel);
        about = (TextView) findViewById(R.id.overvive);
        data = (TextView) findViewById(R.id.date);
        imageButton = (ImageButton) findViewById(R.id.imageButtonLayout);
        imageButton.setOnClickListener(this);
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                position = bundle.getInt("position");
                poster = bundle.getString("poster");
                id = bundle.getString("id");
                release = bundle.getString("release");
                title = bundle.getString("title");
                overview = bundle.getString("overview");

            }
        }
        setTitle(title);
        setBackground();
        titel.setText(title);
        about.setText(overview);
        data.setText(release);
        setBackground();

    }
    public void setBackground() {
        Drawable d = (this.getResources().getDrawable(R.drawable.images));
        String result = "https://image.tmdb.org/t/p/w780";
        Picasso.with(this).load(result + poster).placeholder(d).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                scrollView.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBackground();
            }
        },2000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.position = position;
        overridePendingTransition(R.anim.pull_in_down,R.anim.pull_in_up);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonLayout:
                Intent intent = new Intent(FilmActivity.this,FilmPosterActivity.class);
                intent.putExtra("id",id);
                FilmActivity.this.startActivity(intent);
                break;
        }

    }
}
