package com.example.artman2111.thenewmdb.activity.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.adapter.AdapterMovie;
import com.example.artman2111.thenewmdb.activity.models.FilmAccept;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmList;
import com.example.artman2111.thenewmdb.activity.tmdb.ApiService;
import com.example.artman2111.thenewmdb.activity.tmdb.Constants;
import com.example.artman2111.thenewmdb.activity.tmdb.FilmModalAccept;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by artman2111 on 14.04.17.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout m_DrawerLayout = null;
    private View m_NavigationDrawer = null;
    private ArrayList<FilmAccept> searchFilm;
    public static String query = null;
    private EditText searchText;
    private AdapterMovie adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.searchButtonActivity).setOnClickListener(this);
        Toolbar m_toolbar;
        m_toolbar = (Toolbar) findViewById(R.id.toolbar);
        m_toolbar.setTitle(R.string.SearchEditText);
        m_DrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayoutMainActivity);
        m_NavigationDrawer = findViewById(R.id.NavigationDrawer);
        setSupportActionBar(m_toolbar);
        Drawable menuIconDrawable = ContextCompat.getDrawable(this,R.drawable.ic_menu_black_24dp);
        menuIconDrawable.setColorFilter(ContextCompat.
                getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        recyclerView = (RecyclerView) findViewById(R.id.searchActivityRecyclerView);
        searchText = (EditText) findViewById(R.id.searchEditText);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    search();
                }
                return true;
            }
        });
        m_toolbar.setNavigationIcon(menuIconDrawable);
        m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_DrawerLayout.openDrawer(m_NavigationDrawer);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchButtonActivity:
                search();
                break;
        }
    }
    public void search(){
        searchFilm = new ArrayList<>();
        query = searchText.getText().toString();
        ApiService api = FilmModalAccept.getApiService();

        Call<FilmList> call = api.getMyJSON(Constants.API_KEY,
                "ru",query,1,false);
        call.enqueue(new Callback<FilmList>() {
            @Override
            public void onResponse(Call<FilmList> call, Response<FilmList> response) {
                if (response.isSuccessful()){
                    searchFilm = response.body().getFilmAccepts();
                    final GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this,2);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AdapterMovie(SearchActivity.this,searchFilm,false);
                    recyclerView.setAdapter(adapter);

                }else {
                    Toast toast = Toast.makeText(SearchActivity.this,"Error",Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<FilmList> call, Throwable t) {
                Toast toast = Toast.makeText(SearchActivity.this,"Error",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        hideKeyboard(SearchActivity.this);
    }
    public static void hideKeyboard(Activity activity)
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception e)
        {
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }
}
