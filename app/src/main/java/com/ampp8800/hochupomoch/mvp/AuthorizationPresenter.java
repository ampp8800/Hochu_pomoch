package com.ampp8800.hochupomoch.mvp;


import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.data.AuthorizationRepository;

import moxy.MvpPresenter;

public class AuthorizationPresenter extends MvpPresenter<AuthorizationView> {

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().loadingInteractiveFunctionality();

    }

    public void login(@NonNull String login, @NonNull String password) {
        AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();
        if (authorizationRepository.getLogin().equals(login)) {
            if (authorizationRepository.getPassword().equals(password)) {
                authorizationRepository.setAuthorized(true);
                getViewState().entry();
            } else {
                getViewState().showToastWrongPassword();
                authorizationRepository.setAuthorized(false);
            }
        } else {
            getViewState().showToastWrongLogin();
            authorizationRepository.setAuthorized(false);
        }
    }

    public void hideApplication() {
        getViewState().hideApplication();
    }

}
