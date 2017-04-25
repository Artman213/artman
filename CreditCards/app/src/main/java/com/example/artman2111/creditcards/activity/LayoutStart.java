package com.example.artman2111.creditcards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.artman2111.creditcards.R;

/**
 * Created by artman2111 on 13.02.17.
 */

public class LayoutStart extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.start_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(LayoutStart.this, MainActivity.class);
                LayoutStart.this.startActivity(mainIntent);
                LayoutStart.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
