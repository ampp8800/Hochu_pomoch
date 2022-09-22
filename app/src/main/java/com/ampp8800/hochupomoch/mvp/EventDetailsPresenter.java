package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallbackOffline;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallbackOnline;
import com.ampp8800.hochupomoch.ui.NewsItemModelAndConnect;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {

    public void loadNews(@NonNull String guid) {
        if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
            NewsItemLoadingCallbackOnline newsItemLoadingCallbackOnline = new NewsItemLoadingCallbackOnline() {
                @Override
                public void onNewsItemUpdate(@NonNull NewsItemModelAndConnect newsItemModelAndConnect) {
                    if (newsItemModelAndConnect.isExeption()) {
                        getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.no_response_from_the_network));
                        loadNewsFromDatabase(guid);
                    } else {
                        getViewState().setReceivedData(newsItemModelAndConnect.getNewsItemModel());
                    }
                }
            };
            NetworkNewsRepository.newInstance().loadItemNews(newsItemLoadingCallbackOnline, guid);

        } else {
            loadNewsFromDatabase(guid);
        }
    }

    private void loadNewsFromDatabase(@NonNull String guid){
        NewsItemLoadingCallbackOffline newsItemLoadingCallbackOffline = new NewsItemLoadingCallbackOffline() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel) {
                getViewState().setReceivedData(newsItemModel);
            }
        };
        DatabaseNewsRepository.newInstance().loadItemNews(newsItemLoadingCallbackOffline, guid);
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

    public void setLineWithFriends() {
        getViewState().setLineWithFriends(ProfileRepository.getInstance().getFrendsList());
    }

}
