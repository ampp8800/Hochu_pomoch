package com.ampp8800.hochupomoch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    final int LOAD_SECOND = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screan);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProfileRepository profileRepository = ProfileRepository.getInstance();
                if (profileRepository.getAuthorization()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, AuthorizationActivity.class));
                }
                finish();
            }
        }, LOAD_SECOND * 1000);
    }
}
