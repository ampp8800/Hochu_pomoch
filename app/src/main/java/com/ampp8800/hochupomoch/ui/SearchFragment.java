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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ampp8800.hochupomoch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchFragment extends Fragment {
    private SearchAdapter pageAdapter;
    private ViewPager2 pager;
    private final int AUTOMATIC_SEARCH_SECOND = 2;
    private final Handler handler = new Handler();

    @NonNull
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setUpAppBar(((AppCompatActivity) requireActivity()).getSupportActionBar());
        // две страницы с результатами поиска
        pager = view.findViewById(R.id.vp_search);
        pageAdapter = new SearchAdapter(requireActivity());
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
        //ввод текста
        ((EditText) requireActivity().findViewById(R.id.et_search_query)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                executeSearch();
                return true;
            }
        });
        // нажатие кнопки поиска
        requireActivity().findViewById(R.id.iv_search_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeSearch();
            }
        });
        //автоматический поиск
        initAutoSearch(requireActivity().findViewById(R.id.et_search_query));
        return view;
    }

    private void executeSearch() {
        String searchQuery = ((EditText) requireActivity().findViewById(R.id.et_search_query)).getText().toString();
        int currentItem = pager.getCurrentItem();
        pageAdapter.updatePageContent(requireActivity(), searchQuery, currentItem);
    }

    private void setUpAppBar(@NonNull ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setElevation(0);
        requireActivity().findViewById(R.id.iv_icon_back).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.search_layout).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.search);
    }

    private void initAutoSearch(@NonNull EditText editText) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                executeSearch();
            }
        };
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                handler.removeCallbacks(runnable);
                if (!s.toString().isEmpty()) {
                    handler.postDelayed(runnable, AUTOMATIC_SEARCH_SECOND * 1000);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
