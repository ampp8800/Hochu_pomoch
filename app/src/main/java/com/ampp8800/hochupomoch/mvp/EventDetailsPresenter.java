package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
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
            try {
                NetworkNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
            }
            catch (Exception e) {
                getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.no_response_from_the_network));
                DatabaseNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
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
