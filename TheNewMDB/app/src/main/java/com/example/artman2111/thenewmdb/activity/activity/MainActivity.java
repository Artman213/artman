package com.example.artman2111.thenewmdb.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.adapter.AdapterMovie;
import com.example.artman2111.thenewmdb.activity.models.FilmAccept;
import com.example.artman2111.thenewmdb.activity.tmdb.Constants;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AdapterMovie adapter;
    private Context context = this;
    private FilmModalAccept filmModalAccept;
    private List<FilmAccept> film_accepts;
    private Thread thread;
    private DrawerLayout m_DrawerLayout = null;
    private View m_NavigationDrawer = null;
    private  static  final int MENU_ITEM_SEARCH = 5101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.sortbypop = true;
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new Crashlytics());
        setContentView(R.layout.activity_main);
        film_accepts = new ArrayList<>();
        Toolbar m_toolbar;
        m_toolbar = (Toolbar) findViewById(R.id.toolbar);
        m_toolbar.setTitle(R.string.popFilm);
        m_DrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayoutMainActivity);
        m_NavigationDrawer = findViewById(R.id.NavigationDrawer);
        setSupportActionBar(m_toolbar);
        Drawable menuIconDrawable = ContextCompat.getDrawable(this,R.drawable.ic_menu_black_24dp);
        menuIconDrawable.setColorFilter(ContextCompat.getColor(this,
                android.R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        m_toolbar.setNavigationIcon(menuIconDrawable);
        m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_DrawerLayout.openDrawer(m_NavigationDrawer);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.mainActivityForMovie);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterMovie(context, film_accepts,true);
        if (isNetworkAvailable()){
            startThread();
        }else {
            Toast toast = Toast.makeText(context,R.string.InternetError,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.scrollToPosition(Constants.position);
        if (film_accepts.size()>1){
            adapter = new AdapterMovie(context, film_accepts,true);
            recyclerView.setAdapter(adapter);
        }
        reloadAdapter();

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkinfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkinfo!=null && activeNetworkinfo.isConnected();
    }


    public void update(final List<FilmAccept> film_wrappers2) {
        FilmModalAccept.page++;
        this.film_accepts = film_wrappers2;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FilmModalAccept filmModalAccept = new FilmModalAccept(film_accepts);
                film_accepts = filmModalAccept.getPathsFromAPI(Constants.sortbypop);

            }
        });
        thread.start();
    }
    public void reloadAdapter() {
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
                film_accepts = filmModalAccept.getPathsFromAPI(Constants.sortbypop);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new AdapterMovie(MainActivity.this, film_accepts,true);
                        recyclerView.setAdapter(adapter);

                    }

                });
            }
        });
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem itemTime = menu.add(0, MENU_ITEM_SEARCH,0,R.string.SearchEditText);
        Drawable drawableIconTimer = ContextCompat
                .getDrawable(this, R.drawable.ic_search_black_24dp);
        drawableIconTimer.setColorFilter(ContextCompat
                .getColor(this, android.R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        itemTime.setIcon(drawableIconTimer);
        itemTime.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_ITEM_SEARCH:
                Intent intent = new Intent(this,SearchActivity.class);
                FilmModalAccept.page = 1;
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}

