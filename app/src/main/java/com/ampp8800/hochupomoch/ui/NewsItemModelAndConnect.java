package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;

public class NewsItemModelAndConnect {
    @NonNull
    private NewsItemModel newsItemModel;
    boolean isConnected;

    public NewsItemModelAndConnect(@NonNull NewsItemModel newsItemModel, boolean isConnected) {
        this.newsItemModel = newsItemModel;
        this.isConnected = isConnected;
    }

    @NonNull
    public NewsItemModel getNewsItemModel() {
        return newsItemModel;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
