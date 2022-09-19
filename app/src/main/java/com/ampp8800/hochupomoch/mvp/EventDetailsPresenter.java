package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallback;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {

    public void newsItemLoadingCallback() {
        NewsItemLoadingCallback newsItemLoadingCallback = new NewsItemLoadingCallback() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel) {
                getViewState().setUpAppBar(newsItemModel);
                getViewState().setReceivedData(newsItemModel);
            }
        };
        getViewState().downloadLocationSelection(newsItemLoadingCallback);
    }

    public void setPhotoFromNetwork(int idImageView, @NonNull String imageViewURL) {
        getViewState().setPhotoFromNetwork(idImageView, imageViewURL);
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

}
