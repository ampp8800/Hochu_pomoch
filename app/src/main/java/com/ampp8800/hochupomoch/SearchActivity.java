package com.ampp8800.hochupomoch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchActivity extends AppCompatActivity {
    private SearchAdapter pageAdapter;
    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // инициализация тулбара
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setElevation(0);
        ((ImageView) findViewById(R.id.iv_edit)).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.iv_icon_back)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_toolbar_name)).setText(R.string.search);
        // две страницы с результатами поиска
        pager = findViewById(R.id.vp_search);
        pageAdapter = new SearchAdapter(this);
        pager.setAdapter(pageAdapter);
        // добавление заголовков страниц
        TabLayout tabLayout = findViewById(R.id.tab_search);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText(R.string.by_events);
                } else {
                    tab.setText(R.string.for_npo);
                }
            }
        });
        tabLayoutMediator.attach();
        // нажатие кнопки поиска
        ((View) findViewById(R.id.iv_search_event)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeSearch();
            }
        });
        ((EditText) findViewById(R.id.et_search_query)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                executeSearch();
                return false;
            }
        });
        //логика работы нижней панели навигации
        BottomNavigationLogic.initializeBottomBar((View) findViewById(R.id.bottom_navigation));

    }

    private void executeSearch() {
        String searchQuery = ((EditText) findViewById(R.id.et_search_query)).getText().toString();
        int currentItem = pager.getCurrentItem();
        pageAdapter.updatePageAttachment(searchQuery, currentItem);
    }
}

