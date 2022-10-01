package com.ampp8800.hochupomoch.mvp;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {

    private boolean isInitialized = false;

    public void showNewsScreen() {
        if (!isInitialized) {
            isInitialized = true;
            setUpAppBar();
            showNews();
        }
    }

    public void showNews() {
        getViewState().showNews();
    }

    public void setUpAppBar() {
        getViewState().setUpAppBar();
    }
}
