package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SearchAdapter extends FragmentStateAdapter {
    private final int NUMBER_OF_TABS = 2;

    public SearchAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        SearchType currentSearchType;
        switch (position) {
            case 0:
                currentSearchType = SearchType.EVENT;
                break;
            case 1:
                currentSearchType = SearchType.ORGANIZATION;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return SearchPageFragment.newInstance(currentSearchType);
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_TABS;
    }

    public void updatePageContent(FragmentActivity fragmentActivity, @NonNull String searchQery, int currentItem) {
        ((SearchPageFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("f" + currentItem)).updatePageFragment(searchQery);
    }

}
