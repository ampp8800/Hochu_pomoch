package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallback;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {

    public void newsItemLoadingCallback(boolean isConnect, @NonNull String guid) {
        NewsItemLoadingCallback newsItemLoadingCallback = new NewsItemLoadingCallback() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel) {
                getViewState().setReceivedData(newsItemModel);
            }
        };
        if (isConnect) {
            NetworkNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
        } else {
            DatabaseNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
        }
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

}
