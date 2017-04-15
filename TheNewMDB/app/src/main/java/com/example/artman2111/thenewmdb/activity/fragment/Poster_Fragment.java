package com.example.artman2111.thenewmdb.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artman2111.thenewmdb.R;

/**
 * Created by artman2111 on 14.04.17.
 */

public class Poster_Fragment extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poster, container, false);
        return view;
    }



}
