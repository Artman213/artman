package com.example.artman2111.thenewmdb.activity.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.fragment.BackdropsFragment;
import com.example.artman2111.thenewmdb.activity.fragment.PosterFragment;

/**
 * Created by artman2111 on 08.04.17.
 */

public class FilmPosterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backdrops,poster;
    private BackdropsFragment backdrops_fragment;
    private PosterFragment poster_fragment;
    public static String id;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_film);
        backdrops = (Button) findViewById(R.id.backdrops);
        poster = (Button) findViewById(R.id.posters);
        poster.setOnClickListener(this);
        backdrops.setOnClickListener(this);

        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                id = bundle.getString("id");
            }
        }

        poster_fragment = new PosterFragment();
        backdrops_fragment = new BackdropsFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.fragmentGallery, backdrops_fragment, "backdrops_fragment");
        fragmentTransaction.commit();






    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.backdrops:
                fragmentTransaction
                        .replace(R.id.fragmentGallery, backdrops_fragment, "backdrops_fragment");
                backdrops.setBackground(getDrawable(R.drawable.button_active));
                poster.setBackground(getDrawable(R.drawable.button_disable));
                break;
            case R.id.posters:
                fragmentTransaction
                        .replace(R.id.fragmentGallery,poster_fragment,"poster_fragment");
                poster.setBackground(getDrawable(R.drawable.button_active));
                backdrops.setBackground(getDrawable(R.drawable.button_disable));
                break;
        }
        fragmentTransaction.commit();



    }
}
