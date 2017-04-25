package com.example.artman2111.thenewmdb.activity.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.adapter.Adapter_movie;
import com.example.artman2111.thenewmdb.activity.models.Film_Wrapper;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Adapter_movie adapter;
    static boolean sortbypop = true;
    private Context context = this;
    private FilmModalAccept filmModalAccept;
    private List<Film_Wrapper> film_wrappers;
    private Thread thread;
    public static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new Crashlytics());
        film_wrappers = new ArrayList<>();
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.mainActivityForMovie);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_movie(context, film_wrappers);
        setTitle("Popular Film");
        if (isNetworkAvailable()){
            startThread();
        }else {
            Toast toast = Toast.makeText(context,"No internet connection ",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.scrollToPosition(position);
        if (film_wrappers.size()>1){
            adapter = new Adapter_movie(context, film_wrappers);
            recyclerView.setAdapter(adapter);
        }
        reloadAdapter();

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkinfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkinfo!=null && activeNetworkinfo.isConnected();
    }


    public void update(final List<Film_Wrapper> film_wrappers2, final int size) {
        this.film_wrappers = film_wrappers2;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FilmModalAccept filmModalAccept = new FilmModalAccept(film_wrappers, size);
                film_wrappers = filmModalAccept.getPathsFromAPI(sortbypop);

            }
        });

        FilmModalAccept.page++;
        thread.start();
    }
    private void reloadAdapter() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                reloadAdapter();
            }
        },1000);


    }
    public void startThread(){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                filmModalAccept = new FilmModalAccept(MainActivity.this);
                film_wrappers = filmModalAccept.getPathsFromAPI(sortbypop);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new Adapter_movie(MainActivity.this, film_wrappers);
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

