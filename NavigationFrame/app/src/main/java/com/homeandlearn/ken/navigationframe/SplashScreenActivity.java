package com.homeandlearn.ken.navigationframe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_OUT_TIME=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       Handler handler=new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
            Intent i=new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
           }
       },SPLASH_OUT_TIME);
        }

}
