package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

import com.ampp8800.hochupomoch.data.ListItem;

import java.util.List;

public interface ProfileView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProfile(@NonNull ListItem userListItem, @NonNull List<ListItem> friends);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void disconnect();

}