package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.myapplication.R;
import com.app.myapplication.helper.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Utils.getUserId(SplashActivity.this)!=null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        },1500);
    }
}