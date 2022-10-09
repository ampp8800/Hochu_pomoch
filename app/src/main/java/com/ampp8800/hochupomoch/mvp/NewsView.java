package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface NewsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void refreshNewsListOnScreen(@NonNull List newsListItems);

}
