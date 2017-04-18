package com.example.artman2111.thenewmdb.activity.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.adapter.Adapter_movie;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Adapter_movie adapter;
    static boolean sortbypop = true;
    private Context context = this;
    private FilmModalAccept filmModalAccept;
    private LinearLayout linearLayout;
    private String[][] array;
    private String[][] array1;
    private Thread thread;
    public static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new Crashlytics());
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.mainActivityForMovie);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_movie(context, array);
        ActionBar actionBar = getSupportActionBar();
        setTitle("Popular Film");
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        array1= Adapter_movie.newArray;
        recyclerView.scrollToPosition(position);
        if (array1==null) {
            startThread();
        }
        if (array1!=null){
            adapter = new Adapter_movie(context, array1);
            recyclerView.setAdapter(adapter);
        }
        reloadAdapter();

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkinfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkinfo!=null && activeNetworkinfo.isConnected();
    }


    public String[][] update(){
        thread = new Thread(new Runnable() {
            private String[][] arraylist;
            @Override
            public void run() {
                filmModalAccept = new FilmModalAccept(MainActivity.this);
                arraylist = filmModalAccept.getPathsFromAPI(sortbypop);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        array = arraylist;
                    }

                });
            }
        });
        thread.start();
        return array;

    }
    private void reloadAdapter() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                reloadAdapter();
            }
        },1500);


    }
    public void startThread(){
        thread = new Thread(new Runnable() {
            private String[][] arraylist;

            @Override
            public void run() {
                filmModalAccept = new FilmModalAccept(MainActivity.this);
                arraylist = filmModalAccept.getPathsFromAPI(sortbypop);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        array = arraylist;
                        Context context = MainActivity.this;
                        adapter = new Adapter_movie(context, array);
                        recyclerView.setAdapter(adapter);

                    }

                });
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View v) {

    }
}

