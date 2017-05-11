package com.example.artman2111.thenewmdb.activity.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.artman2111.thenewmdb.R;
import com.example.artman2111.thenewmdb.activity.models.FilmAccept;

import java.util.ArrayList;

/**
 * Created by artman2111 on 14.04.17.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout m_DrawerLayout = null;
    private View m_NavigationDrawer = null;
    private ArrayList<FilmAccept> searchFilm;
    private String query = null;
    private EditText searchText;
    private RecyclerView adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchFilm = new ArrayList<>();
        findViewById(R.id.searchButtonActivity).setOnClickListener(this);
        Toolbar m_toolbar;
        m_toolbar = (Toolbar) findViewById(R.id.toolbar);
        m_toolbar.setTitle("Поиск");
        m_DrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayoutMainActivity);
        m_NavigationDrawer = findViewById(R.id.NavigationDrawer);
        setSupportActionBar(m_toolbar);
        Drawable menuIconDrawable = ContextCompat.getDrawable(this,R.drawable.ic_menu_black_24dp);
        menuIconDrawable.setColorFilter(ContextCompat.
                getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        adapter = (RecyclerView) findViewById(R.id.mainActivityForMovie);
        searchText = (EditText) findViewById(R.id.searchEditText);
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
                query = searchText.toString();
                break;
        }
    }
}
