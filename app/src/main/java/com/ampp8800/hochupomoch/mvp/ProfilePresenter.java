package com.ampp8800.hochupomoch.mvp;

import moxy.MvpPresenter;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private boolean isInitialized = false;

    public void loadProfile() {
        if (!isInitialized) {
            isInitialized = true;
            getViewState().showProfile();
        }
    }
}
