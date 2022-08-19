package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;

import java.util.List;

public class NewsFragment extends Fragment {
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @NonNull
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        setUpAppBar(((AppCompatActivity) requireActivity()).getSupportActionBar());
        swipeRefreshLayout = view.findViewById(R.id.srl_news_fragment);
        swipeRefreshLayout.setColorSchemeResources(R.color.leaf);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeListOfNews(view, view.getContext());
            }
        });
        initializeListOfNews(view, view.getContext());
        return view;
    }

    private void initializeListOfNews(@NonNull View view, @NonNull Context context) {
        RecyclerView recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(false);
        if (isScreenRotatedHorizontally()) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        adapter = new NewsAdapter(context);
        recyclerView.setAdapter(adapter);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            NetworkNewsRepository networkNewsRepository = NetworkNewsRepository.newInstance();
            networkNewsRepository.newsLoading(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(List newsListItems) {
                    refreshNewsListOnScreen(newsListItems);
                }
            });
        } else {
            DatabaseNewsRepository databaseNewsRepository = DatabaseNewsRepository.newInstance();
            databaseNewsRepository.newsLoading(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(List newsListItems) {
                    refreshNewsListOnScreen(newsListItems);
                }
            });
        }
    }

    private void refreshNewsListOnScreen(@NonNull List newsListItems) {
        adapter.updateNewsListItems(newsListItems);
        super.getView().findViewById(R.id.pb_progress_bar).setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean isScreenRotatedHorizontally() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        requireActivity().findViewById(R.id.iv_icon_back).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.iv_filter).setVisibility(View.VISIBLE);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.news);
    }
}