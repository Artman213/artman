package com.example.artman2111.thenewmdb.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.activity.BestFilmActivity;
import com.example.artman2111.thenewmdb.activity.activity.FavoriteActivity;
import com.example.artman2111.thenewmdb.activity.activity.MainActivity;
import com.example.artman2111.thenewmdb.activity.tmdb.Constants;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

/**
 * Created by artman2111 on 05.05.17.
 */

public class NavigationDrawableFragment extends Fragment implements View.OnClickListener {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, true);
        rootView.findViewById(R.id.popFilmButton).setOnClickListener(this);
        rootView.findViewById(R.id.bestFilmButton).setOnClickListener(this);
        rootView.findViewById(R.id.favoriteActivityButton).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bestFilmButton:
                FilmModalAccept.page = 1;
                startIntent(BestFilmActivity.class);
                break;
            case R.id.popFilmButton:
                FilmModalAccept.page = 1;
                startIntent(MainActivity.class);
                break;
            case R.id.favoriteActivityButton:
                startIntent(FavoriteActivity.class);
                break;
        }

    }
    private void startIntent(Class b){
        Intent intent = new Intent(getActivity(), b);
        startActivity(intent);
        Constants.position = 1;

    }
}
