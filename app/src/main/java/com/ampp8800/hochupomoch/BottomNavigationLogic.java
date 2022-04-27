package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;


public class BottomNavigationLogic {

    public static void initializeBottomBar(@NonNull View view) {
        Context context = view.getContext();
        View newsButton = (View) view.findViewById(R.id.news_button);
        View searchButton = (View) view.findViewById(R.id.search_button);
        View helpButton = (View) view.findViewById(R.id.help_button);
        View historyButton = (View) view.findViewById(R.id.history_button);
        View profileButton = (View) view.findViewById(R.id.profile_button);

        if (context.getClass() != SearchActivity.class) {
            searchButton.setOnClickListener(clickedView -> context.startActivity(new Intent(context, SearchActivity.class)));
        }

        if (context.getClass() != MainActivity.class) {
            helpButton.setOnClickListener(clickedView -> context.startActivity(new Intent(context, MainActivity.class)));
        }

        if (context.getClass() != ProfileActivity.class) {
            profileButton.setOnClickListener(clickedView -> context.startActivity(new Intent(context, ProfileActivity.class)));
        }
    }
}