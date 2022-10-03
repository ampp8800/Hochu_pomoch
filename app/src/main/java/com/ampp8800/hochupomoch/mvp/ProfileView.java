package com.ampp8800.hochupomoch.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ProfileView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProfile();

}
