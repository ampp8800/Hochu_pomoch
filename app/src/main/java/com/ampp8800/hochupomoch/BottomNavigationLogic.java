package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BottomNavigationLogic extends AppCompatActivity {
    View newsButton;
    View searchButton;
    View helpButton;
    View historyButton;
    View profileButton;
    Context context;
    Intent intent;

    public BottomNavigationLogic(Context context, View view) {
        this.context = context;
        newsButton = (View) view.findViewById(R.id.news_button);
        searchButton = (View) view.findViewById(R.id.search_button);
        helpButton = (View) view.findViewById(R.id.help_button);
        historyButton = (View) view.findViewById(R.id.history_button);
        profileButton = (View) view.findViewById(R.id.profile_button);

    }

    public void switchingSectionsByAccrual() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.help_button:
                        if (context.getClass() != MainActivity.class) {
                            intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.profile_button:
                        if (context.getClass() != ProfileActivity.class) {
                            intent = new Intent(context, ProfileActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                }
            }
        };

        newsButton.setOnClickListener(onClickListener);
        searchButton.setOnClickListener(onClickListener);
        helpButton.setOnClickListener(onClickListener);
        historyButton.setOnClickListener(onClickListener);
        profileButton.setOnClickListener(onClickListener);

    }
}
