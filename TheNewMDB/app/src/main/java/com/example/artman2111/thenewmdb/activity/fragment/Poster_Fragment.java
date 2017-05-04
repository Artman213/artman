package com.example.artman2111.thenewmdb.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.activity.FilmPosterActivity;
import com.example.artman2111.thenewmdb.activity.adapter.Adapter_Gallery;
import com.example.artman2111.thenewmdb.activity.models.Gallery_Accept;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artman2111 on 14.04.17.
 */

public class Poster_Fragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Adapter_Gallery adapter_gallery;
    private List<Gallery_Accept> galleries = new ArrayList<>();
    private FilmModalAccept filmModalAcceptl;
    private String movieID;
    private String jsonArray;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poster, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar3);
        jsonArray =  "posters";
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        Start();
    }
    public void Start(){
        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentPoster);
        movieID = FilmPosterActivity.id;
        filmModalAcceptl = new FilmModalAccept(getActivity());
        if (movieID!=null){
            galleries = filmModalAcceptl.getGalleryFromAPI(movieID,jsonArray);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter_gallery = new Adapter_Gallery(getActivity(),galleries);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter_gallery);
            }
        },1000);
    }
}
