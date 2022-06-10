package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ampp8800.hochupomoch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchActivity extends AppCompatActivity {
    private SearchAdapter pageAdapter;
    private ViewPager2 pager;
    private final int AUTOMATIC_SEARCH_SECOND = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolbar();
        // две страницы с результатами поиска
        pager = findViewById(R.id.vp_search);
        pageAdapter = new SearchAdapter(this);
        pager.setAdapter(pageAdapter);
        // добавление заголовков страниц
        TabLayout tabLayout = findViewById(R.id.tab_layout_search);
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
        // нажатие кнопки поиска
        findViewById(R.id.iv_search_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeSearch();
            }
        });
        ((EditText) findViewById(R.id.et_search_query)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                executeSearch();
                return true;
            }
        });
        //логика работы нижней панели навигации
        BottomNavigationLogic.initializeBottomBar(findViewById(R.id.bottom_navigation));
        //автоматический поиск
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!((EditText) findViewById(R.id.et_search_query)).getText().toString().equals("")){
                    executeSearch();
                }
            }
        }, AUTOMATIC_SEARCH_SECOND * 1000);
    }

    private void executeSearch() {
        String searchQuery = ((EditText) findViewById(R.id.et_search_query)).getText().toString();
        int currentItem = pager.getCurrentItem();
        pageAdapter.updatePageContent(this, searchQuery, currentItem);
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setElevation(0);
        findViewById(R.id.iv_icon_back).setVisibility(View.GONE);
        findViewById(R.id.search_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_toolbar_name)).setText(R.string.search);
    }

}

