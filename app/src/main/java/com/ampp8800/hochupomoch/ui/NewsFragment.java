package com.ampp8800.hochupomoch.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.OnItemClickListener;
import com.ampp8800.hochupomoch.mvp.NewsPresenter;
import com.ampp8800.hochupomoch.mvp.NewsView;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class NewsFragment extends MvpAppCompatFragment implements NewsView {
    @NonNull
    private NewsAdapter adapter;
    @NonNull
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

    @InjectPresenter
    NewsPresenter newsPresenter;

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
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ivIconBack = activity.findViewById(R.id.iv_icon_back);
        ivFilter = activity.findViewById(R.id.iv_filter);
        tvToolbarName = activity.findViewById(R.id.tv_toolbar_name);
        ivIconBack.setVisibility(View.GONE);
        ivFilter.setVisibility(View.VISIBLE);
        tvToolbarName.setText(R.string.news);
        progressBar = view.findViewById(R.id.pb_progress_bar);
        recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(false);
        if (isScreenRotatedHorizontally()) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String guid) {
                openEventDetails(guid);
            }
        };
        adapter = new NewsAdapter(getContext(), onItemClickListener);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.srl_news_fragment);
        swipeRefreshLayout.setColorSchemeResources(R.color.leaf);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsPresenter.loadNews();
            }
        });
        return view;
    }

    @Override
    public void refreshNewsListOnScreen(@NonNull List newsListItems) {
        adapter.updateNewsListItems(newsListItems);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(@NonNull String string) {
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean isScreenRotatedHorizontally() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void openEventDetails(@NonNull String guid) {
        EventDetailsFragment eventDetailsFragment = EventDetailsFragment.newInstance(guid);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, eventDetailsFragment, ARG_EVENT_DETAIL_FRAGMENT)
                .commit();
    }

}