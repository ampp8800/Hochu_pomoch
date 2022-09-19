package com.ampp8800.hochupomoch.mvp;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {

    public EventDetailsPresenter(){
        getViewState().loadEventDetails();

    }

}
