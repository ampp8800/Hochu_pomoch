package com.ampp8800.hochupomoch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ampp8800.hochupomoch.R;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            HelpFragment helpFragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, helpFragment).commitNow();
            helpFragment.setUpAppBar(getSupportActionBar());
        }
    }
}
