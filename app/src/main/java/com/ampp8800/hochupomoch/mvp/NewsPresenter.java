package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;

import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        showNews();
    }

    public void showNews() {
        getViewState().showNews();
        if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
            NetworkNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(@NonNull List newsListItems) {
                    getViewState().refreshNewsListOnScreen(newsListItems);
                }
            });
        } else {
            DatabaseNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(@NonNull List newsListItems) {
                    getViewState().refreshNewsListOnScreen(newsListItems);
                }
            });
        }
    }

}
