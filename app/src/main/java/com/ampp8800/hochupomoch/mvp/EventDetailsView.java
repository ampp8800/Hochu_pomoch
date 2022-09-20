package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface EventDetailsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setReceivedData(@NonNull NewsItemModel newsItemModel);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void sendEmail(@NonNull NewsItemModel newsItemModel);
}
