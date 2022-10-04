package com.ampp8800.hochupomoch.mvp;

import moxy.MvpPresenter;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProfile();
    }

}
