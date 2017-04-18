package com.example.artman2111.thenewmdb.activity.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.fragment.Backdrops_Fragment;
import com.example.artman2111.thenewmdb.activity.fragment.Poster_Fragment;

/**
 * Created by artman2111 on 08.04.17.
 */

public class FilmPosterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView backdrops,poster;
    private LinearLayout linearLayout;
    private Backdrops_Fragment backdrops_fragment;
    private Poster_Fragment poster_fragment;
    public static String id;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_poster_film);
        linearLayout = (LinearLayout) findViewById(R.id.layoutFilmPosterActivity);
        findViewById(R.id.backdrops).setOnClickListener(this);
        findViewById(R.id.posters).setOnClickListener(this);

        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                id = bundle.getString("id");
            }
        }
        setTitle("Gallery");

        poster_fragment = new Poster_Fragment();
        backdrops_fragment = new Backdrops_Fragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentGallery, backdrops_fragment, "backdrops_fragment");
        fragmentTransaction.commit();






    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.backdrops:
                fragmentTransaction.replace(R.id.fragmentGallery, backdrops_fragment, "backdrops_fragment");
                break;
            case R.id.posters:
                fragmentTransaction.replace(R.id.fragmentGallery,poster_fragment,"poster_fragment");
                break;
        }
        fragmentTransaction.commit();



    }
}
