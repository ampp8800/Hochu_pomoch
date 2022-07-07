package com.ampp8800.hochupomoch.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
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
        newsButton.setOnClickListener(clickedView -> startFragment(NewsFragment.newInstante(), BottomMenuButton.NEWS_BUTTON));
        searchButton.setOnClickListener(clickedView -> startFragment(SearchFragment.newInstance(), BottomMenuButton.SEARCH_BUTTON));
        helpButton.setOnClickListener(clickedView -> startFragment(HelpFragment.newInstance(), BottomMenuButton.HELP_BUTTON));
        profileButton.setOnClickListener(clickedView -> startFragment(ProfileFragment.newInstance(), BottomMenuButton.PROFILE_BUTTON));
    }

    @NonNull
    public BottomMenuButton getPressedButton() {
        return selectedButton;
    }

    public void startFragment(@NonNull Fragment newFragment, @NonNull BottomMenuButton newPressedButton) {
        Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(newPressedButton.getTag());
        if (fragment == null || !fragment.isVisible()) {
            fragmentActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, newFragment, newPressedButton.getTag())
                    .commit();
            removeColorFromButton();
            recolorPressedButton(newPressedButton);
        }
    }

    private void repaintButton(@DrawableRes int drawbleNewImage, @ColorInt int color) {
        ((ImageView) view.findViewById(selectedButton.getButton())).setImageResource(drawbleNewImage);
        ((TextView) view.findViewById(selectedButton.getTitle())).setTextColor(color);
    }

    private void removeColorFromButton() {
        repaintButton(selectedButton.getIconUnselected(),
                fragmentActivity.getResources().getColor(selectedButton.getTextColorUnselected()));
    }

    public void recolorPressedButton(BottomMenuButton selectedButton) {
        this.selectedButton = selectedButton;
        repaintButton(selectedButton.getIconSelected(),
                fragmentActivity.getResources().getColor(selectedButton.getTextColorSelected()));
    }

}