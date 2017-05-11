package com.example.artman2111.thenewmdb.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.artman2111.thenewmdb.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by artman2111 on 03.05.17.
 */

public class ImageActivity extends Activity {
    Intent intent;
    ImageView viewPager;
    String image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager
                .LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_viewer);
        viewPager = (ImageView) findViewById(R.id.ViewPagerViewerActivity);
        String result = "https://image.tmdb.org/t/p/original";
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                image = bundle.getString("image");
            }
        }
        Picasso.with(this).load(result+image).into(viewPager);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(viewPager);
                photoViewAttacher.update();
            }
        },2000);
    }
}
