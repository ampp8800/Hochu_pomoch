package com.ampp8800.hochupomoch.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.ampp8800.hochupomoch.data.OnItemClickListener;

import java.util.List;

public class NewsFragment extends Fragment {
    @NonNull
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @NonNull
    private RecyclerView recyclerView;
    @NonNull
    private ProgressBar progressBar;
    @NonNull
    private ImageView ivIconBack;
    @NonNull
    private ImageView ivFilter;
    @NonNull
    private TextView tvToolbarName;
    @NonNull
    private final String ARG_EVENT_DETAIL_FRAGMENT = "eventDetailFragment";

    @NonNull
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Activity activity = requireActivity();
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        ivIconBack = activity.findViewById(R.id.iv_icon_back);
        ivFilter = activity.findViewById(R.id.iv_filter);
        tvToolbarName = (TextView) activity.findViewById(R.id.tv_toolbar_name);
        progressBar = view.findViewById(R.id.pb_progress_bar);
        recyclerView = view.findViewById(R.id.news_list);
        swipeRefreshLayout = view.findViewById(R.id.srl_news_fragment);
        swipeRefreshLayout.setColorSchemeResources(R.color.leaf);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeListOfNews(view, view.getContext());
            }
        });
        initializeListOfNews(view, view.getContext());
        setUpAppBar(actionBar);
        return view;
    }

    private void initializeListOfNews(@NonNull View view, @NonNull Context context) {
        recyclerView.setHasFixedSize(false);
        if (isScreenRotatedHorizontally()) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String guid) {
                openEventDetails(guid);
            }
        };
        adapter = new NewsAdapter(context, onItemClickListener);
        recyclerView.setAdapter(adapter);
        if (NetworkStateHelper.isConnected(context)) {
            NetworkNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(@NonNull List newsListItems) {
                    refreshNewsListOnScreen(newsListItems);
                }
            });
        } else {
            DatabaseNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(@NonNull List newsListItems) {
                    refreshNewsListOnScreen(newsListItems);
                }
            });
        }
    }

    private void refreshNewsListOnScreen(@NonNull List newsListItems) {
        adapter.updateNewsListItems(newsListItems);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean isScreenRotatedHorizontally() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ivIconBack.setVisibility(View.GONE);
        ivFilter.setVisibility(View.VISIBLE);
        tvToolbarName.setText(R.string.news);
    }

    private void openEventDetails(@NonNull String guid) {
        EventDetailsFragment eventDetailsFragment = EventDetailsFragment.newInstance(guid);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, eventDetailsFragment, ARG_EVENT_DETAIL_FRAGMENT)
                .commit();
    }

}