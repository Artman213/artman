package com.example.artman2111.thenewmdb.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.activity.FilmPosterActivity;
import com.example.artman2111.thenewmdb.activity.adapter.Adapter_Gallery;
import com.example.artman2111.thenewmdb.activity.models.Gallery_Acept;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artman2111 on 14.04.17.
 */

public class Backdrops_Fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private Adapter_Gallery adapter_gallery;
    private List<Gallery_Acept> galleries = new ArrayList<>();
    private FilmModalAccept filmModalAcceptl;
    private String movieID;
    private String jsonArray;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_backdrops, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        filmModalAcceptl = new FilmModalAccept(getActivity());
        movieID = FilmPosterActivity.id;
        jsonArray =  "backdrops";
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        Start();
    }
    public void Start(){
        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentBackdrops);

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
                recyclerView.setAdapter(adapter_gallery);
                progressBar.setVisibility(View.GONE);

            }
        },1000);
    }
}
