package com.ampp8800.hochupomoch.mvp;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallback;

import moxy.MvpView;

public interface EventDetailsView extends MvpView {
    void setUpAppBar(NewsItemModel newsItemModel);
    void setReceivedData(NewsItemModel newsItemModel);
    void downloadLocationSelection(NewsItemLoadingCallback newsItemLoadingCallback);
    void setPhotoFromNetwork(int idImageView, String imageViewURL);
    void sendEmail(@NonNull NewsItemModel newsItemModel);
}
