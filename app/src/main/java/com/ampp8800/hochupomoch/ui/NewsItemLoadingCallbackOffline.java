package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;

public interface NewsItemLoadingCallbackOffline {
    void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel);
}
