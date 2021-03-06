package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ampp8800.hochupomoch.R;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationLogic bottomNavigationLogic;
    private final String ARG_PRESS_BUTTON = "argPressButton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //логика работы нижней панели навигации
        bottomNavigationLogic = new BottomNavigationLogic(this, (View) findViewById(R.id.bottom_navigation), BottomMenuButton.HELP_BUTTON);
        //
        if (savedInstanceState == null) {
            bottomNavigationLogic.startFragment(HelpFragment.newInstance(), BottomMenuButton.HELP_BUTTON);
        } else {
            bottomNavigationLogic.recolorPressedButton((BottomMenuButton) savedInstanceState.getSerializable(ARG_PRESS_BUTTON));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_PRESS_BUTTON, bottomNavigationLogic.getPressedButton());
    }

    public void onBackPressed() {
        if (bottomNavigationLogic.getPressedButton() == BottomMenuButton.HELP_BUTTON) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            bottomNavigationLogic.startFragment(HelpFragment.newInstance(), BottomMenuButton.HELP_BUTTON);
        }
    }

}
