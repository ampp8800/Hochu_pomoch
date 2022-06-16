package com.ampp8800.hochupomoch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.AuthorizationRepository;


public class SplashActivity extends AppCompatActivity {
    final int LOAD_SECOND = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screan);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AuthorizationRepository.getInstance().isAuthorized(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, AuthorizationActivity.class));
                }
                finish();
            }
        }, LOAD_SECOND * 1000);
    }
}