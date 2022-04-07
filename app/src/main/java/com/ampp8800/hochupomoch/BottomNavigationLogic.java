package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;


public class BottomNavigationLogic {

    public static void switchingSectionsByAccrual(@NonNull Context context, @NonNull View view) {
        View newsButton = (View) view.findViewById(R.id.news_button);
        View searchButton = (View) view.findViewById(R.id.search_button);
        View helpButton = (View) view.findViewById(R.id.help_button);
        View historyButton = (View) view.findViewById(R.id.history_button);
        View profileButton = (View) view.findViewById(R.id.profile_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.help_button:
                        if (context.getClass() != MainActivity.class) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.profile_button:
                        if (context.getClass() != ProfileActivity.class) {
                            Intent intent = new Intent(context, ProfileActivity.class);
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



