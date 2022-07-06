package com.ampp8800.hochupomoch.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ampp8800.hochupomoch.R;


public class BottomNavigationLogic {
    private View newsButton;
    private View searchButton;
    private View helpButton;
    private View historyButton;
    private View profileButton;
    private FragmentActivity fragmentActivity;
    private View view;
    private BottomMenuButton selectedButton;

    public BottomNavigationLogic(@NonNull FragmentActivity fragmentActivity, @NonNull View view, @NonNull BottomMenuButton selectedButton) {
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.selectedButton = selectedButton;
        newsButton = (View) view.findViewById(R.id.news_button);
        searchButton = (View) view.findViewById(R.id.search_button);
        helpButton = (View) view.findViewById(R.id.help_button);
        historyButton = (View) view.findViewById(R.id.history_button);
        profileButton = (View) view.findViewById(R.id.profile_button);
        searchButton.setOnClickListener(clickedView -> startSearchFragment());
        helpButton.setOnClickListener(clickedView -> startHelpFragment());
        profileButton.setOnClickListener(clickedView -> startProfileFragment());
    }

    @NonNull
    public BottomMenuButton getPressedButton() {
        return selectedButton;
    }

    public void startSearchFragment() {
        startFragment(SearchFragment.newInstance(), BottomMenuButton.SEARCH_BUTTON);
    }

    public void startHelpFragment() {
        startFragment(HelpFragment.newInstance(), BottomMenuButton.HELP_BUTTON);
    }

    public void startProfileFragment() {
        startFragment(ProfileFragment.newInstance(), BottomMenuButton.PROFILE_BUTTON);
    }

    private void startFragment(@NonNull Fragment newFragment, BottomMenuButton newPressedButton) {
        Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(newPressedButton.getTag());
        if (fragment == null || !fragment.isVisible()) {
            fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, newFragment, newPressedButton.getTag()).commit();
            removeColorFromButton();
            recolorPressedButton(newPressedButton);
        }
    }

    private void recolorButton(@IdRes int idCurrentButton, @DrawableRes int drawbleNewImage, @IdRes int idCurrentTitle, @ColorInt int color) {
        ((ImageView) view.findViewById(idCurrentButton)).setImageResource(drawbleNewImage);
        ((TextView) view.findViewById(idCurrentTitle)).setTextColor(color);
    }

    private void removeColorFromButton() {
        int color = fragmentActivity.getResources().getColor(R.color.warm_grey);
        switch (selectedButton) {
            case SEARCH_BUTTON:
                recolorButton(R.id.iv_search_button, R.drawable.ic_search_grey, R.id.tv_search_button, color);
                break;
            case HELP_BUTTON:
                recolorButton(R.id.iv_help_button, R.drawable.red_circle, R.id.tv_help_button, color);
                break;
            case PROFILE_BUTTON:
                recolorButton(R.id.iv_profile_button, R.drawable.ic_profile_grey, R.id.tv_profile_button, color);
                break;
        }
    }

    public void recolorPressedButton(BottomMenuButton selectedButton) {
        this.selectedButton = selectedButton;
        int color = fragmentActivity.getResources().getColor(R.color.leaf);
        switch (selectedButton) {
            case SEARCH_BUTTON:
                recolorButton(R.id.iv_search_button, R.drawable.ic_search_green, R.id.tv_search_button, color);
                break;
            case HELP_BUTTON:
                recolorButton(R.id.iv_help_button, R.drawable.green_circle, R.id.tv_help_button, color);
                break;
            case PROFILE_BUTTON:
                recolorButton(R.id.iv_profile_button, R.drawable.ic_profile_green, R.id.tv_profile_button, color);
                break;
        }
    }

}