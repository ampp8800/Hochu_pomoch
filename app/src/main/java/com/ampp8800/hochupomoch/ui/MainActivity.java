package com.ampp8800.hochupomoch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ampp8800.hochupomoch.R;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            BottomNavigationLogic.startMainFragment(this);
        }
        //логика работы нижней панели навигации
        BottomNavigationLogic.initializeBottomBar(this, (View) findViewById(R.id.bottom_navigation));
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(BottomNavigationLogic.getMainFragmentTag()) != null) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            BottomNavigationLogic.startMainFragment(this);
        }
    }

}
