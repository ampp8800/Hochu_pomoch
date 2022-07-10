package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.NewsItem;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private final ArrayList<NewsItem> news = new ArrayList<>();

    @NonNull
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        setUpAppBar(((AppCompatActivity) requireActivity()).getSupportActionBar());
        setInitialData();
        initializeListOfCategories(view, view.getContext());
        return view;
    }

    private void initializeListOfCategories(@NonNull View view, @NonNull Context context) {
        RecyclerView recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(true);
        if (isScreenRotatedHorizontally()) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        }
        NewsAdapter adapter = new NewsAdapter(context, news);
        recyclerView.setAdapter(adapter);
    }

    private boolean isScreenRotatedHorizontally() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setInitialData() {
        if (news.isEmpty()) {
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
            addCategoriesItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        }
    }

    private void addCategoriesItem(int photoNews, @NonNull String newsHeadline, @NonNull String briefDescriptionOfNews, @NonNull String date) {
        news.add(new NewsItem(photoNews, newsHeadline, briefDescriptionOfNews, date));
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        requireActivity().findViewById(R.id.iv_icon_back).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.iv_filter).setVisibility(View.VISIBLE);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.news);
    }
}
