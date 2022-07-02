package com.ampp8800.hochupomoch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ampp8800.hochupomoch.R;



public class MainActivity extends AppCompatActivity {
    private BottomNavigationLogic bottomNavigationLogic;
    private final String ID_PRESS_BUTTON = "idPressButton";

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
            bottomNavigationLogic.setButtonWasPreviouslyPress(findViewById(savedInstanceState.getInt(ID_PRESS_BUTTON)));
            bottomNavigationLogic.showPreviousApp();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ID_PRESS_BUTTON, bottomNavigationLogic.getIdOfPressedButton());
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(bottomNavigationLogic.getMainFragmentTag()) != null) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            bottomNavigationLogic.startHelpFragment();
        }
    }

}
