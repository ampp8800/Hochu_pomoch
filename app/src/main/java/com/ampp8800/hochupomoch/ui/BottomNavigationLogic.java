package com.ampp8800.hochupomoch.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public BottomNavigationLogic(@NonNull FragmentActivity fragmentActivity, @NonNull View view) {
        this.fragmentActivity = fragmentActivity;
        this.view = view;
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
    public BottomMenuButton getEnumOfPressedButton() {
        return selectedButton;
    }

    public void startSearchFragment() {
        startFragment(SearchFragment.newInstance(), R.id.iv_search_button, R.drawable.ic_search_green, R.id.tv_search_button, BottomMenuButton.SEARCH_BUTTON);
    }

    public void startHelpFragment() {
        startFragment(HelpFragment.newInstance(), R.id.iv_help_button, R.drawable.green_circle, R.id.tv_help_button, BottomMenuButton.HELP_BUTTON);
    }

    public void startProfileFragment() {
        startFragment(ProfileFragment.newInstance(), R.id.iv_profile_button, R.drawable.ic_profile_green, R.id.tv_profile_button, BottomMenuButton.PROFILE_BUTTON);
    }

    private void startFragment (@NonNull Fragment fragment, @IdRes int idCurrentButton, @DrawableRes int drawbleNewImage, @IdRes int idCurrentTitle, BottomMenuButton newPressedButton) {
        if () {
            fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment, newPressedButton.getTag()).commit();
            setSelectedButton(idCurrentButton, drawbleNewImage, idCurrentTitle, newPressedButton);
        }
    }

    private void setSelectedButton(@IdRes int idCurrentButton, @DrawableRes int drawbleNewImage, @IdRes int idCurrentTitle, BottomMenuButton newPressedButton) {
        ((ImageView) view.findViewById(idCurrentButton)).setImageResource(drawbleNewImage);
        ((TextView) view.findViewById(idCurrentTitle)).setTextColor(fragmentActivity.getResources().getColor(R.color.leaf));
        removeColorSelection();
        selectedButton = newPressedButton;
    }

    private void removeColorSelection(){
        if (selectedButton != null) {
            switch (selectedButton) {
                case SEARCH_BUTTON:
                    ((ImageView) view.findViewById(R.id.iv_search_button)).setImageResource(R.drawable.ic_search_grey);
                    ((TextView) view.findViewById(R.id.tv_search_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
                    break;
                case HELP_BUTTON:
                    ((ImageView) view.findViewById(R.id.iv_help_button)).setImageResource(R.drawable.red_circle);
                    ((TextView) view.findViewById(R.id.tv_help_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
                    break;
                case PROFILE_BUTTON:
                    ((ImageView) view.findViewById(R.id.iv_profile_button)).setImageResource(R.drawable.ic_profile_grey);
                    ((TextView) view.findViewById(R.id.tv_profile_button)).setTextColor(fragmentActivity.getResources().getColor(R.color.warm_grey));
                    break;
            }
        }
    }

    public void showPreviousApp(BottomMenuButton buttonWasPreviouslyPress) {
        switch (buttonWasPreviouslyPress) {
            case SEARCH_BUTTON:
                setSelectedButton(R.id.iv_search_button, R.drawable.ic_search_green, R.id.tv_search_button, BottomMenuButton.SEARCH_BUTTON);
                break;
            case HELP_BUTTON:
                setSelectedButton(R.id.iv_help_button, R.drawable.green_circle, R.id.tv_help_button, BottomMenuButton.HELP_BUTTON);
                break;
            case PROFILE_BUTTON:
                setSelectedButton(R.id.iv_profile_button, R.drawable.ic_profile_green, R.id.tv_profile_button, BottomMenuButton.PROFILE_BUTTON);
                break;
        }
    }

}