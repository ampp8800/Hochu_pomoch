package com.ampp8800.hochupomoch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SearchAdapter extends FragmentStateAdapter {
    private final int NUMBER_OF_TABS = 2;
    private SearchPageFragment searchPageFragment;

    public SearchAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        searchPageFragment = SearchPageFragment.newInstance(position);
        return searchPageFragment;
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_TABS;
    }

    public void updatePageAttachment(String searchQery, @NonNull int currentItem) {
        searchPageFragment.updatePageFragment(searchQery, currentItem);
    }

}
