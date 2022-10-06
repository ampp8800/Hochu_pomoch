package com.ampp8800.hochupomoch.mvp;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface AuthorizationView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadingInteractiveFunctionality();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void entry();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToastWrongPassword();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToastWrongLogin();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideApplication();
}
