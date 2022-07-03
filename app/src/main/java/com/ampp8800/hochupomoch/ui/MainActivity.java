package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ampp8800.hochupomoch.R;



public class MainActivity extends AppCompatActivity {
    private BottomNavigationLogic bottomNavigationLogic;
    private final String PRESS_BUTTON = "pressButton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //логика работы нижней панели навигации
        bottomNavigationLogic = new BottomNavigationLogic(this, (View) findViewById(R.id.bottom_navigation));
        //
        if (savedInstanceState == null) {
            bottomNavigationLogic.startHelpFragment();
        } else {
            bottomNavigationLogic.showPreviousApp((BottomMenuButton) savedInstanceState.getSerializable(PRESS_BUTTON));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PRESS_BUTTON, bottomNavigationLogic.getEnumOfPressedButton());
    }

    public void onBackPressed() {
        if (bottomNavigationLogic.getEnumOfPressedButton() != BottomMenuButton.HELP_BUTTON) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            bottomNavigationLogic.startHelpFragment();
        }
    }

}
