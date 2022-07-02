package com.ampp8800.hochupomoch.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ampp8800.hochupomoch.R;


public class BottomNavigationLogic {
    private Fragment helpFragment = HelpFragment.newInstance();
    private Fragment searchFragment = SearchFragment.newInstance();
    private Fragment profileFragment = ProfileFragment.newInstance();
    private View newsButton;
    private View searchButton;
    private View helpButton;
    private View historyButton;
    private View profileButton;
    private FragmentActivity fragmentActivity;
    private View view;
    private final String MAIN_FRAGMENT = "mainFragment";
    private View buttonWasPreviouslyPress;

    public BottomNavigationLogic(FragmentActivity fragmentActivity, @NonNull View view) {
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        newsButton = (View) view.findViewById(R.id.news_button);
        searchButton = (View) view.findViewById(R.id.search_button);
        helpButton = (View) view.findViewById(R.id.help_button);
        historyButton = (View) view.findViewById(R.id.history_button);
        profileButton = (View) view.findViewById(R.id.profile_button);

        if (!searchFragment.isInLayout()) {
            searchButton.setOnClickListener(clickedView -> startSearchFragment());
        }

        if (!helpFragment.isInLayout()) {
            helpButton.setOnClickListener(clickedView -> startHelpFragment());
        }

        if (!profileFragment.isInLayout()) {
            profileButton.setOnClickListener(clickedView -> startProfileFragment());
        }

    }

    public String getMainFragmentTag() {
        return MAIN_FRAGMENT;
    }

    public int getIdOfPressedButton() {
        if (buttonWasPreviouslyPress == null) {
            return R.id.help_button;
        }
        return buttonWasPreviouslyPress.getId();
    }

    public void setButtonWasPreviouslyPress(View buttonWasPreviouslyPress) {
        this.buttonWasPreviouslyPress = buttonWasPreviouslyPress;
    }

    public void startSearchFragment() {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit();
        ((ImageView) view.findViewById(R.id.iv_search_button)).setImageResource(R.drawable.ic_search_green);
        ((TextView) view.findViewById(R.id.tv_search_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.leaf));
        removeColorSelection();
        buttonWasPreviouslyPress = searchButton;
    }

    public void startHelpFragment() {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, helpFragment, MAIN_FRAGMENT).commit();
        ((ImageView) view.findViewById(R.id.iv_help_button)).setImageResource(R.drawable.green_circle);
        ((TextView) view.findViewById(R.id.tv_help_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.leaf));
        removeColorSelection();
        buttonWasPreviouslyPress = helpButton;
    }

    public void startProfileFragment() {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, profileFragment).commit();
        ((ImageView) view.findViewById(R.id.iv_profile_button)).setImageResource(R.drawable.ic_profile_green);
        ((TextView) view.findViewById(R.id.tv_profile_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.leaf));
        removeColorSelection();
        buttonWasPreviouslyPress = profileButton;
    }

    private void removeColorSelection(){
        if (searchButton.equals(buttonWasPreviouslyPress)) {
            ((ImageView) view.findViewById(R.id.iv_search_button)).setImageResource(R.drawable.ic_search_grey);
            ((TextView) view.findViewById(R.id.tv_search_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
        } else if (helpButton.equals(buttonWasPreviouslyPress)) {
            ((ImageView) view.findViewById(R.id.iv_help_button)).setImageResource(R.drawable.red_circle);
            ((TextView) view.findViewById(R.id.tv_help_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
        } else if (profileButton.equals(buttonWasPreviouslyPress)) {
            ((ImageView) view.findViewById(R.id.iv_profile_button)).setImageResource(R.drawable.ic_profile_grey);
            ((TextView) view.findViewById(R.id.tv_profile_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
        }
    }

    public void showPreviousApp() {
        if (searchButton.equals(buttonWasPreviouslyPress)) {
            startSearchFragment();
        } else if (helpButton.equals(buttonWasPreviouslyPress)) {
            startHelpFragment();
        } else if (profileButton.equals(buttonWasPreviouslyPress)) {
            startProfileFragment();
        }
    }

}