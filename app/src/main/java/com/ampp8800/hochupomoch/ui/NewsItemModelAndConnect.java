package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;

public class NewsItemModelAndConnect {
    @NonNull
    private NewsItemModel newsItemModel;
    private boolean isExeption;

    public NewsItemModelAndConnect(@NonNull NewsItemModel newsItemModel, boolean isExeption) {
        this.newsItemModel = newsItemModel;
        this.isExeption = isExeption;
    }

    @NonNull
    public NewsItemModel getNewsItemModel() {
        return newsItemModel;
    }

    public boolean isExeption() {
        return isExeption;
    }
}
