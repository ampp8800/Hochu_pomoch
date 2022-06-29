package com.ampp8800.hochupomoch.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ampp8800.hochupomoch.R;


public class BottomNavigationLogic {
    private static Fragment helpFragment = HelpFragment.newInstance();
    private static Fragment searchFragment = SearchFragment.newInstance();
    private static Fragment profileFragment = ProfileFragment.newInstance();
    private final static String MAIN_FRAGMENT = "mainFragment";

    public static String getMainFragmentTag() {
        return MAIN_FRAGMENT;
    }

    public static void initializeBottomBar(FragmentActivity fragmentActivity, @NonNull View view) {
        View newsButton = (View) view.findViewById(R.id.news_button);
        View searchButton = (View) view.findViewById(R.id.search_button);
        View helpButton = (View) view.findViewById(R.id.help_button);
        View historyButton = (View) view.findViewById(R.id.history_button);
        View profileButton = (View) view.findViewById(R.id.profile_button);

        if (!searchFragment.isInLayout()) {
            searchButton.setOnClickListener(clickedView -> fragmentActivity.
                    getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragmentContainerView, searchFragment).commit());
        }

        if (!helpFragment.isInLayout()) {
            helpButton.setOnClickListener(clickedView -> startMainFragment(fragmentActivity));
        }

        if (!profileFragment.isInLayout()) {
            profileButton.setOnClickListener(clickedView -> fragmentActivity.
                    getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragmentContainerView, profileFragment).commit());
        }

    }

    public static void startMainFragment(@NonNull FragmentActivity fragmentActivity) {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, helpFragment, MAIN_FRAGMENT).commit();
    }

}