package com.ampp8800.hochupomoch.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ampp8800.hochupomoch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchFragment extends Fragment {
    private SearchAdapter pageAdapter;
    private ViewPager2 pager;
    private final int AUTOMATIC_SEARCH_SECOND = 2;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // две страницы с результатами поиска
        pager = view.findViewById(R.id.vp_search);
        pageAdapter = new SearchAdapter(getActivity());
        pager.setAdapter(pageAdapter);
        // добавление заголовков страниц
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_search);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText(R.string.by_events);
                } else {
                    tab.setText(R.string.for_npo);
                }
            }
        });
        tabLayoutMediator.attach();
        return view;
    }

    private void executeSearch() {
        String searchQuery = ((EditText) getActivity().findViewById(R.id.et_search_query)).getText().toString();
        int currentItem = pager.getCurrentItem();
        pageAdapter.updatePageContent(getActivity(), searchQuery, currentItem);
    }

    public void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setElevation(0);
        getActivity().findViewById(R.id.iv_icon_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.search_layout).setVisibility(View.VISIBLE);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.search);
        //ввод текста
        ((EditText) getActivity().findViewById(R.id.et_search_query)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                executeSearch();
                return true;
            }
        });
        // нажатие кнопки поиска
        getActivity().findViewById(R.id.iv_search_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeSearch();
            }
        });
        //автоматический поиск
        initAutoSearch(getActivity().findViewById(R.id.et_search_query));
    }

    private void initAutoSearch(@NonNull EditText editText) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                executeSearch();
            }
        };
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    handler.postDelayed(runnable, AUTOMATIC_SEARCH_SECOND * 1000);
                }
            }

        });
    }

}
