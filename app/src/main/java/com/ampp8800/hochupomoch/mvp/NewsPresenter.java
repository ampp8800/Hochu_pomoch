package com.ampp8800.hochupomoch.mvp;

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
    }

}
