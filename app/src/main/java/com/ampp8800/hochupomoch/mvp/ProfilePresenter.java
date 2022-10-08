package com.ampp8800.hochupomoch.mvp;

import moxy.MvpPresenter;

import com.ampp8800.hochupomoch.data.AuthorizationRepository;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.ProfileRepository;

import java.util.List;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        ProfileRepository repository = ProfileRepository.getInstance();
        ListItem userListItem = repository.getUserListItem();
        List<ListItem> friends = repository.getFrendsList();
        getViewState().showProfile(userListItem, friends);
    }

    public void disconnect() {
        AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();
        authorizationRepository.setAuthorized(false);
    }

}
