package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallback;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {

    public void loadNews(@NonNull String guid) {
        NewsItemLoadingCallback newsItemLoadingCallback = new NewsItemLoadingCallback() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel) {
                getViewState().setReceivedData(newsItemModel);
            }
        };
        if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
            NetworkNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
            try {
                Thread.sleep(3000);
                if (!NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
                    getViewState().showToast("no response from the network");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            DatabaseNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
        }
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

    public void setLineWithFriends() {
        getViewState().setLineWithFriends(ProfileRepository.getInstance().getFrendsList());
    }

}
